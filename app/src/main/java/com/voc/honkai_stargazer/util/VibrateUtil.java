/*
 * Project Honkai Stargazer (崩壞•星穹觀星者) was
 * Created & Develop by Voc-夜芷冰 , Programmer of Xectorda
 * Copyright © 2023 Xectorda 版權所有
 */

package com.voc.honkai_stargazer.util;

import android.content.Context;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.os.VibratorManager;

public class VibrateUtil {
    public static final int VIBRATE_LEVEL0 = 0;
    public static final int VIBRATE_LEVEL1 = 60;
    public static final int VIBRATE_LEVEL2 = 120;
    public static final int VIBRATE_LEVEL3 = 180;
    public static final int VIBRATE_LEVEL4 = 255;

    public static void vibrate(Context context, int... LEVEL){
        if (context == null){return;}
        int vibrationValue = 0;
        int level = (LEVEL.length == 0 ? context.getSharedPreferences("user_info",Context.MODE_PRIVATE).getInt("vibrator_lvl",0) : LEVEL[0]);
        switch (level){
            case 0 : vibrationValue = VIBRATE_LEVEL0;break;
            case 1 : vibrationValue = VIBRATE_LEVEL1;break;
            case 2 : vibrationValue = VIBRATE_LEVEL2;break;
            case 3 : vibrationValue = VIBRATE_LEVEL3;break;
            case 4 : vibrationValue = VIBRATE_LEVEL4;break;
        }
        if (vibrationValue == 0) return;
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(VibrationEffect.createOneShot(
                100,
                vibrationValue
        ));
    }

    public static void setVibrationLvl(Context context, int LEVEL){
        if (context == null){return;}
        int vibrationValue = VIBRATE_LEVEL0;
        switch (LEVEL){
            case 0 : vibrationValue = VIBRATE_LEVEL0;break;
            case 1 : vibrationValue = VIBRATE_LEVEL1;break;
            case 2 : vibrationValue = VIBRATE_LEVEL2;break;
            case 3 : vibrationValue = VIBRATE_LEVEL3;break;
            case 4 : vibrationValue = VIBRATE_LEVEL4;break;
        }
        context.getSharedPreferences("user_info",Context.MODE_PRIVATE).edit().putInt("vibrator_lvl",LEVEL).apply();
    }

    public static int getVibrationLvl(Context context){
        if (context == null){return -1;}
        return context.getSharedPreferences("user_info",Context.MODE_PRIVATE).getInt("vibrator_lvl",0);
    }

}
