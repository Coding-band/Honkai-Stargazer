package utils.calculator

import files.ActionOrderEnemySpeedExtHigh
import files.ActionOrderEnemySpeedExtSlow
import files.ActionOrderEnemySpeedHigh
import files.ActionOrderEnemySpeedMid
import files.ActionOrderEnemySpeedSlow
import files.Res
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.StringResource
import types.Character
import types.UserAccount
import utils.annotation.VersionUpdateCheck
import utils.app.Constants.Companion.CLARA_KAMOJI
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Serializable
data class TeamListItem(
    val uid: String = UserAccount.INSTANCE.uid,
    val teamBuildUnix: Long = Clock.System.now().toEpochMilliseconds(),
    val id: String = "${uid}-${teamBuildUnix}",
    val teamName: String = CLARA_KAMOJI,
    val teamDataList: ArrayList<TeammateItem> = arrayListOf(),
    val teamEnemySpeed: Int = ActionOrderEnemySpeed.NORMAL.speed,
)

@Serializable
data class TeammateItem(
    val character: Character,
    val level: Int = 1,
    val energyRechargeRate : Float = 1f,
    val energyMax : Int = 100,
    val speedBase: Float = 100.0f,
    val speedRate: Float = 0f,
)

@Serializable
data class ActionOrderProcessItem(
    val charId: Int = -1, //Character's official ID
    val charIcon: String = "", //Character's icon
    val charRarity: Int = 4,
    var charCurrActionTimes: Int = 0,
    var currRound : Int = 0,
    var actionValue : Float = 0f,
    var currSkillPoint : Int = 3,
    var teamMaxSkillPoint : Int = 5,
    val ultimatePointMax: Int = 0, //For Feixiao, Acheron, etc.
    var ultimatePoint: Int = 0, //For Feixiao, Acheron, etc.
    val maxStoreEnergyScale : Int = 1, //E.g. Yunli cost 120 energy per Ultimate, and she can store 2 Ultimate at most, there should be 2.
    val energyMax: Int = 100,
    var energy: Int = energyMax / 2,
    var action: CharAction = CharAction.BASIC,
)

@Serializable
enum class CharAction(val shortForm: Char, val zhName: String){
    BASIC('B', "普通攻擊"),
    SKILL('S', "戰技"),
    ULTIMATE('U', "總結技"),
}

enum class ActionOrderEnemySpeed(val speed: Int, val res: StringResource) {
    EXT_SLOW(120, Res.string.ActionOrderEnemySpeedExtSlow),
    SLOW(132, Res.string.ActionOrderEnemySpeedSlow),
    NORMAL(158, Res.string.ActionOrderEnemySpeedMid),
    HIGH(190, Res.string.ActionOrderEnemySpeedHigh),
    EXT_HIGH(206, Res.string.ActionOrderEnemySpeedExtHigh);

    companion object {
        fun fromSpeed(speed: Int): ActionOrderEnemySpeed {
            return when {
                speed <= EXT_SLOW.speed -> EXT_SLOW
                speed <= SLOW.speed -> SLOW
                speed <= NORMAL.speed -> NORMAL
                speed <= HIGH.speed -> HIGH
                else -> EXT_HIGH
            }
        }
    }
}

/**
 * Check whether the max skill point should increase/decrease in special cases
 * @param teammateList: Teammate List of the Team
 */
@VersionUpdateCheck
fun checkMaxSkillPoint(teammateList: ArrayList<TeammateItem>) : Int{
    var maxSkillPoint = 5
    val specialSP = arrayListOf(Pair(1306, 7))
    teammateList.map { mate ->
        if(specialSP.any { it.first == mate.character.officialId }){
            maxSkillPoint = specialSP.filter { it.first == mate.character.officialId }.first().second
        }
    }
    return maxSkillPoint
}

fun actionOrderSimuation(){

}