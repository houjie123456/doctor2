package com.company.wanbei.app.http;

import com.company.wanbei.app.bean.MessageBean;
import com.company.wanbei.app.bean.PatientBean;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by YC on 2018/8/1.
 */

public class JSONMessageList {
    private String code;
    @SerializedName("msgbox")
    private String msgBox;
    private String pageCount;
    private String rowCount;

    private ArrayList<MessageBean> table;
    private ArrayList<MessageBean> chatRecord;
    private ArrayList<PatientBean> patient;

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

    public String getPageCount() {
        return pageCount;
    }

    public void setPageCount(String pageCount) {
        this.pageCount = pageCount;
    }

    public String getRowCount() {
        return rowCount;
    }

    public void setRowCount(String rowCount) {
        this.rowCount = rowCount;
    }

    public ArrayList<MessageBean> getTable() {
        return table;
    }

    public void setTable(ArrayList<MessageBean> table) {
        this.table = table;
    }

    public ArrayList<MessageBean> getChatRecord() {
        return chatRecord;
    }

    public void setChatRecord(ArrayList<MessageBean> chatRecord) {
        this.chatRecord = chatRecord;
    }

    public ArrayList<PatientBean> getPatient() {
        return patient;
    }

    public void setPatient(ArrayList<PatientBean> patient) {
        this.patient = patient;
    }
}
