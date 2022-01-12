package com.company.wanbei.app.bean;

/**
 * Created by YC on 2018/6/30.
 */

public class OperationBean {
    private String bespeakId;//：预约ID
    private String operationTime;//：手术时间
    private String doctorName;//：医生姓名
    private String doctorMobile;//：医生手机号
    private String visitName;//：患者姓名
    private String visitSex;//：性别
    private String visitAge;//：患者年龄
    private String amount;//：费用
    private String bespeakState;//：预约状态1、待审核，2、已预约，3、已取消，4、已拒绝
    private String bespeakStateStr;//：预约状态1、待审核，2、已预约，3、已取消，4、已拒绝
    private String checkState;//：审核状态 0.未审核 1.审核通过 2.审核不通过（删除）
    private String checkStateStr;//：审核状态 0.未审核 1.审核通过 2.审核不通过（删除）
    private String linkMan;//：联系人
    private String linkPhone;//：联系电话
    private String address;//：手术地点

    public String getBespeakId() {
        return bespeakId;
    }

    public void setBespeakId(String bespeakId) {
        this.bespeakId = bespeakId;
    }

    public String getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(String operationTime) {
        this.operationTime = operationTime;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorMobile() {
        return doctorMobile;
    }

    public void setDoctorMobile(String doctorMobile) {
        this.doctorMobile = doctorMobile;
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

    public String getVisitAge() {
        return visitAge;
    }

    public void setVisitAge(String visitAge) {
        this.visitAge = visitAge;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBespeakState() {
        return bespeakState;
    }

    public void setBespeakState(String bespeakState) {
        this.bespeakState = bespeakState;
    }

    public String getBespeakStateStr() {
        return bespeakStateStr;
    }

    public void setBespeakStateStr(String bespeakStateStr) {
        this.bespeakStateStr = bespeakStateStr;
    }

    public String getCheckState() {
        return checkState;
    }

    public void setCheckState(String checkState) {
        this.checkState = checkState;
    }

    public String getCheckStateStr() {
        return checkStateStr;
    }

    public void setCheckStateStr(String checkStateStr) {
        this.checkStateStr = checkStateStr;
    }

    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }

    public String getLinkPhone() {
        return linkPhone;
    }

    public void setLinkPhone(String linkPhone) {
        this.linkPhone = linkPhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
