/*
 * Project Honkai Stargazer (崩壞•星穹觀星者) was
 * Created & Develop by Voc-夜芷冰 , Programmer of Xectorda
 * Copyright © 2023 Xectorda 版權所有
 */

package com.voc.honkai_stargazer.dev;

import static com.voc.honkai_stargazer.util.ItemRSS.LoadAssestData;
import static com.voc.honkai_stargazer.util.ItemRSS.VERSION_1_2_0;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.voc.honkai_stargazer.util.LangUtil;
import com.voc.honkai_stargazer.util.LogExport;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Iterator;

public class HelpTool {

    /*
    Help Tool, for dev only
     */

    Context context = null;

    public static final String VERSION_CHECK = "VERSION_CHECK";

    public static void trigger_help_tool(Context context){
        String json_base2 = LoadAssestData(context, "character_data/character_list.json");
        try {
            JSONArray array = new JSONArray(json_base2);
            ArrayList<String> materialList = new ArrayList<>();
            /*
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                //Special requirements
                //if (!object.getString("fileName").contains("player")) {return;}
                if (!matchRequirement(object,VERSION_CHECK)){return;}

                String json_base = LoadAssestData(context, "character_data/" + "en" + "/" + object.getString("fileName") + ".json");
                if (!json_base.equals("")) {
                    JSONObject jsonObject = new JSONObject(json_base);
                    HelpTool.help_tool_eidolon(jsonObject, context);
                    HelpTool.help_tool_skill(jsonObject, context);
                }
            }*/
            /*
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                //Special requirements
                //if (object.getString("fileName").contains("player")) {

                String json_base = LoadAssestData(context, "character_data/" + "en" + "/" + object.getString("fileName") + ".json");
                if (!json_base.equals("")) {
                    JSONObject jsonObject = new JSONObject(json_base);
                    materialList = HelpTool.help_tool_material(jsonObject,context,materialList);
                    HelpTool.help_tool_material_id(jsonObject,context,materialList);
                }
                //}
            }*/

        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public static void help_tool_eidolon(JSONObject jsonObject,Context context) throws JSONException {
        String str_final = "";//""-----------"+jsonObject.getString("name")+"-----------"+"\n";
        JSONArray ranks = jsonObject.getJSONArray("ranks");
        for (int x = 0  ;x < ranks.length() ; x++){
            str_final = str_final + "ren https://starrailstation.com/assets/"+ranks.getJSONObject(x).getString("artPath")+".webp \""+jsonObject.getString("name").toLowerCase().replace(" ","_").replace("'","").replace("trailblazer",playerCustomSex(jsonObject))+"_eidolon"+String.valueOf(x+1)+".webp\""+"\n";
        }

        LogExport.special(str_final, context, LogExport.BETA_TESTING);
    }

    private static String playerCustomElement(JSONObject jsonObject) {
        if (jsonObject != null && !jsonObject.equals("")){
            try {
                if (jsonObject.has("damageType") && jsonObject.getJSONObject("damageType").has("name")){
                    return "trailblazer_"+jsonObject.getJSONObject("damageType").getString("name").toLowerCase();
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        return "trailblazer";
    }
    private static String playerCustomSex(JSONObject jsonObject) {
        if (jsonObject != null && !jsonObject.equals("")){
            try {
                if (jsonObject.has("pageId")){
                    if (jsonObject.getString("pageId").contains("playerboy")){return "trailblazer_male";}
                    if (jsonObject.getString("pageId").contains("playergirl")){return "trailblazer_female";}
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        return "trailblazer";
    }

    private static String playerCustomSpecific(JSONObject jsonObject) {
        if (jsonObject != null && !jsonObject.equals("")){
            try {
                if (jsonObject.has("pageId")){
                    switch (jsonObject.getString("pageId")){
                        case "playerboy" : return "trailblazer_physical_male";
                        case "playerboy2" : return "trailblazer_fire_male";

                        case "playergirl" : return "trailblazer_physical_female";
                        case "playergirl2" : return "trailblazer_fire_female";

                        default: return "trailblazer_";
                    }
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        return "trailblazer_";
    }

    public static ArrayList<String> help_tool_material(JSONObject jsonObject, Context context, ArrayList<String> materialList) throws JSONException {
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
        return materialList;
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
    public static void help_tool_skill(JSONObject jsonObject, Context context) throws JSONException {
        String str_final = "";//""-----------"+jsonObject.getString("name")+"-----------"+"\n";
        JSONArray ranks = jsonObject.getJSONArray("skills");
        for (int x = 0  ;x < ranks.length() ; x++){
            if (x != 4){
                str_final = str_final + "ren https://starrailstation.com/assets/"+ranks.getJSONObject(x).getString("iconPath")+".webp \""+jsonObject.getString("name").toLowerCase().replace(" ","_").replace("'","").replace("trailblazer",playerCustomElement(jsonObject))+"_skill"+String.valueOf(x+1)+".webp\""+"\n";
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
                    if (dataInRelic == "") return;
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

    public void help_tool_export_locale_advice(Context context){
        String json_base2 = LoadAssestData(context, "character_data/character_list.json");
        try {
            this.context = context;
            JSONArray array = new JSONArray(json_base2);
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                String urlName = object.getString("name").toLowerCase().replace(" ","-").replace("(","").replace(")","");
                //if (!matchRequirement(object,VERSION_CHECK)){return;}
                new JsonTask().execute("https://www.prydwen.gg/page-data/star-rail/characters/"+urlName+"/page-data.json",object.getString("name"),object.getString("fileName"));
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public static boolean matchRequirement(JSONObject object, String requirement){
        try {
            switch (requirement){
                case VERSION_CHECK : return (object.getString("version").equals(VERSION_1_2_0));
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    private class JsonTask extends AsyncTask<String, String, String> {

        String charName = "N/A";
        String fileName = "N/A";
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... params) {
            charName = params[1];
            fileName = params[2];

            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                }

                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String jsonData) {
            String resultData = "NULL";
            try{
                if (jsonData != null){
                    JSONObject jsonObject = new JSONObject(jsonData);
                    JSONObject jsonTMP = null;
                    JSONArray jsonARR = null;
                    JSONArray buildData = null;
                    JSONObject buildDataOBJ = null;
                    JSONArray teams = null;
                    if (jsonObject.has("result")) jsonTMP = jsonObject.getJSONObject("result");
                    if (jsonTMP.has("data")) jsonTMP = jsonTMP.getJSONObject("data");
                    if (jsonTMP.has("currentUnit")) jsonTMP = jsonTMP.getJSONObject("currentUnit");
                    if (jsonTMP.has("nodes")) jsonARR = jsonTMP.getJSONArray("nodes");
                    if (jsonARR.getJSONObject(0).has("buildData")) buildData = jsonARR.getJSONObject(0).getJSONArray("buildData");
                    if (jsonARR.getJSONObject(0).has("teams")) {
                        teams = jsonARR.getJSONObject(0).getJSONArray("teams");
                        buildData = buildData.put(teams);
                    }
                    buildDataOBJ = buildData.getJSONObject(0);
                    resultData = (buildDataOBJ != null ? buildDataOBJ.toString() : null);

                    System.out.println(charName+" : "+resultData);

                    File ext = context.getFilesDir();
                    System.out.println(ext + "/" + fileName+".json");
                    if (!Files.exists(Paths.get(ext + "/" + fileName+".json"))) {
                        Files.createFile(Paths.get(ext + "/" + fileName+".json"));
                        Files.write(Paths.get(ext + "/" + fileName +".json"), resultData.getBytes(), new StandardOpenOption[]{StandardOpenOption.APPEND});
                    }else{
                        Files.write(Paths.get(ext + "/" + resultData+".json"), resultData.getBytes(), new StandardOpenOption[]{StandardOpenOption.APPEND});
                    }

                }
            } catch (JSONException e) {
                System.out.println(charName+" : []");
            } catch (IOException e) {
                System.out.println(charName+" : I/O ERROR");
            }
            super.onPostExecute(jsonData);
        }
    }
}
