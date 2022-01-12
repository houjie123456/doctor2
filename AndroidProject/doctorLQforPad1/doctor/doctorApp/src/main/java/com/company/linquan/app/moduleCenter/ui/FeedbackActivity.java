package com.company.linquan.app.moduleCenter.ui;

import android.app.Dialog;
import android.content.Context;
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
import com.company.linquan.app.moduleCenter.UserCenterInterface;
import com.company.linquan.app.moduleCenter.imp.FeedbackPresenterImp;
import com.company.linquan.app.util.ExitApp;
import com.company.linquan.app.util.MyDialog;
import com.company.linquan.app.util.MyToast;
import com.company.linquan.app.view.DelEditText;
import com.company.linquan.app.view.MyTextView;

/**
 * Created by YC on 2018/6/20.
 */

public class FeedbackActivity extends BaseActivity implements UserCenterInterface.FeedbackInterface,View.OnClickListener{

    private Dialog myDialog;
    private FeedbackPresenterImp presenter;
    private EditText contentET;
    private DelEditText titleET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExitApp.getInstance().addActivity(this);		// add Activity 方便退出
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 开启自定义标题栏
        setContentView(R.layout.activity_feedback);
        initHead();
        initView();
    }

    private void initHead() {
        RelativeLayout head = (RelativeLayout) findViewById(R.id.head_layout);
        MyTextView title = (MyTextView) head.findViewById(R.id.head_top_title);
        title.setText("用户反馈");
        ImageView rightIV = (ImageView)head.findViewById(R.id.head_top_image);
        rightIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView(){
        presenter = new FeedbackPresenterImp(this);
        findViewById(R.id.feedback_btn).setOnClickListener(this);
        titleET = (DelEditText)findViewById(R.id.feedback_title);
        titleET.setHint("请输入标题");
        contentET= (EditText)findViewById(R.id.feedback_content);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.feedback_btn:
                if (TextUtils.isEmpty(titleET.getText().toString().trim())) return;
                if (TextUtils.isEmpty(contentET.getText().toString().trim())) return;

                presenter.feedback(titleET.getText().toString().trim()
                ,contentET.getText().toString().trim());
                break;
        }
    }

    @Override
    public void finishActivity() {
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
