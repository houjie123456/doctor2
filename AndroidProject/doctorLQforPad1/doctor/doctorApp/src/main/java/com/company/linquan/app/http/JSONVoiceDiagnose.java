package com.company.linquan.app.http;

import com.company.linquan.app.bean.VoiceDiagnoseBean;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by YC on 2018/6/27.
 */

public class JSONVoiceDiagnose {
    private String code;
    @SerializedName("msgbox")
    private String msgBox;
    private ArrayList<VoiceDiagnoseBean> table;

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

    public ArrayList<VoiceDiagnoseBean> getTable() {
        return table;
    }

    public void setTable(ArrayList<VoiceDiagnoseBean> table) {
        this.table = table;
    }
}
