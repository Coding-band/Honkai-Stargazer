package ui.function.HomePage.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import files.AccountLogin
import files.Logout
import files.ModifyHomePage
import files.Res
import files.Setting
import moe.tlaster.precompose.navigation.Navigator
import type.UserAccount
import ui.components.UIButton
import ui.components.UIButtonSize
import ui.navigation.Screen
import ui.navigation.navigateLimited
import ui.navigation.navigatorInstance
import utils.app.pxToDp

@Composable
fun ThreeDotsDialog(
    modifier: Modifier = Modifier,
    navigator: Navigator = navigatorInstance,
    threeDotDialogPos: MutableState<Offset>,
    hazeState: HazeState = remember { HazeState() },
    threeDotDialogDisplay: MutableState<Boolean>,
){
    val density = LocalDensity.current.density
    val showLoginPopUp = remember { mutableStateOf(false) }
    val userAccount = remember { mutableStateOf(UserAccount.INSTANCE) }

    if(threeDotDialogDisplay.value){
        Row {
            Spacer(modifier = Modifier.weight(1f).fillMaxWidth().width(1.dp))
            Box(
                modifier = Modifier
                    .width(170.dp)
                    .wrapContentHeight()
                    .offset(y = pxToDp(threeDotDialogPos.value.y.toInt(), density = density) + 32.dp)
                    .clickable(indication = null, interactionSource = remember { MutableInteractionSource() }, onClick = {})
            ) {
                Box(
                    modifier = Modifier.padding(8.dp)
                        .background(Color(0x96000000), shape = RoundedCornerShape(10.dp)).fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    Column(modifier = Modifier.padding(15.dp)) {
                        UIButton(
                            textRes = if (userAccount.value.isLogin) Res.string.Logout else Res.string.AccountLogin,
                            onClick = {
                                threeDotDialogDisplay.value = false;
                                if (userAccount.value.isLogin) {
                                    //UserAccount.resetUserAccount()
                                    //Preferences().Leaderboard.resetLeaderboard()
                                    userAccount.value = UserAccount.INSTANCE
                                } else {
                                    showLoginPopUp.value = true
                                }
                            },
                            buttonSize = UIButtonSize.SmallChoice
                        )
                        Spacer(Modifier.height(10.dp))
                        UIButton(
                            textRes = Res.string.ModifyHomePage,
                            onClick = { },
                            buttonSize = UIButtonSize.SmallChoice
                        )
                        Spacer(Modifier.height(10.dp))
                        UIButton(
                            textRes = Res.string.Setting,
                            onClick = {
                                threeDotDialogDisplay.value =
                                    false; navigator.navigateLimited(Screen.SettingScreen.route)
                            },
                            buttonSize = UIButtonSize.SmallChoice
                        )
                    }
                }
            }
        }
    }

    //HoyolabServerRemarksPopup(showPopup = showLoginPopUp, hazeState = hazeState)
}