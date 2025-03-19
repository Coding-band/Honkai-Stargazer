package ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import files.AppStatusLostConnect
import files.Res
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import utils.app.FontSizeNormal14
import utils.app.TextColorNormal
import utils.app.removeStrQuote

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
        Row(modifier = Modifier.wrapContentWidth()) {
            Spacer(
                modifier = Modifier
                    .height(2.dp)
                    .requiredWidth(50.dp)
                    .background(Color(0x66FFFFFF))
                    .align(Alignment.CenterVertically),
            )
            Spacer(
                modifier = Modifier
                    .height(2.dp)
                    .requiredWidth(12.dp)
                    .align(Alignment.CenterVertically),
            )
            Text(
                text =
                if (titleString === null) {
                    if (titleRId === null)
                        removeStrQuote(Res.string.AppStatusLostConnect)
                    else
                        removeStrQuote(titleRId)
                } else titleString,
                color = TextColorNormal,
                style = FontSizeNormal14(),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(
                modifier = Modifier
                    .height(2.dp)
                    .requiredWidth(12.dp)
                    .align(Alignment.CenterVertically),
            )
            Spacer(
                modifier = Modifier
                    .height(2.dp)
                    .requiredWidth(50.dp)
                    .background(Color(0x66FFFFFF))
                    .align(Alignment.CenterVertically),
            )
        }

    }
}