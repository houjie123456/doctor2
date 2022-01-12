package com.company.linquan.app.moduleSplash;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.company.linquan.app.R;
import com.company.linquan.app.base.BaseActivity;
import com.company.linquan.app.base.DemoCache;
import com.company.linquan.app.bean.UserInfoBean;
import com.company.linquan.app.config.C;
import com.company.linquan.app.moduleHome.HomeActivity;
import com.company.linquan.app.moduleLogin.LoginActivity;
import com.company.linquan.app.nim.config.preference.UserPreferences;
import com.company.linquan.app.util.ExitApp;
import com.company.linquan.app.util.MyDialog;
import com.company.linquan.app.util.MyToast;
import com.company.linquan.app.util.ToolSharePerference;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.common.ToastHelper;
import com.netease.nim.uikit.common.ui.dialog.DialogMaker;
import com.netease.nim.uikit.common.util.log.LogUtil;
import com.netease.nim.uikit.common.util.string.MD5;
import com.netease.nim.uikit.support.permission.MPermission;
import com.netease.nimlib.sdk.AbortableFuture;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.RequestCallbackWrapper;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.uinfo.UserService;
import com.netease.nimlib.sdk.uinfo.constant.UserInfoFieldEnum;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

/**
 * Created by YC on 2018/1/9.
 */

public class SplashActivity extends BaseActivity implements SplashInterface.SplashViewInterface{

    private Dialog myDialog;
    //----------------AgoraStart---------------------
    private final String TAG = SplashActivity.class.getSimpleName();
    private AbortableFuture<LoginInfo> loginRequest;
    private String appId;
    private int uid;
    private String account;
    private static final int BASIC_PERMISSION_REQUEST_CODE = 100;
    private static final String[] BASIC_PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExitApp.getInstance().addActivity(this);		// add Activity 方便退出
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 开启自定义标题栏
        setContentView(R.layout.activity_splash);
        closeAndroidPDialog();//去除android9.0的弹框
        judgePermission();
        //----------------AgoraStart---------------------
        appId = getString(R.string.agora_app_id);
        // login signaling
        Log.i(TAG, "onClickLogin");
        account = "ju82afm42drhqi1k9xplygrhiyze";
//        Intent intent = new Intent(SplashActivity.this, NumberCallActivity.class);
//        intent.putExtra("account", account);
//        MyBaseApplication.the().getmAgoraAPI().login2(appId, account, "_no_need_token", 1201556999, "", 5, 1);
        //----------------AgoraEnd-----------------------
        requestBasicPermission();

    }
    //----------------AgoraStart-----------------------


    private void requestBasicPermission() {
        MPermission.printMPermissionResult(true, this, BASIC_PERMISSIONS);
        MPermission.with(SplashActivity.this)
                .setRequestCode(BASIC_PERMISSION_REQUEST_CODE)
                .permissions(BASIC_PERMISSIONS)
                .request();
    }


    private void judgePermission() {
        //特殊处理（6.0以上）
//        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_PHONE_STATE)
//                != PackageManager.PERMISSION_GRANTED
//                ||
//                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                        != PackageManager.PERMISSION_GRANTED) {
//            // Check Permissions Now
//            // Callback onRequestPermissionsResult interceptado na Activity MainActivity
//            PermissionGen.with(SplashActivity.this)
//                    .addRequestCode(10)
//                    .permissions(Manifest.permission.READ_PHONE_STATE
//                            , Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                    .request();
//            return;
//        }

        //初始化IMSDK
//        InitBusiness.start(getApplicationContext(),TIMLogLevel.DEBUG.ordinal());


        judgeLogin();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @PermissionSuccess(requestCode = 10)
    public void doSomething(){
        //初始化IMSDK
//        InitBusiness.start(getApplicationContext(),TIMLogLevel.DEBUG.ordinal());
        judgeLogin();
    }

    @PermissionFail(requestCode = 10)
    public void doFailSomething(){
        PermissionGen.with(SplashActivity.this)
                .addRequestCode(10)
                .permissions(Manifest.permission.READ_PHONE_STATE
                        ,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .request();
    }

    private void judgeLogin() {
        SplashPresenter presenter = new SplashPresenter(this);
        if ("".equals(ToolSharePerference.getStringData(SplashActivity.this, C.fileconfig, C.UniqueID))){
            enterActivity();
        }else{
            String versionNo="";
            PackageManager manager = getContext().getPackageManager();
            try {
                PackageInfo info = manager.getPackageInfo(getContext().getPackageName(), 0);
                versionNo = info.versionName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            presenter.login(versionNo);
//            presenter.login();
        }
    }


    private void beforeEnterActivity() {
        login();
    }

    private void enterActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivityForResult(intent,1);
            }
        }, 500);
    }

    @Override
    public void enterHomeActivity() {
        beforeEnterActivity();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK){
            beforeEnterActivity();
//            Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
//            startActivity(intent);
//            finish();
        }
        if (requestCode == 1 && resultCode == RESULT_CANCELED){
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void finishActivity() {
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivityForResult(intent,1);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showDialog() {
        if (myDialog == null) myDialog = MyDialog.showLoadingDialog(this);
        myDialog.show();
    }
    @Override
    public void dismissDialog() {
        if (myDialog != null) myDialog.dismiss();
    }

    @Override
    public void showToast(String txt) {
        MyToast.showToast(this,txt, Toast.LENGTH_SHORT);
    }

    //----------------AgoraStart---------------------
    @Override
    protected void onResume() {

        super.onResume();
//        addCallback();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (myDialog != null && !this.isFinishing()) {
            myDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
//        RtcEngine.destroy();
    }

    //----------------AgoraEnd-----------------------

    private void closeAndroidPDialog(){
        try {
            Class aClass = Class.forName("android.content.pm.PackageParser$Package");
            Constructor declaredConstructor = aClass.getDeclaredConstructor(String.class);
            declaredConstructor.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Class cls = Class.forName("android.app.ActivityThread");
            Method declaredMethod = cls.getDeclaredMethod("currentActivityThread");
            declaredMethod.setAccessible(true);
            Object activityThread = declaredMethod.invoke(null);
            Field mHiddenApiWarningShown = cls.getDeclaredField("mHiddenApiWarningShown");
            mHiddenApiWarningShown.setAccessible(true);
            mHiddenApiWarningShown.setBoolean(activityThread, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ***************************************** 登录 **************************************
     */
    private void login() {
        DialogMaker.showProgressDialog(this, null, getString(R.string.logining), true, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (loginRequest != null) {
                    loginRequest.abort();
                    onLoginDone();
                }
            }
        }).setCanceledOnTouchOutside(false);

        // 云信只提供消息通道，并不包含用户资料逻辑。开发者需要在管理后台或通过服务器接口将用户帐号和token同步到云信服务器。
        // 在这里直接使用同步到云信服务器的帐号和token登录。
        // 这里为了简便起见，demo就直接使用了密码的md5作为token。
        // 如果开发者直接使用这个demo，只更改appkey，然后就登入自己的账户体系的话，需要传入同步到云信服务器的token，而不是用户密码。
//        final String account = loginAccountEdit.getEditableText().toString().toLowerCase();
//        final String token = tokenFromPassword(loginPasswordEdit.getEditableText().toString());

        final String account = UserInfoBean.getInstance().getAccountID();
        final String token = UserInfoBean.getInstance().getToken();
        // 登录
        loginRequest = NimUIKit.login(new LoginInfo(account, token), new RequestCallback<LoginInfo>() {
            @Override
            public void onSuccess(LoginInfo param) {
                LogUtil.i(TAG, "login success");
                onLoginDone();
                DemoCache.setAccount(account);
                saveLoginInfo(account, token);
                // 初始化消息提醒配置
                initNotificationConfig();

                // 进入主界面
//                MainActivity.start(SplashActivity.this, null);
                startActivity(new Intent(SplashActivity.this,HomeActivity.class));
                finish();
            }

            @Override
            public void onFailed(int code) {
                onLoginDone();
                if (code == 302 || code == 404) {
                    ToastHelper.showToast(SplashActivity.this, R.string.login_failed);
                } else {
                    ToastHelper.showToast(SplashActivity.this, "登录失败: " + code);
                }
            }

            @Override
            public void onException(Throwable exception) {
                ToastHelper.showToast(SplashActivity.this, R.string.login_exception);
                onLoginDone();
            }
        });
    }

    private void initNotificationConfig() {
        // 初始化消息提醒
        NIMClient.toggleNotification(UserPreferences.getNotificationToggle());
        // 加载状态栏配置
        StatusBarNotificationConfig statusBarNotificationConfig = UserPreferences.getStatusConfig();
        if (statusBarNotificationConfig == null) {
            statusBarNotificationConfig = DemoCache.getNotificationConfig();
            UserPreferences.setStatusConfig(statusBarNotificationConfig);
        }
        // 更新配置
        NIMClient.updateStatusBarNotificationConfig(statusBarNotificationConfig);
    }

    private void onLoginDone() {
        loginRequest = null;
        DialogMaker.dismissProgressDialog();
    }

    private void saveLoginInfo(final String account, final String token) {
        com.company.linquan.app.nim.config.preference.Preferences.saveUserAccount(account);
        com.company.linquan.app.nim.config.preference.Preferences.saveUserToken(token);
    }

    //DEMO中使用 username 作为 NIM 的account ，md5(password) 作为 token
    //开发者需要根据自己的实际情况配置自身用户系统和 NIM 用户系统的关系
    private String tokenFromPassword(String password) {
        String appKey = readAppKey(this);
        boolean isDemo = "45c6af3c98409b18a84451215d0bdd6e".equals(appKey) ||
                "fe416640c8e8a72734219e1847ad2547".equals(appKey);

        return isDemo ? MD5.getStringMD5(password) : password;
    }

    private static String readAppKey(Context context) {
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            if (appInfo != null) {
                return appInfo.metaData.getString("com.netease.nim.appKey");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
