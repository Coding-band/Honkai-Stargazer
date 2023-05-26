/*
 * Project Honkai Stargazer (崩壞•星穹觀星者) was
 * Created & Develop by Voc-夜芷冰 , Programmer of Xectorda
 * Copyright © 2023 Xectorda 版權所有
 */

package com.voc.honkai_stargazer.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import com.voc.honkai_stargazer.R;
import com.voc.honkai_stargazer.data.HSRItem;
import com.voc.honkai_stargazer.dev.CharAdviceSuggester;
import com.voc.honkai_stargazer.util.LogExport;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

public class DevPage extends AppCompatActivity {

    public static final int TRIG_TOUCH = 5;

    Context context;
    Activity activity;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    CharAdviceSuggester charAdviceSuggester;

    public static final String TAG = "DevPage";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dev_page);

        context = this;
        activity = this;
        sharedPreferences = context.getSharedPreferences("user_info",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        ImageButton dev_back = findViewById(R.id.dev_back);
        dev_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        charAdviceSuggester = new CharAdviceSuggester();
        //themeChanger();

        Button dev_char_advice_btn = findViewById(R.id.dev_char_advice_btn);
        Button dev_expection_btn = findViewById(R.id.dev_expection_btn);
        dev_char_advice_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                charAdviceSuggester.init(context,activity);
            }
        });
        dev_expection_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    JSONObject jsonObject = new JSONObject("{\"sb\":\"AAA\"}");
                    String str = jsonObject.getString("FAILED");
                } catch (JSONException e) {
                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    e.printStackTrace(pw);
                    LogExport.bugLog(TAG, "dev_expection_btn.onClick()", sw.toString(), context);
                }
            }
        });
    }
    private void themeChanger() {

    }

    public void casAddItem(HSRItem hsrItem, String TYPE){
        charAdviceSuggester.portAddItem(hsrItem,TYPE);
    }
}