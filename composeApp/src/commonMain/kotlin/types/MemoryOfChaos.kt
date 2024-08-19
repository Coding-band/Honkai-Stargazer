package types

import files.Fire
import files.HaveNotUsed
import files.Ice
import files.Imaginary
import files.Lightning
import files.Physical
import files.Quantum
import files.Res
import files.Wind
import files.app_name
import files.element_fire
import files.element_ice
import files.element_imaginary
import files.element_lightning
import files.element_physical
import files.element_quantum
import files.element_wind
import files.ic_fire
import files.ic_ice
import files.ic_imaginary
import files.ic_lightning
import files.ic_physical
import files.ic_quatumn
import files.icon_wind
import files.pom_pom_failed_issue
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import utils.Language
import utils.Language.Companion.TextLanguageInstance
import utils.UtilTools
import utils.annotation.VersionUpdateCheck
import utils.errorLogExport

@Serializable
data class MemoryOfChaos(
    @SerialName("name")
    val nameList: Map<Language.TextLanguage, String> = mapOf(),

    @SerialName("info")
    val missionList: ArrayList<MemoryOfChaosMission> = arrayListOf(),

    @SerialName("desc")
    val descList: Map<Language.TextLanguage, String> = mapOf(),

    @SerialName("time")
    val timeInfo: MemoryOfChaosTime,
) {
    companion object{
        fun getMocItemByMocId(mocId: Int): MemoryOfChaos? {
            try {
                val mocFileName = getMocFileNameByMocId(mocId)
                if (mocFileName == "UNKNOWN_MOC_ID") return null

                val mocJsonStr = UtilTools().getAssetsJsonStrByFilePath("memory_of_chao_data/$mocFileName.json")
                return Json.decodeFromString<MemoryOfChaos>(mocJsonStr)
            }catch (e: Exception) {
                errorLogExport("MemoryOfChaos", "getMocItemByMocId($mocId)", e)
                return null
            }
        }

        @VersionUpdateCheck
        fun getMocFileNameByMocId(mocId: Int): String {
            return when(mocId){
                1008 -> "chao_1.5.0_2"
                1009 -> "chao_1.6.0_1"
                1010 -> "chao_1.6.0_2"
                1011 -> "chao_2.0.0_1"
                1012 -> "chao_2.0.0_2"
                1013 -> "chao_2.1.0_1"
                1014 -> "chao_2.2.0_1"
                1015 -> "chao_2.2.0_2"
                1016 -> "chao_2.3.0"
                1017 -> "chao_2.4.0"
                else -> "UNKNOWN_MOC_ID"
            }
        }

    }
}

@Serializable
data class MemoryOfChaosList(
    @SerialName("id") val id: Int,
    @SerialName("name") val nameList: Map<Language.TextLanguage, String>,
    @SerialName("time") val time: MemoryOfChaosTime
){
    companion object{
        fun getMocList(): ArrayList<MemoryOfChaosList> {
            val retArray = arrayListOf<MemoryOfChaosList>()
            try {
                val mocJson = UtilTools().getAssetsJsonStrByFilePath("memory_of_chao_data/chao_list.json")

                //記得以後Enum要寫全 不然會出問題
                return Json.decodeFromString<ArrayList<MemoryOfChaosList>>(mocJson)
                /*
                val mocJson = UtilTools().getAssetsJsonByFilePath("memory_of_chao_data/chao_list.json")
                if(mocJson !is JsonArray) return arrayListOf()

                for(mocItem in mocJson){
                    val nameList = hashMapOf<Language.TextLanguage, String>()
                    mocItem.jsonObject["name"]?.jsonObject?.map {
                        nameList[Language.TextLanguage.valueOf(it.key.uppercase())] = it.value.jsonPrimitive.content
                    }

                    val mocObj = MemoryOfChaosList(
                        id = mocItem.jsonObject["id"]?.jsonPrimitive?.int ?: -1,
                        nameList = nameList,
                        time = Json.decodeFromString(mocItem.jsonObject["time"]!!.jsonObject.toString())
                    )
                    retArray.add(mocObj)
                }
                 */

            }catch (e: Exception) {
                errorLogExport("MemoryOfChaosList", "getMocList()", e)
                return retArray
            }
        }

        fun getMocTitleLocaleNameById(mocId: Int): String {
            val retArray = arrayListOf<MemoryOfChaosList>()
            try {
                val mocJson = UtilTools().getAssetsJsonStrByFilePath("memory_of_chao_data/chao_list.json")
                val mocList = Json.decodeFromString<ArrayList<MemoryOfChaosList>>(mocJson)

                val mocFiltered = mocList.filter { it.id == mocId }
                if(mocFiltered.isEmpty()) return "???"
                return mocFiltered[0].nameList[TextLanguageInstance] ?: "???"
            }catch (e: Exception) {
                errorLogExport("MemoryOfChaosList", "getMocList()", e)
                return "???"
            }
        }
    }

}

@Serializable
data class MemoryOfChaosMission(
    @SerialName("name")
    val nameList: Map<Language.TextLanguage, String> = mapOf(),

    @SerialName("part1")
    val phase1: MemoryOfChaosPhase,

    @SerialName("part2")
    val phase2: MemoryOfChaosPhase,
)

@Serializable
data class MemoryOfChaosPhase(
    @SerialName("weakness_suggest")
    val weaknessList: ArrayList<MemoryOfChaosCombatType> = arrayListOf(),

    @SerialName("totalWaves")
    val totalWaves: Int,

    @SerialName("wave1")
    val monsterWaveInfo1: ArrayList<MemoryOfChaosMonsterInfo> = arrayListOf(),

    @SerialName("wave2")
    val monsterWaveInfo2: ArrayList<MemoryOfChaosMonsterInfo> = arrayListOf(),
)

@Serializable
data class MemoryOfChaosMonsterInfo (
    @SerialName("monster_name")
    val registName: String,

    @SerialName("monster_code")
    val monsterRank: Int,

    @SerialName("monster_weakness")
    val monsterWeakness: ArrayList<MemoryOfChaosCombatType> = arrayListOf(),
)

@Serializable
data class MemoryOfChaosTime(
    val begin: Long,
    val end: Long,
    val versionBegin: String,
    val versionEnd: String,
)

@Serializable
enum class MemoryOfChaosMonsterRank(val rankName: String, val code: Int) {
    CustomValueTags("CustomValueTags", -1),
    BIGBOSS("BigBoss", 5),
    LITTLEBOSS("LittleBoss", 4),
    ELITE("Elite", 3),
    MINIONLV2("MinionLv2", 2),
    MINIONLV1("MinionLv1", 1),
    UNKNOWN("UNKNOWN", 0);
}

@Serializable
enum class MemoryOfChaosCombatType (var chName : String, var resName: StringResource, var iconWhite : DrawableResource, var iconColor : DrawableResource, localeStringId : StringResource = Res.string.app_name){
    @SerialName("Imaginary")
    Imaginary("虛數", Res.string.Imaginary, Res.drawable.ic_imaginary, Res.drawable.element_imaginary),
    @SerialName("Quantum")
    Quantum("量子", Res.string.Quantum, Res.drawable.ic_quatumn, Res.drawable.element_quantum),
    @SerialName("Thunder")
    Lightning("雷", Res.string.Lightning, Res.drawable.ic_lightning, Res.drawable.element_lightning),
    @SerialName("Fire")
    Fire("火", Res.string.Fire, Res.drawable.ic_fire, Res.drawable.element_fire),
    @SerialName("Ice")
    Ice("冰", Res.string.Ice, Res.drawable.ic_ice, Res.drawable.element_ice),
    @SerialName("Wind")
    Wind("風", Res.string.Wind, Res.drawable.icon_wind, Res.drawable.element_wind),
    @SerialName("Physical")
    Physical("物理", Res.string.Physical, Res.drawable.ic_physical, Res.drawable.element_physical),
    @SerialName("Unspecified")
    Unspecified("未知", Res.string.HaveNotUsed, Res.drawable.pom_pom_failed_issue, Res.drawable.pom_pom_failed_issue);
}