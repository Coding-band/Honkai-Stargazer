package components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import utils.FontSizeNormal16
import utils.FontSizeNormal20
import utils.UtilTools

public enum class UIButtonSize {
    Normal,
    NormalLargeText,
    SmallChoice
}
@Composable
fun UIButton(modifierTmp: Modifier = Modifier, textRes: StringResource? = null, text: String? = null, icon: DrawableResource? = null, isAvailable : Boolean = true, buttonSize : UIButtonSize = UIButtonSize.Normal, onClick : () -> Unit = {}) {
    var modifier = modifierTmp
    modifier = if(textRes == null && text == null && icon != null){
        modifier.size(46.dp).background(Color(0xFFDDDDDD), shape = RoundedCornerShape(23.dp))
    }else{
        modifier.fillMaxWidth().wrapContentHeight().background(Color(0xFFDDDDDD), shape = RoundedCornerShape(23.dp))
    }

    Box(
        modifier = modifier.clip(shape = RoundedCornerShape(23.dp)).clickable { if(isAvailable) onClick() }
    ) {
        Box(
            modifier = Modifier.padding(5.dp).border(width = 1.dp, color = Color(0x0F000000), shape = RoundedCornerShape(23.dp))
        ) {
            if(textRes == null && text == null && icon != null){
                Image(painter = painterResource(icon), contentDescription = "UIButton Icon", modifier = Modifier.padding(16.dp))
            }else if(textRes == null || text == null){
                Row {
                    Text(
                        if (textRes == null) { text ?: "?" } else UtilTools().removeStringResDoubleQuotes(textRes),
                        style = if(buttonSize == UIButtonSize.NormalLargeText) FontSizeNormal20() else  FontSizeNormal16(),
                        color = if(isAvailable) Color(0xFF222222) else Color(0x4D222222),
                        textAlign = if(icon != null) TextAlign.Left else TextAlign.Center,
                        maxLines = 1,
                        modifier = Modifier.align(Alignment.CenterVertically).weight(1f).padding(top = if(buttonSize == UIButtonSize.SmallChoice) (2.dp) else 6.dp, bottom = if(buttonSize == UIButtonSize.SmallChoice) (2.dp) else 6.dp, start = 8.dp, end = 8.dp)
                    )
                    if(icon != null){
                        Box(Modifier.width(2.dp).fillMaxHeight().padding(top = 8.dp, bottom =  8.dp))
                        
                        Image(painter = painterResource(icon), contentDescription = "UIButton Icon", modifier = Modifier.padding(16.dp))
                    }
                }
            }
        }
    }
}