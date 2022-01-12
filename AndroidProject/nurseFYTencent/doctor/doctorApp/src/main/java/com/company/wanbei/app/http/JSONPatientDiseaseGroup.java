package com.company.wanbei.app.http;

import com.company.wanbei.app.bean.PatientGroupDiseaseBean;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by YC on 2018/8/1.
 */

public class JSONPatientDiseaseGroup {
    private String code;
    @SerializedName("msgbox")
    private String msgBox;
    private ArrayList<PatientGroupDiseaseBean> table;

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


    public ArrayList<PatientGroupDiseaseBean> getTable() {
        return table;
    }

    public void setTable(ArrayList<PatientGroupDiseaseBean> table) {
        this.table = table;
    }
}
