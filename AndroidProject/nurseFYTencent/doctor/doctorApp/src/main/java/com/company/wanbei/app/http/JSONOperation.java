package com.company.wanbei.app.http;

import com.company.wanbei.app.bean.OperationBean;
import com.company.wanbei.app.bean.OperationScheduleBean;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by YC on 2018/6/27.
 */

public class JSONOperation {
    private String code;
    @SerializedName("msgbox")
    private String msgBox;
    private ArrayList<OperationBean> table;

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

    public ArrayList<OperationBean> getTable() {
        return table;
    }

    public void setTable(ArrayList<OperationBean> table) {
        this.table = table;
    }
}
