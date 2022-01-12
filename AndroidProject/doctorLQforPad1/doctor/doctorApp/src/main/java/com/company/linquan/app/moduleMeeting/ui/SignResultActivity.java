package com.company.linquan.app.moduleMeeting.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.company.linquan.app.R;
import com.company.linquan.app.base.BaseActivity;
import com.company.linquan.app.util.ExitApp;
import com.company.linquan.app.view.MyTextView;

/**
 * Created by YC on 2018/8/5.
 */

public class SignResultActivity extends BaseActivity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExitApp.getInstance().addActivity(this);		// add Activity 方便退出
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 开启自定义标题栏
        setContentView(R.layout.activity_sign_result);
        initHead();
        initView();
    }

    private void initHead() {
        RelativeLayout head = (RelativeLayout) findViewById(R.id.head_layout);
        MyTextView title = (MyTextView) head.findViewById(R.id.head_top_title);
        title.setText("结果");
        ImageView rightIV = (ImageView)head.findViewById(R.id.head_top_image);
        rightIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView(){
        findViewById(R.id.result_btn).setOnClickListener(this);
        MyTextView nameTV = (MyTextView)findViewById(R.id.result_name);
        nameTV.setText(getIntent().getStringExtra("name"));
        MyTextView mobileTV = (MyTextView)findViewById(R.id.result_mobile);
        mobileTV.setText(getIntent().getStringExtra("mobile"));
        MyTextView hospitalTV = (MyTextView)findViewById(R.id.result_hospital);
        hospitalTV.setText(getIntent().getStringExtra("hospital"));
        MyTextView cardTV = (MyTextView)findViewById(R.id.result_card);
        cardTV.setText(getIntent().getStringExtra("card"));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.result_btn:
                Intent intent = new Intent();
                setResult(1,intent);
                finish();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent();
            setResult(1,intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
