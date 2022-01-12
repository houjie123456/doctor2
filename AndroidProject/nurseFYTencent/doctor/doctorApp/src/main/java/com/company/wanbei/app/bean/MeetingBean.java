package com.company.wanbei.app.bean;

import com.company.wanbei.app.http.ScheduleBean;

import java.util.ArrayList;

/**
 * Created by YC on 2018/6/22.
 */

public class MeetingBean {
    private String id;//：id
    private String title;//：标题
    private String speaker;//：讲人
    private String address;//：详细地址
    private String participants;//：参会人员
    private String content;//：会议内容
    private String requirement;//：会议要求
    private String meetingCover1;//：封面图片1
    private String meetingCover2;//：封面图片2
    private String meetingTime;//：会议时间


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSpeaker() {
        return speaker;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getParticipants() {
        return participants;
    }

    public void setParticipants(String participants) {
        this.participants = participants;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public String getMeetingCover1() {
        return meetingCover1;
    }

    public void setMeetingCover1(String meetingCover1) {
        this.meetingCover1 = meetingCover1;
    }

    public String getMeetingCover2() {
        return meetingCover2;
    }

    public void setMeetingCover2(String meetingCover2) {
        this.meetingCover2 = meetingCover2;
    }

    public String getMeetingTime() {
        return meetingTime;
    }

    public void setMeetingTime(String meetingTime) {
        this.meetingTime = meetingTime;
    }
}
