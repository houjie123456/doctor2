package com.company.linquan.app.moduleWork.imp;

import android.util.Log;

import com.company.linquan.app.bean.NurseListBean;
import com.company.linquan.app.config.C;
import com.company.linquan.app.http.HttpApi;
import com.company.linquan.app.http.JSONBean;
import com.company.linquan.app.http.JSONHomeCare;
import com.company.linquan.app.http.JSONNurseList;
import com.company.linquan.app.http.JSONStopRecord;
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

public class StopNursePresenterImp implements WorkInterface.StopNursePresenterInterface{

    private WorkInterface.StopNurseInterface view;


    public StopNursePresenterImp(WorkInterface.StopNurseInterface view) {
        this.view = view;
    }

    /*
    http://ip/doctorProject/manageV2.0/getHomeCareList
    传输参数：
    personID-护士个人ID（必填）
    sign-签名(预留,暂时测试不验证)

    返回参数：
    code-1.成功 0.失败
    msgbox-信息说明
    dateStr-日期
    weekStr-星期
    personID-护士ID
    morning-上午已预约人数
    afternoon-下午已预约人数
    peopleMorning-上午可预约人数
    peopleAfternoon-下午可预约人数
     */
    @Override
    public void getNurseList() {
        Map<String,String> map = new HashMap<>();
        map.put("personID", ToolSharePerference.getStringData(view.getContext(), C.fileconfig,C.UserID));
        map.put("sign", ToolUtil.getSign(map));
        HttpApi.getHomeCareList(map.get("personID"),map.get("sign"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JSONHomeCare>(){
                    @Override
                    public void onCompleted() {
                        Log.i("onCompleted","onCompleted");
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.i("Throwable",e.toString());
                    }
                    @Override
                    public void onNext(JSONHomeCare result) {
                        Log.i("onNext","onNext");
                        //填充UI
                        if ("1".equals(result.getCode())){
                            view.reloadNurse(result.getTable());
                        }else {
                            view.showToast(result.getMsgBox());
                        }
                    }
                });
    }

/*
personID-护士个人ID（必填）
dateStr-日期（必填）
type-上午或下午（1上午，2下午）（必填）
sign-签名(预留,暂时测试不验证)

返回参数：
code-1.成功 0.失败
msgbox-信息说明
id-护理服务ID
myName-护士姓名
typeName-护理类型
serviceName-护理服务名称
sortLevel-优先级
createTime-添加时间
appointStartTime-预约开始时间
appointEndTime-预约结束时间
appointState-预约状态   （ 0.未预约 1.预约中）
totalNumber-总预约人数
useNumber-已预约人数
appointAmount-预约金额
 */
    @Override
    public void getStopNurseRecordList(String dateStr,String type) {
        Map<String,String> map = new HashMap<>();
        map.put("personID", ToolSharePerference.getStringData(view.getContext(), C.fileconfig,C.UserID));
        map.put("dateStr", dateStr);
        map.put("type", type);
        map.put("sign", ToolUtil.getSign(map));
        HttpApi.getNursingTypeList(map.get("personID"),map.get("dateStr"),map.get("type"),map.get("sign"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JSONNurseList>(){
                    @Override
                    public void onCompleted() {
                        Log.i("onCompleted","onCompleted");
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.i("Throwable",e.toString());
                    }
                    @Override
                    public void onNext(JSONNurseList result) {
                        Log.i("onNext","onNext");
                        //填充UI
                        if ("1".equals(result.getCode())){
                            ArrayList<NurseListBean> list = new ArrayList<>();
                            for (NurseListBean bean:
                                 result.getTable()) {
                                NurseListBean object = new NurseListBean();
                                object.setAppointAmount(bean.getAppointAmount());
                                object.setAppointEndTime(bean.getAppointEndTime());
                                object.setAppointStartTime(bean.getAppointStartTime());
                                object.setAppointState(bean.getAppointState());
                                object.setCreateTime(bean.getCreateTime());
                                object.setId(bean.getId());
                                object.setMyName(bean.getMyName());
                                object.setServiceName(bean.getServiceName());
                                object.setSortLevel(bean.getSortLevel());
                                object.setTotalNumber(bean.getTotalNumber());
                                object.setTypeName(bean.getTypeName());
                                object.setUseNumber(bean.getUseNumber());
                                list.add(object);
                            }

                            view.reloadNurseList(list);
                        }else {
                            view.showToast(result.getMsgBox());
                        }
                    }
                });
    }

    @Override
    public void stopNurse(String id) {
        view.showDialog();
        Map<String,String> map = new HashMap<>();
        map.put("id", id);
        map.put("sign", ToolUtil.getSign(map));
        HttpApi.deleteNursing(map.get("id"),map.get("sign"))
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
}
