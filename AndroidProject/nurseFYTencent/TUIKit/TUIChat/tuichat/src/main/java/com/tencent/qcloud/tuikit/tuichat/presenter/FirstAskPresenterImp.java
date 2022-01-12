package com.tencent.qcloud.tuikit.tuichat.presenter;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tencent.qcloud.tuikit.tuichat.fromApp.config.C;
import com.tencent.qcloud.tuikit.tuichat.fromApp.http.HttpApi;
import com.tencent.qcloud.tuikit.tuichat.fromApp.http.JSONFirstAsk;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.ToolSharePerference;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.ToolUtil;
import com.tencent.qcloud.tuikit.tuichat.interfaces.ConversationInterface;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
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

        JSONObject jsonObject =new JSONObject();
        jsonObject.put("id", id);
        if(ToolSharePerference.getStringData(view.getContext(), C.fileconfig, C.IDTYPE).equals("1")){//护理
            jsonObject.put("inquirySign", "3");
        }

        RequestBody requestBody = HttpApi.packageParam(jsonObject);
        HttpApi.getInquiryDetailById(requestBody)
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
                        if ("0".equals(result.getCode())){
                            view.reloadInfo(result);
                        }else {
                            view.showToast(result.getMsgBox());
                        }
                    }
                });
    }
}
