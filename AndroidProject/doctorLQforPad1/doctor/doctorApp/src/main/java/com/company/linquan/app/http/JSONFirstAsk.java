package com.company.linquan.app.http;

import com.company.linquan.app.bean.AddressBean;
import com.company.linquan.app.bean.FileCollectInfoBean;
import com.company.linquan.app.bean.MyInqueryInfoBean;
import com.company.linquan.app.bean.PatientInfoBean;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by YC on 2018/8/4.
 */

public class JSONFirstAsk {
    private String code;
    @SerializedName("msgbox")
    private String msgBox;
    private PatientInfoBean baseInfoJson;
    private ArrayList<MyInqueryInfoBean> healthTable;
    private ArrayList<FileCollectInfoBean> picTable;

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

    public PatientInfoBean getBaseInfoJson() {
        return baseInfoJson;
    }

    public void setBaseInfoJson(PatientInfoBean baseInfoJson) {
        this.baseInfoJson = baseInfoJson;
    }

    public ArrayList<MyInqueryInfoBean> getHealthTable() {
        return healthTable;
    }

    public void setHealthTable(ArrayList<MyInqueryInfoBean> healthTable) {
        this.healthTable = healthTable;
    }

    public ArrayList<FileCollectInfoBean> getPicTable() {
        return picTable;
    }

    public void setPicTable(ArrayList<FileCollectInfoBean> picTable) {
        this.picTable = picTable;
    }
}
