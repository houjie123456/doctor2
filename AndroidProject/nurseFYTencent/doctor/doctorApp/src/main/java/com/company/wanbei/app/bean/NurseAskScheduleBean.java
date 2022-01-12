package com.company.wanbei.app.bean;

import java.io.Serializable;

/**
 * Created by YC on 2018/6/26.
 */

public class NurseAskScheduleBean implements Serializable {

    private String date;//": "01-05",
    private String week;//": "星期二",
    private String endDate;//": "22:00",
    private String id;//": "54dad9066adf4d888e28e6369ae0ed0d",
    private String startDate;//": "06:30"
    private boolean isSelected;
    private boolean canSelected;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isCanSelected() {
        return canSelected;
    }

    public void setCanSelected(boolean canSelected) {
        this.canSelected = canSelected;
    }
}
