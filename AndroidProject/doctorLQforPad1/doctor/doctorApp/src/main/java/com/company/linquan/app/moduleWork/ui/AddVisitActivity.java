package com.company.linquan.app.moduleWork.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.company.linquan.app.R;
import com.company.linquan.app.base.BaseActivity;
import com.company.linquan.app.moduleAuth.SelectDataActivity;
import com.company.linquan.app.moduleWork.WorkInterface;
import com.company.linquan.app.moduleWork.imp.AddVisitPresenterImp;
import com.company.linquan.app.util.ExitApp;
import com.company.linquan.app.util.MyDialog;
import com.company.linquan.app.util.MyToast;
import com.company.linquan.app.view.MyTextView;

/**
 * Created by YC on 2018/7/21.
 */

public class AddVisitActivity extends BaseActivity implements WorkInterface.AddVisitInterface
,View.OnClickListener{
    private Dialog myDialog;
    private MyTextView nameTV, typeTV;
    public final static int TYPE = 9;
    private String patientId = "",visitId ="", typeId="",name = "";
    private EditText remarkET;

    private AddVisitPresenterImp presenter;
    private String id = "", remark = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExitApp.getInstance().addActivity(this);		// add Activity 方便退出
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 开启自定义标题栏
        setContentView(R.layout.activity_add_visit);
        initData();
        initHead();
        initView();
    }

    private void initData() {
        patientId = getIntent().getStringExtra("patientId");
        visitId = getIntent().getStringExtra("visitId");
        name = getIntent().getStringExtra("name");
        id = getIntent().getStringExtra("id");
        typeId = getIntent().getStringExtra("typeId");
        remark = getIntent().getStringExtra("remark");
    }

    private void initHead() {
        RelativeLayout head = (RelativeLayout) findViewById(R.id.head_layout);
        MyTextView title = (MyTextView) head.findViewById(R.id.head_top_title);
        if ("".equals(id)){
            title.setText("添加就诊");
        }else{
            title.setText("就诊详情");
        }

        ImageView leftIV = (ImageView)head.findViewById(R.id.head_top_image);
        leftIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView(){
        presenter = new AddVisitPresenterImp(this);
        findViewById(R.id.add_visit_btn).setOnClickListener(this);
        if ("".equals(id)){
            findViewById(R.id.add_visit_btn).setVisibility(View.GONE);
        }else{
            findViewById(R.id.add_visit_btn).setVisibility(View.VISIBLE);
        }

        typeTV = (MyTextView) findViewById(R.id.add_visit_type);
        typeTV.setOnClickListener(this);
        if ("1".equals(typeId)){
            typeTV.setText("图文问诊");
        }else if ("2".equals(typeId)){
            typeTV.setText("语音问诊");
        }else if ("3".equals(typeId)){
            typeTV.setText("视频问诊");
        }else if ("4".equals(typeId)){
            typeTV.setText("预约面诊");
        }
        nameTV = (MyTextView) findViewById(R.id.add_visit_name);
        nameTV.setText(name);
        remarkET = (EditText)findViewById(R.id.add_visit_remark);
        remarkET.setText(remark);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add_visit_btn:
                if (TextUtils.isEmpty(typeId)) return;
                if (TextUtils.isEmpty(remarkET.getText().toString().trim())) return;
                presenter.addVisit(patientId,visitId,typeId,remarkET.getText().toString().trim());
                break;

            case R.id.add_visit_type:
                Intent typeIntent = new Intent(AddVisitActivity.this, SelectDataActivity.class);
                typeIntent.putExtra("type",TYPE);
                startActivityForResult(typeIntent,TYPE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TYPE && resultCode == RESULT_OK){
            typeId = data.getExtras().getString("id");
            typeTV.setText(data.getExtras().getString("name"));
        }
        super.onActivityResult(requestCode, resultCode, data);
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
}
