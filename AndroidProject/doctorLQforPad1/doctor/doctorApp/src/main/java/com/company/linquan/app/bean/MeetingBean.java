package com.company.linquan.app.bean;

import com.company.linquan.app.http.ScheduleBean;

import java.util.ArrayList;

/**
 * Created by YC on 2018/6/22.
 */

public class MeetingBean {
    private String id;
    private String vedioCover;
    private String thinkNumber;
    private String timeDifference;
    private String onlineNumber;
    private String vedioType;
    private String headUrl;
    private String personID;
    private String doctorName;
    private String department;
    private String title;
    private String hospital;
    private String introduction;
    private String timePart;

    // 详情
//    longitude-经度
//    latitude-纬度
//    address-会议地址
    private String longitude;
    private String latitude;
    private String address;
    private String vedioCover2;
    private String calendarContent;
    private String CalendarRemark;
    private String orgContent;
    private String qrCodeUrl;
    private String detailUrl;
    private String isSignType;
    private ArrayList<ScheduleBean> scheduleTable;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVedioCover() {
        return vedioCover;
    }

    public void setVedioCover(String vedioCover) {
        this.vedioCover = vedioCover;
    }

    public String getThinkNumber() {
        return thinkNumber;
    }

    public void setThinkNumber(String thinkNumber) {
        this.thinkNumber = thinkNumber;
    }

    public String getTimeDifference() {
        return timeDifference;
    }

    public void setTimeDifference(String timeDifference) {
        this.timeDifference = timeDifference;
    }

    public String getOnlineNumber() {
        return onlineNumber;
    }

    public void setOnlineNumber(String onlineNumber) {
        this.onlineNumber = onlineNumber;
    }

    public String getVedioType() {
        return vedioType;
    }

    public void setVedioType(String vedioType) {
        this.vedioType = vedioType;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getTimePart() {
        return timePart;
    }

    public void setTimePart(String timePart) {
        this.timePart = timePart;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getVedioCover2() {
        return vedioCover2;
    }

    public void setVedioCover2(String vedioCover2) {
        this.vedioCover2 = vedioCover2;
    }

    public String getCalendarContent() {
        return calendarContent;
    }

    public void setCalendarContent(String calendarContent) {
        this.calendarContent = calendarContent;
    }

    public String getCalendarRemark() {
        return CalendarRemark;
    }

    public void setCalendarRemark(String calendarRemark) {
        CalendarRemark = calendarRemark;
    }

    public String getOrgContent() {
        return orgContent;
    }

    public void setOrgContent(String orgContent) {
        this.orgContent = orgContent;
    }

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }

    public ArrayList<ScheduleBean> getScheduleTable() {
        return scheduleTable;
    }

    public void setScheduleTable(ArrayList<ScheduleBean> scheduleTable) {
        this.scheduleTable = scheduleTable;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public String getIsSignType() {
        return isSignType;
    }

    public void setIsSignType(String isSignType) {
        this.isSignType = isSignType;
    }
}
