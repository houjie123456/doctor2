package com.company.wanbei.app.http;

import com.company.wanbei.app.bean.FaceRecordTitleBean;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by YC on 2018/6/29.
 */

public class JSONFaceRecord {

    private String code;
    @SerializedName("msgbox")
    private String msgBox;
    private ArrayList<FaceRecordTitleBean> table;

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

    public ArrayList<FaceRecordTitleBean> getTable() {
        return table;
    }

    public void setTable(ArrayList<FaceRecordTitleBean> table) {
        this.table = table;
    }
}
