package com.company.wanbei.app.http;

import com.company.wanbei.app.bean.UserInfoBean;
import com.google.gson.annotations.SerializedName;

/**
 * Created by YC on 2018/7/22.
 */

public class JSONMe {
    private String code;
    @SerializedName("msgbox")
    private String msgBox;
    private String codeUrl;
    private UserInfoBean infoJson;
    private JSONMe data;

    public JSONMe getData() {
        return data;
    }

    public void setData(JSONMe data) {
        this.data = data;
    }

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

    public String getCodeUrl() {
        return codeUrl;
    }

    public void setCodeUrl(String codeUrl) {
        this.codeUrl = codeUrl;
    }

    public UserInfoBean getInfoJson() {
        return infoJson;
    }

    public void setInfoJson(UserInfoBean infoJson) {
        this.infoJson = infoJson;
    }
}
