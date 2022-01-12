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
import com.company.linquan.app.bean.FreeFaceBean;
import com.company.linquan.app.moduleWork.WorkInterface;
import com.company.linquan.app.moduleWork.imp.FreePresenterImp;
import com.company.linquan.app.util.ExitApp;
import com.company.linquan.app.util.MyDialog;
import com.company.linquan.app.util.MyToast;
import com.company.linquan.app.view.MyTextView;
import com.company.linquan.app.view.RoundImageView;

import java.util.ArrayList;

/**
 * Created by YC on 2018/6/13.
 */

public class WorkFreeActivity extends BaseActivity implements WorkInterface.FreeInterface,View.OnClickListener{
    private Dialog myDialog;

    private RecyclerView  recordRecycler;
    private SwipeRefreshLayout refreshLayout;
    private ArrayList<FreeFaceBean> array = new ArrayList<>();
    private RecordListAdapter recordListAdapter;
    int sw;

    int page = 1;

    private FreePresenterImp presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExitApp.getInstance().addActivity(this);		// add Activity 方便退出
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 开启自定义标题栏
        setContentView(R.layout.activity_work_free);
        initHead();
        initView();
        setListener();
        getData();
    }

    private void initHead() {
        RelativeLayout head = (RelativeLayout) findViewById(R.id.layout_head);
        MyTextView title = (MyTextView)head. findViewById(R.id.head_top_title);
        title.setText("免费义诊管理");
        ImageView rightIV = (ImageView)head.findViewById(R.id.head_top_image);
        rightIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView(){

        presenter = new FreePresenterImp(this);

        sw = getContext().getResources().getDisplayMetrics().widthPixels;
        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.work_free_refresh);
        refreshLayout.setColorSchemeColors(ContextCompat.getColor(this,R.color.base_red_color));

        recordRecycler = (RecyclerView)findViewById(R.id.work_free_recycler);
        recordRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));
        recordListAdapter = new RecordListAdapter(getContext(),array);
        recordRecycler.setAdapter(recordListAdapter);
        recordRecycler.setItemAnimator(new DefaultItemAnimator());

        findViewById(R.id.work_free_mid_layout).setOnClickListener(this);
    }

    private void getData() {
        page = 1;
        presenter.getFreeList(page);
    }

    private void setListener() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.work_free_mid_layout:
                startActivity(new Intent(this, SettingFreeActivity.class));
                break;
        }
    }


    public interface OnItemClick{
        public void onItemClick(View view, int position);
    }


    /**
     * recycler 适配器
     */
    private class RecordListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{ 
    private Context mContext;
    private ArrayList<FreeFaceBean> mList;
    private OnItemClick onItemClickListener;

    public RecordListAdapter(Context context,ArrayList<FreeFaceBean> list ){
        this.mContext = context;
        this.mList = list;
    }


    public void setList(ArrayList<FreeFaceBean> list){
        this.mList = list;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.list_item_free,parent,false);
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

    private void initView(RecordViewHolder handler, FreeFaceBean bean){

        if (bean == null) return;
        int w2 = (94*sw)/720;
        int h2 = (94*sw)/720;
        Glide.with(mContext).load(bean.getPatientHeadUrl()).apply(new RequestOptions().override(w2,h2).centerCrop()).into(handler.imageView);

        handler.nameTV.setText(bean.getPatientName());
        handler.timeTV.setText(bean.getCreateTime());
        handler.contentTV.setText(bean.getContent());
        handler.numTV.setText("编号:"+bean.getOrderId());
        handler.moneyTV.setText("￥"+bean.getAmount());
    }
}

/**
 * 静态类
 */
private static class RecordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public RoundImageView imageView;
    public MyTextView nameTV;
    public MyTextView timeTV;
    public MyTextView contentTV;
    public MyTextView numTV;
    public MyTextView moneyTV;
    private OnItemClick onItemClick;

    public RecordViewHolder(View view, OnItemClick onItemClick){
        super(view);
        this.onItemClick = onItemClick;
        view.setOnClickListener(this);
        imageView = (RoundImageView)view.findViewById(R.id.list_item_person_image);
        imageView.setDrawCircle();
        nameTV = (MyTextView) view.findViewById(R.id.list_item_name);
        timeTV = (MyTextView) view.findViewById(R.id.list_item_time);
        contentTV = (MyTextView) view.findViewById(R.id.list_item_content);
        numTV = (MyTextView) view.findViewById(R.id.list_item_num);
        moneyTV = (MyTextView) view.findViewById(R.id.list_item_money);

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
    public void reloadFree(ArrayList<FreeFaceBean> list) {

        if (page == 1){
            refreshLayout.setRefreshing(false);
            array = list;
            recordListAdapter.setList(array);
        }

        if (page > 1){
            for (FreeFaceBean b:
                 list) {
                array.add(b);
            }
            array = list;
            recordListAdapter.setList(array);
        }

    }
}