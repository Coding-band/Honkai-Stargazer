package components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import files.AppStatusLostConnect
import files.Res
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import utils.FontSizeNormal14
import utils.TextColorNormal
import utils.UtilTools

@Composable
fun TitleHeader(iconRId : DrawableResource, titleString: String? = null, titleRId: StringResource? = null){
    Column(
        Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(resource = iconRId),
            contentDescription = "Title Icon",
            modifier = Modifier.size(32.dp),
            colorFilter = ColorFilter.tint(Color.White)
        )
        Row {
            Spacer(
                modifier = Modifier
                    .height(2.dp)
                    .width(50.dp)
                    .background(Color(0x66FFFFFF))
                    .align(Alignment.CenterVertically),
            )
            Text(
                text =
                if (titleString === null) {
                    if (titleRId === null)
                        UtilTools().removeStringResDoubleQuotes(Res.string.AppStatusLostConnect)
                    else
                        UtilTools().removeStringResDoubleQuotes(titleRId)
                } else titleString,
                color = TextColorNormal,
                style = FontSizeNormal14(),
                modifier = Modifier.padding(start = 12.dp, end = 12.dp)
            )
            Spacer(
                modifier = Modifier
                    .height(2.dp)
                    .width(50.dp)
                    .background(Color(0x66FFFFFF))
                    .align(Alignment.CenterVertically),
            )
        }

    }
}