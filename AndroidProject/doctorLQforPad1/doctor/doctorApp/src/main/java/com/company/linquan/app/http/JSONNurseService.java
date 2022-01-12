package com.company.linquan.app.http;

import com.company.linquan.app.bean.FaceRecordTitleBean;
import com.company.linquan.app.bean.NurseServiceBean;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by YC on 2018/6/29.
 */

public class JSONNurseService {

    private String code;
    @SerializedName("msgbox")
    private String msgBox;
    private ArrayList<NurseServiceBean> table;

    private ArrayList<String> describe;

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

    public ArrayList<NurseServiceBean> getTable() {
        return table;
    }

    public void setTable(ArrayList<NurseServiceBean> table) {
        this.table = table;
    }

    public ArrayList<String> getDescribe() {
        return describe;
    }

    public void setDescribe(ArrayList<String> describe) {
        this.describe = describe;
    }
}
