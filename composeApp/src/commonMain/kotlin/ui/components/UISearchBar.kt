package ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import files.Res
import files.UIDEnter
import files.ui_icon_close
import files.ui_icon_search
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import utils.app.FontSizeNormal14
import utils.app.TextFieldValueSaver

@Composable
fun UISearchBar(
    modifier: Modifier = Modifier,
    inputString: MutableState<String>,
    hintRes: StringResource = Res.string.UIDEnter,
    searchIcon: DrawableResource? = null,
    cancelIcon: DrawableResource? = null,
    onClick: () -> Unit = {},
    onCancel: (text: MutableState<TextFieldValue>, inputString: MutableState<String>) -> Unit = { text, inputStr ->
        inputStr.value = ""
        text.value = TextFieldValue("")
    },
    isFocus: Boolean = false
) {
    val text = rememberSaveable(stateSaver = TextFieldValueSaver) { mutableStateOf(TextFieldValue("")) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(isFocus){
        if (isFocus) {
            focusRequester.requestFocus()
        } else {
            focusManager.clearFocus()
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(46.dp)
            .background(Color(0xFFDDDDDD), shape = RoundedCornerShape(23.dp)).clip(shape = RoundedCornerShape(23.dp)).clickable {  }
    ) {
        Box(
            modifier = Modifier.padding(5.dp).border(width = 1.dp, color = Color(0x0F000000), shape = RoundedCornerShape(23.dp))
        ) {
            Row {
                Box(modifier = Modifier.clip(CircleShape).clickable { inputString.value = text.value.text ; onClick.invoke() }) {
                    Image(
                        painter = painterResource(searchIcon ?: Res.drawable.ui_icon_search),
                        contentDescription = "UISearch Icon",
                        modifier = Modifier.fillMaxHeight().padding(8.dp).aspectRatio(1f),
                        colorFilter = ColorFilter.tint(Color(0xCC000000))
                    )
                }

                BasicTextField(
                    value = text.value,
                    onValueChange = { text.value = it },
                    singleLine = true,
                    textStyle = FontSizeNormal14(),
                    modifier = Modifier.fillMaxWidth().align(Alignment.CenterVertically).weight(1f).padding(start = 16.dp).focusRequester(focusRequester),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = {
                        inputString.value = text.value.text ; onClick.invoke()
                    })
                )
                //Box(Modifier.width(2.dp).fillMaxHeight().padding(top = 8.dp, bottom =  8.dp))
                Box(modifier = Modifier.clip(CircleShape).clickable {
                    onCancel.invoke(text,inputString)
                    text.value = TextFieldValue("")
                    //if(text.isNotEmpty()) text = "" else if (isVisible.value) isVisible.value = false
                }) {
                    Image(
                        painter = painterResource(cancelIcon ?: Res.drawable.ui_icon_close),
                        contentDescription = "UISearch Icon",
                        modifier = Modifier.fillMaxHeight().padding(8.dp).aspectRatio(1f),
                        colorFilter = ColorFilter.tint(Color(0xCC000000))
                    )
                }
            }
        }
    }
}