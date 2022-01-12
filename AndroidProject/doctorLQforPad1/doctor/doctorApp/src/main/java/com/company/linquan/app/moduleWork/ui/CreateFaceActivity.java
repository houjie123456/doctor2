package com.company.linquan.app.moduleWork.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.company.linquan.app.R;
import com.company.linquan.app.base.BaseActivity;
import com.company.linquan.app.moduleWork.WorkInterface;
import com.company.linquan.app.moduleWork.imp.CreateFacePresenterImp;
import com.company.linquan.app.util.ExitApp;
import com.company.linquan.app.util.MyDialog;
import com.company.linquan.app.util.MyToast;
import com.company.linquan.app.view.DelEditText;
import com.company.linquan.app.view.MyTextView;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import cn.aigestudio.datepicker.cons.DPMode;
import cn.aigestudio.datepicker.views.DatePicker;

/**
 * Created by YC on 2018/6/11.
 */

public class CreateFaceActivity extends BaseActivity implements WorkInterface.CreateFaceInterface, View.OnClickListener
        ,TimePickerDialog.OnTimeSetListener{

    private Dialog myDialog;
    private CreateFacePresenterImp presenter;
    private DelEditText addressET,amountET,numET;
    private MyTextView startTimeTV, endTimeTV;
    private String startTime = "",endTime ="",lon,lat,room;
    private TimePickerDialog timePickerDialog;
    int index;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private int sH,sM;
    private String selectTime = "";
    private MyTextView selectAddressTV;

    private static final int SELECT = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExitApp.getInstance().addActivity(this);		// add Activity 方便退出
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 开启自定义标题栏
        setContentView(R.layout.activity_create_face);
        initHead();
        initView();
    }

    private void initHead() {
        RelativeLayout head = (RelativeLayout) findViewById(R.id.head_layout);
        MyTextView title = (MyTextView) head.findViewById(R.id.head_top_title);
        title.setText("创建面诊");
        ImageView rightIV = (ImageView)head.findViewById(R.id.head_top_image);
        rightIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView(){
        presenter = new CreateFacePresenterImp(this);
        findViewById(R.id.create_face_btn).setOnClickListener(this);
        addressET = (DelEditText)findViewById(R.id.create_face_address);
        addressET.setHint("请输入地址");
        addressET.setGravity(Gravity.RIGHT);
        amountET = (DelEditText)findViewById(R.id.create_face_money);
        amountET.setHint("请输入金额");
        amountET.setGravity(Gravity.RIGHT);
        numET = (DelEditText)findViewById(R.id.create_face_person_num);
        numET.setHint("请输入人数");
        numET.setGravity(Gravity.RIGHT);
        startTimeTV = (MyTextView) findViewById(R.id.create_face_start_time);
        endTimeTV = (MyTextView) findViewById(R.id.create_face_end_time);
        startTimeTV.setOnClickListener(this);
        endTimeTV.setOnClickListener(this);

        selectAddressTV = (MyTextView)findViewById(R.id.create_face_select_address);
        selectAddressTV.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.create_face_select_address:
                startActivityForResult(new Intent(CreateFaceActivity.this, SelectAddressActivity.class),SELECT);
                break;

            case R.id.create_face_btn:

                if (TextUtils.isEmpty(lat) || TextUtils.isEmpty(lon)){
                    showToast("请选择地址");
                    return;
                }
                if (TextUtils.isEmpty(amountET.getText().toString())){
                    showToast("请输入面诊金额");
                    return;
                }
                if (TextUtils.isEmpty(numET.getText().toString())){
                    showToast("请输入每小时面诊人数");
                    return;
                }

                presenter.createFace(selectAddressTV.getText().toString(),amountET.getText().toString(),startTime
                ,endTime,numET.getText().toString(),lon,lat,room);
//                presenter.createFace(selectAddressTV.getText().toString(),"0",startTime
//                        ,endTime,numET.getText().toString(),lon,lat,room);
                break;

            case R.id.create_face_start_time:
                index = 1;
                showDateDialog();
                break;

            case R.id.create_face_end_time:
                index = 2;

                if (TextUtils.isEmpty(startTime)){
                    showToast("请先选择开始时间");
                    return;
                }

                timePickerDialog.setVibrate(true);
                timePickerDialog.setCloseOnSingleTapMinute(true);
                timePickerDialog.show(getSupportFragmentManager(), "timePicker");
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SELECT && resultCode == RESULT_OK){
            lat = data.getStringExtra("lat");
            lon = data.getStringExtra("lon");
            selectAddressTV.setText(data.getStringExtra("address"));
            room = data.getStringExtra("room");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * @param view      The view associated with this listener.
     * @param hourOfDay The hour that was set.
     * @param minute    The minute that was set.
     */
    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        if (index == 1){
            sH = hourOfDay;
            sM = minute;
//            startTime = selectTime+" "+(hourOfDay<10?"0"+hourOfDay:hourOfDay)
//                    +":"+(minute<10?"0"+minute:minute)+":00";
            startTime = selectTime+" "+(hourOfDay<10?"0"+hourOfDay:hourOfDay)
                    +":"+(minute<30?"00":"30")+":00";

            startTimeTV.setText(startTime);
        }

        if (index == 2){
            endTimeTV.setText("");
            if (hourOfDay < sH || (hourOfDay == sH && minute <=sM)){
                showToast("结束时间选择无效");
                return;
            }
//            endTime = selectTime+" "+(hourOfDay<10?"0"+hourOfDay:hourOfDay)
//                    +":"+(minute<10?"0"+minute:minute)+":00";
            endTime = selectTime+" "+(hourOfDay<10?"0"+hourOfDay:hourOfDay)
                    +":"+(minute<30?"00":"30")+":00";

            endTimeTV.setText(endTime);
        }

    }

    /**
     * 选择时间对话框
     */
    private void showDateDialog(){
        final Dialog mDialog = new Dialog(this, R.style.custom_dialog);
        final View mDialogContentView= LayoutInflater.from(this).inflate(R.layout.dialog_item_date,null);
        final Calendar calendar = Calendar.getInstance();
        timePickerDialog = TimePickerDialog.newInstance(this, calendar.get(Calendar.HOUR_OF_DAY) ,calendar.get(Calendar.MINUTE), false, false);
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH)+1;
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePicker datePicker = (DatePicker) mDialogContentView.findViewById(R.id.dialog_item_date_picker);
        datePicker.setDate(year, month);
        datePicker.setMode(DPMode.SINGLE);

        datePicker.setOnDatePickedListener(new DatePicker.OnDatePickedListener() {
            @Override
            public void onDatePicked(String date) {
                if ("".equals(date)){
                    return;
                }

                if (index == 1){
                    selectTime = date;
                    startTimeTV.setText(selectTime);

                    timePickerDialog.setVibrate(true);
                    timePickerDialog.setCloseOnSingleTapMinute(true);
                    timePickerDialog.show(getSupportFragmentManager(), "timePicker");

                }

                if (index == 2){
                    endTime = date;
                    endTimeTV.setText(endTime);

                    timePickerDialog.setVibrate(true);
                    timePickerDialog.setCloseOnSingleTapMinute(true);
                    timePickerDialog.show(getSupportFragmentManager(), "timePicker");
                }

                mDialog.dismiss();
            }
        });
        mDialog.setContentView(mDialogContentView);
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.setCancelable(true);
        mDialog.getWindow().setGravity(Gravity.CENTER);
        mDialog.show();
        mDialog.getWindow().setWindowAnimations(R.style.anim_dialog);
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
    protected void onDestroy() {
        super.onDestroy();
    }
}
