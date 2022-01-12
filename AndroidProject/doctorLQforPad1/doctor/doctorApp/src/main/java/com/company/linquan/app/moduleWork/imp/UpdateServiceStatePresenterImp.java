package com.company.linquan.app.moduleWork.imp;

import android.util.Log;

import com.company.linquan.app.http.HttpApi;
import com.company.linquan.app.http.JSONBean;
import com.company.linquan.app.moduleWork.WorkInterface;
import com.company.linquan.app.util.ToolUtil;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by YC on 2018/6/29.
 */

public class UpdateServiceStatePresenterImp implements WorkInterface.UpdateServiceStatePresenterInterface {

    private WorkInterface.UpdateServiceStateInterface view;

    public UpdateServiceStatePresenterImp(WorkInterface.UpdateServiceStateInterface view) {
        this.view = view;
    }

    @Override
    public void UpdateServiceState(String id, String serviceState, String checkRemark) {
        view.showDialog();
        Map<String,String> map = new HashMap<>();
        map.put("id", id);
        map.put("serviceState", serviceState);
        map.put("checkRemark", checkRemark);
        map.put("sign", ToolUtil.getSign(map));
        HttpApi.updateServiceState(map.get("id"),map.get("serviceState"),map.get("checkRemark"),map.get("sign"))
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
                            view.finishActivity();
                        }else {
                            view.showToast(result.getMsgBox());
                        }
                    }
                });
    }
}
