package com.company.linquan.app.http;

import com.company.linquan.app.bean.DiscussBean;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by YC on 2018/6/26.
 */

public class JSONDiscuss {
    private String code;
    @SerializedName("msgbox")
    private String msgBox;
    private String  pageCount;//总页数
    private String  rowCount;//总条数
    private ArrayList<DiscussBean> table;

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

    public ArrayList<DiscussBean> getTable() {
        return table;
    }

    public void setTable(ArrayList<DiscussBean> table) {
        this.table = table;
    }
}
