package com.company.linquan.app.moduleCenter.ui;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.company.linquan.app.R;
import com.company.linquan.app.base.BaseActivity;
import com.company.linquan.app.config.C;
import com.company.linquan.app.moduleCenter.UserCenterInterface;
import com.company.linquan.app.moduleCenter.imp.AccountPresenterImp;
import com.company.linquan.app.moduleHome.HomeActivity;
import com.company.linquan.app.moduleLogin.LoginActivity;
import com.company.linquan.app.util.ExitApp;
import com.company.linquan.app.util.MyDialog;
import com.company.linquan.app.util.MyToast;
import com.company.linquan.app.util.ToolSharePerference;
import com.company.linquan.app.util.ToolUtil;
import com.company.linquan.app.util.UploadPersonPhotoUtil;
import com.company.linquan.app.view.DelEditText;
import com.company.linquan.app.view.MyTextView;
import com.company.linquan.app.view.RoundImageView;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.auth.AuthService;

import java.io.File;

import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

/**
 * Created by YC on 2018/6/20.
 */

public class AccountActivity extends BaseActivity implements UserCenterInterface.AccountInterface,View.OnClickListener{

    private Dialog myDialog;
    private AccountPresenterImp presenter;
    private String url="";
    private String mPhotoPath;
    int sw;

    private RoundImageView photoIV;
    private MyTextView nameTV, mobileTV,bindTV,versionTV;
    private final static int LOGIN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExitApp.getInstance().addActivity(this);		// add Activity 方便退出
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 开启自定义标题栏
        setContentView(R.layout.activity_account);
        initHead();
        initView();
    }

    private void initHead() {
        RelativeLayout head = (RelativeLayout) findViewById(R.id.head_layout);
        MyTextView title = (MyTextView) head.findViewById(R.id.head_top_title);
        title.setText("设置");
        ImageView rightIV = (ImageView)head.findViewById(R.id.head_top_image);
        rightIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView(){
        sw = getResources().getDisplayMetrics().widthPixels;
        presenter = new AccountPresenterImp(this);
        findViewById(R.id.account_btn).setOnClickListener(this);
        nameTV = (MyTextView)findViewById(R.id.account_name);
        mobileTV= (MyTextView)findViewById(R.id.account_mobile);
        nameTV.setOnClickListener(this);
        bindTV= (MyTextView)findViewById(R.id.account_wx_bind);
        versionTV= (MyTextView)findViewById(R.id.account_version);
        versionTV.setOnClickListener(this);
        photoIV = (RoundImageView)findViewById(R.id.account_photo);
        photoIV.setDrawCircle();
        photoIV.setOnClickListener(this);
        findViewById(R.id.account_agree).setOnClickListener(this);
        findViewById(R.id.account_about).setOnClickListener(this);

        nameTV.setText(ToolSharePerference.getStringData(getContext(), C.fileconfig,C.UserName));
        mobileTV.setText(ToolSharePerference.getStringData(getContext(), C.fileconfig,C.MOBILE));
        versionTV.setText("V"+ ToolUtil.getApkVersion(getContext()));
        int w = (158*sw)/720;
        Glide.with(AccountActivity.this).load(ToolSharePerference.getStringData(getContext(), C.fileconfig,C.Photo)).apply(new RequestOptions().error(R.drawable.img_me_photo).override(w,w).centerCrop()).into(photoIV);
//        Glide.with(AccountActivity.this).load(ToolSharePerference.getStringData(getContext(), C.fileconfig,C.Photo))
//                .error(R.drawable.img_me_photo).override(w,w).centerCrop().into(photoIV);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.account_btn:
                clearData();
                Intent intent = new Intent(AccountActivity.this, LoginActivity.class);
                startActivityForResult(intent,LOGIN);
                break;
            case R.id.account_name:
                showChangeDialog();
                break;
            case R.id.account_version:
                presenter.checkVersion();
                break;
            case R.id.account_agree:

                break;
            case R.id.account_about:
                startActivity(new Intent(AccountActivity.this,AboutActivity.class));
                break;

            case R.id.account_photo:
                showCameraDialog();
                break;


        }
    }

    private void clearData(){
        ToolSharePerference.putStringData(getContext(),C.fileconfig,C.UserID,"");
        ToolSharePerference.putStringData(getContext(),C.fileconfig,C.UniqueID,"");
        ToolSharePerference.putStringData(getContext(),C.fileconfig,C.AUTH,"");

        ToolSharePerference.putStringData(getContext(), C.fileconfig,C.UserName,"");
        ToolSharePerference.putStringData(getContext(), C.fileconfig,C.MOBILE,"");
        ToolSharePerference.putStringData(getContext(), C.fileconfig,C.Photo,"");

        NIMClient.getService(AuthService.class).logout();
    }

    /**
     *  对话框
     */
    private void showChangeDialog(){
        final Dialog mDialog = new Dialog(this, R.style.custom_dialog);
        final View mDialogContentView= LayoutInflater.from(this).inflate(R.layout.dialog_item_change_info,null);

        final DelEditText nameET = (DelEditText)mDialogContentView. findViewById(R.id.dialog_item_edit);
        nameET.setHint("输入新昵称");
        nameET.setGravity(Gravity.CENTER);
        nameET.setText(nameTV.getText().toString());
        nameET.setLastFocus();

        MyTextView btn = (MyTextView) mDialogContentView.findViewById(R.id.dialog_item_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mDialog.dismiss();

                if ("".equals(nameET.getText())) return;
                presenter.updatePerson(nameET.getText(),"","");
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


    /**
     * 选择拍照
     */
    private void showCameraDialog(){
        //特殊处理（6.0以上）
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED
                ||
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
            // Check Permissions Now
            // Callback onRequestPermissionsResult interceptado na Activity MainActivity
            PermissionGen.with(AccountActivity.this)
                    .addRequestCode(10)
                    .permissions(
                            Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .request();
            return;
        }
        final Dialog mDialog = new Dialog(getContext(), R.style.custom_dialog);
        final View mDialogContentView= LayoutInflater.from(getContext()).inflate(R.layout.dialog_item_camera_select,null);

        MyTextView cameraTV = (MyTextView)mDialogContentView.findViewById(R.id.dialog_item_camera);
        MyTextView albumTV = (MyTextView)mDialogContentView.findViewById(R.id.dialog_item_album);

        cameraTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
                mPhotoPath = UploadPersonPhotoUtil.takePhoto(AccountActivity.this);
            }
        });

        albumTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
                UploadPersonPhotoUtil.selectImageFromLocal(AccountActivity.this);
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
        showCameraDialog();
    }

    @PermissionFail(requestCode = 10)
    public void doFailSomething(){
        PermissionGen.with(AccountActivity.this)
                .addRequestCode(10)
                .permissions(Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .request();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == LOGIN){
            if (resultCode == RESULT_OK){
                Intent intent = new Intent();
                intent.setClass(AccountActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
            if (resultCode == RESULT_CANCELED){
                Intent intent = new Intent();
                intent.setClass(AccountActivity.this, HomeActivity.class);
                intent.putExtra(HomeActivity.EXIT, true);
                startActivity(intent);
                finish();
            }
        }


        if(requestCode == UploadPersonPhotoUtil.REQUEST_CODE_TAKE_PHOTO){
            if(mPhotoPath == null){
                return;
            }
            File file = new File(mPhotoPath);
            if (file.isFile() && file.exists()) {
                Bitmap mBitmap = UploadPersonPhotoUtil.getBitmapFromFile(mPhotoPath, 720,1280);
                UploadPersonPhotoUtil.savePicToSdcard(mBitmap,mPhotoPath);
                mBitmap.recycle();
                mBitmap = null;
                int w = (158*sw)/720;
                Glide.with(AccountActivity.this).load(mPhotoPath).apply(new RequestOptions().error(R.drawable.img_me_photo).override(w,w).centerCrop()).into(photoIV);
                presenter.updatePerson("","",mPhotoPath);

            }

        }

        if(requestCode == UploadPersonPhotoUtil.REQUEST_CODE_SELECT_PICTURE) {
            mPhotoPath = UploadPersonPhotoUtil.getPathFromIntent(data, AccountActivity.this);
            if (mPhotoPath == null) {
                Toast.makeText(AccountActivity.this, "选择照片失败", Toast.LENGTH_SHORT).show();
                return;
            }
            File file = new File(mPhotoPath);
            if (file.isFile()) {
                Bitmap mBitmap = UploadPersonPhotoUtil.getBitmapFromFile(mPhotoPath, 320, 400);
                UploadPersonPhotoUtil.savePicToSdcard(mBitmap, mPhotoPath);
                mBitmap.recycle();
                mBitmap = null;
                int w = (158 * sw) / 720;
                Glide.with(AccountActivity.this).load(mPhotoPath).apply(new RequestOptions().error(R.drawable.img_me_photo).override(w,w).centerCrop()).into(photoIV);
//                presenter.upload(mPhotoPath);
                presenter.updatePerson("","",mPhotoPath);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
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

    @Override
    public void initPath(String url) {
        this.url = url;
        presenter.updatePerson("","",url);
    }

    @Override
    public void reloadView(String bean) {
        nameTV.setText(bean);
        ToolSharePerference.putStringData(getContext(),C.fileconfig,C.UserName,bean);
    }
}
