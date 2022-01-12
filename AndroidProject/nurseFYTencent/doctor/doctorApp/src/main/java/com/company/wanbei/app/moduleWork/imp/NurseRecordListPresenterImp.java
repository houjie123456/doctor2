package com.company.wanbei.app.moduleWork.imp;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.tencent.qcloud.tuikit.tuichat.fromApp.config.C;
import com.company.wanbei.app.http.HttpApi;
import com.company.wanbei.app.http.JSONNurseServe;
import com.company.wanbei.app.http.JSONNurseService;
import com.company.wanbei.app.moduleWork.WorkInterface;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.ToolSharePerference;

import okhttp3.RequestBody;
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
    public void getNurseServiceList() {
        JSONObject map = new JSONObject();
        map.put("doctorId", ToolSharePerference.getStringData(view.getContext(), C.fileconfig, C.UserID));
        RequestBody requestBody = HttpApi.packageParam(map);
        HttpApi.getNursingService(requestBody)
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
                        if ("0".equals(result.getCode())){
                            view.reloadNurseList(result.getData());
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
    public void getNurseRecordList(String startTime,String endTime,String serviceID,String serviceState,String confirmState,String page) {
        JSONObject map = new JSONObject();
        map.put("doctorId", ToolSharePerference.getStringData(view.getContext(), C.fileconfig, C.UserID));
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("serviceId", serviceID);
        map.put("serviceState", serviceState);
        map.put("checkState", confirmState);
        map.put("page", page);
        RequestBody requestBody = HttpApi.packageParam(map);
        HttpApi.nursingOrderList(requestBody)
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
                        if ("0".equals(result.getCode())){
                            view.reloadNurseRecordList(result.getData());
                        }else {
                            view.showToast(result.getMsgBox());
                        }
                    }
                });
    }


}
