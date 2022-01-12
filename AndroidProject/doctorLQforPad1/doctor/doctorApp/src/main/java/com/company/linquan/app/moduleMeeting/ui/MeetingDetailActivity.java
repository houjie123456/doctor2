package com.company.linquan.app.moduleMeeting.ui;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.company.linquan.app.R;
import com.company.linquan.app.base.BaseActivity;
import com.company.linquan.app.bean.DiscussBean;
import com.company.linquan.app.bean.MeetingBean;
import com.company.linquan.app.config.C;
import com.company.linquan.app.moduleMeeting.MeetingInterface;
import com.company.linquan.app.moduleMeeting.impl.MeetingDetailPresenter;
import com.company.linquan.app.moduleWeb.WebActivity;
import com.company.linquan.app.util.Base64;
import com.company.linquan.app.util.ExitApp;
import com.company.linquan.app.util.MyDialog;
import com.company.linquan.app.util.MyToast;
import com.company.linquan.app.util.ToolSharePerference;
import com.company.linquan.app.view.CommonRecyclerView;
import com.company.linquan.app.view.MyTextView;
import com.company.linquan.app.view.RoundImageView;
import com.tencent.rtmp.ITXLivePlayListener;
import com.tencent.rtmp.ITXLivePushListener;
import com.tencent.rtmp.ITXVodPlayListener;
import com.tencent.rtmp.TXLiveBase;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePlayConfig;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.TXLivePushConfig;
import com.tencent.rtmp.TXLivePusher;
import com.tencent.rtmp.TXVodPlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;

import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

/**
 * Created by YC on 2018/6/4.
 */

public class MeetingDetailActivity extends BaseActivity implements MeetingInterface.MeetingDetailInterface,View.OnClickListener
,ITXLivePlayListener,ITXVodPlayListener,ITXLivePushListener{
    private static final String TAG = MeetingDetailActivity.class.getSimpleName();
    private Dialog myDialog;
    private String id = "",personID = "",type = "",userId = "";
    private MeetingDetailPresenter presenter;
    private ImageView videoIV;
    private RoundImageView photoIV;
    private MyTextView titleTV,numTV,contentTV,nameTV,hospitalTV,departTV,addressTV,positionTV;
    int sw;
    int sh;
    int page = 1;

    private ScrollView scrollView;
    private RecyclerView listView;
    private MyListAdapter adapter;
    private ArrayList<DiscussBean> array;
    private EditText editText;

    private String lon,lat;

    private final static int NAV = 1, SIGN = 2;
    private TXLivePlayer mLivePlayer = null;
    private boolean mIsPlaying, mIsPushIng;
    private TXCloudVideoView mPlayerView;
    private ImageView playBtn, fullBtn;
    private ProgressBar progressBar;

    public static final int ACTIVITY_TYPE_PUBLISH      = 1;
    public static final int ACTIVITY_TYPE_LIVE_PLAY    = 2;
    public static final int ACTIVITY_TYPE_VOD_PLAY     = 3;
    public static final int ACTIVITY_TYPE_LINK_MIC     = 4;
    public static final int ACTIVITY_TYPE_REALTIME_PLAY = 5;

    private int mActivityType;
    private int mPlayType = TXLivePlayer.PLAY_TYPE_LIVE_RTMP;
    private int mCurrentRenderMode;
    private int mCurrentRenderRotation;
    private TXLivePlayConfig mPlayConfig;
    private boolean mHWDecode = false;
    private long mStartPlayTS = 0;

    private MyTextView signBtnTV;
    private String signUrl = "",playUrl = "",pushUrl = "";

    private TXLivePusher mLivePusher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExitApp.getInstance().addActivity(this);		// add Activity 方便退出
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 开启自定义标题栏
        setContentView(R.layout.activity_meeting_detail);
        initData();
        initView();
        setListener();
        getDetail();
        getWindow().addFlags(WindowManager.LayoutParams.
                FLAG_KEEP_SCREEN_ON);
    }

    private void getDetail() {
        presenter.getMeetingDetail(id);
        presenter.getDiscuss(id,page);
    }

    private void initData() {
        if (getIntent() !=null){
            id = getIntent().getStringExtra("id");
            type = getIntent().getStringExtra("index");
            personID = getIntent().getStringExtra("personId");
        }

        userId = ToolSharePerference.getStringData(getContext(), C.fileconfig,C.UserID);
    }

    private void initView(){
        presenter = new MeetingDetailPresenter(this);

        sw = getResources().getDisplayMetrics().widthPixels;
        sh = getResources().getDisplayMetrics().heightPixels;

        photoIV = (RoundImageView) findViewById(R.id.meetingDetail_doctor_photo);
        photoIV.setDrawCircle();
        titleTV = (MyTextView) findViewById(R.id.meetingDetail_title);
        numTV = (MyTextView) findViewById(R.id.meetingDetail_num_date);
        contentTV = (MyTextView) findViewById(R.id.meetingDetail_content);
        nameTV = (MyTextView) findViewById(R.id.meetingDetail_doctor_name);
        hospitalTV = (MyTextView) findViewById(R.id.meetingDetail_doctor_address);
        departTV = (MyTextView) findViewById(R.id.meetingDetail_doctor_room);
        addressTV = (MyTextView) findViewById(R.id.meetDetail_address);
        positionTV = (MyTextView) findViewById(R.id.meetingDetail_doctor_position);

        listView = (CommonRecyclerView)findViewById(R.id.meetingDetail_list);
        listView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));
        array = new ArrayList<>();
        adapter = new MyListAdapter(getContext(),array);
        listView.setAdapter(adapter);
        listView.setItemAnimator(new DefaultItemAnimator());

        findViewById(R.id.meetingDetail_send).setOnClickListener(this);
        editText = (EditText)findViewById(R.id.meetingDetail_edit);

        signBtnTV = (MyTextView) findViewById(R.id.meetingDetail_doctor_sign);
        signBtnTV.setOnClickListener(this);
        findViewById(R.id.meetingDetail_nav).setOnClickListener(this);

        scrollView = (ScrollView)findViewById(R.id.scroll_layout);

        mPlayerView = (TXCloudVideoView)findViewById(R.id.meeting_video_view);
        videoIV = (ImageView)findViewById(R.id.meeting_video_image);
        playBtn = (ImageView)findViewById(R.id.meetingDetail_play);
        playBtn.setOnClickListener(this);
        fullBtn = (ImageView)findViewById(R.id.meetingDetail_full);
        fullBtn.setOnClickListener(this);

        progressBar = (ProgressBar)findViewById(R.id.meetingDetail_play_progress);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.meetingDetail_play:
                //vedioType-类型 1.进行中 2.即将开始 3.已结束
                if ("2".equals(type)) return;
                if (mIsPushIng || mIsPlaying) return;
                //特殊处理（6.0以上）
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED
                        ||
                        ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.RECORD_AUDIO)
                                != PackageManager.PERMISSION_GRANTED ) {
                    // Check Permissions Now
                    // Callback onRequestPermissionsResult interceptado na Activity MainActivity
                    PermissionGen.with(MeetingDetailActivity.this)
                            .addRequestCode(10)
                            .permissions(Manifest.permission.CAMERA
                                        ,Manifest.permission.RECORD_AUDIO)
                            .request();
                    return;
                }

                presenter.getVideoUrl(personID);
                break;

            case R.id.meetingDetail_full:
                break;


            case R.id.meetingDetail_send:
                if (TextUtils.isEmpty(editText.getText())) return;
                presenter.sendDiscuss(id,editText.getText().toString().trim(),"");
                break;

            case R.id.meetingDetail_doctor_sign:
                //vedioType-类型 1.进行中 2.即将开始 3.已结束
                if ("2".equals(type)) {
                    if (TextUtils.isEmpty(signUrl)) return;
                    Intent intent = new Intent(MeetingDetailActivity.this, WebActivity.class);
                    Bundle b = new Bundle();
                    b.putString("url",signUrl);
                    b.putString("title","报名");
                    intent.putExtras(b);
                    startActivityForResult(intent,SIGN);
                }

                if ("1".equals(type)){
                    presenter.signIn(id,lon,lat);
                }

                break;

            case R.id.meetingDetail_nav:
                startNav();
//                startActivityForResult(new Intent(MeetingDetailActivity.this,MapActivity.class),NAV);
                break;
        }
    }

    private void setListener() {
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return false;
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
        presenter.getVideoUrl(personID);
    }

    @PermissionFail(requestCode = 10)
    public void doFailSomething(){
        PermissionGen.with(MeetingDetailActivity.this)
                .addRequestCode(10)
                .permissions(Manifest.permission.CAMERA
                        ,Manifest.permission.RECORD_AUDIO)
                .request();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == NAV && resultCode == RESULT_OK){

        }

        if (requestCode == SIGN && resultCode == 1){

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * recycler 适配器
     */
    private class MyListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        private Context mContext;
        private ArrayList<DiscussBean> mList;
        private OnItemClick onItemClickListener;

        public MyListAdapter(Context context,ArrayList<DiscussBean> list ){
            this.mContext = context;
            this.mList = list;
        }


        public void setList(ArrayList<DiscussBean> list){
            this.mList = list;
            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext)
                    .inflate(R.layout.list_item_discuss,parent,false);
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

        private void initView(MyViewHolder handler, DiscussBean bean){
            if (bean == null)return;
            handler.nameTV.setText(bean.getThinkManName());
            handler.timeTV.setText(bean.getCreateTime());
            handler.contentTV.setText(bean.getThinkText());
            handler.ratingBar.setNumStars(Integer.parseInt(bean.getThinkScore()));
        }
    }

    public interface OnItemClick{
        public void onItemClick(View view, int position);
    }

    /**
     * 静态类
     */
    private static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public MyTextView nameTV;
        public MyTextView contentTV;
        public MyTextView timeTV;
        private RatingBar ratingBar;
        private OnItemClick onItemClick;

        public MyViewHolder(View view, OnItemClick onItemClick){
            super(view);
            this.onItemClick = onItemClick;
            view.setOnClickListener(this);
            nameTV = (MyTextView) view.findViewById(R.id.list_item_name);
            timeTV = (MyTextView) view.findViewById(R.id.list_item_time);
            contentTV = (MyTextView) view.findViewById(R.id.list_item_content);
            ratingBar = (RatingBar)view.findViewById(R.id.list_item_ratingBar);
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
    public void reloadView(MeetingBean bean,String url) {

        lat = bean.getLatitude();
        lon = bean.getLongitude();

        signUrl = bean.getDetailUrl();
        personID = bean.getPersonID();
        int w2 = (720*sw)/720;
        int h2 = (592*sw)/720;
        Glide.with(this).load(bean.getVedioCover()).apply(new RequestOptions().override(w2,h2).centerCrop()).into(videoIV);
        int w = (118*sw)/720;
        Glide.with(this).load(bean.getHeadUrl()).apply(new RequestOptions().override(w,w).centerCrop()).into(photoIV);
        titleTV.setText(Base64.decodeToString(bean.getTitle()));

        String[] ss = bean.getTimePart().split("至");
        numTV.setText(bean.getThinkNumber()+"       时间:"+ss[0]);
        contentTV.setText(Base64.decodeToString(bean.getIntroduction()));
        nameTV.setText(bean.getDoctorName());
        hospitalTV.setText(bean.getHospital());
        departTV.setText(bean.getDepartment());
        addressTV.setText(bean.getAddress());
        positionTV.setText(bean.getDepartment());

        //vedioType-类型 1.进行中 2.即将开始 3.已结束
        if ("1".equals(type)){
            signBtnTV.setText("立即签到");
        }
        if ("2".equals(type)){
            signBtnTV.setText("立即报名");
        }
        if ("3".equals(type)){
            signBtnTV.setVisibility(View.GONE);
        }

        //0.未报名 1.未签到 2.已签到
        String signType = bean.getIsSignType();
        if ("".equals(signType)){
            signBtnTV.setVisibility(View.GONE);
        }

        if ("2".equals(signType)){
            signBtnTV.setText("已签到");
            signBtnTV.setEnabled(false);
        }


    }

    @Override
    public void setListView(ArrayList<DiscussBean> bean) {
        array = bean;
        adapter.setList(array);
    }

    @Override
    public void reloadListView() {
        presenter.getDiscuss(id,page=1);
    }


    @Override
    public void initUrl(String url,String pushUrl) {
        playUrl = url;
        this.pushUrl = pushUrl;
        startCamera();
    }

    private void startCamera(){
        //vedioType-类型 1.进行中 2.即将开始 3.已结束
        if ("1".equals(type) && !userId.equals(personID)){
            mPlayType = ACTIVITY_TYPE_LIVE_PLAY;
            playUrl = "http://26741.liveplay.myqcloud.com/live/26741_bztrhyhbek.flv";
            startPlay(playUrl);
        }

//        if ("1".equals(type) && !userId.equals(personID)){
//            pushUrl = "rtmp://26741.livepush.myqcloud.com/live/26741_bztrhyhbek?bizid=26741&txSecret=ef6ecbf86b90a8cc604050480c7634cc&txTime=5b4a1dff";
//            startPush(pushUrl);
//        }

        if ("3".equals(type)){
            mPlayType = ACTIVITY_TYPE_VOD_PLAY;
//            startPlay();
        }
    }


    private boolean startPlay(String url) {
//        if (!checkPlayUrl(playUrl)) {
//            return false;
//        }

        String sdkver = TXLiveBase.getSDKVersionStr();
        Log.d("liteavsdk", "liteav sdk version is : " + sdkver);

        playBtn.setBackgroundResource(R.drawable.img_meeting_detail_stop);
        videoIV.setVisibility(View.INVISIBLE);
        mLivePlayer = new TXLivePlayer(this);
        mLivePlayer.setPlayerView(mPlayerView);
        mLivePlayer.setPlayListener(this);
        SurfaceView mSurfaceView = (SurfaceView) findViewById(R.id.surface_view);
        mLivePlayer.enableHardwareDecode(true);//这个就有点关键了，开启硬解码
        mLivePlayer.setSurface(mSurfaceView.getHolder().getSurface());//这行代码只能执行在硬解码环境下

        mLivePlayer.setRenderRotation(TXLiveConstants.RENDER_ROTATION_PORTRAIT);//设置图像渲染角度
        mLivePlayer.setRenderMode(TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION);//设置图像平铺模式(官网是这么说的，具体什么意思我也不清楚)
        mLivePlayer.setConfig(new TXLivePlayConfig());//这个TXPlayConfig干脆直接new出来就好

        int result = mLivePlayer.startPlay(url,TXLivePlayer.PLAY_TYPE_LIVE_FLV); // result返回值：0 success;  -1 empty url; -2 invalid url; -3 invalid playType;
        Log.w(TAG,"video result:"+result);

        if (result != 0) {
            playBtn.setBackgroundResource(R.drawable.img_meeting_detail_play);
            videoIV.setVisibility(View.VISIBLE);
            return false;
        }
        mIsPlaying = true;
        mStartPlayTS = System.currentTimeMillis();
        return true;
    }

    private  void stopPlay() {
        playBtn.setBackgroundResource(R.drawable.img_meeting_detail_play);
        videoIV.setVisibility(View.VISIBLE);
        if (mLivePlayer != null) {
            mLivePlayer.stopRecord();
            mLivePlayer.setPlayListener(null);
            mLivePlayer.stopPlay(true);
        }
        mIsPlaying = false;
    }

    private boolean checkPlayUrl(final String playUrl) {
        if (TextUtils.isEmpty(playUrl) || (!playUrl.startsWith("http://") && !playUrl.startsWith("https://") && !playUrl.startsWith("rtmp://")  && !playUrl.startsWith("/"))) {
            Toast.makeText(getApplicationContext(), "播放地址不合法，直播目前仅支持rtmp,flv播放方式!", Toast.LENGTH_SHORT).show();
            return false;
        }

        switch (mActivityType) {
            case ACTIVITY_TYPE_LIVE_PLAY:
            {
                if (playUrl.startsWith("rtmp://")) {
                    mPlayType = TXLivePlayer.PLAY_TYPE_LIVE_RTMP;
                } else if ((playUrl.startsWith("http://") || playUrl.startsWith("https://"))&& playUrl.contains(".flv")) {
                    mPlayType = TXLivePlayer.PLAY_TYPE_LIVE_FLV;
                } else {
                    Toast.makeText(getApplicationContext(), "播放地址不合法，直播目前仅支持rtmp,flv播放方式!", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
            break;
            case ACTIVITY_TYPE_REALTIME_PLAY:
                mPlayType = TXLivePlayer.PLAY_TYPE_LIVE_RTMP_ACC;
                break;
            default:
                Toast.makeText(getApplicationContext(), "播放地址不合法，目前仅支持rtmp,flv播放方式!", Toast.LENGTH_SHORT).show();
                return false;
        }
        return true;
    }

    @Override
    public void onPlayEvent(int event, Bundle bundle) {
        Log.d("onPlayEvent", "event="+event);

        if (event == TXLiveConstants.PLAY_EVT_PLAY_BEGIN) {
            Log.d("AutoMonitor", "PlayFirstRender,cost=" +(System.currentTimeMillis()-mStartPlayTS));
        } else if (event == TXLiveConstants.PLAY_ERR_NET_DISCONNECT || event == TXLiveConstants.PLAY_EVT_PLAY_END) {
            stopPlay();
        } else if (event == TXLiveConstants.PLAY_EVT_PLAY_LOADING){
            progressBar.setProgress((int)(System.currentTimeMillis()-mStartPlayTS));
        } else if (event == TXLiveConstants.PLAY_EVT_RCV_FIRST_I_FRAME) {
        } else if (event == TXLiveConstants.PLAY_EVT_CHANGE_RESOLUTION) {
        } else if (event == TXLiveConstants.PLAY_EVT_CHANGE_ROTATION) {
            return;
        } else if (event == TXLiveConstants.PLAY_EVT_PLAY_PROGRESS) {

        }

        if (event < 0) {
            Log.d(TAG, "error:" +bundle.getString(TXLiveConstants.EVT_DESCRIPTION));
            Toast.makeText(getApplicationContext(), bundle.getString(TXLiveConstants.EVT_DESCRIPTION), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNetStatus(Bundle status) {
        String str = getNetStatusString(status);
        Log.d(TAG, "Current status, CPU:"+status.getString(TXLiveConstants.NET_STATUS_CPU_USAGE)+
                ", RES:"+status.getInt(TXLiveConstants.NET_STATUS_VIDEO_WIDTH)+"*"+status.getInt(TXLiveConstants.NET_STATUS_VIDEO_HEIGHT)+
                ", SPD:"+status.getInt(TXLiveConstants.NET_STATUS_NET_SPEED)+"Kbps"+
                ", FPS:"+status.getInt(TXLiveConstants.NET_STATUS_VIDEO_FPS)+
                ", ARA:"+status.getInt(TXLiveConstants.NET_STATUS_AUDIO_BITRATE)+"Kbps"+
                ", VRA:"+status.getInt(TXLiveConstants.NET_STATUS_VIDEO_BITRATE)+"Kbps");
    }

    //公用打印辅助函数
    protected String getNetStatusString(Bundle status) {
        String str = String.format("%-14s %-14s %-12s\n%-8s %-8s %-8s %-8s\n%-14s %-14s\n%-14s %-14s",
                "CPU:"+status.getString(TXLiveConstants.NET_STATUS_CPU_USAGE),
                "RES:"+status.getInt(TXLiveConstants.NET_STATUS_VIDEO_WIDTH)+"*"+status.getInt(TXLiveConstants.NET_STATUS_VIDEO_HEIGHT),
                "SPD:"+status.getInt(TXLiveConstants.NET_STATUS_NET_SPEED)+"Kbps",
                "JIT:"+status.getInt(TXLiveConstants.NET_STATUS_NET_JITTER),
                "FPS:"+status.getInt(TXLiveConstants.NET_STATUS_VIDEO_FPS),
                "GOP:"+status.getInt(TXLiveConstants.NET_STATUS_VIDEO_GOP)+"s",
                "ARA:"+status.getInt(TXLiveConstants.NET_STATUS_AUDIO_BITRATE)+"Kbps",
                "QUE:"+status.getInt(TXLiveConstants.NET_STATUS_CODEC_CACHE)
                        +"|"+status.getInt(TXLiveConstants.NET_STATUS_CACHE_SIZE)
                        +","+status.getInt(TXLiveConstants.NET_STATUS_VIDEO_CACHE_SIZE)
                        +","+status.getInt(TXLiveConstants.NET_STATUS_V_DEC_CACHE_SIZE)
                        +"|"+status.getInt(TXLiveConstants.NET_STATUS_AV_RECV_INTERVAL)
                        +","+status.getInt(TXLiveConstants.NET_STATUS_AV_PLAY_INTERVAL)
                        +","+String.format("%.1f", status.getFloat(TXLiveConstants.NET_STATUS_AUDIO_PLAY_SPEED)).toString(),
                "VRA:"+status.getInt(TXLiveConstants.NET_STATUS_VIDEO_BITRATE)+"Kbps",
                "SVR:"+status.getString(TXLiveConstants.NET_STATUS_SERVER_IP),
                "AUDIO:"+status.getString(TXLiveConstants.NET_STATUS_AUDIO_INFO));
        return str;
    }

    @Override
    public void onPushEvent(int event, Bundle param) {
        String msg = param.getString(TXLiveConstants.EVT_DESCRIPTION);
        String pushEventLog = "receive event: " + event + ", " + msg;
        Log.d(TAG, pushEventLog);
//        if (mLivePusher != null) {
//            mLivePusher.onLogRecord("[event:" + event + "]" + msg + "\n");
//        }
        //错误还是要明确的报一下
        if (event < 0) {
            Toast.makeText(getApplicationContext(), param.getString(TXLiveConstants.EVT_DESCRIPTION), Toast.LENGTH_SHORT).show();
            if(event == TXLiveConstants.PUSH_ERR_OPEN_CAMERA_FAIL || event == TXLiveConstants.PUSH_ERR_OPEN_MIC_FAIL){
                stopRtmpPublish();
            }
        }

        if (event == TXLiveConstants.PUSH_ERR_NET_DISCONNECT) {
            stopRtmpPublish();
        }
        else if (event == TXLiveConstants.PUSH_WARNING_HW_ACCELERATION_FAIL) {
            Toast.makeText(getApplicationContext(), param.getString(TXLiveConstants.EVT_DESCRIPTION), Toast.LENGTH_SHORT).show();
        }
        else if (event == TXLiveConstants.PUSH_ERR_SCREEN_CAPTURE_UNSURPORT) {
            stopRtmpPublish();
        }
        else if (event == TXLiveConstants.PUSH_ERR_SCREEN_CAPTURE_START_FAILED) {
        } else if (event == TXLiveConstants.PUSH_EVT_CHANGE_RESOLUTION) {
            Log.d(TAG, "change resolution to " + param.getInt(TXLiveConstants.EVT_PARAM2) + ", bitrate to" + param.getInt(TXLiveConstants.EVT_PARAM1));
        } else if (event == TXLiveConstants.PUSH_EVT_CHANGE_BITRATE) {
            Log.d(TAG, "change bitrate to" + param.getInt(TXLiveConstants.EVT_PARAM1));
        } else if (event == TXLiveConstants.PUSH_WARNING_NET_BUSY) {
        } else if (event == TXLiveConstants.PUSH_EVT_START_VIDEO_ENCODER) {
            int encType = param.getInt(TXLiveConstants.EVT_PARAM1);
        }
    }

    @Override
    public void onPlayEvent(TXVodPlayer txVodPlayer, int event, Bundle bundle) {

    }

    @Override
    public void onNetStatus(TXVodPlayer txVodPlayer, Bundle bundle) {

    }

    private void startPush(String url){
        mLivePusher = new TXLivePusher(getContext());
        TXLivePushConfig mLivePushConfig = new TXLivePushConfig();
        SurfaceView mSurfaceView = (SurfaceView) findViewById(R.id.surface_view);
        mSurfaceView.setVisibility(View.INVISIBLE);
        mLivePusher.setConfig(mLivePushConfig);
        mLivePusher.setVideoQuality(3,true,true);
        mLivePusher.setPushListener(this);
        mLivePusher.startPusher(url);
        mLivePusher.startCameraPreview(mPlayerView);
        mIsPushIng = true;
    }
    //结束推流，注意做好清理工作
    public void stopRtmpPublish() {
        mLivePusher.stopCameraPreview(true); //停止摄像头预览
        mLivePusher.stopPusher();            //停止推流
        mLivePusher.setPushListener(null);   //解绑 listener
        mIsPushIng = false;
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mLivePlayer != null) {
            mLivePlayer.stopPlay(true);
            mLivePlayer = null;
        }
        if (mPlayerView != null){
            mPlayerView.onDestroy();
            mPlayerView = null;
        }
        mPlayConfig = null;
    }


    private void startNav(){
        if(isInstallByread("com.baidu.BaiduMap")){
            invokingBD();
        }else if(isInstallByread("com.autonavi.minimap")){
            invokingGD();
        }else{
            Toast.makeText(getContext(), "请安装百度地图或者高德地图客户端", Toast.LENGTH_SHORT).show();
        }
    }


    public void invokingBD(){
        //  com.baidu.BaiduMap这是地图的包名
        //调起百度地图客户端try {
        Intent intent = null;
        try {
            String uri = "intent://map/direction?origin=latlng:"+lat+","+lon+"|name:我的位置&destination=" + addressTV.getText().toString() + "&mode=drivingion=" + "合肥" + "&referer=Autohome|GasStation#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end";
            intent = Intent.getIntent(uri);
            startActivity(intent);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void invokingGD(){
        //  com.autonavi.minimap这是高德地图的包名
        Intent intent = new Intent("android.intent.action.VIEW",android.net.Uri.parse("androidamap://navi?sourceApplication=应用名称&lat="+ "&dev=0"));
        intent.setPackage("com.autonavi.minimap");
        intent.setData(Uri.parse("androidamap://poi?sourceApplication=softname&keywords="+addressTV.getText().toString()));
        startActivity(intent);
    }




    /**
     * 判断是否安装目标应用
     * @param packageName 目标应用安装后的包名
     * @return 是否已安装目标应用
     */
    private boolean isInstallByread(String packageName) {
        return new File("/data/data/" + packageName).exists();
    }
}
