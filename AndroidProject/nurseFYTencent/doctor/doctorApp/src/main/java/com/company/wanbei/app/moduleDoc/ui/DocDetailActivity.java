//package com.company.wanbei.app.moduleDoc.ui;
//
//import android.app.Dialog;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.request.RequestOptions;
//import com.company.wanbei.app.base.BaseActivity;
//import com.company.wanbei.app.bean.DocBean;
//import com.company.wanbei.app.moduleDoc.DocInterface;
//import com.company.wanbei.app.moduleDoc.imp.DocDetailPresenterImp;
//import com.company.wanbei.app.util.ExitApp;
//import com.tencent.qcloud.tuikit.tuichat.fromApp.util.MyDialog;
//import com.tencent.qcloud.tuikit.tuichat.fromApp.util.MyToast;
//import com.tencent.qcloud.tuikit.tuichat.fromApp.view.MyTextView;
//import com.tencent.qcloud.tuikit.tuichat.fromApp.view.RoundImageView;
//import com.company.wanbei.app.R;
//
///**
// * Created by YC on 2018/8/1.
// */
//
//public class DocDetailActivity extends BaseActivity implements DocInterface.DocDetailInterFace{
//    private Dialog myDialog;
//    int sw;
//    private DocDetailPresenterImp presenter;
//    private String id;
//    private RoundImageView photoIV;
//    private MyTextView nameTV, titleTV,contentTV, timeTV;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        ExitApp.getInstance().addActivity(this);		// add Activity 方便退出
////        requestWindowFeature(Window.FEATURE_NO_TITLE); // 开启自定义标题栏
//        setContentView(R.layout.activity_doc_detail);
//        initData();
//        initHead();
//        initView();
//        getData();
//    }
//
//    private void getData() {
//        presenter.getData(id);
//    }
//
//    private void initData() {
//        if (getIntent() !=null){
//            id = getIntent().getStringExtra("id");
//        }
//    }
//
//    private void initHead() {
//        RelativeLayout head = (RelativeLayout)findViewById(R.id.head_layout);
//        TextView titleTV = (MyTextView)head.findViewById(R.id.head_top_title);
//        titleTV.setText("详情");
//        ImageView rightIV = (ImageView)head.findViewById(R.id.head_top_image);
//        rightIV.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
//    }
//
//    private void initView(){
//        presenter = new DocDetailPresenterImp(this);
//        sw = getResources().getDisplayMetrics().widthPixels;
//        photoIV = (RoundImageView)findViewById(R.id.doc_detail_photo);
//        nameTV = (MyTextView)findViewById(R.id.doc_detail_name);
//        titleTV = (MyTextView)findViewById(R.id.doc_detail_title);
//        contentTV = (MyTextView)findViewById(R.id.doc_detail_content);
//        timeTV = (MyTextView)findViewById(R.id.doc_detail_time);
//
//    }
//
//    @Override
//    public void finishActivity() {
//        Intent intent = new Intent();
//        setResult(RESULT_OK,intent);
//        finish();
//    }
//
//    @Override
//    public Context getContext() {
//        return this;
//    }
//
//    @Override
//    public void showDialog() {
//        if (myDialog == null) myDialog = MyDialog.showLoadingDialog(this);
//        myDialog.show();
//    }
//    @Override
//    public void dismissDialog() {
//        if (myDialog != null) myDialog.dismiss();
//    }
//
//    @Override
//    public void showToast(String txt) {
//        MyToast.showToast(this,txt, Toast.LENGTH_SHORT);
//    }
//
//    @Override
//    public void reloadView(DocBean bean) {
//        Glide.with(this).load(bean.getHeadImg()).apply(new RequestOptions().error(R.drawable.img_doctor_temp).centerCrop()).into(photoIV);
//        nameTV.setText(bean.getReleaseMan());
//        timeTV.setText(bean.getReleaseTime());
//        titleTV.setText(bean.getTitle());
//        contentTV.setText(bean.getSubtitle());
//    }
//}
