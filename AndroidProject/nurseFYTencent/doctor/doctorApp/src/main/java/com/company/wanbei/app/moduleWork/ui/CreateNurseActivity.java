package com.company.wanbei.app.moduleWork.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

//import com.codbking.widget.DatePickDialog;
//import com.codbking.widget.OnChangeLisener;
//import com.codbking.widget.OnSureLisener;
//import com.codbking.widget.bean.DateType;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.codbking.widget.OnSureLisener;
import com.company.wanbei.app.base.BaseActivity;
import com.company.wanbei.app.bean.NurseInfoBean;
import com.company.wanbei.app.bean.NurseServeBean;
import com.company.wanbei.app.bean.NurseTypeBean;
import com.tencent.qcloud.tuikit.tuichat.fromApp.config.C;
import com.company.wanbei.app.moduleWork.WorkInterface;
import com.company.wanbei.app.moduleWork.imp.CreateNursePresenterImp;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.Base64;
import com.company.wanbei.app.util.ExitApp;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.MyDialog;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.MyToast;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.ToolSharePerference;
import com.company.wanbei.app.util.widget.DatePickDialog1;
import com.company.wanbei.app.util.widget.OnChangeLisener;
import com.company.wanbei.app.util.widget.bean.DateType;
import com.tencent.qcloud.tuikit.tuichat.fromApp.view.MyTextView;
import com.company.wanbei.app.R;
import com.sleepbot.datetimepicker.time.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by YC on 2018/6/11.
 */

public class CreateNurseActivity extends BaseActivity implements WorkInterface.CreateNurseInterface, View.OnClickListener {

    private Dialog myDialog;
    private CreateNursePresenterImp presenter;
    private EditText addressET,amountET,numET;
    private MyTextView startTimeTV, endTimeTV;
    private String startTime = "",endTime ="",lon,lat,room,appointAmount;
    private TimePickerDialog timePickerDialog;
    private Date start,end;
    int index;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private int sH,sM;
    private String selectTime = "";
    private String nurseTypeID = "";
    private String nurseSeriveID = "";
    private MyTextView selectNurseTypeTV;
    private MyTextView selectNurseServeTV;

    private MyTextView serviceNameTV;
    private MyTextView servicePriceTV;
    private MyTextView realPriceTV;
    private MyTextView introductionTV;
    private MyTextView useMansTV;
    private MyTextView tabooTipsTV;
    private MyTextView appointmentNoticeTV;
//    private MyTextView serviceTimeTV;
    private MyTextView refundRulesTV;
//    private MyTextView expendPriceTV;
//    private MyTextView homeGoodsListTV;
    private MyTextView maxPlayersTV;
//    private MyTextView expendNumTV;
//    private MyTextView goodsNameTV;
//    private MyTextView specsTV;



    private static final int SELECT = 1;
    private static final int SELECTSERVE = 2;

    private DatePickDialog1 dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExitApp.getInstance().addActivity(this);		// add Activity 方便退出
//        requestWindowFeature(Window.FEATURE_NO_TITLE); // 开启自定义标题栏
        setContentView(R.layout.activity_create_nurse);
        initHead();
        initView();
    }

    private void initHead() {
        RelativeLayout head = (RelativeLayout) findViewById(R.id.head_layout);
        MyTextView title = (MyTextView) head.findViewById(R.id.head_top_title);
        title.setText("创建护理");
        ImageView rightIV = (ImageView)head.findViewById(R.id.head_top_image);
        rightIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView(){
        presenter = new CreateNursePresenterImp(this);
        findViewById(R.id.create_nurse_btn).setOnClickListener(this);
        serviceNameTV = (MyTextView) findViewById(R.id.service_name);
        servicePriceTV = (MyTextView) findViewById(R.id.service_price);
        realPriceTV = (MyTextView) findViewById(R.id.real_price);
        introductionTV = (MyTextView) findViewById(R.id.introduction);
        introductionTV.setMovementMethod(ScrollingMovementMethod.getInstance());
        introductionTV.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    //通知父控件不要干扰
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                }
                if(event.getAction()==MotionEvent.ACTION_MOVE){
                    //通知父控件不要干扰
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                }
                if(event.getAction()==MotionEvent.ACTION_UP){
                    v.getParent().requestDisallowInterceptTouchEvent(false);
                }
                return false;
            }
        });

        useMansTV = (MyTextView) findViewById(R.id.use_mans);
        tabooTipsTV = (MyTextView) findViewById(R.id.tabooTips);
        tabooTipsTV.setMovementMethod(ScrollingMovementMethod.getInstance());
        tabooTipsTV.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    //通知父控件不要干扰
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                }
                if(event.getAction()==MotionEvent.ACTION_MOVE){
                    //通知父控件不要干扰
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                }
                if(event.getAction()==MotionEvent.ACTION_UP){
                    v.getParent().requestDisallowInterceptTouchEvent(false);
                }
                return false;
            }
        });

        appointmentNoticeTV = (MyTextView) findViewById(R.id.appointmentNotice);
        appointmentNoticeTV.setMovementMethod(ScrollingMovementMethod.getInstance());
        appointmentNoticeTV.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    //通知父控件不要干扰
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                }
                if(event.getAction()==MotionEvent.ACTION_MOVE){
                    //通知父控件不要干扰
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                }
                if(event.getAction()==MotionEvent.ACTION_UP){
                    v.getParent().requestDisallowInterceptTouchEvent(false);
                }
                return false;
            }
        });

//        serviceTimeTV = (MyTextView) findViewById(R.id.serviceTime);
        refundRulesTV = (MyTextView) findViewById(R.id.refundRules);
        refundRulesTV.setMovementMethod(ScrollingMovementMethod.getInstance());
        refundRulesTV.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    //通知父控件不要干扰
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                }
                if(event.getAction()==MotionEvent.ACTION_MOVE){
                    //通知父控件不要干扰
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                }
                if(event.getAction()==MotionEvent.ACTION_UP){
                    v.getParent().requestDisallowInterceptTouchEvent(false);
                }
                return false;
            }
        });

//        expendPriceTV = (MyTextView) findViewById(R.id.expendPrice);
//        homeGoodsListTV = (MyTextView) findViewById(R.id.homeGoodsList);
        maxPlayersTV = (MyTextView) findViewById(R.id.maxPlayers);
//        expendNumTV = (MyTextView) findViewById(R.id.expendNum);
//        goodsNameTV = (MyTextView) findViewById(R.id.goodsName);
//        specsTV = (MyTextView) findViewById(R.id.specs);


        startTimeTV = (MyTextView) findViewById(R.id.create_nurse_start_time);
        endTimeTV = (MyTextView) findViewById(R.id.create_nurse_end_time);
        startTimeTV.setOnClickListener(this);
        endTimeTV.setOnClickListener(this);


        selectNurseTypeTV = (MyTextView)findViewById(R.id.select_nurse_type);
        selectNurseTypeTV.setOnClickListener(this);
        selectNurseServeTV = (MyTextView)findViewById(R.id.select_nurse_service);
        selectNurseServeTV.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.select_nurse_type:
//                startActivityForResult(new Intent(CreateNurseActivity.this, SelectNurseTypeActivity.class),SELECT);
                presenter.getNurseType();
                break;
            case R.id.select_nurse_service:
//                Intent intent = new Intent();
//                intent.setClass(CreateNurseActivity.this, SelectNurseServiceActivity.class);
//                intent.putExtra("ids",nurseTypeID);
//                startActivityForResult(intent,SELECTSERVE);

                presenter.getNurseService(ToolSharePerference.getStringData(getContext(), C.fileconfig, C.UserID),nurseTypeID);
                break;

            case R.id.create_nurse_btn:
                if (TextUtils.isEmpty(selectNurseTypeTV.getText().toString())){
                    showToast("请选择护理类型");
                    return;
                }
                if (TextUtils.isEmpty(selectNurseServeTV.getText().toString())){
                    showToast("请选择护理服务");
                    return;
                }
                if (TextUtils.isEmpty(startTime)){
                    showToast("请先选择开始时间");
                    return;
                }
                if (TextUtils.isEmpty(endTime)){
                    showToast("请先选择结束时间");
                    return;
                }
                presenter.createNurse(nurseSeriveID,selectNurseTypeTV.getText().toString(),startTime
                ,endTime);
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
                dialog.show();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SELECT && resultCode == RESULT_OK){
            selectNurseTypeTV.setText(data.getStringExtra("nurseType"));
            nurseTypeID=data.getStringExtra("nurseTypeID");
        }
        if (requestCode == SELECTSERVE && resultCode == RESULT_OK){
            selectNurseServeTV.setText(data.getStringExtra("nurseService"));
            nurseSeriveID=data.getStringExtra("nurseServeID");
            presenter.getNursingTypeInfo(nurseSeriveID);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * @param view      The view associated with this listener.
     * @param hourOfDay The hour that was set.
     * @param minute    The minute that was set.
     */
//    @Override
//    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
//        if (index == 1){
//            sH = hourOfDay;
//            sM = minute;
////            startTime = selectTime+" "+(hourOfDay<10?"0"+hourOfDay:hourOfDay)
////                    +":"+(minute<10?"0"+minute:minute)+":00";
//            startTime = selectTime+" "+(hourOfDay<10?"0"+hourOfDay:hourOfDay)
//                    +":"+(minute<30?"00":"30")+":00";
//
//            startTimeTV.setText(startTime);
//        }
//
//        if (index == 2){
//            endTimeTV.setText("");
//            if (hourOfDay < sH || (hourOfDay == sH && minute <=sM)){
//                showToast("结束时间选择无效");
//                return;
//            }
////            endTime = selectTime+" "+(hourOfDay<10?"0"+hourOfDay:hourOfDay)
////                    +":"+(minute<10?"0"+minute:minute)+":00";
//            endTime = selectTime+" "+(hourOfDay<10?"0"+hourOfDay:hourOfDay)
//                    +":"+(minute<30?"00":"30")+":00";
//
//            endTimeTV.setText(endTime);
//        }
//
//    }

    /**
     * 选择时间对话框
     */
    private void showDateDialog(){
        dialog = new DatePickDialog1(this);
        //设置上下年分限制
        dialog.setYearLimt(0);
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
                Calendar calCurrent=Calendar.getInstance();
                if (index == 1){
                    if((cal.getTimeInMillis()-calCurrent.getTimeInMillis())/ (1000 * 60 * 60 * 24)>5){
                        showToast("开始时间超过七天");
                        return;
                    }
                    if((cal.getTimeInMillis()-calCurrent.getTimeInMillis())/ (1000 * 60 * 60 * 24)<0){
                        showToast("开始时间不能小于当前时间");
                        return;
                    }
                    start = date;
                    startTime = datestr;
                    startTimeTV.setText(startTime);
                }

                if (index == 2) {
                    end = date;
                    if(end.before(start)||start.equals(end)){
                        showToast("结束时间选择无效");
                        return;
                    }
                    endTime = datestr;
                    endTimeTV.setText(endTime);
                }
            }
        });
        dialog.show();
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

    @Override
    public void reloadNurseInfo(ArrayList<NurseInfoBean> bean) {
        serviceNameTV.setText(bean.get(0).getServiceName());
        servicePriceTV.setText(bean.get(0).getServicePrice());
        realPriceTV.setText(bean.get(0).getRealPrice());
        introductionTV.setText(bean.get(0).getIntroduction());
        useMansTV.setText(bean.get(0).getUseMans());

        String subTitle = new String(Base64.decode(bean.get(0).getTabooTips()));
        tabooTipsTV.setText(subTitle);

        appointmentNoticeTV.setText(bean.get(0).getAppointmentNotice());
//        serviceTimeTV.setText(bean.get(0).getServiceTime());
        refundRulesTV.setText(bean.get(0).getRefundRules());
//        expendPriceTV.setText(bean.get(0).getExpendPrice());
//        homeGoodsListTV.setText(bean.get(0).getHomeGoodsList());
        maxPlayersTV.setText(bean.get(0).getMaxPlayers());
//        expendNumTV.setText(bean2.get(0).getExpendNum());
//        goodsNameTV.setText(bean2.get(0).getGoodsName());
//        specsTV.setText(bean2.get(0).getSpecs());
    }

    @Override
    public void showTypeDialog(ArrayList<NurseTypeBean> beans) {
        List list = new ArrayList<>();
        for(int i=0;i<beans.size();i++){
            list.add(beans.get(i).getTypeName());
        }
        //条件选择器
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                if(!nurseTypeID.equals(beans.get(options1).getId())){
                    nurseSeriveID = "";
                    selectNurseServeTV.setText("请选择名称");
                }
                selectNurseTypeTV.setText(beans.get(options1).getTypeName());
                nurseTypeID=beans.get(options1).getId();

            }
        }).build();
        pvOptions.setPicker(list);
        pvOptions.show();
    }

    @Override
    public void showServiceDialog(ArrayList<NurseServeBean> beans) {
        if(beans.size()<=0){
            showToast("未添加该护理类型下的护理服务");
            return;
        }
        List list = new ArrayList<>();
        for(int i=0;i<beans.size();i++){
            list.add(beans.get(i).getServiceName());
        }
        //条件选择器
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                selectNurseServeTV.setText(beans.get(options1).getServiceName());
                nurseSeriveID=beans.get(options1).getId();
                presenter.getNursingTypeInfo(nurseSeriveID);
            }
        }).build();
        pvOptions.setPicker(list);
        pvOptions.show();
    }
}
