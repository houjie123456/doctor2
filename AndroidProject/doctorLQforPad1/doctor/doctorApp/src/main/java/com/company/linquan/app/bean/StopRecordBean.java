package com.company.linquan.app.bean;

/**
 * Created by YC on 2018/6/29.
 */

public class StopRecordBean {

    private String faceType;
    private String address;
    private String amount;
    private String startDate;
    private String endDate;
    private String faceNumber;

    public String getFaceType() {
        return faceType;
    }

    public void setFaceType(String faceType) {
        this.faceType = faceType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

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

    public String getFaceNumber() {
        return faceNumber;
    }

    public void setFaceNumber(String faceNumber) {
        this.faceNumber = faceNumber;
    }
}
