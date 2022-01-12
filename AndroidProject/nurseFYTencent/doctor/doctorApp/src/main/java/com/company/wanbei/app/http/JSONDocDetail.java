package com.company.wanbei.app.http;

import com.company.wanbei.app.bean.DocBean;
import com.google.gson.annotations.SerializedName;

/**
 * Created by YC on 2018/8/1.
 */

public class JSONDocDetail {

    private String code;
    @SerializedName("msgbox")
    private String msgBox;
    private DocBean infoJson;

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

    public DocBean getInfoJson() {
        return infoJson;
    }

    public void setInfoJson(DocBean infoJson) {
        this.infoJson = infoJson;
    }
}
