package com.company.wanbei.app.moduleWork.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.company.wanbei.app.base.BaseActivity;
import com.company.wanbei.app.moduleWork.WorkInterface;
import com.company.wanbei.app.moduleWork.imp.ConfirmNursePresenterImp;
import com.company.wanbei.app.tim.ImgActivity;
import com.company.wanbei.app.util.ExitApp;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.MyDialog;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.MyToast;
import com.tencent.qcloud.tuikit.tuichat.fromApp.view.MyTextView;
import com.company.wanbei.app.R;
import com.tencent.qcloud.tuikit.tuichat.fromApp.view.RoundImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

//import androidx.recyclerview.widget.DefaultItemAnimator;
//import androidx.recyclerview.widget.GridLayoutManager;

/**
 * Created by YC on 2018/6/11.
 */

public class ConfirmNurseActivity extends BaseActivity implements WorkInterface.ConfirmNurseInterface, View.OnClickListener{

    private Dialog myDialog;

    private ConfirmNursePresenterImp presenter;
    private TextView patientNameTV;
    private TextView phoneNumTV;
    private TextView ageTV;
    private TextView sexTV;
    private TextView appointTimeTV;
    private TextView addressTV;
    private TextView amountTV;
    private TextView payStateTV;
    private TextView serviceStateTV;
    private TextView orderIdTV;
    private TextView illnessDescTV;
    private TextView idCardNoTV,confirmStateTV;
    private ImageView idCardFrontIV;
    private ImageView idCardAgainstIV;
    private ArrayList<String> images=new ArrayList<>();
    private RoundImageView visitHeadIV;

    private EditText checkRemarkET;

    private String position;

//    private String patientID;
//    private String visitID;
//    private String visitName;
//    private String visitSex;
//    private String visitAge;
//    private String visitMobile;
//    private String patientMobile;
//    private String patientHeadUrl;
//    private String visitHeadUrl;
//    private String healthMarried;
//    private String healthBirth;
//    private String healthHeated;
//    private String healthDisease;
//    private String healthMedicined;
    private RecyclerView recordRecycler;

    private RecordListAdapter recordListAdapter;

    int sw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExitApp.getInstance().addActivity(this);		// add Activity 方便退出
//        requestWindowFeature(Window.FEATURE_NO_TITLE); // 开启自定义标题栏
        setContentView(R.layout.activity_confirm_nurse);

        initHead();
        initView();
        getData();
        setListener();
    }

    private void initHead() {
        RelativeLayout head = (RelativeLayout) findViewById(R.id.head_layout);
        MyTextView title = (MyTextView)head. findViewById(R.id.head_top_title);
        title.setText("患者信息");
        ImageView rightIV = (ImageView)head.findViewById(R.id.head_top_image);
        rightIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView(){
        sw = getResources().getDisplayMetrics().widthPixels;

        visitHeadIV = findViewById(R.id.visit_head);
        patientNameTV=(TextView)findViewById(R.id.visit_name);
        phoneNumTV=(TextView)findViewById(R.id.visit_phone);
        ageTV=(TextView)findViewById(R.id.visit_age);
        sexTV=(TextView)findViewById(R.id.visit_sex);
        appointTimeTV=(TextView)findViewById(R.id.visit_start_time);
        addressTV=(TextView)findViewById(R.id.visit_address);
        amountTV=(TextView) findViewById(R.id.amount);
        payStateTV=(TextView)findViewById(R.id.paystate);
        serviceStateTV=(TextView)findViewById(R.id.servicestate);
        orderIdTV=(TextView)findViewById(R.id.orderid);
        illnessDescTV=(TextView)findViewById(R.id.illnessdescr);
        idCardNoTV=(TextView)findViewById(R.id.idcardno);
        idCardFrontIV=(ImageView) findViewById(R.id.idCardFront);
        idCardAgainstIV=(ImageView)findViewById(R.id.idCardAgainst);

        checkRemarkET=findViewById(R.id.checkRemark);

        confirmStateTV = findViewById(R.id.confirm_state);

        sw = getContext().getResources().getDisplayMetrics().widthPixels;
        presenter = new ConfirmNursePresenterImp(this);

        recordRecycler = (RecyclerView)findViewById(R.id.list_item_img);
//        recordRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));
        recordRecycler.setLayoutManager(new GridLayoutManager(this,3,RecyclerView.VERTICAL, false));
        recordListAdapter = new RecordListAdapter(getContext(),images);
        recordRecycler.setAdapter(recordListAdapter);
        recordRecycler.setItemAnimator(new DefaultItemAnimator());

        findViewById(R.id.confirm_btn).setOnClickListener(this);
        findViewById(R.id.fail_btn).setOnClickListener(this);

    }
    private void getData() {

        patientNameTV.setText(getIntent().getStringExtra("patientName"));
        phoneNumTV.setText(getIntent().getStringExtra("phonenum"));
        ageTV.setText(getIntent().getStringExtra("age")+"岁");
        switch (getIntent().getStringExtra("sex")){
            case "1":
                sexTV.setText("男");
                break;
            case "2":
                sexTV.setText("女");
                break;
        }
        appointTimeTV.setText(getIntent().getStringExtra("appointstarttime")+"-"+getIntent().getStringExtra("appointendtime").substring(10));
        addressTV.setText(getIntent().getStringExtra("address"));
        amountTV.setText(getIntent().getStringExtra("amount"));
        switch (getIntent().getStringExtra("paystate")){//（0.未支付 1.已支付 2.支付成功 3.退款）
            case "0":
                payStateTV.setText("未支付");
                break;
            case "1":
                payStateTV.setText("已支付");
                break;
            case "2":
                payStateTV.setText("支付成功");
                break;
            case "3":
                payStateTV.setText("退款");
                break;
        }
        switch (getIntent().getStringExtra("servicestate")){//（0.未开始 1.已服务 2.取消 3.结束）
            case "0":
                serviceStateTV.setText("未开始");
                break;
            case "1":
                serviceStateTV.setText("已服务");
                break;
            case "2":
                serviceStateTV.setText("取消");
                break;
            case "3":
                serviceStateTV.setText("结束");
                break;
        }
        orderIdTV.setText(getIntent().getStringExtra("orderid"));
        illnessDescTV.setText(getIntent().getStringExtra("illnessdescr"));
        idCardNoTV.setText(getIntent().getStringExtra("idcardno"));
        checkRemarkET.setText(getIntent().getStringExtra("checkRemark"));

        String s=getIntent().getStringExtra("confirmState");

        if(!"0".equals(getIntent().getStringExtra("confirmState"))){
            findViewById(R.id.confirm_btn).setVisibility(View.GONE);
            findViewById(R.id.fail_btn).setVisibility(View.GONE);
            findViewById(R.id.layout_mid15).setVisibility(View.VISIBLE);
            if("1".equals(getIntent().getStringExtra("confirmState"))){
                confirmStateTV.setText("已通过");
            }else {
                confirmStateTV.setText("不通过");
            }

        }else {
            findViewById(R.id.confirm_btn).setVisibility(View.VISIBLE);
            findViewById(R.id.fail_btn).setVisibility(View.VISIBLE);
            findViewById(R.id.layout_mid15).setVisibility(View.GONE);
        }
        if("2".equals(getIntent().getStringExtra("servicestate"))){
            findViewById(R.id.confirm_btn).setVisibility(View.GONE);
            findViewById(R.id.fail_btn).setVisibility(View.GONE);
            findViewById(R.id.layout_mid15).setVisibility(View.VISIBLE);
        }


        int w = (112*sw)/720;
        Glide.with(ConfirmNurseActivity.this).load(getIntent().getStringExtra("visitHead")).apply(new RequestOptions().override(w,w).centerCrop()).into(visitHeadIV);

        Glide.with(ConfirmNurseActivity.this).load(getIntent().getStringExtra("idCardFront")).apply(new RequestOptions().error(R.drawable.img_auth_add).override(w,w).centerCrop()).into(idCardFrontIV);
        Glide.with(ConfirmNurseActivity.this).load(getIntent().getStringExtra("idCardAgainst")).apply(new RequestOptions().error(R.drawable.img_auth_add).override(w,w).centerCrop()).into(idCardAgainstIV);

        position=getIntent().getStringExtra("position");

        String str = getIntent().getStringExtra("describe");
        if(str!=null){
            String[] strArray = str.split(",");
            for(String imgs:strArray){
                if(!imgs.equals("")){
                    images.add(imgs);
                }
            }
        }


        recordListAdapter.setList(images);//3
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.confirm_btn:

                String id=getIntent().getStringExtra("id");
                presenter.confirmNurse(getIntent().getStringExtra("id"),"1",checkRemarkET.getText().toString());

                break;
            case R.id.fail_btn:

                if (TextUtils.isEmpty(checkRemarkET.getText().toString())){
                    showToast("请输入备注");
                    return;
                }
                presenter.confirmNurse(getIntent().getStringExtra("id"),"2",checkRemarkET.getText().toString());

                break;
        }
    }


    public void reloadInfo() {

    }


    private void setListener() {

        recordListAdapter.setOnItemClickListener(new OnItemClick() {
            @Override
            public void onItemClick(View view, int position, int index) {//11
                Intent intent = new Intent(ConfirmNurseActivity.this, ImgActivity.class);
                intent.putStringArrayListExtra("images",images);
//                intent.putExtra("images",array);
                intent.putExtra("position",position);
                startActivity(intent);
//                images.clear();

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
        private ArrayList<String> mList;
        private OnItemClick onItemClickListener;

        public RecordListAdapter(Context context,ArrayList<String> list ){//1
            this.mContext = context;
            this.mList = list;
        }


        public void setList(ArrayList<String> list){//4
            this.mList = list;
            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {//6

            View view = LayoutInflater.from(mContext)
                    .inflate(R.layout.list_item_first_inquery,parent,false);
            return new RecordViewHolder(view,onItemClickListener);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {//8
            if(holder instanceof RecordViewHolder){
                initView((RecordViewHolder) holder, mList.get(position));
            }
        }

        @Override
        public int getItemCount() {//5
            return mList.size();
        }

        private void setOnItemClickListener(OnItemClick listener){//2
            onItemClickListener = listener;
        }

        private void initView(RecordViewHolder handler, String imgs){//9
            if (imgs == null||imgs.equals("")) return;
            int w2 = (94*sw)/720;
            int h2 = (94*sw)/720;
//            Glide.with(mContext).load(bean.getPicUrl()).override(w2,h2).centerCrop().into(handler.imageView);
            Picasso.get().load(imgs).into(handler.imageView);
        }
    }




    /**
     * 静态类
     */
    private static class RecordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView imageView;

        private OnItemClick onItemClick;

        public RecordViewHolder(View view, OnItemClick onItemClick){//7
            super(view);
            this.onItemClick = onItemClick;
            view.setOnClickListener(this);
            imageView = (ImageView)view.findViewById(R.id.img_item);


        }

        @Override
        public void onClick(View view) {//10
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
