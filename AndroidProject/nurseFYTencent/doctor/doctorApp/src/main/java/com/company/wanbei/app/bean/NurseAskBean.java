package com.company.wanbei.app.bean;

/**
 * Created by YC on 2018/8/4.
 */

public class NurseAskBean {

    private String id;//：咨询id
    private String visitName;//：就诊人姓名
    private String visitSex;//：就诊人性别
    private String patientHeadUrl;//：患者头像
    private String orderId;//：订单编号
    private String startTime;//：发起时间
    private String amount;//：咨询费用
    private String wyyId;

    private String consultCheckState;// ：护理咨询审核状态  1、未申请  2、待审核  3、已通过  4、不通过
    private String consultCheckRemark;// ：护理咨询审核备注

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVisitName() {
        return visitName;
    }

    public void setVisitName(String visitName) {
        this.visitName = visitName;
    }

    public String getVisitSex() {
        return visitSex;
    }

    public void setVisitSex(String visitSex) {
        this.visitSex = visitSex;
    }

    public String getPatientHeadUrl() {
        return patientHeadUrl;
    }

    public void setPatientHeadUrl(String patientHeadUrl) {
        this.patientHeadUrl = patientHeadUrl;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getWyyId() {
        return wyyId;
    }

    public void setWyyId(String wyyId) {
        this.wyyId = wyyId;
    }

    public String getConsultCheckState() {
        return consultCheckState;
    }

    public void setConsultCheckState(String consultCheckState) {
        this.consultCheckState = consultCheckState;
    }

    public String getConsultCheckRemark() {
        return consultCheckRemark;
    }

    public void setConsultCheckRemark(String consultCheckRemark) {
        this.consultCheckRemark = consultCheckRemark;
    }
}
