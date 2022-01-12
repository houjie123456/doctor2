package com.company.linquan.app.bean;

/**
 * Created by YC on 2018/6/30.
 */

public class FriendsBean {
    private String friendID;
    private String friendName;
    private String friendMobile;
    private String headUrl;
    private String friendType;
    private String hospitalName;
    private String departmentName;
    private String academicTitle;
    private boolean selected;

    public String getFriendID() {
        return friendID;
    }

    public void setFriendID(String friendID) {
        this.friendID = friendID;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public String getFriendMobile() {
        return friendMobile;
    }

    public void setFriendMobile(String friendMobile) {
        this.friendMobile = friendMobile;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getFriendType() {
        return friendType;
    }

    public void setFriendType(String friendType) {
        this.friendType = friendType;
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

    public String getAcademicTitle() {
        return academicTitle;
    }

    public void setAcademicTitle(String academicTitle) {
        this.academicTitle = academicTitle;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
