package ui.components.CharacterTraceTree

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import files.NihilityTraceTree
import files.RemembranceTraceTree
import files.Res
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.jetbrains.compose.resources.painterResource
import utils.app.Constants
import types.Path
import types.TraceTreeItem

//(x,y) base on Figma, no need to handly calculate
val remembranceOffsetList = arrayListOf(
    (0 to 0),//Empty
    (81 to 217),//普攻
    (191 to 217),//戰技
    (136 to 245),//終結技
    (205 to 156),//天賦
    (69 to 156),//祕技
    (261 to 135),

    (132 to 340),
    (108 to 103),
    (1 to 152),
    (36 to 90),
    (1 to 225),
    (242 to 90),

    (278 to 225),
    (78 to 338),
    (216 to 338),
    (76 to 18),
    (146 to 0),
    (218 to 18),

    (179 to 96), //憶靈天賦
    (136 to 174), //憶靈技
)

@Composable
fun RemembranceTraceTree(infoJson: JsonElement, displayWidth: Dp, selectedId: MutableState<Int>, charFileName: String){
    val displayHeight = displayWidth.times(Constants.TRACE_TREE_BASE_HEIGHT / Constants.TRACE_TREE_BASE_WIDTH)

    val selectedId = remember { mutableStateOf(0) }

    val skillTreePointsArray = infoJson.jsonObject["skillTreePoints"]!!.jsonArray
        .takeLast(infoJson.jsonObject["skillTreePoints"]!!.jsonArray.size.let { if(it > 4) it/2 else it }) //取後半段的技能樹點
        .sortedBy { it.jsonObject["anchor"]!!.jsonPrimitive.int }
    val skillTreeCoreArray = infoJson.jsonObject["skills"]!!.jsonArray

    val groupNormalATK = jsonArrayToIntArrayList(infoJson.jsonObject["skillGrouping"]!!.jsonArray[0].jsonArray)
    val groupSkill = jsonArrayToIntArrayList(infoJson.jsonObject["skillGrouping"]!!.jsonArray[1].jsonArray)
    val groupUltimate = jsonArrayToIntArrayList(infoJson.jsonObject["skillGrouping"]!!.jsonArray[2].jsonArray)
    val groupTalent = jsonArrayToIntArrayList(infoJson.jsonObject["skillGrouping"]!!.jsonArray[3].jsonArray)
    val groupSpecial = jsonArrayToIntArrayList(infoJson.jsonObject["skillGrouping"]!!.jsonArray[4].jsonArray)

    val pointNormalATK = getDataFromSkills(skillTreeCoreArray.filter { obj -> groupNormalATK.indexOf(obj.jsonObject["id"]!!.jsonPrimitive.int) != -1 } as ArrayList<JsonElement>, charFileName, 1, infoJson) //普攻
    val pointSkill = getDataFromSkills(skillTreeCoreArray.filter { obj -> groupSkill.indexOf(obj.jsonObject["id"]!!.jsonPrimitive.int) != -1 } as ArrayList<JsonElement>, charFileName, 2, infoJson) //戰技
    val pointUltimate = getDataFromSkills(skillTreeCoreArray.filter { obj -> groupUltimate.indexOf(obj.jsonObject["id"]!!.jsonPrimitive.int) != -1 } as ArrayList<JsonElement>, charFileName, 3, infoJson) //終結技
    val pointTalent = getDataFromSkills(skillTreeCoreArray.filter { obj -> groupTalent.indexOf(obj.jsonObject["id"]!!.jsonPrimitive.int) != -1 } as ArrayList<JsonElement>, charFileName, 4, infoJson) //天賦
    val pointSpecial = getDataFromSkills(skillTreeCoreArray.filter { obj -> groupSpecial.indexOf(obj.jsonObject["id"]!!.jsonPrimitive.int) != -1 } as ArrayList<JsonElement>, charFileName, 6, infoJson) //祕技

    val point6 = getDataFromSkillTreePoints(skillTreePointsArray[0].jsonObject, infoJson)
    val point12 = getDataFromSkillTreePoints(skillTreePointsArray[0].jsonObject["children"]!!.jsonArray[0].jsonObject, infoJson)
    val point13 = getDataFromSkillTreePoints(skillTreePointsArray[0].jsonObject["children"]!!.jsonArray[1].jsonObject, infoJson)

    val point7 = getDataFromSkillTreePoints(skillTreePointsArray[1].jsonObject, infoJson)
    val point14 = getDataFromSkillTreePoints(skillTreePointsArray[1].jsonObject["children"]!!.jsonArray[0].jsonObject, infoJson)
    val point15 = getDataFromSkillTreePoints(skillTreePointsArray[1].jsonObject["children"]!!.jsonArray[1].jsonObject, infoJson)

    val point8 = getDataFromSkillTreePoints(skillTreePointsArray[2].jsonObject, infoJson)
    val point16 = getDataFromSkillTreePoints(skillTreePointsArray[2].jsonObject["children"]!!.jsonArray[0].jsonObject, infoJson)
    val point17 = getDataFromSkillTreePoints(skillTreePointsArray[2].jsonObject["children"]!!.jsonArray[0].jsonObject["children"]!!.jsonArray[0].jsonObject, infoJson)
    val point18 = getDataFromSkillTreePoints(skillTreePointsArray[2].jsonObject["children"]!!.jsonArray[0].jsonObject["children"]!!.jsonArray[0].jsonObject["children"]!!.jsonArray[0].jsonObject, infoJson)

    val point9 = getDataFromSkillTreePoints(skillTreePointsArray[3].jsonObject, infoJson)
    val point10 = getDataFromSkillTreePoints(skillTreePointsArray[3].jsonObject["children"]!!.jsonArray[0].jsonObject, infoJson)
    val point11 = getDataFromSkillTreePoints(skillTreePointsArray[3].jsonObject["children"]!!.jsonArray[1].jsonObject, infoJson)

    //憶靈
    var pointServantTalent : ArrayList<TraceTreeItem>? = null
    var pointServantSkill : ArrayList<TraceTreeItem>? = null

    val hasServant = infoJson.jsonObject["hasServant"]?.jsonPrimitive?.boolean
    val servantInfoJson = infoJson.jsonObject["servant"]
    if(servantInfoJson != null && hasServant == true){
        //val servantSkillTreePointsArray = servantInfoJson.jsonObject["skillTreePoints"]!!.jsonArray.sortedBy { it.jsonObject["anchor"]!!.jsonPrimitive.int }
        val servantSkillTreeCoreArray = servantInfoJson.jsonObject["skills"]!!.jsonArray

        val groupServantTalent = jsonArrayToIntArrayList(servantInfoJson.jsonObject["skillGrouping"]!!.jsonArray[0].jsonArray)
        val groupServantSkill = jsonArrayToIntArrayList(servantInfoJson.jsonObject["skillGrouping"]!!.jsonArray[1].jsonArray)

        pointServantTalent = getDataFromSkills(servantSkillTreeCoreArray.filter { obj -> groupServantTalent.indexOf(obj.jsonObject["id"]!!.jsonPrimitive.int) != -1 } as ArrayList<JsonElement>, charFileName, 0, infoJson, isServant = true) //憶靈天賦
        pointServantSkill = getDataFromSkills(servantSkillTreeCoreArray.filter { obj -> groupServantSkill.indexOf(obj.jsonObject["id"]!!.jsonPrimitive.int) != -1 } as ArrayList<JsonElement>, charFileName, 1, infoJson, isServant = true) //憶靈技
    }

    Box(modifier = Modifier.size(displayWidth,displayHeight)){
        Image(painterResource(Path.Remembrance.iconAbyss),
            modifier = Modifier.aspectRatio(1f).fillMaxSize().padding(start = 12.dp, end = 12.dp).align(
            Alignment.BottomCenter
        ), contentDescription = "Path's Icon", alpha = 0.2f)
        Image(painterResource(Res.drawable.RemembranceTraceTree),
            contentDescription = "Nihility Trace Tree",
            modifier = Modifier
                .size(displayWidth,displayHeight)
        )

        TraceTreeBtn(selectedId, 1, pointNormalATK, displayWidth, offset = remembranceOffsetList)
        TraceTreeBtn(selectedId, 2, pointSkill, displayWidth, offset = remembranceOffsetList)
        TraceTreeBtn(selectedId, 3, pointUltimate, displayWidth, offset = remembranceOffsetList)
        TraceTreeBtn(selectedId, 4, pointTalent, displayWidth, offset = remembranceOffsetList)
        TraceTreeBtn(selectedId, 5, pointSpecial, displayWidth, offset = remembranceOffsetList)

        TraceTreeBtn(selectedId, 6, arrayListOf(point6), displayWidth, offset = remembranceOffsetList)
        TraceTreeBtn(selectedId, 7, arrayListOf(point7), displayWidth, offset = remembranceOffsetList)
        TraceTreeBtn(selectedId, 8, arrayListOf(point8), displayWidth, offset = remembranceOffsetList)
        TraceTreeBtn(selectedId, 9, arrayListOf(point9), displayWidth, offset = remembranceOffsetList)
        TraceTreeBtn(selectedId, 10, arrayListOf(point10), displayWidth, offset = remembranceOffsetList)
        TraceTreeBtn(selectedId, 11, arrayListOf(point11), displayWidth, offset = remembranceOffsetList)
        TraceTreeBtn(selectedId, 12, arrayListOf(point12), displayWidth, offset = remembranceOffsetList)
        TraceTreeBtn(selectedId, 13, arrayListOf(point13), displayWidth, offset = remembranceOffsetList)
        TraceTreeBtn(selectedId, 14, arrayListOf(point14), displayWidth, offset = remembranceOffsetList)
        TraceTreeBtn(selectedId, 15, arrayListOf(point15), displayWidth, offset = remembranceOffsetList)
        TraceTreeBtn(selectedId, 16, arrayListOf(point16), displayWidth, offset = remembranceOffsetList)
        TraceTreeBtn(selectedId, 17, arrayListOf(point17), displayWidth, offset = remembranceOffsetList)
        TraceTreeBtn(selectedId, 18, arrayListOf(point18), displayWidth, offset = remembranceOffsetList)

        if(servantInfoJson != null && hasServant == true){
            TraceTreeBtn(selectedId, 19, pointServantTalent!!, displayWidth, offset = remembranceOffsetList, isServant = true)
            TraceTreeBtn(selectedId, 20, pointServantSkill!!, displayWidth, offset = remembranceOffsetList, isServant = true)
        }
    }
}
