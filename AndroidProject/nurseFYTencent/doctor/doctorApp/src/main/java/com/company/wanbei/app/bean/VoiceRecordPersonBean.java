package com.company.wanbei.app.bean;

/**
 * Created by YC on 2018/6/29.
 */

public class VoiceRecordPersonBean {

    private String accountName;
    private String visitSex;
    private String state;
    private String stateStr;
    private String headUrl;
    private String visitAge;
    private String visitMobile;
    private String visitName;
//    inquiryID：预约ID
//    checkState：审核状态值
//    checkStateStr：审核状态（文字）
    private String checkState;
    private String checkStateStr;
    private String bespeakId;
    private String payState;
    private String payStateStr;

    private String visitId;
    private String isFirstVisitState;

    private String inquiryId;


    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getVisitSex() {
        return visitSex;
    }

    public void setVisitSex(String visitSex) {
        this.visitSex = visitSex;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStateStr() {
        return stateStr;
    }

    public void setStateStr(String stateStr) {
        this.stateStr = stateStr;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getVisitAge() {
        return visitAge;
    }

    public void setVisitAge(String visitAge) {
        this.visitAge = visitAge;
    }

    public String getVisitMobile() {
        return visitMobile;
    }

    public void setVisitMobile(String visitMobile) {
        this.visitMobile = visitMobile;
    }

    public String getVisitName() {
        return visitName;
    }

    public void setVisitName(String visitName) {
        this.visitName = visitName;
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

    public String getBespeakId() {
        return bespeakId;
    }

    public void setBespeakId(String bespeakId) {
        this.bespeakId = bespeakId;
    }

    public String getPayState() {
        return payState;
    }

    public void setPayState(String payState) {
        this.payState = payState;
    }

    public String getPayStateStr() {
        return payStateStr;
    }

    public void setPayStateStr(String payStateStr) {
        this.payStateStr = payStateStr;
    }

    public String getVisitId() {
        return visitId;
    }

    public void setVisitId(String visitId) {
        this.visitId = visitId;
    }

    public String getIsFirstVisitState() {
        return isFirstVisitState;
    }

    public void setIsFirstVisitState(String isFirstVisitState) {
        this.isFirstVisitState = isFirstVisitState;
    }

    public String getInquiryId() {
        return inquiryId;
    }

    public void setInquiryId(String inquiryId) {
        this.inquiryId = inquiryId;
    }
}
