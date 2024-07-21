package types

import androidx.compose.material.SnackbarHostState
import com.multiplatform.webview.cookie.Cookie
import com.russhwolf.settings.Settings
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import utils.errorLogExport
import utils.hoyolab.HoyolabAPI
import utils.hoyolab.HoyolabConst
import utils.hoyolab.HoyolabRequest

@Serializable
class UserAccount(
    var uid: String = "000000000",
    var username: String = "Unknown",
    var level: Int = 0,
    var icon: String = "",
    var activeDays: Int = 0,
    var unlockedCharCount: Int = 0,
    var achievements: Int = 0,
    var chestOpened: Int = 0,

    var isLogin: Boolean = false,
    var cookies: String = "",
    var hoyolabId: String = "",
    var server: HoyolabConst.SERVER = HoyolabConst.SERVER.UNKNOWN,
    var showCharList: Boolean = false,

    var characterList: ArrayList<Character> = arrayListOf(),

    var userNote: UserNote = UserNote(),
){
    companion object{
        var INSTANCE = Json.decodeFromString<UserAccount>(Settings().getString("userAccount", Json.encodeToString(UserAccount())))

        fun pasteCookies(
            cookieList: List<Cookie>,
            serverSelected: HoyolabConst.SERVER,
            snackbarHostState: SnackbarHostState? = null
        ) {
            INSTANCE.server = serverSelected

            INSTANCE.cookies = ""
            for(cookie in cookieList.filter { HoyolabConst().HOYOLAB_V2_KEY_GROUP.contains(it.name) }){
                INSTANCE.cookies += "${cookie.name}=${cookie.value};"

                if((cookie.name == "account_id_v2" && serverSelected.platform == HoyolabRequest.PLATFORM.HOYOLAB) ||
                    (cookie.name == "ltuid_v2" && serverSelected.platform == HoyolabRequest.PLATFORM.MIYOUSHE)
                    ){ INSTANCE.hoyolabId = cookie.value }
            }
            refreshUserAccount()
        }

        fun resetUserAccount(){
            INSTANCE = UserAccount()
            Settings().putString("userAccount", Json.encodeToString(INSTANCE))
        }

        fun refreshUserAccount() {
            try {
                INSTANCE.isLogin = true
                val api = HoyolabAPI(INSTANCE.server.platform, INSTANCE.cookies)

                if(INSTANCE.cookies == "" || INSTANCE.hoyolabId == ""){ return }
                //Get User UID & Account Info
                val userCards = api.getGameRecordCard(INSTANCE.hoyolabId).data
                if (userCards.jsonObject.isEmpty()) {
                    //Load data from Database
                    //...

                    return
                } else {
                    val userInfo = userCards.jsonObject["list"]!!.jsonArray.filter { it.jsonObject["game_id"]!!.jsonPrimitive.int == HoyolabConst.GAME.HONKAI_STAR_RAIL.gameId }[0]
                    INSTANCE.uid = userInfo.jsonObject["game_role_id"]!!.jsonPrimitive.content
                    INSTANCE.username = userInfo.jsonObject["nickname"]!!.jsonPrimitive.content
                    INSTANCE.level = userInfo.jsonObject["level"]!!.jsonPrimitive.int
                    INSTANCE.activeDays = userInfo.jsonObject["data"]!!.jsonArray[0].jsonObject["value"]!!.jsonPrimitive.int
                    INSTANCE.unlockedCharCount = userInfo.jsonObject["data"]!!.jsonArray[1].jsonObject["value"]!!.jsonPrimitive.int
                    INSTANCE.achievements = userInfo.jsonObject["data"]!!.jsonArray[2].jsonObject["value"]!!.jsonPrimitive.int
                    INSTANCE.chestOpened = userInfo.jsonObject["data"]!!.jsonArray[3].jsonObject["value"]!!.jsonPrimitive.int
                }

                val userIndexData = api.getHsrIndexData(INSTANCE.uid, INSTANCE.server).data
                if(!userIndexData.jsonObject.isEmpty()){
                    INSTANCE.icon = userIndexData.jsonObject["cur_head_icon_url"]!!.jsonPrimitive.content
                }

                val userFullData = api.getHsrFullData(INSTANCE.uid, INSTANCE.server).data

                val userNoteData = api.getHsrNote(INSTANCE.uid, INSTANCE.server).data
                if(!userNoteData.jsonObject.isEmpty()){
                    val userNoteJson = userNoteData.jsonObject
                    INSTANCE.userNote.currStamina = userNoteJson["current_stamina"]!!.jsonPrimitive.int
                    INSTANCE.userNote.staminaRecoverTime = userNoteJson["stamina_recover_time"]!!.jsonPrimitive.int
                    INSTANCE.userNote.currReserveStamina = userNoteJson["current_reserve_stamina"]!!.jsonPrimitive.int
                    INSTANCE.userNote.currTrainScore = userNoteJson["current_train_score"]!!.jsonPrimitive.int
                    INSTANCE.userNote.maxTrainScore = userNoteJson["max_train_score"]!!.jsonPrimitive.int
                    INSTANCE.userNote.currUniversialScore = userNoteJson["current_rogue_score"]!!.jsonPrimitive.int
                    INSTANCE.userNote.targetUniversialScore = userNoteJson["max_rogue_score"]!!.jsonPrimitive.int
                    INSTANCE.userNote.weeklyBossChances = userNoteJson["weekly_cocoon_cnt"]!!.jsonPrimitive.int

                    val expeditionJson = userNoteJson["expeditions"]!!.jsonArray
                    for (expedition in expeditionJson){
                        val expeditionObj = expedition.jsonObject
                        val expeditionCharacterIcon = arrayListOf<String>()
                        for (icon in expeditionObj["avatars"]!!.jsonArray){
                            expeditionCharacterIcon.add(icon.jsonPrimitive.content)
                        }
                        INSTANCE.userNote.expedition.add(UserExpedition(
                            status = expeditionObj["status"]!!.jsonPrimitive.content,
                            remainingTime = expeditionObj["remaining_time"]!!.jsonPrimitive.int,
                            materialName = expeditionObj["name"]!!.jsonPrimitive.content,
                            materialUrl = expeditionObj["item_url"]!!.jsonPrimitive.content,
                            expeditionCharacterIcon = expeditionCharacterIcon
                        ))
                    }
                }

                println(userFullData)

                Settings().putString("userAccount", Json.encodeToString(INSTANCE))

            }catch (e : Exception){
                resetUserAccount()
                errorLogExport("UserAccount", "refreshUserAccount()", e)
            }
        }
    }
}


@Serializable
data class UserNote(
    var currStamina: Int = 0,
    var staminaRecoverTime : Int = 0,
    var currReserveStamina : Int = 0,
    var currTrainScore: Int = 0,
    var maxTrainScore: Int = 1,
    var currUniversialScore: Int = 0,
    var targetUniversialScore: Int = 14000,
    var weeklyBossChances: Int = 0,
    var expedition: ArrayList<UserExpedition> = arrayListOf()
){

}

@Serializable
data class UserExpedition(
    var status: String = "Unknown",
    var remainingTime: Int = 0,
    var materialName: String = "Unknown",
    var materialUrl: String = "Unknown",
    var expeditionCharacterIcon : ArrayList<String> = arrayListOf()
)

