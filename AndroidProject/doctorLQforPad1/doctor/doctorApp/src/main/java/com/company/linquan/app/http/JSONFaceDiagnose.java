package com.company.linquan.app.http;

import com.company.linquan.app.bean.FaceDiagnoseBean;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by YC on 2018/6/27.
 */

public class JSONFaceDiagnose {
    private String code;
    @SerializedName("msgbox")
    private String msgBox;
    private ArrayList<FaceDiagnoseBean> table;

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

    public ArrayList<FaceDiagnoseBean> getTable() {
        return table;
    }

    public void setTable(ArrayList<FaceDiagnoseBean> table) {
        this.table = table;
    }
}
