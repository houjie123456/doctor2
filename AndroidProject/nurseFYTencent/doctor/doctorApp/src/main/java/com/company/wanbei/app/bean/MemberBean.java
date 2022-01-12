package com.company.wanbei.app.bean;

/**
 * Created by YC on 2018/6/30.
 */

public class MemberBean {
    private String memberID;
    private String memberName;
    private String memberHeadUrl;
    private String academicTitle;
    private String hospitalName;
    private String departmentName;

    public String getMemberID() {
        return memberID;
    }

    public void setMemberID(String memberID) {
        this.memberID = memberID;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberHeadUrl() {
        return memberHeadUrl;
    }

    public void setMemberHeadUrl(String memberHeadUrl) {
        this.memberHeadUrl = memberHeadUrl;
    }

    public String getAcademicTitle() {
        return academicTitle;
    }

    public void setAcademicTitle(String academicTitle) {
        this.academicTitle = academicTitle;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
