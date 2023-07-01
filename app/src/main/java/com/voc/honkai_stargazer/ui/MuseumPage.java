/*
 * Project Honkai Stargazer (崩壞•星穹觀星者) was
 * Created & Develop by Voc-夜芷冰 , Programmer of Xectorda
 * Copyright © 2023 Xectorda 版權所有
 */

package com.voc.honkai_stargazer.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.voc.honkai_stargazer.R;
import com.voc.honkai_stargazer.museum.MuseumLib;
import com.voc.honkai_stargazer.util.ItemRSS;

import java.util.ArrayList;

public class MuseumPage extends AppCompatActivity {

    public static final int areaCount = 4;

    public long coin = 0;
    public long currEXP = 0;
    public int currLvl = 1;

    public ArrayList<Boolean> areaUnlock = new ArrayList<>();
    public ArrayList<Integer> areaTimeStatus = new ArrayList<>();
    public ArrayList<Integer> areaValueStatus = new ArrayList<>();
    public ArrayList<Integer> areaPersonStatus = new ArrayList<>();
    public ArrayList<Integer> areaLevelStatus = new ArrayList<>();
    public ArrayList<ArrayList<String>> areaAssistant = new ArrayList<ArrayList<String>>();

    //HEAD
    TextView museum_coin_tv, museum_exp_tv, museum_lvl , museum_target_tv, museum_predict_exp_tv;
    TextView preview_expect_exp_tv, preview_area_lvl, preview_upgrade , preview_time_tv, preview_value_tv, preview_person_tv, preview_area_rank;
    ImageButton museum_exit;
    ProgressBar museum_exp_pb, preview_time_pb, preview_value_pb, preview_person_pb;
    ImageView preview_assistant_1,preview_assistant_2,preview_assistant_3,preview_assistant_4;
    LinearLayout museum_area_ll;

    long tmpMaxEXP = MuseumLib.expMaxList[0];

    Context context;
    Activity activity;
    DisplayMetrics displayMetrics = new DisplayMetrics();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    MuseumLib museumLib;
    ItemRSS item_rss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_museum_page);
        context = this;
        activity = this;
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        sharedPreferences = context.getSharedPreferences("game_museum",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        museumLib = new MuseumLib();
        item_rss = new ItemRSS();

        env_init();
    }

    private void env_init() {
        //Variable init
        coin = 0;
        currEXP = 0;
        currLvl = 1;

        for (int x = 0 ; x < areaCount  ;x++){
            if (x == 0){
                areaUnlock.add(x,true);
            }else {
                areaUnlock.add(x, false);
            }
            areaTimeStatus.add(x,MuseumLib.itemValueTargetInit);
            areaValueStatus.add(x,MuseumLib.itemValueTargetInit);
            areaPersonStatus.add(x,MuseumLib.itemValueTargetInit);
            areaLevelStatus.add(x,1);

            ArrayList<String> tmpAssistant = new ArrayList<>();
            tmpAssistant.add("Silver Wolf");
            tmpAssistant.add("Bronya");
            tmpAssistant.add("Clara");
            areaAssistant.add(tmpAssistant);
        }

        //UI init
        museum_coin_tv = findViewById(R.id.museum_coin_tv);
        museum_exp_tv = findViewById(R.id.museum_exp_tv);
        museum_exp_pb = findViewById(R.id.museum_exp_pb);
        museum_lvl = findViewById(R.id.museum_lvl);
        museum_exit = findViewById(R.id.museum_exit);
        museum_area_ll = findViewById(R.id.museum_area_ll);
        museum_target_tv = findViewById(R.id.museum_target_tv);
        museum_predict_exp_tv = findViewById(R.id.museum_predict_exp_tv);

        preview_area_lvl = findViewById(R.id.preview_area_lvl);
        preview_upgrade = findViewById(R.id.preview_upgrade);
        preview_expect_exp_tv = findViewById(R.id.preview_expect_exp_tv);
        preview_time_tv = findViewById(R.id.preview_time_tv);
        preview_value_tv = findViewById(R.id.preview_value_tv);
        preview_person_tv = findViewById(R.id.preview_person_tv);
        preview_area_rank = findViewById(R.id.preview_area_rank);
        preview_time_pb = findViewById(R.id.preview_time_pb);
        preview_value_pb = findViewById(R.id.preview_value_pb);
        preview_person_pb = findViewById(R.id.preview_person_pb);
        preview_assistant_1 = findViewById(R.id.preview_assistant_1);
        preview_assistant_2 = findViewById(R.id.preview_assistant_2);
        preview_assistant_3 = findViewById(R.id.preview_assistant_3);
        preview_assistant_4 = findViewById(R.id.preview_assistant_4);

        refreshMainUI();
        museum_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
                finish();
            }
        });
    }

    /**
     * ONLY FOR BEGIN NEW DAY
     */
    public void refreshMainUI(){
        //Header
        museum_coin_tv.setText(String.valueOf(coin));
        museum_target_tv.setText("TARGET");
        museum_exp_tv.setText(String.valueOf(currEXP) + "/" + String.valueOf(tmpMaxEXP));
        museum_lvl.setText(String.valueOf(currLvl));
        museum_exp_pb.setMax((int) tmpMaxEXP);
        museum_exp_pb.setProgress((int) currEXP);

        //Right
        preview_area_rank.setText(museumLib.getRankingFromProgress(0, areaLevelStatus.get(0), areaTimeStatus.get(0), areaValueStatus.get(0), areaPersonStatus.get(0)));
        preview_area_lvl.setText(String.valueOf(areaLevelStatus.get(0)));
        preview_expect_exp_tv.setText(String.valueOf(museumLib.getEXPFromProgress(0, areaLevelStatus.get(0), areaTimeStatus.get(0), areaValueStatus.get(0), areaPersonStatus.get(0))));
        preview_time_pb.setProgress(areaTimeStatus.get(0));
        preview_value_pb.setProgress(areaValueStatus.get(0));
        preview_person_pb.setProgress(areaPersonStatus.get(0));
        preview_time_pb.setSecondaryProgress((int) museumLib.getCurrLvlItemMax(0, areaLevelStatus.get(0), areaTimeStatus.get(0)));
        preview_value_pb.setSecondaryProgress((int) museumLib.getCurrLvlItemMax(0, areaLevelStatus.get(0), areaValueStatus.get(0)));
        preview_person_pb.setSecondaryProgress((int) museumLib.getCurrLvlItemMax(0, areaLevelStatus.get(0), areaPersonStatus.get(0)));
        preview_time_tv.setText(String.valueOf(areaTimeStatus.get(0)) + "/" + String.valueOf((int) museumLib.getCurrLvlItemMax(0, areaLevelStatus.get(0), areaTimeStatus.get(0))));
        preview_value_tv.setText(String.valueOf(areaValueStatus.get(0)) + "/" + String.valueOf((int) museumLib.getCurrLvlItemMax(0, areaLevelStatus.get(0), areaValueStatus.get(0))));
        preview_person_tv.setText(String.valueOf(areaPersonStatus.get(0)) + "/" + String.valueOf((int) museumLib.getCurrLvlItemMax(0, areaLevelStatus.get(0), areaPersonStatus.get(0))));

        Picasso.get().load(item_rss.getCharByName(areaAssistant.get(0).get(0))[0]).into(preview_assistant_1);
        Picasso.get().load(item_rss.getCharByName(areaAssistant.get(0).get(1))[0]).into(preview_assistant_2);
        Picasso.get().load(item_rss.getCharByName(areaAssistant.get(0).get(2))[0]).into(preview_assistant_3);

        //Left - Event & Area
        for (int x = 0 ; x < areaCount ; x++){
            View itemView = View.inflate(context, R.layout.item_museum_area_choose,null);
            TextView area_id = itemView.findViewById(R.id.area_id);
            TextView area_name = itemView.findViewById(R.id.area_name);
            TextView area_rank = itemView.findViewById(R.id.area_rank);
            ImageView area_lock = itemView.findViewById(R.id.area_lock);

            area_id.setText((x+1 < 10 ? "0" : "")+String.valueOf(x+1));
            area_name.setText(MuseumLib.areaName[x]);
            if (areaUnlock.get(x) == false){
                area_lock.setVisibility(View.VISIBLE);
                area_rank.setVisibility(View.GONE);
            }else{
                area_lock.setVisibility(View.GONE);
                area_rank.setVisibility(View.VISIBLE);
                area_rank.setText(museumLib.getRankingFromProgress(x, areaLevelStatus.get(x), areaTimeStatus.get(x), areaValueStatus.get(x), areaPersonStatus.get(x)));
            }

            // Left -> Right
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            museum_area_ll.addView(itemView);
        }

    }

    private void saveData(){
        //Save

    }
}