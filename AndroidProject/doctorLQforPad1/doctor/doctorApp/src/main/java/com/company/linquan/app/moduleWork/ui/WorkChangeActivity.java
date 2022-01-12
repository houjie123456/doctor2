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
import com.company.linquan.app.bean.ChangeBean;
import com.company.linquan.app.config.C;
import com.company.linquan.app.moduleWeb.WebActivity;
import com.company.linquan.app.moduleWork.WorkInterface;
import com.company.linquan.app.moduleWork.imp.ChangePresenterImp;
import com.company.linquan.app.util.ExitApp;
import com.company.linquan.app.util.MyDialog;
import com.company.linquan.app.util.MyToast;
import com.company.linquan.app.util.ToolSharePerference;
import com.company.linquan.app.view.MyTextView;

import java.util.ArrayList;

/**
 * Created by YC on 2018/6/14.
 */

public class WorkChangeActivity extends BaseActivity implements WorkInterface.ChangeInterface,View.OnClickListener{
    private Dialog myDialog;

    private RecyclerView  recordRecycler;
    private SwipeRefreshLayout refreshLayout;
    private ArrayList<ChangeBean> array;
    private RecordListAdapter recordListAdapter;
    private ChangePresenterImp presenter;
    private int page = 1;
    int sw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExitApp.getInstance().addActivity(this);		// add Activity 方便退出
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 开启自定义标题栏
        setContentView(R.layout.activity_work_change);
        initHead();
        initView();
        getData();
        setListener();
    }



    private void getData() {
        presenter.getChange(page);
    }

    private void initHead() {
        RelativeLayout head = (RelativeLayout) findViewById(R.id.layout_head);
        MyTextView title = (MyTextView)head. findViewById(R.id.head_top_title);
        title.setText("转诊管理");
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
        presenter = new ChangePresenterImp(this);

        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.work_change_refresh);
        refreshLayout.setColorSchemeColors(ContextCompat.getColor(this,R.color.base_red_color));

        recordRecycler = (RecyclerView)findViewById(R.id.work_change_recycler);
        recordRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));
        array = new ArrayList<>();
        recordListAdapter = new RecordListAdapter(getContext(),array);
        recordRecycler.setAdapter(recordListAdapter);
        recordRecycler.setItemAnimator(new DefaultItemAnimator());

        findViewById(R.id.work_change_mid_layout).setOnClickListener(this);
    }

    private void setListener() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getChange(page = 1);
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
            case R.id.work_change_mid_layout:
                Intent intent = new Intent();
                intent.setClass(this, WebActivity.class);
                Bundle b = new Bundle();
                b.putString("url", C.Url_IP+"doctorEdition/selHospital.html?id="
                + ToolSharePerference.getStringData(this,C.fileconfig,C.UserID));
                b.putString("title","预约面诊");
                intent.putExtras(b);
                startActivity(intent);
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
        private ArrayList<ChangeBean> mList;
        private OnItemClick onItemClickListener;

        public RecordListAdapter(Context context,ArrayList<ChangeBean> list ){
            this.mContext = context;
            this.mList = list;
        }


        public void setList(ArrayList<ChangeBean> list){
            this.mList = list;
            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext)
                    .inflate(R.layout.list_item_change_record,parent,false);
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

        private void initView(RecordViewHolder handler, ChangeBean bean){
            if (bean == null) return;
            int w2 = (94*sw)/720;
            Glide.with(mContext).load(bean.getVisitHeadUrl())
                    .apply(new RequestOptions()
                    .override(w2,w2)
                    .error(R.drawable.img_patient_photo)
                    .centerCrop())
                    .into(handler.patientIV);

            Glide.with(mContext).load(bean.getToDoctorHeadUrl())
                    .apply(new RequestOptions()
                    .override(w2,w2)
                    .error(R.drawable.img_doctor_temp)
                    .centerCrop())
                    .into(handler.doctorIV);

            handler.hospitalTV.setText("");
            handler.timeTV.setText(bean.getStartTime());

            handler.doctorNameTV.setText(bean.getToDoctorName());
            String type = bean.getToDoctorAcademicTitle();
            if ("1".equals(type)){
                handler.doctorTxtTV.setText("主任医师");
            }else if("2".equals(type)){
                handler.doctorTxtTV.setText("副主任医师");
            }else if("3".equals(type)){
                handler.doctorTxtTV.setText("主治医师");
            }
            handler.patientNameTV.setText(bean.getPatientName());
            handler.patientTxtTV.setText("患者");

        }
    }

    /**
     * 静态类
     */
    private static class RecordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView doctorIV;
        public ImageView patientIV;
        public MyTextView timeTV;
        public MyTextView hospitalTV;
        public MyTextView doctorNameTV;
        public MyTextView patientNameTV;
        public MyTextView doctorTxtTV;
        public MyTextView patientTxtTV;
        private OnItemClick onItemClick;

        public RecordViewHolder(View view, OnItemClick onItemClick){
            super(view);
            this.onItemClick = onItemClick;
            view.setOnClickListener(this);
            doctorIV = (ImageView)view.findViewById(R.id.list_item_doctor);
            doctorNameTV = (MyTextView) view.findViewById(R.id.list_item_doctor_name);
            doctorTxtTV = (MyTextView) view.findViewById(R.id.list_item_doctor_txt);

            patientIV = (ImageView)view.findViewById(R.id.list_item_ill);
            patientNameTV = (MyTextView) view.findViewById(R.id.list_item_ill_name);
            patientTxtTV = (MyTextView) view.findViewById(R.id.list_item_ill_txt);

            timeTV = (MyTextView) view.findViewById(R.id.list_item_time);
            hospitalTV = (MyTextView) view.findViewById(R.id.list_item_hospital);

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
    public void reloadList(ArrayList<ChangeBean> list) {
        if (page == 1){
            refreshLayout.setRefreshing(false);
            array = list;
            recordListAdapter.setList(array);
        }

        if (page > 1){
            for (ChangeBean bean:
                 list) {
                array.add(bean);
            }
            recordListAdapter.setList(array);
        }
    }
}
