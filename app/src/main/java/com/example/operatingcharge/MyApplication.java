package com.example.operatingcharge;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.os.Environment;
import android.os.Vibrator;
import android.support.v7.app.AppCompatDelegate;

import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * Author : 赵彬彬
 * Date   : 2019/6/17
 * Time   : 23:05
 * Desc   :
 */
public class MyApplication extends Application {

    public static String SDPATH;
    public Vibrator mVibrator;
    private static Context context;

    static {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        x.Ext.init(this);
        x.Ext.setDebug(true);
        instance = this;
//      百度地图初始化
        mVibrator = (Vibrator) getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
        SDPATH = getDiskCacheDir(getBaseContext()) + File.separator;
//      初始化sdk
        //JPushInterface.setDebugMode(true);  //  正式版的时候设置false，关闭调试
        //JPushInterface.init(this);
//      设置保留最近通知条数
        //JPushInterface.setLatestNotificationNumber(context, 30);
    }

    public static Context getContext() {
        return context;
    }

    public String getDiskCacheDir(Context context) {
        String cachePath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ||
                !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();//mounted 安装好的
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return cachePath;
    }

    /**
     * 打开的activity
     **/
    private List<Activity> activities = new ArrayList<Activity>();
    /**
     * 应用实例
     **/
    private static MyApplication instance;

    /**
     * 获得实例
     *
     * @return
     */
    public static MyApplication getInstance() {
        return instance;
    }

    /**
     * 新建了一个activity
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    /**
     * 结束指定的Activity
     *
     * @param activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            this.activities.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 应用退出，结束所有的activity
     */
    public void exit() {

        for (Activity activity : activities) {
            if (activity != null) {
                activity.finish();
            }
        }
        System.exit(0);
    }
}
