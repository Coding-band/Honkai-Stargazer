package types

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.int
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import utils.app.Preferences
import utils.app.errorLog
import utils.hoyolab.HoyolabAPI
import utils.hoyolab.HoyolabConst

@Serializable
data class UserAbyssRecord(
    var userCurrMOCList: ArrayList<UserAbyssRecordData> = Json.decodeFromString<ArrayList<UserAbyssRecordData>>(
        Preferences().Leaderboard.getLocalMOCDataString()),
    var userCurrPFList: ArrayList<UserAbyssRecordData> = Json.decodeFromString<ArrayList<UserAbyssRecordData>>(
        Preferences().Leaderboard.getLocalPFDataString()),
    var userCurrASList: ArrayList<UserAbyssRecordData> = Json.decodeFromString<ArrayList<UserAbyssRecordData>>(
        Preferences().Leaderboard.getLocalASDataString()),
    var userCurrAAList: ArrayList<UserAbyssRecordData> = Json.decodeFromString<ArrayList<UserAbyssRecordData>>(
        Preferences().Leaderboard.getLocalAADataString()),
){
    companion object{
        var INSTANCE = UserAbyssRecord()

        fun refreshMOCData(){
            try{
                if(UserAccount.INSTANCE.uid == "000000000"){ return }
                if(!Preferences().Leaderboard.isUpdateLeaderboardNow() && !Preferences.LeaderboardClass().getIsForceUpdateMOC()){ return }

                val api = HoyolabAPI(UserAccount.INSTANCE.server.platform, UserAccount.INSTANCE.cookies)

                val userMocCurr = api.getHsrMemoryOfChaos(UserAccount.INSTANCE.uid, UserAccount.INSTANCE.server, 1).data
                val userMocLast = api.getHsrMemoryOfChaos(UserAccount.INSTANCE.uid, UserAccount.INSTANCE.server, 2).data

                val mocList = arrayListOf<UserAbyssRecordData>()
                repeat(2){
                    val userMoc = if (it == 0) userMocCurr else userMocLast

                    if(userMoc !is JsonNull && !userMoc.jsonObject.isEmpty()){
                        if(userMoc.jsonObject["groups"]!!.jsonArray.size <= it) return@repeat
                        val mocId = userMoc.jsonObject["schedule_id"]!!.jsonPrimitive.int
                        val mocDetails = userMoc.jsonObject["all_floor_detail"]?.jsonArray

                        if(!mocDetails.isNullOrEmpty()){
                            for (mocDetail in mocDetails){
                                val detail = mocDetail.jsonObject
                                val floor = detail["maze_id"]!!.jsonPrimitive.int % 100
                                val roundUsed = detail["round_num"]!!.jsonPrimitive.int
                                val star = detail["star_num"]!!.jsonPrimitive.int
                                val isFastPass = detail["is_fast"]!!.jsonPrimitive.boolean

                                repeat(2){
                                    val nodeData = if (it == 0){ detail["node_1"]!!.jsonObject } else { detail["node_2"]!!.jsonObject }
                                    val charList = arrayListOf<UserAbyssCharData>()

                                    //Character Data of this node
                                    for (avatar in nodeData.jsonObject["avatars"]!!.jsonArray){
                                        val avatarObj = avatar.jsonObject
                                        charList.add(
                                            UserAbyssCharData(
                                                charId = avatarObj["id"]!!.jsonPrimitive.int,
                                                charLevel = avatarObj["level"]!!.jsonPrimitive.int,
                                                charEidolon = avatarObj["rank"]!!.jsonPrimitive.int
                                            )
                                        )
                                    }
                                    mocList.add(UserAbyssRecordData(
                                        id = mocId,
                                        floor = floor,
                                        partId = it + 1,
                                        roundUsed = roundUsed,
                                        star = star,
                                        recordTime = HoyolabConst.HoyolabTime()
                                            .getDateTimeFromHoyolabTime(
                                                Json.decodeFromJsonElement<HoyolabConst.HoyolabTime>(
                                                    nodeData.jsonObject["challenge_time"]!!
                                                )
                                            ),
                                        isFastPass = isFastPass,
                                        charList = charList,
                                    ))
                                }
                            }
                        }
                    }
                }
                println("[HoYoLab] Updated MOC Data: size = ${mocList.size}, ${Json.encodeToString(mocList)}")
                INSTANCE.userCurrMOCList = mocList

            }catch (e : Exception){
                errorLog("UserAccount", "refreshMOCData()", e)
            }
        }

        fun refreshPFData(){
            try{
                if(UserAccount.INSTANCE.uid == "000000000"){ return }
                if(!Preferences().Leaderboard.isUpdateLeaderboardNow() && !Preferences.LeaderboardClass().getIsForceUpdateMOC()){ return }

                val api = HoyolabAPI(UserAccount.INSTANCE.server.platform, UserAccount.INSTANCE.cookies)
                val userPfCurr = api.getHsrPureFiction(UserAccount.INSTANCE.uid, UserAccount.INSTANCE.server, 1).data
                val userPfLast = api.getHsrPureFiction(UserAccount.INSTANCE.uid, UserAccount.INSTANCE.server, 2).data

                val pfList = arrayListOf<UserAbyssRecordData>()
                repeat(2){
                    val userPf = if (it == 0) userPfCurr else userPfLast
                    if(userPf !is JsonNull && !userPf.jsonObject.isEmpty()){
                        if(userPf.jsonObject["groups"]!!.jsonArray.size <= it) return@repeat
                        val pfId = userPf.jsonObject["groups"]!!.jsonArray[it].jsonObject["schedule_id"]!!.jsonPrimitive.int
                        val pfDetails = userPf.jsonObject["all_floor_detail"]?.jsonArray

                        if(!pfDetails.isNullOrEmpty()){
                            for (pfDetail in pfDetails){
                                val detail = pfDetail.jsonObject
                                val floor = detail["maze_id"]!!.jsonPrimitive.int % 10
                                val roundUsed = detail["round_num"]!!.jsonPrimitive.int
                                val star = detail["star_num"]!!.jsonPrimitive.int
                                val isFastPass = detail["is_fast"]!!.jsonPrimitive.boolean

                                repeat(2){
                                    val nodeData = if (it == 0){ detail["node_1"]!!.jsonObject } else { detail["node_2"]!!.jsonObject }
                                    val charList = arrayListOf<UserAbyssCharData>()
                                    val score = nodeData.jsonObject["score"]?.jsonPrimitive?.content?.toIntOrNull() ?: -1
                                    val buffId = if(nodeData.jsonObject["buff"] != JsonNull) {nodeData.jsonObject["buff"]?.jsonObject?.get("id")?.jsonPrimitive?.intOrNull ?: -1} else -1

                                    //Character Data of this node
                                    for (avatar in nodeData.jsonObject["avatars"]!!.jsonArray){
                                        val avatarObj = avatar.jsonObject
                                        charList.add(
                                            UserAbyssCharData(
                                                charId = avatarObj["id"]!!.jsonPrimitive.int,
                                                charLevel = avatarObj["level"]!!.jsonPrimitive.int,
                                                charEidolon = avatarObj["rank"]!!.jsonPrimitive.int
                                            )
                                        )
                                    }
                                    pfList.add(UserAbyssRecordData(
                                        id = pfId,
                                        floor = floor,
                                        partId = it + 1,
                                        roundUsed = roundUsed,
                                        star = star,
                                        score = score,
                                        recordTime = HoyolabConst.HoyolabTime()
                                            .getDateTimeFromHoyolabTime(
                                                Json.decodeFromJsonElement<HoyolabConst.HoyolabTime>(
                                                    nodeData.jsonObject["challenge_time"]!!
                                                )
                                            ),
                                        buffId = buffId,
                                        isFastPass = isFastPass,
                                        charList = charList,
                                    ))

                                }
                            }
                        }
                    }
                }

                println("[HoYoLab] Updated PF Data: size = ${pfList.size}, ${Json.encodeToString(pfList)}")
                INSTANCE.userCurrPFList = pfList

            }catch (e : Exception){
                errorLog("UserAccount", "refreshPFData()", e)
            }
        }

        fun refreshASData(){
            try{
                if(UserAccount.INSTANCE.uid == "000000000"){ return }
                if(!Preferences().Leaderboard.isUpdateLeaderboardNow() && !Preferences.LeaderboardClass().getIsForceUpdateMOC()){ return }

                val api = HoyolabAPI(UserAccount.INSTANCE.server.platform, UserAccount.INSTANCE.cookies)
                val userASCurr = api.getHsrApocalypticShadow(UserAccount.INSTANCE.uid, UserAccount.INSTANCE.server, 1).data
                val userASLast = api.getHsrApocalypticShadow(UserAccount.INSTANCE.uid, UserAccount.INSTANCE.server, 2).data
                val asList = arrayListOf<UserAbyssRecordData>()
                repeat(2){
                    val userAS = if (it == 0) userASCurr else userASLast
                    if(userAS !is JsonNull && !userAS.jsonObject.isEmpty()){
                        if(userAS.jsonObject["groups"]!!.jsonArray.size <= it) return@repeat
                        val asId = userAS.jsonObject["groups"]!!.jsonArray[it].jsonObject["schedule_id"]!!.jsonPrimitive.int
                        val asDetails = userAS.jsonObject["all_floor_detail"]?.jsonArray

                        if(!asDetails.isNullOrEmpty()){
                            for (pfDetail in asDetails){
                                val detail = pfDetail.jsonObject
                                val floor = detail["maze_id"]!!.jsonPrimitive.int % 10
                                val star = detail["star_num"]!!.jsonPrimitive.int
                                val isFastPass = detail["is_fast"]!!.jsonPrimitive.boolean

                                repeat(2){
                                    val nodeData = if (it == 0){ detail["node_1"]!!.jsonObject } else { detail["node_2"]!!.jsonObject }
                                    val charList = arrayListOf<UserAbyssCharData>()
                                    val score = nodeData.jsonObject["score"]?.jsonPrimitive?.content?.toIntOrNull() ?: -1
                                    val bossDefeated = nodeData.jsonObject["boss_defeated"]!!.jsonPrimitive.boolean
                                    val buffId = if(nodeData.jsonObject["buff"] != JsonNull) {nodeData.jsonObject["buff"]?.jsonObject?.get("id")?.jsonPrimitive?.intOrNull ?: -1} else -1

                                    //Character Data of this node
                                    for (avatar in nodeData.jsonObject["avatars"]!!.jsonArray){
                                        val avatarObj = avatar.jsonObject
                                        charList.add(
                                            UserAbyssCharData(
                                                charId = avatarObj["id"]!!.jsonPrimitive.int,
                                                charLevel = avatarObj["level"]!!.jsonPrimitive.int,
                                                charEidolon = avatarObj["rank"]!!.jsonPrimitive.int
                                            )
                                        )
                                    }
                                    asList.add(UserAbyssRecordData(
                                        id = asId,
                                        floor = floor,
                                        partId = it + 1,
                                        star = star,
                                        score = score,
                                        recordTime = if(nodeData.jsonObject["challenge_time"] is JsonNull) null else HoyolabConst.HoyolabTime()
                                            .getDateTimeFromHoyolabTime(
                                                Json.decodeFromJsonElement<HoyolabConst.HoyolabTime>(
                                                    nodeData.jsonObject["challenge_time"]!!
                                                )
                                            ),
                                        isFastPass = isFastPass,
                                        isBossDefeated = bossDefeated,
                                        buffId = buffId,
                                        charList = charList,
                                    ))

                                }
                            }
                        }
                    }
                }

                println("[HoYoLab] Updated AS Data: size = ${asList.size}, ${Json.encodeToString(asList)}")
                INSTANCE.userCurrASList = asList


            }catch (e : Exception){
                errorLog("UserAccount", "refreshASData()", e)
            }
        }

        fun refreshAAData(){
            try{
                if(UserAccount.INSTANCE.uid == "000000000"){ return }
                if(!Preferences().Leaderboard.isUpdateLeaderboardNow() && !Preferences.LeaderboardClass().getIsForceUpdateMOC()){ return }

                val api = HoyolabAPI(UserAccount.INSTANCE.server.platform, UserAccount.INSTANCE.cookies)
                val userAACurr = api.getHsrAnomalyArbitration(UserAccount.INSTANCE.uid, UserAccount.INSTANCE.server, 1).data
                val userAALast = api.getHsrAnomalyArbitration(UserAccount.INSTANCE.uid, UserAccount.INSTANCE.server, 2).data
                val aaList = arrayListOf<UserAbyssRecordData>()

                repeat(2){
                    val userAA = if (it == 0) userAACurr else userAALast
                    if(userAA !is JsonNull && !userAA.jsonObject.isEmpty()){
                        if(userAA.jsonObject["challenge_peak_records"]!!.jsonArray.size <= it) return@repeat
                        val aaData = userAA.jsonObject["challenge_peak_records"]!!.jsonArray[it]
                        if(aaData is JsonNull || aaData == null) return@repeat
                        val aaId = aaData.jsonObject["group"]!!.jsonObject["group_id"]!!.jsonPrimitive.int

                        repeat(2){aaType ->
                            val aaDetailsElement = aaData.jsonObject[if(aaType == 0) "mob_records" else "boss_record"]
                            if(aaDetailsElement is JsonNull || aaDetailsElement == null) return@repeat
                            val aaDetails = if(aaDetailsElement is JsonObject) buildJsonArray { aaDetailsElement.jsonObject } else aaDetailsElement.jsonArray
                            if(!aaDetails.isNullOrEmpty()){
                                for (aaDetail in aaDetails){
                                    if(aaDetail is JsonNull) continue
                                    val detail = aaDetail.jsonObject
                                    val floor = detail["maze_id"]!!.jsonPrimitive.int % 100
                                    val star = detail["star_num"]!!.jsonPrimitive.int
                                    val isFastPass = detail["is_fast"]!!.jsonPrimitive.boolean
                                    val buffId = if(detail.jsonObject["buff"] != JsonNull) {detail.jsonObject["buff"]?.jsonObject?.get("id")?.jsonPrimitive?.intOrNull ?: -1} else -1
                                    val isBossDefeated = detail["has_challenge_record"]!!.jsonPrimitive.boolean
                                    val charList = arrayListOf<UserAbyssCharData>()

                                    //Character Data of this node
                                    for (avatar in detail.jsonObject["avatars"]!!.jsonArray){
                                        val avatarObj = avatar.jsonObject
                                        charList.add(
                                            UserAbyssCharData(
                                                charId = avatarObj["id"]!!.jsonPrimitive.int,
                                                charLevel = avatarObj["level"]!!.jsonPrimitive.int,
                                                charEidolon = avatarObj["rank"]!!.jsonPrimitive.int
                                            )
                                        )
                                    }

                                    aaList.add(UserAbyssRecordData(
                                        id = aaId,
                                        floor = floor,
                                        partId = 0,
                                        star = star,
                                        recordTime = if(detail.jsonObject["challenge_time"] is JsonNull) null else HoyolabConst.HoyolabTime()
                                            .getDateTimeFromHoyolabTime(
                                                Json.decodeFromJsonElement<HoyolabConst.HoyolabTime>(
                                                    detail.jsonObject["challenge_time"]!!
                                                )
                                            ),
                                        isFastPass = isFastPass,
                                        isBossDefeated = isBossDefeated,
                                        buffId = buffId,
                                        charList = charList,
                                    ))

                                }
                            }
                        }
                    }
                }

                println("[HoYoLab] Updated AA Data: size = ${aaList.size}, ${Json.encodeToString(aaList)}")
                INSTANCE.userCurrAAList = aaList


            }catch (e : Exception){
                errorLog("UserAccount", "refreshAAData()", e)
            }
        }
    }
}

@Serializable
data class UserAbyssCharData(
    val charId: Int,
    val charLevel: Int,
    val charEidolon: Int
)


@Serializable
data class UserAbyssRecordData(
    val id: Int,
    val floor: Int,
    val partId: Int,
    val recordTime: String? = null,
    val roundUsed: Int = -1,
    val star: Int = -1,
    val score: Int = -1,
    val isFastPass: Boolean = false,
    val isBossDefeated: Boolean? = false,
    val buffId: Int = -1,
    val charList: ArrayList<UserAbyssCharData> = arrayListOf(),
)
