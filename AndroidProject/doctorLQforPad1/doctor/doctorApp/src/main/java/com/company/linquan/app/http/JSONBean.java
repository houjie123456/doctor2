package com.company.linquan.app.http;

import com.google.gson.annotations.SerializedName;

/**
 * Created by YC on 2017/7/11.
 */

public class JSONBean {
    private String code;
    @SerializedName("msgbox")
    private String msgBox;

    private String checkType;//checkType：状态： 1.已经分组；2.没有分组
    /**
      登录参数
     */
    private String personID;
    private String uniqueID;
    private String isAuthenticat;
    private String checkState;
    private String userSig;
    private String idType;// 登录接口 新增了一个idType 字段 -身份类型类别 0是医生 1护士
    /**
     网易云账号
     accountID-网易云账号
     token-网易云token
    */
    private String accountID;
    private String token;
    /**
     腾讯云账号
    */
    private String userTencent;

    /**
     上传图片
    */
    private String picUrl;
    private String picSignID;

    /**
     添加评论
    */
    private String meetingID;

    /**
     面诊id
    */
    private String faceID;
    /**
    图文id
   */
    private String groupID;

    /**
    图文id
   */
    private String graphicID;


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
    private String recipeID;

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
    private String summaryID;

    /**
        图文问诊
     */
    private String id;
    private String dayNumber;
    private String barNumber;
    private String amount;

    /**
     免费义诊
     */
    private String manNumber;
    private String isUse;


    public String getCheckType() {
        return checkType;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }

    public String getCheckState() {
        return checkState;
    }

    public void setCheckState(String checkState) {
        this.checkState = checkState;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsgBox() {
        return msgBox;
    }

    public void setMsgBox(String msgBox) {
        this.msgBox = msgBox;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    public String getUserTencent() {
        return userTencent;
    }

    public void setUserTencent(String userTencent) {
        this.userTencent = userTencent;
    }

    public String getIsAuthenticat() {
        return isAuthenticat;
    }

    public void setIsAuthenticat(String isAuthenticat) {
        this.isAuthenticat = isAuthenticat;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getPicSignID() {
        return picSignID;
    }

    public void setPicSignID(String picSignID) {
        this.picSignID = picSignID;
    }

    public String getMeetingID() {
        return meetingID;
    }

    public void setMeetingID(String meetingID) {
        this.meetingID = meetingID;
    }

    public String getFaceID() {
        return faceID;
    }

    public void setFaceID(String faceID) {
        this.faceID = faceID;
    }

    public String getGraphicID() {
        return graphicID;
    }

    public void setGraphicID(String graphicID) {
        this.graphicID = graphicID;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
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

    public String getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(String recipeID) {
        this.recipeID = recipeID;
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

    public String getSummaryID() {
        return summaryID;
    }

    public void setSummaryID(String summaryID) {
        this.summaryID = summaryID;
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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getUserSig() {
        return userSig;
    }

    public void setUserSig(String userSig) {
        this.userSig = userSig;
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

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }
}
