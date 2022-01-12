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
        ExitApp.getInstance().addActivity(this);		// add Activity 方便退出
//        requestWindowFeature(Window.FEATURE_NO_TITLE); // 开启自定义标题栏
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
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        startTime = simpleDateFormat.format(date);
        startDateTV.setText(simpleDateFormat.format(date));

        if(getIntent().getExtras() != null ){
            type = getIntent().getExtras().getString("type");
        }

        RelativeLayout head = (RelativeLayout) findViewById(R.id.head_layout);
        MyTextView title = (MyTextView) head.findViewById(R.id.head_top_title);
        title.setText("我的收入");
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
            stateTV.setText("全部");
            showTV.setText("总收益(元)");
        }
        if(type.equals("settled")){
            state = "3";
            stateTV.setText("结算");
            showTV.setText("总结算(元)");
        }
        if(type.equals("balance")){
            state = "1";
            stateTV.setText("支付");
            showTV.setText("总支付(元)");
        }
        sw = getResources().getDisplayMetrics().widthPixels;
        typeList = new ArrayList<>();
        if(ToolSharePerference.getStringData(this, C.fileconfig, C.IDTYPE).equals("1")){//护理
//            typeList.add("全部");
            typeList.add("院外护理");
        }else {
            typeList.add("全部");
            typeList.add("图文问诊");
            typeList.add("视频问诊");
            typeList.add("语音问诊");
        }
        stateList = new ArrayList<>();
        stateList.add("全部");
        stateList.add("支付");
        stateList.add("退款");
        stateList.add("结算");

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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            case R.id.head_top_right_text2:
//                if (TextUtils.isEmpty(startTime)){
//                    showToast("请先选择开始时间");
//                    return;
//                }
//                if (TextUtils.isEmpty(profitType)){
//                    showToast("请先选择报告类型");
//                    return;
//                }
//                getData();
//                break;
            case R.id.layout01:
                dialog.show();
                break;
            case R.id.layout02:
                //条件选择器
                OptionsPickerView pvOptions = new OptionsPickerBuilder(MyMoneyActivity.this, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                        //返回的分别是三个级别的选中位置
                        switch (typeList.get(options1)){
                            case "全部":
                                profitType = "";
                                break;
                            case "图文问诊":
                                profitType = "1";
                                break;
                            case "视频问诊":
                                profitType = "3";
                                break;
                            case "语音问诊":
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
                //条件选择器
                OptionsPickerView pvOptions2 = new OptionsPickerBuilder(MyMoneyActivity.this, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                        //返回的分别是三个级别的选中位置
                        switch (stateList.get(options1)){
                            case "全部":
                                state = "";
                                showTV.setText("总收益(元)");
                                break;
                            case "支付":
                                state = "1";
                                showTV.setText("总支付(元)");
                                break;
                            case "退款":
                                state = "2";
                                showTV.setText("总退款(元)");
                                break;
                            case "结算":
                                state = "3";
                                showTV.setText("总结算(元)");
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
     * 选择时间对话框
     */
    private void InitDateDialog(){
        dialog = new DatePickDialog1(this);
        //设置上下年分限制
        dialog.setYearLimt(5);
        //设置标题
        dialog.setTitle("选择时间");
        //设置类型
        dialog.setType(DateType.TYPE_YM);
        //设置消息体的显示格式，日期格式
        dialog.setMessageFormat("yyyy-MM");
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
     * recycler 适配器
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
            handler.orderIdTV.setText("订单号："+bean.getOrderId());
            handler.patNameTV.setText("患者姓名："+bean.getVisitName());
            handler.orderStatusTV.setText("订单状态："+bean.getState());
            handler.orderTypeTV.setText(bean.getProfitType());
            handler.amountTV.setText("¥"+bean.getAmount());

        }
    }

    public interface OnItemClick{
        public void onItemClick(View view, int position);
    }

    /**
     * 静态类
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
