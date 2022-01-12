package com.company.wanbei.app.http;

import com.tencent.qcloud.tuikit.tuichat.fromApp.bean.DiseaseBean;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by YC on 2018/8/1.
 */

public class JSONDiseaseGroupList {
    private String code;
    @SerializedName("msgbox")
    private String msgBox;
    private ArrayList<DiseaseBean> table;

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


    public ArrayList<DiseaseBean> getTable() {
        return table;
    }

    public void setTable(ArrayList<DiseaseBean> table) {
        this.table = table;
    }
}
