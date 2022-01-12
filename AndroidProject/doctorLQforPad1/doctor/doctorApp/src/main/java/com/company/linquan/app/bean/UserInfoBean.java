package com.company.linquan.app.bean;

import java.util.ArrayList;

/**
 * Created by YC on 2018/6/22.
 */

public class UserInfoBean {
    /**
     * 网易云账号
     * **/
    private String accountID;
    private String token;
    private String wyyID;
    /**
     * 系统账号
     * **/
    private String myName;
    private String sex;
    private String personRemark;
    private String beGoodInfo;
    private String beGoodAt;
    private String idCardNo;
    private String headUrl;
    private String hospitalID;
    private String departmentID;
    private String careerType;
    private String academicTitle;
    private String mobile;
    private String homeAddress;
    private String isAuthenticat;

    private String myBalance;
    private String myScore;
    private String commentScore;
    private String bankName;
    private String bankClearNo;
    private String branchName;
    private String branchClearNo;

    private String bankCardNo;
    private String accountMobile;
    private String accountName;
    private String checkState;
    private String checkRemark;
    private String createTime;
    private String openIDPublic;

    private String openIDSmall;
    private String openIDOpen;
    private String otherParam1;
    private String otherParam2;
    private String otherParam3;
    private String hospitalName;
    private String departmentName;

    private String personHonor;

    private String idType;

    private String years;
    private String specialty;

    private ArrayList<NurseListBean> nurseList;

    private static UserInfoBean ourInstance = new UserInfoBean();

    public static UserInfoBean getInstance() {
        return ourInstance;
    }

    public String getWyyID() {
        return wyyID;
    }

    public void setWyyID(String wyyID) {
        this.wyyID = wyyID;
    }

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMyName() {
        return myName;
    }

    public void setMyName(String myName) {
        this.myName = myName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPersonRemark() {
        return personRemark;
    }

    public void setPersonRemark(String personRemark) {
        this.personRemark = personRemark;
    }

    public String getBeGoodAt() {
        return beGoodAt;
    }

    public void setBeGoodAt(String beGoodAt) {
        this.beGoodAt = beGoodAt;
    }

    public String getBeGoodInfo() {
        return beGoodInfo;
    }

    public void setBeGoodInfo(String beGoodInfo) {
        this.beGoodInfo = beGoodInfo;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getHospitalID() {
        return hospitalID;
    }

    public void setHospitalID(String hospitalID) {
        this.hospitalID = hospitalID;
    }

    public String getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(String departmentID) {
        this.departmentID = departmentID;
    }

    public String getCareerType() {
        return careerType;
    }

    public void setCareerType(String careerType) {
        this.careerType = careerType;
    }

    public String getAcademicTitle() {
        return academicTitle;
    }

    public void setAcademicTitle(String academicTitle) {
        this.academicTitle = academicTitle;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getIsAuthenticat() {
        return isAuthenticat;
    }

    public void setIsAuthenticat(String isAuthenticat) {
        this.isAuthenticat = isAuthenticat;
    }


    public String getMyBalance() {
        return myBalance;
    }

    public void setMyBalance(String myBalance) {
        this.myBalance = myBalance;
    }

    public String getMyScore() {
        return myScore;
    }

    public void setMyScore(String myScore) {
        this.myScore = myScore;
    }

    public String getCommentScore() {
        return commentScore;
    }

    public void setCommentScore(String commentScore) {
        this.commentScore = commentScore;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankClearNo() {
        return bankClearNo;
    }

    public void setBankClearNo(String bankClearNo) {
        this.bankClearNo = bankClearNo;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBranchClearNo() {
        return branchClearNo;
    }

    public void setBranchClearNo(String branchClearNo) {
        this.branchClearNo = branchClearNo;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public String getAccountMobile() {
        return accountMobile;
    }

    public void setAccountMobile(String accountMobile) {
        this.accountMobile = accountMobile;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getOpenIDPublic() {
        return openIDPublic;
    }

    public void setOpenIDPublic(String openIDPublic) {
        this.openIDPublic = openIDPublic;
    }

    public String getOpenIDSmall() {
        return openIDSmall;
    }

    public void setOpenIDSmall(String openIDSmall) {
        this.openIDSmall = openIDSmall;
    }

    public String getOpenIDOpen() {
        return openIDOpen;
    }

    public void setOpenIDOpen(String openIDOpen) {
        this.openIDOpen = openIDOpen;
    }

    public String getOtherParam1() {
        return otherParam1;
    }

    public void setOtherParam1(String otherParam1) {
        this.otherParam1 = otherParam1;
    }

    public String getOtherParam2() {
        return otherParam2;
    }

    public void setOtherParam2(String otherParam2) {
        this.otherParam2 = otherParam2;
    }

    public String getOtherParam3() {
        return otherParam3;
    }

    public void setOtherParam3(String otherParam3) {
        this.otherParam3 = otherParam3;
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

    public String getPersonHonor() {
        return personHonor;
    }

    public void setPersonHonor(String personHonor) {
        this.personHonor = personHonor;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getYears() {
        return years;
    }

    public void setYears(String years) {
        this.years = years;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public ArrayList<NurseListBean> getNurseList() {
        return nurseList;
    }

    public void setNurseList(ArrayList<NurseListBean> nurseList) {
        this.nurseList = nurseList;
    }
}
