package com.company.wanbei.app.http;

import com.company.wanbei.app.bean.ReportBean2;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by YC on 2018/7/15.
 */

public class JSONReport2Desc {

    private String code;
    @SerializedName("msgbox")
    private String msgBox;
    private ArrayList<ReportBean2> table;

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


    public ArrayList<ReportBean2> getTable() {
        return table;
    }

    public void setTable(ArrayList<ReportBean2> table) {
        this.table = table;
    }

}
