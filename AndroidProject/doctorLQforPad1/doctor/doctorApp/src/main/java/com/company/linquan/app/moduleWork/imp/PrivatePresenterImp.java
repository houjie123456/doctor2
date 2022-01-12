package com.company.linquan.app.moduleWork.imp;

import android.util.Log;

import com.company.linquan.app.config.C;
import com.company.linquan.app.http.HttpApi;
import com.company.linquan.app.http.JSONPrivateRecord;
import com.company.linquan.app.moduleWork.WorkInterface;
import com.company.linquan.app.util.ToolSharePerference;
import com.company.linquan.app.util.ToolUtil;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by YC on 2018/7/1.
 */

public class PrivatePresenterImp implements WorkInterface.PrivatePresenterInterface {

    private WorkInterface.PrivateDoctorInterface view;

    public PrivatePresenterImp(WorkInterface.PrivateDoctorInterface view) {
        this.view = view;
    }

    @Override
    public void getPrivateRecord(int page) {
        Map<String,String> map = new HashMap<>();
        map.put("personID", ToolSharePerference.getStringData(view.getContext(), C.fileconfig,C.UserID));
        map.put("currPage", page+"");
        map.put("sign", ToolUtil.getSign(map));
        HttpApi.getPrivateDoctorRecordInfo(map.get("personID"),map.get("currPage"),map.get("sign"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JSONPrivateRecord>(){
                    @Override
                    public void onCompleted() {
                        Log.i("onCompleted","onCompleted");
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.i("Throwable",e.toString());
                    }
                    @Override
                    public void onNext(JSONPrivateRecord result) {
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
