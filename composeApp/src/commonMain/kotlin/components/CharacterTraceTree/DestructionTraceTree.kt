package components.CharacterTraceTree

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
import files.DestructionTraceTree
import files.Res
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import org.jetbrains.compose.resources.painterResource
import types.Constants
import types.Path

val offsetListLeftTop = arrayListOf(
    (0 to 0),//Empty
    (56 to 189),//普攻
    (219 to 189),//戰技
    (138 to 217),//終結技
    (138 to 134),//天賦
    (138 to 298),//祕技
    (52 to 308),

    (215 to 308),
    (134 to 52),
    (150 to 372),
    (28 to 258),
    (0 to 208),
    (22 to 159),

    (265 to 258),
    (293 to 208),
    (275 to 159),
    (150 to 0),
    (84 to 14),
    (216 to 14),
)

@Composable
fun DestructionTraceTree(infoJson: JsonElement, displayWidth: Dp, selectedId: MutableState<Int>, charFileName: String){
    val displayHeight = displayWidth.times(Constants.TRACE_TREE_BASE_HEIGHT / Constants.TRACE_TREE_BASE_WIDTH)

    val selectedId = remember { mutableStateOf(0) }

    val skillTreePointsArray = infoJson.jsonObject["skillTreePoints"]!!.jsonArray
    val skillTreeCoreArray = infoJson.jsonObject["skills"]!!.jsonArray

    val pointNormalATK = getDataFromSkills(skillTreeCoreArray[0].jsonObject, charFileName, 1) //普攻
    val pointSkill = getDataFromSkills(skillTreeCoreArray[1].jsonObject, charFileName, 2) //戰技
    val pointUltimate = getDataFromSkills(skillTreeCoreArray[2].jsonObject, charFileName, 3) //終結技
    val pointTalent = getDataFromSkills(skillTreeCoreArray[3].jsonObject, charFileName, 4) //天賦
    val pointSpecial = getDataFromSkills(skillTreeCoreArray[4].jsonObject, charFileName, 6) //祕技

    val point6 = getDataFromSkillTreePoints(skillTreePointsArray[0].jsonObject)
    val point10 = getDataFromSkillTreePoints(skillTreePointsArray[0].jsonObject["children"]!!.jsonArray[0].jsonObject)
    val point11 = getDataFromSkillTreePoints(skillTreePointsArray[0].jsonObject["children"]!!.jsonArray[0].jsonObject["children"]!!.jsonArray[0].jsonObject)
    val point12 = getDataFromSkillTreePoints(skillTreePointsArray[0].jsonObject["children"]!!.jsonArray[0].jsonObject["children"]!!.jsonArray[0].jsonObject["children"]!!.jsonArray[0].jsonObject)

    val point7 = getDataFromSkillTreePoints(skillTreePointsArray[1].jsonObject)
    val point13 = getDataFromSkillTreePoints(skillTreePointsArray[1].jsonObject["children"]!!.jsonArray[0].jsonObject)
    val point14 = getDataFromSkillTreePoints(skillTreePointsArray[1].jsonObject["children"]!!.jsonArray[0].jsonObject["children"]!!.jsonArray[0].jsonObject)
    val point15 = getDataFromSkillTreePoints(skillTreePointsArray[1].jsonObject["children"]!!.jsonArray[0].jsonObject["children"]!!.jsonArray[0].jsonObject["children"]!!.jsonArray[0].jsonObject)

    val point8 = getDataFromSkillTreePoints(skillTreePointsArray[2].jsonObject)
    val point16 = getDataFromSkillTreePoints(skillTreePointsArray[2].jsonObject["children"]!!.jsonArray[0].jsonObject)
    val point17 = getDataFromSkillTreePoints(skillTreePointsArray[2].jsonObject["children"]!!.jsonArray[0].jsonObject["children"]!!.jsonArray[0].jsonObject)
    val point18 = getDataFromSkillTreePoints(skillTreePointsArray[2].jsonObject["children"]!!.jsonArray[0].jsonObject["children"]!!.jsonArray[1].jsonObject)

    val point9 = getDataFromSkillTreePoints(skillTreePointsArray[3].jsonObject)

    Box(modifier = Modifier.size(displayWidth,displayHeight)){
        Image(painterResource(Path.Destruction.iconAbyss),
            modifier = Modifier.aspectRatio(1f).fillMaxSize().padding(start = 12.dp, end = 12.dp).align(
            Alignment.BottomCenter
        ), contentDescription = "Path's Icon", alpha = 0.2f)
        Image(painterResource(Res.drawable.DestructionTraceTree),
            contentDescription = "Destruction Trace Tree",
            modifier = Modifier
                .size(displayWidth,displayHeight)
        )

        TraceTreeBtn(selectedId, 1, pointNormalATK, displayWidth, offset = offsetListLeftTop)
        TraceTreeBtn(selectedId, 2, pointSkill, displayWidth, offset = offsetListLeftTop)
        TraceTreeBtn(selectedId, 3, pointUltimate, displayWidth, offset = offsetListLeftTop)
        TraceTreeBtn(selectedId, 4, pointTalent, displayWidth, offset = offsetListLeftTop)
        TraceTreeBtn(selectedId, 5, pointSpecial, displayWidth, offset = offsetListLeftTop)

        TraceTreeBtn(selectedId, 6, point6, displayWidth, offset = offsetListLeftTop)
        TraceTreeBtn(selectedId, 7, point7, displayWidth, offset = offsetListLeftTop)
        TraceTreeBtn(selectedId, 8, point8, displayWidth, offset = offsetListLeftTop)
        TraceTreeBtn(selectedId, 9, point9, displayWidth, offset = offsetListLeftTop)
        TraceTreeBtn(selectedId, 10, point10, displayWidth, offset = offsetListLeftTop)
        TraceTreeBtn(selectedId, 11, point11, displayWidth, offset = offsetListLeftTop)
        TraceTreeBtn(selectedId, 12, point12, displayWidth, offset = offsetListLeftTop)
        TraceTreeBtn(selectedId, 13, point13, displayWidth, offset = offsetListLeftTop)
        TraceTreeBtn(selectedId, 14, point14, displayWidth, offset = offsetListLeftTop)
        TraceTreeBtn(selectedId, 15, point15, displayWidth, offset = offsetListLeftTop)
        TraceTreeBtn(selectedId, 16, point16, displayWidth, offset = offsetListLeftTop)
        TraceTreeBtn(selectedId, 17, point17, displayWidth, offset = offsetListLeftTop)
        TraceTreeBtn(selectedId, 18, point18, displayWidth, offset = offsetListLeftTop)
    }
}
