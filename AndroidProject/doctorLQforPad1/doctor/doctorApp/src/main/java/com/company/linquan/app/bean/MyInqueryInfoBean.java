package com.company.linquan.app.bean;

/**
 * Created by YC on 2018/7/1.
 */

public class MyInqueryInfoBean {
    private String id;//（问诊记录主键）、
    private String doctorid;//（医生id）、
    private String patientid;//（患者用户id）、
    private String visitid;//（就诊人id）、
    private String content;//（问诊内容）、
    private String starttime;//（问诊开始时间）、
    private String endtime;//（问诊结束时间）、
    private String inquirytype;//（问诊类型 1.图文 3.语音 4.名医团 5.私人医生 9.1和2图文问诊）、
    private String status;//（1.咨询中 2.带接诊 3.已完成 4.已评价）、
    private String createtime;//（添加时间）、
    private String isfree;//（是否免费 1是，2否）、
    private String relatid;//（关联表ID(根据类型关联对应的表)）、
    private String orderid;//（订单号）

    private String infoContent;
    private String infoType;
    private String infoTypeName;


    public String getInfoContent() {
        return infoContent;
    }

    public void setInfoContent(String infoContent) {
        this.infoContent = infoContent;
    }

    public String getInfoType() {
        return infoType;
    }

    public void setInfoType(String infoType) {
        this.infoType = infoType;
    }

    public String getInfoTypeName() {
        return infoTypeName;
    }

    public void setInfoTypeName(String infoTypeName) {
        this.infoTypeName = infoTypeName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDoctorid() {
        return doctorid;
    }

    public void setDoctorid(String doctorid) {
        this.doctorid = doctorid;
    }

    public String getPatientid() {
        return patientid;
    }

    public void setPatientid(String patientid) {
        this.patientid = patientid;
    }

    public String getVisitid() {
        return visitid;
    }

    public void setVisitid(String visitid) {
        this.visitid = visitid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getInquirytype() {
        return inquirytype;
    }

    public void setInquirytype(String inquirytype) {
        this.inquirytype = inquirytype;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getIsfree() {
        return isfree;
    }

    public void setIsfree(String isfree) {
        this.isfree = isfree;
    }

    public String getRelatid() {
        return relatid;
    }

    public void setRelatid(String relatid) {
        this.relatid = relatid;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }
}
