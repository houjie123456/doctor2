package com.company.linquan.app.moduleWork.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.company.linquan.app.R;
import com.company.linquan.app.base.BaseActivity;
import com.company.linquan.app.http.JSONBean;
import com.company.linquan.app.moduleAuth.SelectDataActivity;
import com.company.linquan.app.moduleWork.WorkInterface;
import com.company.linquan.app.moduleWork.imp.SettingPicturePresenterImp;
import com.company.linquan.app.util.ExitApp;
import com.company.linquan.app.util.MyDialog;
import com.company.linquan.app.util.MyToast;
import com.company.linquan.app.view.DelEditText;
import com.company.linquan.app.view.MyTextView;

/**
 * Created by YC on 2018/6/13.
 */

public class SettingPictureActivity extends BaseActivity implements WorkInterface.SettingPictureInterface,View.OnClickListener{

    private Dialog myDialog;
    private String timeStr ="",numStr ="";
//    private DelEditText amountET;
    private TextView amountET;
    private MyTextView timeET,numET;
    private SettingPicturePresenterImp presenter;
    public static final int TIME = 7,NUM = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExitApp.getInstance().addActivity(this);		// add Activity 方便退出
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 开启自定义标题栏
        setContentView(R.layout.activity_setting_picture);
        initHead();
        initView();
        getData();
    }

    private void getData() {
        presenter.getPicture();
    }

    private void initHead() {
        RelativeLayout head = (RelativeLayout) findViewById(R.id.head_layout);
        MyTextView title = (MyTextView) head.findViewById(R.id.head_top_title);
        title.setText("设置图文问诊");
        ImageView rightIV = (ImageView)head.findViewById(R.id.head_top_image);
        rightIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView(){
        presenter = new SettingPicturePresenterImp(this);
//        amountET = (DelEditText)findViewById(R.id.setting_picture_money);
        amountET = (TextView) findViewById(R.id.setting_picture_money);
//        amountET.setHint("请输入问诊金额");
        amountET.setText("0");
        amountET.setInputType(InputType.TYPE_CLASS_NUMBER);
        amountET.setGravity(Gravity.RIGHT);
//        amountET.setEnabled(false);//去掉点击时编辑框下面横线:
//        amountET.setFocusable(false);//不可编辑
//        amountET.setFocusableInTouchMode(false);//不可编辑

        timeET = (MyTextView)findViewById(R.id.setting_picture_time);
        timeET.setHint("请输入问诊时长");
        timeET.setGravity(Gravity.RIGHT);
        timeET.setOnClickListener(this);

        numET = (MyTextView)findViewById(R.id.setting_picture_num);
        numET.setHint("请输入问诊条数");
        numET.setGravity(Gravity.RIGHT);
        numET.setOnClickListener(this);

        findViewById(R.id.setting_picture_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.setting_picture_btn:
                if (TextUtils.isEmpty(amountET.getText().toString())) return;
                if (TextUtils.isEmpty(timeStr)) return;
                if (TextUtils.isEmpty(numStr)) return;
                if(timeStr.equals("0")&&numStr.equals("0")){
                    showToast("问诊时长和条数需至少限制一项");
                    return;
                }

//                presenter.setPicture(timeET.getText().toString(),numET.getText().toString(),amountET.getText().toString().trim());
                presenter.setPicture(timeStr,numStr,amountET.getText().toString().trim());
                break;
            case R.id.setting_picture_time:
                Intent timeIntent = new Intent(SettingPictureActivity.this, SelectDataActivity.class);
                timeIntent.putExtra("type",TIME);
                startActivityForResult(timeIntent,TIME);
                break;
            case R.id.setting_picture_num:
                Intent numIntent = new Intent(SettingPictureActivity.this, SelectDataActivity.class);
                numIntent.putExtra("type",NUM);
                startActivityForResult(numIntent,NUM);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TIME && resultCode == RESULT_OK){
            timeStr = data.getStringExtra("id");
            timeET.setText(data.getStringExtra("name"));
        }

        if (requestCode == NUM && resultCode == RESULT_OK){
            numStr = data.getStringExtra("id");
            numET.setText(data.getStringExtra("name"));
        }

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
    public void reloadPicture(JSONBean bean) {
        numStr = bean.getBarNumber();
        if ("0".equals(numStr)) {
            numET.setText("不限");
        }else{
            numET.setText(numStr);
        }


        timeStr = bean.getDayNumber();
        timeET.setText(timeStr);

        amountET.setText(bean.getAmount());
    }
}
