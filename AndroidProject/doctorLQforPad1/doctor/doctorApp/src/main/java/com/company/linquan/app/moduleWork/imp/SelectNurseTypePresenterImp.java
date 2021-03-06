package com.company.linquan.app.moduleWork.imp;

import android.util.Log;

import com.company.linquan.app.bean.AddressBean;
import com.company.linquan.app.bean.NurseTypeBean;
import com.company.linquan.app.config.C;
import com.company.linquan.app.http.HttpApi;
import com.company.linquan.app.http.JSONAddress;
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

public class SelectNurseTypePresenterImp implements WorkInterface.SelectNurseTypePresenterInterface {
    private WorkInterface.SelectNurseTypeInterface view;

    public SelectNurseTypePresenterImp(WorkInterface.SelectNurseTypeInterface view) {
        this.view = view;
    }

    @Override
    public void getNurseType(String docID) {
        view.showDialog();
        Map<String,String> map = new HashMap<>();
        map.put("docID", docID);
        map.put("sign", ToolUtil.getSign(map));
        HttpApi.getNurseType(map.get("docID"),map.get("sign"))
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
                        //??????UI
                        if ("1".equals(result.getCode())){
                            view.reloadList(result.getTable());
                        }else {
                            view.showToast(result.getMsgBox());
                            view.reloadList(new ArrayList<NurseTypeBean>());
                        }
                    }
                });
    }
}
