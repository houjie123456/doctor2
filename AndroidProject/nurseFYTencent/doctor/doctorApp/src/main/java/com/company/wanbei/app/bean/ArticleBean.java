package com.company.wanbei.app.bean;

import java.io.Serializable;

/**
 * Created by YC on 2018/6/26.
 */

public class ArticleBean implements Serializable {

    private String id;// ：ID
    private String title;// ：标题
    private String subtitle;// ：副标题（base64加密）
    private String releaseTime;// ：发布时间
    private String releaseMan;// ：发布人
    private String headImg;// ：发布人头像
    private String thisContent;// ：内容（base64加密）已过滤html标签并最多只显示200个字符长度
    private String remark;// ：备注（base64加密）
    private String logoUrl;// ：文章logo
    private String hospital;// ：医院
    private String department;// ：科室
    private String readNumber;// ：阅读量

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

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getThisContent() {
        return thisContent;
    }

    public void setThisContent(String thisContent) {
        this.thisContent = thisContent;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getReadNumber() {
        return readNumber;
    }

    public void setReadNumber(String readNumber) {
        this.readNumber = readNumber;
    }
}
