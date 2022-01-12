package com.company.wanbei.app.bean;

import java.util.ArrayList;

/**
 * Created by YC on 2018/8/1.
 */

public class DutyDateBean {
    private String day;// ：日期
    private ArrayList<DutyTimeBean> dutyArray;// ：值班时段数组

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public ArrayList<DutyTimeBean> getDutyArray() {
        return dutyArray;
    }

    public void setDutyArray(ArrayList<DutyTimeBean> dutyArray) {
        this.dutyArray = dutyArray;
    }
}
