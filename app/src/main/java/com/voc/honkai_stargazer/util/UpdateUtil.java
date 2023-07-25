/*
 * Project Honkai Stargazer (崩壞•星穹觀星者) was
 * Created & Develop by Voc-夜芷冰 , Programmer of Xectorda
 * Copyright © 2023 Xectorda 版權所有
 */

package com.voc.honkai_stargazer.util;

import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.util.Log;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.voc.honkai_stargazer.R;

import java.io.PrintWriter;
import java.io.StringWriter;

public class UpdateUtil {
    public static final int REQUEST_CODE_UPDATE = 2048;
    private static final String TAG = "UpdateUtil";
    private Context context;
    private Activity activity;

    AppUpdateManager appUpdateManager = null;
    //https://stackoverflow.com/questions/55939853/how-to-work-with-androids-in-app-update-api
    public void init(Context context, Activity activity){
        this.context = context;
        this.activity = activity;
        appUpdateManager = AppUpdateManagerFactory.create(context);
        appUpdateManager.registerListener(installStateUpdatedListener);

        appUpdateManager.getAppUpdateInfo().addOnSuccessListener(appUpdateInfo -> {

            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE /*AppUpdateType.IMMEDIATE*/)){

                try {
                    appUpdateManager.startUpdateFlowForResult(
                            appUpdateInfo, AppUpdateType.FLEXIBLE /*AppUpdateType.IMMEDIATE*/, activity, REQUEST_CODE_UPDATE);

                } catch (IntentSender.SendIntentException e) {
                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    e.printStackTrace(pw);
                    LogExport.bugLog(TAG, "appUpdateManager -> AppUpdateType.FLEXIBLE", sw.toString(), e.getMessage(), context);
                }

            } else if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)){

                try {
                    appUpdateManager.startUpdateFlowForResult(
                            appUpdateInfo, AppUpdateType.IMMEDIATE, activity, REQUEST_CODE_UPDATE);

                } catch (IntentSender.SendIntentException e) {
                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    e.printStackTrace(pw);
                    LogExport.bugLog(TAG, "appUpdateManager -> AppUpdateType.IMMEDIATE", sw.toString(), e.getMessage(), context);
                }

            } else if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED){
                //CHECK THIS if AppUpdateType.FLEXIBLE, otherwise you can skip
                popupSnackbarForCompleteUpdate();
            } else {
                LogExport.export(TAG, "appUpdateManager -> OTHER_CASE", "OTHER_CASE", context, LogExport.BETA_TESTING);
            }
        });

    }

    public AppUpdateManager getAppUpdateManager (){
        return appUpdateManager;
    }

    public void unregister(){
        if (appUpdateManager != null && installStateUpdatedListener != null){
            appUpdateManager.unregisterListener(installStateUpdatedListener);
        }
    }

    InstallStateUpdatedListener installStateUpdatedListener = new
            InstallStateUpdatedListener() {
                @Override
                public void onStateUpdate(InstallState state) {
                    if (state.installStatus() == InstallStatus.DOWNLOADED){
                        //CHECK THIS if AppUpdateType.FLEXIBLE, otherwise you can skip
                        popupSnackbarForCompleteUpdate();
                    } else if (state.installStatus() == InstallStatus.INSTALLED){
                        if (appUpdateManager != null){
                            appUpdateManager.unregisterListener(installStateUpdatedListener);
                        }

                    } else {
                        Log.i(TAG, "InstallStateUpdatedListener: state: " + state.installStatus());
                    }
                }
            };

    private void popupSnackbarForCompleteUpdate() {
        Snackbar snackbar =
                Snackbar.make(
                        activity.findViewById((activity.findViewById(R.id.rootView_home) == null ? R.id.rootView_home_settings : R.id.rootView_home)),
                        "New app is ready!",
                        Snackbar.LENGTH_INDEFINITE);

        snackbar.setAction("Install", view -> {
            if (appUpdateManager != null){
                appUpdateManager.completeUpdate();
            }
        });

        ThemeUtil themeUtil = new ThemeUtil(context,activity);
        snackbar.setActionTextColor(themeUtil.themeColorExport());
        snackbar.show();
    }



}
