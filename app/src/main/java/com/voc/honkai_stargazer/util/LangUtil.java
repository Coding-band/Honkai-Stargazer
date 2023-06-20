/*
 * Project Honkai Stargazer (崩壞•星穹觀星者) was
 * Created & Develop by Voc-夜芷冰 , Programmer of Xectorda
 * Copyright © 2023 Xectorda 版權所有
 */

package com.voc.honkai_stargazer.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.Log;

import java.util.Locale;

public class LangUtil {

    private static LangUtil INSTANCE = null;

    public enum LangType{
        EN(Locale.ENGLISH,"en","English"),
        ZH_CN(Locale.SIMPLIFIED_CHINESE,"zh_cn","简体中文"),
        ZH_HK(Locale.TRADITIONAL_CHINESE,"zh_hk","繁體中文"),
        JP(Locale.JAPAN,"jp","日本語"),
        FR(Locale.FRANCE,"fr","Français"),
        RU(new Locale("ru", "RU"),"ru","Русский"),
        UA(new Locale("uk", "UA"),"ua","Українська"),
        DE(new Locale("de", "DE"),"de","Deutsch"),
        PT(new Locale("pt", "PT"),"pt","Português");

        private Locale locale;
        private String code;
        private String fullName;

        LangType(Locale locale, String code, String fullName){
            this.locale = locale;
            this.code = code;
            this.fullName = fullName;
        }
        public Locale getLocale() {
            return locale;
        }
        public String getCode() {
            return code;
        }
        public String getFullName() {
            return fullName;
        }
    }

    public static LangUtil getInstance(LangType type){
        if (INSTANCE == null) {
            INSTANCE = new LangUtil();
        }
        return(INSTANCE);
    }

    public static LangType getLangTypeByCode(String code){
        LangType langType = LangType.EN;
        switch (code){
            case ItemRSS.LANG_EN: langType = LangType.EN;break;
            case ItemRSS.LANG_ZH_CN: langType = LangType.ZH_CN;break;
            case ItemRSS.LANG_ZH_HK: langType = LangType.ZH_HK;break;
            case ItemRSS.LANG_JP: langType = LangType.JP;break;
            case ItemRSS.LANG_FR: langType = LangType.FR;break;
            case ItemRSS.LANG_RU: langType = LangType.RU;break;
            case ItemRSS.LANG_UA: langType = LangType.UA;break;
            case ItemRSS.LANG_DE: langType = LangType.DE;break;
            case ItemRSS.LANG_PT: langType = LangType.PT;break;
            default:langType = LangType.EN;break;
        }
        return langType;
    }

    private LangUtil(){};

    public static Context getAttachBaseContext(Context context, LangType type) {
        return updateResources(context, type);
    }


    public static void updateLocale(Context baseContext, LangType type, Activity activity) {


        SharedPreferences.Editor editor = baseContext.getSharedPreferences("user_info",Context.MODE_PRIVATE).edit();
        switch (type){
            case ZH_CN: editor.putString("curr_lang",LangType.ZH_CN.getCode()).apply();break;
            case ZH_HK: editor.putString("curr_lang",LangType.ZH_HK.getCode()).apply();break;
            case FR: editor.putString("curr_lang",LangType.FR.getCode()).apply();break;
            case JP: editor.putString("curr_lang",LangType.JP.getCode()).apply();break;
            case RU: editor.putString("curr_lang",LangType.RU.getCode()).apply();break;
            case UA: editor.putString("curr_lang",LangType.UA.getCode()).apply();break;
            case DE: editor.putString("curr_lang",LangType.DE.getCode()).apply();break;
            case PT: editor.putString("curr_lang",LangType.PT.getCode()).apply();break;
            default:
            case EN: editor.putString("curr_lang",LangType.EN.getCode()).apply();break;
        }

        Locale locale = new Locale(type.getCode());
        Locale.setDefault(locale);
        Resources resources = activity.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }

    private static Context updateResources(Context context, LangType type) {
        Resources resources = context.getResources();
        Locale currentLang = type.getLocale();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(currentLang);
        SharedPreferences.Editor editor = context.getSharedPreferences("user_info",Context.MODE_PRIVATE).edit();
        switch (type){
            case ZH_CN: editor.putString("curr_lang",LangType.ZH_CN.getCode()).apply();break;
            case ZH_HK: editor.putString("curr_lang",LangType.ZH_HK.getCode()).apply();break;
            case FR: editor.putString("curr_lang",LangType.FR.getCode()).apply();break;
            case JP: editor.putString("curr_lang",LangType.JP.getCode()).apply();break;
            case RU: editor.putString("curr_lang",LangType.RU.getCode()).apply();break;
            case UA: editor.putString("curr_lang",LangType.UA.getCode()).apply();break;
            case DE: editor.putString("curr_lang",LangType.DE.getCode()).apply();break;
            case PT: editor.putString("curr_lang",LangType.PT.getCode()).apply();break;
            default:
            case EN: editor.putString("curr_lang",LangType.EN.getCode()).apply();break;
        }

        return context.createConfigurationContext(configuration);
    }

    private static void changeResLanguage(Context context, LangType type) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(type.getLocale());
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }
}
