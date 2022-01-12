package com.company.linquan.app.moduleWork.imp;

import android.util.Log;

import com.company.linquan.app.http.HttpApi;
import com.company.linquan.app.http.JSONDrugStore;
import com.company.linquan.app.moduleWork.WorkInterface;
import com.company.linquan.app.util.ToolUtil;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by YC on 2018/7/1.
 */

public class SelectDrugPresenterImp implements WorkInterface.SelectDrugPresenterInterface {

    private WorkInterface.SelectDrugInterface view;
    public SelectDrugPresenterImp(WorkInterface.SelectDrugInterface view) {
        this.view = view;
    }

    @Override
    public void getDrugStore(String drugName, int currPage) {
        Map<String,String> map = new HashMap<>();
        map.put("drugID", "");
        map.put("drugName", drugName);
        map.put("currPage", currPage+"");
        map.put("pageSize", "");
        map.put("sign", ToolUtil.getSign(map));
        HttpApi.getDrugStoreInfo(map.get("drugID"),map.get("drugName"),map.get("currPage")
                ,map.get("pageSize"),map.get("sign"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JSONDrugStore>(){
                    @Override
                    public void onCompleted() {
                        Log.i("onCompleted","onCompleted");
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.i("Throwable",e.toString());
                    }
                    @Override
                    public void onNext(JSONDrugStore result) {
                        Log.i("onNext","onNext");
                        //填充UI
                        if ("1".equals(result.getCode())){
                            view.reloadList(result.getTable());
                        }else {
                            view.showToast(result.getMsgBox());
                        }
                    }
                });
    }
}
