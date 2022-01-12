package com.company.wanbei.app.bean;

/**
 * Created by YC on 2018/7/15.
 */

public class CADataBean {
    private String signTotal;
    private CASelfSignSessionBean selfSignSession;

    public String getSignTotal() {
        return signTotal;
    }

    public void setSignTotal(String signTotal) {
        this.signTotal = signTotal;
    }

    public CASelfSignSessionBean getSelfSignSession() {
        return selfSignSession;
    }

    public void setSelfSignSession(CASelfSignSessionBean selfSignSession) {
        this.selfSignSession = selfSignSession;
    }
}
