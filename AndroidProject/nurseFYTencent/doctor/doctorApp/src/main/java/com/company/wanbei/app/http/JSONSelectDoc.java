package com.company.wanbei.app.http;

import com.company.wanbei.app.bean.SelectDataBean;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class JSONSelectDoc {
    private String code;
    @SerializedName("msgbox")
    private String msgBox;
    private ArrayList<SelectDataBean> data;

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

    public ArrayList<SelectDataBean> getData() {
        return data;
    }

    public void setData(ArrayList<SelectDataBean> data) {
        this.data = data;
    }
}
