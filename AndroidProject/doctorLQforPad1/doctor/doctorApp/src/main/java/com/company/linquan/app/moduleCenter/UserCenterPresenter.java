package com.company.linquan.app.moduleCenter;


import android.util.Log;

import com.company.linquan.app.config.C;
import com.company.linquan.app.http.HttpApi;
import com.company.linquan.app.http.JSONAuthStatus;
import com.company.linquan.app.util.ToolSharePerference;
import com.company.linquan.app.util.ToolUtil;

import java.util.HashMap;
import java.util.Map;

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
        Map<String,String> map = new HashMap<>();
        map.put("personID", ToolSharePerference.getStringData(view.getContext(), C.fileconfig,C.UserID));
        map.put("sign", ToolUtil.getSign(map));
        HttpApi.getPersonInfoCheckStateByParam(map.get("personID"),map.get("sign"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JSONAuthStatus>(){
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
                    public void onNext(JSONAuthStatus result) {
                        Log.i("onNext","onNext");
                        //填充UI
                        if ("1".equals(result.getCode())){
                            view.reloadView(result.getInfoJson());
                            ToolSharePerference.putStringData(view.getContext(),C.AppConfig,C.Photo,result.getInfoJson().getHeadUrl());
                            ToolSharePerference.putStringData(view.getContext(),C.AppConfig,C.LoginName,result.getInfoJson().getMyName());
                            ToolSharePerference.putStringData(view.getContext(),C.AppConfig,C.AUTH,result.getInfoJson().getCheckState());
                        }else {
                            view.showToast(result.getMsgBox());
                        }
                    }
                });
    }
}
