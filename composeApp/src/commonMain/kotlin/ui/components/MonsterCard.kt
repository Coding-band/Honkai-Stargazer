package ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import org.jetbrains.compose.resources.painterResource
import types.AbyssInfoMonster
import types.ImageFolder
import utils.app.Constants
import utils.app.getAssetsURLByFileName
import utils.app.getImageNameByRegistName
import utils.app.newImageRequest


@Composable
fun MonsterCard(monsterInfo: AbyssInfoMonster){
    val context = LocalPlatformContext.current

    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Box(Modifier.size(48.dp).background(Brush.verticalGradient(Constants.getCardBgColorByRare(1)), shape = RoundedCornerShape(4.dp)).wrapContentHeight(), contentAlignment = Alignment.Center) {
            AsyncImage(
                model = newImageRequest(
                    context = context,
                    getAssetsURLByFileName(
                        ImageFolder.MONSTER_ICON,
                        "monster_${getImageNameByRegistName(monsterInfo.registName)}"
                    )
                ),
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                //onError = { println("${it.result.throwable.message} : ${it.result.throwable.stackTraceToString()}") }
            )
        }

        Spacer(Modifier.height(3.dp))
        Row{
            repeat(monsterInfo.monsterWeakness.size) {
                Image(painterResource(monsterInfo.monsterWeakness[it].iconColor), modifier = Modifier.size(16.dp) ,contentDescription = null)
            }
        }
    }
}