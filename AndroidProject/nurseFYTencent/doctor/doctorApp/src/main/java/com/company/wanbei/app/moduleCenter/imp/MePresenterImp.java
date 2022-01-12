package com.company.wanbei.app.moduleCenter.imp;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.tencent.qcloud.tuikit.tuichat.fromApp.config.C;
import com.company.wanbei.app.http.HttpApi;
import com.company.wanbei.app.http.JSONMe;
import com.company.wanbei.app.moduleCenter.UserCenterInterface;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.ToolSharePerference;

import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by YC on 2018/7/2.
 */

public class MePresenterImp implements UserCenterInterface.MePresenterInterface {
    private UserCenterInterface.MeInterface view;

    public MePresenterImp(UserCenterInterface.MeInterface view) {
        this.view = view;
    }

    @Override
    public void getPerson() {
        view.showDialog();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", ToolSharePerference.getStringData(view.getContext(), C.fileconfig, C.UserID));
        RequestBody requestBody = HttpApi.packageParam(jsonObject);
        HttpApi.myBusinessCard(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JSONMe>(){
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
                    public void onNext(JSONMe result) {
                        Log.i("onNext","onNext");
                        //填充UI
                        if ("0".equals(result.getCode())){
                            view.reloadView(result.getData());
                        }else {
                            view.showToast(result.getMsgBox());
                        }
                    }
                });
    }
}
