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
import com.company.linquan.app.bean.PrivateRecordBean;
import com.company.linquan.app.moduleWork.WorkInterface;
import com.company.linquan.app.moduleWork.imp.PrivatePresenterImp;
import com.company.linquan.app.util.ExitApp;
import com.company.linquan.app.util.MyDialog;
import com.company.linquan.app.util.MyToast;
import com.company.linquan.app.view.MyTextView;
import com.company.linquan.app.view.RoundImageView;

import java.util.ArrayList;

/**
 * Created by YC on 2018/6/17.
 */

public class PrivateDoctorActivity extends BaseActivity implements WorkInterface.PrivateDoctorInterface, View.OnClickListener{
    private Dialog myDialog;

    private RecyclerView recordRecycler;
    private SwipeRefreshLayout refreshLayout;
    private ArrayList<PrivateRecordBean> array = new ArrayList<>();
    private RecordListAdapter adapter;
    private int sw;
    private PrivatePresenterImp presenter;
    int page=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExitApp.getInstance().addActivity(this);		// add Activity 方便退出
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 开启自定义标题栏
        setContentView(R.layout.activity_private_doctor);
        initHead();
        initView();
        getData();
        setListener();
    }

    private void getData() {
        presenter.getPrivateRecord(page);
    }

    private void initHead() {
        RelativeLayout head = (RelativeLayout) findViewById(R.id.head_layout);
        MyTextView title = (MyTextView) head.findViewById(R.id.head_top_title);
        title.setText("私人医生");
        ImageView rightIV = (ImageView)head.findViewById(R.id.head_top_image);
        rightIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView(){
        presenter = new PrivatePresenterImp(this);

        sw = getContext().getResources().getDisplayMetrics().widthPixels;
        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.private_doctor_refresh);
        refreshLayout.setColorSchemeColors(ContextCompat.getColor(this,R.color.base_red_color));
        findViewById(R.id.private_doctor_setting_layout).setOnClickListener(this);
        recordRecycler = (RecyclerView)findViewById(R.id.private_doctor_recycler);
        recordRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));
        adapter = new RecordListAdapter(getContext(),array);
        recordRecycler.setAdapter(adapter);
        recordRecycler.setItemAnimator(new DefaultItemAnimator());
    }

    private void setListener() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getPrivateRecord(page = 1);
            }
        });

        recordRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            //用来标记是否正在向最后一个滑动，既是否向下滑动
            boolean isSlidingToLast = false;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                // 当不滚动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //获取最后一个完全显示的ItemPosition
                    int lastVisiblePositions = manager.findLastVisibleItemPosition();
                    int totalItemCount = manager.getItemCount();
                    // 判断是否滚动到底部
                    if (lastVisiblePositions >= (totalItemCount -2) && isSlidingToLast) {
                        //加载更多功能的代码
                        page = page + 1;
                        getData();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //dx用来判断横向滑动方向，dy用来判断纵向滑动方向
                if(dy > 0){
                    //大于0表示，正在向下滚动
                    isSlidingToLast = true;
                }else{
                    //小于等于0 表示停止或向上滚动
                    isSlidingToLast = false;
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.private_doctor_setting_layout:
                startActivity(new Intent(PrivateDoctorActivity.this,SettingPrivateActivity.class));
                break;
        }
    }

    /**
     * recycler 适配器
     */
    private class RecordListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        private Context mContext;
        private ArrayList<PrivateRecordBean> mList;
        private OnItemClick onItemClickListener;

        public RecordListAdapter(Context context,ArrayList<PrivateRecordBean> list ){
            this.mContext = context;
            this.mList = list;
        }


        public void setList(ArrayList<PrivateRecordBean> list){
            this.mList = list;
            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext)
                    .inflate(R.layout.list_item_private_doctor,parent,false);
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

        private void initView(RecordViewHolder handler, PrivateRecordBean bean){
            if (bean == null) return;
            int w2 = (94*sw)/720;
            int h2 = (94*sw)/720;
            Glide.with(mContext).load(bean.getPatientHeadUrl()).apply(new RequestOptions().override(w2,h2).centerCrop()).into(handler.photoIV);

            handler.nameTV.setText(bean.getPatientName());
            handler.moneyTV.setText(bean.getPayAmount());
            handler.orderIdTV.setText("订单号:"+bean.getOrderId());
            //类型 1.一个月 2.三个月 3.六个月 4.十二个月
            if ("1".equals(bean.getServiceCycle())){
                handler.serviceTV.setText("一个月服务");
            }else if ("2".equals(bean.getServiceCycle())){
                handler.serviceTV.setText("三个月服务");
            }else if ("3".equals(bean.getServiceCycle())){
                handler.serviceTV.setText("六个月服务");
            }else if ("4".equals(bean.getServiceCycle())){
                handler.serviceTV.setText("十二个月服务");
            }
            handler.timeTV.setText(bean.getPayDate());

        }
    }

    public interface OnItemClick{
        public void onItemClick(View view, int position);
    }

    /**
     * 静态类
     */
    private static class RecordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public MyTextView orderIdTV;
        public MyTextView moneyTV;
        public MyTextView nameTV;
        public MyTextView timeTV;
        public MyTextView serviceTV;
        private RoundImageView photoIV;
        private OnItemClick onItemClick;

        public RecordViewHolder(View view, OnItemClick onItemClick){
            super(view);
            this.onItemClick = onItemClick;
            view.setOnClickListener(this);
            orderIdTV = (MyTextView) view.findViewById(R.id.list_item_num);
            timeTV = (MyTextView) view.findViewById(R.id.list_item_time);
            nameTV = (MyTextView) view.findViewById(R.id.list_item_name);
            moneyTV = (MyTextView) view.findViewById(R.id.list_item_price);
            serviceTV = (MyTextView) view.findViewById(R.id.list_item_service);
            photoIV = (RoundImageView) view.findViewById(R.id.list_item_photo);
            photoIV.setDrawCircle();

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
    public void reloadList(ArrayList<PrivateRecordBean> list) {

        if (page == 1){
            refreshLayout.setRefreshing(false);
            array = list;
            adapter.setList(array);
        }

        if (page > 1){

            for (PrivateRecordBean bean:
                 list) {
                array.add(bean);
            }
            adapter.setList(array);
        }
    }
}
