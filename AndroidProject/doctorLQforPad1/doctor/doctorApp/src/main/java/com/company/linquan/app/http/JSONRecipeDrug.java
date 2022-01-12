package com.company.linquan.app.http;

import com.company.linquan.app.bean.RecipeDrugBean;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by YC on 2018/7/1.
 */

public class JSONRecipeDrug {
    private String code;
    @SerializedName("msgbox")
    private String msgBox;
    private ArrayList<RecipeDrugBean> table;

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

    public ArrayList<RecipeDrugBean> getTable() {
        return table;
    }

    public void setTable(ArrayList<RecipeDrugBean> table) {
        this.table = table;
    }
}
