package com.company.linquan.app.moduleWork.ui;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.company.linquan.app.R;
import com.company.linquan.app.base.BaseActivity;
import com.company.linquan.app.bean.RecipeBean;
import com.company.linquan.app.bean.UserInfoBean;
import com.company.linquan.app.bean.VisitRecordBean;
import com.company.linquan.app.moduleWork.WorkInterface;
import com.company.linquan.app.moduleWork.imp.PatientInfoPresenterImp;
import com.company.linquan.app.util.ExitApp;
import com.company.linquan.app.util.MyDialog;
import com.company.linquan.app.util.MyToast;
import com.company.linquan.app.view.CommonRecyclerView;
import com.company.linquan.app.view.MyTextView;
import com.company.linquan.app.view.RoundImageView;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.visitinfo.VisitInfomation;

import java.util.ArrayList;

import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

import static com.company.linquan.app.config.C.VISIDS;
import static com.company.linquan.app.config.C.VISNAMES;

/**
 * Created by YC on 2018/6/21.
 */

public class PatientInfoActivity extends BaseActivity implements WorkInterface.PatientInfoInterface,View.OnClickListener{

    private Dialog myDialog;

    private CommonRecyclerView recyclerView, recordRecycler;
    private ArrayList<RecipeBean> array;
    private ArrayList<VisitRecordBean> arrayList;
    private MyListAdapter adapter;
    private RecordListAdapter recordListAdapter;

    private String patientId = "", name = "", mobile = "",url="",visitId = "",accountName="";
    private RoundImageView photoIV;
    private MyTextView nameTV, mobileTV;
    int sw ;
    private LinearLayout noDataLayout1,noDataLayout2;
    private PatientInfoPresenterImp presenter;
    private static final int ADD = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExitApp.getInstance().addActivity(this);		// add Activity 方便退出
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 开启自定义标题栏
        setContentView(R.layout.activity_patient_info);
        initData();
        initHead();
        initView();
        getData();
        setListener();
    }


    private void initData() {
        accountName=getIntent().getStringExtra("accountName");
        patientId = getIntent().getStringExtra("patientId");
        visitId = getIntent().getStringExtra("visitId");
        name = getIntent().getStringExtra("name");
        mobile = getIntent().getStringExtra("mobile");
        url = getIntent().getStringExtra("url");
    }

    private void initHead() {
        RelativeLayout head = (RelativeLayout) findViewById(R.id.layout_head);
        MyTextView title = (MyTextView)head. findViewById(R.id.head_top_title);
        title.setText("患者信息");
        ImageView rightIV = (ImageView)head.findViewById(R.id.head_top_image);
        rightIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView(){
        presenter = new PatientInfoPresenterImp(this);

        sw = getResources().getDisplayMetrics().widthPixels;
        photoIV = (RoundImageView)findViewById(R.id.patient_info_photo) ;
        photoIV.setDrawCircle();
        int w2 = (94*sw)/720;
        Glide.with(this).load(url).apply(new RequestOptions().override(w2,w2).centerCrop()).into(photoIV);

        nameTV = (MyTextView)findViewById(R.id.patient_info_name) ;
        nameTV.setText(name);

        mobileTV = (MyTextView)findViewById(R.id.patient_info_content) ;
        mobileTV.setText(mobile);

        //就诊记录
        recyclerView = (CommonRecyclerView)findViewById(R.id.patient_info_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));
        arrayList = new ArrayList<>();
        recordListAdapter = new RecordListAdapter(getContext(),arrayList);
        recyclerView.setAdapter(recordListAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //处方记录
        recordRecycler = (CommonRecyclerView)findViewById(R.id.patient_info_record_recycler);
        recordRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));
        array = new ArrayList<>();
        adapter = new MyListAdapter(getContext(),array);
        recordRecycler.setAdapter(adapter);
        recordRecycler.setItemAnimator(new DefaultItemAnimator());

        findViewById(R.id.patient_info_add).setOnClickListener(this);
        findViewById(R.id.patient_info_contact).setOnClickListener(this);

        noDataLayout1 = (LinearLayout)findViewById(R.id.patient_info_no_layout1);
        noDataLayout2 = (LinearLayout)findViewById(R.id.patient_info_no_layout2);
        noDataLayout1.setVisibility(View.VISIBLE);
        noDataLayout2.setVisibility(View.VISIBLE);
    }

    private void getData() {
        presenter.getRecipe(patientId,visitId);
        presenter.getVisitRecord(patientId,visitId);
    }


    private void setListener() {
        //处方记录
        adapter.setOnItemClickListener(new OnItemClick() {
            @Override
            public void onItemClick(View view, int position) {
//                Intent intent = new Intent();
//                intent.setClass(PatientInfoActivity.this,CreateRecipeActivity.class);
//                intent.putExtra("recipeId",array.get(position).getId());
//                startActivity(intent);
                Intent intent = new Intent();
                intent.setClass(PatientInfoActivity.this,RecipeImgActivity.class);
                intent.putExtra("imgUrl",array.get(position).getImgUrl());
                startActivity(intent);
            }
        });

        //就诊记录
        recordListAdapter.setOnItemClickListener(new OnItemClick() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent();
                intent.setClass(PatientInfoActivity.this,AddVisitActivity.class);
                intent.putExtra("patientId",patientId);
                intent.putExtra("visitId",visitId);
                intent.putExtra("name",name);
                intent.putExtra("id",arrayList.get(position).getId());
                intent.putExtra("typeId",arrayList.get(position).getVisitType());
                intent.putExtra("remark",arrayList.get(position).getVisitRemark());
                startActivity(intent);

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.patient_info_add:
                Intent addIntent = new Intent(this, AddVisitActivity.class);
                addIntent.putExtra("patientId",patientId);
                addIntent.putExtra("visitId",visitId);
                addIntent.putExtra("name",name);
                startActivityForResult(addIntent,ADD);
                break;

            case R.id.patient_info_contact:
//                callPhone(mobile);
                UserInfoBean.getInstance().setWyyID(accountName);
                VisitInfomation.getInstance().setWyyID(accountName);
                VISIDS=new ArrayList<>();
                VISNAMES=new ArrayList<>();
                NimUIKit.startP2PSession(this, accountName);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD && resultCode == RESULT_OK){
            getData();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    /**
     * 拨打电话（直接拨打电话）
     * @param phoneNum 电话号码
     */
    public void callPhone(String phoneNum){
        //特殊处理（6.0以上）
        if (ActivityCompat.checkSelfPermission(PatientInfoActivity.this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED
                ||
                ActivityCompat.checkSelfPermission(PatientInfoActivity.this, Manifest.permission.READ_PHONE_STATE)
                        != PackageManager.PERMISSION_GRANTED) {
            // Check Permissions Now
            // Callback onRequestPermissionsResult interceptado na Activity MainActivity
            PermissionGen.with(PatientInfoActivity.this)
                    .addRequestCode(10)
                    .permissions(
                            Manifest.permission.CALL_PHONE,
                            Manifest.permission.READ_PHONE_STATE)
                    .request();
            return;
        }
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @PermissionSuccess(requestCode = 10)
    public void doSomething(){
        callPhone(mobile);
    }

    @PermissionFail(requestCode = 10)
    public void doFailSomething(){
    }


    /**
     * recycler 适配器
     */
    private class MyListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        private Context mContext;
        private ArrayList<RecipeBean> mList;
        private OnItemClick onItemClickListener;

        public MyListAdapter(Context context,ArrayList<RecipeBean> list ){
            this.mContext = context;
            this.mList = list;
        }


        public void setList(ArrayList<RecipeBean> list){
            this.mList = list;
            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext)
                    .inflate(R.layout.list_item_patient_info,parent,false);
            return new MyViewHolder(view,onItemClickListener);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if(holder instanceof MyViewHolder){
                initView((MyViewHolder) holder, mList.get(position));
            }
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        private void setOnItemClickListener(OnItemClick listener){
            onItemClickListener = listener;
        }

        private void initView(MyViewHolder handler, RecipeBean bean){
            if (bean == null) return;
            handler.titleTV.setText(bean.getCreateTime()+"  "+bean.getDoctorName()+"  添加了处方记录" );
        }
    }

    public interface OnItemClick{
        public void onItemClick(View view, int position);
    }

    /**
     * 静态类
     */
    private static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public MyTextView titleTV;
        private OnItemClick onItemClick;

        public MyViewHolder(View view, OnItemClick onItemClick){
            super(view);
            this.onItemClick = onItemClick;
            view.setOnClickListener(this);
            titleTV = (MyTextView) view.findViewById(R.id.list_item_name);
        }

        @Override
        public void onClick(View view) {
            if(onItemClick != null){
                onItemClick.onItemClick(view,getLayoutPosition());
            }
        }
    }


    /**
     * recycler 适配器
     */
    private class RecordListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        private Context mContext;
        private ArrayList<VisitRecordBean> mList;
        private OnItemClick onItemClickListener;

        public RecordListAdapter(Context context,ArrayList<VisitRecordBean> list ){
            this.mContext = context;
            this.mList = list;
        }


        public void setList(ArrayList<VisitRecordBean> list){
            this.mList = list;
            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext)
                    .inflate(R.layout.list_item_patient_info,parent,false);
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

        private void initView(RecordViewHolder handler, VisitRecordBean bean){
            if (bean == null) return;
            handler.titleTV.setText(bean.getCreateTime()+"  "+bean.getDoctorName()+"  添加了就诊记录" );
        }
    }

    /**
     * 静态类
     */
    private static class RecordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public MyTextView titleTV;
        private OnItemClick onItemClick;

        public RecordViewHolder(View view, OnItemClick onItemClick){
            super(view);
            this.onItemClick = onItemClick;
            view.setOnClickListener(this);
            titleTV = (MyTextView) view.findViewById(R.id.list_item_name);
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

    /**
     * 处方记录
     * @param list
     */
    @Override
    public void reloadRecipe(ArrayList<RecipeBean> list) {
        array = list;
        adapter.setList(array);
        if (array.size()<1){
            noDataLayout2.setVisibility(View.VISIBLE);
        }else{
            noDataLayout2.setVisibility(View.GONE);
        }
    }

    /**
     * 就诊记录
     * @param list
     */
    @Override
    public void reloadVisitRecord(ArrayList<VisitRecordBean> list) {
        arrayList = list;
        recordListAdapter.setList(arrayList);
        if (arrayList.size()<1){
            noDataLayout1.setVisibility(View.VISIBLE);
        }else{
            noDataLayout1.setVisibility(View.GONE);
        }
    }
}
