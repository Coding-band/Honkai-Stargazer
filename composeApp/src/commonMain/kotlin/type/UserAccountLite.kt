package type

import com.russhwolf.settings.Settings
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import utils.annotation.DoItLater
import utils.hoyolab.HoyolabConst

@Serializable
data class UserAccountLite(
    val uid: String,
    val username: String,
    val level: Int,
    val icon: String,
    val server: HoyolabConst.SERVER
){

    @DoItLater("Rearrange those function later")
    companion object{
        fun getSearchRecordList() : ArrayList<UserAccountLite>{
            return Json.decodeFromString<ArrayList<UserAccountLite>>(Settings().getString("searchRecordList", "[]"))
        }

        fun saveSearchRecordList(list: ArrayList<UserAccountLite>){
            Settings().putString("searchRecordList", Json.encodeToString<ArrayList<UserAccountLite>>(list))
        }
    }
}