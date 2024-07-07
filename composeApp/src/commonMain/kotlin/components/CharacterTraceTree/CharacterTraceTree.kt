package components.CharacterTraceTree

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import components.TitleHeader
import files.Res
import files.TraceTree
import files.Upgrade
import files.phorphos_tree_structure_fill
import getScreenSizeInfo
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.float
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import types.Constants
import types.Constants.Companion.getTraceTreeScale
import types.Path
import types.TraceTreeItem
import types.TracecTreeCost
import types.TracecTreeKeyStatus
import types.TracecTreeLevelData
import utils.UtilTools

@Composable
fun CharacterTraceTree(infoJson : JsonElement, path: Path, charName: String){
    val displayWidth = getScreenSizeInfo().wDP - 36.dp;
    val selectedId = remember { mutableStateOf(0) }
    Column(modifier = Modifier.fillMaxWidth().statusBarsPadding().padding(start = 18.dp, end = 18.dp)){
        TitleHeader(iconRId = Res.drawable.phorphos_tree_structure_fill, titleRId = Res.string.TraceTree)

        //Empty Blank
        Spacer(modifier = Modifier.height(24.dp))

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

//skills[n]
fun getDataFromSkills(skillObject: JsonObject, charFileName : String, skillIndex : Int) : TraceTreeItem {
    //Init Level Data
    val levelData : ArrayList<TracecTreeLevelData> = arrayListOf();

    for(data in skillObject.jsonObject["levelData"]!!.jsonArray){
        //Init Cost Data
        val costData : ArrayList<TracecTreeCost> = arrayListOf()
        val paramData : ArrayList<Float> = arrayListOf()

        for(costItem in data.jsonObject["cost"]!!.jsonArray){
            costData.add(TracecTreeCost(costItem.jsonObject["id"]!!.jsonPrimitive.int,costItem.jsonObject["count"]!!.jsonPrimitive.int))
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

    return TraceTreeItem(
        id = skillObject.jsonObject["id"]!!.jsonPrimitive.int,
        desc = skillObject.jsonObject["descHash"]!!.jsonPrimitive.content,
        name = skillObject.jsonObject["name"]!!.jsonPrimitive.content,
        energy = skillObject.jsonObject["energy"]!!.jsonPrimitive.content.replace("/hit","").toInt(),
        iconPath = "${charFileName}_skill${skillIndex}",
        levelData = levelData,
        statusList = null

    )
}

//skillTreePoints
fun getDataFromSkillTreePoints(skillObject: JsonObject) : TraceTreeItem {
//Init Level Data
    val levelData : ArrayList<TracecTreeLevelData> = arrayListOf();
    val statusList : ArrayList<TracecTreeKeyStatus> = arrayListOf();
    val trigCostList : ArrayList<TracecTreeCost> = arrayListOf();

    if(skillObject.jsonObject["embedBonusSkill"] !== null && skillObject.jsonObject["embedBonusSkill"]!!.jsonObject["levelData"] !== null){
        for(data in skillObject.jsonObject["embedBonusSkill"]!!.jsonObject["levelData"]!!.jsonArray){
            //Init Cost Data
            val costData : ArrayList<TracecTreeCost> = arrayListOf()
            val paramData : ArrayList<Float> = arrayListOf()

            for(costItem in data.jsonObject["cost"]!!.jsonArray){
                costData.add(TracecTreeCost(costItem.jsonObject["id"]!!.jsonPrimitive.int,costItem.jsonObject["count"]!!.jsonPrimitive.int))
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

    if(skillObject.jsonObject["statusList"] !== null){
        for(status in skillObject.jsonObject["statusList"]!!.jsonArray){
            statusList.add(TracecTreeKeyStatus(status.jsonObject["key"]!!.jsonPrimitive.content,status.jsonObject["value"]!!.jsonPrimitive.float))
        }
    }
    if(skillObject.jsonObject["cost"] !== null){
        for(cost in skillObject.jsonObject["cost"]!!.jsonArray){
            trigCostList.add(TracecTreeCost(cost.jsonObject["id"]!!.jsonPrimitive.int,cost.jsonObject["count"]!!.jsonPrimitive.int))
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
        desc = "{XPRKey}${Res.string.Upgrade}{XPRValue}",
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
    traceTreeItem: TraceTreeItem,
    displayWidth: Dp,
    modifier: Modifier = Modifier,
    offset: ArrayList<Pair<Int, Int>>
) {
    val isSelected = (selectedId.value == selfId)

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
                onClick = { selectedId.value = if (selectedId.value == selfId) 0 else selfId },
            )
    ) {
        Image(
            bitmap = if (selfId <= 5) {
                UtilTools().getAssetsWebpByFileName(
                    UtilTools.ImageFolderType.CHAR_SKILL,
                    UtilTools().getImageNameByRegistName(traceTreeItem.iconPath, isCharNoGen = true)
                )
            } else {
                UtilTools().getAssetsWebpByFileName(
                    UtilTools.ImageFolderType.CHAR_SKILL_TREE,
                    traceTreeItem.iconPath
                )
            },
            contentDescription = "Skill Icon",
            modifier = Modifier.size(imgWidth).align(Alignment.Center)
        )
    }
}