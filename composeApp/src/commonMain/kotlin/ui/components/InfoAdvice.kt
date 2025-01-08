package ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.cheonjaeung.compose.grid.SimpleGridCells
import com.cheonjaeung.compose.grid.VerticalGrid
import files.AdviceCharacters
import files.AdviceLightcones
import files.AdviceRelics
import files.AdviceTeams
import files.MainAffix
import files.NoDataYet
import files.RelicPropBody
import files.RelicPropFeet
import files.RelicPropLinkRope
import files.RelicPropPlanarSphere
import files.Res
import files.SubAffix
import files.ic_add_icon
import files.ic_arrow_left_page
import files.ic_arrow_right_page
import files.phorphos_baseball_cap_regular
import files.phorphos_person_regular
import files.phorphos_sword_regular
import files.pom_pom_failed_issue
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.jetbrains.compose.resources.painterResource
import types.Attribute
import types.Character
import types.Lightcone
import types.Relic
import utils.annotation.YouMustKiddingMe
import utils.app.CharWeightList
import utils.app.Constants
import utils.app.Constants.Companion.CHAR_CARD_WIDTH
import utils.app.Constants.Companion.SCREEN_SAVE_PADDING
import utils.app.FontSizeNormal14
import utils.app.FontSizeNormal16
import utils.app.Language
import utils.app.TextColorNormalDim
import utils.app.TextColorNormalDimCC
import utils.app.pxToDp
import utils.app.removeStrQuote

@Composable
fun InfoAdviceLightcone(charWeightData : JsonObject? = null) {
    Column(modifier = Modifier.fillMaxWidth().statusBarsPadding().padding(start = SCREEN_SAVE_PADDING, end = SCREEN_SAVE_PADDING)){
        TitleHeader(iconRId = Res.drawable.phorphos_sword_regular, titleRId = Res.string.AdviceLightcones)

        //Empty Blank
        Spacer(modifier = Modifier.height(24.dp))

        if(charWeightData == null) return@Column
        val adviceList = charWeightData.jsonObject["advice_lightcone"]?.jsonArray?.filter { lc -> lc.jsonPrimitive.int != -1 }
        val normalList = charWeightData.jsonObject["normal_lightcone"]?.jsonArray?.filter { lc -> lc.jsonPrimitive.int != -1 }

        //Content
        if(!(adviceList.isNullOrEmpty() || normalList.isNullOrEmpty())){
            //Show of recommend Lightcones
            LazyRow(
                state = rememberLazyListState(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                //Most Advice To Equip
                if(adviceList.isNotEmpty()){
                    for(lcItem in adviceList){
                        val officialLcId = lcItem.jsonPrimitive.int
                        if (officialLcId == -1) continue
                        val lightcone = Lightcone.getLightconeItemFromJSON(officialLcId.toString(), textLanguage = Language.TextLanguageInstance)

                        item{
                            Box(Modifier.size(Constants.LC_CARD_WIDTH, (Constants.LC_CARD_HEIGHT+Constants.LC_CARD_TITLE_HEIGHT))){
                                LightconeCard(lightcone)
                            }
                        }
                    }
                }
                //Still Can Use If Only Have
                if (normalList.isNotEmpty()){
                    for(lcItem in normalList){
                        val officialLcId = lcItem.jsonPrimitive.int
                        if (officialLcId == -1) continue
                        val lightcone = Lightcone.getLightconeItemFromJSON(officialLcId.toString(), textLanguage = Language.TextLanguageInstance)

                        item{
                            Box(Modifier.size(Constants.LC_CARD_WIDTH, (Constants.LC_CARD_HEIGHT+Constants.LC_CARD_TITLE_HEIGHT))){
                                LightconeCard(lightcone)
                            }
                        }
                    }
                }
            }
        }else{
            InfoCannotFind()
        }
    }

}


@YouMustKiddingMe
@Composable
fun InfoAdviceRelic(charWeightData : JsonObject? = null) {
    val relicWidth = remember { mutableStateOf(Constants.RELIC_CARD_WIDTH) }
    val relicHeight = remember { mutableStateOf(Constants.RELIC_CARD_HEIGHT + Constants.RELIC_CARD_TITLE_HEIGHT) }
    val density = LocalDensity.current.density
    val relicPart = arrayListOf(Res.string.RelicPropBody,Res.string.RelicPropFeet,Res.string.RelicPropPlanarSphere, Res.string.RelicPropLinkRope)
    //val relicPartShort = arrayListOf(Res.string.RelicPropBodyShort, Res.string.RelicPropFeetShort,Res.string.RelicPropPlanarSphereShort, Res.string.RelicPropLinkRopeShort)
    val relicList : ArrayList<Pair<Relic,Relic>> = arrayListOf()
    val ornamentList : ArrayList<Relic> = arrayListOf()
    val relicSelectIndex = remember { mutableStateOf(0) }
    val ornamentSelectIndex = remember { mutableStateOf(0) }

    val adviceAttrList : ArrayList<Pair<String, Attribute>> = arrayListOf() //["ATTR_DEF","ATTR_SPD", "ATTR_SPD", "ATTR_SPD"] //must be 4 since 1 option for that index's relic/ornament
    val adviceAttrSubList : ArrayList<Attribute> = arrayListOf() //As much as it provide, mostly 2-4

    if(charWeightData != null){
        for(relicSets in charWeightData.jsonObject["advice_relic"]!!.jsonArray){
            if(relicSets is JsonArray && relicSets.jsonArray.size >= 2) {
                val relicId1 = relicSets.jsonArray[0].jsonPrimitive.int
                val relicId2 = relicSets.jsonArray[1].jsonPrimitive.int

                if (relicId1 == -1 || relicId2 == -1) continue

                val relic1 = Relic.getRelicItemFromJSON(relicId1.toString(), Language.TextLanguageInstance)
                val relic2 = Relic.getRelicItemFromJSON(relicId2.toString(), Language.TextLanguageInstance)

                relicList.add(relic1 to relic2)
            }
        }

        for(ornament in charWeightData.jsonObject["advice_ornament"]!!.jsonArray){
            if (ornament.jsonPrimitive.int == -1) continue
            ornamentList.add(Relic.getRelicItemFromJSON(ornament.jsonPrimitive.int.toString(), Language.TextLanguageInstance))
        }

        //Must rewrite if later extend to multi choices of one relic index
        for(adviceAttr in charWeightData.jsonObject["advice_relic_attr"]!!.jsonArray){
            if (adviceAttr !is JsonObject
                || adviceAttr.jsonObject["propertyName"] == null
                || adviceAttr.jsonObject["propertyName"]!!.jsonPrimitive.content == ""
                || adviceAttr.jsonObject["relicType"] == null
                || adviceAttr.jsonObject["relicType"]!!.jsonPrimitive.content == "") {
                continue
            } else {
                adviceAttrList.add(
                    adviceAttr.jsonObject["relicType"]!!.jsonPrimitive.content
                            to Attribute.valueOf(adviceAttr.jsonObject["propertyName"]!!.jsonPrimitive.content))

            }
        }
        for(adviceAttr in charWeightData.jsonObject["advice_relic_sub"]!!.jsonArray){
            if(adviceAttr.jsonPrimitive.content == "") continue
            adviceAttrSubList.add(Attribute.valueOf(adviceAttr.jsonPrimitive.content))
        }
    }

    Column(modifier = Modifier
        .wrapContentSize()
        .widthIn(Constants.INFO_MIN_WIDTH, Constants.INFO_MAX_WIDTH)
        .padding(start = SCREEN_SAVE_PADDING, end = SCREEN_SAVE_PADDING)
    ){
        TitleHeader(iconRId = Res.drawable.phorphos_baseball_cap_regular, titleRId = Res.string.AdviceRelics)

        //Empty Blank
        Spacer(modifier = Modifier.height(24.dp))

        //Content
        Column(modifier = Modifier.wrapContentSize().align(Alignment.CenterHorizontally)){
            if(charWeightData != null && relicList.size > 0){
                Row(
                    Modifier.wrapContentWidth().widthIn(Constants.INFO_MIN_WIDTH, Constants.INFO_MAX_WIDTH),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    //Relic
                    Image(
                        painterResource(Res.drawable.ic_arrow_left_page),
                        modifier = Modifier.size(24.dp)
                            .padding(bottom = Constants.ADVICE_RELIC_BAR_HEIGHT)
                            .alpha(if (relicList.size > 1) 1f else 0.2f)
                            .clickable(enabled = true, indication = null, interactionSource = MutableInteractionSource(), onClick = {
                                relicSelectIndex.value = (relicSelectIndex.value + relicList.size + 1) % relicList.size
                            }),
                        contentDescription = "Relic Left Button"
                    )
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.wrapContentWidth().weight(2f)
                    ) {
                        //Bar that show how many pages can show
                        Row(modifier = Modifier.wrapContentWidth()) {
                            for ((index) in relicList.withIndex()) {
                                Box(
                                    modifier = Modifier
                                        .height(Constants.ADVICE_RELIC_BAR_HEIGHT)
                                        .width(if (relicSelectIndex.value == index) Constants.ADVICE_RELIC_SELECTED_BAR_WIDTH else Constants.ADVICE_RELIC_UNSELECT_BAR_WIDTH)
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(
                                            if (relicSelectIndex.value == index) Color(
                                                0x66FFFFFF
                                            ) else Color(0x33FFFFFF)
                                        )
                                )
                                if (index < relicList.size - 1) {
                                    Spacer(Modifier.width(4.dp))
                                }
                            }
                        }

                        Spacer(Modifier.height(10.dp))

                        //Relic Set
                        Row {
                            Box(
                                modifier = Modifier.weight(1f)
                            ) {
                                RelicCard(
                                    relicList[relicSelectIndex.value].first,
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Box(
                                modifier = Modifier.weight(1f)
                                    .onSizeChanged {
                                        relicWidth.value = pxToDp(it.width, density)
                                        relicHeight.value = pxToDp(it.height, density)
                                    }
                            ) {
                                RelicCard(
                                    relicList[relicSelectIndex.value].second,
                                )
                            }
                        }

                    }
                    Image(
                        painterResource(Res.drawable.ic_arrow_right_page),
                        modifier = Modifier.size(24.dp)
                            .padding(bottom = Constants.ADVICE_RELIC_BAR_HEIGHT)
                            .alpha(if (relicList.size > 1) 1f else 0.2f)
                            .clickable(enabled = true, indication = null, interactionSource = MutableInteractionSource(), onClick = {
                                relicSelectIndex.value = (relicSelectIndex.value + 1) % relicList.size
                            }),
                        contentDescription = "Relic Right Button"
                    )

                    Image(
                        painterResource(Res.drawable.ic_add_icon),
                        modifier = Modifier.size(24.dp),
                        contentDescription = "Add Icon"
                    )

                    //Ornament Left Button
                    Image(
                        painterResource(Res.drawable.ic_arrow_left_page),
                        modifier = Modifier.size(24.dp)
                            .padding(bottom = Constants.ADVICE_RELIC_BAR_HEIGHT)
                            .alpha(if (ornamentList.size > 1) 1f else 0.2f)
                            .clickable(enabled = true, indication = null, interactionSource = MutableInteractionSource(), onClick = {
                                ornamentSelectIndex.value = (ornamentSelectIndex.value + ornamentList.size + 1) % ornamentList.size
                            }),
                        contentDescription = "Ornament Left Button"
                    )

                    //Ornament Cards & Headers
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.wrapContentWidth().weight(1f)
                    ) {
                        //Bar that show how many pages can show
                        Row(modifier = Modifier.wrapContentWidth()) {
                            for ((index) in ornamentList.withIndex()) {
                                Box(
                                    modifier = Modifier
                                        .height(Constants.ADVICE_RELIC_BAR_HEIGHT)
                                        .width(if (ornamentSelectIndex.value == index) Constants.ADVICE_RELIC_SELECTED_BAR_WIDTH else Constants.ADVICE_RELIC_UNSELECT_BAR_WIDTH)
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(
                                            if (ornamentSelectIndex.value == index) Color(
                                                0x66FFFFFF
                                            ) else Color(0x33FFFFFF)
                                        )
                                )
                                if (index < ornamentList.size - 1) {
                                    Spacer(Modifier.width(4.dp))
                                }
                            }
                        }

                        Spacer(Modifier.height(10.dp))

                        //Ornament Cards
                        Row(
                            modifier = Modifier.size(relicWidth.value, relicHeight.value).wrapContentSize()
                        ) {
                            Box(modifier = Modifier.align(Alignment.CenterVertically)) {
                                RelicCard(
                                    ornamentList[ornamentSelectIndex.value],
                                )
                            }
                        }

                    }

                    //Ornament Right Button
                    Image(
                        painterResource(Res.drawable.ic_arrow_right_page),
                        modifier = Modifier.size(24.dp)
                            .padding(bottom = Constants.ADVICE_RELIC_BAR_HEIGHT)
                            .alpha(if (ornamentList.size > 1) 1f else 0.2f)
                            .clickable(enabled = true, indication = null, interactionSource = MutableInteractionSource(), onClick = {
                                ornamentSelectIndex.value = (ornamentSelectIndex.value + 1) % ornamentList.size
                            })
                        ,
                        contentDescription = "Ornament Right Button"
                    )

                }

                //Attr Suggestion
                Column {
                    Text(removeStrQuote(Res.string.MainAffix),fontWeight = FontWeight.Bold, style = FontSizeNormal16(), color = TextColorNormalDim)

                    Spacer(Modifier.height(10.dp))

                    VerticalGrid(
                        modifier = Modifier.fillMaxWidth().widthIn(Constants.INFO_MIN_WIDTH, Constants.INFO_MAX_WIDTH).wrapContentHeight().align(Alignment.CenterHorizontally),
                        columns = SimpleGridCells.Fixed(2),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ){

                        for((index, attr) in adviceAttrList.withIndex()){
                            Row(Modifier.fillMaxWidth().wrapContentHeight()){
                                Text(
                                    removeStrQuote(relicPart[index]),
                                    fontWeight = FontWeight.Bold,
                                    style = FontSizeNormal14(),
                                    color = TextColorNormalDim
                                )
                                Spacer(Modifier.width(10.dp))
                                Text(
                                    text = removeStrQuote(attr.second.resName),
                                    style = FontSizeNormal14(),
                                    color = TextColorNormalDimCC,
                                    textAlign = TextAlign.End,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }

                    Spacer(Modifier.height(10.dp))

                    var subAttrString = ""
                    for((index, subAttr) in adviceAttrSubList.withIndex()){
                        subAttrString += removeStrQuote(subAttr.resName) + if(index < adviceAttrSubList.size-1){","} else ""
                    }
                    Row {
                        Text(removeStrQuote(Res.string.SubAffix), fontWeight = FontWeight.Bold,style = FontSizeNormal16(), color = TextColorNormalDim)

                        Text(
                            text = subAttrString,
                            style = FontSizeNormal14(),
                            color = TextColorNormalDimCC,
                            textAlign = TextAlign.End,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

            }else{
                InfoCannotFind()
            }
        }
    }

    Spacer(modifier = Modifier.height(24.dp))
}


@Composable
fun InfoAdviceTeammate(
    charWeightData: JsonObject? = null,
    characterId: String,

    dialogTitle: MutableState<String>,
    dialogDisplay: MutableState<Boolean>,
    dialogLastTrigType: MutableState<String>,
    dialogComponent: MutableState<@Composable () -> Unit>

) {

    val leaderInfo = Character.getCharacterItemFromJSON(characterId, Language.TextLanguageInstance)
    Column(modifier = Modifier.wrapContentSize().statusBarsPadding().padding(start = SCREEN_SAVE_PADDING, end = SCREEN_SAVE_PADDING)){
        TitleHeader(iconRId = Res.drawable.phorphos_person_regular, titleRId = Res.string.AdviceTeams)

        //Empty Blank
        Spacer(modifier = Modifier.height(24.dp))

        //Content
        if(charWeightData != null){
            val teamList = charWeightData.jsonObject["team"]!!.jsonArray
            //Show of recommend Teams
            Column {
                for(team in teamList){
                    Box(modifier = Modifier.fillMaxWidth().wrapContentHeight()){
                        LazyRow(
                            state = rememberLazyListState(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.align(Alignment.Center)
                        ) {
                            item {
                                //Team Leader
                                Box(Modifier.size(Constants.CHAR_CARD_WIDTH, (Constants.CHAR_CARD_WIDTH+Constants.CHAR_CARD_TITLE_HEIGHT))){
                                    CharacterCard(leaderInfo)
                                }
                            }
                            for(teamChoice in team.jsonArray){
                                item {
                                    val teamChoiceString = teamChoice.jsonPrimitive.content
                                    Box(Modifier.size(Constants.CHAR_CARD_WIDTH, (Constants.CHAR_CARD_WIDTH+Constants.CHAR_CARD_TITLE_HEIGHT))){
                                        if(teamChoiceString.toIntOrNull() != null && teamChoiceString.toIntOrNull()!! in 1000 .. 9999){
                                            CharacterCard(Character.getCharacterItemFromJSON(teamChoiceString, Language.TextLanguageInstance))
                                        }else{
                                            MultiChoiceCard(teamChoiceString,leaderInfo, dialogTitle, dialogDisplay, dialogLastTrigType, dialogComponent)
                                        }
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }else{
            InfoCannotFind()
        }
    }

}

@Composable
fun InfoAdviceCharacter(
    lightconeId: String,
){
    val charWeightList = CharWeightList.INSTANCE
    val adviceChar : ArrayList<Character> = arrayListOf()
    for(char in charWeightList.jsonObject.keys){
        for(lcId in charWeightList.jsonObject[char]!!.jsonArray[0].jsonObject["advice_lightcone"]!!.jsonArray){
            if(lcId.jsonPrimitive.content == lightconeId){
                if(adviceChar.none { charAdvice -> charAdvice.officialId.toString() == char }){
                    adviceChar.add(Character.getCharacterItemFromJSON(char, Language.TextLanguageInstance))
                    break
                }
            }
        }

        for(lcId in charWeightList.jsonObject[char]!!.jsonArray[0].jsonObject["normal_lightcone"]!!.jsonArray){
            if(lcId.jsonPrimitive.content == lightconeId){
                if(adviceChar.none { charAdvice -> charAdvice.officialId.toString() == char }){
                    adviceChar.add(Character.getCharacterItemFromJSON(char, Language.TextLanguageInstance))
                    break
                }
            }
        }
    }

    Column(modifier = Modifier.fillMaxWidth().statusBarsPadding().padding(start = SCREEN_SAVE_PADDING, end = SCREEN_SAVE_PADDING)) {
        TitleHeader(
            iconRId = Res.drawable.phorphos_person_regular,
            titleRId = Res.string.AdviceCharacters
        )

        //Empty Blank
        Spacer(modifier = Modifier.height(24.dp))
        Box(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {
            LazyRow(
                state = rememberLazyListState(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.align(Alignment.Center).wrapContentSize()
            ) {
                for (char in adviceChar) {
                    item {
                        Box(Modifier.width(Constants.CHAR_CARD_WIDTH).wrapContentHeight()) {
                            CharacterCard(char)
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun InfoCannotFind(){
    //Cannot find that Character
    Column(modifier = Modifier.fillMaxWidth()) {
        Image(
            painter = painterResource(Res.drawable.pom_pom_failed_issue),
            contentDescription = "No Recommendation Data now.",
            modifier = Modifier.size(72.dp).align(Alignment.CenterHorizontally)
        )
        Text(
            removeStrQuote(Res.string.NoDataYet),
            style = FontSizeNormal14(),
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally)
        )
    }
}