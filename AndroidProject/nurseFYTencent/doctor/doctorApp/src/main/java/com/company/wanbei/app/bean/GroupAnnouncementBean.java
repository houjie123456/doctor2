package com.company.wanbei.app.bean;

/**
 * Created by YC on 2018/6/29.
 */

public class GroupAnnouncementBean {
    private String groupId;// ：群id
    private String name;// ：群名称
    private String announcement;// ：群公告
    private String headUrl;// ：成员头像
    private String publishTime;// ：公告发布时间
    private String memberName;// ：在本群昵称
    private String memberRole;// ：成员角色：1群主，2管理员，3普通成员

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

    public String getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(String announcement) {
        this.announcement = announcement;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
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
}
