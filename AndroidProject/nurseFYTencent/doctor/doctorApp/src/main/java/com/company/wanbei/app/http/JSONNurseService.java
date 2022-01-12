package com.company.wanbei.app.http;

import com.company.wanbei.app.bean.NurseServiceBean;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by YC on 2018/6/29.
 */

public class JSONNurseService {

    private String code;
    @SerializedName("msgbox")
    private String msgBox;
    private ArrayList<NurseServiceBean> data;
    private ArrayList<String> describe;

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

    public ArrayList<NurseServiceBean> getData() {
        return data;
    }

    public void setData(ArrayList<NurseServiceBean> data) {
        this.data = data;
    }

    public ArrayList<String> getDescribe() {
        return describe;
    }

    public void setDescribe(ArrayList<String> describe) {
        this.describe = describe;
    }
}
