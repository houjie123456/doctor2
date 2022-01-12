package com.company.wanbei.app.bean;

import java.util.ArrayList;

/**
 * Created by YC on 2018/8/4.
 */

public class LoginBean {

    private String myName;// ：医生名称
    private String departmentName;// ：科室名称
    private String hospitalName;// ：医院名称
    private String academicTitleName;// ：职称
    private String headUrl;// ：头像

    private String serviceCount;// 服务次数
    private String consultCount;// 咨询次数

    private String openId;

    private String stampImg;

    private String tid;

    private ArrayList<String> uniqueIdCa;//同步处方CA生成的唯一标识

    private String clientId;

    private String mobile;

    private String isSubject;//是否为阜阳人民医院的医生

    private String recipeID;//处方ID
    private String imgUrl;//住院处方二维码

    private String groupId;//：分组ID （没有分组时返回空字符串）
    private String groupName;//：分组名称（没有分组时返回空字符串）

    private String checkType;//checkType：状态： 1.已经分组；2.没有分组
    private String isFirstVisit;//:是否为首诊 1、是 2、否

    private String deptWorkstationId;// ：科室工作站id
    /**
     登录参数
     */
    private String personId;
    private String uniqueId;
    private String isAuthenticat;
    private String checkState;
    private String userSig;
    private String idType;// 登录接口 新增了一个idType 字段 -身份类型类别 0是医生 1护士
    /**
     网易云账号
     accountID-网易云账号
     token-网易云token
     */
    private String accountId;
    private String token;
    /**
     腾讯云账号
     */
    private String userTencent;

    /**
     上传图片
     */
    private String picUrl;
    private String picSignId;

    /**
     添加评论
     */
    private String meetingId;

    /**
     面诊id
     */
    private String faceId;

    /**
     图文id
     */
    private String graphicId;


    /**
     私人医生详情
     */
    private String amountOneMonth;
    private String amountThreeMonth;
    private String amountSixMonth;
    private String amountTwelveMonth;

    /**
     处方id
     */
    private String recipeId;

    /**
     版本
     */

    private String versionIOS;
    private String versionNumberIOS;
    private String downUrlIOS;
    private String isForceIOS;
    private String versionAndroid;
    private String versionNumberAndroid;
    private String downUrlAndroid;
    private String isForceAndroid;
    private String upgradeRemarkIOS;
    private String upgradeRemarkAndroid;


    /**
     直播地址
     */
    private String pushUrl;
    private String palyUrlRTMP;
    private String palyUrlFLV;
    private String palyUrlHLS;

    /**
     0.有资格未创建 1.已创建 2.无资格创建
     */
    private String isCreateState;
    /**
     小结id
     */
    private String summaryId;

    /**
     图文问诊
     */
    private String id;
    private String dayNumber;
    private String barNumber;
    private String amount;
    private String doctorType;//医生类型1、医生（默认1）2、心理咨询师
    private String status;//图文问诊状态：1、空闲；2、忙碌

    /**
     免费义诊
     */
    private String manNumber;
    private String isUse;

    private InquiryBean inquiryJson;


    public String getMyName() {
        return myName;
    }

    public void setMyName(String myName) {
        this.myName = myName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getAcademicTitleName() {
        return academicTitleName;
    }

    public void setAcademicTitleName(String academicTitleName) {
        this.academicTitleName = academicTitleName;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getServiceCount() {
        return serviceCount;
    }

    public void setServiceCount(String serviceCount) {
        this.serviceCount = serviceCount;
    }

    public String getConsultCount() {
        return consultCount;
    }

    public void setConsultCount(String consultCount) {
        this.consultCount = consultCount;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getStampImg() {
        return stampImg;
    }

    public void setStampImg(String stampImg) {
        this.stampImg = stampImg;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public ArrayList<String> getUniqueIdCa() {
        return uniqueIdCa;
    }

    public void setUniqueIdCa(ArrayList<String> uniqueIdCa) {
        this.uniqueIdCa = uniqueIdCa;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIsSubject() {
        return isSubject;
    }

    public void setIsSubject(String isSubject) {
        this.isSubject = isSubject;
    }

    public String getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(String recipeID) {
        this.recipeID = recipeID;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getCheckType() {
        return checkType;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }

    public String getIsFirstVisit() {
        return isFirstVisit;
    }

    public void setIsFirstVisit(String isFirstVisit) {
        this.isFirstVisit = isFirstVisit;
    }

    public String getDeptWorkstationId() {
        return deptWorkstationId;
    }

    public void setDeptWorkstationId(String deptWorkstationId) {
        this.deptWorkstationId = deptWorkstationId;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getIsAuthenticat() {
        return isAuthenticat;
    }

    public void setIsAuthenticat(String isAuthenticat) {
        this.isAuthenticat = isAuthenticat;
    }

    public String getCheckState() {
        return checkState;
    }

    public void setCheckState(String checkState) {
        this.checkState = checkState;
    }

    public String getUserSig() {
        return userSig;
    }

    public void setUserSig(String userSig) {
        this.userSig = userSig;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserTencent() {
        return userTencent;
    }

    public void setUserTencent(String userTencent) {
        this.userTencent = userTencent;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getPicSignId() {
        return picSignId;
    }

    public void setPicSignId(String picSignId) {
        this.picSignId = picSignId;
    }

    public String getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(String meetingId) {
        this.meetingId = meetingId;
    }

    public String getFaceId() {
        return faceId;
    }

    public void setFaceId(String faceId) {
        this.faceId = faceId;
    }

    public String getGraphicId() {
        return graphicId;
    }

    public void setGraphicId(String graphicId) {
        this.graphicId = graphicId;
    }

    public String getAmountOneMonth() {
        return amountOneMonth;
    }

    public void setAmountOneMonth(String amountOneMonth) {
        this.amountOneMonth = amountOneMonth;
    }

    public String getAmountThreeMonth() {
        return amountThreeMonth;
    }

    public void setAmountThreeMonth(String amountThreeMonth) {
        this.amountThreeMonth = amountThreeMonth;
    }

    public String getAmountSixMonth() {
        return amountSixMonth;
    }

    public void setAmountSixMonth(String amountSixMonth) {
        this.amountSixMonth = amountSixMonth;
    }

    public String getAmountTwelveMonth() {
        return amountTwelveMonth;
    }

    public void setAmountTwelveMonth(String amountTwelveMonth) {
        this.amountTwelveMonth = amountTwelveMonth;
    }

    public String getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

    public String getVersionIOS() {
        return versionIOS;
    }

    public void setVersionIOS(String versionIOS) {
        this.versionIOS = versionIOS;
    }

    public String getVersionNumberIOS() {
        return versionNumberIOS;
    }

    public void setVersionNumberIOS(String versionNumberIOS) {
        this.versionNumberIOS = versionNumberIOS;
    }

    public String getDownUrlIOS() {
        return downUrlIOS;
    }

    public void setDownUrlIOS(String downUrlIOS) {
        this.downUrlIOS = downUrlIOS;
    }

    public String getIsForceIOS() {
        return isForceIOS;
    }

    public void setIsForceIOS(String isForceIOS) {
        this.isForceIOS = isForceIOS;
    }

    public String getVersionAndroid() {
        return versionAndroid;
    }

    public void setVersionAndroid(String versionAndroid) {
        this.versionAndroid = versionAndroid;
    }

    public String getVersionNumberAndroid() {
        return versionNumberAndroid;
    }

    public void setVersionNumberAndroid(String versionNumberAndroid) {
        this.versionNumberAndroid = versionNumberAndroid;
    }

    public String getDownUrlAndroid() {
        return downUrlAndroid;
    }

    public void setDownUrlAndroid(String downUrlAndroid) {
        this.downUrlAndroid = downUrlAndroid;
    }

    public String getIsForceAndroid() {
        return isForceAndroid;
    }

    public void setIsForceAndroid(String isForceAndroid) {
        this.isForceAndroid = isForceAndroid;
    }

    public String getUpgradeRemarkIOS() {
        return upgradeRemarkIOS;
    }

    public void setUpgradeRemarkIOS(String upgradeRemarkIOS) {
        this.upgradeRemarkIOS = upgradeRemarkIOS;
    }

    public String getUpgradeRemarkAndroid() {
        return upgradeRemarkAndroid;
    }

    public void setUpgradeRemarkAndroid(String upgradeRemarkAndroid) {
        this.upgradeRemarkAndroid = upgradeRemarkAndroid;
    }

    public String getPushUrl() {
        return pushUrl;
    }

    public void setPushUrl(String pushUrl) {
        this.pushUrl = pushUrl;
    }

    public String getPalyUrlRTMP() {
        return palyUrlRTMP;
    }

    public void setPalyUrlRTMP(String palyUrlRTMP) {
        this.palyUrlRTMP = palyUrlRTMP;
    }

    public String getPalyUrlFLV() {
        return palyUrlFLV;
    }

    public void setPalyUrlFLV(String palyUrlFLV) {
        this.palyUrlFLV = palyUrlFLV;
    }

    public String getPalyUrlHLS() {
        return palyUrlHLS;
    }

    public void setPalyUrlHLS(String palyUrlHLS) {
        this.palyUrlHLS = palyUrlHLS;
    }

    public String getIsCreateState() {
        return isCreateState;
    }

    public void setIsCreateState(String isCreateState) {
        this.isCreateState = isCreateState;
    }

    public String getSummaryId() {
        return summaryId;
    }

    public void setSummaryId(String summaryId) {
        this.summaryId = summaryId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(String dayNumber) {
        this.dayNumber = dayNumber;
    }

    public String getBarNumber() {
        return barNumber;
    }

    public void setBarNumber(String barNumber) {
        this.barNumber = barNumber;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDoctorType() {
        return doctorType;
    }

    public void setDoctorType(String doctorType) {
        this.doctorType = doctorType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getManNumber() {
        return manNumber;
    }

    public void setManNumber(String manNumber) {
        this.manNumber = manNumber;
    }

    public String getIsUse() {
        return isUse;
    }

    public void setIsUse(String isUse) {
        this.isUse = isUse;
    }

    public InquiryBean getInquiryJson() {
        return inquiryJson;
    }

    public void setInquiryJson(InquiryBean inquiryJson) {
        this.inquiryJson = inquiryJson;
    }
}
