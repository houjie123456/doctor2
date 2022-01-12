package com.company.linquan.app.moduleWork.imp;

import android.util.Log;

import com.company.linquan.app.config.C;
import com.company.linquan.app.http.HttpApi;
import com.company.linquan.app.http.JSONChange;
import com.company.linquan.app.moduleWork.WorkInterface;
import com.company.linquan.app.util.ToolSharePerference;
import com.company.linquan.app.util.ToolUtil;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by YC on 2018/7/15.
 */

public class ChangePresenterImp implements WorkInterface.ChangePresenterInterface {

    private WorkInterface.ChangeInterface view;

    public ChangePresenterImp(WorkInterface.ChangeInterface view) {
        this.view = view;
    }

    @Override
    public void getChange(int currPage) {
        view.showDialog();
        Map<String,String> map = new HashMap<>();
        map.put("personID", ToolSharePerference.getStringData(view.getContext(), C.fileconfig,C.UserID));
        map.put("currPage", currPage+"");
        map.put("content", "");
        map.put("sign", ToolUtil.getSign(map));
        HttpApi.getGotoBespeakInfo(map.get("personID"),map.get("currPage"),map.get("content"),map.get("sign"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JSONChange>(){
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
                    public void onNext(JSONChange result) {
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
