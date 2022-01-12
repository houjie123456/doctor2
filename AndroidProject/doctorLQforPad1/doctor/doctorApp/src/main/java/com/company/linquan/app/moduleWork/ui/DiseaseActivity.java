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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.company.linquan.app.R;
import com.company.linquan.app.base.BaseActivity;
import com.company.linquan.app.bean.DiseaseBean;
import com.company.linquan.app.bean.DiseaseGroupBean;
import com.company.linquan.app.bean.PatientBean;
import com.company.linquan.app.config.C;
import com.company.linquan.app.moduleWork.WorkInterface;
import com.company.linquan.app.moduleWork.imp.PatientGroupPresenterImp;
import com.company.linquan.app.util.ExitApp;
import com.company.linquan.app.util.MyDialog;
import com.company.linquan.app.util.MyToast;
import com.company.linquan.app.util.ToolSharePerference;
import com.company.linquan.app.view.MyTextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by YC on 2018/7/15.
 */

public class DiseaseActivity extends BaseActivity implements WorkInterface.DiseaseListInterface,View.OnClickListener{

    private Dialog myDialog;

    private RecyclerView recordRecycler;
    private ArrayList<DiseaseBean> array;
    private RecordListAdapter adapter;
    private SwipeRefreshLayout refreshLayout;
    int sw;
    private MyTextView numTV;

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
        ArrayList<DiseaseBean> diseaseBeans = (ArrayList<DiseaseBean>) this.getIntent().getSerializableExtra("diseaseBeans");

        refreshLayout.setRefreshing(false);
        array = diseaseBeans;
        adapter.setList(array);
//        for(DiseaseBean p : diseaseBeans) {
//            strData = strData + p.strName + " " + p.iAge + "\n";
//        }
    }

    private void initHead() {
        RelativeLayout head = (RelativeLayout) findViewById(R.id.head_layout);
        MyTextView title = (MyTextView) head.findViewById(R.id.head_top_title);
        title.setText("患者分组");
        ImageView rightIV = (ImageView)head.findViewById(R.id.head_top_image);
        rightIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView() {
        sw = getContext().getResources().getDisplayMetrics().widthPixels;
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.member_list_refresh);
        refreshLayout.setColorSchemeColors(ContextCompat.getColor(this, R.color.base_red_color));
        recordRecycler = (RecyclerView) findViewById(R.id.member_list_recycler);
        recordRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        array = new ArrayList<>();
        adapter = new RecordListAdapter(getContext(), array);
        recordRecycler.setAdapter(adapter);
        recordRecycler.setItemAnimator(new DefaultItemAnimator());

        numTV = (MyTextView) findViewById(R.id.member_list_num);
    }
    private void setListener() {
        adapter.setOnItemClickListener(new OnItemClick() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent();
                intent.putExtra("diseaseName",array.get(position).getIcdname());
                intent.setClass(DiseaseActivity.this,PatientListActivity.class);
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
    private class RecordListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        private Context mContext;
        private ArrayList<DiseaseBean> mList;
        private OnItemClick onItemClickListener;

        public RecordListAdapter(Context context,ArrayList<DiseaseBean> list ){
            this.mContext = context;
            this.mList = list;
        }


        public void setList(ArrayList<DiseaseBean> list){
            this.mList = list;
            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext)
                    .inflate(R.layout.list_item_select_data,parent,false);
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

        private void initView(RecordViewHolder handler, DiseaseBean bean){

            if (bean == null)return;

            int w2 = (94*sw)/720;
            int h2 = (94*sw)/720;
//            Glide.with(mContext).load(bean.getMemberHeadUrl()).apply(new RequestOptions().override(w2,h2).centerCrop()).into(handler.photoIV);
            handler.titleTV.setText(bean.getIcdname());
        }
    }

    public interface OnItemClick{
        public void onItemClick(View view, int position);
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
            titleTV = (MyTextView) view.findViewById(R.id.list_item_data);
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
    public void reloadList(ArrayList<DiseaseGroupBean> list) {

    }

    @Override
    public void reloadDiseaseList(ArrayList<DiseaseBean> list) {
        refreshLayout.setRefreshing(false);
        array = list;
        adapter.setList(array);
    }

    @Override
    public void reloadPatientList(ArrayList<PatientBean> list) {

    }

    @Override
    public void checkType(String json) {

    }

    @Override
    public void reload() {

    }

}
