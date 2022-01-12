package com.company.wanbei.app.http;

import com.company.wanbei.app.bean.ChangeBean;
import com.company.wanbei.app.bean.DeptBean;
import com.company.wanbei.app.bean.DutyDateBean;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by YC on 2018/7/15.
 */

public class JSONDeptWorkSchedule {

    private String code;
    @SerializedName("msgbox")
    private String msgBox;
    private String dayNumber;// ：天数
    private ArrayList<String> days;// ：日期
    private ArrayList<String> weeks;// ：星期
    private ArrayList<DutyDateBean> table;// ：科室数据

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsgBox() {
        return msgBox;
    }

    public void setMsgBox(String msgBox) {
        this.msgBox = msgBox;
    }

    public String getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(String dayNumber) {
        this.dayNumber = dayNumber;
    }

    public ArrayList<String> getDays() {
        return days;
    }

    public void setDays(ArrayList<String> days) {
        this.days = days;
    }

    public ArrayList<String> getWeeks() {
        return weeks;
    }

    public void setWeeks(ArrayList<String> weeks) {
        this.weeks = weeks;
    }

    public ArrayList<DutyDateBean> getTable() {
        return table;
    }

    public void setTable(ArrayList<DutyDateBean> table) {
        this.table = table;
    }
}
