package com.company.linquan.app.moduleMeeting.ui;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.company.linquan.app.R;
import com.company.linquan.app.base.BaseActivity;
import com.company.linquan.app.moduleMeeting.MeetingInterface;
import com.company.linquan.app.moduleMeeting.impl.CreateMeetingPresenterImp;
import com.company.linquan.app.util.ExitApp;
import com.company.linquan.app.util.MyDialog;
import com.company.linquan.app.util.MyToast;
import com.company.linquan.app.util.UploadPersonPhotoUtil;
import com.company.linquan.app.view.DelEditText;
import com.company.linquan.app.view.MyTextView;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;

import java.io.File;
import java.util.Calendar;

import cn.aigestudio.datepicker.cons.DPMode;
import cn.aigestudio.datepicker.views.DatePicker;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

/**
 * Created by YC on 2018/6/5.
 */

public class CreateMeetingActivity extends BaseActivity implements MeetingInterface.CreateMeetingInterface,View.OnClickListener
,TimePickerDialog.OnTimeSetListener{

    private Dialog myDialog;

    private EditText remarkET;
    private DelEditText titleET;
    private MyTextView startTimeTV, endTimeTV, personTV,addressTV;
    private String startTimeStr, endTimeStr,personStr,lon,lat;
    private CreateMeetingPresenterImp  presenter;
    private String mPhotoPath;
    private ImageView addIV;
    int sw;
    private String url;
    private final static int MAP = 1,MEMBER=2;
    private TimePickerDialog timePickerDialog;
    int index;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExitApp.getInstance().addActivity(this);		// add Activity 方便退出
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 开启自定义标题栏
        setContentView(R.layout.activity_create_meeting);
        judgePermission();
        initHead();
        initView();
    }


    private void judgePermission(){
        //特殊处理（6.0以上）
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Check Permissions Now
            // Callback onRequestPermissionsResult interceptado na Activity MainActivity
            PermissionGen.with(CreateMeetingActivity.this)
                    .addRequestCode(10)
                    .permissions(Manifest.permission.ACCESS_COARSE_LOCATION
                            ,Manifest.permission.ACCESS_FINE_LOCATION
                            ,Manifest.permission.WRITE_EXTERNAL_STORAGE
                            ,Manifest.permission.CAMERA
                            ,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .request();
            return;
        }
        startLocation();
    }

    private void startLocation(){
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位模式为AMapLocationMode.Battery_Saving，低功耗模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        //获取一次定位结果：
        mLocationOption.setInterval(1000);
        //该方法默认为false。
        mLocationOption.setOnceLocation(false);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //关闭缓存机制
        mLocationOption.setLocationCacheEnable(false);
        //设置定位回调监听
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {

                Log.i("setLocationListener","lon:"+aMapLocation.getLongitude()+"lat:"+aMapLocation.getLatitude());
                if (aMapLocation.getErrorCode() == 0) {
                    //可在其中解析amapLocation获取相应内容。
                    lon = aMapLocation.getLongitude()+"";
                    lat = aMapLocation.getLatitude()+"";
                    mLocationClient.stopLocation();
                }
            }
        });

        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    private void initHead() {
        RelativeLayout head = (RelativeLayout)findViewById(R.id.head_layout);
        TextView titleTV = (MyTextView)head.findViewById(R.id.head_top_title);
        titleTV.setText("创建会议");

        ImageView leftIV = (ImageView)head.findViewById(R.id.head_top_image);
        leftIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private void initView(){
        sw = getResources().getDisplayMetrics().widthPixels;
        presenter = new CreateMeetingPresenterImp(this);

        remarkET = (EditText)findViewById(R.id.createMeeting_content);
        titleET = (DelEditText) findViewById(R.id.createMeeting_title);
        titleET.setGravity(Gravity.RIGHT);
        titleET.setHint("请输入会议标题");

        addressTV = (MyTextView) findViewById(R.id.createMeeting_address);
        addressTV.setOnClickListener(this);
        startTimeTV = (MyTextView)findViewById(R.id.createMeeting_date);
        startTimeTV.setOnClickListener(this);
        endTimeTV = (MyTextView)findViewById(R.id.createMeeting_date_end);
        endTimeTV.setOnClickListener(this);
        personTV = (MyTextView)findViewById(R.id.createMeeting_member);
        personTV.setOnClickListener(this);
        addIV = (ImageView)findViewById(R.id.createMeeting_add);
        addIV.setOnClickListener(this);
        findViewById(R.id.createMeeting_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.createMeeting_btn){
//            if (TextUtils.isEmpty(titleET.getText())
//                    || TextUtils.isEmpty(addressTV.getText())
//                    || TextUtils.isEmpty(remarkET.getText())){
//                return;
//            }

            presenter.createMeeting(titleET.getText().toString().trim(),startTimeStr,endTimeStr,addressTV.getText().toString()
                ,remarkET.getText().toString().trim(),url,personStr,lon,lat);
        }
        if (view.getId() == R.id.createMeeting_address){
            Intent intent = new Intent(CreateMeetingActivity.this,MapActivity.class);
            startActivityForResult(intent,MAP);

        }
        if (view.getId() == R.id.createMeeting_date){
            index = 1;
            showDateDialog();

        }
        if (view.getId() == R.id.createMeeting_date_end){
            index = 2;
//            showDateDialog();
            timePickerDialog.setVibrate(true);
            timePickerDialog.setCloseOnSingleTapMinute(true);
            timePickerDialog.show(getSupportFragmentManager(), "timePicker");

        }
        if (view.getId() == R.id.createMeeting_member){
            Intent intent = new Intent(CreateMeetingActivity.this,SelectPersonActivity.class);
            intent.putExtra("ids",personStr);
            intent.putExtra("index",0);
            startActivityForResult(intent,MEMBER);
        }
        if (view.getId() == R.id.createMeeting_add){
            showCameraDialog();
        }

    }


    /**
     * @param view      The view associated with this listener.
     * @param hourOfDay The hour that was set.
     * @param minute    The minute that was set.
     */
    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        if (index == 1){
            startTimeStr = startTimeStr+" "+hourOfDay+":"+minute+":00";
            startTimeTV.setText(startTimeStr);
        }

        if (index == 2){
            endTimeStr = endTimeStr+" "+hourOfDay+":"+minute+":00";
            endTimeTV.setText(endTimeStr);
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
                    startTimeStr = date;
                    endTimeStr = date;
                    startTimeTV.setText(startTimeStr);

                    timePickerDialog.setVibrate(true);
                    timePickerDialog.setCloseOnSingleTapMinute(true);
                    timePickerDialog.show(getSupportFragmentManager(), "timePicker");

                }

                if (index == 2){
                    endTimeStr = date;
                    endTimeTV.setText(endTimeStr);

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


    /**
     * 选择拍照
     */
    private void showCameraDialog(){
        final Dialog mDialog = new Dialog(getContext(), R.style.custom_dialog);
        final View mDialogContentView= LayoutInflater.from(getContext()).inflate(R.layout.dialog_item_camera_select,null);

        MyTextView cameraTV = (MyTextView)mDialogContentView.findViewById(R.id.dialog_item_camera);
        MyTextView albumTV = (MyTextView)mDialogContentView.findViewById(R.id.dialog_item_album);

        cameraTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();

                //特殊处理（6.0以上）
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    // Check Permissions Now
                    // Callback onRequestPermissionsResult interceptado na Activity MainActivity
                    PermissionGen.with(CreateMeetingActivity.this)
                            .addRequestCode(10)
                            .permissions(
                                    Manifest.permission.CAMERA,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            .request();
                    return;
                }
                mPhotoPath = UploadPersonPhotoUtil.takePhoto(CreateMeetingActivity.this);
            }
        });

        albumTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
                UploadPersonPhotoUtil.selectImageFromLocal(CreateMeetingActivity.this);
            }
        });

        mDialog.setContentView(mDialogContentView);
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.setCancelable(true);
        mDialog.getWindow().setGravity(Gravity.CENTER);
        WindowManager.LayoutParams p = mDialog.getWindow().getAttributes(); // 获取对话框当前的参数值
        p.width = (int) (getResources().getDisplayMetrics().widthPixels * 0.85); // 宽度设置为屏幕的0.85
        mDialog.getWindow().setAttributes(p);
        mDialog.show();
        mDialog.getWindow().setWindowAnimations(R.style.anim_dialog);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @PermissionSuccess(requestCode = 10)
    public void doSomething(){
        startLocation();
    }

    @PermissionFail(requestCode = 10)
    public void doFailSomething(){
        PermissionGen.with(CreateMeetingActivity.this)
                .addRequestCode(10)
                .permissions(Manifest.permission.CAMERA
                        ,Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ,Manifest.permission.ACCESS_COARSE_LOCATION
                        ,Manifest.permission.ACCESS_FINE_LOCATION
                        ,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .request();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MAP && resultCode == RESULT_OK){
            addressTV.setText(data.getStringExtra("address"));
            return;
        }

        if (requestCode == MEMBER && resultCode == RESULT_OK){
            personStr = data.getStringExtra("ids");
            personTV.setText(data.getStringExtra("names"));
            return;
        }

        if(requestCode == UploadPersonPhotoUtil.REQUEST_CODE_TAKE_PHOTO){
            if(mPhotoPath == null){
                return;
            }
            File file = new File(mPhotoPath);
            if (file.isFile()) {
                Bitmap mBitmap = UploadPersonPhotoUtil.getBitmapFromFile(mPhotoPath, 720,1280);
                UploadPersonPhotoUtil.savePicToSdcard(mBitmap,mPhotoPath);
                mBitmap.recycle();
                mBitmap = null;
                int w = (112*sw)/720;
                Glide.with(CreateMeetingActivity.this).load(mPhotoPath).apply(new RequestOptions().error(R.drawable.img_me_photo).override(w,w).centerCrop()).into(addIV);
            }
            presenter.upload(mPhotoPath);

        }

        if(requestCode == UploadPersonPhotoUtil.REQUEST_CODE_SELECT_PICTURE) {
            mPhotoPath = UploadPersonPhotoUtil.getPathFromIntent(data, CreateMeetingActivity.this);
            if (mPhotoPath == null) {
                Toast.makeText(CreateMeetingActivity.this, "选择照片失败", Toast.LENGTH_SHORT).show();
                return;
            }
            File file = new File(mPhotoPath);
            if (file.isFile()) {
                Bitmap mBitmap = UploadPersonPhotoUtil.getBitmapFromFile(mPhotoPath, 720, 1280);
                UploadPersonPhotoUtil.savePicToSdcard(mBitmap, mPhotoPath);
                mBitmap.recycle();
                mBitmap = null;
                int w = (112 * sw) / 720;
                Glide.with(CreateMeetingActivity.this).load(mPhotoPath).apply(new RequestOptions().error(R.drawable.img_me_photo).override(w,w).centerCrop()).into(addIV);
                presenter.upload(mPhotoPath);
            } else {
                Toast.makeText(CreateMeetingActivity.this, "选择照片失败", Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void finishActivity() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showDialog() {
        if (myDialog == null) myDialog = MyDialog.showLoadingDialog(this);
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
    public void initPath(String url) {
        this.url = url;
    }
}
