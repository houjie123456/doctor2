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

import com.bumptech.glide.Glide;
import com.company.wanbei.app.R;
import com.company.wanbei.app.base.BaseActivity;
import com.company.wanbei.app.moduleHome.HomeActivity;
import com.company.wanbei.app.util.ExitApp;
import com.tencent.qcloud.tuikit.tuichat.fromApp.view.MyTextView;

import cc.shinichi.library.ImagePreview;


public class NurseCenterGuideActivity extends BaseActivity implements View.OnClickListener{

    private MyTextView workCenterTV;
    private ImageView centerIV1,centerIV2,centerIV3,centerIV4,centerIV5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 开启自定义标题栏
        super.onCreate(savedInstanceState);
        ExitApp.getInstance().addActivity(this);		// add Activity 方便退出
        setContentView(R.layout.activity_guide_center_nurse);
//        initData();
        initHead();
//        initView();
//        setListener();
//        getData();
    }

    private void initHead() {
        RelativeLayout head = (RelativeLayout) findViewById(R.id.head_layout);
        MyTextView title = (MyTextView)head. findViewById(R.id.head_top_title);
        title.setText("个人中心指南");
        ImageView rightIV = (ImageView)head.findViewById(R.id.head_top_image);
        rightIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        workCenterTV = findViewById(R.id.work_center_tv);
        centerIV1 = findViewById(R.id.center_img1);
//        Picasso.get().load(Img_Url_IP+"center_img1.png").into(centerIV1);
        centerIV2 = findViewById(R.id.center_img2);
//        Picasso.get().load(Img_Url_IP+"download_sc1.jpg").into(centerIV2);
        centerIV3 = findViewById(R.id.center_img3);
//        Picasso.get().load(Img_Url_IP+"center_nurse_img3.png").into(centerIV3);
        centerIV4 = findViewById(R.id.center_sc1);
//        Picasso.get().load(Img_Url_IP+"center_nurse_sc1.jpg").into(centerIV4);
        centerIV5 = findViewById(R.id.center_img4);
//        Picasso.get().load(Img_Url_IP+"center_nurse_img4.png").into(centerIV5);

        Glide.with(this).load(Img_Url_IP+"NurseCenterGuide/center_nurse_img1.png").into(centerIV1);
        Glide.with(this).load(Img_Url_IP+"NurseCenterGuide/download_sc1.jpg").into(centerIV2);
        Glide.with(this).load(Img_Url_IP+"NurseCenterGuide/center_nurse_img3.png").into(centerIV3);
        Glide.with(this).load(Img_Url_IP+"NurseCenterGuide/center_nurse_sc1.jpg").into(centerIV4);
        Glide.with(this).load(Img_Url_IP+"NurseCenterGuide/center_nurse_img4.png").into(centerIV5);

        workCenterTV.setOnClickListener(this);
        centerIV1.setOnClickListener(this);
        centerIV2.setOnClickListener(this);
        centerIV3.setOnClickListener(this);
        centerIV4.setOnClickListener(this);
        centerIV5.setOnClickListener(this);
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
            case R.id.work_center_tv:
                Intent intent2 = new Intent();
                intent2.putExtra("index",2);
                intent2.setClass(NurseCenterGuideActivity.this, HomeActivity.class);
                startActivity(intent2);
                break;
            case R.id.center_img1:
                ImagePreview.getInstance().setContext(NurseCenterGuideActivity.this).setIndex(0).setImage(Img_Url_IP+"NurseCenterGuide/center_nurse_img1.png").start();
                break;
            case R.id.center_sc1:
                ImagePreview.getInstance().setContext(NurseCenterGuideActivity.this).setIndex(0).setImage(Img_Url_IP+"NurseCenterGuide/center_nurse_sc1.jpg").start();
                break;
            case R.id.center_img2:
                ImagePreview.getInstance().setContext(NurseCenterGuideActivity.this).setIndex(0).setImage(Img_Url_IP+"NurseCenterGuide/download_sc1.jpg").start();
                break;
            case R.id.center_img3:
                ImagePreview.getInstance().setContext(NurseCenterGuideActivity.this).setIndex(0).setImage(Img_Url_IP+"NurseCenterGuide/center_nurse_img3.png").start();
                break;
            case R.id.center_img4:
                ImagePreview.getInstance().setContext(NurseCenterGuideActivity.this).setIndex(0).setImage(Img_Url_IP+"NurseCenterGuide/center_nurse_img4.png").start();
                break;

        }
    }
}
