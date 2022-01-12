package com.company.wanbei.app.http;

import com.company.wanbei.app.bean.OperationBean;
import com.company.wanbei.app.bean.OperationListBean;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by YC on 2018/6/27.
 */

public class JSONOperationList {
    private String code;
    @SerializedName("msgbox")
    private String msgBox;
    private OperationListBean infoJson;

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

    public OperationListBean getInfoJson() {
        return infoJson;
    }

    public void setInfoJson(OperationListBean infoJson) {
        this.infoJson = infoJson;
    }
}
