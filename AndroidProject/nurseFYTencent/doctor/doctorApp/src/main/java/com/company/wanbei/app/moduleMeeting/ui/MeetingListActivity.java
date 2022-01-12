package com.company.wanbei.app.moduleMeeting.ui;

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

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.company.wanbei.app.R;
import com.company.wanbei.app.base.BaseActivity;
import com.company.wanbei.app.bean.ArticleBean;
import com.company.wanbei.app.bean.MeetingBean;
import com.company.wanbei.app.moduleMeeting.MeetingInterface;
import com.company.wanbei.app.moduleMeeting.MeetingPresenter;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.Base64;
import com.company.wanbei.app.util.ExitApp;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.MyDialog;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.MyToast;
import com.tencent.qcloud.tuikit.tuichat.fromApp.view.MyTextView;

import java.util.ArrayList;

//import androidx.core.content.ContextCompat;
//import androidx.recyclerview.widget.DefaultItemAnimator;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by YC on 2018/6/6.
 */

public class MeetingListActivity extends BaseActivity implements MeetingInterface.ViewInterface{

    private Dialog myDialog;
    private String status,lat,address,room;

    private MeetingPresenter presenter;
    private RecyclerView recordRecycler;
    private ArrayList<MeetingBean> array;
    private ArrayList<ArticleBean> arrayArticle;
    private RecordListAdapter adapter;
    private ArticleListAdapter articleAdapter;
    private SwipeRefreshLayout refreshLayout;
    private int sw,page = 1,pageSize = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExitApp.getInstance().addActivity(this);		// add Activity 方便退出
//        requestWindowFeature(Window.FEATURE_NO_TITLE); // 开启自定义标题栏
        setContentView(R.layout.activity_meeting_list);
        initHead();
        initView();
        setListener();
        getData();
    }

    private void initHead() {
        status = getIntent().getStringExtra("status");
        RelativeLayout head = (RelativeLayout)findViewById(R.id.head_layout);
        TextView titleTV = (MyTextView)head.findViewById(R.id.head_top_title);
        if(status.equals("1")){
            titleTV.setText("未开始会议");
        }else if(status.equals("2")){
            titleTV.setText("以往会议");
        }else {
            titleTV.setText("学术文章");
        }

        ImageView rightIV = (ImageView)head.findViewById(R.id.head_top_image);
        rightIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView(){
        sw = getContext().getResources().getDisplayMetrics().widthPixels;
        presenter = new MeetingPresenter(this);

        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.select_refresh);
        refreshLayout.setColorSchemeColors(ContextCompat.getColor(this,R.color.base_red_color));
        recordRecycler = (RecyclerView)findViewById(R.id.meeting_list);
        recordRecycler.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL, false));
        if(status.equals("3")){
            arrayArticle = new ArrayList<>();
            articleAdapter = new ArticleListAdapter(getContext(),arrayArticle);
            recordRecycler.setAdapter(articleAdapter);
        }else {
            array = new ArrayList<>();
            adapter = new RecordListAdapter(getContext(),array);
            recordRecycler.setAdapter(adapter);
        }

        recordRecycler.setItemAnimator(new DefaultItemAnimator());



    }

    private void setListener() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getData();
            }
        });
        if(status.equals("3")){
            articleAdapter.setOnItemClickListener(new OnItemClick() {
                @Override
                public void onItemClick(View view, int position, int index) {
                    if (position < 0){
                        return;
                    }
                    Intent intent = new Intent(getContext(), ArticleDetailActivity.class);
                    intent.putExtra("writingID",arrayArticle.get(position).getId());
                    startActivity(intent);

                }
            });
        }else {
            adapter.setOnItemClickListener(new OnItemClick() {
                @Override
                public void onItemClick(View view, int position, int index) {
                    if (position < 0){
                        return;
                    }
                    if(view.getId()==R.id.desc_btn){
                        Intent intent = new Intent(getContext(), MeetingDetailActivity.class);
                        intent.putExtra("meetingID",array.get(position).getId());
                        startActivity(intent);
                    }

                }
            });
        }


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

    public void getData(){
        if(status.equals("3")){
            presenter.getWritingsByPage("1",String.valueOf(page),String.valueOf(pageSize));
        }else {
            presenter.getMeetingListByPage(Integer.parseInt(status),page,pageSize);
        }
    }




    @Override
    public void refreshList(int status, ArrayList<MeetingBean> list) {
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

    @Override
    public void reloadBanner(ArrayList<String> list) {

    }

    @Override
    public void refreshArticle(ArrayList<ArticleBean> list) {
        if (page == 1){
            refreshLayout.setRefreshing(false);
            arrayArticle = list;
            articleAdapter.setList(arrayArticle);
        }

        if (page > 1){
            for (ArticleBean bean:
                    list) {
                arrayArticle.add(bean);
            }
            articleAdapter.setList(arrayArticle);
        }
    }

    @Override
    public void initUrl(int position, String url) {

    }

    public interface OnItemClick{
        public void onItemClick(View view, int position, int index);
    }
    /**
     * recycler 适配器
     */
    private class RecordListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        private Context mContext;
        private ArrayList<MeetingBean> mList;
        private OnItemClick onItemClickListener;

        public RecordListAdapter(Context context, ArrayList<MeetingBean> list ){
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
                    .inflate(R.layout.list_item_home_meeting,parent,false);
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

        private void initView(RecordViewHolder handler, MeetingBean bean){
            if (bean == null) return;

            handler.titleTV.setText(bean.getTitle());
            int w2 = (94*sw)/720;
            int h2 = (94*sw)/620;
            Glide.with(mContext).load(bean.getMeetingCover1())
                    .apply(new RequestOptions()
                            .override(h2,w2)
                            .error(R.drawable.img_no_img)
                            .centerCrop())
                    .into(handler.imageView);
            handler.timeTV.setText(bean.getMeetingTime());
            handler.speakerTV.setText("主讲人："+bean.getSpeaker());
        }
    }

    /**
     * 静态类
     */
    private static class RecordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView imageView;
        public TextView timeTV;
        public TextView titleTV;
        public TextView speakerTV;
        public LinearLayout descLL;

        private OnItemClick onItemClick;

        public RecordViewHolder(View view, OnItemClick onItemClick){
            super(view);
            this.onItemClick = onItemClick;
            view.setOnClickListener(this);
            imageView = view.findViewById(R.id.article_image);
            speakerTV=view.findViewById(R.id.speaker);

            titleTV = (TextView) view.findViewById(R.id.article_title);
            timeTV = (TextView) view.findViewById(R.id.article_time);
            descLL=view.findViewById(R.id.desc_btn);

            descLL.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(onItemClick != null){
                onItemClick.onItemClick(view,getLayoutPosition(),3);
            }
        }
    }


    /**
     * recycler 适配器
     */
    private class ArticleListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        private Context mContext;
        private ArrayList<ArticleBean> mList;
        private OnItemClick onItemClickListener;

        public ArticleListAdapter(Context context, ArrayList<ArticleBean> list ){
            this.mContext = context;
            this.mList = list;
        }


        public void setList(ArrayList<ArticleBean> list){
            this.mList = list;
            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext)
                    .inflate(R.layout.list_item_home_article,parent,false);
            return new ArticleViewHolder(view,onItemClickListener);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if(holder instanceof ArticleViewHolder){
                initView((ArticleViewHolder) holder, mList.get(position));
            }
        }


        @Override
        public int getItemCount() {
            return mList.size();
        }

        private void setOnItemClickListener(OnItemClick listener){
            onItemClickListener = listener;
        }

        private void initView(ArticleViewHolder handler, ArticleBean bean){
            if (bean == null) return;
//            int w2 = (94*sw)/720;
//            int h2 = (94*sw)/620;
            Glide.with(mContext).load(bean.getLogoUrl()).into(handler.imageView);
            handler.fromTV.setText(bean.getReleaseMan());
            handler.titleTV.setText(bean.getTitle());
            String content = new String(Base64.decode(bean.getThisContent()));

            handler.contentTV.setText(content.replace("&nbsp;",""));
            handler.timeTV.setText(bean.getReleaseTime());
//            handler.readNumTV.setText("阅读量："+bean.getReadNumber());
        }
    }

    /**
     * 静态类
     */
    private static class ArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView imageView;
        public TextView fromTV;
        public TextView titleTV;
        public TextView contentTV;
        public TextView timeTV;
        public TextView readNumTV;

        private OnItemClick onItemClick;

        public ArticleViewHolder(View view, OnItemClick onItemClick){
            super(view);
            this.onItemClick = onItemClick;
            view.setOnClickListener(this);
            imageView = view.findViewById(R.id.article_image);
            fromTV = view.findViewById(R.id.article_from);
            titleTV = (TextView) view.findViewById(R.id.article_title);
            contentTV = (TextView) view.findViewById(R.id.article_content);
            timeTV = (TextView) view.findViewById(R.id.article_time);
            readNumTV = (TextView) view.findViewById(R.id.article_read_num);
        }

        @Override
        public void onClick(View view) {
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
