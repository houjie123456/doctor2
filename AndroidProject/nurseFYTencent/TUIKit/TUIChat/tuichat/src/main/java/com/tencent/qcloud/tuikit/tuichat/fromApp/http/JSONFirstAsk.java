package com.tencent.qcloud.tuikit.tuichat.fromApp.http;

import com.google.gson.annotations.SerializedName;
import com.tencent.qcloud.tuikit.tuichat.fromApp.bean.FirstAskBean;

import java.util.ArrayList;

/**
 * Created by YC on 2018/8/4.
 */

public class JSONFirstAsk {
    private String code;
    @SerializedName("msgbox")
    private String msgBox;
    private FirstAskBean table;

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

    public FirstAskBean getTable() {
        return table;
    }

    public void setTable(FirstAskBean table) {
        this.table = table;
    }
}
