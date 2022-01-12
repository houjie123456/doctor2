package com.company.linquan.app.http;

import com.company.linquan.app.bean.DiseaseBean;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by YC on 2018/8/1.
 */

public class JSONDiseaseList {
    private String code;
    @SerializedName("msgbox")
    private String msgBox;
    private ArrayList<DiseaseBean> diseaseList;

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

    public ArrayList<DiseaseBean> getDiseaseList() {
        return diseaseList;
    }

    public void setDiseaseList(ArrayList<DiseaseBean> diseaseList) {
        this.diseaseList = diseaseList;
    }
}
