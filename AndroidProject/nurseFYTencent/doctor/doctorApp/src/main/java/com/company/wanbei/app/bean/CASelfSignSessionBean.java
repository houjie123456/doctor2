package com.company.wanbei.app.bean;

import java.util.ArrayList;

/**
 * Created by YC on 2018/8/4.
 */

public class CASelfSignSessionBean {

    private String clientId;
    private String clientName;
    private String endTime;
    private ArrayList<String> sysTags;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public ArrayList<String> getSysTags() {
        return sysTags;
    }

    public void setSysTags(ArrayList<String> sysTags) {
        this.sysTags = sysTags;
    }
}
