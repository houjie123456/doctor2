package com.company.wanbei.app.http;

import com.company.wanbei.app.bean.ArticleBean;
import com.company.wanbei.app.bean.TransactionBean;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by YC on 2018/7/15.
 */

public class JSONMyMoney {

    private String code;
    @SerializedName("msgbox")
    private String msgBox;
    private String pageCount;
    private String rowCount;
    private String totalAmount;
    private ArrayList<TransactionBean> table;
    private JSONMyMoney data;

    public JSONMyMoney getData() {
        return data;
    }

    public void setData(JSONMyMoney data) {
        this.data = data;
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

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public ArrayList<TransactionBean> getTable() {
        return table;
    }

    public void setTable(ArrayList<TransactionBean> table) {
        this.table = table;
    }

}
