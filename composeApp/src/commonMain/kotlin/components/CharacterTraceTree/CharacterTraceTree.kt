package components.CharacterTraceTree

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichText
import components.MaterialCard
import components.ThemedSlider
import components.TitleHeader
import files.Res
import files.TraceEnergyEarn
import files.TraceTree
import files.Upgrade
import files.phorphos_tree_structure_fill
import getScreenSizeInfo
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.float
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import types.Constants
import types.Constants.Companion.TRACE_TREE_BASE_WIDTH
import types.Constants.Companion.getTraceTreeScale
import types.Material
import types.Path
import types.TraceTreeItem
import types.TracecTreeKeyStatus
import types.TracecTreeLevelData
import utils.FontSizeNormal14
import utils.FontSizeNormal16
import utils.UtilTools

private lateinit var dialogTitleLocal : MutableState<String>
private lateinit var dialogDisplayLocal: MutableState<Boolean>
private lateinit var dialogLastTrigTypeLocal: MutableState<String>
private lateinit var dialogComponentLocal: MutableState<@Composable () -> Unit>

private const val lastTrigTypeTag = "TRACE_TREE"

@Composable
fun CharacterTraceTree(
    infoJson: JsonElement,
    path: Path,
    charName: String,
    dialogTitle: MutableState<String>,
    dialogDisplay: MutableState<Boolean>,
    dialogLastTrigType: MutableState<String>,
    dialogComponent: MutableState<@Composable () -> Unit>
){
    dialogTitleLocal = dialogTitle
    dialogDisplayLocal = dialogDisplay
    dialogComponentLocal = dialogComponent
    dialogLastTrigTypeLocal = dialogLastTrigType

    val displayWidth = min(getScreenSizeInfo().wDP - 36.dp, (TRACE_TREE_BASE_WIDTH*1.5f));
    val selectedId = remember { mutableStateOf(0) }
    Column(modifier = Modifier.fillMaxWidth().statusBarsPadding().padding(start = Constants.SCREEN_SAVE_PADDING, end = Constants.SCREEN_SAVE_PADDING)){
        TitleHeader(iconRId = Res.drawable.phorphos_tree_structure_fill, titleRId = Res.string.TraceTree)

        //Empty Blank
        Spacer(modifier = Modifier.height(24.dp))

        Column(modifier = Modifier.fillMaxWidth().wrapContentHeight(), horizontalAlignment = Alignment.CenterHorizontally) {
            when(path){
                Path.Abundance -> AbundanceTraceTree(infoJson, displayWidth, selectedId, charName)
                Path.Destruction -> DestructionTraceTree(infoJson, displayWidth, selectedId, charName)
                Path.Erudition -> EruditionTraceTree(infoJson, displayWidth, selectedId, charName)
                Path.Harmony -> HarmonyTraceTree(infoJson, displayWidth, selectedId, charName)
                Path.Hunt -> HuntTraceTree(infoJson, displayWidth, selectedId, charName)
                Path.Nihility -> NihilityTraceTree(infoJson, displayWidth, selectedId, charName)
                Path.Preservation -> PreservationTraceTree(infoJson, displayWidth, selectedId, charName)
                else -> {}
            }
        }


    }
}

//skills[n]
fun getDataFromSkills(
    skillObjectList: ArrayList<JsonElement>,
    charFileName: String,
    skillIndex: Int,
    infoJson: JsonElement
): ArrayList<TraceTreeItem> {
    val itemReferences = infoJson.jsonObject["itemReferences"];
    //Since the ObjectList store at least 1 data
    val treeItemList = arrayListOf<TraceTreeItem>()
    for (skillObject in skillObjectList) {
        //Init Level Data
        val levelData: ArrayList<TracecTreeLevelData> = arrayListOf();

        for (data in skillObject.jsonObject["levelData"]!!.jsonArray) {
            //Init Cost Data
            val costData: ArrayList<Material> = arrayListOf()
            val paramData: ArrayList<Float> = arrayListOf()

            for (costItem in data.jsonObject["cost"]!!.jsonArray) {
                val id = costItem.jsonObject["id"]!!.jsonPrimitive.int
                if(itemReferences == null) break
                costData.add(
                    Material(
                        officialId = id,
                        name = itemReferences.jsonObject[id.toString()]!!.jsonObject["name"]!!.jsonPrimitive.content,
                        rarity = itemReferences.jsonObject[id.toString()]!!.jsonObject["rarity"]!!.jsonPrimitive.int,
                        count = costItem.jsonObject["count"]!!.jsonPrimitive.int,
                    ),
                )
            }

            for (param in data.jsonObject["params"]!!.jsonArray) {
                paramData.add(param.jsonPrimitive.float)
            }

            levelData.add(
                TracecTreeLevelData(
                    level = data.jsonObject["level"]!!.jsonPrimitive.int,
                    cost = costData,
                    params = paramData
                )
            )
        }

        treeItemList.add(
            TraceTreeItem(
                id = skillObject.jsonObject["id"]!!.jsonPrimitive.int,
                desc = skillObject.jsonObject["descHash"]!!.jsonPrimitive.content,
                name = skillObject.jsonObject["name"]!!.jsonPrimitive.content,
                energy = skillObject.jsonObject["energy"]!!.jsonPrimitive.content.split("/")[0].toInt(),
                iconPath = "${charFileName}_skill${skillIndex}",
                levelData = levelData,
                statusList = null,
                tagHash = skillObject.jsonObject["tagHash"]!!.jsonPrimitive.content,
                typeDescHash = skillObject.jsonObject["typeDescHash"]!!.jsonPrimitive.content,
            )
        )
    }
    return treeItemList
}


//skillTreePoints
@Composable
fun getDataFromSkillTreePoints(skillObject: JsonObject, infoJson: JsonElement) : TraceTreeItem {
    val itemReferences = infoJson.jsonObject["itemReferences"];
//Init Level Data
    val levelData : ArrayList<TracecTreeLevelData> = arrayListOf();
    val statusList : ArrayList<TracecTreeKeyStatus> = arrayListOf();
    val trigCostList : ArrayList<Material> = arrayListOf();

    if(skillObject.jsonObject["embedBonusSkill"] !== null && skillObject.jsonObject["embedBonusSkill"]!!.jsonObject["levelData"] !== null){
        for(data in skillObject.jsonObject["embedBonusSkill"]!!.jsonObject["levelData"]!!.jsonArray){
            //Init Cost Data
            val costData : ArrayList<Material> = arrayListOf()
            val paramData : ArrayList<Float> = arrayListOf()

            for (costItem in data.jsonObject["cost"]!!.jsonArray) {
                val id = costItem.jsonObject["id"]!!.jsonPrimitive.int
                if(itemReferences == null) break
                costData.add(
                    Material(
                        officialId = id,
                        name = itemReferences.jsonObject[id.toString()]!!.jsonObject["name"]!!.jsonPrimitive.content,
                        rarity = itemReferences.jsonObject[id.toString()]!!.jsonObject["rarity"]!!.jsonPrimitive.int,
                        count = costItem.jsonObject["count"]!!.jsonPrimitive.int,
                    ),
                )
            }

            for(param in data.jsonObject["params"]!!.jsonArray){
                paramData.add(param.jsonPrimitive.float)
            }

            levelData.add(
                TracecTreeLevelData(
                    level = data.jsonObject["level"]!!.jsonPrimitive.int,
                    cost = costData,
                    params = paramData
                )
            )
        }
    }

    val skillObjectExt = skillObject.jsonObject["embedBuff"] ?: skillObject.jsonObject["embedBonusSkill"]
    if(skillObjectExt != null && skillObjectExt.jsonObject["statusList"] !== null){
        for(status in skillObjectExt.jsonObject["statusList"]!!.jsonArray){
            statusList.add(TracecTreeKeyStatus(status.jsonObject["key"]!!.jsonPrimitive.content,status.jsonObject["value"]!!.jsonPrimitive.float))
        }
    }
    if(skillObjectExt != null && skillObjectExt.jsonObject["cost"] !== null){
        for (costItem in skillObjectExt.jsonObject["cost"]!!.jsonArray) {
            val id = costItem.jsonObject["id"]!!.jsonPrimitive.int
            if(itemReferences == null) break
            trigCostList.add(
                Material(
                    officialId = id,
                    name = itemReferences.jsonObject[id.toString()]!!.jsonObject["name"]!!.jsonPrimitive.content,
                    rarity = itemReferences.jsonObject[id.toString()]!!.jsonObject["rarity"]!!.jsonPrimitive.int,
                    count = costItem.jsonObject["count"]!!.jsonPrimitive.int,
                ),
            )
        }
    }

    if(skillObject.jsonObject["embedBonusSkill"] !== null){
        return TraceTreeItem(
            id = skillObject.jsonObject["id"]!!.jsonPrimitive.int,
            anchor = skillObject.jsonObject["anchor"]!!.jsonPrimitive.int ,
            desc = skillObject.jsonObject["embedBonusSkill"]!!.jsonObject["descHash"]!!.jsonPrimitive.content,
            name = skillObject.jsonObject["embedBonusSkill"]!!.jsonObject["name"]!!.jsonPrimitive.content,
            energy = skillObject.jsonObject["embedBonusSkill"]!!.jsonObject["ultimateCost"]!!.jsonPrimitive.int,
            iconPath = skillObject.jsonObject["embedBonusSkill"]!!.jsonObject["iconPath"]!!.jsonPrimitive.content,
            levelData = if(skillObject.jsonObject["embedBonusSkill"]!!.jsonObject["levelData"] !== null) levelData else null,
            statusList = if(skillObject.jsonObject["embedBonusSkill"]!!.jsonObject["statusList"] !== null) statusList else null,
            trigCost = if(skillObject.jsonObject["embedBonusSkill"]!!.jsonObject["cost"] !== null) trigCostList else null
        )
    }
    return TraceTreeItem(
        id = skillObject.jsonObject["id"]!!.jsonPrimitive.int,
        anchor = skillObject.jsonObject["anchor"]!!.jsonPrimitive.int ,
        desc = "",
        name = skillObject.jsonObject["embedBuff"]!!.jsonObject["name"]!!.jsonPrimitive.content,
        iconPath = skillObject.jsonObject["embedBuff"]!!.jsonObject["iconPath"]!!.jsonPrimitive.content,
        levelData = if(skillObject.jsonObject["embedBuff"]!!.jsonObject["levelData"] !== null) levelData else null,
        statusList = if(skillObject.jsonObject["embedBuff"]!!.jsonObject["statusList"] !== null) statusList else null,
        trigCost = if(skillObject.jsonObject["embedBuff"]!!.jsonObject["cost"] !== null) trigCostList else null
    )
}

@Composable
fun TraceTreeBtn(
    selectedId: MutableState<Int>,
    selfId: Int,
    traceTreeItem: ArrayList<TraceTreeItem>,
    displayWidth: Dp,
    modifier: Modifier = Modifier,
    offset: ArrayList<Pair<Int, Int>>
) {
    val isSelected = (selectedId.value == selfId) && dialogDisplayLocal.value && dialogLastTrigTypeLocal.value == lastTrigTypeTag

    var btnBaseSize = Constants.TRACE_TREE_BTN_EXTEND_BASE_SIZE
    var imgBaseSize = Constants.TRACE_TREE_IMG_EXTEND_BASE_SIZE

    when (selfId) {
        in 1..5 -> {
            btnBaseSize = Constants.TRACE_TREE_BTN_CORE_BASE_SIZE
            imgBaseSize = Constants.TRACE_TREE_IMG_CORE_BASE_SIZE
        }

        in 6..8 -> {
            btnBaseSize = Constants.TRACE_TREE_BTN_SUBCORE_BASE_SIZE
            imgBaseSize = Constants.TRACE_TREE_IMG_SUBCORE_BASE_SIZE
        }
    }

    val btnWidth = btnBaseSize * getTraceTreeScale(displayWidth)
    val imgWidth = imgBaseSize * getTraceTreeScale(displayWidth)
    Box(
        modifier = modifier
            .offset(
                offset[selfId].first.dp.times(getTraceTreeScale(displayWidth)),
                offset[selfId].second.dp.times(getTraceTreeScale(displayWidth))
            )
            .clip(CircleShape)
            .size(btnWidth)
            .border(
                width = 2.dp, color = (if (isSelected) {
                    if(selfId <= 5) Color(0xFFFCBC62) else Color(0xFFFFFFFF)
                } else
                    if(selfId <= 5) Color(0xFF31B5FF) else Color(0x00000000)),
                shape = CircleShape
            ).clickable(
                onClick = {
                    selectedId.value = if (selectedId.value == selfId) 0 else selfId
                    dialogDisplayLocal.value = (selectedId.value != 0)
                    dialogTitleLocal.value = traceTreeItem[0].name
                    dialogComponentLocal.value = { TreePointDialogComponent(traceTreeItem) }
                    dialogLastTrigTypeLocal.value = lastTrigTypeTag
                },
            )
    ) {
        Image(
            bitmap = if (selfId <= 5) {
                UtilTools().getAssetsWebpByFileName(
                    UtilTools.ImageFolderType.CHAR_SKILL,
                    UtilTools().getImageNameByRegistName(traceTreeItem[0].iconPath, isCharNoGen = true)
                )
            } else {
                UtilTools().getAssetsWebpByFileName(
                    UtilTools.ImageFolderType.CHAR_SKILL_TREE,
                    traceTreeItem[0].iconPath
                )
            },
            contentDescription = "Skill Icon",
            modifier = Modifier.size(imgWidth).align(Alignment.Center)
        )

        /*
        AsyncImage(
            model = UtilTools().newImageRequest(
                LocalPlatformContext.current,
                if (selfId <= 5) {
                    UtilTools().getAssetsWebpByteArrayByFileName(
                        UtilTools.ImageFolderType.CHAR_SKILL,
                        UtilTools().getImageNameByRegistName(traceTreeItem[0].iconPath, isCharNoGen = true)
                    )
                } else {
                    UtilTools().getAssetsWebpByteArrayByFileName(
                        UtilTools.ImageFolderType.CHAR_SKILL_TREE,
                        traceTreeItem[0].iconPath
                    )
                },
                false
            ),
            contentDescription = "Skill Icon",
            modifier = Modifier.size(imgWidth).align(Alignment.Center),
            imageLoader = UtilTools().newImageLoader(LocalPlatformContext.current)
        )
        */
    }
}

@Composable
fun justTesting(){
    Box(modifier = Modifier.size(50.dp)){
        Text("OK")
    }
}

/*

Task :composeApp:linkDebugFrameworkIosSimulatorArm64 FAILED
 */
@Composable
fun TreePointDialogComponent(treeItemArray: ArrayList<TraceTreeItem>){
    var infoLevel by remember { mutableStateOf(1f) }
    var richTextState = rememberRichTextState()
    val scrollState = rememberScrollState()
    Column(Modifier.verticalScroll(scrollState)) {
        if(treeItemArray[0].typeDescHash !== null){
            Box(Modifier.clip(RoundedCornerShape(41.dp)).background(Color(0xFF666666))){
                Text(treeItemArray[0].typeDescHash ?: "?", modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 6.dp, bottom = 6.dp), style = FontSizeNormal14(), color = Color.White)
            }
        }

        Spacer(Modifier.height(8.dp))

        for((index, treeItem) in treeItemArray.withIndex()){
            //Name Title of that Skill
            if(index > 0){
                Spacer(Modifier.height(12.dp))
            }
            if(treeItemArray.size > 1){
                Text(treeItem.name, style = FontSizeNormal16(), fontStyle = FontStyle.Italic, color = Color(0xFF666666))
            }

            //TagHash & Energy Recharge
            Row(Modifier.fillMaxWidth()){
                Box(Modifier.weight(1f)) {
                    if (treeItem.tagHash !== null) {
                        Text(
                            treeItem.tagHash,
                            style = FontSizeNormal14(),
                            color = Color(0xFFDD8200),
                        )
                    }
                }

                if(treeItem.energy != -1){
                    Text("${UtilTools().removeStringResDoubleQuotes(Res.string.TraceEnergyEarn)} ${treeItem.energy.toString()}",
                        style = FontSizeNormal14(),
                        color = Color(0xFF666666)
                    )
                }
            }

            var params = arrayListOf<Float>()
            //Slider
            if(treeItem.levelData != null && treeItem.levelData.size > 0){
                if(infoLevel.toInt() >= treeItem.levelData.size){
                    infoLevel = treeItem.levelData.size.toFloat()
                }
                params = treeItem.levelData[infoLevel.toInt()-1].params
                Spacer(Modifier.height(8.dp))
                Row(Modifier.height(20.dp)){
                    Text("Lv.${infoLevel.toInt()}/${treeItem.levelData.size}", modifier = Modifier.defaultMinSize(minWidth = 60.dp).wrapContentWidth().align(
                        Alignment.CenterVertically), color = Color.Black)
                    Spacer(Modifier.width(24.dp))
                    ThemedSlider(infoLevel, { infoLevel = it}, valueRange = 1f .. treeItem.levelData.size.toFloat(), steps = 0)
                }
            }

            Spacer(Modifier.height(8.dp))

            //Description
            var statusDesc = ""
            if(treeItem.statusList != null) {
                for ((index, status) in treeItem.statusList.withIndex()) {
                    statusDesc = "$statusDesc${if(index > 0) "," else ""} " +
                            "${status.key} ${UtilTools().removeStringResDoubleQuotes(Res.string.Upgrade)} " +
                            if(status.value < 1){UtilTools().formatDecimal((status.value * 100))+"%"} else UtilTools().formatDecimal(status.value,0)
                }
            }
            richTextState.setHtml(
                if(statusDesc != ""){
                    statusDesc
                }else{
                    UtilTools().htmlDescApplier(treeItem.desc, params)
                }
            )
            RichText(state = richTextState, style = FontSizeNormal14(), modifier = Modifier.fillMaxWidth(), color = Color(0xFF666666))


            //Material Cost
            if(treeItem.levelData != null && treeItem.levelData.size > 0 && index == 0) {
                if(infoLevel.toInt() >= treeItem.levelData.size){
                    infoLevel = treeItem.levelData.size.toFloat()
                }
                val sortedMaterialKeyList = treeItem.levelData[infoLevel.toInt() - 1].cost.sortedBy { cost -> cost.officialId }
                LazyRow(modifier = Modifier.fillMaxWidth()) {
                    for ((index, key) in sortedMaterialKeyList.withIndex()) {
                        item(key = key.officialId) {
                            if (index != 0) {
                                Spacer(modifier = Modifier.width(16.dp))
                            }
                            MaterialCard(key)
                        }

                    }
                }
                Spacer(Modifier.height(8.dp))
            }

        }

    }

}

fun jsonArrayToIntArrayList(jsonArray: JsonArray? = null) : ArrayList<Int>{
    val intArrayList : ArrayList<Int> = arrayListOf()
    if(jsonArray != null){
        for(value in jsonArray){
            intArrayList.add(value.jsonPrimitive.int)
        }
    }
    return intArrayList

}