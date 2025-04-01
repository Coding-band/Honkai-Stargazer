package types

import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import files.Res
import files.material_celestial_ambrosia
import files.material_clock_credits
import files.material_credit
import files.material_hertareum
import files.material_shield
import files.material_strale
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.DrawableResource
import utils.app.Language

@Serializable
data class IIRCCharacter(
    val id: Int,
    val name: Map<Language.TextLanguage, String>,
    val isUnlocked: Boolean = false,
    val isVisible: Boolean = false,
    val count: Int = 0,
    val status: IIRCItemStatus = IIRCItemStatus(),
    val upgradeRequirements: List<IIRCItemUpgradeRequirement> = emptyList(),
    val visibleRequirements: List<IIRCItemVisibleRequirement> = emptyList(),
)

@Serializable
data class IIRCItemUpgradeRequirement(
    val currencyType: IIRCCurrecyType = IIRCCurrecyType.CREDIT,
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
)

@Serializable
data class IIRCWorld(
    val id: Int,
    val name: Map<Language.TextLanguage, String>,
    val characters: List<IIRCCharacter> = emptyList(),
    val isVisible: Boolean = false,
)

@Serializable
data class IIRCPlayerInfo(
    val restartTimes: Int = 0,
    val currencyValues: Map<IIRCCurrecyType, Long> = mapOf(Pair(IIRCCurrecyType.CREDIT, 100L)),
    val finalRate: Float = 1f,
    val characters: ArrayList<IIRCCharacter> = arrayListOf(),
    val worlds: ArrayList<IIRCWorld> = arrayListOf(),
    val isPlayMusic: Boolean = true,
)

@Serializable
enum class IIRCCurrecyType(val icon: DrawableResource) {
    CREDIT(Res.drawable.material_credit),
    HERTAREUM(Res.drawable.material_hertareum),
    SHIELD(Res.drawable.material_shield),
    STRALE(Res.drawable.material_strale),
    CLOCK_CREDIT(Res.drawable.material_clock_credits),
    CELESTIAL_AMBROSIA(Res.drawable.material_celestial_ambrosia),
}

open class IIRC {
    companion object {
        /*
        val iircWorldInfo = getIIRCWorldInfoFromJSON()
        val iircCharInfo = getIIRCCharInfoFromJSON()

        private fun getIIRCWorldInfoFromJSON() : ArrayList<IIRCWorld> {
            val iircWorldJson = getAssetsJsonByFilePath("easter_egg/iirc/iirc_world.json", defaultData = "[]")

            if(iircWorldJson !is JsonArray || iircWorldJson.jsonArray.isEmpty()) return arrayListOf()

            return runBlocking {
                val job = async(Dispatchers.Default){
                    val listExtDataJson = charExtListJson.jsonArray.firstOrNull { charData -> charData.jsonObject["officialId"]!!.jsonPrimitive.content == charId } ?: return@async Character(path = Path.Unspecified, )
                    // World-level
                    for(world in iircWorldJson.jsonArray){

                    }
                    return@async arrayListOf<IIRCWorld>()
                }
            }

        }


         */
        val WorldListSaver: Saver<ArrayList<IIRCWorld>, Any> = listSaver(
            save = { listOf(Json.encodeToString(it)) },
            restore = { Json.decodeFromString(it[0]) }
        )
    }
}