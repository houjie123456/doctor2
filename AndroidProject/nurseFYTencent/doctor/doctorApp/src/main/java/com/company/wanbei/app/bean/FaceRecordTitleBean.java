package com.company.wanbei.app.bean;

import java.util.ArrayList;

/**
 * Created by YC on 2018/6/29.
 */

public class FaceRecordTitleBean {
    private String startDate;
    private String endDate;
    private String address;
    private ArrayList<FaceRecordPersonBean> childTable;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ArrayList<FaceRecordPersonBean> getChildTable() {
        return childTable;
    }

    public void setChildTable(ArrayList<FaceRecordPersonBean> childTable) {
        this.childTable = childTable;
    }
}
