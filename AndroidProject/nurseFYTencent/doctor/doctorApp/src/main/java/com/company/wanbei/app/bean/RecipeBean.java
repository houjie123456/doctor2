package com.company.wanbei.app.bean;

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
    private String visitId;
    private String visitName;
    private String visitMobile;
    private String visitHeadUrl;
    private String patientHeadUrl;
    private String visitSex;
    private String visitAge;
    private ArrayList<DrugBean> drugTable;
    private String imgUrl;//电子处方图片路径

    private String checkState;//：审核状态（药师）0.未审核 1.已通过 2.不通过
    private String checkRemark;//：审核意见

    private String recipeSign;//：处方标识1、显示处方笺，2、显示二维码
    private String patientMobile;//：患者手机号

    private String signStatus;//：签名状态
    private String signStatusStr;//：签名状态

    private ArrayList<String> uniqueIdCa;

    private String payState;//支付状态 0.未支付 1.支付成功 3.已退款


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
        return visitId;
    }

    public void setVisitID(String visitID) {
        this.visitId = visitID;
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

    public String getVisitId() {
        return visitId;
    }

    public void setVisitId(String visitId) {
        this.visitId = visitId;
    }

    public String getPatientHeadUrl() {
        return patientHeadUrl;
    }

    public void setPatientHeadUrl(String patientHeadUrl) {
        this.patientHeadUrl = patientHeadUrl;
    }

    public String getCheckState() {
        return checkState;
    }

    public void setCheckState(String checkState) {
        this.checkState = checkState;
    }

    public String getCheckRemark() {
        return checkRemark;
    }

    public void setCheckRemark(String checkRemark) {
        this.checkRemark = checkRemark;
    }

    public String getRecipeSign() {
        return recipeSign;
    }

    public void setRecipeSign(String recipeSign) {
        this.recipeSign = recipeSign;
    }

    public String getPatientMobile() {
        return patientMobile;
    }

    public void setPatientMobile(String patientMobile) {
        this.patientMobile = patientMobile;
    }

    public String getSignStatus() {
        return signStatus;
    }

    public void setSignStatus(String signStatus) {
        this.signStatus = signStatus;
    }

    public String getSignStatusStr() {
        return signStatusStr;
    }

    public void setSignStatusStr(String signStatusStr) {
        this.signStatusStr = signStatusStr;
    }

    public ArrayList<String> getUniqueIdCa() {
        return uniqueIdCa;
    }

    public void setUniqueIdCa(ArrayList<String> uniqueIdCa) {
        this.uniqueIdCa = uniqueIdCa;
    }

    public String getPayState() {
        return payState;
    }

    public void setPayState(String payState) {
        this.payState = payState;
    }
}
