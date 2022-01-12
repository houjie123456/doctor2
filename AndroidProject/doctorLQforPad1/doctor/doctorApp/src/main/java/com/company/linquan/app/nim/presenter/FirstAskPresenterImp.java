package com.company.linquan.app.nim.presenter;

import android.content.Context;
import android.util.Log;

import com.company.linquan.app.config.C;
import com.company.linquan.app.http.HttpApi;
import com.company.linquan.app.http.JSONFirstAsk;
import com.company.linquan.app.nim.ConversationInterface;
import com.company.linquan.app.util.ToolSharePerference;
import com.company.linquan.app.util.ToolUtil;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class FirstAskPresenterImp implements ConversationInterface.FirstAskPresenterInterface {

    private ConversationInterface.FirstAskInterface view;
    public FirstAskPresenterImp(ConversationInterface.FirstAskInterface view) {
        this.view = view;
    }

    @Override
    public void getFirstAskInfo(String id) {
        Map<String,String> map = new HashMap<>();
        map.put("inquiryId", id);
        map.put("sign", ToolUtil.getSign(map));
        HttpApi.getInquiryDetailById(map.get("inquiryId"),map.get("sign"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JSONFirstAsk>(){
                    @Override
                    public void onCompleted() {
                        Log.i("onCompleted","onCompleted");
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.i("Throwable",e.toString());
                    }
                    @Override
                    public void onNext(JSONFirstAsk result) {
                        Log.i("onNext","onNext");
                        //填充UI
                        if ("1".equals(result.getCode())){
                            view.reloadInfo(result);
                        }else {
                            view.showToast(result.getMsgBox());
                        }
                    }
                });
    }
}
