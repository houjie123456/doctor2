package com.company.wanbei.app.moduleWork.imp;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.company.wanbei.app.bean.NurseServeBean;
import com.company.wanbei.app.http.HttpApi;
import com.company.wanbei.app.http.JSONNurseServe;
import com.company.wanbei.app.moduleWork.WorkInterface;

import java.util.ArrayList;

import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by YC on 2018/8/4.
 */

public class SelectNurseServicePresenterImp implements WorkInterface.SelectNurseServicePresenterInterface {
    private WorkInterface.SelectNurseServiceInterface view;

    public SelectNurseServicePresenterImp(WorkInterface.SelectNurseServiceInterface view) {
        this.view = view;
    }

    @Override
    public void getNurseService(String docID,String typeID) {
        view.showDialog();
        JSONObject map = new JSONObject();
        map.put("doctorId", docID);
        map.put("typeId", typeID);
        RequestBody requestBody = HttpApi.packageParam(map);
        HttpApi.getNursingType(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JSONNurseServe>(){
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
                    public void onNext(JSONNurseServe result) {
                        Log.i("onNext","onNext");
                        //填充UI
                        if ("0".equals(result.getCode())){
                            view.reloadList(result.getData());
                        }else {
                            view.showToast(result.getMsgBox());
                            view.reloadList(new ArrayList<NurseServeBean>());
                        }
                    }
                });
    }
}
