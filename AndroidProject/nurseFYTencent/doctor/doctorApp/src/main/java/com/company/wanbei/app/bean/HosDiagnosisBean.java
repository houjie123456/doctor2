package com.company.wanbei.app.bean;

import java.util.ArrayList;

/**
 * Created by YC on 2018/8/4.
 */

public class HosDiagnosisBean {

//    visitingCardNo : 患者就诊卡号
//    hisPatientId : HIS系统患者登记号
//    admid : 就诊号
//    visitdate : 就诊时间 yyyy-MM-dd
//    departName : 就诊科室
//    diseaseNameList : 诊断列表，类型：List<String>

    private String visitingCardNo;
    private String hisPatientId;
    private String admid;
    private String visitdate;
    private String departName;
    private ArrayList<String> diseaseNameList;

    private boolean isSelected;

    public String getVisitingCardNo() {
        return visitingCardNo;
    }

    public void setVisitingCardNo(String visitingCardNo) {
        this.visitingCardNo = visitingCardNo;
    }

    public String getHisPatientId() {
        return hisPatientId;
    }

    public void setHisPatientId(String hisPatientId) {
        this.hisPatientId = hisPatientId;
    }

    public String getAdmid() {
        return admid;
    }

    public void setAdmid(String admid) {
        this.admid = admid;
    }

    public String getVisitdate() {
        return visitdate;
    }

    public void setVisitdate(String visitdate) {
        this.visitdate = visitdate;
    }

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    public ArrayList<String> getDiseaseNameList() {
        return diseaseNameList;
    }

    public void setDiseaseNameList(ArrayList<String> diseaseNameList) {
        this.diseaseNameList = diseaseNameList;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
