package com.company.wanbei.app.moduleCenter.imp;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.tencent.qcloud.tuikit.tuichat.fromApp.config.C;
import com.company.wanbei.app.http.HttpApi;
import com.company.wanbei.app.http.JSONMyMoney;
import com.company.wanbei.app.moduleCenter.UserCenterInterface;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.ToolSharePerference;

import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by YC on 2018/7/5.
 */

public class MyMoneyPresenterImp implements UserCenterInterface.MyMoneyPresenterInterface {

    private UserCenterInterface.MyMoneyInterface view;

    public MyMoneyPresenterImp(UserCenterInterface.MyMoneyInterface view) {
        this.view = view;
    }

    @Override
    public void getList(String profitDate,String state,String profitType,String page) {
//        view.showDialog();
        JSONObject map = new JSONObject();
        map.put("doctorId", ToolSharePerference.getStringData(view.getContext(), C.fileconfig, C.UserID));
        map.put("profitDate", profitDate);
        map.put("state", state);
        map.put("profitType", profitType);
        map.put("page", page);
        RequestBody requestBody = HttpApi.packageParam(map);
        HttpApi.profitInfoList(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JSONMyMoney>(){
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
                    public void onNext(JSONMyMoney result) {
                        Log.i("onNext","onNext");
                        //填充UI
                        if ("0".equals(result.getCode())){
                            view.reloadList(result.getData().getTable(),result.getData().getTotalAmount());
                        }else {
                            view.showToast(result.getMsgBox());
                        }
                    }
                });
    }
}
