package com.tencent.qcloud.tuikit.tuichat.ui.page;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.tencent.qcloud.tuikit.tuichat.R;
import com.tencent.qcloud.tuikit.tuichat.fromApp.view.MyTextView;


public class HealthRecordActivity extends AppCompatActivity {

    private TextView marriedTV;
    private TextView birthTV;
    private TextView heatedTV;
    private TextView diseaseTV;
    private TextView medicinedTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        marriedTV.setMovementMethod(ScrollingMovementMethod.getInstance());
        birthTV=(TextView)findViewById(R.id.birth);
        birthTV.setMovementMethod(ScrollingMovementMethod.getInstance());
        heatedTV=(TextView)findViewById(R.id.heated);
        heatedTV.setMovementMethod(ScrollingMovementMethod.getInstance());
        diseaseTV=(TextView)findViewById(R.id.disease);
        diseaseTV.setMovementMethod(ScrollingMovementMethod.getInstance());
        medicinedTV=(TextView)findViewById(R.id.medicined);
        medicinedTV.setMovementMethod(ScrollingMovementMethod.getInstance());
        String health_married=getIntent().getStringExtra("health_married")==null||getIntent().getStringExtra("health_married").equals("")?"暂无":getIntent().getStringExtra("health_married");
        String health_birth=getIntent().getStringExtra("health_birth")==null||getIntent().getStringExtra("health_birth").equals("")?"暂无":getIntent().getStringExtra("health_birth");
        String health_heated=getIntent().getStringExtra("health_heated")==null||getIntent().getStringExtra("health_heated").equals("")?"暂无":getIntent().getStringExtra("health_heated");
        String health_disease=getIntent().getStringExtra("health_disease")==null||getIntent().getStringExtra("health_disease").equals("")?"暂无":getIntent().getStringExtra("health_disease");
        String health_medicined=getIntent().getStringExtra("health_medicined")==null||getIntent().getStringExtra("health_medicined").equals("")?"暂无":getIntent().getStringExtra("health_medicined");
        marriedTV.setText(health_married);
        birthTV.setText(health_birth);
        heatedTV.setText(health_heated);
        diseaseTV.setText(health_disease);
        medicinedTV.setText(health_medicined);
    }


}
