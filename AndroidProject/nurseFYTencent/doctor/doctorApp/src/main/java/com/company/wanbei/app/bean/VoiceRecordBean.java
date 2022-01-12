package com.company.wanbei.app.bean;

/**
 * Created by YC on 2018/6/29.
 */

public class VoiceRecordBean {
    private String startDate;
    private String endDate;
    private String amount;
    private String childTable;
    private String name;
    private String headUrl;
    private String mobile;
    private boolean isShow;

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

    public String getChildTable() {
        return childTable;
    }

    public void setChildTable(String childTable) {
        this.childTable = childTable;
    }

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

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }
}
