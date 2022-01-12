package com.company.wanbei.app.http;

import com.company.wanbei.app.bean.OutHospitalBean;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by YC on 2018/8/4.
 */

public class JSONOutHospital {
    private String code;
    @SerializedName("msgbox")
    private String msgBox;
    private ArrayList<OutHospitalBean> infoJson;

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

    public ArrayList<OutHospitalBean> getTable() {
        return infoJson;
    }

    public void setTable(ArrayList<OutHospitalBean> infoJson) {
        this.infoJson = infoJson;
    }
}
