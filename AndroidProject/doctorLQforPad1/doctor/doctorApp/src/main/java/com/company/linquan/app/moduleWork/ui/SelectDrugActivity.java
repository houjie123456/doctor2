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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.company.linquan.app.R;
import com.company.linquan.app.base.BaseActivity;
import com.company.linquan.app.bean.DrugStoreBean;
import com.company.linquan.app.moduleWork.WorkInterface;
import com.company.linquan.app.moduleWork.imp.SelectDrugPresenterImp;
import com.company.linquan.app.util.ExitApp;
import com.company.linquan.app.util.MyDialog;
import com.company.linquan.app.util.MyToast;
import com.company.linquan.app.view.DividerItemDecoration;
import com.company.linquan.app.view.MyTextView;
import com.company.linquan.app.view.SearchBar;

import java.util.ArrayList;

/**
 * Created by YC on 2018/7/1.
 */

public class SelectDrugActivity extends BaseActivity implements WorkInterface.SelectDrugInterface{
    private Dialog myDialog;

    private RecyclerView recordRecycler;
    private ArrayList<DrugStoreBean> array = new ArrayList<>();
    private RecordListAdapter adapter;
    private SwipeRefreshLayout refreshLayout;

    private SelectDrugPresenterImp presenter;
    private int page = 1;
    private String drugName = "";
    private SearchBar searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExitApp.getInstance().addActivity(this);		// add Activity 方便退出
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 开启自定义标题栏
        setContentView(R.layout.activity_select_drug);
        initHead();
        initView();
        setListener();
        getData();
    }

    private void initHead() {
        RelativeLayout head = (RelativeLayout) findViewById(R.id.head_layout);
        MyTextView title = (MyTextView) head.findViewById(R.id.head_top_title);
        title.setText("选择");
        ImageView leftIV = (ImageView)head.findViewById(R.id.head_top_image);
        leftIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeKeyboard();
                finish();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            closeKeyboard();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initView(){

        presenter = new SelectDrugPresenterImp(this);

        searchBar = (SearchBar)findViewById(R.id.select_search);
        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.select_refresh);
        refreshLayout.setColorSchemeColors(ContextCompat.getColor(this,R.color.base_red_color));
        recordRecycler = (RecyclerView)findViewById(R.id.select_recycler);
        recordRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));
        adapter = new RecordListAdapter(getContext(),array);
        recordRecycler.setAdapter(adapter);
        recordRecycler.setItemAnimator(new DefaultItemAnimator());
        recordRecycler.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST,
                ContextCompat.getDrawable(this,R.drawable.shape_list_line_style)));
    }

    private void getData() {
        presenter.getDrugStore(drugName,page);
    }

    private void setListener() {
        searchBar.setOnEditTextDataChanged(new SearchBar.OnEditTextDataChanged() {
            @Override
            public void onTextisEmpty() {
                drugName = "";
                page = 1;
                getData();
            }

            @Override
            public void onTextChanged() {
                drugName = searchBar.getRequestKey();
                page = 1;
                getData();
                showKeyboard();
            }
        });


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
                Intent intent = new Intent();
                Bundle b = new Bundle();
                b.putString("id",array.get(position).getId());
                b.putString("name",array.get(position).getDrugName().getCommonName());
                b.putString("standard",array.get(position).getStandard());
                b.putString("price",array.get(position).getDrugPrice());
                intent.putExtras(b);
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

                closeKeyboard();
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

    private void showKeyboard(){
        try{
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.showSoftInput(searchBar, 0);
        }catch (Exception e){}

    }

    private void closeKeyboard() {
        try{
            View view = getWindow().peekDecorView();
            if (view != null) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }catch (Exception e){}

    }


    /**
     * recycler 适配器
     */
    private class RecordListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        private Context mContext;
        private ArrayList<DrugStoreBean> mList;
        private OnItemClick onItemClickListener;

        public RecordListAdapter(Context context,ArrayList<DrugStoreBean> list ){
            this.mContext = context;
            this.mList = list;
        }


        public void setList(ArrayList<DrugStoreBean> list){
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

        private void initView(RecordViewHolder handler, DrugStoreBean bean){
            if (bean == null) return;
            handler.dataTV.setText(bean.getDrugName().getCommonName());
            handler.contentTV.setText("规格:"+bean.getStandard());
            handler.contentTV.setVisibility(View.VISIBLE);
        }
    }

    public interface OnItemClick{
        public void onItemClick(View view, int position);
    }

    /**
     * 静态类
     */
    private static class RecordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public MyTextView dataTV;
        public MyTextView contentTV;
        private OnItemClick onItemClick;

        public RecordViewHolder(View view, OnItemClick onItemClick){
            super(view);
            this.onItemClick = onItemClick;
            view.setOnClickListener(this);
            dataTV = (MyTextView) view.findViewById(R.id.list_item_data);
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
    public void reloadList(ArrayList<DrugStoreBean> list) {

        if (page == 1){
            refreshLayout.setRefreshing(false);
            array = list;
            adapter.setList(array);
        }

        if (page > 1){
            for (DrugStoreBean bean:
                 list) {
                array.add(bean);
            }
            adapter.setList(array);
        }

    }
}
