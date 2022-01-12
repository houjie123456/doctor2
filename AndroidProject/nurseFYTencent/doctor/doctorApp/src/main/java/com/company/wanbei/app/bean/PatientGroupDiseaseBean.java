package com.company.wanbei.app.bean;

/**
 * Created by YC on 2018/6/26.
 */

public class PatientGroupDiseaseBean {

    private String docId;
    private String groupName;
    private String groupId;//分组ID
    private String icdId;
    private String icdName;
    private String id;//关联ID
    private String createDate;//创建时间（字符串）
    private String createTime;//添加时间（时间戳）

    private String icdCode;
    private String startTime;
    private String hisCode;
    private String large;
    private String icdType;
    private String type;


    public String getDocid() {
        return docId;
    }

    public void setDocid(String docId) {
        this.docId = docId;
    }

    public String getGroupname() {
        return groupName;
    }

    public void setGroupname(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupid() {
        return groupId;
    }

    public void setGroupid(String groupId) {
        this.groupId = groupId;
    }

    public String getIcdid() {
        return icdId;
    }

    public void setIcdid(String icdid) {
        this.icdId = icdid;
    }

    public String getIcdname() {
        return icdName;
    }

    public void setIcdname(String icdname) {
        this.icdName = icdname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedate() {
        return createDate;
    }

    public void setCreatedate(String createdate) {
        this.createDate = createdate;
    }

    public String getCreatetime() {
        return createTime;
    }

    public void setCreatetime(String createtime) {
        this.createTime = createtime;
    }

    public String getIcdcode() {
        return icdCode;
    }

    public void setIcdcode(String icdcode) {
        this.icdCode = icdcode;
    }

    public String getStarttime() {
        return startTime;
    }

    public void setStarttime(String starttime) {
        this.startTime = starttime;
    }

    public String getHiscode() {
        return hisCode;
    }

    public void setHiscode(String hiscode) {
        this.hisCode = hiscode;
    }

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public String getIcdtype() {
        return icdType;
    }

    public void setIcdtype(String icdtype) {
        this.icdType = icdtype;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
