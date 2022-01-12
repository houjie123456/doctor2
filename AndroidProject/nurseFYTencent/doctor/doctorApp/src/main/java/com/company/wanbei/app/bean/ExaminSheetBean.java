package com.company.wanbei.app.bean;

/**
 * Created by YC on 2018/7/15.
 */

public class ExaminSheetBean {
//    visitId：就诊人Id
//    visitName：就诊人姓名
//    visitSex：就诊人性别
//    visitAge：就诊人年龄
//    hisPatientId：HIS系统患者登记号
//    tsRowId ：检验报告的rowid
//    tsName ：检验医嘱名称
//    specimen ：检验标本
//    collDT ：采血时间
//    recDT ：接收时间
//    authDT ：审核时间
//    reqLoc ：申请科室
//    status ：报告状态
    private String visitId;
    private String visitName;
    private String visitSex;
    private String hisPatientId;
    private String visitAge;
    private String tsRowId;
    private String tsName;
    private String specimen;
    private String collDT;
    private String recDT;
    private String authDT;
    private String reqLoc;
    private String status;

    public String getVisitId() {
        return visitId;
    }

    public void setVisitId(String visitId) {
        this.visitId = visitId;
    }

    public String getVisitName() {
        return visitName;
    }

    public void setVisitName(String visitName) {
        this.visitName = visitName;
    }

    public String getVisitSex() {
        return visitSex;
    }

    public void setVisitSex(String visitSex) {
        this.visitSex = visitSex;
    }

    public String getHisPatientId() {
        return hisPatientId;
    }

    public void setHisPatientId(String hisPatientId) {
        this.hisPatientId = hisPatientId;
    }

    public String getVisitAge() {
        return visitAge;
    }

    public void setVisitAge(String visitAge) {
        this.visitAge = visitAge;
    }

    public String getTsRowId() {
        return tsRowId;
    }

    public void setTsRowId(String tsRowId) {
        this.tsRowId = tsRowId;
    }

    public String getTsName() {
        return tsName;
    }

    public void setTsName(String tsName) {
        this.tsName = tsName;
    }

    public String getSpecimen() {
        return specimen;
    }

    public void setSpecimen(String specimen) {
        this.specimen = specimen;
    }

    public String getCollDT() {
        return collDT;
    }

    public void setCollDT(String collDT) {
        this.collDT = collDT;
    }

    public String getRecDT() {
        return recDT;
    }

    public void setRecDT(String recDT) {
        this.recDT = recDT;
    }

    public String getAuthDT() {
        return authDT;
    }

    public void setAuthDT(String authDT) {
        this.authDT = authDT;
    }

    public String getReqLoc() {
        return reqLoc;
    }

    public void setReqLoc(String reqLoc) {
        this.reqLoc = reqLoc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
