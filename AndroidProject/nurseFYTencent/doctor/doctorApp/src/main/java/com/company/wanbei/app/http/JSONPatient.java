package com.company.wanbei.app.http;

import com.tencent.qcloud.tuikit.tuichat.fromApp.bean.DiseaseBean;
import com.company.wanbei.app.bean.InquiryBean;
import com.company.wanbei.app.bean.PatientBean;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by YC on 2018/7/1.
 */

public class JSONPatient {
    private String code;
    @SerializedName("msgbox")
    private String msgBox;
    private String pageCount;
    private String rowCount;
    private ArrayList<PatientBean> table;
    private PatientBean visitJson;
    private ArrayList<DiseaseBean> diseaseArray;

    private PatientBean infoJson;

    private InquiryBean inquiryJson;

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

    public ArrayList<PatientBean> getTable() {
        return table;
    }

    public void setTable(ArrayList<PatientBean> table) {
        this.table = table;
    }

    public PatientBean getVisitJson() {
        return visitJson;
    }

    public void setVisitJson(PatientBean visitJson) {
        this.visitJson = visitJson;
    }

    public ArrayList<DiseaseBean> getDiseaseArray() {
        return diseaseArray;
    }

    public void setDiseaseArray(ArrayList<DiseaseBean> diseaseArray) {
        this.diseaseArray = diseaseArray;
    }

    public PatientBean getInfoJson() {
        return infoJson;
    }

    public void setInfoJson(PatientBean infoJson) {
        this.infoJson = infoJson;
    }

    public InquiryBean getInquiryJson() {
        return inquiryJson;
    }

    public void setInquiryJson(InquiryBean inquiryJson) {
        this.inquiryJson = inquiryJson;
    }
}
