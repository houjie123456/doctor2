package com.company.wanbei.app.http;

import com.company.wanbei.app.bean.PictureEvaluateBean;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by YC on 2018/6/29.
 */

public class JSONPictureEvaluate {

    private String pageCount;
    private String rowCount;
    private String code;
    @SerializedName("msgbox")
    private String msgBox;
    private ArrayList<PictureEvaluateBean> table;

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

    public ArrayList<PictureEvaluateBean> getTable() {
        return table;
    }

    public void setTable(ArrayList<PictureEvaluateBean> table) {
        this.table = table;
    }
}
