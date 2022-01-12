package com.company.linquan.app.moduleWork.ui;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.company.linquan.app.R;
import com.company.linquan.app.base.BaseActivity;
import com.company.linquan.app.moduleMeeting.ui.MapActivity;
import com.company.linquan.app.moduleWork.WorkInterface;
import com.company.linquan.app.moduleWork.imp.AddAddressPresenterImp;
import com.company.linquan.app.util.ExitApp;
import com.company.linquan.app.util.MyDialog;
import com.company.linquan.app.util.MyToast;
import com.company.linquan.app.view.DelEditText;
import com.company.linquan.app.view.MyTextView;

import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

/**
 * Created by YC on 2018/8/4.
 */

public class AddAddressActivity extends BaseActivity implements WorkInterface.AddAddressInterface
,View.OnClickListener{

    private Dialog myDialog;
    private MyTextView selectTV;
    private DelEditText doorET;
    private final static int SELECT = 1;
    private String lat, lon;
    private AddAddressPresenterImp presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExitApp.getInstance().addActivity(this);		// add Activity 方便退出
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 开启自定义标题栏
        setContentView(R.layout.activity_add_address);
        initHead();
        initView();
    }

    private void initHead() {
        RelativeLayout head = (RelativeLayout) findViewById(R.id.head_layout);
        MyTextView title = (MyTextView) head.findViewById(R.id.head_top_title);
        title.setText("添加地址");
        ImageView leftIV = (ImageView)head.findViewById(R.id.head_top_image);
        leftIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView(){
        presenter = new AddAddressPresenterImp(this);
        findViewById(R.id.add_address_btn).setOnClickListener(this);
        selectTV = (MyTextView)findViewById(R.id.add_address_select);
        selectTV.setOnClickListener(this);
        doorET = (DelEditText)findViewById(R.id.add_address_door);
        doorET.setHint("请输入楼栋号或者门牌号码");
        doorET.setGravity(Gravity.RIGHT|Gravity.CENTER_VERTICAL);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add_address_select:
                if (ActivityCompat.checkSelfPermission(AddAddressActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED
                        ||
                        ActivityCompat.checkSelfPermission(AddAddressActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                                != PackageManager.PERMISSION_GRANTED
                        ||
                        ActivityCompat.checkSelfPermission(AddAddressActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) {
                    // Check Permissions Now
                    // Callback onRequestPermissionsResult interceptado na Activity MainActivity
                    PermissionGen.with(AddAddressActivity.this)
                            .addRequestCode(10)
                            .permissions(
                                    Manifest.permission.ACCESS_COARSE_LOCATION,
                                    Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            .request();
                    return;
                }

                startActivityForResult(new Intent(AddAddressActivity.this, MapActivity.class),SELECT);
                break;

            case R.id.add_address_btn:
                if (TextUtils.isEmpty(selectTV.getText().toString().trim())){
                    showToast("请选择地址");
                    return;
                }
                if (TextUtils.isEmpty(doorET.getText().toString().trim())){
                    showToast("请输入楼栋号或者门牌号码");
                    return;
                }
                presenter.addAddress(selectTV.getText().toString().trim(),doorET.getText(),lat,lon);
                break;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @PermissionSuccess(requestCode = 10)
    public void doSomething(){
        startActivityForResult(new Intent(AddAddressActivity.this, MapActivity.class),SELECT);

    }

    @PermissionFail(requestCode = 10)
    public void doFailSomething(){
        PermissionGen.with(AddAddressActivity.this)
                .addRequestCode(10)
                .permissions(Manifest.permission.ACCESS_COARSE_LOCATION
                        ,Manifest.permission.ACCESS_FINE_LOCATION
                        ,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .request();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SELECT && resultCode == RESULT_OK){
            lat = data.getExtras().getString("lat");
            lon = data.getExtras().getString("lon");
            selectTV.setText(data.getExtras().getString("address"));
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void finishActivity() {
        Intent intent = new Intent();
        intent.putExtra("address",selectTV.getText().toString());
        intent.putExtra("room",doorET.getText().toString());
        intent.putExtra("lon",lon);
        intent.putExtra("lat",lat);
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
