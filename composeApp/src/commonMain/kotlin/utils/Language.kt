package utils

import androidx.compose.runtime.Composable
import com.russhwolf.settings.Settings


//App語言 Language for App (R.string)
class Language() {

    enum class AppLanguage(var localeName: String, var folderName: String) {
        VOCCHINESE("粵語", "yue"),
        EN("English", "en"),
        ZH_CN("简体中文", "zh_cn"),
        ZH_HK("繁體中文", "zh_hk"),
        JP("日本語", "jp"),
        FR("Français", "fr"),
        RU("Русский", "ru"),
        DE("Deutsch", "de"),
        PT("Português", "pt_pt"),
        VI("tiếng Việt", "vi"),
        ES("Español", "es_es"),
        KR("한국어", "kr"),
        TH("ภาษาไทย", "th"),
        JYU_YAM("ㄓㄨˋ ㄧㄣ", "zh"),
        UK("Українська", "uk");
    }
    enum class TextLanguage(val localeName: String, val folderName: String, val hoyolabName: String, val langCode: String) {
        EN("English", "en", "en-us", "en"),
        ZH_CN("简体中文", "zh_cn", "zh-cn", "cn"),
        ZH_HK("繁體中文", "zh_hk", "zh-tw", "cht"),
        JP("日本語", "jp", "ja-jp", "jp"),
        FR("Français", "fr", "fr-fr", "fr"),
        RU("Русский", "ru", "ru-ru", "ru"),
        DE("Deutsch", "de", "de-de", "de"),
        PT("Português", "pt_pt", "pt-pt", "pt"),
        VI("tiếng Việt", "vi", "vi-vn", "vi"),
        ES("Español", "es_es", "es-es", "es"),
        KR("한국어", "kr", "ko-kr", "kr"),
        TH("ภาษาไทย", "th", "th-th", "th"),

        //@DoItLater("Add support to ITALIAN") IT("ITALIAN", "it", "it-it"),
        //@DoItLater("Add support to TURKISH") TR("TURKISH", "tr", "tr-tr"),
        //@DoItLater("Add support to INDONESIAN") ID("INDONESIAN", "id", "id-id"),
    }

    companion object{
        var TextLanguageInstance = TextLanguage.valueOf(Settings().getString("textLanguage", TextLanguage.EN.name))
        var AppLanguageInstance = AppLanguage.valueOf(Settings().getString("appLanguage", AppLanguage.EN.name))
    }

    @Composable
    fun initLangSetting(){

    }


    fun setAppLanguage(lang: AppLanguage){
        Settings().putString("appLanguage", lang.name)
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
}