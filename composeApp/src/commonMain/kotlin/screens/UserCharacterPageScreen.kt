package screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichText
import com.voc.honkai_stargazer.component.RelicSmallCard
import components.DropdownMenuNoPadding
import components.HeaderData
import components.NonLazyGrid
import components.PAGE_HEADER_ALPHA_HEIGHT
import components.PageHeaderAlpha
import components.defaultHeaderData
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import files.CharRank
import files.CharScore
import files.Eidolon
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
import moe.tlaster.precompose.navigation.BackStackEntry
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.query
import org.jetbrains.compose.resources.painterResource
import types.Character
import types.Constants
import types.HsrProperties
import types.Lightcone
import types.UserAccount
import utils.AdditionalGreen
import utils.FontSizeNormal12
import utils.FontSizeNormal14
import utils.FontSizeNormal16
import utils.FontSizeNormal20
import utils.FontSizeNormalLarge24
import utils.FontSizeNormalLarge32
import utils.FontSizeNormalSmall
import utils.GradReachYellow
import utils.UtilTools
import utils.annotation.DoItLater
import utils.calculator.getCharRange
import utils.calculator.getCharScore
import utils.calculator.getGradAttrAndValue
import utils.calculator.getLcAttrData
import utils.hoyolab.AttributeExchange

@DoItLater("Confirm that work when charStatus is null")
@Composable
fun UserCharacterPageScreen(
    modifier: Modifier = Modifier,
    navigator: Navigator,
    headerData: HeaderData = defaultHeaderData,
    backStackEntry: BackStackEntry,
) {
    val uid = backStackEntry.query<String>("uid")!!
    val userAccount by remember { mutableStateOf(
        if (UserAccount.INSTANCE.uid == uid) {
            UserAccount.INSTANCE
        } else {
            UserAccount.UIDSEARCH
        }
    ) }
    val characterId = backStackEntry.query<Int>("charId")!!
    val characterFilter = userAccount.characterList.filter { it.officialId == characterId }
    val hazeState = remember { HazeState() }
    val listState = rememberLazyListState()
    val isScrolling by remember {
        derivedStateOf {
            // whatever logic you need
            listState.canScrollBackward
        }
    }
    val character = if(characterFilter.isEmpty()) null else characterFilter[0]
    val isShare = remember { mutableStateOf(false) }

    if(character == null){ navigator.popBackStack() }else{
        Box(modifier = modifier
            .fillMaxSize()
        ){
            CharacterInfoFadeImg(
                fileName = character.registName!!,
                isVisible = !isScrolling //alpha = scrollToAlpha
            )

            LazyColumn(
                state = listState,
                modifier = Modifier.padding(
                    start = Constants.SCREEN_SAVE_PADDING,
                    end = Constants.SCREEN_SAVE_PADDING
                ).haze(hazeState)
            ) {
                item { Spacer(Modifier.statusBarsPadding().height(PAGE_HEADER_ALPHA_HEIGHT + 240.dp)) }
                item { CharBioSkillInfo(character) }
                item { LightconeInfo(character) }
                item { RelicInfo(character) }
                item { ProficientScoreInfo(character) }

                item { Spacer(Modifier.statusBarsPadding()) }
            }


            PageHeaderAlpha(
                navigator = navigator,
                onForward = {
                    //TODO : Remember to add the Share Function
                },
                forwardIconId = Res.drawable.ui_icon_share,
                hazeState = hazeState,

            ) {
                AnimatedVisibility(
                    visible = !isScrolling,
                    enter = fadeIn(),
                    exit = fadeOut(),
                    modifier = Modifier.fillMaxWidth().wrapContentHeight().align(Alignment.TopCenter)
                ) {
                    Column(Modifier.fillMaxSize()) {
                        Text(
                            userAccount.username,
                            modifier = Modifier.align(Alignment.CenterHorizontally).padding(2.dp),
                            style = FontSizeNormal16(),
                            color = Color.White
                        )

                        Text(
                            text = "${userAccount.uid}·${
                                UtilTools().removeStringResDoubleQuotes(
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
            }
        }
    }


}

@Composable
fun ProficientScoreInfo(character: Character) {

    //Divider
    UserCharPageDivider()

    val isExpandSchoolDropdown = remember { mutableStateOf(false) }
    val schoolDataNameArray = arrayListOf("默認流派")
    val schoolIndex = remember { mutableStateOf(0) }
    val optionTextViewSize = remember { mutableStateOf(IntSize.Zero) }
    val density = LocalDensity.current.density

    val charScore = getCharScore(character, schoolIndex.value)
    val scoreInfoList = arrayListOf(
        Res.string.CharScore to charScore,
        Res.string.CharRank to getCharRange(charScore),
        //Res.string.RelicScore to 123.4f,
        //Res.string.RelicRank to "B",
    )

    val gradRequirement = getGradAttrAndValue(character, schoolIndex.value)

    Column {
        //Title and Spinner
        Row {
            Text(
                text = UtilTools().removeStringResDoubleQuotes(Res.string.ScoreLevel),
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
                        .width(UtilTools().pxToDp(optionTextViewSize.value.width, density)),
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
        Row(Modifier.fillMaxWidth().wrapContentHeight()) {
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
                            text = if(scoreInfo.second is String) {Constants.getScoreRankingFont(scoreInfo.second as String).toString()} else UtilTools().formatDecimal(scoreInfo.second as Number, 1 ,
                                isRoundDown = true),
                            style = FontSizeNormalLarge24(),
                            color = Color.White,
                            maxLines = 1,
                            modifier = Modifier.align(Alignment.CenterHorizontally).onSizeChanged {
                                txt24Height.value = UtilTools().pxToDp(it.height, density)
                            },
                        )
                    }

                    Spacer(Modifier.height(6.dp))

                    Text(
                        text = UtilTools().removeStringResDoubleQuotes(scoreInfo.first),
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
            for (req in gradRequirement){
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
                                text = UtilTools().removeStringResDoubleQuotes(req.first.attribute.resName),
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
            text = UtilTools().removeStringResDoubleQuotes(Res.string.OverWholeServerUser).replace("$"+"{1}", "-100.0"),
            style = FontSizeNormal16(),
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = UtilTools().removeStringResDoubleQuotes(Res.string.LeaderboardDataFrom),
            style = FontSizeNormalSmall(),
            color = Color(0x99FFFFFF),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(Modifier.height(8.dp))

        //Make by Stargazer
        Text(
            text = UtilTools().removeStringResDoubleQuotes(Res.string.ProducedByStargazer),
            style = FontSizeNormal12(),
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

    }
}

@Composable
fun getDataDecimalsByAttrExchange(attrExchange: AttributeExchange, value: Float): String {
    return UtilTools().formatDecimal(
        value * if (attrExchange.isPercent == true) 100 else 1,
        if (attrExchange.key == "spd") 1 else if (attrExchange.isPercent == true) 1 else 0,
        isRoundDown = true
    ) + if (attrExchange.isPercent == true) "%" else ""
}

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
            NonLazyGrid(
                columns = 2,
                modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                itemCount = relicValidList.size
            ) {
                val relic = relicValidList[it].first!!
                val index = relicValidList[it].second

                val score = remember { mutableStateOf(0f) }
                val relicSubAttr = relic.properties.subList(1, relic.properties.size)
                Row(Modifier.fillMaxWidth().wrapContentHeight().padding(8.dp)) {
                    Box(Modifier.width(48.dp).wrapContentHeight()) {
                        RelicSmallCard(relic, index, onClick = { selectedRelicIndex.value = it })
                    }
                    Spacer(Modifier.width(4.dp))
                    Column(Modifier.weight(1f).wrapContentHeight()) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            StatusShortUI(relic.properties[0], FontSizeNormal12(), isRelic = true)
                            Spacer(Modifier.width(8.dp).weight(1f))
                            Text(
                                text = UtilTools().formatDecimal(score.value,1,
                                    isRoundDown = true),
                                style = FontSizeNormal14(),
                                color = Color.White
                            )
                        }
                        NonLazyGrid(columns = 2, itemCount = relicSubAttr.size){
                                subAttrIndex -> StatusShortUI(relicSubAttr[subAttrIndex], FontSizeNormalSmall(), isRelic = true)
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

@Composable
fun CharBioSkillInfo(character: Character) {
    val charStatus = character.characterStatus!!
    Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = Character.getCharacterItemFromJSON(character.officialId.toString()).displayName!!,
            style = FontSizeNormalLarge32(),
            color = Color.White
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
                UtilTools().removeStringResDoubleQuotes(
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
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(character.path.iconWhite),
                modifier = Modifier.size(24.dp).padding(end = 6.dp)
                    .align(Alignment.CenterVertically),
                contentDescription = "CombatType Icon"
            )
            Text(
                text = UtilTools().removeStringResDoubleQuotes(character.path.resName),
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
                text = UtilTools().removeStringResDoubleQuotes(character.combatType.resName),
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
        Row {
            for (skill in skillLvlList) {
                Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                    Image(
                        bitmap = UtilTools().getAssetsWebpByFileName(
                            UtilTools.ImageFolderType.CHAR_SKILL,
                            "${UtilTools().getImageNameByRegistName(character.registName!!, isCharNoGen = true)}_skill" + when(skill.first) {
                                Res.string.TraceNormalATK -> "1"
                                Res.string.TraceSkill -> "2"
                                Res.string.TraceUltimate -> "3"
                                Res.string.TraceTalent -> "4"
                                Res.string.TraceTechnique -> "6"
                                else -> "1"
                            }),
                        contentDescription = "Skill Icon",
                        modifier = Modifier.size(36.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        modifier = Modifier.padding(top = 2.dp),
                        text = UtilTools().removeStringResDoubleQuotes(skill.first),
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
            Column(Modifier.clickable {
                showFullStatus.value = !showFullStatus.value
            }) {
                for ((index, status) in statusList.withIndex()){
                    if(showFullStatus.value){
                        StatusFullUI(status)
                    }else if (index % 5 == 0) {
                        Row(
                            Modifier.fillMaxWidth().wrapContentHeight(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            for (i in 0 until 5) {
                                if (index + i < statusList.size) {
                                    StatusShortUI(statusList[index + i])
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LightconeInfo(character: Character){
    val charStatus = character.characterStatus!!
    val isLightconeShowDesc = remember { mutableStateOf(false) }
    if (charStatus.equippingLightcone != null && charStatus.equippingLightcone!!.registName != null) {
        //Divider
        UserCharPageDivider()

        val lightcone = charStatus.equippingLightcone!!
        val lcInfoJson = Lightcone.getLightconeDataFromJSON(lightcone.fileName!!)
        Row(Modifier.fillMaxWidth().wrapContentHeight().clickable { isLightconeShowDesc.value = !isLightconeShowDesc.value }) {
            Box(Modifier.requiredHeight(120.dp).weight(0.4f), contentAlignment = Alignment.Center) {
                    val context = LocalPlatformContext.current
                    val imageRequest = remember { UtilTools().newImageRequest(
                        context = context,
                        data = UtilTools().getAssetsWebpByteArrayByFileName(
                            UtilTools.ImageFolderType.LC_ARTWORK,
                            UtilTools().getImageNameByRegistName(lightcone.registName!!)
                        ))
                    }
                    val imageLoader = remember { UtilTools().newImageLoader(context) }
                    AsyncImage(
                        model = imageRequest,
                        contentDescription = "Lightcone Image",
                        modifier = Modifier.height(if (isLightconeShowDesc.value) 100.dp else 112.dp).wrapContentWidth().rotate(if (isLightconeShowDesc.value) 0f else 5f)
                            .border(4.dp, Color.White, RectangleShape).align(
                                Alignment.Center
                            ),
                        contentScale = ContentScale.Fit,
                        imageLoader = imageLoader
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
                        UtilTools().removeStringResDoubleQuotes(
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
                            text = UtilTools().removeStringResDoubleQuotes(character.path.resName),
                            style = FontSizeNormal16(),
                            color = Color.White,
                            textAlign = TextAlign.End,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }

                    //Get Lightcone Status
                    val lcAttrData = getLcAttrData(lcInfoJson, lightcone.level)
                    val lcAttrDataArr = arrayOf(Res.drawable.ic_hp to lcAttrData.hp, Res.drawable.ic_atk to lcAttrData.atk, Res.drawable.ic_def to lcAttrData.def)
                    Row {
                        for ((index, attr) in lcAttrDataArr.withIndex()){
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
                                    text = UtilTools().formatDecimal(attr.second, 0,
                                        isRoundDown = true),
                                    style = FontSizeNormal14(),
                                    color = Color.White,
                                    maxLines = 1
                                )
                                if(index < lcAttrDataArr.size - 1){
                                    Spacer(Modifier.width(8.dp))
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

    return UtilTools().htmlDescApplier(
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
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(status.attributeExchange.attribute.iconWhite),
            contentDescription = "Attribute Icon",
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = (if(isRelic) "+" else "") + UtilTools().formatDecimal(
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
            text = UtilTools().removeStringResDoubleQuotes(status.attributeExchange.attribute.resName),
            style = FontSizeNormal14(),
            color = Color.White,
            maxLines = 1
        )

        Spacer(Modifier.weight(1f))

        if(status.valueBase == 0f && status.valueAdd == 0f){
            Text(
                text = "+" + UtilTools().formatDecimal(
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
                    text = UtilTools().formatDecimal(
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
                    text = UtilTools().formatDecimal(
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
    isVisible: Boolean = true
) {
    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.fillMaxWidth().wrapContentHeight().align(Alignment.TopCenter)
        ) {
            Image(
                /*
                model = UtilTools().newImageRequest(context = LocalPlatformContext.current, data = Character.getCharacterImageByteArrayFromFileName(
                    UtilTools.ImageFolderType.CHAR_FADE, fileName
                )),
                 */
                bitmap = Character.getCharacterImageFromFileName(
                    UtilTools.ImageFolderType.CHAR_FADE, fileName
                ),
                contentDescription = "Character Full Image",
                contentScale = ContentScale.Fit,
                //imageLoader = UtilTools().newImageLoader(LocalPlatformContext.current)
            )
        }
        Box(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.5f).background(
                Brush.verticalGradient(
                    colors = listOf(Color(0x00000000), Color(0xCC000000))
                )
            ).align(Alignment.BottomCenter),
        )

    }
}
