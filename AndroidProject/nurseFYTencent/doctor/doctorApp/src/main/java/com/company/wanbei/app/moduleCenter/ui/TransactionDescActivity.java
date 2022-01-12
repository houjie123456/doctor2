package com.company.wanbei.app.moduleCenter.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.company.wanbei.app.R;
import com.company.wanbei.app.base.BaseActivity;
import com.company.wanbei.app.util.ExitApp;
import com.tencent.qcloud.tuikit.tuichat.fromApp.view.MyTextView;
import com.tencent.qcloud.tuikit.tuichat.fromApp.view.RoundImageView;

public class TransactionDescActivity extends BaseActivity implements View.OnClickListener{

    private Dialog myDialog;


    private RoundImageView patHeadIV;
    private TextView patNameTV,orderIDTV,getAmountBigTV,getAmountTV,serveTypeTV,transactionTimeTV,transactionAmountTV,orderStatusTV;

    int sw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExitApp.getInstance().addActivity(this);		// add Activity 方便退出
//        requestWindowFeature(Window.FEATURE_NO_TITLE); // 开启自定义标题栏
        setContentView(R.layout.activity_transaction_desc);

//        initData();
        initHead();
        initView();
    }

    private void initHead() {
        RelativeLayout head = (RelativeLayout) findViewById(R.id.head_layout);
        MyTextView title = (MyTextView)head. findViewById(R.id.head_top_title);
        title.setText("交易详情");
        ImageView rightIV = (ImageView)head.findViewById(R.id.head_top_image);
        rightIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView(){
        sw = getResources().getDisplayMetrics().widthPixels;

        patHeadIV = findViewById(R.id.pat_head);
        patNameTV = findViewById(R.id.pat_name);
        getAmountBigTV = findViewById(R.id.get_amount_big);
        orderIDTV = findViewById(R.id.order_id);
        serveTypeTV = findViewById(R.id.serve_type);
        transactionTimeTV = findViewById(R.id.transaction_time);
        transactionAmountTV = findViewById(R.id.transaction_amount);
        getAmountTV = findViewById(R.id.get_amount);
        orderStatusTV = findViewById(R.id.order_status);

        int w2 = (134*sw)/720;
        int h2 = (134*sw)/720;
        Glide.with(this).load(getIntent().getStringExtra("patHead")).apply(new RequestOptions().override(w2,h2).centerCrop()).into(patHeadIV);
        patNameTV.setText(getIntent().getStringExtra("patName"));
        getAmountBigTV.setText(getIntent().getStringExtra("getAmountBig"));
        orderIDTV.setText(getIntent().getStringExtra("orderID"));
        serveTypeTV.setText(getIntent().getStringExtra("serveType"));
        transactionTimeTV.setText(getIntent().getStringExtra("transactionTime"));
        transactionAmountTV.setText(getIntent().getStringExtra("transactionAmount"));
        getAmountTV.setText(getIntent().getStringExtra("getAmount"));
        orderStatusTV.setText(getIntent().getStringExtra("orderStatus"));

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
        }
    }



}
