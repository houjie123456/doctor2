package com.company.wanbei.app.http;

import com.company.wanbei.app.bean.StopRecordBean;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by YC on 2018/6/29.
 */

public class JSONStopRecord {

    private String code;
    @SerializedName("msgbox")
    private String msgBox;
    private ArrayList<StopRecordBean> table;

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

    public ArrayList<StopRecordBean> getTable() {
        return table;
    }

    public void setTable(ArrayList<StopRecordBean> table) {
        this.table = table;
    }
}
