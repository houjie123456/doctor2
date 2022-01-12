package com.company.linquan.app.moduleWork.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.company.linquan.app.R;
import com.company.linquan.app.base.BaseActivity;
import com.company.linquan.app.bean.HomeCareBean;
import com.company.linquan.app.bean.NurseServiceBean;
import com.company.linquan.app.bean.UserInfoBean;
import com.company.linquan.app.moduleWork.WorkInterface;
import com.company.linquan.app.moduleWork.imp.WorkNursePresenterImp;
import com.company.linquan.app.util.ExitApp;
import com.company.linquan.app.util.MyDialog;
import com.company.linquan.app.util.MyToast;
import com.company.linquan.app.util.ToolSharePerference;
import com.company.linquan.app.view.MyTextView;
import com.company.linquan.app.view.RoundImageView;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.visitinfo.VisitInfomation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.company.linquan.app.config.C.VISIDS;
import static com.company.linquan.app.config.C.VISNAMES;

public class WorkNurseActivity extends BaseActivity implements WorkInterface.NurseInterface,View.OnClickListener{
    private Dialog myDialog;

    private RecyclerView recyclerView, recordRecycler;
    private ArrayList<NurseServiceBean> array;
    private MyListAdapter adapter;
    private ArrayList<HomeCareBean> arrayList;
    private RecordListAdapter recordListAdapter;
    private WorkNursePresenterImp presenter;
    int sw;

    private String confirmState;

    private int topPosition, topIndex;

    private static final int STOP = 1;
    private static final int CONFIRM=2;
    private static final int UPDATE=3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExitApp.getInstance().addActivity(this);		// add Activity 方便退出
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 开启自定义标题栏
        setContentView(R.layout.activity_work_nurse);
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

        presenter = new WorkNursePresenterImp(this);

//        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.work_face_refresh);
//        refreshLayout.setColorSchemeColors(ContextCompat.getColor(WorkFaceActivity.this,R.color.base_red_color));
        recyclerView = (RecyclerView)findViewById(R.id.work_nurse_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(WorkNurseActivity.this,LinearLayoutManager.HORIZONTAL, false));
        arrayList = new ArrayList<>();
        adapter = new MyListAdapter(getContext(),arrayList);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        recordRecycler = (RecyclerView)findViewById(R.id.work_nurse_record_recycler);
        recordRecycler.setLayoutManager(new LinearLayoutManager(WorkNurseActivity.this,LinearLayoutManager.VERTICAL, false));
        array = new ArrayList<>();
        recordListAdapter = new RecordListAdapter(getContext(),array);
        recordRecycler.setAdapter(recordListAdapter);
        recordRecycler.setItemAnimator(new DefaultItemAnimator());

        findViewById(R.id.work_nurse_mid_layout).setOnClickListener(this);
    }

    private void getData() {
        presenter.getNurseList();
    }

    private void setListener() {
        adapter.setOnItemClickListener(new WorkNurseActivity.OnItemClick() {
            @Override
            public void onItemClick(View view, int position, int index) {

                if (position < 0){
                    return;
                }
                topPosition=position;
                topIndex=index;
                presenter.getNurseRecordList(arrayList.get(position).getDateStr(), String.valueOf(index));

            }
        });
        recordListAdapter.setOnItemClickListener(new OnItemClick() {
            @Override
            public void onItemClick(View view, int position, int index) {
                if (position < 0){
                    return;
                }
                if(view.getId()==R.id.list_item_state){
                    if(array.get(position).getCheckState().equals("0")){
                        showToast("该服务尚未审核，请先审核后再操作");
                        return;
                    }
                    if(array.get(position).getCheckState().equals("2")){
                        showToast("该服务已审核不通过，无法修改服务状态");
                        return;
                    }
                    //服务状态 0.未开始 1.已出发 2.取消 3.结束（必填）
                    Intent intent = new Intent();
                    intent.setClass(WorkNurseActivity.this,UpdateServiceStateActivity.class);

                    intent.putExtra("id",array.get(position).getId());
                    intent.putExtra("serviceState",array.get(position).getServicestate());

                    startActivityForResult(intent,UPDATE);
                }
                if(view.getId()==R.id.list_item_contact){
                    SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//格式
                    try {
//                        Date date=format.parse(array.get(position).getAppointstarttime());//第一个日期（字符串）
                        Date date1=format.parse(array.get(position).getAppointendtime());//第二个日期（字符串）
                        String datestr=format.format(new Date());
                        Date datecurrent=format.parse(datestr);//当前日期（字符串）
                        if (datecurrent.getTime()>date1.getTime()){//比较
                            showToast("服务时间已结束，无法联系患者");
                        }else {
                            if(array.get(position).getAccountID()!=null&&array.get(position).getAccountID()!=""){
                                VisitInfomation.getInstance().setBespeakId(array.get(position).getId());
                                UserInfoBean.getInstance().setWyyID(array.get(position).getAccountID());
                                VisitInfomation.getInstance().setWyyID(array.get(position).getAccountID());
                                ToolSharePerference.putStringData(getContext(),"doctor","bespeakID",array.get(position).getId());
                                VISIDS=new ArrayList<>();
                                VISNAMES=new ArrayList<>();
                                NimUIKit.startP2PSession(WorkNurseActivity.this, array.get(position).getAccountID());
                            }else {
                                showToast("联系人不存在");
                            }
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                if(view.getId()==R.id.list_item_confirm){
                    Intent intent = new Intent();
                    intent.setClass(WorkNurseActivity.this,ConfirmNurseActivity.class);

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
                    intent.putStringArrayListExtra("describe",array.get(position).getDescribes());
                    intent.putExtra("checkRemark",array.get(position).getCheckRemark());
                    intent.putExtra("confirmState",confirmState);

                    intent.putExtra("position",position);
                    startActivityForResult(intent,CONFIRM);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.work_nurse_mid_layout:
                startActivityForResult(new Intent(this, WorkStopNurseActivity.class),STOP);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == STOP||requestCode == CONFIRM||requestCode == UPDATE){
            getData();
            presenter.getNurseRecordList(arrayList.get(topPosition).getDateStr(), String.valueOf(topIndex));
        }
//        if (requestCode == CONFIRM && resultCode == RESULT_OK){
//            int position= Integer.parseInt(data.getStringExtra("position"));
//            array.get(position).setConfirmstate("已审核");
//            getData();
//        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * recycler 适配器
     */
    private class MyListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        private Context mContext;
        private ArrayList<HomeCareBean> mList;
        private WorkNurseActivity.OnItemClick onItemClickListener;

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

        private void setOnItemClickListener(WorkNurseActivity.OnItemClick listener){
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
                handler.titleSecondTV.setText(bean.getMorning());
                handler.imageSecondIV.setVisibility(View.GONE);
            }else{
                handler.titleSecondTV.setVisibility(View.GONE);
                handler.imageSecondIV.setVisibility(View.VISIBLE);
            }

            if ("1".equals(bean.getAfternoonSign())){
                handler.titleThreeTV.setVisibility(View.VISIBLE);
                handler.titleThreeTV.setText(bean.getAfternoon());
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
        private WorkNurseActivity.OnItemClick onItemClick;

        public MyViewHolder(View view, WorkNurseActivity.OnItemClick onItemClick){
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
        private ArrayList<NurseServiceBean> mList;
        private WorkNurseActivity.OnItemClick onItemClickListener;

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

        private void setOnItemClickListener(WorkNurseActivity.OnItemClick listener){
            onItemClickListener = listener;
        }

        private void initView(
                RecordViewHolder handler, NurseServiceBean bean){
            if (bean == null) return;
            int w2 = (94*sw)/720;
            int h2 = (94*sw)/720;
//            Glide.with(mContext).load(bean.getHeadUrl()).apply(new RequestOptions().override(w2,h2).centerCrop()).into(handler.imageView);

            handler.txtTimeTV.setText("预约开始时间:"+"\n"+"预约结束时间:");
            handler.timeTV.setText(bean.getAppointstarttime()+"\n"+bean.getAppointendtime());

            handler.nameTV.setText(bean.getPatientName());

            switch (bean.getSex()){
                case "1":
                    handler.sexTV.setText("男");
                    break;
                case "2":
                    handler.sexTV.setText("女");
                    break;
            }
            handler.ageTV.setText(bean.getAge()+"岁");
            //（0.未开始 1.已服务 2.取消 3.结束）
            switch (bean.getServicestate()){
                case "0":
                    handler.stateTV.setText("未开始");
                    break;
                case "1":
                    handler.stateTV.setText("已服务");
                    break;
                case "2":
                    handler.stateTV.setText("已取消");
                    break;
                case "3":
                    handler.stateTV.setText("已结束");
                    break;
            }
            confirmState=bean.getCheckState();
//            handler.confirmTV.setText("审核");
            /**
             * TODO switch case判断审核状态
             */
            switch (bean.getCheckState()){
                case "0":
                    handler.confirmTV.setText("审核");
                    break;
                case "1":
                    handler.confirmTV.setText("审核通过");
                    break;
                case "2":
                    handler.confirmTV.setText("审核不通过");
                    break;
                case "3":
                    handler.confirmTV.setText("护理部审核通过");
                    break;
                case "4":
                    handler.confirmTV.setText("护理部审核不通过");
                    break;
            }
//            handler.btnTV.setVisibility(View.GONE);

        }
    }

    /**
     * 静态类
     */
    private static class RecordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public RoundImageView imageView;
        public MyTextView timeTV;
        public MyTextView nameTV;
        public MyTextView sexTV;
        public MyTextView ageTV;
        public MyTextView stateTV;
        public MyTextView confirmTV;
        public MyTextView txtTimeTV;
        public MyTextView contactTV;

//        public MyTextView btnTV;
        private WorkNurseActivity.OnItemClick onItemClick;

        public RecordViewHolder(View view, WorkNurseActivity.OnItemClick onItemClick){
            super(view);
            this.onItemClick = onItemClick;
            view.setOnClickListener(this);
//            imageView = (RoundImageView)view.findViewById(R.id.list_item_person_image);
//            imageView.setDrawCircle();
            nameTV = (MyTextView) view.findViewById(R.id.list_item_name);
            sexTV = (MyTextView) view.findViewById(R.id.list_item_sex);
            ageTV = (MyTextView) view.findViewById(R.id.list_item_age);
            stateTV = (MyTextView) view.findViewById(R.id.list_item_state);

            txtTimeTV=view.findViewById(R.id.list_item_txt);

//            btnTV = (MyTextView) view.findViewById(R.id.list_item_btn);
            timeTV = (MyTextView) view.findViewById(R.id.list_item_time);
            confirmTV=view.findViewById(R.id.list_item_confirm);
            contactTV=view.findViewById(R.id.list_item_contact);

            confirmTV.setOnClickListener(this);
            contactTV.setOnClickListener(this);
            stateTV.setOnClickListener(this);
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
    public void reloadNurse(ArrayList<HomeCareBean> list) {
        arrayList = list;
        adapter.setList(arrayList);
    }

    @Override
    public void reloadNurseRecord(ArrayList<NurseServiceBean> list) {
        array = list;
        recordListAdapter.setList(array);
    }
}
