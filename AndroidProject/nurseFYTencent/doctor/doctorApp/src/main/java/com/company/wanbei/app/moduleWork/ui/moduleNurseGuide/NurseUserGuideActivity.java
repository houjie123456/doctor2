package com.company.wanbei.app.moduleWork.ui.moduleNurseGuide;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.company.wanbei.app.R;
import com.company.wanbei.app.base.BaseActivity;
import com.company.wanbei.app.util.ExitApp;
import com.tencent.qcloud.tuikit.tuichat.fromApp.view.MyTextView;

public class NurseUserGuideActivity extends BaseActivity implements View.OnClickListener{

    private LinearLayout downLoadLL,nurseLL,nurseAskLL,messageLL,centerLL;
    private final static int DLD =1;
    private String img1,img2,img3,img4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 开启自定义标题栏
        super.onCreate(savedInstanceState);
        ExitApp.getInstance().addActivity(this);		// add Activity 方便退出
        setContentView(R.layout.activity_user_guide_nurse);
//        initData();
        initHead();
//        initView();
//        setListener();
//        getData();
    }

    private void initHead() {
        RelativeLayout head = (RelativeLayout) findViewById(R.id.head_layout);
        MyTextView title = (MyTextView)head. findViewById(R.id.head_top_title);
        title.setText("使用指南");
        ImageView rightIV = (ImageView)head.findViewById(R.id.head_top_image);
        rightIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        downLoadLL = findViewById(R.id.download_guide);
        nurseLL = findViewById(R.id.nurse_guide);
        nurseAskLL = findViewById(R.id.nurse_ask_guide);
        messageLL = findViewById(R.id.message_guide);
        centerLL = findViewById(R.id.center_guide);
        downLoadLL.setOnClickListener(this);
        nurseLL.setOnClickListener(this);
        nurseAskLL.setOnClickListener(this);
        messageLL.setOnClickListener(this);
        centerLL.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.download_guide:
                Intent intent = new Intent();
                intent.setClass(NurseUserGuideActivity.this, NurseDownloadGuideActivity.class);
                startActivityForResult(intent,DLD);
                break;
            case R.id.nurse_guide:
                Intent intent4 = new Intent();
                intent4.setClass(NurseUserGuideActivity.this, NurseGuideActivity.class);
                startActivity(intent4);
                break;
            case R.id.nurse_ask_guide:
                Intent intent5 = new Intent();
                intent5.setClass(NurseUserGuideActivity.this, NurseAskGuideActivity.class);
                startActivity(intent5);
                break;
            case R.id.message_guide:
                Intent intent6 = new Intent();
                intent6.setClass(NurseUserGuideActivity.this, NurseMessageGuideActivity.class);
                startActivity(intent6);
                break;
            case R.id.center_guide:
                Intent intent7 = new Intent();
                intent7.setClass(NurseUserGuideActivity.this, NurseCenterGuideActivity.class);
                startActivity(intent7);
                break;
        }
    }

}
