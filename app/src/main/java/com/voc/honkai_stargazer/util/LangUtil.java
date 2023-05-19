/*
 * Project Honkai Stargazer (崩壞•星穹觀星者) was
 * Created & Develop by Voc-夜芷冰 , Programmer of Xectorda
 * Copyright © 2023 Xectorda 版權所有
 */

package com.voc.honkai_stargazer.util;

import android.content.Context;
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
        UA(new Locale("uk", "UA"),"ua","українська");

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

    private static Context context;
    public static LangUtil getInstance(LangType type){
        if (INSTANCE == null) {
            INSTANCE = new LangUtil();
        }
        return(INSTANCE);
    }
    private LangUtil(){};

    public static Context getAttachBaseContext(Context context, LangType type) {
        if (Build.VERSION.SDK_INT >= 24) {
            return updateResources(context, type);
        }
        changeResLanguage(context, type);
        return context;
    }

    private static Context updateResources(Context context, LangType type) {
        Resources resources = context.getResources();
        Locale currentLang = type.getLocale();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(currentLang);
        return context.createConfigurationContext(configuration);
    }

    private static void changeResLanguage(Context context, LangType type) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(type.getLocale());
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }
}
