package com.company.linquan.app.moduleCenter.imp;

import android.util.Log;

import com.company.linquan.app.config.C;
import com.company.linquan.app.http.HttpApi;
import com.company.linquan.app.http.JSONBean;
import com.company.linquan.app.moduleCenter.UserCenterInterface;
import com.company.linquan.app.util.ToolSharePerference;
import com.company.linquan.app.util.ToolUtil;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by YC on 2018/7/1.
 */

public class FeedbackPresenterImp implements UserCenterInterface.FeedbackPresenterInterface {

    private UserCenterInterface.FeedbackInterface view;

    public FeedbackPresenterImp(UserCenterInterface.FeedbackInterface view) {
        this.view = view;
    }

    @Override
    public void feedback(String title, String content) {
        view.showDialog();
        Map<String,String> map = new HashMap<>();
        map.put("personID", ToolSharePerference.getStringData(view.getContext(), C.fileconfig,C.UserID));
        map.put("title", title);
        map.put("content", content);
        map.put("sign", ToolUtil.getSign(map));
        HttpApi.addSysFeedbackInfo(map.get("personID"),map.get("title"),map.get("content")
                ,map.get("sign"))
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
                        }else {
                            view.showToast(result.getMsgBox());
                        }
                    }
                });
    }
}
