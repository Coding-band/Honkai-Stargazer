package screens

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.russhwolf.settings.Settings
import com.voc.honkaistargazer.BuildKonfig
import components.BackIcon
import components.DropdownMenuNoPadding
import components.HeaderData
import components.PAGE_HEADER_HEIGHT
import components.PageHeader
import components.defaultHeaderData
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
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
import files.NotiExpedition
import files.NotiMission
import files.NotiSimulatedUniverse
import files.NotiStamina
import files.Notifi
import files.NotifiAll
import files.OsVersion
import files.Res
import files.SettingPersonalPageDisable
import files.SettingPersonalPageShow
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
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import types.Character
import types.Wallpaper
import utils.FontSizeNormal14
import utils.FontSizeNormal16
import utils.FontSizeNormalLarge24
import utils.Language
import utils.UtilTools
import utils.annotation.DoItLater
import utils.navigation.Screen
import utils.navigation.navigateLimited
import utils.res.LocalComposeEnvironment
import kotlin.math.max

@Composable
fun LocaleController() {
    var localeTextField by remember { mutableStateOf("ar") }
    val locale = LocalComposeEnvironment.current

    OutlinedTextField(
        modifier = Modifier.padding(16.dp).fillMaxWidth(),
        value = localeTextField,
        onValueChange = { localeTextField = it },
        label = { Text("Locale") },
        enabled = true,
        colors = TextFieldDefaults.textFieldColors(
            disabledTextColor = MaterialTheme.colors.onSurface,
            disabledLabelColor = MaterialTheme.colors.onSurface,
        )
    )

    OutlinedButton(
        onClick = {
            locale.setLocale(Locale(localeTextField))
        },
        modifier = Modifier.padding(16.dp)
    ) {
        Text("Change Locale")
    }
}

@Composable
fun SettingScreen(modifier: Modifier = Modifier, navigator: Navigator, headerData: HeaderData = defaultHeaderData
){
    val hazeState = remember { HazeState() }
    val wallpaper = Wallpaper.wallpaperList.find { it.id == Settings().getString("backgroundImage", "221000") } ?: Wallpaper.wallpaperList[0]
    val doRecompose = remember { mutableStateOf(false) }

    key(doRecompose.value){
        Box {

            LazyColumn (
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .haze(state = hazeState),
            ){
                //Spacer for padding status bar
                item { Spacer(modifier = Modifier.statusBarsPadding().height(PAGE_HEADER_HEIGHT)) }

                //item { LocaleController() }

                //帳號設定 Account Setup
                item {
                    SettingCategory(title = UtilTools().removeStringResDoubleQuotes(Res.string.AccountSetup).replace("$"+"{1}", "900033852")) {

                        //使用邀請碼 Use Invite Code
                        SettingOptionNoneBar(
                            titleRes = Res.string.UseInviteCode,
                            optionStatic = UtilTools().removeStringResDoubleQuotes(Res.string.HaveNotUsed)
                        )

                        //個人頁面展示 User Info Page Display All Character?
                        SettingOptionDropDownBar(
                            titleRes = Res.string.SettingPersonalPageShow,
                            //optionSavedChoice = @DoItLater("Add the function of show personal page"),
                            optionList = arrayListOf(UtilTools().removeStringResDoubleQuotes(Res.string.SettingPersonalPageDisable), UtilTools().removeStringResDoubleQuotes(Res.string.SettingPersonalPageShow)),
                            optionAction = { index: Int -> DoItLater("Add function of show personal page") }
                        )
                    }
                }

                //語言設定 Language Setting
                //Known Issue : Cannot Implement Locale Change, please refer to https://github.com/JetBrains/compose-multiplatform/issues/4347
                item {
                    SettingCategory(title = UtilTools().removeStringResDoubleQuotes(Res.string.LanguageSetup)) {
                        //文本語言 Text Language
                        SettingOptionDropDownBar(
                            titleRes = Res.string.DocumentLanguage,
                            optionSavedChoice = Language.TextLanguageInstance.localeName,
                            optionList = Language().getTextLangLocaleNameList(),
                            optionAction = { index: Int -> Language().setTextLanguage(Language().getTextLangEnumList()[index]) ; Language.TextLanguageInstance = Language().getTextLangEnumList()[index] }
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
                    SettingCategory(title = UtilTools().removeStringResDoubleQuotes(Res.string.Customize)) {
                        //更換桌布 Change Wallpaper
                        SettingOptionNavigateBar(
                            titleRes = Res.string.ChangeWallPaper,
                            navigateDesc = wallpaper.localeName ?: Character.getCharacterItemFromJSON(wallpaper.id).displayName ?: "?",
                            navigateClick = { navigator.navigateLimited(Screen.BackgroundSettingScreen.route) }
                        )

                        //啟用模糊效果
                        SettingOptionDropDownTFBar(
                            titleRes = Res.string.UseBlurEffect,
                            optionSavedChoice = Settings().getBoolean("useBlurEffect", true),
                            optionAction = { index: Int -> Settings().putBoolean("useBlurEffect", index == 1) }
                        )
                    }
                }

                //通知 Notification
                item {
                    SettingCategory(title = UtilTools().removeStringResDoubleQuotes(Res.string.Notifi), isAvailable = false) {
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

                //支持我們 Support Us
                item {
                    SettingCategory(title = UtilTools().removeStringResDoubleQuotes(Res.string.SupportUs)) {
                        //捐贈 Donation
                        SettingOptionNavigateBar(
                            titleRes = Res.string.DonateUs,
                            navigateClick = { } //@DoItLater("Add the function of donation")
                        )

                        //邀請使用 Invite Friends To Use Stargazer3
                        SettingOptionNavigateBar(
                            titleRes = Res.string.InviteOthers,
                            navigateClick = { } //@DoItLater("Add the function of invite link")
                        )

                        //Discord Invite Link
                        SettingOptionNavigateBar(
                            title = "Discord",
                            navigateClick = { } //@DoItLater("Add the function of invite link")
                        )
                    }
                }

                //關於 About
                item {
                    SettingCategory(title = UtilTools().removeStringResDoubleQuotes(Res.string.About)) {
                        //捐贈 Donation
                        SettingOptionNavigateBar(
                            titleRes = Res.string.AboutTheApp,
                            navigateClick = { } //@DoItLater("Add the function of donation")
                        )

                        //邀請使用 Invite Friends To Use Stargazer3
                        SettingOptionNavigateBar(
                            titleRes = Res.string.SourceCode,
                            navigateClick = { } //@DoItLater("Add the function of invite link")
                        )

                        //App 版本 App Version
                        SettingOptionNavigateBar(
                            titleRes = Res.string.AppVersion,
                            navigateDesc = "${
                                if (!arrayListOf("PRODUCTION", "RELEASE").contains(BuildKonfig.appProfile)) "${BuildKonfig.appProfile} " else " "
                            }${BuildKonfig.appVersionName} (${BuildKonfig.appVersionCode})",
                            navigateClick = { } //@DoItLater("Add the function of invite link")
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
                    }
                }

            }

            PageHeader(navigator, headerData = headerData, hazeState = hazeState, backIconId = BackIcon.BACK)
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
        Row(Modifier.background(Color(0xCCF3F9FF))) {
            //Title
            Row(modifier = Modifier.weight(19/35f)){
                Text(
                    color = Color.Black,
                    text = title ?: UtilTools().removeStringResDoubleQuotes(titleRes),
                    style = FontSizeNormal14(),
                    modifier = Modifier.padding(12.dp).align(Alignment.CenterVertically)
                )
            }

            //Options
            Row(modifier = Modifier.weight(16/35f).background(Color(0xCCF3F9FF))){
                Image(
                    painter = painterResource(Res.drawable.bg_transparent),
                    contentDescription = null,
                    modifier = Modifier.padding(12.dp).size(16.dp).align(Alignment.CenterVertically)
                )
                Text(
                    color = Color.Black,
                    text = optionStatic,
                    style = FontSizeNormal14(),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(12.dp).weight(1f).align(Alignment.CenterVertically)
                )
                Image(
                    painter = painterResource(Res.drawable.bg_transparent),
                    contentDescription = null,
                    modifier = Modifier.padding(12.dp).size(16.dp).align(Alignment.CenterVertically)
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
        Row(Modifier.background(Color(0xCCF3F9FF))) {
            //Title
            Row(modifier = Modifier.weight(19/35f)){
                Text(
                    color = Color.Black,
                    text = title ?: UtilTools().removeStringResDoubleQuotes(titleRes),
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
                        .onSizeChanged { optionTextViewSize.value = it },
                ){
                    Image(
                        painter = painterResource(Res.drawable.bg_transparent),
                        contentDescription = null,
                        modifier = Modifier.padding(12.dp).size(16.dp).align(Alignment.CenterVertically)
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
                        modifier = Modifier.padding(12.dp).size(16.dp).align(Alignment.CenterVertically)
                    )
                }
                //對於DropdownItem沒法按照設計稿展示，暫時無解
                DropdownMenuNoPadding(
                    expanded = expanded.value,
                    onDismissRequest = { expanded.value = false },
                    modifier = Modifier
                        .background(Color(0xFFDDDDDD))
                        .width(UtilTools().pxToDp(optionTextViewSize.value.width, density)),
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
    val defaultDropdownChoices = arrayListOf(UtilTools().removeStringResDoubleQuotes(Res.string.SwitchOff), UtilTools().removeStringResDoubleQuotes(Res.string.SwitchOn))


    Column {
        Row(Modifier.background(Color(0xCCF3F9FF))) {
            //Title
            Row(modifier = Modifier.weight(19/35f)){
                Text(
                    color = Color.Black,
                    text = title ?: UtilTools().removeStringResDoubleQuotes(titleRes),
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
                        .onSizeChanged { optionTextViewSize.value = it },
                ){
                    Image(
                        painter = painterResource(Res.drawable.bg_transparent),
                        contentDescription = null,
                        modifier = Modifier.padding(12.dp).size(16.dp).align(Alignment.CenterVertically)
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
                        modifier = Modifier.padding(12.dp).size(16.dp).align(Alignment.CenterVertically)
                    )
                }
                //對於DropdownItem沒法按照設計稿展示，暫時無解
                DropdownMenuNoPadding(
                    expanded = expanded.value,
                    onDismissRequest = { expanded.value = false },
                    modifier = Modifier
                        .background(Color(0xFFDDDDDD))
                        .width(UtilTools().pxToDp(optionTextViewSize.value.width, density)),
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
        Row(Modifier.background(Color(0xCCF3F9FF))) {
            //Title
            Row(modifier = Modifier.weight(19/35f)){
                Text(
                    color = Color.Black,
                    text = title ?: UtilTools().removeStringResDoubleQuotes(titleRes),
                    style = FontSizeNormal14(),
                    modifier = Modifier.padding(12.dp).align(Alignment.CenterVertically)
                )
            }

            //Options
            Row(modifier = Modifier.weight(16/35f).background(Color(0xCCF3F9FF)).clickable { navigateClick() }){
                Image(
                    painter = painterResource(Res.drawable.bg_transparent),
                    contentDescription = null,
                    modifier = Modifier.padding(12.dp).size(16.dp).align(Alignment.CenterVertically)
                )
                Text(
                    color = Color.Black,
                    text = navigateDesc ?: UtilTools().removeStringResDoubleQuotes(Res.string.Navigate),
                    style = FontSizeNormal14(),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(12.dp).weight(1f).align(Alignment.CenterVertically)
                )
                Image(
                    painter = painterResource(Res.drawable.phorphos_caret_right_regular),
                    contentDescription = null,
                    modifier = Modifier.padding(12.dp).size(16.dp).align(Alignment.CenterVertically)
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
            text = title ?: UtilTools().removeStringResDoubleQuotes(titleRes),
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