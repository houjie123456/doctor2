package com.company.linquan.app.moduleWork.imp;

import android.util.Log;

import com.company.linquan.app.bean.NurseServeBean;
import com.company.linquan.app.bean.NurseTypeBean;
import com.company.linquan.app.config.C;
import com.company.linquan.app.http.HttpApi;
import com.company.linquan.app.http.JSONNurseServe;
import com.company.linquan.app.http.JSONNurseType;
import com.company.linquan.app.moduleWork.WorkInterface;
import com.company.linquan.app.util.ToolSharePerference;
import com.company.linquan.app.util.ToolUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        Map<String,String> map = new HashMap<>();
        map.put("docID", docID);
        map.put("typeID", typeID);
        map.put("sign", ToolUtil.getSign(map));
        HttpApi.getNursingType(map.get("typeID"),map.get("docID"),map.get("sign"))
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
                        if ("1".equals(result.getCode())){
                            view.reloadList(result.getTable());
                        }else {
                            view.showToast(result.getMsgBox());
                            view.reloadList(new ArrayList<NurseServeBean>());
                        }
                    }
                });
    }
}
