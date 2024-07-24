package screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import components.HeaderData
import components.PageHeaderAlpha
import components.defaultHeaderData
import files.Res
import files.UserInfoGameData
import files.ui_icon_share
import utils.UtilTools

@Composable
fun UserInfoPageScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    headerData: HeaderData = defaultHeaderData,
) {

    Box(modifier = modifier.fillMaxSize()) {

        PageHeaderAlpha(
            navController = navController,
            forwardIconId = Res.drawable.ui_icon_share,
            onForward = { /* TODO : Share Function*/ },
        ){
            Text(
                UtilTools().removeStringResDoubleQuotes(Res.string.UserInfoGameData),
                modifier = Modifier
                    .background(Color(0x33FFFFFF),RoundedCornerShape(16.dp))
                    .padding(top = 6.dp, bottom = 6.dp, start = 10.dp, end = 10.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .align(Alignment.Center)
            )
        }
    }
}