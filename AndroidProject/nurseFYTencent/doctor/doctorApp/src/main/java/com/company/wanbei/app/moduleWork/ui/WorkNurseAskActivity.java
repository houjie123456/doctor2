package com.company.wanbei.app.moduleWork.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.company.wanbei.app.R;
import com.company.wanbei.app.base.BaseActivity;
import com.company.wanbei.app.bean.NurseAskBean;
import com.company.wanbei.app.bean.NurseAskScheduleBean;
import com.company.wanbei.app.bean.PictureEvaluateBean;
import com.company.wanbei.app.moduleWork.WorkInterface;
import com.company.wanbei.app.moduleWork.imp.WorkNurseAskPresenterImp;
import com.company.wanbei.app.util.DoubleDatePickerDialog;
import com.company.wanbei.app.util.ExitApp;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.MyDialog;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.MyToast;
import com.tencent.qcloud.tuikit.tuichat.fromApp.view.MyTextView;
import com.tencent.qcloud.tuikit.tuichat.fromApp.view.RoundImageView;
import com.hb.dialog.myDialog.MyAlertDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by YC on 2018/6/13.
 */

public class WorkNurseAskActivity extends BaseActivity implements WorkInterface.NurseAskInterface,View.OnClickListener{
    private Dialog myDialog;

    private RecyclerView  recyclerView,recordRecycler,evaluateRecycler;
    private SwipeRefreshLayout refreshLayout,evaluateRefresh;
    private ArrayList<NurseAskScheduleBean> array = new ArrayList<>();
    private ArrayList<NurseAskScheduleBean> arraySelected = new ArrayList<>();

    private ArrayList<PictureEvaluateBean> arrayEvaluate = new ArrayList<>();
    private MyListAdapter adapter;
    private ArrayList<NurseAskBean> arrayList;
    private RecordListAdapter recordListAdapter;
    private EvaluateListAdapter evaluateListAdapter;
    private MyTextView switchOffTV,infoListTV,infoEvaluateTV,caseTV;
    int sw;

    int page = 1,pageEvaluate=1;
    private final int CREATE=1;
    private MyTextView editTV,selectAllTV,cancelTV,selectTimeTV,applyBtn,applyRemarkTV;
    private ImageView leftIV,editIV,deleteIV;
    private LinearLayout bottomLL,editLL,deleteLL,selectTimeLL;
    private TextView editBottomTV,deleteBottomTV;
    private boolean recycleClickable,recordClickable=true;
    private ConstraintLayout midLL,applyLL;

    private String startDate="",endDate="";

    private WorkNurseAskPresenterImp presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExitApp.getInstance().addActivity(this);		// add Activity ????????????
//        requestWindowFeature(Window.FEATURE_NO_TITLE); // ????????????????????????
        setContentView(R.layout.activity_work_nurse_ask);
        initHead();
        initLeftView();
        initView();
        setListener();
        getData();
    }

    private void initHead() {
        RelativeLayout head = (RelativeLayout) findViewById(R.id.layout_head);
        MyTextView title = (MyTextView)head. findViewById(R.id.head_top_title);
        title.setText("????????????");
        leftIV = (ImageView)head.findViewById(R.id.head_top_image);
        leftIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        selectAllTV = (MyTextView) head.findViewById(R.id.head_top_select_all);
        selectAllTV.setVisibility(View.GONE);
        selectAllTV.setOnClickListener(this);
        editTV = (MyTextView) head.findViewById(R.id.head_top_right_text2);
        editTV.setVisibility(View.VISIBLE);
        editTV.setOnClickListener(this);
        cancelTV = (MyTextView) head.findViewById(R.id.head_top_cancel);
        cancelTV.setVisibility(View.GONE);
        cancelTV.setOnClickListener(this);
    }
    private void initLeftView() {
        LinearLayout leftLayout = (LinearLayout)findViewById(R.id.nurse_left_layout);
        MyTextView txt1 = (MyTextView)leftLayout.findViewById(R.id.list_item_week);
        txt1.setText("??????");

        MyTextView txt2 = (MyTextView)leftLayout.findViewById(R.id.list_item_month);
        txt2.setVisibility(View.GONE);

        LinearLayout top1 = (LinearLayout)leftLayout.findViewById(R.id.list_item_right_top_layout);
        top1.setVisibility(View.GONE);

        MyTextView txt3 = (MyTextView)leftLayout.findViewById(R.id.list_item_title_second);
        txt3.setText("??????"+"\n"+"??????");

        ImageView image1 = (ImageView) leftLayout.findViewById(R.id.list_item_image_second);
        image1.setVisibility(View.GONE);

//        MyTextView txt4 = (MyTextView)leftLayout.findViewById(R.id.list_item_title_three);
//        txt4.setText("??????");
//
//        ImageView image2 = (ImageView) leftLayout.findViewById(R.id.list_item_image_three);
//        image2.setVisibility(View.GONE);
    }
    private void initView(){
        presenter = new WorkNurseAskPresenterImp(this);

        recyclerView = (RecyclerView)findViewById(R.id.work_nurse_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));
        arrayList = new ArrayList<>();
        adapter = new MyListAdapter(getContext(),array);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        sw = getContext().getResources().getDisplayMetrics().widthPixels;
        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.work_nurse_ask_refresh);
        refreshLayout.setColorSchemeColors(ContextCompat.getColor(this,R.color.base_red_color));
        refreshLayout.setVisibility(View.VISIBLE);

        evaluateRefresh = (SwipeRefreshLayout)findViewById(R.id.work_nurse_evaluate_refresh);
        evaluateRefresh.setColorSchemeColors(ContextCompat.getColor(this,R.color.base_red_color));
        evaluateRefresh.setVisibility(View.GONE);

        caseTV=findViewById(R.id.work_nurse_txt);
        caseTV.setText("????????????");

        recordRecycler = (RecyclerView)findViewById(R.id.work_nurse_ask_recycler);
        recordRecycler.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL, false));
        recordListAdapter = new RecordListAdapter(getContext(),arrayList);
        recordRecycler.setAdapter(recordListAdapter);
        recordRecycler.setItemAnimator(new DefaultItemAnimator());
        recordRecycler.setVisibility(View.VISIBLE);

        evaluateRecycler = (RecyclerView)findViewById(R.id.work_nurse_evaluate_recycler);
        evaluateRecycler.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL, false));
        evaluateListAdapter = new EvaluateListAdapter(getContext(),arrayEvaluate);
        evaluateRecycler.setAdapter(evaluateListAdapter);
        evaluateRecycler.setItemAnimator(new DefaultItemAnimator());

        infoListTV=findViewById(R.id.info_list_tv);
        infoListTV.setTag(R.drawable.shape_corner_btn_group1);
        infoEvaluateTV=findViewById(R.id.info_evaluate_tv);
        infoEvaluateTV.setTag(R.drawable.shape_corner_btn_group2);
        infoListTV.setOnClickListener(this);
        infoEvaluateTV.setOnClickListener(this);

        bottomLL = findViewById(R.id.bottom_layout);
        bottomLL.setVisibility(View.GONE);

        editLL = findViewById(R.id.edit_layout);
        editIV = findViewById(R.id.edit_img);
        editBottomTV = findViewById(R.id.edit_text);
        editLL.setOnClickListener(this);

        deleteLL = findViewById(R.id.delete_layout);
        deleteIV = findViewById(R.id.delete_img);
        deleteBottomTV = findViewById(R.id.delete_text);
        deleteLL.setOnClickListener(this);

        editIV.setImageDrawable(getResources().getDrawable(R.drawable.img_edit));
        editBottomTV.setTextColor(getResources().getColor(R.color.grey_txt));
        editLL.setClickable(false);
        deleteIV.setImageDrawable(getResources().getDrawable(R.drawable.img_delete));
        deleteBottomTV.setTextColor(getResources().getColor(R.color.grey_txt));
        deleteLL.setClickable(false);

        selectTimeLL = findViewById(R.id.work_nurse_time_select);
        selectTimeLL.setOnClickListener(this);
        selectTimeTV = findViewById(R.id.work_nurse_time_txt);

        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        startDate = simpleDateFormat.format(calendar.getTime());
        endDate = simpleDateFormat.format(date);
        selectTimeTV.setText(startDate+"???"+endDate);

        midLL = findViewById(R.id.work_nurse_mid_layout);
        applyLL = findViewById(R.id.work_nurse_apply_layout);
        applyBtn = findViewById(R.id.apply_btn);
        applyRemarkTV = findViewById(R.id.myTextView103);
        applyLL.setVisibility(View.GONE);
        midLL.setVisibility(View.VISIBLE);
        midLL.setOnClickListener(this);
        applyBtn.setOnClickListener(this);
    }

    private void getData() {
        presenter.getNurseAskList();
        presenter.getNurseRecordList(startDate,endDate,page);
        presenter.getNurseAskStatus();
    }

    private void setListener() {
        recordListAdapter.setOnItemClickListener(new OnItemClick() {
            @Override
            public void onItemClick(View view, int position, int index) {
                if(!recordClickable){
                    return;
                }
//                MessageHistoryActivity.start(getContext(), arrayList.get(position).getWyyId(), SessionTypeEnum.P2P); // ??????????????????
            }
        });
        adapter.setOnItemClickListener(new OnItemClick() {
            @Override
            public void onItemClick(View view, int position, int index) {
                if(!recycleClickable){
                    return;
                }
                if(array.get(position).getId().equals("")||array.get(position).getId()==null){
                    return;
                }
                if(array.get(position).isSelected()){
                    array.get(position).setSelected(false);
                    arraySelected.remove(array.get(position));
                }else {
                    array.get(position).setSelected(true);
                    arraySelected.add(array.get(position));
                }
                if(arraySelected.size() > 1){
                    editIV.setImageDrawable(getResources().getDrawable(R.drawable.img_edit));
                    editBottomTV.setTextColor(getResources().getColor(R.color.grey_txt));
                    editLL.setClickable(false);
                    deleteIV.setImageDrawable(getResources().getDrawable(R.drawable.img_delete_bold));
                    deleteBottomTV.setTextColor(getResources().getColor(R.color.color_black));
                    deleteLL.setClickable(true);
                }else if(arraySelected.size() == 1){
                    editIV.setImageDrawable(getResources().getDrawable(R.drawable.img_edit_bold));
                    editBottomTV.setTextColor(getResources().getColor(R.color.color_black));
                    editLL.setClickable(true);
                    deleteIV.setImageDrawable(getResources().getDrawable(R.drawable.img_delete_bold));
                    deleteBottomTV.setTextColor(getResources().getColor(R.color.color_black));
                    deleteLL.setClickable(true);
                }else {
                    editIV.setImageDrawable(getResources().getDrawable(R.drawable.img_edit));
                    editBottomTV.setTextColor(getResources().getColor(R.color.grey_txt));
                    editLL.setClickable(false);
                    deleteIV.setImageDrawable(getResources().getDrawable(R.drawable.img_delete));
                    deleteBottomTV.setTextColor(getResources().getColor(R.color.grey_txt));
                    deleteLL.setClickable(false);
                }
                adapter.setList(array);
            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(!recordClickable){
                    refreshLayout.setRefreshing(false);
                    return;
                }
                page = 1;
                getData();
            }
        });
        evaluateRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageEvaluate=1;
                presenter.getEvaluateList("11",pageEvaluate);
            }
        });
        recordRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            //?????????????????????????????????????????????????????????????????????
            boolean isSlidingToLast = false;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if(!recordClickable){
                    return;
                }
                // ???????????????
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //?????????????????????????????????ItemPosition
                    int lastVisiblePositions = manager.findLastVisibleItemPosition();
                    int totalItemCount = manager.getItemCount();
                    // ???????????????????????????
                    if (lastVisiblePositions >= (totalItemCount -2) && isSlidingToLast) {

                        //???????????????????????????
                        page = page + 1;
                        getData();
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
        evaluateRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                        pageEvaluate = pageEvaluate + 1;
                        presenter.getEvaluateList("11",pageEvaluate);
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
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.work_nurse_time_select:
                SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date date1 = format.parse(startDate);
                    Calendar c1 = Calendar.getInstance();
                    c1.setTime(date1);
                    Date date2 = format.parse(endDate);
                    Calendar c2 = Calendar.getInstance();
                    c2.setTime(date2);
                    // ????????????false?????????????????????????????????????????????????????????????????????true??????????????????
                    new DoubleDatePickerDialog(this, 0, new DoubleDatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear,
                                              int startDayOfMonth, DatePicker endDatePicker, int endYear, int endMonthOfYear,
                                              int endDayOfMonth) {
                            startDate = String.format("%d-%d-%d", startYear, startMonthOfYear + 1, startDayOfMonth);
                            endDate = String.format("%d-%d-%d", endYear, endMonthOfYear + 1, endDayOfMonth);
                            selectTimeTV.setText(startDate+"???"+endDate);
                            page = 1;
                            presenter.getNurseRecordList(startDate,endDate,page);
                        }
                    }, c1.get(Calendar.YEAR), c1.get(Calendar.MONTH), c1.get(Calendar.DATE),c2.get(Calendar.YEAR), c2.get(Calendar.MONTH), c2.get(Calendar.DATE), true).show();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.edit_layout:
                Intent intent = new Intent();
                intent.setClass(this, CreateNurseAskActivity.class);
                intent.putExtra("selectType", "2");
                intent.putExtra("id", arraySelected.get(0).getId());
                intent.putExtra("startTime", arraySelected.get(0).getStartDate());
                intent.putExtra("endTime", arraySelected.get(0).getEndDate());
                startActivityForResult(intent,CREATE);
                break;
            case R.id.delete_layout:
//                showConfirmDialog();
                final MyAlertDialog alertDialog=new MyAlertDialog(this).builder().setTitle("??????????????????????????????");
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.setPositiveButton("??????", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String s = "";
                        for (NurseAskScheduleBean bean:
                                arraySelected) {
                            if(arraySelected.size() - 1 == arraySelected.indexOf(bean)){
                                s += bean.getId();
                            }else {
                                s += bean.getId()+",";
                            }
                        }
                        presenter.deleteNurseAsk(s);

                    }
                }).setNegativeButton("??????", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editIV.setImageDrawable(getResources().getDrawable(R.drawable.img_edit));
                        editBottomTV.setTextColor(getResources().getColor(R.color.grey_txt));
                        editLL.setClickable(false);
                        deleteIV.setImageDrawable(getResources().getDrawable(R.drawable.img_delete));
                        deleteBottomTV.setTextColor(getResources().getColor(R.color.grey_txt));
                        deleteLL.setClickable(false);
                        recycleClickable = false;
                        arraySelected = new ArrayList<>();
                        selectAllTV.setVisibility(View.GONE);
                        leftIV.setVisibility(View.VISIBLE);
                        cancelTV.setVisibility(View.GONE);
                        editTV.setVisibility(View.VISIBLE);
                        bottomLL.setVisibility(View.GONE);
                        recyclerView.setClickable(false);
                        for (NurseAskScheduleBean bean:
                                array) {
                            bean.setSelected(false);
                            bean.setCanSelected(false);
                        }
                        adapter.setList(array);
                    }
                }).show();

                break;
            case R.id.head_top_select_all:
                arraySelected = new ArrayList<>();
                for (NurseAskScheduleBean bean:
                        array) {
                    if(bean.isCanSelected()){
                        bean.setSelected(true);
                        arraySelected.add(bean);
                    }
                }
                adapter.setList(array);
                editIV.setImageDrawable(getResources().getDrawable(R.drawable.img_edit));
                editBottomTV.setTextColor(getResources().getColor(R.color.grey_txt));
                editLL.setClickable(false);
                deleteIV.setImageDrawable(getResources().getDrawable(R.drawable.img_delete_bold));
                deleteBottomTV.setTextColor(getResources().getColor(R.color.color_black));
                deleteLL.setClickable(true);
                break;
            case R.id.head_top_right_text2:
                recycleClickable = true;
                recordClickable = false;

                selectAllTV.setVisibility(View.VISIBLE);
                leftIV.setVisibility(View.GONE);
                cancelTV.setVisibility(View.VISIBLE);
                editTV.setVisibility(View.GONE);
                bottomLL.setVisibility(View.VISIBLE);
                recyclerView.setClickable(true);
                for (NurseAskScheduleBean bean:
                        array) {
                    bean.setCanSelected(false);
                    if(bean.getId()!=null&&bean.getId()!=""){
                        bean.setCanSelected(true);
                    }
                }
                adapter.setList(array);
                break;
            case R.id.head_top_cancel:
                editIV.setImageDrawable(getResources().getDrawable(R.drawable.img_edit));
                editBottomTV.setTextColor(getResources().getColor(R.color.grey_txt));
                editLL.setClickable(false);
                deleteIV.setImageDrawable(getResources().getDrawable(R.drawable.img_delete));
                deleteBottomTV.setTextColor(getResources().getColor(R.color.grey_txt));
                deleteLL.setClickable(false);
                recycleClickable = false;
                recordClickable = true;
                arraySelected = new ArrayList<>();
                selectAllTV.setVisibility(View.GONE);
                leftIV.setVisibility(View.VISIBLE);
                cancelTV.setVisibility(View.GONE);
                editTV.setVisibility(View.VISIBLE);
                bottomLL.setVisibility(View.GONE);
                recyclerView.setClickable(false);
                for (NurseAskScheduleBean bean:
                        array) {
                    bean.setSelected(false);
                    bean.setCanSelected(false);
                }
                adapter.setList(array);
                break;
            case R.id.info_list_tv:
                int r1 = R.drawable.shape_corner_btn_group1;
                int r2 = (int) infoListTV.getTag();
                if(r1==r2){
                    infoListTV.setBackgroundResource(R.drawable.shape_corner_btn_group1_click);
                    infoListTV.setTextColor(getResources().getColor(R.color.white));
                    infoEvaluateTV.setBackgroundResource(R.drawable.shape_corner_btn_group2);
                    infoEvaluateTV.setTextColor(getResources().getColor(R.color.colorBtn));

                    caseTV.setText("????????????");
                    page = 1;
                    presenter.getNurseRecordList(startDate,endDate,page);
                    refreshLayout.setVisibility(View.VISIBLE);
                    evaluateRefresh.setVisibility(View.GONE);
                    selectTimeLL.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.info_evaluate_tv:
                int r3 = R.drawable.shape_corner_btn_group2;
                int r4 = (int) infoEvaluateTV.getTag();
                if(r3==r4){
                    infoEvaluateTV.setBackgroundResource(R.drawable.shape_corner_btn_group2_click);
                    infoEvaluateTV.setTextColor(getResources().getColor(R.color.white));
                    infoListTV.setBackgroundResource(R.drawable.shape_corner_btn_group1);
                    infoListTV.setTextColor(getResources().getColor(R.color.colorBtn));

                    caseTV.setText("????????????");
                    pageEvaluate=1;
                    presenter.getEvaluateList("11",pageEvaluate);
                    refreshLayout.setVisibility(View.GONE);
                    evaluateRefresh.setVisibility(View.VISIBLE);
                    selectTimeLL.setVisibility(View.GONE);
                }
                break;
            case R.id.work_nurse_mid_layout:
                Intent intent2 = new Intent();
                intent2.setClass(this, CreateNurseAskActivity.class);
                intent2.putExtra("selectType", "1");
                startActivityForResult(intent2,CREATE);
                break;
            case R.id.apply_btn:
                presenter.applyNurseAsk();
                break;
        }
    }

    @Override
    protected void onResume() {
        editIV.setImageDrawable(getResources().getDrawable(R.drawable.img_edit));
        editBottomTV.setTextColor(getResources().getColor(R.color.grey_txt));
        editLL.setClickable(false);
        deleteIV.setImageDrawable(getResources().getDrawable(R.drawable.img_delete));
        deleteBottomTV.setTextColor(getResources().getColor(R.color.grey_txt));
        deleteLL.setClickable(false);
        recycleClickable = false;
        arraySelected = new ArrayList<>();
        selectAllTV.setVisibility(View.GONE);
        leftIV.setVisibility(View.VISIBLE);
        cancelTV.setVisibility(View.GONE);
        editTV.setVisibility(View.VISIBLE);
        bottomLL.setVisibility(View.GONE);
        recyclerView.setClickable(false);
        for (NurseAskScheduleBean bean:
                array) {
            bean.setSelected(false);
            bean.setCanSelected(false);
        }
        adapter.setList(array);
        getData();
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        getData();
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void showConfirmDialog(){
        final Dialog mDialog = new Dialog(this, R.style.custom_dialog);
        final View mDialogContentView= LayoutInflater.from(this).inflate(R.layout.dialog_show_confirm,null);
        TextView title = (TextView) mDialogContentView.findViewById(R.id.title);
        title.setText("??????????????????????????????");
        TextView btnConfirm = (TextView) mDialogContentView.findViewById(R.id.positiveButton);
        btnConfirm.setText("??????");
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();

            }
        });
        TextView btnCancel = (TextView) mDialogContentView.findViewById(R.id.negativeButton);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });
        mDialog.setContentView(mDialogContentView);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setCancelable(true);
        mDialog.getWindow().setGravity(Gravity.CENTER);
        WindowManager.LayoutParams p = mDialog.getWindow().getAttributes(); // ?????????????????????????????????
        p.width = (int) (getResources().getDisplayMetrics().widthPixels * 0.85); // ????????????????????????0.85
        mDialog.getWindow().setAttributes(p);
        mDialog.show();
        mDialog.getWindow().setWindowAnimations(R.style.anim_dialog);
    }
    /**
     * recycler ?????????
     */
    private class MyListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        private Context mContext;
        private ArrayList<NurseAskScheduleBean> mList;
        private OnItemClick onItemClickListener;

        public MyListAdapter(Context context,ArrayList<NurseAskScheduleBean> list ){
            this.mContext = context;
            this.mList = list;
        }


        public void setList(ArrayList<NurseAskScheduleBean> list){
            this.mList = list;
            notifyDataSetChanged();
        }


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext)
                    .inflate(R.layout.list_item_nurse_ask,parent,false);
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

        private void initView(MyViewHolder handler, NurseAskScheduleBean bean){
            if (bean == null) return;
            handler.weekTV.setText(bean.getWeek());
            handler.monthTV.setText(bean.getDate());
//            if ("0".equals(bean.getAfternoonSign()) && "0".equals(bean.getMorningSign())){
//                handler.rightTopLayout.setVisibility(View.GONE);
//            }else{
//                handler.rightTopLayout.setVisibility(View.VISIBLE);
//            }
            handler.rightTopLayout.setVisibility(View.GONE);
            if (bean.isCanSelected()){
                handler.rightTopLayout.setVisibility(View.VISIBLE);
                handler.rightTopLayout.setBackgroundResource(R.drawable.layer_face_grid_right_top_no_click_style);
                if (bean.isSelected()){
//                    handler.rightTopLayout.setVisibility(View.VISIBLE);
                    handler.rightTopLayout.setBackgroundResource(R.drawable.layer_face_grid_right_top_style);
                }else{
//                    handler.rightTopLayout.setVisibility(View.VISIBLE);
                    handler.rightTopLayout.setBackgroundResource(R.drawable.layer_face_grid_right_top_no_click_style);
                }
            }


            if (!"".equals(bean.getId())){
                handler.titleSecondTV.setVisibility(View.VISIBLE);
                handler.titleSecondStartTV.setVisibility(View.VISIBLE);
                handler.titleSecondEndTV.setVisibility(View.VISIBLE);
                handler.titleSecondStartTV.setText(bean.getStartDate().substring(10,bean.getStartDate().length()));
                handler.titleSecondEndTV.setText(bean.getEndDate().substring(10,bean.getEndDate().length()));
                handler.imageSecondIV.setVisibility(View.GONE);
            }else{
                handler.titleSecondStartTV.setVisibility(View.GONE);
                handler.titleSecondEndTV.setVisibility(View.GONE);
                handler.titleSecondTV.setVisibility(View.GONE);
                handler.imageSecondIV.setVisibility(View.VISIBLE);
            }

//            if ("1".equals(bean.getAfternoonSign())){
//                handler.titleThreeTV.setVisibility(View.VISIBLE);
//                handler.titleThreeTV.setText(bean.getPeopleAfternoon());
//                handler.imageThreeIV.setVisibility(View.GONE);
//            }else{
//                handler.titleThreeTV.setVisibility(View.GONE);
//                handler.imageThreeIV.setVisibility(View.VISIBLE);
//            }
        }
    }

    public interface OnItemClick{
        public void onItemClick(View view, int position, int index);
    }

    /**
     * ?????????
     */
    private static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public MyTextView weekTV;
        public LinearLayout rightTopLayout,listLayout;
        public MyTextView monthTV;
        public MyTextView titleSecondTV,titleSecondStartTV,titleSecondEndTV;
//        public MyTextView titleThreeTV;
        public ImageView imageSecondIV;
//        public ImageView imageThreeIV;
        private OnItemClick onItemClick;

        public MyViewHolder(View view, OnItemClick onItemClick){
            super(view);
            this.onItemClick = onItemClick;
            weekTV = (MyTextView) view.findViewById(R.id.list_item_week);
            monthTV = (MyTextView) view.findViewById(R.id.list_item_month);
            titleSecondTV = (MyTextView) view.findViewById(R.id.list_item_title_second);
            titleSecondStartTV = (MyTextView) view.findViewById(R.id.list_item_title_second_start);
            titleSecondEndTV = (MyTextView) view.findViewById(R.id.list_item_title_second_end);
//            titleThreeTV = (MyTextView) view.findViewById(R.id.list_item_title_three);
            imageSecondIV = (ImageView)view.findViewById(R.id.list_item_image_second);
//            imageThreeIV = (ImageView)view.findViewById(R.id.list_item_image_three);
            rightTopLayout = (LinearLayout) view.findViewById(R.id.list_item_right_top_layout);

            listLayout = view.findViewById(R.id.list_item_layout);
            listLayout.setOnClickListener(this);
//            view.findViewById(R.id.list_item_three_layout).setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(onItemClick != null && getLayoutPosition() >= 0){

                if (view.getId() == R.id.list_item_layout){
                    onItemClick.onItemClick(view,getLayoutPosition(),1);
                }

//                if (view.getId() == R.id.list_item_three_layout){
//                    onItemClick.onItemClick(view,getLayoutPosition(),2);
//                }
            }
        }
    }


    /**
     * recycler ?????????
     */
    private class RecordListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{ 
        private Context mContext;
        private ArrayList<NurseAskBean> mList;
        private OnItemClick onItemClickListener;

        public RecordListAdapter(Context context,ArrayList<NurseAskBean> list ){
            this.mContext = context;
            this.mList = list;
        }


        public void setList(ArrayList<NurseAskBean> list){
            this.mList = list;
            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext)
                    .inflate(R.layout.list_item_nurse_ask_record,parent,false);
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

        private void initView(RecordViewHolder handler, NurseAskBean bean){

            if (bean == null) return;
            int w2 = (94*sw)/720;
            int h2 = (94*sw)/720;

            Glide.with(mContext).load(bean.getPatientHeadUrl()).apply(new RequestOptions().override(w2,h2).centerCrop()).into(handler.imageView);

            handler.nameTV.setText(bean.getVisitName());
            handler.timeTV.setText(bean.getStartTime());
//            handler.contentTV.setText(bean.getStartTime());
            handler.numTV.setText("??????:"+bean.getOrderId());
            handler.moneyTV.setText("???"+bean.getAmount());
        }
    }

    /**
     * ?????????
     */
    private static class RecordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public RoundImageView imageView;
        public MyTextView nameTV;
        public MyTextView timeTV;
        public MyTextView contentTV;
        public MyTextView numTV;
        public MyTextView moneyTV;
        private OnItemClick onItemClick;

        public RecordViewHolder(View view, OnItemClick onItemClick){
            super(view);
            this.onItemClick = onItemClick;
            view.setOnClickListener(this);
            imageView = (RoundImageView)view.findViewById(R.id.list_item_person_image);
            nameTV = (MyTextView) view.findViewById(R.id.list_item_name);
            timeTV = (MyTextView) view.findViewById(R.id.list_item_time);
            contentTV = (MyTextView) view.findViewById(R.id.list_item_content);
            numTV = (MyTextView) view.findViewById(R.id.list_item_num);
            moneyTV = (MyTextView) view.findViewById(R.id.list_item_money);

        }

        @Override
        public void onClick(View view) {
            if(onItemClick != null){
                onItemClick.onItemClick(view,getLayoutPosition(),3);
            }
        }
    }


    /**
     * recycler ?????????
     */
    private class EvaluateListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        private Context mContext;
        private ArrayList<PictureEvaluateBean> mList;
        private OnItemClick onItemClickListener;

        public EvaluateListAdapter(Context context,ArrayList<PictureEvaluateBean> list ){
            this.mContext = context;
            this.mList = list;
        }


        public void setList(ArrayList<PictureEvaluateBean> list){
            this.mList = list;
            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext)
                    .inflate(R.layout.list_item_picture_evaluate,parent,false);
            return new EvaluateViewHolder(view,onItemClickListener);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if(holder instanceof EvaluateViewHolder){
                initView((EvaluateViewHolder) holder, mList.get(position));
            }
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        private void setOnItemClickListener(OnItemClick listener){
            onItemClickListener = listener;
        }

        private void initView(EvaluateViewHolder handler, PictureEvaluateBean bean){

            if (bean == null) return;
            int w2 = (94*sw)/720;
            int h2 = (94*sw)/720;
            Glide.with(mContext).load(bean.getHeadUrl()).apply(new RequestOptions().override(w2,h2).centerCrop()).into(handler.imageView);

            handler.nameTV.setText(bean.getPatientName());
            handler.timeTV.setText(bean.getCreateTime());
            handler.evaluateTV.setText(bean.getContent());
            switch (bean.getScore()){
                case "0":
                    handler.stars1IV.setImageDrawable(getResources().getDrawable(R.drawable.xing_hui));
                    handler.stars2IV.setImageDrawable(getResources().getDrawable(R.drawable.xing_hui));
                    handler.stars3IV.setImageDrawable(getResources().getDrawable(R.drawable.xing_hui));
                    handler.stars4IV.setImageDrawable(getResources().getDrawable(R.drawable.xing_hui));
                    handler.stars5IV.setImageDrawable(getResources().getDrawable(R.drawable.xing_hui));
                    break;
                case "2":
                    handler.stars1IV.setImageDrawable(getResources().getDrawable(R.drawable.xing));
                    handler.stars2IV.setImageDrawable(getResources().getDrawable(R.drawable.xing_hui));
                    handler.stars3IV.setImageDrawable(getResources().getDrawable(R.drawable.xing_hui));
                    handler.stars4IV.setImageDrawable(getResources().getDrawable(R.drawable.xing_hui));
                    handler.stars5IV.setImageDrawable(getResources().getDrawable(R.drawable.xing_hui));
                    break;
                case "4":
                    handler.stars1IV.setImageDrawable(getResources().getDrawable(R.drawable.xing));
                    handler.stars2IV.setImageDrawable(getResources().getDrawable(R.drawable.xing));
                    handler.stars3IV.setImageDrawable(getResources().getDrawable(R.drawable.xing_hui));
                    handler.stars4IV.setImageDrawable(getResources().getDrawable(R.drawable.xing_hui));
                    handler.stars5IV.setImageDrawable(getResources().getDrawable(R.drawable.xing_hui));
                    break;
                case "6":
                    handler.stars1IV.setImageDrawable(getResources().getDrawable(R.drawable.xing));
                    handler.stars2IV.setImageDrawable(getResources().getDrawable(R.drawable.xing));
                    handler.stars3IV.setImageDrawable(getResources().getDrawable(R.drawable.xing));
                    handler.stars4IV.setImageDrawable(getResources().getDrawable(R.drawable.xing_hui));
                    handler.stars5IV.setImageDrawable(getResources().getDrawable(R.drawable.xing_hui));
                    break;
                case "8":
                    handler.stars1IV.setImageDrawable(getResources().getDrawable(R.drawable.xing));
                    handler.stars2IV.setImageDrawable(getResources().getDrawable(R.drawable.xing));
                    handler.stars3IV.setImageDrawable(getResources().getDrawable(R.drawable.xing));
                    handler.stars4IV.setImageDrawable(getResources().getDrawable(R.drawable.xing));
                    handler.stars5IV.setImageDrawable(getResources().getDrawable(R.drawable.xing_hui));
                    break;
                case "10":
                    handler.stars1IV.setImageDrawable(getResources().getDrawable(R.drawable.xing));
                    handler.stars2IV.setImageDrawable(getResources().getDrawable(R.drawable.xing));
                    handler.stars3IV.setImageDrawable(getResources().getDrawable(R.drawable.xing));
                    handler.stars4IV.setImageDrawable(getResources().getDrawable(R.drawable.xing));
                    handler.stars5IV.setImageDrawable(getResources().getDrawable(R.drawable.xing));
                    break;
            }
        }
    }

    /**
     * ?????????
     */
    private static class EvaluateViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public RoundImageView imageView;
        public MyTextView nameTV;
        public MyTextView timeTV;
        public MyTextView evaluateTV;
        public ImageView stars1IV,stars2IV,stars3IV,stars4IV,stars5IV;
        private OnItemClick onItemClick;

        public EvaluateViewHolder(View view, OnItemClick onItemClick){
            super(view);
            this.onItemClick = onItemClick;
            view.setOnClickListener(this);
            imageView = (RoundImageView)view.findViewById(R.id.list_item_person_image);
            nameTV = (MyTextView) view.findViewById(R.id.list_item_name);
            timeTV = (MyTextView) view.findViewById(R.id.list_item_time);
            evaluateTV = (MyTextView) view.findViewById(R.id.list_item_evaluate);
            stars1IV = (ImageView) view.findViewById(R.id.stars1);
            stars2IV = (ImageView) view.findViewById(R.id.stars2);
            stars3IV = (ImageView) view.findViewById(R.id.stars3);
            stars4IV = (ImageView) view.findViewById(R.id.stars4);
            stars5IV = (ImageView) view.findViewById(R.id.stars5);

        }

        @Override
        public void onClick(View view) {
            if(onItemClick != null){
                onItemClick.onItemClick(view,getLayoutPosition(),4);
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


    @Override
    public void reloadNurseAsk(ArrayList<NurseAskScheduleBean> list) {
//        if (page == 1){
//            refreshLayout.setRefreshing(false);
            array = list;
            adapter.setList(array);
//        }
//
//        if (page > 1){
//            for (NurseAskScheduleBean b:
//                    list) {
//                array.add(b);
//            }
//            adapter.setList(array);
//        }
    }

    @Override
    public void reloadEvaluate(ArrayList<PictureEvaluateBean> list) {
        if (pageEvaluate == 1){
            evaluateRefresh.setRefreshing(false);
            arrayEvaluate = list;
            evaluateListAdapter.setList(arrayEvaluate);
        }

        if (pageEvaluate > 1){
            for (PictureEvaluateBean b:
                    list) {
                arrayEvaluate.add(b);
            }
            evaluateListAdapter.setList(arrayEvaluate);
        }
    }

    @Override
    public void reloadNurseList(ArrayList<NurseAskBean> list) {
        if (page == 1){
            refreshLayout.setRefreshing(false);
            arrayList = list;
            recordListAdapter.setList(arrayList);
        }

        if (page > 1){
            for (NurseAskBean b:
                    list) {
                arrayList.add(b);
            }
            recordListAdapter.setList(arrayList);
        }
    }

    @Override
    public void afterDelete() {
        editIV.setImageDrawable(getResources().getDrawable(R.drawable.img_edit));
        editBottomTV.setTextColor(getResources().getColor(R.color.grey_txt));
        editLL.setClickable(false);
        deleteIV.setImageDrawable(getResources().getDrawable(R.drawable.img_delete));
        deleteBottomTV.setTextColor(getResources().getColor(R.color.grey_txt));
        deleteLL.setClickable(false);
        recycleClickable = false;
        recordClickable = true;
        arraySelected = new ArrayList<>();
        selectAllTV.setVisibility(View.GONE);
        leftIV.setVisibility(View.VISIBLE);
        cancelTV.setVisibility(View.GONE);
        editTV.setVisibility(View.VISIBLE);
        bottomLL.setVisibility(View.GONE);
        presenter.getNurseAskList();
    }

    @Override
    public void isShowApply(NurseAskBean bean) {
        switch (bean.getConsultCheckState()){
            case "1"://?????????
                applyLL.setVisibility(View.VISIBLE);
                midLL.setVisibility(View.GONE);
                applyBtn.setText("??????");
                applyBtn.setBackground(getResources().getDrawable(R.drawable.shape_corner_state));
                applyRemarkTV.setText("?????????????????????????????????????????????????????????????????????");
                break;
            case "2"://?????????
                applyLL.setVisibility(View.VISIBLE);
                midLL.setVisibility(View.GONE);
                applyBtn.setText("?????????");
                applyBtn.setClickable(false);
                applyBtn.setBackground(getResources().getDrawable(R.drawable.shape_corner_state_green));
                applyRemarkTV.setText("?????????????????????????????????????????????????????????????????????");
                break;
            case "3"://?????????
                applyLL.setVisibility(View.GONE);
                midLL.setVisibility(View.VISIBLE);
                break;
            case "4"://?????????
                applyLL.setVisibility(View.VISIBLE);
                midLL.setVisibility(View.GONE);
                applyBtn.setText("????????????");
                applyBtn.setBackground(getResources().getDrawable(R.drawable.shape_corner_state));
                applyRemarkTV.setText("???????????????????????? \n???????????????"+bean.getConsultCheckRemark());
                break;
            default:
                break;
        }
    }

    @Override
    public void showApplyDialog() {
        final Dialog mDialog = new Dialog(this, R.style.custom_dialog);
        final View mDialogContentView= LayoutInflater.from(this).inflate(R.layout.dialog_item_error,null);
        MyTextView cameraTV = (MyTextView)mDialogContentView.findViewById(R.id.dialog_item_cancel);
        cameraTV.setVisibility(View.GONE);
        MyTextView albumTV = (MyTextView)mDialogContentView.findViewById(R.id.dialog_item_sure);
        MyTextView contentTV = (MyTextView)mDialogContentView.findViewById(R.id.dialog_item_txt);
        contentTV.setText("???????????????????????????????????????");
        albumTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
                presenter.getNurseAskStatus();
            }
        });
        mDialog.setContentView(mDialogContentView);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setCancelable(false);
        mDialog.getWindow().setGravity(Gravity.CENTER);
        WindowManager.LayoutParams p = mDialog.getWindow().getAttributes(); // ?????????????????????????????????
        p.width = (int) (getResources().getDisplayMetrics().widthPixels * 0.85); // ????????????????????????0.85
        mDialog.getWindow().setAttributes(p);
        mDialog.show();
        mDialog.getWindow().setWindowAnimations(R.style.anim_dialog);
    }


}