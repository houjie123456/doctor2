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
import com.company.linquan.app.bean.SelectDataBean;
import com.company.linquan.app.config.C;
import com.company.linquan.app.moduleAuth.AuthActivity;
import com.company.linquan.app.moduleAuth.AuthInterface;
import com.company.linquan.app.moduleAuth.imp.SelectDataPresenterImp;
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

public class SearchPatientActivity extends BaseActivity implements WorkInterface.PatientInterface{
    private Dialog myDialog;

    private RecyclerView recordRecycler;
    private ArrayList<PatientBean> array;
    private RecordListAdapter adapter;
    private SwipeRefreshLayout refreshLayout;

    String doctorID = "";
    String icdName = "";
    String patient = "";
    private PatientPresenterImp presenter;
    private String keyWords = "";
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
        getData();
    }

    private void getData() {
        doctorID= ToolSharePerference.getStringData(getContext(), C.fileconfig,C.UserID);

        presenter.getMyPatientInfo(doctorID,icdName,patient);
    }



    private void initHead() {
        RelativeLayout head = (RelativeLayout) findViewById(R.id.head_layout);
        MyTextView title = (MyTextView) head.findViewById(R.id.head_top_title);
        title.setText("选择");
        ImageView rightIV = (ImageView)head.findViewById(R.id.head_top_image);
        rightIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView(){
        presenter = new PatientPresenterImp(this);

        searchBar = (SearchBar) findViewById(R.id.select_search);
        searchBar.setLayoutStyle(R.drawable.shape_search_bar_style);
        searchBar.setVisibility(View.VISIBLE);

        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.select_refresh);
        refreshLayout.setColorSchemeColors(ContextCompat.getColor(this,R.color.base_red_color));
        recordRecycler = (RecyclerView)findViewById(R.id.select_recycler);
        recordRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));
        array = new ArrayList<>();
        adapter = new RecordListAdapter(getContext(),array);
        recordRecycler.setAdapter(adapter);
        recordRecycler.setItemAnimator(new DefaultItemAnimator());

        searchBar.setVisibility(View.VISIBLE); //医院
    }

    private void setListener() {

        searchBar.setOnEditTextDataChanged(new SearchBar.OnEditTextDataChanged() {
            @Override
            public void onTextisEmpty() {
            }

            @Override
            public void onTextChanged() {
                patient = searchBar.getRequestKey();
                getData();
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
                Intent intent = new Intent();
                intent.setClass(SearchPatientActivity.this,PatientInfoActivity.class);
                intent.putExtra("visitId",array.get(position).getVisitID());
                intent.putExtra("patientId",array.get(position).getPatientID());
                intent.putExtra("name",array.get(position).getVisitName()
                        +" "+ ("1".equals(array.get(position).getVisitSex())? "男":"女")
                        +" "+array.get(position).getVisitAge()+"岁");
                if (!"".equals(array.get(position).getVisitMobile())){
                    intent.putExtra("mobile",array.get(position).getVisitMobile());
                }
                if (!"".equals(array.get(position).getPatientMobile())){
                    intent.putExtra("mobile",array.get(position).getPatientMobile());
                }
                if (!"".equals(array.get(position).getVisitHeadUrl())){
                    intent.putExtra("url",array.get(position).getVisitHeadUrl());
                }
                if (!"".equals(array.get(position).getPatientHeadUrl())){
                    intent.putExtra("url",array.get(position).getPatientHeadUrl());
                }
                startActivity(intent);
            }
        });
    }

    @Override
    public void reloadList(ArrayList<PatientBean> list) {
        refreshLayout.setRefreshing(false);
        array = list;
        adapter.setList(array);
    }


    /**
     * recycler 适配器
     */
    private class RecordListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        private Context mContext;
        private  ArrayList<PatientBean> mList;
        private OnItemClick onItemClickListener;

        public RecordListAdapter(Context context, ArrayList<PatientBean> list ){
            this.mContext = context;
            this.mList = list;
        }


        public void setList( ArrayList<PatientBean> list){
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

        private void initView(RecordViewHolder handler, PatientBean  bean){
            if (bean == null) return;

            handler.dataTV.setText(bean.getPatientName());
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
