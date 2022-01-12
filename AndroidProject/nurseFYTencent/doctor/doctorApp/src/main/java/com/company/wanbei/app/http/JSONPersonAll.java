package com.company.wanbei.app.http;

import com.company.wanbei.app.bean.NurseListBean;
import com.company.wanbei.app.bean.PictureBean;
import com.company.wanbei.app.bean.UserInfoBean;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by YC on 2018/7/2.
 */

public class JSONPersonAll {
    private String code;
    @SerializedName("msgbox")
    private String msgBox;
    private ArrayList<PictureBean> picTable;
    private JSONBean data;
    private ArrayList<NurseListBean> seriveNur;

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

    public JSONBean getData() {
        return data;
    }

    public void setData(JSONBean data) {
        this.data = data;
    }

    public ArrayList<PictureBean> getPicTable() {
        return picTable;
    }

    public void setPicTable(ArrayList<PictureBean> picTable) {
        this.picTable = picTable;
    }

    public ArrayList<NurseListBean> getSeriveNur() {
        return seriveNur;
    }

    public void setSeriveNur(ArrayList<NurseListBean> seriveNur) {
        this.seriveNur = seriveNur;
    }
}
