/*
 * Project Honkai Stargazer (崩壞•星穹觀星者) was
 * Created & Develop by Voc-夜芷冰 , Programmer of Xectorda
 * Copyright © 2023 Xectorda 版權所有
 */

package com.voc.honkai_stargazer.dev;

import static com.voc.honkai_stargazer.util.ItemRSS.LoadAssestData;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

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
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class HelpTool {

    /*
    Help Tool, for dev only
     */

    Context context = null;

    ArrayList<String> skillTreePointList = new ArrayList<>();
    public static final String VERSION_CHECK = "VERSION_CHECK";
    public ArrayList<String> skillTreePointArray = new ArrayList<>();

    public void trigger_help_tool(Context context){
        String json_base2 = LoadAssestData(context, "relic_data/relic_list.json");
        help_tool_export_locale_advice(context);
        try {
            JSONArray array = new JSONArray(json_base2);
            ArrayList<String> materialList = new ArrayList<>();

            /* 獲取RELIC PC ICON
            for (int x =  0 ;x < array.length() ; x++){
                String data = LoadAssestData(context,"relic_data/en/"+array.getJSONObject(x).getString("fileName")+".json");
                String name = (array.getJSONObject(x).getString("name"));
                String iconPath = new JSONObject(data).getString("iconPath");
                LogExport.special("https://cdn.starrailstation.com/assets/"+iconPath+".webp\t"+name.toLowerCase().replace(" • ","_").replace(":","").replace("(","").replace(")","").replace(" ","_").replace("_&_numby","").replace("imbibitor_lunae","il").replace(",","")+".webp\n", context, LogExport.BETA_TESTING);
            }
             */

            /*
            EXPORT RELIC EACH PIECE ICON
            for (int x =  0 ;x < array.length() ; x++){
                String data = LoadAssestData(context,"relic_data/en/"+array.getJSONObject(x).getString("fileName")+".json");
                String name = (array.getJSONObject(x).getString("name"));
                JSONObject pieces = new JSONObject(data).getJSONObject("pieces");
                for (int y = 0  ;y < 7  ;y++){
                    if (pieces.has(String.valueOf(y))){
                        LogExport.special("https://cdn.starrailstation.com/assets/"+pieces.getJSONObject(String.valueOf(y)).getString("iconPath")+".webp\t"+name.toLowerCase().replace("-","_").replace(" • ","_").replace(":","").replace("(","").replace(")","").replace(" ","_").replace("_&_numby","").replace(",","").replace("!","").replace("imbibitor_lunae","il").replace(",","")+"_"+String.valueOf(y)+".webp\n", context, LogExport.BETA_TESTING);
                    }
                }
            }

             */

            //skillTreePointArray = new ArrayList<>();
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
            }
 */
            //help_tool_export_lightcone_icon(context);

            /*
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                //Special requirements
                //if (object.getString("fileName").contains("player")) {

                String json_base = LoadAssestData(context, "character_data/" + "en" + "/" + object.getString("fileName") + ".json");
                if (!json_base.equals("")) {
                    JSONObject jsonObject = new JSONObject(json_base);
                    if (jsonObject.has("ranks") && !jsonObject.isNull("ranks") && jsonObject.getJSONArray("ranks").length() > 0){
                        singwan(jsonObject.getJSONArray("ranks"),object.getString("name").toLowerCase().replace(" • ","_").replace("(","").replace(")","").replace(" ","_").replace("_&_numby","").replace("imbibitor_lunae","il"),context);
                    }else {
                        Toast.makeText(context, "ranksIsNull in "+object.getString("fileName"), Toast.LENGTH_SHORT).show();
                    }
                }
                //}
            }
             */

            /*
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                //Special requirements
                //if (object.getString("fileName").contains("player")) {

                String json_base = LoadAssestData(context, "character_data/" + "en" + "/" + object.getString("fileName") + ".json");
                if (!json_base.equals("")) {
                    JSONObject jsonObject = new JSONObject(json_base);
                    if (jsonObject.has("skillTreePoints") && !jsonObject.isNull("skillTreePoints") && jsonObject.getJSONArray("skillTreePoints").length() > 0){
                        skillTree(jsonObject.getJSONArray("skillTreePoints"),object.getString("fileName"),0);
                    }else {
                        Toast.makeText(context, "SkillTreeIsNull in "+object.getString("fileName"), Toast.LENGTH_SHORT).show();
                    }
                }
                //}
            }
            new Handler().postDelayed(() -> {
                String str_final = "";

                for (String value : skillTreePointArray){
                    str_final += value +"\n";
                }
                LogExport.special(str_final, context, LogExport.BETA_TESTING);
            },10000);
             */



        }catch (JSONException e){
            e.printStackTrace();
        }
    }


    public void singwan(JSONArray ranks, String charName, Context context){
        String str_final = "";
        for(int x = 0 ; x < ranks.length() ; x++){
            try {
                if (ranks.getJSONObject(x).has("iconPath")){
                    str_final += "ren https://cdn.starrailstation.com/assets/"+ranks.getJSONObject(x).getString("iconPath")+".webp\t"+charName+"_soul"+String.valueOf(x+1)+".webp\n";
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        LogExport.special(str_final, context, LogExport.BETA_TESTING);
    }

    public void skillTree(JSONArray skillTreePoints, String charName, int level){
        for (int x = 0 ; x < skillTreePoints.length() ; x++){
            try{
                JSONObject jsonObject = skillTreePoints.getJSONObject(x);
                if (jsonObject.has("embedBonusSkill") && jsonObject.getJSONObject("embedBonusSkill").has("iconPath")){
                    String iconPath = jsonObject.getJSONObject("embedBonusSkill").getString("iconPath");
                    boolean isExist = true;
                    if (!skillTreePointArray.contains(iconPath)) {
                        skillTreePointArray.add(iconPath);
                        isExist = false;
                    }
                    System.out.println("skillTree ["+charName+"] - LVL"+level+" || "+"["+isExist+"] : "+iconPath);

                }

                if (jsonObject.has("embedBuff") && jsonObject.getJSONObject("embedBuff").has("iconPath")){
                    String iconPath = jsonObject.getJSONObject("embedBuff").getString("iconPath");
                    boolean isExist = true;
                    if (!skillTreePointArray.contains(iconPath)) {
                        skillTreePointArray.add(iconPath);
                        isExist = false;
                    }
                    System.out.println("skillTree ["+charName+"] - LVL"+level+" || "+"["+isExist+"] : "+iconPath);

                }

                if (jsonObject.has("children")){
                    skillTree(jsonObject.getJSONArray("children"),charName, level+1);
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
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

    public void help_tool_export_lightcone_icon(Context context) throws JSONException {
        LangUtil.LangType langType = LangUtil.LangType.ZH_HK;
        String dataInList = LoadAssestData(context, "lightcone_data/lightcone_list.json");
        if (!dataInList.equals("")){
            JSONArray array = new JSONArray(dataInList);
            String dataRelease = "";
            for (int x = 0 ; x < array.length() ; x ++){
                if (!array.getJSONObject(x).getString("fileName").isEmpty() && !array.getJSONObject(x).getString("fileName").contains("N/A")){
                    String dataInRelic = LoadAssestData(context, "lightcone_data/"+langType.getCode()+"/"+array.getJSONObject(x).getString("fileName")+".json");
                    if (dataInRelic == "") return;
                    dataRelease += "ren https://cdn.starrailstation.com/assets/"+new JSONObject(dataInRelic).getString("iconPath")+".webp "+array.getJSONObject(x).getString("name").toLowerCase().replace(" ","_").toLowerCase().replace("-","_").replace(",","").replace("!","").replace(":","").replace("'","")+".webp\n";
                }
            }
            LogExport.special(dataRelease, context, LogExport.BETA_TESTING);
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
                String urlName = object.getString("name").replace("Dan Heng • ","").replace(".","").replace(" & Numby","").toLowerCase().replace(" ","-").replace("(","").replace(")","");
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
                case VERSION_CHECK : return (object.getString("version").equals("VERSION_1_2_0"));
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
                    JSONArray conesNew = new JSONArray();
                    JSONArray conesNewFAKE = new JSONArray();
                    if (jsonObject.has("result")) jsonTMP = jsonObject.getJSONObject("result");
                    if (jsonTMP.has("data")) jsonTMP = jsonTMP.getJSONObject("data");
                    if (jsonTMP.has("currentUnit")) jsonTMP = jsonTMP.getJSONObject("currentUnit");
                    if (jsonTMP.has("nodes")) jsonARR = jsonTMP.getJSONArray("nodes");
                    if (jsonARR.getJSONObject(0).has("buildData")) buildData = jsonARR.getJSONObject(0).getJSONArray("buildData");
                    buildDataOBJ = buildData.getJSONObject(0);
                    if (buildDataOBJ.has("comments")) buildDataOBJ.remove("comments");
                    if (buildDataOBJ.has("name")) buildDataOBJ.remove("name");
                    if (jsonARR.getJSONObject(0).has("teams") && !jsonARR.getJSONObject(0).isNull("teams")) {
                        teams = jsonARR.getJSONObject(0).getJSONArray("teams");
                        buildDataOBJ = (teams == null ? buildDataOBJ : buildDataOBJ.put("teams",teams));
                    }
                    if (jsonARR.getJSONObject(0).has("conesNew") && !jsonARR.getJSONObject(0).isNull("conesNew")) {
                        if (buildDataOBJ.has("cones")) buildDataOBJ.remove("cones");
                        conesNewFAKE = jsonARR.getJSONObject(0).getJSONArray("conesNew");
                        ArrayList<String> conesNewX = new ArrayList<>();
                        for(int y = 0 ; y < conesNewFAKE.length()  ;y++){
                            if (!conesNewFAKE.getJSONObject(y).has("cone") || conesNewFAKE.getJSONObject(y).isNull("cone")) continue;
                            if (!conesNewX.contains(conesNewFAKE.getJSONObject(y).getString("cone"))){
                                JSONObject tmp = new JSONObject().put("cone",conesNewFAKE.getJSONObject(y).getString("cone"));
                                conesNew.put(tmp);
                                conesNewX.add(conesNewFAKE.getJSONObject(y).getString("cone"));
                            }
                        }
                        buildDataOBJ = (conesNew == null ? buildDataOBJ : buildDataOBJ.put("conesNew",conesNew));
                    }
                    resultData = (buildDataOBJ != null ? buildDataOBJ.toString() : null);

                    System.out.println(charName+" [XPRR] : "+resultData);

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
                System.out.println(charName+" : "+e.getMessage());
            } catch (IOException e) {
                System.out.println(charName+" : I/O ERROR");
            }
            super.onPostExecute(jsonData);
        }
    }
}
