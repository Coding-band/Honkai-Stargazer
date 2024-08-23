/*
 * Project Honkai Stargazer and app Stargazer (星穹觀星者) were
 * Organized & Develop by Coding Band.
 * Copyright © 2024 Coding Band 版權所有
 */

package components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.voc.honkai_stargazer.component.CharacterCard
import files.MatchRequirementChar
import files.PressToView
import files.RequirementAND
import files.RequirementOR
import files.Res
import files.ic_plus
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.jetbrains.compose.resources.painterResource
import types.Character
import types.Constants.Companion.CARD_BG_COLOR_RARE_UNKNOWN
import types.Constants.Companion.CHAR_CARD_HEIGHT
import types.Constants.Companion.CHAR_CARD_WIDTH
import types.getTeamAdviceById
import utils.FontSizeNormal12
import utils.Language
import utils.TextColorNormalDim
import utils.UtilTools
import utils.annotation.YouMustKiddingMe


private lateinit var dialogTitleLocal : MutableState<String>
private lateinit var dialogDisplayLocal: MutableState<Boolean>
private lateinit var dialogLastTrigTypeLocal: MutableState<String>
private lateinit var dialogComponentLocal: MutableState<@Composable () -> Unit>
private var lastTrigTypeTag = "ADVICE_TEAMMATE"

@YouMustKiddingMe
@Composable
fun MultiChoiceCard(
    choiceStr: String,
    leaderInfo: Character,
    dialogTitle: MutableState<String>,
    dialogDisplay: MutableState<Boolean>,
    dialogLastTrigType: MutableState<String>,
    dialogComponent: MutableState<@Composable () -> Unit>
) {
    dialogTitleLocal = dialogTitle
    dialogDisplayLocal = dialogDisplay
    dialogComponentLocal = dialogComponent
    dialogLastTrigTypeLocal = dialogLastTrigType

    val interactionSource = remember { MutableInteractionSource() }
    val typeList = choiceStr.split("||");
    val typeInUI : ArrayList<String> = arrayListOf();
    val typeIsAdd : ArrayList<Boolean> = arrayListOf();//[true, false, false] = 1+2 , but 2 not + 3

    for ((x, typeData) in typeList.withIndex()) {
        var pos = 0
        val typeSplitArray = typeData.split("&&")
        typeSplitArray.withIndex().forEach { (index, item) ->
            if (index > 0) pos++
            if(typeInUI.size > (x+pos)){typeInUI[x+pos] = item} else typeInUI.add(x+pos, item)
            if(typeIsAdd.size > (x+pos)){typeIsAdd[x+pos] = index + 1 != typeSplitArray.size;} else typeIsAdd.add(x+pos, index + 1 != typeSplitArray.size)
        }
    }

    if(typeIsAdd.size >= 2 && typeIsAdd[1]){
        typeInUI.add(1,"EMPTY")
        typeIsAdd.add(1,false)
    }
    if(typeInUI.size == 2 && typeIsAdd.size >= 1 && !typeIsAdd[0]){
        typeInUI.add(1,"EMPTY")
        typeIsAdd.add(1,false)
    }

    var dialogTitleProgress = ""

    val requirePair = getKeywordsRequire(choiceStr)
    requirePair.first.withIndex().forEach { (index, choice) ->
        dialogTitleProgress +=
            if(choice.toIntOrNull() != null && choice.toInt() in 1000 .. 10000){
                Character.getCharacterItemFromJSON(choice, Language.TextLanguageInstance).displayName ?: ""
            } else choice.toIntOrNull()?.let { getTeamAdviceById(it).cnName } //Edit when translation done

        dialogTitleProgress += if(index + 1 < requirePair.first.size){
                UtilTools().removeStringResDoubleQuotes(if(requirePair.second){Res.string.RequirementAND} else Res.string.RequirementOR)
            } else {
                ""
            }
    }
    val dialogTitleTmp = UtilTools().removeStringResDoubleQuotes(Res.string.MatchRequirementChar).replace("$"+"{1}",dialogTitleProgress)

    Box(
        modifier = Modifier
            .defaultMinSize(CHAR_CARD_WIDTH, CHAR_CARD_HEIGHT)
            .clip(
                RoundedCornerShape(
                    topEnd = 15.dp,
                    topStart = 4.dp,
                    bottomEnd = 4.dp,
                    bottomStart = 4.dp
                )
            )
            .background(
                Brush.verticalGradient(colors = CARD_BG_COLOR_RARE_UNKNOWN)
            )
            .clickable(enabled = true, indication = null, interactionSource = remember { MutableInteractionSource() }, onClick = {
                if(dialogDisplayLocal.value && dialogLastTrigTypeLocal.value == lastTrigTypeTag){
                    dialogDisplayLocal.value = false
                }else {
                    dialogTitleLocal.value = dialogTitleTmp
                    dialogDisplayLocal.value = true
                    dialogComponentLocal.value = { MultiChoiceDialog(getMatchRequireCharList(requirePair,leaderInfo)) }
                }
                dialogLastTrigTypeLocal.value = lastTrigTypeTag
            })
    ) {
        Column {
            Column(Modifier.fillMaxWidth().aspectRatio(1f)) {
                //Row 1
                Row(modifier = Modifier.fillMaxSize().weight(1f), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically){
                    if(typeInUI.size >= 1){ Box(Modifier.weight(1f), contentAlignment = Alignment.Center){getIconByInfo(typeInUI[0]) } }
                    if(typeIsAdd[0]){
                        Image(
                            contentDescription = null,
                            modifier = Modifier.fillMaxHeight().width(10.dp),
                            painter = painterResource(Res.drawable.ic_plus)
                        )
                    }
                    if(typeInUI.size >= 2 && typeInUI[1] != "EMPTY"){ Box(Modifier.weight(1f)){getIconByInfo(typeInUI[1]) } }
                }

                //Row 2
                if(typeInUI.size > 2){
                    Row(modifier = Modifier.fillMaxSize().weight(1f), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically){
                        if(typeInUI.size >= 3){ Box(Modifier.weight(1f), contentAlignment = Alignment.Center){getIconByInfo(typeInUI[2]) } }
                        if(typeIsAdd[2]){
                            Image(
                                contentDescription = null,
                                modifier = Modifier.fillMaxHeight().width(10.dp),
                                painter = painterResource(Res.drawable.ic_plus)
                            )
                        }
                        if(typeInUI.size >= 4){ Box(Modifier.weight(1f), contentAlignment = Alignment.Center){getIconByInfo(typeInUI[3]) } }
                    }
                }
            }
            Row(
                Modifier.fillMaxWidth().background(Color(0xFF222222)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = UtilTools().removeStringResDoubleQuotes(Res.string.PressToView),
                    textAlign = TextAlign.Center,
                    color = TextColorNormalDim,
                    fontSize = FontSizeNormal12().fontSize,
                    maxLines = 1
                )
            }
            Spacer(modifier = Modifier.height(2.dp))
        }
    }
}

fun getMatchRequireCharList(requirePair: Pair<ArrayList<String>, Boolean>, leaderInfo: Character): ArrayList<Character> {
    val matchList: ArrayList<Character> = arrayListOf()
    val charWeightList = UtilTools.TemporaryFunction().getCharWeightListJson().jsonObject
    charWeightList.keys.filter { officialId -> forLoopOfMRCL(requirePair,leaderInfo, charWeightList, officialId)}.forEach { charId ->
        val char = Character.getCharacterItemFromJSON(charId, Language.TextLanguageInstance)
        matchList.add(char)
    }
    return matchList
}

fun forLoopOfMRCL(
    requirePair: Pair<ArrayList<String>, Boolean>,
    leaderInfo: Character,
    charWeightList: JsonObject,
    officialId: String
): Boolean{
    for (typeStr in requirePair.first) {
        //This will cause unable to search in jsonArray[n > 0]
        for(typeId in charWeightList[officialId]!!.jsonArray[0].jsonObject["keywords"]!!.jsonArray) {
            if ((typeId.jsonPrimitive.content == typeStr || officialId == typeStr) && officialId.toInt() != leaderInfo.officialId) {
                return !requirePair.second
            }
        }
    }
    return false
}

@Composable
fun getIconByInfo(infoId : String){
    if(infoId.toIntOrNull() != null){
        Box(Modifier.clip(CircleShape).padding(4.dp)) {
            if (infoId.toInt() in 1000..9999) {
                val characterSearch =
                    Character.charListJson.jsonArray.find { char -> char.jsonObject["charId"]!!.jsonPrimitive.content == infoId }
                        ?: return
                Image(
                    contentDescription = null,
                    modifier = Modifier
                        .background(Color(0x66000000), shape = CircleShape).wrapContentHeight()
                        .aspectRatio(1f).clip(CircleShape),
                    bitmap = Character.getCharacterImageFromFileName(
                        UtilTools.ImageFolderType.CHAR_ICON,
                        characterSearch.jsonObject["name"]!!.jsonPrimitive.content
                    )
                )
            } else {
                Image(
                    contentDescription = null,
                    modifier = Modifier
                        .background(Color(0x66000000), shape = CircleShape).wrapContentHeight()
                        .aspectRatio(1f).clip(CircleShape),
                    painter = painterResource(getTeamAdviceById(infoId.toInt()).iconWhite)
                )
            }
        }
    }
}

fun getKeywordsRequire(typeListStr: String) : Pair<ArrayList<String>, Boolean>{
    val typeList = typeListStr.split("||");
    var typeListFinal : ArrayList<String> = arrayListOf();
    for ((x, typeItem) in typeList.withIndex()) {
        typeItem.split("&&").forEach { str ->
            typeListFinal.add(str)
        }
    }
    return (typeListFinal to (typeList[0].split("&&").size > 1));
}

@Composable
fun MultiChoiceDialog(choiceList: ArrayList<Character>) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(CHAR_CARD_WIDTH),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        for (selectedChar in choiceList) {
            item { CharacterCard(selectedChar) }
        }
    }
}