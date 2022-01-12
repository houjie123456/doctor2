package com.company.wanbei.app.http;

/**
 * Created by YC on 2018/7/13.
 */

public class ScheduleBean {
    private String currDate;
    private String id;
    private String startHour;
    private String endHour;
    private String content;
    private String topicAddress;

    public String getCurrDate() {
        return currDate;
    }

    public void setCurrDate(String currDate) {
        this.currDate = currDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStartHour() {
        return startHour;
    }

    public void setStartHour(String startHour) {
        this.startHour = startHour;
    }

    public String getEndHour() {
        return endHour;
    }

    public void setEndHour(String endHour) {
        this.endHour = endHour;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTopicAddress() {
        return topicAddress;
    }

    public void setTopicAddress(String topicAddress) {
        this.topicAddress = topicAddress;
    }
}
