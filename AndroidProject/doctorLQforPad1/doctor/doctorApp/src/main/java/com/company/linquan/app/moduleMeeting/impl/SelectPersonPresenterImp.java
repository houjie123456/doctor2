package com.company.linquan.app.moduleMeeting.impl;

import android.util.Log;

import com.company.linquan.app.bean.FriendsBean;
import com.company.linquan.app.config.C;
import com.company.linquan.app.http.HttpApi;
import com.company.linquan.app.http.JSONFriends;
import com.company.linquan.app.moduleMeeting.MeetingInterface;
import com.company.linquan.app.util.ToolSharePerference;
import com.company.linquan.app.util.ToolUtil;

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
        map.put("personID", ToolSharePerference.getStringData(view.getContext(), C.fileconfig,C.UserID));
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
                        if ("1".equals(result.getCode())){
                            view.refreshList(result.getTable());
                        }else {
                            view.showToast(result.getMsgBox());
                            view.refreshList(new ArrayList<FriendsBean>());
                        }
                    }
                });
    }
}
