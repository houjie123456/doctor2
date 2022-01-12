package com.company.wanbei.app.http;

import com.company.wanbei.app.bean.SignOtherPersonBean;
import com.company.wanbei.app.bean.SignPersonBean;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by YC on 2018/8/5.
 */

public class JSONSignPerson {

    private String code;
    @SerializedName("msgbox")
    private String msgBox;
    private SignPersonBean infoJson;
    private ArrayList<SignOtherPersonBean> otherTable;

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

    public SignPersonBean getInfoJson() {
        return infoJson;
    }

    public void setInfoJson(SignPersonBean infoJson) {
        this.infoJson = infoJson;
    }

    public ArrayList<SignOtherPersonBean> getOtherTable() {
        return otherTable;
    }

    public void setOtherTable(ArrayList<SignOtherPersonBean> otherTable) {
        this.otherTable = otherTable;
    }
}
