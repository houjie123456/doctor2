package com.company.linquan.app.nim.activity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.company.linquan.app.R;
import com.company.linquan.app.base.BaseActivity;
import com.company.linquan.app.bean.FileCollectInfoBean;
import com.company.linquan.app.bean.MyInqueryInfoBean;
import com.company.linquan.app.http.JSONFirstAsk;
import com.company.linquan.app.moduleWork.ui.PatientInfoActivity;
import com.company.linquan.app.nim.ConversationInterface;
import com.company.linquan.app.nim.presenter.FirstAskPresenterImp;
import com.company.linquan.app.util.ExitApp;
import com.company.linquan.app.util.MyDialog;
import com.company.linquan.app.util.MyToast;
import com.company.linquan.app.view.MyTextView;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.List;

public class FirstAskActivity extends BaseActivity implements View.OnClickListener,ConversationInterface.FirstAskInterface {

    private Dialog myDialog;

    private FirstAskPresenterImp presenter;

    private TextView visitnameTV;
    private TextView visitsexTV;
    private TextView visitageTV;
    private TextView visitname1TV;
    private TextView visitsex1TV;
    private TextView visitage1TV;
    private TextView visittimeTV;
    private LinearLayout visithealthLL;
    private TextView visitdescTV;
    private RecyclerView recordRecycler;
    private ArrayList<FileCollectInfoBean> array;
    private ArrayList<String> images=new ArrayList<>();

    private String patientID;
    private String visitID;
    private String visitName;
    private String visitSex;
    private String visitAge;
    private String visitMobile;
    private String patientMobile;
    private String patientHeadUrl;
    private String visitHeadUrl;
    private String healthMarried;
    private String healthBirth;
    private String healthHeated;
    private String healthDisease;
    private String healthMedicined;

    private RecordListAdapter recordListAdapter;

    int sw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExitApp.getInstance().addActivity(this);		// add Activity 方便退出
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 开启自定义标题栏
        setContentView(R.layout.activity_first_inquery);

        initHead();
        initView();
        getData();
        setListener();
    }

    private void initHead() {
        RelativeLayout head = (RelativeLayout) findViewById(R.id.head_layout_top);
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

        visitnameTV=(TextView)findViewById(R.id.visit_name);
        visitsexTV=(TextView)findViewById(R.id.visit_sex);
        visitageTV=(TextView)findViewById(R.id.visit_age);
        visitname1TV=(TextView)findViewById(R.id.visit_name1);
        visitsex1TV=(TextView)findViewById(R.id.visit_sex1);
        visitage1TV=(TextView)findViewById(R.id.visit_age1);
        visittimeTV=(TextView)findViewById(R.id.visit_time);
        visithealthLL=(LinearLayout) findViewById(R.id.layout_health);
        visitdescTV=(TextView)findViewById(R.id.visit_desc);
        visitdescTV.setMovementMethod(ScrollingMovementMethod.getInstance());

        sw = getContext().getResources().getDisplayMetrics().widthPixels;
        presenter = new FirstAskPresenterImp(this);

        recordRecycler = (RecyclerView)findViewById(R.id.list_item_img);
//        recordRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));
        recordRecycler.setLayoutManager(new GridLayoutManager(this,3,GridLayoutManager.VERTICAL, false));
        array = new ArrayList<>();
        recordListAdapter = new RecordListAdapter(getContext(),array);
        recordRecycler.setAdapter(recordListAdapter);
        recordRecycler.setItemAnimator(new DefaultItemAnimator());

        findViewById(R.id.layout_head).setOnClickListener(this);
        findViewById(R.id.layout_health).setOnClickListener(this);

    }
    private void getData() {
        presenter.getFirstAskInfo(getIntent().getStringExtra("inquiryId"));
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.layout_head:
                Intent intent = new Intent();
                intent.setClass(FirstAskActivity.this,PatientInfoActivity.class);
                intent.putExtra("visitId",visitID);
                intent.putExtra("patientId",patientID);
                intent.putExtra("name",visitName
                        +" "+ ("1".equals(visitSex)? "男":"女")
                        +" "+visitAge+"岁");
                if (!"".equals(visitMobile)){
                    intent.putExtra("mobile",visitMobile);
                }
                if (!"".equals(patientMobile)){
                    intent.putExtra("mobile",patientMobile);
                }

                if (!"".equals(visitHeadUrl)){
                    intent.putExtra("url",visitHeadUrl);
                }
                if (!"".equals(patientHeadUrl)){
                    intent.putExtra("url",patientHeadUrl);
                }
                startActivity(intent);
                break;
            case R.id.layout_health:
                Intent intent2 = new Intent();
                intent2.setClass(FirstAskActivity.this,HealthRecordActivity.class);
                intent2.putExtra("health_married",healthMarried);
                intent2.putExtra("health_birth",healthBirth);
                intent2.putExtra("health_heated",healthHeated);
                intent2.putExtra("health_disease",healthDisease);
                intent2.putExtra("health_medicined",healthMedicined);
                startActivity(intent2);
                break;
        }
    }

    @Override
    public void reloadInfo(JSONFirstAsk bean) {
        patientID=bean.getBaseInfoJson().getPatientID();
        visitID=bean.getBaseInfoJson().getVisitID();
        visitName=bean.getBaseInfoJson().getVisitName();
        visitSex=bean.getBaseInfoJson().getVisitSex();
        visitAge=bean.getBaseInfoJson().getVisitAge();
        visitMobile=bean.getBaseInfoJson().getVisitMobile();
        patientMobile=bean.getBaseInfoJson().getPatientMobile();
        patientHeadUrl=bean.getBaseInfoJson().getPatientHeadUrl();
        visitHeadUrl=bean.getBaseInfoJson().getVisitHeadUrl();

        visitnameTV.setText(bean.getBaseInfoJson().getVisitName());
        switch (bean.getBaseInfoJson().getVisitSex()){
            case "1":
                visitsexTV.setText("男");
                break;
            case "2":
                visitsexTV.setText("女");
                break;
        }
        visitageTV.setText(bean.getBaseInfoJson().getVisitAge());
        visitname1TV.setText(visitnameTV.getText());
        visitsex1TV.setText(visitsexTV.getText());
        visitage1TV.setText(visitageTV.getText());
        switch (bean.getBaseInfoJson().getIllLenghTime()){//患病时长：1.一周 2.一个月 3.半年 4.一年及以上
            case "1":
                visittimeTV.setText("一周");
                break;
            case "2":
                visittimeTV.setText("一个月");
                break;
            case "3":
                visittimeTV.setText("半年");
                break;
            case "4":
                visittimeTV.setText("一年及以上");
                break;
        }

//        String healthRecord="";
        ArrayList<MyInqueryInfoBean> arr1;
        arr1=bean.getHealthTable();
        for(MyInqueryInfoBean list:arr1){
            switch (list.getInfoTypeName()){
                case "婚姻状况":
                    healthMarried=list.getInfoContent();
                    break;
                case "生育状况":
                    healthBirth=list.getInfoContent();
                    break;
                case "手术和外伤":
                    healthHeated=list.getInfoContent();
                    break;
                case "家族病史":
                    healthDisease=list.getInfoContent();
                    break;
                case "药物过敏":
                    healthMedicined=list.getInfoContent();
                    break;
            }
        }

//        visithealthTV.setText(healthRecord);
        visitdescTV.setText(bean.getBaseInfoJson().getInquiryContent());

        array = bean.getPicTable();
        recordListAdapter.setList(array);//3
        for (FileCollectInfoBean b:array) {
            images.add(b.getPicUrl());
        }
    }


    private void setListener() {

        recordListAdapter.setOnItemClickListener(new OnItemClick() {
            @Override
            public void onItemClick(View view, int position, int index) {//11
                Intent intent = new Intent(FirstAskActivity.this,ImgActivity.class);
                intent.putStringArrayListExtra("images",images);
//                intent.putExtra("images",array);
                intent.putExtra("position",position);
                startActivity(intent);
//                images.clear();

            }
        });
    }
    public interface OnItemClick{
        public void onItemClick(View view, int position, int index);
    }
    /**
     * recycler 适配器
     */
    private class RecordListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        private Context mContext;
        private ArrayList<FileCollectInfoBean> mList;
        private OnItemClick onItemClickListener;

        public RecordListAdapter(Context context,ArrayList<FileCollectInfoBean> list ){//1
            this.mContext = context;
            this.mList = list;
        }


        public void setList(ArrayList<FileCollectInfoBean> list){//4
            this.mList = list;
            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {//6

            View view = LayoutInflater.from(mContext)
                    .inflate(R.layout.list_item_first_inquery,parent,false);
            return new RecordViewHolder(view,onItemClickListener);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {//8
            if(holder instanceof RecordViewHolder){
                initView((RecordViewHolder) holder, mList.get(position));
            }
        }

        @Override
        public int getItemCount() {//5
            return mList.size();
        }

        private void setOnItemClickListener(OnItemClick listener){//2
            onItemClickListener = listener;
        }

        private void initView(RecordViewHolder handler, FileCollectInfoBean bean){//9
            if (bean == null) return;
            int w2 = (94*sw)/720;
            int h2 = (94*sw)/720;
//            Glide.with(mContext).load(bean.getPicUrl()).override(w2,h2).centerCrop().into(handler.imageView);
            Picasso.get().load(bean.getPicUrl()).into(handler.imageView);
        }
    }




    /**
     * 静态类
     */
    private static class RecordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView imageView;

        private OnItemClick onItemClick;

        public RecordViewHolder(View view, OnItemClick onItemClick){//7
            super(view);
            this.onItemClick = onItemClick;
            view.setOnClickListener(this);
            imageView = (ImageView)view.findViewById(R.id.img_item);


        }

        @Override
        public void onClick(View view) {//10
            if(onItemClick != null){
                onItemClick.onItemClick(view,getLayoutPosition(),3);
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

}
