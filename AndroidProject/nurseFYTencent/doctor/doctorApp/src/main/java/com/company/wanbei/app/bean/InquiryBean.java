package com.company.wanbei.app.bean;

import com.tencent.qcloud.tuikit.tuichat.fromApp.bean.DiseaseBean;

import java.util.ArrayList;

/**
 * Created by YC on 2018/7/15.
 */

public class InquiryBean {
    private ArrayList<DiseaseBean> diagnosisArray;

    private String inquiryId;//：问诊ID
    private String isFirstVisit;//:是否为首诊 1、是 2、否
    private String subseqVisitCert;// : 复诊凭证类型 1.手动上传 2.院内诊断
    private String inquiryPurpose;// : 问诊目的 1.咨询 2.复诊

    public ArrayList<DiseaseBean> getDiagnosisArray() {
        return diagnosisArray;
    }

    public void setDiagnosisArray(ArrayList<DiseaseBean> diagnosisArray) {
        this.diagnosisArray = diagnosisArray;
    }

    public String getInquiryId() {
        return inquiryId;
    }

    public void setInquiryId(String inquiryId) {
        this.inquiryId = inquiryId;
    }

    public String getIsFirstVisit() {
        return isFirstVisit;
    }

    public void setIsFirstVisit(String isFirstVisit) {
        this.isFirstVisit = isFirstVisit;
    }

    public String getSubseqVisitCert() {
        return subseqVisitCert;
    }

    public void setSubseqVisitCert(String subseqVisitCert) {
        this.subseqVisitCert = subseqVisitCert;
    }

    public String getInquiryPurpose() {
        return inquiryPurpose;
    }

    public void setInquiryPurpose(String inquiryPurpose) {
        this.inquiryPurpose = inquiryPurpose;
    }
}
