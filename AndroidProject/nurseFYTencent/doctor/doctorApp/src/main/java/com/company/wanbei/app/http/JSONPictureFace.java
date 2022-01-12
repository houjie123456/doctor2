package com.company.wanbei.app.http;

import com.company.wanbei.app.bean.PictureFaceBean;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by YC on 2018/6/30.
 */

public class JSONPictureFace {

    private String code;
    @SerializedName("msgbox")
    private String msgBox;
    private String rowCount;
    private ArrayList<PictureFaceBean> table;

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

    public String getRowCount() {
        return rowCount;
    }

    public void setRowCount(String rowCount) {
        this.rowCount = rowCount;
    }

    public ArrayList<PictureFaceBean> getTable() {
        return table;
    }

    public void setTable(ArrayList<PictureFaceBean> table) {
        this.table = table;
    }
}
