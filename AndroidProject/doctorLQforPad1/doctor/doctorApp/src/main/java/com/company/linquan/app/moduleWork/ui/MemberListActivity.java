package com.company.linquan.app.moduleWork.ui;

import android.app.Dialog;
import android.content.Context;
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
import com.company.linquan.app.bean.MemberBean;
import com.company.linquan.app.moduleWork.WorkInterface;
import com.company.linquan.app.moduleWork.imp.MemberListPresenterImp;
import com.company.linquan.app.util.ExitApp;
import com.company.linquan.app.util.MyDialog;
import com.company.linquan.app.util.MyToast;
import com.company.linquan.app.view.MyTextView;
import com.company.linquan.app.view.RoundImageView;

import java.util.ArrayList;

/**
 * Created by YC on 2018/7/15.
 */

public class MemberListActivity extends BaseActivity implements WorkInterface.MemberListInterface{

    private Dialog myDialog;

    private RecyclerView recordRecycler;
    private ArrayList<MemberBean> array;
    private RecordListAdapter adapter;
    private SwipeRefreshLayout refreshLayout;
    int sw;
    private String json;
    private MemberListPresenterImp presenter;
    private MyTextView numTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExitApp.getInstance().addActivity(this);		// add Activity 方便退出
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 开启自定义标题栏
        setContentView(R.layout.activity_member_list);
        initData();
        initHead();
        initView();
        setListener();
        getData();
    }

    private void getData() {
        presenter.getMember(json); //类型
    }

    private void initData() {
        json = getIntent().getStringExtra("json");
    }

    private void initHead() {
        RelativeLayout head = (RelativeLayout) findViewById(R.id.head_layout);
        MyTextView title = (MyTextView) head.findViewById(R.id.head_top_title);
        title.setText("成员");
        ImageView rightIV = (ImageView)head.findViewById(R.id.head_top_image);
        rightIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView(){
        presenter = new MemberListPresenterImp(this);
        sw = getContext().getResources().getDisplayMetrics().widthPixels;
        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.member_list_refresh);
        refreshLayout.setColorSchemeColors(ContextCompat.getColor(this,R.color.base_red_color));
        recordRecycler = (RecyclerView)findViewById(R.id.member_list_recycler);
        recordRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));
        array = new ArrayList<>();
        adapter = new RecordListAdapter(getContext(),array);
        recordRecycler.setAdapter(adapter);
        recordRecycler.setItemAnimator(new DefaultItemAnimator());

        numTV = (MyTextView)findViewById(R.id.member_list_num);
    }

    private void setListener() {
        adapter.setOnItemClickListener(new OnItemClick() {
            @Override
            public void onItemClick(View view, int position) {
            }
        });
    }

    /**
     * recycler 适配器
     */
    private class RecordListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        private Context mContext;
        private ArrayList<MemberBean> mList;
        private OnItemClick onItemClickListener;

        public RecordListAdapter(Context context,ArrayList<MemberBean> list ){
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

        private void initView(RecordViewHolder handler, MemberBean bean){

            if (bean == null)return;
            handler.nameTV.setText(bean.getMemberName());
            handler.addressTV.setText(bean.getHospitalName());
            handler.jobTV.setText(bean.getDepartmentName());

            int w2 = (94*sw)/720;
            int h2 = (94*sw)/720;
            Glide.with(mContext).load(bean.getMemberHeadUrl()).apply(new RequestOptions().override(w2,h2).centerCrop()).into(handler.photoIV);
            handler.selectIV.setVisibility(View.GONE);
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
    public void reloadList(ArrayList<MemberBean> list) {
        array = list;
        adapter.setList(list);
        numTV.setText("团内成功("+list.size()+")");
}

}
