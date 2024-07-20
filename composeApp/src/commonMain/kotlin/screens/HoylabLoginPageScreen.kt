package screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichText
import components.AppDialog
import components.BackIcon
import components.HeaderData
import components.PageHeader
import components.UIButton
import components.UIButtonSize
import components.defaultHeaderData
import dev.chrisbanes.haze.HazeState
import files.NotOK
import files.OK
import files.RemarksInLogin
import files.Res
import files.SelectAccountInServer
import files.SelectServerTitle
import files.UseCookiesToLogin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import utils.FontSizeNormal14
import utils.FontSizeNormal16
import utils.Language
import utils.LongStringXML
import utils.UtilTools
import utils.annotation.DoItLater
import utils.hoyolab.HoyolabConst

@DoItLater("Implement the HoyolabLoginPageScreen Webview later")
@Composable
fun HoyolabLoginPageScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    headerData: HeaderData = defaultHeaderData,
    backStackEntry: NavBackStackEntry? = null,
    snackbarHostState: SnackbarHostState? = remember { SnackbarHostState() },
){
    val serverId = backStackEntry!!.arguments?.getString("relicName")!!.replace("_", " ")
    val hazeState = remember { HazeState() }

    Box(modifier = modifier.fillMaxSize()) {


        PageHeader(
            navController = navController,
            headerData = headerData,
            hazeState = hazeState,
            backIconId = BackIcon.CANCEL,
        )
    }
}

@Composable
fun HoyolabServerRemarksPopup(modifier: Modifier = Modifier, showPopup : MutableState<Boolean> = remember { mutableStateOf(false) },  hazeState: HazeState = remember { HazeState() }) {
    val timeCountDown = remember { mutableStateOf(5) }
    val courtineScope = rememberCoroutineScope()
    val showSelectPopUp = remember { mutableStateOf(false) }
    val isCountDowning = remember { mutableStateOf(false) }

    courtineScope.launch {
        if (isCountDowning.value) return@launch
        for (i in 5 downTo 0) {
            isCountDowning.value = true
            timeCountDown.value = i
            delay(1000)
        }
        isCountDowning.value = false
    }

    if(showPopup.value) {
        Popup(alignment = Alignment.Center) {
            AppDialog(
                titleString = UtilTools().removeStringResDoubleQuotes(Res.string.RemarksInLogin),
                hazeState = hazeState,
                components = {
                    val richTextState = rememberRichTextState()
                    richTextState.setHtml(LongStringXML().LoginPolicy(Language.AppLanguageInstance))
                    Column {
                        //Remarks Text
                        RichText(
                            state = richTextState,
                            color = Color.Black,
                            style = FontSizeNormal14(),
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        //Agree
                        UIButton(
                            isAvailable = timeCountDown.value <= 0,
                            text = if (timeCountDown.value > 0) "${timeCountDown.value}s" else UtilTools().removeStringResDoubleQuotes(Res.string.OK),
                            onClick = {
                                showPopup.value = false
                                showSelectPopUp.value = true
                            }
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        //Disagree
                        UIButton(
                            text = UtilTools().removeStringResDoubleQuotes(Res.string.NotOK),
                            onClick = {
                                showPopup.value = false
                            }
                        )
                    }
                },
                isPopupShow = showPopup
            )
        }
    }

    HoyolabServerSelectPopup(showPopup = showSelectPopUp, hazeState = hazeState)
}

@Composable
fun HoyolabServerSelectPopup(modifier: Modifier = Modifier, showPopup : MutableState<Boolean> = remember { mutableStateOf(false) },  hazeState: HazeState = remember { HazeState() }){
    if (showPopup.value){
        Popup(alignment = Alignment.Center) {
            AppDialog(
                titleString = UtilTools().removeStringResDoubleQuotes(Res.string.SelectServerTitle),
                hazeState = hazeState,
                components = {
                    Column {
                        //Please Select server that you want to login
                        Text(
                            text = UtilTools().removeStringResDoubleQuotes(Res.string.SelectAccountInServer),
                            color = Color.Black,
                            style = FontSizeNormal14(),
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        //Remarks that not support 3rd-party login
                        Text(
                            text = LongStringXML().LoginHint(Language.AppLanguageInstance),
                            color = Color.Black,
                            style = FontSizeNormal16(),
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // Manually Login by Cookies 手動登錄（自行提供Cookies）
                        UIButton(
                            text = UtilTools().removeStringResDoubleQuotes(Res.string.UseCookiesToLogin),
                            buttonSize = UIButtonSize.NormalLargeText,
                            onClick = {
                                showPopup.value = false
                            }
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // Different Server Choices
                        for (server in HoyolabConst.SERVER.entries.filter { it != HoyolabConst.SERVER.UNKNOWN }) {
                            UIButton(
                                text = UtilTools().removeStringResDoubleQuotes(server.localeName),
                                onClick = {
                                    showPopup.value = false
                                }
                            )

                            Spacer(modifier = Modifier.height(12.dp))
                        }


                    }
                },
                isPopupShow = showPopup
            )
        }
    }
}