package com.company.wanbei.app.moduleWork.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.company.wanbei.app.base.BaseActivity;
import com.company.wanbei.app.bean.HomeCareBean;
import com.company.wanbei.app.bean.NurseListBean;
import com.company.wanbei.app.moduleWork.WorkInterface;
import com.company.wanbei.app.moduleWork.imp.StopNursePresenterImp;
import com.company.wanbei.app.util.ExitApp;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.MyDialog;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.MyToast;
import com.tencent.qcloud.tuikit.tuichat.fromApp.view.MyTextView;
import com.company.wanbei.app.R;

import java.util.ArrayList;

public class WorkStopNurseActivity extends BaseActivity implements WorkInterface.StopNurseInterface,View.OnClickListener{
    private Dialog myDialog;

    private RecyclerView recyclerView, recordRecycler;
    private ArrayList<NurseListBean> array;
    private MyListAdapter adapter;
    private ArrayList<HomeCareBean> arrayList;
    private RecordListAdapter recordListAdapter;
    private StopNursePresenterImp presenter;
    int sw;
    private ImageView noRecordImg;
    private MyTextView noRecordText;

    private int topPosition,topIndex;

    private static int CREATE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExitApp.getInstance().addActivity(this);		// add Activity 方便退出
//        requestWindowFeature(Window.FEATURE_NO_TITLE); // 开启自定义标题栏
        setContentView(R.layout.activity_work_stop_nurse);
        initHead();
        initLeftView();
        initView();
        getData();
        setListener();
    }


    private void initHead() {
        RelativeLayout head = (RelativeLayout) findViewById(R.id.layout_head);
        MyTextView title = (MyTextView)head. findViewById(R.id.head_top_title);
        title.setText("护理管理");
        ImageView rightIV = (ImageView)head.findViewById(R.id.head_top_image);
        rightIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initLeftView() {
        LinearLayout leftLayout = (LinearLayout)findViewById(R.id.nurse_left_layout);
        MyTextView txt1 = (MyTextView)leftLayout.findViewById(R.id.list_item_week);
        txt1.setText("日期");

        MyTextView txt2 = (MyTextView)leftLayout.findViewById(R.id.list_item_month);
        txt2.setVisibility(View.GONE);

        LinearLayout top1 = (LinearLayout)leftLayout.findViewById(R.id.list_item_right_top_layout);
        top1.setVisibility(View.GONE);

        MyTextView txt3 = (MyTextView)leftLayout.findViewById(R.id.list_item_title_second);
        txt3.setText("上午");

        ImageView image1 = (ImageView) leftLayout.findViewById(R.id.list_item_image_second);
        image1.setVisibility(View.GONE);

        MyTextView txt4 = (MyTextView)leftLayout.findViewById(R.id.list_item_title_three);
        txt4.setText("下午");

        ImageView image2 = (ImageView) leftLayout.findViewById(R.id.list_item_image_three);
        image2.setVisibility(View.GONE);

    }

    private void initView(){

        sw = getContext().getResources().getDisplayMetrics().widthPixels;

        presenter = new StopNursePresenterImp(this);

//        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.work_face_refresh);
//        refreshLayout.setColorSchemeColors(ContextCompat.getColor(WorkFaceActivity.this,R.color.base_red_color));
        recyclerView = (RecyclerView)findViewById(R.id.work_nurse_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(WorkStopNurseActivity.this,LinearLayoutManager.HORIZONTAL, false));
        arrayList = new ArrayList<>();
        adapter = new MyListAdapter(getContext(),arrayList);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recordRecycler = (RecyclerView)findViewById(R.id.work_nurse_record_recycler);
        recordRecycler.setLayoutManager(new LinearLayoutManager(WorkStopNurseActivity.this,RecyclerView.VERTICAL, false));
        array = new ArrayList<>();
        recordListAdapter = new RecordListAdapter(getContext(),array);
        recordRecycler.setAdapter(recordListAdapter);
        recordRecycler.setItemAnimator(new DefaultItemAnimator());

        findViewById(R.id.work_stop_nurse_btn).setOnClickListener(this);

        noRecordImg=findViewById(R.id.imageView13);
        noRecordText=findViewById(R.id.myTextView16);
    }

    private void getData() {
        presenter.getNurseList();
    }

    private void setListener() {
        adapter.setOnItemClickListener(new WorkStopNurseActivity.OnItemClick() {
            @Override
            public void onItemClick(View view, int position, int index) {

                if (position < 0){
                    return;
                }
                topPosition=position;
                topIndex=index;
                presenter.getStopNurseRecordList(arrayList.get(position).getDateStr(),index+"");

            }
        });
        recordListAdapter.setOnItemClickListener(new WorkStopNurseActivity.OnItemClick() {
            @Override
            public void onItemClick(View view, int position, int index) {
                if (position < 0){
                    return;
                }

                int s=view.getId();
                int l=R.id.list_item_stop_layout;
                if(view.getId()==R.id.list_item_stop_layout){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("提示");
                    builder.setMessage("确定停诊该排班吗？");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            presenter.stopNurse(array.get(position).getId());

                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.show();
                }

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.work_stop_nurse_btn:
                startActivityForResult(new Intent(this, CreateNurseActivity.class),CREATE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CREATE && resultCode == RESULT_OK){
            getData();

            if (arrayList.size()>0 && !"0".equals(arrayList.get(0).getAllNumberMorning())){
                presenter.getStopNurseRecordList(arrayList.get(0).getDateStr(),"1");
            }



        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * recycler 适配器
     */
    private class MyListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        private Context mContext;
        private ArrayList<HomeCareBean> mList;
        private WorkStopNurseActivity.OnItemClick onItemClickListener;

        public MyListAdapter(Context context,ArrayList<HomeCareBean> list ){
            this.mContext = context;
            this.mList = list;
        }


        public void setList(ArrayList<HomeCareBean> list){
            this.mList = list;
            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext)
                    .inflate(R.layout.list_item_nurse,parent,false);
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

        private void setOnItemClickListener(WorkStopNurseActivity.OnItemClick listener){
            onItemClickListener = listener;
        }

        private void initView(MyViewHolder handler, HomeCareBean bean){
            if (bean == null) return;
            handler.weekTV.setText(bean.getWeekStr());
            handler.monthTV.setText(bean.getDateStr().substring(5,bean.getDateStr().length()));
            if ("0".equals(bean.getAfternoonSign()) && "0".equals(bean.getMorningSign())){
                handler.rightTopLayout.setVisibility(View.GONE);
            }else{
                handler.rightTopLayout.setVisibility(View.VISIBLE);
            }

            if ("1".equals(bean.getMorningSign())){
                handler.titleSecondTV.setVisibility(View.VISIBLE);
                handler.titleSecondTV.setText(bean.getPeopleMorning());
                handler.imageSecondIV.setVisibility(View.GONE);
            }else{
                handler.titleSecondTV.setVisibility(View.GONE);
                handler.imageSecondIV.setVisibility(View.VISIBLE);
            }

            if ("1".equals(bean.getAfternoonSign())){
                handler.titleThreeTV.setVisibility(View.VISIBLE);
                handler.titleThreeTV.setText(bean.getPeopleAfternoon());
                handler.imageThreeIV.setVisibility(View.GONE);
            }else{
                handler.titleThreeTV.setVisibility(View.GONE);
                handler.imageThreeIV.setVisibility(View.VISIBLE);
            }
        }
    }

    public interface OnItemClick{
        public void onItemClick(View view, int position, int index);
    }

    /**
     * 静态类
     */
    private static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public MyTextView weekTV;
        public LinearLayout rightTopLayout;
        public MyTextView monthTV;
        public MyTextView titleSecondTV;
        public MyTextView titleThreeTV;
        public ImageView imageSecondIV;
        public ImageView imageThreeIV;
        private WorkStopNurseActivity.OnItemClick onItemClick;

        public MyViewHolder(View view, WorkStopNurseActivity.OnItemClick onItemClick){
            super(view);
            this.onItemClick = onItemClick;
            weekTV = (MyTextView) view.findViewById(R.id.list_item_week);
            monthTV = (MyTextView) view.findViewById(R.id.list_item_month);
            titleSecondTV = (MyTextView) view.findViewById(R.id.list_item_title_second);
            titleThreeTV = (MyTextView) view.findViewById(R.id.list_item_title_three);
            imageSecondIV = (ImageView)view.findViewById(R.id.list_item_image_second);
            imageThreeIV = (ImageView)view.findViewById(R.id.list_item_image_three);
            rightTopLayout = (LinearLayout) view.findViewById(R.id.list_item_right_top_layout);
            view.findViewById(R.id.list_item_second_layout).setOnClickListener(this);
            view.findViewById(R.id.list_item_three_layout).setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(onItemClick != null && getLayoutPosition() >= 0){

                if (view.getId() == R.id.list_item_second_layout){
                    onItemClick.onItemClick(view,getLayoutPosition(),1);
                }

                if (view.getId() == R.id.list_item_three_layout){
                    onItemClick.onItemClick(view,getLayoutPosition(),2);
                }
            }
        }
    }


    /**
     * recycler 适配器
     */
    private class RecordListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        private Context mContext;
        private ArrayList<NurseListBean> mList;
        private WorkStopNurseActivity.OnItemClick onItemClickListener;

        public RecordListAdapter(Context context,ArrayList<NurseListBean> list ){
            this.mContext = context;
            this.mList = list;
        }


        public void setList(ArrayList<NurseListBean> list){
            this.mList = list;
            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext)
                    .inflate(R.layout.list_item_stop_nurse,parent,false);
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

        private void setOnItemClickListener(WorkStopNurseActivity.OnItemClick listener){
            onItemClickListener = listener;
        }

        private void initView(
                RecordViewHolder handler, NurseListBean bean){
            if (bean == null) return;
            handler.moneyTV.setText("￥"+bean.getAppointAmount());
            handler.personNumTV.setText(bean.getUseNumber()+"人");
            handler.myNameTV.setText(bean.getMyName());
            handler.typeNameTV.setText(bean.getTypeName());
            handler.serviceNameTV.setText(bean.getServiceName());
            handler.timeTV.setText(bean.getAppointStartTime()+"至"+bean.getAppointEndTime());

            //-预约状态   （ 0.未预约 1.预约中）
            if ("1".equals(bean.getAppointState())){
                handler.stopLayout.setClickable(false);
                handler.remarkTV.setText("删除");
                handler.faceTypeTV.setText("预约中");
            }else{
                handler.stopLayout.setClickable(true);
                handler.remarkTV.setText("删除");
                handler.faceTypeTV.setText("未预约");
            }

        }
    }

    /**
     * 静态类
     */
    private static class RecordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public MyTextView myNameTV;
        public MyTextView typeNameTV;
        public MyTextView serviceNameTV;
        public LinearLayout stopLayout;
        public MyTextView timeTV;
        public MyTextView personNumTV;
        public MyTextView moneyTV;
        public MyTextView remarkTV;
        public MyTextView faceTypeTV;
        private WorkStopNurseActivity.OnItemClick onItemClick;

        public RecordViewHolder(View view, WorkStopNurseActivity.OnItemClick onItemClick){
            super(view);
            this.onItemClick = onItemClick;
            view.setOnClickListener(this);
            myNameTV = (MyTextView) view.findViewById(R.id.list_item_name);
            typeNameTV = (MyTextView) view.findViewById(R.id.list_item_type);
            serviceNameTV = (MyTextView) view.findViewById(R.id.list_item_service);
            timeTV = (MyTextView) view.findViewById(R.id.list_item_time);
            personNumTV = (MyTextView) view.findViewById(R.id.list_item_person_num);
            moneyTV = (MyTextView) view.findViewById(R.id.list_item_money);
            faceTypeTV=(MyTextView) view.findViewById(R.id.list_item_face_type);
            stopLayout = (LinearLayout) view.findViewById(R.id.list_item_stop_layout);
            remarkTV = (MyTextView) view.findViewById(R.id.list_item_stop_remark);
            stopLayout.setOnClickListener(this);

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
        getData();
        presenter.getStopNurseRecordList(arrayList.get(topPosition).getDateStr(),topIndex+"");
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
    public void reloadNurse(ArrayList<HomeCareBean> list) {
        arrayList = list;
        adapter.setList(arrayList);
    }

    @Override
    public void reloadNurseList(ArrayList<NurseListBean> list) {
        array = list;
        recordListAdapter.setList(array);
        if (list.size()>0){
            recordRecycler.setVisibility(View.VISIBLE);
            noRecordImg.setVisibility(View.GONE);
            noRecordText.setVisibility(View.GONE);
        }else{
            recordRecycler.setVisibility(View.GONE);
            noRecordImg.setVisibility(View.VISIBLE);
            noRecordText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void getStopNurse() {
        if (arrayList.size()>0 && !"0".equals(arrayList.get(0).getAllNumberMorning())){
            presenter.getStopNurseRecordList(arrayList.get(0).getDateStr(),"1");
        }
    }
}
