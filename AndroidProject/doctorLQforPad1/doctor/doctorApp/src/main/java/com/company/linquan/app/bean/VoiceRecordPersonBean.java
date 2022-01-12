package com.company.linquan.app.bean;

/**
 * Created by YC on 2018/6/29.
 */

public class VoiceRecordPersonBean {
    private String name;
    private String sex;
    private String visitAge;
    private String headUrl;
    private String mobile;
    private String agoraUser;
    private String sDate;
    private String eDate;
    private String amount;
    private String agoraState;
    private String callState;
    private boolean isShow;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }


    public String getCallState() {
        return callState;
    }

    public void setCallState(String callState) {
        this.callState = callState;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public String getVisitAge() {
        return visitAge;
    }

    public void setVisitAge(String visitAge) {
        this.visitAge = visitAge;
    }

    public String getAgoraUser() {
        return agoraUser;
    }

    public void setAgoraUser(String agoraUser) {
        this.agoraUser = agoraUser;
    }

    public String getsDate() {
        return sDate;
    }

    public void setsDate(String sDate) {
        this.sDate = sDate;
    }

    public String geteDate() {
        return eDate;
    }

    public void seteDate(String eDate) {
        this.eDate = eDate;
    }

    public String getAgoraState() {
        return agoraState;
    }

    public void setAgoraState(String agoraState) {
        this.agoraState = agoraState;
    }
}
