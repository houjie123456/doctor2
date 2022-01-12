package com.company.wanbei.app.moduleCenter.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.company.wanbei.app.R;
import com.company.wanbei.app.base.BaseActivity;
import com.company.wanbei.app.util.ExitApp;
import com.tencent.qcloud.tuikit.tuichat.fromApp.view.MyTextView;

import java.util.ArrayList;

//import androidx.core.content.ContextCompat;

/**
 * Created by YC on 2018/6/18.
 */

public class GetSignAutoActivity extends BaseActivity{

    private TextView signAutoNumTV, signAutoEndTimeTV,signAutoSystemTV;
    private String signAutoNum, signAutoEndTime;
    private ArrayList<String> signAutoSystem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExitApp.getInstance().addActivity(this);		// add Activity 方便退出
//        requestWindowFeature(Window.FEATURE_NO_TITLE); // 开启自定义标题栏
        setContentView(R.layout.activity_sign_auto_info);
        getData();
        initHead();
        initView();

    }

    private void getData() {
        signAutoNum = getIntent().getStringExtra("signAutoNum");
        signAutoEndTime = getIntent().getStringExtra("signAutoEndTime");
        signAutoSystem = getIntent().getStringArrayListExtra("signAutoSystem");
    }

    private void initHead() {
        RelativeLayout head = (RelativeLayout) findViewById(R.id.head_layout);
        MyTextView title = (MyTextView) head.findViewById(R.id.head_top_title);
        title.setText("查看自动签名状态");
        ImageView rightIV = (ImageView)head.findViewById(R.id.head_top_image);
        rightIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView(){

        signAutoNumTV = (TextView)findViewById(R.id.auto_sign_num);
        signAutoEndTimeTV  = (TextView)findViewById(R.id.auto_sign_end_time);
        signAutoSystemTV = (TextView)findViewById(R.id.auto_sign_system);

        signAutoNumTV.setText(signAutoNum+"个");
        signAutoEndTimeTV.setText(signAutoEndTime);
        String system = "";
        for (int i=0;i<signAutoSystem.size();i++){
            if(i==signAutoSystem.size()-1){
                system+=signAutoSystem.get(i);
            }else {
                system+=signAutoSystem.get(i)+",";
            }

        }
        signAutoSystemTV.setText(system);

    }



}
