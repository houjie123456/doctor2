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
import com.company.linquan.app.moduleWork.WorkInterface;
import com.company.linquan.app.moduleWork.imp.SelectPatientPresenterImp;
import com.company.linquan.app.util.ExitApp;
import com.company.linquan.app.util.MyDialog;
import com.company.linquan.app.util.MyToast;
import com.company.linquan.app.view.DividerItemDecoration;
import com.company.linquan.app.view.MyTextView;
import com.company.linquan.app.view.RoundImageView;

import java.util.ArrayList;

/**
 * Created by YC on 2018/7/14.
 */

public class SelectPatientActivity extends BaseActivity implements WorkInterface.SelectPatientInterface {

    private Dialog myDialog;

    private RecyclerView recordRecycler;
    private ArrayList<PatientBean> array;
    private RecordListAdapter adapter;
    private SwipeRefreshLayout refreshLayout;
    int sw;
    private final static int ADD = 1;
    private SelectPatientPresenterImp presenter;
    private int page = 1;
    private String ids = "";
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExitApp.getInstance().addActivity(this);		// add Activity 方便退出
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 开启自定义标题栏
        setContentView(R.layout.activity_select_person);
        initData();
        initHead();
        initView();
        setListener();
        getData();
    }

    private void initData() {
        ids = getIntent().getStringExtra("ids");
        index = getIntent().getIntExtra("index",0);
    }

    private void getData() {
        presenter.getPatient("",page);
    }

    private void initHead() {
        RelativeLayout head = (RelativeLayout) findViewById(R.id.head_layout);
        MyTextView title = (MyTextView) head.findViewById(R.id.head_top_title);
        title.setText("选择就诊人");
        ImageView leftIV = (ImageView)head.findViewById(R.id.head_top_image);
        leftIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        MyTextView rightTV = (MyTextView) head.findViewById(R.id.head_top_right_menu);
        rightTV.setText("完成");
        rightTV.setVisibility(View.VISIBLE);
        rightTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String visitId = "";
                String patinetId = "";
                String names = "";
                for (PatientBean bean:
                        array) {
                    if (bean.isSelected()){
                        visitId = bean.getVisitID();
                        names = bean.getVisitName();
                        patinetId = bean.getPatientID();
                    }

                }
                Intent intent = new Intent();
                intent.putExtra("visitId",visitId);
                intent.putExtra("patientId",patinetId);
                intent.putExtra("names",names);
                setResult(RESULT_OK,intent);
                finish();
            }
        });


    }

    private void initView(){
        presenter = new SelectPatientPresenterImp(this);
        sw = getContext().getResources().getDisplayMetrics().widthPixels;
        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.select_refresh);
        refreshLayout.setColorSchemeColors(ContextCompat.getColor(this,R.color.base_red_color));
        recordRecycler = (RecyclerView)findViewById(R.id.select_recycler);
        recordRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));
        array = new ArrayList<>();
        adapter = new RecordListAdapter(getContext(),array);
        recordRecycler.setAdapter(adapter);
        recordRecycler.setItemAnimator(new DefaultItemAnimator());
        recordRecycler.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST,
                ContextCompat.getDrawable(this,R.drawable.shape_list_line_style)));
    }

    private void setListener() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getData();
            }
        });


        adapter.setOnItemClickListener(new OnItemClick() {
            @Override
            public void onItemClick(View view, int position) {
                for (PatientBean bean:
                     array) {
                    bean.setSelected(false);
                }
                array.get(position).setSelected(true);
                adapter.setList(array);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD && resultCode == RESULT_OK)
        {
            page = 1;
            getData();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * recycler 适配器
     */
    private class RecordListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        private Context mContext;
        private ArrayList<PatientBean> mList;
        private OnItemClick onItemClickListener;

        public RecordListAdapter(Context context,ArrayList<PatientBean> list ){
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
                    .inflate(R.layout.list_item_select_person,parent,false);
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

        private void initView(RecordViewHolder handler, PatientBean bean){
            if (bean == null) return;
            int w = (118*sw)/720;
            String url = "";
            if (!"".equals(bean.getVisitHeadUrl())){
                url = bean.getVisitHeadUrl();
            }

            if (!"".equals(bean.getPatientHeadUrl())){
                url = bean.getPatientHeadUrl();
            }
            Glide.with(mContext).load(url).apply(new RequestOptions().override(w,w).centerCrop()).into(handler.photoIV);

            handler.mobileTV.setText(bean.getVisitMobile());
            handler.nameTV.setText(bean.getVisitName());
            if (bean.isSelected()){
                handler.selectIV.setBackgroundResource(R.drawable.img_stop_face_selected);
            }else{
                handler.selectIV.setBackgroundResource(R.drawable.img_stop_face_select);
            }
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
        public MyTextView mobileTV;
        private OnItemClick onItemClick;
        public ImageView selectIV;

        public RecordViewHolder(View view, OnItemClick onItemClick){
            super(view);
            this.onItemClick = onItemClick;
            view.setOnClickListener(this);
            photoIV = (RoundImageView) view.findViewById(R.id.list_item_photo);
            photoIV.setDrawCircle();
            selectIV = (ImageView) view.findViewById(R.id.list_item_select);
            nameTV = (MyTextView) view.findViewById(R.id.list_item_name);
            mobileTV = (MyTextView) view.findViewById(R.id.list_item_mobile);
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
    public void reloadList(ArrayList<PatientBean> list) {
        if (page == 1){
            array = list;
            adapter.setList(array);
            refreshLayout.setRefreshing(false);
        }else if(page > 1){
            for (PatientBean b:
                    list) {
                if (ids.equals(b.getPatientID())){
                    b.setSelected(true);
                }
                array.add(b);
            }
            adapter.setList(array);
        }
    }
}
