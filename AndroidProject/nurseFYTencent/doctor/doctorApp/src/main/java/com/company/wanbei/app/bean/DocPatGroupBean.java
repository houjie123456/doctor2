package com.company.wanbei.app.bean;

/**
 * Created by YC on 2018/7/1.
 */

public class DocPatGroupBean {
    private String id;// ：群组id
    private String groupId;// ：群id
    private String name;// ：群名称
    private String ownerName;// ：群主名称
    private String memberName;// ：本人群昵称
    private String memberRole;// ：本人群属性  成员角色：1群主，2管理员，3普通成员
    private String currentMemberNum;// ：群人数
    private String createTime;// ：创建时间

    private String announcement;// ：群公告
    private String intro;// ：群描述
    private String memberLimit;// ：群最大人数(包含群主)，范围：2至应用定义的最大群人数(默认:200)
    private String memberType;// ：成员类型：1医生，2护士，3患者
    private String isValid;// ：是否有效 1、有效 2、无效

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberRole() {
        return memberRole;
    }

    public void setMemberRole(String memberRole) {
        this.memberRole = memberRole;
    }

    public String getCurrentMemberNum() {
        return currentMemberNum;
    }

    public void setCurrentMemberNum(String currentMemberNum) {
        this.currentMemberNum = currentMemberNum;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(String announcement) {
        this.announcement = announcement;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getMemberLimit() {
        return memberLimit;
    }

    public void setMemberLimit(String memberLimit) {
        this.memberLimit = memberLimit;
    }

    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }
}
