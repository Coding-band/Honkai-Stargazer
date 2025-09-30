package types

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import files.Fire
import files.HaveNotUsed
import files.Ice
import files.Imaginary
import files.Lightning
import files.NoDataYet
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
import files.ic_arrow_to_down
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
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import ui.components.MonsterCard
import ui.components.horizontalFadingEdge
import utils.app.Constants
import utils.app.FontSizeNormal12
import utils.app.FontSizeNormal14
import utils.app.FontSizeNormal16
import utils.app.Language
import utils.app.Language.Companion.TextLanguageInstance
import utils.app.errorLog
import utils.app.formatDecimal
import utils.app.getAssetsJsonByFilePath
import utils.app.getAssetsStrByFilePath
import utils.app.newImageRequest
import utils.app.pxToDp
import utils.app.removeStrQuote
import utils.app.toInt
import utils.starbase.StarbaseAPI

@Serializable
enum class AbyssInfoType {
    @SerialName("MemoryOfChaos")
    MemoryOfChaos,
    @SerialName("PureFiction")
    PureFiction,
    @SerialName("ApocalypticShadow")
    ApocalypticShadow,
    @SerialName("AnomalyArbitration")
    AnomalyArbitration,
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
                        AbyssInfoType.AnomalyArbitration -> "anomaly_arbitration_data/$abyssFileName.json"
                    },
                    defaultData = "[]"
                )
                val json = Json { ignoreUnknownKeys = true }
                return json.decodeFromString<AbyssInfo>(abyssJsonStr)
            }catch (e: Exception) {
                println("getAbyssItemByMocId(abyssId = $abyssId, type = $type)")
                e.printStackTrace()
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
        private var aaListJson = getAssetsJsonByFilePath("anomaly_arbitration_data/aa_list.json", defaultData = "[]")

        fun refreshListJson() {
            mocListJson = getAssetsJsonByFilePath("memory_of_chao_data/chao_list.json", defaultData = "[]")
            pfListJson = getAssetsJsonByFilePath("pure_fiction_data/pf_list.json", defaultData = "[]")
            asListJson = getAssetsJsonByFilePath("apocalyptic_shadow_data/as_list.json", defaultData = "[]")
            aaListJson = getAssetsJsonByFilePath("anomaly_arbitration_data/aa_list.json", defaultData = "[]")
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
                                AbyssInfoType.AnomalyArbitration -> "anomaly_arbitration_data/aa_list.json"
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
                                AbyssInfoType.AnomalyArbitration -> aaListJson
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
                                AbyssInfoType.AnomalyArbitration -> aaListJson
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
    val part2: AbyssInfoPhase? = null,

    @SerialName("type")
    val type: String? = "",
)

@Serializable
data class AbyssInfoPhase(
    @SerialName("weakness_suggest")
    val weaknessList: ArrayList<AbyssInfoCombatType> = arrayListOf(),

    @SerialName("wave1")
    val monsterWaveInfo1: ArrayList<AbyssInfoMonster> = arrayListOf(),

    @SerialName("wave2")
    val monsterWaveInfo2: ArrayList<AbyssInfoMonster> = arrayListOf(),

    @SerialName("totalWaves")
    val totalWaves: Int? = monsterWaveInfo1.isNotEmpty().toInt() + monsterWaveInfo2.isNotEmpty().toInt(),
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

    @SerialName("buff_info")
    val buffInfo : ArrayList<AbyssInfoBuffUsage> = arrayListOf(),
)

@Serializable
data class AbyssInfoBuffUsage(
    @SerialName("id")
    val id: String,

    @SerialName("rate")
    val rate: Float = 0f,
)

// ---------------------------- Composable --------------------------------

@Composable
fun AbyssNoDataContent(){
    Box(
        Modifier.wrapContentWidth().fillMaxHeight().padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = removeStrQuote(Res.string.NoDataYet),
            style = FontSizeNormal14(),
            color = Color.White,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun AbyssCharUsageContent(charUsageList: SnapshotStateList<AbyssInfoUsage>, phase: Int){
    val maxWidthOfItem = remember { mutableStateOf(36.dp) }
    val context = LocalPlatformContext.current
    val density = LocalDensity.current.density

    if(charUsageList.isEmpty()){
        AbyssNoDataContent()
    }else{
        val maxRowWhenNotExpand = 2
        val charUsageListSorted = charUsageList.filter { it.phase == (phase+1) && it.id != "?" }.sortedByDescending { it.rate }
        val isDisplayFullList = remember { mutableStateOf(false) }

        Column {
            FlowRow(
                maxLines = if(isDisplayFullList.value) Int.MAX_VALUE else 2,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                itemVerticalAlignment = Alignment.CenterVertically,
            ) {
                for (item in charUsageListSorted){
                    Column(modifier = Modifier.width(maxWidthOfItem.value).align(Alignment.CenterVertically)) {
                        AsyncImage(
                            model = newImageRequest(context = context, data = Character.getCharacterImageFromOfficialId(
                                ImageFolder.CHAR_ICON, item.id)),
                            contentDescription = null,
                            modifier = Modifier.size(36.dp).clip(CircleShape).align(Alignment.CenterHorizontally)
                        )
                        Text(
                            text = "${formatDecimal(item.rate*100, isRoundDown = true)}%",
                            style = FontSizeNormal12(),
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.wrapContentWidth()
                                .onGloballyPositioned {
                                    maxWidthOfItem.value = max(maxWidthOfItem.value, pxToDp(it.size.width, density))
                                }
                        )
                    }
                }
            }

            Box(Modifier.fillMaxWidth().wrapContentHeight(), contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(Res.drawable.ic_arrow_to_down),
                    modifier = Modifier.size(24.dp).clip(CircleShape).padding(4.dp)
                        .rotate(if (isDisplayFullList.value) 180f else 0f)
                        .clickable { isDisplayFullList.value = !isDisplayFullList.value },
                    colorFilter = ColorFilter.tint(Color(0x66F3F9FF)),
                    contentDescription = "Expand / Collapse List"
                )
            }
        }
    }
}

@Composable
fun AbyssTeamUsageContent(teamUsageList: SnapshotStateList<AbyssInfoUsage>, infoList: AbyssInfo? , phase: Int){
    val maxWidthOfItem = remember { mutableStateOf(32.dp) }
    val context = LocalPlatformContext.current
    val density = LocalDensity.current.density

    if(teamUsageList.isEmpty()){
        AbyssNoDataContent()
    }else{
        val maxRowWhenNotExpand = 4
        val teamUsageListSorted = teamUsageList.filter { it.phase == (phase+1) }.sortedByDescending { it.rate }
        val isDisplayFullList = remember { mutableStateOf(false) }
        Column {
            teamUsageListSorted.forEachIndexed { index, item ->
                if(!isDisplayFullList.value && index >= maxRowWhenNotExpand) return@forEachIndexed
                //Row that show all characters in a team
                val scrollState = rememberScrollState()
                Row(modifier =  Modifier.horizontalScroll(scrollState).horizontalFadingEdge(scrollState, 16.dp, Color.Black)) {
                    Text(
                        text = "${formatDecimal(item.rate*100, isRoundDown = true)}%",
                        style = FontSizeNormal14(),
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.wrapContentWidth().requiredWidth(64.dp).align(Alignment.CenterVertically)
                    )

                    Spacer(Modifier.width(8.dp))

                    //Character Icon
                    val charIdList = (item.id).chunked(4)
                    charIdList.forEach { charId ->
                        AsyncImage(
                            model = newImageRequest(context = context, data = Character.getCharacterImageFromOfficialId(
                                ImageFolder.CHAR_ICON, charId)),
                            contentDescription = null,
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier
                                .size(36.dp)
                                .background(brush = Brush.linearGradient(Constants.getCardBgColorByRare(Character.getCharacterRarityFromOfficialId(charId))), CircleShape)
                                .clip(CircleShape)
                                .align(Alignment.CenterVertically)
                        )
                        Spacer(Modifier.width(4.dp))
                    }

                    if(infoList == null) return
                    item.buffInfo.filter { it.id != "-1" }.map { buff ->
                        val buffIcon = infoList.buffList.find { it.buffId == buff.id }
                        if(buffIcon == null) return
                        Column(modifier = Modifier.wrapContentWidth().align(Alignment.CenterVertically)) {
                            AsyncImage(
                                model = newImageRequest(context = context, data = (StarbaseAPI().getGitHubStaticAssetURL() + "/images/buff_icons/${buffIcon.buffIcon}.webp")),
                                contentDescription = null,
                                contentScale = ContentScale.FillBounds,
                                modifier = Modifier.size(32.dp).padding(4.dp).background(Color(0xCC000000), CircleShape).border(1.dp, Color.White, CircleShape).align(Alignment.CenterHorizontally)
                            )
                            Text(
                                text = "${formatDecimal(item.rate*100, isRoundDown = true)}%",
                                style = FontSizeNormal12(),
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.width(40.dp)
                            )
                        }
                    }
                }

                Spacer(Modifier.height(8.dp))
            }

            if(teamUsageListSorted.size > maxRowWhenNotExpand){
                Box(Modifier.fillMaxWidth().wrapContentHeight(), contentAlignment = Alignment.Center) {
                    Image(
                        painter = painterResource(Res.drawable.ic_arrow_to_down),
                        modifier = Modifier.size(24.dp).clip(CircleShape).padding(4.dp)
                            .rotate(if (isDisplayFullList.value) 180f else 0f)
                            .clickable { isDisplayFullList.value = !isDisplayFullList.value },
                        colorFilter = ColorFilter.tint(Color(0x66F3F9FF)),
                        contentDescription = "Expand / Collapse List"
                    )
                }
            }
        }
    }
}


@Composable
fun AbyssMonsterInfoContent(modifier: Modifier = Modifier, phaseInfo: AbyssInfoPhase){
    Column(modifier.fillMaxWidth().wrapContentHeight()) {
        repeat(2){
            val monsterInfo = when(it){
                0 -> phaseInfo.monsterWaveInfo1
                1 -> phaseInfo.monsterWaveInfo2
                else -> null
            }

            if(monsterInfo.isNullOrEmpty()) return@repeat

            Row {
                MonsterCard(monsterInfo[0])
                Spacer(Modifier.width(8.dp))

                val scrollState = rememberScrollState()
                Row(Modifier.horizontalScroll(scrollState).horizontalFadingEdge(scrollState, 16.dp, Color.Black), verticalAlignment = Alignment.CenterVertically) {
                    monsterInfo.forEachIndexed { index, monster ->
                        if(index == 0) return@forEachIndexed
                        Spacer(Modifier.width(8.dp))
                        MonsterCard(monster)
                        Spacer(Modifier.width(8.dp))
                    }
                }
            }

            if(it == 0){
                Spacer(Modifier.height(8.dp))
            }
        }
    }
}

@OptIn(FormatStringsInDatetimeFormats::class)
@Composable
fun AbyssInfoTimeContent(infoDisplayIndex: MutableState<Int>, infoList: AbyssInfo?, infoUsageUpdateMS: MutableState<Long>){
    Column {
        Spacer(Modifier.height(4.dp))
        Text(
            text = if (infoDisplayIndex.value == 0) {
                val dateFormat = LocalDateTime.Format { byUnicodePattern("yyyy-MM-dd") }

                "${
                    dateFormat.format(
                        Instant.fromEpochMilliseconds(infoList?.timeInfo?.begin ?: 0L).toLocalDateTime(
                            TimeZone.currentSystemDefault())
                    )
                } ~ ${
                    dateFormat.format(
                        Instant.fromEpochMilliseconds(infoList?.timeInfo?.end ?: 0L).toLocalDateTime(
                            TimeZone.currentSystemDefault())
                    )
                }"
            } else {
                val dateFormat = LocalDateTime.Format { byUnicodePattern("yyyy-MM-dd HH:mm") }
                dateFormat.format(
                    Instant.fromEpochMilliseconds(infoUsageUpdateMS.value).toLocalDateTime(TimeZone.currentSystemDefault())
                )
            },
            textAlign = TextAlign.Center,
            style = FontSizeNormal16(),
            color = Color(0xFFDDDDDD),
            modifier = Modifier.fillMaxWidth()
        )
    }
}