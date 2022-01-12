package com.company.linquan.app.moduleWork.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.company.linquan.app.bean.MemberBean;
import com.company.linquan.app.bean.OrderTeamBean;
import com.company.linquan.app.bean.TeamDetailBean;
import com.company.linquan.app.http.JSONTeam;
import com.company.linquan.app.moduleWork.WorkInterface;
import com.company.linquan.app.moduleWork.imp.TeamDetailPresenterImp;
import com.company.linquan.app.util.ExitApp;
import com.company.linquan.app.util.MyDialog;
import com.company.linquan.app.util.MyToast;
import com.company.linquan.app.view.MyTextView;
import com.company.linquan.app.view.RoundImageView;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by YC on 2018/6/17.
 */

public class TeamDetailActivity extends BaseActivity implements WorkInterface.TeamDetailInterface,View.OnClickListener{

    private Dialog myDialog;

    private RecyclerView recordRecycler;
    private ArrayList<OrderTeamBean> array;
    private RecordListAdapter adapter;
    int sw;
    private TeamDetailPresenterImp presenter;
    private MyTextView titleTV, contentTV;
    private RoundImageView photoIV;
    private MyTextView numTV;

    private RecyclerView memberRecycler;
    private ArrayList<MemberBean> memberArray;
    private MemberListAdapter memberAdapter;
    int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExitApp.getInstance().addActivity(this);		// add Activity 方便退出
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 开启自定义标题栏
        setContentView(R.layout.activity_team_detail);
        initHead();
        initView();
        getData();
        setListener();
    }



    private void initHead() {
        RelativeLayout head = (RelativeLayout) findViewById(R.id.head_layout);
        MyTextView title = (MyTextView) head.findViewById(R.id.head_top_title);
        title.setText("名医团信息");
        ImageView rightIV = (ImageView)head.findViewById(R.id.head_top_image);
        rightIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView(){
        presenter = new TeamDetailPresenterImp(this);

        titleTV = (MyTextView)findViewById(R.id.team_detail_title);
        contentTV = (MyTextView)findViewById(R.id.team_detail_content);
        photoIV = (RoundImageView) findViewById(R.id.team_detail_photo);
        photoIV.setDrawCircle();

        sw = getContext().getResources().getDisplayMetrics().widthPixels;
        memberRecycler = (RecyclerView)findViewById(R.id.team_detail_recycler);
        memberRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));
        memberArray = new ArrayList<>();
        memberAdapter = new MemberListAdapter(getContext(),memberArray);
        memberRecycler.setAdapter(memberAdapter);
        memberRecycler.setItemAnimator(new DefaultItemAnimator());


        recordRecycler = (RecyclerView)findViewById(R.id.team_detail_record_recycler);
        recordRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));
        array = new ArrayList<>();
        adapter = new RecordListAdapter(getContext(),array);
        recordRecycler.setAdapter(adapter);
        recordRecycler.setItemAnimator(new DefaultItemAnimator());

        numTV = (MyTextView)findViewById(R.id.team_detail_num);
    }

    private void getData() {
        presenter.getTeamDetail();
    }

    private void setListener() {
        memberAdapter.setOnItemClickListener(new OnItemClick() {
            @Override
            public void onItemClick(View view, int position) {
                Gson gson = new Gson();
                Intent intent = new Intent();
                intent.setClass(TeamDetailActivity.this,MemberListActivity.class);
                intent.putExtra("json",gson.toJson(memberArray));
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
        }
    }

    /**
     * recycler 适配器
     */
    private class RecordListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        private Context mContext;
        private ArrayList<OrderTeamBean> mList;
        private OnItemClick onItemClickListener;

        public RecordListAdapter(Context context,ArrayList<OrderTeamBean> list ){
            this.mContext = context;
            this.mList = list;
        }


        public void setList(ArrayList<OrderTeamBean> list){
            this.mList = list;
            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext)
                    .inflate(R.layout.list_item_team_detail_record,parent,false);
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

        private void initView(RecordViewHolder handler, OrderTeamBean bean){
            if (bean == null)return;
            handler.nameTV.setText(bean.getPayManName());
            handler.amountTV.setText("￥"+bean.getAmount());
            handler.contentTV.setText(bean.getAdvisoryText());
            handler.numTV.setText(bean.getOrderId());
            handler.timeTV.setText(bean.getPayTime());
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
        public MyTextView nameTV;
        public MyTextView timeTV;
        public MyTextView contentTV;
        public MyTextView numTV;
        public MyTextView amountTV;
        private OnItemClick onItemClick;

        public RecordViewHolder(View view, OnItemClick onItemClick){
            super(view);
            this.onItemClick = onItemClick;
            view.setOnClickListener(this);
            photoIV = (RoundImageView) view.findViewById(R.id.list_item_photo);
            photoIV.setDrawCircle();
            nameTV = (MyTextView) view.findViewById(R.id.list_item_name);
            timeTV = (MyTextView) view.findViewById(R.id.list_item_time);
            contentTV = (MyTextView) view.findViewById(R.id.list_item_content);
            numTV = (MyTextView) view.findViewById(R.id.list_item_num);
            amountTV = (MyTextView) view.findViewById(R.id.list_item_money);
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
    private class MemberListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        private Context mContext;
        private ArrayList<MemberBean> mList;
        private OnItemClick onItemClickListener;

        public MemberListAdapter(Context context,ArrayList<MemberBean> list ){
            this.mContext = context;
            this.mList = list;
        }


        public void setList(ArrayList<MemberBean> list){
            this.mList = list;
            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext)
                    .inflate(R.layout.list_item_team_detail,parent,false);
            return new MemberViewHolder(view,onItemClickListener);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if(holder instanceof MemberViewHolder){
                initView((MemberViewHolder) holder, mList.get(position));
            }
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        private void setOnItemClickListener(OnItemClick listener){
            onItemClickListener = listener;
        }

        private void initView(MemberViewHolder handler, MemberBean bean){

            if (bean == null)return;
            handler.nameTV.setText(bean.getMemberName());
            int w2 = (94*sw)/720;
            int h2 = (94*sw)/720;
            Glide.with(mContext).load(bean.getMemberHeadUrl()).apply(new RequestOptions().override(w2,h2).centerCrop()).into(handler.photoIV);
        }
    }


    /**
     * 静态类
     */
    private static class MemberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public RoundImageView photoIV;
        public MyTextView nameTV;
        private OnItemClick onItemClick;

        public MemberViewHolder(View view, OnItemClick onItemClick){
            super(view);
            this.onItemClick = onItemClick;
            view.setOnClickListener(this);
            photoIV = (RoundImageView) view.findViewById(R.id.list_item_photo);
            photoIV.setDrawCircle();
            nameTV = (MyTextView) view.findViewById(R.id.list_item_name);
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
    public void reloadView(JSONTeam bean) {
        memberArray = bean.getTable();
        memberAdapter.setList(memberArray);
        numTV.setText("团内成员（"+memberArray.size()+"）");

        array = bean.getOrderTable();
        adapter.setList(array);

        TeamDetailBean detailBean = bean.getInfoJson();
        int w2 = (94*sw)/720;
        int h2 = (94*sw)/720;
        Glide.with(this).load(detailBean.getLogo()).apply(new RequestOptions().override(w2,h2).centerCrop()).into(photoIV);
        contentTV.setText(detailBean.getRemark());
        titleTV.setText(detailBean.getTitle());
    }
}
