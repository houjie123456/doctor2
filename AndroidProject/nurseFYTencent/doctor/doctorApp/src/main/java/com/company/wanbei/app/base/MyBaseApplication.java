package com.company.wanbei.app.base;

import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import androidx.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.tencent.qcloud.tuikit.tuichat.fromApp.config.C;
import com.company.wanbei.app.receiver.NetStatusReceive;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.ToolSharePerference;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.TypefaceUtil;
import com.mob.MobApplication;
import com.tencent.qcloud.tuicore.TUILogin;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

import static cn.sharesdk.framework.ShareSDK.isDebug;
import static com.company.wanbei.app.tim.signature.GenerateTestUserSig.SDKAPPID;

//import android.support.multidex.MultiDex;


public class MyBaseApplication extends MobApplication {
    private final String TAG = MyBaseApplication.class.getSimpleName();

    private static MyBaseApplication mInstance;

    public static MyBaseApplication the() {
        return mInstance;
    }

    public MyBaseApplication() {
        mInstance = this;
    }
    private List<Activity> oList;//用于存放所有启动的Activity的集合



    //-----------------------------
    private NetStatusReceive wifiReceive;
    private static Context context;

    private static MyBaseApplication instance;
    public static Context instance() {
        return instance;
    }

    @Override
    public void onCreate() {
        Log.d("MyBaseApplication", "[MyBaseApplication] onCreate");
        super.onCreate();

        context = getApplicationContext();
        JPushInterface.init(context);

        if (isDebug()) {           // These two lines must be written before init, otherwise these configurations will be invalid in the init process
            ARouter.openLog();     // Print log
            ARouter.openDebug();   // Turn on debugging mode (If you are running in InstantRun mode, you must turn on debug mode! Online version needs to be closed, otherwise there is a security risk)
        }
        ARouter.init(mInstance); // As early as possible, it is recommended to initialize in the Application

        oList = new ArrayList<Activity>();
        //启动监听网络
        startReceive();
        // 启动文本字体
        TypefaceUtil.initTypefaceUtil(getApplicationContext());
//        TXLiveBase.setConsoleEnabled(true);
//        TXLiveBase.setAppID("1252463788");
//        TXLiveBase.setLogLevel(TXLiveConstants.LOG_LEVEL_DEBUG);


        // 在程序启动的时候初始化 TUI 组件，通常是在 Application 的 onCreate 中进行初始化：
        TUILogin.init(this, SDKAPPID, null, null);

    }




    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }


    private void startReceive(){
        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = manager.getActiveNetworkInfo();
        if(activeInfo != null){
            ToolSharePerference.putBooleanData(getApplicationContext(), C.fileconfig, C.IsNetOK, true);
        }else{
            ToolSharePerference.putBooleanData(getApplicationContext(), C.fileconfig, C.IsNetOK, false);
        }
        wifiReceive = NetStatusReceive.getInstance();
        IntentFilter mFilter = new IntentFilter();
        mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(wifiReceive, mFilter);
    }

    public static Context getContext(){
        return context;
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    /**
     * 添加Activity
     */
    public void addActivity_(Activity activity) {
// 判断当前集合中不存在该Activity
        if (!oList.contains(activity)) {
            oList.add(activity);//把当前Activity添加到集合中
        }
    }

    /**
     * 销毁单个Activity
     */
    public void removeActivity_(Activity activity) {
//判断当前集合中存在该Activity
        if (oList.contains(activity)) {
            oList.remove(activity);//从集合中移除
            activity.finish();//销毁当前Activity
        }
    }

    /**
     * 销毁所有的Activity
     */
    public void removeALLActivity_() {
        //通过循环，把集合中的所有Activity销毁
        for (Activity activity : oList) {
            activity.finish();
        }
    }
}
