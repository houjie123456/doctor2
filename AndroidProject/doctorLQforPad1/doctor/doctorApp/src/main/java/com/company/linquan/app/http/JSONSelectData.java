package com.company.linquan.app.http;

import com.company.linquan.app.bean.SelectDataBean;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by YC on 2018/7/8.
 */

public class JSONSelectData {
    private String code;
    @SerializedName("msgbox")
    private String msgBox;
    private ArrayList<SelectDataBean> table;

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

    public ArrayList<SelectDataBean> getTable() {
        return table;
    }

    public void setTable(ArrayList<SelectDataBean> table) {
        this.table = table;
    }
}
