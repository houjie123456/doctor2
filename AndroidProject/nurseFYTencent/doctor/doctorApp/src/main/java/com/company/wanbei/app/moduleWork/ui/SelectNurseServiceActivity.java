package com.company.wanbei.app.moduleWork.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.company.wanbei.app.R;
import com.company.wanbei.app.base.BaseActivity;
import com.company.wanbei.app.bean.NurseServeBean;
import com.tencent.qcloud.tuikit.tuichat.fromApp.config.C;
import com.company.wanbei.app.moduleWork.WorkInterface;
import com.company.wanbei.app.moduleWork.imp.SelectNurseServicePresenterImp;
import com.company.wanbei.app.util.ExitApp;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.MyDialog;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.MyToast;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.ToolSharePerference;
import com.company.wanbei.app.view.DividerItemDecoration;
import com.tencent.qcloud.tuikit.tuichat.fromApp.view.MyTextView;

import java.util.ArrayList;

//import androidx.core.content.ContextCompat;
//import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
//import androidx.recyclerview.widget.DefaultItemAnimator;
//import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * Created by YC on 2018/8/4.
 */

public class SelectNurseServiceActivity extends BaseActivity implements WorkInterface.SelectNurseServiceInterface{
    private Dialog myDialog;

    private RecyclerView recordRecycler;
    private ArrayList<NurseServeBean> array;
    private RecordListAdapter adapter;
    private SwipeRefreshLayout refreshLayout;
    int sw;
    private final static int ADD = 1,NURSESERVICE=6;
    private SelectNurseServicePresenterImp presenter;
    private int page = 1;
    private String ids = "";
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExitApp.getInstance().addActivity(this);		// add Activity 方便退出
//        requestWindowFeature(Window.FEATURE_NO_TITLE); // 开启自定义标题栏
        setContentView(R.layout.activity_select_address);
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
        int type=getIntent().getIntExtra("type",0);
        if(type==6){
            presenter.getNurseService("",ids);
        }else {
            String docID= ToolSharePerference.getStringData(getContext(), C.fileconfig, C.UserID);
            presenter.getNurseService(docID,ids);
        }
    }

    private void initHead() {
        RelativeLayout head = (RelativeLayout) findViewById(R.id.head_layout);
        MyTextView title = (MyTextView) head.findViewById(R.id.head_top_title);
        title.setText("选择类型");
        ImageView leftIV = (ImageView)head.findViewById(R.id.head_top_image);
        leftIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

//        MyTextView rightTV = (MyTextView) head.findViewById(R.id.head_top_right_menu);
//        rightTV.setText("新增");
//        rightTV.setVisibility(View.VISIBLE);
//        rightTV.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent();
//                intent.setClass(SelectNurseServiceActivity.this, AddAddressActivity.class);
//                startActivityForResult(intent,ADD);
//            }
//        });
    }

    private void initView(){
        presenter = new SelectNurseServicePresenterImp(this);
        sw = getContext().getResources().getDisplayMetrics().widthPixels;
        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.select_refresh);
        refreshLayout.setColorSchemeColors(ContextCompat.getColor(this, R.color.base_red_color));
        recordRecycler = (RecyclerView)findViewById(R.id.select_recycler);
        recordRecycler.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL, false));
        array = new ArrayList<>();
        adapter = new RecordListAdapter(getContext(),array);
        recordRecycler.setAdapter(adapter);
        recordRecycler.setItemAnimator(new DefaultItemAnimator());
        recordRecycler.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST,
                ContextCompat.getDrawable(this, R.drawable.shape_list_line_style)));
    }

    private void setListener() {
        adapter.setOnItemClickListener(new OnItemClick() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent();
//                if(getIntent().getIntExtra("type",0)==NURSESERVICE){
//                    getIntent().getStringArrayListExtra("nurseList").add(array.get(position).getServiceName());
//                    getIntent().getStringArrayListExtra("nurseIDS").add(array.get(position).getId());
//                    ArrayList<String> array2 = new ArrayList<>();
//                    array2.add(array.get(position).getServiceName());
//                    intent.putStringArrayListExtra("nurseList",array2);
//                    ArrayList<String> array3 = new ArrayList<>();
//                    array3.add(array.get(position).getId());
//                    intent.putStringArrayListExtra("nurseIDS",array3);
//                    intent.putExtra("nurseID",array.get(position).getId());
//                    intent.putExtra("nurseService",array.get(position).getServiceName());
//                }
                intent.putExtra("nurseService",array.get(position).getServiceName());
                intent.putExtra("nurseServeID",array.get(position).getId());
                setResult(RESULT_OK,intent);
                finish();
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
            Intent intent = new Intent();
            intent.putExtra("serviceName",data.getStringExtra("serviceName"));
            setResult(RESULT_OK,intent);
            finish();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }



    /**
     * recycler 适配器
     */
    private class RecordListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        private Context mContext;
        private ArrayList<NurseServeBean> mList;
        private OnItemClick onItemClickListener;

        public RecordListAdapter(Context context, ArrayList<NurseServeBean> list ){
            this.mContext = context;
            this.mList = list;
        }


        public void setList(ArrayList<NurseServeBean> list){
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

        private void initView(RecordViewHolder handler, NurseServeBean bean){
            if (bean == null) return;
            handler.titleTV.setText(bean.getServiceName());
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
        public MyTextView contentTV;
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

    @Override
    public void reloadList(ArrayList<NurseServeBean> list) {
        refreshLayout.setRefreshing(false);
        array = list;
        adapter.setList(array);
    }
}
