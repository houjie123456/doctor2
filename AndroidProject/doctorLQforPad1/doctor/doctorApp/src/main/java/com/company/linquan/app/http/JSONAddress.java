package com.company.linquan.app.http;

import com.company.linquan.app.bean.AddressBean;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by YC on 2018/8/4.
 */

public class JSONAddress {
    private String code;
    @SerializedName("msgbox")
    private String msgBox;
    private ArrayList<AddressBean> table;

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

    public ArrayList<AddressBean> getTable() {
        return table;
    }

    public void setTable(ArrayList<AddressBean> table) {
        this.table = table;
    }
}
