package com.company.linquan.app.moduleAuth;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.company.linquan.app.R;
import com.company.linquan.app.base.BaseActivity;
import com.company.linquan.app.http.JSONPersonAll;
import com.company.linquan.app.moduleWork.ui.SelectNurseServiceActivity;
import com.company.linquan.app.moduleWork.ui.SelectNurseTypeActivity;
import com.company.linquan.app.nim.helper.UserUpdateHelper;
import com.company.linquan.app.util.Base64;
import com.company.linquan.app.util.ExitApp;
import com.company.linquan.app.util.MyDialog;
import com.company.linquan.app.util.MyToast;
import com.company.linquan.app.util.UploadPersonPhotoUtil;
import com.company.linquan.app.view.MyTextView;
import com.netease.nim.uikit.business.session.actions.PickImageAction;
import com.netease.nim.uikit.common.ToastHelper;
import com.netease.nim.uikit.common.util.log.LogUtil;
import com.netease.nimlib.sdk.AbortableFuture;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallbackWrapper;
import com.netease.nimlib.sdk.ResponseCode;
import com.netease.nimlib.sdk.nos.NosService;
import com.netease.nimlib.sdk.uinfo.UserService;
import com.netease.nimlib.sdk.uinfo.constant.UserInfoFieldEnum;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;
import okio.Utf8;

import static com.netease.nim.uikit.common.util.sys.NetworkUtil.TAG;

/**
 * Created by YC on 2018/1/9.
 */

public class AuthActivity extends BaseActivity implements AuthInterface.ViewInterface
,View.OnClickListener{

    private Dialog myDialog;
    private EditText nameET, idCardET,personRemarkET,goodJobET,headET,yearET;
    private String sex="",typeId ="",positionId = "",depateId ="",hospitalId = "";
    private AuthPresenter presenter;
    private ImageView image1,image101,image102, image2, image3, image4, image5;
    private MyTextView sexTV,typeTV, positionTV, depateTV, hosptialTV,nurseTypeTV,nurseServiceTV;
    private String mPhotoPath;
    private int index = 0;
    private String[] ids = new String[]{"3","4","10","1","2","5"};
    private String[] signs = new String[]{"","","","","",""};
    private RecyclerView recyclerView;
    private ArrayList<String> array,arrayIDS;
    private MyListAdapter adapter;

    private Spinner mSpinner1;
    private String nurseTypeID = "",specialty="";

    public final static int SEX=0,TYPE = 1,POSITION = 2, DEPATE = 3, HOSPITAL = 4,NURSETYPE=5,NURSESERVICE=6;
    private String photoPath = "";
    int sw;

    AbortableFuture<String> uploadAvatarFuture;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExitApp.getInstance().addActivity(this);		// add Activity 方便退出
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 开启自定义标题栏
        setContentView(R.layout.activity_auth);
        initHead();
        initView();
        getData();
        setListener();
    }

    private void getData() {
        presenter.getInfo();
    }

    private void initHead() {
        RelativeLayout head = (RelativeLayout)findViewById(R.id.head_layout);
        TextView titleTV = (MyTextView)head.findViewById(R.id.head_top_title);
        titleTV.setVisibility(View.GONE);
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
        presenter = new AuthPresenter(this);

        nameET = (EditText)findViewById(R.id.auth_name);
        idCardET = (EditText)findViewById(R.id.auth_idCard);
        idCardET.setInputType( InputType.TYPE_CLASS_NUMBER);
        findViewById(R.id.auth_btn).setOnClickListener(this);

        sexTV= (MyTextView)findViewById(R.id.auth_sex);
        typeTV = (MyTextView)findViewById(R.id.auth_type);
        positionTV = (MyTextView)findViewById(R.id.auth_position);
        depateTV = (MyTextView)findViewById(R.id.auth_room);
        hosptialTV = (MyTextView)findViewById(R.id.auth_hospital);
        nurseTypeTV=(MyTextView)findViewById(R.id.auth_nurse_type);
        nurseServiceTV=(MyTextView)findViewById(R.id.auth_nurse_service);
        sexTV.setOnClickListener(this);
        typeTV.setOnClickListener(this);
        positionTV.setOnClickListener(this);
        depateTV.setOnClickListener(this);
        hosptialTV.setOnClickListener(this);
        nurseTypeTV.setOnClickListener(this);
        nurseServiceTV.setOnClickListener(this);

        image1 = (ImageView) findViewById(R.id.auth_image1);
        image1.setOnClickListener(this);
        image101 = (ImageView) findViewById(R.id.auth_image101);
        image101.setOnClickListener(this);
        image102 = (ImageView) findViewById(R.id.auth_image102);
        image102.setOnClickListener(this);
        image3 = (ImageView)findViewById(R.id.auth_image3);
        image3.setOnClickListener(this);
        image2 = (ImageView)findViewById(R.id.auth_image5);
        image2.setOnClickListener(this);
        image5 = (ImageView)findViewById(R.id.auth_image6);
        image5.setOnClickListener(this);
        image4 = (ImageView)findViewById(R.id.auth_image4);
        image4.setOnClickListener(this);

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(AuthActivity.this,LinearLayoutManager.VERTICAL, false));
        array = new ArrayList<>();
        arrayIDS= new ArrayList<>();
        adapter = new MyListAdapter(getContext(),array);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        personRemarkET=(EditText)findViewById(R.id.auth_person_remark);
        goodJobET= (EditText)findViewById(R.id.auth_good_job);
        headET= (EditText)findViewById(R.id.auth_head);

        yearET=findViewById(R.id.auth_years);

        mSpinner1=findViewById(R.id.spinner_1);
        //原始string数组
        final String[] spinnerItems = {"否","是"};
        //简单的string数组适配器：样式res，数组
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, spinnerItems);
        //下拉的样式res
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //绑定 Adapter到控件
        mSpinner1.setAdapter(spinnerAdapter);
        //选择监听
        mSpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //parent就是父控件spinner
            //view就是spinner内填充的textview,id=@android:id/text1
            //position是值所在数组的位置
            //id是值所在行的位置，一般来说与positin一致
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
//                Log.i("onItemSelected : parent.id="+parent.getId()+
//                        ",isSpinnerId="+(parent.getId() == R.id.spinner_1)+
//                        ",viewid="+view.getId()+ ",pos="+pos+",id="+id);
//                Toast.makeText(getContext(),"选择了["+spinnerItems[pos]+"]",Toast.LENGTH_SHORT).show();
                if(spinnerItems[pos].equals("是")){
                    specialty="1";
                }else {
                    specialty="2";
                }
                //设置spinner内的填充文字居中
                //((TextView)view).setGravity(Gravity.CENTER);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
    }
    public void reloadGrid(ArrayList<String> array) {
        this.array = array;
        adapter.setList(array);
    }
    /**
     * recycler 适配器
     */
    private class MyListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        private Context mContext;
        private ArrayList<String> mList;
        private OnItemClick onItemClickListener;

        public MyListAdapter(Context context,ArrayList<String> list ){
            this.mContext = context;
            this.mList = list;
        }


        public void setList(ArrayList<String> list){
            this.mList = list;
            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext)
                    .inflate(R.layout.list_item_nurse_service,parent,false);
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

        private void initView(MyViewHolder handler, String bean){
            handler.nurseServiceTV.setText(bean);
        }
    }

    public interface OnItemClick{
        public void onItemClick(View view, int position);
    }

    /**
     * 静态类
     */
    private static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public MyTextView nurseServiceTV;
        public MyTextView deleteTV;
        private OnItemClick onItemClick;

        public MyViewHolder(View view, OnItemClick onItemClick){
            super(view);
            this.onItemClick = onItemClick;
            view.setOnClickListener(this);
            nurseServiceTV = (MyTextView)view.findViewById(R.id.list_item_nurse_service);
            deleteTV = (MyTextView) view.findViewById(R.id.list_item_delete);
            deleteTV.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(onItemClick != null){
                onItemClick.onItemClick(view,getLayoutPosition());
            }
        }
    }

    private void setListener() {
        adapter.setOnItemClickListener(new AuthActivity.OnItemClick() {
            @Override
            public void onItemClick(View view, int position) {

                if (position < 0) {
                    return;
                }
                /**
                 * 点击项删除护理服务
                 */
                switch (view.getId()){
                    case R.id.list_item_delete:
                        array.remove(position);
                        arrayIDS.remove(position);
                        reloadGrid(array);
                        break;
                }

            }
        });
    }
    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.auth_sex:
                intent.setClass(AuthActivity.this, SelectDataActivity.class);
                intent.putExtra("type",SEX);
                startActivityForResult(intent,SEX);
                break;
            case R.id.auth_type:
                intent.setClass(AuthActivity.this, SelectDataActivity.class);
                intent.putExtra("type",TYPE);
                startActivityForResult(intent,TYPE);
                break;
            case R.id.auth_position:
                intent.setClass(AuthActivity.this, SelectDataActivity.class);
                intent.putExtra("type",POSITION);
                startActivityForResult(intent,POSITION);
                break;
            case R.id.auth_room:
                intent.setClass(AuthActivity.this, SelectDataActivity.class);
                intent.putExtra("type",DEPATE);
                startActivityForResult(intent,DEPATE);
                break;
            case R.id.auth_hospital:
                intent.setClass(AuthActivity.this, SelectDataActivity.class);
                intent.putExtra("type",HOSPITAL);
                startActivityForResult(intent,HOSPITAL);
                break;
            case R.id.auth_nurse_type:
                intent.setClass(AuthActivity.this, SelectNurseTypeActivity.class);
                intent.putExtra("type",NURSETYPE);
                startActivityForResult(intent,NURSETYPE);
                break;
            case R.id.auth_nurse_service:
                intent.setClass(AuthActivity.this, SelectNurseServiceActivity.class);
                intent.putExtra("type",NURSESERVICE);
                intent.putExtra("ids",nurseTypeID);
                intent.putStringArrayListExtra("nurseList",array);
                intent.putStringArrayListExtra("nurseIDS",arrayIDS);
                startActivityForResult(intent,NURSESERVICE);
                break;
            case R.id.auth_btn:

                if ("".equals(nameET.getText().toString().trim())){
                    showToast("请输入真实姓名");
                    return;
                }
                if ("".equals(idCardET.getText().toString().trim())||idCardET.getText().toString().trim().length()!=18){
                    showToast("请输入正确的身份证格式");
                    return;
                }
                if ("".equals(sex)){
                    showToast("请选择性别");
                    return;
                }
                if ("".equals(typeId)){
                    showToast("请选择职业类型");
                    return;
                }
                if ("".equals(positionId)){
                    showToast("请选择职称");
                    return;
                }
                if ("".equals(hospitalId)){
                    showToast("请选择医院");
                    return;
                }
                if ("".equals(depateId)){
                    showToast("请选择科室");
                    return;
                }
//                if ("".equals(goodJobET.getText().toString().trim())){
//                    showToast("请输入简介或者擅长");
//                    return;
//                }
//                if ("".equals(headET.getText().toString().trim())){
//                    showToast("请输入头衔");
//                    return;
//                }
                if ("".equals(signs[0])){
                    showToast("请上传医师资格证书");
                    return;
                }
                if ("".equals(signs[1])){
                    showToast("请上传医师执业证书正面");
                    return;
                }
                if ("".equals(signs[2])){
                    showToast("请上传医师执业证书背面");
                    return;
                }
                if ("".equals(signs[3])){
                    showToast("请上传医师身份证正面");
                    return;
                }
                if ("".equals(signs[4])){
                    showToast("请上传医师身份证背面");
                    return;
                }

                if ("".equals(photoPath)){
                    showToast("请上传医师头像");
                    return;
                }
                String ids ="";
                String signs = "";
                for (int i=0;i < 5;i++){

//                    signs = signs + this.signs[i]+",";
                    if (this.signs[i].startsWith("http")){
                        signs = signs + ""+",";
                        ids = ids + ""+",";
                    }else{
                        ids = ids + this.ids[i]+",";
                        signs = signs + this.signs[i]+",";
                    }
                }
                String serviceIdList="";
                for(int i=0;i<arrayIDS.size();i++){
                    serviceIdList+=arrayIDS.get(i)+",";
                }
                //网易云更新头像
                File file = new File(photoPath);
                if (file == null) {
                    return;
                }
                uploadAvatarFuture = NIMClient.getService(NosService.class).upload(file, PickImageAction.MIME_JPEG);
                uploadAvatarFuture.setCallback(new RequestCallbackWrapper<String>() {
                    @Override
                    public void onResult(int code, String url, Throwable exception) {
                        if (code == ResponseCode.RES_SUCCESS && !TextUtils.isEmpty(url)) {
                            LogUtil.i(TAG, "upload avatar success, url =" + url);

                            UserUpdateHelper.update(UserInfoFieldEnum.AVATAR, url, new RequestCallbackWrapper<Void>() {
                                @Override
                                public void onResult(int code, Void result, Throwable exception) {
                                    if (code == ResponseCode.RES_SUCCESS) {
                                        ToastHelper.showToast(getContext(), R.string.head_update_success);
                                    } else {
                                        ToastHelper.showToast(getContext(), R.string.head_update_failed);
                                    }
                                }
                            }); // 更新资料
                        } else {
                            ToastHelper.showToast(getContext(), R.string.user_info_update_failed);
                        }
                    }
                });
                //网易云更新昵称
                Map<UserInfoFieldEnum, Object> fields = new HashMap<>(1);
                fields.put(UserInfoFieldEnum.Name, nameET.getText().toString().trim());
                NIMClient.getService(UserService.class).updateUserInfo(fields)
                        .setCallback(new RequestCallbackWrapper<Void>() {
                            @Override
                            public void onResult(int i, Void aVoid, Throwable throwable) {

                            }
                        });
                if(sexTV.getText().toString().equals("男")){
                    sex="1";
                }else {
                    sex="2";
                }
                if(serviceIdList.length()<=1){
                    presenter.auth(nameET.getText().toString().trim(),idCardET.getText().toString().trim()
                            ,sex,typeId,positionId,hospitalId,depateId,"".equals(ids)?"":ids.substring(0,ids.length()-1),signs.substring(0,signs.length()-1)
                            ,photoPath,personRemarkET.getText().toString().trim(),goodJobET.getText().toString().trim(),headET.getText().toString().trim(),"","","");
                }else {
                    presenter.auth(nameET.getText().toString().trim(),idCardET.getText().toString().trim()
                            ,sex,typeId,positionId,hospitalId,depateId,"".equals(ids)?"":ids.substring(0,ids.length()-1),signs.substring(0,signs.length()-1)
                            ,photoPath,personRemarkET.getText().toString().trim(),goodJobET.getText().toString().trim(),headET.getText().toString().trim(),serviceIdList.substring(0,serviceIdList.length()-1),yearET.getText().toString().trim(),specialty);
                }


                break;
            case R.id.auth_image1:
                index = 0;
                showCameraDialog();
                break;
            case R.id.auth_image101:
                index = 1;
                showCameraDialog();
                break;
            case R.id.auth_image102:
                index = 2;
                showCameraDialog();
                break;
            case R.id.auth_image5:
                index = 3;
                showCameraDialog();
                break;
            case R.id.auth_image6:
                index = 4;
                showCameraDialog();
                break;
            case R.id.auth_image3:
                index = 5;
                showCameraDialog();
                break;
            case R.id.auth_image4:
                index = 6;
                showCameraDialog();
                break;

        }
    }

    /**
     * 选择拍照
     */
    private void showCameraDialog(){
        //特殊处理（6.0以上）
        if (ActivityCompat.checkSelfPermission(AuthActivity.this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED
                ||
                ActivityCompat.checkSelfPermission(AuthActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
            // Check Permissions Now
            // Callback onRequestPermissionsResult interceptado na Activity MainActivity
            PermissionGen.with(AuthActivity.this)
                    .addRequestCode(10)
                    .permissions(
                            Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .request();
            return;
        }

        final Dialog mDialog = new Dialog(AuthActivity.this, R.style.custom_dialog);
        final View mDialogContentView= LayoutInflater.from(AuthActivity.this).inflate(R.layout.dialog_item_camera_select,null);

        MyTextView cameraTV = (MyTextView)mDialogContentView.findViewById(R.id.dialog_item_camera);
        MyTextView albumTV = (MyTextView)mDialogContentView.findViewById(R.id.dialog_item_album);

        cameraTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
                mPhotoPath = UploadPersonPhotoUtil.takePhoto(AuthActivity.this);
            }
        });

        albumTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
                UploadPersonPhotoUtil.selectImageFromLocal(AuthActivity.this);
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @PermissionSuccess(requestCode = 10)
    public void doSomething(){
        showCameraDialog();
    }

    @PermissionFail(requestCode = 10)
    public void doFailSomething(){
        PermissionGen.with(AuthActivity.this)
                .addRequestCode(10)
                .permissions(Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .request();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == NURSETYPE && resultCode == RESULT_OK){
            nurseTypeTV.setText(data.getStringExtra("nurseType"));
            nurseTypeID=data.getStringExtra("nurseTypeID");
        }
        if (requestCode == NURSESERVICE && resultCode == RESULT_OK){
            nurseServiceTV.setText(data.getStringExtra("nurseService"));
            boolean flag=false;
            for (int i = 0; i < arrayIDS.size(); i++) {
                if(arrayIDS.get(i).equals(data.getStringExtra("nurseServeID"))){
                    flag=true;
                }
            }
            if(!flag){
                array.add(data.getStringExtra("nurseService"));
                arrayIDS.add(data.getStringExtra("nurseServeID"));
            }

            reloadGrid(array);
        }


        if (requestCode == SEX && resultCode == RESULT_OK){
            sexTV.setText(data.getExtras().getString("name"));
            sex = data.getExtras().getString("id");
        }

        if (requestCode == TYPE && resultCode == RESULT_OK){
            typeTV.setText(data.getExtras().getString("name"));
            typeId = data.getExtras().getString("id");
        }

        if (requestCode == POSITION && resultCode == RESULT_OK){
            positionTV.setText(data.getExtras().getString("name"));
            positionId = data.getExtras().getString("id");
            if(data.getExtras().getString("name").equals("护士")||data.getExtras().getString("name").equals("护师")||data.getExtras().getString("name").equals("主管护师")||data.getExtras().getString("name").equals("副主任护师")||data.getExtras().getString("name").equals("主任护师")){
                findViewById(R.id.line103).setVisibility(View.VISIBLE);
                findViewById(R.id.recycler_view).setVisibility(View.VISIBLE);
                findViewById(R.id.line102).setVisibility(View.VISIBLE);
                findViewById(R.id.imageView102).setVisibility(View.VISIBLE);
                findViewById(R.id.auth_nurse_service).setVisibility(View.VISIBLE);
                findViewById(R.id.myTextView102).setVisibility(View.VISIBLE);
                findViewById(R.id.line101).setVisibility(View.VISIBLE);
                findViewById(R.id.imageView101).setVisibility(View.VISIBLE);
                findViewById(R.id.auth_nurse_type).setVisibility(View.VISIBLE);
                findViewById(R.id.myTextView101).setVisibility(View.VISIBLE);

                findViewById(R.id.myTextView192).setVisibility(View.VISIBLE);
                findViewById(R.id.auth_years).setVisibility(View.VISIBLE);
                findViewById(R.id.myTextView1921).setVisibility(View.VISIBLE);
                findViewById(R.id.line1012).setVisibility(View.VISIBLE);
                findViewById(R.id.myTextView191).setVisibility(View.VISIBLE);
                findViewById(R.id.spinner_1).setVisibility(View.VISIBLE);
                findViewById(R.id.line1011).setVisibility(View.VISIBLE);
            }else {
                findViewById(R.id.line103).setVisibility(View.GONE);
                findViewById(R.id.recycler_view).setVisibility(View.GONE);
                findViewById(R.id.line102).setVisibility(View.GONE);
                findViewById(R.id.imageView102).setVisibility(View.GONE);
                findViewById(R.id.auth_nurse_service).setVisibility(View.GONE);
                findViewById(R.id.myTextView102).setVisibility(View.GONE);
                findViewById(R.id.line101).setVisibility(View.GONE);
                findViewById(R.id.imageView101).setVisibility(View.GONE);
                findViewById(R.id.auth_nurse_type).setVisibility(View.GONE);
                findViewById(R.id.myTextView101).setVisibility(View.GONE);

                findViewById(R.id.myTextView192).setVisibility(View.GONE);
                findViewById(R.id.auth_years).setVisibility(View.GONE);
                findViewById(R.id.myTextView1921).setVisibility(View.GONE);
                findViewById(R.id.line1012).setVisibility(View.GONE);
                findViewById(R.id.myTextView191).setVisibility(View.GONE);
                findViewById(R.id.spinner_1).setVisibility(View.GONE);
                findViewById(R.id.line1011).setVisibility(View.GONE);
            }
        }

        if (requestCode == DEPATE && resultCode == RESULT_OK){
            depateTV.setText(data.getExtras().getString("name"));
            depateId = data.getExtras().getString("id");
        }

        if (requestCode == HOSPITAL && resultCode == RESULT_OK){
            hosptialTV.setText(data.getExtras().getString("name"));
            hospitalId = data.getExtras().getString("id");
        }

        if(requestCode == UploadPersonPhotoUtil.REQUEST_CODE_TAKE_PHOTO){
            if(mPhotoPath == null){
                return;
            }
            File file = new File(mPhotoPath);
            if (file.isFile() && file.exists()) {
                Bitmap mBitmap = UploadPersonPhotoUtil.getBitmapFromFile(mPhotoPath, 720,1280);
                UploadPersonPhotoUtil.savePicToSdcard(mBitmap,mPhotoPath);
                mBitmap.recycle();
                mBitmap = null;
                int w = (112*sw)/720;
                switch (index){
                    case 0:
                        Glide.with(AuthActivity.this).load(mPhotoPath).apply(new RequestOptions().error(R.drawable.img_auth_add).override(w,w).centerCrop()).into(image1);
                        presenter.upLoad(mPhotoPath,index);
                        break;
                    case 1:
                        Glide.with(AuthActivity.this).load(mPhotoPath).apply(new RequestOptions().error(R.drawable.img_auth_add).override(w,w).centerCrop()).into(image101);
                        presenter.upLoad(mPhotoPath,index);
                        break;
                    case 2:
                        Glide.with(AuthActivity.this).load(mPhotoPath).apply(new RequestOptions().error(R.drawable.img_auth_add).override(w,w).centerCrop()).into(image102);
                        presenter.upLoad(mPhotoPath,index);
                        break;
                    case 3:
                        Glide.with(AuthActivity.this).load(mPhotoPath).apply(new RequestOptions().error(R.drawable.img_auth_add).override(w,w).centerCrop()).into(image2);
                        presenter.upLoad(mPhotoPath,index);
                        break;
                    case 4:
                        Glide.with(AuthActivity.this).load(mPhotoPath).apply(new RequestOptions().error(R.drawable.img_auth_add).override(w,w).centerCrop()).into(image5);
                        presenter.upLoad(mPhotoPath,index);
                        break;
                    case 5:
                        Glide.with(AuthActivity.this).load(mPhotoPath).apply(new RequestOptions().error(R.drawable.img_auth_add).override(w,w).centerCrop()).into(image3);
                        presenter.upLoad(mPhotoPath,index);
                        break;
                    case 6:
                        photoPath = mPhotoPath;
                        Glide.with(AuthActivity.this).load(mPhotoPath).apply(new RequestOptions().error(R.drawable.img_auth_add).override(w,w).centerCrop()).into(image4);
                        break;

                }

            }
        }

        if(requestCode == UploadPersonPhotoUtil.REQUEST_CODE_SELECT_PICTURE) {
            mPhotoPath = UploadPersonPhotoUtil.getPathFromIntent(data,AuthActivity.this);
            if (mPhotoPath == null) {
                Toast.makeText(AuthActivity.this, "选择照片失败", Toast.LENGTH_SHORT).show();
                return;
            }
            File file = new File(mPhotoPath);
            if (file.isFile()) {
                Bitmap mBitmap = UploadPersonPhotoUtil.getBitmapFromFile(mPhotoPath, 720, 1280);
                UploadPersonPhotoUtil.savePicToSdcard(mBitmap, mPhotoPath);
                mBitmap.recycle();
                mBitmap = null;
                int w = (112 * sw) / 720;
                switch (index){
                    case 0:
                        Glide.with(AuthActivity.this).load(mPhotoPath).apply(new RequestOptions().error(R.drawable.img_auth_add).override(w,w).centerCrop()).into(image1);
                        presenter.upLoad(mPhotoPath,index);
                        break;
                    case 1:
                        Glide.with(AuthActivity.this).load(mPhotoPath).apply(new RequestOptions().error(R.drawable.img_auth_add).override(w,w).centerCrop()).into(image101);
                        presenter.upLoad(mPhotoPath,index);
                        break;
                    case 2:
                        Glide.with(AuthActivity.this).load(mPhotoPath).apply(new RequestOptions().error(R.drawable.img_auth_add).override(w,w).centerCrop()).into(image102);
                        presenter.upLoad(mPhotoPath,index);
                        break;
                    case 3:
                        Glide.with(AuthActivity.this).load(mPhotoPath).apply(new RequestOptions().error(R.drawable.img_auth_add).override(w,w).centerCrop()).into(image2);
                        presenter.upLoad(mPhotoPath,index);
                        break;
                    case 4:
                        Glide.with(AuthActivity.this).load(mPhotoPath).apply(new RequestOptions().error(R.drawable.img_auth_add).override(w,w).centerCrop()).into(image5);
                        presenter.upLoad(mPhotoPath,index);
                        break;
                    case 5:
                        Glide.with(AuthActivity.this).load(mPhotoPath).apply(new RequestOptions().error(R.drawable.img_auth_add).override(w,w).centerCrop()).into(image3);
                        presenter.upLoad(mPhotoPath,index);
                        break;
                    case 6:
                        photoPath = mPhotoPath;
                        Glide.with(AuthActivity.this).load(mPhotoPath).apply(new RequestOptions().error(R.drawable.img_auth_add).override(w,w).centerCrop()).into(image4);
                        break;

                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void finishActivity() {
        setResult(RESULT_OK);
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
    public void initPath(String path,String urlId,int type) {
        signs[type] = urlId;
    }
    private void setSpinnerDefaultValue(Spinner spinner, String value) {
        SpinnerAdapter apsAdapter = spinner.getAdapter();
        int size = apsAdapter.getCount();
        for (int i = 0; i < size; i++) {
            if (TextUtils.equals(value, apsAdapter.getItem(i).toString())) {
                spinner.setSelection(i,true);
                break;
            }
        }
    }
    @Override
    public void reloadView(JSONPersonAll bean) {
        int size = bean.getPicTable().size();
        int w = (112 * sw) / 720;
        for (int j=0;j<size;j++){
            if (ids[0].equals(bean.getPicTable().get(j).getFileType())){
                Glide.with(AuthActivity.this)
                        .load(bean.getPicTable().get(j).getFileUrl())
                        .apply(new RequestOptions()
                        .error(R.drawable.img_auth_add)
                        .override(w,w)
                        .centerCrop())
                        .into(image1);
                signs[0] = bean.getPicTable().get(j).getFileUrl();
            }
            if (ids[1].equals(bean.getPicTable().get(j).getFileType())){
                Glide.with(AuthActivity.this)
                        .load(bean.getPicTable().get(j).getFileUrl())
                        .apply(new RequestOptions()
                        .error(R.drawable.img_auth_add)
                        .override(w,w)
                        .centerCrop())
                        .into(image101);
                signs[1] = bean.getPicTable().get(j).getFileUrl();
            }
            if (ids[2].equals(bean.getPicTable().get(j).getFileType())){
                Glide.with(AuthActivity.this)
                        .load(bean.getPicTable().get(j).getFileUrl())
                        .apply(new RequestOptions()
                        .error(R.drawable.img_auth_add)
                        .override(w,w)
                        .centerCrop())
                        .into(image102);
                signs[2] = bean.getPicTable().get(j).getFileUrl();
            }
            if (ids[3].equals(bean.getPicTable().get(j).getFileType())){
                Glide.with(AuthActivity.this)
                        .load(bean.getPicTable().get(j).getFileUrl())
                        .apply(new RequestOptions()
                        .error(R.drawable.img_auth_add)
                        .override(w,w)
                        .centerCrop())
                        .into(image2);
                signs[3] = bean.getPicTable().get(j).getFileUrl();
            }

            if (ids[4].equals(bean.getPicTable().get(j).getFileType())){
                Glide.with(AuthActivity.this)
                        .load(bean.getPicTable().get(j).getFileUrl())
                        .apply(new RequestOptions()
                        .error(R.drawable.img_auth_add)
                        .override(w,w)
                        .centerCrop())
                        .into(image5);
                signs[4] = bean.getPicTable().get(j).getFileUrl();
            }


            if (ids[5].equals(bean.getPicTable().get(j).getFileType())){
                Glide.with(AuthActivity.this)
                        .load(bean.getPicTable().get(j).getFileUrl())
                        .apply(new RequestOptions()
                        .error(R.drawable.img_auth_add)
                        .override(w,w)
                        .centerCrop())
                        .into(image3);
                signs[5] = bean.getPicTable().get(j).getFileUrl();
            }



        }

        nameET.setText(bean.getInfoJson().getMyName());
        idCardET.setText(bean.getInfoJson().getIdCardNo());

        try {
            goodJobET.setText(Base64.decodeToString(URLDecoder.decode(bean.getInfoJson().getBeGoodAt(),"utf-8")));
            personRemarkET.setText(Base64.decodeToString(URLDecoder.decode(bean.getInfoJson().getPersonRemark(),"utf-8")));
            headET.setText(Base64.decodeToString(URLDecoder.decode(bean.getInfoJson().getPersonHonor(),"utf-8")));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        photoPath = bean.getInfoJson().getHeadUrl();
        Glide.with(AuthActivity.this)
                .load(bean.getInfoJson().getHeadUrl())
                .apply(new RequestOptions()
                .error(R.drawable.img_auth_add)
                .override(w,w)
                .centerCrop())
                .into(image4);

        sex=bean.getInfoJson().getSex();
        if ("1".equals(sex)){
            sexTV.setText("男");
        }
        if("2".equals(sex)){
            sexTV.setText("女");
        }
        typeId = bean.getInfoJson().getCareerType();
        if ("1".equals(typeId)){
            typeTV.setText("全职");
        }else{
            typeTV.setText("兼职");
        }
        positionId = bean.getInfoJson().getAcademicTitle();
        if ("1".equals(positionId)){
            positionTV.setText("主任医师");
        }else if ("2".equals(positionId)){
            positionTV.setText("副主任医师");
        }else if ("3".equals(positionId)){
            positionTV.setText("主治医师");
        }else if ("4".equals(positionId)){
            positionTV.setText("医师");
        }else if ("5".equals(positionId)){
            positionTV.setText("护士");
        }else if ("6".equals(positionId)){
            positionTV.setText("护师");
        }else if ("7".equals(positionId)){
            positionTV.setText("副主任护师");
        }else if ("8".equals(positionId)){
            positionTV.setText("主任护师");
        }else if ("10".equals(positionId)){
            positionTV.setText("医士");
        }else if ("11".equals(positionId)){
            positionTV.setText("主管护师");
        }
        depateId = bean.getInfoJson().getDepartmentID();
        depateTV.setText(bean.getInfoJson().getDepartmentName());
        hospitalId = bean.getInfoJson().getHospitalID();
        hosptialTV.setText(bean.getInfoJson().getHospitalName());

        if(positionId.equals("5")||positionId.equals("6")||positionId.equals("7")||positionId.equals("8")||positionId.equals("11")){
            findViewById(R.id.line103).setVisibility(View.VISIBLE);
            findViewById(R.id.recycler_view).setVisibility(View.VISIBLE);
            findViewById(R.id.line102).setVisibility(View.VISIBLE);
            findViewById(R.id.imageView102).setVisibility(View.VISIBLE);
            findViewById(R.id.auth_nurse_service).setVisibility(View.VISIBLE);
            findViewById(R.id.myTextView102).setVisibility(View.VISIBLE);
            findViewById(R.id.line101).setVisibility(View.VISIBLE);
            findViewById(R.id.imageView101).setVisibility(View.VISIBLE);
            findViewById(R.id.auth_nurse_type).setVisibility(View.VISIBLE);
            findViewById(R.id.myTextView101).setVisibility(View.VISIBLE);

            findViewById(R.id.myTextView192).setVisibility(View.VISIBLE);
            findViewById(R.id.auth_years).setVisibility(View.VISIBLE);
            findViewById(R.id.myTextView1921).setVisibility(View.VISIBLE);
            findViewById(R.id.line1012).setVisibility(View.VISIBLE);
            findViewById(R.id.myTextView191).setVisibility(View.VISIBLE);
            findViewById(R.id.spinner_1).setVisibility(View.VISIBLE);
            findViewById(R.id.line1011).setVisibility(View.VISIBLE);

            for(int i=0;i<bean.getSeriveNur().size();i++){
                array.add(bean.getSeriveNur().get(i).getServiceName());
                arrayIDS.add(bean.getSeriveNur().get(i).getId());
            }
            yearET.setText(bean.getInfoJson().getYears());
            if(bean.getInfoJson().getSpecialty().equals("1")){
                setSpinnerDefaultValue(mSpinner1,"是");
            }
        }


    }
}
