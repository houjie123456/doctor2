package com.company.wanbei.app.bean;

/**
 * Created by YC on 2018/7/21.
 */

public class VisitRecordBean {
    private String id;
    private String doctorId;
    private String doctorName;
    private String doctorMobile;
    private String doctorHeadUrl;

    private String patientId;
    private String patientName;
    private String patientHeadUrl;
    private String patientMobile;

    private String visitId;
    private String visitName;
    private String visitMobile;
    private String visitHeadUrl;
    private String thisRemark;
    private String thisType;

    private String createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPersonID() {
        return doctorId;
    }

    public void setPersonID(String personID) {
        this.doctorId = personID;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorMobile() {
        return doctorMobile;
    }

    public void setDoctorMobile(String doctorMobile) {
        this.doctorMobile = doctorMobile;
    }

    public String getDoctorHeadUrl() {
        return doctorHeadUrl;
    }

    public void setDoctorHeadUrl(String doctorHeadUrl) {
        this.doctorHeadUrl = doctorHeadUrl;
    }

    public String getPatientID() {
        return patientId;
    }

    public void setPatientID(String patientID) {
        this.patientId = patientID;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientHeadUrl() {
        return patientHeadUrl;
    }

    public void setPatientHeadUrl(String patientHeadUrl) {
        this.patientHeadUrl = patientHeadUrl;
    }

    public String getPatientMobile() {
        return patientMobile;
    }

    public void setPatientMobile(String patientMobile) {
        this.patientMobile = patientMobile;
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

    public String getVisitRemark() {
        return thisRemark;
    }

    public void setVisitRemark(String visitRemark) {
        this.thisRemark = visitRemark;
    }

    public String getVisitType() {
        return thisType;
    }

    public void setVisitType(String visitType) {
        this.thisType = visitType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
