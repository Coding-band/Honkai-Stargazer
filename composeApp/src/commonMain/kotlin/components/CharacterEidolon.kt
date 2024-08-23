package components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.zIndex
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichText
import files.CharSoul
import files.Eidolon
import files.EidolonFrame1
import files.EidolonFrame2
import files.EidolonFrame3
import files.EidolonFrame4
import files.EidolonFrame5
import files.EidolonFrame6
import files.Res
import files.bg_eidolon_soul
import files.phorphos_star_half_regular
import getScreenSizeInfo
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.float
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.jetbrains.compose.resources.painterResource
import types.Constants
import types.Constants.Companion.EIDOLON_FRAME_BASE_WIDTH
import types.Constants.Companion.SCREEN_SAVE_PADDING
import types.Constants.Companion.getEidolonScale
import types.Eidolon
import utils.FontSizeNormal14
import utils.UtilTools

private lateinit var dialogTitleLocal : MutableState<String>
private lateinit var dialogDisplayLocal: MutableState<Boolean>
private lateinit var dialogLastTrigTypeLocal: MutableState<String>
private lateinit var dialogComponentLocal: MutableState<@Composable () -> Unit>

val eidolonOffSet = arrayListOf(
    (0 to 0), //Empty
    (3 to 22),
    (107 to 0),
    (192 to 0),
    (202 to 114),
    (100 to 140),
    (0 to 121),
)

private const val lastTrigTypeTag = "EIDOLON"

@Composable
fun CharacterEidolon(
    infoJson: JsonElement,
    charName: String,
    dialogTitle: MutableState<String>,
    dialogDisplay: MutableState<Boolean>,
    dialogLastTrigType: MutableState<String>,
    dialogComponent: MutableState<@Composable () -> Unit>
) {
    dialogTitleLocal = dialogTitle
    dialogDisplayLocal = dialogDisplay
    dialogComponentLocal = dialogComponent
    dialogLastTrigTypeLocal = dialogLastTrigType

    val selectIndex = remember { mutableStateOf(0) }

    if(infoJson.jsonObject["ranks"] != null) {

        val eidolonList: ArrayList<Eidolon> = arrayListOf()
        val eidolonJsonArray = infoJson.jsonObject["ranks"]!!.jsonArray

        for ((index, eidolonJsonItem) in eidolonJsonArray.withIndex()) {
            eidolonList.add(
                Eidolon(
                    eidolonJsonItem.jsonObject["id"]!!.jsonPrimitive.int,
                    eidolonJsonItem.jsonObject["name"]!!.jsonPrimitive.content,
                    eidolonJsonItem.jsonObject["descHash"]!!.jsonPrimitive.content,
                    jsonArrayToFloatArrayList(eidolonJsonItem.jsonObject["params"]!!.jsonArray),
                    "${UtilTools().getImageNameByRegistName(charName, isCharNoElement = true)}_eidolon${index+1}",
                    "${UtilTools().getImageNameByRegistName(charName, isCharNoGen = true)}_soul${index+1}",
                )
            )
        }

        Column(modifier = Modifier.fillMaxWidth().statusBarsPadding().padding(start = SCREEN_SAVE_PADDING, end = SCREEN_SAVE_PADDING)){
            TitleHeader(iconRId = Res.drawable.phorphos_star_half_regular, titleRId = Res.string.Eidolon)

            //Empty Blank
            Spacer(modifier = Modifier.height(24.dp))

            Column(modifier = Modifier.fillMaxWidth().wrapContentHeight(), horizontalAlignment = Alignment.CenterHorizontally) {
                CharacterEidolonBox(eidolonList, selectIndex)
            }
        }
    }
}

@Composable
fun CharacterEidolonBox(eidolonList: ArrayList<Eidolon>, selectIndex : MutableState<Int>) {
    val eidolonScale = getEidolonScale(min(getScreenSizeInfo().wDP - 36.dp, EIDOLON_FRAME_BASE_WIDTH * 1.5f))

    Box(modifier = Modifier.fillMaxWidth().height(Constants.EIDOLON_FRAME_BASE_HEIGHT * eidolonScale)){
        //Box for Padding
        for(eidolon in eidolonList){
            //Box for Eidolon Image & Stroke
            Box(
                modifier = Modifier
                    .size(Constants.EIDOLON_IMG_BASE_SIZE * eidolonScale)
                    .zIndex(if(selectIndex.value == eidolon.eidolonIndex) 10f else eidolon.eidolonIndex.toFloat())
                    .offset(
                        eidolonOffSet[eidolon.eidolonIndex].first.dp.times(eidolonScale),
                        eidolonOffSet[eidolon.eidolonIndex].second.dp.times(eidolonScale),
                    )
                    .clickable(enabled = true, indication = null, interactionSource = remember { MutableInteractionSource() }, onClick = {
                        if(selectIndex.value == eidolon.eidolonIndex && dialogLastTrigTypeLocal.value == lastTrigTypeTag){
                            selectIndex.value = 0
                            dialogDisplayLocal.value = false
                        }else {
                            selectIndex.value = eidolon.eidolonIndex
                            dialogTitleLocal.value = eidolon.name
                            dialogDisplayLocal.value = true
                            dialogComponentLocal.value = { EidolonDialogComponent(eidolon) }
                        }
                        dialogLastTrigTypeLocal.value = lastTrigTypeTag
                    }),

            ){

                Image(
                    bitmap = UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.CHAR_EIDOLON, eidolon.eidolonImgName),
                    modifier = Modifier.size(Constants.EIDOLON_IMG_BASE_SIZE * eidolonScale),
                    contentDescription = "Character Eidolon${eidolon.eidolonIndex}'s Image"
                )

                /*
                AsyncImage(
                    model = UtilTools().newImageRequest(
                        LocalPlatformContext.current,
                        UtilTools().getAssetsWebpByteArrayByFileName(UtilTools.ImageFolderType.CHAR_EIDOLON, eidolon.eidolonImgName),
                        false
                    ),
                    modifier = Modifier.size(Constants.EIDOLON_IMG_BASE_SIZE * eidolonScale),
                    contentDescription = "Character Eidolon${eidolon.eidolonIndex}'s Image",
                    imageLoader = UtilTools().newImageLoader(LocalPlatformContext.current)
                )
                 */

                if((selectIndex.value == eidolon.eidolonIndex) && dialogLastTrigTypeLocal.value == lastTrigTypeTag) {
                    Image(
                        painter = painterResource(
                            when (eidolon.eidolonIndex) {
                                1 -> Res.drawable.EidolonFrame1
                                2 -> Res.drawable.EidolonFrame2
                                3 -> Res.drawable.EidolonFrame3
                                4 -> Res.drawable.EidolonFrame4
                                5 -> Res.drawable.EidolonFrame5
                                6 -> Res.drawable.EidolonFrame6
                                else -> Res.drawable.EidolonFrame1
                            }
                        ),
                        modifier = Modifier.size(Constants.EIDOLON_IMG_BASE_SIZE * eidolonScale)
                        ,
                        contentDescription = "Character Eidolon${eidolon.eidolonIndex}'s Frame"
                    )
                }
            }
        }
    }
}
/*
Task :composeApp:linkDebugFrameworkIosSimulatorArm64 FAILED
Task :composeApp:linkReleaseFrameworkIosSimulatorArm64 FAILED
Please use K1 instead of K2 version of RichText
*/

@Composable
fun EidolonDialogComponent(eidolon: Eidolon){
    val richTextState = rememberRichTextState()
    richTextState.setHtml(UtilTools().htmlDescApplier(eidolon.desc, eidolon.params))

    Row(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier.size(64.dp).align(Alignment.CenterVertically)
        ){
            Image(
                painterResource(Res.drawable.bg_eidolon_soul),
                contentDescription = "Character Eidolon${eidolon.eidolonIndex}'s Soul Background",
                modifier = Modifier.size(64.dp).align(Alignment.Center)
            )

            Image(
                bitmap = UtilTools().getAssetsWebpByFileName(UtilTools.ImageFolderType.CHAR_SOUL, eidolon.soulIconName),
                contentDescription = "Character Eidolon${eidolon.eidolonIndex}'s Soul Icon",
                modifier = Modifier.size(50.5.dp).align(Alignment.Center)
            )
        }

        Spacer(Modifier.width(6.dp))

        Column {
            Text(UtilTools().removeStringResDoubleQuotes(Res.string.CharSoul).replace("$"+"{1}",""), color = Color(0xFF333333))
            RichText(richTextState, color = Color(0xFF666666),style = FontSizeNormal14(),)
        }
    }
}

fun jsonArrayToFloatArrayList(jsonArray: JsonArray? = null) : ArrayList<Float>{
    val floatArrayList : ArrayList<Float> = arrayListOf()
    if(jsonArray != null){
        for(value in jsonArray){
            floatArrayList.add(value.jsonPrimitive.float)
        }
    }
    return floatArrayList

}