package com.company.wanbei.app.http;

import com.google.gson.annotations.SerializedName;

/**
 * Created by YC on 2017/7/11.
 */

public class JSONInquiryStatus {
    private String code;
    @SerializedName("msgbox")
    private String msgBox;

    private String status;// 图文问诊状态 1空闲；2忙碌


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsgBox() {
        return msgBox;
    }

    public void setMsgBox(String msgBox) {
        this.msgBox = msgBox;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
