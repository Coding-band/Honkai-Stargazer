/*
 * Project Honkai Stargazer (崩壞•星穹觀星者) was
 * Created & Develop by Voc-夜芷冰 , Programmer of Xectorda
 * Copyright © 2023 Xectorda 版權所有
 */

package com.voc.honkai_stargazer.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.ColorUtils;
import androidx.core.graphics.drawable.DrawableCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.voc.honkai_stargazer.R;
import com.willy.ratingbar.ScaleRatingBar;

public class ThemeUtil {
    public static final String DAYNIGHT_DAY = "DAY";
    public static final String DAYNIGHT_NIGHT = "NIGHT";
    public static final String DAYNIGHT_FOLLOW_SYSTEM = "FOLLOW_SYSTEM";

    public static final float TINT_HEAVY = 0.4f;
    public static final float TINT_COMMON = 0.6f;
    public static final float TINT_CARD = 0.85f;

    public static final String COLOR_1 = "#6750A4";
    public static final String COLOR_2 = "#009688";
    public static final String COLOR_3 = "#F6C032";
    public static final String COLOR_4 = "#32C7F6";
    public static final String COLOR_5 = "#F63279";
    public static final String COLOR_6 = "#73D246";

    private Context context;
    private Activity activity;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    int themedColor = Color.parseColor("#009688");
    public ThemeUtil(Context context, Activity activity){
        this.context = context;
        this.activity = activity;
        sharedPreferences = context.getSharedPreferences("user_info",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void navigationSetup(Window window){
        themedColor = Color.parseColor(sharedPreferences.getString("themedColor","#6750A4"));
        window.setNavigationBarColor(colorMultiply(themedColor,context.getColor(R.color.nav_bar_tint),TINT_COMMON,true));
    }
    public void themeTint(ViewGroup... parentLayouts){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        themedColor = Color.parseColor(sharedPreferences.getString("themedColor","#6750A4"));
        ColorStateList baseList = new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_enabled},
                        new int[]{android.R.attr.state_selected},
                        new int[]{android.R.attr.state_checked},
                        new int[]{-android.R.attr.state_checked},
                },
                new int[] {
                        themedColor,
                        colorMultiply(themedColor,context.getColor(R.color.nav_bar_selected_tint),TINT_COMMON,true),
                        themedColor,
                        Color.parseColor("#494949")
                }
        );ColorStateList switchList = new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_checked},
                        new int[]{-android.R.attr.state_checked},
                },
                new int[] {
                        themedColor,
                        Color.parseColor("#494949")
                }
        );
        ColorStateList cardList = new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_enabled},
                },
                new int[] {
                        colorMultiply(themedColor,context.getColor(R.color.home_bar_tint),TINT_CARD,false)
                }
        );

        ColorStateList oneColorList = ColorStateList.valueOf(themedColor);
        ColorStateList mixColorList = ColorStateList.valueOf(colorMultiply(themedColor,context.getColor(R.color.nav_bar_selected_tint),TINT_COMMON,true));

        for (ViewGroup parentLayout : parentLayouts){
            for (int count = 0; count < parentLayout.getChildCount(); count++){
                View view = parentLayout.getChildAt(count);
                if(view instanceof Switch){
                    ((Switch) view).setTrackTintList(switchList);
                }
                else if (view instanceof RadioButton){
                    ((RadioButton) view).setButtonTintList(oneColorList);
                }
                else if (view instanceof Button){
                    if (((Button) view).getText().equals(context.getText(R.string.apply)) || ((Button) view).getText().equals(context.getText(android.R.string.ok)) || ((Button) view).getText().equals(context.getText(R.string.dev_btn_open))){
                        ((Button) view).setBackgroundTintList(oneColorList);
                    }else if (((Button) view).getText().equals(context.getText(R.string.cancel))){
                        ((Button) view).setTextColor(themedColor);
                    }
                }
                else if(view instanceof TextView){
                    if (((TextView) view).getCurrentTextColor() == context.getColor(R.color.theme_color_base)){
                        ((TextView) view).setTextColor(themedColor);
                    }
                }
                else if(view instanceof BottomNavigationView){
                    view.setBackgroundColor(colorMultiply(themedColor,context.getColor(R.color.nav_bar_tint),TINT_COMMON,true));
                    ((BottomNavigationView) view).setItemActiveIndicatorColor(mixColorList);

                }
                else if(view.getId() == R.id.characterSearchBar || view.getId() == R.id.lightconeSearchBar || view.getId() == R.id.relicSearchBar){
                    view.setBackgroundTintList(mixColorList);
                }
                else if(view.getId() == R.id.filterMain){
                    view.setBackgroundTintList(ColorStateList.valueOf(colorMultiply(themedColor,context.getColor(R.color.home_bar_tint),TINT_COMMON,false)));
                }
                else if(view instanceof ScaleRatingBar){
                    Drawable rare_star = context.getDrawable(R.drawable.ic_rare_star);
                    rare_star.setTint(themedColor);
                    ((ScaleRatingBar) view).setEmptyDrawable(rare_star);
                    ((ScaleRatingBar) view).setFilledDrawable(rare_star);
                }
                else if(view instanceof CardView){
                    ((CardView) view).setCardElevation((sharedPreferences.getBoolean("isShadowInListItem",true) ? 4*displayMetrics.density : 0));
                    ((CardView) view).setCardBackgroundColor(colorMultiply(themedColor,context.getColor(R.color.home_bar_tint),TINT_COMMON,false));
                }
                else if(view.getId() == R.id.view_top_grad || view.getId() == R.id.view_bottom_grad){
                    view.setBackgroundTintList(ColorStateList.valueOf(colorMultiply(themedColor,context.getColor(R.color.home_bar_tint),TINT_COMMON,false)));
                }
                else if (view.getBackground() != null && view.getBackground().getConstantState() == context.getDrawable(R.drawable.bg_char_info_card).getConstantState()){
                    view.setTranslationZ(sharedPreferences.getBoolean("isShadowInListItem",true) ? 4*displayMetrics.density : 0);
                    view.setBackgroundTintList(cardList);
                }
                else if (view instanceof SeekBar){
                    ((SeekBar) view).setProgressTintList(ColorStateList.valueOf(themedColor));
                    ((SeekBar) view).setProgressTintMode(PorterDuff.Mode.SRC_IN);
                    ((SeekBar) view).setThumbTintList(ColorStateList.valueOf(themedColor));
                }
                else if (view instanceof TabLayout){
                    ((TabLayout) view).setSelectedTabIndicatorColor(themedColor);
                }
                else if (view instanceof ImageView && view.getBackground() != null && view.getBackground().getConstantState() == context.getDrawable(R.drawable.bg_skill_round).getConstantState()){
                    Drawable rare_star = context.getDrawable(R.drawable.bg_skill_round);
                    rare_star.setTint(themedColor);
                    ((ImageView) view).setBackground(rare_star);
                }

                if(view instanceof ViewGroup){
                    themeTint((ViewGroup) view);
                }
            }
        }
    }

    @ColorInt
    public int colorMultiply(@ColorInt int themedColor,@ColorInt int tint, float ratio, boolean isAlpha){
        if (!isAlpha){
            return ColorUtils.compositeColors(ColorUtils.blendARGB(themedColor, tint, ratio),(context.getString(R.string.daynight).equals("DAY") ? Color.WHITE : Color.BLACK));
        }
        return ColorUtils.blendARGB(themedColor, tint, ratio);
    }


}
