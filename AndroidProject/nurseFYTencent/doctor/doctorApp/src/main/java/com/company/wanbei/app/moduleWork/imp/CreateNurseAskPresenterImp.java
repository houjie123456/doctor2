package com.company.wanbei.app.moduleWork.imp;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.tencent.qcloud.tuikit.tuichat.fromApp.config.C;
import com.company.wanbei.app.http.HttpApi;
import com.company.wanbei.app.http.JSONBean;
import com.company.wanbei.app.moduleWork.WorkInterface;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.ToolSharePerference;

import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by YC on 2018/6/29.
 */

public class CreateNurseAskPresenterImp implements WorkInterface.CreateNurseAskPresenterInterface {

    private WorkInterface.CreateNurseAskInterface view;

    public CreateNurseAskPresenterImp(WorkInterface.CreateNurseAskInterface view) {
        this.view = view;
    }

    @Override
    public void createNurseAsk(String startDate, String endDate, String id) {
        view.showDialog();
        JSONObject map = new JSONObject();
        map.put("nurseId", ToolSharePerference.getStringData(view.getContext(), C.fileconfig, C.UserID));
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        map.put("id", id);
        RequestBody requestBody = HttpApi.packageParam(map);
        HttpApi.createNurseAsk(requestBody)
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
                        if ("0".equals(result.getCode())){
                            view.showToast(result.getMsgBox());
                            view.finishActivity();
                        }else {
                            view.showToast(result.getMsgBox());
                        }
                    }
                });
    }
}
