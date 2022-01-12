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

import com.company.linquan.app.R;
import com.company.linquan.app.base.BaseActivity;
import com.company.linquan.app.bean.DiseaseBean;
import com.company.linquan.app.bean.DiseaseGroupBean;
import com.company.linquan.app.bean.PatientBean;
import com.company.linquan.app.config.C;
import com.company.linquan.app.moduleWork.WorkInterface;
import com.company.linquan.app.moduleWork.imp.PatientGroupPresenterImp;
import com.company.linquan.app.moduleWork.imp.PatientPresenterImp;
import com.company.linquan.app.util.ExitApp;
import com.company.linquan.app.util.MyDialog;
import com.company.linquan.app.util.MyToast;
import com.company.linquan.app.util.ToolSharePerference;
import com.company.linquan.app.view.MyTextView;
import com.company.linquan.app.view.SearchBar;

import java.util.ArrayList;

/**
 * Created by YC on 2018/6/24.
 */

public class SearchDiseaseActivity extends BaseActivity implements WorkInterface.DiseaseListInterface{
    private Dialog myDialog;

    private RecyclerView recordRecycler;
    private ArrayList<DiseaseBean> array;
    private RecordListAdapter adapter;
    private SwipeRefreshLayout refreshLayout;
    private MyTextView searchTV;
    String docID = "";
    String large = "";
    String icdID = "";
    String icdName = "";
    private PatientGroupPresenterImp presenter;
    private SearchBar searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExitApp.getInstance().addActivity(this);		// add Activity 方便退出
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 开启自定义标题栏
        setContentView(R.layout.activity_select_data);
        initHead();
        initView();
        setListener();
//        getData();
    }

    private void getData() {
        docID=ToolSharePerference.getStringData(getContext(), C.fileconfig,C.UserID);
        presenter.getDiseaseList(large);
    }



    private void initHead() {
        RelativeLayout head = (RelativeLayout) findViewById(R.id.head_layout);
        MyTextView title = (MyTextView) head.findViewById(R.id.head_top_title);
        title.setText("搜索疾病");
        ImageView rightIV = (ImageView)head.findViewById(R.id.head_top_image);
        rightIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView(){
        presenter = new PatientGroupPresenterImp(this);

        searchBar = (SearchBar) findViewById(R.id.select_search);
        searchBar.setLayoutStyle(R.drawable.shape_search_bar_style);
        searchBar.setVisibility(View.VISIBLE);
        searchTV=findViewById(R.id.search_tv);

        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.select_refresh);
        refreshLayout.setColorSchemeColors(ContextCompat.getColor(this,R.color.base_red_color));
        recordRecycler = (RecyclerView)findViewById(R.id.select_recycler);
        recordRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));
        array = new ArrayList<>();
        adapter = new RecordListAdapter(getContext(),array);
        recordRecycler.setAdapter(adapter);
        recordRecycler.setItemAnimator(new DefaultItemAnimator());
    }

    private void setListener() {

        searchTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                large = searchBar.getRequestKey();
                getData();
            }
        });
        searchBar.setOnEditTextDataChanged(new SearchBar.OnEditTextDataChanged() {
            @Override
            public void onTextisEmpty() {
            }

            @Override
            public void onTextChanged() {

            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });


        adapter.setOnItemClickListener(new OnItemClick() {
            @Override
            public void onItemClick(View view, int position) {

                presenter.checkDisease(docID,array.get(position).getIcdname());
                icdName=array.get(position).getIcdname();
                icdID=array.get(position).getId();
            }
        });
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
    public void checkType(String checkType) {Intent intent = new Intent();
        intent.putExtra("icdName",icdName);
        intent.putExtra("icdID",icdID);
        intent.putExtra("checkType",checkType);
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    public void reload() {

    }


    /**
     * recycler 适配器
     */
    private class RecordListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        private Context mContext;
        private  ArrayList<DiseaseBean> mList;
        private OnItemClick onItemClickListener;

        public RecordListAdapter(Context context, ArrayList<DiseaseBean> list ){
            this.mContext = context;
            this.mList = list;
        }


        public void setList( ArrayList<DiseaseBean> list){
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

        private void initView(RecordViewHolder handler, DiseaseBean  bean){
            if (bean == null) return;

            handler.dataTV.setText(bean.getIcdname());
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
        public MyTextView levelTV;
        private OnItemClick onItemClick;

        public RecordViewHolder(View view, OnItemClick onItemClick){
            super(view);
            this.onItemClick = onItemClick;
            view.setOnClickListener(this);
            contentTV = (MyTextView) view.findViewById(R.id.list_item_content);
            dataTV = (MyTextView) view.findViewById(R.id.list_item_data);
            levelTV = (MyTextView) view.findViewById(R.id.list_item_level);
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

}
