package com.company.linquan.app.moduleWork.imp;

import android.util.Log;

import com.company.linquan.app.bean.FaceRecordBean;
import com.company.linquan.app.bean.FaceRecordPersonBean;
import com.company.linquan.app.bean.FaceRecordTitleBean;
import com.company.linquan.app.config.C;
import com.company.linquan.app.http.HttpApi;
import com.company.linquan.app.http.JSONFaceDiagnose;
import com.company.linquan.app.http.JSONFaceRecord;
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
 * Created by YC on 2018/6/27.
 */

public class WorkFacePresenterImp implements WorkInterface.FacePresenterInterface{

    private WorkInterface.FaceInterface view;


    public WorkFacePresenterImp(WorkInterface.FaceInterface view) {
        this.view = view;
    }

    @Override
    public void getFaceList() {
        Map<String,String> map = new HashMap<>();
        map.put("personID", ToolSharePerference.getStringData(view.getContext(), C.fileconfig,C.UserID));
        map.put("sign", ToolUtil.getSign(map));
        HttpApi.getFaceDiagnoseManageList(map.get("personID"),map.get("sign"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JSONFaceDiagnose>(){
                    @Override
                    public void onCompleted() {
                        Log.i("onCompleted","onCompleted");
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.i("Throwable",e.toString());
                    }
                    @Override
                    public void onNext(JSONFaceDiagnose result) {
                        Log.i("onNext","onNext");
                        //填充UI
                        if ("1".equals(result.getCode())){
                            view.reloadFace(result.getTable());
                        }else {
                            view.showToast(result.getMsgBox());
                        }
                    }
                });
    }


    @Override
    public void getFaceRecordList(String id) {
        Map<String,String> map = new HashMap<>();
        map.put("personID", ToolSharePerference.getStringData(view.getContext(), C.fileconfig,C.UserID));
        map.put("faceID", id);
        map.put("sign", ToolUtil.getSign(map));
        HttpApi.getBespeakList(map.get("personID"),map.get("faceID"),map.get("sign"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JSONFaceRecord>(){
                    @Override
                    public void onCompleted() {
                        Log.i("onCompleted","onCompleted");
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.i("Throwable",e.toString());
                    }
                    @Override
                    public void onNext(JSONFaceRecord result) {
                        Log.i("onNext","onNext");
                        //填充UI
                        if ("1".equals(result.getCode())){
                            ArrayList<FaceRecordBean> list = new ArrayList<>();
                            for (FaceRecordTitleBean bean:
                                 result.getTable()) {
                                boolean isFlag = false;
                                for (FaceRecordPersonBean b:
                                     bean.getChildTable()) {
                                    FaceRecordBean object = new FaceRecordBean();
                                    object.setStartDate(bean.getStartDate());
                                    object.setEndDate(bean.getEndDate());
                                    object.setAddress(bean.getAddress());
                                    object.setHeadUrl(b.getHeadUrl());
                                    object.setMobile(b.getMobile());
                                    object.setName(b.getName());
                                    if (!isFlag){
                                        object.setShow(true);
                                        isFlag = false;
                                    }else{
                                        object.setShow(false);
                                    }
                                    list.add(object);
                                }
                            }

                            view.reloadFaceRecord(list);
                        }else {
                            view.showToast(result.getMsgBox());
                        }
                    }
                });
    }
}
