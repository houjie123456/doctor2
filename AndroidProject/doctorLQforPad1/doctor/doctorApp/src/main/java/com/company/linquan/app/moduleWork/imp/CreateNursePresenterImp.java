package com.company.linquan.app.moduleWork.imp;

import android.util.Base64;
import android.util.Log;

import com.company.linquan.app.bean.NurseInfoBean;
import com.company.linquan.app.bean.NurseToolBean;
import com.company.linquan.app.config.C;
import com.company.linquan.app.http.HttpApi;
import com.company.linquan.app.http.JSONBean;
import com.company.linquan.app.http.JSONNurseInfo;
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
        Map<String,String> map = new HashMap<>();
        map.put("personID", ToolSharePerference.getStringData(view.getContext(), C.fileconfig,C.UserID));
        map.put("serviceID", serviceID);
        map.put("serviceName", serviceName);
//        map.put("sortLevel", sortLevel);
        map.put("appointStartTime", appointStartTime);
        map.put("appointEndTime", appointEndTime);
        map.put("sign", ToolUtil.getSign(map));
        HttpApi.addNursing(map.get("personID"),map.get("serviceID"),map.get("serviceName"),map.get("appointStartTime"),map.get("appointEndTime")
                ,map.get("sign"))
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
        Map<String,String> map = new HashMap<>();
        map.put("id", id);
        map.put("sign", ToolUtil.getSign(map));
        HttpApi.getNursingTypeInfo(map.get("id"),map.get("sign"))
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
                        if ("1".equals(result.getCode())){
                            ArrayList<NurseInfoBean> sbBuffer = new ArrayList<>();
                            ArrayList<NurseToolBean> hosBuffer = new ArrayList<>();

                            for (NurseInfoBean bean:
                                   result.getSbBuffer()) {
                                NurseInfoBean bean1=new NurseInfoBean();
                                bean1.setAppointmentNotice(bean.getAppointmentNotice());
                                bean1.setExpendPrice(bean.getExpendPrice());
                                bean1.setHomeGoodsList(bean.getHomeGoodsList());

                                bean1.setId(bean.getId());
                                bean1.setMaxPlayers(bean.getMaxPlayers());
                                bean1.setIntroduction(bean.getIntroduction());
                                bean1.setRealPrice(bean.getRealPrice());
                                bean1.setRefundRules(bean.getRefundRules());
                                bean1.setServiceName(bean.getServiceName());
                                bean1.setServicePrice(bean.getServicePrice());
                                bean1.setServiceTime(bean.getServiceTime());
//                                bean1.setTabooTips(bean.getTabooTips());
                                String tabooTips = new String(Base64.decode(bean.getTabooTips().getBytes(), Base64.DEFAULT));
                                bean1.setTabooTips(tabooTips);
                                bean1.setUseMans(bean.getUseMans());
//                            for (String s:
//                                    result.getSbBuffer().getHomeGoodsList()) {
//                                object.setStartDate(bean.getStartDate());
//                                object.setEndDate(bean.getEndDate());
//                                object.setAddress(bean.getAddress());
//                                object.setHeadUrl(b.getHeadUrl());
//                                object.setMobile(b.getMobile());
//                                object.setName(b.getName());
//                                list.add(object);
//                            }
                                sbBuffer.add(bean1);

                            }
                            for (NurseToolBean bean:
                                    result.getHosBuffer()) {
                                NurseToolBean bean1=new NurseToolBean();
                                bean1.setExpendNum(bean.getExpendNum());
                                bean1.setGoodsName(bean.getGoodsName());
                                bean1.setSpecs(bean.getSpecs());
                                hosBuffer.add(bean1);
                            }

                            view.reloadNurseInfo(sbBuffer,hosBuffer);
                        }else {
                            view.showToast(result.getMsgBox());
                        }
                    }
                });
    }
}
