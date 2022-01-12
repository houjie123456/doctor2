package com.company.linquan.app.moduleCenter.imp;

import android.util.Log;

import com.company.linquan.app.bean.MeetingBean;
import com.company.linquan.app.config.C;
import com.company.linquan.app.http.HttpApi;
import com.company.linquan.app.http.JSONMeeting;
import com.company.linquan.app.moduleCenter.UserCenterInterface;
import com.company.linquan.app.util.ToolSharePerference;
import com.company.linquan.app.util.ToolUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by YC on 2018/7/5.
 */

public class MyMeetingPresenterImp implements UserCenterInterface.MyMeetingPresenterInterface {

    private UserCenterInterface.MyMeetingInterface view;

    public MyMeetingPresenterImp(UserCenterInterface.MyMeetingInterface view) {
        this.view = view;
    }

    @Override
    public void getMeeting(int page, int type) {
        view.showDialog();
        String ss;
        if (type == 0){
            ss = "2";
        }else if (type == 1){
            ss = "1";
        }else if (type == 2){
            ss = "3";
        }else{
            ss = "9";
        }
        Map<String,String> map = new HashMap<>();
        map.put("personID", ToolSharePerference.getStringData(view.getContext(), C.fileconfig,C.UserID));
        map.put("vedioType", ss);
        map.put("partakeType", "1");
        map.put("currPage", page+"");
        map.put("sign", ToolUtil.getSign(map));
        HttpApi.getMeetingList(map.get("personID"),map.get("vedioType"),map.get("partakeType"),map.get("currPage"),map.get("sign"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JSONMeeting>(){
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
                    public void onNext(JSONMeeting result) {
                        Log.i("onNext","onNext");
                        //填充UI
                        if ("1".equals(result.getCode())){
                            view.refreshList(result.getTable());
                        }else {
                            view.showToast(result.getMsgBox());
                            view.refreshList(new ArrayList<MeetingBean>());
                        }
                    }
                });
    }
}
