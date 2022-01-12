//package com.company.linquan.app.moduleWork.imp;
//
//import android.util.Log;
//
//import com.company.linquan.app.config.C;
//import com.company.linquan.app.http.HttpApi;
//import com.company.linquan.app.http.JSONFaceDiagnose;
//import com.company.linquan.app.http.JSONHomeCare;
//import com.company.linquan.app.http.JSONStopRecord;
//import com.company.linquan.app.moduleWork.WorkInterface;
//import com.company.linquan.app.util.ToolSharePerference;
//import com.company.linquan.app.util.ToolUtil;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import rx.Subscriber;
//import rx.android.schedulers.AndroidSchedulers;
//import rx.schedulers.Schedulers;
//
///**
// * Created by YC on 2018/6/29.
// */
//
//public class StopNursePresenterImp1 implements WorkInterface.StopNursePresenterInterface {
//
//    private WorkInterface.StopNurseInterface view;
//
//    public StopNursePresenterImp1(WorkInterface.StopNurseInterface view) {
//        this.view = view;
//    }
//
//    @Override
//    public void getNurseList() {
//        Map<String,String> map = new HashMap<>();
//        map.put("personID", ToolSharePerference.getStringData(view.getContext(), C.fileconfig,C.UserID));
//        map.put("sign", ToolUtil.getSign(map));
//        HttpApi.getHomeCareList(map.get("personID"),map.get("sign"))
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<JSONHomeCare>(){
//                    @Override
//                    public void onCompleted() {
//                        Log.i("onCompleted","onCompleted");
//                    }
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.i("Throwable",e.toString());
//                    }
//                    @Override
//                    public void onNext(JSONHomeCare result) {
//                        Log.i("onNext","onNext");
//                        //填充UI
//                        if ("1".equals(result.getCode())){
//                            view.reloadNurse(result.getTable());
//                        }else {
//                            view.showToast(result.getMsgBox());
//                        }
//                    }
//                });
//    }
//
//    @Override
//    public void getStopNurseRecordList(String isDayPart, String selectDay) {
//        Map<String,String> map = new HashMap<>();
//        map.put("personID", ToolSharePerference.getStringData(view.getContext(), C.fileconfig,C.UserID));
//        map.put("isDayPart", isDayPart);
//        map.put("selectDay", selectDay);
//        map.put("sign", ToolUtil.getSign(map));
//        HttpApi.getFaceDiagnoseRecord(map.get("personID"),map.get("isDayPart"),map.get("selectDay"),map.get("sign"))
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<JSONStopRecord>(){
//                    @Override
//                    public void onCompleted() {
//                        Log.i("onCompleted","onCompleted");
//                    }
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.i("Throwable",e.toString());
//                    }
//                    @Override
//                    public void onNext(JSONStopRecord result) {
//                        Log.i("onNext","onNext");
//                        //填充UI
//                        if ("1".equals(result.getCode())){
//                            view.reloadStopNurse(result.getTable());
//                        }else {
//                            view.showToast(result.getMsgBox());
//                        }
//                    }
//                });
//    }
//
//    @Override
//    public void stopNurse(String relatID, final String relatType) {
//        view.showDialog();
//        Map<String,String> map = new HashMap<>();
//        map.put("personID", ToolSharePerference.getStringData(view.getContext(), C.fileconfig,C.UserID));
//        map.put("relatID", relatID);
//        map.put("relatType", relatType);
//        map.put("sign", ToolUtil.getSign(map));
//        HttpApi.setStopFaceDiagnose(map.get("personID"),map.get("relatID"),map.get("relatType"),map.get("sign"))
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<JSONStopRecord>(){
//                    @Override
//                    public void onCompleted() {
//                        Log.i("onCompleted","onCompleted");
//                        view.dismissDialog();
//                    }
//                    @Override
//                    public void onError(Throwable e) {
//                        view.dismissDialog();
//                        Log.i("Throwable",e.toString());
//                    }
//                    @Override
//                    public void onNext(JSONStopRecord result) {
//                        Log.i("onNext","onNext");
//                        //填充UI
//                        if ("1".equals(result.getCode())){
//                            if(relatType=="2"){
//                                view.showToast(result.getMsgBox());
//
//                            }else {
//                                view.getStopNurse();
//                            }
//                        }else {
//                            view.showToast(result.getMsgBox());
//                        }
//                    }
//                });
//    }
//}
