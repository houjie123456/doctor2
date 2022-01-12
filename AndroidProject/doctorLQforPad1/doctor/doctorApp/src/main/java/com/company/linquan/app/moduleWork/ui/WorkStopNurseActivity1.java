//package com.company.linquan.app.moduleWork.ui;
//
//import android.app.Dialog;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v7.widget.DefaultItemAnimator;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.Window;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.Toast;
//
//import com.company.linquan.app.R;
//import com.company.linquan.app.base.BaseActivity;
//import com.company.linquan.app.bean.FaceDiagnoseBean;
//import com.company.linquan.app.bean.StopRecordBean;
//import com.company.linquan.app.moduleWork.WorkInterface;
//import com.company.linquan.app.moduleWork.imp.StopNursePresenterImp1;
//import com.company.linquan.app.util.ExitApp;
//import com.company.linquan.app.util.MyDialog;
//import com.company.linquan.app.util.MyToast;
//import com.company.linquan.app.view.MyTextView;
//
//import java.util.ArrayList;
//
///**
// * Created by YC on 2018/6/11.
// */
//
//public class WorkStopNurseActivity1 extends BaseActivity implements WorkInterface.StopNurseInterface, View.OnClickListener{
//    private Dialog myDialog;
//
//    private RecyclerView recyclerView, recordRecycler;
//    private ArrayList<StopRecordBean> array;
//    private MyListAdapter adapter;
//    private RecordListAdapter recordListAdapter;
//    private ArrayList<FaceDiagnoseBean> arrayList;
//    private StopNursePresenterImp1 presenter;
//    private MyTextView noRecordTxt;
//    private ImageView noRecordImg;
//    private static int CREATE = 1;
//    private String relatID = "";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        ExitApp.getInstance().addActivity(this);		// add Activity 方便退出
//        requestWindowFeature(Window.FEATURE_NO_TITLE); // 开启自定义标题栏
//        setContentView(R.layout.activity_work_stop_nurse);
//
//        initHead();
//        initLeftView();
//        initView();
//        getData();
//        setListener();
//    }
//
//
//
//    private void initHead() {
//        RelativeLayout head = (RelativeLayout) findViewById(R.id.layout_head);
//        MyTextView title = (MyTextView)head.findViewById(R.id.head_top_title);
//        title.setText("发布上门护理");
//        ImageView rightIV = (ImageView)head.findViewById(R.id.head_top_image);
//        rightIV.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
//    }
//
//    private void initLeftView() {
//        LinearLayout leftLayout = (LinearLayout)findViewById(R.id.nurse_left_layout);
//        MyTextView txt1 = (MyTextView)leftLayout.findViewById(R.id.list_item_week);
//        txt1.setText("日期");
//
//        MyTextView txt2 = (MyTextView)leftLayout.findViewById(R.id.list_item_month);
//        txt2.setVisibility(View.GONE);
//
//        LinearLayout top1 = (LinearLayout)leftLayout.findViewById(R.id.list_item_right_top_layout);
//        top1.setVisibility(View.GONE);
//
//        MyTextView txt3 = (MyTextView)leftLayout.findViewById(R.id.list_item_title_second);
//        txt3.setText("上午");
//
//        ImageView image1 = (ImageView) leftLayout.findViewById(R.id.list_item_image_second);
//        image1.setVisibility(View.GONE);
//
//        MyTextView txt4 = (MyTextView)leftLayout.findViewById(R.id.list_item_title_three);
//        txt4.setText("下午");
//
//        ImageView image2 = (ImageView) leftLayout.findViewById(R.id.list_item_image_three);
//        image2.setVisibility(View.GONE);
//
//    }
//
//    private void initView(){
//
//        presenter = new StopNursePresenterImp1(this);
//        recyclerView = (RecyclerView)findViewById(R.id.work_nurse_recycler);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));
//        arrayList = new ArrayList<>();
//        adapter = new MyListAdapter(getContext(),arrayList);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//
//
//        recordRecycler = (RecyclerView)findViewById(R.id.work_nurse_record_recycler);
//        recordRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));
//        array = new ArrayList<>();
//        recordListAdapter = new RecordListAdapter(getContext(),array);
//        recordRecycler.setAdapter(recordListAdapter);
//        recordRecycler.setItemAnimator(new DefaultItemAnimator());
//
//        findViewById(R.id.work_stop_nurse_btn).setOnClickListener(this);
//        noRecordImg = (ImageView)findViewById(R.id.imageView13);
//        noRecordTxt = findViewById(R.id.myTextView16);
//    }
//
//    private void getData() {
//        presenter.getNurseList();
//    }
//
//    private void setListener() {
//        adapter.setOnItemClickListener(new OnItemClick() {
//            @Override
//            public void onItemClick(View view, int position, int index) {
//                if (index == 1){
//                    relatID = arrayList.get(position).getFaceIDMorning();
//                }else{
//                    relatID = arrayList.get(position).getFaceIDAfternoon();
//                }
//                presenter.getStopNurseRecordList(index+"",arrayList.get(position).getDateStr());
//            }
//        });
//
//        recordListAdapter.setOnItemClickListener(new OnItemClick() {
//            @Override
//            public void onItemClick(View view, int position, int index) {
//                presenter.stopNurse(relatID,"1");
//            }
//        });
//    }
//
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()){
//            case R.id.work_stop_nurse_btn:
//                startActivityForResult(new Intent(this, CreateNurseActivity.class),CREATE);
//                break;
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == CREATE && resultCode == RESULT_OK){
//            getData();
//            if (arrayList.size()>0 && !"0".equals(arrayList.get(0).getAllNumberMorning())){
//                presenter.getStopNurseRecordList("1",arrayList.get(0).getDateStr());
//            }
//
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }
//
//    /**
//     * recycler 适配器
//     */
//    private class MyListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
//
//        private Context mContext;
//        private ArrayList<FaceDiagnoseBean> mList;
//        private OnItemClick onItemClickListener;
//
//        public MyListAdapter(Context context,ArrayList<FaceDiagnoseBean> list ){
//            this.mContext = context;
//            this.mList = list;
//        }
//
//
//        public void setList(ArrayList<FaceDiagnoseBean> list){
//            this.mList = list;
//            notifyDataSetChanged();
//        }
//
//        @Override
//        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(mContext)
//                    .inflate(R.layout.list_item_nurse,parent,false);
//            return new MyViewHolder(view,onItemClickListener);
//        }
//
//        @Override
//        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//            if(holder instanceof MyViewHolder){
//                initView((MyViewHolder) holder, mList.get(position));
//            }
//        }
//
//        @Override
//        public int getItemCount() {
//            return mList.size();
//        }
//
//        private void setOnItemClickListener(OnItemClick listener){
//            onItemClickListener = listener;
//        }
//
//        private void initView(MyViewHolder handler, FaceDiagnoseBean bean){
//            if (bean == null) return;
//            handler.weekTV.setText(bean.getWeekStr());
//            handler.monthTV.setText(bean.getDateStr().substring(5,bean.getDateStr().length()));
//            if ("0".equals(bean.getAllNumberAfternoon()) && "0".equals(bean.getAllNumberMorning())){
//                handler.rightTopLayout.setVisibility(View.GONE);
//            }else{
//                handler.rightTopLayout.setVisibility(View.VISIBLE);
//            }
//
//            if ("1".equals(bean.getMorning())){
//                handler.titleSecondTV.setVisibility(View.VISIBLE);
//                handler.titleSecondTV.setText(bean.getAllNumberMorning());
//                handler.imageSecondIV.setVisibility(View.GONE);
//            }else{
//                handler.titleSecondTV.setVisibility(View.GONE);
//                handler.imageSecondIV.setVisibility(View.VISIBLE);
//            }
//
//            if ("1".equals(bean.getAfternoon())){
//                handler.titleThreeTV.setVisibility(View.VISIBLE);
//                handler.titleThreeTV.setText(bean.getAllNumberAfternoon());
//                handler.imageThreeIV.setVisibility(View.GONE);
//            }else{
//                handler.titleThreeTV.setVisibility(View.GONE);
//                handler.imageThreeIV.setVisibility(View.VISIBLE);
//            }
//        }
//    }
//
//    public interface OnItemClick{
//        public void onItemClick(View view, int position, int index);
//    }
//
//    /**
//     * 静态类
//     */
//    private static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
//        public MyTextView weekTV;
//        public LinearLayout rightTopLayout;
//        public MyTextView monthTV;
//        public MyTextView titleSecondTV;
//        public MyTextView titleThreeTV;
//        public ImageView imageSecondIV;
//        public ImageView imageThreeIV;
//        private OnItemClick onItemClick;
//
//        public MyViewHolder(View view, OnItemClick onItemClick){
//            super(view);
//            this.onItemClick = onItemClick;
//            weekTV = (MyTextView) view.findViewById(R.id.list_item_week);
//            monthTV = (MyTextView) view.findViewById(R.id.list_item_month);
//            titleSecondTV = (MyTextView) view.findViewById(R.id.list_item_title_second);
//            titleThreeTV = (MyTextView) view.findViewById(R.id.list_item_title_three);
//            imageSecondIV = (ImageView)view.findViewById(R.id.list_item_image_second);
//            imageThreeIV = (ImageView)view.findViewById(R.id.list_item_image_three);
//            rightTopLayout = (LinearLayout) view.findViewById(R.id.list_item_right_top_layout);
//            view.findViewById(R.id.list_item_second_layout).setOnClickListener(this);
//            view.findViewById(R.id.list_item_three_layout).setOnClickListener(this);
//        }
//
//        @Override
//        public void onClick(View view) {
//            if(onItemClick != null){
//                if (view.getId() == R.id.list_item_second_layout){
//                    onItemClick.onItemClick(view,getLayoutPosition(),1);
//                }
//
//                if (view.getId() == R.id.list_item_three_layout){
//                    onItemClick.onItemClick(view,getLayoutPosition(),2);
//                }
//            }
//        }
//    }
//
//
//    /**
//     * recycler 适配器
//     */
//    private class RecordListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
//
//        private Context mContext;
//        private ArrayList<StopRecordBean> mList;
//        private OnItemClick onItemClickListener;
//
//        public RecordListAdapter(Context context,ArrayList<StopRecordBean> list ){
//            this.mContext = context;
//            this.mList = list;
//        }
//
//
//        public void setList(ArrayList<StopRecordBean> list){
//            this.mList = list;
//            notifyDataSetChanged();
//        }
//
//        @Override
//        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(mContext)
//                    .inflate(R.layout.list_item_stop_nurse,parent,false);
//            return new RecordViewHolder(view,onItemClickListener);
//        }
//
//        @Override
//        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//            if(holder instanceof RecordViewHolder){
//                initView((RecordViewHolder) holder, mList.get(position));
//            }
//        }
//
//        @Override
//        public int getItemCount() {
//            return mList.size();
//        }
//
//        private void setOnItemClickListener(OnItemClick listener){
//            onItemClickListener = listener;
//        }
//
//        private void initView(RecordViewHolder handler, StopRecordBean bean){
//            if (bean == null) return;
//            handler.moneyTV.setText("￥"+bean.getAmount());
//            handler.personNumTV.setText(bean.getFaceNumber()+"人");
//            handler.addressTV.setText(bean.getAddress());
//            handler.timeTV.setText(bean.getStartDate()+"至"+bean.getEndDate());
//
//            //出停诊状态 1.出诊 2.停诊
//            if ("1".equals(bean.getFaceType())){
//                handler.stopLayout.setClickable(true);
//                handler.remarkTV.setText("停止护理");
//                handler.faceTypeTV.setText("护理中");
//            }else{
//                handler.stopLayout.setClickable(false);
//                handler.remarkTV.setText("停止护理");
//                handler.faceTypeTV.setText("护理中");
//            }
//        }
//    }
//
//    /**
//     * 静态类
//     */
//    private static class RecordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
//        public MyTextView addressTV;
//        public LinearLayout stopLayout;
//        public MyTextView timeTV;
//        public MyTextView personNumTV;
//        public MyTextView moneyTV;
//        public MyTextView remarkTV;
//        public MyTextView faceTypeTV;
//        private OnItemClick onItemClick;
//
//        public RecordViewHolder(View view, OnItemClick onItemClick){
//            super(view);
//            this.onItemClick = onItemClick;
//            addressTV = (MyTextView) view.findViewById(R.id.list_item_address);
//            timeTV = (MyTextView) view.findViewById(R.id.list_item_time);
//            personNumTV = (MyTextView) view.findViewById(R.id.list_item_person_num);
//            moneyTV = (MyTextView) view.findViewById(R.id.list_item_money);
//            faceTypeTV=(MyTextView) view.findViewById(R.id.list_item_face_type);
//            stopLayout = (LinearLayout) view.findViewById(R.id.list_item_stop_layout);
//            remarkTV = (MyTextView) view.findViewById(R.id.list_item_stop_remark);
//            stopLayout.setOnClickListener(this);
//
//        }
//
//        @Override
//        public void onClick(View view) {
//            if(onItemClick != null){
//                onItemClick.onItemClick(view,getLayoutPosition(),0);
//            }
//        }
//    }
//
//    @Override
//    public void finishActivity() {
//    }
//
//    @Override
//    public Context getContext() {
//        return this;
//    }
//
//    @Override
//    public void showDialog() {
//        if (myDialog == null) myDialog = MyDialog.showLoadingDialog(this);
//        myDialog.show();
//    }
//    @Override
//    public void dismissDialog() {
//        if (myDialog != null) myDialog.dismiss();
//    }
//
//    @Override
//    public void showToast(String txt) {
//        MyToast.showToast(this,txt, Toast.LENGTH_SHORT);
//    }
//
//    @Override
//    public void reloadNurse(ArrayList<FaceDiagnoseBean> list) {
//        arrayList = list;
//        adapter.setList(list);
//    }
//
//    @Override
//    public void reloadStopNurse(ArrayList<StopRecordBean> list) {
//        array = list;
//        recordListAdapter.setList(list);
//        if (list.size()>0){
//            recordRecycler.setVisibility(View.VISIBLE);
//            noRecordImg.setVisibility(View.GONE);
//            noRecordTxt.setVisibility(View.GONE);
//        }else{
//            recordRecycler.setVisibility(View.GONE);
//            noRecordImg.setVisibility(View.VISIBLE);
//            noRecordTxt.setVisibility(View.VISIBLE);
//        }
//    }
//
//    @Override
//    public void getStopNurse() {
//        if (arrayList.size()>0 && !"0".equals(arrayList.get(0).getAllNumberMorning())){
//            presenter.getStopNurseRecordList("1",arrayList.get(0).getDateStr());
//        }
//    }
//}
