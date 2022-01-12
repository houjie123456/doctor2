package com.company.linquan.app.moduleWork.imp;

import android.util.Log;

import com.company.linquan.app.config.C;
import com.company.linquan.app.http.HttpApi;
import com.company.linquan.app.http.JSONBean;
import com.company.linquan.app.moduleWork.WorkInterface;
import com.company.linquan.app.util.ToolSharePerference;
import com.company.linquan.app.util.ToolUtil;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by YC on 2018/6/29.
 */

public class CreateDiseasePresenterImp implements WorkInterface.CreateDiseasePresenterInterface {

    private WorkInterface.CreateDiseaseInterface view;

    public CreateDiseasePresenterImp(WorkInterface.CreateDiseaseInterface view) {
        this.view = view;
    }

    @Override
    public void createDisease(String docID, String groupName, String id) {
        view.showDialog();
        Map<String,String> map = new HashMap<>();
        map.put("docID", docID);
        map.put("groupName", groupName);
        map.put("id", id);
        HttpApi.createDisease(map.get("docID"),map.get("groupName"),map.get("id"))
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
                            view.finishActivity();
                        }else {
                            view.showToast(result.getMsgBox());
                        }
                    }
                });
    }
}
