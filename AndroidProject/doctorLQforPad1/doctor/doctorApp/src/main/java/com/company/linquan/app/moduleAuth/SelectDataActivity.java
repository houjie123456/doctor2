package com.company.linquan.app.moduleAuth;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.company.linquan.app.R;
import com.company.linquan.app.base.BaseActivity;
import com.company.linquan.app.bean.SelectDataBean;
import com.company.linquan.app.moduleAuth.imp.SelectDataPresenterImp;
import com.company.linquan.app.moduleWork.ui.AddDrugsActivity;
import com.company.linquan.app.moduleWork.ui.AddVisitActivity;
import com.company.linquan.app.moduleWork.ui.SettingPictureActivity;
import com.company.linquan.app.util.ExitApp;
import com.company.linquan.app.util.MyDialog;
import com.company.linquan.app.util.MyToast;
import com.company.linquan.app.view.MyTextView;
import com.company.linquan.app.view.SearchBar;
import com.hb.dialog.myDialog.MyAlertInputDialog;

import java.util.ArrayList;

/**
 * Created by YC on 2018/6/24.
 */

public class SelectDataActivity extends BaseActivity implements AuthInterface.SelectInterface{
    private Dialog myDialog;

    private RecyclerView recordRecycler;
    private ArrayList<SelectDataBean> array;
    private RecordListAdapter adapter;
    private SwipeRefreshLayout refreshLayout;

    private int type;
    private SelectDataPresenterImp presenter;
    private String keyWords = "";
    private SearchBar searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExitApp.getInstance().addActivity(this);		// add Activity 方便退出
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 开启自定义标题栏
        setContentView(R.layout.activity_select_data);
        initData();
        initHead();
        initView();
        setListener();
        getData();
    }

    private void getData() {
        if (type == AuthActivity.SEX) presenter.getSex(); //性别
        if (type == AuthActivity.TYPE) presenter.getJob(); //类型
        if (type == AuthActivity.POSITION) presenter.getPosition(); //职称
        if (type == AuthActivity.DEPATE) presenter.getDepartment(); //科室
        if (type == AuthActivity.HOSPITAL) presenter.getHospital(keyWords); //医院
        if (type == AddDrugsActivity.DRUG_TIME) presenter.getTime(); //服用时间
        if (type == AddDrugsActivity.DRUG_EAT) presenter.getEat(); //服用方式
        if (type == SettingPictureActivity.TIME) presenter.getSettingPictureTime(); //设置图文问诊时长
        if (type == SettingPictureActivity.NUM) presenter.getSettingPictureNum(); //设置图文问诊次数
        if (type == AddVisitActivity.TYPE) presenter.getVisitType(); //设置添加就诊类型
    }

    private void initData() {
        type = getIntent().getIntExtra("type",1);
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
        presenter = new SelectDataPresenterImp(this);

        searchBar = (SearchBar) findViewById(R.id.select_search);
        searchBar.setLayoutStyle(R.drawable.shape_search_bar_style);
        searchBar.setVisibility(View.GONE);

        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.select_refresh);
        refreshLayout.setColorSchemeColors(ContextCompat.getColor(this,R.color.base_red_color));
        recordRecycler = (RecyclerView)findViewById(R.id.select_recycler);
        recordRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));
        array = new ArrayList<>();
        adapter = new RecordListAdapter(getContext(),array);
        recordRecycler.setAdapter(adapter);
        recordRecycler.setItemAnimator(new DefaultItemAnimator());

        if (type == AuthActivity.HOSPITAL) searchBar.setVisibility(View.VISIBLE); //医院

        if (type == SettingPictureActivity.TIME) ; //显示自定义选项
    }

    private void setListener() {

        searchBar.setOnEditTextDataChanged(new SearchBar.OnEditTextDataChanged() {
            @Override
            public void onTextisEmpty() {
            }

            @Override
            public void onTextChanged() {
                keyWords = searchBar.getRequestKey();
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
                Bundle b = new Bundle();
                b.putString("id",array.get(position).getId());
                if (type == AuthActivity.SEX) b.putString("name",array.get(position).getSex()); //性别
                if (type == AuthActivity.TYPE) b.putString("name",array.get(position).getJobName()); //类型
                if (type == AuthActivity.POSITION) b.putString("name",array.get(position).getIdVal()); //职称
                if (type == AuthActivity.DEPATE) b.putString("name",array.get(position).getDepartmentName()); //科室
                if (type == AuthActivity.HOSPITAL) b.putString("name",array.get(position).getHospitalName()); //医院
                if (type == AddDrugsActivity.DRUG_TIME) b.putString("name",array.get(position).getPositionName()); //服用时间
                if (type == AddDrugsActivity.DRUG_EAT) b.putString("name",array.get(position).getPositionName()); //服用方式
                if (type == SettingPictureActivity.TIME ) {//问诊时长
                    if(position == array.size()-1){
                        final MyAlertInputDialog myAlertInputDialog = new MyAlertInputDialog(SelectDataActivity.this).builder()
                                .setTitle("请输入问诊时长(单位：天)")
                                .setEditText("").setEditType(InputType.TYPE_CLASS_NUMBER);
                        myAlertInputDialog.setPositiveButton("确认", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                b.putString("id",myAlertInputDialog.getResult());
                                b.putString("name",myAlertInputDialog.getResult()+"天");
                                intent.putExtras(b);
                                setResult(RESULT_OK,intent);

                                myAlertInputDialog.dismiss();
                                finish();
                            }
                        }).setNegativeButton("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                myAlertInputDialog.dismiss();
                            }
                        });
                        myAlertInputDialog.show();
                        return;
                    }else {
                        b.putString("name",array.get(position).getPositionName());
                    }
                }
                if (type == SettingPictureActivity.NUM ) {//问诊条数
                    if(position == array.size()-1){
                        final MyAlertInputDialog myAlertInputDialog = new MyAlertInputDialog(SelectDataActivity.this).builder()
                                .setTitle("请输入问诊条数(单位：条)")
                                .setEditText("").setEditType(InputType.TYPE_CLASS_NUMBER);
                        myAlertInputDialog.setPositiveButton("确认", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                b.putString("id",myAlertInputDialog.getResult());
                                b.putString("name",myAlertInputDialog.getResult()+"条");
                                intent.putExtras(b);
                                setResult(RESULT_OK,intent);

                                myAlertInputDialog.dismiss();
                                finish();
                            }
                        }).setNegativeButton("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                myAlertInputDialog.dismiss();
                            }
                        });
                        myAlertInputDialog.show();
                        return;
                    }else {
                        b.putString("name",array.get(position).getPositionName());
                    }
                }
                if (type == AddVisitActivity.TYPE) b.putString("name",array.get(position).getPositionName()); //服用方式
                intent.putExtras(b);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }

    /**
     * recycler 适配器
     */
    private class RecordListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        private Context mContext;
        private  ArrayList<SelectDataBean> mList;
        private OnItemClick onItemClickListener;

        public RecordListAdapter(Context context, ArrayList<SelectDataBean> list ){
            this.mContext = context;
            this.mList = list;
        }


        public void setList( ArrayList<SelectDataBean> list){
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

        private void initView(RecordViewHolder handler, SelectDataBean bean){
            if (bean == null) return;
            if (type == AuthActivity.SEX)handler.dataTV.setText(bean.getSex()); //性别
            if (type == AuthActivity.TYPE)handler.dataTV.setText(bean.getJobName()); //类型
            if (type == AuthActivity.POSITION)handler.dataTV.setText(bean.getIdVal()); //职称
            if (type == AuthActivity.DEPATE)handler.dataTV.setText(bean.getDepartmentName()); //科室
            if (type == AuthActivity.HOSPITAL){
                handler.dataTV.setText(bean.getHospitalName()); //医院
                handler.contentTV.setText(bean.getAddress()); //医院
                handler.contentTV.setVisibility(View.VISIBLE);
                handler.levelTV.setVisibility(View.VISIBLE);
                String level = bean.getHospitalLevel();
                if ("1".equals(level)){
                    handler.levelTV.setText("三甲"); //医院
                }else if ("2".equals(level)){
                    handler.levelTV.setText("二甲"); //医院
                }else if ("3".equals(level)){
                    handler.levelTV.setText("三乙"); //医院
                }else if ("4".equals(level)){
                    handler.levelTV.setText("二乙"); //医院
                }else if ("5".equals(level)){
                    handler.levelTV.setText("三级综合"); //医院
                }else if ("6".equals(level)){
                    handler.levelTV.setText("二级综合"); //医院
                }else if ("7".equals(level)){
                    handler.levelTV.setText("一级以下"); //医院
                }else{
                    handler.levelTV.setText(""); //医院
                }

            }
            if (type == AddDrugsActivity.DRUG_TIME)handler.dataTV.setText(bean.getPositionName()); //服用时间
            if (type == AddDrugsActivity.DRUG_EAT)handler.dataTV.setText(bean.getPositionName()); //服用方式
            if (type == SettingPictureActivity.TIME)handler.dataTV.setText(bean.getPositionName()); //问诊时间
            if (type == SettingPictureActivity.NUM)handler.dataTV.setText(bean.getPositionName()); //问诊条数
            if (type == AddVisitActivity.TYPE)handler.dataTV.setText(bean.getPositionName()); //服用方式

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

    @Override
    public void setListData(ArrayList<SelectDataBean> list) {
        refreshLayout.setRefreshing(false);
        array = list;
        adapter.setList(list);
    }
}
