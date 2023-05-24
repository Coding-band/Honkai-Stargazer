/*
 * Project Honkai Stargazer (崩壞•星穹觀星者) was
 * Created & Develop by Voc-夜芷冰 , Programmer of Xectorda
 * Copyright © 2023 Xectorda 版權所有
 */

package com.voc.honkai_stargazer.util;

import static com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE;

import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;

public class UpdateUtil {
    private static final int REQUEST_CODE_UPDATE = 2048;

    //https://juejin.cn/post/6844903846448398343
    public void init(Context context, Activity activity){
        AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(context);
        Task<AppUpdateInfo> appUpdateInfo = appUpdateManager.getAppUpdateInfo();
        appUpdateInfo.addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(Task task) {
                if (task.isSuccessful()) {
                    // 监听成功，不一定检测到更新
                    AppUpdateInfo it = (AppUpdateInfo)task.getResult();
                    if (it.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                            && it.isUpdateTypeAllowed(IMMEDIATE)) { // 检测到更新可用且支持即时更新
                        try {
                            // 启动即时更新
                            appUpdateManager.startUpdateFlowForResult(it, IMMEDIATE, activity, REQUEST_CODE_UPDATE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    // 监听失败
                }
            }
        });
    }

}
