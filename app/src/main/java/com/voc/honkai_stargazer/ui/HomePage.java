/*
 * Project Honkai Stargazer (崩壞•星穹觀星者) was
 * Created & Develop by Voc-夜芷冰 , Programmer of Xectorda
 * Copyright © 2023 Xectorda 版權所有
 */

package com.voc.honkai_stargazer.ui;

import static com.voc.honkai_stargazer.util.LoadAssestData.LoadAssestData;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.voc.honkai_stargazer.R;
import com.voc.honkai_stargazer.data.HSRItem;
import com.voc.honkai_stargazer.data.HSRItemAdapter;
import com.voc.honkai_stargazer.util.CustomViewPager;
import com.voc.honkai_stargazer.util.CustomViewPagerAdapter;
import com.voc.honkai_stargazer.util.ItemRSS;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomePage extends AppCompatActivity {

    CustomViewPager viewPager;
    private ArrayList<View> viewPager_List;
    View home_characters, home_lightcones, home_relics, home_settings;
    BottomNavigationView home_nav;

    RecyclerView charactersListView ;
    RecyclerView lightconesListView ;
    RecyclerView relicsListView ;

    ArrayList<HSRItem> charactersList = new ArrayList<>();
    ArrayList<HSRItem> lightconesList = new ArrayList<>();
    ArrayList<HSRItem> relicsList = new ArrayList<>();

    HSRItemAdapter charactersAdapter ;
    HSRItemAdapter lightconesAdapter ;
    HSRItemAdapter relicsAdapter ;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    Context context;
    Activity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        context = this;
        activity = this;
        sharedPreferences = context.getSharedPreferences("user_info",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        root_init();
        character_init();
        lightcone_init();
        relic_init();
    }

    public void root_init(){
        final LayoutInflater mInflater = getLayoutInflater().from(this);
        home_characters = mInflater.inflate(R.layout.fragment_home_characters, null,false);
        home_lightcones = mInflater.inflate(R.layout.fragment_home_lightcones, null,false);
        home_relics = mInflater.inflate(R.layout.fragment_home_relics, null,false);
        home_settings = mInflater.inflate(R.layout.fragment_home_settings, null,false);

        viewPager_List = new ArrayList<View>();
        viewPager_List.add(home_characters);
        viewPager_List.add(home_lightcones);
        viewPager_List.add(home_relics);
        viewPager_List.add(home_settings);

        viewPager = findViewById(R.id.home_vp);
        viewPager.setAdapter(new CustomViewPagerAdapter(viewPager_List));

        home_nav = findViewById(R.id.home_nav);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int[] posList = new int[]{R.id.menu_characters, R.id.menu_lightcones, R.id.menu_relics, R.id.menu_settings};
                home_nav.setSelectedItemId(posList[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        home_nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_characters:{
                        viewPager.setCurrentItem(0);
                        return true;
                    }
                    case R.id.menu_lightcones: {
                        viewPager.setCurrentItem(1);
                        return true;
                    }
                    case R.id.menu_relics:{
                        viewPager.setCurrentItem(2);
                        return true;
                    }
                    case R.id.menu_settings:{
                        viewPager.setCurrentItem(3);
                        return true;
                    }
                }
                return false;
            }
        });
    }

    public void character_init(){
        charactersListView = home_characters.findViewById(R.id.charactersListView);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 1);

        charactersAdapter = new HSRItemAdapter(context,activity,sharedPreferences, ItemRSS.TYPE_CHARACTER);
        charactersListView.setLayoutManager(mLayoutManager);
        charactersListView.setAdapter(charactersAdapter);
        charactersListView.removeAllViewsInLayout();
        char_list_reload();
    }

    public void lightcone_init(){
        lightconesListView = home_lightcones.findViewById(R.id.lightconesListView);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 1);

        lightconesAdapter = new HSRItemAdapter(context,activity,sharedPreferences, ItemRSS.TYPE_LIGHTCONE);
        lightconesListView.setLayoutManager(mLayoutManager);
        lightconesListView.setAdapter(lightconesAdapter);
        lightconesListView.removeAllViewsInLayout();
        lightcone_list_reload();
    }
    public void relic_init(){
        relicsListView = home_relics.findViewById(R.id.relicsListView);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 1);

        relicsAdapter = new HSRItemAdapter(context,activity,sharedPreferences, ItemRSS.TYPE_RELIC);
        relicsListView.setLayoutManager(mLayoutManager);
        relicsListView.setAdapter(relicsAdapter);
        relicsListView.removeAllViewsInLayout();
        relic_list_reload();
    }

    private void char_list_reload() {
        String name ,element,path,sex,status,fileName;
        int rare;
        //charactersList.clear();

        String json_base = LoadAssestData(context,"character_data/character_list.json");;
        //Get data from JSON
        try {
            JSONArray array = new JSONArray(json_base);
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                name = object.getString("name");
                element = object.getString("element");
                path = object.getString("path");
                sex = object.getString("sex");
                rare = object.getInt("rare");
                status = object.getString("status");
                fileName = object.getString("fileName");

                HSRItem hsrItem = new HSRItem();
                hsrItem.setName(name);
                hsrItem.setElement(element);
                hsrItem.setPath(path);
                hsrItem.setName(name);
                hsrItem.setRare(rare);
                hsrItem.setStatus(status);
                hsrItem.setFileName(fileName);

                charactersList.add(hsrItem);
            }
            charactersAdapter.filterList(charactersList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void lightcone_list_reload() {
        String name ,path,status,fileName;
        int rare;
        //charactersList.clear();

        String json_base = LoadAssestData(context,"lightcone_data/lightcone_list.json");;
        //Get data from JSON
        try {
            JSONArray array = new JSONArray(json_base);
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                name = object.getString("name");
                path = object.getString("path");
                rare = object.getInt("rare");
                status = object.getString("status");
                fileName = object.getString("fileName");

                HSRItem hsrItem = new HSRItem();
                hsrItem.setName(name);
                hsrItem.setPath(path);
                hsrItem.setRare(rare);
                hsrItem.setStatus(status);
                hsrItem.setFileName(fileName);

                lightconesList.add(hsrItem);
            }
            lightconesAdapter.filterList(lightconesList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void relic_list_reload() {
        String name ,type,status,fileName;
        int rare;
        //charactersList.clear();

        String json_base = LoadAssestData(context,"relic_data/relic_list.json");;
        //Get data from JSON
        try {
            JSONArray array = new JSONArray(json_base);
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                name = object.getString("name");
                type = object.getString("type");
                status = object.getString("status");
                fileName = object.getString("fileName");

                HSRItem hsrItem = new HSRItem();
                hsrItem.setName(name);
                hsrItem.setType(type);
                hsrItem.setStatus(status);
                hsrItem.setFileName(fileName);

                relicsList.add(hsrItem);
            }
            relicsAdapter.filterList(relicsList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}