/*
 * Project Honkai Stargazer (崩壞•星穹觀星者) was
 * Created & Develop by Voc-夜芷冰 , Programmer of Xectorda
 * Copyright © 2023 Xectorda 版權所有
 */

package com.voc.honkai_stargazer.ui;


import static com.voc.honkai_stargazer.util.LoadAssestData.LoadAssestData;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.voc.honkai_stargazer.R;
import com.voc.honkai_stargazer.data.HSRItem;
import com.voc.honkai_stargazer.util.CustomViewPagerAdapter;
import com.voc.honkai_stargazer.util.ItemRSS;
import com.willy.ratingbar.ScaleRatingBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class InfoCharacterPage {
    Context context;
    Activity activity;
    HSRItem hsrItem;

    ViewPager info_vp;
    View info_introducing, info_combat, info_eidolon;
    ArrayList<View> viewArrayList = new ArrayList<>();
    TabLayout info_tablayout;

    public static final int TAB_INTRODUCING = 100;
    public static final int TAB_COMBAT = 101;
    public static final int TAB_EIDOLON = 102;

    ItemRSS item_rss;

    public void setup(Context context, Activity activity, HSRItem hsrItem){
        this.context = context;
        this.activity = activity;
        this.hsrItem = hsrItem;
        item_rss = new ItemRSS();

        final LayoutInflater mInflater = activity.getLayoutInflater().from(context);
        info_introducing = mInflater.inflate(R.layout.fragment_info_character_intro, null,false);
        info_combat = mInflater.inflate(R.layout.fragment_info_character_combat, null,false);
        info_eidolon = mInflater.inflate(R.layout.fragment_info_character_eidolon, null,false);

        viewArrayList = new ArrayList<>();
        viewArrayList.add(info_introducing);
        viewArrayList.add(info_combat);
        viewArrayList.add(info_eidolon);

        final Dialog dialog = new Dialog(context, R.style.PageDialogStyle_P);
        View view = View.inflate(context, R.layout.fragment_info_character_root, null);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);
        //view.setMinimumHeight((int) (ScreenSizeUtils.getInstance(this).getScreenHeight()));
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();

        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.CENTER;
        dialogWindow.setAttributes(lp);
        dialog.show();

        ImageButton info_back = view.findViewById(R.id.info_back);
        info_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        info_vp = view.findViewById(R.id.info_vp);
        info_vp.setAdapter(new CustomViewPagerAdapter(viewArrayList));
        info_tablayout = view.findViewById(R.id.info_tablayout);

        info_tablayout.addTab(info_tablayout.newTab().setId(TAB_INTRODUCING).setText(R.string.character_intro));
        info_tablayout.addTab(info_tablayout.newTab().setId(TAB_COMBAT).setText(R.string.character_combat));
        info_tablayout.addTab(info_tablayout.newTab().setId(TAB_EIDOLON).setText(R.string.character_eidolon));

        info_tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                info_vp.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        info_vp.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(info_tablayout));

        String LANGUAGE = ItemRSS.LANG_EN;
        //Read JSON from Assests
        // Still need to add parallel list
        String json_base = LoadAssestData(context,"character_data/"+LANGUAGE+"/"+hsrItem.getFileName()+".json");
        if (json_base != null){
            try {
                JSONObject jsonObject = new JSONObject(json_base);
                ((TextView) view.findViewById(R.id.info_char_name)).setText(jsonObject.getString("name"));

                init_intro(jsonObject);
                //init_combat(jsonObject);
                //init_eidolon(jsonObject);

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }else{
            Toast.makeText(context, "["+LANGUAGE+"] "+hsrItem.getName()+"'s file not exist", Toast.LENGTH_SHORT).show();
        }

    }
    private void init_intro(JSONObject jsonObject) throws JSONException {
        ImageView intro_ico = info_introducing.findViewById(R.id.intro_ico);
        ImageView intro_path_ico = info_introducing.findViewById(R.id.intro_path_ico);
        ImageView intro_element_ico = info_introducing.findViewById(R.id.intro_element_ico);
        TextView intro_path_tv = info_introducing.findViewById(R.id.intro_path_tv);
        TextView intro_element_tv = info_introducing.findViewById(R.id.intro_element_tv);
        TextView intro_desc = info_introducing.findViewById(R.id.intro_desc);
        ScaleRatingBar intro_rare = info_introducing.findViewById(R.id.intro_rare);

        intro_ico.setImageResource(item_rss.getCharByName(hsrItem.getName())[1]);
        intro_path_ico.setImageResource(item_rss.getIconByPath(hsrItem.getPath()));
        intro_element_ico.setImageResource(item_rss.getIconByElement(hsrItem.getElement()));
        intro_path_tv.setText(item_rss.getNameByPath(hsrItem.getPath()));
        intro_element_tv.setText(item_rss.getNameByElement(hsrItem.getElement()));
        intro_rare.setNumStars(hsrItem.getRare());
        intro_rare.setRating(hsrItem.getRare());
        intro_desc.setText(jsonObject.getString("descHash"));

    }

    private void init_combat() {
    }

    private void init_eidolon() {


    }

}