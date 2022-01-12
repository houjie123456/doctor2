package com.company.wanbei.app.moduleWork.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.company.wanbei.app.R;
import com.company.wanbei.app.bean.NurseServeBean;
import com.company.wanbei.app.bean.NurseServiceBean;
import com.company.wanbei.app.moduleWork.WorkInterface;
import com.company.wanbei.app.moduleWork.imp.NurseRecordListPresenterImp;
import com.company.wanbei.app.util.ExitApp;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.MyDialog;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.MyToast;
import com.tencent.qcloud.tuikit.tuichat.fromApp.view.MyTextView;
import com.tencent.qcloud.tuicore.util.ToastUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class NurseHistoryScreenActivity extends Activity implements WorkInterface.NurseServiceListInterface ,View.OnClickListener {

    private Dialog myDialog;

    private NurseRecordListPresenterImp presenter;
    private RecyclerView serviceStatusRV,nurseServiceRV,confirmStatusRV;
    private SparseArray<SparseArray<String>> serviceStatusArray,nurseServiceArray,confirmStatusArray;
    private MyListAdapter serviceStatusAdapter,nurseServiceAdapter,confirmStatusAdapter;
    private String confirmStatusID,serviceStateID,nurseID,startDate,endDate;
    private TextView resetTV,confirmTV,startDateTV,endDateTV;
    private LinearLayout startDateLL,endDateLL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 开启自定义标题栏
        super.onCreate(savedInstanceState);
        ExitApp.getInstance().addActivity(this);		// add Activity 方便退出
        setContentView(R.layout.activity_nurse_history_screen);
        getWindow().setLayout(900, WindowManager.LayoutParams.MATCH_PARENT);
        getWindow().getAttributes().gravity= Gravity.RIGHT;
        initData();
        initHead();

    }

    private void initHead() {
        RelativeLayout head = (RelativeLayout) findViewById(R.id.head_layout);
        MyTextView title = (MyTextView) head.findViewById(R.id.head_top_title);
        title.setText("筛选");
        ImageView rightIV = (ImageView)head.findViewById(R.id.head_top_image);
        rightIV.setVisibility(View.INVISIBLE);
    }
    private void initData(){
        serviceStatusArray = new SparseArray<>();
        nurseServiceArray = new SparseArray<>();
        confirmStatusArray = new SparseArray<>();
        presenter = new NurseRecordListPresenterImp(this);
        presenter.getNurseServiceList();


    }
    private void setListener() {
        serviceStatusAdapter.setOnItemClickListener(new OnItemClick() {
            @Override
            public void onItemClick(View view, int position) {
                serviceStateID = serviceStatusArray.get(position).get(1);
                for(int i=0;i<serviceStatusArray.size();i++){
                    if(i==position){
                        serviceStatusArray.get(i).put(2,"1");
                    }else {
                        serviceStatusArray.get(i).put(2,"0");
                    }
                }
                serviceStatusAdapter.setList(serviceStatusArray);
            }
        });
        nurseServiceAdapter.setOnItemClickListener(new OnItemClick() {
            @Override
            public void onItemClick(View view, int position) {
                nurseID = nurseServiceArray.get(position).get(1);
                for(int i=0;i<nurseServiceArray.size();i++){
                    if(i==position){
                        nurseServiceArray.get(i).put(2,"1");
                    }else {
                        nurseServiceArray.get(i).put(2,"0");
                    }
                }
                nurseServiceAdapter.setList(nurseServiceArray);
            }
        });
        confirmStatusAdapter.setOnItemClickListener(new OnItemClick() {
            @Override
            public void onItemClick(View view, int position) {
                confirmStatusID = confirmStatusArray.get(position).get(1);
                for(int i=0;i<confirmStatusArray.size();i++){
                    if(i==position){
                        confirmStatusArray.get(i).put(2,"1");
                    }else {
                        confirmStatusArray.get(i).put(2,"0");
                    }
                }
                confirmStatusAdapter.setList(confirmStatusArray);
            }
        });
    }
    private void initView(){
        nurseServiceRV= (RecyclerView)findViewById(R.id.nurse_service_list);
        nurseServiceRV.setLayoutManager(new GridLayoutManager(this,3,RecyclerView.VERTICAL, false));
//        payStatusArray = new SparseArray<>();
        nurseServiceAdapter = new MyListAdapter(this,nurseServiceArray);
        nurseServiceRV.setAdapter(nurseServiceAdapter);
        nurseServiceRV.setItemAnimator(new DefaultItemAnimator());

        serviceStatusRV= (RecyclerView)findViewById(R.id.service_status_list);
        serviceStatusRV.setLayoutManager(new GridLayoutManager(this,3,RecyclerView.VERTICAL, false));
//        inquiryStatusArray = new SparseArray<>();
        serviceStatusAdapter = new MyListAdapter(this,serviceStatusArray);
        serviceStatusRV.setAdapter(serviceStatusAdapter);
        serviceStatusRV.setItemAnimator(new DefaultItemAnimator());



        confirmStatusRV= (RecyclerView)findViewById(R.id.confirm_status_list);
        confirmStatusRV.setLayoutManager(new GridLayoutManager(this,3,RecyclerView.VERTICAL, false));
//        confirmStatusArray = new SparseArray<>();
        confirmStatusAdapter = new MyListAdapter(this,confirmStatusArray);
        confirmStatusRV.setAdapter(confirmStatusAdapter);
        confirmStatusRV.setItemAnimator(new DefaultItemAnimator());

        startDateTV = findViewById(R.id.start_date_show);
        endDateTV = findViewById(R.id.end_date_show);

        startDateLL = findViewById(R.id.start_date_layout);
        endDateLL = findViewById(R.id.end_date_layout);
        startDateLL.setOnClickListener(this);
        endDateLL.setOnClickListener(this);

        if(getIntent().getStringExtra("serviceStateID")!=null&&!getIntent().getStringExtra("serviceStateID").equals("")){
            serviceStateID = getIntent().getStringExtra("serviceStateID");
            for(int i=0;i<serviceStatusArray.size();i++){
                if(serviceStatusArray.get(i).get(1).equals(serviceStateID)){
                    serviceStatusArray.get(i).put(2,"1");
                }else {
                    serviceStatusArray.get(i).put(2,"0");
                }
            }
            serviceStatusAdapter.setList(serviceStatusArray);
            nurseID = getIntent().getStringExtra("nurseID");
            for(int i=0;i<nurseServiceArray.size();i++){
                if(nurseServiceArray.get(i).get(1).equals(nurseID)){
                    nurseServiceArray.get(i).put(2,"1");
                }else {
                    nurseServiceArray.get(i).put(2,"0");
                }
            }
            nurseServiceAdapter.setList(nurseServiceArray);
            confirmStatusID = getIntent().getStringExtra("confirmStatusID");
            for(int i=0;i<confirmStatusArray.size();i++){
                if(confirmStatusArray.get(i).get(1).equals(confirmStatusID)){
                    confirmStatusArray.get(i).put(2,"1");
                }else {
                    confirmStatusArray.get(i).put(2,"0");
                }
            }
            confirmStatusAdapter.setList(confirmStatusArray);

            startDate = getIntent().getStringExtra("startDate");
            startDateTV.setText(startDate);
            endDate = getIntent().getStringExtra("endDate");
            endDateTV.setText(endDate);
        }else {
            setResetTV();
        }


        resetTV = findViewById(R.id.reset_btn);
        resetTV.setOnClickListener(this);
        confirmTV = findViewById(R.id.confirm_btn);
        confirmTV.setOnClickListener(this);
    }

    @Override
    public void reloadNurseList(ArrayList<NurseServeBean> list) {
        SparseArray<String> ss0 = new SparseArray<>();
        ss0.put(0,"全部");
        ss0.put(1,"");
        ss0.put(2,"0");
        nurseServiceArray.put(0,ss0);
        for (int i=0;i<list.size();i++){
            SparseArray<String> ss1 = new SparseArray<>();
            ss1.put(0,list.get(i).getServiceName());
            ss1.put(1,list.get(i).getId());
            ss1.put(2,"0");
            nurseServiceArray.put(i+1,ss1);
        }
        SparseArray<String> s1 = new SparseArray<>();
        s1.put(0,"全部");
        s1.put(1,"");
        s1.put(2,"0");
        serviceStatusArray.put(0,s1);
        SparseArray<String> s2 = new SparseArray<>();
        s2.put(0,"未开始");
        s2.put(1,"0");
        s2.put(2,"0");
        serviceStatusArray.put(1,s2);
        SparseArray<String> s3 = new SparseArray<>();
        s3.put(0,"已出发");
        s3.put(1,"1");
        s3.put(2,"0");
        serviceStatusArray.put(2,s3);
        SparseArray<String> s4 = new SparseArray<>();
        s4.put(0,"已取消");
        s4.put(1,"2");
        s4.put(2,"0");
        serviceStatusArray.put(3,s4);
        SparseArray<String> s5 = new SparseArray<>();
        s5.put(0,"已结束");
        s5.put(1,"3");
        s5.put(2,"0");
        serviceStatusArray.put(4,s5);

        SparseArray<String> sss1 = new SparseArray<>();
        sss1.put(0,"全部");
        sss1.put(1,"");
        sss1.put(2,"0");
        confirmStatusArray.put(0,sss1);
        SparseArray<String> sss2 = new SparseArray<>();
        sss2.put(0,"未审核");
        sss2.put(1,"0");
        sss2.put(2,"0");
        confirmStatusArray.put(1,sss2);
        SparseArray<String> sss3 = new SparseArray<>();
        sss3.put(0,"通过");
        sss3.put(1,"1");
        sss3.put(2,"0");
        confirmStatusArray.put(2,sss3);
        SparseArray<String> sss4 = new SparseArray<>();
        sss4.put(0,"不通过");
        sss4.put(1,"2");
        sss4.put(2,"0");
        confirmStatusArray.put(3,sss4);

        initView();
        setListener();
    }

    @Override
    public void reloadNurseRecordList(ArrayList<NurseServiceBean> list) {

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

    /**
     * recycler 适配器
     */
    private class MyListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        private Context mContext;
        private SparseArray<SparseArray<String>> mList;
        private OnItemClick onItemClickListener;

        public MyListAdapter(Context context,SparseArray<SparseArray<String>> list ){
            this.mContext = context;
            this.mList = list;
        }


        public void setList(SparseArray<SparseArray<String>> list){
            this.mList = list;
            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext)
                    .inflate(R.layout.grid_item_screen,parent,false);
            return new MyViewHolder(view,onItemClickListener);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if(holder instanceof MyViewHolder){
                initView((MyViewHolder) holder, mList.get(position));
            }
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        private void setOnItemClickListener(OnItemClick listener){
            onItemClickListener = listener;
        }

        private void initView(MyViewHolder handler, SparseArray<String> bean){
            handler.titleTV.setText(bean.get(0));
            if(bean.get(2).equals("1")){
                handler.backLayout.setBackground(getResources().getDrawable(R.drawable.shape_corner_voice_history));
                handler.titleTV.setTextColor(getResources().getColor(R.color.colorBtn));
            }else {
                handler.backLayout.setBackgroundColor(getResources().getColor(R.color.colorBtnGrey));
                handler.titleTV.setTextColor(getResources().getColor(R.color.colorBtnText));
            }
        }
    }


    public interface OnItemClick{
        public void onItemClick(View view, int position);
    }

    /**
     * 静态类
     */
    private static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ConstraintLayout backLayout;
        public MyTextView titleTV;
        private OnItemClick onItemClick;

        public MyViewHolder(View view, OnItemClick onItemClick){
            super(view);
            this.onItemClick = onItemClick;
            view.setOnClickListener(this);
            backLayout = view.findViewById(R.id.grid_item_back);
            titleTV = (MyTextView) view.findViewById(R.id.grid_item_name);
        }

        @Override
        public void onClick(View view) {
            if(onItemClick != null){
                onItemClick.onItemClick(view,getLayoutPosition());
            }
        }
    }

    public void setResetTV() {
        serviceStateID = serviceStatusArray.get(0).get(1);
        for(int i=0;i<serviceStatusArray.size();i++){
            if(i==0){
                serviceStatusArray.get(i).put(2,"1");
            }else {
                serviceStatusArray.get(i).put(2,"0");
            }
        }
        serviceStatusAdapter.setList(serviceStatusArray);
        nurseID = nurseServiceArray.get(0).get(1);
        for(int i=0;i<nurseServiceArray.size();i++){
            if(i==0){
                nurseServiceArray.get(i).put(2,"1");
            }else {
                nurseServiceArray.get(i).put(2,"0");
            }
        }
        nurseServiceAdapter.setList(nurseServiceArray);
        confirmStatusID = confirmStatusArray.get(0).get(1);
        for(int i=0;i<confirmStatusArray.size();i++){
            if(i==0){
                confirmStatusArray.get(i).put(2,"1");
            }else {
                confirmStatusArray.get(i).put(2,"0");
            }
        }
        confirmStatusAdapter.setList(confirmStatusArray);

        startDateTV.setText("请选择");
        startDate = "";
        endDateTV.setText("请选择");
        endDate = "";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.reset_btn:
                setResetTV();
                break;
            case R.id.confirm_btn:
//                String s= inquiryStatus+payStatus+confirmStatus+startDate+endDate;
                Intent intent = new Intent();
                intent.putExtra("serviceStateID",serviceStateID);
                intent.putExtra("confirmStatusID",confirmStatusID);
                intent.putExtra("nurseID",nurseID);
                intent.putExtra("startDate",startDate);
                intent.putExtra("endDate",endDate);
                setResult(RESULT_OK,intent);
                finish();
                break;
            case R.id.start_date_layout:
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    Calendar calendar = Calendar.getInstance();
                    DatePickerDialog datePicker = new DatePickerDialog(NurseHistoryScreenActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            startDate = year + "-" + ((month+1)<10?"0"+(month+1):(month+1)) + "-" + (dayOfMonth<10?"0"+dayOfMonth:dayOfMonth) ;
                            startDateTV.setText(startDate);
                        }
                    },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                    datePicker.show();
                }
                break;
            case R.id.end_date_layout:
                if(startDate == null||startDate.equals("")){
                    showToast("请选择开始时间");
                    return;
                }
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    Calendar calendar = Calendar.getInstance();
                    DatePickerDialog datePicker = new DatePickerDialog(NurseHistoryScreenActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT,new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            endDate = year + "-" + ((month+1)<10?"0"+(month+1):(month+1)) + "-" + (dayOfMonth<10?"0"+dayOfMonth:dayOfMonth) ;
                            endDateTV.setText(endDate);
                            SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd");
                            try {
                                Date date1 = dateFormat.parse(startDate);//开始时间
                                Date date2 = dateFormat.parse(endDate);//结束时间
                                if(date1.getTime()>date2.getTime()){
                                    ToastUtil.toastLongMessage("结束日期不可以小于开始日期");
                                    endDateTV.setText("请选择");
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                        }
                    },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                    datePicker.show();
                }
                break;
        }
    }

    @Override
    public void finish() {
        super.finish();
    }
}
