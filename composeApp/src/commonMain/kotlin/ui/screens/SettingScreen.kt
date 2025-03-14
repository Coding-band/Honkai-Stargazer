package ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.russhwolf.settings.Settings
import com.voc.stargazer3.BuildKonfig
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeSource
import files.About
import files.AboutTheApp
import files.AccountSetup
import files.AppInnerVersionCode
import files.AppLanguage
import files.AppVersion
import files.ChangeWallPaper
import files.Customize
import files.DocumentLanguage
import files.DonateUs
import files.HaveNotUsed
import files.InviteOthers
import files.LanguageSetup
import files.Navigate
import files.OsVersion
import files.Res
import files.SettingDeviceModel
import files.SettingInternalVersionCode
import files.SettingPadModeHomePageShowBg
import files.SettingPersonalPageDisable
import files.SettingPersonalPageShow
import files.SettingReDownloadFullData
import files.SourceCode
import files.SupportUs
import files.SwitchOff
import files.SwitchOn
import files.UseBlurEffect
import files.UseInviteCode
import files.bg_transparent
import files.ic_selected_orange_circle
import files.phorphos_caret_down_regular
import files.phorphos_caret_right_regular
import getDeviceInfo
import okio.FileSystem
import okio.SYSTEM
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import types.UserAccount
import types.Wallpaper
import ui.components.BackIcon
import ui.components.DropdownMenuNoPadding
import ui.components.PAGE_HEADER_HEIGHT
import ui.components.PageHeader
import ui.navigation.AboutStargazerRoute
import ui.navigation.BackgroundSettingRoute
import ui.navigation.IIRCHomePageRoute
import ui.navigation.Screen
import ui.navigation.navigateLimited
import utils.annotation.DoItLater
import utils.annotation.TranslationPls
import utils.app.Constants
import utils.app.DefaultZIndex
import utils.app.FontSizeNormal14
import utils.app.FontSizeNormal16
import utils.app.FontSizeNormalLarge24
import utils.app.Language
import utils.app.Preferences
import utils.app.UpdateAssetsPopup
import utils.app.pxToDp
import utils.app.removeStrQuote
import utils.app.showFunctionIsDevelopingToast
import utils.app.showSuccessToast
import utils.app.showWarningToast
import utils.starbase.StarbaseAPI
import kotlin.math.max

val doRecompose = mutableStateOf(false)
val doRecomposeText = mutableStateOf(false)
val doInit = mutableStateOf(false)
val doRefresh = mutableStateOf(false)
lateinit var showUpdatePopupInSetting : MutableState<Boolean>

@Composable
fun SettingScreen(
    navigator: NavHostController,
    hazeState: HazeState
){

    val urlHandler = LocalUriHandler.current
    showUpdatePopupInSetting = remember { mutableStateOf(false) }
    val canUpdatePopup = remember { mutableStateOf(true) } //Not for use
    val wallpaperName = remember { mutableStateOf("----") }

    LaunchedEffect(Settings().getString("backgroundImage", "221000"), Language.TextLanguageInstance, doRecomposeText.value){
        wallpaperName.value = Wallpaper.getPreferenceWallpaperLocaleName()
    }

    key(doRecompose.value){

        Box {

            LazyColumn (
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .hazeSource(state = hazeState, zIndex = DefaultZIndex),
            ){
                //Spacer for padding status bar
                item { Spacer(modifier = Modifier.statusBarsPadding().height(PAGE_HEADER_HEIGHT)) }

                //item { LocaleController() }

                //帳號設定 Account Setup
                item {
                    SettingCategory(title = removeStrQuote(Res.string.AccountSetup).replace("$"+"{1}", UserAccount.INSTANCE.uid)) {

                        //使用邀請碼 Use Invite Code
                        SettingOptionNoneBar(
                            titleRes = Res.string.UseInviteCode,
                            optionStatic = removeStrQuote(Res.string.HaveNotUsed)
                        )

                        //個人頁面展示 User Info Page Display All Character?
                        val choiceOnOff = arrayListOf(removeStrQuote(Res.string.SettingPersonalPageDisable), removeStrQuote(Res.string.SettingPersonalPageShow))
                        SettingOptionDropDownBar(
                            titleRes = Res.string.SettingPersonalPageShow,
                            //optionSavedChoice = @DoItLater("Add the function of show personal page"),
                            optionList = choiceOnOff,
                            optionAction = { index: Int ->
                                //UserAccount.INSTANCE.showCharList = (index == 1)
                                Preferences().AppSettings.setIsShowChar( index == 1 )
                                StarbaseAPI().updateUserAccountInfo(isSetShowCharOnly = true)
                            },
                            optionSavedChoice = choiceOnOff[if(Preferences().AppSettings.isShowChar()) 1 else 0]
                        )
                    }
                }

                //語言設定 Language Setting
                //Known Issue : Cannot Implement Locale Change, please refer to https://github.com/JetBrains/compose-multiplatform/issues/4347
                item {
                    SettingCategory(title = removeStrQuote(Res.string.LanguageSetup)) {
                        //文本語言 Text Language
                        SettingOptionDropDownBar(
                            titleRes = Res.string.DocumentLanguage,
                            optionSavedChoice = Language.TextLanguageInstance.localeName,
                            optionList = Language().getTextLangLocaleNameList(),
                            optionAction = { index: Int -> Language().setTextLanguage(Language().getTextLangEnumList()[index]) ; Language.TextLanguageInstance = Language().getTextLangEnumList()[index] ; doRecomposeText.value = !doRecomposeText.value}
                        )

                        //App語言 App Language
                        SettingOptionDropDownBar(
                            titleRes = Res.string.AppLanguage,
                            optionSavedChoice = Language.AppLanguageInstance.localeName,
                            optionList = Language().getAppLangLocaleNameList(),
                            optionAction = { index: Int -> Language().setAppLanguage(Language().getAppLangEnumList()[index]) ; Language.AppLanguageInstance = Language().getAppLangEnumList()[index] ; doRecompose.value = !doRecompose.value }
                        )
                    }
                }

                //偏好 Preferences
                item {
                    SettingCategory(title = removeStrQuote(Res.string.Customize)) {
                        //更換桌布 Change Wallpaper
                        SettingOptionNavigateBar(
                            titleRes = Res.string.ChangeWallPaper,
                            navigateDesc = wallpaperName.value,
                            navigateClick = { navigator.navigateLimited(BackgroundSettingRoute) }
                        )

                        //啟用模糊效果
                        SettingOptionDropDownTFBar(
                            titleRes = Res.string.UseBlurEffect,
                            optionSavedChoice = Settings().getBoolean("useHazeBlurEffect", true),
                            optionAction = { index: Int ->
                                globalHazeBlur.value = (index == 1 )
                                Settings().putBoolean("useHazeBlurEffect", index == 1)
                            }
                        )

                        //首頁平板模式下展示背景
                        SettingOptionDropDownTFBar(
                            titleRes = Res.string.SettingPadModeHomePageShowBg,
                            optionSavedChoice = Settings().getBoolean("padModeHomePageBG", true),
                            optionAction = { index: Int ->
                                globalPadHomePageBg.value = (index == 1 )
                                Settings().putBoolean("padModeHomePageBG", index == 1)
                            }
                        )
                    }
                }

                //通知 Notification
                /*

                item {
                    SettingCategory(title = removeStrQuote(Res.string.Notifi), isAvailable = false) {
                        //所有通知 All Notification
                        SettingOptionDropDownTFBar(
                            titleRes = Res.string.NotifiAll,
                            optionSavedChoice = Settings().getBoolean("enableAllNotifi", false),
                            optionAction = { index: Int -> Settings().putBoolean("enableAllNotifi", index == 1) }
                        )

                        //開拓力 Stamina Notification
                        SettingOptionDropDownTFBar(
                            titleRes = Res.string.NotiStamina,
                            optionSavedChoice = Settings().getBoolean("enableStaminaNotifi", false),
                            optionAction = { index: Int -> Settings().putBoolean("enableStaminaNotifi", index == 1) }
                        )

                        //派遣委託 Stamina Notification
                        SettingOptionDropDownTFBar(
                            titleRes = Res.string.NotiExpedition,
                            optionSavedChoice = Settings().getBoolean("enableExpeditionNotifi", false),
                            optionAction = { index: Int -> Settings().putBoolean("enableExpeditionNotifi", index == 1) }
                        )

                        //每日實訓 Mission Notification
                        SettingOptionDropDownTFBar(
                            titleRes = Res.string.NotiMission,
                            optionSavedChoice = Settings().getBoolean("enableMissionNotifi", false),
                            optionAction = { index: Int -> Settings().putBoolean("enableMissionNotifi", index == 1) }
                        )

                        //模擬宇宙 Simulated Universe Notification
                        SettingOptionDropDownTFBar(
                            titleRes = Res.string.NotiSimulatedUniverse,
                            optionSavedChoice = Settings().getBoolean("enableSimulatedUniverseNotifi", false),
                            optionAction = { index: Int -> Settings().putBoolean("enableSimulatedUniverseNotifi", index == 1) }
                        )
                    }
                }
                 */

                //支持我們 Support Us
                item {
                    SettingCategory(title = removeStrQuote(Res.string.SupportUs)) {
                        //捐贈 Donation
                        SettingOptionNavigateBar(
                            titleRes = Res.string.DonateUs,
                            navigateClick = {
                                showFunctionIsDevelopingToast()
                            } //@DoItLater("Add the function of donation")
                        )

                        //邀請使用 Invite Friends To Use Stargazer3
                        SettingOptionNavigateBar(
                            titleRes = Res.string.InviteOthers,
                            navigateClick = {
                                showFunctionIsDevelopingToast()
                            } //@DoItLater("Add the function of invite link")
                        )

                        //Discord Invite Link
                        SettingOptionNavigateBar(
                            title = "Discord",
                            navigateClick = {
                                urlHandler.openUri("https://discord.gg/uXatcbWKv2")
                            } //@DoItLater("Add the function of invite link")
                        )
                    }
                }

                //關於 About
                item {
                    SettingCategory(title = removeStrQuote(Res.string.About)) {
                        //關於應用程式 About The App
                        @DoItLater("openUrl or Remake AboutStargazerPageScreen")
                        SettingOptionNavigateBar(
                            titleRes = Res.string.AboutTheApp,
                            navigateClick = {
                                navigator.navigateLimited(AboutStargazerRoute)
                            }
                        )

                        //邀請使用 Invite Friends To Use Stargazer3
                        SettingOptionNavigateBar(
                            titleRes = Res.string.SourceCode,
                            navigateClick = {
                                urlHandler.openUri("https://github.com/Coding-band/Honkai-Stargazer")
                            }
                        )

                        //強制下載更新
                        SettingOptionNavigateBar(
                            //titleRes = Res.string.SourceCode,
                            title = removeStrQuote(Res.string.SettingReDownloadFullData),
                            navigateClick = {
                                showUpdatePopupInSetting.value = true
                                canUpdatePopup.value = true
                            }
                        )

                        //一鍵清理緩存
                        @TranslationPls
                        SettingOptionNavigateBar(
                            title = "一鍵清理緩存",
                            navigateClick = {
                                try {
                                    FileSystem.SYSTEM.deleteRecursively(FileSystem.SYSTEM_TEMPORARY_DIRECTORY.resolve("image_cache"))
                                }catch (e: Exception){
                                    showWarningToast(message = "緩存清理失敗，請稍後再試")
                                    e.printStackTrace()
                                }
                                showSuccessToast(message = "緩存清理完成")
                            }
                        )

                        //App 版本 App VersionName
                        SettingOptionNavigateBar(
                            titleRes = Res.string.AppVersion,
                            navigateDesc = BuildKonfig.appVersionName,
                            navigateClick = {
                                showSuccessToast(message = Constants.CLARA_KAMOJI)
                            }
                        )

                        val versionNameClickTimes = remember { mutableStateOf(0) }
                        //App 內部版本號 App Internal VersionName
                        SettingOptionNavigateBar(
                            title = removeStrQuote(Res.string.SettingInternalVersionCode),
                            navigateDesc = "${BuildKonfig.appProfile} ${BuildKonfig.appVersionCode}",
                            navigateClick = {
                                @TranslationPls
                                val totalClickToUnlock = 5
                                if(Settings().getBoolean("isUnlockedIIRC",false) || versionNameClickTimes.value >= totalClickToUnlock){
                                    Settings().putBoolean("isUnlockedIIRC", true)
                                    navigator.navigateLimited(IIRCHomePageRoute)
                                }else{
                                    versionNameClickTimes.value++
                                    showSuccessToast(message = "再點擊 ${totalClickToUnlock - versionNameClickTimes.value} 次即可解鎖小彩蛋")
                                }
                            }
                        )

                        //App 開發代號 Codename
                        SettingOptionNoneBar(
                            titleRes = Res.string.AppInnerVersionCode,
                            optionStatic = BuildKonfig.appVersionCodeName,
                        )

                        //系統版本 System Version
                        SettingOptionNoneBar(
                            titleRes = Res.string.OsVersion,
                            optionStatic = "${getDeviceInfo().deviceOSName} ${getDeviceInfo().deviceOSVersion}",
                        )
                        //裝置型號名稱 Model Name
                        SettingOptionNoneBar(
                            title = removeStrQuote(Res.string.SettingDeviceModel),
                            optionStatic = getDeviceInfo().deviceModel,
                        )
                    }
                }

                item { Box(modifier = Modifier.navigationBarsPadding())}

            }

            PageHeader(navigator, headerData = Screen.SettingScreen.headerData, hazeState = hazeState, backIconId = BackIcon.BACK)

            UpdateAssetsPopup(showUpdatePopupInSetting, hazeState, forceDownload = true)
        }
    }
}

@Composable
fun SettingOptionNoneBar(
    title: String? = null,
    titleRes: StringResource = Res.string.HaveNotUsed,
    optionStatic: String = "?",
){
    Column {
        val itemMaxHeight = remember { mutableStateOf(20.dp) }
        val density = LocalDensity.current.density
        Row(Modifier.background(Color(0xCCF3F9FF))) {
            //Title
            Row(modifier = Modifier.weight(19/35f).align(Alignment.CenterVertically).onSizeChanged { itemMaxHeight.value = pxToDp(it.height, density) }){
                Text(
                    color = Color.Black,
                    text = title ?: removeStrQuote(titleRes),
                    style = FontSizeNormal14(),
                    modifier = Modifier.padding(12.dp).align(Alignment.CenterVertically)
                )
            }

            //Options
            Row(modifier = Modifier.weight(16/35f).background(Color(0xCCF3F9FF)).height(itemMaxHeight.value)){
                Text(
                    color = Color.Black,
                    text = optionStatic,
                    style = FontSizeNormal14(),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(12.dp).weight(1f).align(Alignment.CenterVertically)
                )
            }
        }
        Box(Modifier.fillMaxWidth().height(2.dp).background(Color(0xFF979797)))
    }
}

@DoItLater("對於DropdownItem沒法按照設計稿展示，暫時無解")
@Composable
fun SettingOptionDropDownBar(
    title: String? = null,
    titleRes: StringResource = Res.string.HaveNotUsed,
    optionList: ArrayList<String> = arrayListOf("On", "Off"),
    optionAction : (optionIndex : Int) -> Unit = {},
    optionSavedChoice : String = "?",
){
    val optionIndex = remember { mutableStateOf(max(0, optionList.indexOf(optionSavedChoice))) }
    val expanded = remember { mutableStateOf(false) }
    val optionTextViewSize = remember { mutableStateOf(IntSize.Zero) }
    val density = LocalDensity.current.density

    Column {
        val itemMaxHeight = remember { mutableStateOf(20.dp) }
        Row(Modifier.background(Color(0xCCF3F9FF))) {
            //Title
            Row(modifier = Modifier.weight(19/35f).align(Alignment.CenterVertically).onSizeChanged { itemMaxHeight.value = pxToDp(it.height, density) }){
                Text(
                    color = Color.Black,
                    text = title ?: removeStrQuote(titleRes),
                    style = FontSizeNormal14(),
                    modifier = Modifier.padding(12.dp).align(Alignment.CenterVertically)
                )
            }

            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier
                    .weight(16 / 35f)
                    .clickable { expanded.value = !expanded.value }
            ) {
                Row(
                    modifier = Modifier
                        .background(Color(0xCCF3F9FF))
                        .height(itemMaxHeight.value)
                        .onSizeChanged { optionTextViewSize.value = it },
                ){
                    Image(
                        painter = painterResource(Res.drawable.bg_transparent),
                        contentDescription = null,
                        modifier = Modifier.padding(start = 12.dp, top = 12.dp, bottom = 12.dp, end = 0.dp).size(16.dp).align(Alignment.CenterVertically)
                    )
                    Text(
                        color = Color.Black,
                        text = optionList[optionIndex.value],
                        style = FontSizeNormal14(),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(12.dp).weight(1f).align(Alignment.CenterVertically)
                    )
                    Image(
                        painter = painterResource(Res.drawable.phorphos_caret_down_regular),
                        contentDescription = null,
                        modifier = Modifier.padding(start = 0.dp, top = 12.dp, bottom = 12.dp, end = 12.dp).size(16.dp).align(Alignment.CenterVertically)
                    )
                }
                //對於DropdownItem沒法按照設計稿展示，暫時無解
                DropdownMenuNoPadding(
                    expanded = expanded.value,
                    onDismissRequest = { expanded.value = false },
                    modifier = Modifier
                        .background(Color(0xFFDDDDDD))
                        .width(pxToDp(optionTextViewSize.value.width, density)),
                ) {
                    optionList.forEachIndexed { index, option ->
                        DropdownMenuItem(
                            onClick = {
                                optionIndex.value = index
                                expanded.value = false
                                optionAction(optionIndex.value)
                            },
                            modifier = Modifier.background(if(optionIndex.value == index) Color(0x0F000000) else Color(0x00000000))
                        ) {
                            Row{
                                Text(
                                    text = option,
                                    style = FontSizeNormal14(),
                                    color = Color(0xFF222222),
                                    modifier = Modifier.weight(1f)
                                )
                                Image(
                                    painterResource(if (optionIndex.value == index) Res.drawable.ic_selected_orange_circle else Res.drawable.bg_transparent),
                                    contentDescription = null
                                )
                            }
                        }
                    }
                }
            }
        }
        Box(Modifier.fillMaxWidth().height(2.dp).background(Color(0xFF979797)))
    }
}

@DoItLater("對於DropdownItem沒法按照設計稿展示，暫時無解")
@Composable
fun SettingOptionDropDownTFBar(
    title: String? = null,
    titleRes: StringResource = Res.string.HaveNotUsed,
    optionAction : (optionIndex : Int) -> Unit = {},
    optionSavedChoice : Boolean = true,
){
    val optionIndex = remember { mutableStateOf(if(optionSavedChoice) 1 else 0) }
    val expanded = remember { mutableStateOf(false) }
    val optionTextViewSize = remember { mutableStateOf(IntSize.Zero) }
    val density = LocalDensity.current.density
    val defaultDropdownChoices = arrayListOf(removeStrQuote(Res.string.SwitchOff), removeStrQuote(Res.string.SwitchOn))


    Column {
        val itemMaxHeight = remember { mutableStateOf(20.dp) }
        Row(Modifier.background(Color(0xCCF3F9FF))) {
            //Title
            Row(modifier = Modifier.weight(19/35f).align(Alignment.CenterVertically).onSizeChanged { itemMaxHeight.value = pxToDp(it.height, density) }){
                Text(
                    color = Color.Black,
                    text = title ?: removeStrQuote(titleRes),
                    style = FontSizeNormal14(),
                    modifier = Modifier.padding(12.dp).align(Alignment.CenterVertically),
                )
            }

            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier
                    .weight(16 / 35f)
                    .clickable { expanded.value = !expanded.value }
            ) {
                Row(
                    modifier = Modifier
                        .background(Color(0xCCF3F9FF))
                        .height(itemMaxHeight.value)
                        .onSizeChanged { optionTextViewSize.value = it },
                ){
                    Image(
                        painter = painterResource(Res.drawable.bg_transparent),
                        contentDescription = null,
                        modifier = Modifier.padding(start = 12.dp, top = 12.dp, bottom = 12.dp, end = 0.dp).size(16.dp).align(Alignment.CenterVertically)
                    )
                    Text(
                        color = Color.Black,
                        text = defaultDropdownChoices[optionIndex.value],
                        style = FontSizeNormal14(),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(12.dp).weight(1f).align(Alignment.CenterVertically)
                    )
                    Image(
                        painter = painterResource(Res.drawable.phorphos_caret_down_regular),
                        contentDescription = null,
                        modifier = Modifier.padding(start = 0.dp, top = 12.dp, bottom = 12.dp, end = 12.dp).size(16.dp).align(Alignment.CenterVertically)
                    )
                }
                //對於DropdownItem沒法按照設計稿展示，暫時無解
                DropdownMenuNoPadding(
                    expanded = expanded.value,
                    onDismissRequest = { expanded.value = false },
                    modifier = Modifier
                        .background(Color(0xFFDDDDDD))
                        .width(pxToDp(optionTextViewSize.value.width, density)),
                ) {
                    defaultDropdownChoices.forEachIndexed { index, option ->
                        DropdownMenuItem(
                            onClick = {
                                optionIndex.value = index
                                expanded.value = false
                                optionAction(optionIndex.value)
                            },
                            modifier = Modifier.background(if(optionIndex.value == index) Color(0x0F000000) else Color(0x00000000))
                        ) {
                            Row{
                                Text(
                                    text = option,
                                    style = FontSizeNormal14(),
                                    color = Color(0xFF222222),
                                    modifier = Modifier.weight(1f)
                                )
                                Image(
                                    painterResource(if (optionIndex.value == index) Res.drawable.ic_selected_orange_circle else Res.drawable.bg_transparent),
                                    contentDescription = null
                                )
                            }
                        }
                    }
                }
            }
        }
        Box(Modifier.fillMaxWidth().height(2.dp).background(Color(0xFF979797)))
    }
}

@Composable
fun SettingOptionNavigateBar(
    title: String? = null,
    titleRes: StringResource = Res.string.HaveNotUsed,
    navigateDesc: String? = null,
    navigateClick: () -> Unit = {},
){
    Column {
        val itemMaxHeight = remember { mutableStateOf(20.dp) }
        val density = LocalDensity.current.density
        Row(Modifier.background(Color(0xCCF3F9FF))) {
            //Title
            Row(modifier = Modifier.weight(19/35f).align(Alignment.CenterVertically).onSizeChanged { itemMaxHeight.value = pxToDp(it.height, density) }){
                Text(
                    color = Color.Black,
                    text = title ?: removeStrQuote(titleRes),
                    style = FontSizeNormal14(),
                    modifier = Modifier.padding(12.dp).align(Alignment.CenterVertically)
                )
            }

            //Options
            Row(modifier = Modifier.weight(16/35f).background(Color(0xCCF3F9FF)).height(itemMaxHeight.value).clickable { navigateClick() }){
                Image(
                    painter = painterResource(Res.drawable.bg_transparent),
                    contentDescription = null,
                    modifier = Modifier.padding(start = 12.dp, top = 12.dp, bottom = 12.dp, end = 0.dp).size(16.dp).align(Alignment.CenterVertically)
                )
                Text(
                    color = Color.Black,
                    text = navigateDesc ?: removeStrQuote(Res.string.Navigate),
                    style = FontSizeNormal14(),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(12.dp).weight(1f).align(Alignment.CenterVertically)
                )
                Image(
                    painter = painterResource(Res.drawable.phorphos_caret_right_regular),
                    contentDescription = null,
                    modifier = Modifier.padding(start = 0.dp, top = 12.dp, bottom = 12.dp, end = 12.dp ).size(16.dp).align(Alignment.CenterVertically)
                )
            }
        }
        Box(Modifier.fillMaxWidth().height(2.dp).background(Color(0xFF979797)))
    }
}

@Composable
fun SettingCategory(
    title: String? = null,
    titleRes: StringResource = Res.string.HaveNotUsed,
    isAvailable: Boolean = true,
    content: @Composable () -> Unit,
){
    Column(modifier = Modifier.padding(top = 20.dp)) {
        Text(
            color = Color.White,
            text = title ?: removeStrQuote(titleRes),
            style = FontSizeNormal16(),
            modifier = Modifier.padding(bottom = 6.dp )
        )


        Box(modifier = Modifier.wrapContentHeight()){
            Column { content() }

            if(!isAvailable){
                Box(
                    Modifier.matchParentSize().background(Color(0xCC3D3D3D)).clickable(
                        enabled = true,
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                        onClick = {})
                ) {
                    Text(
                        "Coming Soon",
                        color = Color.White,
                        style = FontSizeNormalLarge24(),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}
fun booleanToOnOff(boolean: Boolean): String {
    return if(boolean) "On" else "Off"
}