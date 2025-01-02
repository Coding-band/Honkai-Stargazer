package ui.function.homePage.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade
import files.PlayerLevel
import files.Res
import files.ic_rounded_option_btn
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import type.UserAccount
import ui.navigation.Screen
import ui.navigation.navigateLimited
import utils.app.BlackAlpha30
import utils.app.FontSizeNormal14
import utils.app.FontSizeNormalLarge24
import utils.app.ProgressLevelBackground
import utils.app.ProgressLevelPrimary
import utils.app.TextColorLevel
import utils.app.TextColorNormal
import utils.app.getIconByUserAccountIconValue
import utils.app.removeStrQuote
import kotlin.math.min

@Composable
fun HomePageHeader(
    modifier: Modifier = Modifier,
    navigator: Navigator,
    threeDotDialogPos: MutableState<Offset>,
    threeDotDialogDisplay: MutableState<Boolean>,
) {
    val userAccount = remember { UserAccount.INSTANCE }
    Box(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 4.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Column {
            Text(
                text = userAccount.uid,
                modifier = Modifier
                    .background(BlackAlpha30, CircleShape)
                    .padding(all = 8.dp)
                    .wrapContentSize(),
                color = TextColorNormal,
                style = FontSizeNormal14(),
            )
            Spacer(modifier = Modifier.height(12.dp))
            Box {
                Row(Modifier.height(72.dp)) {
                    val context = LocalPlatformContext.current
                    val imageRequest =  remember {
                        ImageRequest.Builder(context)
                            .data(
                                getIconByUserAccountIconValue(userAccount.icon)
                            )
                            .networkCachePolicy(CachePolicy.ENABLED)
                            .crossfade(true)
                            .diskCachePolicy(CachePolicy.ENABLED)
                            .build()
                    }

                    //User Avatar (With Reducing Padding's Scale)
                    Box(
                        Modifier.requiredSize(72.dp)
                        .background(Color(0xFFCAB89E), CircleShape)
                        .clip(CircleShape)
                        .border(1.dp, Color(0x66907C54), CircleShape)
                        .clickable {
                            if (userAccount.isLogin) {
                                navigator.navigateLimited("${Screen.UserInfoPageScreen.route}?uid=${userAccount.uid}")
                            }
                        }, contentAlignment = Alignment.Center
                    ) {

                        val scale = if(userAccount.icon.startsWith("http")) 1.142857f else 1f
                        Box(Modifier.requiredSize(72.dp * scale)) {
                            // User Avatar
                            AsyncImage(
                                modifier = Modifier.size(72.dp * scale),
                                model = imageRequest,
                                contentDescription = "",
                            )
                        }
                    }
                    // User Name & Helping Team
                    Column(
                        modifier = Modifier
                            .padding(start = 8.dp, end = 8.dp)
                            .weight(1f)
                    ) {
                        //User Name - Hmm interesting Kt
                        Text(
                            text = userAccount.username,
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxSize()
                                .wrapContentHeight(align = Alignment.CenterVertically),
                            color = TextColorNormal,
                            style = FontSizeNormalLarge24(),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                        //Helping Team
                        LazyRow(
                            modifier = Modifier
                                .height(30.dp)
                                .fillMaxWidth()
                        ) {
                            item {
                                val filterResult = userAccount.characterList.filter { it.characterStatus != null && it.characterStatus!!.isHelper }
                                if (filterResult.isNotEmpty()) {
                                    filterResult.forEach { UserHelpTeamIcon(character = it, navigator = navigator, uid = userAccount.uid) }
                                }else if(userAccount.characterList.size > 0){
                                    for (i in 0 until min(userAccount.characterList.size, 6)) {
                                        UserHelpTeamIcon(
                                            character = userAccount.characterList[i],
                                            navigator = navigator,
                                            uid = userAccount.uid
                                        )
                                    }
                                }else{
                                    Box(modifier = Modifier
                                        .size(30.dp)
                                        .background(Color(0xFFD9D9D9), CircleShape)
                                        .clip(CircleShape)
                                        .border(1.5.dp, Color(0xFFD3D3D3), CircleShape)
                                        .padding()
                                    )
                                }
                            }
                        }
                    }
                    Column(
                        Modifier
                            .padding(top = 12.dp)
                            .fillMaxHeight(), horizontalAlignment = Alignment.End
                    ) {
                        //Three Dots
                        IconButton(
                            modifier = Modifier
                                .height(23.dp)
                                .width(45.dp)
                                .onGloballyPositioned {
                                    threeDotDialogPos.value = it.positionInRoot()
                                },
                            onClick = {
                                threeDotDialogDisplay.value = !threeDotDialogDisplay.value
                            }
                        ) {
                            Image(painterResource(resource = Res.drawable.ic_rounded_option_btn), "")
                        }

                        Spacer(Modifier.weight(1f))

                        Text(
                            text = "${removeStrQuote(Res.string.PlayerLevel)} ${userAccount.level}",
                            color = TextColorLevel,
                            style = FontSizeNormal14(),
                            //fontWeight = FontWeight.Bold
                        )
                    }
                }

            }
            LinearProgressIndicator(
                progress = (userAccount.level / 60f),
                Modifier
                    .padding(top = 12.dp, bottom = 12.dp)
                    .fillMaxWidth(),
                backgroundColor = ProgressLevelBackground,
                color = ProgressLevelPrimary,
            )
        }
    }
}