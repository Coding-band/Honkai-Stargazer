package ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.toRoute
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichText
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.WebViewState
import com.multiplatform.webview.web.rememberWebViewState
import dev.chrisbanes.haze.HazeState
import files.LoginPCNotAcceptViaWebsiteYet
import files.NotOK
import files.OK
import files.RemarksInLogin
import files.Res
import files.SelectAccountInServer
import files.SelectServerTitle
import files.TutorialVideo
import files.UseCookiesToLogin
import files.bg_transparent
import files.ic_arrow_down_spinner
import files.ic_selected_orange_circle
import files.phorphos_clipboard_regular
import files.phorphos_clipboard_text_fill
import files.phorphos_clipboard_text_regular
import getDeviceInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.painterResource
import types.UserAccount
import ui.components.AppDialog
import ui.components.BackIcon
import ui.components.DropdownMenuNoPadding
import ui.components.HeaderData
import ui.components.PAGE_HEADER_HEIGHT
import ui.components.PageHeader
import ui.components.PomPomPopup
import ui.components.UIButton
import ui.components.UIButtonSize
import ui.components.defaultHeaderData
import ui.components.pomPomPopupInstance
import ui.navigation.CharacterInfoRoute
import ui.navigation.HoyolabLoginRoute
import ui.navigation.Screen
import ui.navigation.navigateLimited
import ui.navigation.navigatorInstance
import ui.navigation.popBackStackLimited
import utils.annotation.DoItLater
import utils.app.AppFont
import utils.app.FontSizeNormal14
import utils.app.FontSizeNormal16
import utils.app.Language.Companion.TextLanguageInstance
import utils.app.LongStringXML
import utils.app.isLinuxPlatform
import utils.app.isMacOSPlatform
import utils.app.isWindowsPlatform
import utils.app.pxToDp
import utils.app.removeStrQuote
import utils.app.showWarningToast
import utils.hoyolab.HoyolabConst
import utils.starbase.StarbaseAPI

@DoItLater("Implement the HoyolabLoginPageScreen Webview later")
@Composable
fun HoyolabLoginPageScreen(
    navigator: NavHostController,
    hazeState: HazeState,
    backStackEntry: NavBackStackEntry,
    snackbarHostState: SnackbarHostState? = remember { SnackbarHostState() },
){
    val route = backStackEntry.toRoute<HoyolabLoginRoute>()
    val serverId = route.serverId
    val serverSelected = HoyolabConst().getServerById(serverId)
    val url = HoyolabConst().getLoginURL(serverSelected)
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

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            WebView(webviewState, modifier = Modifier.statusBarsPadding().padding(top = PAGE_HEADER_HEIGHT).fillMaxSize().weight(1f))
            Box(modifier = Modifier.background(Color(0xCCF3F9FF)).fillMaxWidth().padding(16.dp).navigationBarsPadding()) {
                Text(
                    text = LongStringXML().LoginHint(),
                    color = Color.Black,
                    style = FontSizeNormal14(),
                )

            }
        }

        PageHeader(
            navigator = navigator,
            headerData = Screen.HoyolabLoginPageScreen.headerData,
            hazeState = hazeState,
            backIconId = BackIcon.CANCEL,
            onBack = {
                initDataAfterLogin(
                    webviewState = webviewState,
                    url = url,
                    serverSelected = serverSelected,
                    snackbarHostState = snackbarHostState,
                )
                navigator.popBackStackLimited()
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
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
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
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
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
                                val loginPCNotAcceptMsg = removeStrQuote(Res.string.LoginPCNotAcceptViaWebsiteYet)
                                UIButton(
                                    text = removeStrQuote(server.localeName),
                                    onClick = {
                                        showPopup.value = false

                                        if(isWindowsPlatform() || isMacOSPlatform() || isLinuxPlatform()){
                                            showWarningToast(message = loginPCNotAcceptMsg)
                                        }else{
                                            navigatorInstance.navigateLimited(HoyolabLoginRoute(server.serverId))
                                        }
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
    val focusManager = LocalFocusManager.current
    var cookieInput by remember { mutableStateOf(TextFieldValue("")) }
    val isDropDownOpen = remember { mutableStateOf(false) }
    val density = LocalDensity.current.density

    val optionTextViewSize = remember { mutableStateOf(IntSize.Zero) }

    if (showPopup.value){
        //popup properties, is really sad to see that it still need to manually set focusable to true
        //Popup(alignment = Alignment.Center, properties = PopupProperties(focusable = true)) {
        Box(modifier = modifier.fillMaxSize().pointerInput(Unit){
            detectTapGestures(onTap = {
                focusManager.clearFocus()
            })
        }, contentAlignment = Alignment.Center) {
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
                            Box(
                                contentAlignment = Alignment.BottomCenter,
                                modifier = Modifier
                                    .defaultMinSize(100.dp, 30.dp)
                                    .wrapContentSize()
                                    .clickable { isDropDownOpen.value = !isDropDownOpen.value }
                            ) {
                                Column(
                                    modifier = Modifier
                                        .wrapContentWidth()
                                        .onSizeChanged { optionTextViewSize.value = it },
                                ){
                                    UIButton(
                                        buttonSize = UIButtonSize.NormalTextLeft,
                                        text = removeStrQuote(serverList[serverSelectedIndex.value].localeName),
                                        isAvailable = true,
                                        onClick = { isDropDownOpen.value = !isDropDownOpen.value },
                                        icon = Res.drawable.ic_arrow_down_spinner
                                    )
                                    Spacer(Modifier.height(8.dp))
                                }
                                //對於DropdownItem沒法按照設計稿展示，暫時無解
                                DropdownMenuNoPadding(
                                    expanded = isDropDownOpen.value,
                                    onDismissRequest = { isDropDownOpen.value = false },
                                    modifier = Modifier
                                        .background(Color(0xFFDDDDDD))
                                        .width(pxToDp(optionTextViewSize.value.width, density)),
                                ) {
                                    serverList.forEachIndexed { index, option ->
                                        DropdownMenuItem(
                                            onClick = {
                                                serverSelectedIndex.value = index
                                                isDropDownOpen.value = false
                                                //optionAction(schoolIndex.value)
                                            },
                                            modifier = Modifier.background(if(serverSelectedIndex.value == index)
                                            //Color(0x0F000000) else Color(0x00000000)
                                                Color(0x0F000000) else Color(0x00000000)
                                            )
                                        ) {
                                            Row{
                                                Text(
                                                    text = removeStrQuote(option.localeName),
                                                    style = FontSizeNormal14(),
                                                    color = Color.Black,
                                                    modifier = Modifier.weight(1f)
                                                )
                                                Image(
                                                    painterResource(if (serverSelectedIndex.value == index) Res.drawable.ic_selected_orange_circle else Res.drawable.bg_transparent),
                                                    contentDescription = null
                                                )
                                            }
                                        }
                                    }
                                }
                            }
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

                        item { Spacer(modifier = Modifier.height(4.dp)) }

                        item {
                            // Text Field for Cookies Input
                            BoxWithConstraints(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(128.dp,320.dp)
                            ) {
                                val clipboard = LocalClipboardManager.current
                                BasicTextField(
                                    value = cookieInput,
                                    onValueChange = { cookieInput = it },
                                    modifier = Modifier
                                        .background(Color(0xCCFFFFFF), RoundedCornerShape(24.dp))
                                        .heightIn(128.dp,320.dp)
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    decorationBox = { innerTextField ->
                                        Box(modifier = Modifier.fillMaxSize()){
                                            if (cookieInput.text.isEmpty()){
                                                Text(
                                                    text = LongStringXML().LoginViaPCToGetCookies(),
                                                    color = Color.Gray,
                                                    style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Normal, fontFamily = AppFont())
                                                )
                                            }
                                            Box(modifier = Modifier.fillMaxSize()){
                                                innerTextField()
                                            }
                                            Image(
                                                painter = painterResource(Res.drawable.phorphos_clipboard_text_fill),
                                                contentDescription = "Paste",
                                                modifier = Modifier
                                                    .wrapContentSize()
                                                    .align(Alignment.BottomEnd)
                                                    .size(48.dp)
                                                    .background(Color(0xFFDEDFE0), RoundedCornerShape(12.dp))
                                                    .clickable {
                                                        cookieInput = TextFieldValue(
                                                            annotatedString = clipboard.getText() ?: AnnotatedString("")
                                                        )
                                                    }
                                                    .padding(8.dp)
                                            )
                                        }

                                    },
                                )
                            }
                        }

                        item {
                            // Button to Confirm
                            UIButton(
                                text = removeStrQuote(Res.string.OK),
                                onClick = {
                                    initDataAfterLogin(
                                        cookieList = cookieInput.text,
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