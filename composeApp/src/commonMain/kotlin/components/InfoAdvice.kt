package components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.voc.honkai_stargazer.component.LightconeCard
import com.voc.honkai_stargazer.component.RelicCard
import files.AdviceLightcones
import files.AdviceRelics
import files.NoDataYet
import files.Res
import files.ic_add_icon
import files.ic_arrow_left_page
import files.ic_arrow_right_page
import files.phorphos_baseball_cap_regular
import files.phorphos_sword_regular
import files.pom_pom_failed_issue
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.jetbrains.compose.resources.painterResource
import types.Constants
import types.Lightcone
import types.Relic
import utils.FontSizeNormal
import utils.UtilTools

@Composable
fun InfoAdviceLightcone(charWeightData : JsonObject? = null) {
    Column(modifier = Modifier.fillMaxWidth().statusBarsPadding().padding(start = 18.dp, end = 18.dp)){
        TitleHeader(iconRId = Res.drawable.phorphos_sword_regular, titleRId = Res.string.AdviceLightcones)

        //Empty Blank
        Spacer(modifier = Modifier.height(24.dp))

        //Content
        if(charWeightData != null){
            //Show of recommend Lightcones
            LazyRow(
                state = rememberLazyListState(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                //Most Advice To Equip
                for(lcItem in charWeightData.jsonObject["advice_lightcone"]!!.jsonArray){
                    val officialLcId = lcItem.jsonPrimitive.int
                    val lightcone = Lightcone.getLightconeItemFromJSON(officialLcId.toString(), textLanguage = UtilTools.TextLanguage.ZH_HK)

                    item{
                        Box(Modifier.size(Constants.LC_CARD_WIDTH, (Constants.LC_CARD_HEIGHT+Constants.LC_CARD_TITLE_HEIGHT))){
                            LightconeCard(lightcone, displayName = lightcone.displayName)
                        }
                    }

                }
                //Still Can Use If Only Have
                for(lcItem in charWeightData.jsonObject["normal_lightcone"]!!.jsonArray){
                    val officialLcId = lcItem.jsonPrimitive.int
                    val lightcone = Lightcone.getLightconeItemFromJSON(officialLcId.toString(), textLanguage = UtilTools.TextLanguage.ZH_HK)

                    item{
                        Box(Modifier.size(Constants.LC_CARD_WIDTH, (Constants.LC_CARD_HEIGHT+Constants.LC_CARD_TITLE_HEIGHT))){
                            LightconeCard(lightcone, displayName = lightcone.displayName)
                        }
                    }
                }
            }
        }else{
            InfoCannotFind()
        }
    }

}


@Composable
fun InfoAdviceRelic(charWeightData : JsonObject? = null) {
    Column(modifier = Modifier.fillMaxWidth().statusBarsPadding().padding(start = 18.dp, end = 18.dp)){
        TitleHeader(iconRId = Res.drawable.phorphos_baseball_cap_regular, titleRId = Res.string.AdviceRelics)

        //Empty Blank
        Spacer(modifier = Modifier.height(24.dp))

        //Content
        if(charWeightData != null){
            val relicList : ArrayList<Pair<Relic,Relic>> = arrayListOf()
            val ornamentList : ArrayList<Relic> = arrayListOf()
            val relicSelectIndex = remember { mutableStateOf(0) }
            val ornamentSelectIndex = remember { mutableStateOf(0) }

            for(relicSets in charWeightData.jsonObject["advice_relic"]!!.jsonArray){
                if(relicSets.jsonArray.size >= 2) {
                    val relicId1 = relicSets.jsonArray[0].jsonPrimitive.int
                    val relicId2 = relicSets.jsonArray[1].jsonPrimitive.int

                    if (relicId1 == -1 || relicId2 == -1) continue

                    val relic1 = Relic.getRelicItemFromJSON(relicId1.toString(), UtilTools.TextLanguage.ZH_HK)
                    val relic2 = Relic.getRelicItemFromJSON(relicId2.toString(), UtilTools.TextLanguage.ZH_HK)

                    relicList.add(relic1 to relic2)
                }
            }

            for(ornament in charWeightData.jsonObject["advice_ornament"]!!.jsonArray){
                if (ornament.jsonPrimitive.int == -1) continue
                ornamentList.add(Relic.getRelicItemFromJSON(ornament.jsonPrimitive.int.toString(), UtilTools.TextLanguage.ZH_HK))
            }

            Row(
                Modifier.wrapContentWidth().widthIn(320.dp, 450.dp),
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
                            Modifier.size(
                                Constants.RELIC_CARD_WIDTH,
                                Constants.RELIC_CARD_HEIGHT + Constants.RELIC_CARD_TITLE_HEIGHT
                            )
                        ) {
                            RelicCard(
                                relicList[relicSelectIndex.value].first,
                                displayName = relicList[relicSelectIndex.value].first.displayName
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Box(
                            Modifier.size(
                                Constants.RELIC_CARD_WIDTH,
                                Constants.RELIC_CARD_HEIGHT + Constants.RELIC_CARD_TITLE_HEIGHT
                            )
                        ) {
                            RelicCard(
                                relicList[relicSelectIndex.value].second,
                                displayName = relicList[relicSelectIndex.value].second.displayName
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
                        modifier = Modifier.height(Constants.RELIC_CARD_HEIGHT + Constants.RELIC_CARD_TITLE_HEIGHT)
                            .width(Constants.RELIC_CARD_WIDTH)
                    ) {
                        RelicCard(
                            ornamentList[ornamentSelectIndex.value],
                            displayName = ornamentList[ornamentSelectIndex.value].displayName
                        )
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
        }else{
            InfoCannotFind()
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
            UtilTools().removeStringResDoubleQuotes(Res.string.NoDataYet),
            style = FontSizeNormal(),
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally)
        )
    }
}