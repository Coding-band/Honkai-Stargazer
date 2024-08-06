package components

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import files.Res
import files.UIDEnter
import files.ui_icon_search
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import utils.FontSizeNormal14

@Composable
fun UISearchBar(
    modifier: Modifier = Modifier,
    inputString: MutableState<String>,
    hintRes: StringResource = Res.string.UIDEnter,
    searchIcon: DrawableResource? = null,
    isAvailable: Boolean = true,
    onClick: () -> Unit = {}
) {
    var text by rememberSaveable { mutableStateOf("") }

    Box(
        modifier = modifier.fillMaxWidth().height(46.dp).background(Color(0xFFDDDDDD), shape = RoundedCornerShape(23.dp)).clip(shape = RoundedCornerShape(23.dp)).clickable { if(isAvailable) onClick() }
    ) {
        Box(
            modifier = Modifier.padding(5.dp).border(width = 1.dp, color = Color(0x0F000000), shape = RoundedCornerShape(23.dp))
        ) {
            Row {
                BasicTextField(
                    value = text,
                    onValueChange = { text = it },
                    singleLine = true,
                    textStyle = FontSizeNormal14(),
                    modifier = Modifier.fillMaxWidth().align(Alignment.CenterVertically).weight(1f).padding(start = 16.dp),
                )
                //Box(Modifier.width(2.dp).fillMaxHeight().padding(top = 8.dp, bottom =  8.dp))

                Image(
                    painter = painterResource(searchIcon ?: Res.drawable.ui_icon_search),
                    contentDescription = "UISearch Icon",
                    modifier = Modifier.fillMaxHeight().padding(8.dp).aspectRatio(1f).clip(CircleShape).clickable { inputString.value = text ; onClick() },
                    colorFilter = ColorFilter.tint(Color(0xCC000000))
                )
            }
        }
    }
}