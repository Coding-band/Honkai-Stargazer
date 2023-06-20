/*
 * Project Honkai Stargazer (崩壞•星穹觀星者) was
 * Created & Develop by Voc-夜芷冰 , Programmer of Xectorda
 * Copyright © 2023 Xectorda 版權所有
 */

package com.voc.honkai_stargazer.ui;


import static android.graphics.Bitmap.Config.ARGB_8888;
import static com.voc.honkai_stargazer.util.ItemRSS.LoadAssestData;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.voc.honkai_stargazer.R;
import com.voc.honkai_stargazer.data.HSRItem;
import com.voc.honkai_stargazer.data.MaterialItem;
import com.voc.honkai_stargazer.dev.HelpTool;
import com.voc.honkai_stargazer.util.ItemRSS;
import com.voc.honkai_stargazer.util.LangUtil;
import com.voc.honkai_stargazer.util.RoundedCornersTransformation;
import com.voc.honkai_stargazer.util.VibrateUtil;
import com.willy.ratingbar.ScaleRatingBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class InfoLightconePage {
    Context context;
    Activity activity;
    HSRItem hsrItem;
    ItemRSS item_rss;

    ArrayList<MaterialItem> materialItemsRef = new ArrayList<>();
    ArrayList<Integer> materialItemsID = new ArrayList<>();

    String[] skillLvl = new String[]{"1","2","3","4","5"};
    SharedPreferences sharedPreferences;

    public void setup(Context context, Activity activity, HSRItem hsrItem) {
        this.context = context;
        this.activity = activity;
        this.hsrItem = hsrItem;
        item_rss = new ItemRSS();
        sharedPreferences = context.getSharedPreferences("user_info",Context.MODE_PRIVATE);

        final Dialog dialog = new Dialog(context, R.style.PageDialogStyle_P);
        View view = View.inflate(context, R.layout.fragment_info_lightcone_root, null);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);
        //view.setMinimumHeight((int) (ScreenSizeUtils.getInstance(this).getScreenHeight()));
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();

        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.CENTER;
        dialogWindow.setAttributes(lp);

        ImageButton info_back = view.findViewById(R.id.info_back);
        info_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        String LANGUAGE = ItemRSS.initLang(context).getCode();
        //Read JSON from Assests
        String json_base = LoadAssestData(context, "lightcone_data/" + LANGUAGE + "/" + hsrItem.getFileName() + ".json");
        String json_base2 = LoadAssestData(context,"lightcone_data/"+LangUtil.LangType.EN.getCode()+"/"+hsrItem.getFileName()+".json");
        if (json_base == "" && json_base2 != ""){json_base  =json_base2;}
        if (!json_base.equals("")) {
            try {
                JSONObject jsonObject = new JSONObject(json_base);
                TextView info_lightcone_name = view.findViewById(R.id.info_lightcone_name);
                info_lightcone_name.setText(jsonObject.getString("name"));
                info_lightcone_name.getPaint().setFakeBoldText(true);

                itemReferences_init(jsonObject);
                init(jsonObject, view);

                if (!dialog.isShowing()){
                    dialog.show();
                }

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        } else {
            Toast.makeText(context, "[" + LANGUAGE + "] " + hsrItem.getName() + "'s file not exist", Toast.LENGTH_SHORT).show();
        }

    }

    private void init(JSONObject jsonObject, View view) throws JSONException {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        Transformation transformation = new RoundedCornersTransformation((int) (15*displayMetrics.density), 0);

        ImageView info_lightcone_path_ico = view.findViewById(R.id.info_lightcone_path_ico);
        TextView info_lightcone_path_tv = view.findViewById(R.id.info_lightcone_path_tv);
        ScaleRatingBar info_lightcone_rare = view.findViewById(R.id.info_lightcone_rare);

        ImageView info_lightcone_img = view.findViewById(R.id.info_lightcone_img);
        ImageView info_lightcone_img_back = view.findViewById(R.id.info_lightcone_img_back);

        TextView info_lightcone_hp = view.findViewById(R.id.info_lightcone_hp);
        TextView info_lightcone_atk = view.findViewById(R.id.info_lightcone_atk);
        TextView info_lightcone_def = view.findViewById(R.id.info_lightcone_def);
        LinearLayout info_lightcone_material_ll = view.findViewById(R.id.info_lightcone_material_ll);
        TextView info_lightcone_lv = view.findViewById(R.id.info_lightcone_lv);
        TextView info_skill_name = view.findViewById(R.id.info_skill_name);
        TextView info_skill_desc = view.findViewById(R.id.info_skill_desc);
        TextView info_desc = view.findViewById(R.id.info_desc);
        SeekBar info_lightcone_seekbar = view.findViewById(R.id.info_lightcone_seekbar);

        LinearLayout lightcone_ll_1 = view.findViewById(R.id.lightcone_ll_1);
        LinearLayout lightcone_ll_2 = view.findViewById(R.id.lightcone_ll_2);
        LinearLayout lightcone_ll_3 = view.findViewById(R.id.lightcone_ll_3);

        lightcone_ll_1.setTranslationZ(sharedPreferences.getBoolean("isShadowInListItem",true) ? 4*displayMetrics.density : 0);
        lightcone_ll_2.setTranslationZ(sharedPreferences.getBoolean("isShadowInListItem",true) ? 4*displayMetrics.density : 0);
        lightcone_ll_3.setTranslationZ(sharedPreferences.getBoolean("isShadowInListItem",true) ? 4*displayMetrics.density : 0);

        Bitmap bitmapOriginal = BitmapFactory.decodeResource(context.getResources(),item_rss.getLightconeByName(hsrItem.getName())[1]);
        Bitmap croppedBitmap = Bitmap.createBitmap(bitmapOriginal, (int) (20*displayMetrics.density), (int) (20*displayMetrics.density),bitmapOriginal.getWidth()-(int) (40*displayMetrics.density), bitmapOriginal.getHeight()-(int) (40*displayMetrics.density));

        info_lightcone_img.setImageBitmap(croppedBitmap);
        info_lightcone_img_back.setImageBitmap(croppedBitmap);


        /*
        Picasso.get()
                .load(item_rss.getLightconeByName(hsrItem.getName())[1])
                .transform(transformation)
                .into(info_lightcone_img);
        Picasso.get()
                .load(item_rss.getLightconeByName(hsrItem.getName())[1])
                .transform(transformation)
                .into(info_lightcone_img_back);

         */

        info_lightcone_path_ico.setImageResource(item_rss.getIconByPath(hsrItem.getPath()));
        info_lightcone_path_tv.setText(item_rss.getNameByPath(hsrItem.getPath()));
        info_lightcone_path_tv.getPaint().setFakeBoldText(true);
        info_lightcone_lv.setText("01/20");
        info_lightcone_rare.setRating(hsrItem.getRare());
        info_lightcone_rare.setNumStars(hsrItem.getRare());
        info_lightcone_seekbar.setMax(80-1+6);
        info_lightcone_seekbar.setProgress(0);
        info_desc.setText(jsonObject.getString("descHash").replace("<br />", "\n"));

        JSONObject skill = jsonObject.getJSONObject("skill");
        info_skill_name.setText(skill.getString("name"));

        asc_material_init(info_lightcone_material_ll, jsonObject,-1 );
        asc_material_change(info_lightcone_material_ll, jsonObject, -1);
        asc_status_change(jsonObject, info_lightcone_seekbar, 0, info_lightcone_hp, info_lightcone_atk, info_lightcone_def);


        info_lightcone_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                VibrateUtil.vibrate(context);
                try {
                    int lvlCurr = seekBar.getProgress()+1;
                    int lvlPART = 0;
                    int[] lvlMax = new int[]{20,30,40,50,60,70,80};
                    for (int x = 0 ; x < lvlMax.length ; x ++){
                        if (seekBar.getProgress()+1 - lvlPART > lvlMax[x]){
                            lvlPART ++;
                        }
                    }

                    lvlCurr = seekBar.getProgress() + 1 - lvlPART;

                    asc_material_change(info_lightcone_material_ll, jsonObject, lvlPART-1);
                    asc_status_change(jsonObject, seekBar, lvlPART, info_lightcone_hp, info_lightcone_atk, info_lightcone_def);
                    info_lightcone_lv.setText((lvlCurr < 10 ? "0" : "")+String.valueOf(lvlCurr)+" / "+String.valueOf(lvlMax[lvlPART]));
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        ArrayAdapter server_aa = new ArrayAdapter(context,R.layout.spinner_item,skillLvl);
        server_aa.setDropDownViewResource(R.layout.spinner_dropdown_item);
        Spinner server_spinner = view.findViewById(R.id.server_spinner);
        server_spinner.setAdapter(server_aa);
        server_spinner.setSelection(0);

        server_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    skill_desc_change(info_skill_desc, skill, position);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void itemReferences_init(JSONObject jsonObject) throws JSONException {
        materialItemsRef = new ArrayList<>();
        ArrayList<MaterialItem> materialItemsRefPRE = new ArrayList<>();
        ArrayList<MaterialItem> currency = new ArrayList<>();
        ArrayList<MaterialItem> exp = new ArrayList<>();
        JSONObject itemReferences = jsonObject.getJSONObject("itemReferences");
        Iterator<String> item_iter = itemReferences.keys();
        while (item_iter.hasNext()) {
            String materialKey = item_iter.next();
            MaterialItem materialItem = new MaterialItem();
            materialItem.setId(itemReferences.getJSONObject(materialKey).getInt("id"));
            materialItem.setType(itemReferences.getJSONObject(materialKey).getInt("type"));
            materialItem.setPurposeId(itemReferences.getJSONObject(materialKey).getInt("purposeId"));
            materialItem.setName(itemReferences.getJSONObject(materialKey).getString("name"));
            materialItem.setDesc(itemReferences.getJSONObject(materialKey).getString("desc"));
            materialItem.setLore(itemReferences.getJSONObject(materialKey).getString("lore"));
            materialItem.setPurpose(itemReferences.getJSONObject(materialKey).getString("purpose"));
            materialItem.setRarity(itemReferences.getJSONObject(materialKey).getInt("rarity"));

            ArrayList<String> comeFrom = new ArrayList<>();
            for (int x = 0; x < itemReferences.getJSONObject(materialKey).getJSONArray("comeFrom").length(); x++) {
                comeFrom.add(String.valueOf(itemReferences.getJSONObject(materialKey).getJSONArray("comeFrom").get(x)));
            }
            materialItem.setComeFrom(comeFrom);
            if (materialItem.getPurposeId() == ItemRSS.MATERIAL_COMMON_CURRENCY) {
                currency.add(materialItem);
            } else if (materialItem.getPurposeId() == ItemRSS.MATERIAL_CHARACTER_EXP_MATERIAL) {
                exp.add(materialItem);
            } else {
                materialItemsRefPRE.add(materialItem);
            }
        }

        //https://stackoverflow.com/questions/9109890/android-java-how-to-sort-a-list-of-objects-by-a-certain-value-within-the-object
        Collections.sort(materialItemsRefPRE, new Comparator<MaterialItem>() {
            public int compare(MaterialItem obj1, MaterialItem obj2) {
                // ## Ascending order
                //return obj1.getId().compareToIgnoreCase(obj2.firstName); // To compare string values
                return Integer.valueOf(obj1.getRarity()).compareTo(Integer.valueOf(obj2.getRarity())); // To compare integer values

                // ## Descending order
                // return obj2.firstName.compareToIgnoreCase(obj1.firstName); // To compare string values
                // return Integer.valueOf(obj2.empId).compareTo(Integer.valueOf(obj1.empId)); // To compare integer values
            }
        });
        Collections.sort(exp, new Comparator<MaterialItem>() {
            public int compare(MaterialItem obj1, MaterialItem obj2) {
                return Integer.valueOf(obj1.getRarity()).compareTo(Integer.valueOf(obj2.getRarity()));
            }
        });
        Collections.sort(currency, new Comparator<MaterialItem>() {
            public int compare(MaterialItem obj1, MaterialItem obj2) {
                return Integer.valueOf(obj1.getRarity()).compareTo(Integer.valueOf(obj2.getRarity()));
            }
        });
        materialItemsRef.addAll(currency);
        materialItemsRef.addAll(exp);
        materialItemsRef.addAll(materialItemsRefPRE);
    }

    /*
    jsonObject -> skills[x]
     */
    private void asc_material_init(LinearLayout material_ll, JSONObject jsonObject, int finalX) throws JSONException {
        materialItemsID = new ArrayList<>();
        if (jsonObject.has("levelData")){
            JSONArray levelData = jsonObject.getJSONArray("levelData");
            for (int x = 0 ; x < levelData.length() ; x++){
                JSONArray cost = levelData.getJSONObject(x).getJSONArray("cost");
                for (int y = 0 ; y < cost.length() ; y++){
                    if (y - 1 >= 0){
                        if (!materialItemsID.contains(cost.getJSONObject(y).getInt("id"))){
                            materialItemsID.add(cost.getJSONObject(y).getInt("id"));
                        }
                    }
                }
            }

            int[] acceptPurposeIdDemo = new int[]{ItemRSS.MATERIAL_COMMON_CURRENCY, ItemRSS.MATERIAL_TRACE_MATERIAL_LIGHTCONE_ASCENSION_MATERIALS, ItemRSS.MATERIAL_TRACE_MATERIAL_CHARACTER_ASCENSION_MATERIALS};

            switch (finalX){
                case 0 : acceptPurposeIdDemo =  new int[]{
                        ItemRSS.MATERIAL_COMMON_CURRENCY,
                        ItemRSS.MATERIAL_TRACE_MATERIAL_LIGHTCONE_ASCENSION_MATERIALS,
                        ItemRSS.MATERIAL_TRACE_MATERIAL_CHARACTER_ASCENSION_MATERIALS
                };break;

                case 1 :
                case 2 :
                case 3 :
                    acceptPurposeIdDemo =  new int[]{
                            ItemRSS.MATERIAL_COMMON_CURRENCY,
                            ItemRSS.MATERIAL_TRACE_MATERIAL_LIGHTCONE_ASCENSION_MATERIALS,
                            ItemRSS.MATERIAL_TRACE_MATERIAL_CHARACTER_ASCENSION_MATERIALS,
                            ItemRSS.MATERIAL_TRACE_MATERIALS
                    };break;
                case 4 :
                    acceptPurposeIdDemo =  new int[]{};break;
                case -1 :
                    acceptPurposeIdDemo =  new int[]{
                            ItemRSS.MATERIAL_COMMON_CURRENCY,
                            //ItemRSS.MATERIAL_CHARACTER_EXP_MATERIAL,
                            ItemRSS.MATERIAL_TRACE_MATERIAL_CHARACTER_ASCENSION_MATERIALS,
                            ItemRSS.MATERIAL_CHARACTER_ASCENSION_MATERIALS
                    };break;
            }

            ArrayList<Integer> acceptPurposeId = IntStream.of(acceptPurposeIdDemo).boxed().collect(Collectors.toCollection(ArrayList::new));

            for (int x = 0 ; x < materialItemsRef.size() ; x++){
                if (acceptPurposeId.contains(materialItemsRef.get(x).getPurposeId())){
                    View item_view = LayoutInflater.from(context).inflate(R.layout.item_info_character_material, material_ll, false);
                    ImageView material_ico  = item_view.findViewById(R.id.material_ico);
                    TextView material_tv = item_view.findViewById(R.id.material_tv);
                    item_view.setId(87000000+materialItemsRef.get(x).getId());
                    material_tv.setId(87100000+materialItemsRef.get(x).getId());
                    material_ico.setId(87200000+materialItemsRef.get(x).getId());
                    Picasso.get()
                            .load(item_rss.getMaterialByID(materialItemsRef.get(x).getId()))
                            .fit()
                            .into(material_ico);
                    material_tv.setText("0");
                    material_ico.setBackgroundResource(item_rss.getBgByItemRarity(materialItemsRef.get(x).getRarity()));
                    material_ll.addView(item_view);
                }
            }
        }

    }
    private void asc_material_change(LinearLayout material_ll, JSONObject jsonObject, int max) throws JSONException {
        if (jsonObject.has("levelData")){
            JSONArray levelData = jsonObject.getJSONArray("levelData");
            for (int x = 0 ; x < materialItemsRef.size() ; x++){
                if (material_ll.findViewById(87100000+materialItemsRef.get(x).getId()) != null){
                    TextView material_tv = material_ll.findViewById(87100000+materialItemsRef.get(x).getId());
                    material_tv.setText("0");
                }
            }


            ArrayList<Integer> costSum = new ArrayList<>();
            ArrayList<Integer> costID = new ArrayList<>();
            for (int pos = 0 ; pos < max+1 ; pos++){
                JSONArray cost = levelData.getJSONObject(pos).getJSONArray("cost");
                for (int x = 0 ; x < cost.length(); x++) {
                    if (costID.contains(cost.getJSONObject(x).getInt("id"))){
                        int position = costID.indexOf(cost.getJSONObject(x).getInt("id"));
                        costSum.set(position, costSum.get(position) + cost.getJSONObject(x).getInt("count"));
                    }else{
                        costID.add(cost.getJSONObject(x).getInt("id"));
                        costSum.add(cost.getJSONObject(x).getInt("count"));
                    }
                }
            }

            if (jsonObject.has("calculator")){

            }

            for (int x = 0 ; x < costID.size(); x++){
                if (material_ll.findViewById(87100000+costID.get(x)) != null){
                    TextView material_tv = material_ll.findViewById(87100000+costID.get(x));
                    material_tv.setText(String.valueOf(costSum.get(x)));
                }
            }


        }
    }
    private void asc_status_change(JSONObject jsonObject, SeekBar seekBar,  int lvlPART, TextView asc_status_hp, TextView asc_status_atk, TextView asc_status_def) throws JSONException {
        if (jsonObject.has("levelData")) {
            JSONArray levelData = jsonObject.getJSONArray("levelData");

            DecimalFormat df = ItemRSS.getDecimalFormat();

            asc_status_hp.setText(df.format(levelData.getJSONObject(lvlPART).getDouble("hpBase") + levelData.getJSONObject(lvlPART).getDouble("hpAdd") * (seekBar.getProgress() - lvlPART)));
            asc_status_atk.setText(df.format(levelData.getJSONObject(lvlPART).getDouble("attackBase") + levelData.getJSONObject(lvlPART).getDouble("attackAdd") * (seekBar.getProgress() - lvlPART)));
            asc_status_def.setText(df.format(levelData.getJSONObject(lvlPART).getDouble("defenseBase") + levelData.getJSONObject(lvlPART).getDouble("defenseAdd") * (seekBar.getProgress() - lvlPART)));

        }
    }
    /*
    jsonObject -> skills[x]
     */
    private void skill_desc_change(TextView descTV, JSONObject jsonObject, int pos) throws JSONException {
        if (jsonObject.has("levelData")) {
            JSONArray levelData = jsonObject.getJSONArray("levelData");
            String desc = jsonObject.getString("descHash");
            JSONArray params = levelData.getJSONObject(pos).getJSONArray("params");
            descTV.setText(ItemRSS.valuedText(desc, params, context), TextView.BufferType.SPANNABLE);
        }
    }
}