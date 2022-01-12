package com.company.linquan.app.zxing.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.company.linquan.app.R;
import com.company.linquan.app.base.BaseActivity;
import com.company.linquan.app.config.C;
import com.company.linquan.app.http.HttpApi;
import com.company.linquan.app.http.JSONSignPerson;
import com.company.linquan.app.moduleMeeting.ui.SignResultActivity;
import com.company.linquan.app.util.ExitApp;
import com.company.linquan.app.util.ToolSharePerference;
import com.company.linquan.app.util.ToolUtil;
import com.company.linquan.app.zxing.camera.CameraManager;
import com.company.linquan.app.zxing.camera.open.CameraFacing;
import com.company.linquan.app.zxing.decoding.ActivityHandler;
import com.company.linquan.app.zxing.decoding.InactivityTimer;
import com.company.linquan.app.zxing.view.ViewfinderView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by YC on 2017/5/3.
 */

public class CameraActivity extends BaseActivity implements SurfaceHolder.Callback{

    //扫描器
    private CameraManager cameraManager;
    private ActivityHandler handler;
    private ViewfinderView viewfinderView;
    private boolean hasSurface;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    private InactivityTimer inactivityTimer;
    private boolean playBeep;
    private SoundPool soundPool;
    private int loadId;

    private static final float BEEP_VOLUME = 0.10f;
    private boolean vibrate;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExitApp.getInstance().addActivity(this);		// add Activity 方便退出
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 开启自定义标题栏
        setContentView(R.layout.activity_camera);
        initData();
        initBar();
        initCamera();
        setListener();
    }

    private void initData() {
    }

    private void initBar() {
        RelativeLayout view = (RelativeLayout) findViewById(R.id.topbar);
        // 标题
        TextView topTitle = (TextView) view.findViewById(R.id.head_top_title);
        topTitle.setText("扫一扫");
        ImageView backIV = (ImageView)view.findViewById(R.id.head_top_image);
        backIV.setVisibility(View.VISIBLE);
        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initCamera(){
        cameraManager = new CameraManager(getApplicationContext());
        cameraManager.setBackORFront(CameraFacing.BACK);
        cameraManager.setFullScreen(false);
        viewfinderView = (ViewfinderView) findViewById(R.id.cashier_viewfinder_view);
        viewfinderView.setCameraManager(cameraManager);
        viewfinderView.setMoney("");
        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
        initView();
    }

    private void initView() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.cashier_preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }

        decodeFormats = null;
        characterSet = null;

        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {playBeep = false;}
        initBeepSound();
        vibrate = true;
        initView();
    }

    /**
     * Handler scan result
     * @param result
     * @param barcode
     */
    public void handleDecode(Result result, Bitmap barcode) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        String resultString = result.getText();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (handler != null) {
                    Message message = new Message();
                    message.what = R.id.restart_preview;
                    handler.handleMessage(message);
                }
            }
        }, 2000);


        //FIXME
        if (resultString.equals("")) {
            Toast.makeText(CameraActivity.this, "无法扫描，请稍后再试!", Toast.LENGTH_SHORT).show();
        }else {
//            Intent intent = new Intent();
//            Bundle b = new Bundle();
//            b.putString("result",resultString);
//            intent.putExtras(b);
//            setResult(RESULT_OK,intent);
//            finish();
            getMeetingList(resultString);
        }
    }

    public void getMeetingList(String meetingID) {
        Map<String,String> map = new HashMap<>();
        map.put("personID", ToolSharePerference.getStringData(CameraActivity.this, C.fileconfig,C.UserID));
        map.put("meetingID", meetingID);
        map.put("longitude", "");
        map.put("latitude", "");
        map.put("sign", ToolUtil.getSign(map));
        HttpApi.meetingSignIN(map.get("meetingID"),map.get("personID"),map.get("longitude"),map.get("latitude"),map.get("sign"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JSONSignPerson>(){
                    @Override
                    public void onCompleted() {
                        Log.i("onCompleted","onCompleted");
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.i("Throwable",e.toString());
                    }
                    @Override
                    public void onNext(JSONSignPerson result) {
                        Log.i("onNext","onNext");
                        //填充UI
                        if ("1".equals(result.getCode())){
                            Intent intent = new Intent(CameraActivity.this, SignResultActivity.class);
                            intent.putExtra("name",result.getInfoJson().getName());
                            intent.putExtra("card",result.getInfoJson().getIdCardNo());
                            intent.putExtra("mobile",result.getInfoJson().getMobile());
                            intent.putExtra("hospital",result.getInfoJson().getHospitalName());
                            intent.putExtra("position",result.getInfoJson().getAcademicTitle());
                            startActivityForResult(intent,0);
                        }else {
                            Toast.makeText(CameraActivity.this,result.getMsgBox(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }



    private void setListener() {
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            cameraManager.openDriver(surfaceHolder);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }
        if (handler == null) {
            handler = new ActivityHandler(this, decodeFormats, characterSet, cameraManager);
        }
    }


    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(surfaceHolder);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        hasSurface = false;
    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();
    }

    public void getManager() {
        viewfinderView.drawViewfinder();
    }

    private void initBeepSound() {
        if (playBeep && soundPool == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            soundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 100);
            loadId = soundPool.load(this,R.raw.beep,1);
        }
    }

    private static final long VIBRATE_DURATION = 200L;

    private void playBeepSoundAndVibrate() {
        if (playBeep && soundPool != null) {
            soundPool.play(loadId, 1, 1, 1, 0, 1f);
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final MediaPlayer.OnCompletionListener beepListener = new MediaPlayer.OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        cameraManager.closeDriver();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0){
            //返回上一级
            Intent intent = new Intent();
            setResult(1,intent);
            finish();
        }

    }

}
