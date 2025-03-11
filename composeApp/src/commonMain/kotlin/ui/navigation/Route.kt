package ui.navigation

import kotlinx.serialization.Serializable
import types.CombatType
import types.Path



@Serializable
data class CharacterInfoRoute(
    val charName: String,
    val fileName: String,
    val combatType: String,
    val path: String,
    val charId: Int
)

@Serializable
data class LightconeInfoRoute(val lcName: String, val fileName: String, val path: String)

@Serializable
data class RelicInfoRoute(val relicName: String, val fileName: String)

@Serializable
data class HoyolabLoginRoute(val serverId: String)

@Serializable
data class EventContentRoute(val eventId: Int)

@Serializable
data class UserInfoRoute(val uid: String)

@Serializable
data class UserCharacterRoute(val uid: String, val charId: String)

@Serializable
data class BattleChronicleRoute(val uid: String)

@Serializable
data class ActionOrderSimulatorRoute(val index: Int)

@Serializable object SplashRoute
@Serializable object HomeRoute
@Serializable object BlankRoute
@Serializable object CharacterListRoute
@Serializable object LightconeListRoute
@Serializable object RelicListRoute
@Serializable object SettingRoute
@Serializable object BackgroundSettingRoute
@Serializable object EventListRoute
@Serializable object MapRoute
@Serializable object UIDSearchRoute
@Serializable object MemoryOfChaosMissionRoute
@Serializable object PureFictionMissionRoute
@Serializable object AboutStargazerRoute
@Serializable object ExpeditionRoute
@Serializable object ProficientLeaderboardRoute
@Serializable object ActionOrderListRoute
@Serializable object IIRCHomePageRoute