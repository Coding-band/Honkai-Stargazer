package ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichText
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.WebViewState
import com.multiplatform.webview.web.rememberWebViewState
import dev.chrisbanes.haze.HazeState
import files.NotOK
import files.OK
import files.RemarksInLogin
import files.Res
import files.SelectAccountInServer
import files.SelectServerTitle
import files.TutorialVideo
import files.UseCookiesToLogin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moe.tlaster.precompose.navigation.BackStackEntry
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.query
import types.UserAccount
import ui.components.AppDialog
import ui.components.BackIcon
import ui.components.HeaderData
import ui.components.PAGE_HEADER_HEIGHT
import ui.components.PageHeader
import ui.components.PomPomPopup
import ui.components.UIButton
import ui.components.UIButtonSize
import ui.components.defaultHeaderData
import ui.components.pomPomPopupInstance
import ui.navigation.Screen
import ui.navigation.navigateLimited
import ui.navigation.navigatorInstance
import utils.annotation.DoItLater
import utils.app.FontSizeNormal14
import utils.app.FontSizeNormal16
import utils.app.LongStringXML
import utils.app.removeStrQuote
import utils.hoyolab.HoyolabConst
import utils.starbase.StarbaseAPI

@DoItLater("Implement the HoyolabLoginPageScreen Webview later")
@Composable
fun HoyolabLoginPageScreen(
    modifier: Modifier = Modifier,
    navigator: Navigator,
    headerData: HeaderData = defaultHeaderData,
    backStackEntry: BackStackEntry,
    snackbarHostState: SnackbarHostState? = remember { SnackbarHostState() },
){
    val serverId = backStackEntry.query<String>("serverId")!!
    val serverSelected = HoyolabConst().getServerById(serverId)
    val url = HoyolabConst().getLoginURL(serverSelected)
    val hazeState = remember { HazeState() }
    val coroutineScope = rememberCoroutineScope()
    val webviewState = rememberWebViewState(url = url)

    //Clean All Cookies first
    coroutineScope.launch {
        webviewState.cookieManager.removeAllCookies()
    }

    DisposableEffect(Unit) {
        webviewState.webSettings.apply {
            androidWebSettings.domStorageEnabled = true
            desktopWebSettings.disablePopupWindows = true
            allowFileAccessFromFileURLs = true
        }

        onDispose {  }
    }

    Box(modifier = modifier.fillMaxSize()) {
        WebView(webviewState, modifier = Modifier.statusBarsPadding().padding(top = PAGE_HEADER_HEIGHT).matchParentSize())

        PageHeader(
            navigator = navigator,
            headerData = headerData,
            hazeState = hazeState,
            backIconId = BackIcon.CANCEL,
            onBack = {
                initDataAfterLogin(
                    webviewState = webviewState,
                    url = url,
                    serverSelected = serverSelected,
                    snackbarHostState = snackbarHostState,
                )
                navigator.popBackStack()
            }
        )
    }
}

fun initDataAfterLogin(
    cookieList: Any = "",
    webviewState: WebViewState? = null,
    url: String? = null,
    serverSelected: HoyolabConst.SERVER,
    snackbarHostState: SnackbarHostState? = null,
) {
    CoroutineScope(Dispatchers.Default).launch{
        withContext(Dispatchers.Main){
            pomPomPopupInstance.value = PomPomPopup(isDisplay = true)

        }

        UserAccount.pasteCookies(if(webviewState != null && url != null) webviewState.cookieManager.getCookies(url) else cookieList, serverSelected, snackbarHostState)
        StarbaseAPI().updateUserAccountInfo()
        StarbaseAPI().updateCharData()
        withContext(Dispatchers.Main){
            pomPomPopupInstance.value = PomPomPopup(isDisplay = false)
            doRecompose.value = !doRecompose.value
        }
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
                titleString = removeStrQuote(Res.string.RemarksInLogin),
                hazeState = hazeState,
                components = {
                    val richTextState = rememberRichTextState()
                    richTextState.setHtml(LongStringXML().LoginPolicy())
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
                            text = if (timeCountDown.value > 0) "${timeCountDown.value}s" else removeStrQuote(Res.string.OK),
                            onClick = {
                                showPopup.value = false
                                showSelectPopUp.value = true
                            }
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        //Disagree
                        UIButton(
                            text = removeStrQuote(Res.string.NotOK),
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
    val showManualPopup = remember { mutableStateOf(false) }
    if (showPopup.value){
        Popup(alignment = Alignment.Center) {
            AppDialog(
                titleString = removeStrQuote(Res.string.SelectServerTitle),
                hazeState = hazeState,
                components = {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp), state = rememberLazyListState()) {
                        item {
                            //Please Select server that you want to login
                            Text(
                                text = removeStrQuote(Res.string.SelectAccountInServer),
                                color = Color.Black,
                                style = FontSizeNormal14(),
                            )
                        }

                        item {
                            //Remarks that not support 3rd-party login
                            Text(
                                text = LongStringXML().LoginHint(),
                                color = Color.Black,
                                style = FontSizeNormal16(),
                            )
                        }

                        item {
                            // Manually Login by Cookies 手動登錄（自行提供Cookies）
                            UIButton(
                                text = removeStrQuote(Res.string.UseCookiesToLogin),
                                buttonSize = UIButtonSize.NormalLargeText,
                                onClick = {
                                    showPopup.value = false
                                    showManualPopup.value = true
                                }
                            )
                        }

                        // Different Server Choices
                        for (server in HoyolabConst.SERVER.entries.filter { it != HoyolabConst.SERVER.UNKNOWN }) {
                            item {
                                UIButton(
                                    text = removeStrQuote(server.localeName),
                                    onClick = {
                                        navigatorInstance.navigateLimited("${Screen.HoyolabLoginPageScreen.route}?serverId=${server.serverId}")
                                        showPopup.value = false
                                    }
                                )
                            }
                        }
                    }
                },
                isPopupShow = showPopup
            )
        }
    }

    if (showManualPopup.value){
        HoyolabManualLoginPopup(showPopup = showManualPopup, hazeState = hazeState)
    }
}

@Composable
fun HoyolabManualLoginPopup(modifier: Modifier = Modifier, showPopup : MutableState<Boolean> = remember { mutableStateOf(false) },  hazeState: HazeState = remember { HazeState() }) {
    val serverList = HoyolabConst.SERVER.entries.filter { it != HoyolabConst.SERVER.UNKNOWN }
    val serverSelectedIndex = remember { mutableStateOf(0) }
    val urlHandler = LocalUriHandler.current

    if (showPopup.value){
        Popup(alignment = Alignment.Center) {
            val cookieInput = remember { mutableStateOf("") }
            AppDialog(
                titleString = removeStrQuote(Res.string.SelectServerTitle),
                hazeState = hazeState,
                components = {
                    val lazyListState = rememberLazyListState()
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp), state = lazyListState) {
                        //Please Select server that you want to login
                        item {
                            Text(
                                text = LongStringXML().SelectServerAndPasteCookies(),
                                color = Color.Black,
                                style = FontSizeNormal14(),
                            )
                        }

                        item {
                            // Button to change server
                            UIButton(
                                text = removeStrQuote(serverList[serverSelectedIndex.value].localeName),
                                buttonSize = UIButtonSize.NormalLargeText,
                                onClick = {
                                    serverSelectedIndex.value = (serverSelectedIndex.value + 1) % serverList.size
                                }
                            )
                        }

                        item {
                            //Tutorial Video Button
                            UIButton(
                                text = removeStrQuote(Res.string.TutorialVideo),
                                onClick = {
                                    urlHandler.openUri("https://www.youtube.com/watch?v=CLkhV30kg_A")
                                }
                            )
                        }

                        item {
                            // Text Field for Cookies Input
                            BoxWithConstraints(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.White)
                                    .clip(RoundedCornerShape(24.dp))
                            ) {
                                BasicTextField(
                                    value = cookieInput.value,
                                    onValueChange = { cookieInput.value = it },
                                    textStyle = FontSizeNormal14().plus(TextStyle(Color.DarkGray)),
                                    modifier = Modifier
                                        .background(Color.White)
                                        .fillMaxWidth().heightIn(128.dp,480.dp).aspectRatio(1f)
                                )
                            }
                        }

                        item {
                            // Button to Confirm
                            UIButton(
                                text = removeStrQuote(Res.string.OK),
                                onClick = {
                                    initDataAfterLogin(
                                        cookieList = cookieInput.value,
                                        serverSelected = serverList[serverSelectedIndex.value],
                                        snackbarHostState = null,
                                    )
                                    showPopup.value = false
                                }
                            )
                        }
                    }
                },
                isPopupShow = showPopup
            )
        }
    }
}