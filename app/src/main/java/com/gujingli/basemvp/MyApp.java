package com.gujingli.basemvp;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.widget.Toast;

import com.gujingli.basemvplibrary.base.activity.BaseActivity;
import com.gujingli.basemvplibrary.utils.UiUtil;

/**
 * Created by gjl on 2017/9/26.
 */

public class MyApp extends MultiDexApplication {
    public static Context uiContext;
    public static int uiThreadId;//主线程的Id;
    public static Handler uiHandler;//主线程的消息

    @Override
    public void onCreate() {
        super.onCreate();
        uiContext = getApplicationContext();
        uiHandler = new Handler();
        uiThreadId = android.os.Process.myPid();
        /**多分解包框架**/
        MultiDex.install(this);
        UiUtil.init(this);
    }

    /**
     * 判断是否在主线程运行
     *
     * @return
     */
    public static boolean isRunOnUiThread() {
        int threadId = android.os.Process.myPid();
        return threadId == uiThreadId;
    }

    /**
     * 保证当前任务在主线程运行
     *
     * @param runnable
     */
    public static void runOnUIThread(Runnable runnable) {
        uiHandler.post(runnable);
    }

    /**
     * 系统吐司
     *
     * @param message
     */
    public static void toast(final String message) {
        if (isRunOnUiThread()) {
            Toast.makeText(uiContext, message, Toast.LENGTH_SHORT).show();
        } else {
            runOnUIThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(uiContext, message, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public static void exit() {
        Intent intent = new Intent(BaseActivity.EXITACTION);
        uiContext.sendBroadcast(intent);
    }
}
