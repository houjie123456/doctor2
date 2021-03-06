package com.company.wanbei.app.moduleCenter.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.codbking.widget.OnSureLisener;
import com.company.wanbei.app.R;
import com.company.wanbei.app.base.BaseActivity;
import com.company.wanbei.app.bean.TransactionBean;
import com.tencent.qcloud.tuikit.tuichat.fromApp.config.C;
import com.company.wanbei.app.moduleCenter.UserCenterInterface;
import com.company.wanbei.app.moduleCenter.imp.MyMoneyPresenterImp;
import com.company.wanbei.app.util.ExitApp;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.MyDialog;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.MyToast;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.ToolSharePerference;
import com.company.wanbei.app.util.widget.DatePickDialog1;
import com.company.wanbei.app.util.widget.OnChangeLisener;
import com.company.wanbei.app.util.widget.bean.DateType;
import com.tencent.qcloud.tuikit.tuichat.fromApp.view.MyTextView;
import com.tencent.qcloud.tuikit.tuichat.fromApp.view.RoundImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

//import androidx.recyclerview.widget.DefaultItemAnimator;
//import androidx.recyclerview.widget.GridLayoutManager;

/**
 * Created by YC on 2018/6/11.
 */

public class MyMoneyActivity extends BaseActivity implements UserCenterInterface.MyMoneyInterface,View.OnClickListener{

    private Dialog myDialog;
    private MyMoneyPresenterImp myMoneyPresenterImp;

    private String startTime = "",state="",profitType="",type="";

    private TextView startDateTV,profitTypeTV,stateTV,showTV,showMoneyTV;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private ArrayList<TransactionBean> array;
    private MyListAdapter adapter;
    int page = 1,sw;
    private DatePickDialog1 dialog;
    private List<String> typeList,stateList;
    private LinearLayout noListLL,profitDateLL,stateLL,profitTypeLL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExitApp.getInstance().addActivity(this);		// add Activity ????????????
//        requestWindowFeature(Window.FEATURE_NO_TITLE); // ????????????????????????
        setContentView(R.layout.activity_my_money);
        initHead();
        initView();
        InitDateDialog();
        setListener();
        getData();
    }

    private void initHead() {
        startDateTV = findViewById(R.id.time);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");// HH:mm:ss
        //??????????????????
        Date date = new Date(System.currentTimeMillis());
        startTime = simpleDateFormat.format(date);
        startDateTV.setText(simpleDateFormat.format(date));

        if(getIntent().getExtras() != null ){
            type = getIntent().getExtras().getString("type");
        }

        RelativeLayout head = (RelativeLayout) findViewById(R.id.head_layout);
        MyTextView title = (MyTextView) head.findViewById(R.id.head_top_title);
        title.setText("????????????");
        ImageView leftIV = (ImageView)head.findViewById(R.id.head_top_image);

        leftIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView(){
        showTV = findViewById(R.id.textView01);
        showMoneyTV = findViewById(R.id.show_money);
        stateTV = findViewById(R.id.status);
        if(type.equals("profit")){
            state = "";
            stateTV.setText("??????");
            showTV.setText("?????????(???)");
        }
        if(type.equals("settled")){
            state = "3";
            stateTV.setText("??????");
            showTV.setText("?????????(???)");
        }
        if(type.equals("balance")){
            state = "1";
            stateTV.setText("??????");
            showTV.setText("?????????(???)");
        }
        sw = getResources().getDisplayMetrics().widthPixels;
        typeList = new ArrayList<>();
        if(ToolSharePerference.getStringData(this, C.fileconfig, C.IDTYPE).equals("1")){//??????
//            typeList.add("??????");
            typeList.add("????????????");
        }else {
            typeList.add("??????");
            typeList.add("????????????");
            typeList.add("????????????");
            typeList.add("????????????");
        }
        stateList = new ArrayList<>();
        stateList.add("??????");
        stateList.add("??????");
        stateList.add("??????");
        stateList.add("??????");

        myMoneyPresenterImp=new MyMoneyPresenterImp(this);


        profitTypeTV = findViewById(R.id.type);

        stateLL = findViewById(R.id.layout03);
        profitDateLL = findViewById(R.id.layout01);
        profitTypeLL = findViewById(R.id.layout02);
        stateLL.setOnClickListener(this);
        profitDateLL.setOnClickListener(this);
        profitTypeLL.setOnClickListener(this);
        noListLL = findViewById(R.id.no_layout1);

        array = new ArrayList<>();
        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.my_money_refresh);
        refreshLayout.setColorSchemeColors(ContextCompat.getColor(this,R.color.base_red_color));
        recyclerView = (RecyclerView)findViewById(R.id.my_money_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL, false));
        adapter = new MyListAdapter(getContext(),array);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }
    private void getData() {
        myMoneyPresenterImp.getList(startTime,state,profitType,page+"");
    }

    private void setListener(){
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getData();
            }
        });
        adapter.setOnItemClickListener(new OnItemClick() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent();
                intent.putExtra("patHead",array.get(position).getVisitHeadUrl());
                intent.putExtra("patName",array.get(position).getVisitName());
                intent.putExtra("getAmountBig",array.get(position).getAmount());
                intent.putExtra("orderID",array.get(position).getOrderId());
                intent.putExtra("serveType",array.get(position).getProfitType());
                intent.putExtra("transactionTime",array.get(position).getProfitTime());
                intent.putExtra("transactionAmount",array.get(position).getTradeAmount());
                intent.putExtra("getAmount",array.get(position).getAmount());
                intent.putExtra("orderStatus",array.get(position).getState());
                intent.setClass(MyMoneyActivity.this, TransactionDescActivity.class);
                startActivity(intent);
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            case R.id.head_top_right_text2:
//                if (TextUtils.isEmpty(startTime)){
//                    showToast("????????????????????????");
//                    return;
//                }
//                if (TextUtils.isEmpty(profitType)){
//                    showToast("????????????????????????");
//                    return;
//                }
//                getData();
//                break;
            case R.id.layout01:
                dialog.show();
                break;
            case R.id.layout02:
                //???????????????
                OptionsPickerView pvOptions = new OptionsPickerBuilder(MyMoneyActivity.this, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                        //?????????????????????????????????????????????
                        switch (typeList.get(options1)){
                            case "??????":
                                profitType = "";
                                break;
                            case "????????????":
                                profitType = "1";
                                break;
                            case "????????????":
                                profitType = "3";
                                break;
                            case "????????????":
                                profitType = "2";
                                break;
                            default:
                                profitType = "21";
                                break;
                        }
                        profitTypeTV.setText(typeList.get(options1));
                        page = 1;
                        getData();
                    }
                }).build();
                pvOptions.setPicker(typeList);
                pvOptions.show();
                break;
            case R.id.layout03:
                //???????????????
                OptionsPickerView pvOptions2 = new OptionsPickerBuilder(MyMoneyActivity.this, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                        //?????????????????????????????????????????????
                        switch (stateList.get(options1)){
                            case "??????":
                                state = "";
                                showTV.setText("?????????(???)");
                                break;
                            case "??????":
                                state = "1";
                                showTV.setText("?????????(???)");
                                break;
                            case "??????":
                                state = "2";
                                showTV.setText("?????????(???)");
                                break;
                            case "??????":
                                state = "3";
                                showTV.setText("?????????(???)");
                                break;
                            default:
//                                state = "21";
                                break;
                        }
                        stateTV.setText(stateList.get(options1));
                        page = 1;
                        getData();
                    }
                }).build();
                pvOptions2.setPicker(stateList);
                pvOptions2.show();
                break;

        }
    }
    /**
     * ?????????????????????
     */
    private void InitDateDialog(){
        dialog = new DatePickDialog1(this);
        //????????????????????????
        dialog.setYearLimt(5);
        //????????????
        dialog.setTitle("????????????");
        //????????????
        dialog.setType(DateType.TYPE_YM);
        //?????????????????????????????????????????????
        dialog.setMessageFormat("yyyy-MM");
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
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
                String datestr=sdf.format(date);
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                startTime = datestr;
                startDateTV.setText(startTime);

                page = 1;
                getData();
            }
        });
//        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        if (requestCode == HOS && resultCode == RESULT_OK){
//            hosID = data.getExtras().getString("hosID");
//            toDoctorId = data.getExtras().getString("doctorId");
//            hosNameTV.setText(data.getExtras().getString("name"));
//        }
//
//        if (requestCode == PAT && resultCode == RESULT_OK){
//            visitID = data.getStringExtra("visitId");
//            patID = data.getStringExtra("patientId");
//            patNameTV.setText(data.getStringExtra("name"));
//        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void reloadList(ArrayList<TransactionBean> list,String amount) {
        showMoneyTV.setText(amount);
        if (page == 1){
            if(list.size()<=0){
                noListLL.setVisibility(View.VISIBLE);
            }else {
                noListLL.setVisibility(View.GONE);
            }
            refreshLayout.setRefreshing(false);
            array = list;
            adapter.setList(array);
        }

        if (page > 1){
            for (TransactionBean bean:
                    list) {
                array.add(bean);
            }
            adapter.setList(array);
        }
    }


    /**
     * recycler ?????????
     */
    private class MyListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        private Context mContext;
        private ArrayList<TransactionBean> mList;
        private OnItemClick onItemClickListener;

        public MyListAdapter(Context context,ArrayList<TransactionBean> list ){
            this.mContext = context;
            this.mList = list;
        }


        public void setList(ArrayList<TransactionBean> list){
            this.mList = list;
            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext)
                    .inflate(R.layout.list_item_my_money,parent,false);
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

        private void initView(MyViewHolder handler, TransactionBean bean){
            if (bean == null) return;
            handler.timeTV.setText(bean.getProfitTime());
            int w2 = (134*sw)/720;
            int h2 = (134*sw)/720;
            Glide.with(getContext()).load(bean.getVisitHeadUrl()).apply(new RequestOptions().override(w2,h2).centerCrop()).into(handler.headIV);
            handler.orderIdTV.setText("????????????"+bean.getOrderId());
            handler.patNameTV.setText("???????????????"+bean.getVisitName());
            handler.orderStatusTV.setText("???????????????"+bean.getState());
            handler.orderTypeTV.setText(bean.getProfitType());
            handler.amountTV.setText("??"+bean.getAmount());

        }
    }

    public interface OnItemClick{
        public void onItemClick(View view, int position);
    }

    /**
     * ?????????
     */
    private static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView timeTV,orderIdTV,patNameTV,orderStatusTV,orderTypeTV,amountTV;
        public RoundImageView headIV;
        private OnItemClick onItemClick;

        public MyViewHolder(View view, OnItemClick onItemClick){
            super(view);
            this.onItemClick = onItemClick;
            view.setOnClickListener(this);
            timeTV =  view.findViewById(R.id.order_time);
            headIV =  view.findViewById(R.id.img_head);
            orderIdTV =  view.findViewById(R.id.order_id);
            patNameTV =  view.findViewById(R.id.pat_name);
            orderStatusTV =  view.findViewById(R.id.order_status);
            orderTypeTV =  view.findViewById(R.id.order_type);
            amountTV =  view.findViewById(R.id.order_amount);
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
