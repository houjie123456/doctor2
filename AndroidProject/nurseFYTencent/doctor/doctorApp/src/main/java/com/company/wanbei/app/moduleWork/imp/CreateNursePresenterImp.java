package com.company.wanbei.app.moduleWork.imp;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.company.wanbei.app.bean.NurseInfoBean;
import com.tencent.qcloud.tuikit.tuichat.fromApp.config.C;
import com.company.wanbei.app.http.HttpApi;
import com.company.wanbei.app.http.JSONBean;
import com.company.wanbei.app.http.JSONNurseInfo;
import com.company.wanbei.app.http.JSONNurseServe;
import com.company.wanbei.app.http.JSONNurseType;
import com.company.wanbei.app.moduleWork.WorkInterface;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.ToolSharePerference;

import java.util.ArrayList;

import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by YC on 2018/6/29.
 */

public class CreateNursePresenterImp implements WorkInterface.CreateNursePresenterInterface {

    private WorkInterface.CreateNurseInterface view;

    public CreateNursePresenterImp(WorkInterface.CreateNurseInterface view) {
        this.view = view;
    }

    /*
    * personID-护士个人ID（必填）
        serviceID-护理服务ID（必填）
        serviceName-护理服务名称
        sortLevel-优先级
        appointStartTime-预约开始时间（必填）
        appointEndTime-预约结束时间（必填）
        appointState-预约状态
        totalNumber-总预约人数
        useNumber-已预约人数
        appointAmount-预约金额
        sign-签名(预留,暂时测试不验证)
    * */
    @Override
    public void createNurse(String serviceID, String serviceName,  String appointStartTime
            , String appointEndTime) {
        view.showDialog();
        JSONObject map = new JSONObject();
        map.put("doctorId", ToolSharePerference.getStringData(view.getContext(), C.fileconfig, C.UserID));
        map.put("serviceId", serviceID);
        map.put("serviceName", serviceName);
//        map.put("sortLevel", sortLevel);
        map.put("startTime", appointStartTime);
        map.put("endTime", appointEndTime);
        RequestBody requestBody = HttpApi.packageParam(map);
        HttpApi.addNursing(requestBody)
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
                        if ("0".equals(result.getCode())){
                            view.showToast(result.getMsgBox());
                            view.finishActivity();
                        }else {
                            view.showToast(result.getMsgBox());
                        }
                    }
                });
    }

    @Override
    public void getNursingTypeInfo(String id) {
        view.showDialog();
        JSONObject map = new JSONObject();
        map.put("id", id);
        RequestBody requestBody = HttpApi.packageParam(map);
        HttpApi.getNursingTypeInfo(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JSONNurseInfo>(){
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
                    public void onNext(JSONNurseInfo result) {
                        Log.i("onNext","onNext");
                        //填充UI
                        if ("0".equals(result.getCode())){
                            ArrayList<NurseInfoBean> sbBuffer = new ArrayList<>();
                            sbBuffer.add(result.getData());
                            view.reloadNurseInfo(sbBuffer);
                        }else {
                            view.showToast(result.getMsgBox());
                        }
                    }
                });
    }


    @Override
    public void getNurseType() {
        view.showDialog();
        JSONObject map = new JSONObject();
        RequestBody requestBody = HttpApi.packageParam(map);
        HttpApi.getNurseType(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JSONNurseType>(){
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
                    public void onNext(JSONNurseType result) {
                        Log.i("onNext","onNext");
                        //填充UI
                        if ("0".equals(result.getCode())){
                            view.showTypeDialog(result.getData());
                        }else {
                            view.showToast(result.getMsgBox());
//                            view.reloadList(new ArrayList<NurseTypeBean>());
                        }
                    }
                });
    }

    @Override
    public void getNurseService(String docID,String typeID) {
        view.showDialog();
        JSONObject map = new JSONObject();
        map.put("doctorId", docID);
        map.put("typeId", typeID);
        RequestBody requestBody = HttpApi.packageParam(map);
        HttpApi.getNursingType(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JSONNurseServe>(){
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
                    public void onNext(JSONNurseServe result) {
                        Log.i("onNext","onNext");
                        //填充UI
                        if ("0".equals(result.getCode())){
                            view.showServiceDialog(result.getData());
                        }else {
                            view.showToast(result.getMsgBox());
//                            view.reloadList(new ArrayList<NurseServeBean>());
                        }
                    }
                });
    }
}
