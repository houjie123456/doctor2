package com.company.wanbei.app.http;

import com.google.gson.annotations.SerializedName;

/**
 * Created by YC on 2018/8/4.
 */

public class JSONOnlineStudy {
    private String code;
    @SerializedName("msgbox")
    private String msgBox;
    private String sign;

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

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
