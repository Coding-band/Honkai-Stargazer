/*
 * Project Honkai Stargazer (崩壞•星穹觀星者) was
 * Created & Develop by Voc-夜芷冰 , Programmer of Xectorda
 * Copyright © 2023 Xectorda 版權所有
 */

package com.voc.honkai_stargazer.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    int themedColor = Color.parseColor("#009688");
    public ThemeUtil(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences("user_info",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void navigationSetup(Window window){
        window.setNavigationBarColor(colorMultiply(themedColor,context.getColor(R.color.nav_bar_tint),TINT_COMMON));
    }
    public void themeTint(ViewGroup... parentLayouts){
        for (ViewGroup parentLayout : parentLayouts){
            for (int count = 0; count < parentLayout.getChildCount(); count++){
                View view = parentLayout.getChildAt(count);
                if(view instanceof TextView){
                    if (((TextView) view).getCurrentTextColor() == context.getColor(R.color.theme_color_base)){
                        ((TextView) view).setTextColor(themedColor);
                    }

                }else if(view instanceof Switch){
                    ColorStateList myList = new ColorStateList(
                            new int[][]{
                                    new int[]{android.R.attr.state_checked},
                                    new int[]{-android.R.attr.state_checked},
                            },
                            new int[] {
                                    themedColor,
                                    Color.parseColor("#494949")
                            }
                    );
                    ((Switch) view).setTrackTintList(myList);

                }else if(view instanceof BottomNavigationView){
                    ColorStateList myList = new ColorStateList(
                            new int[][]{
                                    new int[]{android.R.attr.state_selected},
                            },
                            new int[] {
                                    colorMultiply(themedColor,context.getColor(R.color.nav_bar_selected_tint),TINT_COMMON)
                            }
                    );
                    view.setBackgroundColor(colorMultiply(themedColor,context.getColor(R.color.nav_bar_tint),TINT_COMMON));
                    ((BottomNavigationView) view).setItemActiveIndicatorColor(myList);

                }else if(view.getId() == R.id.characterSearchBar || view.getId() == R.id.lightconeSearchBar || view.getId() == R.id.relicSearchBar){
                    ColorStateList myList = new ColorStateList(
                            new int[][]{
                                    new int[]{android.R.attr.state_enabled},
                            },
                            new int[] {
                                    colorMultiply(themedColor,context.getColor(R.color.home_bar_tint),TINT_COMMON)
                            }
                    );
                    view.setBackgroundTintList(myList);

                }else if(view.getId() == R.id.filterMain){
                    ColorStateList myList = new ColorStateList(
                            new int[][]{
                                    new int[]{android.R.attr.state_enabled},
                            },
                            new int[] {
                                    ColorUtils.compositeColors(colorMultiply(themedColor,context.getColor(R.color.home_bar_tint),TINT_COMMON), (context.getString(R.string.daynight).equals("DAY") ? Color.WHITE : Color.BLACK))
                            }
                    );
                    view.setBackgroundTintList(myList);

                }else if(view instanceof ScaleRatingBar){
                    Drawable rare_star = context.getDrawable(R.drawable.ic_rare_star);
                    Drawable wrappedDrawable = DrawableCompat.wrap(rare_star);
                    DrawableCompat.setTint(wrappedDrawable, themedColor);
                    ((ScaleRatingBar) view).setEmptyDrawable(wrappedDrawable);
                    ((ScaleRatingBar) view).setFilledDrawable(wrappedDrawable);

                }else if(view instanceof CardView){
                    ((CardView) view).setCardBackgroundColor(colorMultiply(themedColor,context.getColor(R.color.home_bar_tint),TINT_CARD));

                }else if(view.getId() == R.id.view_top_grad || view.getId() == R.id.view_bottom_grad){
                    ColorStateList myList = new ColorStateList(
                            new int[][]{
                                    new int[]{android.R.attr.state_enabled},
                            },
                            new int[] {
                                    colorMultiply(themedColor,context.getColor(R.color.home_bar_tint),TINT_CARD)
                            }
                    );
                    view.setBackgroundTintList(myList);

                }else if (view.getBackground() != null && view.getBackground().getConstantState() == context.getDrawable(R.drawable.bg_char_info_card).getConstantState()){
                    ColorStateList myList = new ColorStateList(
                            new int[][]{
                                    new int[]{android.R.attr.state_enabled},
                            },
                            new int[] {
                                    colorMultiply(themedColor,context.getColor(R.color.home_bar_tint),TINT_CARD)
                            }
                    );
                    view.setBackgroundTintList(myList);
                }else if (view instanceof SeekBar){
                    ((SeekBar) view).setProgressTintList(ColorStateList.valueOf(colorMultiply(themedColor,context.getColor(R.color.home_bar_tint),TINT_COMMON)));
                    ((SeekBar) view).setProgressTintMode(PorterDuff.Mode.MULTIPLY);
                    ((SeekBar) view).setThumbTintList(ColorStateList.valueOf(themedColor));
                }else if (view instanceof TabLayout){
                    ((TabLayout) view).setSelectedTabIndicatorColor(themedColor);
                }else if (view instanceof Button){
                    if (((Button) view).getBackground() != null){
                        if (view.getBackground() instanceof ColorDrawable){
                            if (((ColorDrawable) view.getBackground()).getColor() == context.getColor(R.color.filter_btn_themed)){
                                view.setBackgroundColor(themedColor);
                            }
                        }
                    }
                }else if (view instanceof ImageView && view.getBackground() != null && view.getBackground().getConstantState() == context.getDrawable(R.drawable.bg_skill_round).getConstantState()){
                    Drawable rare_star = context.getDrawable(R.drawable.bg_skill_round);
                    Drawable wrappedDrawable = DrawableCompat.wrap(rare_star);
                    DrawableCompat.setTint(wrappedDrawable, themedColor);
                    ((ImageView) view).setBackground(wrappedDrawable);

                }

                if(view instanceof ViewGroup){
                    themeTint((ViewGroup) view);
                }
            }
        }
    }

    @ColorInt
    public int colorMultiply(@ColorInt int themedColor,@ColorInt int tint, float ratio){
        return ColorUtils.blendARGB(themedColor, tint, ratio);
    }


}
