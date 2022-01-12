package com.company.linquan.app.http;

import com.company.linquan.app.bean.FaceDiagnoseBean;
import com.company.linquan.app.bean.HomeCareBean;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by YC on 2018/6/27.
 */

public class JSONHomeCare {
    private String code;
    @SerializedName("msgbox")
    private String msgBox;
    private ArrayList<HomeCareBean> table;

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

    public ArrayList<HomeCareBean> getTable() {
        return table;
    }

    public void setTable(ArrayList<HomeCareBean> table) {
        this.table = table;
    }
}
