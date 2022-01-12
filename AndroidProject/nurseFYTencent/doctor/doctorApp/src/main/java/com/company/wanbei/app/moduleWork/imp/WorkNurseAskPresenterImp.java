package com.company.wanbei.app.moduleWork.imp;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tencent.qcloud.tuikit.tuichat.fromApp.config.C;
import com.company.wanbei.app.http.HttpApi;
import com.company.wanbei.app.http.JSONBean;
import com.company.wanbei.app.http.JSONNurseAsk;
import com.company.wanbei.app.http.JSONNurseAskStatus;
import com.company.wanbei.app.http.JSONNurseRecord;
import com.company.wanbei.app.http.JSONPictureEvaluate;
import com.company.wanbei.app.moduleWork.WorkInterface;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.ToolSharePerference;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.ToolUtil;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by YC on 2018/6/27.
 */

public class WorkNurseAskPresenterImp implements WorkInterface.NurseAskPresenterInterface{

    private WorkInterface.NurseAskInterface view;

    public WorkNurseAskPresenterImp(WorkInterface.NurseAskInterface view) {
        this.view = view;
    }

    @Override
    public void getNurseAskList() {
        JSONObject map = new JSONObject();
        map.put("nurseId", ToolSharePerference.getStringData(view.getContext(), C.fileconfig, C.UserID));
        RequestBody requestBody = HttpApi.packageParam(map);
        HttpApi.getNurseAskSchedule(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JSONNurseAsk>(){
                    @Override
                    public void onCompleted() {
                        Log.i("onCompleted","onCompleted");
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.i("Throwable",e.toString());
                    }
                    @Override
                    public void onNext(JSONNurseAsk result) {
                        Log.i("onNext","onNext");
                        //填充UI
                        if ("0".equals(result.getCode())){
                            view.reloadNurseAsk(result.getData());
                        }else {
                            view.showToast(result.getMsgBox());
                        }
                    }
                });
    }

    @Override
    public void getNurseRecordList(String startDate, String endDate, int page) {
        JSONObject map = new JSONObject();
        map.put("nurseId", ToolSharePerference.getStringData(view.getContext(), C.fileconfig, C.UserID));
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        map.put("page", page+"");
        RequestBody requestBody = HttpApi.packageParam(map);
        HttpApi.getNurseRecord(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JSONNurseRecord>(){
                    @Override
                    public void onCompleted() {
                        Log.i("onCompleted","onCompleted");
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.i("Throwable",e.toString());
                    }
                    @Override
                    public void onNext(JSONNurseRecord result) {
                        Log.i("onNext","onNext");
                        //填充UI
                        if ("0".equals(result.getCode())){
                            view.reloadNurseList(result.getData());
                        }else {
                            view.showToast(result.getMsgBox());
                        }
                    }
                });
    }

    @Override
    public void getEvaluateList(String serviceType, int page) {
        Map<String,String> map = new HashMap<>();
        map.put("doctorId", ToolSharePerference.getStringData(view.getContext(), C.fileconfig, C.UserID));
        map.put("serviceType", serviceType);
        map.put("page", page+"");
        map.put("sign", ToolUtil.getSign2(map));

        String json = JSON.toJSONString(map);//map转String
        JSONObject jsonObject = JSON.parseObject(json);//String转json
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),jsonObject.toString());

        HttpApi.findInquiryEvaluation(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JSONPictureEvaluate>(){
                    @Override
                    public void onCompleted() {
                        Log.i("onCompleted","onCompleted");
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.i("Throwable",e.toString());
                    }
                    @Override
                    public void onNext(JSONPictureEvaluate result) {
                        Log.i("onNext","onNext");
                        //填充UI
                        if ("0".equals(result.getCode())){
                            view.reloadEvaluate(result.getTable());
                        }else {
                            view.showToast(result.getMsgBox());
                        }
                    }
                });
    }

    @Override
    public void deleteNurseAsk(String id) {
        JSONObject map = new JSONObject();
        map.put("id", id);
        RequestBody requestBody = HttpApi.packageParam(map);
        HttpApi.deleteScheduling(requestBody)
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
                        if ("0".equals(result.getCode())){
                            view.showToast(result.getMsgBox());
                            view.afterDelete();
                        }else {
                            view.showToast(result.getMsgBox());
                        }
                    }
                });
    }

    @Override
    public void getNurseAskStatus() {
        JSONObject map = new JSONObject();
        map.put("nurseId", ToolSharePerference.getStringData(view.getContext(), C.fileconfig, C.UserID));
        RequestBody requestBody = HttpApi.packageParam(map);
        HttpApi.getNurseAskStatus(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JSONNurseAskStatus>(){
                    @Override
                    public void onCompleted() {
                        Log.i("onCompleted","onCompleted");
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.i("Throwable",e.toString());
                    }
                    @Override
                    public void onNext(JSONNurseAskStatus result) {
                        Log.i("onNext","onNext");
                        //填充UI
                        if ("0".equals(result.getCode())){
                            view.isShowApply(result.getData());
                        }else {
                            view.showToast(result.getMsgBox());
                        }
                    }
                });
    }

    @Override
    public void applyNurseAsk() {
        JSONObject map = new JSONObject();
        map.put("nurseId", ToolSharePerference.getStringData(view.getContext(), C.fileconfig, C.UserID));
        RequestBody requestBody = HttpApi.packageParam(map);
        HttpApi.applyNurseAsk(requestBody)
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
                        if ("0".equals(result.getCode())){
                            view.showApplyDialog();
                        }else {
                            view.showToast(result.getMsgBox());
                        }
                    }
                });
    }
}
