package com.company.wanbei.app.moduleMeeting;

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


import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.company.wanbei.app.R;
import com.company.wanbei.app.base.BaseFragment;
import com.company.wanbei.app.bean.ArticleBean;
import com.company.wanbei.app.bean.MeetingBean;
import com.company.wanbei.app.moduleMeeting.ui.ArticleDetailActivity;
import com.company.wanbei.app.moduleMeeting.ui.MeetingDetailActivity;
import com.company.wanbei.app.moduleMeeting.ui.MeetingListActivity;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.Base64;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.MyDialog;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.MyToast;
import com.tencent.qcloud.tuikit.tuichat.fromApp.view.MyTextView;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;

public class HomeFragmentNew extends BaseFragment implements MeetingInterface.ViewInterface, View.OnClickListener {

    private Dialog myDialog;
    private Banner banner;
    private MeetingPresenter presenter;
    private RecyclerView recordRecycler,previousRecycle,articleRecycle;
    private ArrayList<MeetingBean> array,arrayList;
    private ArrayList<ArticleBean> arrayArticle;
    private RecordListAdapter adapter,previousAdapter;
    private ArticleListAdapter articleAdapter;
    private RelativeLayout notStartRL,beforeRL,articelRL;
    private LinearLayout layout1,layout2;
    private int sw,statusMeeting;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_meeting, null);
        initHead(view);
        initView(view);
        setListener();
        getData();
        return view;
    }
    @Override
    protected void lazyLoad() {
        if(getActivity() == null){
            return;
        }

    }
    private void initHead(View view) {
        RelativeLayout head = (RelativeLayout)view.findViewById(R.id.layout_head);
        MyTextView title = (MyTextView)head.findViewById(R.id.head_top_title);
        title.setText("首页");
        ImageView rightIV = (ImageView)head.findViewById(R.id.head_top_image);
        rightIV.setVisibility(View.GONE);
    }
    private void initView(View view){
        sw = getContext().getResources().getDisplayMetrics().widthPixels;
        presenter = new MeetingPresenter(this);
        banner = (Banner) view.findViewById(R.id.banner);

        notStartRL = view.findViewById(R.id.layout_not_start);
        notStartRL.setVisibility(View.VISIBLE);
        beforeRL = view.findViewById(R.id.layout_before);
        beforeRL.setVisibility(View.VISIBLE);
        articelRL = view.findViewById(R.id.layout_article);
        articelRL.setVisibility(View.VISIBLE);

        recordRecycler = (RecyclerView)view.findViewById(R.id.not_start_meeting);
        recordRecycler.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL, false));
        array = new ArrayList<>();
        adapter = new RecordListAdapter(getContext(),array);
        recordRecycler.setAdapter(adapter);
        recordRecycler.setItemAnimator(new DefaultItemAnimator());

        previousRecycle = (RecyclerView)view.findViewById(R.id.previous_meeting);
        previousRecycle.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL, false));
        arrayList = new ArrayList<>();
        previousAdapter = new RecordListAdapter(getContext(),arrayList);
        previousRecycle.setAdapter(previousAdapter);
        previousRecycle.setItemAnimator(new DefaultItemAnimator());

        articleRecycle = (RecyclerView)view.findViewById(R.id.article_recycle);
        articleRecycle.setLayoutManager(new ScrollLinearLayoutManager(getContext()));
        arrayArticle = new ArrayList<>();
        articleAdapter = new ArticleListAdapter(getContext(),arrayArticle);
        articleRecycle.setAdapter(articleAdapter);
        articleRecycle.setItemAnimator(new DefaultItemAnimator());

        layout1 = view.findViewById(R.id.layout01);
        layout2 = view.findViewById(R.id.layout02);

        view.findViewById(R.id.not_start_more).setOnClickListener(this);
        view.findViewById(R.id.previous_more).setOnClickListener(this);
        view.findViewById(R.id.article_more).setOnClickListener(this);
    }

    public class ScrollLinearLayoutManager extends LinearLayoutManager {
        private boolean mCanVerticalScroll = false;

        public ScrollLinearLayoutManager(Context context) {
            super(context);
        }

        @Override
        public boolean canScrollVertically() {
            if (!mCanVerticalScroll){
                return false;
            }else {
                return super.canScrollVertically();
            }
        }

        public void setmCanVerticalScroll(boolean b){
            mCanVerticalScroll = b;
        }
    }
    private void setListener() {
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
        previousAdapter.setOnItemClickListener(new OnItemClick() {
            @Override
            public void onItemClick(View view, int position, int index) {
                if (position < 0){
                    return;
                }
                if(view.getId()==R.id.desc_btn){
                    Intent intent = new Intent(getContext(), MeetingDetailActivity.class);
                    intent.putExtra("meetingID",arrayList.get(position).getId());
                    startActivity(intent);
                }

            }
        });
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
    }

    protected void getData() {
        presenter.getBanner();
        presenter.getMeetingList(1);
        presenter.getMeetingList(2);
        presenter.getWritingsByPage("1","1","5");
    }

    @Override
    public void refreshList(int status,ArrayList<MeetingBean> list) {
        if(status == 1){
            if(list.size()<=0){
                notStartRL.setVisibility(View.GONE);
                layout1.setVisibility(View.GONE);
            }
            array = list;
            adapter.setList(array);
        }else {
            if(list.size()<=0){
                beforeRL.setVisibility(View.GONE);
                layout2.setVisibility(View.GONE);
            }
            arrayList = list;
            previousAdapter.setList(arrayList);
        }

    }

    @Override
    public void reloadBanner(ArrayList<String> list) {
        ArrayList<String> images=list;

        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        //设置轮播时间
        banner.setDelayTime(2000);
        //设置点击事件
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {

            }
        });
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    @Override
    public void refreshArticle(ArrayList<ArticleBean> list) {
        if(list.size()<=0){
            articelRL.setVisibility(View.GONE);
        }
        arrayArticle = list;
        articleAdapter.setList(arrayArticle);
    }

    @Override
    public void initUrl(int position, String url) {

    }


    @Override
    public Context getContext() {
        return getActivity();
    }

    @Override
    public void showDialog() {
        if (myDialog == null) myDialog = MyDialog.showLoadingDialog(getActivity());
    }
    @Override
    public void dismissDialog() {
        if (myDialog != null) myDialog.dismiss();
    }

    @Override
    public void showToast(String txt) {
        MyToast.showToast(getActivity(),txt, Toast.LENGTH_SHORT);
    }
    @Override
    public void finishActivity() {

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.not_start_more){
            Intent intent = new Intent(getContext(), MeetingListActivity.class);
            intent.putExtra("status","1");
            startActivity(intent);
        }
        if(v.getId()==R.id.previous_more){
            Intent intent = new Intent(getContext(), MeetingListActivity.class);
            intent.putExtra("status","2");
            startActivity(intent);
        }
        if(v.getId()==R.id.article_more){
            Intent intent = new Intent(getContext(), MeetingListActivity.class);
            intent.putExtra("status","3");
            startActivity(intent);
        }
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

    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            /**
             注意：
             1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
             2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
             传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
             切记不要胡乱强转！
             */

            //Glide 加载图片简单用法
            Glide.with(context).load(path).into(imageView);
        }


    }
}
