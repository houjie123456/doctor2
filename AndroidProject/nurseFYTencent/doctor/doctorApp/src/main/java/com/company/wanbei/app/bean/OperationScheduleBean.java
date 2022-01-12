package com.company.wanbei.app.bean;

/**
 * Created by YC on 2018/6/27.
 */
public class OperationScheduleBean {
    private String serviceId;//：服务id
    private String dateStr;//：日期 MM月dd日
    private String dayStr;//：日期 yyyy-MM-dd
    private String weekStr;//：星期
    private String morning;//：上午总人数
    private String morningReserved;//：上午已预约人数
    private String morningSurplus;//：上午是否剩余 1、未发布，2、已约满，3、可预约
    private String afternoon;//：下午总人数
    private String afternoonReserved;//：下午已预约人数
    private String afternoonSurplus;//：上午是否剩余 1、未发布，2、已约满，3、可预约
    private boolean isSelected;

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public String getDayStr() {
        return dayStr;
    }

    public void setDayStr(String dayStr) {
        this.dayStr = dayStr;
    }

    public String getWeekStr() {
        return weekStr;
    }

    public void setWeekStr(String weekStr) {
        this.weekStr = weekStr;
    }

    public String getMorning() {
        return morning;
    }

    public void setMorning(String morning) {
        this.morning = morning;
    }

    public String getMorningReserved() {
        return morningReserved;
    }

    public void setMorningReserved(String morningReserved) {
        this.morningReserved = morningReserved;
    }

    public String getMorningSurplus() {
        return morningSurplus;
    }

    public void setMorningSurplus(String morningSurplus) {
        this.morningSurplus = morningSurplus;
    }

    public String getAfternoon() {
        return afternoon;
    }

    public void setAfternoon(String afternoon) {
        this.afternoon = afternoon;
    }

    public String getAfternoonReserved() {
        return afternoonReserved;
    }

    public void setAfternoonReserved(String afternoonReserved) {
        this.afternoonReserved = afternoonReserved;
    }

    public String getAfternoonSurplus() {
        return afternoonSurplus;
    }

    public void setAfternoonSurplus(String afternoonSurplus) {
        this.afternoonSurplus = afternoonSurplus;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
