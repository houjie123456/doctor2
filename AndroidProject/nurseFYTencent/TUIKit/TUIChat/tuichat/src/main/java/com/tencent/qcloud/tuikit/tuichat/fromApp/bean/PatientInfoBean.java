package com.tencent.qcloud.tuikit.tuichat.fromApp.bean;

/**
 * Created by YC on 2018/7/1.
 */

public class PatientInfoBean {
    private String diagnosised;
    private String hospitaled;
    private String id;
    private String illLenghTime;
    private String inquiryContent;
    private String patientHeadUrl;
    private String patientId;
    private String patientMobile;
    private String patientName;
    private String visitAge;
    private String visitHeadUrl;
    private String visitId;
    private String visitMobile;
    private String visitName;
    private String visitRemark;
    private String visitSex;

    private String patientid;//（患者ID(账户)）、
    private String patientname;//（就诊人姓名）、
    private String patientSex;//（就诊人性别  1.男 2.女）、
    private String birthdate;//（出生日期）、
    private String idcardno;//（身份证）、
    private String mobile;//（手机号）、
    private String headurl;//（头像地址相对路径）、
    private String diseaseName;//（疾病名称）

    private String inquiryPurpose;// : 问诊目的 1.咨询 2.复诊
    private String inquiryPurposeStr;// : 问诊目的 1.咨询 2.复诊
    private String subseqVisitCert;// : 复诊凭证类型 1.手动上传 2.院内诊断
    private String subseqVisitCertStr;// : 复诊凭证类型 1.手动上传 2.院内诊断
    private String disease;// : 院内诊断


    private String isGuardian;// ：是否显示监护人
    private String guardianName;// ：监护人姓名
    private String guardianIdCardNo;// ：监护人身份证号





    public String getVisitSex() {
        return visitSex;
    }

    public void setVisitSex(String visitSex) {
        this.visitSex = visitSex;
    }

    public String getIllLenghTime() {
        return illLenghTime;
    }

    public void setIllLenghTime(String illLenghTime) {
        this.illLenghTime = illLenghTime;
    }

    public String getInquiryContent() {
        return inquiryContent;
    }

    public void setInquiryContent(String inquiryContent) {
        this.inquiryContent = inquiryContent;
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

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getVisitAge() {
        return visitAge;
    }

    public void setVisitAge(String visitAge) {
        this.visitAge = visitAge;
    }

    public String getVisitHeadUrl() {
        return visitHeadUrl;
    }

    public void setVisitHeadUrl(String visitHeadUrl) {
        this.visitHeadUrl = visitHeadUrl;
    }

    public String getVisitMobile() {
        return visitMobile;
    }

    public void setVisitMobile(String visitMobile) {
        this.visitMobile = visitMobile;
    }

    public String getVisitName() {
        return visitName;
    }

    public void setVisitName(String visitName) {
        this.visitName = visitName;
    }

    public String getVisitRemark() {
        return visitRemark;
    }

    public void setVisitRemark(String visitRemark) {
        this.visitRemark = visitRemark;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatientid() {
        return patientid;
    }

    public void setPatientid(String patientid) {
        this.patientid = patientid;
    }

    public String getPatientname() {
        return patientname;
    }

    public void setPatientname(String patientname) {
        this.patientname = patientname;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getIdcardno() {
        return idcardno;
    }

    public void setIdcardno(String idcardno) {
        this.idcardno = idcardno;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getHeadurl() {
        return headurl;
    }

    public void setHeadurl(String headurl) {
        this.headurl = headurl;
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

    public String getPatientSex() {
        return patientSex;
    }

    public void setPatientSex(String patientSex) {
        this.patientSex = patientSex;
    }

    public String getHospitaled() {
        return hospitaled;
    }

    public void setHospitaled(String hospitaled) {
        this.hospitaled = hospitaled;
    }

    public String getDiagnosised() {
        return diagnosised;
    }

    public void setDiagnosised(String diagnosised) {
        this.diagnosised = diagnosised;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public String getInquiryPurpose() {
        return inquiryPurpose;
    }

    public void setInquiryPurpose(String inquiryPurpose) {
        this.inquiryPurpose = inquiryPurpose;
    }

    public String getInquiryPurposeStr() {
        return inquiryPurposeStr;
    }

    public void setInquiryPurposeStr(String inquiryPurposeStr) {
        this.inquiryPurposeStr = inquiryPurposeStr;
    }

    public String getSubseqVisitCert() {
        return subseqVisitCert;
    }

    public void setSubseqVisitCert(String subseqVisitCert) {
        this.subseqVisitCert = subseqVisitCert;
    }

    public String getSubseqVisitCertStr() {
        return subseqVisitCertStr;
    }

    public void setSubseqVisitCertStr(String subseqVisitCertStr) {
        this.subseqVisitCertStr = subseqVisitCertStr;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getIsGuardian() {
        return isGuardian;
    }

    public void setIsGuardian(String isGuardian) {
        this.isGuardian = isGuardian;
    }

    public String getGuardianName() {
        return guardianName;
    }

    public void setGuardianName(String guardianName) {
        this.guardianName = guardianName;
    }

    public String getGuardianIdCardNo() {
        return guardianIdCardNo;
    }

    public void setGuardianIdCardNo(String guardianIdCardNo) {
        this.guardianIdCardNo = guardianIdCardNo;
    }
}
