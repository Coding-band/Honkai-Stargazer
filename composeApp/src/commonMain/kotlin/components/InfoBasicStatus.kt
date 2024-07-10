package components

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import files.BasicStatus
import files.Res
import files.ic_aggro
import files.ic_arrow_to_down
import files.ic_atk
import files.ic_def
import files.ic_energy
import files.ic_hp
import files.ic_speed
import files.phorphos_info_regular
import kotlinx.serialization.json.JsonElement
import org.jetbrains.compose.resources.painterResource
import types.Constants.Companion.MATERIAL_CARD_HEIGHT
import utils.FontSizeNormal16
import utils.calculator.AttrData
import utils.calculator.getCharAttrData
import utils.calculator.getCharMaterialData

enum class StatusType{
    CHARACTER, LIGHTCONE
}

@Composable
fun InfoBasicStatus(infoJson : JsonElement, statusType : StatusType = StatusType.CHARACTER){
    var statusCal : AttrData by remember { mutableStateOf(AttrData(0f,0f,0f,0f,0,0)) }

    var basicStatusLvBegin by remember { mutableStateOf(1f) }
    var basicStatusLvEnd by remember { mutableStateOf(80f) }
    statusCal = getCharAttrData(infoJson, basicStatusLvEnd.toInt())

    if(basicStatusLvBegin > basicStatusLvEnd){
        basicStatusLvBegin = basicStatusLvEnd
    }else if(basicStatusLvEnd < basicStatusLvBegin){
        basicStatusLvEnd = basicStatusLvBegin
    }

    val materialList = remember { getCharMaterialData(infoJson, beginLv = basicStatusLvBegin.toInt(), endLv = basicStatusLvEnd.toInt()) }
    val sortedMaterialKeyList = remember { materialList.keys.sorted() }

    Column (modifier = Modifier.statusBarsPadding().padding(start = 18.dp, end = 18.dp)){
        TitleHeader(iconRId = Res.drawable.phorphos_info_regular, titleRId = Res.string.BasicStatus)

        //Empty Blank
        Spacer(modifier = Modifier.height(24.dp))

        //Character Attr Data
        Row(modifier = Modifier.align(Alignment.CenterHorizontally)){
            Row(modifier = Modifier.padding(2.dp)){
                Image(painter = painterResource(Res.drawable.ic_hp), contentDescription = "HP Icon", modifier = Modifier.size(24.dp))
                Box(modifier = Modifier.width(2.dp))
                Text(style = FontSizeNormal16(), text = (statusCal.hp).toInt().toString(), color = Color.White, modifier = Modifier.align(
                    Alignment.CenterVertically))
            }
            Row(modifier = Modifier.padding(2.dp)){
                Image(painter = painterResource(Res.drawable.ic_atk), contentDescription = "ATK Icon", modifier = Modifier.size(24.dp))
                Box(modifier = Modifier.width(2.dp))
                Text(style = FontSizeNormal16(), text = (statusCal.atk).toInt().toString(), color = Color.White, modifier = Modifier.align(
                    Alignment.CenterVertically))
            }
            Row(modifier = Modifier.padding(2.dp)){
                Image(painter = painterResource(Res.drawable.ic_def), contentDescription = "DEF Icon", modifier = Modifier.size(24.dp))
                Box(modifier = Modifier.width(2.dp))
                Text(style = FontSizeNormal16(), text = (statusCal.def).toInt().toString(),color = Color.White, modifier = Modifier.align(
                    Alignment.CenterVertically))
            }
            if (statusType === StatusType.CHARACTER){
                Row(modifier = Modifier.padding(2.dp)){
                    Image(painter = painterResource(Res.drawable.ic_speed), contentDescription = "SPEED Icon", modifier = Modifier.size(24.dp))
                    Box(modifier = Modifier.width(2.dp))
                    Text(style = FontSizeNormal16(), text = (statusCal.spd).toInt().toString(),color = Color.White, modifier = Modifier.align(
                        Alignment.CenterVertically))
                }
                Row(modifier = Modifier.padding(2.dp)){
                    Image(painter = painterResource(Res.drawable.ic_energy), contentDescription = "ENERGY Icon", modifier = Modifier.size(24.dp))
                    Box(modifier = Modifier.width(2.dp))
                    Text(style = FontSizeNormal16(), text = (statusCal.energy).toString(),color = Color.White, modifier = Modifier.align(
                        Alignment.CenterVertically))
                }
                Row(modifier = Modifier.padding(2.dp)){
                    Image(painter = painterResource(Res.drawable.ic_aggro), contentDescription = "AGGRO Icon", modifier = Modifier.size(24.dp))
                    Box(modifier = Modifier.width(2.dp))
                    Text(style = FontSizeNormal16(), text = (statusCal.aggro).toString(),color = Color.White, modifier = Modifier.align(
                        Alignment.CenterVertically))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        //Level Slider
        Column {
            Row(Modifier.height(20.dp)){
                Text("Lv.${basicStatusLvBegin.toInt()}", modifier = Modifier.width(60.dp).align(
                    Alignment.CenterVertically), color = Color.White)
                ThemedSlider(basicStatusLvBegin, { basicStatusLvBegin = it})
            }

            Image(painterResource(Res.drawable.ic_arrow_to_down), contentDescription = "Arrow Down", modifier = Modifier.padding(8.dp))

            Row(Modifier.height(20.dp)){
                Text("Lv.${basicStatusLvEnd.toInt()}", modifier = Modifier.width(60.dp).align(
                    Alignment.CenterVertically), color = Color.White)
                ThemedSlider(basicStatusLvEnd, { basicStatusLvEnd = it})
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        //Material List
        LazyRow(modifier = Modifier.height(MATERIAL_CARD_HEIGHT).fillMaxWidth()) {
            for((index, key) in (sortedMaterialKeyList).withIndex()){
                if(materialList[key] !== null){
                    item(key = key){
                        if(index != 0){
                            Spacer(modifier = Modifier.width(16.dp))
                        }
                        MaterialCard(materialList[key]!!)
                    }
                }

            }
        }

    }
}