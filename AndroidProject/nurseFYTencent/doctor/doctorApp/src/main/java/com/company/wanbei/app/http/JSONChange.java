package com.company.wanbei.app.http;

import com.company.wanbei.app.bean.ChangeBean;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by YC on 2018/7/15.
 */

public class JSONChange {

    private String code;
    @SerializedName("msgbox")
    private String msgBox;
    private ChangeBean infoJson;
    private ArrayList<ChangeBean> table;

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

    public ChangeBean getInfoJson() {
        return infoJson;
    }

    public void setInfoJson(ChangeBean infoJson) {
        this.infoJson = infoJson;
    }

    public ArrayList<ChangeBean> getTable() {
        return table;
    }

    public void setTable(ArrayList<ChangeBean> table) {
        this.table = table;
    }
}
