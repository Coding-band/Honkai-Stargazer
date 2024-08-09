package utils

import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import kotlinx.datetime.Clock
import utils.starbase.StarbaseAPI

class Preferences {
    private val CHAR_LIST_UPDATE_MINS = 15
    private val CHAR_WEIGHT_LIST_UPDATE_MINS = 15

    val KEY_CHAR_LIST_LAST_UPDATE_TIME = "lastUpdateCharListTime"

    fun isUpdateCharListNow(): Boolean {
        val currentSysTime = StarbaseAPI().getSystemTime()
        val lastUpdateTime = Settings().get(KEY_CHAR_LIST_LAST_UPDATE_TIME, Clock.System.now().toEpochMilliseconds())

        return (currentSysTime - lastUpdateTime > CHAR_LIST_UPDATE_MINS * 60 * 1000)
    }

    fun updatedCharList(){
        Settings().putLong(KEY_CHAR_LIST_LAST_UPDATE_TIME, StarbaseAPI().getSystemTime())
    }

    fun isUpdateCharWeightListNow(): Boolean {
        val currentSysTime = StarbaseAPI().getSystemTime()
        val lastUpdateTime = Settings().get(KEY_CHAR_LIST_LAST_UPDATE_TIME, Clock.System.now().toEpochMilliseconds())

        return (currentSysTime - lastUpdateTime > CHAR_WEIGHT_LIST_UPDATE_MINS * 60 * 1000)
    }
    fun updatedCharWeightList(){
        Settings().putLong(KEY_CHAR_LIST_LAST_UPDATE_TIME, StarbaseAPI().getSystemTime())
    }
}