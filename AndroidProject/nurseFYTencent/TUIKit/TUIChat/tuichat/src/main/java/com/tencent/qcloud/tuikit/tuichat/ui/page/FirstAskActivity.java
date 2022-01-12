package com.tencent.qcloud.tuikit.tuichat.ui.page;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.tencent.qcloud.tuikit.tuichat.R;
import com.tencent.qcloud.tuikit.tuichat.fromApp.bean.DiseaseBean;
import com.tencent.qcloud.tuikit.tuichat.fromApp.bean.FileCollectInfoBean;
import com.tencent.qcloud.tuikit.tuichat.fromApp.bean.MyInqueryInfoBean;
import com.tencent.qcloud.tuikit.tuichat.fromApp.config.C;
import com.tencent.qcloud.tuikit.tuichat.fromApp.http.JSONFirstAsk;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.MyDialog;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.MyToast;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.SpaceItemDecoration;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.ToolSharePerference;
import com.tencent.qcloud.tuikit.tuichat.fromApp.view.MyTextView;
import com.tencent.qcloud.tuikit.tuichat.fromApp.view.RoundImageView;
import com.tencent.qcloud.tuikit.tuichat.interfaces.ConversationInterface;
import com.tencent.qcloud.tuikit.tuichat.presenter.FirstAskPresenterImp;

import java.util.ArrayList;

public class FirstAskActivity extends AppCompatActivity implements View.OnClickListener, ConversationInterface.FirstAskInterface {

    private Dialog myDialog;

    private FirstAskPresenterImp presenter;

    private RoundImageView visitHeadIV;
    private TextView visitnameTV;
    private TextView visitsexTV;
    private TextView visitageTV;
    private TextView purposeTV;
    private TextView returnTV;
    private TextView diseaseTV;
    private TextView visittimeTV,lookNameTV,lookIDCardTV;
    private LinearLayout visithealthLL,returnLL,diseaseLL,lookNameLL,lookIDCardLL,inHosVisitLL,exportLL;
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

        lookNameLL = findViewById(R.id.layout_mid01);
        lookIDCardLL = findViewById(R.id.layout_mid1);
        lookNameTV = findViewById(R.id.look_name);
        lookIDCardTV = findViewById(R.id.look_id_card);

        inHosVisitLL = findViewById(R.id.layout_visit);
        exportLL = findViewById(R.id.layout_report);

        visitHeadIV=findViewById(R.id.visit_head);
        visitnameTV=(TextView)findViewById(R.id.visit_name);
        visitsexTV=(TextView)findViewById(R.id.visit_sex);
        visitageTV=(TextView)findViewById(R.id.visit_age);
        visittimeTV=(TextView)findViewById(R.id.visit_time);

        purposeTV=(TextView)findViewById(R.id.visit_purpose);
        returnTV=(TextView)findViewById(R.id.visit_return_info);
        diseaseTV=(TextView)findViewById(R.id.visit_disease);

        returnLL = findViewById(R.id.layout_mid_return);
        diseaseLL = findViewById(R.id.layout_mid_disease);

        visithealthLL=(LinearLayout) findViewById(R.id.layout_health);
        visitdescTV=(TextView)findViewById(R.id.visit_desc);
        visitdescTV.setMovementMethod(ScrollingMovementMethod.getInstance());

        sw = getContext().getResources().getDisplayMetrics().widthPixels;
        presenter = new FirstAskPresenterImp(this);

        recordRecycler = (RecyclerView)findViewById(R.id.list_item_img);
//        recordRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));
        recordRecycler.setLayoutManager(new GridLayoutManager(this,3,RecyclerView.VERTICAL, false));
        array = new ArrayList<>();
        recordListAdapter = new RecordListAdapter(getContext(),array);
        recordRecycler.setAdapter(recordListAdapter);
        recordRecycler.setItemAnimator(new DefaultItemAnimator());
        recordRecycler.addItemDecoration(new SpaceItemDecoration(10));

        findViewById(R.id.layout_head).setOnClickListener(this);
        findViewById(R.id.layout_health).setOnClickListener(this);
        findViewById(R.id.layout_visit).setOnClickListener(this);
        findViewById(R.id.layout_report).setOnClickListener(this);

    }

    private void getData() {
        presenter.getFirstAskInfo(getIntent().getStringExtra("inquiryId"));
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.layout_visit) {
//            Intent intentDiagnose = new Intent();
//            intentDiagnose.putExtra("visitId", visitID);
//            intentDiagnose.setClass(FirstAskActivity.this, HosDiagnoseActivity.class);
//            startActivity(intentDiagnose);
        } else if (id == R.id.layout_report) {
//            Intent intentReport = new Intent();
//            intentReport.putExtra("visitId", visitID);
//            intentReport.setClass(FirstAskActivity.this, ReportSearchActivity.class);
//            startActivity(intentReport);
        } else if (id == R.id.layout_head) {
//            if (ToolSharePerference.getStringData(getContext(), C.fileconfig, C.IDTYPE).equals("1")) {//护理
//                return;
//            }
//            Intent intent = new Intent();
//            intent.setClass(FirstAskActivity.this, PatientInfoActivity.class);
//            intent.putExtra("visitId", visitID);
//            intent.putExtra("patientId", patientID);
//            intent.putExtra("name", visitName + " " + visitSex + " " + visitAge + "岁");
//            if (!"".equals(visitMobile)) {
//                intent.putExtra("mobile", visitMobile);
//            }
//            if (!"".equals(patientMobile)) {
//                intent.putExtra("mobile", patientMobile);
//            }
//
//            if (!"".equals(visitHeadUrl)) {
//                intent.putExtra("url", visitHeadUrl);
//            }
//            if (!"".equals(patientHeadUrl)) {
//                intent.putExtra("url", patientHeadUrl);
//            }
//            startActivity(intent);
        } else if (id == R.id.layout_health) {
            Intent intent2 = new Intent();
            intent2.setClass(FirstAskActivity.this, HealthRecordActivity.class);
            intent2.putExtra("health_married", healthMarried == "" ? "暂无" : healthMarried);
            intent2.putExtra("health_birth", healthBirth == "" ? "暂无" : healthBirth);
            intent2.putExtra("health_heated", healthHeated == "" ? "暂无" : healthHeated);
            intent2.putExtra("health_disease", healthDisease == "" ? "暂无" : healthDisease);
            intent2.putExtra("health_medicined", healthMedicined == "" ? "暂无" : healthMedicined);
            startActivity(intent2);
        }
    }

    @Override
    public void reloadInfo(JSONFirstAsk bean) {
        patientID=bean.getTable().getBaseInfoJson().getPatientId();
        visitID=bean.getTable().getBaseInfoJson().getVisitId();
        visitName=bean.getTable().getBaseInfoJson().getVisitName();
        visitSex=bean.getTable().getBaseInfoJson().getVisitSex();
        visitAge=bean.getTable().getBaseInfoJson().getVisitAge();
        visitMobile=bean.getTable().getBaseInfoJson().getVisitMobile();
        patientMobile=bean.getTable().getBaseInfoJson().getPatientMobile();
        patientHeadUrl=bean.getTable().getBaseInfoJson().getPatientHeadUrl();
        visitHeadUrl=patientHeadUrl;

        if(bean.getTable().getBaseInfoJson().getIsGuardian().equals("1")){
            lookNameLL.setVisibility(View.GONE);
            lookIDCardLL.setVisibility(View.GONE);
        } else {
            lookNameLL.setVisibility(View.VISIBLE);
            lookIDCardLL.setVisibility(View.VISIBLE);
            lookNameTV.setText(bean.getTable().getBaseInfoJson().getGuardianName());
            lookIDCardTV.setText(bean.getTable().getBaseInfoJson().getGuardianIdCardNo());
        }



        visitHeadIV.setDrawCircle();
        int w2 = (94*sw)/720;
        Glide.with(this).load(visitHeadUrl).apply(new RequestOptions().override(w2,w2).centerCrop()).into(visitHeadIV);

        visitnameTV.setText(bean.getTable().getBaseInfoJson().getVisitName());
//        switch (bean.getTable().getBaseInfoJson().getVisitSex()){
//            case "1":
//                visitsexTV.setText("男");
//                break;
//            case "2":
//                visitsexTV.setText("女");
//                break;
//        }
        visitsexTV.setText(bean.getTable().getBaseInfoJson().getVisitSex());
        visitageTV.setText(bean.getTable().getBaseInfoJson().getVisitAge()+"岁");

        purposeTV.setText(bean.getTable().getBaseInfoJson().getInquiryPurposeStr());
        if(bean.getTable().getBaseInfoJson().getInquiryPurpose().equals("1")){
            returnLL.setVisibility(View.GONE);
            diseaseLL.setVisibility(View.GONE);
            inHosVisitLL.setVisibility(View.VISIBLE);
            exportLL.setVisibility(View.VISIBLE);
        }else if(bean.getTable().getBaseInfoJson().getInquiryPurpose().equals("3")) {
            inHosVisitLL.setVisibility(View.GONE);
            exportLL.setVisibility(View.GONE);
            returnLL.setVisibility(View.GONE);
            diseaseLL.setVisibility(View.GONE);
        }else {
            inHosVisitLL.setVisibility(View.VISIBLE);
            exportLL.setVisibility(View.VISIBLE);
            returnLL.setVisibility(View.VISIBLE);
            returnTV.setText(bean.getTable().getBaseInfoJson().getSubseqVisitCertStr());
            if(bean.getTable().getBaseInfoJson().getSubseqVisitCert().equals("2")){
                diseaseLL.setVisibility(View.VISIBLE);
                diseaseTV.setText(bean.getTable().getBaseInfoJson().getDisease());
            }else {
                diseaseLL.setVisibility(View.GONE);
            }

        }

        switch (bean.getTable().getBaseInfoJson().getIllLenghTime()){//患病时长：1.一周 2.一个月 3.半年 4.一年及以上
            case "0":
                visittimeTV.setText("无");
                break;
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
        arr1=bean.getTable().getHealthTable();
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
        visitdescTV.setText(bean.getTable().getBaseInfoJson().getInquiryContent());

        array = bean.getTable().getPicTable();
        recordListAdapter.setList(array);//3
        for (FileCollectInfoBean b:array) {
            images.add(b.getPicUrl());
        }
    }

    @Override
    public void gotoRecipe(ArrayList<DiseaseBean> list, String isFirstVisit) {

    }


    private void setListener() {

        recordListAdapter.setOnItemClickListener(new OnItemClick() {
            @Override
            public void onItemClick(View view, int position, int index) {//11
                Intent intent = new Intent(FirstAskActivity.this, ImgActivity.class);
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
            Glide.with(mContext).load(bean.getPicUrl()).override(w2,h2).centerCrop().into(handler.imageView);
//            Picasso.get().load(bean.getPicUrl()).into(handler.imageView);
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

}
