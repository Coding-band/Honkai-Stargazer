package com.voc.honkai_stargazer.util;/*
 * Project Genshin Spirit (原神小幫手) was
 * Created & Develop by Voc-夜芷冰 , Programmer of Xectorda
 * Copyright © 2023 Xectorda 版權所有
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import com.voc.honkai_stargazer.BuildConfig;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LogExport {
    public static String DOWNLOAD_UNZIP_TASK = "downloadUnzipTask.txt";
    public static String FETCH_FILE_FUTURE = "fetchFileFuture.txt";
    public static String BETA_TESTING = "betaTesting.txt";
    public static String HELP_TOOL = "helpTool.txt";
    public static String STABLE_ERROR_LOG = "stableErrorLog.txt";

    public static String[] list = {STABLE_ERROR_LOG,DOWNLOAD_UNZIP_TASK,BETA_TESTING, FETCH_FILE_FUTURE, HELP_TOOL};

    public static void export (String className, String functionName, String data, Context context, String fileName) {
        //if (BuildConfig.FLAVOR.equals("dev") || BuildConfig.FLAVOR.equals("beta")) {
        String dataFinal =
                "--------\n" +
                        "Class : " + className + "\n" +
                        "Function : " + functionName + "\n" +
                        "Time : " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()) + "\n" +
                        "UnixTimeStamp : " + System.currentTimeMillis() + "\n\n";
        String fileHead =
                "This file was created in " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()) + "\n";
        try {
            dataFinal = dataFinal + data + "\n\n";
            File ext = context.getExternalMediaDirs()[0];
            Files.write(Paths.get(ext + "/" + "honkai_stargazer/" + fileName), dataFinal.getBytes(), new StandardOpenOption[]{StandardOpenOption.APPEND});
        } catch (IOException e) {
            Log.i("LogExport", e.getMessage());
        }
        //}
    }
    public static void bugLog (String className, String functionName, String data, Context context) {
        //if (BuildConfig.FLAVOR.equals("dev") || BuildConfig.FLAVOR.equals("beta")) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user_info",Context.MODE_PRIVATE);
        String dataFinal =
                        "Class : " + className + "\n" +
                        "Function : " + functionName + "\n" +
                        "Time : " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()) + "\n" +
                        "Device Name : " + Build.MODEL + "\n" +
                        "Android Version : " + String.valueOf(Build.VERSION.SDK_INT) + "\n" +
                        "App Version : " + String.valueOf(BuildConfig.VERSION_NAME) + "\n" +
                        "App Preferences : " + sharedPreferences.getString("curr_lang","N/A") +" | " + sharedPreferences.getString("dayNight","N/A") + "\n" +
                        "UnixTimeStamp : " + System.currentTimeMillis() + "\n\n";
        String fileHead =
                "This file was created in " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()) + "\n";
        try {
            File ext = context.getExternalMediaDirs()[0];
            long currTime = System.currentTimeMillis();
            File file = new File(ext + "/" + "honkai_stargazer/bugLog/Log_" + currTime+".txt");
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }

            dataFinal = dataFinal + data;
            Files.write(Paths.get(file.getPath()), dataFinal.getBytes(), new StandardOpenOption[]{StandardOpenOption.WRITE});
            context.getSharedPreferences("user_info",Context.MODE_PRIVATE).edit().putString("last_bug_report","Log_" + currTime+".txt").apply();
        } catch (IOException e) {
            Log.i("LogExport", e.getMessage());
        }
        //}
    }
    public static void special (String data, Context context, String fileName) {
        try {
            File ext = context.getFilesDir();
            if (!Files.exists(Paths.get(ext + "/" + BETA_TESTING))) {
                Files.createFile(Paths.get(ext + "/" + BETA_TESTING));
                Files.write(Paths.get(ext + "/" + BETA_TESTING), data.getBytes(), new StandardOpenOption[]{StandardOpenOption.APPEND});
            }else{
                Files.write(Paths.get(ext + "/" + fileName), data.getBytes(), new StandardOpenOption[]{StandardOpenOption.APPEND});
            }
        } catch (IOException e) {
            Log.i("LogExport", e.getMessage());
        }
    }

    public static void init(Context context) {
        //if (BuildConfig.FLAVOR.equals("dev") || BuildConfig.FLAVOR.equals("beta")) {
            String fileHead = "This file was created in " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()) + "\n";
            try {
                File ext = context.getExternalMediaDirs()[0];
                if (!Files.exists(Paths.get(ext + "/" + "honkai_stargazer/"))) {
                    Files.createDirectory(Paths.get(ext + "/" + "honkai_stargazer/"));
                }
                for (int x = 0; x < list.length; x++) {
                    if (!Files.exists(Paths.get(ext + "/" + "honkai_stargazer/" + list[x]))) {
                        Files.createFile(Paths.get(ext + "/" + "honkai_stargazer/" + list[x]));
                        Log.i("Voc", ext + "/" + "honkai_stargazer/" + list[x]);
                        Files.write(Paths.get(ext + "/" + "honkai_stargazer/" + list[x]), fileHead.getBytes(), new StandardOpenOption[]{StandardOpenOption.APPEND});
                    }
                }
            } catch (IOException e) {
                Log.i("LogExport", e.getMessage());
                e.printStackTrace();
            }
        }
    //}
}
