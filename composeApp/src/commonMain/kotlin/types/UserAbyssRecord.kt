package types

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import utils.Preferences
import utils.errorLogExport
import utils.hoyolab.HoyolabAPI
import utils.hoyolab.HoyolabConst

@Serializable
data class UserAbyssRecord(
    var userCurrMOCList: ArrayList<UserAbyssRecordData> = Json.decodeFromString<ArrayList<UserAbyssRecordData>>(
        Preferences().Leaderboard.getLocalMOCDataString()),
    var userCurrPFList: ArrayList<UserAbyssRecordData> = Json.decodeFromString<ArrayList<UserAbyssRecordData>>(
        Preferences().Leaderboard.getLocalPFDataString()),
    var userCurrASList: ArrayList<UserAbyssRecordData> = arrayListOf(),
){
    companion object{
        var INSTANCE = UserAbyssRecord()

        fun refreshMOCData(){
            try{
                if(UserAccount.INSTANCE.uid == "000000000"){ return }
                if(!Preferences().Leaderboard.isUpdateLeaderboardNow()){ return }

                val api = HoyolabAPI(UserAccount.INSTANCE.server.platform, UserAccount.INSTANCE.cookies)
                val userMocCurr = api.getHsrMemoryOfChaos(UserAccount.INSTANCE.uid, UserAccount.INSTANCE.server, 1).data
                val userMocLast = api.getHsrMemoryOfChaos(UserAccount.INSTANCE.uid, UserAccount.INSTANCE.server, 2).data

                //Check whether is same as local data
                if(Preferences().Leaderboard.getMOCHoyolabJsonString() == Json.encodeToString(userMocCurr)) {
                    return
                }else{
                    Preferences().Leaderboard.setMOCHoyolabJsonString(Json.encodeToString(userMocCurr))
                }

                val mocList = arrayListOf<UserAbyssRecordData>()
                repeat(2){
                    val userMoc = if (it == 0) userMocCurr else userMocLast

                    if(userMoc !is JsonNull && !userMoc.jsonObject.isEmpty()){
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
                errorLogExport("UserAccount", "refreshMOCData()", e)
            }
        }

        fun refreshPFData(){
            try{
                if(UserAccount.INSTANCE.uid == "000000000"){ return }
                if(!Preferences().Leaderboard.isUpdateLeaderboardNow()){ return }

                val api = HoyolabAPI(UserAccount.INSTANCE.server.platform, UserAccount.INSTANCE.cookies)
                val userPfCurr = api.getHsrPureFiction(UserAccount.INSTANCE.uid, UserAccount.INSTANCE.server, 1).data
                val userPfLast = api.getHsrPureFiction(UserAccount.INSTANCE.uid, UserAccount.INSTANCE.server, 2).data

                //Check whether is same as local data
                if(Preferences().Leaderboard.getPFHoyolabJsonString() == Json.encodeToString(userPfCurr)) {
                    return
                }else{
                    Preferences().Leaderboard.setPFHoyolabJsonString(Json.encodeToString(userPfCurr))
                }

                val pfList = arrayListOf<UserAbyssRecordData>()
                repeat(2){
                    val userPf = if (it == 0) userPfCurr else userPfLast
                    if(userPf !is JsonNull && !userPf.jsonObject.isEmpty()){
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
                errorLogExport("UserAccount", "refreshPFData()", e)
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
    val recordTime: String,
    val roundUsed: Int,
    val star: Int = -1,
    val score: Int = -1,
    val isFastPass: Boolean = false,
    val charList: ArrayList<UserAbyssCharData> = arrayListOf(),
)
