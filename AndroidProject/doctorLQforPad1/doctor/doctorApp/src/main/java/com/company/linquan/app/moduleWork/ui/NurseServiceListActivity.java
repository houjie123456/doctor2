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

    //??????string??????
    private ArrayList<NurseServeBean> nurseListInfo;
    private ArrayList<String> nurseList;
    //??????string??????
//    private ArrayList<String> stateList;
    final String[] stateList = {"??????","?????????","?????????","?????????","?????????"};//???????????? 0.????????? 1.????????? 2.?????? 3.??????

    /** popup????????????ListView */
    private ListView mTypeLv;

    /** popup?????? */
    private PopupWindow typeSelectPopup;

    /** ?????????????????? */
    private List<String> testData;

    /** ??????????????? */
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
        ExitApp.getInstance().addActivity(this);		// add Activity ????????????
        requestWindowFeature(Window.FEATURE_NO_TITLE); // ????????????????????????
        setContentView(R.layout.activity_nurse_service_list);
        initHead();
        initView();
        getData();
        setListener();
    }


    private void initHead() {
        RelativeLayout head = (RelativeLayout) findViewById(R.id.layout_head);
        MyTextView title = (MyTextView)head.findViewById(R.id.head_top_title);
        title.setText("????????????");
        ImageView leftIV = (ImageView)head.findViewById(R.id.head_top_image);
        leftIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        TextView rightTV = (MyTextView)head.findViewById(R.id.head_top_right_menu);
        rightTV.setText("????????????");
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
            //?????????????????????????????????????????????????????????????????????
            boolean isSlidingToLast = false;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                // ???????????????
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //?????????????????????????????????ItemPosition
                    int lastVisiblePositions = manager.findLastVisibleItemPosition();
                    int totalItemCount = manager.getItemCount();
                    // ???????????????????????????
                    if (lastVisiblePositions >= (totalItemCount -2) && isSlidingToLast) {
                        //???????????????????????????
                        page = page + 1;
                        presenter.getNurseRecordList(startTime,endTime,nurseID,stateID, String.valueOf(page));
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //dx?????????????????????????????????dy??????????????????????????????
                if(dy > 0){
                    //??????0???????????????????????????
                    isSlidingToLast = true;
                }else{
                    //????????????0 ???????????????????????????
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
                    //???????????? 0.????????? 1.????????? 2.?????? 3.??????????????????
                    Intent intent = new Intent();
                    intent.setClass(NurseServiceListActivity.this, UpdateServiceStateActivity.class);

                    intent.putExtra("id",array.get(position).getId());
                    intent.putExtra("serviceState",array.get(position).getServicestate());

                    startActivityForResult(intent,UPDATE);
                }
                if(view.getId()== R.id.list_item_contact){
                    SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//??????
                    try {
//                        Date date=format.parse(array.get(position).getAppointstarttime());//??????????????????????????????
                        Date date1=format.parse(array.get(position).getAppointendtime());//??????????????????????????????
                        String datestr=format.format(new Date());
                        Date datecurrent=format.parse(datestr);//???????????????????????????
                        if (datecurrent.getTime()>date1.getTime()){//??????
                            showToast("??????????????????????????????????????????");
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
                                showToast("??????????????????");
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
                // ?????????????????????popup??????
                initSelectPopup();
                // ??????isShowing()??????popup???????????????????????????
                if (typeSelectPopup != null && !typeSelectPopup.isShowing()) {
                    typeSelectPopup.showAsDropDown(nurseTV, 0, 10);
                }
                break;
            case R.id.myTextView_state:
                index = 4;
                // ?????????????????????popup??????
                initSelectPopup();
                // ??????isShowing()??????popup???????????????????????????
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
                    showToast("????????????????????????");
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
     * ?????????????????????
     */
    private void showDateDialog(){
        dialog = new DatePickDialog(this);
        //????????????????????????
        dialog.setYearLimt(5);
        //????????????
        dialog.setTitle("????????????");
        //????????????
        dialog.setType(DateType.TYPE_ALL);
        //?????????????????????????????????????????????
        dialog.setMessageFormat("yyyy-MM-dd HH:mm");
        //??????????????????
        dialog.setOnChangeLisener(new OnChangeLisener() {
            @Override
            public void onChanged(Date date) {

            }
        });
        //??????????????????????????????
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
                        showToast("????????????????????????");
                        return;
                    }
//                    if (startTime.equals(endTime)){
//                        showToast("??????????????????????????????");
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
//            array.get(position).setConfirmstate("?????????");
//            getData();
//        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void reloadNurseList(ArrayList<NurseServeBean> list) {

        nurseListInfo=list;
        nurseList=new ArrayList<>();
        nurseList.add("??????");
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
     * ?????????popup??????
     */
    private void initSelectPopup() {
        mTypeLv = new ListView(this);
        // ???????????????
        if(index==3){
            testDataAdapter = new ArrayAdapter<String>(this, R.layout.popup_text_item, nurseList);
        }
        if(index==4){
            testDataAdapter = new ArrayAdapter<String>(this, R.layout.popup_text_item, stateList);
        }
        mTypeLv.setAdapter(testDataAdapter);

        // ??????ListView??????????????????
        mTypeLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(index==3){
                    // ???????????????item??????
                    String value = nurseList.get(position);
                    // ?????????????????????????????????TextView???
                    nurseTV.setText(value);
                    if(position>0){
                        nurseID=nurseListInfo.get(position-1).getId();
                    }else {
                        nurseID="";
                    }
                }
                if(index==4){//???????????? 0.????????? 1.????????? 2.?????? 3.??????
                    // ???????????????item??????
                    String value = stateList[position];
                    // ?????????????????????????????????TextView???
                    stateTV.setText(value);
                    if(position>0){
                        stateID= String.valueOf(position-1);
                    }else {
                        stateID="";
                    }
                }

                // ??????????????????popup??????
                typeSelectPopup.dismiss();
            }
        });
        if(index==3){
            typeSelectPopup = new PopupWindow(mTypeLv, nurseTV.getWidth(), ActionBar.LayoutParams.WRAP_CONTENT, true);
        }
        if(index==4){//???????????? 0.????????? 1.????????? 2.?????? 3.??????
            typeSelectPopup = new PopupWindow(mTypeLv, stateTV.getWidth(), ActionBar.LayoutParams.WRAP_CONTENT, true);
        }

        // ??????popup?????????????????????
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.bg_corner);
        typeSelectPopup.setBackgroundDrawable(drawable);
        typeSelectPopup.setFocusable(true);
        typeSelectPopup.setOutsideTouchable(true);
        typeSelectPopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                // ??????popup??????
                typeSelectPopup.dismiss();
            }
        });
    }

    public interface OnItemClick{
        public void onItemClick(View view, int position, int index);
    }


    /**
     * recycler ?????????
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

            handler.txtTimeTV.setText("??????????????????:"+"\n"+"??????????????????:");
            handler.timeTV.setText(bean.getAppointstarttime()+"\n"+bean.getAppointendtime());

            handler.nameTV.setText(bean.getPatientName());

            switch (bean.getSex()){
                case "1":
                    handler.sexTV.setText("???");
                    break;
                case "2":
                    handler.sexTV.setText("???");
                    break;
            }
            handler.ageTV.setText(bean.getAge()+"???");
            //???0.????????? 1.????????? 2.?????? 3.?????????
            switch (bean.getServicestate()){
                case "0":
                    handler.stateTV.setText("?????????");
                    break;
                case "1":
                    handler.stateTV.setText("?????????");
                    break;
                case "2":
                    handler.stateTV.setText("?????????");
                    break;
                case "3":
                    handler.stateTV.setText("?????????");
                    break;
            }
            confirmState=bean.getCheckState();
//            handler.confirmTV.setText("??????");
            /**
             * TODO switch case??????????????????
             */
            switch (bean.getCheckState()){
                case "0":
                    handler.confirmTV.setText("??????");
                    break;
                case "1":
                    handler.confirmTV.setText("????????????");
                    break;
                case "2":
                    handler.confirmTV.setText("???????????????");
                    break;
                case "3":
                    handler.confirmTV.setText("?????????????????????");
                    break;
                case "4":
                    handler.confirmTV.setText("????????????????????????");
                    break;
            }
//            handler.btnTV.setVisibility(View.GONE);

        }
    }

    /**
     * ?????????
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
