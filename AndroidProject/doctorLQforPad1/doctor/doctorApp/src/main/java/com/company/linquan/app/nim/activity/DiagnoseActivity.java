package com.company.linquan.app.nim.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.company.linquan.app.R;
import com.company.linquan.app.base.BaseActivity;
import com.company.linquan.app.bean.FileCollectInfoBean;
import com.company.linquan.app.bean.MessageBean;
import com.company.linquan.app.bean.MyInqueryInfoBean;
import com.company.linquan.app.bean.PatientBean;
import com.company.linquan.app.bean.UserInfoBean;
import com.company.linquan.app.http.JSONFirstAsk;
import com.company.linquan.app.moduleWork.WorkInterface;
import com.company.linquan.app.moduleWork.ui.CreateRecipe2Activity;
import com.company.linquan.app.moduleWork.ui.CreateRecipeActivity;
import com.company.linquan.app.moduleWork.ui.DiseaseGroupActivity;
import com.company.linquan.app.moduleWork.ui.PatientInfoActivity;
import com.company.linquan.app.moduleWork.ui.SearchDiseaseActivity;
import com.company.linquan.app.moduleWork.ui.SearchPatientActivity;
import com.company.linquan.app.nim.ConversationInterface;
import com.company.linquan.app.nim.presenter.DiagnosePresenterImp;
import com.company.linquan.app.nim.presenter.FirstAskPresenterImp;
import com.company.linquan.app.util.Base64;
import com.company.linquan.app.util.ExitApp;
import com.company.linquan.app.util.JSONHelper;
import com.company.linquan.app.util.MyDialog;
import com.company.linquan.app.util.MyImageView;
import com.company.linquan.app.util.MyToast;
import com.company.linquan.app.view.MyTextView;
import com.google.gson.JsonObject;
import com.hb.dialog.myDialog.MyAlertDialog;
import com.hb.dialog.myDialog.MyAlertInputDialog;
import com.netease.nim.uikit.common.CommonUtil;
import com.netease.nim.uikit.visitinfo.VisitInfomation;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import static com.company.linquan.app.config.C.VISIDS;
import static com.company.linquan.app.config.C.VISNAMES;

public class DiagnoseActivity extends BaseActivity implements View.OnClickListener, WorkInterface.DiagnoseInterface {

    private Dialog myDialog;

    private DiagnosePresenterImp presenter;

    private TextView diseaseTV,diseaseGroupTV;
    private LinearLayout selectGroupLL,createReceipeLL;
    private RecyclerView recordRecycler;
    private ArrayList<MessageBean> array;
    private ArrayList<String> messages=new ArrayList<>();

    private RecordListAdapter recordListAdapter;

    private String icdID,diseaseGroupID,icdName,checkType,patientId;;

    private final static int GROUP =1,DISEASE=2;

    int sw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExitApp.getInstance().addActivity(this);		// add Activity 方便退出
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 开启自定义标题栏
        setContentView(R.layout.activity_diagnose2);

        initHead();
        initView();
        getData();
        setListener();
    }

    private void initHead() {
        RelativeLayout head = (RelativeLayout) findViewById(R.id.head_layout_top);
        MyTextView title = (MyTextView)head. findViewById(R.id.head_top_title);
        title.setText("诊断");
        ImageView rightIV = (ImageView)head.findViewById(R.id.head_top_image);
        rightIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView(){
        diseaseTV=findViewById(R.id.select_disease);
        diseaseGroupTV=findViewById(R.id.select_disease_group);
        selectGroupLL=findViewById(R.id.layout3);
        selectGroupLL.setVisibility(View.GONE);
        createReceipeLL=findViewById(R.id.create_receipe);

        sw = getContext().getResources().getDisplayMetrics().widthPixels;
        presenter = new DiagnosePresenterImp(this);

        recordRecycler = (RecyclerView)findViewById(R.id.list_item_img);
        recordRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        recordRecycler.setLayoutManager(new GridLayoutManager(this,3,GridLayoutManager.VERTICAL, false));
        array = new ArrayList<MessageBean>();
        recordListAdapter = new RecordListAdapter(getContext(),array);
        recordRecycler.setAdapter(recordListAdapter);
        recordRecycler.setItemAnimator(new DefaultItemAnimator());

        diseaseTV.setOnClickListener(this);
        diseaseGroupTV.setOnClickListener(this);
        createReceipeLL.setOnClickListener(this);

    }
    private void getData() {
        presenter.getMessage(UserInfoBean.getInstance().getWyyID());
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.select_disease:
                Intent intent = new Intent();
                intent.setClass(DiagnoseActivity.this, SearchDiseaseActivity.class);
                startActivityForResult(intent,DISEASE);
                break;
            case R.id.select_disease_group:
                Intent intent3 = new Intent();
                intent3.setClass(DiagnoseActivity.this, DiseaseGroupActivity.class);
                startActivityForResult(intent3,GROUP);
                break;
            case R.id.create_receipe:
                if(checkType=="1"){
                    Intent intent2 = new Intent();
                    intent2.setClass(DiagnoseActivity.this, CreateRecipe2Activity.class);
                    intent2.putExtra("patientId",patientId);
                    intent2.putExtra("visitID",VISIDS.get(0));
                    intent2.putExtra("visitName",VISNAMES.get(0));
                    intent2.putExtra("icdID",icdID);
                    intent2.putExtra("icdName",icdName);
                    startActivity(intent2);
                }else{
                    if(icdID==null){
                        showToast("请选择疾病");
                        return;
                    }
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
                                        Intent intent3 = new Intent();
                                        intent3.setClass(DiagnoseActivity.this, CreateRecipe2Activity.class);
                                        intent3.putExtra("patientId",patientId);
                                        intent3.putExtra("visitID",VISIDS.get(0));
                                        intent3.putExtra("visitName",VISNAMES.get(0));
                                        intent3.putExtra("icdID",icdID);
                                        intent3.putExtra("icdName",icdName);
                                        startActivity(intent3);
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



    private void setListener() {

        recordListAdapter.setOnItemClickListener(new OnItemClick() {
            @Override
            public void onItemClick(View view, ViewName viewName, int position) {//11
                if(view.getId()==R.id.list_item_voice_btn){
                    try {
                        MediaPlayer mediaPlayer = new MediaPlayer();
                        mediaPlayer.setDataSource(array.get(position).getContentchat()+"?audioTrans&type=mp3");
                        mediaPlayer.prepareAsync();
                        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mediaPlayer) {
                                mediaPlayer.start();
                            }
                        });
                        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                if (mp != null) {
                                    mp.release();
                                }
                                Log.d("mediaplayer", "onCompletion: play sound.");
                            }
                        });
                        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                            @Override
                            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                                Log.d("mediaplayer", "Play online sound onError: " + i + ", " + i1);
                                return false;
                            }
                        });
                    } catch (IOException e1) {
                        Log.e("mediaplayer", "url: ", e1);
                    }
                }


            }
        });
    }

    @Override
    public void reloadMessage(ArrayList<MessageBean> list,ArrayList<PatientBean> patient) {
        array = list;
        recordListAdapter.setList(array);

        patientId=patient.get(0).getId();
    }

    @Override
    public void addGroupInfoResult(String code) {
//        if("1".equals(code)){
            Intent intent = new Intent();
            intent.setClass(DiagnoseActivity.this, CreateRecipe2Activity.class);
            intent.putExtra("patientId",patientId);
            intent.putExtra("visitID",VISIDS.get(0));
            intent.putExtra("visitName",VISNAMES.get(0));
            intent.putExtra("icdID",icdID);
            intent.putExtra("icdName",icdName);
            startActivity(intent);
//        }
    }


    public interface OnItemClick{
        public void onItemClick(View view, ViewName viewName, int position);
    }
    /**
     * recycler 适配器
     */
    private class RecordListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        private Context mContext;
        private ArrayList<MessageBean> mList;
        private OnItemClick onItemClickListener;

        public RecordListAdapter(Context context,ArrayList<MessageBean> list ){//1
            this.mContext = context;
            this.mList = list;
        }


        public void setList(ArrayList<MessageBean> list){//4
            this.mList = list;
            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {//6

            View view = LayoutInflater.from(mContext)
                    .inflate(R.layout.list_item_message_record,parent,false);
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

        private void initView(RecordViewHolder handler, MessageBean bean){//9
            if (bean == null) return;
            /**
             * TODO
             * 设置聊天记录控件显示内容
             */

            handler.nameTV.setText(bean.getFromname());
            handler.timeTV.setText(bean.getSenddate());

            switch (bean.getMessagetype()){
                case "1"://文字
                    String content = new String(Base64.decode(bean.getContentchat()));
                    handler.contentTV.setText(content);
                    handler.contentTV.setVisibility(View.VISIBLE);
                    handler.imageView.setVisibility(View.GONE);
                    handler.voiceBTN.setVisibility(View.GONE);
                    break;
                case "2"://图片
                    handler.imageView.setImageURL(bean.getContentchat());
                    handler.contentTV.setVisibility(View.GONE);
                    handler.imageView.setVisibility(View.VISIBLE);
                    handler.voiceBTN.setVisibility(View.GONE);
                    break;
                case "3"://语音
                    handler.contentTV.setVisibility(View.GONE);
                    handler.imageView.setVisibility(View.GONE);
                    handler.voiceBTN.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }


        }
    }
    //item里面有多个控件可以点击（item+item内部控件）
    public enum ViewName {
        ITEM,
        PRACTISE
    }
    /**
     * 静态类
     */
    private static class RecordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public MyTextView nameTV;
        public MyTextView timeTV;
        public MyTextView contentTV;
        public MyImageView imageView;
        public Button voiceBTN;

        private OnItemClick onItemClick;

        public RecordViewHolder(View view, OnItemClick onItemClick) {//7
            super(view);
            this.onItemClick = onItemClick;
            view.setOnClickListener(this);
            nameTV = (MyTextView) view.findViewById(R.id.list_item_name);
            timeTV = (MyTextView) view.findViewById(R.id.list_item_time);
            contentTV = (MyTextView) view.findViewById(R.id.list_item_txt);
            imageView = (MyImageView)view.findViewById(R.id.list_item_image);
            voiceBTN = view.findViewById(R.id.list_item_voice_btn);
            voiceBTN.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {//10
            if (onItemClick != null) {
//                onItemClick.onItemClick(view,getLayoutPosition(),3);
                switch (view.getId()) {
                    case R.id.member_list_recycler:
                        onItemClick.onItemClick(view, ViewName.PRACTISE, getLayoutPosition());
                        break;
                    default:
                        onItemClick.onItemClick(view, ViewName.ITEM, getLayoutPosition());
                        break;
                }
            }

        }



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == DISEASE && resultCode == RESULT_OK) {
            icdID = data.getExtras().getString("icdID");
            icdName = data.getExtras().getString("icdName");
            diseaseTV.setText(data.getExtras().getString("icdName"));
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

        super.onActivityResult(requestCode, resultCode, data);
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
        MyToast.showToast(this, txt, Toast.LENGTH_SHORT);
    }

}
