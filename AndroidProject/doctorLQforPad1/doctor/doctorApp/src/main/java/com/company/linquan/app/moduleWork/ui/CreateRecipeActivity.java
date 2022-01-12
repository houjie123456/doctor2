package com.company.linquan.app.moduleWork.ui;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.company.linquan.app.R;
import com.company.linquan.app.base.BaseActivity;
import com.company.linquan.app.bean.DrugNameBean;
import com.company.linquan.app.bean.RecipeDrugBean;
import com.company.linquan.app.bean.RecipeDrugRequest;
import com.company.linquan.app.moduleAuth.AuthPresenter;
import com.company.linquan.app.moduleWork.WorkInterface;
import com.company.linquan.app.moduleWork.imp.CreateRecipePresenterImp;
import com.company.linquan.app.util.ExitApp;
import com.company.linquan.app.util.MyDialog;
import com.company.linquan.app.util.MyToast;
import com.company.linquan.app.util.UploadPersonPhotoUtil;
import com.company.linquan.app.view.DividerItemDecoration;
import com.company.linquan.app.view.MyTextView;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;

import kr.co.namee.permissiongen.PermissionGen;

/**
 * Created by YC on 2018/6/13.
 */

public class CreateRecipeActivity extends BaseActivity implements WorkInterface.CreateRecipeInterface, View.OnClickListener{

    private Dialog myDialog;

    private String recipeId="",patientId = "",visitId = "";
    private RecyclerView  recordRecycler;
    private ArrayList<RecipeDrugBean> array = new ArrayList<>();
    private RecordListAdapter adapter;
    private ArrayList<RecipeDrugRequest> requests = new ArrayList<>();

    private CreateRecipePresenterImp presenter;
    private final static int ADD =1,SELECT = 2;
    private EditText remarkET;
    private MyTextView diseaseLL,diseaseGroupTV;
    private LinearLayout selectGroupLL,createReceipeLL;
    private ConstraintLayout noRecordLayout;
    private MyTextView nameTV;
    private MyTextView handleReceipeTV;
    private String mPhotoPath;
    private String icdID,diseaseGroupID,icdName,checkType;
    private final static int GROUP =3,DISEASE=4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExitApp.getInstance().addActivity(this);		// add Activity 方便退出
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 开启自定义标题栏
        setContentView(R.layout.activity_create_recipe);
        initData();
        initHead();
        initView();
        getData();
    }


    private void initData() {
        if (getIntent().getExtras() != null){
            recipeId = getIntent().getStringExtra("recipeId");
        }
    }

    private void initHead() {
        RelativeLayout head = (RelativeLayout) findViewById(R.id.head_layout);
        MyTextView title = (MyTextView) head.findViewById(R.id.head_top_title);
        title.setText("开处方");
        MyTextView right = (MyTextView) head.findViewById(R.id.head_top_right_menu);
        right.setText("添加药品");
        right.setTextColor(getResources().getColor(R.color.home_click_color));
        right.setVisibility(View.VISIBLE);
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(CreateRecipeActivity.this,AddDrugsActivity.class),ADD);
            }
        });
        ImageView leftIV = (ImageView)head.findViewById(R.id.head_top_image);
        leftIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView(){
        presenter = new CreateRecipePresenterImp(this);
        findViewById(R.id.create_recipe_btn).setOnClickListener(this);
        recordRecycler = (RecyclerView)findViewById(R.id.work_recipe_record_recycler);
        recordRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));
        adapter = new RecordListAdapter(getContext(),array);
        recordRecycler.setAdapter(adapter);
        recordRecycler.setItemAnimator(new DefaultItemAnimator());
        recordRecycler.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST,
                ContextCompat.getDrawable(this,R.drawable.shape_list_line_style)));

        diseaseLL=findViewById(R.id.select_disease);
        diseaseGroupTV=findViewById(R.id.select_disease_group);
        selectGroupLL=findViewById(R.id.layout3);
        selectGroupLL.setVisibility(View.GONE);
//        remarkET = (EditText)findViewById(R.id.create_recipe_content);
        noRecordLayout = (ConstraintLayout)findViewById(R.id.work_recipe_no_record_layout);
        nameTV = (MyTextView)findViewById(R.id.create_recipe_name);
//        handleReceipeTV=(MyTextView)findViewById(R.id.handle_receipe);
        nameTV.setOnClickListener(this);
//        handleReceipeTV.setOnClickListener(this);
        diseaseLL.setOnClickListener(this);
        diseaseGroupTV.setOnClickListener(this);
    }

    private void getData() {
        if (!"".equals(recipeId)){
            presenter.getRecipe(recipeId);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.create_recipe_name:
                Intent intent = new Intent();
                intent.setClass(CreateRecipeActivity.this, SelectPatientActivity.class);
                intent.putExtra("ids",patientId);
                startActivityForResult(intent,SELECT);

                break;
            case R.id.select_disease:
                Intent intent1 = new Intent();
                intent1.setClass(CreateRecipeActivity.this, SearchDiseaseActivity.class);
                startActivityForResult(intent1,DISEASE);
                break;
            case R.id.select_disease_group:
                Intent intent3 = new Intent();
                intent3.setClass(CreateRecipeActivity.this, DiseaseGroupActivity.class);
                startActivityForResult(intent3,GROUP);
                break;
//            case R.id.handle_receipe:
//                showCameraDialog();
//                break;
            case R.id.create_recipe_btn:
                if (TextUtils.isEmpty(diseaseLL.getText().toString().trim())||"请选择疾病".equals(diseaseLL.getText().toString().trim())){
                    showToast("请选择疾病诊断说明");
                    return;
                }
                if (array.size()<=0){
                    showToast("请添加处方记录");
                    return;
                }
                for (RecipeDrugBean bean:
                     array) {
                    RecipeDrugRequest request = new RecipeDrugRequest();
                    request.setDrugID(bean.getDrugID());
                    request.setDosage(bean.getDosage());
                    request.setDrugName(bean.getDrugName().getCommonName());
                    request.setDrugNumber(bean.getDrugNumber());
                    request.setTakingTime(bean.getTakingTime());
                    request.setTakingType(bean.getTakingType());
                    request.setDrugRemark(bean.getRemark());
                    request.setStandard(bean.getStandard());
                    request.setDrugPrice(bean.getDrugPrice());
                    request.setDrugUnit(bean.getDrugUnit());
                    requests.add(request);
                }
                Gson gson = new Gson();
                if(checkType=="1"){
                    presenter.createRecipe(patientId,visitId,diseaseLL.getText().toString().trim(), gson.toJson(requests));
                }else{
                    if(diseaseGroupID==null){
                        AlertDialog alertDialog2 = new AlertDialog.Builder(this)
                                .setTitle("提示")
                                .setMessage("检测到您选择的疾病未选择分组，是否进行分组？")
                                .setIcon(R.drawable.img_icon)
                                .setPositiveButton("我要分组", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                })
                                .setNegativeButton("不分组", new DialogInterface.OnClickListener() {//添加取消
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        presenter.createRecipe(patientId,visitId,diseaseLL.getText().toString().trim(), gson.toJson(requests));
                                    }
                                })
                                .create();
                        alertDialog2.show();
                    }else {
                        presenter.addGroupInfo(diseaseGroupID,icdName);

                    }
                }

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD && resultCode == RESULT_OK){
            RecipeDrugBean bean = new RecipeDrugBean();
            bean.setDrugID(data.getStringExtra("id"));
            DrugNameBean nameBean = new DrugNameBean();
            nameBean.setCommonName(data.getStringExtra("name"));
            bean.setDrugName(nameBean);
            bean.setStandard(data.getStringExtra("standard"));
            bean.setDrugNumber(data.getStringExtra("num"));
            bean.setTakingTime(data.getStringExtra("timeId"));
            bean.setTakingType(data.getStringExtra("typeId"));
            bean.setDosage(data.getStringExtra("method"));
            bean.setRemark(data.getStringExtra("remark"));
            bean.setDrugPrice(data.getStringExtra("price"));
            bean.setDrugUnit(data.getStringExtra("drugUnit"));
            array.add(bean);
            reloadList(array);
//            adapter.setList(array);
//            RecipeDrugRequest bean = new RecipeDrugRequest();
//            bean.setDrugID(data.getStringExtra("id"));
        }
        if (requestCode == DISEASE && resultCode == RESULT_OK) {
            icdID = data.getExtras().getString("icdID");
            icdName = data.getExtras().getString("icdName");
            diseaseLL.setText(data.getExtras().getString("icdName"));
            if ("2".equals(data.getExtras().getString("checkType"))) {
                selectGroupLL.setVisibility(View.VISIBLE);
            } else {
                checkType="1";
                selectGroupLL.setVisibility(View.GONE);
            }

        }
        if (requestCode == GROUP && resultCode == RESULT_OK) {

            diseaseGroupID = data.getExtras().getString("diseaseGroupID");
            diseaseGroupTV.setText(data.getExtras().getString("diseaseGroupName"));
        }
        if (requestCode == SELECT && resultCode == RESULT_OK){
            patientId = data.getStringExtra("patientId");
            visitId = data.getStringExtra("visitId");
            nameTV.setText(data.getStringExtra("names"));
        }
        if(requestCode == UploadPersonPhotoUtil.REQUEST_CODE_TAKE_PHOTO){

            if (resultCode==RESULT_OK) {
                presenter.upLoad(mPhotoPath,"8");
            }else {
                Toast.makeText(CreateRecipeActivity.this, "拍照失败", Toast.LENGTH_SHORT).show();
            }


        }

        if(requestCode == UploadPersonPhotoUtil.REQUEST_CODE_SELECT_PICTURE) {
            mPhotoPath = UploadPersonPhotoUtil.getPathFromIntent(data,CreateRecipeActivity.this);
            if (mPhotoPath == null) {
                Toast.makeText(CreateRecipeActivity.this, "选择照片失败", Toast.LENGTH_SHORT).show();
                return;
            }
            presenter.upLoad(mPhotoPath,"8");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * recycler 适配器
     */
    private class RecordListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        private Context mContext;
        private ArrayList<RecipeDrugBean> mList;
        private OnItemClick onItemClickListener;

        public RecordListAdapter(Context context,ArrayList<RecipeDrugBean> list ){
            this.mContext = context;
            this.mList = list;
        }


        public void setList(ArrayList<RecipeDrugBean> list){
            this.mList = list;
            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext)
                    .inflate(R.layout.list_item_create_recipe,parent,false);
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

        private void initView(RecordViewHolder handler, RecipeDrugBean bean){
            if (bean == null) return;

            String timeStr = "";
            if ("1".equals(bean.getTakingTime())){
                timeStr = "一日一次";
            }else if("2".equals(bean.getTakingTime())){
                timeStr = "一日二次";
            }else if("3".equals(bean.getTakingTime())){
                timeStr = "一日三次";
            }else if("4".equals(bean.getTakingTime())){
                timeStr = "一日四次";
            }

            String typeStr = "";
            if ("1".equals(bean.getTakingType())){
                typeStr = "口服";
            }else if("2".equals(bean.getTakingType())){
                typeStr = "外敷";
            }else if("3".equals(bean.getTakingType())){
                typeStr = "注射";
            }

            handler.addressTV.setText("用法用量:"+typeStr+","+timeStr);
            handler.methodTV.setText("规格："+bean.getStandard()+"*"+bean.getDrugNumber());
            handler.nameTV.setText(bean.getDrugName().getCommonName());
        }
    }

    public interface OnItemClick{
        public void onItemClick(View view, int position);
    }

    /**
     * 静态类
     */
    private static class RecordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public MyTextView nameTV;
        public LinearLayout lineLayout;
        public MyTextView methodTV;
        public MyTextView addressTV;
        private OnItemClick onItemClick;

        public RecordViewHolder(View view, OnItemClick onItemClick){
            super(view);
            this.onItemClick = onItemClick;
            view.setOnClickListener(this);
            addressTV = (MyTextView) view.findViewById(R.id.list_item_address);
            nameTV = (MyTextView) view.findViewById(R.id.list_item_name);
            methodTV = (MyTextView) view.findViewById(R.id.list_item_method);
            lineLayout = (LinearLayout) view.findViewById(R.id.list_item_line1);
        }

        @Override
        public void onClick(View view) {
            if(onItemClick != null){
                onItemClick.onItemClick(view,getLayoutPosition());
            }
        }
    }
    /**
     * 选择拍照
     */
    private void showCameraDialog(){
        //特殊处理（6.0以上）
        if (ActivityCompat.checkSelfPermission(CreateRecipeActivity.this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED
                ||
                ActivityCompat.checkSelfPermission(CreateRecipeActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
            // Check Permissions Now
            // Callback onRequestPermissionsResult interceptado na Activity MainActivity
            PermissionGen.with(CreateRecipeActivity.this)
                    .addRequestCode(10)
                    .permissions(
                            Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .request();
            return;
        }

        final Dialog mDialog = new Dialog(CreateRecipeActivity.this, R.style.custom_dialog);
        final View mDialogContentView= LayoutInflater.from(CreateRecipeActivity.this).inflate(R.layout.dialog_item_camera_select,null);

        MyTextView cameraTV = (MyTextView)mDialogContentView.findViewById(R.id.dialog_item_camera);
        MyTextView albumTV = (MyTextView)mDialogContentView.findViewById(R.id.dialog_item_album);

        cameraTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
                mPhotoPath = UploadPersonPhotoUtil.takePhoto(CreateRecipeActivity.this);

            }
        });

        albumTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
                UploadPersonPhotoUtil.selectImageFromLocal(CreateRecipeActivity.this);
            }
        });

        mDialog.setContentView(mDialogContentView);
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.setCancelable(true);
        mDialog.getWindow().setGravity(Gravity.CENTER);
        WindowManager.LayoutParams p = mDialog.getWindow().getAttributes(); // 获取对话框当前的参数值
        p.width = (int) (getResources().getDisplayMetrics().widthPixels * 0.85); // 宽度设置为屏幕的0.85
        mDialog.getWindow().setAttributes(p);
        mDialog.show();
        mDialog.getWindow().setWindowAnimations(R.style.anim_dialog);
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
    public void reloadList(ArrayList<RecipeDrugBean> list) {
        array = list;
        adapter.setList(array);

        if (list.size()>0){
            noRecordLayout.setVisibility(View.GONE);
            recordRecycler.setVisibility(View.VISIBLE);
        }else {
            noRecordLayout.setVisibility(View.VISIBLE);
            recordRecycler.setVisibility(View.GONE);
        }
    }

    @Override
    public void reloadPicState() {
        handleReceipeTV.setText("已上传");
        handleReceipeTV.setClickable(false);
    }

    @Override
    public void addGroupInfoResult(String code) {
        if("1".equals(code)){
            Gson gson = new Gson();
            presenter.createRecipe(patientId,visitId,diseaseLL.getText().toString().trim(), gson.toJson(requests));
        }
    }
}
