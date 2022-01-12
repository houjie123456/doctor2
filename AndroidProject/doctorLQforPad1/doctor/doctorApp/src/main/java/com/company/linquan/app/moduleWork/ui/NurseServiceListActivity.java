package com.company.linquan.app.moduleWork.ui;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnChangeLisener;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;
import com.company.linquan.app.R;
import com.company.linquan.app.base.BaseActivity;
import com.company.linquan.app.bean.NurseServeBean;
import com.company.linquan.app.bean.NurseServiceBean;
import com.company.linquan.app.bean.UserInfoBean;
import com.company.linquan.app.config.C;
import com.company.linquan.app.moduleWork.WorkInterface;
import com.company.linquan.app.moduleWork.imp.NurseRecordListPresenterImp;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import static com.company.linquan.app.config.C.VISIDS;
import static com.company.linquan.app.config.C.VISNAMES;

public class NurseServiceListActivity extends BaseActivity implements WorkInterface.NurseServiceListInterface,View.OnClickListener{
    private Dialog myDialog;

    private RecyclerView recordRecycler;
    private ArrayList<NurseServiceBean> array;
    private RecordListAdapter recordListAdapter;
    private NurseRecordListPresenterImp presenter;
    int sw,page=1;

    private String confirmState;

    private int topPosition, topIndex;
    private static final int STOP = 1;
    private static final int CONFIRM=2;
    private static final int UPDATE=3;

    //原始string数组
    private ArrayList<NurseServeBean> nurseListInfo;
    private ArrayList<String> nurseList;
    //原始string数组
//    private ArrayList<String> stateList;
    final String[] stateList = {"全部","未开始","已出发","已取消","已结束"};//服务状态 0.未开始 1.已出发 2.取消 3.结束

    /** popup窗口里的ListView */
    private ListView mTypeLv;

    /** popup窗口 */
    private PopupWindow typeSelectPopup;

    /** 模拟的假数据 */
    private List<String> testData;

    /** 数据适配器 */
    private ArrayAdapter<String> testDataAdapter;

    private SwipeRefreshLayout refreshLayout;
    private MyTextView nurseTV,stateTV,startTimeTV, endTimeTV;
    private DatePickDialog dialog;
    int index;
    private int sD,sH,sM;
    private Date start,end;
    private String nurseID="",stateID="",startTime = "",endTime ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExitApp.getInstance().addActivity(this);		// add Activity 方便退出
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 开启自定义标题栏
        setContentView(R.layout.activity_nurse_service_list);
        initHead();
        initView();
        getData();
        setListener();
    }


    private void initHead() {
        RelativeLayout head = (RelativeLayout) findViewById(R.id.layout_head);
        MyTextView title = (MyTextView)head.findViewById(R.id.head_top_title);
        title.setText("护理订单");
        ImageView leftIV = (ImageView)head.findViewById(R.id.head_top_image);
        leftIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        TextView rightTV = (MyTextView)head.findViewById(R.id.head_top_right_menu);
        rightTV.setText("确定查询");
        rightTV.setOnClickListener(this);
    }



    private void initView(){
        nurseTV = (MyTextView) findViewById(R.id.myTextView_nurse);
        stateTV = (MyTextView) findViewById(R.id.myTextView_state);
        startTimeTV = (MyTextView) findViewById(R.id.nurse_start_time);
        endTimeTV = (MyTextView) findViewById(R.id.nurse_end_time);
        nurseTV.setOnClickListener(this);
        stateTV.setOnClickListener(this);
        startTimeTV.setOnClickListener(this);
        endTimeTV.setOnClickListener(this);

        sw = getContext().getResources().getDisplayMetrics().widthPixels;

        presenter = new NurseRecordListPresenterImp(this);

        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.work_nurse_record_refresh);
        refreshLayout.setColorSchemeColors(ContextCompat.getColor(this, R.color.base_red_color));
        recordRecycler = (RecyclerView)findViewById(R.id.work_nurse_record_recycler);
        recordRecycler.setLayoutManager(new LinearLayoutManager(NurseServiceListActivity.this,LinearLayoutManager.VERTICAL, false));
        array = new ArrayList<>();
        recordListAdapter = new RecordListAdapter(getContext(),array);
        recordRecycler.setAdapter(recordListAdapter);
        recordRecycler.setItemAnimator(new DefaultItemAnimator());



    }

    private void getData() {
        presenter.getNurseServiceList(ToolSharePerference.getStringData(getContext(), C.fileconfig, C.UserID));
        presenter.getNurseRecordList("","","","", String.valueOf(page));
    }

    private void setListener() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getNurseRecordList(startTime,endTime,nurseID,stateID, String.valueOf(page));
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
                        presenter.getNurseRecordList(startTime,endTime,nurseID,stateID, String.valueOf(page));
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
        recordListAdapter.setOnItemClickListener(new OnItemClick() {
            @Override
            public void onItemClick(View view, int position, int index) {
                if (position < 0){
                    return;
                }
                if(view.getId()== R.id.list_item_state){
                    //服务状态 0.未开始 1.已出发 2.取消 3.结束（必填）
                    Intent intent = new Intent();
                    intent.setClass(NurseServiceListActivity.this, UpdateServiceStateActivity.class);

                    intent.putExtra("id",array.get(position).getId());
                    intent.putExtra("serviceState",array.get(position).getServicestate());

                    startActivityForResult(intent,UPDATE);
                }
                if(view.getId()== R.id.list_item_contact){
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
                                NimUIKit.startP2PSession(NurseServiceListActivity.this, array.get(position).getAccountID());
                            }else {
                                showToast("联系人不存在");
                            }
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                if(view.getId()== R.id.list_item_confirm){
                    Intent intent = new Intent();
                    intent.setClass(NurseServiceListActivity.this, ConfirmNurseActivity.class);

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
            case R.id.myTextView_nurse:
                index = 3;
                // 点击控件后显示popup窗口
                initSelectPopup();
                // 使用isShowing()检查popup窗口是否在显示状态
                if (typeSelectPopup != null && !typeSelectPopup.isShowing()) {
                    typeSelectPopup.showAsDropDown(nurseTV, 0, 10);
                }
                break;
            case R.id.myTextView_state:
                index = 4;
                // 点击控件后显示popup窗口
                initSelectPopup();
                // 使用isShowing()检查popup窗口是否在显示状态
                if (typeSelectPopup != null && !typeSelectPopup.isShowing()) {
                    typeSelectPopup.showAsDropDown(stateTV, 0, 10);
                }
                break;
            case R.id.nurse_start_time:
                index = 1;
                showDateDialog();
                break;
            case R.id.nurse_end_time:
                index = 2;
                if (TextUtils.isEmpty(startTime)){
                    showToast("请先选择开始时间");
                    return;
                }
                dialog.show();
                break;
            case R.id.head_top_right_menu:
                presenter.getNurseRecordList(startTime,endTime,nurseID,stateID, String.valueOf(page));
                break;
        }
    }
    public static Date parseServerTime(String serverTime, String format) {
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINESE);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        Date date = null;
        try {
            date = sdf.parse(serverTime);
        } catch (Exception e) {
            Log.e("parseServerTime", "msg: ", e);
        }
        return date;
    }
    /**
     * 选择时间对话框
     */
    private void showDateDialog(){
        dialog = new DatePickDialog(this);
        //设置上下年分限制
        dialog.setYearLimt(5);
        //设置标题
        dialog.setTitle("选择时间");
        //设置类型
        dialog.setType(DateType.TYPE_ALL);
        //设置消息体的显示格式，日期格式
        dialog.setMessageFormat("yyyy-MM-dd HH:mm");
        //设置选择回调
        dialog.setOnChangeLisener(new OnChangeLisener() {
            @Override
            public void onChanged(Date date) {

            }
        });
        //设置点击确定按钮回调
        dialog.setOnSureLisener(new OnSureLisener() {
            @Override
            public void onSure(Date date) {
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                String datestr=sdf.format(date);
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);

                if (index == 1){
                    sD = cal.get(Calendar.DAY_OF_YEAR);
                    sH = cal.get(Calendar.HOUR_OF_DAY);
                    sM = cal.get(Calendar.MINUTE);
//            startTime = selectTime+" "+(hourOfDay<10?"0"+hourOfDay:hourOfDay)
//                    +":"+(minute<10?"0"+minute:minute)+":00";
                    startTime = datestr+" "+(cal.get(Calendar.HOUR_OF_DAY)<10?"0"+cal.get(Calendar.HOUR_OF_DAY):cal.get(Calendar.HOUR_OF_DAY))
                            +":"+(cal.get(Calendar.MINUTE)<30?"00":"30")+":00";
                    start=parseServerTime(startTime,"yyyy-MM-dd HH:mm");
                    startTimeTV.setText(startTime);
                }

                if (index == 2){
                    endTimeTV.setText("");
//                  endTime = selectTime+" "+(hourOfDay<10?"0"+hourOfDay:hourOfDay)
//                          +":"+(minute<10?"0"+minute:minute)+":00";
                    endTime = datestr+" "+(cal.get(Calendar.HOUR_OF_DAY)<10?"0"+cal.get(Calendar.HOUR_OF_DAY):cal.get(Calendar.HOUR_OF_DAY))
                            +":"+(cal.get(Calendar.MINUTE)<30?"00":"30")+":00";
                    end=parseServerTime(endTime,"yyyy-MM-dd HH:mm");
                    if(start.getTime()>end.getTime()||start.getTime()==end.getTime()){
                        showToast("结束时间选择无效");
                        return;
                    }
//                    if (startTime.equals(endTime)){
//                        showToast("结束时间等于开始时间");
//                        return;
//                    }

                    endTimeTV.setText(endTime);
                }

            }
        });
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CONFIRM||requestCode == UPDATE){
            getData();
//            presenter.getNurseRecordList(arrayList.get(topPosition).getDateStr(), String.valueOf(topIndex));
        }
//        if (requestCode == CONFIRM && resultCode == RESULT_OK){
//            int position= Integer.parseInt(data.getStringExtra("position"));
//            array.get(position).setConfirmstate("已审核");
//            getData();
//        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void reloadNurseList(ArrayList<NurseServeBean> list) {

        nurseListInfo=list;
        nurseList=new ArrayList<>();
        nurseList.add("全部");
        for(NurseServeBean bean:list){
            nurseList.add(bean.getServiceName());
        }



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

    }
    /**
     * 初始化popup窗口
     */
    private void initSelectPopup() {
        mTypeLv = new ListView(this);
        // 设置适配器
        if(index==3){
            testDataAdapter = new ArrayAdapter<String>(this, R.layout.popup_text_item, nurseList);
        }
        if(index==4){
            testDataAdapter = new ArrayAdapter<String>(this, R.layout.popup_text_item, stateList);
        }
        mTypeLv.setAdapter(testDataAdapter);

        // 设置ListView点击事件监听
        mTypeLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(index==3){
                    // 在这里获取item数据
                    String value = nurseList.get(position);
                    // 把选择的数据展示对应的TextView上
                    nurseTV.setText(value);
                    if(position>0){
                        nurseID=nurseListInfo.get(position-1).getId();
                    }else {
                        nurseID="";
                    }
                }
                if(index==4){//服务状态 0.未开始 1.已出发 2.取消 3.结束
                    // 在这里获取item数据
                    String value = stateList[position];
                    // 把选择的数据展示对应的TextView上
                    stateTV.setText(value);
                    if(position>0){
                        stateID= String.valueOf(position-1);
                    }else {
                        stateID="";
                    }
                }

                // 选择完后关闭popup窗口
                typeSelectPopup.dismiss();
            }
        });
        if(index==3){
            typeSelectPopup = new PopupWindow(mTypeLv, nurseTV.getWidth(), ActionBar.LayoutParams.WRAP_CONTENT, true);
        }
        if(index==4){//服务状态 0.未开始 1.已出发 2.取消 3.结束
            typeSelectPopup = new PopupWindow(mTypeLv, stateTV.getWidth(), ActionBar.LayoutParams.WRAP_CONTENT, true);
        }

        // 取得popup窗口的背景图片
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.bg_corner);
        typeSelectPopup.setBackgroundDrawable(drawable);
        typeSelectPopup.setFocusable(true);
        typeSelectPopup.setOutsideTouchable(true);
        typeSelectPopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                // 关闭popup窗口
                typeSelectPopup.dismiss();
            }
        });
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
        private NurseServiceListActivity.OnItemClick onItemClickListener;

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

        private void setOnItemClickListener(NurseServiceListActivity.OnItemClick listener){
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
        private NurseServiceListActivity.OnItemClick onItemClick;

        public RecordViewHolder(View view, NurseServiceListActivity.OnItemClick onItemClick){
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

}
