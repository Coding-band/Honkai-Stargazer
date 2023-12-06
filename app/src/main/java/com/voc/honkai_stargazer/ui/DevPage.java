/*
 * Project Honkai Stargazer (崩壞•星穹觀星者) was
 * Created & Develop by Voc-夜芷冰 , Programmer of Xectorda
 * Copyright © 2023 Xectorda 版權所有
 */

package com.voc.honkai_stargazer.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.play.core.appupdate.testing.FakeAppUpdateManager;
import com.google.android.play.core.install.model.AppUpdateType;
import com.voc.honkai_stargazer.R;
import com.voc.honkai_stargazer.data.HSRItem;
import com.voc.honkai_stargazer.dev.CharAdviceSuggester;
import com.voc.honkai_stargazer.dev.HelpTool;
import com.voc.honkai_stargazer.util.LogExport;
import com.voc.honkai_stargazer.util.UpdateUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringWriter;

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
        charAdviceSuggester = new CharAdviceSuggester();

        //Back
        ImageButton dev_back = findViewById(R.id.dev_back);
        dev_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //init
        Button dev_char_advice_btn = findViewById(R.id.dev_char_advice_btn);
        Button dev_expection_btn = findViewById(R.id.dev_expection_btn);
        Button dev_museum_btn = findViewById(R.id.dev_museum_btn);
        Button dev_scrollview_btn = findViewById(R.id.dev_scrollview_btn);

        Button dev_fake_update_1 = findViewById(R.id.dev_fake_update_1);
        Button dev_fake_update_2 = findViewById(R.id.dev_fake_update_2);

        Button dev_help_tool = findViewById(R.id.dev_help_tool);

        Switch dev_siptik_rotate = findViewById(R.id.dev_siptik_rotate);
        Switch setting_shadow_list_item = findViewById(R.id.setting_shadow_list_item);

        //CAS Function
        dev_char_advice_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                charAdviceSuggester.init(context,activity);
            }
        });

        //EXPECTION
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
                    LogExport.bugLog(TAG, "dev_expection_btn.onClick()", sw.toString(), e.getMessage(), context,LogExport.MODE_SERVER);

                    Toast.makeText(context, "Please check test log available or not", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Rotate - Funny
        dev_siptik_rotate.setChecked(sharedPreferences.getBoolean("isRotateItemIcon", false));
        dev_siptik_rotate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor.putBoolean("isRotateItemIcon", isChecked).apply();
            }
        });

        //Shadow
        setting_shadow_list_item.setChecked(sharedPreferences.getBoolean("isShadowInListItem", true));
        setting_shadow_list_item.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor.putBoolean("isShadowInListItem", isChecked).apply();
            }
        });

        //Museum
        dev_museum_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, MuseumPage.class);
                startActivity(intent);
            }
        });

        //ScrollView
        dev_scrollview_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ScrollingActivity.class);
                startActivity(intent);
            }
        });

        //FakeAppUpdate
        dev_fake_update_1.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {fake_update_test(AppUpdateType.FLEXIBLE);}});
        dev_fake_update_2.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {fake_update_test(AppUpdateType.IMMEDIATE);}});

        //HelpTool
        dev_help_tool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new HelpTool().trigger_help_tool(context);
                HelpTool helpTool = new HelpTool();
                try {
                    //helpTool.help_tool_export_relic_pc_run(context);
                    //helpTool.help_tool_export_locale_advice(context);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                Toast.makeText(context, "Trigged HelpTool.trigger_help_tool", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void fake_update_test(int level) {
        UpdateUtil updateUtil = new UpdateUtil();
        updateUtil.init(context,activity);

        FakeAppUpdateManager fakeAppUpdateManager = new FakeAppUpdateManager(context);
        fakeAppUpdateManager.setUpdateAvailable(33333);
        fakeAppUpdateManager.setUpdatePriority(4);

        System.out.println("isImmediateFlowVisible ? "+fakeAppUpdateManager.isImmediateFlowVisible());

        fakeAppUpdateManager.userAcceptsUpdate();
        System.out.println("userAcceptsUpdate");

        fakeAppUpdateManager.downloadStarts();
        System.out.println("downloadStarts");

        fakeAppUpdateManager.downloadCompletes();
        System.out.println("downloadCompletes");

        System.out.println("isInstallSplashScreenVisible ? "+fakeAppUpdateManager.isInstallSplashScreenVisible());



    }

    public void casAddItem(HSRItem hsrItem, String TYPE){
        charAdviceSuggester.portAddItem(hsrItem,TYPE);
    }
}