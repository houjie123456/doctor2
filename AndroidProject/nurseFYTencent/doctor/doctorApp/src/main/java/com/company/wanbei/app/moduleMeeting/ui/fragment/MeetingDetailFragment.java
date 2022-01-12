//package com.company.wanbei.app.moduleMeeting.ui.fragment;
//
//import android.app.Dialog;
//import android.content.Context;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.EditText;
//import android.widget.RatingBar;
//import android.widget.Toast;
//
//import androidx.recyclerview.widget.DefaultItemAnimator;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.request.RequestOptions;
//import com.company.wanbei.app.base.BaseFragment;
//import com.company.wanbei.app.bean.DiscussBean;
//import com.company.wanbei.app.bean.MeetingBean;
//import com.company.wanbei.app.moduleMeeting.MeetingInterface;
//import com.company.wanbei.app.moduleMeeting.impl.MeetingDetailFragmentPresenterImp;
//import com.tencent.qcloud.tuikit.tuichat.fromApp.util.Base64;
//import com.tencent.qcloud.tuikit.tuichat.fromApp.util.MyDialog;
//import com.tencent.qcloud.tuikit.tuichat.fromApp.util.MyToast;
//import com.company.wanbei.app.view.CommonRecyclerView;
//import com.tencent.qcloud.tuikit.tuichat.fromApp.view.MyTextView;
//import com.tencent.qcloud.tuikit.tuichat.fromApp.view.RoundImageView;
//import com.company.wanbei.app.R;
//
//import java.io.File;
//import java.net.URISyntaxException;
//import java.util.ArrayList;
//
////import androidx.recyclerview.widget.DefaultItemAnimator;
////import androidx.recyclerview.widget.LinearLayoutManager;
////import androidx.recyclerview.widget.RecyclerView;
//
///**
// * Created by YC on 2018/7/25.
// */
//
//public class MeetingDetailFragment extends BaseFragment implements MeetingInterface.MeetingDetailFragmentInterface
//,View.OnClickListener{
//    /**
//     * 延迟加载
//     * 子类必须重写此方法
//     */
//    @Override
//    protected void lazyLoad() {
//        if (getActivity() == null) return;
//        presenter.getMeetingDetail(id);
//        presenter.getDiscuss(id,page = 1);
//    }
//    private Dialog myDialog;
//    private String id = "";
//
//    private RoundImageView photoIV;
//    private MyTextView titleTV,numTV,contentTV,nameTV,hospitalTV,departTV,addressTV,positionTV;
//    int sw;
//    int sh;
//    int page = 1;
//
//    private CommonRecyclerView listView;
//    private MyListAdapter adapter;
//    private ArrayList<DiscussBean> array;
//    private EditText editText;
//
//    private String lon,lat;
//
//    private MeetingDetailFragmentPresenterImp presenter;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_meeting_detail, null);
//        initData();
//        initView(view);
//        setListener();
//        lazyLoad();
//        return view;
//    }
//
//    private void initData() {
//        id = getActivity().getIntent().getStringExtra("id");
//    }
//
//    private void initView(View view) {
//
//        presenter = new MeetingDetailFragmentPresenterImp(this);
//
//        sw = getResources().getDisplayMetrics().widthPixels;
//        sh = getResources().getDisplayMetrics().heightPixels;
//
//        photoIV = (RoundImageView) view.findViewById(R.id.meetingDetail_doctor_photo);
//        photoIV.setDrawCircle();
//        titleTV = (MyTextView) view.findViewById(R.id.meetingDetail_title);
//        numTV = (MyTextView) view.findViewById(R.id.meetingDetail_num_date);
//        contentTV = (MyTextView) view.findViewById(R.id.meetingDetail_content);
//        nameTV = (MyTextView) view.findViewById(R.id.meetingDetail_doctor_name);
//        hospitalTV = (MyTextView) view.findViewById(R.id.meetingDetail_doctor_address);
//        departTV = (MyTextView) view.findViewById(R.id.meetingDetail_doctor_room);
//        addressTV = (MyTextView) view.findViewById(R.id.meetDetail_address);
//        positionTV = (MyTextView) view.findViewById(R.id.meetingDetail_doctor_position);
//
//        listView = (CommonRecyclerView) view.findViewById(R.id.meetingDetail_list);
//        listView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
//        array = new ArrayList<>();
//        adapter = new MyListAdapter(getContext(),array);
//        listView.setAdapter(adapter);
//        listView.setItemAnimator(new DefaultItemAnimator());
//
//        view.findViewById(R.id.meetingDetail_send).setOnClickListener(this);
//        editText = (EditText)view.findViewById(R.id.meetingDetail_edit);
//        view.findViewById(R.id.meetingDetail_nav).setOnClickListener(this);
//    }
//
//    private void setListener() {
//        listView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            //用来标记是否正在向最后一个滑动，既是否向下滑动
//            boolean isSlidingToLast = false;
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//
//                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
//                // 当不滚动时
//                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//                    //获取最后一个完全显示的ItemPosition
//                    int lastVisiblePositions = manager.findLastVisibleItemPosition();
//                    int totalItemCount = manager.getItemCount();
//                    // 判断是否滚动到底部
//                    if (lastVisiblePositions >= (totalItemCount -2) && isSlidingToLast) {
//                        //加载更多功能的代码
//                        page = page + 1;
//                        presenter.getDiscuss(id,page);
//                    }
//                }
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                //dx用来判断横向滑动方向，dy用来判断纵向滑动方向
//                if(dy > 0){
//                    //大于0表示，正在向下滚动
//                    isSlidingToLast = true;
//                }else{
//                    //小于等于0 表示停止或向上滚动
//                    isSlidingToLast = false;
//                }
//            }
//        });
//    }
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()){
//            case R.id.meetingDetail_send:
//                if (TextUtils.isEmpty(editText.getText())) return;
//                presenter.sendDiscuss(id,editText.getText().toString().trim(),"");
//                break;
//            case R.id.meetingDetail_nav:
//                startNav();
//                break;
//        }
//    }
//
//
//    private void startNav(){
//        if(isInstallByread("com.baidu.BaiduMap")){
//            invokingBD();
//        }else if(isInstallByread("com.autonavi.minimap")){
//            invokingGD();
//        }else{
//            Toast.makeText(getContext(), "请安装百度地图或者高德地图客户端", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//
//    /**
//     * 判断是否安装目标应用
//     * @param packageName 目标应用安装后的包名
//     * @return 是否已安装目标应用
//     */
//    private boolean isInstallByread(String packageName) {
//        return new File("/data/data/" + packageName).exists();
//    }
//
//
//    public void invokingBD(){
//        //  com.baidu.BaiduMap这是地图的包名
//        //调起百度地图客户端try {
//        Intent intent = null;
//        try {
//            String uri = "intent://map/direction?origin=latlng:"+lat+","+lon+"|name:我的位置&destination=" + addressTV.getText().toString() + "&mode=drivingion=" + "合肥" + "&referer=Autohome|GasStation#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end";
//            intent = Intent.getIntent(uri);
//            startActivity(intent);
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void invokingGD(){
//        //  com.autonavi.minimap这是高德地图的包名
//        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("androidamap://navi?sourceApplication=应用名称&lat="+ "&dev=0"));
//        intent.setPackage("com.autonavi.minimap");
//        intent.setData(Uri.parse("androidamap://poi?sourceApplication=softname&keywords="+addressTV.getText().toString()));
//        startActivity(intent);
//    }
//
//
//    /**
//     * recycler 适配器
//     */
//    private class MyListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
//
//        private Context mContext;
//        private ArrayList<DiscussBean> mList;
//        private OnItemClick onItemClickListener;
//
//        public MyListAdapter(Context context,ArrayList<DiscussBean> list ){
//            this.mContext = context;
//            this.mList = list;
//        }
//
//
//        public void setList(ArrayList<DiscussBean> list){
//            this.mList = list;
//            notifyDataSetChanged();
//        }
//
//        @Override
//        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(mContext)
//                    .inflate(R.layout.list_item_discuss,parent,false);
//            return new MyViewHolder(view,onItemClickListener);
//        }
//
//        @Override
//        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//            if(holder instanceof MyViewHolder){
//                initView((MyViewHolder) holder, mList.get(position));
//            }
//        }
//
//        @Override
//        public int getItemCount() {
//            return mList.size();
//        }
//
//        private void setOnItemClickListener(OnItemClick listener){
//            onItemClickListener = listener;
//        }
//
//        private void initView(MyViewHolder handler, DiscussBean bean){
//            if (bean == null)return;
//            handler.nameTV.setText(bean.getThinkManName());
//            handler.timeTV.setText(bean.getCreateTime());
//            handler.contentTV.setText(bean.getThinkText());
//            handler.ratingBar.setNumStars(Integer.parseInt(bean.getThinkScore()));
//        }
//    }
//
//    public interface OnItemClick{
//        public void onItemClick(View view, int position);
//    }
//
//    /**
//     * 静态类
//     */
//    private static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
//        public MyTextView nameTV;
//        public MyTextView contentTV;
//        public MyTextView timeTV;
//        private RatingBar ratingBar;
//        private OnItemClick onItemClick;
//
//        public MyViewHolder(View view, OnItemClick onItemClick){
//            super(view);
//            this.onItemClick = onItemClick;
//            view.setOnClickListener(this);
//            nameTV = (MyTextView) view.findViewById(R.id.list_item_name);
//            timeTV = (MyTextView) view.findViewById(R.id.list_item_time);
//            contentTV = (MyTextView) view.findViewById(R.id.list_item_content);
//            ratingBar = (RatingBar)view.findViewById(R.id.list_item_ratingBar);
//        }
//
//        @Override
//        public void onClick(View view) {
//            if(onItemClick != null){
//                onItemClick.onItemClick(view,getLayoutPosition());
//            }
//        }
//    }
//
//    @Override
//    public void finishActivity() {
//    }
//
//    @Override
//    public Context getContext() {
//        return getActivity();
//    }
//
//    @Override
//    public void showDialog() {
//        if (myDialog == null) myDialog = MyDialog.showLoadingDialog(getActivity());
//    }
//    @Override
//    public void dismissDialog() {
//        if (myDialog != null) myDialog.dismiss();
//    }
//
//    @Override
//    public void showToast(String txt) {
//        MyToast.showToast(getActivity(),txt, Toast.LENGTH_SHORT);
//    }
//
//    @Override
//    public void reloadView(MeetingBean bean) {
//        lat = bean.getLatitude();
//        lon = bean.getLongitude();
//        int w = (118*sw)/720;
//        Glide.with(this).load(bean.getHeadUrl()).apply(new RequestOptions().override(w,w).error(R.drawable.img_doctor_temp).centerCrop()).into(photoIV);
//        titleTV.setText(Base64.decodeToString(bean.getTitle()));
//
//        String[] ss = bean.getTimePart().split("至");
//        numTV.setText(bean.getThinkNumber()+"       时间:"+ss[0]);
//        contentTV.setText(Base64.decodeToString(bean.getIntroduction()));
//        nameTV.setText(bean.getDoctorName());
//        hospitalTV.setText(bean.getHospital());
//        departTV.setText(bean.getDepartment());
//        addressTV.setText(bean.getAddress());
//        positionTV.setText(bean.getDepartment());
//    }
//
//    @Override
//    public void setListView(ArrayList<DiscussBean> bean) {
//        if (page == 1){
//            array = bean;
//            adapter.setList(array);
//        }
//
//        if (page > 1){
//            for (DiscussBean b:
//                 bean) {
//                array.add(b);
//            }
//            adapter.setList(array);
//        }
//    }
//
//    @Override
//    public void reloadListView() {
//        editText.setText("");
//        presenter.getDiscuss(id,page=1);
//    }
//}
