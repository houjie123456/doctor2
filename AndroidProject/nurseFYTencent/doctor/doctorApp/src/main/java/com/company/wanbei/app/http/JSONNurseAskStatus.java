package com.company.wanbei.app.http;

import com.company.wanbei.app.bean.NurseAskBean;
import com.company.wanbei.app.bean.NurseAskScheduleBean;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by YC on 2018/8/4.
 */

public class JSONNurseAskStatus {
    private String code;
    @SerializedName("msgbox")
    private String msgBox;
    private NurseAskBean data;

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

    public NurseAskBean getData() {
        return data;
    }

    public void setData(NurseAskBean data) {
        this.data = data;
    }
}
