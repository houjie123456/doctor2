package com.company.linquan.app.moduleWork.imp;

import android.util.Log;

import com.company.linquan.app.bean.AddressBean;
import com.company.linquan.app.config.C;
import com.company.linquan.app.http.HttpApi;
import com.company.linquan.app.http.JSONAddress;
import com.company.linquan.app.http.JSONBean;
import com.company.linquan.app.http.JSONDiseaseGroup;
import com.company.linquan.app.http.JSONDiseaseGroupList;
import com.company.linquan.app.http.JSONDiseaseList;
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

public class PatientGroupPresenterImp implements WorkInterface.PatientGroupPresenterInterface {
    private WorkInterface.DiseaseListInterface view;

    public PatientGroupPresenterImp(WorkInterface.DiseaseListInterface view) {
        this.view = view;
    }

    @Override
    public void getDiseaseGroup(String docID) {
        view.showDialog();
        Map<String,String> map = new HashMap<>();
        map.put("docID",docID);
//        map.put("sign", ToolUtil.getSign(map));
        HttpApi.getDiseaseGroup(map.get("docID"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JSONDiseaseGroup>(){
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
                    public void onNext(JSONDiseaseGroup result) {
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
    public void getDisease(String docID) {
        view.showDialog();
        Map<String,String> map = new HashMap<>();
        map.put("docID",docID);
//        map.put("sign", ToolUtil.getSign(map));
        HttpApi.getDiseaseGroupList(map.get("docID"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JSONDiseaseGroupList>(){
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
                    public void onNext(JSONDiseaseGroupList result) {
                        Log.i("onNext","onNext");
                        //填充UI
                        if ("1".equals(result.getCode())){
                            view.reloadDiseaseList(result.getTable());
                        }else {
                            view.showToast(result.getMsgBox());
                        }
                    }
                });
    }

    @Override
    public void getDiseaseList(String large) {
        view.showDialog();
        Map<String,String> map = new HashMap<>();
        map.put("large",large);
//        map.put("sign", ToolUtil.getSign(map));
        HttpApi.findDisease(map.get("large"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JSONDiseaseList>(){
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
                    public void onNext(JSONDiseaseList result) {
                        Log.i("onNext","onNext");
                        //填充UI
                        if ("1".equals(result.getCode())){
                            view.reloadDiseaseList(result.getDiseaseList());
                        }else {
                            view.showToast(result.getMsgBox());
                        }
                    }
                });
    }

    @Override
    public void checkDisease(String docID, String icdName) {
        view.showDialog();
        Map<String,String> map = new HashMap<>();
        map.put("docID",docID);
        map.put("icdName", icdName);
        HttpApi.checkDisease(map.get("docID"),map.get("icdName"))
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
                            view.checkType(result.getCheckType());
                        }else {
                            view.showToast(result.getMsgBox());
                        }
                    }
                });
    }

    @Override
    public void updateDiseaseGroup(String docID, String groupName, String id) {
        view.showDialog();
        Map<String,String> map = new HashMap<>();
        map.put("docID", docID);
        map.put("groupName", groupName);
        map.put("id", id);
        HttpApi.createDisease(map.get("docID"),map.get("groupName"),map.get("id"))
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
                            view.showToast(result.getMsgBox());
                            view.reload();
                        }else {
                            view.showToast(result.getMsgBox());
                        }
                    }
                });
    }

    @Override
    public void deleteDiseaseGroup(String id) {
        view.showDialog();
        Map<String,String> map = new HashMap<>();
        map.put("id", id);
        HttpApi.deleteDisease(map.get("id"))
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
                            view.showToast(result.getMsgBox());
                            view.reload();
                        }else {
                            view.showToast(result.getMsgBox());
                        }
                    }
                });
    }
}
