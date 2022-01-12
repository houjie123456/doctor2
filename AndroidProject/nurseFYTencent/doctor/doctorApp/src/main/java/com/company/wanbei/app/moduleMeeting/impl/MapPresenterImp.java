package com.company.wanbei.app.moduleMeeting.impl;

import android.util.Log;

import com.tencent.qcloud.tuikit.tuichat.fromApp.config.C;
import com.company.wanbei.app.http.HttpApi;
import com.company.wanbei.app.http.JSONBean;
import com.company.wanbei.app.moduleMeeting.MeetingInterface;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.ToolSharePerference;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.ToolUtil;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by YC on 2018/8/4.
 */

public class MapPresenterImp implements MeetingInterface.MapPresenterInterface{

    private MeetingInterface.MapInterface view;

    public MapPresenterImp(MeetingInterface.MapInterface view) {
        this.view = view;
    }

    @Override
    public void addAddress(String address, String room, String lat, String lon) {
        view.showDialog();
        Map<String,String> map = new HashMap<>();
        map.put("personID", ToolSharePerference.getStringData(view.getContext(), C.fileconfig, C.UserID));
        map.put("detailAddress", address);
        map.put("roomNo", room);
        map.put("latitude", lat);
        map.put("longitude", lon);
        map.put("sign", ToolUtil.getSign(map));
        HttpApi.addCommonlyAddress(map.get("personID"),map.get("detailAddress"),map.get("roomNo"),map.get("longitude"),map.get("latitude"),map.get("sign"))
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
                        if ("0".equals(result.getCode())){
                            view.finishActivity();
                        }else {
                            view.showToast(result.getMsgBox());
                        }
                    }
                });
    }
}
