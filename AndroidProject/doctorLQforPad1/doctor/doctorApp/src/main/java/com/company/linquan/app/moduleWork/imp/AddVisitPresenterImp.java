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
 * Created by YC on 2018/7/21.
 */

public class AddVisitPresenterImp implements WorkInterface.AddVisitPresenterInterface {

    private WorkInterface.AddVisitInterface view;
    public AddVisitPresenterImp(WorkInterface.AddVisitInterface view) {
        this.view = view;
    }

    @Override
    public void addVisit(String patientId,String visitId,String type,String remark) {
        view.showDialog();
        Map<String,String> map = new HashMap<>();
        map.put("personID", ToolSharePerference.getStringData(view.getContext(), C.fileconfig,C.UserID));
        map.put("patientID", patientId);
        map.put("visitID", visitId);
        map.put("visitRemark", remark);
        map.put("visitType", type);
        map.put("sign", ToolUtil.getSign(map));
        HttpApi.addVisitRecodeInfo(map.get("personID"),map.get("patientID"),map.get("visitID")
                ,map.get("visitRemark"),map.get("visitType"),map.get("sign"))
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
