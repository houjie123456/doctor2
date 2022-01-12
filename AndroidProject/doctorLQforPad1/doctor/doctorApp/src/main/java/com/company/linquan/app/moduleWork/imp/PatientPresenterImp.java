package com.company.linquan.app.moduleWork.imp;

import android.util.Log;

import com.company.linquan.app.config.C;
import com.company.linquan.app.http.HttpApi;
import com.company.linquan.app.http.JSONPatient;
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

public class PatientPresenterImp implements WorkInterface.PatientPresenterInterface{

    private WorkInterface.PatientInterface view;

    public PatientPresenterImp(WorkInterface.PatientInterface view) {
        this.view = view;
    }

    @Override
    public void getPatient(int page, String stageType) {
        Map<String,String> map = new HashMap<>();
        map.put("personID", ToolSharePerference.getStringData(view.getContext(), C.fileconfig,C.UserID));
        map.put("serverType", "1");
        map.put("stageType", stageType);
        map.put("patientID", "");
        map.put("visitID", "");
        map.put("currPage", page+"");
        map.put("sign", ToolUtil.getSign(map));
        HttpApi.getMyPatientInfo(map.get("personID"),map.get("serverType"),map.get("stageType")
                ,map.get("patientID"),map.get("visitID"),map.get("currPage"),map.get("sign"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JSONPatient>(){
                    @Override
                    public void onCompleted() {
                        Log.i("onCompleted","onCompleted");
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.i("Throwable",e.toString());
                    }
                    @Override
                    public void onNext(JSONPatient result) {
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

    @Override
    public void getMyPatientInfo(String doctorID, String icdName, String patient) {
        Map<String,String> map = new HashMap<>();
        map.put("doctorID", doctorID);
        map.put("icdName", icdName);
        map.put("patient", patient);
        HttpApi.getMyPatientInfo2(map.get("doctorID"),map.get("icdName"),map.get("patient"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JSONPatient>(){
                    @Override
                    public void onCompleted() {
                        Log.i("onCompleted","onCompleted");
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.i("Throwable",e.toString());
                    }
                    @Override
                    public void onNext(JSONPatient result) {
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
