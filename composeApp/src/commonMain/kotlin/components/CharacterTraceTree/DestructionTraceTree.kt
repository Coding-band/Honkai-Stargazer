package components.CharacterTraceTree

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import types.Path

@Composable
fun DestructionTraceTree(infoJson: JsonElement, displayWidth: Dp){
    val baseWidth = 325.dp; val baseHeight = 405.dp ; val displayHeight = displayWidth.times(baseWidth / baseHeight)

    var selectedId by remember { mutableStateOf(0) }

    val skillTreePointsArray = infoJson.jsonObject["skillTreePoints"]!!.jsonArray
    val skillTreeCoreArray = infoJson.jsonObject["skills"]!!.jsonArray

    val pointNormalATK = getDataFromSkills(skillTreeCoreArray[0].jsonObject) //普攻
    val pointSkill = getDataFromSkills(skillTreeCoreArray[1].jsonObject) //戰技
    val pointUltimate = getDataFromSkills(skillTreeCoreArray[2].jsonObject) //終結技
    val pointTalent = getDataFromSkills(skillTreeCoreArray[3].jsonObject) //天賦
    val pointSpecial = getDataFromSkills(skillTreeCoreArray[4].jsonObject) //祕技

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

    Box(modifier = Modifier.wrapContentSize()){
        Image(painterResource(Path.Destruction.iconAbyss), modifier = Modifier.aspectRatio(1f).fillMaxSize().padding(start = 12.dp, end = 12.dp).align(
            Alignment.BottomCenter
        ), contentDescription = "Path's Icon", alpha = 0.2f)
       Image(painterResource(Res.drawable.DestructionTraceTree), contentDescription = "Destruction Trace Tree", modifier = Modifier.aspectRatio(1f).size(displayWidth,displayHeight))
    }
}
