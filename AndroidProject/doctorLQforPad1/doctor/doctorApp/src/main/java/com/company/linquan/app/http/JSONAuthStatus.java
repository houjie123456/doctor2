package com.company.linquan.app.http;

import com.company.linquan.app.bean.AuthStatusBean;
import com.google.gson.annotations.SerializedName;

/**
 * Created by YC on 2018/7/1.
 */

public class JSONAuthStatus {
    private String code;
    @SerializedName("msgbox")
    private String msgBox;
    private AuthStatusBean infoJson;

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

    public AuthStatusBean getInfoJson() {
        return infoJson;
    }

    public void setInfoJson(AuthStatusBean infoJson) {
        this.infoJson = infoJson;
    }
}
