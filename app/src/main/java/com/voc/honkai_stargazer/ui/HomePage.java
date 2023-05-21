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
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.voc.honkai_stargazer.R;
import com.voc.honkai_stargazer.data.FilterPreference;
import com.voc.honkai_stargazer.data.HSRItem;
import com.voc.honkai_stargazer.data.HSRItemAdapter;
import com.voc.honkai_stargazer.util.CustomViewPager;
import com.voc.honkai_stargazer.util.CustomViewPagerAdapter;
import com.voc.honkai_stargazer.util.ItemRSS;
import com.voc.honkai_stargazer.util.LangUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

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

    //Character, Lightcone, Relic
    FilterPreference[] filterPreferences = new FilterPreference[]{new FilterPreference(),new FilterPreference(),new FilterPreference()};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        context = this;
        activity = this;
        sharedPreferences = context.getSharedPreferences("user_info",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        ItemRSS.initLang(context);

        root_init();
        character_init();
        lightcone_init();
        relic_init();
        setting_init();

    }

    public void setting_init() {

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
        int grid = 1;
        switch (sharedPreferences.getString("grid_"+ItemRSS.TYPE_CHARACTER,HSRItemAdapter.DEFAULT)){
            default:
            case HSRItemAdapter.ONE_IN_ROW: grid = 1; break;
            case HSRItemAdapter.THREE_IN_ROW: grid = 3; break;
        }
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context,grid );

        charactersAdapter = new HSRItemAdapter(context,activity,sharedPreferences, ItemRSS.TYPE_CHARACTER);
        charactersListView.setLayoutManager(mLayoutManager);
        charactersListView.setAdapter(charactersAdapter);
        charactersListView.removeAllViewsInLayout();
        char_list_reload();

        ImageButton characterFilter = home_characters.findViewById(R.id.characterFilter);
        EditText characterSearchEt = home_characters.findViewById(R.id.characterSearchEt);
        characterFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterHandler(ItemRSS.TYPE_CHARACTER);
            }
        });

        ImageButton characterLayout = home_characters.findViewById(R.id.characterLayout);
        characterLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeListGrid(characterLayout,ItemRSS.TYPE_CHARACTER, charactersListView, charactersAdapter,charactersAdapter.getFilterList());
            }
        });
        characterSearchEt.addTextChangedListener(searchBarHandler(ItemRSS.TYPE_CHARACTER, characterSearchEt));
    }

    public void lightcone_init(){
        lightconesListView = home_lightcones.findViewById(R.id.lightconesListView);
        int grid = 1;
        switch (sharedPreferences.getString("grid_"+ItemRSS.TYPE_LIGHTCONE,HSRItemAdapter.DEFAULT)){
            default:
            case HSRItemAdapter.ONE_IN_ROW: grid = 1; break;
            case HSRItemAdapter.THREE_IN_ROW: grid = 3; break;
        }
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context,grid );

        lightconesAdapter = new HSRItemAdapter(context,activity,sharedPreferences, ItemRSS.TYPE_LIGHTCONE);
        lightconesListView.setLayoutManager(mLayoutManager);
        lightconesListView.setAdapter(lightconesAdapter);
        lightconesListView.removeAllViewsInLayout();

        lightcone_list_reload();

        ImageButton lightconeFilter = home_lightcones.findViewById(R.id.lightconeFilter);
        EditText lightconeSearchEt = home_lightcones.findViewById(R.id.lightconeSearchEt);
        lightconeFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterHandler(ItemRSS.TYPE_LIGHTCONE);
            }
        });
        lightconeSearchEt.addTextChangedListener(searchBarHandler(ItemRSS.TYPE_LIGHTCONE, lightconeSearchEt));

        ImageButton lightconeLayout = home_lightcones.findViewById(R.id.lightconeLayout);
        lightconeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeListGrid(lightconeLayout,ItemRSS.TYPE_LIGHTCONE, lightconesListView,lightconesAdapter,lightconesAdapter.getFilterList());
            }
        });
    }
    public void relic_init(){
        relicsListView = home_relics.findViewById(R.id.relicsListView);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 1);

        relicsAdapter = new HSRItemAdapter(context,activity,sharedPreferences, ItemRSS.TYPE_RELIC);
        relicsListView.setLayoutManager(mLayoutManager);
        relicsListView.setAdapter(relicsAdapter);
        relicsListView.removeAllViewsInLayout();
        relic_list_reload();

        ImageButton relicFilter = home_relics.findViewById(R.id.relicFilter);
        EditText relicSearchEt = home_relics.findViewById(R.id.relicSearchEt);
        relicFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterHandler(ItemRSS.TYPE_RELIC);
            }
        });
        relicSearchEt.addTextChangedListener(searchBarHandler(ItemRSS.TYPE_RELIC, relicSearchEt));
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

    private TextWatcher searchBarHandler(String TYPE, EditText editText){
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                HSRItemAdapter adapter = charactersAdapter;
                ArrayList<HSRItem> list = charactersList;
                ItemRSS item_rss = new ItemRSS();

                switch (TYPE){
                    case ItemRSS.TYPE_CHARACTER:{
                        adapter = charactersAdapter;
                        list = charactersList;
                        break;
                    }
                    case ItemRSS.TYPE_LIGHTCONE:{
                        adapter = lightconesAdapter;
                        list = lightconesList;
                        break;
                    }
                    case ItemRSS.TYPE_RELIC:{
                        adapter = relicsAdapter;
                        list = relicsList;
                        break;
                    }
                }

                if (editText.getText() != null){
                    String request = editText.getText().toString();
                    if (!request.equals("")){
                        ArrayList<HSRItem> filteredList = new ArrayList<>();
                        int x = 0;
                        for (HSRItem item : list) {
                            String str = request.toLowerCase();
                            if (item_rss.getLocalNameByName(item.getName(),context).contains(str)||item_rss.getLocalNameByName(item.getName(),context).toLowerCase().contains(str)||item.getName().toLowerCase().contains(str)){ // EN -> ZH
                                filteredList.add(item);
                            }
                            x = x +1;
                        }
                        adapter.filterList(filteredList);
                    }else{
                        adapter.filterList(list);
                    }
                }else{
                    adapter.filterList(list);
                }
            }
        };
    }

    private void filterHandler(String TYPE){
        final Dialog dialog = new Dialog(context, R.style.FilterDialogStyle_F);
        View view = View.inflate(context, R.layout.fragment_home_filter, null);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();

        final FilterPreference[] filterPreference = {new FilterPreference()};

        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;
        dialogWindow.setAttributes(lp);

        //Init UI
        LinearLayout filter_element_ll = view.findViewById(R.id.filter_element_ll);
        ImageView filter_physical = view.findViewById(R.id.filter_physical);
        ImageView filter_fire = view.findViewById(R.id.filter_fire);
        ImageView filter_ice = view.findViewById(R.id.filter_ice);
        ImageView filter_lightning = view.findViewById(R.id.filter_lightning);
        ImageView filter_wind = view.findViewById(R.id.filter_wind);
        ImageView filter_quantum = view.findViewById(R.id.filter_quantum);
        ImageView filter_imaginary = view.findViewById(R.id.filter_imaginary);

        LinearLayout filter_path_ll = view.findViewById(R.id.filter_path_ll);
        ImageView filter_abundance = view.findViewById(R.id.filter_abundance);
        ImageView filter_destruction = view.findViewById(R.id.filter_destruction);
        ImageView filter_hunt = view.findViewById(R.id.filter_hunt);
        ImageView filter_harmony = view.findViewById(R.id.filter_harmony);
        ImageView filter_erudition = view.findViewById(R.id.filter_erudition);
        ImageView filter_nihility = view.findViewById(R.id.filter_nihility);
        ImageView filter_preservation = view.findViewById(R.id.filter_preservation);

        LinearLayout filter_rarity_ll = view.findViewById(R.id.filter_rarity_ll);
        ToggleButton filter_rare_1 = view.findViewById(R.id.filter_rare_1);
        ToggleButton filter_rare_2 = view.findViewById(R.id.filter_rare_2);
        ToggleButton filter_rare_3 = view.findViewById(R.id.filter_rare_3);
        ToggleButton filter_rare_4 = view.findViewById(R.id.filter_rare_4);
        ToggleButton filter_rare_5 = view.findViewById(R.id.filter_rare_5);

        LinearLayout filter_status_ll = view.findViewById(R.id.filter_status_ll);
        ToggleButton filter_release = view.findViewById(R.id.filter_release);
        ToggleButton filter_soon = view.findViewById(R.id.filter_soon);
        ToggleButton filter_beta = view.findViewById(R.id.filter_beta);

        Button filter_cancel = view.findViewById(R.id.filter_cancel);
        Button filter_apply = view.findViewById(R.id.filter_apply);

        switch (TYPE){
            case ItemRSS.TYPE_CHARACTER: {filter_rare_1.setVisibility(View.GONE); filter_rare_2.setVisibility(View.GONE); filter_rare_3.setVisibility(View.GONE); filterPreference[0] = filterPreferences[0]; break;}
            case ItemRSS.TYPE_LIGHTCONE: {filter_rare_1.setVisibility(View.GONE); filter_rare_2.setVisibility(View.GONE); filter_element_ll.setVisibility(View.GONE);  filterPreference[0] = filterPreferences[1]; break;}
            case ItemRSS.TYPE_RELIC: {filter_element_ll.setVisibility(View.GONE); filter_path_ll.setVisibility(View.GONE);  filterPreference[0] = filterPreferences[2]; break;}
        }
        filter_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (TYPE){
                    case ItemRSS.TYPE_CHARACTER: {filterPreference[0].setType(ItemRSS.TYPE_CHARACTER); filterPreferences[0] = filterPreference[0]; charactersAdapter.filterRequestList(charactersList,filterPreference[0]); break;}
                    case ItemRSS.TYPE_LIGHTCONE: {filterPreference[0].setType(ItemRSS.TYPE_LIGHTCONE);filterPreferences[1] = filterPreference[0]; lightconesAdapter.filterRequestList(lightconesList,filterPreference[0]);break;}
                    case ItemRSS.TYPE_RELIC: {filterPreference[0].setType(ItemRSS.TYPE_RELIC);filterPreferences[2] = filterPreference[0]; relicsAdapter.filterRequestList(relicsList,filterPreference[0]);break;}
                }
                dialog.dismiss();
            }
        });

        filter_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        if(!filterPreference[0].isFire()){filter_fire.setAlpha(0.4f);}else{filter_fire.setAlpha(1.0f);}
        if(!filterPreference[0].isIce()){filter_ice.setAlpha(0.4f);}else{filter_ice.setAlpha(1.0f);}
        if(!filterPreference[0].isImaginary()){filter_imaginary.setAlpha(0.4f);}else{filter_imaginary.setAlpha(1.0f);}
        if(!filterPreference[0].isLightning()){filter_lightning.setAlpha(0.4f);}else{filter_lightning.setAlpha(1.0f);}
        if(!filterPreference[0].isPhysical()){filter_physical.setAlpha(0.4f);}else{filter_physical.setAlpha(1.0f);}
        if(!filterPreference[0].isQuantum()){filter_quantum.setAlpha(0.4f);}else{filter_quantum.setAlpha(1.0f);}
        if(!filterPreference[0].isWind()){filter_wind.setAlpha(0.4f);}else{filter_wind.setAlpha(1.0f);}

        if(!filterPreference[0].isAbundance()){filter_abundance.setAlpha(0.4f);}else{filter_abundance.setAlpha(1.0f);}
        if(!filterPreference[0].isDestruction()){filter_destruction.setAlpha(0.4f);}else{filter_destruction.setAlpha(1.0f);}
        if(!filterPreference[0].isErudition()){filter_erudition.setAlpha(0.4f);}else{filter_erudition.setAlpha(1.0f);}
        if(!filterPreference[0].isHarmony()){filter_harmony.setAlpha(0.4f);}else{filter_harmony.setAlpha(1.0f);}
        if(!filterPreference[0].isHunt()){filter_hunt.setAlpha(0.4f);}else{filter_hunt.setAlpha(1.0f);}
        if(!filterPreference[0].isNihility()){filter_nihility.setAlpha(0.4f);}else{filter_nihility.setAlpha(1.0f);}
        if(!filterPreference[0].isPreservation()){filter_preservation.setAlpha(0.4f);}else{filter_preservation.setAlpha(1.0f);}


        if(!filterPreference[0].isRare1()){filter_rare_1.setChecked(false);}else{filter_rare_1.setChecked(true);}
        if(!filterPreference[0].isRare2()){filter_rare_2.setChecked(false);}else{filter_rare_2.setChecked(true);}
        if(!filterPreference[0].isRare3()){filter_rare_3.setChecked(false);}else{filter_rare_3.setChecked(true);}
        if(!filterPreference[0].isRare4()){filter_rare_4.setChecked(false);}else{filter_rare_4.setChecked(true);}
        if(!filterPreference[0].isRare5()){filter_rare_5.setChecked(false);}else{filter_rare_5.setChecked(true);}

        if(!filterPreference[0].isRelease()){filter_release.setChecked(false);}else{filter_release.setChecked(true);}
        if(!filterPreference[0].isBeta()){filter_beta.setChecked(false);}else{filter_beta.setChecked(true);}
        if(!filterPreference[0].isSoon()){filter_soon.setChecked(false);}else{filter_soon.setChecked(true);}


        filter_fire.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {filterPreference[0].setFire(!filterPreference[0].isFire());if(!filterPreference[0].isFire()){filter_fire.setAlpha(0.4f);}else{filter_fire.setAlpha(1.0f);}}});
        filter_ice.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {filterPreference[0].setIce(!filterPreference[0].isIce());if(!filterPreference[0].isIce()){filter_ice.setAlpha(0.4f);}else{filter_ice.setAlpha(1.0f);}}});
        filter_imaginary.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {filterPreference[0].setImaginary(!filterPreference[0].isImaginary());if(!filterPreference[0].isImaginary()){filter_imaginary.setAlpha(0.4f);}else{filter_imaginary.setAlpha(1.0f);}}});
        filter_lightning.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {filterPreference[0].setLightning(!filterPreference[0].isLightning());if(!filterPreference[0].isLightning()){filter_lightning.setAlpha(0.4f);}else{filter_lightning.setAlpha(1.0f);}}});
        filter_physical.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {filterPreference[0].setPhysical(!filterPreference[0].isPhysical());if(!filterPreference[0].isPhysical()){filter_physical.setAlpha(0.4f);}else{filter_physical.setAlpha(1.0f);}}});
        filter_quantum.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {filterPreference[0].setQuantum(!filterPreference[0].isQuantum());if(!filterPreference[0].isQuantum()){filter_quantum.setAlpha(0.4f);}else{filter_quantum.setAlpha(1.0f);}}});
        filter_wind.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {filterPreference[0].setWind(!filterPreference[0].isWind());if(!filterPreference[0].isWind()){filter_wind.setAlpha(0.4f);}else{filter_wind.setAlpha(1.0f);}}});

        filter_abundance.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {filterPreference[0].setAbundance(!filterPreference[0].isAbundance());if(!filterPreference[0].isAbundance()){filter_abundance.setAlpha(0.4f);}else{filter_abundance.setAlpha(1.0f);}}});
        filter_destruction.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {filterPreference[0].setDestruction(!filterPreference[0].isDestruction());if(!filterPreference[0].isDestruction()){filter_destruction.setAlpha(0.4f);}else{filter_destruction.setAlpha(1.0f);}}});
        filter_erudition.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {filterPreference[0].setErudition(!filterPreference[0].isErudition());if(!filterPreference[0].isErudition()){filter_erudition.setAlpha(0.4f);}else{filter_erudition.setAlpha(1.0f);}}});
        filter_harmony.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {filterPreference[0].setHarmony(!filterPreference[0].isHarmony());if(!filterPreference[0].isHarmony()){filter_harmony.setAlpha(0.4f);}else{filter_harmony.setAlpha(1.0f);}}});
        filter_hunt.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {filterPreference[0].setHunt(!filterPreference[0].isHunt());if(!filterPreference[0].isHunt()){filter_hunt.setAlpha(0.4f);}else{filter_hunt.setAlpha(1.0f);}}});
        filter_nihility.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {filterPreference[0].setNihility(!filterPreference[0].isNihility());if(!filterPreference[0].isNihility()){filter_nihility.setAlpha(0.4f);}else{filter_nihility.setAlpha(1.0f);}}});
        filter_preservation.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {filterPreference[0].setPreservation(!filterPreference[0].isPreservation());if(!filterPreference[0].isWind()){filter_preservation.setAlpha(0.4f);}else{filter_preservation.setAlpha(1.0f);}}});


        filter_rare_1.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {filterPreference[0].setRare1(!filterPreference[0].isRare1());}});
        filter_rare_2.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {filterPreference[0].setRare2(!filterPreference[0].isRare2());}});
        filter_rare_3.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {filterPreference[0].setRare3(!filterPreference[0].isRare3());}});
        filter_rare_4.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {filterPreference[0].setRare4(!filterPreference[0].isRare4());}});
        filter_rare_5.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {filterPreference[0].setRare5(!filterPreference[0].isRare5());}});

        filter_release.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {filterPreference[0].setRelease(!filterPreference[0].isRelease());}});
        filter_beta.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {filterPreference[0].setBeta(!filterPreference[0].isBeta());}});
        filter_soon.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {filterPreference[0].setSoon(!filterPreference[0].isSoon());}});


        if (!dialog.isShowing()){
            dialog.show();
        }
    }


    private void changeListGrid(ImageButton button, String TYPE, RecyclerView recyclerView, HSRItemAdapter adapter, ArrayList<HSRItem> arrayList){
        String status = sharedPreferences.getString("grid_"+TYPE,HSRItemAdapter.DEFAULT);
        switch (status){
            case HSRItemAdapter.ONE_IN_ROW: {
                editor.putString("grid_"+TYPE,HSRItemAdapter.THREE_IN_ROW).apply();
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 3);
                recyclerView.setLayoutManager(mLayoutManager);
                adapter.filterList(arrayList);
                recyclerView.setAdapter(adapter);

                button.setImageResource(R.drawable.ic_row_3_item);
                break;
            }
            case HSRItemAdapter.THREE_IN_ROW: {
                editor.putString("grid_"+TYPE,HSRItemAdapter.ONE_IN_ROW).apply();
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 1);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setAdapter(adapter);
                adapter.filterList(arrayList);
                button.setImageResource(R.drawable.ic_row_1_item);
                break;
            }
        }
    }
}