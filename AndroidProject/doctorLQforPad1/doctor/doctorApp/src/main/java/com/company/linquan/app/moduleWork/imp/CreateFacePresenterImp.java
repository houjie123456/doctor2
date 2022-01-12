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

public class CreateFacePresenterImp implements WorkInterface.CreatePresenterInterface {

    private WorkInterface.CreateFaceInterface view;

    public CreateFacePresenterImp(WorkInterface.CreateFaceInterface view) {
        this.view = view;
    }

    @Override
    public void createFace(String faceAddress, String faceAmount, String startTime, String endTime
            , String hourManNumber, String longitude, String latitude, String room) {
        view.showDialog();
        Map<String,String> map = new HashMap<>();
        map.put("personID", ToolSharePerference.getStringData(view.getContext(), C.fileconfig,C.UserID));
        map.put("faceAddress", faceAddress);
        map.put("faceAmount", faceAmount);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("hourManNumber", hourManNumber);
        map.put("longitude", longitude);
        map.put("latitude", latitude);
        map.put("roomNo", room);
        map.put("sign", ToolUtil.getSign(map));
        HttpApi.createFaceDiagnose(map.get("personID"),map.get("faceAddress"),map.get("faceAmount"),map.get("startTime"),map.get("endTime"),map.get("hourManNumber")
                ,map.get("longitude"),map.get("latitude"),map.get("roomNo"),map.get("sign"))
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
