package com.company.linquan.app.moduleWork.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.company.linquan.app.R;
import com.company.linquan.app.base.BaseActivity;
import com.company.linquan.app.bean.FileCollectInfoBean;
import com.company.linquan.app.bean.MyInqueryInfoBean;
import com.company.linquan.app.bean.NurseInfoBean;
import com.company.linquan.app.bean.NurseToolBean;
import com.company.linquan.app.moduleAuth.AuthActivity;
import com.company.linquan.app.moduleWork.WorkInterface;
import com.company.linquan.app.moduleWork.imp.ConfirmNursePresenterImp;
import com.company.linquan.app.moduleWork.imp.CreateNursePresenterImp;
import com.company.linquan.app.nim.activity.ImgActivity;
import com.company.linquan.app.util.ExitApp;
import com.company.linquan.app.util.MyDialog;
import com.company.linquan.app.util.MyToast;
import com.company.linquan.app.view.MyTextView;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import cn.aigestudio.datepicker.cons.DPMode;
import cn.aigestudio.datepicker.views.DatePicker;

/**
 * Created by YC on 2018/6/11.
 */

public class ConfirmNurseActivity extends BaseActivity implements WorkInterface.ConfirmNurseInterface, View.OnClickListener{

    private Dialog myDialog;

    private ConfirmNursePresenterImp presenter;
    private TextView patientNameTV;
    private TextView phonenumTV;
    private TextView ageTV;
    private TextView sexTV;
    private TextView appointstarttimeTV;
    private TextView appointendtimeTV;
    private TextView addressTV;
    private TextView amountTV;
    private TextView paystateTV;
    private TextView servicestateTV;
    private TextView orderidTV;
    private TextView illnessdescrTV;
    private TextView idcardnoTV;
    private ImageView idCardFrontIV;
    private ImageView idCardAgainstIV;
    private ArrayList<String> images=new ArrayList<>();

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
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 开启自定义标题栏
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

        patientNameTV=(TextView)findViewById(R.id.visit_name);
        phonenumTV=(TextView)findViewById(R.id.visit_phone);
        ageTV=(TextView)findViewById(R.id.visit_age);
        sexTV=(TextView)findViewById(R.id.visit_sex);
        appointstarttimeTV=(TextView)findViewById(R.id.visit_start_time);
        appointendtimeTV=(TextView)findViewById(R.id.visit_end_time);
        addressTV=(TextView)findViewById(R.id.visit_address);
        amountTV=(TextView) findViewById(R.id.amount);
        paystateTV=(TextView)findViewById(R.id.paystate);
        servicestateTV=(TextView)findViewById(R.id.servicestate);
        orderidTV=(TextView)findViewById(R.id.orderid);
        illnessdescrTV=(TextView)findViewById(R.id.illnessdescr);
        idcardnoTV=(TextView)findViewById(R.id.idcardno);
        idCardFrontIV=(ImageView) findViewById(R.id.idCardFront);
        idCardAgainstIV=(ImageView)findViewById(R.id.idCardAgainst);

        checkRemarkET=findViewById(R.id.checkRemark);



        sw = getContext().getResources().getDisplayMetrics().widthPixels;
        presenter = new ConfirmNursePresenterImp(this);

        recordRecycler = (RecyclerView)findViewById(R.id.list_item_img);
//        recordRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));
        recordRecycler.setLayoutManager(new GridLayoutManager(this,3,GridLayoutManager.VERTICAL, false));
        recordListAdapter = new RecordListAdapter(getContext(),images);
        recordRecycler.setAdapter(recordListAdapter);
        recordRecycler.setItemAnimator(new DefaultItemAnimator());

        findViewById(R.id.confirm_btn).setOnClickListener(this);
        findViewById(R.id.fail_btn).setOnClickListener(this);

    }
    private void getData() {
        patientNameTV.setText(getIntent().getStringExtra("patientName"));
        phonenumTV.setText(getIntent().getStringExtra("phonenum"));
        ageTV.setText(getIntent().getStringExtra("age"));
        switch (getIntent().getStringExtra("sex")){
            case "1":
                sexTV.setText("男");
                break;
            case "2":
                sexTV.setText("女");
                break;
        }
        appointstarttimeTV.setText(getIntent().getStringExtra("appointstarttime"));
        appointendtimeTV.setText(getIntent().getStringExtra("appointendtime"));
        addressTV.setText(getIntent().getStringExtra("address"));
        amountTV.setText(getIntent().getStringExtra("amount"));
        switch (getIntent().getStringExtra("paystate")){//（0.未支付 1.已支付 2.支付成功 3.退款）
            case "0":
                paystateTV.setText("未支付");
                break;
            case "1":
                paystateTV.setText("已支付");
                break;
            case "2":
                paystateTV.setText("支付成功");
                break;
            case "3":
                paystateTV.setText("退款");
                break;
        }
        switch (getIntent().getStringExtra("servicestate")){//（0.未开始 1.已服务 2.取消 3.结束）
            case "0":
                servicestateTV.setText("未开始");
                break;
            case "1":
                servicestateTV.setText("已服务");
                break;
            case "2":
                servicestateTV.setText("取消");
                break;
            case "3":
                servicestateTV.setText("结束");
                break;
        }
        orderidTV.setText(getIntent().getStringExtra("orderid"));
        illnessdescrTV.setText(getIntent().getStringExtra("illnessdescr"));
        idcardnoTV.setText(getIntent().getStringExtra("idcardno"));
        checkRemarkET.setText(getIntent().getStringExtra("checkRemark"));

        if(!"0".equals(getIntent().getStringExtra("confirmState"))){
            findViewById(R.id.confirm_btn).setVisibility(View.GONE);
            findViewById(R.id.fail_btn).setVisibility(View.GONE);
        }


        int w = (112*sw)/720;
        Glide.with(ConfirmNurseActivity.this).load(getIntent().getStringExtra("idCardFront")).apply(new RequestOptions().error(R.drawable.img_auth_add).override(w,w).centerCrop()).into(idCardFrontIV);
        Glide.with(ConfirmNurseActivity.this).load(getIntent().getStringExtra("idCardAgainst")).apply(new RequestOptions().error(R.drawable.img_auth_add).override(w,w).centerCrop()).into(idCardAgainstIV);

        position=getIntent().getStringExtra("position");

        for(String imgs:getIntent().getStringArrayListExtra("describe")){
            images.add(imgs);
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
                Intent intent = new Intent(ConfirmNurseActivity.this,ImgActivity.class);
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
