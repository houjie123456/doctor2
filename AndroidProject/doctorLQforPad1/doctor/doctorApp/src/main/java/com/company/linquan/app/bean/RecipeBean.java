package com.company.linquan.app.bean;

import java.util.ArrayList;

/**
 * Created by YC on 2018/7/1.
 */

public class RecipeBean {
    private String id;
    private String doctorName;
    private String doctorHeadUrl;
    private String diagnosisRemark;
    private String createTime;
    private String visitID;
    private String visitName;
    private String visitMobile;
    private String visitHeadUrl;
    private String visitSex;
    private String visitAge;
    private ArrayList<DrugBean> drugTable;
    private String imgUrl;//电子处方图片路径

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDiagnosisRemark() {
        return diagnosisRemark;
    }

    public void setDiagnosisRemark(String diagnosisRemark) {
        this.diagnosisRemark = diagnosisRemark;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getVisitID() {
        return visitID;
    }

    public void setVisitID(String visitID) {
        this.visitID = visitID;
    }

    public String getVisitName() {
        return visitName;
    }

    public void setVisitName(String visitName) {
        this.visitName = visitName;
    }

    public String getVisitMobile() {
        return visitMobile;
    }

    public void setVisitMobile(String visitMobile) {
        this.visitMobile = visitMobile;
    }

    public String getVisitHeadUrl() {
        return visitHeadUrl;
    }

    public void setVisitHeadUrl(String visitHeadUrl) {
        this.visitHeadUrl = visitHeadUrl;
    }

    public ArrayList<DrugBean> getDrugTable() {
        return drugTable;
    }

    public void setDrugTable(ArrayList<DrugBean> drugTable) {
        this.drugTable = drugTable;
    }

    public String getVisitSex() {
        return visitSex;
    }

    public void setVisitSex(String visitSex) {
        this.visitSex = visitSex;
    }

    public String getVisitAge() {
        return visitAge;
    }

    public void setVisitAge(String visitAge) {
        this.visitAge = visitAge;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorHeadUrl() {
        return doctorHeadUrl;
    }

    public void setDoctorHeadUrl(String doctorHeadUrl) {
        this.doctorHeadUrl = doctorHeadUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
