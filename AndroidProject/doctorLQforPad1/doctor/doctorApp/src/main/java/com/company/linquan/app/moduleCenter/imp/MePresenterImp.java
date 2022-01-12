package com.company.linquan.app.moduleCenter.imp;

import android.util.Log;

import com.company.linquan.app.config.C;
import com.company.linquan.app.http.HttpApi;
import com.company.linquan.app.http.JSONMe;
import com.company.linquan.app.moduleCenter.UserCenterInterface;
import com.company.linquan.app.util.ToolSharePerference;
import com.company.linquan.app.util.ToolUtil;

import java.util.HashMap;
import java.util.Map;

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
        Map<String,String> map = new HashMap<>();
        map.put("personID", ToolSharePerference.getStringData(view.getContext(), C.fileconfig,C.UserID));
        map.put("sign", ToolUtil.getSign(map));
        HttpApi.myBusinessCard(map.get("personID"),map.get("sign"))
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
                        if ("1".equals(result.getCode())){
                            view.reloadView(result);
                        }else {
                            view.showToast(result.getMsgBox());
                        }
                    }
                });
    }
}
