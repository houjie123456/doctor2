package com.company.linquan.app.moduleMeeting.impl;

import android.util.Log;

import com.company.linquan.app.http.HttpApi;
import com.company.linquan.app.http.JSONBean;
import com.company.linquan.app.moduleMeeting.MeetingInterface;
import com.company.linquan.app.util.ToolUtil;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by YC on 2018/7/25.
 */

public class MeetingDetailWebPresenterImp implements MeetingInterface.MeetingDetailWebPresenterInterface{

    private MeetingInterface.MeetingDetailWebInterface view;

    public MeetingDetailWebPresenterImp(MeetingInterface.MeetingDetailWebInterface view) {
        this.view = view;
    }

    @Override
    public void getVideoUrl(String personId) {
        view.showDialog();
        Map<String,String> map = new HashMap<>();
        map.put("pushManID", personId);
        map.put("pushManType", "4");
        map.put("sign", ToolUtil.getSign(map));
        HttpApi.getLivePushAndOpenAddress(map.get("pushManID"),map.get("pushManType"),map.get("sign"))
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
                            view.initUrl(result.getPalyUrlFLV(),result.getPushUrl());
                        }else {
                            view.showToast(result.getMsgBox());
                        }
                    }
                });
    }
}
