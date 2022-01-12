package com.company.wanbei.app.http;

import com.company.wanbei.app.bean.VoiceGivenBean;
import com.company.wanbei.app.bean.VoiceRecordTitleBean;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by YC on 2018/6/29.
 */

public class JSONVoiceRecord {

    private String code;
    @SerializedName("msgbox")
    private String msgBox;
    private ArrayList<VoiceRecordTitleBean> table;
    private VoiceGivenBean docJson;

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

    public ArrayList<VoiceRecordTitleBean> getTable() {
        return table;
    }

    public void setTable(ArrayList<VoiceRecordTitleBean> table) {
        this.table = table;
    }

    public VoiceGivenBean getDocJson() {
        return docJson;
    }

    public void setDocJson(VoiceGivenBean docJson) {
        this.docJson = docJson;
    }
}
