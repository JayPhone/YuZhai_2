package com.yuzhai.yuzhaiwork_2.base.global;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Process;
import android.util.Log;

import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.mipush.sdk.Logger;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.util.List;

import cn.bmob.newsmssdk.BmobSMS;

/**
 * Created by 35429 on 2017/6/1.
 */

public class CustomApplication extends Application {
    public static final boolean isConnect = false;
    private boolean mIoginState = false;
    //小米推送服务
    public static final String MI_APP_ID = "2882303761517584267";
    public static final String MI_APP_KEY = "5381758485267";
    public static final String TAG = "com.yuzhai.yuzhaiwork_2";
    public static final String BOMB_APP_ID = "d6c96dccb6148cc1b83b00b9676d6e43";

    //Application的实例
    private static CustomApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

        //初始化短信接口
        BmobSMS.initialize(getApplicationContext(), BOMB_APP_ID);

        // 注册push服务，注册成功后会向MiPushMessageReceiver发送广播
        // 可以从MiPushMessageReceiver的onCommandResult方法中MiPushCommandMessage对象参数中获取注册信息
        if (shouldInit()) {
            MiPushClient.registerPush(this, MI_APP_ID, MI_APP_KEY);
        }

        LoggerInterface newLogger = new LoggerInterface() {
            @Override
            public void setTag(String tag) {
                // ignore
            }

            @Override
            public void log(String content, Throwable t) {
                Log.d(TAG, content, t);
            }

            @Override
            public void log(String content) {
                Log.d(TAG, content);
            }
        };
        Logger.setLogger(this, newLogger);
    }

    public static CustomApplication getInstance() {
        return sInstance;
    }

    public boolean isLogin() {
        return mIoginState;
    }

    public void setIoginState(boolean loginState) {
        this.mIoginState = loginState;
    }

    //通过判断手机里的所有进程是否有这个App的进程
    //从而判断该App是否有打开
    private boolean shouldInit() {
        //通过ActivityManager我们可以获得系统里正在运行的activities
        //包括进程(Process)等、应用程序/包、服务(Service)、任务(Task)信息。
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();

        //获取本App的唯一标识
        int myPid = Process.myPid();
        //利用一个增强for循环取出手机里的所有进程
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            //通过比较进程的唯一标识和包名判断进程里是否存在该App
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }
}
