package com.company.linquan.app.moduleWork.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.company.linquan.app.R;
import com.company.linquan.app.base.BaseActivity;
import com.company.linquan.app.http.JSONBean;
import com.company.linquan.app.moduleAuth.SelectDataActivity;
import com.company.linquan.app.moduleWork.WorkInterface;
import com.company.linquan.app.moduleWork.imp.SettingFreePresenterImp;
import com.company.linquan.app.util.ExitApp;
import com.company.linquan.app.util.MyDialog;
import com.company.linquan.app.util.MyToast;
import com.company.linquan.app.view.DelEditText;
import com.company.linquan.app.view.MyTextView;

/**
 * Created by YC on 2018/6/13.
 */

public class SettingFreeActivity extends BaseActivity implements WorkInterface.SettingFreeInterface,View.OnClickListener{

    private Dialog myDialog;
    private String manStr ="",numStr ="",isUse="",isUseStr="";
    private DelEditText manET,numET;
    private Switch mSwitch;
//    private MyTextView timeET,numET;
    private SettingFreePresenterImp presenter;
    public static final int TIME = 7,NUM = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExitApp.getInstance().addActivity(this);		// add Activity 方便退出
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 开启自定义标题栏
        setContentView(R.layout.activity_setting_free);
        initHead();
        initSwitch();
        initView();
        getData();
    }

    private void getData() {
        presenter.getFree();
    }

    private void initHead() {
        RelativeLayout head = (RelativeLayout) findViewById(R.id.head_layout);
        MyTextView title = (MyTextView) head.findViewById(R.id.head_top_title);
        title.setText("设置免费义诊");
        ImageView rightIV = (ImageView)head.findViewById(R.id.head_top_image);
        rightIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private  void initSwitch(){
        mSwitch = (Switch) findViewById(R.id.setting_free_if);
        // 添加监听
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    isUse="1";
                }else {
                    isUse="0";
                }
            }
        });
    }
    private void initView(){
        presenter = new SettingFreePresenterImp(this);
        numET = (DelEditText)findViewById(R.id.setting_free_time);
        numET.setHint("请输入问诊次数");
        numET.setGravity(Gravity.RIGHT);

//        timeET = (MyTextView)findViewById(R.id.setting_picture_time);
//        timeET.setHint("请输入问诊时长");
//        timeET.setGravity(Gravity.RIGHT);
//        timeET.setOnClickListener(this);

        manET = (DelEditText)findViewById(R.id.setting_free_people);
        manET.setHint("请输入问诊人数");
        manET.setGravity(Gravity.RIGHT);

//        findViewById(R.id.setting_free_if).setOnClickListener(this);
        findViewById(R.id.setting_free_btn).setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.setting_free_btn:
                if (TextUtils.isEmpty(numET.getText().toString())) return;
                if (TextUtils.isEmpty(manET.getText().toString())) return;

                presenter.setFree(manET.getText().toString().trim(),numET.getText().toString().trim(),isUse);
                break;

//            case R.id.setting_picture_time:
//                Intent timeIntent = new Intent(SettingFreeActivity.this, SelectDataActivity.class);
//                timeIntent.putExtra("type",TIME);
//                startActivityForResult(timeIntent,TIME);
//                break;
            case R.id.setting_free_people:
                Intent numIntent = new Intent(SettingFreeActivity.this, SelectDataActivity.class);
                numIntent.putExtra("type",NUM);
                startActivityForResult(numIntent,NUM);
                break;
            case R.id.setting_free_time:
                Intent timeIntent = new Intent(SettingFreeActivity.this, SelectDataActivity.class);
                timeIntent.putExtra("type",NUM);
                startActivityForResult(timeIntent,NUM);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == TIME && resultCode == RESULT_OK){
//            timeStr = data.getStringExtra("id");
//            timeET.setText(data.getStringExtra("name"));
//        }
//
//        if (requestCode == NUM && resultCode == RESULT_OK){
//            numStr = data.getStringExtra("id");
//            numET.setText(data.getStringExtra("name"));
//        }

        super.onActivityResult(requestCode, resultCode, data);
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
    public void reloadFree(JSONBean bean) {
        numStr = bean.getBarNumber();
        if ("0".equals(numStr)) {
            numET.setText("不限");
        }else{
            numET.setText(numStr+"次");
        }
        isUseStr = bean.getIsUse();
        if ("0".equals(isUseStr)) {
            mSwitch.setChecked(false);
        }else{
            mSwitch.setChecked(true);
        }

        manStr = bean.getManNumber();
        manET.setText(manStr+"人");

    }
}
