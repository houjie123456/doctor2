package com.company.linquan.app.http;

import com.company.linquan.app.bean.NurseListBean;
import com.company.linquan.app.bean.NurseServiceBean;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by YC on 2018/6/29.
 */

public class JSONNurseList {

    private String code;
    @SerializedName("msgbox")
    private String msgBox;
    private ArrayList<NurseListBean> table;

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

    public ArrayList<NurseListBean> getTable() {
        return table;
    }

    public void setTable(ArrayList<NurseListBean> table) {
        this.table = table;
    }
}
