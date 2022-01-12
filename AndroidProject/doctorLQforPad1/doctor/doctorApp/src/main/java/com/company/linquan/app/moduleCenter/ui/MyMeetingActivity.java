package com.company.linquan.app.moduleCenter.ui;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.company.linquan.app.R;
import com.company.linquan.app.base.BaseActivity;
import com.company.linquan.app.bean.MeetingBean;
import com.company.linquan.app.moduleCenter.UserCenterInterface;
import com.company.linquan.app.moduleCenter.imp.MyMeetingPresenterImp;
import com.company.linquan.app.moduleMeeting.ui.CreateMeetingActivity;
import com.company.linquan.app.moduleMeeting.ui.MeetingDetailWebActivity;
import com.company.linquan.app.util.Base64;
import com.company.linquan.app.util.ExitApp;
import com.company.linquan.app.util.MyDialog;
import com.company.linquan.app.util.MyToast;
import com.company.linquan.app.view.MyTextView;

import java.util.ArrayList;

import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

/**
 * Created by YC on 2018/6/19.
 */

public class MyMeetingActivity extends BaseActivity implements UserCenterInterface.MyMeetingInterface,View.OnClickListener{
    private Dialog myDialog;

    private RecyclerView recordRecycler;
    private RecordListAdapter adapter;
    private ArrayList<MeetingBean>  array;
    private SwipeRefreshLayout refreshLayout;
    int sw;
    private final static int ADD =1;
    private MyMeetingPresenterImp presenter;
    private int page = 1;
    private MyTextView oneTV,twoTV,threeTV,fourTV;
    private int type = -1;
    int clickIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExitApp.getInstance().addActivity(this);		// add Activity 方便退出
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 开启自定义标题栏
        setContentView(R.layout.activity_my_meeting);
        initHead();
        initView();
        setListener();
        getData();
    }

    private void getData() {
        presenter.getMeeting(page,type);
    }

    private void initHead() {
        RelativeLayout head = (RelativeLayout) findViewById(R.id.head_layout);
        MyTextView title = (MyTextView) head.findViewById(R.id.head_top_title);
        title.setText("直播会议");
        ImageView rightIV = (ImageView)head.findViewById(R.id.head_top_image);
        rightIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView(){

        presenter = new MyMeetingPresenterImp(this);

        findViewById(R.id.my_meeting_btn).setOnClickListener(this);
        findViewById(R.id.my_meeting_btn).setVisibility(View.GONE);
        sw = getContext().getResources().getDisplayMetrics().widthPixels;
        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.my_meeting_refresh);
        refreshLayout.setColorSchemeColors(ContextCompat.getColor(this,R.color.base_red_color));
        recordRecycler = (RecyclerView)findViewById(R.id.my_meeting_recycler);
        recordRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));
        array = new ArrayList<>();
        adapter = new RecordListAdapter(getContext(),array);
        recordRecycler.setAdapter(adapter);
        recordRecycler.setItemAnimator(new DefaultItemAnimator());

        oneTV = (MyTextView) findViewById(R.id.my_meeting_one);
        oneTV.setOnClickListener(this);
        twoTV = (MyTextView) findViewById(R.id.my_meeting_two);
        twoTV.setOnClickListener(this);
        threeTV = (MyTextView) findViewById(R.id.my_meeting_three);
        threeTV.setOnClickListener(this);
        fourTV = (MyTextView) findViewById(R.id.my_meeting_four);
        fourTV.setOnClickListener(this);
    }

    private void setListener() {
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

                clickIndex = position;
                //特殊处理（6.0以上）
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED
                        ||
                        ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.RECORD_AUDIO)
                                != PackageManager.PERMISSION_GRANTED) {
                    // Check Permissions Now
                    // Callback onRequestPermissionsResult interceptado na Activity MainActivity
                    PermissionGen.with(MyMeetingActivity.this)
                            .addRequestCode(10)
                            .permissions(Manifest.permission.CAMERA
                                    , Manifest.permission.RECORD_AUDIO)
                            .request();
                    return;
                }
                Intent intent = new Intent(MyMeetingActivity.this,MeetingDetailWebActivity.class);
                intent.putExtra("index",array.get(position).getVedioType());
                intent.putExtra("id",array.get(position).getId());
                intent.putExtra("personId",array.get(position).getPersonID());
                intent.putExtra("url",array.get(position).getVedioCover2());
                startActivity(intent);
            }
        });

        recordRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @PermissionSuccess(requestCode = 10)
    public void doSomething(){
        Intent intent = new Intent(MyMeetingActivity.this,MeetingDetailWebActivity.class);
        intent.putExtra("index",array.get(clickIndex).getVedioType());
        intent.putExtra("id",array.get(clickIndex).getId());
        intent.putExtra("personId",array.get(clickIndex).getPersonID());
        intent.putExtra("url",array.get(clickIndex).getVedioCover());
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.my_meeting_btn:
                startActivityForResult(new Intent(this,CreateMeetingActivity.class),ADD);
                break;
            case R.id.my_meeting_one:
                type = -1;
                setHeadLayout(type);
                break;
            case R.id.my_meeting_two:
                type = 0;
                setHeadLayout(type);
                break;
            case R.id.my_meeting_three:
                type = 1;
                setHeadLayout(type);
                break;
            case R.id.my_meeting_four:
                type = 2;
                setHeadLayout(type);
                break;
        }
    }


    private void setHeadLayout(int index){
        oneTV.setBackgroundResource(R.drawable.shape_my_meeting_top_style);
        oneTV.setTextColor(ContextCompat.getColor(this,R.color.base_red_color));

        twoTV.setBackgroundResource(R.drawable.shape_my_meeting_top_style);
        twoTV.setTextColor(ContextCompat.getColor(this,R.color.base_red_color));

        threeTV.setBackgroundResource(R.drawable.shape_my_meeting_top_style);
        threeTV.setTextColor(ContextCompat.getColor(this,R.color.base_red_color));

        fourTV.setBackgroundResource(R.drawable.shape_my_meeting_top_style);
        fourTV.setTextColor(ContextCompat.getColor(this,R.color.base_red_color));

        switch (index){
            case -1:
                oneTV.setBackgroundResource(R.drawable.shape_my_meeting_top_checked_style);
                oneTV.setTextColor(ContextCompat.getColor(this,R.color.white));
                break;
            case 0:
                twoTV.setBackgroundResource(R.drawable.shape_my_meeting_top_checked_style);
                twoTV.setTextColor(ContextCompat.getColor(this,R.color.white));
                break;
            case 1:
                threeTV.setBackgroundResource(R.drawable.shape_my_meeting_top_checked_style);
                threeTV.setTextColor(ContextCompat.getColor(this,R.color.white));
                break;
            case 2:
                fourTV.setBackgroundResource(R.drawable.shape_my_meeting_top_checked_style);
                fourTV.setTextColor(ContextCompat.getColor(this,R.color.white));
                break;
        }
        presenter.getMeeting(page=1,index);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD && resultCode == RESULT_OK)
        {
            page = 1;
            getData();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * recycler 适配器
     */
    private class RecordListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        private Context mContext;
        private ArrayList<MeetingBean> mList;
        private OnItemClick onItemClickListener;

        public RecordListAdapter(Context context,ArrayList<MeetingBean> list ){
            this.mContext = context;
            this.mList = list;
        }


        public void setList(ArrayList<MeetingBean> list){
            this.mList = list;
            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext)
                    .inflate(R.layout.list_item_my_meeting,parent,false);
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

        private void initView(RecordViewHolder handler,MeetingBean bean){

            if (bean == null) return;

            int w = (208*sw)/720;
            Glide.with(mContext).load(bean.getHeadUrl()).apply(new RequestOptions().override(w,w).centerCrop()).into(handler.imageIV);
            handler.titleTV.setText(Base64.decodeToString(bean.getTitle()));
            handler.contentTV.setText(Base64.decodeToString(bean.getIntroduction()));
            String[] ss = bean.getTimePart().split("至");
            handler.timeTV.setText(ss[0]);
        }
    }

    public interface OnItemClick{
        public void onItemClick(View view, int position);
    }

    /**
     * 静态类
     */
    private static class RecordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView imageIV;
        public MyTextView timeTV;
        public MyTextView titleTV;
        public MyTextView contentTV;
        private OnItemClick onItemClick;

        public RecordViewHolder(View view, OnItemClick onItemClick){
            super(view);
            this.onItemClick = onItemClick;
            view.setOnClickListener(this);
            imageIV = (ImageView) view.findViewById(R.id.list_item_video);
            timeTV = (MyTextView) view.findViewById(R.id.list_item_time);
            titleTV = (MyTextView) view.findViewById(R.id.list_item_title);
            contentTV = (MyTextView) view.findViewById(R.id.list_item_content);
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
    public void refreshList(ArrayList<MeetingBean> list) {
        if (page == 1){
            refreshLayout.setRefreshing(false);
            array = list;
            adapter.setList(array);
        }
        if (page > 1){
            for (MeetingBean bean:
                 list) {
                array.add(bean);
            }
            adapter.setList(array);
        }

    }
}
