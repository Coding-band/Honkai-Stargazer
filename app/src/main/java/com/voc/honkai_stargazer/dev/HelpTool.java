/*
 * Project Honkai Stargazer (崩壞•星穹觀星者) was
 * Created & Develop by Voc-夜芷冰 , Programmer of Xectorda
 * Copyright © 2023 Xectorda 版權所有
 */

package com.voc.honkai_stargazer.dev;

import static com.voc.honkai_stargazer.util.LoadAssestData.LoadAssestData;

import android.content.Context;

import com.voc.honkai_stargazer.data.MaterialItem;
import com.voc.honkai_stargazer.util.LogExport;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class HelpTool {

    /*
    Help Tool, for dev only
     */

    public static void trigger_help_tool(Context context){
        String json_base2 = LoadAssestData(context, "character_data/character_list.json");
        try {
            JSONArray array = new JSONArray(json_base2);
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                String json_base = LoadAssestData(context, "character_data/" + "en" + "/" + object.getString("fileName") + ".json");
                if (json_base != null) {
                    JSONObject jsonObject = new JSONObject(json_base);

                }
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public static void help_tool_eidolon(JSONObject jsonObject,Context context) throws JSONException {
        String str_final = "";//""-----------"+jsonObject.getString("name")+"-----------"+"\n";
        JSONArray ranks = jsonObject.getJSONArray("ranks");
        for (int x = 0  ;x < ranks.length() ; x++){
            str_final = str_final + "ren https://starrailstation.com/assets/"+ranks.getJSONObject(x).getString("artPath")+".webp \""+jsonObject.getString("name").toLowerCase().replace(" ","_").replace("'","")+"_eidolon"+String.valueOf(x+1)+".webp\""+"\n";
        }

        LogExport.special(str_final, context, LogExport.BETA_TESTING);
    }
    public static void help_tool_material(JSONObject jsonObject, Context context, ArrayList<String> materialList) throws JSONException {
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
    public static void help_tool_material_id(JSONObject jsonObject, Context context, ArrayList<String> materialList) throws JSONException {
        String str_final = "";//""-----------"+jsonObject.getString("name")+"-----------"+"\n";
        JSONObject ranks = jsonObject.getJSONObject("itemReferences");
        Iterator<String> iter = ranks.keys();
        for (int x = 0  ;x < ranks.length() ; x++){
            String key = iter.next();
            if (!materialList.contains(String.valueOf(ranks.getJSONObject(key).getInt("id")))){
                str_final = str_final + "ID: "+String.valueOf(ranks.getJSONObject(key).getInt("id"))+" || material_"+ranks.getJSONObject(key).getString("name").toLowerCase().replace(" ","_").replace("'","")+"\""+"\n";
                materialList.add(String.valueOf(ranks.getJSONObject(key).getInt("id")));
            }
        }

        LogExport.special(str_final, context, LogExport.BETA_TESTING);
    }
    public void help_tool_skill(JSONObject jsonObject, Context context) throws JSONException {
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
