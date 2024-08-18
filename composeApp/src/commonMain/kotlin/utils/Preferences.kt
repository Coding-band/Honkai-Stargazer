package utils

import com.russhwolf.settings.Settings
import utils.starbase.StarbaseAPI

class Preferences {
    val CharList = CharListClass()
    val CharWeightList = CharWeightListClass()
    val Leaderboard = LeaderboardClass()

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
}