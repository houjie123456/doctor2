package com.company.wanbei.app.http;

import com.company.wanbei.app.bean.HomeCareBean;
import com.company.wanbei.app.bean.OperationScheduleBean;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by YC on 2018/6/27.
 */

public class JSONOperationSchedule {
    private String code;
    @SerializedName("msgbox")
    private String msgBox;
    private ArrayList<OperationScheduleBean> table;

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

    public ArrayList<OperationScheduleBean> getTable() {
        return table;
    }

    public void setTable(ArrayList<OperationScheduleBean> table) {
        this.table = table;
    }
}
