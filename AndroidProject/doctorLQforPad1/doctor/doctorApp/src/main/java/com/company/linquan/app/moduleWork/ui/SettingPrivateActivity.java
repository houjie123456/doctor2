package com.company.linquan.app.moduleWork.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.company.linquan.app.R;
import com.company.linquan.app.base.BaseActivity;
import com.company.linquan.app.http.JSONBean;
import com.company.linquan.app.moduleWork.WorkInterface;
import com.company.linquan.app.moduleWork.imp.SetPrivatePresenterImp;
import com.company.linquan.app.util.ExitApp;
import com.company.linquan.app.util.MyDialog;
import com.company.linquan.app.util.MyToast;
import com.company.linquan.app.view.DelEditText;
import com.company.linquan.app.view.MyTextView;

/**
 * Created by YC on 2018/6/18.
 */

public class SettingPrivateActivity extends BaseActivity implements WorkInterface.SettingPrivateInterface, View.OnClickListener{

    private Dialog myDialog;
    private SetPrivatePresenterImp presenter;
    private DelEditText oneET,threeET,sixET,twelveET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExitApp.getInstance().addActivity(this);		// add Activity 方便退出
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 开启自定义标题栏
        setContentView(R.layout.activity_setting_private);
        initHead();
        initView();
        getData();
    }

    private void getData() {
        presenter.getPrivate();
    }

    private void initHead() {
        RelativeLayout head = (RelativeLayout) findViewById(R.id.head_layout);
        MyTextView title = (MyTextView) head.findViewById(R.id.head_top_title);
        title.setText("设置私人医生");
        ImageView rightIV = (ImageView)head.findViewById(R.id.head_top_image);
        rightIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView(){
        presenter = new SetPrivatePresenterImp(this);
        findViewById(R.id.setting_private_btn).setOnClickListener(this);
        oneET = (DelEditText)findViewById(R.id.setting_private_one);
        oneET.setHint("请输入一个月费用");
        oneET.setGravity(Gravity.RIGHT);
        threeET = (DelEditText)findViewById(R.id.setting_private_three);
        threeET.setHint("请输入三个月费用");
        threeET.setGravity(Gravity.RIGHT);
        sixET = (DelEditText)findViewById(R.id.setting_private_six);
        sixET.setHint("请输入六个月费用");
        sixET.setGravity(Gravity.RIGHT);
        twelveET = (DelEditText)findViewById(R.id.setting_private_year);
        twelveET.setHint("请输入十二个月费用");
        twelveET.setGravity(Gravity.RIGHT);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.setting_private_btn:

                if (TextUtils.isEmpty(oneET.getText().toString().trim())) return;
                if (TextUtils.isEmpty(threeET.getText().toString().trim())) return;
                if (TextUtils.isEmpty(sixET.getText().toString().trim())) return;
                if (TextUtils.isEmpty(twelveET.getText().toString().trim())) return;

                presenter.setPrivate(oneET.getText().toString().trim(),threeET.getText().toString().trim()
                ,sixET.getText().toString().trim(),twelveET.getText().toString().trim());

                break;
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
    public void reloadView(JSONBean bean) {
        oneET.setText(bean.getAmountOneMonth());
        threeET.setText(bean.getAmountThreeMonth());
        sixET.setText(bean.getAmountSixMonth());
        twelveET.setText(bean.getAmountTwelveMonth());
    }
}
