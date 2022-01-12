package com.company.wanbei.app.moduleSplash;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.company.wanbei.app.base.BaseActivity;
import com.company.wanbei.app.bean.UserInfoBean;
import com.tencent.qcloud.tuikit.tuichat.fromApp.config.C;
import com.company.wanbei.app.moduleHome.HomeActivity;
import com.company.wanbei.app.moduleLogin.LoginActivity;
import com.company.wanbei.app.moduleWeb.WebActivity;
import com.company.wanbei.app.util.ExitApp;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.MyToast;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.ToolSharePerference;
import com.company.wanbei.app.util.permission.MPermission;
import com.tencent.qcloud.tuikit.tuichat.fromApp.view.MyTextView;
import com.company.wanbei.app.R;
import com.tencent.imsdk.v2.V2TIMCallback;
import com.tencent.qcloud.tuicore.TUILogin;
import com.tencent.qcloud.tuicore.util.ToastUtil;

import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

import static com.tencent.qcloud.tuikit.tuichat.fromApp.config.C.Url_IP;

//import android.support.annotation.NonNull;
////import android.support.v4.app.ActivityCompat;

/**
 * Created by YC on 2018/1/9.
 */

public class SplashActivity extends BaseActivity implements SplashInterface.SplashViewInterface{

    private Dialog mDialog;
    private View mDialogContentView;
    //----------------AgoraStart---------------------
    private final String TAG = SplashActivity.class.getSimpleName();
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
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CALL_PHONE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExitApp.getInstance().addActivity(this);		// add Activity 方便退出


        setContentView(R.layout.activity_splash);
//        closeAndroidPDialog();//去除android9.0的弹框
        mDialog = new Dialog(this, R.style.custom_dialog);
        mDialogContentView= LayoutInflater.from(this).inflate(R.layout.dialog_secret,null);


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
        judgeLogin();
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

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        MPermission.onRequestPermissionsResult(this, requestCode, permissions, grantResults);

        ToolSharePerference.putBooleanData(this, C.fileconfig, String.valueOf(C.IS_GET_PERMISSION), true);

        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        judgeLogin();

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @PermissionSuccess(requestCode = 10)
    public void doSomething(){
        showToast("成功");
        //初始化IMSDK
//        InitBusiness.start(getApplicationContext(),TIMLogLevel.DEBUG.ordinal());

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
        String s = ToolSharePerference.getStringData(SplashActivity.this, C.fileconfig, C.UniqueID);
        String s2 = ToolSharePerference.getStringData(SplashActivity.this, C.fileconfig, C.ISAGREE);
        if ("".equals(ToolSharePerference.getStringData(SplashActivity.this, C.fileconfig, C.UniqueID))&&"".equals(ToolSharePerference.getStringData(SplashActivity.this, C.fileconfig, C.ISAGREE))){
            showTwoBtnDialog();

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
            boolean b = ToolSharePerference.getBooleanData(SplashActivity.this, C.fileconfig, C.IS_GET_PERMISSION);
            if (ToolSharePerference.getBooleanData(SplashActivity.this, C.fileconfig, C.IS_GET_PERMISSION)){
                judgeLogin();

            }else {
                requestBasicPermission();
            }
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
//        if (myDialog == null) myDialog = MyDialog.showLoadingDialog(this);
//        myDialog.show();
    }
    @Override
    public void dismissDialog() {
//        if (myDialog != null) myDialog.dismiss();
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
//        if (myDialog != null && !this.isFinishing()) {
//            myDialog.dismiss();
//        }
    }

    @Override
    protected void onDestroy() {
        if(mDialog != null) {
            mDialog.dismiss();
        }
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

    //----------------AgoraEnd-----------------------
    /**
     * 提示登录
     */
    public void showTwoBtnDialog(){


        MyTextView cancelTV = (MyTextView)mDialogContentView.findViewById(R.id.dialog_item_cancel);
        MyTextView confirmTV = (MyTextView)mDialogContentView.findViewById(R.id.dialog_item_sure);

        MyTextView serveTV = (MyTextView)mDialogContentView.findViewById(R.id.dialog_item_serve);
        MyTextView secretTV = (MyTextView)mDialogContentView.findViewById(R.id.dialog_item_secret);

        serveTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SplashActivity.this, WebActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("title","用户服务协议");
                bundle.putString("url",Url_IP+"/doctorProject/pages/agreement2.html");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        secretTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SplashActivity.this, WebActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("title","隐私政策");
                bundle.putString("url",Url_IP+"/doctorProject/pages/agreement3.html");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        cancelTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
                finish();
            }
        });

        confirmTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
                ToolSharePerference.putStringData(SplashActivity.this, C.fileconfig, C.ISAGREE,"yes");
                enterActivity();

            }
        });

        mDialog.setContentView(mDialogContentView);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setCancelable(true);
        mDialog.getWindow().setGravity(Gravity.CENTER);
        WindowManager.LayoutParams p = mDialog.getWindow().getAttributes(); // 获取对话框当前的参数值
        p.width = (int) (getResources().getDisplayMetrics().widthPixels * 0.85); // 宽度设置为屏幕的0.85
        mDialog.getWindow().setAttributes(p);
        mDialog.show();
        mDialog.getWindow().setWindowAnimations(R.style.anim_dialog);
    }



    /**
     * ***************************************** 登录 **************************************
     */
    private void login() {
        final String account = UserInfoBean.getInstance().getAccountID();
        final String token = UserInfoBean.getInstance().getToken();
        TUILogin.login(account, token, new V2TIMCallback() {
            @Override
            public void onError(final int code, final String desc) {
                ToastUtil.toastLongMessage("登录失败: " + code + "，" + desc);
            }

            @Override
            public void onSuccess() {
                // 进入主界面
//                MainActivity.start(SplashActivity.this, null);

                startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                finish();//避免返回键两次退出不掉App
            }
        });


    }
}
