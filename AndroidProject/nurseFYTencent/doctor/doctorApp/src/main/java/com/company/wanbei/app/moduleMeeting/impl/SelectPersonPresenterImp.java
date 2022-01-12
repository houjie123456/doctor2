package com.company.wanbei.app.moduleMeeting.impl;

import android.util.Log;

import com.company.wanbei.app.bean.FriendsBean;
import com.tencent.qcloud.tuikit.tuichat.fromApp.config.C;
import com.company.wanbei.app.http.HttpApi;
import com.company.wanbei.app.http.JSONFriends;
import com.company.wanbei.app.moduleMeeting.MeetingInterface;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.ToolSharePerference;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.ToolUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by YC on 2018/7/6.
 */

public class SelectPersonPresenterImp implements MeetingInterface.SelectPersonPresenterInterface {


    private MeetingInterface.SelectPersonInterface view;

    public SelectPersonPresenterImp(MeetingInterface.SelectPersonInterface view) {
        this.view = view;
    }

    @Override
    public void getPersonList(int page, int type) {
        view.showDialog();
        Map<String,String> map = new HashMap<>();
        map.put("personID", ToolSharePerference.getStringData(view.getContext(), C.fileconfig, C.UserID));
        map.put("sign", ToolUtil.getSign(map));
        HttpApi.getMyFriendList(map.get("personID"),map.get("sign"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JSONFriends>(){
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
                    public void onNext(JSONFriends result) {
                        Log.i("onNext","onNext");
                        //填充UI
                        if ("0".equals(result.getCode())){
                            view.refreshList(result.getTable());
                        }else {
                            view.showToast(result.getMsgBox());
                            view.refreshList(new ArrayList<FriendsBean>());
                        }
                    }
                });
    }
}
