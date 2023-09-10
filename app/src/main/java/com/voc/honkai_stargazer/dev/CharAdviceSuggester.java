/*
 * Project Honkai Stargazer (崩壞•星穹觀星者) was
 * Created & Develop by Voc-夜芷冰 , Programmer of Xectorda
 * Copyright © 2023 Xectorda 版權所有
 */

package com.voc.honkai_stargazer.dev;

import static android.content.Context.MODE_PRIVATE;

import static com.voc.honkai_stargazer.util.ItemRSS.LoadAssestData;
import static com.voc.honkai_stargazer.util.ItemRSS.LoadData;
import static com.voc.honkai_stargazer.util.ItemRSS.TYPE_CHARACTER;
import static com.voc.honkai_stargazer.util.ItemRSS.TYPE_CHARACTER_TEAM1;
import static com.voc.honkai_stargazer.util.ItemRSS.TYPE_CHARACTER_TEAM2;
import static com.voc.honkai_stargazer.util.ItemRSS.TYPE_LIGHTCONE;
import static com.voc.honkai_stargazer.util.ItemRSS.TYPE_ORNAMENT;
import static com.voc.honkai_stargazer.util.ItemRSS.TYPE_RELIC;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
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
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.voc.honkai_stargazer.R;
import com.voc.honkai_stargazer.data.FilterPreference;
import com.voc.honkai_stargazer.data.HSRItem;
import com.voc.honkai_stargazer.data.HSRItemAdapter;
import com.voc.honkai_stargazer.util.IconArrayAdapter;
import com.voc.honkai_stargazer.util.ItemRSS;
import com.voc.honkai_stargazer.util.LogExport;
import com.voc.honkai_stargazer.util.MyItemAnimator;
import com.voc.honkai_stargazer.util.RoundedCornersTransformation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;

public class CharAdviceSuggester {

    public static final String CAS_RELIC = "RELIC";
    public static final String CAS_PLANETARY = "ORNAMENTS";
    final int LIGHTCON_SIZE = 10;
    final int TEAMMATE_SIZE = 3;
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
    ArrayList<HSRItem> charactersBaseList = new ArrayList<>();
    ArrayList<HSRItem> lightconesList = new ArrayList<>();
    ArrayList<HSRItem> lightconesListBase = new ArrayList<>();
    ArrayList<HSRItem> relicsList = new ArrayList<>();
    ArrayList<HSRItem> planetaryList = new ArrayList<>();
    ArrayList<HSRItem> selectedLightCones = new ArrayList<>();
    ArrayList<HSRItem> selectedRelics = new ArrayList<>();
    ArrayList<HSRItem> selectedPlanetarys = new ArrayList<>();
    ArrayList<HSRItem> selectedTeammates1 = new ArrayList<>();
    ArrayList<HSRItem> selectedTeammates2 = new ArrayList<>();

    LinearLayout lightcone_ll, relic_ll, planetary_ll,teammate1_ll,teammate2_ll;
    DisplayMetrics displayMetrics;
    HSRItemAdapter adapter;
    EditText name_et;


    FloatingActionButton lightcone_float, relic_float, planetary_float, teammate1_float,teammate2_float = null;

    FilterPreference[] filterPreferences = new FilterPreference[]{new FilterPreference(),new FilterPreference(),new FilterPreference()};

    String charName = "N/A";
    String charTag = "N/A";
    String authorName = "N/A";
    long unix = System.currentTimeMillis();

    public static final String TAG = "CharAdviceSuggester";

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
        IconArrayAdapter char_aa = new IconArrayAdapter(context,charactersBaseList);
        char_aa.setDropDownViewResource(R.layout.icon_spinner_dropdown_item);
        Spinner char_spinner = rootView.findViewById(R.id.char_spinner);
        char_spinner.setAdapter(char_aa);
        char_spinner.setSelection(0);

        lightcone_ll = rootView.findViewById(R.id.lightcone_ll);
        relic_ll = rootView.findViewById(R.id.relic_ll);
        planetary_ll = rootView.findViewById(R.id.planetary_ll);
        teammate1_ll = rootView.findViewById(R.id.teammate1_ll);
        teammate2_ll = rootView.findViewById(R.id.teammate2_ll);
        name_et = rootView.findViewById(R.id.name_et);

        char_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView status_file = rootView.findViewById(R.id.status_file);
                if (new File(context.getFilesDir() + "/suggestions/"+charName.toLowerCase().replace(" ","_")+".json").exists()){
                    status_file.setText(context.getString(R.string.cas_exist_file));
                }else{
                    status_file.setVisibility(View.GONE);
                }
                charName = charactersBaseList.get(position).getName();
                ArrayList<HSRItem> availableLightCones = new ArrayList<>();
                for (HSRItem hsrItem : lightconesListBase){
                    if (hsrItem.getPath().equals(charactersBaseList.get(position).getPath())){
                        availableLightCones.add(hsrItem);
                    }
                }
                lightconesList = availableLightCones;
                charactersList = new ArrayList<>(charactersBaseList);
                charactersList.remove(position);
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

        ll_refresh(TYPE_CHARACTER_TEAM1);
        ll_refresh(TYPE_CHARACTER_TEAM2);
        ll_refresh(TYPE_LIGHTCONE);
        ll_refresh(TYPE_RELIC);
        ll_refresh(TYPE_ORNAMENT);

        ImageButton cas_back = rootView.findViewById(R.id.cas_back);
        cas_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( dialog != null && dialog.isShowing()){
                    dialog.dismiss();
                }
            }
        });

        Button save = rootView.findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (name_et.getText() == null || name_et.getText().toString().equals(" ") || name_et.getText().toString().equals("")){
                        Toast.makeText(context, context.getString(R.string.cas_name_reuired), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    saveJSON();
                } catch (IOException | JSONException e) {
                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    e.printStackTrace(pw);
                    LogExport.bugLog(TAG, "save.onClick()", sw.toString(), e.getMessage(), context,LogExport.MODE_SERVER);
                }
            }
        });
        Button email = rootView.findViewById(R.id.email);
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name_et.getText() == null || name_et.getText().toString().equals(" ") || name_et.getText().toString().equals("")){
                    Toast.makeText(context, context.getString(R.string.cas_name_reuired), Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    saveJSON();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Uri path = FileProvider.getUriForFile(activity, ItemRSS.APPLICATION_ID_PROVIDER,new File(context.getFilesDir() + "/suggestions/"+charName.toLowerCase().replace(" ","_")+".json"));
                            Intent i = new Intent(Intent.ACTION_SEND);
                            i.setType("message/rfc822");
                            i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"xectorda@gmail.com"});
                            i.putExtra(Intent.EXTRA_SUBJECT, "[Honkai Stargazer - CAS]");
                            i.putExtra(Intent.EXTRA_TEXT   , "This is an auto-generate Email from Honkai Stargazer app, with an appendix of "+charName+"'s suggestion.");
                            i.putExtra(Intent.EXTRA_STREAM, path);
                            if (i.resolveActivity(activity.getPackageManager()) != null) {
                                activity.startActivity(i);
                            }
                        }
                    },500);
                } catch (IOException | JSONException e) {
                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    e.printStackTrace(pw);
                    LogExport.bugLog(TAG, "email.onClick()", sw.toString(), e.getMessage(), context, LogExport.MODE_SERVER);
                }
            }
        });
    }

    private void ll_refresh(String TYPE) {
        FloatingActionButton floatingActionButton = planetary_float;
        LinearLayout linearLayout = planetary_ll;
        ArrayList<HSRItem> selected = selectedPlanetarys;
        switch (TYPE){
            case ItemRSS.TYPE_CHARACTER_TEAM1:{
                if (teammate1_float == null){
                    teammate1_float = rootView.findViewById(R.id.teammate1_float);
                    teammate1_float.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {listDialog(ItemRSS.TYPE_CHARACTER_TEAM1, charactersList, selectedTeammates1, TEAMMATE_SIZE);}});
                }
                floatingActionButton = teammate1_float;
                linearLayout = teammate1_ll;
                selected = selectedTeammates1;
                break;
            }
            case TYPE_CHARACTER_TEAM2:{
                if (teammate2_float == null){
                    teammate2_float = rootView.findViewById(R.id.teammate2_float);
                    teammate2_float.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {listDialog(ItemRSS.TYPE_CHARACTER_TEAM2, charactersList, selectedTeammates2, TEAMMATE_SIZE);}});
                }
                floatingActionButton = teammate2_float;
                linearLayout = teammate2_ll;
                selected = selectedTeammates2;
                break;
            }
            case TYPE_LIGHTCONE:{
                if (lightcone_float == null){
                    lightcone_float = rootView.findViewById(R.id.lightcone_float);
                    lightcone_float.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {listDialog(TYPE_LIGHTCONE, lightconesList, selectedLightCones, LIGHTCON_SIZE);}});
                }
                floatingActionButton = lightcone_float;
                linearLayout = lightcone_ll;
                selected = selectedLightCones;
                break;
            }
            case TYPE_RELIC:{
                if (relic_float == null){
                    relic_float = rootView.findViewById(R.id.relic_float);
                    relic_float.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {listDialog(TYPE_RELIC, relicsList, selectedRelics, RELIC_SIZE);}});
                }
                floatingActionButton = relic_float;
                linearLayout = relic_ll;
                selected = selectedRelics;
                break;
            }
            case TYPE_ORNAMENT:{
                if (planetary_float == null){
                    planetary_float = rootView.findViewById(R.id.planetary_float);
                    planetary_float.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {listDialog(TYPE_ORNAMENT, planetaryList, selectedPlanetarys, PLANETARY_SIZE);}});
                }
                floatingActionButton = planetary_float;
                linearLayout = planetary_ll;
                selected = selectedPlanetarys;
                break;
            }
        }

        linearLayout.removeAllViews();
        for (int x = 0 ; x < selected.size() ; x++){
            View item_view = LayoutInflater.from(context).inflate(R.layout.item_char_advice_suggest_ll_item, linearLayout, false);
            ImageView item_img  = item_view.findViewById(R.id.item_img);
            ImageView item_delete = item_view.findViewById(R.id.item_delete);
            TextView item_choice = item_view.findViewById(R.id.item_choice);

            Transformation transformation = new RoundedCornersTransformation(90, 0);
            switch (TYPE){
                case TYPE_CHARACTER_TEAM1:
                case TYPE_CHARACTER_TEAM2: Picasso.get().load(item_rss.getCharByName(selected.get(x).getName(),selected.get(x).getSex())[0]).transform(transformation).fit().into(item_img);break;
                case TYPE_LIGHTCONE: Picasso.get().load(item_rss.getLightconeByName(selected.get(x).getName())[0]).fit().into(item_img);break;
                case TYPE_RELIC:
                case TYPE_ORNAMENT: Picasso.get().load(item_rss.getRelicByName(selected.get(x).getName())[0]).fit().into(item_img);break;
            }

            item_choice.setText(String.valueOf(x+1));
            int finalX = x;

            LinearLayout finalLinearLayout = linearLayout;
            ArrayList<HSRItem> finalSelected = selected;
            item_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finalLinearLayout.removeView(item_view);
                    finalSelected.remove(finalX);
                    ll_refresh(TYPE);
                }
            });
            linearLayout.addView(item_view);
        }
        linearLayout.addView(floatingActionButton);
    }

    private void saveJSON() throws IOException, JSONException {
        if (name_et.getText() == null || name_et.getText().toString().equals(" ") || name_et.getText().toString().equals("")){
            Toast.makeText(context, context.getString(R.string.cas_name_reuired), Toast.LENGTH_SHORT).show();
            return;
        }
        // Save To .JSON
        File folder = new File(context.getFilesDir() + "/suggestions/");
        File file = new File(context.getFilesDir() + "/suggestions/"+charName.toLowerCase().replace(" ","_")+".json");

        if (!folder.exists()){
            folder.mkdir();
        }

        JsonArray advice = new JsonArray();
        if (!file.exists()){
            file.createNewFile();
        }else{

            //Get old data
            JSONObject jsonObject = new JSONObject(LoadData(context, "suggestions/"+charName.toLowerCase()+".json"));
            authorName = jsonObject.getJSONObject("author_info").getString("author");
            JSONArray adviceOutput = jsonObject.getJSONArray("advice");
            JsonArray gsonArray = (JsonArray) JsonParser.parseString(adviceOutput.toString());
            advice = gsonArray;
        }

        unix = System.currentTimeMillis();

        //Now Data -> JsonArray
        JsonObject objInAdvice = new JsonObject();
        JsonArray lightcones = new JsonArray();
        JsonArray relics = new JsonArray();
        JsonArray planetarys = new JsonArray();
        JsonArray teammates = new JsonArray();
        JsonArray teammates1 = new JsonArray();
        JsonArray teammates2 = new JsonArray();

        for (HSRItem hsrItem : selectedLightCones){
            lightcones.add(hsrItem.getName());
        }
        for (HSRItem hsrItem : selectedRelics){
            relics.add(hsrItem.getName());
        }
        for (HSRItem hsrItem : selectedPlanetarys){
            planetarys.add(hsrItem.getName());
        }
        for (HSRItem hsrItem : selectedTeammates1){
            teammates1.add(hsrItem.getName());
        }
        for (HSRItem hsrItem : selectedTeammates2){
            teammates2.add(hsrItem.getName());
        }

        teammates.add(teammates1);
        teammates.add(teammates2);

        objInAdvice.add("lightcones",lightcones);
        objInAdvice.add("relics",relics);
        objInAdvice.add("planetarys",planetarys);
        objInAdvice.add("teammates",teammates);
        objInAdvice.addProperty("tag",charTag);
        advice.add(objInAdvice);

        //Final - Export
        JsonObject root = new JsonObject();
        root.addProperty("name", charName);

        JsonObject author_info = new JsonObject();
        author_info.addProperty("author",authorName);
        author_info.addProperty("create_time",unix);

        root.add("author_info",author_info);
        root.add("advice",advice);

        Writer output = new BufferedWriter(new FileWriter(file));
        output.write(root.toString());
        output.close();

        Toast.makeText(context, context.getString(R.string.cas_saved), Toast.LENGTH_SHORT).show();

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

        RecyclerView listView = view.findViewById(R.id.charactersListView);
        int grid = 3;
        switch (sharedPreferences.getString("grid_"+TYPE,HSRItemAdapter.DEFAULT)){
            default:
            case HSRItemAdapter.ONE_IN_ROW: grid = 1; break;
            case HSRItemAdapter.THREE_IN_ROW: grid = 3; break;
        }
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context,grid );

        adapter = new HSRItemAdapter(context,activity,sharedPreferences, TYPE,true);
        listView.setLayoutManager(mLayoutManager);
        listView.setItemAnimator(new MyItemAnimator());
        listView.setAdapter(adapter);
        listView.removeAllViewsInLayout();
        adapter.filterList(hsrItems);
        adapter.selectedList(selectedHsrItems);
        adapter.maxSizeOfList(maxSizeOfList);

        ImageButton filter = view.findViewById(R.id.characterFilter);
        EditText searchEt = view.findViewById(R.id.characterSearchEt);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterHandler(TYPE);
            }
        });

        ImageButton layout = view.findViewById(R.id.characterLayout);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeListGrid(layout,TYPE, listView, adapter,adapter.getFilterList());
            }
        });
        searchEt.addTextChangedListener(searchBarHandler(TYPE, searchEt));
    }

    public void portAddItem(HSRItem hsrItem, String type) {
        if (dialogX != null && dialogX.isShowing()){
            dialogX.dismiss();
        }
        switch (type){
            case ItemRSS.TYPE_CHARACTER_TEAM1: {
                selectedTeammates1.add(hsrItem);
                ll_refresh(TYPE_CHARACTER_TEAM1);
                break;
            }
            case TYPE_CHARACTER_TEAM2: {
                selectedTeammates2.add(hsrItem);
                ll_refresh(TYPE_CHARACTER_TEAM2);
                break;
            }
            case TYPE_LIGHTCONE: {
                selectedLightCones.add(hsrItem);
                ll_refresh(TYPE_LIGHTCONE);
                break;
            }
            case TYPE_RELIC: {
                selectedRelics.add(hsrItem);
                ll_refresh(TYPE_RELIC);
                break;
            }
            case TYPE_ORNAMENT: {
                selectedPlanetarys.add(hsrItem);
                ll_refresh(TYPE_ORNAMENT);
                break;
            }
        }
    }

    //IMPORTANT
    private void char_list_reload() {
        charactersBaseList = new ArrayList<>();
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

                charactersBaseList.add(hsrItem);
            }
        } catch (JSONException e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            LogExport.bugLog(TAG, "char_list_reload()", sw.toString(), e.getMessage(), context, LogExport.MODE_SERVER);
        }
    }
    private void lightcone_list_reload() {
        lightconesListBase = new ArrayList<>();
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
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            LogExport.bugLog(TAG, "lightcone_list_reload()", sw.toString(), e.getMessage(), context, LogExport.MODE_SERVER);
        }
    }
    private void relic_list_reload() {
        relicsList = new ArrayList<>();
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
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            LogExport.bugLog(TAG, "relic_list_reload()", sw.toString(), e.getMessage(), context, LogExport.MODE_SERVER);
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
                    case TYPE_ORNAMENT:{
                        list = planetaryList;
                        break;
                    }
                    case TYPE_LIGHTCONE:{
                        list = lightconesList;
                        break;
                    }
                    case TYPE_RELIC:{
                        list = relicsList;
                        break;
                    }
                    case TYPE_CHARACTER_TEAM1:
                    case TYPE_CHARACTER_TEAM2:
                    case TYPE_CHARACTER:{
                        list = charactersList;
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
            case TYPE_CHARACTER_TEAM1:
            case TYPE_CHARACTER_TEAM2:
            case TYPE_CHARACTER: {filter_rare_1.setVisibility(View.GONE); filter_rare_2.setVisibility(View.GONE); filter_rare_3.setVisibility(View.GONE); filterPreference[0] = filterPreferences[0]; break;}
            case TYPE_LIGHTCONE: {filter_rare_1.setVisibility(View.GONE); filter_rare_2.setVisibility(View.GONE); filter_element_ll.setVisibility(View.GONE);  filterPreference[0] = filterPreferences[1]; break;}
            case TYPE_ORNAMENT:
            case TYPE_RELIC: {filter_element_ll.setVisibility(View.GONE); filter_path_ll.setVisibility(View.GONE);  filterPreference[0] = filterPreferences[2]; break;}
        }
        filter_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (TYPE){
                    case TYPE_CHARACTER_TEAM1:
                    case TYPE_CHARACTER_TEAM2:
                    case TYPE_CHARACTER: {filterPreference[0].setType(TYPE_CHARACTER); filterPreferences[0] = filterPreference[0]; adapter.filterRequestList(charactersList,filterPreference[0]); break;}
                    case TYPE_LIGHTCONE: {filterPreference[0].setType(TYPE_LIGHTCONE);filterPreferences[1] = filterPreference[0]; adapter.filterRequestList(lightconesList,filterPreference[0]);break;}
                    case TYPE_ORNAMENT:
                    case TYPE_RELIC: {filterPreference[0].setType(TYPE_RELIC);filterPreferences[2] = filterPreference[0]; adapter.filterRequestList(relicsList,filterPreference[0]);break;}
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
