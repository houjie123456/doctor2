package com.company.linquan.app.moduleWork.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.company.linquan.app.R;
import com.company.linquan.app.base.BaseActivity;
import com.company.linquan.app.bean.FaceDiagnoseBean;
import com.company.linquan.app.bean.FaceRecordBean;
import com.company.linquan.app.moduleWork.WorkInterface;
import com.company.linquan.app.moduleWork.imp.WorkFacePresenterImp;
import com.company.linquan.app.util.ExitApp;
import com.company.linquan.app.util.MyDialog;
import com.company.linquan.app.util.MyToast;
import com.company.linquan.app.view.MyTextView;
import com.company.linquan.app.view.RoundImageView;

import java.util.ArrayList;

/**
 * Created by YC on 2018/6/10.
 */

public class WorkFaceActivity extends BaseActivity implements WorkInterface.FaceInterface,View.OnClickListener{
    private Dialog myDialog;

    private RecyclerView recyclerView, recordRecycler;
    private ArrayList<FaceRecordBean> array;
    private MyListAdapter adapter;
    private ArrayList<FaceDiagnoseBean> arrayList;
    private RecordListAdapter recordListAdapter;
    private WorkFacePresenterImp presenter;
    int sw;

    private static final int STOP = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExitApp.getInstance().addActivity(this);		// add Activity 方便退出
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 开启自定义标题栏
        setContentView(R.layout.activity_work_face);
        initHead();
        initLeftView();
        initView();
        getData();
        setListener();
    }


    private void initHead() {
        RelativeLayout head = (RelativeLayout) findViewById(R.id.layout_head);
        MyTextView title = (MyTextView)head. findViewById(R.id.head_top_title);
        title.setText("面诊管理");
        ImageView rightIV = (ImageView)head.findViewById(R.id.head_top_image);
        rightIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initLeftView() {
        LinearLayout leftLayout = (LinearLayout)findViewById(R.id.face_left_layout);
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

        presenter = new WorkFacePresenterImp(this);

//        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.work_face_refresh);
//        refreshLayout.setColorSchemeColors(ContextCompat.getColor(WorkFaceActivity.this,R.color.base_red_color));
        recyclerView = (RecyclerView)findViewById(R.id.work_face_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(WorkFaceActivity.this,LinearLayoutManager.HORIZONTAL, false));
        arrayList = new ArrayList<>();
        adapter = new MyListAdapter(getContext(),arrayList);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        recordRecycler = (RecyclerView)findViewById(R.id.work_face_record_recycler);
        recordRecycler.setLayoutManager(new LinearLayoutManager(WorkFaceActivity.this,LinearLayoutManager.VERTICAL, false));
        array = new ArrayList<>();
        recordListAdapter = new RecordListAdapter(getContext(),array);
        recordRecycler.setAdapter(recordListAdapter);
        recordRecycler.setItemAnimator(new DefaultItemAnimator());

        findViewById(R.id.work_face_mid_layout).setOnClickListener(this);
    }

    private void getData() {
        presenter.getFaceList();
    }

    private void setListener() {
        adapter.setOnItemClickListener(new OnItemClick() {
            @Override
            public void onItemClick(View view, int position, int index) {

                if (position < 0){
                    return;
                }
                if (index == 1){
                    presenter.getFaceRecordList(arrayList.get(position).getFaceIDMorning());
                }

                if (index == 2){
                    presenter.getFaceRecordList(arrayList.get(position).getFaceIDAfternoon());
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.work_face_mid_layout:
                startActivityForResult(new Intent(WorkFaceActivity.this, WorkStopFaceActivity.class),STOP);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == STOP){
            getData();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * recycler 适配器
     */
    private class MyListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        private Context mContext;
        private ArrayList<FaceDiagnoseBean> mList;
        private OnItemClick onItemClickListener;

        public MyListAdapter(Context context,ArrayList<FaceDiagnoseBean> list ){
            this.mContext = context;
            this.mList = list;
        }


        public void setList(ArrayList<FaceDiagnoseBean> list){
            this.mList = list;
            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext)
                    .inflate(R.layout.list_item_face,parent,false);
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

        private void initView(MyViewHolder handler, FaceDiagnoseBean bean){
            if (bean == null) return;
            handler.weekTV.setText(bean.getWeekStr());
            handler.monthTV.setText(bean.getDateStr().substring(5,bean.getDateStr().length()));
            if ("0".equals(bean.getAfternoon()) && "0".equals(bean.getMorning())){
                handler.rightTopLayout.setVisibility(View.GONE);
            }else{
                handler.rightTopLayout.setVisibility(View.VISIBLE);
            }

            if ("1".equals(bean.getMorning())){
                handler.titleSecondTV.setVisibility(View.VISIBLE);
                handler.titleSecondTV.setText(bean.getPeopleMorning());
                handler.imageSecondIV.setVisibility(View.GONE);
            }else{
                handler.titleSecondTV.setVisibility(View.GONE);
                handler.imageSecondIV.setVisibility(View.VISIBLE);
            }

            if ("1".equals(bean.getAfternoon())){
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
        public void onItemClick(View view, int position,int index);
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
        private OnItemClick onItemClick;

        public MyViewHolder(View view, OnItemClick onItemClick){
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
        private ArrayList<FaceRecordBean> mList;
        private OnItemClick onItemClickListener;

        public RecordListAdapter(Context context,ArrayList<FaceRecordBean> list ){
            this.mContext = context;
            this.mList = list;
        }


        public void setList(ArrayList<FaceRecordBean> list){
            this.mList = list;
            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext)
                    .inflate(R.layout.list_item_work_face_record_old,parent,false);
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

        private void initView(RecordViewHolder handler, FaceRecordBean bean){
            if (bean == null) return;
            int w2 = (94*sw)/720;
            int h2 = (94*sw)/720;
            Glide.with(mContext).load(bean.getHeadUrl()).apply(new RequestOptions().override(w2,h2).centerCrop()).into(handler.imageView);

            if (bean.isShow()){
                handler.headLayout.setVisibility(View.VISIBLE);
            }else{
                handler.headLayout.setVisibility(View.GONE);
            }

            handler.timeTV.setText(bean.getStartDate()+"\n"+bean.getEndDate());
            handler.hospitalTV.setText(bean.getAddress());
            handler.nameTV.setText(bean.getName());
            handler.btnTV.setVisibility(View.GONE);

        }
    }

    /**
     * 静态类
     */
    private static class RecordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public RoundImageView imageView;
        public LinearLayout headLayout;
        public MyTextView timeTV;
        public MyTextView nameTV;
        public MyTextView hospitalTV;
        public MyTextView btnTV;
        private OnItemClick onItemClick;

        public RecordViewHolder(View view, OnItemClick onItemClick){
            super(view);
            this.onItemClick = onItemClick;
            view.setOnClickListener(this);
            imageView = (RoundImageView)view.findViewById(R.id.list_item_person_image);
            imageView.setDrawCircle();
            nameTV = (MyTextView) view.findViewById(R.id.list_item_name);
            hospitalTV = (MyTextView) view.findViewById(R.id.list_item_hospital);
            btnTV = (MyTextView) view.findViewById(R.id.list_item_btn);
            timeTV = (MyTextView) view.findViewById(R.id.list_item_time);
            headLayout = (LinearLayout) view.findViewById(R.id.list_item_head_layout);

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
    public void reloadFace(ArrayList<FaceDiagnoseBean> list) {
        arrayList = list;
        adapter.setList(arrayList);
    }

    @Override
    public void reloadFaceRecord(ArrayList<FaceRecordBean> list) {
        array = list;
        recordListAdapter.setList(array);
    }
}
