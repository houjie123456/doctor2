package com.company.linquan.app.moduleMeeting.impl;

import android.util.Log;

import com.company.linquan.app.config.C;
import com.company.linquan.app.http.HttpApi;
import com.company.linquan.app.http.JSONMeetingDetail;
import com.company.linquan.app.moduleMeeting.MeetingInterface;
import com.company.linquan.app.util.ToolSharePerference;
import com.company.linquan.app.util.ToolUtil;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by YC on 2018/7/25.
 */

public class MeetingWebPresenterImp implements MeetingInterface.MeetingWebPresenterInterface{

    private MeetingInterface.MeetingWebInterface view;

    public MeetingWebPresenterImp(MeetingInterface.MeetingWebInterface view) {
        this.view = view;
    }

    @Override
    public void getDetailUrl(String id) {
        Map<String,String> map = new HashMap<>();
        map.put("personID", ToolSharePerference.getStringData(view.getContext(), C.fileconfig, C.UserID));
        map.put("meetingID", id);
        map.put("isH5", "1");
        map.put("sign", ToolUtil.getSign(map));
        HttpApi.getMeetingDetail(map.get("personID"),map.get("meetingID"),map.get("isH5"),map.get("sign"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JSONMeetingDetail>(){
                    @Override
                    public void onCompleted() {
                        Log.i("onCompleted","onCompleted");
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.i("Throwable",e.toString());
                    }
                    @Override
                    public void onNext(JSONMeetingDetail result) {
                        Log.i("onNext","onNext");
                        //填充UI
                        if ("1".equals(result.getCode())){
                            view.initUrl(result.getDetailUrl());
                        }else {
                            view.showToast(result.getMsgBox());
                        }
                    }
                });
    }
}
