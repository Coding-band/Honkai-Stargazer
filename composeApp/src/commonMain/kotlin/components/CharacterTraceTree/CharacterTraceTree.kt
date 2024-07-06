package components.CharacterTraceTree

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
import types.Path
import types.TraceTreeItem
import types.TracecTreeCost
import types.TracecTreeKeyStatus
import types.TracecTreeLevelData

@Composable
fun CharacterTraceTree(infoJson : JsonElement, path: Path){
    val displayWidth = getScreenSizeInfo().wDP - 36.dp;
    Column(modifier = Modifier.fillMaxWidth().statusBarsPadding().padding(start = 18.dp, end = 18.dp)){
        TitleHeader(iconRId = Res.drawable.phorphos_tree_structure_fill, titleRId = Res.string.TraceTree)

        //Empty Blank
        Spacer(modifier = Modifier.height(24.dp))

        when(path){
            Path.Destruction -> DestructionTraceTree(infoJson, displayWidth)
            else -> {}
        }


    }
}

//skills[n]
fun getDataFromSkills(skillObject: JsonObject) : TraceTreeItem {
    //Init Level Data
    var levelData : ArrayList<TracecTreeLevelData> = arrayListOf();

    for(data in skillObject.jsonObject["levelData"]!!.jsonArray){
        //Init Cost Data
        var costData : ArrayList<TracecTreeCost> = arrayListOf()
        var paramData : ArrayList<Float> = arrayListOf()

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
        iconPath = skillObject.jsonObject["iconPath"]!!.jsonPrimitive.content,
        levelData = levelData,
        statusList = null

    )
}

//skillTreePoints
fun getDataFromSkillTreePoints(skillObject: JsonObject) : TraceTreeItem {
//Init Level Data
    var levelData : ArrayList<TracecTreeLevelData> = arrayListOf();
    var statusList : ArrayList<TracecTreeKeyStatus> = arrayListOf();
    var trigCostList : ArrayList<TracecTreeCost> = arrayListOf();

    if(skillObject.jsonObject["embedBonusSkill"] !== null && skillObject.jsonObject["embedBonusSkill"]!!.jsonObject["levelData"] !== null){
        for(data in skillObject.jsonObject["embedBonusSkill"]!!.jsonObject["levelData"]!!.jsonArray){
            //Init Cost Data
            var costData : ArrayList<TracecTreeCost> = arrayListOf()
            var paramData : ArrayList<Float> = arrayListOf()

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
fun TraceTreeCoreBtn(){

}