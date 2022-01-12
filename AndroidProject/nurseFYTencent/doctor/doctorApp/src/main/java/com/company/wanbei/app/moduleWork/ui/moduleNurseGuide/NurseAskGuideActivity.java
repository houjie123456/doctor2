package com.company.wanbei.app.moduleWork.ui.moduleNurseGuide;

import static com.tencent.qcloud.tuikit.tuichat.fromApp.config.C.Img_Url_IP;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.company.wanbei.app.moduleWork.ui.WorkNurseAskActivity;
import com.company.wanbei.app.util.ExitApp;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.MyToast;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.ToolSharePerference;
import com.tencent.qcloud.tuikit.tuichat.fromApp.view.MyTextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import cc.shinichi.library.ImagePreview;


public class NurseAskGuideActivity extends BaseActivity implements View.OnClickListener{

    private MyTextView workNurseAskTV;
    private ImageView nurseAskIV1,nurseAskIV2,nurseAskIV3,nurseAskIV4,nurseAskIV5,nurseAskIV6,nurseAskIV7,nurseAskIV8,nurseAskIV9,nurseAskIV10
            ,nurseAskIV11,nurseAskIV12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 开启自定义标题栏
        super.onCreate(savedInstanceState);
        ExitApp.getInstance().addActivity(this);		// add Activity 方便退出
        setContentView(R.layout.activity_guide_nurse_ask);
//        initData();
        initHead();
//        initView();
//        setListener();
//        getData();
    }
    /**
     * 从服务器取图片
     *http://bbs.3gstdy.com
     * @param url
     * @return
     */
    public static Bitmap getHttpBitmap(String url) {
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
//            Log.d(TAG, url);
            myFileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setConnectTimeout(0);
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
    private void initHead() {
        RelativeLayout head = (RelativeLayout) findViewById(R.id.head_layout);
        MyTextView title = (MyTextView)head. findViewById(R.id.head_top_title);
        title.setText("护理咨询指南");
        ImageView rightIV = (ImageView)head.findViewById(R.id.head_top_image);
        rightIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        workNurseAskTV = findViewById(R.id.work_nurse_ask_tv);
        nurseAskIV1 = findViewById(R.id.nurse_ask_img1);
//        Picasso.get().load(Img_Url_IP+"nurse_ask_img1.png").into(nurseAskIV1);
        nurseAskIV2 = findViewById(R.id.nurse_ask_sc1);
//        Picasso.get().load(Img_Url_IP+"nurse_ask_sc1.jpg").into(nurseAskIV2);
        nurseAskIV3 = findViewById(R.id.nurse_ask_sc2);
//        Picasso.get().load(Img_Url_IP+"nurse_ask_sc2.jpg").into(nurseAskIV3);
        nurseAskIV4 = findViewById(R.id.nurse_ask_sc3);
//        Picasso.get().load(Img_Url_IP+"nurse_ask_sc3.jpg").into(nurseAskIV4);
        nurseAskIV5 = findViewById(R.id.nurse_ask_img2);
//        Picasso.get().load(Img_Url_IP+"nurse_ask_img2.png").into(nurseAskIV5);
        nurseAskIV6 = findViewById(R.id.nurse_ask_sc4);
//        Picasso.get().load(Img_Url_IP+"nurse_ask_sc4.jpg").into(nurseAskIV6);
        nurseAskIV7 = findViewById(R.id.nurse_ask_sc5);
//        Picasso.get().load(Img_Url_IP+"nurse_ask_sc5.jpg").into(nurseAskIV7);
        nurseAskIV8 = findViewById(R.id.nurse_ask_sc6);
//        Picasso.get().load(Img_Url_IP+"nurse_ask_sc6.jpg").into(nurseAskIV8);
        nurseAskIV9 = findViewById(R.id.nurse_ask_img3);
//        Picasso.get().load(Img_Url_IP+"nurse_ask_img3.png").into(nurseAskIV9);
        nurseAskIV10 = findViewById(R.id.nurse_ask_sc7);
//        Picasso.get().load(Img_Url_IP+"nurse_ask_sc7.jpg").into(nurseAskIV10);
        nurseAskIV11 = findViewById(R.id.nurse_ask_sc8);
//        Picasso.get().load(Img_Url_IP+"nurse_ask_sc8.jpg").into(nurseAskIV11);
        nurseAskIV12 = findViewById(R.id.nurse_ask_img4);
//        Picasso.get().load(Img_Url_IP+"nurse_ask_img4.png").into(nurseAskIV12);

        Glide.with(this).load(Img_Url_IP+"NurseAskGuide/nurse_ask_img1.png").into(nurseAskIV1);
        Glide.with(this).load(Img_Url_IP+"NurseAskGuide/nurse_ask_sc1.jpg").into(nurseAskIV2);
        Glide.with(this).load(Img_Url_IP+"NurseAskGuide/nurse_ask_sc2.jpg").into(nurseAskIV3);
        Glide.with(this).load(Img_Url_IP+"NurseAskGuide/nurse_ask_sc3.jpg").into(nurseAskIV4);
        Glide.with(this).load(Img_Url_IP+"NurseAskGuide/nurse_ask_img2.png").into(nurseAskIV5);
        Glide.with(this).load(Img_Url_IP+"NurseAskGuide/nurse_ask_sc4.jpg").into(nurseAskIV6);
        Glide.with(this).load(Img_Url_IP+"NurseAskGuide/nurse_ask_sc5.jpg").into(nurseAskIV7);
        Glide.with(this).load(Img_Url_IP+"NurseAskGuide/nurse_ask_sc6.jpg").into(nurseAskIV8);
        Glide.with(this).load(Img_Url_IP+"NurseAskGuide/nurse_ask_img3.png").into(nurseAskIV9);
        Glide.with(this).load(Img_Url_IP+"NurseAskGuide/nurse_ask_sc7.jpg").into(nurseAskIV10);
        Glide.with(this).load(Img_Url_IP+"NurseAskGuide/nurse_ask_sc8.jpg").into(nurseAskIV11);
        Glide.with(this).load(Img_Url_IP+"NurseAskGuide/nurse_ask_img4.png").into(nurseAskIV12);

        workNurseAskTV.setOnClickListener(this);
        nurseAskIV1.setOnClickListener(this);
        nurseAskIV2.setOnClickListener(this);
        nurseAskIV3.setOnClickListener(this);
        nurseAskIV4.setOnClickListener(this);
        nurseAskIV5.setOnClickListener(this);
        nurseAskIV6.setOnClickListener(this);
        nurseAskIV7.setOnClickListener(this);
        nurseAskIV8.setOnClickListener(this);
        nurseAskIV9.setOnClickListener(this);
        nurseAskIV10.setOnClickListener(this);
        nurseAskIV11.setOnClickListener(this);
        nurseAskIV12.setOnClickListener(this);
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
            case R.id.work_nurse_ask_tv:
                if (!"1".equals(ToolSharePerference.getStringData(NurseAskGuideActivity.this, C.fileconfig, C.AUTH))){
                    MyToast.showToast(NurseAskGuideActivity.this,"请先进行实名认证", Toast.LENGTH_LONG);
                }else {
                    startActivity(new Intent(NurseAskGuideActivity.this, WorkNurseAskActivity.class));
                }
                break;
            case R.id.nurse_ask_img1:
                ImagePreview.getInstance().setContext(NurseAskGuideActivity.this).setIndex(0).setImage(Img_Url_IP+"NurseAskGuide/nurse_ask_img1.png").start();
                break;
            case R.id.nurse_ask_sc1:
                ImagePreview.getInstance().setContext(NurseAskGuideActivity.this).setIndex(0).setImage(Img_Url_IP+"NurseAskGuide/nurse_ask_sc1.jpg").start();
                break;
            case R.id.nurse_ask_sc2:
                ImagePreview.getInstance().setContext(NurseAskGuideActivity.this).setIndex(0).setImage(Img_Url_IP+"NurseAskGuide/nurse_ask_sc2.jpg").start();
                break;
            case R.id.nurse_ask_sc3:
                ImagePreview.getInstance().setContext(NurseAskGuideActivity.this).setIndex(0).setImage(Img_Url_IP+"NurseAskGuide/nurse_ask_sc3.jpg").start();
                break;
            case R.id.nurse_ask_img2:
                ImagePreview.getInstance().setContext(NurseAskGuideActivity.this).setIndex(0).setImage(Img_Url_IP+"NurseAskGuide/nurse_ask_img2.png").start();
                break;
            case R.id.nurse_ask_sc4:
                ImagePreview.getInstance().setContext(NurseAskGuideActivity.this).setIndex(0).setImage(Img_Url_IP+"NurseAskGuide/nurse_ask_sc4.jpg").start();
                break;
            case R.id.nurse_ask_sc5:
                ImagePreview.getInstance().setContext(NurseAskGuideActivity.this).setIndex(0).setImage(Img_Url_IP+"NurseAskGuide/nurse_ask_sc5.jpg").start();
                break;
            case R.id.nurse_ask_sc6:
                ImagePreview.getInstance().setContext(NurseAskGuideActivity.this).setIndex(0).setImage(Img_Url_IP+"NurseAskGuide/nurse_ask_sc6.jpg").start();
                break;
            case R.id.nurse_ask_img3:
                ImagePreview.getInstance().setContext(NurseAskGuideActivity.this).setIndex(0).setImage(Img_Url_IP+"NurseAskGuide/nurse_ask_img3.png").start();
                break;
            case R.id.nurse_ask_sc7:
                ImagePreview.getInstance().setContext(NurseAskGuideActivity.this).setIndex(0).setImage(Img_Url_IP+"NurseAskGuide/nurse_ask_sc7.jpg").start();
                break;
            case R.id.nurse_ask_sc8:
                ImagePreview.getInstance().setContext(NurseAskGuideActivity.this).setIndex(0).setImage(Img_Url_IP+"NurseAskGuide/nurse_ask_sc8.jpg").start();
                break;
            case R.id.nurse_ask_img4:
                ImagePreview.getInstance().setContext(NurseAskGuideActivity.this).setIndex(0).setImage(Img_Url_IP+"NurseAskGuide/nurse_ask_img4.png").start();
                break;
        }
    }
}
