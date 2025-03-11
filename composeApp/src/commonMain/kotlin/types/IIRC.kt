package types

import kotlinx.serialization.Serializable
import utils.app.Language

@Serializable
data class IIRCItem(
    val id: Int,
    val name: Map<Language.TextLanguage, String>,
    val description: String = "",
    val isUnlocked: Boolean = false,
    val isVisible: Boolean = false,
    val count: Int = 0,
    val status: IIRCItemStatus = IIRCItemStatus(),
    val unlockRequirements: List<IIRCItemUnlockRequirement> = emptyList(),
    val visibleRequirements: List<IIRCItemVisibleRequirement> = emptyList(),
)

@Serializable
data class IIRCItemUnlockRequirement(
    val element: CombatType = CombatType.Physical,
    val value: Int = 10,
)
@Serializable
data class IIRCItemVisibleRequirement(
    val requireItemId: Int,
    val requireItemCount: Int = 1,
)

@Serializable
data class IIRCItemStatus(
    val basicValue: Int = 1,
    val additionValue: Int = 0,
    val bonusPercentage: Float = 0f,
)

@Serializable
data class IIRCWorld(
    val id: Int,
    val name: Map<Language.TextLanguage, String>,
    val description: String = "",
    val items: List<IIRCItem> = emptyList(),
    val isVisible: Boolean = false,
)

@Serializable
data class IIRCPlayerInfo(
    val restartTimes: Int = 0,
    val elementValues: Map<CombatType, Long> = mapOf(Pair(CombatType.Physical, 0L)),
    val finalRate: Float = 1f,
    val items: ArrayList<IIRCItem> = arrayListOf(),
    val worlds: ArrayList<IIRCWorld> = arrayListOf(),
    val isPlayMusic: Boolean = true,
)