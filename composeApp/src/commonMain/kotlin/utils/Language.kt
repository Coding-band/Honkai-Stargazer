package utils

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import changeLanguage
import com.russhwolf.settings.Settings
import components.AppDialog
import components.UIButton
import dev.chrisbanes.haze.HazeState
import files.LanguageSetup
import files.Res
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder


//App語言 Language for App (R.string)
class Language() {

    enum class AppLanguage(var localeName: String, var folderName: String, var localeCode: String) {
        VOCCHINESE("粵語", "yue", "zh-HK"),
        EN("English", "en", "en"),
        ZH_CN("简体中文", "zh_cn", "zh-CN"),
        ZH_HK("繁體中文", "zh_hk", "zh-TW"),
        JP("日本語", "jp", "ja-JP"),
        FR("Français", "fr", "fr-FR"),
        RU("Русский", "ru", "ru-RU"),
        DE("Deutsch", "de", "de-DE"),
        PT("Português", "pt_pt", "pt-PT"),
        VI("tiếng Việt", "vi", "vi"),
        ES("Español", "es_es", "es-ES"),
        KR("한국어", "kr", "ko-KR"),
        TH("ภาษาไทย", "th", "th-TH"),
        JYU_YAM("ㄓㄨˋ ㄧㄣ", "zh", "zh-TW"),
        UK("Українська", "uk", "uk");
    }

    object TextLanguageSerializer : KSerializer<Language.TextLanguage> {
        override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("TextLanguage", PrimitiveKind.STRING)

        override fun serialize(encoder: Encoder, value: Language.TextLanguage) {
            encoder.encodeString(value.name)
        }

        override fun deserialize(decoder: Decoder): Language.TextLanguage {
            return Language.TextLanguage.valueOf(decoder.decodeString())
        }
    }
    @Serializable
    enum class TextLanguage(val localeName: String, val folderName: String, val hoyolabName: String, val langCode: String) {
        @SerialName("en")
        EN("English", "en", "en-us", "en"),

        @SerialName("zh_cn")
        ZH_CN("简体中文", "zh_cn", "zh-cn", "cn"),

        @SerialName("zh_hk")
        ZH_HK("繁體中文", "zh_hk", "zh-tw", "cht"),

        @SerialName("jp")
        JP("日本語", "jp", "ja-jp", "jp"),

        @SerialName("fr")
        FR("Français", "fr", "fr-fr", "fr"),

        @SerialName("ru")
        RU("Русский", "ru", "ru-ru", "ru"),

        @SerialName("de")
        DE("Deutsch", "de", "de-de", "de"),

        @SerialName("pt")
        PT("Português", "pt", "pt-pt", "pt"),

        @SerialName("vi")
        VI("tiếng Việt", "vi", "vi-vn", "vi"),

        @SerialName("es")
        ES("Español", "es", "es-es", "es"),

        @SerialName("kr")
        KR("한국어", "kr", "ko-kr", "kr"),

        @SerialName("th")
        TH("ภาษาไทย", "th", "th-th", "th"),

        @SerialName("it")
        IT("ITALIAN", "it", "it-it", "it"),

        @SerialName("tr")
        TR("TURKISH", "tr", "tr-tr", "tr"),

        @SerialName("id")
        ID("INDONESIAN", "id", "id-id", "id"),
    }

    companion object{
        var TextLanguageInstance = TextLanguage.valueOf(Settings().getString("textLanguage", TextLanguage.EN.name))
        var AppLanguageInstance = AppLanguage.valueOf(Settings().getString("appLanguage", AppLanguage.EN.name))
    }

    @Composable
    fun initLangSetting(){

    }


    fun setAppLanguage(lang: AppLanguage = AppLanguageInstance, isFirstInit: Boolean = false){
        Settings().putString("appLanguage", lang.name)
        val langC = lang.localeCode.split("-")
        changeLanguage(langC[0], langC.getOrNull(1))
        AppLanguageInstance = lang
        if(isFirstInit){
            TextLanguageInstance = TextLanguage.entries.firstOrNull { it.folderName == lang.folderName } ?: TextLanguage.EN
        }
    }

    fun setAppLanguage(lang: String){
        Settings().putString("appLanguage", lang)
    }

    fun setTextLanguage(lang: TextLanguage){
        Settings().putString("textLanguage", lang.name)
    }

    fun setTextLanguage(lang: String){
        Settings().putString("textLanguage", lang)
    }

    fun getAppLangLocaleNameList(): ArrayList<String> {
        val langLocaleNameList = arrayListOf<String>()
        AppLanguage.entries.forEach { language ->
            langLocaleNameList.add(language.localeName)
        }

        return langLocaleNameList
    }

    fun getAppLangEnumList(): ArrayList<AppLanguage> {
        val appLangList = arrayListOf<AppLanguage>()
        AppLanguage.entries.forEach { language ->
            appLangList.add(language)
        }

        return appLangList
    }

    fun getTextLangLocaleNameList(): ArrayList<String> {
        val langLocaleNameList = arrayListOf<String>()
        TextLanguage.entries.forEach { language ->
            langLocaleNameList.add(language.localeName)
        }

        return langLocaleNameList
    }

    fun getTextLangEnumList(): ArrayList<TextLanguage> {
        val textLangList = arrayListOf<TextLanguage>()
        TextLanguage.entries.forEach { language ->
            textLangList.add(language)
        }

        return textLangList
    }

    @Composable
    fun initAppLanguagePopup(showPopup : MutableState<Boolean>, hazeState: HazeState = remember { HazeState() }){
        if (showPopup.value){
            Popup(alignment = Alignment.Center) {
                val scrollState = rememberScrollState()
                AppDialog(
                    titleString = UtilTools().removeStringResDoubleQuotes(Res.string.LanguageSetup),
                    hazeState = hazeState,
                    components = {
                        Column(Modifier.fillMaxHeight(2/3f).verticalScroll(state = scrollState)) {
                            // Different Language Choices
                            for (appLanguage in AppLanguage.entries.filter { it != AppLanguage.JYU_YAM && it != AppLanguage.VOCCHINESE }) {
                                UIButton(
                                    text = appLanguage.localeName,
                                    onClick = {
                                        setAppLanguage(appLanguage, true)
                                        showPopup.value = false
                                        Preferences().AppSettings.setLangInitialized()
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
}