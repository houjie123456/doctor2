package com.company.wanbei.app.bean;

/**
 * Created by YC on 2018/8/4.
 */

public class MessageBean {
//    private String fromid;//发起人ID
//    private String fromname;//发起人姓名
//    private String headurl;//发起人头像
//    private String fromtype;//发起人类型 0,患者,1医生  2.药师
//    private String contentchat;//聊天内容
//    private String messagetype;//类型 1.文字 2.图片 3.语音 4.视频 5.位置信息 6.自定义信息 99.其他
//    private String sendtime;//添加时间（时间戳）
//    private String senddate;//添加时间（字符串）
//    private String toid;//接受人ID
//    private String totype;//接受人类型 0,患者,1医生 2.药师

    private String doctorName;//：医生姓名
    private String patientName;//：患者姓名
    private String patientId;//:患者ID
    private String fromType;//：发起人类型 0,患者,1医生  2.药师
    private String sendTime;//：发送时间
    private String contentChat;//：聊天内容(密文)
    private String messageType;//：类型 1.文字 2.图片 3.语音 4.视频 5.位置信息 6.自定义信息 99.其他




    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getFromType() {
        return fromType;
    }

    public void setFromType(String fromType) {
        this.fromType = fromType;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getContentChat() {
        return contentChat;
    }

    public void setContentChat(String contentChat) {
        this.contentChat = contentChat;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }
}
