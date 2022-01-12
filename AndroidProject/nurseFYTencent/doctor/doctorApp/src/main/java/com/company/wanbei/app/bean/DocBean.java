package com.company.wanbei.app.bean;

import java.util.ArrayList;

/**
 * Created by YC on 2018/8/1.
 */

public class DocBean {
    private String id;
    private String title;
    private String subtitle;
    private String releaseTime;
    private String headImg;
    private String releaseMan;
    private String content;
    private String remark;
    private String logoUrl;
    private String detailUrl;

    private String doctorName;// ：医生名称
    private String dutyRow;// ：排班行数

    private String doctorId;// ：医生id
    private ArrayList<DutyDateBean> dutyDateArray;// ：值班日期数组

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getReleaseMan() {
        return releaseMan;
    }

    public void setReleaseMan(String releaseMan) {
        this.releaseMan = releaseMan;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDutyRow() {
        return dutyRow;
    }

    public void setDutyRow(String dutyRow) {
        this.dutyRow = dutyRow;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public ArrayList<DutyDateBean> getDutyDateArray() {
        return dutyDateArray;
    }

    public void setDutyDateArray(ArrayList<DutyDateBean> dutyDateArray) {
        this.dutyDateArray = dutyDateArray;
    }
}
