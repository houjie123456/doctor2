package com.company.wanbei.app.http;

import com.company.wanbei.app.bean.AdviceInfoBean;
import com.company.wanbei.app.bean.AdviceListBean;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by YC on 2018/8/1.
 */

public class JSONAdviceInfo {
    private String code;
    @SerializedName("msgbox")
    private String msgBox;
    private String pageCount;
    private String rowCount;
    private AdviceInfoBean table;

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

    public AdviceInfoBean getTable() {
        return table;
    }

    public void setTable(AdviceInfoBean table) {
        this.table = table;
    }
}
