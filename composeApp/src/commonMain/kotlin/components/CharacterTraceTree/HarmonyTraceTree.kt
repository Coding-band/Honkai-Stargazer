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
import files.HarmonyTraceTree
import files.Res
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import org.jetbrains.compose.resources.painterResource
import types.Constants
import types.Path

//(x,y) base on Figma, no need to handly calculate
val harmonyOffsetList = arrayListOf(
    (0 to 0),//Empty
    (72 to 163),//普攻
    (202 to 163),//戰技
    (136 to 154),//終結技
    (136 to 80),//天賦
    (136 to 228),//祕技
    (243 to 80),

    (22 to 80),
    (133 to 0),
    (147 to 312),
    (293 to 146),
    (274 to 193),
    (253 to 240),

    (0 to 146),
    (21 to 193),
    (47 to 240),
    (214 to 28),
    (83 to 28),
    (147 to 372),
)

@Composable
fun HarmonyTraceTree(infoJson: JsonElement, displayWidth: Dp, selectedId: MutableState<Int>, charFileName: String){
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

    val point7 = getDataFromSkillTreePoints(skillTreePointsArray[1].jsonObject)
    val point13 = getDataFromSkillTreePoints(skillTreePointsArray[1].jsonObject["children"]!!.jsonArray[0].jsonObject)
    val point14 = getDataFromSkillTreePoints(skillTreePointsArray[1].jsonObject["children"]!!.jsonArray[0].jsonObject["children"]!!.jsonArray[0].jsonObject)

    val point8 = getDataFromSkillTreePoints(skillTreePointsArray[2].jsonObject)
    val point16 = getDataFromSkillTreePoints(skillTreePointsArray[2].jsonObject["children"]!!.jsonArray[0].jsonObject)
    val point17 = getDataFromSkillTreePoints(skillTreePointsArray[2].jsonObject["children"]!!.jsonArray[0].jsonObject["children"]!!.jsonArray[0].jsonObject)
    val point18 = getDataFromSkillTreePoints(skillTreePointsArray[2].jsonObject["children"]!!.jsonArray[0].jsonObject["children"]!!.jsonArray[1].jsonObject)

    val point9 = getDataFromSkillTreePoints(skillTreePointsArray[3].jsonObject)
    val point12 = getDataFromSkillTreePoints(skillTreePointsArray[3].jsonObject["children"]!!.jsonArray[0].jsonObject)
    val point15 = getDataFromSkillTreePoints(skillTreePointsArray[3].jsonObject["children"]!!.jsonArray[1].jsonObject)


    Box(modifier = Modifier.size(displayWidth,displayHeight)){
        Image(painterResource(Path.Harmony.iconAbyss),
            modifier = Modifier.aspectRatio(1f).fillMaxSize().padding(start = 12.dp, end = 12.dp).align(
            Alignment.BottomCenter
        ), contentDescription = "Path's Icon", alpha = 0.2f)
        Image(painterResource(Res.drawable.HarmonyTraceTree),
            contentDescription = "Harmony Trace Tree",
            modifier = Modifier
                .size(displayWidth,displayHeight)
        )

        TraceTreeBtn(selectedId, 1, pointNormalATK, displayWidth, offset = harmonyOffsetList)
        TraceTreeBtn(selectedId, 2, pointSkill, displayWidth, offset = harmonyOffsetList)
        TraceTreeBtn(selectedId, 3, pointUltimate, displayWidth, offset = harmonyOffsetList)
        TraceTreeBtn(selectedId, 4, pointTalent, displayWidth, offset = harmonyOffsetList)
        TraceTreeBtn(selectedId, 5, pointSpecial, displayWidth, offset = harmonyOffsetList)

        TraceTreeBtn(selectedId, 6, point6, displayWidth, offset = harmonyOffsetList)
        TraceTreeBtn(selectedId, 7, point7, displayWidth, offset = harmonyOffsetList)
        TraceTreeBtn(selectedId, 8, point8, displayWidth, offset = harmonyOffsetList)
        TraceTreeBtn(selectedId, 9, point9, displayWidth, offset = harmonyOffsetList)
        TraceTreeBtn(selectedId, 10, point10, displayWidth, offset = harmonyOffsetList)
        TraceTreeBtn(selectedId, 11, point11, displayWidth, offset = harmonyOffsetList)
        TraceTreeBtn(selectedId, 12, point12, displayWidth, offset = harmonyOffsetList)
        TraceTreeBtn(selectedId, 13, point13, displayWidth, offset = harmonyOffsetList)
        TraceTreeBtn(selectedId, 14, point14, displayWidth, offset = harmonyOffsetList)
        TraceTreeBtn(selectedId, 15, point15, displayWidth, offset = harmonyOffsetList)
        TraceTreeBtn(selectedId, 16, point16, displayWidth, offset = harmonyOffsetList)
        TraceTreeBtn(selectedId, 17, point17, displayWidth, offset = harmonyOffsetList)
        TraceTreeBtn(selectedId, 18, point18, displayWidth, offset = harmonyOffsetList)
    }
}
