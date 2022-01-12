package com.company.linquan.app.moduleWork.imp;

import android.util.Log;

import com.company.linquan.app.bean.NurseServiceBean;
import com.company.linquan.app.config.C;
import com.company.linquan.app.http.HttpApi;
import com.company.linquan.app.http.JSONNurseServe;
import com.company.linquan.app.http.JSONNurseService;
import com.company.linquan.app.moduleWork.WorkInterface;
import com.company.linquan.app.util.ToolSharePerference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by YC on 2018/6/27.
 */

public class NurseRecordListPresenterImp implements WorkInterface.NurseRecordListPresenterInterface{

    private WorkInterface.NurseServiceListInterface view;


    public NurseRecordListPresenterImp(WorkInterface.NurseServiceListInterface view) {
        this.view = view;
    }

    @Override
    public void getNurseServiceList(String personID) {
        Map<String,String> map = new HashMap<>();
        map.put("personID", personID);
        HttpApi.getNursingService(map.get("personID"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JSONNurseServe>(){
                    @Override
                    public void onCompleted() {
                        Log.i("onCompleted","onCompleted");
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.i("Throwable",e.toString());
                    }
                    @Override
                    public void onNext(JSONNurseServe result) {
                        Log.i("onNext","onNext");
                        //填充UI
                        if ("1".equals(result.getCode())){
                            view.reloadNurseList(result.getTable());
                        }else {
                            view.showToast(result.getMsgBox());
                        }
                    }
                });
    }
/*
personID-护理人员ID（必填）
startTime- 开始时间
endTime-结束时间
serviceID-护理服务ID
serviceState-服务状态 0.未开始 1.已出发 2.取消 3.结束
page-页码

返回参数：
code-1.成功 0.失败
msgbox-信息说明
table ---
id-患者预约信息id
patientName-就诊人姓名
phonenum-联系方式
appointstarttime-预约开始时间
appointendtime-预约结束时间
address-患者地址
amount-金额
paystate-支付状态（0.未支付 1.已支付 2.支付成功 3.退款）
servicestate-服务状态（0.未开始 1.已出发 2.取消 3.结束）
orderid-订单号
illnessdescr-病情描述
sex-就诊人性别 1男 2女
birthdate-出生日期
idcardno-身份证号
age-年龄
idCardFront-身份证正面
idCardAgainst-身份证反面
describe：-描述图片（多张，最多9张）字符串用 逗号   分割
checkState - 审核状态 0.未审核 1.护士审核通过 2.护士审核不通过 3.护理部审核通过 4.护理部审核不通过
checkRemark -审核备注（新加字段）

personID-护理人员ID（必填）
startTime- 开始时间
endTime-结束时间
serviceID-护理服务ID
serviceState-服务状态 0.未开始 1.已出发 2.取消 3.结束
page-页码
 */
    @Override
    public void getNurseRecordList(String startTime,String endTime,String serviceID,String serviceState,String page) {
        Map<String,String> map = new HashMap<>();
        map.put("personID", ToolSharePerference.getStringData(view.getContext(), C.fileconfig, C.UserID));
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("serviceID", serviceID);
        map.put("serviceState", serviceState);
        map.put("page", page);
        HttpApi.nursingOrderList(map.get("personID"),map.get("startTime"),map.get("endTime"),map.get("serviceID"),map.get("serviceState"),map.get("page"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JSONNurseService>(){
                    @Override
                    public void onCompleted() {
                        Log.i("onCompleted","onCompleted");
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.i("Throwable",e.toString());
                    }
                    @Override
                    public void onNext(JSONNurseService result) {
                        Log.i("onNext","onNext");
                        //填充UI
                        if ("1".equals(result.getCode())){
                            ArrayList<NurseServiceBean> list = new ArrayList<>();
                            boolean isFlag = false;

                            for (NurseServiceBean bean:
                                 result.getTable()) {
                                NurseServiceBean object = new NurseServiceBean();

                                object.setAccountID(bean.getAccountID());
                                object.setId(bean.getId());
                                object.setAddress(bean.getAddress());
                                object.setAmount(bean.getAmount());
                                object.setAppointendtime(bean.getAppointendtime());
                                object.setAppointstarttime(bean.getAppointstarttime());
                                object.setOrderid(bean.getOrderid());
                                object.setPatientName(bean.getPatientName());
                                object.setPaystate(bean.getPaystate());
                                object.setPhonenum(bean.getPhonenum());
                                object.setAge(bean.getAge());
                                object.setServicestate(bean.getServicestate());
                                object.setIllnessdescr(bean.getIllnessdescr());
                                object.setSex(bean.getSex());
                                object.setBirthdate(bean.getBirthdate());
                                object.setIdcardno(bean.getIdcardno());
                                object.setIdCardFront(bean.getIdCardFront());
                                object.setIdCardAgainst(bean.getIdCardAgainst());
                                object.setCheckState(bean.getCheckState());
                                object.setCheckRemark(bean.getCheckRemark());

                                String str2=bean.getDescribe().replace(" ", "");//去掉所用空格
                                ArrayList<String> arr=new ArrayList<>(Arrays.asList(str2.split(",")));
                                object.setDescribes(arr);
//                                for(String s:arr){
//                                    object.getDescribes().add(s);
//                                }

                                if (!isFlag){
                                    object.setShow(true);
                                    isFlag = false;
                                }else{
                                    object.setShow(false);
                                }

                                list.add(object);
                            }

                            view.reloadNurseRecordList(list);
                        }else {
                            view.showToast(result.getMsgBox());
                        }
                    }
                });
    }


}
