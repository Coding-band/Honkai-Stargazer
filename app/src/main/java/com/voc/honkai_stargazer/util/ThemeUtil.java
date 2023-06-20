/*
 * Project Honkai Stargazer (崩壞•星穹觀星者) was
 * Created & Develop by Voc-夜芷冰 , Programmer of Xectorda
 * Copyright © 2023 Xectorda 版權所有
 */

package com.voc.honkai_stargazer.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;

public class ThemeUtil {
    public static final String DAYNIGHT_DAY = "DAY";
    public static final String DAYNIGHT_NIGHT = "NIGHT";
    public static final String DAYNIGHT_FOLLOW_SYSTEM = "FOLLOW_SYSTEM";


    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public void init(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences("user_info",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public int themeTint(){
        return Color.parseColor("#456686");
    }

}
