package com.company.linquan.app.moduleWork.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.company.linquan.app.R;
import com.company.linquan.app.base.BaseActivity;
import com.company.linquan.app.bean.PatientBean;
import com.company.linquan.app.config.C;
import com.company.linquan.app.moduleWork.WorkInterface;
import com.company.linquan.app.moduleWork.imp.PatientPresenterImp;
import com.company.linquan.app.util.ExitApp;
import com.company.linquan.app.util.MyDialog;
import com.company.linquan.app.util.MyToast;
import com.company.linquan.app.util.ToolSharePerference;
import com.company.linquan.app.view.MyTextView;
import com.company.linquan.app.view.RoundImageView;

import java.util.ArrayList;

/**
 * Created by YC on 2018/6/20.
 */

public class PatientListActivity extends BaseActivity implements WorkInterface.PatientInterface,View.OnClickListener{

    private Dialog myDialog;

    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private ArrayList<PatientBean> array = new ArrayList<>();
    private MyListAdapter adapter;

    private PatientPresenterImp presenter;
    String doctorID = "";
    String icdName = "";
    String patient = "";
    int sw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExitApp.getInstance().addActivity(this);		// add Activity 方便退出
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 开启自定义标题栏
        setContentView(R.layout.activity_patient_list);
        initHead();
        initView();
        setListener();
        getData();
    }

    private void getData() {
        doctorID=ToolSharePerference.getStringData(getContext(), C.fileconfig,C.UserID);
        icdName=getIntent().getExtras().getString("diseaseName");
        presenter.getMyPatientInfo(doctorID,icdName,patient);
    }


    private void initHead() {
        RelativeLayout head = (RelativeLayout) findViewById(R.id.head_layout);
        MyTextView title = (MyTextView) head.findViewById(R.id.head_top_title);
        title.setText("我的患者");
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
        presenter = new PatientPresenterImp(this);
        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.member_list_refresh);
        refreshLayout.setColorSchemeColors(ContextCompat.getColor(this,R.color.base_red_color));
        recyclerView = (RecyclerView)findViewById(R.id.member_list_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));
        adapter = new MyListAdapter(getContext(),array);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }


    private void setListener() {

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });

        adapter.setOnItemClickListener(new OnItemClick() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent();
                intent.setClass(PatientListActivity.this,PatientInfoActivity.class);

                intent.putExtra("accountName",array.get(position).getAccountName());
                intent.putExtra("visitId",array.get(position).getVisitID());
                intent.putExtra("patientId",array.get(position).getPatientID());
                intent.putExtra("name",array.get(position).getVisitName()
                        +" "+ ("1".equals(array.get(position).getVisitSex())? "男":"女")
                        +" "+array.get(position).getVisitAge()+"岁");
                if (!"".equals(array.get(position).getVisitMobile())){
                    intent.putExtra("mobile",array.get(position).getVisitMobile());
                }
                if (!"".equals(array.get(position).getPatientMobile())){
                    intent.putExtra("mobile",array.get(position).getPatientMobile());
                }
                if (!"".equals(array.get(position).getVisitHeadUrl())){
                    intent.putExtra("url",array.get(position).getVisitHeadUrl());
                }
                if (!"".equals(array.get(position).getPatientHeadUrl())){
                    intent.putExtra("url",array.get(position).getPatientHeadUrl());
                }
                startActivity(intent);
            }
        });
    }


    @Override
    public void onClick(View view) {
    }

    /**
     * recycler 适配器
     */
    private class MyListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        private Context mContext;
        private ArrayList<PatientBean> mList;
        private OnItemClick onItemClickListener;

        public MyListAdapter(Context context,ArrayList<PatientBean> list ){
            this.mContext = context;
            this.mList = list;
        }


        public void setList(ArrayList<PatientBean> list){
            this.mList = list;
            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext)
                    .inflate(R.layout.list_item_patient,parent,false);
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

        private void initView(MyViewHolder handler, PatientBean bean){
            if (bean == null) return;
            int w2 = (94*sw)/720;
            int h2 = (94*sw)/720;

            String url = "";
            if (!"".equals(bean.getVisitHeadUrl())){
                url = bean.getVisitHeadUrl();
            }
            if (!"".equals(bean.getPatientHeadUrl())){
                url = bean.getPatientHeadUrl();
            }
            Glide.with(mContext).load(url).apply(new RequestOptions().override(w2,h2).centerCrop()).into(handler.imageView);

            String name = "";
            if (!"".equals(bean.getPatientName())){
                name = bean.getPatientName();
            }
            if (!"".equals(bean.getVisitName())){
                name = bean.getVisitName();
            }
            handler.titleTV.setText(name);

            String mobile = "";
            if (!"".equals(bean.getPatientMobile())){
                mobile = bean.getPatientMobile();
            }
            if (!"".equals(bean.getVisitMobile())){
                mobile = bean.getVisitMobile();
            }

            handler.contentTV.setText(mobile);

        }
    }

    public interface OnItemClick{
        public void onItemClick(View view, int position);
    }

    /**
     * 静态类
     */
    private static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public RoundImageView imageView;
        public MyTextView titleTV;
        public MyTextView contentTV;
        private OnItemClick onItemClick;

        public MyViewHolder(View view, OnItemClick onItemClick){
            super(view);
            this.onItemClick = onItemClick;
            view.setOnClickListener(this);
            imageView = (RoundImageView)view.findViewById(R.id.list_item_photo);
            imageView.setDrawCircle();
            titleTV = (MyTextView) view.findViewById(R.id.list_item_name);
            contentTV = (MyTextView) view.findViewById(R.id.list_item_content);
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

    @Override
    public void reloadList(ArrayList<PatientBean> beans) {

            refreshLayout.setRefreshing(false);
            array = beans;
            adapter.setList(array);
    }
}
