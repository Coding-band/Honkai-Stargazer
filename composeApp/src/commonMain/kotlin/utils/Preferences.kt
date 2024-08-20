package utils

import com.russhwolf.settings.Settings
import utils.starbase.StarbaseAPI

class Preferences {
    val CharList = CharListClass()
    val CharWeightList = CharWeightListClass()
    val Leaderboard = LeaderboardClass()
    val AppSettings = AppSettingsClass()

    private class Constants{
        val CHAR_LIST_UPDATE_MINS = 15
        val CHAR_WEIGHT_LIST_UPDATE_MINS = 15
        val LEADERBOARD_UPDATE_MINS = 60
        val KEY_HYB_CHAR_LIST_LAST_UPDATE_TIME = "lastUpdateHoyolabCharListTime"
        val KEY_HYB_LEADERBOARD_LAST_UPDATE_TIME = "lastUpdateHoyolabLeaderboardTime"

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
            Settings().putString("localCharList", "[]")
        }

        fun getLocalCharListString(): String {
            return Settings().getString("localCharList", "[]")
        }

        fun setLocalCharListString(charList: String){
            Settings().putString("localCharList", charList)
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

        fun getMOCHoyolabJsonString(): String {
            return Settings().getString("MOCHoyolabJson", "[]")
        }
        fun setMOCHoyolabJsonString(charList: String){
            Settings().putString("MOCHoyolabJson", charList)
        }

        fun getPFHoyolabJsonString(): String {
            return Settings().getString("PFHoyolabJson", "[]")
        }
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
    }
}