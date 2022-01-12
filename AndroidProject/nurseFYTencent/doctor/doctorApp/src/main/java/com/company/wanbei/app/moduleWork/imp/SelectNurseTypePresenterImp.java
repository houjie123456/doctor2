package com.company.wanbei.app.moduleWork.imp;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.company.wanbei.app.bean.NurseTypeBean;
import com.company.wanbei.app.http.HttpApi;
import com.company.wanbei.app.http.JSONNurseType;
import com.company.wanbei.app.moduleWork.WorkInterface;

import java.util.ArrayList;

import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by YC on 2018/8/4.
 */

public class SelectNurseTypePresenterImp implements WorkInterface.SelectNurseTypePresenterInterface {
    private WorkInterface.SelectNurseTypeInterface view;

    public SelectNurseTypePresenterImp(WorkInterface.SelectNurseTypeInterface view) {
        this.view = view;
    }

    @Override
    public void getNurseType() {
        view.showDialog();
        JSONObject map = new JSONObject();
        RequestBody requestBody = HttpApi.packageParam(map);
        HttpApi.getNurseType(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JSONNurseType>(){
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
                    public void onNext(JSONNurseType result) {
                        Log.i("onNext","onNext");
                        //填充UI
                        if ("0".equals(result.getCode())){
                            view.reloadList(result.getData());
                        }else {
                            view.showToast(result.getMsgBox());
                            view.reloadList(new ArrayList<NurseTypeBean>());
                        }
                    }
                });
    }
}
