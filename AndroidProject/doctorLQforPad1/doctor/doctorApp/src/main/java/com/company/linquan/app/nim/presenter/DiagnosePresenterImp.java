package com.company.linquan.app.nim.presenter;

import android.util.Log;

import com.company.linquan.app.http.HttpApi;
import com.company.linquan.app.http.JSONBean;
import com.company.linquan.app.http.JSONFirstAsk;
import com.company.linquan.app.http.JSONMessageList;
import com.company.linquan.app.moduleWork.WorkInterface;
import com.company.linquan.app.nim.ConversationInterface;
import com.company.linquan.app.util.ToolUtil;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DiagnosePresenterImp implements WorkInterface.DiagnosePresenterInterface {

    private WorkInterface.DiagnoseInterface view;
    public DiagnosePresenterImp(WorkInterface.DiagnoseInterface view) {
        this.view = view;
    }

//    @Override
//    public void getFirstAskInfo(String id) {
//        Map<String,String> map = new HashMap<>();
//        map.put("inquiryId", id);
//        map.put("sign", ToolUtil.getSign(map));
//        HttpApi.getInquiryDetailById(map.get("inquiryId"),map.get("sign"))
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<JSONFirstAsk>(){
//                    @Override
//                    public void onCompleted() {
//                        Log.i("onCompleted","onCompleted");
//                    }
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.i("Throwable",e.toString());
//                    }
//                    @Override
//                    public void onNext(JSONFirstAsk result) {
//                        Log.i("onNext","onNext");
//                        //填充UI
//                        if ("1".equals(result.getCode())){
//                            view.reloadInfo(result);
//                        }else {
//                            view.showToast(result.getMsgBox());
//                        }
//                    }
//                });
//    }

    @Override
    public void getMessage(String wyyID) {
        Map<String,String> map = new HashMap<>();
        map.put("wyyID", wyyID);
        HttpApi.getMessageList(map.get("wyyID"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JSONMessageList>(){
                    @Override
                    public void onCompleted() {
                        Log.i("onCompleted","onCompleted");
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.i("Throwable",e.toString());
                    }
                    @Override
                    public void onNext(JSONMessageList result) {
                        Log.i("onNext","onNext");
                        //填充UI
                        if ("1".equals(result.getCode())){
                            view.reloadMessage(result.getChatRecord(),result.getPatient());
                        }else {
                            view.showToast(result.getMsgBox());
                        }
                    }
                });
    }

    @Override
    public void addGroupInfo(String groupid,String icdName) {
        Map<String,String> map = new HashMap<>();
        map.put("groupid", groupid);
        map.put("icdName", icdName);
        HttpApi.addGroupInfo(map.get("groupid"),map.get("icdName"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JSONBean>(){
                    @Override
                    public void onCompleted() {
                        Log.i("onCompleted","onCompleted");
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.i("Throwable",e.toString());
                    }
                    @Override
                    public void onNext(JSONBean result) {
                        Log.i("onNext","onNext");
                        //填充UI
                        if ("1".equals(result.getCode())){
                            view.addGroupInfoResult(result.getCode());
                        }else {
//                            view.showToast(result.getMsgBox());
                            view.addGroupInfoResult(result.getCode());
                        }
                    }
                });
    }

    @Override
    public void adddiseaseGroup() {

    }
}
