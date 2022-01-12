package com.company.linquan.app.http;

import com.company.linquan.app.bean.MeetingBean;
import com.google.gson.annotations.SerializedName;

/**
 * Created by YC on 2018/6/25.
 */

public class JSONMeetingDetail {
    private String code;
    @SerializedName("msgbox")
    private String msgBox;
    private String detailUrl;
    private MeetingBean infoJson;

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

    public MeetingBean getInfoJson() {
        return infoJson;
    }

    public void setInfoJson(MeetingBean infoJson) {
        this.infoJson = infoJson;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }
}
