package com.company.wanbei.app.bean;

/**
 * Created by YC on 2018/7/15.
 */

public class VoiceGivenBean {
//    医生姓名，科室，医生职称，服务结束时间，医生id
    private String inquiryID;// 预约ID
    private String deptName;// 科室名称
    private String docHead;// 医生头像
    private String docName;// 医生姓名
    private String docTitle;// 医生职称
    private String duration;// 时长
    private String endTime;// 服务结束时间
    private String docID;// 医生id

    public String getInquiryID() {
        return inquiryID;
    }

    public void setInquiryID(String inquiryID) {
        this.inquiryID = inquiryID;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDocHead() {
        return docHead;
    }

    public void setDocHead(String docHead) {
        this.docHead = docHead;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getDocTitle() {
        return docTitle;
    }

    public void setDocTitle(String docTitle) {
        this.docTitle = docTitle;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDocID() {
        return docID;
    }

    public void setDocID(String docID) {
        this.docID = docID;
    }
}
