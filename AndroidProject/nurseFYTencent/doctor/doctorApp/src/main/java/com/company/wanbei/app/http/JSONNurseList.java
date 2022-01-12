package com.company.wanbei.app.http;

import com.company.wanbei.app.bean.NurseListBean;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by YC on 2018/6/29.
 */

public class JSONNurseList {

    private String code;
    @SerializedName("msgbox")
    private String msgBox;
    private ArrayList<NurseListBean> data;

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

    public ArrayList<NurseListBean> getData() {
        return data;
    }

    public void setData(ArrayList<NurseListBean> data) {
        this.data = data;
    }
}
