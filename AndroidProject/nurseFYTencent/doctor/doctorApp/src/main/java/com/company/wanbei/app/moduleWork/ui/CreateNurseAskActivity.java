package com.company.wanbei.app.moduleWork.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.codbking.widget.OnSureLisener;
import com.company.wanbei.app.R;
import com.company.wanbei.app.base.BaseActivity;
import com.company.wanbei.app.moduleWork.WorkInterface;
import com.company.wanbei.app.moduleWork.imp.CreateNurseAskPresenterImp;
import com.company.wanbei.app.util.ExitApp;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.MyDialog;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.MyToast;
import com.company.wanbei.app.util.widget.DatePickDialog1;
import com.company.wanbei.app.util.widget.OnChangeLisener;
import com.company.wanbei.app.util.widget.bean.DateType;
import com.company.wanbei.app.view.DelEditText;
import com.tencent.qcloud.tuikit.tuichat.fromApp.view.MyTextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//import com.codbking.widget.DatePickDialog;
//import com.codbking.widget.OnChangeLisener;
//import com.codbking.widget.OnSureLisener;
//import com.codbking.widget.bean.DateType;


/**
 * Created by YC on 2018/6/13.
 */

public class CreateNurseAskActivity extends BaseActivity implements WorkInterface.CreateNurseAskInterface, View.OnClickListener {

    private Dialog myDialog;
    private CreateNurseAskPresenterImp presenter;
    private DelEditText amountET, totalTimeET;
    private MyTextView startTimeTV,endTimeTV,createTV;
    private String startTime = "",endTime = "",selectType="",id="";
    private DatePickDialog1 dialog;
    private int money,moneyfeesS,moneyfeesM;
    int index;
    private Date start,end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 开启自定义标题栏
        super.onCreate(savedInstanceState);
        ExitApp.getInstance().addActivity(this); // add Activity 方便退出

        setContentView(R.layout.activity_create_nurse_ask);
        initHead();
        initView();
        getData();
    }

    private void getData(){
//        presenter.costSetting();
    }

    private void initHead() {
        selectType=getIntent().getStringExtra("selectType");
        id = getIntent().getStringExtra("id");

        RelativeLayout head = (RelativeLayout) findViewById(R.id.head_layout);
        MyTextView title = (MyTextView) head.findViewById(R.id.head_top_title);
        if(selectType.equals("1")){
            title.setText("创建护理咨询");
        }else {
            title.setText("修改护理咨询");
        }
        ImageView rightIV = (ImageView) head.findViewById(R.id.head_top_image);
        rightIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView() {
        presenter = new CreateNurseAskPresenterImp(this);
        createTV =  (MyTextView) findViewById(R.id.create_nurse_ask_btn);
        createTV.setOnClickListener(this);

        startTimeTV = (MyTextView) findViewById(R.id.create_nurse_start_time);
        startTimeTV.setOnClickListener(this);
        endTimeTV = (MyTextView) findViewById(R.id.create_nurse_end_time);
        endTimeTV.setOnClickListener(this);
        startTimeTV.setText("请选择开始时间");
        endTimeTV.setText("请选择结束时间");
        if(getIntent().getStringExtra("id")!=null&&!getIntent().getStringExtra("id").equals("")){
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            startTime = getIntent().getStringExtra("startTime");
            endTime = getIntent().getStringExtra("endTime");

            try {
                start =formatter.parse(startTime);
                end =formatter.parse(endTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            startTimeTV.setText(startTime);
            endTimeTV.setText(endTime);
        }
        if(selectType.equals("1")){
            createTV.setText("确定发布");
        }else {
            createTV.setText("确定修改");
        }
//        findViewById(R.id.create_voice_btn).setOnClickListener(this);
    }

    @Override
    public void reloadTime(String startTime, String endTime) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.create_nurse_ask_btn:
                if (TextUtils.isEmpty(startTime)){
                    showToast("请先选择开始时间");
                    return;
                }
                if (TextUtils.isEmpty(endTime)){
                    showToast("请先选择开始时间");
                    return;
                }
                if(start.getTime()>end.getTime()){
                    showToast("结束时间不能小于开始时间");
                    return;
                }
                if(selectType.equals("1")){
                    presenter.createNurseAsk( startTime, endTime,"");
                }else {
                    presenter.createNurseAsk( startTime, endTime,id);
                }

                break;
            case R.id.create_nurse_start_time:
                index = 1;

                showDateDialog();
                break;
            case R.id.create_nurse_end_time:
                index = 2;
                if (TextUtils.isEmpty(startTime)){
                    showToast("请先选择开始时间");
                    return;
                }

                showDateDialog();
                break;

        }
    }

    /**
     * 选择时间对话框
     */
    private void showDateDialog(){
        dialog = new DatePickDialog1(this);

        if(index == 1){
            dialog.setStartDate(start);
        }
        if(index == 2){
            dialog.setStartDate(end);
        }
        //设置上下年分限制
        dialog.setYearLimt(5);
        //设置标题
        dialog.setTitle("选择时间");
        //设置类型
        dialog.setType(DateType.TYPE_YMDHM);
        //设置消息体的显示格式，日期格式
        dialog.setMessageFormat("yyyy-MM-dd HH:mm");
        //设置选择回调
        dialog.setOnChangeLisener(new OnChangeLisener() {
            @Override
            public void onChanged(Date date) {

            }
        });
        //设置点击确定按钮回调
        dialog.setOnSureLisener(new OnSureLisener() {
            @Override
            public void onSure(Date date) {
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String datestr=sdf.format(date);
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                if (index == 1){
                    start = date;
                    startTime = datestr;
                    startTimeTV.setText(startTime);
                }

                if (index == 2){
                    endTime = datestr;
                    end = date;
                    endTimeTV.setText(endTime);
                }
            }
        });
        dialog.show();
    }


    @Override
    public void finishActivity() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
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
        MyToast.showToast(this, txt, Toast.LENGTH_SHORT);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
