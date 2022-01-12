package com.company.linquan.app.moduleWork.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.company.linquan.app.R;
import com.company.linquan.app.base.BaseActivity;
import com.company.linquan.app.moduleAuth.SelectDataActivity;
import com.company.linquan.app.moduleWork.WorkInterface;
import com.company.linquan.app.util.ExitApp;
import com.company.linquan.app.util.MyDialog;
import com.company.linquan.app.util.MyToast;
import com.company.linquan.app.view.DelEditText;
import com.company.linquan.app.view.MyTextView;
import com.netease.nim.uikit.common.util.log.LogUtil;

/**
 * Created by YC on 2018/6/14.
 */

public class AddDrugsActivity extends BaseActivity implements WorkInterface.AddDrugsInterface,View.OnClickListener{

    private Dialog myDialog;
    private MyTextView selectBtn, drugNameTV, timeTV, eatTV;
    private DelEditText methodET,numET;
    private final static int SELECT = 1, TIME = 2, EAT = 3;
    private String id = "",timeId ="", eatId="",timeStr ="", eatStr="",name ="",standard="",price="",drugUnit="";
    private EditText remarkET;
    private Spinner mSpinner1;
    public final static int DRUG_TIME = 5, DRUG_EAT = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExitApp.getInstance().addActivity(this);		// add Activity 方便退出
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 开启自定义标题栏
        setContentView(R.layout.activity_add_drugs);
        initHead();
        initView();
    }

    private void initHead() {
        RelativeLayout head = (RelativeLayout) findViewById(R.id.head_layout);
        MyTextView title = (MyTextView) head.findViewById(R.id.head_top_title);
        title.setText("添加药品");
        ImageView leftIV = (ImageView)head.findViewById(R.id.head_top_image);
        leftIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView(){
        findViewById(R.id.add_drugs_btn).setOnClickListener(this);
        selectBtn = (MyTextView) findViewById(R.id.add_drugs_select_btn);
        selectBtn.setOnClickListener(this);
        drugNameTV = (MyTextView) findViewById(R.id.add_drugs_name);
        timeTV = (MyTextView) findViewById(R.id.add_drugs_time);
        eatTV = (MyTextView) findViewById(R.id.add_drugs_eat);
        methodET = (DelEditText)findViewById(R.id.add_drugs_method);
        methodET.setHint("请输入每次用量");
        methodET.setInputType(InputType.TYPE_CLASS_NUMBER);
        methodET.setGravity(Gravity.RIGHT);
        numET = (DelEditText)findViewById(R.id.add_drugs_num);
        numET.setHint("请输入数量");
        numET.setGravity(Gravity.RIGHT);
        timeTV.setOnClickListener(this);
        eatTV.setOnClickListener(this);
        remarkET = (EditText)findViewById(R.id.add_drugs_remark);

        mSpinner1=findViewById(R.id.spinner_1);
        //原始string数组
        final String[] spinnerItems = {"片","丸","粒","袋","毫升(ml)","毫克(mg"};
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
                drugUnit=spinnerItems[pos];
                //设置spinner内的填充文字居中
                //((TextView)view).setGravity(Gravity.CENTER);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add_drugs_select_btn:
                startActivityForResult(new Intent(AddDrugsActivity.this, SelectDrugActivity.class),SELECT);
                break;
            case R.id.add_drugs_time:
                Intent timeIntent = new Intent(AddDrugsActivity.this, SelectDataActivity.class);
                timeIntent.putExtra("type",DRUG_TIME);
                startActivityForResult(timeIntent,TIME);
                break;
            case R.id.add_drugs_eat:
                Intent eatIntent = new Intent(AddDrugsActivity.this, SelectDataActivity.class);
                eatIntent.putExtra("type",DRUG_EAT);
                startActivityForResult(eatIntent,EAT);
                break;

            case R.id.add_drugs_btn:
                if (TextUtils.isEmpty(methodET.getText().toString().trim())) return;
                if (TextUtils.isEmpty(numET.getText().toString().trim())) return;
                if (TextUtils.isEmpty(eatId)) return;
                if (TextUtils.isEmpty(timeId)) return;

                Intent intent = new Intent();
                intent.putExtra("id",id);
                intent.putExtra("name",name);
                intent.putExtra("standard",standard);
                intent.putExtra("method",methodET.getText().toString().trim());
                intent.putExtra("time",timeStr);
                intent.putExtra("type",eatStr);
                intent.putExtra("timeId",timeId);
                intent.putExtra("typeId",eatId);
                intent.putExtra("num",numET.getText().toString().trim());
                intent.putExtra("remark",remarkET.getText().toString().trim());
                intent.putExtra("price",price);
                intent.putExtra("drugUnit",drugUnit);
                setResult(RESULT_OK,intent);
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SELECT && resultCode == RESULT_OK){
            selectBtn.setText("重新选择");
            id = data.getExtras().getString("id");
            name = data.getExtras().getString("name");
            standard = data.getExtras().getString("standard");
            price=data.getExtras().getString("price");
            drugNameTV.setText(name+"\n" +standard);
        }

        if (requestCode == TIME && resultCode == RESULT_OK){
            timeStr = data.getStringExtra("name");
            timeId = data.getStringExtra("id");
            timeTV.setText(timeStr);
        }

        if (requestCode == EAT && resultCode == RESULT_OK){
            eatStr = data.getStringExtra("name");
            eatId = data.getStringExtra("id");
            eatTV.setText(eatStr);
        }

        super.onActivityResult(requestCode, resultCode, data);
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
