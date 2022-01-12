package com.company.linquan.app.moduleDoc;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.company.linquan.app.R;
import com.company.linquan.app.base.BaseFragment;
import com.company.linquan.app.bean.DocBean;
import com.company.linquan.app.moduleWeb.WebActivity;
import com.company.linquan.app.util.Base64;
import com.company.linquan.app.util.MyDialog;
import com.company.linquan.app.util.MyToast;
import com.company.linquan.app.view.MyTextView;
import com.company.linquan.app.view.RoundImageView;

import java.util.ArrayList;

/**
 * Created by YC on 2018/8/1.
 */

public class DocFragment extends BaseFragment implements DocInterface.DocInterFace{

    private Dialog myDialog;

    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private ArrayList<DocBean> arrayList;
    private MyListAdapter adapter;

    int page = 1;
    int sw;

    private DocPresenter presenter;

    /**
     * 延迟加载
     * 子类必须重写此方法
     */
    @Override
    protected void lazyLoad() {
        if(getActivity() == null){
            return;
        }
        presenter.getListData(page);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_doc, null);
        initHead(view);
        initView(view);
        setListener();
        lazyLoad();
        return view;
    }

    private void initHead(View view) {
        RelativeLayout head = (RelativeLayout) view.findViewById(R.id.layout_head);
        head.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.transparent));
        MyTextView title = (MyTextView) head.findViewById(R.id.head_top_title);
        title.setText("学术");
        ImageView rightIV = (ImageView)head.findViewById(R.id.head_top_image);
        rightIV.setVisibility(View.GONE);
    }

    private void initView(View view){

        sw = getContext().getResources().getDisplayMetrics().widthPixels;

        presenter = new DocPresenter(this);

        refreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.doc_refresh);
        refreshLayout.setColorSchemeColors(ContextCompat.getColor(getActivity(),R.color.base_red_color));
        recyclerView = (RecyclerView)view.findViewById(R.id.doc_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false));
        arrayList = new ArrayList<>();
        adapter = new MyListAdapter(getContext(),arrayList);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    private void setListener() {

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);
                presenter.getListData(page);
            }
        });

        adapter.setOnItemClickListener(new OnItemClick() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(),WebActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("title",arrayList.get(position).getTitle());
                bundle.putString("url",arrayList.get(position).getDetailUrl());
                intent.putExtras(bundle);
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
                        presenter.getListData(page);
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



    /**
     * recycler 适配器
     */
    private class MyListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        private Context mContext;
        private ArrayList<DocBean> mList;
        private OnItemClick onItemClickListener;

        public MyListAdapter(Context context,ArrayList<DocBean> list ){
            this.mContext = context;
            this.mList = list;
        }


        public void setList(ArrayList<DocBean> list){
            this.mList = list;
            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext)
                    .inflate(R.layout.list_item_doc,parent,false);
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

        private void initView(MyViewHolder handler, DocBean bean){
            if (bean == null) return;
            int w = (118*sw)/720;
            Glide.with(mContext).load(bean.getHeadImg()).apply(new RequestOptions().override(w,w).error(R.drawable.img_doctor_temp).centerCrop()).into(handler.photoIV);
            handler.nameTV.setText(bean.getReleaseMan());
            handler.timeTV.setText(bean.getReleaseTime());
            handler.titleTV.setText(bean.getTitle());
//            handler.contentTV.setText(bean.getSubtitle());
            handler.contentTV.setText(Base64.decodeToString(bean.getSubtitle()));
//            handler.contentTV.setText(Base64.decodeToString(bean.getContent()));
        }
    }

    public interface OnItemClick{
        public void onItemClick(View view, int position);
    }

    /**
     * 静态类
     */
    private static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private RoundImageView photoIV;
        private MyTextView nameTV;
        private MyTextView titleTV;
        private MyTextView contentTV;
        private MyTextView timeTV;
        private OnItemClick onItemClick;

        public MyViewHolder(View view, OnItemClick onItemClick){
            super(view);
            this.onItemClick = onItemClick;
            view.setOnClickListener(this);
            photoIV = (RoundImageView)view.findViewById(R.id.list_item_photo);
            photoIV.setDrawCircle();
            nameTV = (MyTextView)view.findViewById(R.id.list_item_name);
            titleTV = (MyTextView)view.findViewById(R.id.list_item_title);
            contentTV = (MyTextView)view.findViewById(R.id.list_item_content);
            timeTV = (MyTextView)view.findViewById(R.id.list_item_time);
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
    public void reloadList(ArrayList<DocBean> list) {
        if (page == 1){
            refreshLayout.setRefreshing(false);
            if (adapter == null) return;
            arrayList = list;
            adapter.setList(arrayList);
        }
        if (page>1) {
            for (DocBean b :
                    list) {
                arrayList.add(b);
            }
            if (adapter == null) return;
            adapter.setList(arrayList);
        }
    }

}
