package com.company.wanbei.app.http;

import com.company.wanbei.app.bean.OperationDescBean;
import com.google.gson.annotations.SerializedName;

/**
 * Created by YC on 2018/6/27.
 */

public class JSONOperationDesc {
    private String code;
    @SerializedName("msgbox")
    private String msgBox;
    private OperationDescBean infoJson;

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

    public OperationDescBean getInfoJson() {
        return infoJson;
    }

    public void setInfoJson(OperationDescBean infoJson) {
        this.infoJson = infoJson;
    }

}
