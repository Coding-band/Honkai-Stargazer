/*
 * Project Honkai Stargazer (崩壞•星穹觀星者) was
 * Created & Develop by Voc-夜芷冰 , Programmer of Xectorda
 * Copyright © 2023 Xectorda 版權所有
 */

package com.voc.honkai_stargazer.dev;

import static android.content.Context.MODE_PRIVATE;

import static com.voc.honkai_stargazer.util.ItemRSS.LoadAssestData;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.voc.honkai_stargazer.R;
import com.voc.honkai_stargazer.data.FilterPreference;
import com.voc.honkai_stargazer.data.HSRItem;
import com.voc.honkai_stargazer.data.HSRItemAdapter;
import com.voc.honkai_stargazer.util.IconArrayAdapter;
import com.voc.honkai_stargazer.util.ItemRSS;
import com.voc.honkai_stargazer.util.MyItemAnimator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CharAdviceSuggester {

    public static final String CAS_RELIC = "RELIC";
    public static final String CAS_PLANETARY = "ORNAMENTS";
    final int LIGHTCON_SIZE = 10;
    final int RELIC_SIZE = 5;
    final int PLANETARY_SIZE = 5;
    Context context;
    Activity activity;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    Dialog dialog;
    Dialog dialogX;
    View rootView;
    ItemRSS item_rss;
    RecyclerView lightconesListView ;

    ArrayList<HSRItem> charactersList = new ArrayList<>();
    ArrayList<HSRItem> lightconesList = new ArrayList<>();
    ArrayList<HSRItem> lightconesListBase = new ArrayList<>();
    ArrayList<HSRItem> relicsList = new ArrayList<>();
    ArrayList<HSRItem> planetaryList = new ArrayList<>();
    ArrayList<HSRItem> selectedLightCones = new ArrayList<>();
    ArrayList<HSRItem> selectedRelics = new ArrayList<>();
    ArrayList<HSRItem> selectedPlanetarys = new ArrayList<>();

    LinearLayout lightcone_ll, relic_ll, planetary_ll;
    DisplayMetrics displayMetrics;
    HSRItemAdapter adapter;

    FloatingActionButton lightcone_float, relic_float, planetary_float = null;

    FilterPreference[] filterPreferences = new FilterPreference[]{new FilterPreference(),new FilterPreference(),new FilterPreference()};

    String charName = "N/A";
    String charTag = "N/A";

    String[] tagList = new String[]{"DPS","Support","Healer","Utility","Shielder","Debuffer","Tanker"};

    public void init(Context context, Activity activity){
        this.context = context;
        this.activity = activity;
        sharedPreferences = context.getSharedPreferences("user_info",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        item_rss = new ItemRSS();

        dialog = new Dialog(context, R.style.PageDialogStyle_P);
        rootView = View.inflate(context, R.layout.fragment_char_advice_suggest, null);
        dialog.setContentView(rootView);
        dialog.setCanceledOnTouchOutside(true);
        //view.setMinimumHeight((int) (ScreenSizeUtils.getInstance(this).getScreenHeight()));
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();

        displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.CENTER;
        dialogWindow.setAttributes(lp);

        char_list_reload();
        lightcone_list_reload();
        relic_list_reload();

        root_init();

        if (!dialog.isShowing()){
            dialog.show();
        }
    }

    public void root_init(){
        IconArrayAdapter char_aa = new IconArrayAdapter(context,charactersList);
        char_aa.setDropDownViewResource(R.layout.icon_spinner_dropdown_item);
        Spinner char_spinner = rootView.findViewById(R.id.char_spinner);
        char_spinner.setAdapter(char_aa);
        char_spinner.setSelection(0);

        lightcone_ll = rootView.findViewById(R.id.lightcone_ll);
        relic_ll = rootView.findViewById(R.id.relic_ll);
        planetary_ll = rootView.findViewById(R.id.planetary_ll);

        char_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                charName = charactersList.get(position).getName();
                ArrayList<HSRItem> availableLightCones = new ArrayList<>();
                for (HSRItem hsrItem : lightconesListBase){
                    if (hsrItem.getPath().equals(charactersList.get(position).getPath())){
                        availableLightCones.add(hsrItem);
                    }
                }
                lightconesList = availableLightCones;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter tag_aa = new ArrayAdapter(context,R.layout.spinner_item,tagList);
        tag_aa.setDropDownViewResource(R.layout.spinner_dropdown_item);
        Spinner tag_spinner = rootView.findViewById(R.id.tag_spinner);
        tag_spinner.setAdapter(tag_aa);
        tag_spinner.setSelection(0);

        lightcone_ll = rootView.findViewById(R.id.lightcone_ll);
        relic_ll = rootView.findViewById(R.id.relic_ll);
        planetary_ll = rootView.findViewById(R.id.planetary_ll);

        tag_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                charTag = tagList[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        lightcone_ll_refresh();
        relic_ll_refresh();
        planetary_ll_refresh();

        Button save = rootView.findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    saveJSON();
                } catch (IOException | JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void saveJSON() throws IOException, JSONException {
        //Arrays To JSON -> "advice"
        String itemInAdvice = "{";

        //Add Lightcones
        itemInAdvice = itemInAdvice + "\n  \"lightcones\" : [";
        for (int x =  0; x < selectedLightCones.size() ; x++){
            itemInAdvice = itemInAdvice + "\""+selectedLightCones.get(x).getName()+"\"";
            if (x + 1 < selectedLightCones.size()){
                itemInAdvice = itemInAdvice + ",\n";
            }
        }
        itemInAdvice = itemInAdvice + "\n],";

        //Add Relic
        itemInAdvice = itemInAdvice + "\n  \"relics\" : [";
        for (int x =  0; x < selectedRelics.size() ; x++){
            itemInAdvice = itemInAdvice + "\""+selectedRelics.get(x).getName()+"\"";
            if (x + 1 < selectedRelics.size()){
                itemInAdvice = itemInAdvice + ",\n";
            }
        }
        itemInAdvice = itemInAdvice + "\n],";

        //Add Planetary
        itemInAdvice = itemInAdvice + "\n  \"planetarys" +
                "\" : [";
        for (int x =  0; x < selectedPlanetarys.size() ; x++){
            itemInAdvice = itemInAdvice + "\""+selectedPlanetarys.get(x).getName()+"\"";
            if (x + 1 < selectedPlanetarys.size()){
                itemInAdvice = itemInAdvice + ",\n";
            }
        }
        itemInAdvice = itemInAdvice + "\n],";

        //Add Tag
        itemInAdvice = itemInAdvice + "\n\"tag\":\""+ charTag+"\"";

        itemInAdvice = itemInAdvice + "}";

        // Save To .JSON
        File folder = new File(context.getFilesDir() + "/suggestions/");
        File file = new File(context.getFilesDir() + "/suggestions/"+charName.toLowerCase()+".json");


        if (!folder.exists()){
            folder.mkdir();
        }
        if (!file.exists()){
            file.createNewFile();
        }else{

        }


    }
    private void lightcone_ll_refresh() {
        if (lightcone_float == null){
            lightcone_float = rootView.findViewById(R.id.lightcone_float);
            lightcone_float.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listDialog(ItemRSS.TYPE_LIGHTCONE, lightconesList, selectedLightCones, LIGHTCON_SIZE);
                }
            });
        }
        lightcone_ll.removeAllViews();
        for (int x = 0 ; x < selectedLightCones.size() ; x++){
            View item_view = LayoutInflater.from(context).inflate(R.layout.item_char_advice_suggest_ll_item, lightcone_ll, false);
            ImageView item_img  = item_view.findViewById(R.id.item_img);
            ImageView item_delete = item_view.findViewById(R.id.item_delete);
            TextView item_choice = item_view.findViewById(R.id.item_choice);
            Picasso.get()
                    .load(item_rss.getLightconeByName(selectedLightCones.get(x).getName())[0])
                    .fit()
                    .into(item_img);
            item_choice.setText(String.valueOf(x+1));
            int finalX = x;
            item_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lightcone_ll.removeView(item_view);
                    selectedLightCones.remove(finalX);
                    lightcone_ll_refresh();
                }
            });
            lightcone_ll.addView(item_view);
        }
        lightcone_ll.addView(lightcone_float);
    }
    private void relic_ll_refresh() {
        if (relic_float == null){
            relic_float = rootView.findViewById(R.id.relic_float);
            relic_float.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listDialog(ItemRSS.TYPE_RELIC, relicsList, selectedRelics, RELIC_SIZE);
                }
            });
        }
        relic_ll.removeAllViews();
        for (int x = 0 ; x < selectedRelics.size() ; x++){
            View item_view = LayoutInflater.from(context).inflate(R.layout.item_char_advice_suggest_ll_item, relic_ll, false);
            ImageView item_img  = item_view.findViewById(R.id.item_img);
            ImageView item_delete = item_view.findViewById(R.id.item_delete);
            TextView item_choice = item_view.findViewById(R.id.item_choice);
            Picasso.get()
                    .load(item_rss.getRelicByName(selectedRelics.get(x).getName())[0])
                    .fit()
                    .into(item_img);
            item_choice.setText(String.valueOf(x+1));
            int finalX = x;
            item_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    relic_ll.removeView(item_view);
                    selectedRelics.remove(finalX);
                    relic_ll_refresh();
                }
            });
            relic_ll.addView(item_view);
        }
        relic_ll.addView(relic_float);
    }
    private void planetary_ll_refresh() {
        if (planetary_float == null){
            planetary_float = rootView.findViewById(R.id.planetary_float);
            planetary_float.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listDialog(ItemRSS.TYPE_ORNAMENT, planetaryList, selectedPlanetarys, PLANETARY_SIZE);
                }
            });
        }
        planetary_ll.removeAllViews();
        for (int x = 0 ; x < selectedPlanetarys.size() ; x++){
            View item_view = LayoutInflater.from(context).inflate(R.layout.item_char_advice_suggest_ll_item, planetary_ll, false);
            ImageView item_img  = item_view.findViewById(R.id.item_img);
            ImageView item_delete = item_view.findViewById(R.id.item_delete);
            TextView item_choice = item_view.findViewById(R.id.item_choice);
            Picasso.get()
                    .load(item_rss.getRelicByName(selectedPlanetarys.get(x).getName())[0])
                    .fit()
                    .into(item_img);
            item_choice.setText(String.valueOf(x+1));
            int finalX = x;
            item_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    planetary_ll.removeView(item_view);
                    selectedPlanetarys.remove(finalX);
                    planetary_ll_refresh();
                }
            });
            planetary_ll.addView(item_view);
        }
        planetary_ll.addView(planetary_float);
    }

    private void listDialog(String TYPE, ArrayList<HSRItem> hsrItems, ArrayList<HSRItem> selectedHsrItems, int maxSizeOfList){
        dialogX = new Dialog(context, R.style.PageDialogStyle_P);
        View view = View.inflate(context, R.layout.fragment_home_characters, null);

        dialogX.setContentView(view);
        dialogX.setCanceledOnTouchOutside(true);
        //view.setMinimumHeight((int) (ScreenSizeUtils.getInstance(this).getScreenHeight()));
        Window dialogWindow = dialogX.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();

        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.CENTER;
        dialogWindow.setAttributes(lp);

        if (!dialogX.isShowing()){
            dialogX.show();
        }

        RecyclerView charactersListView = view.findViewById(R.id.charactersListView);
        int grid = 3;
        switch (sharedPreferences.getString("grid_"+TYPE,HSRItemAdapter.DEFAULT)){
            default:
            case HSRItemAdapter.ONE_IN_ROW: grid = 1; break;
            case HSRItemAdapter.THREE_IN_ROW: grid = 3; break;
        }
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context,grid );

        adapter = new HSRItemAdapter(context,activity,sharedPreferences, TYPE,true);
        charactersListView.setLayoutManager(mLayoutManager);
        charactersListView.setItemAnimator(new MyItemAnimator());
        charactersListView.setAdapter(adapter);
        charactersListView.removeAllViewsInLayout();
        adapter.filterList(hsrItems);
        adapter.selectedList(selectedHsrItems);
        adapter.maxSizeOfList(maxSizeOfList);

        ImageButton characterFilter = view.findViewById(R.id.characterFilter);
        EditText characterSearchEt = view.findViewById(R.id.characterSearchEt);
        characterFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterHandler(TYPE);
            }
        });

        ImageButton characterLayout = view.findViewById(R.id.characterLayout);
        characterLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeListGrid(characterLayout,TYPE, charactersListView, adapter,adapter.getFilterList());
            }
        });
        characterSearchEt.addTextChangedListener(searchBarHandler(TYPE, characterSearchEt));
    }

    public void portAddItem(HSRItem hsrItem, String type) {
        if (dialogX != null && dialogX.isShowing()){
            dialogX.dismiss();
        }
        switch (type){
            case ItemRSS.TYPE_LIGHTCONE: {
                selectedLightCones.add(hsrItem);
                lightcone_ll_refresh();
                break;
            }
            case ItemRSS.TYPE_RELIC: {
                selectedRelics.add(hsrItem);
                relic_ll_refresh();
                break;
            }
            case ItemRSS.TYPE_ORNAMENT: {
                selectedPlanetarys.add(hsrItem);
                planetary_ll_refresh();
                break;
            }
        }
    }

    //IMPORTANT
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

                lightconesListBase.add(hsrItem);
            }
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

                if (type.equals(CAS_RELIC)){
                    relicsList.add(hsrItem);
                }else{
                    planetaryList.add(hsrItem);
                }
            }
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
                ArrayList<HSRItem> list = planetaryList;
                ItemRSS item_rss = new ItemRSS();

                switch (TYPE){
                    case ItemRSS.TYPE_ORNAMENT:{
                        list = planetaryList;
                        break;
                    }
                    case ItemRSS.TYPE_LIGHTCONE:{
                        list = lightconesList;
                        break;
                    }
                    case ItemRSS.TYPE_RELIC:{
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
        Chip filter_rare_1 = view.findViewById(R.id.filter_rare_1);
        Chip filter_rare_2 = view.findViewById(R.id.filter_rare_2);
        Chip filter_rare_3 = view.findViewById(R.id.filter_rare_3);
        Chip filter_rare_4 = view.findViewById(R.id.filter_rare_4);
        Chip filter_rare_5 = view.findViewById(R.id.filter_rare_5);

        LinearLayout filter_status_ll = view.findViewById(R.id.filter_status_ll);
        Chip filter_release = view.findViewById(R.id.filter_release);
        Chip filter_soon = view.findViewById(R.id.filter_soon);
        Chip filter_beta = view.findViewById(R.id.filter_beta);

        Button filter_cancel = view.findViewById(R.id.filter_cancel);
        Button filter_apply = view.findViewById(R.id.filter_apply);

        switch (TYPE){
            case ItemRSS.TYPE_LIGHTCONE: {filter_rare_1.setVisibility(View.GONE); filter_rare_2.setVisibility(View.GONE); filter_element_ll.setVisibility(View.GONE);  filterPreference[0] = filterPreferences[1]; break;}
            case ItemRSS.TYPE_ORNAMENT:
            case ItemRSS.TYPE_RELIC: {filter_element_ll.setVisibility(View.GONE); filter_path_ll.setVisibility(View.GONE);  filterPreference[0] = filterPreferences[2]; break;}
        }
        filter_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (TYPE){
                    case ItemRSS.TYPE_LIGHTCONE: {filterPreference[0].setType(ItemRSS.TYPE_LIGHTCONE);filterPreferences[1] = filterPreference[0]; adapter.filterRequestList(lightconesList,filterPreference[0]);break;}
                    case ItemRSS.TYPE_ORNAMENT:
                    case ItemRSS.TYPE_RELIC: {filterPreference[0].setType(ItemRSS.TYPE_RELIC);filterPreferences[2] = filterPreference[0]; adapter.filterRequestList(relicsList,filterPreference[0]);break;}
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
        filter_preservation.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {filterPreference[0].setPreservation(!filterPreference[0].isPreservation());if(!filterPreference[0].isPreservation()){filter_preservation.setAlpha(0.4f);}else{filter_preservation.setAlpha(1.0f);}}});

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
