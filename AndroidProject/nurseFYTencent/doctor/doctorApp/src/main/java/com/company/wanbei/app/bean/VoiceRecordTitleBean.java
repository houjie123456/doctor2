package com.company.wanbei.app.bean;

import java.util.ArrayList;

/**
 * Created by YC on 2018/6/29.
 */

public class VoiceRecordTitleBean {

    private String startTime;
    private String endTime;
    private String amount;
    private String timeNumber;
    private String videoId;
    private String faceType;
    private String inquiryType;
    private ArrayList<VoiceRecordPersonBean> childArray;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTimeNumber() {
        return timeNumber;
    }

    public void setTimeNumber(String timeNumber) {
        this.timeNumber = timeNumber;
    }


    public String getFaceType() {
        return faceType;
    }

    public void setFaceType(String faceType) {
        this.faceType = faceType;
    }

    public String getInquiryType() {
        return inquiryType;
    }

    public void setInquiryType(String inquiryType) {
        this.inquiryType = inquiryType;
    }

    public ArrayList<VoiceRecordPersonBean> getChildArray() {
        return childArray;
    }

    public void setChildArray(ArrayList<VoiceRecordPersonBean> childArray) {
        this.childArray = childArray;
    }
}
