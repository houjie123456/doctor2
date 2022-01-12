package com.company.linquan.app.moduleMeeting.ui;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.company.linquan.app.R;
import com.company.linquan.app.base.BaseFragmentActivity;
import com.company.linquan.app.config.C;
import com.company.linquan.app.moduleMeeting.MeetingInterface;
import com.company.linquan.app.moduleMeeting.impl.MeetingDetailWebPresenterImp;
import com.company.linquan.app.moduleMeeting.ui.fragment.MeetingDetailFragment;
import com.company.linquan.app.moduleMeeting.ui.fragment.MeetingWebFragment;
import com.company.linquan.app.util.ExitApp;
import com.company.linquan.app.util.MyDialog;
import com.company.linquan.app.util.MyToast;
import com.company.linquan.app.util.ToolSharePerference;
import com.company.linquan.app.view.MyTextView;
import com.tencent.rtmp.ITXLivePlayListener;
import com.tencent.rtmp.ITXLivePushListener;
import com.tencent.rtmp.TXLiveBase;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePlayConfig;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.TXLivePushConfig;
import com.tencent.rtmp.TXLivePusher;
import com.tencent.rtmp.ui.TXCloudVideoView;

import java.util.ArrayList;

import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

import static com.tencent.rtmp.TXLiveConstants.VIDEO_QUALITY_SUPER_DEFINITION;

/**
 * Created by YC on 2018/7/25.
 */

public class MeetingDetailWebActivity extends BaseFragmentActivity implements MeetingInterface.MeetingDetailWebInterface
,View.OnClickListener,ITXLivePlayListener ,ITXLivePushListener {
    private Dialog myDialog;
    private String id = "",personID = "",type = "",userId = "",url = "";
    private ImageView videoIV;
    int sw;
    int sh;
    private TXLivePlayer mLivePlayer = null;
    private boolean mIsPlaying, mIsPushIng;
    private TXCloudVideoView mPlayerView;
    private ImageView playBtn, fullBtn;
    private ProgressBar progressBar;

    private String signUrl = "",playUrl = "",pushUrl = "";
    private long mStartPlayTS = 0;

    private MeetingDetailWebPresenterImp presenter;

    private ArrayList<Fragment> arrayList = new ArrayList<>();
    private ViewPager vp;
    private MyTextView txtTV1, txtTV2;
    private int index = 0;
    private TXLivePusher mLivePusher;
    private boolean isFull;
    private SurfaceView mSurfaceView;
    private ImageView playBtnIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExitApp.getInstance().addActivity(this);		// add Activity 方便退出
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 开启自定义标题栏
        setContentView(R.layout.activity_meeting_detail_web);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        initData();
        initHead();
        initView();
        initPager();
    }

    private void initData() {
        if (getIntent() !=null){
            id = getIntent().getStringExtra("id");
            type = getIntent().getStringExtra("index");
            personID = getIntent().getStringExtra("personId");
            url = getIntent().getStringExtra("url");
        }

        userId = ToolSharePerference.getStringData(getContext(), C.fileconfig,C.UserID);
    }

    private void initHead() {
    }

    private void initView(){

        presenter = new MeetingDetailWebPresenterImp(this);

        sw = getResources().getDisplayMetrics().widthPixels;
        sh = getResources().getDisplayMetrics().heightPixels;
        int h2 = (420*sw)/720;

        mPlayerView = (TXCloudVideoView)findViewById(R.id.meeting_video_view);
        videoIV = (ImageView)findViewById(R.id.meeting_video_image);
        playBtn = (ImageView)findViewById(R.id.meetingDetail_play);
        playBtn.setOnClickListener(this);
        fullBtn = (ImageView)findViewById(R.id.meetingDetail_full);
        fullBtn.setOnClickListener(this);
        findViewById(R.id.meetingDetail_back).setOnClickListener(this);


        videoIV.setVisibility(View.VISIBLE);
        ViewGroup.LayoutParams params = videoIV.getLayoutParams();
        params.height = h2;
        videoIV.setLayoutParams(params);
        Glide.with(this).load(url).apply(new RequestOptions().error(R.drawable.img_meeting_temp).centerCrop()).into(videoIV);

        ViewGroup.LayoutParams pa = mPlayerView.getLayoutParams();
        pa.height = h2;
        mPlayerView.setLayoutParams(pa);

        mSurfaceView = (SurfaceView) findViewById(R.id.surface_view);
        ViewGroup.LayoutParams par = mSurfaceView.getLayoutParams();
        par.height = h2;
        mSurfaceView.setLayoutParams(par);

        progressBar = (ProgressBar)findViewById(R.id.meetingDetail_play_progress);

        vp = (ViewPager)findViewById(R.id.meeting_page);
        txtTV1 = (MyTextView)findViewById(R.id.meeting_mid_txt1);
        txtTV1.setOnClickListener(this);
        txtTV2 = (MyTextView)findViewById(R.id.meeting_mid_txt2);
        txtTV2.setOnClickListener(this);

        playBtnIV = (ImageView)findViewById(R.id.meetingDetail_play_btn);
        playBtnIV.setOnClickListener(this);
        setHead(index);
    }

    private void initPager() {
        arrayList = new ArrayList<>();
        Fragment p1 = new MeetingWebFragment();
        Fragment p2 = new MeetingDetailFragment();
        arrayList.add(p1);
        arrayList.add(p2);
        vp.setAdapter(new MyAdapter(getSupportFragmentManager()));
        vp.setCurrentItem(index);
        vp.setOffscreenPageLimit(1);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.meetingDetail_back:
                finish();
                break;
            case R.id.meetingDetail_play_btn:
                //vedioType-类型 1.进行中 2.即将开始 3.已结束
                if ("2".equals(type)) return;
                if (mIsPlaying) {
                    stopPlay();
                    return;
                }
                if (mIsPushIng) {
                    stopRtmpPublish();
                    return;
                }
                //特殊处理（6.0以上）
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED
                        ||
                        ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.RECORD_AUDIO)
                                != PackageManager.PERMISSION_GRANTED) {
                    // Check Permissions Now
                    // Callback onRequestPermissionsResult interceptado na Activity MainActivity
                    PermissionGen.with(MeetingDetailWebActivity.this)
                            .addRequestCode(10)
                            .permissions(Manifest.permission.CAMERA
                                    , Manifest.permission.RECORD_AUDIO)
                            .request();
                    return;
                }

                presenter.getVideoUrl(id);
                break;
            case R.id.meetingDetail_play:
                playOrPause();
                break;

            case R.id.meetingDetail_full:
                fullScreen();
                break;

            case R.id.meeting_mid_txt1:
                vp.setCurrentItem(0);
                setHead(index = 0);
                break;

            case R.id.meeting_mid_txt2:
                vp.setCurrentItem(1);
                setHead(index = 1);
                break;
        }
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
        PermissionGen.with(MeetingDetailWebActivity.this)
                .addRequestCode(10)
                .permissions(Manifest.permission.CAMERA
                        ,Manifest.permission.RECORD_AUDIO)
                .request();
    }


    private void setHead(int index){
        txtTV1.setTextColor(ContextCompat.getColor(this,R.color.home_color));
        txtTV2.setTextColor(ContextCompat.getColor(this,R.color.home_color));
        switch (index) {
            case 0:
                txtTV1.setTextColor(ContextCompat.getColor(this,R.color.home_click_color));
                break;
            case 1:
                txtTV2.setTextColor(ContextCompat.getColor(this,R.color.home_click_color));
                break;
            default:
                break;
        }
    }

    class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int arg0) {
            // TODO Auto-generated method stub
            return arrayList.get(arg0);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return arrayList.size();
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
    public void initUrl(String url,String pushPath) {
        playUrl = url;
        pushUrl = pushPath;
        startCamera();
    }
    private void startCamera(){
        //vedioType-类型 1.进行中 2.即将开始 3.已结束
        if ("1".equals(type) && !userId.equals(personID)){
//            mPlayType = ACTIVITY_TYPE_LIVE_PLAY;
//            playUrl = "http://26741.liveplay.myqcloud.com/live/26741_bztrhyhbek.flv";
            startPlay(playUrl);
        }

        if ("1".equals(type) && userId.equals(personID)){
//            pushUrl = "rtmp://26741.livepush.myqcloud.com/live/26741_bztrhyhbek?bizid=26741&txSecret=ef6ecbf86b90a8cc604050480c7634cc&txTime=5b4a1dff";
            startPush(pushUrl);
        }

        if ("3".equals(type)){
//            mPlayType = ACTIVITY_TYPE_VOD_PLAY;
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
        playBtnIV.setVisibility(View.INVISIBLE);
        videoIV.setVisibility(View.INVISIBLE);
        mLivePlayer = new TXLivePlayer(this);
        mLivePlayer.setPlayerView(mPlayerView);
        mLivePlayer.setPlayListener(this);
        mLivePlayer.enableHardwareDecode(true);//这个就有点关键了，开启硬解码
        mLivePlayer.setSurface(mSurfaceView.getHolder().getSurface());//这行代码只能执行在硬解码环境下
        mLivePlayer.setRenderRotation(TXLiveConstants.RENDER_ROTATION_PORTRAIT);//设置图像渲染角度
        mLivePlayer.setRenderMode(TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION);//设置图像平铺模式(官网是这么说的，具体什么意思我也不清楚)
        mLivePlayer.setConfig(new TXLivePlayConfig());//这个TXPlayConfig干脆直接new出来就好

        int result = mLivePlayer.startPlay(url,TXLivePlayer.PLAY_TYPE_LIVE_FLV); // result返回值：0 success;  -1 empty url; -2 invalid url; -3 invalid playType;
        Log.w("MeetingDetailWeb","video result:"+result);

        if (result != 0) {
            playBtnIV.setVisibility(View.VISIBLE);
            playBtn.setBackgroundResource(R.drawable.img_meeting_detail_play);
            videoIV.setVisibility(View.VISIBLE);
            return false;
        }
        mIsPlaying = true;
        mStartPlayTS = System.currentTimeMillis();
        return true;
    }

    private void playOrPause(){
        if (null != mLivePlayer){
            if (mIsPlaying){
                mLivePlayer.pause();
                mIsPlaying = false;
                playBtn.setBackgroundResource(R.drawable.img_meeting_detail_play);
            }else{
                mLivePlayer.resume();
                mIsPlaying = true;
                playBtn.setBackgroundResource(R.drawable.img_meeting_detail_stop);
            }
        }

        if (null != mLivePusher){
            if (mIsPushIng){
                mLivePusher.pausePusher();
                mIsPushIng = false;
                playBtn.setBackgroundResource(R.drawable.img_meeting_detail_play);
            }else{
                mLivePusher.resumePusher();
                mIsPushIng = true;
                playBtn.setBackgroundResource(R.drawable.img_meeting_detail_stop);
            }
        }
    }


    private void fullScreen(){
        if (null != mLivePlayer){
            if (!isFull){
                if (mLivePlayer.isPlaying()){
                    mLivePlayer.pause();
                }

                ViewGroup.LayoutParams params = mPlayerView.getLayoutParams();
                params.width = sw;
                params.height = sh;
                mPlayerView.setLayoutParams(params);

                ViewGroup.LayoutParams pa = mSurfaceView.getLayoutParams();
                pa.width = sw;
                pa.height = sh;
                mSurfaceView.setLayoutParams(params);
                mLivePlayer.setSurface(mSurfaceView.getHolder().getSurface());//这行代码只能执行在硬解码环境下

                mLivePlayer.setRenderRotation(TXLiveConstants.RENDER_ROTATION_PORTRAIT);//设置图像渲染角度
                mLivePlayer.setRenderMode(TXLiveConstants.RENDER_MODE_FULL_FILL_SCREEN);//设置图像平铺模式(官网是这么说的，具体什么意思我也不清楚)

                mLivePlayer.setPlayerView(mPlayerView);
                mLivePlayer.resume();

                isFull = true;

            }else{

                if (mLivePlayer.isPlaying()){
                    mLivePlayer.pause();
                }
                int h2 = (420*sw)/720;
                ViewGroup.LayoutParams params = mPlayerView.getLayoutParams();
                params.width = sw;
                params.height = h2;
                mPlayerView.setLayoutParams(params);

                ViewGroup.LayoutParams pa = mSurfaceView.getLayoutParams();
                pa.width = sw;
                pa.height = h2;
                mSurfaceView.setLayoutParams(params);
                mLivePlayer.setSurface(mSurfaceView.getHolder().getSurface());//这行代码只能执行在硬解码环境下

                mLivePlayer.setRenderRotation(TXLiveConstants.RENDER_ROTATION_PORTRAIT);//设置图像渲染角度
                mLivePlayer.setRenderMode(TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION);//设置图像平铺模式(官网是这么说的，具体什么意思我也不清楚)

                mLivePlayer.setPlayerView(mPlayerView);
                mLivePlayer.resume();

                isFull = false;
            }
        }
        if (null != mLivePusher){
            if (!isFull){
                if (mLivePusher.isPushing()){
                    mLivePusher.pausePusher();
                }

                ViewGroup.LayoutParams params = mPlayerView.getLayoutParams();
                params.width = sw;
                params.height = sh;
                mPlayerView.setLayoutParams(params);
                mLivePusher.setRenderRotation(TXLiveConstants.RENDER_ROTATION_PORTRAIT);//设置图像渲染角度
                mLivePusher.startCameraPreview(mPlayerView);
                mLivePusher.resumePusher();

                isFull = true;

            }else{

                if (mLivePusher.isPushing()){
                    mLivePusher.pausePusher();
                }
                int h2 = (420*sw)/720;
                ViewGroup.LayoutParams params = mPlayerView.getLayoutParams();
                params.width = sw;
                params.height = h2;
                mPlayerView.setLayoutParams(params);

                mLivePusher.setRenderRotation(TXLiveConstants.RENDER_ROTATION_PORTRAIT);//设置图像渲染角度
                mLivePusher.startCameraPreview(mPlayerView);
                mLivePusher.resumePusher();

                isFull = false;
            }
        }
    }

    private void stopPlay() {
        playBtn.setBackgroundResource(R.drawable.img_meeting_detail_play);
        videoIV.setVisibility(View.VISIBLE);
        if (mLivePlayer != null) {
            mLivePlayer.setPlayListener(null);
            mLivePlayer.stopPlay(true);
        }
        mIsPlaying = false;
    }

    @Override
    public void onPlayEvent(int event, Bundle bundle) {
        Log.d("onPlayEvent", "event="+event);

        if (event == TXLiveConstants.PLAY_EVT_PLAY_BEGIN) {
            begin();
        } else if (event == TXLiveConstants.PLAY_ERR_NET_DISCONNECT || event == TXLiveConstants.PLAY_EVT_PLAY_END) {
            stopPlay();
        } else if (event == TXLiveConstants.PLAY_EVT_PLAY_LOADING){
            loading();
        } else if (event == TXLiveConstants.PLAY_EVT_RCV_FIRST_I_FRAME) {
        } else if (event == TXLiveConstants.PLAY_EVT_CHANGE_RESOLUTION) {
        } else if (event == TXLiveConstants.PLAY_EVT_CHANGE_ROTATION) {
            return;
        } else if (event == TXLiveConstants.PLAY_EVT_PLAY_PROGRESS) {
            progressBar.setProgress((int)(System.currentTimeMillis()-mStartPlayTS));
        }
        if (event < 0) {
            Log.d("MeetingDetailWeb", "error:" +bundle.getString(TXLiveConstants.EVT_DESCRIPTION));
            Toast.makeText(getApplicationContext(), bundle.getString(TXLiveConstants.EVT_DESCRIPTION), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNetStatus(Bundle status) {
        String str = getNetStatusString(status);
        Log.d("MeetingDetailWeb", "Current status, CPU:"+status.getString(TXLiveConstants.NET_STATUS_CPU_USAGE)+
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

    private void startPush(String url){
        playBtnIV.setVisibility(View.INVISIBLE);
        playBtn.setBackgroundResource(R.drawable.img_meeting_detail_stop);
        videoIV.setVisibility(View.INVISIBLE);
        mLivePusher = new TXLivePusher(getContext());
        TXLivePushConfig mLivePushConfig = new TXLivePushConfig();
        mSurfaceView.setVisibility(View.INVISIBLE);
        mLivePushConfig.setTouchFocus(false);
        mLivePushConfig.setFrontCamera(false);
        mLivePusher.setConfig(mLivePushConfig);
        mLivePusher.setMirror(true);
        mLivePusher.setVideoQuality(VIDEO_QUALITY_SUPER_DEFINITION ,false,false);
        mLivePusher.setPushListener(this);
        mLivePusher.startCameraPreview(mPlayerView);
        mLivePusher.switchCamera();
        int result = mLivePusher.startPusher(url);
        if (result != 0 ){
            playBtnIV.setVisibility(View.VISIBLE);
            playBtn.setBackgroundResource(R.drawable.img_meeting_detail_play);
            videoIV.setVisibility(View.VISIBLE);
            mIsPushIng = false;
        }else{
            mIsPushIng = true;
        }
    }


    //结束推流，注意做好清理工作
    public void stopRtmpPublish() {
        playBtn.setBackgroundResource(R.drawable.img_meeting_detail_play);
        videoIV.setVisibility(View.VISIBLE);
        mLivePusher.stopCameraPreview(true); //停止摄像头预览
        mLivePusher.stopPusher();            //停止推流
        mLivePusher.setPushListener(null);   //解绑 listener
        mIsPushIng = false;
    }


    @Override
    public void onPushEvent(int event, Bundle param) {
        String msg = param.getString(TXLiveConstants.EVT_DESCRIPTION);
        String pushEventLog = "receive event: " + event + ", " + msg;
        Log.d("MeetingDetailWeb", pushEventLog);
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
            Log.d("MeetingDetailWeb", "change resolution to " + param.getInt(TXLiveConstants.EVT_PARAM2) + ", bitrate to" + param.getInt(TXLiveConstants.EVT_PARAM1));
        } else if (event == TXLiveConstants.PUSH_EVT_CHANGE_BITRATE) {
            Log.d("MeetingDetailWeb", "change bitrate to" + param.getInt(TXLiveConstants.EVT_PARAM1));
        } else if (event == TXLiveConstants.PUSH_WARNING_NET_BUSY) {
        } else if (event == TXLiveConstants.PUSH_EVT_START_VIDEO_ENCODER) {
            int encType = param.getInt(TXLiveConstants.EVT_PARAM1);
        }
    }


    private void loading(){
        if (mIsPlaying || mIsPushIng){
            playBtn.setBackgroundResource(R.drawable.img_meeting_detail_play);
            videoIV.setVisibility(View.VISIBLE);
        }
    }

    private void begin(){
        if (mIsPlaying || mIsPushIng){
            playBtn.setBackgroundResource(R.drawable.img_meeting_detail_stop);
            videoIV.setVisibility(View.GONE);
        }
    }


    @Override
    protected void onPause() {
        pausePlay();
        super.onPause();
    }

    /**
     * 暂停时
     */
    private void pausePlay(){
        if (mIsPushIng){
            playBtn.setBackgroundResource(R.drawable.img_meeting_detail_play);
            videoIV.setVisibility(View.VISIBLE);
            mLivePusher.pausePusher();
        }

        if (mIsPlaying){
            playBtn.setBackgroundResource(R.drawable.img_meeting_detail_play);
            videoIV.setVisibility(View.VISIBLE);
            mLivePlayer.pause();
        }
    }

    @Override
    protected void onResume() {
        resumePlay();
        super.onResume();
    }

    /**
     * 恢复时
     */
    private void resumePlay(){
        if (mIsPushIng){
            playBtn.setBackgroundResource(R.drawable.img_meeting_detail_stop);
            videoIV.setVisibility(View.GONE);
            mLivePusher.resumePusher();
        }

        if (mIsPlaying){
            playBtn.setBackgroundResource(R.drawable.img_meeting_detail_stop);
            videoIV.setVisibility(View.GONE);
            mLivePlayer.resume();
        }
    }

    @Override
    protected void onDestroy() {
        if (null != mLivePlayer){
            mLivePlayer.stopPlay(true);
            mLivePlayer = null;
        }
        super.onDestroy();
    }
}
