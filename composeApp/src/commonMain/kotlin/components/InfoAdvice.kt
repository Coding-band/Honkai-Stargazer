package components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.voc.honkai_stargazer.component.LightconeCard
import files.AdviceLightcones
import files.NoDataYet
import files.Res
import files.phorphos_sword_regular
import files.pom_pom_failed_issue
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.jetbrains.compose.resources.painterResource
import types.Constants
import types.Lightcone
import utils.FontSizeNormal
import utils.UtilTools

@Composable
fun InfoAdviceLightcone(charId : String) {
    val charWeightListJson = UtilTools.TemporaryFunction().getCharWeightListJson()
    val charWeightDataList = charWeightListJson.jsonObject[charId]
    val selectedSectIndex = remember { mutableStateOf(0) } //流派

    Column(modifier = Modifier.fillMaxWidth().statusBarsPadding().padding(start = 18.dp, end = 18.dp)){
        TitleHeader(iconRId = Res.drawable.phorphos_sword_regular, titleRId = Res.string.AdviceLightcones)

        //Empty Blank
        Spacer(modifier = Modifier.height(24.dp))

        //Content
        if(charWeightDataList != null && charWeightDataList.jsonArray.size > 0){
            //Show of recommend Lightcones
            LazyRow(
                state = rememberLazyListState(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                //Most Advice To Equip
                for(lcItem in charWeightDataList.jsonArray[selectedSectIndex.value].jsonObject["advice_lightcone"]!!.jsonArray){
                    val officialLcId = lcItem.jsonPrimitive.int
                    val lightcone = Lightcone.getLightconeItemFromJSON(officialLcId.toString(), textLanguage = UtilTools.TextLanguage.ZH_HK)

                    item{
                        Box(Modifier.size(Constants.LC_CARD_WIDTH, Constants.LC_CARD_HEIGHT)){
                            LightconeCard(lightcone, displayName = lightcone.displayName)
                        }
                    }

                }
                //Still Can Use If Only Have
                for(lcItem in charWeightDataList.jsonArray[selectedSectIndex.value].jsonObject["normal_lightcone"]!!.jsonArray){
                    val officialLcId = lcItem.jsonPrimitive.int
                    val lightcone = Lightcone.getLightconeItemFromJSON(officialLcId.toString(), textLanguage = UtilTools.TextLanguage.ZH_HK)

                    item{
                        Box(Modifier.size(Constants.LC_CARD_WIDTH, Constants.LC_CARD_HEIGHT)){
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