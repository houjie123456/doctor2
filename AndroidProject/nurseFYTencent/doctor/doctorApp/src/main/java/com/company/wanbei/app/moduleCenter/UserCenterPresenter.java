package com.company.wanbei.app.moduleCenter;


import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.tencent.qcloud.tuikit.tuichat.fromApp.config.C;
import com.company.wanbei.app.http.HttpApi;
import com.company.wanbei.app.http.JSONPersonAll;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.ToolSharePerference;

import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by YC on 2018/1/9.
 */

public class UserCenterPresenter implements UserCenterInterface.PresenterInterface {

    private UserCenterInterface.ViewInterface view;
    public UserCenterPresenter(UserCenterInterface.ViewInterface view) {
        this.view = view;
    }

    @Override
    public void getPersonInfo() {
        view.showDialog();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", ToolSharePerference.getStringData(view.getContext(), C.fileconfig, C.UserID));
        RequestBody requestBody = HttpApi.packageParam(jsonObject);
        HttpApi.getPersonInfoByParam(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JSONPersonAll>(){
                    @Override
                    public void onCompleted() {
                        view.dismissDialog();
                        Log.i("onCompleted","onCompleted");
                    }
                    @Override
                    public void onError(Throwable e) {
                        view.dismissDialog();
                        Log.i("Throwable",e.toString());
                    }
                    @Override
                    public void onNext(JSONPersonAll result) {
                        Log.i("onNext","onNext");
                        //填充UI
                        if ("0".equals(result.getCode())){
                            view.reloadView(result.getData().getInfoJson());
                            ToolSharePerference.putStringData(view.getContext(), C.AppConfig, C.Photo,result.getData().getInfoJson().getHeadUrl());
                            ToolSharePerference.putStringData(view.getContext(), C.AppConfig, C.LoginName,result.getData().getInfoJson().getMyName());
                            ToolSharePerference.putStringData(view.getContext(), C.AppConfig, C.AUTH,result.getData().getInfoJson().getCheckState());
                        }else {
                            view.showToast(result.getMsgBox());
                        }
                    }
                });
    }

    @Override
    public void getMyMoney() {
        view.showDialog();
        JSONObject map = new JSONObject();
        map.put("doctorId", ToolSharePerference.getStringData(view.getContext(), C.fileconfig, C.UserID));
        RequestBody requestBody = HttpApi.packageParam(map);
        HttpApi.findMyIncome(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JSONPersonAll>(){
                    @Override
                    public void onCompleted() {
                        view.dismissDialog();
                        Log.i("onCompleted","onCompleted");
                    }
                    @Override
                    public void onError(Throwable e) {
                        view.dismissDialog();
                        Log.i("Throwable",e.toString());
                    }
                    @Override
                    public void onNext(JSONPersonAll result) {
                        Log.i("onNext","onNext");
                        //填充UI
                        if ("0".equals(result.getCode())){
                            view.reloadMyMoney(result.getData());
                        }else {
                            view.showToast(result.getMsgBox());
                        }
                    }
                });
    }
}
