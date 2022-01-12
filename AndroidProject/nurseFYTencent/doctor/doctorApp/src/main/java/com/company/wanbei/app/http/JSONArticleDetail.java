package com.company.wanbei.app.http;

import com.company.wanbei.app.bean.ArticleBean;
import com.company.wanbei.app.bean.MeetingBean;
import com.google.gson.annotations.SerializedName;

/**
 * Created by YC on 2018/6/25.
 */

public class JSONArticleDetail {
    private String code;
    @SerializedName("msgbox")
    private String msgBox;
    private ArticleBean infoJson;

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

    public ArticleBean getInfoJson() {
        return infoJson;
    }

    public void setInfoJson(ArticleBean infoJson) {
        this.infoJson = infoJson;
    }
}
