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
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import utils.Language
import utils.Language.Companion.TextLanguageInstance
import utils.UtilTools
import utils.annotation.VersionUpdateCheck
import utils.errorLogExport

@Serializable
enum class AbyssInfoType {
    @SerialName("MemoryOfChaos")
    MemoryOfChaos,
    @SerialName("PureFiction")
    PureFiction,
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

    @SerialName("time")
    val timeInfo: AbyssInfoTime,
) {
    companion object{
        fun getAbyssItemById(abyssId: Int, type: AbyssInfoType): AbyssInfo? {
            try {
                val abyssFileName = getAbyssFileNameById(abyssId, type)
                if (abyssFileName == "UNKNOWN_ID") return null

                val abyssJsonStr = UtilTools().getAssetsJsonStrByFilePath(
                    when(type){
                        AbyssInfoType.MemoryOfChaos -> "memory_of_chao_data/$abyssFileName.json"
                        AbyssInfoType.PureFiction -> "pure_fiction_data/$abyssFileName.json"
                    }
                )

                val json = Json { ignoreUnknownKeys = true }
                return json.decodeFromString<AbyssInfo>(abyssJsonStr)
            }catch (e: Exception) {
                errorLogExport("AbyssInfo", "getAbyssItemByMocId(abyssId = $abyssId, type = $type)", e)
                return null
            }
        }

        @VersionUpdateCheck
        fun getAbyssFileNameById(abyssId: Int, type: AbyssInfoType): String {
            when(type){
                AbyssInfoType.MemoryOfChaos -> {
                    return when(abyssId){
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
                        else -> "UNKNOWN_ID"
                    }
                }
                AbyssInfoType.PureFiction -> {
                    return when(abyssId){
                        2001 -> "pure_fiction_1"
                        2002 -> "pure_fiction_2"
                        2003 -> "pure_fiction_3"
                        2004 -> "pure_fiction_4"
                        2005 -> "pure_fiction_5"
                        2006 -> "pure_fiction_6"
                        2007 -> "pure_fiction_7"
                        2008 -> "pure_fiction_8"
                        else -> "UNKNOWN_ID"
                    }
                }
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
    @SerialName("name") val nameList: Map<Language.TextLanguage, String>,
    @SerialName("time") val time: AbyssInfoTime
){
    companion object{
        @OptIn(ExperimentalCoroutinesApi::class)
        fun getAbyssList(type: AbyssInfoType): ArrayList<AbyssInfoList> {
            return runBlocking {
                val job = async(Dispatchers.Default) {
                    val retArray = arrayListOf<AbyssInfoList>()
                    try {
                        val abyssJson = UtilTools().getAssetsJsonStrByFilePath(
                            when(type){
                                AbyssInfoType.MemoryOfChaos -> "memory_of_chao_data/chao_list.json"
                                AbyssInfoType.PureFiction -> "pure_fiction_data/pf_list.json"
                            }
                        )

                        //記得以後Enum要寫全 不然會出問題
                        val json = Json { ignoreUnknownKeys = true }
                        return@async json.decodeFromString<ArrayList<AbyssInfoList>>(abyssJson)

                    }catch (e: Exception) {
                        errorLogExport("AbyssInfoList", "getAbyssList(type = $type)", e)
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
                        val abyssJson = UtilTools().getAssetsJsonStrByFilePath(
                            when(type){
                                AbyssInfoType.MemoryOfChaos -> "memory_of_chao_data/chao_list.json"
                                AbyssInfoType.PureFiction -> "pure_fiction_data/pf_list.json"
                            }
                        )
                        val abyssList = Json.decodeFromString<ArrayList<AbyssInfoList>>(abyssJson)

                        val abyssFiltered = abyssList.filter { it.id == abyssId }
                        if(abyssFiltered.isEmpty()) return@async "???"
                        return@async abyssFiltered[0].nameList[TextLanguageInstance] ?: "???"
                    }catch (e: Exception) {
                        errorLogExport("AbyssInfoList", "getAbyssTitleLocaleNameById(abyssId = $abyssId, type = $type)", e)
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