package com.company.linquan.app.bean;

/**
 * Created by YC on 2018/6/27.
 */
/*
dateStr-日期
weekStr-星期
personID-护士ID
morning-上午已预约人数
afternoon-下午已预约人数
peopleMorning-上午可预约人数
peopleAfternoon-下午可预约人数
 */
public class HomeCareBean {
    private String faceID;
    private String dateStr;
    private String weekStr;
    private String morningSign;
    private String afternoonSign;
    private String faceIDMorning;
    private String faceIDAfternoon;
    private String morning;//上午已预约人数
    private String afternoon;//下午已预约人数
    private String peopleMorning;//上午已预约人数
    private String peopleAfternoon;//下午已预约人数
    private String allNumberMorning;// 上午可预约总人数
    private String allNumberAfternoon;//下午可预约总人数

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

    public String getMorningSign() {
        return morningSign;
    }

    public void setMorningSign(String morningSign) {
        this.morningSign = morningSign;
    }

    public String getAfternoonSign() {
        return afternoonSign;
    }

    public void setAfternoonSign(String afternoonSign) {
        this.afternoonSign = afternoonSign;
    }

    public String getFaceIDMorning() {
        return faceIDMorning;
    }

    public void setFaceIDMorning(String faceIDMorning) {
        this.faceIDMorning = faceIDMorning;
    }

    public String getFaceIDAfternoon() {
        return faceIDAfternoon;
    }

    public void setFaceIDAfternoon(String faceIDAfternoon) {
        this.faceIDAfternoon = faceIDAfternoon;
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
}
