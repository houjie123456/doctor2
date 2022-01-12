package com.company.linquan.app.moduleWork.imp;

import android.util.Log;

import com.company.linquan.app.bean.FaceRecordBean;
import com.company.linquan.app.bean.FaceRecordPersonBean;
import com.company.linquan.app.bean.FaceRecordTitleBean;
import com.company.linquan.app.bean.NurseListBean;
import com.company.linquan.app.bean.NurseServiceBean;
import com.company.linquan.app.config.C;
import com.company.linquan.app.http.HttpApi;
import com.company.linquan.app.http.JSONBean;
import com.company.linquan.app.http.JSONFaceDiagnose;
import com.company.linquan.app.http.JSONFaceRecord;
import com.company.linquan.app.http.JSONHomeCare;
import com.company.linquan.app.http.JSONNurseList;
import com.company.linquan.app.http.JSONNurseService;
import com.company.linquan.app.moduleWork.WorkInterface;
import com.company.linquan.app.util.ToolSharePerference;
import com.company.linquan.app.util.ToolUtil;

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

public class WorkNursePresenterImp implements WorkInterface.NursePresenterInterface{

    private WorkInterface.NurseInterface view;


    public WorkNursePresenterImp(WorkInterface.NurseInterface view) {
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
    public void getNurseRecordList(String dateStr,String type) {
        Map<String,String> map = new HashMap<>();
        map.put("personID", ToolSharePerference.getStringData(view.getContext(), C.fileconfig,C.UserID));
        map.put("dateStr", dateStr);
        map.put("type", type);
        map.put("sign", ToolUtil.getSign(map));
        HttpApi.getPatientList(map.get("personID"),map.get("dateStr"),map.get("type"),map.get("sign"))
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

                            view.reloadNurseRecord(list);
                        }else {
                            view.showToast(result.getMsgBox());
                        }
                    }
                });
    }


}
