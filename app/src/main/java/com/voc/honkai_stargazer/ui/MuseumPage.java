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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.voc.honkai_stargazer.R;
import com.voc.honkai_stargazer.museum.MuseumLib;

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

    //HEAD
    TextView museum_coin_tv, museum_exp_tv, museum_lvl;
    ImageButton museum_exit;
    ProgressBar museum_exp_pb;
    LinearLayout museum_area_ll;

    long tmpMaxEXP = MuseumLib.expMaxList[0];

    Context context;
    Activity activity;
    DisplayMetrics displayMetrics = new DisplayMetrics();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    MuseumLib museumLib;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_museum_page);
        context = this;
        activity = this;
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        sharedPreferences = context.getSharedPreferences("game_museum",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        museumLib = new MuseumLib();

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
            areaTimeStatus.add(x,65);
            areaValueStatus.add(x,65);
            areaPersonStatus.add(x,65);
            areaLevelStatus.add(x,1);
        }

        //UI init
        museum_coin_tv = findViewById(R.id.museum_coin_tv);
        museum_exp_tv = findViewById(R.id.museum_exp_tv);
        museum_exp_pb = findViewById(R.id.museum_exp_pb);
        museum_lvl = findViewById(R.id.museum_lvl);
        museum_exit = findViewById(R.id.museum_exit);
        museum_area_ll = findViewById(R.id.museum_area_ll);

        refreshUI();
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
    public void refreshUI(){
        museum_coin_tv.setText(String.valueOf(coin));
        museum_exp_tv.setText(String.valueOf(currEXP) + "/" + String.valueOf(tmpMaxEXP));
        museum_lvl.setText(String.valueOf(currLvl));
        museum_exp_pb.setMax((int) tmpMaxEXP);
        museum_exp_pb.setProgress((int) currEXP);
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
                area_rank.setText(museumLib.getRankingFromProgress(areaLevelStatus.get(x), areaTimeStatus.get(x), areaValueStatus.get(x), areaPersonStatus.get(x)));
            }

            museum_area_ll.addView(itemView);
        }
    }

    private void saveData(){
        //Save

    }
}