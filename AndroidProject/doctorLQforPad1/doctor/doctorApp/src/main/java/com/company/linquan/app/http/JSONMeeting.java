package com.company.linquan.app.http;

import com.company.linquan.app.bean.MeetingBean;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by YC on 2018/6/22.
 */

public class JSONMeeting {
    private String code;
    @SerializedName("msgbox")
    private String msgBox;
    private ArrayList<MeetingBean> table;

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

    public ArrayList<MeetingBean> getTable() {
        return table;
    }

    public void setTable(ArrayList<MeetingBean> table) {
        this.table = table;
    }
}
