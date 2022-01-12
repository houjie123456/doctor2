package com.company.wanbei.app.http;

import com.company.wanbei.app.bean.DoctorBean;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by YC on 2018/8/1.
 */

public class JSONDoctor {
    private String code;
    @SerializedName("msgbox")
    private String msgBox;
    private ArrayList<DoctorBean> doctorList;
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

    public ArrayList<DoctorBean> getDoctorList() {
        return doctorList;
    }

    public void setDoctorList(ArrayList<DoctorBean> doctorList) {
        this.doctorList = doctorList;
    }

    public ArrayList<DoctorBean> getTable() {
        return table;
    }

    public void setTable(ArrayList<DoctorBean> table) {
        this.table = table;
    }
}
