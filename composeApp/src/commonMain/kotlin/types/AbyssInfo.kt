package types

import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.decodeFromJsonElement
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import utils.app.Language
import utils.app.Language.Companion.TextLanguageInstance
import utils.annotation.VersionUpdateCheck
import utils.app.errorLog
import utils.app.getAssetsJsonByFilePath
import utils.app.getAssetsJsonStrByFilePath
import utils.app.getAssetsStrByFilePath

@Serializable
enum class AbyssInfoType {
    @SerialName("MemoryOfChaos")
    MemoryOfChaos,
    @SerialName("PureFiction")
    PureFiction,
    @SerialName("ApocalypticShadow")
    ApocalypticShadow,
}

/**
 * This is for reading the content of abyss info (<chao/pure_fiction>_xxx.json)
 */
@Serializable
data class AbyssInfo(
    @SerialName("name")
    val nameList: Map<Language.TextLanguage, String> = mapOf(),

    @SerialName("info")
    val missionList: ArrayList<AbyssInfoMission> = arrayListOf(),

    @SerialName("desc")
    val descList: Map<Language.TextLanguage, String> = mapOf(),

    @SerialName("buff")
    val buffList: ArrayList<AbyssInfoBuff> = arrayListOf(),

    @SerialName("time")
    val timeInfo: AbyssInfoTime = AbyssInfoTime(0, 0),
) {
    companion object{
        fun getAbyssItemById(abyssId: Int, type: AbyssInfoType, abyssFileName: String): AbyssInfo? {
            try {
                /*
                val abyssFileName = getAbyssFileNameById(abyssId, type)
                if (abyssFileName == "UNKNOWN_ID") return null

                val abyssJsonStr = getAssetsStrByFilePath(
                    when(type){
                        AbyssInfoType.MemoryOfChaos -> "memory_of_chao_data/$abyssFileName.json"
                        AbyssInfoType.PureFiction -> "pure_fiction_data/$abyssFileName.json"
                    },
                    defaultData = "[]"
                )
                 */

                val abyssJsonStr = getAssetsStrByFilePath(
                    when(type){
                        AbyssInfoType.MemoryOfChaos -> "memory_of_chao_data/$abyssFileName.json"
                        AbyssInfoType.PureFiction -> "pure_fiction_data/$abyssFileName.json"
                        AbyssInfoType.ApocalypticShadow -> "apocalyptic_shadow_data/$abyssFileName.json"
                    },
                    defaultData = "[]"
                )
                val json = Json { ignoreUnknownKeys = true }
                return json.decodeFromString<AbyssInfo>(abyssJsonStr)
            }catch (e: Exception) {
                errorLog("AbyssInfo", "getAbyssItemByMocId(abyssId = $abyssId, type = $type)", e)
                return null
            }
        }
    }
}

/**
 * This is for reading the list of abyss info (xxx_list.json)
 */
@Serializable
data class AbyssInfoList(
    @SerialName("id") val id: Int,
    @SerialName("fileName") val fileName: String,
    @SerialName("name") val nameList: Map<Language.TextLanguage, String>,
    @SerialName("time") val time: AbyssInfoTime
){
    companion object{
        private var mocListJson = getAssetsJsonByFilePath("memory_of_chao_data/chao_list.json", defaultData = "[]")
        private var pfListJson = getAssetsJsonByFilePath("pure_fiction_data/pf_list.json", defaultData = "[]")
        private var asListJson = getAssetsJsonByFilePath("apocalyptic_shadow_data/as_list.json", defaultData = "[]")

        fun refreshListJson() {
            mocListJson = getAssetsJsonByFilePath("memory_of_chao_data/chao_list.json", defaultData = "[]")
            pfListJson = getAssetsJsonByFilePath("pure_fiction_data/pf_list.json", defaultData = "[]")
            asListJson = getAssetsJsonByFilePath("apocalyptic_shadow_data/as_list.json", defaultData = "[]")
        }

        @OptIn(ExperimentalCoroutinesApi::class)
        fun getAbyssList(type: AbyssInfoType): ArrayList<AbyssInfoList> {
            return runBlocking {
                val job = async(Dispatchers.Default) {
                    val retArray = arrayListOf<AbyssInfoList>()
                    try {
                        val abyssJson = getAssetsStrByFilePath(
                            when(type){
                                AbyssInfoType.MemoryOfChaos -> "memory_of_chao_data/chao_list.json"
                                AbyssInfoType.PureFiction -> "pure_fiction_data/pf_list.json"
                                AbyssInfoType.ApocalypticShadow -> "apocalyptic_shadow_data/as_list.json"
                            },
                            defaultData = "[]"
                        )

                        //記得以後Enum要寫全 不然會出問題
                        val json = Json { ignoreUnknownKeys = true }
                        return@async json.decodeFromString<ArrayList<AbyssInfoList>>(abyssJson)

                    }catch (e: Exception) {
                        errorLog("AbyssInfoList", "getAbyssList(type = $type)", e)
                        return@async retArray
                    }
                }
                job.await()
                job.getCompleted()
            }
        }
        @OptIn(ExperimentalCoroutinesApi::class)
        fun getAbyssTitleLocaleNameById(abyssId: Int, type: AbyssInfoType): String {
            return runBlocking {
                val job = async(Dispatchers.Default) {
                    try {
                        val abyssJson =
                            when(type){
                                AbyssInfoType.MemoryOfChaos -> mocListJson
                                AbyssInfoType.PureFiction -> pfListJson
                                AbyssInfoType.ApocalypticShadow -> asListJson
                            }

                        val abyssList = Json.decodeFromJsonElement<ArrayList<AbyssInfoList>>(abyssJson)

                        val abyssFiltered = abyssList.filter { it.id == abyssId }
                        if(abyssFiltered.isEmpty()) return@async "???"
                        return@async abyssFiltered[0].nameList[TextLanguageInstance] ?: "???"
                    }catch (e: Exception) {
                        errorLog("AbyssInfoList", "getAbyssTitleLocaleNameById(abyssId = $abyssId, type = $type)", e)
                        return@async "???"
                    }
                }
                job.await()
                job.getCompleted()
            }

        }

        @OptIn(ExperimentalCoroutinesApi::class)
        fun getAbyssInfoFileNameById(abyssId: Int, type: AbyssInfoType): String {
            return runBlocking {
                val job = async(Dispatchers.Default) {
                    try {
                        val abyssJson =
                            when(type){
                                AbyssInfoType.MemoryOfChaos -> mocListJson
                                AbyssInfoType.PureFiction -> pfListJson
                                AbyssInfoType.ApocalypticShadow -> asListJson
                            }

                        val abyssList = Json.decodeFromJsonElement<ArrayList<AbyssInfoList>>(abyssJson)

                        val abyssFiltered = abyssList.filter { it.id == abyssId }
                        if(abyssFiltered.isEmpty()) return@async "???"
                        return@async abyssFiltered[0].fileName
                    }catch (e: Exception) {
                        errorLog("AbyssInfoList", "getAbyssTitleLocaleNameById(abyssId = $abyssId, type = $type)", e)
                        return@async "???"
                    }
                }
                job.await()
                job.getCompleted()
            }

        }
        val Saver: Saver<AbyssInfoList, Any> = Saver(
            save = { Json.encodeToString(it) },
            restore = { Json.decodeFromString<AbyssInfoList>(it as String) }
        )
        val ListSaver: Saver<ArrayList<AbyssInfoList>, Any> = listSaver(
            save = { listOf(Json.encodeToString(it)) },
            restore = { Json.decodeFromString(it[0]) }
        )
    }

}

@Serializable
data class AbyssInfoMission(
    @SerialName("name")
    val nameList: Map<Language.TextLanguage, String> = mapOf(),

    @SerialName("part1")
    val part1: AbyssInfoPhase,

    @SerialName("part2")
    val part2: AbyssInfoPhase,
)

@Serializable
data class AbyssInfoPhase(
    @SerialName("weakness_suggest")
    val weaknessList: ArrayList<AbyssInfoCombatType> = arrayListOf(),

    @SerialName("totalWaves")
    val totalWaves: Int,

    @SerialName("wave1")
    val monsterWaveInfo1: ArrayList<AbyssInfoMonster> = arrayListOf(),

    @SerialName("wave2")
    val monsterWaveInfo2: ArrayList<AbyssInfoMonster> = arrayListOf(),
)

@Serializable
data class AbyssInfoMonster (
    @SerialName("monster_name")
    val registName: String,

    @SerialName("monster_code")
    val monsterRank: Int,

    @SerialName("monster_weakness")
    val monsterWeakness: ArrayList<AbyssInfoCombatType> = arrayListOf(),
)

@Serializable
data class AbyssInfoTime(
    val begin: Long,
    val end: Long,
    val versionBegin: String? = null,
    val versionEnd: String? = null,
)

@Serializable
enum class AbyssInfoMonsterRank(val rankName: String, val code: Int) {
    CustomValueTags("CustomValueTags", -1),
    BIGBOSS("BigBoss", 5),
    LITTLEBOSS("LittleBoss", 4),
    ELITE("Elite", 3),
    MINIONLV2("MinionLv2", 2),
    MINIONLV1("MinionLv1", 1),
    UNKNOWN("UNKNOWN", 0);
}

@Serializable
enum class AbyssInfoCombatType(
    var chName: String,
    var resName: StringResource,
    var iconWhite: DrawableResource,
    var iconColor: DrawableResource,
    localeStringId: StringResource = Res.string.app_name
) {
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


@Serializable
data class AbyssInfoBuff(
    @SerialName("buffId")
    val buffId: String,

    @SerialName("buffIcon")
    val buffIcon: String,

    @SerialName("name")
    val nameList: Map<Language.TextLanguage, String> = mapOf(),

    @SerialName("desc")
    val descList: Map<Language.TextLanguage, String> = mapOf(),
)

@Serializable
data class AbyssInfoUsage(
    @SerialName("id")
    val id: String,

    @SerialName("rate")
    val rate: Float = 0f,

    val phase: Int = 1,
)

@Serializable
data class AbyssInfoTeamUsage(
    @SerialName("id")
    val id: String,

    @SerialName("rate")
    val rate: Float = 0f,

    @SerialName("buff_info")
    val buffInfo : ArrayList<AbyssInfoUsage> = arrayListOf(),

)