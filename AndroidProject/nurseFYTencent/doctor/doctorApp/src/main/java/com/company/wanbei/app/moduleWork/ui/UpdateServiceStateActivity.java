package com.company.wanbei.app.moduleWork.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.company.wanbei.app.R;
import com.company.wanbei.app.base.BaseActivity;
import com.company.wanbei.app.moduleWork.WorkInterface;
import com.company.wanbei.app.moduleWork.imp.UpdateServiceStatePresenterImp;
import com.company.wanbei.app.util.ExitApp;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.MyDialog;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.MyToast;
import com.tencent.qcloud.tuikit.tuichat.fromApp.view.MyTextView;

/**
 * Created by YC on 2018/6/11.
 */

public class UpdateServiceStateActivity extends BaseActivity implements WorkInterface.UpdateServiceStateInterface, View.OnClickListener,RadioGroup.OnCheckedChangeListener {

    private Dialog myDialog;

    private UpdateServiceStatePresenterImp presenter;

    private RadioGroup radioGroup_gender;
    private EditText checkRemarkET;
    private String id;

    int sw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 开启自定义标题栏
        super.onCreate(savedInstanceState);
        ExitApp.getInstance().addActivity(this);		// add Activity 方便退出

        setContentView(R.layout.activity_update_service_state);

        initHead();
        initView();
    }

    private void initHead() {
        RelativeLayout head = (RelativeLayout) findViewById(R.id.head_layout_top);
        MyTextView title = (MyTextView)head. findViewById(R.id.head_top_title);
        title.setText("修改服务状态");
        ImageView rightIV = (ImageView)head.findViewById(R.id.head_top_image);
        rightIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView(){


        checkRemarkET=findViewById(R.id.checkRemark);

        this.radioGroup_gender= (RadioGroup) this.findViewById(R.id.radioGroup_gender);
        this.radioGroup_gender.setOnCheckedChangeListener(this);

        presenter = new UpdateServiceStatePresenterImp(this);


        findViewById(R.id.confirm_btn).setOnClickListener(this);


        RadioButton radioButton0=findViewById(R.id.radioButton0);
        RadioButton radioButton1=findViewById(R.id.radioButton1);
        RadioButton radioButton3=findViewById(R.id.radioButton3);
        if(getIntent().getStringExtra("serviceState").equals("0")){
            radioButton0.setChecked(true);
            radioButton1.setChecked(false);
            radioButton3.setChecked(false);
        }
        if(getIntent().getStringExtra("serviceState").equals("1")){
            radioButton1.setChecked(true);
            radioButton0.setChecked(false);
            radioButton3.setChecked(false);
        }
        if(getIntent().getStringExtra("serviceState").equals("3")){
            radioButton3.setChecked(true);
            radioButton1.setChecked(false);
            radioButton0.setChecked(false);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.confirm_btn:

                presenter.UpdateServiceState(getIntent().getStringExtra("id"),id,checkRemarkET.getText().toString());

                break;
        }
    }



    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        //得到用户选中的 RadioButton 对象
        switch (checkedId){
            case R.id.radioButton0:
                id="0";
                break;
            case R.id.radioButton1:
                id="1";
                break;
            case R.id.radioButton3:
                id="3";
                break;
        }
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

}
