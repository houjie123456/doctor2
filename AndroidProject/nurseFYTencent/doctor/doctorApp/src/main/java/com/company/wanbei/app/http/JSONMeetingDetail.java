package com.company.wanbei.app.http;

import com.company.wanbei.app.bean.MeetingBean;
import com.google.gson.annotations.SerializedName;

/**
 * Created by YC on 2018/6/25.
 */

public class JSONMeetingDetail {
    private String code;
    @SerializedName("msgbox")
    private String msgBox;
    private MeetingBean table;

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

    public MeetingBean getTable() {
        return table;
    }

    public void setTable(MeetingBean table) {
        this.table = table;
    }
}
