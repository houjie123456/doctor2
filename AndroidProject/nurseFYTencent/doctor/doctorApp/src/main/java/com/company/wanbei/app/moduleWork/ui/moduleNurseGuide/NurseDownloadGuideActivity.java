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


public class NurseDownloadGuideActivity extends BaseActivity implements View.OnClickListener{

    private MyTextView workFragmentTV;
    private ImageView downLoadIV1,downLoadIV2,downLoadIV3,downLoadIV4,downLoadIV5,downLoadIV6,downLoadIV7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 开启自定义标题栏
        super.onCreate(savedInstanceState);
        ExitApp.getInstance().addActivity(this);		// add Activity 方便退出
        setContentView(R.layout.activity_guide_download_nurse);
//        initData();
        initHead();
//        initView();
//        setListener();
//        getData();
    }

    private void initHead() {
        RelativeLayout head = (RelativeLayout) findViewById(R.id.head_layout);
        MyTextView title = (MyTextView)head. findViewById(R.id.head_top_title);
        title.setText("下载登录指南");
        ImageView rightIV = (ImageView)head.findViewById(R.id.head_top_image);
        rightIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        workFragmentTV = findViewById(R.id.work_fragment_tv);
        downLoadIV1 = findViewById(R.id.download_img1);
//        Picasso.get().load(Img_Url_IP+"download_image1.png").into(downLoadIV1);
        downLoadIV2 = findViewById(R.id.download_img2);
//        Picasso.get().load(Img_Url_IP+"download_image2.png").into(downLoadIV2);
        downLoadIV3 = findViewById(R.id.download_img3);
//        Picasso.get().load(Img_Url_IP+"download_image3.png").into(downLoadIV3);
        downLoadIV4 = findViewById(R.id.download_img4);
//        Picasso.get().load(Img_Url_IP+"download_sc1.jpg").into(downLoadIV4);
        downLoadIV5 = findViewById(R.id.download_img5);
//        Picasso.get().load(Img_Url_IP+"download_sc2.jpg").into(downLoadIV5);
        downLoadIV6 = findViewById(R.id.download_img6);

        Glide.with(this).load(Img_Url_IP+"NurseDownloadGuide/nurse_download_image1.png").into(downLoadIV1);
        Glide.with(this).load(Img_Url_IP+"NurseDownloadGuide/nurse_download_image2.png").into(downLoadIV2);
        Glide.with(this).load(Img_Url_IP+"NurseDownloadGuide/nurse_download_image3.png").into(downLoadIV3);
        Glide.with(this).load(Img_Url_IP+"NurseDownloadGuide/download_sc1.jpg").into(downLoadIV4);
        Glide.with(this).load(Img_Url_IP+"NurseDownloadGuide/download_nurse_sc2.jpg").into(downLoadIV5);
        Glide.with(this).load(Img_Url_IP+"NurseDownloadGuide/download_nurse_sc1.jpg").into(downLoadIV6);

        workFragmentTV.setOnClickListener(this);
        downLoadIV1.setOnClickListener(this);
        downLoadIV2.setOnClickListener(this);
        downLoadIV3.setOnClickListener(this);
        downLoadIV4.setOnClickListener(this);
        downLoadIV5.setOnClickListener(this);
        downLoadIV6.setOnClickListener(this);
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
            case R.id.work_fragment_tv:
                Intent intent2 = new Intent();
                intent2.putExtra("index",0);
                intent2.setClass(NurseDownloadGuideActivity.this, HomeActivity.class);
                startActivity(intent2);
                break;
            case R.id.download_img1:
                ImagePreview
                        .getInstance()
                        // 上下文，必须是activity，不需要担心内存泄漏，本框架已经处理好；
                        .setContext(NurseDownloadGuideActivity.this)
                        // 设置从第几张开始看（索引从0开始）
                        .setIndex(0)
                        // 有三种设置数据集合的方式，根据自己的需求进行三选一：
                        // 1：第一步生成的imageInfo List
                        //.setImageInfoList(imageInfoList)
                        // 2：直接传url List
//                        .setImageList(imageList)
                        // 3：只有一张图片的情况，可以直接传入这张图片的url
                        .setImage(Img_Url_IP+"nurse_download_image1.png")
                        // 开启预览
                        .start();
                break;
            case R.id.download_img2:
                ImagePreview.getInstance().setContext(NurseDownloadGuideActivity.this).setIndex(0).setImage(Img_Url_IP+"NurseDownloadGuide/nurse_download_image2.png").start();
                break;
            case R.id.download_img3:
                ImagePreview.getInstance().setContext(NurseDownloadGuideActivity.this).setIndex(0).setImage(Img_Url_IP+"NurseDownloadGuide/nurse_download_image3.png").start();
                break;
            case R.id.download_img4:
                ImagePreview.getInstance().setContext(NurseDownloadGuideActivity.this).setIndex(0).setImage(Img_Url_IP+"NurseDownloadGuide/download_sc1.jpg").start();
                break;
            case R.id.download_img5:
                ImagePreview.getInstance().setContext(NurseDownloadGuideActivity.this).setIndex(0).setImage(Img_Url_IP+"NurseDownloadGuide/download_nurse_sc2.jpg").start();
                break;
            case R.id.download_img6:
                ImagePreview.getInstance().setContext(NurseDownloadGuideActivity.this).setIndex(0).setImage(Img_Url_IP+"NurseDownloadGuide/download_nurse_sc1.jpg").start();
                break;
        }
    }
}
