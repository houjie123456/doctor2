package com.tencent.qcloud.tuikit.tuichat.fromApp.bean;

import java.io.Serializable;

/**
 * Created by YC on 2018/6/26.
 */

public class DiseaseBean implements Serializable {

    private String icdId;
    private String icdName;
    private String id;//分组ID
    private String docId;//医生ID
    private String groupName;//分组名称
    private String createDate;//创建时间（字符串）
    private String createTime;//添加时间（时间戳）

    private String icdCode;
    private String startTime;
    private String hisCode;
    private String large;
    private String icdType;

    private String groupId;
    private String checkType;



    public DiseaseBean(String icdname, String groupname) {
        this.icdName = icdname;
        this.groupName = groupname;
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

    public String getDocid() {
        return docId;
    }

    public void setDocid(String docid) {
        this.docId = docid;
    }

    public String getGroupname() {
        return groupName;
    }

    public void setGroupname(String groupname) {
        this.groupName = groupname;
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

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getIcdType() {
        return icdType;
    }

    public void setIcdType(String icdType) {
        this.icdType = icdType;
    }
}
