package com.company.wanbei.app.bean;

/**
 * Created by YC on 2018/6/27.
 */

public class VoiceDiagnoseBean {
    private String faceID;//
    private String dateStr;//日期(yyyy-MM-dd)
    private String weekStr;//星期
    private String morning;//上午是否设定了面诊 0.否 1.是
    private String afternoon;//下午是否设定了面诊 0.否 1.是
    private String workIDMorning;//上午面诊ID
    private String workIDAfternoon;//下午面诊ID
    private String peopleMorning;//上午已预约人数
    private String peopleAfternoon;//下午已预约人数
    private String allNumberMorning;// 上午可预约总人数
    private String allNumberAfternoon;//下午可预约总人数

    public String getPeopleMorning() {
        return peopleMorning;
    }

    public void setPeopleMorning(String peopleMorning) {
        this.peopleMorning = peopleMorning;
    }

    public String getPeopleAfternoon() {
        return peopleAfternoon;
    }

    public void setPeopleAfternoon(String peopleAfternoon) {
        this.peopleAfternoon = peopleAfternoon;
    }

    public String getAllNumberMorning() {
        return allNumberMorning;
    }

    public void setAllNumberMorning(String allNumberMorning) {
        this.allNumberMorning = allNumberMorning;
    }

    public String getAllNumberAfternoon() {
        return allNumberAfternoon;
    }

    public void setAllNumberAfternoon(String allNumberAfternoon) {
        this.allNumberAfternoon = allNumberAfternoon;
    }

    public String getFaceID() {
        return faceID;
    }

    public void setFaceID(String faceID) {
        this.faceID = faceID;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
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

    public String getAfternoon() {
        return afternoon;
    }

    public void setAfternoon(String afternoon) {
        this.afternoon = afternoon;
    }

    public String getWorkIDMorning() {
        return workIDMorning;
    }

    public void setWorkIDMorning(String workIDMorning) {
        this.workIDMorning = workIDMorning;
    }

    public String getWorkIDAfternoon() {
        return workIDAfternoon;
    }

    public void setWorkIDAfternoon(String workIDAfternoon) {
        this.workIDAfternoon = workIDAfternoon;
    }
}
