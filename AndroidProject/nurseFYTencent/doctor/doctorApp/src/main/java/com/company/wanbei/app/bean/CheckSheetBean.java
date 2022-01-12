package com.company.wanbei.app.bean;

/**
 * Created by YC on 2018/7/15.
 */

public class CheckSheetBean {
//    visitId：就诊人Id
//    visitName：就诊人姓名
//    visitSex：就诊人性别
//    hisPatientId：HIS系统患者登记号
//    visitAge：就诊人年龄
//    reportSubTitle：报告标题
//    risStudyId：查询单号
//    ordStartDate：发布时间（医嘱时间）
//    ordItemId：医嘱号

    private String visitId;
    private String visitName;
    private String visitSex;
    private String hisPatientId;
    private String visitAge;
    private String reportSubTitle;
    private String risStudyId;
    private String ordStartDate;
    private String ordItemId;

    public String getVisitId() {
        return visitId;
    }

    public void setVisitId(String visitId) {
        this.visitId = visitId;
    }

    public String getVisitName() {
        return visitName;
    }

    public void setVisitName(String visitName) {
        this.visitName = visitName;
    }

    public String getVisitSex() {
        return visitSex;
    }

    public void setVisitSex(String visitSex) {
        this.visitSex = visitSex;
    }

    public String getHisPatientId() {
        return hisPatientId;
    }

    public void setHisPatientId(String hisPatientId) {
        this.hisPatientId = hisPatientId;
    }

    public String getVisitAge() {
        return visitAge;
    }

    public void setVisitAge(String visitAge) {
        this.visitAge = visitAge;
    }

    public String getReportSubTitle() {
        return reportSubTitle;
    }

    public void setReportSubTitle(String reportSubTitle) {
        this.reportSubTitle = reportSubTitle;
    }

    public String getRisStudyId() {
        return risStudyId;
    }

    public void setRisStudyId(String risStudyId) {
        this.risStudyId = risStudyId;
    }

    public String getOrdStartDate() {
        return ordStartDate;
    }

    public void setOrdStartDate(String ordStartDate) {
        this.ordStartDate = ordStartDate;
    }

    public String getOrdItemId() {
        return ordItemId;
    }

    public void setOrdItemId(String ordItemId) {
        this.ordItemId = ordItemId;
    }
}
