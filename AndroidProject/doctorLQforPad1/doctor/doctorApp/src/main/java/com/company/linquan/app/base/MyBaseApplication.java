package com.company.linquan.app.base;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.util.Log;

import com.company.linquan.app.R;
import com.company.linquan.app.config.C;
import com.company.linquan.app.nim.config.preference.Preferences;
import com.company.linquan.app.nim.helper.SessionHelper;
import com.company.linquan.app.nim.session.CustomAttachParser;
import com.company.linquan.app.nim.session.MsgViewHolderCustom;
import com.company.linquan.app.nim.session.PatCardAttachment;
import com.company.linquan.app.receiver.NetStatusReceive;
import com.company.linquan.app.util.ToolSharePerference;
import com.company.linquan.app.util.TypefaceUtil;
import com.mob.MobApplication;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.api.UIKitOptions;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.util.NIMUtil;

import java.io.IOException;




public class MyBaseApplication extends MobApplication {
    private final String TAG = MyBaseApplication.class.getSimpleName();

    private static MyBaseApplication mInstance;

    public static MyBaseApplication the() {
        return mInstance;
    }

    public MyBaseApplication() {
        mInstance = this;
    }




    //-----------------------------
    private NetStatusReceive wifiReceive;
    private static Context context;
    @Override
    public void onCreate() {
        Log.d("MyBaseApplication", "[MyBaseApplication] onCreate");
        super.onCreate();
        context = getApplicationContext();
        //启动监听网络
        startReceive();
        // 启动文本字体
        TypefaceUtil.initTypefaceUtil(getApplicationContext());
//        TXLiveBase.setConsoleEnabled(true);
//        TXLiveBase.setAppID("1252463788");
//        TXLiveBase.setLogLevel(TXLiveConstants.LOG_LEVEL_DEBUG);
        // 4.6.0 开始，第三方推送配置入口改为 SDKOption#mixPushConfig，旧版配置方式依旧支持。
        DemoCache.setContext(this);
        NIMClient.init(this, getLoginInfo(), NimSDKOptionConfig.getSDKOptions(this));

        if (NIMUtil.isMainProcess(this)) {
            // 在主进程中初始化UI组件，判断所属进程方法请参见demo源码。
            initUIKit();

        }

    }

    private LoginInfo getLoginInfo() {
        String account = Preferences.getUserAccount();
        String token = Preferences.getUserToken();

        if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(token)) {
            DemoCache.setAccount(account.toLowerCase());
            return new LoginInfo(account, token);
        } else {
            return null;
        }
    }

    private void initUIKit() {
        // 初始化
        NimUIKit.init(this, buildUIKitOptions());
//        //将该附件解析器注册到 SDK 中。为了保证生成历史消息时能够正确解析自定义附件，注册一般应放在 Application 的 onCreate 中完成
//        NIMClient.getService(MsgService.class).registerCustomAttachmentParser(new CustomAttachParser());
//        //第八步，注册扩展消息类型的显示ViewHolder，由于这里使用我们UIKIT，所以也需要注册到Application的onCreate中
//        NimUIKit.registerMsgItemViewHolder(PatCardAttachment.class, MsgViewHolderCustom.class);

    }

    private UIKitOptions buildUIKitOptions() {
        UIKitOptions options = new UIKitOptions();
        // 设置app图片/音频/日志等缓存目录
        options.appCacheDir = NimSDKOptionConfig.getAppCacheDir(this) + "/app";
        return options;
    }


    /**
     * 配置 APP 保存图片/语音/文件/log等数据的目录
     * 这里示例用SD卡的应用扩展存储目录
     */
    static String getAppCacheDir(Context context) {
        String storageRootPath = null;
        try {
            // SD卡应用扩展存储区(APP卸载后，该目录下被清除，用户也可以在设置界面中手动清除)，请根据APP对数据缓存的重要性及生命周期来决定是否采用此缓存目录.
            // 该存储区在API 19以上不需要写权限，即可配置 <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" android:maxSdkVersion="18"/>
            if (context.getExternalCacheDir() != null) {
                storageRootPath = context.getExternalCacheDir().getCanonicalPath();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (TextUtils.isEmpty(storageRootPath)) {
            // SD卡应用公共存储区(APP卸载后，该目录不会被清除，下载安装APP后，缓存数据依然可以被加载。SDK默认使用此目录)，该存储区域需要写权限!
            storageRootPath = Environment.getExternalStorageDirectory() + "/" + DemoCache.getContext().getPackageName();
        }

        return storageRootPath;
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


}
