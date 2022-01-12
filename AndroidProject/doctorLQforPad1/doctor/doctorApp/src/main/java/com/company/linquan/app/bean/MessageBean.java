package com.company.linquan.app.bean;

/**
 * Created by YC on 2018/8/4.
 */

public class MessageBean {
    private String fromid;//发起人ID
    private String fromname;//发起人姓名
    private String headurl;//发起人头像
    private String fromtype;//发起人类型 0,患者,1医生  2.药师
    private String contentchat;//聊天内容
    private String messagetype;//类型 1.文字 2.图片 3.语音 4.视频 5.位置信息 6.自定义信息 99.其他
    private String sendtime;//添加时间（时间戳）
    private String senddate;//添加时间（字符串）
    private String toid;//接受人ID
    private String totype;//接受人类型 0,患者,1医生 2.药师

    public String getFromid() {
        return fromid;
    }

    public void setFromid(String fromid) {
        this.fromid = fromid;
    }

    public String getFromname() {
        return fromname;
    }

    public void setFromname(String fromname) {
        this.fromname = fromname;
    }

    public String getHeadurl() {
        return headurl;
    }

    public void setHeadurl(String headurl) {
        this.headurl = headurl;
    }

    public String getFromtype() {
        return fromtype;
    }

    public void setFromtype(String fromtype) {
        this.fromtype = fromtype;
    }

    public String getContentchat() {
        return contentchat;
    }

    public void setContentchat(String contentchat) {
        this.contentchat = contentchat;
    }

    public String getMessagetype() {
        return messagetype;
    }

    public void setMessagetype(String messagetype) {
        this.messagetype = messagetype;
    }

    public String getSendtime() {
        return sendtime;
    }

    public void setSendtime(String sendtime) {
        this.sendtime = sendtime;
    }

    public String getSenddate() {
        return senddate;
    }

    public void setSenddate(String senddate) {
        this.senddate = senddate;
    }

    public String getToid() {
        return toid;
    }

    public void setToid(String toid) {
        this.toid = toid;
    }

    public String getTotype() {
        return totype;
    }

    public void setTotype(String totype) {
        this.totype = totype;
    }
}
