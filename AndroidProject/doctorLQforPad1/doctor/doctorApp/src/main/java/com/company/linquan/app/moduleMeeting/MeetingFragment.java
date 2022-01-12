package com.company.linquan.app.moduleMeeting;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.company.linquan.app.R;
import com.company.linquan.app.base.BaseFragment;
import com.company.linquan.app.bean.MeetingBean;
import com.company.linquan.app.moduleMeeting.ui.MeetingDetailWebActivity;
import com.company.linquan.app.util.Base64;
import com.company.linquan.app.util.MyDialog;
import com.company.linquan.app.util.MyToast;
import com.company.linquan.app.util.ToolUtil;
import com.company.linquan.app.util.recycler.CardAdapterHelper;
import com.company.linquan.app.util.recycler.CardScaleHelper;
import com.company.linquan.app.view.MyTextView;
import com.company.linquan.app.view.RoundImageView;
import com.company.linquan.app.view.SearchBar;
import com.company.linquan.app.zxing.activity.CameraActivity;
import com.tencent.rtmp.ITXLivePlayListener;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;

import java.util.ArrayList;

import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

/**
 * Created by YC on 2018/1/9.
 */

public class MeetingFragment extends BaseFragment implements MeetingInterface.ViewInterface
,View.OnClickListener,ITXLivePlayListener{

    private Dialog myDialog;

    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private ArrayList<MeetingBean> arrayList;
    private MyListAdapter adapter;
    private CardScaleHelper mCardScaleHelper = null;

    private SearchBar searchBar;
    private LinearLayout layout1, layout2, layout3;
    private MyTextView txtTV1, txtTV2, txtTV3;
    private LinearLayout iv1, iv2, iv3;
    int index = 1;
    int page = 1;
    int sw;
    int sh;
    private TXLivePlayer mLivePlayer = null;
    private boolean mIsPlaying;
    private TXCloudVideoView mPlayerView;

    private MeetingPresenter presenter;
    private int clickIndex = -1;
    private final static int CAMERA = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_meeting, null);
        initHead(view);
        initView(view);
        setListener();
        lazyLoad();
        return view;
    }

    private void initHead(View view) {
        RelativeLayout head = (RelativeLayout) view.findViewById(R.id.layout_head);
        MyTextView title = (MyTextView) head.findViewById(R.id.head_top_title);
        title.setText("直播会议");
        ImageView rightIV = (ImageView)head.findViewById(R.id.head_top_image);
        rightIV.setVisibility(View.GONE);
        MyTextView menuTV = (MyTextView) head.findViewById(R.id.head_top_right_menu);
        menuTV.setText("签到");
        menuTV.setVisibility(View.VISIBLE);
        menuTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //特殊处理（6.0以上）
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    // Check Permissions Now
                    // Callback onRequestPermissionsResult interceptado na Activity MainActivity
                    PermissionGen.with(MeetingFragment.this)
                            .addRequestCode(11)
                            .permissions(Manifest.permission.CAMERA)
                            .request();
                    return;
                }

                startActivityForResult(new Intent(getActivity(), CameraActivity.class), CAMERA);
            }
        });
    }

    @Override
    public void sureBtnClick() {
        super.sureBtnClick();
    }

    @Override
    public void cancelBtnClick() {
        super.cancelBtnClick();
    }

    @Override
    protected void lazyLoad() {
        if(getActivity() == null){
            return;
        }
        presenter.getMeetingList(page,index);
    }

    private void initView(View view){

        sw = getContext().getResources().getDisplayMetrics().widthPixels;
        sh = getContext().getResources().getDisplayMetrics().heightPixels;

        presenter = new MeetingPresenter(this);

        searchBar = (SearchBar) view.findViewById(R.id.meeting_search);
        searchBar.setHint("请输入您要搜索的内容");
        searchBar.setBackgroundResource(R.drawable.shape_meeting_search_style);

        layout1 = (LinearLayout) view.findViewById(R.id.main_head_1_layout);
        layout2 = (LinearLayout) view.findViewById(R.id.main_head_2_layout);
        layout3 = (LinearLayout) view.findViewById(R.id.main_head_3_layout);

        txtTV1 = (MyTextView) view.findViewById(R.id.main_head_1_txt);
        txtTV2 = (MyTextView) view.findViewById(R.id.main_head_2_txt);
        txtTV3 = (MyTextView) view.findViewById(R.id.main_head_3_txt);

        iv1 = (LinearLayout) view.findViewById(R.id.main_head_1_image);
        iv2 = (LinearLayout) view.findViewById(R.id.main_head_2_image);
        iv3 = (LinearLayout) view.findViewById(R.id.main_head_3_image);

        setHead(index);

        refreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.meeting_refresh);
        refreshLayout.setColorSchemeColors(ContextCompat.getColor(getActivity(),R.color.base_red_color));
        recyclerView = (RecyclerView)view.findViewById(R.id.meeting_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false));
        arrayList = new ArrayList<>();
        adapter = new MyListAdapter(getContext(),arrayList);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        // mRecyclerView绑定scale效果
        mCardScaleHelper = new CardScaleHelper();
        mCardScaleHelper.attachToRecyclerView(recyclerView);

    }

    private void setListener() {

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);
                presenter.getMeetingList(page,index);
            }
        });

        adapter.setOnItemClickListener(new OnItemClick() {
            @Override
            public void onItemClick(View view, int position) {
//                if (view.getId() == R.id.list_video_image){
//                    presenter.getVideoUrl(arrayList.get(position).getPersonID(),position);
//                    return;
//                }
                clickIndex = position;
                //特殊处理（6.0以上）
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED
                        ||
                        ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.RECORD_AUDIO)
                                != PackageManager.PERMISSION_GRANTED) {
                    // Check Permissions Now
                    // Callback onRequestPermissionsResult interceptado na Activity MainActivity
                    PermissionGen.with(MeetingFragment.this)
                            .addRequestCode(10)
                            .permissions(Manifest.permission.CAMERA
                                    , Manifest.permission.RECORD_AUDIO)
                            .request();
                    return;
                }

                Intent intent = new Intent(getActivity(),MeetingDetailWebActivity.class);
                intent.putExtra("index",arrayList.get(position).getVedioType());
                intent.putExtra("id",arrayList.get(position).getId());
                intent.putExtra("personId",arrayList.get(position).getPersonID());
                intent.putExtra("url",arrayList.get(position).getVedioCover2());
                startActivity(intent);
            }
        });

        layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = 0;
                setHead(index);
                presenter.getMeetingList(page = 1,index);
            }
        });

        layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = 1;
                setHead(index);
                presenter.getMeetingList(page = 1,index);
            }
        });

        layout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = 2;
                setHead(2);
                presenter.getMeetingList(page = 1,index);
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
                        presenter.getMeetingList(page,index);
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
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @PermissionSuccess(requestCode = 10)
    public void doSomething(){
        Intent intent = new Intent(getActivity(),MeetingDetailWebActivity.class);
        intent.putExtra("index",arrayList.get(clickIndex).getVedioType());
        intent.putExtra("id",arrayList.get(clickIndex).getId());
        intent.putExtra("personId",arrayList.get(clickIndex).getPersonID());
        intent.putExtra("url",arrayList.get(clickIndex).getVedioCover());
        getActivity().startActivity(intent);
    }

    @PermissionFail(requestCode = 10)
    public void doFailSomething(){
        PermissionGen.with(getActivity())
                .addRequestCode(10)
                .permissions(Manifest.permission.CAMERA
                        ,Manifest.permission.RECORD_AUDIO)
                .request();
    }


    private void setHead(int index){
        txtTV1.setTextColor(ContextCompat.getColor(getContext(),R.color.meeting_color));
        txtTV2.setTextColor(ContextCompat.getColor(getContext(),R.color.meeting_color));
        txtTV3.setTextColor(ContextCompat.getColor(getContext(),R.color.meeting_color));
        iv1.setVisibility(View.GONE);
        iv2.setVisibility(View.GONE);
        iv3.setVisibility(View.GONE);

        switch (index) {
            case 0:
                txtTV1.setTextColor(ContextCompat.getColor(getContext(),R.color.meeting_click_color));
                iv1.setVisibility(View.VISIBLE);
                break;
            case 1:
                txtTV2.setTextColor(ContextCompat.getColor(getContext(),R.color.meeting_click_color));
                iv2.setVisibility(View.VISIBLE);
                break;
            case 2:
                txtTV3.setTextColor(ContextCompat.getColor(getContext(),R.color.meeting_click_color));
                iv3.setVisibility(View.VISIBLE);
                break;

            default:
                break;
        }
    }

    /**
     * recycler 适配器
     */
    private class MyListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        private Context mContext;
        private ArrayList<MeetingBean> mList;
        private OnItemClick onItemClickListener;
        private CardAdapterHelper mCardAdapterHelper = new CardAdapterHelper();

        public MyListAdapter(Context context,ArrayList<MeetingBean> list ){
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
                    .inflate(R.layout.list_item_meeting,parent,false);
            mCardAdapterHelper.onCreateViewHolder(parent, view);
            return new MyViewHolder(view,onItemClickListener);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            mCardAdapterHelper.onBindViewHolder(holder.itemView, position, getItemCount());
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

        private void initView(MyViewHolder handler, MeetingBean bean){
            if (bean == null) return;
            int w = (118*sw)/720;
            Glide.with(mContext).load(bean.getHeadUrl()).apply(new RequestOptions().override(w,w).centerCrop()).into(handler.photoIV);

            int ww = mCardAdapterHelper.getWith();
            ViewGroup.LayoutParams params = handler.videoImage.getLayoutParams();
            params.width = ww;
            params.height = ww - ToolUtil.dp2px(mContext,28);
            handler.videoImage.setLayoutParams(params);
            Glide.with(mContext).load(bean.getVedioCover()).apply(new RequestOptions().centerCrop()).into(handler.videoImage);

            handler.nameTV.setText(bean.getDoctorName());
            handler.positionTV.setText(bean.getDepartment());
            String[] ss = bean.getTimePart().split("至");
            handler.timeTV.setText(ss[0]);
            handler.titleTV.setText(Base64.decodeToString(bean.getTitle()));
            handler.contentTV.setText(Base64.decodeToString(bean.getIntroduction()));
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
        private ImageView videoImage;
        private MyTextView nameTV;
        private MyTextView positionTV;
        private MyTextView titleTV;
        private MyTextView contentTV;
        private MyTextView timeTV;
        private TXCloudVideoView videoView;
        private OnItemClick onItemClick;

        public MyViewHolder(View view, OnItemClick onItemClick){
            super(view);
            this.onItemClick = onItemClick;
            view.setOnClickListener(this);
            videoView = (TXCloudVideoView) view.findViewById(R.id.list_video_view);
            photoIV = (RoundImageView)view.findViewById(R.id.list_item_photo);
            photoIV.setDrawCircle();
            videoImage = (ImageView)view.findViewById(R.id.list_video_image);
            nameTV = (MyTextView)view.findViewById(R.id.list_item_name);
            positionTV = (MyTextView)view.findViewById(R.id.list_item_position);
            titleTV = (MyTextView)view.findViewById(R.id.list_item_title);
            contentTV = (MyTextView)view.findViewById(R.id.list_item_content);
            timeTV = (MyTextView)view.findViewById(R.id.list_item_time);
            videoImage.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(onItemClick != null){
                onItemClick.onItemClick(view,getLayoutPosition());
            }
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
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
    public void refreshList(ArrayList<MeetingBean> list) {
        refreshLayout.setRefreshing(false);
        if (adapter == null) return;
        arrayList = list;
        adapter.setList(arrayList);
    }

    @Override
    public void initUrl(int position, String url) {

    }

    @Override
    public void onPlayEvent(int i, Bundle bundle) {

    }

    @Override
    public void onNetStatus(Bundle bundle) {

    }
}
