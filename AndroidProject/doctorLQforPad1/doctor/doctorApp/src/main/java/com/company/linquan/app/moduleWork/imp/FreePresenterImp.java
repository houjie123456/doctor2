package com.company.linquan.app.moduleWork.imp;

import android.util.Log;

import com.company.linquan.app.config.C;
import com.company.linquan.app.http.HttpApi;
import com.company.linquan.app.http.JSONFreeFace;
import com.company.linquan.app.moduleWork.WorkInterface;
import com.company.linquan.app.util.ToolSharePerference;
import com.company.linquan.app.util.ToolUtil;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by YC on 2018/6/30.
 */

public class FreePresenterImp implements WorkInterface.FreePresenterInterface {

    private WorkInterface.FreeInterface view;

    public FreePresenterImp(WorkInterface.FreeInterface view) {
        this.view = view;
    }

    @Override
    public void getFreeList(int page) {
        Map<String,String> map = new HashMap<>();
        map.put("personID", ToolSharePerference.getStringData(view.getContext(), C.fileconfig,C.UserID));
        map.put("currPage", page+"");
        map.put("sign", ToolUtil.getSign(map));
        HttpApi.getFreeList(map.get("personID"),map.get("currPage"),map.get("sign"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JSONFreeFace>(){
                    @Override
                    public void onCompleted() {
                        Log.i("onCompleted","onCompleted");
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.i("Throwable",e.toString());
                    }
                    @Override
                    public void onNext(JSONFreeFace result) {
                        Log.i("onNext","onNext");
                        //填充UI
                        if ("1".equals(result.getCode())){
                            view.reloadFree(result.getTable());
                        }else {
                            view.showToast(result.getMsgBox());
                        }
                    }
                });
    }
}
