package com.company.linquan.app.moduleWork.imp;

import android.util.Log;

import com.company.linquan.app.config.C;
import com.company.linquan.app.http.HttpApi;
import com.company.linquan.app.http.JSONRecipe;
import com.company.linquan.app.http.JSONVisitRecord;
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

public class PatientInfoPresenterImp implements WorkInterface.PatientInfoPresenterInterface {

    private WorkInterface.PatientInfoInterface view;

    public PatientInfoPresenterImp(WorkInterface.PatientInfoInterface view) {
        this.view = view;
    }

    @Override
    public void getRecipe(String patientId,String visitId) {
        Map<String,String> map = new HashMap<>();
        map.put("personID", ToolSharePerference.getStringData(view.getContext(), C.fileconfig,C.UserID));
        map.put("patientID", patientId);
        map.put("visitID", visitId);
        map.put("currPage", "");
        map.put("sign", ToolUtil.getSign(map));
        HttpApi.getRecipeInfo(map.get("personID"),map.get("patientID"),map.get("visitID")
                ,map.get("currPage"),map.get("sign"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JSONRecipe>(){
                    @Override
                    public void onCompleted() {
                        Log.i("onCompleted","onCompleted");
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.i("Throwable",e.toString());
                    }
                    @Override
                    public void onNext(JSONRecipe result) {
                        Log.i("onNext","onNext");
                        //填充UI
                        if ("1".equals(result.getCode())){
                            view.reloadRecipe(result.getTable());
                        }else {
                            view.showToast(result.getMsgBox());
                        }
                    }
                });
    }

    @Override
    public void getVisitRecord(String patientId,String visitId) {
        Map<String,String> map = new HashMap<>();
        map.put("personID", ToolSharePerference.getStringData(view.getContext(), C.fileconfig,C.UserID));
        map.put("patientID", patientId);
        map.put("visitID", visitId);
        map.put("visitType", "");
        map.put("pageSize", "0");
        map.put("currPage", "");
        map.put("sign", ToolUtil.getSign(map));
        HttpApi.getVisitRecodeInfo(map.get("personID"),map.get("patientID"),map.get("visitID")
                ,map.get("visitType"),map.get("pageSize"),map.get("currPage"),map.get("sign"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JSONVisitRecord>(){
                    @Override
                    public void onCompleted() {
                        Log.i("onCompleted","onCompleted");
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.i("Throwable",e.toString());
                    }
                    @Override
                    public void onNext(JSONVisitRecord result) {
                        Log.i("onNext","onNext");
                        //填充UI
                        if ("1".equals(result.getCode())){
                            view.reloadVisitRecord(result.getTable());
                        }else {
                            view.showToast(result.getMsgBox());
                        }
                    }
                });
    }
}
