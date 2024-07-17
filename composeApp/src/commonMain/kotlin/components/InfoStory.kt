package components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mohamedrejeb.richeditor.model.RichTextState
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material.RichText
import files.CharacterStory
import files.Res
import files.phorphos_chats_circle_regular
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import types.Constants
import utils.FontSizeNormal16
import utils.UtilTools

@Composable
fun InfoStory(infoJson : JsonElement, isLcStory : Boolean = false){
    val storyList : ArrayList<String> = arrayListOf(infoJson.jsonObject["descHash"]!!.jsonPrimitive.content)
    val storyTitleList : ArrayList<String> = arrayListOf(UtilTools().removeStringResDoubleQuotes(Res.string.CharacterStory))
    if(!isLcStory && infoJson.jsonObject["storyItems"] != null){
        for(storyItem in infoJson.jsonObject["storyItems"]!!.jsonArray){
            storyList.add(storyItem.jsonObject["text"]!!.jsonPrimitive.content)
            storyTitleList.add(storyItem.jsonObject["title"]!!.jsonPrimitive.content)
        }
    }

    val currStoryIndex = remember { mutableStateOf(0) }
    val richTextState : RichTextState = rememberRichTextState()
    val interactionSource = remember { MutableInteractionSource() }

    LaunchedEffect(interactionSource) {
        var isLongClick = false

        interactionSource.interactions.collectLatest { interaction ->
            when (interaction) {
                is PressInteraction.Press -> {
                    isLongClick = false
                    delay(1000)
                    isLongClick = true
                    currStoryIndex.value = (currStoryIndex.value + storyList.size - 1) % storyList.size
                }

                is PressInteraction.Release -> {
                    if (isLongClick.not()) {
                        currStoryIndex.value = (currStoryIndex.value + 1) % storyList.size
                    }

                }

            }
        }
    }

    Column(modifier = Modifier.fillMaxWidth().statusBarsPadding().padding(start = Constants.SCREEN_SAVE_PADDING, end = Constants.SCREEN_SAVE_PADDING)){
        TitleHeader(iconRId = Res.drawable.phorphos_chats_circle_regular, titleString = storyTitleList[currStoryIndex.value])

        //Empty Blank
        Spacer(modifier = Modifier.height(24.dp))

        RichText(
            state = richTextState.setHtml(storyList[currStoryIndex.value]),
            color = Color.White,
            style = FontSizeNormal16(),
            modifier = Modifier.fillMaxWidth().wrapContentHeight().clickable(enabled = true, indication = null, interactionSource = interactionSource, onClick = {})
        )
    }
}