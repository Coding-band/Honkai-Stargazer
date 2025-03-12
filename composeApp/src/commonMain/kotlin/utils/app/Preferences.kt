package utils.app

import com.russhwolf.settings.Settings
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ui.components.HomePageBlocks
import ui.screens.actionOrderTeamList
import utils.app.Constants.Companion.HOME_PAGE_MENU_DEFAULT
import utils.calculator.TeamListItem
import utils.starbase.StarbaseAPI

class Preferences {
    val CharList = CharListClass()
    val CharWeightList = CharWeightListClass()
    val Leaderboard = LeaderboardClass()
    val AppSettings = AppSettingsClass()
    val HomePageMenu = HomePageMenuClass()
    val ActionOrder = ActionOrderClass()
    //val WrapSimulator = WrapSimulatorClass()
    //val WrapAnalysis = WrapAnalysisClass()

    private class Constants{
        val CHAR_LIST_UPDATE_MINS = 15
        val CHAR_WEIGHT_LIST_UPDATE_MINS = 15
        val LEADERBOARD_UPDATE_MINS = 60
        val KEY_HYB_CHAR_LIST_LAST_UPDATE_TIME = "lastUpdateHoyolabCharListTime"
        val KEY_HYB_LEADERBOARD_LAST_UPDATE_TIME = "lastUpdateHoyolabLeaderboardTime"
    }

    class FavouriteClass {
        companion object{
            val charFavourList = getFavouriteList(TYPE.CHAR)
            val lcFavourList = getFavouriteList(TYPE.LC)
            val relicFavourList = getFavouriteList(TYPE.RELIC)

            fun setFavouriteList(favourList: ArrayList<String> = charFavourList, type: TYPE = TYPE.CHAR){
                Settings().putString(when(type){
                    TYPE.CHAR -> "charFavouriteList"
                    TYPE.LC -> "lcFavouriteList"
                    TYPE.RELIC -> "relicFavouriteList"
                }, Json.encodeToString(favourList))
            }

            fun getFavouriteList(type: TYPE = TYPE.CHAR) : ArrayList<String> {
                return Json.decodeFromString(
                    Settings().getString(
                        when(type){
                            TYPE.CHAR -> "charFavouriteList"
                            TYPE.LC -> "lcFavouriteList"
                            TYPE.RELIC -> "relicFavouriteList"
                        },
                        "[]"
                    )
                )
            }

            fun checkIsFavourite(itemId: String, type: TYPE = TYPE.CHAR): Boolean {
                return when(type){
                    TYPE.CHAR -> charFavourList
                    TYPE.LC -> lcFavourList
                    TYPE.RELIC -> relicFavourList
                }.contains(itemId)
            }

            fun addToFavouriteList(itemId: String, type: TYPE = TYPE.CHAR){
                val tempList = when(type){
                    TYPE.CHAR -> charFavourList
                    TYPE.LC -> lcFavourList
                    TYPE.RELIC -> relicFavourList
                }

                tempList.add(itemId)
                setFavouriteList(tempList, type)
            }

            fun removeFromFavouriteList(itemId: String, type: TYPE = TYPE.CHAR){
                val tempList = when(type){
                    TYPE.CHAR -> charFavourList
                    TYPE.LC -> lcFavourList
                    TYPE.RELIC -> relicFavourList
                }

                tempList.remove(itemId)
                setFavouriteList(tempList, type)
            }
        }


        enum class TYPE {CHAR, LC, RELIC}
    }

    class CharListClass{
        fun isUpdateHoYoLabCharListNow(): Boolean {
            val currentSysTime = StarbaseAPI().getSystemTime()
            val lastUpdateTime = Settings().getLong(Constants().KEY_HYB_CHAR_LIST_LAST_UPDATE_TIME, 0L)

            return (currentSysTime - lastUpdateTime > Constants().CHAR_LIST_UPDATE_MINS * 60 * 1000)
        }

        fun updatedHoYoLabCharList(){
            Settings().putLong(Constants().KEY_HYB_CHAR_LIST_LAST_UPDATE_TIME, StarbaseAPI().getSystemTime())
        }
        fun resetCharList(){
            Settings().putLong(Constants().KEY_HYB_CHAR_LIST_LAST_UPDATE_TIME, 0L)
            writeToFile("localCharList.json", "[]")
            //Settings().putString("localCharList", "[]")
        }

        fun getLocalCharListString(): String {
            //return Settings().getString("localCharList", "[]")
            return readFromFile("localCharList.json", true, "[]")
        }

        fun setLocalCharListString(charList: String){
            //Settings().putString("localCharList", charList)
            writeToFile("localCharList.json", charList)
        }
    }

    //Leaderboards

    class LeaderboardClass(){
        fun isUpdateLeaderboardNow(): Boolean {
            val currentSysTime = StarbaseAPI().getSystemTime()
            val lastUpdateTime = Settings().getLong(Constants().KEY_HYB_LEADERBOARD_LAST_UPDATE_TIME, 0L)

            return (currentSysTime - lastUpdateTime > Constants().LEADERBOARD_UPDATE_MINS * 60 * 1000)
        }
        fun updatedLeaderboard(){
            Settings().putLong(Constants().KEY_HYB_LEADERBOARD_LAST_UPDATE_TIME, StarbaseAPI().getSystemTime())
        }
        fun resetLeaderboard(){
            Settings().putLong(Constants().KEY_HYB_LEADERBOARD_LAST_UPDATE_TIME, 0L)
            Settings().putString("localMOCData", "[]")
            Settings().putString("localPFData", "[]")
            Settings().putString("localASData", "[]")
        }

        @Deprecated("use getLocalMOCDataString instead")
        fun getMOCHoyolabJsonString(): String {
            return Settings().getString("MOCHoyolabJson", "[]")
        }
        @Deprecated("use setLocalMOCDataString instead")
        fun setMOCHoyolabJsonString(charList: String){
            Settings().putString("MOCHoyolabJson", charList)
        }

        @Deprecated("use getLocalPFDataString instead")
        fun getPFHoyolabJsonString(): String {
            return Settings().getString("PFHoyolabJson", "[]")
        }
        @Deprecated("use setLocalPFDataString instead")
        fun setPFHoyolabJsonString(charList: String){
            Settings().putString("PFHoyolabJson", charList)
        }

        fun getLocalMOCDataString(): String {
            return Settings().getString("localMOCData", "[]")
        }
        fun setLocalMOCDataString(charList: String){
            Settings().putString("localMOCData", charList)
        }

        fun getLocalPFDataString(): String {
            return Settings().getString("localPFData", "[]")
        }

        fun setLocalPFDataString(charList: String){
            Settings().putString("localPFData", charList)
        }

        fun getLocalASDataString(): String {
            return Settings().getString("localASData", "[]")
        }

        fun setLocalASDataString(charList: String){
            Settings().putString("localASData", charList)
        }
    }

    //CharWeightList
    class CharWeightListClass(){
        fun isUpdateCharWeightListNow(): Boolean {
            val currentSysTime = StarbaseAPI().getSystemTime()
            val lastUpdateTime = Settings().getLong(Constants().KEY_HYB_CHAR_LIST_LAST_UPDATE_TIME, 0L)

            return (currentSysTime - lastUpdateTime > Constants().CHAR_WEIGHT_LIST_UPDATE_MINS * 60 * 1000)
        }
        fun updatedCharWeightList(){
            Settings().putLong(Constants().KEY_HYB_CHAR_LIST_LAST_UPDATE_TIME, StarbaseAPI().getSystemTime())
        }

    }

    class AppSettingsClass(){
        fun isLangInitialized(): Boolean {
            return Settings().getBoolean("isLangInitialized", false)
        }
        fun setLangInitialized(){
            Settings().putBoolean("isLangInitialized", true)
        }

        fun isShowChar(): Boolean {
            return Settings().getBoolean("isShowChar", true)
        }
        fun setIsShowChar(isShowChar: Boolean){
            Settings().putBoolean("isShowChar", isShowChar)
        }
    }

    class HomePageMenuClass(){
        fun getHomePageMenuArray(): ArrayList<HomePageBlocks.HomePageBlockItem>{
            val homePageMenuArrayStr = Settings().getString("homePageMenuArray", Json.encodeToString(
                HOME_PAGE_MENU_DEFAULT.map { it.itemId }))
            val homePageMeun : ArrayList<HomePageBlocks.HomePageBlockItem>  = arrayListOf()

            val homePageMenuArray = Json.decodeFromString<ArrayList<String>>(homePageMenuArrayStr)
            homePageMenuArray.forEach { itemId ->
                val item = HOME_PAGE_MENU_DEFAULT.firstOrNull { it.itemId == itemId }
                if (item != null) homePageMeun.add(item)
            }
            return homePageMeun
        }
        fun setHomePageMenuArray(homePageMenuArray: ArrayList<HomePageBlocks.HomePageBlockItem>){
            val menuStrArray = homePageMenuArray.map { it.itemId }
            Settings().putString("homePageMenuArray", Json.encodeToString(menuStrArray))
        }
    }

    class ActionOrderClass(){
        fun getActionOrderList(): ArrayList<TeamListItem>{
            val actionOrderListStr = readFromFile("actionOrderTeamList.json", true, "[]")
            return Json.decodeFromString<ArrayList<TeamListItem>>(actionOrderListStr)
        }
        fun setActionOrderList(actionOrderList: ArrayList<TeamListItem> = ArrayList(actionOrderTeamList)){
            writeToFile("actionOrderTeamList.json", Json.encodeToString(actionOrderList))
        }
    }
}