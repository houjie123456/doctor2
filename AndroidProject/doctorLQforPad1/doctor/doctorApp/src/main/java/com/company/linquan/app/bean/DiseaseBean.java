package com.company.linquan.app.bean;

import java.io.Serializable;

/**
 * Created by YC on 2018/6/26.
 */

public class DiseaseBean implements Serializable {

    private String icdid;
    private String icdname;
    private String id;//分组ID
    private String docid;//医生ID
    private String groupname;//分组名称
    private String createdate;//创建时间（字符串）
    private String createtime;//添加时间（时间戳）

    private String icdcode;
    private String starttime;
    private String hiscode;
    private String large;
    private String icdtype;


    public DiseaseBean(String icdid, String icdname, String id, String docid, String groupname, String createdate, String createtime) {
    }


    public String getIcdid() {
        return icdid;
    }

    public void setIcdid(String icdid) {
        this.icdid = icdid;
    }

    public String getIcdname() {
        return icdname;
    }

    public void setIcdname(String icdname) {
        this.icdname = icdname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getIcdcode() {
        return icdcode;
    }

    public void setIcdcode(String icdcode) {
        this.icdcode = icdcode;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getHiscode() {
        return hiscode;
    }

    public void setHiscode(String hiscode) {
        this.hiscode = hiscode;
    }

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public String getIcdtype() {
        return icdtype;
    }

    public void setIcdtype(String icdtype) {
        this.icdtype = icdtype;
    }
}
