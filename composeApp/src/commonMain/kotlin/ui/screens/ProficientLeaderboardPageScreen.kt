package ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import files.CharSoul
import files.NoDataYet
import files.Res
import files.phorphos_caret_down_regular
import files.pom_pom_praying
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.jetbrains.compose.resources.painterResource
import types.Character
import types.Character.Companion.charExtListJson
import types.Character.Companion.charListJson
import types.CharacterProficient
import types.CombatType
import types.ImageFolder
import types.Lightcone
import types.UserAccount.Companion.UIDSEARCH
import ui.components.DropdownMenuNoPadding
import ui.components.HeaderData
import ui.components.PAGE_HEADER_HEIGHT
import ui.components.PageHeaderAlpha
import ui.components.PomPomPopup
import ui.components.TitleHeader
import ui.components.defaultHeaderData
import ui.components.pomPomPopupInstance
import ui.navigation.Screen
import ui.navigation.navigateLimited
import utils.app.CharWeightList
import utils.app.Constants
import utils.app.Constants.Companion.CLARA_KAMOJI
import utils.app.Constants.Companion.LOST_IMAGE_DRAWABLE
import utils.app.Constants.Companion.SCREEN_SAVE_PADDING
import utils.app.FontSizeNormal14
import utils.app.FontSizeNormal20
import utils.app.Language
import utils.app.formatDecimal
import utils.app.newImageRequest
import utils.app.removeStrQuote
import utils.app.replaceStrRes
import utils.hoyolab.MihomoRequest
import utils.starbase.StarbaseAPI

data class ProficientSchool(
    val zhName: String = CLARA_KAMOJI,
    val enName: String = "Default",
    val schoolIndex: Int,
    val charId: Int,
    val combatType: CombatType = CombatType.Unspecified,
)

@Composable
fun ProficientLeaderboardPageScreen(
    modifier: Modifier = Modifier,
    navigator: NavHostController,
    headerData: HeaderData = defaultHeaderData,
    snackbarHostState: SnackbarHostState? = remember { SnackbarHostState() },
) {
    val hazeState = remember { HazeState() }
    val selectedLeaderboardIndex = rememberSaveable { mutableStateOf(0) }
    val schoolList by rememberSaveable { mutableStateOf(arrayListOf(ProficientSchool(schoolIndex = 0, charId = 0))) }
    var leaderboardList by rememberSaveable { mutableStateOf(arrayListOf<CharacterProficient>()) }
    val isExpandSchoolDropdown = remember { mutableStateOf(false) }
    val optionTextViewSize = remember { mutableStateOf(IntSize.Zero) }
    val density = LocalDensity.current.density
    val isInited = rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if(!isInited.value){
            CoroutineScope(Dispatchers.Default).launch {
                async {
                    //println("KEYS: ${CharWeightList.INSTANCE.jsonObject.keys.size}")
                    schoolList.clear()
                    CharWeightList.INSTANCE.jsonObject.mapKeys { item ->
                        val listDataJson = charListJson.jsonArray.firstOrNull { charData -> charData.jsonObject["charId"]!!.jsonPrimitive.content == item.key }
                        val listExtDataJson = charExtListJson.jsonArray.firstOrNull { charData -> charData.jsonObject["officialId"]!!.jsonPrimitive.content == item.key }

                        if (listExtDataJson == null || listDataJson == null) return@mapKeys

                        item.value.jsonArray.forEachIndexed { index, schoolData ->
                            schoolList.add(
                                ProficientSchool(
                                    charId = item.key.toInt(),
                                    schoolIndex = index,
                                    zhName = schoolData.jsonObject["zh_name"]?.jsonPrimitive?.content ?: "???",
                                    enName = schoolData.jsonObject["en_name"]?.jsonPrimitive?.content ?: "???",
                                    //icon = schoolData.jsonObject["icon"]?.jsonPrimitive?.content ?: "",
                                    combatType = CombatType.valueOf(listDataJson.jsonObject["element"]?.jsonPrimitive?.content ?: "Unspecified")
                                )
                            )
                        }
                    }
                }.await()

                withContext(Dispatchers.Main) {
                    isInited.value = true
                }
            }
        }
    }

    LaunchedEffect(isInited.value) {
        if (isInited.value && schoolList.isNotEmpty()) {
            CoroutineScope(Dispatchers.Default).launch {
                async {
                    val request = StarbaseAPI().getProfLeaderboardList(
                        schoolList[selectedLeaderboardIndex.value].charId, schoolList[selectedLeaderboardIndex.value].schoolIndex
                    )
                    //println("Leadeboard in ${schoolList[selectedLeaderboardIndex.value].zhName} (${schoolList[selectedLeaderboardIndex.value].schoolIndex}) : ${request.size}")

                    leaderboardList = request
                }.await()
            }
        }
    }

    LaunchedEffect(selectedLeaderboardIndex.value) {
        if(isInited.value){
            CoroutineScope(Dispatchers.Default).launch {
                async {
                    val request = StarbaseAPI().getProfLeaderboardList(
                        schoolList[selectedLeaderboardIndex.value].charId,
                        schoolList[selectedLeaderboardIndex.value].schoolIndex
                    )
                    //println("Leadeboard in ${schoolList[selectedLeaderboardIndex.value].zhName} (${schoolList[selectedLeaderboardIndex.value].schoolIndex}) : ${request.size}")

                    leaderboardList = request
                }.await()
            }
        }
    }

    Box(Modifier.fillMaxSize()) {
        Column {
            Spacer(
                modifier = Modifier
                    .statusBarsPadding()
                    .height(PAGE_HEADER_HEIGHT)
            )

            Row(modifier = Modifier.clip(RoundedCornerShape(43.dp))) {
                //DropDownBar
                Box(
                    contentAlignment = Alignment.BottomCenter,
                    modifier = Modifier
                        .defaultMinSize(100.dp, 30.dp)
                        .wrapContentSize()
                        .clip(RoundedCornerShape(43.dp))
                        .padding(start = SCREEN_SAVE_PADDING, end = SCREEN_SAVE_PADDING)
                        .clickable { isExpandSchoolDropdown.value = !isExpandSchoolDropdown.value }
                ) {
                    if(!schoolList.isEmpty()){
                        Row(
                            modifier = Modifier.background(Color(0x66000000), RoundedCornerShape(43.dp))
                                .wrapContentWidth()
                                .onSizeChanged { optionTextViewSize.value = it },
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                AsyncImage(
                                    model = newImageRequest(context = LocalPlatformContext.current, Character.getCharacterImageFromOfficialId(ImageFolder.CHAR_ICON,
                                        schoolList[selectedLeaderboardIndex.value].charId.toString()
                                    )),
                                    contentDescription = null,
                                    modifier = Modifier.size(36.dp).clip(CircleShape).align(Alignment.CenterVertically),
                                    error = painterResource(LOST_IMAGE_DRAWABLE)
                                )
                                Spacer(Modifier.width(8.dp))
                                Image(
                                    painter = painterResource(schoolList[selectedLeaderboardIndex.value].combatType.iconColor),
                                    modifier = Modifier.size(24.dp).align(Alignment.CenterVertically),
                                    contentDescription = "CombatType Icon"
                                )
                                Text(
                                    text = if (listOf(Language.TextLanguage.ZH_HK, Language.TextLanguage.ZH_CN).contains(Language.TextLanguageInstance)) schoolList[selectedLeaderboardIndex.value].zhName else schoolList[selectedLeaderboardIndex.value].enName,
                                    style = FontSizeNormal14(),
                                    color = Color.White,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(4.dp).align(Alignment.CenterVertically)
                                )
                            }
                            Image(
                                painter = painterResource(Res.drawable.phorphos_caret_down_regular),
                                contentDescription = null,
                                modifier = Modifier.padding(6.dp).size(16.dp).align(Alignment.CenterVertically),
                                colorFilter = ColorFilter.tint(Color.White)
                            )
                        }
                    }
                    //對於DropdownItem沒法按照設計稿展示，暫時無解
                    DropdownMenuNoPadding(
                        expanded = isExpandSchoolDropdown.value,
                        onDismissRequest = { isExpandSchoolDropdown.value = false },
                        modifier = Modifier
                            .background(Color(0xFF3E3E47))
                            //.width(pxToDp(optionTextViewSize.value.width, density)),
                            .wrapContentWidth()
                            .statusBarsPadding().navigationBarsPadding()
                    ) {
                        schoolList.forEachIndexed { index, option ->
                            DropdownMenuItem(
                                onClick = {
                                    selectedLeaderboardIndex.value = index
                                    isExpandSchoolDropdown.value = false
                                    //optionAction(schoolIndex.value)
                                },
                                modifier = Modifier.background(if (selectedLeaderboardIndex.value == index) Color(0x0F000000) else Color(0x00000000))
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    AsyncImage(
                                        model = newImageRequest(context = LocalPlatformContext.current, Character.getCharacterImageFromOfficialId(ImageFolder.CHAR_ICON,
                                            option.charId.toString()
                                        )),
                                        contentDescription = null,
                                        modifier = Modifier.size(36.dp).clip(CircleShape).align(Alignment.CenterVertically),
                                        error = painterResource(LOST_IMAGE_DRAWABLE)
                                    )
                                    Spacer(Modifier.width(8.dp))
                                    Image(
                                        painter = painterResource(option.combatType.iconColor),
                                        modifier = Modifier.size(24.dp).align(Alignment.CenterVertically),
                                        contentDescription = "CombatType Icon"
                                    )
                                    Text(
                                        text = if (listOf(Language.TextLanguage.ZH_HK, Language.TextLanguage.ZH_CN).contains(Language.TextLanguageInstance)) option.zhName else option.enName,
                                        style = FontSizeNormal14(),
                                        color = Color.White,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.padding(4.dp).align(Alignment.CenterVertically)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = Constants.SCREEN_SAVE_PADDING, end = Constants.SCREEN_SAVE_PADDING)
                    .haze(hazeState),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                item { Spacer(Modifier.height(10.dp)) }
                if (leaderboardList.isEmpty()) {
                    item {
                        Column {
                            Spacer(Modifier.height(64.dp))
                            Image(
                                painter = painterResource(Res.drawable.pom_pom_praying),
                                contentDescription = "No Data",
                                modifier = Modifier.fillMaxSize(0.5f).align(Alignment.CenterHorizontally),
                            )

                            Text(
                                text = removeStrQuote(Res.string.NoDataYet),
                                style = FontSizeNormal20(),
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxSize().align(Alignment.CenterHorizontally)
                            )
                        }
                    }
                } else {
                    items(leaderboardList) { item ->
                        ProfLeaderboardItem(item,navigator)
                    }
                }
                //Comments & Suggestions
            }
        }

        PageHeaderAlpha(
            navigator = navigator,
            hazeState = hazeState,
        ) {
            TitleHeader(headerData.titleIconId, headerData.title, headerData.titleRId)
        }
    }
}

@Composable
fun ProfLeaderboardItem(charProf: CharacterProficient, navigator: NavHostController) {
    val lcDataJson = Lightcone.lcListJson.jsonArray.firstOrNull { lcData -> lcData.jsonObject["fileName"]!!.jsonPrimitive.int == charProf.lcId }
    val lcName = lcDataJson?.jsonObject?.get("name")?.jsonPrimitive?.content ?: ""
    val isQuerying = remember { mutableStateOf(false) }
    Row(
        modifier = Modifier.clickable {
            CoroutineScope(Dispatchers.Default).launch{
                if(isQuerying.value) return@launch

                withContext(Dispatchers.Main){
                    pomPomPopupInstance.value = PomPomPopup(isDisplay = true)
                    isQuerying.value = true
                }

                val starbaseResult = StarbaseAPI().getUserAccountInfo(uid = charProf.playerId.toString(), ignoreNotFind = true, isForProf = true, charProf.charId.toString())
                UIDSEARCH = if (starbaseResult.uid != "000000000") {
                    starbaseResult
                } else {
                    MihomoRequest(charProf.playerId.toString()).getUserAccountByMiHomo()
                }

                withContext(Dispatchers.Main){
                    pomPomPopupInstance.value = PomPomPopup(isDisplay = false)
                    isQuerying.value = false
                    navigator.navigateLimited("${Screen.UserCharacterPageScreen.route}?uid=${charProf.playerId}&charId=${charProf.charId}")
                }
            }
        }
    ) {
        Text(
            text = charProf.rank.toString(),
            style = FontSizeNormal20(),
            color = when (charProf.rank) {
                1 -> Color(0xFFFFD070)
                2 -> Color(0xCCF3F9FF)
                3 -> Color(0xFFAB6F66)
                else -> Color.White
            },
            modifier = Modifier.align(Alignment.CenterVertically)
        )

        Spacer(Modifier.width(12.dp))

        Text(
            text = charProf.playerName,
            style = FontSizeNormal20(),
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterVertically).weight(1f)
        )

        Spacer(Modifier.width(8.dp))

        //DoItLater("Add Cast-Handle for lc_id == null")
        if(lcName.isEmpty()){
            Text(
                text = "---",
                style = FontSizeNormal20(),
                color = Color.White,
                modifier = Modifier.align(Alignment.CenterVertically).size(36.dp)
            )
        }else{
            AsyncImage(
                model = newImageRequest(
                    LocalPlatformContext.current,
                    Lightcone.getLightconeImageFromJSON(ImageFolder.LC_ICON, lcName)
                ),
                contentDescription = null,
                modifier = Modifier.size(36.dp),
                error = painterResource(LOST_IMAGE_DRAWABLE)
            )
        }

        Spacer(Modifier.width(8.dp))

        Text(
            text = removeStrQuote(Res.string.CharSoul).replaceStrRes(charProf.charSoul.toString()),
            style = FontSizeNormal20(),
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterVertically)
        )

        Spacer(Modifier.width(16.dp))

        Text(
            text = formatDecimal(charProf.charTotalScore, 2),
            style = FontSizeNormal20(),
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterVertically).defaultMinSize(50.dp, minHeight = Dp.Unspecified),
            minLines = 1
        )
    }
}