package com.company.wanbei.app.http;

import com.company.wanbei.app.bean.ChatDoctorBean;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class JSONChatDoctor {
    private String code;
    @SerializedName("msgbox")
    private String msgBox;
    private ArrayList<ChatDoctorBean> table;

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

    public ArrayList<ChatDoctorBean> getTable() {
        return table;
    }

    public void setTable(ArrayList<ChatDoctorBean> table) {
        this.table = table;
    }
}
