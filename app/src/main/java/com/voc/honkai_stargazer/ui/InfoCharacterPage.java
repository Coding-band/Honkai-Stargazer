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
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
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
import com.voc.honkai_stargazer.util.LogExport;
import com.willy.ratingbar.ScaleRatingBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

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

    ArrayList<String> materialList = new ArrayList<>();

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
                /*
                String json_base2 = LoadAssestData(context, "character_data/character_list.json");
                try {
                    JSONArray array = new JSONArray(json_base2);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        String json_base = LoadAssestData(context, "character_data/" + "en" + "/" + object.getString("fileName") + ".json");
                        if (json_base != null) {
                            JSONObject jsonObject = new JSONObject(json_base);
                            help_tool_skill(jsonObject);
                        }
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
                 */
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
                init_eidolon(jsonObject);
                //help_tool_eidolon(jsonObject);
                //help_tool_material(jsonObject);
                //help_tool_skill(jsonObject, hsrItem.getName());

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

    private void init_eidolon(JSONObject jsonObject) throws JSONException {
        JSONArray ranks = jsonObject.getJSONArray("ranks");

        ImageView eidolon_ico1 = info_eidolon.findViewById(R.id.eidolon_ico1);
        ImageView eidolon_ico2 = info_eidolon.findViewById(R.id.eidolon_ico2);
        ImageView eidolon_ico3 = info_eidolon.findViewById(R.id.eidolon_ico3);
        ImageView eidolon_ico4 = info_eidolon.findViewById(R.id.eidolon_ico4);
        ImageView eidolon_ico5 = info_eidolon.findViewById(R.id.eidolon_ico5);
        ImageView eidolon_ico6 = info_eidolon.findViewById(R.id.eidolon_ico6);
        TextView eidolon_name1 = info_eidolon.findViewById(R.id.eidolon_name1);
        TextView eidolon_name2 = info_eidolon.findViewById(R.id.eidolon_name2);
        TextView eidolon_name3 = info_eidolon.findViewById(R.id.eidolon_name3);
        TextView eidolon_name4 = info_eidolon.findViewById(R.id.eidolon_name4);
        TextView eidolon_name5 = info_eidolon.findViewById(R.id.eidolon_name5);
        TextView eidolon_name6 = info_eidolon.findViewById(R.id.eidolon_name6);
        TextView eidolon_desc1 = info_eidolon.findViewById(R.id.eidolon_desc1);
        TextView eidolon_desc2 = info_eidolon.findViewById(R.id.eidolon_desc2);
        TextView eidolon_desc3 = info_eidolon.findViewById(R.id.eidolon_desc3);
        TextView eidolon_desc4 = info_eidolon.findViewById(R.id.eidolon_desc4);
        TextView eidolon_desc5 = info_eidolon.findViewById(R.id.eidolon_desc5);
        TextView eidolon_desc6 = info_eidolon.findViewById(R.id.eidolon_desc6);

        eidolon_ico1.setImageResource(item_rss.getCharByName(hsrItem.getName())[2]);
        eidolon_ico2.setImageResource(item_rss.getCharByName(hsrItem.getName())[3]);
        eidolon_ico3.setImageResource(item_rss.getCharByName(hsrItem.getName())[4]);
        eidolon_ico4.setImageResource(item_rss.getCharByName(hsrItem.getName())[5]);
        eidolon_ico5.setImageResource(item_rss.getCharByName(hsrItem.getName())[6]);
        eidolon_ico6.setImageResource(item_rss.getCharByName(hsrItem.getName())[7]);

        eidolon_name1.setText(ranks.getJSONObject(0).getString("name"));
        eidolon_name2.setText(ranks.getJSONObject(1).getString("name"));
        eidolon_name3.setText(ranks.getJSONObject(2).getString("name"));
        eidolon_name4.setText(ranks.getJSONObject(3).getString("name"));
        eidolon_name5.setText(ranks.getJSONObject(4).getString("name"));
        eidolon_name6.setText(ranks.getJSONObject(5).getString("name"));

        eidolon_desc1.setText(ItemRSS.valuedText(ranks.getJSONObject(0).getString("descHash"), ranks.getJSONObject(0).getJSONArray("params"),context), TextView.BufferType.SPANNABLE);
        eidolon_desc2.setText(ItemRSS.valuedText(ranks.getJSONObject(1).getString("descHash"), ranks.getJSONObject(1).getJSONArray("params"),context), TextView.BufferType.SPANNABLE);
        eidolon_desc3.setText(ItemRSS.valuedText(ranks.getJSONObject(2).getString("descHash"), ranks.getJSONObject(2).getJSONArray("params"),context), TextView.BufferType.SPANNABLE);
        eidolon_desc4.setText(ItemRSS.valuedText(ranks.getJSONObject(3).getString("descHash"), ranks.getJSONObject(3).getJSONArray("params"),context), TextView.BufferType.SPANNABLE);
        eidolon_desc5.setText(ItemRSS.valuedText(ranks.getJSONObject(4).getString("descHash"), ranks.getJSONObject(4).getJSONArray("params"),context), TextView.BufferType.SPANNABLE);
        eidolon_desc6.setText(ItemRSS.valuedText(ranks.getJSONObject(5).getString("descHash"), ranks.getJSONObject(5).getJSONArray("params"),context), TextView.BufferType.SPANNABLE);


    }

    private void help_tool_eidolon(JSONObject jsonObject) throws JSONException {
        String str_final = "";//""-----------"+jsonObject.getString("name")+"-----------"+"\n";
        JSONArray ranks = jsonObject.getJSONArray("ranks");
        for (int x = 0  ;x < ranks.length() ; x++){
            str_final = str_final + "ren https://starrailstation.com/assets/"+ranks.getJSONObject(x).getString("artPath")+".webp \""+jsonObject.getString("name").toLowerCase().replace(" ","_").replace("'","")+"_eidolon"+String.valueOf(x+1)+".webp\""+"\n";
        }

        LogExport.special(str_final, context, LogExport.BETA_TESTING);
    }
    private void help_tool_material(JSONObject jsonObject) throws JSONException {
        String str_final = "";//""-----------"+jsonObject.getString("name")+"-----------"+"\n";
        JSONObject ranks = jsonObject.getJSONObject("itemReferences");
        Iterator<String> iter = ranks.keys();
        for (int x = 0  ;x < ranks.length() ; x++){
            String key = iter.next();
            if (!materialList.contains(ranks.getJSONObject(key).getString("iconPath")+".webp")){
                str_final = str_final + "ren https://starrailstation.com/assets/"+ranks.getJSONObject(key).getString("iconPath")+".webp \"material_"+ranks.getJSONObject(key).getString("name").toLowerCase().replace(" ","_").replace("'","")+".webp\""+"\n";
                materialList.add(ranks.getJSONObject(key).getString("iconPath")+".webp");
            }
        }

        LogExport.special(str_final, context, LogExport.BETA_TESTING);
    }
    private void help_tool_skill(JSONObject jsonObject) throws JSONException {
        String str_final = "";//""-----------"+jsonObject.getString("name")+"-----------"+"\n";
        JSONArray ranks = jsonObject.getJSONArray("skills");
        for (int x = 0  ;x < ranks.length() ; x++){
            if (x != 4){
                str_final = str_final + "ren https://starrailstation.com/assets/"+ranks.getJSONObject(x).getString("iconPath")+".webp \""+jsonObject.getString("name").toLowerCase().replace(" ","_").replace("'","")+"_skill"+String.valueOf(x+1)+".webp\""+"\n";
            }
        }

        LogExport.special(str_final, context, LogExport.BETA_TESTING);
    }

}