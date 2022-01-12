package com.company.linquan.app.http;

import com.company.linquan.app.bean.DiseaseBean;
import com.company.linquan.app.bean.DiseaseGroupBean;
import com.company.linquan.app.bean.DocBean;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by YC on 2018/8/1.
 */

public class JSONDiseaseGroup {
    private String code;
    @SerializedName("msgbox")
    private String msgBox;
    private ArrayList<DiseaseGroupBean> table;

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


    public ArrayList<DiseaseGroupBean> getTable() {
        return table;
    }

    public void setTable(ArrayList<DiseaseGroupBean> table) {
        this.table = table;
    }
}
