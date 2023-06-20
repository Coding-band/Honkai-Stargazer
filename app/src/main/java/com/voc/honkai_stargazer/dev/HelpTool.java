/*
 * Project Honkai Stargazer (崩壞•星穹觀星者) was
 * Created & Develop by Voc-夜芷冰 , Programmer of Xectorda
 * Copyright © 2023 Xectorda 版權所有
 */

package com.voc.honkai_stargazer.dev;

import static com.voc.honkai_stargazer.util.ItemRSS.LoadAssestData;

import android.content.Context;
import android.util.Log;

import com.voc.honkai_stargazer.data.MaterialItem;
import com.voc.honkai_stargazer.util.LangUtil;
import com.voc.honkai_stargazer.util.LogExport;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
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
                if (!json_base.equals("")) {
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

    public void help_tool_export_relic_pc_run(Context context) throws JSONException {
        help_tool_export_relic_pc(LangUtil.LangType.EN,context);
        help_tool_export_relic_pc(LangUtil.LangType.ZH_HK,context);
        help_tool_export_relic_pc(LangUtil.LangType.ZH_CN,context);
        help_tool_export_relic_pc(LangUtil.LangType.JP,context);
        help_tool_export_relic_pc(LangUtil.LangType.RU,context);
        help_tool_export_relic_pc(LangUtil.LangType.FR,context);
        help_tool_export_relic_pc(LangUtil.LangType.UA,context);
        help_tool_export_relic_pc(LangUtil.LangType.DE,context);
        help_tool_export_relic_pc(LangUtil.LangType.PT,context);
    }

    public void help_tool_export_relic_pc(LangUtil.LangType langType,Context context) throws JSONException{
        String dataInList = LoadAssestData(context, "relic_data/relic_list.json");
        if (!dataInList.equals("")){
            JSONArray array = new JSONArray(dataInList);
            String dataRelease = "{\n";
            for (int x = 0 ; x < array.length() ; x ++){
                if (!array.getJSONObject(x).getString("fileName").isEmpty() && !array.getJSONObject(x).getString("fileName").contains("N/A")){
                    String dataInRelic = LoadAssestData(context, "relic_data/"+langType.getCode()+"/"+array.getJSONObject(x).getString("fileName")+".json");
                    JSONArray skills = new JSONObject(dataInRelic).getJSONArray("skills");
                    dataRelease = dataRelease + "\t\""+array.getJSONObject(x).getString("name")+"\" : "+skills.toString() + (x+1 < array.length() ? ",\n" : "\n}") ;
                }
            }

            try {
                File ext = context.getFilesDir();
                if (!Files.exists(Paths.get(ext + "/" + "relic_pc_"+langType.getCode()+".json"))) {
                    Files.createFile(Paths.get(ext + "/" + "relic_pc_"+langType.getCode()+".json"));
                    Files.write(Paths.get(ext + "/" + "relic_pc_"+langType.getCode()+".json"), dataRelease.getBytes(), new StandardOpenOption[]{StandardOpenOption.WRITE});
                }else{
                    Files.write(Paths.get(ext + "/" + "relic_pc_"+langType.getCode()+".json"), dataRelease.getBytes(), new StandardOpenOption[]{StandardOpenOption.WRITE});
                }
            } catch (IOException e) {
                Log.i("LogExport -> HelpTool", e.getMessage());
            }
        }
    }

    /*
    https://www.prydwen.gg/page-data/star-rail/characters/bailu/page-data.json
    only need
    buildData in ./result/data/currentUnit/nodes/[x]/
    teams in ./result/data/currentUnit/nodes/[x]/
     */
}
