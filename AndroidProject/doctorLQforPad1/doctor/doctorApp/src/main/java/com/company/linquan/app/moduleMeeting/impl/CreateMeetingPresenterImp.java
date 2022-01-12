package com.company.linquan.app.moduleMeeting.impl;

import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

import com.company.linquan.app.config.C;
import com.company.linquan.app.http.HttpApi;
import com.company.linquan.app.http.JSONBean;
import com.company.linquan.app.moduleMeeting.MeetingInterface;
import com.company.linquan.app.util.ToolSharePerference;
import com.company.linquan.app.util.ToolUtil;
import com.company.linquan.app.util.UploadPersonPhotoUtil;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by YC on 2018/6/27.
 */

public class CreateMeetingPresenterImp implements MeetingInterface.CreatePresenterInterface {

    private MeetingInterface.CreateMeetingInterface view;

    public CreateMeetingPresenterImp(MeetingInterface.CreateMeetingInterface view) {
        this.view = view;
    }

    @Override
    public void createMeeting(String title, String startTime
            , String endTime,String address, String remark,String vedioCover, String inviterIDStr
            ,String longitude, String latitude) {
        view.showDialog();
        Map<String,String> map = new HashMap<>();
        map.put("personID", ToolSharePerference.getStringData(view.getContext(),C.fileconfig,C.UserID));
        map.put("title", title);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("address", address);
        map.put("remark", remark);
        map.put("vedioCover", vedioCover);
        map.put("inviterIDStr", inviterIDStr);
        map.put("longitude", longitude);
        map.put("latitude", latitude);
        map.put("sign", ToolUtil.getSign(map));
        HttpApi.createMeeting(map.get("personID"),map.get("title"),map.get("startTime"),map.get("endTime")
                ,map.get("address"),map.get("remark"),map.get("vedioCover"),map.get("inviterIDStr")
                ,map.get("longitude"),map.get("latitude"),map.get("sign"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JSONBean>(){
                    @Override
                    public void onCompleted() {
                        Log.i("onCompleted","onCompleted");
                        view.dismissDialog();
                    }
                    @Override
                    public void onError(Throwable e) {
                        view.dismissDialog();
                        Log.i("Throwable",e.toString());
                    }
                    @Override
                    public void onNext(JSONBean result) {
                        Log.i("onNext","onNext");
                        //填充UI
                        if ("1".equals(result.getCode())){
                            view.showToast(result.getMsgBox());
                            view.finishActivity();
                        }else {
                            view.showToast(result.getMsgBox());
                        }
                    }
                });
    }

    @Override
    public void upload(final String path) {
        view.showDialog();
        new Thread(new Runnable() {
            @Override
            public void run() {
                StringBuilder buffer = new StringBuilder();
                if (!"".equals(path)) {
                    Bitmap mBitmap = UploadPersonPhotoUtil.getBitmapFromFile(path, 720, 1280);
                    final ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    mBitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
                    buffer = buffer.append(Base64.encodeToString(baos.toByteArray(),0));
                    mBitmap.recycle();
                    mBitmap = null;
                }

                Map<String, String> map = new HashMap<>();
                map.put("personID", buffer.toString());
                map.put("picType", "9");
                map.put("picFile", buffer.toString());
                map.put("sign", ToolUtil.getSign(map));
                HttpApi.uploadPicTemp(map.get("personID"), map.get("picType"), map.get("picFile"), map.get("sign"))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<JSONBean>() {
                            @Override
                            public void onCompleted() {
                                Log.i("onCompleted", "onCompleted");
                                view.dismissDialog();
                            }
                            @Override
                            public void onError(Throwable e) {
                                view.dismissDialog();
                                Log.i("Throwable", e.toString());
                            }
                            @Override
                            public void onNext(JSONBean result) {
                                Log.i("onNext", "onNext");
                                //填充UI
                                if ("1".equals(result.getCode())) {
                                    view.initPath(result.getPicSignID());
                                } else {
                                    view.showToast(result.getMsgBox());
                                }
                            }
                        });

            }
        }).start();
    }
}
