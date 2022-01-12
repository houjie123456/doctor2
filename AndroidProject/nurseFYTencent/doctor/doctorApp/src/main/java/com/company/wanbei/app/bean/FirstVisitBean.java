package com.company.wanbei.app.bean;

import java.io.Serializable;

/**
 * Created by YC on 2018/6/26.
 */

public class FirstVisitBean implements Serializable {

    private String inquiryId;// ：ID
    private String isFirstVisit;// ：标题

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
}
