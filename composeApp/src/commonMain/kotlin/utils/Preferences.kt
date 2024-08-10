package utils

import com.russhwolf.settings.Settings
import utils.starbase.StarbaseAPI

class Preferences {
    private val CHAR_LIST_UPDATE_MINS = 15
    private val CHAR_WEIGHT_LIST_UPDATE_MINS = 15

    val KEY_HYB_CHAR_LIST_LAST_UPDATE_TIME = "lastUpdateHoyolabCharListTime"

    fun isUpdateHoYoLabCharListNow(): Boolean {
        val currentSysTime = StarbaseAPI().getSystemTime()
        val lastUpdateTime = Settings().getLong(KEY_HYB_CHAR_LIST_LAST_UPDATE_TIME, 0L)

        return (currentSysTime - lastUpdateTime > CHAR_LIST_UPDATE_MINS * 60 * 1000)
    }

    fun updatedHoYoLabCharList(){
        Settings().putLong(KEY_HYB_CHAR_LIST_LAST_UPDATE_TIME, StarbaseAPI().getSystemTime())
    }
    fun resetCharList(){
        Settings().putLong(KEY_HYB_CHAR_LIST_LAST_UPDATE_TIME, 0L)
        Settings().putString("localCharList", "[]")
    }

    fun getLocalCharListString(): String {
        return Settings().getString("localCharList", "[]")
    }

    fun setLocalCharListString(charList: String){
        Settings().putString("localCharList", charList)
    }

    fun isUpdateCharWeightListNow(): Boolean {
        val currentSysTime = StarbaseAPI().getSystemTime()
        val lastUpdateTime = Settings().getLong(KEY_HYB_CHAR_LIST_LAST_UPDATE_TIME, 0L)

        return (currentSysTime - lastUpdateTime > CHAR_WEIGHT_LIST_UPDATE_MINS * 60 * 1000)
    }
    fun updatedCharWeightList(){
        Settings().putLong(KEY_HYB_CHAR_LIST_LAST_UPDATE_TIME, StarbaseAPI().getSystemTime())
    }
}