package com.company.wanbei.app.bean;

import java.io.Serializable;

/**
 * Created by YC on 2018/7/1.
 */

public class PatientBean implements Serializable {

    private String id;
    private String patientId;
    private String patientName;
    private String patientHeadUrl;
    private String patientMobile;
    private String visitId;
    private String visitName;
    private String visitMobile;
    private String visitHeadUrl;
    private String visitSex;
    private String visitSexStr;//visitSexStr：就诊人性别
    private String visitAge;
    private String serverType;
    private String stageType;
    private boolean isSelected;

    private String accountName;

    private String sex;
    private String nikename;
    private String headurl;
    private String mobile;

    private String inquiryId;// ：问诊ID
    private String isFirstVisit;// : 是否为首诊 1、是 2、否

    private String visitIdCardNo;

    private String doctorMobile;
    private String doctorId;// ：医生id
    private String doctorName;// ：医生名称
    private String docTitle;// ：医生职称
    private String isReply;// ：医生是否回复 1、是 2、否
    private String createTime;// ：最近问诊时间




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatientID() {
        return patientId;
    }

    public void setPatientID(String patientId) {
        this.patientId = patientId;
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

    public void setVisitID(String visitId) {
        this.visitId = visitId;
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

    public String getServerType() {
        return serverType;
    }

    public void setServerType(String serverType) {
        this.serverType = serverType;
    }

    public String getStageType() {
        return stageType;
    }

    public void setStageType(String stageType) {
        this.stageType = stageType;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNikename() {
        return nikename;
    }

    public void setNikename(String nikename) {
        this.nikename = nikename;
    }

    public String getHeadurl() {
        return headurl;
    }

    public void setHeadurl(String headurl) {
        this.headurl = headurl;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getInquiryId() {
        return inquiryId;
    }

    public void setInquiryId(String inquiryId) {
        this.inquiryId = inquiryId;
    }

    public String getIsFirstVisit() {
        return isFirstVisit;
    }

    public void setIsFirstVisit(String isFirstVisit) {
        this.isFirstVisit = isFirstVisit;
    }

    public String getVisitSexStr() {
        return visitSexStr;
    }

    public void setVisitSexStr(String visitSexStr) {
        this.visitSexStr = visitSexStr;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getVisitId() {
        return visitId;
    }

    public void setVisitId(String visitId) {
        this.visitId = visitId;
    }

    public String getVisitIdCardNo() {
        return visitIdCardNo;
    }

    public void setVisitIdCardNo(String visitIdCardNo) {
        this.visitIdCardNo = visitIdCardNo;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDocTitle() {
        return docTitle;
    }

    public void setDocTitle(String docTitle) {
        this.docTitle = docTitle;
    }

    public String getIsReply() {
        return isReply;
    }

    public void setIsReply(String isReply) {
        this.isReply = isReply;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDoctorMobile() {
        return doctorMobile;
    }

    public void setDoctorMobile(String doctorMobile) {
        this.doctorMobile = doctorMobile;
    }
}
