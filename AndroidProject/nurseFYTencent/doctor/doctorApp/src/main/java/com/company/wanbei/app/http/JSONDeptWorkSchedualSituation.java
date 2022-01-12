package com.company.wanbei.app.http;

import com.company.wanbei.app.bean.AddressBean;
import com.company.wanbei.app.bean.DoctorBean;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by YC on 2018/8/4.
 */

public class JSONDeptWorkSchedualSituation {
    private String code;
    @SerializedName("msgbox")
    private String msgBox;
    private ArrayList<DoctorBean> table;

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

    public ArrayList<DoctorBean> getTable() {
        return table;
    }

    public void setTable(ArrayList<DoctorBean> table) {
        this.table = table;
    }
}
