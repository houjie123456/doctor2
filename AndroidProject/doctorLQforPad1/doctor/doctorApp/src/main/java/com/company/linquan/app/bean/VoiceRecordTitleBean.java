package com.company.linquan.app.bean;

import java.util.ArrayList;

/**
 * Created by YC on 2018/6/29.
 */

public class VoiceRecordTitleBean {
    private String startDate;
    private String endDate;
    private String amount;
    private String agoraChannel;
    private String agoraUser;
    private String peopleNum;
    private ArrayList<VoiceRecordPersonBean> childTable;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public ArrayList<VoiceRecordPersonBean> getChildTable() {
        return childTable;
    }

    public void setChildTable(ArrayList<VoiceRecordPersonBean> childTable) {
        this.childTable = childTable;
    }

    public String getAgoraChannel() {
        return agoraChannel;
    }

    public void setAgoraChannel(String agoraChannel) {
        this.agoraChannel = agoraChannel;
    }

    public String getAgoraUser() {
        return agoraUser;
    }

    public void setAgoraUser(String agoraUser) {
        this.agoraUser = agoraUser;
    }

    public String getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(String peopleNum) {
        this.peopleNum = peopleNum;
    }
}
