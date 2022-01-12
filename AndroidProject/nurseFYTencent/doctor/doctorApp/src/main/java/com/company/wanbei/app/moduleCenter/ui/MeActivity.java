package com.company.wanbei.app.moduleCenter.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.company.wanbei.app.base.BaseActivity;
import com.company.wanbei.app.bean.UserInfoBean;
import com.company.wanbei.app.http.JSONMe;
import com.company.wanbei.app.moduleCenter.UserCenterInterface;
import com.company.wanbei.app.moduleCenter.imp.MePresenterImp;
import com.company.wanbei.app.util.ExitApp;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.MyDialog;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.MyToast;
import com.tencent.qcloud.tuikit.tuichat.fromApp.view.MyTextView;
import com.tencent.qcloud.tuikit.tuichat.fromApp.view.RoundImageView;
import com.company.wanbei.app.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.Hashtable;

//import androidx.core.content.ContextCompat;

/**
 * Created by YC on 2018/6/18.
 */

public class MeActivity extends BaseActivity implements UserCenterInterface.MeInterface, View.OnClickListener{

    private Dialog myDialog;
    private MePresenterImp presenter;
    private MyTextView nameTV, contentTV;
    private ImageView imageView;
    private RoundImageView photoIV;
    int sw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExitApp.getInstance().addActivity(this);		// add Activity 方便退出
//        requestWindowFeature(Window.FEATURE_NO_TITLE); // 开启自定义标题栏
        setContentView(R.layout.activity_me);
        initHead();
        initView();
        getData();
    }

    private void getData() {
        presenter.getPerson();
    }

    private void initHead() {
        RelativeLayout head = (RelativeLayout) findViewById(R.id.head_layout);
        head.setBackgroundColor(ContextCompat.getColor(this,R.color.transparent));
        MyTextView title = (MyTextView) head.findViewById(R.id.head_top_title);
        title.setText("我的名片");
        title.setTextColor(ContextCompat.getColor(this,R.color.white));
        ImageView rightIV = (ImageView)head.findViewById(R.id.head_top_image);
        rightIV.setImageResource(R.drawable.img_top_back_white);
        rightIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView(){

        sw = getContext().getResources().getDisplayMetrics().widthPixels;

        presenter = new MePresenterImp(this);
        nameTV = (MyTextView)findViewById(R.id.me_name);
        contentTV  = (MyTextView)findViewById(R.id.me_content);
        imageView = (ImageView) findViewById(R.id.me_image);
        photoIV = (RoundImageView)findViewById(R.id.roundImageView);
        photoIV.setDrawCircle();

    }


    /**
     　　* 生成二维码
     　　* @param string 二维码中包含的文本信息
     　　* @param mBitmap logo图片
     　　* @param format 编码格式
     　　* @return Bitmap 位图
     　　* @throws WriterException
     　　*/
    public Bitmap createCode(String string, int w)throws WriterException {
        MultiFormatWriter writer = new MultiFormatWriter();
        Hashtable hst = new Hashtable();
        hst.put(EncodeHintType.CHARACTER_SET, "UTF-8");// 设置字符编码
        hst.put(EncodeHintType.MARGIN, 0);// 设置padding
        int sw = getResources().getDisplayMetrics().widthPixels;
        BitMatrix matrix = new MultiFormatWriter().encode(string,
                BarcodeFormat.QR_CODE, w, w, hst);

        int width = matrix.getWidth();
        int height = matrix.getHeight();
        // 二维矩阵转为一维像素数组,也就是一直横着排了
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (matrix.get(x, y)) {
                    pixels[y * width + x] = 0xff000000;
                }
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_4444);
        // 通过像素数组生成bitmap,具体参考api
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
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

    @Override
    public void reloadView(JSONMe be) {

        UserInfoBean bean = be.getInfoJson();
        int w = (158*sw)/720;
        Glide.with(this).load(bean.getHeadUrl()).apply(new RequestOptions().override(w,w).centerCrop()).into(photoIV);

        nameTV.setText(bean.getMyName());
        String job = bean.getAcademicTitleName();
//        if ("1".equals(bean.getAcademicTitle()))
//        {
//            job = "主任医师";
//        }else if("2".equals(bean.getAcademicTitle())){
//            job = "副主任医师";
//        }else if("3".equals(bean.getAcademicTitle())){
//            job = "主治医师";
//        }else if("4".equals(bean.getAcademicTitle())){
//            job = "医师";
//        }else if("5".equals(bean.getAcademicTitle())){
//            job = "护士";
//        }else if("6".equals(bean.getAcademicTitle())){
//            job = "护师";
//        }else if("7".equals(bean.getAcademicTitle())){
//            job = "副主任护师";
//        }else if("8".equals(bean.getAcademicTitle())){
//            job = "主任护师";
//        }else if("10".equals(bean.getAcademicTitle())){
//            job = "医士";
//        }else if("11".equals(bean.getAcademicTitle())){
//            job = "主管护师";
//        }else{
//            job = "未知";
//        }
        contentTV.setText(bean.getDepartmentName()+" "+job+" "+bean.getHospitalName());

        Glide.with(MeActivity.this)
                .load(be.getCodeUrl()).apply(new RequestOptions().override(sw/2-40,sw/2-40)
                .centerCrop())
                .into(imageView);
    }
}
