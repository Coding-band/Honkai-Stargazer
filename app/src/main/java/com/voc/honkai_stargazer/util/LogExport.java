package com.voc.honkai_stargazer.util;/*
 * Project Genshin Spirit (原神小幫手) was
 * Created & Develop by Voc-夜芷冰 , Programmer of Xectorda
 * Copyright © 2023 Xectorda 版權所有
 */

import static com.voc.honkai_stargazer.util.ItemRSS.LoadExtendData;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.voc.honkai_stargazer.BuildConfig;
import com.voc.honkai_stargazer.R;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LogExport {
    public static String DOWNLOAD_UNZIP_TASK = "downloadUnzipTask.txt";
    public static String FETCH_FILE_FUTURE = "fetchFileFuture.txt";
    public static String BETA_TESTING = "betaTesting.txt";
    public static String HELP_TOOL = "helpTool.txt";
    public static String STABLE_ERROR_LOG = "stableErrorLog.txt";

    public static String[] list = {STABLE_ERROR_LOG,DOWNLOAD_UNZIP_TASK,BETA_TESTING, FETCH_FILE_FUTURE, HELP_TOOL};

    public static String MODE_EMAIL = "EMAIL";
    public static String MODE_SERVER = "SERVER";

    private Context context;
    private Activity activity;

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
    public static void bugLog (String className, String functionName, String data, String message_key, Context context, String mode) {
        //if (BuildConfig.FLAVOR.equals("dev") || BuildConfig.FLAVOR.equals("beta")) {
        if (data.equals("INSERT_OK") || message_key.equals("INSERT_OK")) return;

        SharedPreferences sharedPreferences = context.getSharedPreferences("user_info",Context.MODE_PRIVATE);
        String unixCurr = String.valueOf(System.currentTimeMillis());
        String dataFinal =
                        "Class : " + className + "\n" +
                        "Function : " + functionName + "\n" +
                        "Time : " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()) + "\n" +
                        "Device Name : " + Build.MODEL + "\n" +
                        "Android Version : " + String.valueOf(Build.VERSION.SDK_INT) + "\n" +
                        "App Version : " + String.valueOf(BuildConfig.VERSION_NAME) + "\n" +
                        "App Preferences : " + sharedPreferences.getString("curr_lang","N/A") +" | " + sharedPreferences.getString("dayNight","N/A") + "\n" +
                        "UnixTimeStamp : " + unixCurr + "\n" +
                        "Error Key : " + message_key + "\n\n";
        String fileHead =
                "This file was created in " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()) + "\n";
        try {
            File ext = context.getExternalMediaDirs()[0];
            File file = new File(ext + "/" + "honkai_stargazer/bugLog/Log_" + unixCurr+".txt");
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }

            dataFinal = dataFinal + data;
            Files.write(Paths.get(file.getPath()), dataFinal.getBytes(), new StandardOpenOption[]{StandardOpenOption.WRITE});
            context.getSharedPreferences("user_info",Context.MODE_PRIVATE).edit().putString("last_bug_report","Log_" + unixCurr+".txt").apply();
            context.getSharedPreferences("user_info",Context.MODE_PRIVATE).edit().putString("last_bug_report_mode",mode).apply();
            context.getSharedPreferences("user_info",Context.MODE_PRIVATE).edit().putString("last_bug_report_error_keys",
                        className+"XPR"+
                            functionName+"XPR"+
                            sharedPreferences.getString("curr_lang","N/A") +" | " + sharedPreferences.getString("dayNight","N/A") +"XPR"+
                            String.valueOf(Build.MODEL) + "XPR" +
                            String.valueOf(Build.VERSION.SDK_INT) + "XPR" +
                            String.valueOf(BuildConfig.VERSION_NAME) + "XPR" +
                            unixCurr+"XPR"+
                            message_key
            ).apply();
        } catch (IOException e) {
            Log.i("LogExport", e.getMessage());
        }
        //}
    }
    public static void special (String data, Context context, String fileName) {
        try {
            File ext = context.getExternalMediaDirs()[0];
            if (!Files.exists(Paths.get(ext + "/" + "honkai_stargazer/" + BETA_TESTING))) {
                Files.createFile(Paths.get(ext + "/" + "honkai_stargazer/" + BETA_TESTING));
                Files.write(Paths.get(ext + "/" + "honkai_stargazer/" + BETA_TESTING), data.getBytes(), new StandardOpenOption[]{StandardOpenOption.APPEND});
            }else{
                Files.write(Paths.get(ext + "/" + "honkai_stargazer/" + fileName), data.getBytes(), new StandardOpenOption[]{StandardOpenOption.APPEND});
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

    public static void checkHasBugLog(Context context, SharedPreferences sharedPreferences,Activity activity) {
        if (!sharedPreferences.getString("last_bug_report","NONE").equals("NONE")){
            String reportName = sharedPreferences.getString("last_bug_report","NONE");

            showErrorLogReportDialog(context,sharedPreferences,activity,reportName);

            /*
            final Dialog dialog = new Dialog(context, R.style.NormalDialogStyle_N);
            View view = View.inflate(context, R.layout.fragment_dialog_bug, null);
            dialog.setContentView(view);
            dialog.setCanceledOnTouchOutside(true);
            Window dialogWindow = dialog.getWindow();
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();

            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            lp.gravity = Gravity.CENTER;
            dialogWindow.setAttributes(lp);

            String bugReport = LoadExtendData(context, "honkai_stargazer/bugLog/" + reportName);
            TextView bug_log = view.findViewById(R.id.bug_log);
            bug_log.setText((bugReport.equals("") ? "Empty" : bugReport));

            Button cancel = view.findViewById(R.id.bug_cancel);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialog.isShowing() && dialog != null){
                        dialog.dismiss();
                    }
                }
            });

            Button email = view.findViewById(R.id.bug_email);
            email.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialog.isShowing() && dialog != null){
                        dialog.dismiss();
                    }
                    new LogExport().sendErrorHandler(context,sharedPreferences,activity);
                }
            });


            if (dialog != null && !dialog.isShowing()){
                dialog.show();
            }
             */
        }
    }
    static void showErrorLogReportDialog(Context context, SharedPreferences sharedPreferences,Activity activity, String reportName){
        ThemeUtil themeUtil = new ThemeUtil(context,activity);
        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(context);
        materialAlertDialogBuilder.setTitle(R.string.bug_title);
        materialAlertDialogBuilder.setIcon(R.drawable.pom_pom_failed_issue);
        materialAlertDialogBuilder.setMessage(context.getString(R.string.bug_desc)+"\n"+(context.getString(R.string.bug_collect_data)));
        Drawable bg = materialAlertDialogBuilder.getBackground();
        Objects.requireNonNull(bg).setTint(themeUtil.themeColorMultiplyExport(ThemeUtil.TINT_COMMON, false));
        materialAlertDialogBuilder.setBackground(bg);
        materialAlertDialogBuilder.setPositiveButton(R.string.bug_report_btn_report, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                new LogExport().sendErrorHandler(context,sharedPreferences,activity);
            }
        });
        materialAlertDialogBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Exit Dialog

            }
        });
        materialAlertDialogBuilder.setNeutralButton(R.string.bug_title, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                showErrorLogMessageDialog(context,sharedPreferences,activity,reportName);
            }
        });
        materialAlertDialogBuilder.create().show();
    }
    static void showErrorLogMessageDialog(Context context, SharedPreferences sharedPreferences,Activity activity, String reportName){
        ThemeUtil themeUtil = new ThemeUtil(context,activity);
        String bugReport = LoadExtendData(context, "honkai_stargazer/bugLog/" + reportName);

        MaterialAlertDialogBuilder materialAlertDialogBuilderERROR = new MaterialAlertDialogBuilder(context);
        materialAlertDialogBuilderERROR.setTitle(R.string.bug_title);
        materialAlertDialogBuilderERROR.setMessage((bugReport.equals("") ? context.getString(R.string.n_a) : bugReport));
        Drawable bg = materialAlertDialogBuilderERROR.getBackground();
        Objects.requireNonNull(bg).setTint(themeUtil.themeColorMultiplyExport(ThemeUtil.TINT_COMMON, false));
        materialAlertDialogBuilderERROR.setBackground(bg);
        materialAlertDialogBuilderERROR.setPositiveButton(context.getString(R.string.bug_report_return), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                showErrorLogReportDialog(context,sharedPreferences,activity,reportName);
            }
        });
        materialAlertDialogBuilderERROR.create().show();
    }
    //}

    public void sendErrorHandler(Context context, SharedPreferences sharedPreferences, Activity activity){
        this.context = context;
        this.activity = activity;
        String reportName = sharedPreferences.getString("last_bug_report","NONE");
        if (sharedPreferences.getString("last_bug_report_mode",LogExport.MODE_SERVER).equals(LogExport.MODE_SERVER)){
            //PHP+PYTHON+SQLite
            String[] error_keys = sharedPreferences.getString("last_bug_report_error_keys","NONE").split("XPR");
            System.out.println(Arrays.toString(error_keys));
            if (!error_keys.equals("NONE")){
                new sendErrorToServer().execute((ItemRSS.SERVER_DAILYMEMO_URL+"bugReportPort.php?"+
                        "className=\""+error_keys[0]+"\"&"+
                        "functionName=\""+error_keys[1]+"\"&"+
                        "preferences=\""+error_keys[2]+"\"&"+
                        "deviceName=\""+error_keys[3]+"\"&"+
                        "sdkVersion=\""+error_keys[4]+"\"&"+
                        "appVersion=\""+error_keys[5]+"\"&"+
                        "unixHappen=\""+error_keys[6]+"\"&"+
                        "errorKey=\""+error_keys[7]+"\"&"+
                        "data=\""+reportName+"\""));
                new UploadFileAsync().execute(context.getExternalMediaDirs()[0]+"/"+"honkai_stargazer/bugLog/" +reportName,reportName);
            }
        }else if (sharedPreferences.getString("last_bug_report_mode",LogExport.MODE_SERVER).equals(LogExport.MODE_EMAIL)){
            //EMAIL
            Uri path = FileProvider.getUriForFile(activity, ItemRSS.APPLICATION_ID_PROVIDER,new File(context.getExternalMediaDirs()[0]+"/"+"honkai_stargazer/bugLog/" +reportName));
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("message/rfc822");
            i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"xectorda@gmail.com"});
            i.putExtra(Intent.EXTRA_SUBJECT, "[Honkai Stargazer - BUG REPORT]");
            i.putExtra(Intent.EXTRA_TEXT   , "This is an auto-generate Email from Honkai Stargazer app, with an appendix of bug issue.");
            i.putExtra(Intent.EXTRA_STREAM, path);
            if (i.resolveActivity(activity.getPackageManager()) != null) {
                activity.startActivity(i);
            }

            Toast.makeText(context, context.getString(R.string.bug_report_success), Toast.LENGTH_SHORT).show();
            sharedPreferences.edit().putString("last_bug_report_error_keys","NONE").apply();
            sharedPreferences.edit().putString("last_bug_report","NONE").apply();
        }


    }

    private class sendErrorToServer extends AsyncTask<String,Integer,String> {
        private static final int TIME_OUT = 5000;
        private String status = context.getString(R.string.bug_report_failed);
        String str = "";
        protected void onPreExecute() {
            str = "";
        }
        @Override
        protected String doInBackground(String... url) {
            // TODO Auto-generated method stub
            // 再背景中處理的耗時工作
            System.out.println(Arrays.toString(url));
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url[0]).build();

            try {
                Response sponse = client.newCall(request).execute();
                str = sponse.body().string();
                if (str.contains("INSERT_OK")){
                    status = context.getString(R.string.bug_report_success);
                    context.getSharedPreferences("user_info",Context.MODE_PRIVATE).edit().putString("last_bug_report_error_keys","NONE").apply();
                    context.getSharedPreferences("user_info",Context.MODE_PRIVATE).edit().putString("last_bug_report","NONE").apply();
                } else {
                    status = context.getString(R.string.bug_report_failed);
                    LogExport.bugLog("LogExport","sendErrorToServer.doInBackground", str, "PHP_PYTHON_SQL_ERROR RAISE", context, LogExport.MODE_EMAIL);
                }

            } catch (IOException e) {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                if (sw.toString().contains("INSERT_OK")) return "DONE";
                LogExport.bugLog("LogExport","sendErrorToServer.doInBackground", sw.toString(), e.getMessage(), context, LogExport.MODE_EMAIL);
            }
            return "DONE";
        }



        public void onPostExecute(String result ){
            super.onPreExecute();
            // 背景工作處理完"後"需作的事
            Toast.makeText(context, status, Toast.LENGTH_SHORT).show();

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            // TODO Auto-generated method stub
            super.onProgressUpdate(values);

        }
    }


    //https://stackoverflow.com/questions/25398200/uploading-file-in-php-server-from-android-device
    private class UploadFileAsync extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            try {
                String sourceFileUri = params[0];
                String fileName = params[1];

                HttpURLConnection conn = null;
                DataOutputStream dos = null;
                String lineEnd = "\r\n";
                String twoHyphens = "--";
                String boundary = "*****";
                int bytesRead, bytesAvailable, bufferSize;
                byte[] buffer;
                int maxBufferSize = 1 * 1024 * 1024;
                File sourceFile = new File(sourceFileUri);

                if (sourceFile.isFile()) {

                    try {
                        String upLoadServerUri = ItemRSS.SERVER_DAILYMEMO_URL+"bugReportDataUpload.php";

                        // open a URL connection to the Servlet
                        FileInputStream fileInputStream = new FileInputStream(
                                sourceFile);
                        URL url = new URL(upLoadServerUri);

                        // Open a HTTP connection to the URL
                        conn = (HttpURLConnection) url.openConnection();
                        conn.setDoInput(true); // Allow Inputs
                        conn.setDoOutput(true); // Allow Outputs
                        conn.setUseCaches(false); // Don't use a Cached Copy
                        conn.setRequestMethod("POST");
                        conn.setRequestProperty("Connection", "Keep-Alive");
                        conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                        conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                        conn.setRequestProperty("LogExport", sourceFileUri);

                        dos = new DataOutputStream(conn.getOutputStream());

                        dos.writeBytes(twoHyphens + boundary + lineEnd);
                        dos.writeBytes("Content-Disposition: form-data; name=\"LogExport\";filename=\""
                                + fileName + "\"" + lineEnd);

                        dos.writeBytes(lineEnd);

                        // create a buffer of maximum size
                        bytesAvailable = fileInputStream.available();

                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        buffer = new byte[bufferSize];

                        // read file and write it into form...
                        bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                        while (bytesRead > 0) {

                            dos.write(buffer, 0, bufferSize);
                            bytesAvailable = fileInputStream.available();
                            bufferSize = Math
                                    .min(bytesAvailable, maxBufferSize);
                            bytesRead = fileInputStream.read(buffer, 0,
                                    bufferSize);

                        }

                        // send multipart form data necesssary after file
                        // data...
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + twoHyphens
                                + lineEnd);

                        // Responses from the server (code and message)
                        int serverResponseCode = conn.getResponseCode();
                        String serverResponseMessage = conn
                                .getResponseMessage();

                        System.out.println("serverResponseCode : "+serverResponseCode);
                        System.out.println("serverResponseMessage : "+serverResponseMessage);

                        if (serverResponseCode == 200) {


                            // messageText.setText(msg);
                            //Toast.makeText(ctx, "File Upload Complete.",
                            //      Toast.LENGTH_SHORT).show();

                            // recursiveDelete(mDirectory1);

                        }

                        // close the streams //
                        fileInputStream.close();
                        dos.flush();
                        dos.close();



                    } catch (Exception e) {

                        // dial og.dismiss();
                        e.printStackTrace();

                    }
                    // dialog.dismiss();

                } // End else block


            } catch (Exception ex) {
                // dialog.dismiss();

                ex.printStackTrace();
            }
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
            if (!result.contains("Executed")){
                Toast.makeText(context, context.getString(R.string.bug_report_failed)+" [TXT]", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }
}
