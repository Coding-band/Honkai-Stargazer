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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.jetbrains.compose.resources.DrawableResource
import utils.app.Language
import utils.app.readFromOnlineURL
import utils.starbase.StarbaseAPI

@Serializable
data class IIRCCharacter(
    val id: Int,
    val worldId: Int,
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
        // final variables
        val iircCharInfo = getIIRCCharInfoFromJSON()
        val iircWorldInfo = getIIRCWorldInfoFromJSON()

        private fun getIIRCWorldInfoFromJSON() : ArrayList<IIRCWorld> {
            val iircWorldJson = Json.parseToJsonElement(readFromOnlineURL(StarbaseAPI().getGitHubStaticAssetURL()+"/data/iirc/iirc_world.json", defaultData = "[]"))

            if(iircWorldJson !is JsonArray || iircWorldJson.jsonArray.isEmpty()) return arrayListOf()

            return runBlocking(Dispatchers.IO) {
                val iircWorldArrayList = arrayListOf<IIRCWorld>()
                async {
                    iircWorldJson.jsonArray.map {
                        iircWorldArrayList.add(
                            IIRCWorld(
                                id = it.jsonObject["id"]!!.jsonPrimitive.int,
                                name = Json.decodeFromJsonElement(it.jsonObject["name"]!!),
                                characters = iircCharInfo.filter { char -> char.worldId == it.jsonObject["id"]!!.jsonPrimitive.int },
                                isVisible = it.jsonObject["isVisible"]!!.jsonPrimitive.boolean
                            )
                        )
                    }
                    return@async iircWorldArrayList
                }.await()
            }

        }

        private fun getIIRCCharInfoFromJSON() : ArrayList<IIRCCharacter> {
            val iircCharJson = Json.parseToJsonElement(readFromOnlineURL(StarbaseAPI().getGitHubStaticAssetURL()+"/data/iirc/iirc_char.json", defaultData = "[]"))

            if(iircCharJson !is JsonArray || iircCharJson.jsonArray.isEmpty()) return arrayListOf()

            return runBlocking(Dispatchers.IO) {
                async {
                    iircCharJson.jsonArray.map {
                        /*
                        IIRCCharacter(
                            id = it.jsonObject["id"]!!.jsonPrimitive.int,
                            worldId = it.jsonObject["worldId"]!!.jsonPrimitive.int,
                            name = Json.decodeFromJsonElement(it.jsonObject["name"]!!),
                            isUnlocked = it.jsonObject["isUnlocked"]!!.jsonPrimitive.boolean,
                            isVisible = it.jsonObject["isVisible"]!!.jsonPrimitive.boolean,
                            count = it.jsonObject["count"]!!.jsonPrimitive.int,
                            status = Json.decodeFromJsonElement(it.jsonObject["status"]!!),
                            upgradeRequirements = Json.decodeFromJsonElement(it.jsonObject["upgradeRequirements"]!!),
                            visibleRequirements = Json.decodeFromJsonElement(it.jsonObject["visibleRequirements"]!!)
                        )

                         */
                    }
                }.await() as ArrayList<IIRCCharacter>
            }
        }

        val WorldListSaver: Saver<ArrayList<IIRCWorld>, Any> = listSaver(
            save = { listOf(Json.encodeToString(it)) },
            restore = { Json.decodeFromString(it[0]) }
        )
    }
}