package com.company.linquan.app.moduleCenter.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import com.company.linquan.app.R;
import com.company.linquan.app.bean.TaskBean;
import com.company.linquan.app.util.ExitApp;
import com.company.linquan.app.view.MyTextView;

import java.util.ArrayList;

import static com.company.linquan.app.base.MyBaseApplication.getContext;

public class MyScoreActivity extends Activity {
    private RecyclerView taskRecycler;
    private taskListAdapter adapter;
    private SwipeRefreshLayout refreshLayout;
    private ArrayList<TaskBean> array;

    int sw;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExitApp.getInstance().addActivity(this);		// add Activity 方便退出
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 开启自定义标题栏
        setContentView(R.layout.activity_work_task);
        initHead();
        initView();
    }

    private void initHead() {
        RelativeLayout head = (RelativeLayout) findViewById(R.id.head_layout);
        MyTextView title = (MyTextView) head.findViewById(R.id.head_top_title);
        title.setText("我的积分");
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
        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.my_meeting_refresh);
        refreshLayout.setColorSchemeColors(ContextCompat.getColor(this,R.color.base_red_color));
        taskRecycler = (RecyclerView)findViewById(R.id.my_meeting_recycler);
        taskRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));
//        array = new ArrayList<>();
//        adapter = new MyScoreActivity.RecordListAdapter(getContext(),array);
        taskRecycler.setAdapter(adapter);
        taskRecycler.setItemAnimator(new DefaultItemAnimator());
    }


    /**
     * 适配器
     */
    private class taskListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        private Context mContext;
        private ArrayList<TaskBean> mList;
        private OnItemClick onItemClickListener;

        public taskListAdapter(Context context,ArrayList<TaskBean> list ){
            this.mContext = context;
            this.mList = list;
        }

        public void setList(ArrayList<TaskBean> list){
            this.mList = list;
            notifyDataSetChanged();
        }
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext)
                    .inflate(R.layout.list_item_task,parent,false);
            return new TaskViewHolder(view,onItemClickListener);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if(holder instanceof TaskViewHolder){
                initView((TaskViewHolder) holder, mList.get(position));
            }
        }
        private void initView(TaskViewHolder handler,TaskBean bean){

            if (bean == null) return;

            int w = (208*sw)/720;
//            Glide.with(mContext).load(bean.getHeadUrl()).override(w,w).centerCrop().into(handler.imageIV);
//            handler.titleTV.setText(Base64.decodeToString(bean.getTitle()));
//            handler.contentTV.setText(Base64.decodeToString(bean.getIntroduction()));
//            String[] ss = bean.getTimePart().split("至");
//            handler.timeTV.setText(ss[0]);
        }
        private void setOnItemClickListener(OnItemClick listener){
            onItemClickListener = listener;
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }
    public interface OnItemClick{
        public void onItemClick(View view, int position);
    }
    /**
     * 静态类
     */
    private static class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView imageIV;
        public MyTextView timeTV;
        public MyTextView titleTV;
        public MyTextView contentTV;
        private OnItemClick onItemClick;

        public TaskViewHolder(View view, OnItemClick onItemClick){
            super(view);
            this.onItemClick = onItemClick;
            view.setOnClickListener(this);
            imageIV = (ImageView) view.findViewById(R.id.list_item_video);
            timeTV = (MyTextView) view.findViewById(R.id.list_item_time);
            titleTV = (MyTextView) view.findViewById(R.id.list_item_title);
            contentTV = (MyTextView) view.findViewById(R.id.list_item_content);
        }

        @Override
        public void onClick(View view) {
            if(onItemClick != null){
                onItemClick.onItemClick(view,getLayoutPosition());
            }
        }
    }
}
