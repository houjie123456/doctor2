package com.company.wanbei.app.bean;

/**
 * Created by YC on 2018/6/26.
 */

public class DiscussBean {

    private String id;//评论ID
    private String thinkText;//评论内容
    private String thinkScore;//评论分数
    private String thinkManID;//评论人
    private String thinkManName;//评论人名称
    private String createTime;//评论时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getThinkText() {
        return thinkText;
    }

    public void setThinkText(String thinkText) {
        this.thinkText = thinkText;
    }

    public String getThinkScore() {
        return thinkScore;
    }

    public void setThinkScore(String thinkScore) {
        this.thinkScore = thinkScore;
    }

    public String getThinkManID() {
        return thinkManID;
    }

    public void setThinkManID(String thinkManID) {
        this.thinkManID = thinkManID;
    }

    public String getThinkManName() {
        return thinkManName;
    }

    public void setThinkManName(String thinkManName) {
        this.thinkManName = thinkManName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
