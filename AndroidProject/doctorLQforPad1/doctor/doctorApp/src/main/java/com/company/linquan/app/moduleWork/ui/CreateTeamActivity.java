package com.company.linquan.app.moduleWork.ui;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.company.linquan.app.R;
import com.company.linquan.app.base.BaseActivity;
import com.company.linquan.app.bean.FriendsBean;
import com.company.linquan.app.moduleWork.WorkInterface;
import com.company.linquan.app.moduleWork.imp.CreateTeamPresenterImp;
import com.company.linquan.app.util.ExitApp;
import com.company.linquan.app.util.MyDialog;
import com.company.linquan.app.util.MyToast;
import com.company.linquan.app.util.UploadPersonPhotoUtil;
import com.company.linquan.app.view.DelEditText;
import com.company.linquan.app.view.MyTextView;
import com.company.linquan.app.view.RoundImageView;

import java.io.File;
import java.util.ArrayList;

import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

/**
 * Created by YC on 2018/6/17.
 */

public class CreateTeamActivity extends BaseActivity implements WorkInterface.CreateTeamInterface,View.OnClickListener{

    private Dialog myDialog;

    private RecyclerView recordRecycler;
    private ArrayList<FriendsBean> array;
    private RecordListAdapter adapter;

    private DelEditText titleET, amountET;
    private EditText contentET;

    private CreateTeamPresenterImp presenter;
    private String logoUrl = "", memberStr = "";
    int sw;

    private ImageView addImage;
    private String mPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExitApp.getInstance().addActivity(this);		// add Activity 方便退出
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 开启自定义标题栏
        setContentView(R.layout.activity_create_team);
        initHead();
        initView();
        setListener();
        getData();
    }

    private void getData() {
        presenter.getFriends();
    }


    private void initHead() {
        RelativeLayout head = (RelativeLayout) findViewById(R.id.head_layout);
        MyTextView title = (MyTextView) head.findViewById(R.id.head_top_title);
        title.setText("创建名医团");
        ImageView rightIV = (ImageView)head.findViewById(R.id.head_top_image);
        rightIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView(){
        sw = getContext().getResources().getDisplayMetrics().widthPixels;
        presenter = new CreateTeamPresenterImp(this);

        findViewById(R.id.create_team_btn).setOnClickListener(this);
        recordRecycler = (RecyclerView)findViewById(R.id.create_team_recycler);
        recordRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));
        array = new ArrayList<>();
        adapter = new RecordListAdapter(getContext(),array);
        recordRecycler.setAdapter(adapter);
        recordRecycler.setItemAnimator(new DefaultItemAnimator());

        addImage = (ImageView)findViewById(R.id.create_team_image_add);
        addImage.setOnClickListener(this);

        titleET = (DelEditText)findViewById(R.id.create_team_title);
        titleET.setHint("请输入标题");
        titleET.setGravity(Gravity.RIGHT);

        contentET = (EditText)findViewById(R.id.create_team_content);
        contentET.setHint("请输入名医团内容介绍");

        amountET = (DelEditText)findViewById(R.id.create_team_price);
        amountET.setHint("请输入金额");
        amountET.setGravity(Gravity.RIGHT);
    }

    private void setListener() {
        adapter.setOnItemClickListener(new OnItemClick() {
            @Override
            public void onItemClick(View view, int position) {
                array.get(position).setSelected(!array.get(position).isSelected());
                adapter.setList(array);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.create_team_btn:
                if (TextUtils.isEmpty(titleET.getText().toString())) return;
                if (TextUtils.isEmpty(contentET.getText().toString())) return;
                if (TextUtils.isEmpty(amountET.getText().toString())) return;
                if (TextUtils.isEmpty(mPhotoPath)) return;

                for (FriendsBean bean:array){
                    memberStr = memberStr+bean.getFriendID()+",";
                }
                presenter.createTeam(titleET.getText().toString().trim(),mPhotoPath,contentET.getText().toString().trim()
                ,amountET.getText().toString().trim(),memberStr.substring(0,memberStr.length()-1));
                break;

            case R.id.create_team_image_add:
                showCameraDialog();
                break;
        }
    }


    /**
     * 选择拍照
     */
    private void showCameraDialog(){
        final Dialog mDialog = new Dialog(CreateTeamActivity.this, R.style.custom_dialog);
        final View mDialogContentView= LayoutInflater.from(CreateTeamActivity.this).inflate(R.layout.dialog_item_camera_select,null);

        MyTextView cameraTV = (MyTextView)mDialogContentView.findViewById(R.id.dialog_item_camera);
        MyTextView albumTV = (MyTextView)mDialogContentView.findViewById(R.id.dialog_item_album);

        cameraTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();

                //特殊处理（6.0以上）
                if (ActivityCompat.checkSelfPermission(CreateTeamActivity.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    // Check Permissions Now
                    // Callback onRequestPermissionsResult interceptado na Activity MainActivity
                    PermissionGen.with(CreateTeamActivity.this)
                            .addRequestCode(10)
                            .permissions(
                                    Manifest.permission.CAMERA,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            .request();
                    return;
                }
                mPhotoPath = UploadPersonPhotoUtil.takePhoto(CreateTeamActivity.this);
            }
        });

        albumTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
                UploadPersonPhotoUtil.selectImageFromLocal(CreateTeamActivity.this);
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
        mPhotoPath = UploadPersonPhotoUtil.takePhoto(CreateTeamActivity.this);
    }

    @PermissionFail(requestCode = 10)
    public void doFailSomething(){
        PermissionGen.with(CreateTeamActivity.this)
                .addRequestCode(10)
                .permissions(Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .request();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                int sw = getResources().getDisplayMetrics().widthPixels;
                int w = (112*sw)/720;
                 Glide.with(CreateTeamActivity.this).load(mPhotoPath).apply(new RequestOptions().error(R.drawable.img_auth_add).override(w,w).centerCrop()).into(addImage);
            }
        }

        if(requestCode == UploadPersonPhotoUtil.REQUEST_CODE_SELECT_PICTURE) {
            mPhotoPath = UploadPersonPhotoUtil.getPathFromIntent(data, CreateTeamActivity.this);
            if (mPhotoPath == null) {
                Toast.makeText(CreateTeamActivity.this, "选择照片失败", Toast.LENGTH_SHORT).show();
                return;
            }
            File file = new File(mPhotoPath);
            if (file.isFile()) {
                Bitmap mBitmap = UploadPersonPhotoUtil.getBitmapFromFile(mPhotoPath, 720, 1280);
                UploadPersonPhotoUtil.savePicToSdcard(mBitmap, mPhotoPath);
                mBitmap.recycle();
                mBitmap = null;
                int sw = getResources().getDisplayMetrics().widthPixels;
                int w = (112 * sw) / 720;
                Glide.with(CreateTeamActivity.this).load(mPhotoPath).apply(new RequestOptions().error(R.drawable.img_auth_add).override(w,w).centerCrop()).into(addImage);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * recycler 适配器
     */
    private class RecordListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        private Context mContext;
        private ArrayList<FriendsBean> mList;
        private OnItemClick onItemClickListener;

        public RecordListAdapter(Context context,ArrayList<FriendsBean> list ){
            this.mContext = context;
            this.mList = list;
        }


        public void setList(ArrayList<FriendsBean> list){
            this.mList = list;
            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext)
                    .inflate(R.layout.list_item_create_team,parent,false);
            return new RecordViewHolder(view,onItemClickListener);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if(holder instanceof RecordViewHolder){
                initView((RecordViewHolder) holder, mList.get(position));
            }
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        private void setOnItemClickListener(OnItemClick listener){
            onItemClickListener = listener;
        }

        private void initView(RecordViewHolder handler, FriendsBean bean){
            if (bean == null)return;
            handler.nameTV.setText(bean.getFriendName());
            handler.addressTV.setText(bean.getHospitalName());
            handler.jobTV.setText(bean.getDepartmentName());

            int w2 = (94*sw)/720;
            int h2 = (94*sw)/720;
            Glide.with(mContext).load(bean.getHeadUrl()).apply(new RequestOptions().override(w2,h2).centerCrop()).into(handler.photoIV);

            if (bean.isSelected()){
                handler.selectIV.setBackgroundResource(R.drawable.img_stop_face_selected);
            }else{
                handler.selectIV.setBackgroundResource(R.drawable.img_stop_face_select);
            }
        }
    }

    public interface OnItemClick{
        public void onItemClick(View view, int position);
    }

    /**
     * 静态类
     */
    private static class RecordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public RoundImageView photoIV;
        public ImageView selectIV;
        public MyTextView nameTV;
        public MyTextView addressTV;
        public MyTextView jobTV;
        private OnItemClick onItemClick;

        public RecordViewHolder(View view, OnItemClick onItemClick){
            super(view);
            this.onItemClick = onItemClick;
            view.setOnClickListener(this);
            addressTV = (MyTextView) view.findViewById(R.id.list_item_address);
            photoIV = (RoundImageView) view.findViewById(R.id.list_item_photo);
            photoIV.setDrawCircle();
            selectIV = (ImageView) view.findViewById(R.id.list_item_image);
            nameTV = (MyTextView) view.findViewById(R.id.list_item_name);
            jobTV = (MyTextView) view.findViewById(R.id.list_item_job);
        }

        @Override
        public void onClick(View view) {
            if(onItemClick != null){
                onItemClick.onItemClick(view,getLayoutPosition());
            }
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
    public void reloadList(ArrayList<FriendsBean> list) {
        array = list;
        adapter.setList(array);
    }

    @Override
    public void enterDetail() {
        Intent intent = new Intent();
        intent.setClass(CreateTeamActivity.this, TeamDetailActivity.class);
        startActivity(intent);
        finish();
    }
}
