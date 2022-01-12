package com.company.wanbei.app.moduleWork.ui.moduleNurseGuide;

import static com.tencent.qcloud.tuikit.tuichat.fromApp.config.C.Img_Url_IP;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.company.wanbei.app.R;
import com.company.wanbei.app.base.BaseActivity;
import com.tencent.qcloud.tuikit.tuichat.fromApp.config.C;
import com.company.wanbei.app.moduleWork.ui.WorkNurseActivity;
import com.company.wanbei.app.util.ExitApp;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.MyToast;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.ToolSharePerference;
import com.tencent.qcloud.tuikit.tuichat.fromApp.view.MyTextView;

import cc.shinichi.library.ImagePreview;


public class NurseGuideActivity extends BaseActivity implements View.OnClickListener{

    private MyTextView workNurseTV;
    private ImageView nurseIV1,nurseIV2,nurseIV3,nurseIV4,nurseIV5,nurseIV6,nurseIV7,nurseIV8,nurseIV9,nurseIV10
            ,nurseIV11,nurseIV12,nurseIV13;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 开启自定义标题栏
        super.onCreate(savedInstanceState);
        ExitApp.getInstance().addActivity(this);		// add Activity 方便退出
        setContentView(R.layout.activity_guide_nurse);
//        initData();
        initHead();
//        initView();
//        setListener();
//        getData();
    }

    private void initHead() {
        RelativeLayout head = (RelativeLayout) findViewById(R.id.head_layout);
        MyTextView title = (MyTextView)head. findViewById(R.id.head_top_title);
        title.setText("护理管理指南");
        ImageView rightIV = (ImageView)head.findViewById(R.id.head_top_image);
        rightIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        workNurseTV = findViewById(R.id.work_nurse_tv);
        nurseIV1 = findViewById(R.id.nurse_img1);
//        Picasso.get().load(Img_Url_IP+"nurse_img1.png").into(nurseIV1);
        nurseIV2 = findViewById(R.id.nurse_sc1);
//        Picasso.get().load(Img_Url_IP+"nurse_sc1.png").into(nurseIV2);
        nurseIV3 = findViewById(R.id.nurse_sc2);
//        Picasso.get().load(Img_Url_IP+"nurse_sc2.png").into(nurseIV3);
        nurseIV4 = findViewById(R.id.nurse_sc3);
//        Picasso.get().load(Img_Url_IP+"nurse_sc3.png").into(nurseIV4);
        nurseIV5 = findViewById(R.id.nurse_img2);
//        Picasso.get().load(Img_Url_IP+"nurse_img2.png").into(nurseIV5);
        nurseIV6 = findViewById(R.id.nurse_sc4);
//        Picasso.get().load(Img_Url_IP+"nurse_sc4.png").into(nurseIV6);
        nurseIV7 = findViewById(R.id.nurse_img3);
//        Picasso.get().load(Img_Url_IP+"nurse_img3.png").into(nurseIV7);
        nurseIV8 = findViewById(R.id.nurse_sc5);
//        Picasso.get().load(Img_Url_IP+"nurse_sc5.png").into(nurseIV8);
        nurseIV9 = findViewById(R.id.nurse_sc6);
//        Picasso.get().load(Img_Url_IP+"nurse_sc6.png").into(nurseIV9);
        nurseIV10 = findViewById(R.id.nurse_sc7);
//        Picasso.get().load(Img_Url_IP+"nurse_sc7.png").into(nurseIV10);
        nurseIV11 = findViewById(R.id.nurse_img4);
//        Picasso.get().load(Img_Url_IP+"nurse_img4.png").into(nurseIV11);
        nurseIV12 = findViewById(R.id.nurse_sc8);
//        Picasso.get().load(Img_Url_IP+"nurse_sc8.png").into(nurseIV12);
        nurseIV13 = findViewById(R.id.nurse_sc9);
//        Picasso.get().load(Img_Url_IP+"nurse_sc9.png").into(nurseIV13);

        Glide.with(this).load(Img_Url_IP+"NurseGuide/nurse_img1.png").into(nurseIV1);
        Glide.with(this).load(Img_Url_IP+"NurseGuide/nurse_sc1.png").into(nurseIV2);
        Glide.with(this).load(Img_Url_IP+"NurseGuide/nurse_sc2.png").into(nurseIV3);
        Glide.with(this).load(Img_Url_IP+"NurseGuide/nurse_sc3.png").into(nurseIV4);
        Glide.with(this).load(Img_Url_IP+"NurseGuide/nurse_img2.png").into(nurseIV5);
        Glide.with(this).load(Img_Url_IP+"NurseGuide/nurse_sc4.png").into(nurseIV6);
        Glide.with(this).load(Img_Url_IP+"NurseGuide/nurse_img3.png").into(nurseIV7);
        Glide.with(this).load(Img_Url_IP+"NurseGuide/nurse_sc5.png").into(nurseIV8);
        Glide.with(this).load(Img_Url_IP+"NurseGuide/nurse_sc6.png").into(nurseIV9);
        Glide.with(this).load(Img_Url_IP+"NurseGuide/nurse_sc7.png").into(nurseIV10);
        Glide.with(this).load(Img_Url_IP+"NurseGuide/nurse_img4.png").into(nurseIV11);
        Glide.with(this).load(Img_Url_IP+"NurseGuide/nurse_sc8.png").into(nurseIV12);
        Glide.with(this).load(Img_Url_IP+"NurseGuide/nurse_sc9.png").into(nurseIV13);

        workNurseTV.setOnClickListener(this);
        nurseIV1.setOnClickListener(this);
        nurseIV2.setOnClickListener(this);
        nurseIV3.setOnClickListener(this);
        nurseIV4.setOnClickListener(this);
        nurseIV5.setOnClickListener(this);
        nurseIV6.setOnClickListener(this);
        nurseIV7.setOnClickListener(this);
        nurseIV8.setOnClickListener(this);
        nurseIV9.setOnClickListener(this);
        nurseIV10.setOnClickListener(this);
        nurseIV11.setOnClickListener(this);
        nurseIV12.setOnClickListener(this);
        nurseIV13.setOnClickListener(this);
    }
    private String getMipmapToUri(int resId) {
        Resources r = getResources();
        Uri uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                + r.getResourcePackageName(resId) + "/"
                + r.getResourceTypeName(resId) + "/"
                + r.getResourceEntryName(resId));

        return uri.toString();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.work_nurse_tv:
                if (!"1".equals(ToolSharePerference.getStringData(NurseGuideActivity.this, C.fileconfig, C.AUTH))){
                    MyToast.showToast(NurseGuideActivity.this,"请先进行实名认证", Toast.LENGTH_LONG);
                }else {
                    startActivity(new Intent(NurseGuideActivity.this, WorkNurseActivity.class));
                }
                break;
            case R.id.nurse_img1:
                ImagePreview.getInstance().setContext(NurseGuideActivity.this).setIndex(0).setImage(Img_Url_IP+"NurseGuide/nurse_img1.png").start();
                break;
            case R.id.nurse_sc1:
                ImagePreview.getInstance().setContext(NurseGuideActivity.this).setIndex(0).setImage(Img_Url_IP+"NurseGuide/nurse_sc1.png").start();
                break;
            case R.id.nurse_sc2:
                ImagePreview.getInstance().setContext(NurseGuideActivity.this).setIndex(0).setImage(Img_Url_IP+"NurseGuide/nurse_sc2.png").start();
                break;
            case R.id.nurse_sc3:
                ImagePreview.getInstance().setContext(NurseGuideActivity.this).setIndex(0).setImage(Img_Url_IP+"NurseGuide/nurse_sc3.png").start();
                break;
            case R.id.nurse_img2:
                ImagePreview.getInstance().setContext(NurseGuideActivity.this).setIndex(0).setImage(Img_Url_IP+"NurseGuide/nurse_img2.png").start();
                break;
            case R.id.nurse_sc4:
                ImagePreview.getInstance().setContext(NurseGuideActivity.this).setIndex(0).setImage(Img_Url_IP+"NurseGuide/nurse_sc4.png").start();
                break;
            case R.id.nurse_sc5:
                ImagePreview.getInstance().setContext(NurseGuideActivity.this).setIndex(0).setImage(Img_Url_IP+"NurseGuide/nurse_sc5.png").start();
                break;
            case R.id.nurse_sc6:
                ImagePreview.getInstance().setContext(NurseGuideActivity.this).setIndex(0).setImage(Img_Url_IP+"NurseGuide/nurse_sc6.png").start();
                break;
            case R.id.nurse_img3:
                ImagePreview.getInstance().setContext(NurseGuideActivity.this).setIndex(0).setImage(Img_Url_IP+"NurseGuide/nurse_img3.png").start();
                break;
            case R.id.nurse_sc7:
                ImagePreview.getInstance().setContext(NurseGuideActivity.this).setIndex(0).setImage(Img_Url_IP+"NurseGuide/nurse_sc7.png").start();
                break;
            case R.id.nurse_sc8:
                ImagePreview.getInstance().setContext(NurseGuideActivity.this).setIndex(0).setImage(Img_Url_IP+"NurseGuide/nurse_sc8.png").start();
                break;
            case R.id.nurse_img4:
                ImagePreview.getInstance().setContext(NurseGuideActivity.this).setIndex(0).setImage(Img_Url_IP+"NurseGuide/nurse_img4.png").start();
                break;
            case R.id.nurse_sc9:
                ImagePreview.getInstance().setContext(NurseGuideActivity.this).setIndex(0).setImage(Img_Url_IP+"NurseGuide/nurse_sc9.png").start();
                break;
        }
    }
}
