package com.company.wanbei.app.moduleWork.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.company.wanbei.app.R;
import com.company.wanbei.app.base.BaseActivity;
import com.company.wanbei.app.bean.NurseServeBean;
import com.company.wanbei.app.bean.NurseServiceBean;
import com.company.wanbei.app.moduleWork.WorkInterface;
import com.company.wanbei.app.moduleWork.imp.NurseRecordListPresenterImp;
import com.company.wanbei.app.util.ExitApp;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.MyDialog;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.MyToast;
import com.tencent.qcloud.tuikit.tuichat.fromApp.view.MyTextView;
import com.tencent.qcloud.tuikit.tuichat.fromApp.view.RoundImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by YC on 2018/6/13.
 */

public class NurseHistoryActivity extends BaseActivity implements WorkInterface.NurseServiceListInterface,View.OnClickListener{

    private Dialog myDialog;

    private RecyclerView recordRecycler;
    private ArrayList<NurseServiceBean> array;

    private RecordListAdapter recordListAdapter;

    private NurseRecordListPresenterImp presenter;
    private SwipeRefreshLayout refreshLayout;
    private LinearLayout noListLL;

    int sw;
    int page = 1;

    private String serviceStateID,confirmStatusID ,nurseID,stateID,inquiryID,isFirstVisitState,startDate="",endDate="",payState="",checkState="",state="";

    private static int VOICE = 1,VIDEO=2,DESC=3,SCREEN = 4;
    private final static int ITEM_VIEW_TYPE_HEADER = 1,ITEM_VIEW_TYPE_ITEM = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 开启自定义标题栏
        super.onCreate(savedInstanceState);
        ExitApp.getInstance().addActivity(this);		// add Activity 方便退出
        setContentView(R.layout.activity_work_voice_history);
        initHead();
        initView();
        getData();
        setListener();
    }

    private void initHead() {
        RelativeLayout head = (RelativeLayout) findViewById(R.id.layout_head);
        MyTextView title = (MyTextView)head. findViewById(R.id.head_top_title);
        title.setText("历史记录");
        ImageView leftIV = (ImageView)head.findViewById(R.id.head_top_image);
        leftIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        MyTextView rightTV = (MyTextView)head.findViewById(R.id.head_top_right_menu);
        rightTV.setText("筛选");
        rightTV.setTextColor(getResources().getColor(R.color.colorBtn));
        rightTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent();
                intent1.setClass(NurseHistoryActivity.this, NurseHistoryScreenActivity.class);
                intent1.putExtra("serviceStateID",serviceStateID);
                intent1.putExtra("confirmStatusID",confirmStatusID);
                intent1.putExtra("nurseID",nurseID);
                intent1.putExtra("startDate",startDate);
                intent1.putExtra("endDate",endDate);
                startActivityForResult(intent1,SCREEN);
            }
        });
    }


    private void initView(){
        sw = getContext().getResources().getDisplayMetrics().widthPixels;
//        stopAll = findViewById(R.id.stop_all);
//        stopAll.setOnClickListener(this);

        presenter = new NurseRecordListPresenterImp(this);

        noListLL = findViewById(R.id.no_layout1);

        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.work_recipe_refresh);
        refreshLayout.setColorSchemeColors(ContextCompat.getColor(this,R.color.base_red_color));

        recordRecycler = findViewById(R.id.work_voice_record_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setStackFromEnd(true);
//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recordRecycler.setLayoutManager(linearLayoutManager);
        array = new ArrayList<>();
        recordListAdapter = new RecordListAdapter(getContext(),array);
        recordRecycler.setAdapter(recordListAdapter);
//        recordListAdapter = new OrderAdapter(getContext(),array);
//        recordRecycler.setAdapter(recordListAdapter);
        recordRecycler.setItemAnimator(new DefaultItemAnimator());

//        findViewById(R.id.work_voice_mid_layout).setOnClickListener(this);
//        findViewById(R.id.history_list).setOnClickListener(this);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Calendar ca = Calendar.getInstance();
        ca.setTime(new Date());
        endDate = sdf.format(ca.getTime());
        ca.add(Calendar.YEAR,-1);
        startDate = sdf.format(ca.getTime());
    }

    private void getData() {
        presenter.getNurseRecordList(startDate,endDate,nurseID,serviceStateID,confirmStatusID,String.valueOf(page));
    }

    private void setListener() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                getData();
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
        recordListAdapter.setOnItemClickListener(new NurseHistoryActivity.OnItemClick() {
            @Override
            public void onItemClick(View view, int position, int index) {
                if (position < 0){
                    return;
                }
                if(view.getId()==R.id.list_item_state){
//                    if(array.get(position).getCheckState().equals("0")){
//                        showToast("该服务尚未审核，请先审核后再操作");
//                        return;
//                    }
//                    if(array.get(position).getCheckState().equals("2")){
//                        showToast("该服务已审核不通过，无法修改服务状态");
//                        return;
//                    }
//                    //服务状态 0.未开始 1.已出发 2.取消 3.结束（必填）
//                    Intent intent = new Intent();
//                    intent.setClass(NurseHistoryActivity.this, UpdateServiceStateActivity.class);
//
//                    intent.putExtra("id",array.get(position).getId());
//                    intent.putExtra("serviceState",array.get(position).getServicestate());
//
//                    startActivityForResult(intent,UPDATE);
                }
                if(view.getId()==R.id.list_item_contact){
//                    if(array.get(position).getServicestate().equals("2")){
//                        showToast("该服务已取消，无法联系患者");
//                        return;
//                    }
//                    if(array.get(position).getCheckState().equals("2")){
//                        showToast("该服务审核未通过，无法联系患者");
//                        return;
//                    }
//                    SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//格式
//                    try {
////                        Date date=format.parse(array.get(position).getAppointstarttime());//第一个日期（字符串）
//                        Date date1=format.parse(array.get(position).getAppointendtime());//第二个日期（字符串）
//                        String datestr=format.format(new Date());
//                        Date datecurrent=format.parse(datestr);//当前日期（字符串）
//                        if (datecurrent.getTime()>date1.getTime()){//比较
//                            showToast("服务时间已结束，无法联系患者");
//                        }else {
//                            if(array.get(position).getAccountID()!=null&&array.get(position).getAccountID()!=""){
//                                VisitInfomation.getInstance().setBespeakId(array.get(position).getId());
////                                ToolSharePerference.putStringData(getContext(),"doctor","bespeakID",array.get(position).getId());
//                                UserInfoBean.getInstance().setWyyID(array.get(position).getAccountID());
//                                VisitInfomation.getInstance().setWyyID(array.get(position).getAccountID());
//                                VisitInfomation.getInstance().setBespeakId(array.get(position).getId());
//                                INQUERYIDS=new ArrayList<>();
//                                VISIDS=new ArrayList<>();
//                                VISNAMES=new ArrayList<>();
//                                NimUIKit.startP2PSession(NurseHistoryActivity.this, array.get(position).getAccountID());
//                            }else {
//                                showToast("联系人不存在");
//                            }
//                        }
//
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
                }
                if(view.getId()==R.id.nurse_record_info){
//                    if(array.get(position).getServicestate().equals("2")){
//                        showToast("该服务已取消，无法审核");
//                        return;
//                    }
//                    if(array.get(position).getServicestate().equals("3")){
//                        showToast("该服务已结束，无法审核");
//                        return;
//                    }
                    Intent intent = new Intent();
                    intent.setClass(NurseHistoryActivity.this, ConfirmNurseActivity.class);

                    intent.putExtra("visitHead",array.get(position).getPatientHeadUrl());
                    intent.putExtra("id",array.get(position).getId());
                    intent.putExtra("patientName",array.get(position).getPatientName());
                    intent.putExtra("phonenum",array.get(position).getPhonenum());
                    intent.putExtra("age",array.get(position).getAge());
                    intent.putExtra("appointstarttime",array.get(position).getAppointstarttime());
                    intent.putExtra("appointendtime",array.get(position).getAppointendtime());
                    intent.putExtra("address",array.get(position).getAddress());
                    intent.putExtra("amount",array.get(position).getAmount());
                    intent.putExtra("paystate",array.get(position).getPaystate());
                    intent.putExtra("servicestate",array.get(position).getServicestate());
                    intent.putExtra("illnessdescr",array.get(position).getIllnessdescr());
                    intent.putExtra("sex",array.get(position).getSex());
                    intent.putExtra("birthdate",array.get(position).getBirthdate());
                    intent.putExtra("idcardno",array.get(position).getIdcardno());
                    intent.putExtra("idCardFront",array.get(position).getIdCardFront());
                    intent.putExtra("idCardAgainst",array.get(position).getIdCardAgainst());
                    intent.putExtra("orderid",array.get(position).getOrderid());
                    intent.putExtra("describe",array.get(position).getDescribe());
                    intent.putExtra("checkRemark",array.get(position).getCheckRemark());
                    intent.putExtra("confirmState",array.get(position).getCheckState());

                    intent.putExtra("position",position+"");
                    startActivity(intent);
                }
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){



        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SCREEN ){
            page = 1;
            if(data==null)return;
            if(data.getStringExtra("startDate")==null){
                showToast("未选择开始时间");
                return;
            }
            if(data.getStringExtra("endDate")==null){
                showToast("未选择结束时间");
                return;
            }
            startDate = data.getStringExtra("startDate");
            endDate = data.getStringExtra("endDate");
            nurseID = data.getStringExtra("nurseID");
            serviceStateID = data.getStringExtra("serviceStateID");
            confirmStatusID = data.getStringExtra("confirmStatusID");
            getData();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void reloadNurseList(ArrayList<NurseServeBean> list) {

    }

    @Override
    public void reloadNurseRecordList(ArrayList<NurseServiceBean> list) {
        if (page == 1){
            refreshLayout.setRefreshing(false);
            array = list;
            recordListAdapter.setList(array);
        }

        if (page > 1){
            for (NurseServiceBean bean:
                    list) {
                array.add(bean);
            }
            recordListAdapter.setList(array);
        }
        if(array.size()<=0){
            noListLL.setVisibility(View.VISIBLE);
        }else {
            noListLL.setVisibility(View.GONE);
        }
    }


    public interface OnItemClick{
        public void onItemClick(View view, int position, int index);
    }


    /**
     * recycler 适配器
     */
    private class RecordListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        private Context mContext;
        private ArrayList<NurseServiceBean> mList;
        private OnItemClick onItemClickListener;

        public RecordListAdapter(Context context,ArrayList<NurseServiceBean> list ){
            this.mContext = context;
            this.mList = list;
        }


        public void setList(ArrayList<NurseServiceBean> list){
            this.mList = list;
            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext)
                    .inflate(R.layout.list_item_work_nurse_record,parent,false);
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

        private void initView(RecordViewHolder handler, NurseServiceBean bean){
            if (bean == null) return;
            int w2 = (94*sw)/720;
            int h2 = (94*sw)/720;
            Glide.with(mContext).load(bean.getPatientHeadUrl()).apply(new RequestOptions().override(w2,h2).centerCrop()).into(handler.imageView);

            handler.timeTV.setText(bean.getAppointstarttime()+"-"+bean.getAppointendtime().substring(10,bean.getAppointendtime().length()));

            handler.nameTV.setText(bean.getPatientName());

            handler.sexTV.setText(bean.getSex());
            handler.ageTV.setText(bean.getAge()+"岁");
            //（0.未开始 1.已服务 2.取消 3.结束）
            switch (bean.getServicestate()){
                case "0":
                    handler.stateTV.setText("未开始");
                    handler.stateTV.setTextColor(getResources().getColor(R.color.color_blue_3a9efb));
                    break;
                case "1":
                    handler.stateTV.setText("已出发");
                    handler.stateTV.setTextColor(getResources().getColor(R.color.color_green));
                    break;
                case "2":
                    handler.stateTV.setText("已取消");
                    handler.stateTV.setTextColor(getResources().getColor(R.color.color_red_ccfa3c55));
                    break;
                case "3":
                    handler.stateTV.setText("结束");
                    handler.stateTV.setTextColor(getResources().getColor(R.color.color_grey_888888));
                    break;
            }
//            handler.confirmTV.setText("审核");
            handler.confirmTV.setVisibility(View.GONE);
            handler.updateStateTV.setVisibility(View.GONE);
            handler.contactTV.setVisibility(View.GONE);
            /**
             * TODO switch case判断审核状态
             */
            switch (bean.getCheckState()){
                case "0":
                    handler.confirmStateTV.setText("未审核");
                    break;
                case "1":
                    handler.confirmStateTV.setText("已通过");
                    break;
                case "2":
                    handler.confirmStateTV.setText("不通过");
                    break;
            }
//            handler.btnTV.setVisibility(View.GONE);
            handler.nurseTypeTV.setText(bean.getTypeName());
            handler.nurseServiceTV.setText(bean.getServiceName());
        }
    }

    /**
     * 静态类
     */
    private static class RecordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public RoundImageView imageView;
        public MyTextView nameTV;
        public MyTextView sexTV;
        public MyTextView ageTV;
        public MyTextView stateTV;
        public MyTextView confirmTV;
        public MyTextView confirmStateTV;
        public MyTextView contactTV;
        public MyTextView nurseTypeTV,nurseServiceTV,updateStateTV,timeTV;
        public LinearLayout nurseDescLL;

        private OnItemClick onItemClick;

        public RecordViewHolder(View view, OnItemClick onItemClick){
            super(view);
            this.onItemClick = onItemClick;
            view.setOnClickListener(this);
            imageView = (RoundImageView)view.findViewById(R.id.list_item_person_image);
            imageView.setDrawCircle();
            nameTV = (MyTextView) view.findViewById(R.id.list_item_name);
            sexTV = (MyTextView) view.findViewById(R.id.list_item_sex);
            ageTV = (MyTextView) view.findViewById(R.id.list_item_age);
            stateTV = (MyTextView) view.findViewById(R.id.list_item_state);

            timeTV = (MyTextView) view.findViewById(R.id.list_item_time);

            confirmStateTV=view.findViewById(R.id.list_item_confirm_state);

            nurseTypeTV = (MyTextView) view.findViewById(R.id.list_item_nurse_type_name);
            nurseServiceTV = (MyTextView) view.findViewById(R.id.list_item_nurse_service_name);
            nurseDescLL = (LinearLayout) view.findViewById(R.id.nurse_record_info);
            updateStateTV = (MyTextView) view.findViewById(R.id.list_item_update_state);

            confirmTV=view.findViewById(R.id.list_item_confirm);
            contactTV=view.findViewById(R.id.list_item_contact);


//            confirmTV.setOnClickListener(this);
//            contactTV.setOnClickListener(this);
//            updateStateTV.setOnClickListener(this);
            nurseDescLL.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(onItemClick != null){
                onItemClick.onItemClick(view,getLayoutPosition(),3);
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


}