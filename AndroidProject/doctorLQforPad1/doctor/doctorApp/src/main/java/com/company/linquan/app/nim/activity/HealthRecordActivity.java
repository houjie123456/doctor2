package com.company.linquan.app.nim.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.company.linquan.app.R;
import com.company.linquan.app.base.BaseActivity;
import com.company.linquan.app.util.ExitApp;
import com.company.linquan.app.util.MyDialog;
import com.company.linquan.app.util.MyToast;
import com.company.linquan.app.view.MyTextView;

public class HealthRecordActivity extends BaseActivity {

    private TextView marriedTV;
    private TextView birthTV;
    private TextView heatedTV;
    private TextView diseaseTV;
    private TextView medicinedTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExitApp.getInstance().addActivity(this);		// add Activity 方便退出
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 开启自定义标题栏
        setContentView(R.layout.activity_health_record);

        initHead();
        initView();
    }

    private void initHead() {
        RelativeLayout head = (RelativeLayout) findViewById(R.id.head_layout_top);
        MyTextView title = (MyTextView)head. findViewById(R.id.head_top_title);
        title.setText("健康档案");
        ImageView rightIV = (ImageView)head.findViewById(R.id.head_top_image);
        rightIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView(){
        marriedTV=(TextView)findViewById(R.id.married);
        birthTV=(TextView)findViewById(R.id.birth);
        heatedTV=(TextView)findViewById(R.id.heated);
        diseaseTV=(TextView)findViewById(R.id.disease);
        medicinedTV=(TextView)findViewById(R.id.medicined);
        String health_married=getIntent().getStringExtra("health_married")==null?"暂无":getIntent().getStringExtra("health_married");
        String health_birth=getIntent().getStringExtra("health_birth")==null?"暂无":getIntent().getStringExtra("health_birth");
        String health_heated=getIntent().getStringExtra("health_heated")==null?"暂无":getIntent().getStringExtra("health_heated");
        String health_disease=getIntent().getStringExtra("health_disease")==null?"暂无":getIntent().getStringExtra("health_disease");
        String health_medicined=getIntent().getStringExtra("health_medicined")==null?"暂无":getIntent().getStringExtra("health_medicined");
        marriedTV.setText(health_married);
        birthTV.setText(health_birth);
        heatedTV.setText(health_heated);
        diseaseTV.setText(health_disease);
        medicinedTV.setText(health_medicined);
    }


}
