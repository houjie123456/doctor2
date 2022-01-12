package com.company.wanbei.app.bean;

/**
 * Created by YC on 2018/6/27.
 */

public class FaceDiagnoseBean {
    private String faceID;
    private String dateStr;
    private String weekStr;
    private String morning;
    private String afternoon;
    private String faceIDMorning;
    private String faceIDAfternoon;
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
