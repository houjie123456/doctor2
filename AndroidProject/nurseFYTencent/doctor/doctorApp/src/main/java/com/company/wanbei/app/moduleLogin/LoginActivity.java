package com.company.wanbei.app.moduleLogin;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.company.wanbei.app.base.BaseActivity;
import com.company.wanbei.app.moduleWeb.WebActivity;
import com.company.wanbei.app.util.ExitApp;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.MyDialog;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.MyToast;
import com.tencent.qcloud.tuikit.tuichat.fromApp.view.MyTextView;
import com.company.wanbei.app.R;

import java.util.Timer;
import java.util.TimerTask;

import static com.tencent.qcloud.tuikit.tuichat.fromApp.config.C.Url_IP;

//import androidx.core.content.ContextCompat;

/**
 * Created by YC on 2018/1/9.
 */

public class LoginActivity extends BaseActivity implements LoginInterface.LoginViewInterface
        ,View.OnClickListener{

    private Dialog myDialog;
    private EditText photoET, codeET;
    private MyTextView codeBtn,loginBtn;
    private CheckBox checkBox;

    private Timer mTimer;
    private int index = 0;

    private LoginPresenter presenter;
    //退出时的时间
    private long mExitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExitApp.getInstance().addActivity(this);		// add Activity 方便退出

        setContentView(R.layout.activity_login);
        initHead();
        initView();
    }

    private void initHead() {
        RelativeLayout head = (RelativeLayout)findViewById(R.id.head_layout);
        head.setBackgroundColor(ContextCompat.getColor(this,R.color.transparent));
        ImageView rightIV = (ImageView)head.findViewById(R.id.head_top_image);
        rightIV.setImageResource(R.drawable.img_close);
        rightIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        TextView titleTV = (MyTextView)head.findViewById(R.id.head_top_title);
        titleTV.setVisibility(View.GONE);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            exit();
            return true;
//                setResult(RESULT_CANCELED);
//                finish();

        }
        return super.onKeyDown(keyCode, event);
    }
    //退出方法
    private void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Toast.makeText(this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            //用户退出处理
//            finish();
//            System.exit(0);
            removeALLActivity();//执行移除所以Activity方法
        }
    }
    private void initView(){
        presenter = new LoginPresenter(this);

        loginBtn = (MyTextView) findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(this);
        codeBtn =  (MyTextView) findViewById(R.id.login_send);
        codeBtn.setOnClickListener(this);
        photoET = (EditText)findViewById(R.id.login_phone);
        photoET.setInputType(InputType.TYPE_CLASS_PHONE);
        codeET = (EditText)findViewById(R.id.login_code);

        checkBox = findViewById(R.id.check_secret);
        findViewById(R.id.secret_txt).setOnClickListener(this);
        findViewById(R.id.serve_txt).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_send:
                if ("".equals(photoET.getText().toString().trim())){
                    showToast("请输入手机号码");
                    return;
                }
                presenter.getCode(photoET.getText().toString().trim());
                break;
            case R.id.login_btn:
                if ("".equals(photoET.getText().toString().trim())){
                    showToast("请输入手机号码");
                    return;
                }
                if ("".equals(codeET.getText().toString().trim())) {
                    showToast("请输入验证码");
                    return;
                }
                if (!checkBox.isChecked()) {
                    showToast("请同意隐私政策和用户服务协议");
                    return;
                }
                String versionNo="";
                PackageManager manager = getContext().getPackageManager();
                try {
                    PackageInfo info = manager.getPackageInfo(getContext().getPackageName(), 0);
                    versionNo = info.versionName;
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }

                presenter.login(photoET.getText().toString().trim(),codeET.getText().toString().trim()
                ,"","","",versionNo,"");
//                presenter.login(photoET.getText().toString().trim(),codeET.getText().toString().trim()
//                        ,"","","","");
                break;

            case R.id.secret_txt:
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, WebActivity.class);
                Bundle b = new Bundle();
                b.putString("title","隐私政策");
                b.putString("url",Url_IP+"/doctorProject/pages/agreement3.html");
                intent.putExtras(b);
                startActivity(intent);
                break;
            case R.id.serve_txt:
                Intent intent2 = new Intent();
                intent2.setClass(LoginActivity.this, WebActivity.class);
                Bundle b2 = new Bundle();
                b2.putString("title","用户服务协议");
                b2.putString("url",Url_IP+"/doctorProject/pages/agreement2.html");
                intent2.putExtras(b2);
                startActivity(intent2);
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
                int sw = LoginActivity.this.getResources().getDisplayMetrics().widthPixels;
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
    protected void onDestroy() {
        if (mTimer != null) mTimer.cancel();
        super.onDestroy();
    }
}
