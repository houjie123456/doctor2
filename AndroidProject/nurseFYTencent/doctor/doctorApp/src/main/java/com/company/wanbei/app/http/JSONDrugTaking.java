package com.company.wanbei.app.http;

import com.company.wanbei.app.bean.DrugTakingBean;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by YC on 2017/7/11.
 */

public class JSONDrugTaking {
    private String code;
    @SerializedName("msgbox")
    private String msgBox;
    private String pageCount;
    private String rowCount;
    private ArrayList<DrugTakingBean> table;

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

    public String getPageCount() {
        return pageCount;
    }

    public void setPageCount(String pageCount) {
        this.pageCount = pageCount;
    }

    public String getRowCount() {
        return rowCount;
    }

    public void setRowCount(String rowCount) {
        this.rowCount = rowCount;
    }

    public ArrayList<DrugTakingBean> getTable() {
        return table;
    }

    public void setTable(ArrayList<DrugTakingBean> table) {
        this.table = table;
    }
}
