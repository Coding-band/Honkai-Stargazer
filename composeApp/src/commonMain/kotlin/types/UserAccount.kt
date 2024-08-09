package types

import androidx.compose.material.SnackbarHostState
import com.multiplatform.webview.cookie.Cookie
import com.russhwolf.settings.Settings
import com.voc.honkaistargazer.BuildKonfig
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import utils.Preferences
import utils.errorLogExport
import utils.hoyolab.AttributeExchange
import utils.hoyolab.HoyolabAPI
import utils.hoyolab.HoyolabConst
import utils.hoyolab.HoyolabRequest
import utils.starbase.StarbaseAPI

@Serializable
class UserAccount(
    var uid: String = "000000000",
    var username: String = "Unknown",
    var level: Int = 0,
    var ascLevel: Int = 0,
    var signature: String = "",
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

    var adPlan : AdPlan = AdPlan.NORMAL,
    var role: Role = Role.USER,
    var lastLoginTime: Long = 0,
){
    companion object{
        var INSTANCE = Json.decodeFromString<UserAccount>(Settings().getString("userAccount", Json.encodeToString(UserAccount())))
        var UIDSEARCH : UserAccount = UserAccount()

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

                if(userIndexData is JsonObject && !userIndexData.jsonObject.isEmpty()){
                    INSTANCE.icon = userIndexData.jsonObject["cur_head_icon_url"]!!.jsonPrimitive.content
                }

                refreshCharacterListHoyolab()

                refreshNoteData()

                Settings().putString("userAccount", Json.encodeToString(INSTANCE))

            }catch (e : Exception){
                resetUserAccount()
                errorLogExport("UserAccount", "refreshUserAccount()", e)
            }
        }

        fun printUserAPIResults(){
            if(BuildKonfig.appProfile == "RELEASE" || BuildKonfig.appProfile == "PRODUCTION"){ return }
            val api = HoyolabAPI(INSTANCE.server.platform, INSTANCE.cookies)
            val userCards = api.getGameRecordCard(INSTANCE.hoyolabId).data
            val userIndexData = api.getHsrIndexData(INSTANCE.uid, INSTANCE.server).data
            val userFullData = api.getHsrFullData(INSTANCE.uid, INSTANCE.server).data
            val userNoteData = api.getHsrNote(INSTANCE.uid, INSTANCE.server).data
            val userMemoryOfChaos = api.getHsrMemoryOfChaos(INSTANCE.uid, INSTANCE.server).data

            println(userCards)
            println(userIndexData)
            println(userFullData)
            println(userNoteData)
            println(userMemoryOfChaos)

        }

        fun refreshCharacterListHoyolab() {
            try {
                if(INSTANCE.uid == "000000000"){ return}
                if(!Preferences().isUpdateCharListNow()){
                    val characterList = StarbaseAPI().getCharData(INSTANCE.uid)
                    println("characterListY : $characterList")
                    if(characterList.size > 0 || INSTANCE.unlockedCharCount == 0){
                        INSTANCE.characterList.clear()
                        INSTANCE.characterList = characterList
                        Preferences().updatedCharList()
                    }
                    return
                }

                val api = HoyolabAPI(INSTANCE.server.platform, INSTANCE.cookies)
                val userFull = api.getHsrFullData(INSTANCE.uid, INSTANCE.server)

                println("userFull : $userFull")
                val userFullData = userFull.data
                var characterList = arrayListOf<Character>()

                if(userFullData is JsonNull || userFullData.jsonObject.isEmpty()){
                    characterList = StarbaseAPI().getCharData(INSTANCE.uid)
                    println("characterListX : $characterList")
                    if(characterList.size > 0 || INSTANCE.unlockedCharCount == 0){
                        INSTANCE.characterList.clear()
                        INSTANCE.characterList = characterList
                        Preferences().updatedCharList()
                    }
                    return
                }

                if(userFullData !is JsonNull && userFullData.jsonObject["avatar_list"] != null) {
                    for (data in userFullData.jsonObject["avatar_list"]!!.jsonArray) {
                        val characterData = data.jsonObject
                        var character = Character.getCharacterItemFromJSON(characterData["id"]!!.jsonPrimitive.int.toString())

                        val lcData = if(characterData["equip"] != null && characterData["equip"] is JsonObject) characterData["equip"]!!.jsonObject else null
                        val relicData = if(characterData["relics"] != null && characterData["relics"] is JsonArray) characterData["relics"]!!.jsonArray else null
                        val ornamentData = if(characterData["ornaments"] != null && characterData["ornaments"] is JsonArray) characterData["ornaments"]!!.jsonArray else null
                        val skillData = if(characterData["skills"] != null && characterData["skills"] is JsonArray) characterData["skills"]!!.jsonArray else null

                        character.characterStatus = CharacterStatus(
                            // Check if "equip" is not null and is a JsonObject
                            equippingLightcone = if (lcData != null) {
                                val lc = Lightcone.getLightconeItemFromJSON(lcData["id"]!!.jsonPrimitive.content)
                                lc.level = lcData["level"]!!.jsonPrimitive.int
                                lc.superimposition = lcData["rank"]!!.jsonPrimitive.int
                                lc
                            } else null,
                            characterLevel = characterData["level"]!!.jsonPrimitive.int,
                            equippingRelicHead = readRelicHoyolabData(relicData, 1),
                            equippingRelicHands = readRelicHoyolabData(relicData, 2),
                            equippingRelicBody = readRelicHoyolabData(relicData, 3),
                            equippingRelicFeet = readRelicHoyolabData(relicData, 4),
                            equippingRelicPlanar = readRelicHoyolabData(ornamentData, 5),
                            equippingRelicLinkRope = readRelicHoyolabData(ornamentData, 6),

                            characterProperties = readCharHoyolabProperties(characterData["properties"]!!.jsonArray),

                            traceBasicAtkLevel = if(skillData != null && skillData.size > 0) skillData[0].jsonObject["level"]!!.jsonPrimitive.int else -1,
                            traceSkillLevel = if(skillData != null && skillData.size > 1) skillData[1].jsonObject["level"]!!.jsonPrimitive.int else -1,
                            traceUltimateLevel = if(skillData != null && skillData.size > 2) skillData[2].jsonObject["level"]!!.jsonPrimitive.int else -1,
                            traceTalentLevel = if(skillData != null && skillData.size > 3) skillData[3].jsonObject["level"]!!.jsonPrimitive.int else -1,

                            eidolon = characterData["rank"]!!.jsonPrimitive.int,
                            //ascension = characterData["ascend"]!!.jsonPrimitive.int,
                        )

                        characterList.add(character)
                    }

                    if(characterList.size > 0 || INSTANCE.unlockedCharCount == 0){
                        INSTANCE.characterList.clear()
                        INSTANCE.characterList = characterList
                        Preferences().updatedCharList()
                        println("characterList : $characterList")
                    }
                }
            } catch (e: Exception) {
                errorLogExport("UserAccount", "refreshCharacterListHoyolab()", e)
            }
        }

        fun readRelicHoyolabData(relicData : JsonArray?, pos: Int) : Relic?{
            if(relicData == null) return null

            val relicAny = relicData.filter { it.jsonObject["pos"] != null && it.jsonObject["pos"]!!.jsonPrimitive.int == pos }
            if(relicAny.isEmpty()){
                return null
            }else{
                val relic = Relic.getRelicItemFromJSON(Relic.getRelicIdFromHoyoRelicId(relicAny[0].jsonObject["id"]!!.jsonPrimitive.int).toString())
                val mainProperties = relicAny[0].jsonObject["main_property"]!!.jsonObject
                val subProperties = relicAny[0].jsonObject["properties"]!!.jsonArray
                relic.level = relicAny[0].jsonObject["level"]!!.jsonPrimitive.int
                relic.rarity = relicAny[0].jsonObject["rarity"]!!.jsonPrimitive.int
                relic.properties.clear()
                relic.properties.add(
                    HsrProperties(
                        attributeExchange = AttributeExchange.getAttrKeyByPropertyType(mainProperties["property_type"]!!.jsonPrimitive.int),
                        valueFinal = HsrProperties.turnStrToValue(mainProperties["value"]!!.jsonPrimitive.content),
                        times = mainProperties["times"]!!.jsonPrimitive.int
                    )
                )

                for (prop in subProperties){
                    val subProp = prop.jsonObject
                    relic.properties.add(
                        HsrProperties(
                            attributeExchange = AttributeExchange.getAttrKeyByPropertyType(subProp["property_type"]!!.jsonPrimitive.int),
                            valueFinal = HsrProperties.turnStrToValue(subProp["value"]!!.jsonPrimitive.content),
                            times = subProp["times"]!!.jsonPrimitive.int
                        )
                    )
                }
                return relic
            }
        }

        fun readCharHoyolabProperties(propList: JsonArray?) : ArrayList<HsrProperties>? {
            if(propList.isNullOrEmpty()) {
                return null
            }else{
                val properties = arrayListOf<HsrProperties>()
                for(prop in propList){
                    properties.add(
                        HsrProperties(
                            attributeExchange = AttributeExchange.getAttrKeyByPropertyType(prop.jsonObject["property_type"]!!.jsonPrimitive.int),
                            valueBase = HsrProperties.turnStrToValue(prop.jsonObject["base"]!!.jsonPrimitive.content),
                            valueAdd = HsrProperties.turnStrToValue(prop.jsonObject["add"]!!.jsonPrimitive.content),
                            valueFinal = HsrProperties.turnStrToValue(prop.jsonObject["final"]!!.jsonPrimitive.content),
                        )
                    )
                }
                return properties
            }
        }

        fun refreshNoteData(){
            try{
                val api = HoyolabAPI(INSTANCE.server.platform, INSTANCE.cookies)
                val userNoteData = api.getHsrNote(INSTANCE.uid, INSTANCE.server).data

                if(userNoteData !is JsonNull && !userNoteData.jsonObject.isEmpty()){
                    val userNoteJson = userNoteData.jsonObject
                    INSTANCE.userNote = UserNote()
                    INSTANCE.userNote.currStamina = userNoteJson["current_stamina"]!!.jsonPrimitive.int
                    INSTANCE.userNote.staminaRecoverTime = userNoteJson["stamina_recover_time"]!!.jsonPrimitive.int
                    INSTANCE.userNote.currReserveStamina = userNoteJson["current_reserve_stamina"]!!.jsonPrimitive.int
                    INSTANCE.userNote.currTrainScore = userNoteJson["current_train_score"]!!.jsonPrimitive.int
                    INSTANCE.userNote.maxTrainScore = userNoteJson["max_train_score"]!!.jsonPrimitive.int
                    INSTANCE.userNote.currUniversialScore = userNoteJson["current_rogue_score"]!!.jsonPrimitive.int
                    INSTANCE.userNote.targetUniversialScore = userNoteJson["max_rogue_score"]!!.jsonPrimitive.int
                    INSTANCE.userNote.weeklyBossChances = userNoteJson["weekly_cocoon_cnt"]!!.jsonPrimitive.int

                    val expeditionJson = userNoteJson["expeditions"]!!.jsonArray
                    println("expeditionJson : ${Json.encodeToString(expeditionJson)}, size = ${expeditionJson.size}")
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
            }catch (e : Exception){
                errorLogExport("UserAccount", "refreshCharacterList()", e)
            }
        }

        //Reaction between UserAccount and Database Server

        fun getUserInfoFromServer(uid: String){

        }
        fun saveMyUserInfoToServer(uid: String){

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

@Serializable
enum class AdPlan(){
    NORMAL, SPONSOR, INVITER, EVENT, BETATESTER,DEV
}
@Serializable
enum class Role(){
    USER, BETATESTER,DEV
}

