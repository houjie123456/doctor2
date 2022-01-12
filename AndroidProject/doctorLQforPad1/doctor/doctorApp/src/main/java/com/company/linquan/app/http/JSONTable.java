package com.company.linquan.app.http;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by YC on 2018/6/22.
 */

public class JSONTable {
    private String code;
    @SerializedName("msgbox")
    private String msgBox;
    private ArrayList<Object> table;

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

    public ArrayList<Object> getTable() {
        return table;
    }

    public void setTable(ArrayList<Object> table) {
        this.table = table;
    }
}
