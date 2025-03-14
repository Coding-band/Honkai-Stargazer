package ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.toRoute
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import com.cheonjaeung.compose.grid.SimpleGridCells
import com.cheonjaeung.compose.grid.VerticalGrid
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichText
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeSource
import files.CharRank
import files.CharScore
import files.Eidolon
import files.LackOfUserData
import files.LeaderboardDataFrom
import files.OverWholeServerUser
import files.ProducedByStargazer
import files.Res
import files.ScoreLevel
import files.Superimpose
import files.TraceNormalATK
import files.TraceSkill
import files.TraceTalent
import files.TraceTechnique
import files.TraceUltimate
import files.bg_transparent
import files.ic_atk
import files.ic_def
import files.ic_hp
import files.ic_selected_orange_circle
import files.phorphos_caret_down_regular
import files.ui_icon_share
import files.ui_icon_star
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.float
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.jetbrains.compose.resources.painterResource
import types.Character
import types.HsrProperties
import types.ImageFolder
import types.Lightcone
import types.UserAccount
import ui.components.DropdownMenuNoPadding
import ui.components.PAGE_HEADER_ALPHA_HEIGHT
import ui.components.PageHeaderAlpha
import ui.components.RelicSmallCard
import ui.navigation.UserCharacterRoute
import utils.annotation.DoItLater
import utils.app.AdditionalGreen
import utils.app.CharWeightList
import utils.app.Constants
import utils.app.Constants.Companion.INFO_MAX_WIDTH
import utils.app.Constants.Companion.INFO_MIN_WIDTH
import utils.app.FontSizeNormal12
import utils.app.FontSizeNormal14
import utils.app.FontSizeNormal16
import utils.app.FontSizeNormal20
import utils.app.FontSizeNormalLarge24
import utils.app.FontSizeNormalLarge32
import utils.app.FontSizeNormalSmall
import utils.app.GradReachYellow
import utils.app.JsonElementSaver
import utils.app.Language
import utils.app.formatDecimal
import utils.app.getAssetsURLByFileName
import utils.app.getImageNameByRegistName
import utils.app.htmlDescApplier
import utils.app.newImageRequest
import utils.app.pxToDp
import utils.app.removeStrQuote
import utils.app.replaceStrRes
import utils.app.showFunctionIsDevelopingToast
import utils.calculator.getCharRange
import utils.calculator.getCharScore
import utils.calculator.getGradAttrAndValue
import utils.calculator.getLcAttrData
import utils.hoyolab.AttributeExchange
import utils.starbase.StarbaseAPI

@DoItLater("Confirm that work when charStatus is null")
@Composable
fun UserCharacterPageScreen(
    navigator: NavHostController,
    hazeState: HazeState,
    backStackEntry: NavBackStackEntry,
) {
    val route = backStackEntry.toRoute<UserCharacterRoute>()
    val uid = route.uid
    val characterId = route.charId
    
    val userAccount by remember { mutableStateOf(
        if (UserAccount.INSTANCE.uid == uid) {
            UserAccount.INSTANCE
        } else {
            UserAccount.UIDSEARCH
        }
    ) }
    val characterFilter = userAccount.characterList.filter { it.officialId == characterId.toIntOrNull() }
    val listState = rememberLazyListState()
    val isScrolling by remember {
        derivedStateOf {
            // whatever logic you need
            listState.canScrollBackward
        }
    }

    val character = if(characterFilter.isEmpty()) null else characterFilter[0]
    val isShare = remember { mutableStateOf(false) }
    val charNameVisible = remember { mutableStateOf(true) }
    val charNameBigHeight = remember { mutableStateOf(20) }
    val isInited = rememberSaveable { mutableStateOf(false) }
    val defaultSchoolName = "默認流派 - Default"

    if(character == null){ navigator.popBackStack() }else{
        val charScoreLocal = remember { mutableStateOf(getCharScore(character, 0)) }
        val overPercentage = remember { mutableStateOf(getProfRankResult(charScoreLocal.value, character, 0, uid)) }
        val gradRequirement = remember { mutableStateOf(getGradAttrAndValue(character, 0)) }
        val schoolIndex = remember { mutableStateOf(0) }
        val schoolDataNameArray = remember { arrayListOf("默認流派 - Default") }

        if(CharWeightList.INSTANCE.jsonObject[characterId] != null){
            val charWeight = CharWeightList.INSTANCE.jsonObject[characterId]!!.jsonArray
            schoolDataNameArray.clear()
            for (i in 0 until charWeight.size){
                schoolDataNameArray.add(charWeight[i].jsonObject[if(Language.TextLanguageInstance.folderName.contains("zh")) "zh_name" else "en_name"]!!.jsonPrimitive.content)
            }
        }else{
            schoolDataNameArray.clear()
            schoolDataNameArray.add(defaultSchoolName)
        }

        LaunchedEffect(schoolIndex.value){
            val charScore = getCharScore(character, schoolIndex.value)
            charScoreLocal.value = charScore
            overPercentage.value = getProfRankResult(charScore, character, schoolIndex.value, uid)
            gradRequirement.value = getGradAttrAndValue(character, schoolIndex.value)
        }

        Box(modifier = Modifier.fillMaxSize()){
            CharacterInfoFadeImg(
                fileName = character.registName!!,
                isScrollMode = isScrolling, //alpha = scrollToAlpha
            )

            Column {
                PageHeaderAlpha(
                    navigator = navigator,
                    onForward = {
                        //TODO : Remember to add the Share Function
                        showFunctionIsDevelopingToast()
                    },
                    forwardIconId = Res.drawable.ui_icon_share,
                    hazeState = hazeState,

                    ) {

                    Column(Modifier.fillMaxSize()) {
                        Text(
                            "${userAccount.username}${if(charNameVisible.value)"·${character.displayName}" else ""}",
                            modifier = Modifier.align(Alignment.CenterHorizontally).padding(2.dp),
                            style = FontSizeNormal16(),
                            color = Color.White
                        )

                        Text(
                            text = "${userAccount.uid}·${
                                removeStrQuote(
                                    userAccount.server.localeName
                                )
                            }",
                            modifier = Modifier.align(Alignment.CenterHorizontally).padding(2.dp)
                                .background(Color(0x4D000000), RoundedCornerShape(49.dp))
                                .clip(RoundedCornerShape(49.dp)).padding(8.dp),
                            style = FontSizeNormal14(),
                            color = Color.White
                        )
                    }
                }

                charNameVisible.value = (listState.firstVisibleItemIndex > 0 && listState.firstVisibleItemScrollOffset > charNameBigHeight.value || listState.firstVisibleItemIndex > 1 )


                LazyColumn(
                    state = listState,
                    modifier = Modifier.padding(
                        start = Constants.SCREEN_SAVE_PADDING,
                        end = Constants.SCREEN_SAVE_PADDING
                    ).hazeSource(hazeState)
                ) {
                    item { Spacer(Modifier.statusBarsPadding().height(PAGE_HEADER_ALPHA_HEIGHT + 240.dp)) }
                    item { CharBioSkillInfo(character, charNameBigHeight) }
                    item { LightconeInfo(character) }
                    item { RelicInfo(character) }
                    item { ProficientScoreInfo(character, schoolDataNameArray, schoolIndex, charScoreLocal, overPercentage, gradRequirement) }

                    item { Spacer(Modifier.navigationBarsPadding()) }
                }
            }
        }
    }


}

@Composable
fun ProficientScoreInfo(character: Character, schoolDataNameArray: ArrayList<String>, schoolIndex: MutableState<Int>, charScoreLocal: MutableState<Float>, overPercentage: MutableState<Float>, gradRequirement: MutableState<ArrayList<Pair<AttributeExchange, Float>>>) {
    //Divider
    UserCharPageDivider()

    val isExpandSchoolDropdown = remember { mutableStateOf(false) }
    val optionTextViewSize = remember { mutableStateOf(IntSize.Zero) }
    val density = LocalDensity.current.density


    val scoreInfoList = arrayListOf(
        Res.string.CharScore to charScoreLocal.value,
        Res.string.CharRank to getCharRange(charScoreLocal.value),
        //Res.string.RelicScore to 123.4f,
        //Res.string.RelicRank to "B",
    )

    Box(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {

        Column(modifier = Modifier.widthIn(INFO_MIN_WIDTH, INFO_MAX_WIDTH).wrapContentHeight().align(Alignment.Center)) {
            //Title and Spinner
            Row {
                Text(
                    text = removeStrQuote(Res.string.ScoreLevel),
                    style = FontSizeNormal20(),
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )

                Spacer(Modifier.weight(1f))

                Box(
                    contentAlignment = Alignment.BottomCenter,
                    modifier = Modifier
                        .defaultMinSize(100.dp, 30.dp)
                        .wrapContentSize()
                        .clip(RoundedCornerShape(43.dp))
                        .clickable { isExpandSchoolDropdown.value = !isExpandSchoolDropdown.value }
                ) {
                    Row(
                        modifier = Modifier.background(Color(0x66000000), RoundedCornerShape(43.dp))
                            .wrapContentWidth()
                            .onSizeChanged { optionTextViewSize.value = it },
                    ){
                        Text(
                            color = Color.White,
                            text = schoolDataNameArray[schoolIndex.value],
                            style = FontSizeNormal14(),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(12.dp).align(Alignment.CenterVertically)
                        )
                        Image(
                            painter = painterResource(Res.drawable.phorphos_caret_down_regular),
                            contentDescription = null,
                            modifier = Modifier.padding(12.dp).size(16.dp).align(Alignment.CenterVertically),
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                    }
                    //對於DropdownItem沒法按照設計稿展示，暫時無解
                    DropdownMenuNoPadding(
                        expanded = isExpandSchoolDropdown.value,
                        onDismissRequest = { isExpandSchoolDropdown.value = false },
                        modifier = Modifier
                            .background(Color(0xFF3E3E47))
                            .width(pxToDp(optionTextViewSize.value.width, density)),
                    ) {
                        schoolDataNameArray.forEachIndexed { index, option ->
                            DropdownMenuItem(
                                onClick = {
                                    schoolIndex.value = index
                                    isExpandSchoolDropdown.value = false
                                    //optionAction(schoolIndex.value)
                                },
                                modifier = Modifier.background(if(schoolIndex.value == index) Color(0x0F000000) else Color(0x00000000))
                            ) {
                                Row{
                                    Text(
                                        text = option,
                                        style = FontSizeNormal14(),
                                        color = Color(0xFFFFFFFF),
                                        modifier = Modifier.weight(1f)
                                    )
                                    Image(
                                        painterResource(if (schoolIndex.value == index) Res.drawable.ic_selected_orange_circle else Res.drawable.bg_transparent),
                                        contentDescription = null
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(8.dp))

            //Scores
            val txt24Height = remember { mutableStateOf(30.dp) }
            Row(Modifier.wrapContentWidth().wrapContentHeight()) {
                for (scoreInfo in scoreInfoList){
                    Column(Modifier.weight(1f).wrapContentHeight().align(Alignment.CenterVertically), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {

                        if(scoreInfo.second is String){
                            Image(
                                painter = painterResource(Constants.getScoreRankingFont(scoreInfo.second as String)),
                                contentDescription = "Ranking Icon",
                                modifier = Modifier.height(txt24Height.value - 8.dp).aspectRatio(1f).align(Alignment.CenterHorizontally),
                            )
                        }else{
                            Text(
                                text = if(scoreInfo.second is String) {
                                    Constants.getScoreRankingFont(scoreInfo.second as String).toString()} else formatDecimal(scoreInfo.second as Number, 1 ,
                                    isRoundDown = true),
                                style = FontSizeNormalLarge24(),
                                color = Color.White,
                                maxLines = 1,
                                modifier = Modifier.align(Alignment.CenterHorizontally).onSizeChanged {
                                    txt24Height.value = pxToDp(it.height, density)
                                },
                            )
                        }

                        Spacer(Modifier.height(6.dp))

                        Text(
                            text = removeStrQuote(scoreInfo.first),
                            style = FontSizeNormal12(),
                            color = Color.White,
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            Spacer(Modifier.height(8.dp))

            Column {
                for (req in gradRequirement.value){
                    val charHsrProperties = character.characterStatus!!.characterProperties!!.find { it.attributeExchange == req.first }
                    val charValue = charHsrProperties?.valueFinal ?: 0f

                    Row(Modifier.padding(top = 4.dp, bottom = 4.dp)) {
                        Image(
                            painter = painterResource(req.first.attribute.iconWhite),
                            contentDescription = "Attribute Icon",
                            modifier = Modifier.size(24.dp).align(Alignment.CenterVertically)
                        )

                        Spacer(Modifier.width(8.dp))

                        Column {
                            //Grad Name and Value
                            Row(Modifier.fillMaxWidth().wrapContentHeight()) {
                                Text(
                                    text = removeStrQuote(req.first.attribute.resName),
                                    style = FontSizeNormal12(),
                                    color = Color.White
                                )
                                Spacer(Modifier.weight(1f))
                                Text(
                                    text = "${getDataDecimalsByAttrExchange(req.first, charValue)} / ${getDataDecimalsByAttrExchange(req.first, (req.second as Number).toFloat())}",
                                    style = FontSizeNormal12(),
                                    color = if(charValue >= (req.second as Number).toFloat()) GradReachYellow else Color.White
                                )
                            }
                            LinearProgressIndicator(
                                progress = charValue / (req.second as Number).toFloat(),
                                color = Color(0xCCFFFFFF),
                                backgroundColor = Color(0x66FFFFFF),
                                modifier = Modifier.fillMaxWidth().height(4.dp)
                            )
                        }
                    }
                }
            }



            Spacer(Modifier.height(8.dp))

            //Leaderboard Overview - 100 is example
            Text(
                text = if(overPercentage.value == -1f) {
                    removeStrQuote(Res.string.LackOfUserData)
                } else removeStrQuote(Res.string.OverWholeServerUser).replaceStrRes(
                    formatDecimal(overPercentage.value*100)
                ),
                style = FontSizeNormal16(),
                color = Color.White,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                text = removeStrQuote(Res.string.LeaderboardDataFrom),
                style = FontSizeNormalSmall(),
                color = Color(0x99FFFFFF),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(Modifier.height(8.dp))

            //Make by Stargazer
            Text(
                text = removeStrQuote(Res.string.ProducedByStargazer),
                style = FontSizeNormal12(),
                color = Color.White,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

        }
    }
}

@Composable
fun getDataDecimalsByAttrExchange(attrExchange: AttributeExchange, value: Float): String {
    return formatDecimal(
        value * if (attrExchange.isPercent == true) 100 else 1,
        if (attrExchange.key == "spd") 1 else if (attrExchange.isPercent == true) 1 else 0,
        isRoundDown = true
    ) + if (attrExchange.isPercent == true) "%" else ""
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RelicInfo(character: Character) {
    val relics = arrayOf(
        character.characterStatus!!.equippingRelicHead to 1,
        character.characterStatus!!.equippingRelicHands to 2,
        character.characterStatus!!.equippingRelicBody to 3,
        character.characterStatus!!.equippingRelicFeet to 4,
        character.characterStatus!!.equippingRelicPlanar to 5,
        character.characterStatus!!.equippingRelicLinkRope to 6
    )

    if(!relics.all { it.first == null }){
        val relicValidList = relics.filter { it.first != null }
        val selectedRelicIndex = remember { mutableStateOf(-1) }
        //Divider
        UserCharPageDivider()

        if(selectedRelicIndex.value == -1){

            Box(Modifier.fillMaxWidth().wrapContentHeight()) {

                VerticalGrid(
                    columns = SimpleGridCells.Fixed(2),
                    modifier = Modifier.widthIn(INFO_MIN_WIDTH, INFO_MAX_WIDTH).wrapContentHeight().align(Alignment.Center)
                ){
                    for ((it, _) in relicValidList.withIndex()){

                        val relic = relicValidList[it].first!!
                        val index = relicValidList[it].second

                        //val score = remember { mutableStateOf(0f) }
                        val relicSubAttr = relic.properties.subList(1, relic.properties.size)
                        Row(Modifier.wrapContentSize().padding(8.dp)) {
                            Box(Modifier.width(48.dp).wrapContentHeight().align(Alignment.CenterVertically)) {
                                RelicSmallCard(relic, index, onClick = { selectedRelicIndex.value = it })
                            }

                            Spacer(Modifier.width(4.dp))

                            Column(Modifier.wrapContentSize()) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    StatusShortUI(relic.properties[0], FontSizeNormal12(), isRelic = true)
                                    Spacer(Modifier.weight(1f).width(8.dp))
                                    /* Since Relic Score is not available anymore.
                                    Text(
                                        text = formatDecimal(score.value,1,
                                            isRoundDown = true),
                                        style = FontSizeNormal14(),
                                        color = Color.White
                                    )
                                     */
                                }

                                FlowRow(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    maxItemsInEachRow = 2
                                ) {
                                    for (status in relicSubAttr){
                                        Box(modifier = Modifier.fillMaxWidth().weight(1f)) {
                                            StatusShortUI(status, FontSizeNormal12(), isRelic = true)
                                        }
                                    }
                                }
                            }
                        }

                    }
                }
            }

        }else{
            Box(Modifier.fillMaxWidth()) {
                val relicPair = relicValidList[selectedRelicIndex.value]
                val relic = relicValidList[selectedRelicIndex.value].first!!

                Column(modifier = Modifier.fillMaxWidth(0.5f).wrapContentHeight().align(Alignment.Center), verticalArrangement = Arrangement.Center) {
                    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                        Box(Modifier.width(48.dp).wrapContentHeight().align(Alignment.CenterVertically)) {
                            RelicSmallCard(relic, relicPair.second, onClick = { selectedRelicIndex.value = -1 })
                        }
                    }

                    for (status in relic.properties){
                        StatusFullUI(status)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CharBioSkillInfo(character: Character, charNameBigHeight: MutableState<Int>) {
    val charStatus = character.characterStatus!!
    Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = Character.getCharacterItemFromJSON(character.officialId.toString()).displayName!!,
            style = FontSizeNormalLarge32(),
            color = Color.White,
            modifier = Modifier.onGloballyPositioned {
                charNameBigHeight.value = (it.size.height)
            }
        )

        Spacer(modifier = Modifier.size(6.dp))

        Row {
            repeat(character.rarity) {
                Image(
                    modifier = Modifier.size(12.dp, 12.dp),
                    painter = painterResource(Res.drawable.ui_icon_star),
                    contentScale = ContentScale.FillHeight,
                    contentDescription = "Stars to represent Rarity"
                )
            }
        }

        Spacer(modifier = Modifier.size(6.dp))

        Text(
            "Lv ${charStatus.characterLevel} · ${
                removeStrQuote(
                    Res.string.Eidolon
                )
            } ${charStatus.eidolon}",
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(2.dp)
                .background(Color(0x4D000000), RoundedCornerShape(49.dp))
                .clip(RoundedCornerShape(49.dp))
                .padding(top = 4.dp, bottom = 4.dp, start = 8.dp, end = 8.dp),
            style = FontSizeNormal12(),
            color = Color.White
        )

        //CombatType and Path
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.widthIn(INFO_MIN_WIDTH, INFO_MAX_WIDTH).wrapContentHeight()
        ) {
            Image(
                painter = painterResource(character.path.iconWhite),
                modifier = Modifier.size(24.dp).padding(end = 6.dp)
                    .align(Alignment.CenterVertically),
                contentDescription = "CombatType Icon"
            )
            Text(
                text = removeStrQuote(character.path.resName),
                style = FontSizeNormal16(),
                color = Color.White,
                textAlign = TextAlign.End,
                modifier = Modifier.align(Alignment.CenterVertically)
            )

            Box(modifier = Modifier.width(24.dp))

            Image(
                painter = painterResource(character.combatType.iconColor),
                modifier = Modifier.size(24.dp).padding(end = 6.dp)
                    .align(Alignment.CenterVertically),
                contentDescription = "CombatType Icon"
            )
            Text(
                text = removeStrQuote(character.combatType.resName),
                style = FontSizeNormal16(),
                color = Color.White,
                textAlign = TextAlign.End,
                modifier = Modifier.align(Alignment.CenterVertically)
            )

        }

        Spacer(Modifier.height(8.dp))

        //Skill Level
        val skillLvlList = arrayListOf(
            Res.string.TraceNormalATK to (charStatus.traceBasicAtkLevel),
            Res.string.TraceSkill to (charStatus.traceSkillLevel),
            Res.string.TraceUltimate to (charStatus.traceUltimateLevel),
            Res.string.TraceTalent to (charStatus.traceTalentLevel),
            Res.string.TraceTechnique to (1),
        )

        //Skill Icons
        Row(modifier = Modifier.widthIn(INFO_MIN_WIDTH, INFO_MAX_WIDTH).wrapContentHeight()) {
            for (skill in skillLvlList) {
                Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                    AsyncImage(
                        model = newImageRequest(
                            LocalPlatformContext.current,
                            getAssetsURLByFileName(
                                ImageFolder.CHAR_SKILL,
                                "${getImageNameByRegistName(character.registName!!, isCharNoGen = true)}_skill" + when(skill.first) {
                                    Res.string.TraceNormalATK -> "1"
                                    Res.string.TraceSkill -> "2"
                                    Res.string.TraceUltimate -> "3"
                                    Res.string.TraceTalent -> "4"
                                    Res.string.TraceTechnique -> "6"
                                    else -> "1"
                                }
                            )
                        ),
                        contentDescription = "Skill Icon",
                        modifier = Modifier.size(36.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        modifier = Modifier.padding(top = 2.dp),
                        text = removeStrQuote(skill.first),
                        style = FontSizeNormal14(),
                        color = Color.White
                    )
                    Text(
                        text = "Lv ${skill.second}",
                        style = FontSizeNormal14(),
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }

        //Status Info
        val showFullStatus = remember { mutableStateOf(false) }
        if(charStatus.characterProperties != null){
            val statusList = charStatus.characterProperties!!.filter { it.valueFinal > 0 }
            FlowRow(modifier = Modifier
                .clickable { showFullStatus.value = !showFullStatus.value }
                .widthIn(INFO_MIN_WIDTH, INFO_MAX_WIDTH).wrapContentHeight(),
                horizontalArrangement = Arrangement.Center
            ) {
                for (status in statusList){
                    if(showFullStatus.value){
                        StatusFullUI(status)
                    }else{
                        Box(modifier = Modifier.wrapContentWidth()){
                            StatusShortUI(status)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LightconeInfo(character: Character){
    val charStatus = character.characterStatus!!
    val isLightconeShowDesc = remember { mutableStateOf(false) }
    if (charStatus.equippingLightcone != null && charStatus.equippingLightcone!!.registName != null && charStatus.equippingLightcone!!.registName != "Unknown") {
        //Divider
        UserCharPageDivider()

        val lightcone = charStatus.equippingLightcone!!
        val lcInfoJson : JsonElement by rememberSaveable(stateSaver = JsonElementSaver) { mutableStateOf(Lightcone.getLightconeDataFromJSON(lightcone.fileName!!, Language.TextLanguageInstance) as JsonElement) }

        Box(modifier = Modifier.fillMaxWidth().wrapContentHeight()){
            Row(Modifier.widthIn(INFO_MIN_WIDTH, INFO_MAX_WIDTH).wrapContentHeight().align(Alignment.Center).clickable { isLightconeShowDesc.value = !isLightconeShowDesc.value }) {
                Box(Modifier.requiredHeight(120.dp).weight(0.4f), contentAlignment = Alignment.Center) {
                    val context = LocalPlatformContext.current

                    AsyncImage(
                        model = newImageRequest(
                            context,
                            getAssetsURLByFileName(
                                ImageFolder.LC_ARTWORK,
                                getImageNameByRegistName(lightcone.registName!!)
                            )
                        ),
                        contentDescription = "Lightcone Image",
                        modifier = Modifier.height(if (isLightconeShowDesc.value) 100.dp else 112.dp).wrapContentWidth().rotate(if (isLightconeShowDesc.value) 0f else 5f)
                            .border(4.dp, Color.White, RectangleShape).align(
                                Alignment.Center
                            ),
                        contentScale = ContentScale.Fit,
                    )
                }

                Spacer(Modifier.width(16.dp))

                Column (Modifier.weight(0.6f)){
                    Text(
                        text = lightcone.displayName!!,
                        modifier = Modifier.padding(2.dp),
                        style = FontSizeNormal20(),
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                    )
                    Text(
                        text = "Lv ${lightcone.level} · ${
                            removeStrQuote(
                                Res.string.Superimpose
                            ).replace("$"+"{1}",lightcone.superimposition.toString())
                        }",
                        modifier = Modifier.padding(top = 2.dp, bottom = 2.dp, start = 8.dp, end = 8.dp)
                            .background(Color(0x4D000000), RoundedCornerShape(49.dp))
                            .clip(RoundedCornerShape(49.dp)).padding(4.dp),
                        style = FontSizeNormal12(),
                        color = Color.White
                    )

                    if(isLightconeShowDesc.value && lcInfoJson.jsonObject["skill"] != null){
                        val richTextState = rememberRichTextState()
                        richTextState.setHtml(getLightconeMetaInfo(lightcone, lcInfoJson, lightcone.superimposition))

                        RichText(richTextState, color = Color(0xFFFFFFFF), style = FontSizeNormal14())
                    }else{
                        Row {
                            repeat(lightcone.rarity) {
                                Image(
                                    modifier = Modifier.size(12.dp, 12.dp),
                                    painter = painterResource(Res.drawable.ui_icon_star),
                                    contentScale = ContentScale.FillHeight,
                                    contentDescription = "Stars to represent Rarity"
                                )
                            }
                        }

                        Row{
                            Image(
                                painter = painterResource(character.path.iconWhite),
                                modifier = Modifier.size(24.dp).padding(end = 6.dp)
                                    .align(Alignment.CenterVertically),
                                contentDescription = "CombatType Icon"
                            )
                            Text(
                                text = removeStrQuote(character.path.resName),
                                style = FontSizeNormal16(),
                                color = Color.White,
                                textAlign = TextAlign.End,
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )
                        }

                        //Get Lightcone Status
                        val lcAttrData = getLcAttrData(lcInfoJson, lightcone.level)
                        val lcAttrDataArr = arrayOf(Res.drawable.ic_hp to lcAttrData.hp, Res.drawable.ic_atk to lcAttrData.atk, Res.drawable.ic_def to lcAttrData.def)
                        FlowRow(horizontalArrangement = Arrangement.Center) {
                            for (attr in lcAttrDataArr){
                                Row(
                                    Modifier.wrapContentWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Image(
                                        painter = painterResource(attr.first),
                                        contentDescription = "Attribute Icon",
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Text(
                                        text = formatDecimal(attr.second, 0,
                                            isRoundDown = true),
                                        style = FontSizeNormal14(),
                                        color = Color.White,
                                        maxLines = 1
                                    )
                                }
                            }
                        }
                    }
                }

            }
        }
    }
}

fun getLightconeMetaInfo(lightcone: Lightcone, lcInfoJson: JsonElement, metaLv: Int): String {
    val paramsList : ArrayList<Float> = arrayListOf()
    val levelData = lcInfoJson.jsonObject["skill"]!!.jsonObject["levelData"]
    if (levelData != null && levelData.jsonArray.size >= (metaLv)){
        for(param in levelData.jsonArray[metaLv -1].jsonObject["params"]!!.jsonArray){
            paramsList.add(param.jsonPrimitive.float)
        }
    }

    return htmlDescApplier(
        lcInfoJson.jsonObject["skill"]!!.jsonObject["descHash"]!!.jsonPrimitive.content,
        paramsList
    )
}

@Composable
fun UserCharPageDivider() {
    Box(Modifier.fillMaxWidth().padding(top = 12.dp, bottom = 12.dp), contentAlignment = Alignment.Center) {
        Box(modifier = Modifier.fillMaxWidth(0.33f).height(1.dp).background(Color(0x66F3F9FF)),)
    }
}

@Composable
fun StatusShortUI(
    status: HsrProperties,
    textStyle: TextStyle = FontSizeNormal14(),
    isRelic: Boolean = false,
){
    Row(
        Modifier.wrapContentWidth().padding(if(isRelic) 0.dp else 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = painterResource(status.attributeExchange.attribute.iconWhite),
            contentDescription = "Attribute Icon",
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = (if(isRelic) "+" else "") + formatDecimal(
                status.valueFinal * if (status.attributeExchange.isPercent == true) 100 else 1,
                if (status.attributeExchange.key == "spd") 1 else if (status.attributeExchange.isPercent == true) 1 else 0,
                isRoundDown = true
            ) + if (status.attributeExchange.isPercent == true) "%" else "",
            style = textStyle,
            color = Color.White,
            maxLines = 1
        )
    }
}
@Composable
fun StatusFullUI(status : HsrProperties){
    Row(Modifier.fillMaxWidth().wrapContentHeight().padding(top = 4.dp, bottom = 4.dp)) {
        Image(
            painter = painterResource(status.attributeExchange.attribute.iconWhite),
            contentDescription = "Attribute Icon",
            modifier = Modifier.size(24.dp)
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = removeStrQuote(status.attributeExchange.attribute.resName),
            style = FontSizeNormal14(),
            color = Color.White,
            maxLines = 1
        )

        Spacer(Modifier.weight(1f))

        if(status.valueBase == 0f && status.valueAdd == 0f){
            Text(
                text = "+" + formatDecimal(
                    status.valueFinal * if (status.attributeExchange.isPercent == true) 100 else 1,
                    if (status.attributeExchange.key == "spd") 1 else if (status.attributeExchange.isPercent == true) 1 else 0,
                    isRoundDown = true
                ) + if (status.attributeExchange.isPercent == true) "%" else "",
                style = FontSizeNormal14(),
                color = Color.White,
                maxLines = 1
            )
        }else{
            if(status.valueBase > 0f){
                Text(
                    text = formatDecimal(
                        status.valueBase * if (status.attributeExchange.isPercent == true) 100 else 1,
                        if (status.attributeExchange.key == "spd") 1 else if (status.attributeExchange.isPercent == true) 1 else 0,
                        isRoundDown = true,
                    ) + if (status.attributeExchange.isPercent == true) "%" else "",
                    style = FontSizeNormal14(),
                    color = Color.White,
                    maxLines = 1
                )
            }

            if(status.valueAdd > 0f && status.valueBase > 0f){
                Text(
                    text = "+",
                    style = FontSizeNormal14(),
                    color = Color.White,
                    maxLines = 1,
                    modifier = Modifier.padding(end = 4.dp, start = 4.dp)
                )
            }

            if(status.valueAdd > 0f){
                Text(
                    text = formatDecimal(
                        status.valueAdd * if (status.attributeExchange.isPercent == true) 100 else 1,
                        if (status.attributeExchange.key == "spd") 1 else if (status.attributeExchange.isPercent == true) 1 else 0,
                        isRoundDown = true
                    ) + if (status.attributeExchange.isPercent == true) "%" else "",
                    style = FontSizeNormal14(),
                    color = AdditionalGreen,
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
fun CharacterInfoFadeImg(
    modifier: Modifier = Modifier,
    fileName: String,
    isScrollMode: Boolean = true,
) {
    val alpha by animateFloatAsState(targetValue = if (isScrollMode) 0.4f else 1f, animationSpec = tween(1000))
    //val alpha by animateFloatAsState(targetValue = if (isScrollMode) 0.4f else 1f, animationSpec = tween(500))
    Box(modifier = Modifier.fillMaxSize()) {
        AsyncImage(
            model = newImageRequest(context = LocalPlatformContext.current, data = Character.getCharacterImageFromFileName(
                ImageFolder.CHAR_FADE, fileName
            )),
            contentDescription = "Character Full Image",
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxWidth().wrapContentHeight().align(Alignment.TopCenter).alpha(alpha)
            //imageLoader = UtilTools().newImageLoader(LocalPlatformContext.current)
        )
        Box(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.5f).background(
                Brush.verticalGradient(
                    colors = listOf(Color(0x00000000), Color(0xCC000000))
                )
            ).align(Alignment.BottomCenter),
        )

    }
}

fun getProfRankResult(score: Float, character: Character, schoolIndex: Int, uid: String): Float {
    return StarbaseAPI().getSpecificUserCharProfOver(character.officialId!!, schoolId = schoolIndex, myScore = score, uid = uid)
}