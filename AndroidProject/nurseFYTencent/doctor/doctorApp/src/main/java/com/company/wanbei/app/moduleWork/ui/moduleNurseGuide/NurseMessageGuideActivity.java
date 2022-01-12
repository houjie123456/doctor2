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


public class NurseMessageGuideActivity extends BaseActivity implements View.OnClickListener{

    private MyTextView workMessageTV;
    private ImageView messageIV1,messageIV2,messageIV3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 开启自定义标题栏
        super.onCreate(savedInstanceState);
        ExitApp.getInstance().addActivity(this);		// add Activity 方便退出
        setContentView(R.layout.activity_guide_message_nurse);
//        initData();
        initHead();
//        initView();
//        setListener();
//        getData();
    }

    private void initHead() {
        RelativeLayout head = (RelativeLayout) findViewById(R.id.head_layout);
        MyTextView title = (MyTextView)head. findViewById(R.id.head_top_title);
        title.setText("消息指南");
        ImageView rightIV = (ImageView)head.findViewById(R.id.head_top_image);
        rightIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        workMessageTV = findViewById(R.id.work_message_tv);
        messageIV1 = findViewById(R.id.message_img1);
//        Picasso.get().load(Img_Url_IP+"message_image1.png").into(messageIV1);
        messageIV2 = findViewById(R.id.message_nurse_sc1);
//        Picasso.get().load(Img_Url_IP+"message_nurse_sc1.jpg").into(messageIV2);
        messageIV3 = findViewById(R.id.message_nurse_sc2);
//        Picasso.get().load(Img_Url_IP+"nurse_ask_sc8.jpg").into(messageIV3);

        Glide.with(this).load(Img_Url_IP+"NurseMessageGuide/nurse_message_image1.png").into(messageIV1);
        Glide.with(this).load(Img_Url_IP+"NurseMessageGuide/message_nurse_sc1.jpg").into(messageIV2);
        Glide.with(this).load(Img_Url_IP+"NurseMessageGuide/nurse_ask_sc8.jpg").into(messageIV3);

        workMessageTV.setOnClickListener(this);
        messageIV1.setOnClickListener(this);
        messageIV2.setOnClickListener(this);
        messageIV3.setOnClickListener(this);
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
            case R.id.work_message_tv:
                Intent intent2 = new Intent();
                intent2.putExtra("index",1);
                intent2.setClass(NurseMessageGuideActivity.this, HomeActivity.class);
                startActivity(intent2);
                break;
            case R.id.message_img1:
                ImagePreview.getInstance().setContext(NurseMessageGuideActivity.this).setIndex(0).setImage(Img_Url_IP+"NurseMessageGuide/nurse_message_image1.png").start();
                break;
            case R.id.message_nurse_sc1:
                ImagePreview.getInstance().setContext(NurseMessageGuideActivity.this).setIndex(0).setImage(Img_Url_IP+"NurseMessageGuide/message_nurse_sc1.jpg").start();
                break;
            case R.id.message_nurse_sc2:
                ImagePreview.getInstance().setContext(NurseMessageGuideActivity.this).setIndex(0).setImage(Img_Url_IP+"NurseMessageGuide/nurse_ask_sc8.jpg").start();
                break;
        }
    }
}
