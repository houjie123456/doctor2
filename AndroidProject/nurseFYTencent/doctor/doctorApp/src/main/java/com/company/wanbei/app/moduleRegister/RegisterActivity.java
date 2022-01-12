package com.company.wanbei.app.moduleRegister;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.company.wanbei.app.base.BaseActivity;
import com.company.wanbei.app.util.ExitApp;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.MyDialog;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.MyToast;
import com.tencent.qcloud.tuikit.tuichat.fromApp.view.MyTextView;
import com.company.wanbei.app.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by YC on 2018/1/9.
 */

public class RegisterActivity extends BaseActivity implements RegisterInterface.RegisterViewInterface
,View.OnClickListener{


    private Dialog myDialog;
    private EditText photoET, codeET;
    private MyTextView codeBtn,loginBtn;

    private Timer mTimer;
    private int index = 0;

    private RegisterPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExitApp.getInstance().addActivity(this);		// add Activity 方便退出
//        requestWindowFeature(Window.FEATURE_NO_TITLE); // 开启自定义标题栏
        setContentView(R.layout.activity_resgister);
        initHead();
        initView();
    }

    private void initHead() {
        RelativeLayout head = (RelativeLayout)findViewById(R.id.head_layout);
        TextView titleTV = (MyTextView)head.findViewById(R.id.head_top_title);
        titleTV.setVisibility(View.GONE);
        ImageView rightIV = (ImageView)head.findViewById(R.id.head_top_image);
        rightIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView(){
        presenter = new RegisterPresenter(this);

        loginBtn = (MyTextView) findViewById(R.id.register_btn);
        loginBtn.setOnClickListener(this);
        codeBtn =  (MyTextView) findViewById(R.id.register_send);
        codeBtn.setOnClickListener(this);
        photoET = (EditText)findViewById(R.id.register_phone);
        codeET = (EditText)findViewById(R.id.register_code);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_send:
                if ("".equals(photoET.getText().toString().trim())) return;
                presenter.getCode(photoET.getText().toString().trim());
                break;
            case R.id.login_btn:
                if ("".equals(photoET.getText().toString().trim())) return;
                if ("".equals(codeET.getText().toString().trim())) return;
                String versionNo="";
                PackageManager manager = getContext().getPackageManager();
                try {
                    PackageInfo info = manager.getPackageInfo(getContext().getPackageName(), 0);
                    versionNo = info.versionName;
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                presenter.login(photoET.getText().toString().trim(),codeET.getText().toString().trim()
                        ,"","","","",versionNo);
//                presenter.login(photoET.getText().toString().trim(),codeET.getText().toString().trim()
//                        ,"","","","");
                break;
        }
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 0){
                mTimer.cancel();
                index = 0;
                codeBtn.setEnabled(true);
                codeBtn.setText("获取验证码");
                codeBtn.invalidate();
            }else if(msg.what > 0){
                codeBtn.setEnabled(false);
                codeBtn.setText(msg.what+"s后可重发");
                codeBtn.invalidate();
            }else if (msg.what == -1){
                loginBtn.setText("绑定手机号码并验证");
                int sw = getResources().getDisplayMetrics().widthPixels;
                int w = (sw*174)/720;
//                photoIV.setDrawCircle();
//                Glide.with(LoginActivity.this).load(msg.obj.toString()).override(w,w).centerCrop().into(photoIV);
//                bottomLayout.setVisibility(View.GONE);
//                iconIV.setVisibility(View.GONE);
//                photoIV.setVisibility(View.VISIBLE);
            }else if (msg.what == -2){
                loginBtn.setText("验证手机号码");
//                bottomLayout.setVisibility(View.VISIBLE);
//                iconIV.setVisibility(View.VISIBLE);
//                photoIV.setVisibility(View.GONE);
            }else if (msg.what == -3){
//                presenter.checkLogin(userInfo.getOpenId(),"2",token);
            }else if (msg.what == -4){
//                presenter.checkLogin(userInfo.getOpenId(),"3",token);
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public void finishActivity() {
        Intent intent = new Intent();
        setResult(RESULT_OK,intent);
        finish();
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

    @Override
    public void startTimer() {
        mTimer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                index++;
                Message message = new Message();
                message.what = 60 - index;
                handler.sendMessage(message);
            }
        };
        mTimer.schedule(task, 0, 1000);
    }
}
