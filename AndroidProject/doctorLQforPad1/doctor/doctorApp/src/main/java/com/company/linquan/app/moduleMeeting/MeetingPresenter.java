package com.company.linquan.app.moduleMeeting;

import android.util.Log;

import com.company.linquan.app.bean.MeetingBean;
import com.company.linquan.app.http.HttpApi;
import com.company.linquan.app.http.JSONBean;
import com.company.linquan.app.http.JSONMeeting;
import com.company.linquan.app.util.ToolUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by YC on 2018/1/9.
 */

public class MeetingPresenter implements MeetingInterface.PresenterInterface {

    private MeetingInterface.ViewInterface view;

    public MeetingPresenter(MeetingInterface.ViewInterface view) {
        this.view = view;
    }

    @Override
    public void getMeetingList(int page, int type) {
        String typeStr = "";
        if (type == 0){
            typeStr = "2";
        }else if (type == 1){
            typeStr = "1";
        }else if (type == 2){
            typeStr = "3";
        }
        Map<String,String> map = new HashMap<>();
        map.put("personID", "");
        map.put("vedioType", typeStr);
        map.put("partakeType", "9");
        map.put("currPage", page+"");
        map.put("sign", ToolUtil.getSign(map));
        HttpApi.getMeetingList(map.get("personID"),map.get("vedioType"),map.get("partakeType"),map.get("currPage"),map.get("sign"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JSONMeeting>(){
                    @Override
                    public void onCompleted() {
                        Log.i("onCompleted","onCompleted");
                    }
                    @Override
                    public void onError(Throwable e) {
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

    @Override
    public void getVideoUrl(String personId, final int position) {
        view.showDialog();
        Map<String,String> map = new HashMap<>();
        map.put("pushManID", personId);
        map.put("pushManType", "1");
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
                            view.initUrl(position,result.getPalyUrlHLS());
                        }else {
                            view.showToast(result.getMsgBox());
                        }
                    }
                });
    }
}
