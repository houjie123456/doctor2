package com.company.wanbei.app.http;

import com.company.wanbei.app.bean.NurseTypeBean;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by YC on 2018/8/4.
 */

public class JSONNurseType {
    /*
    code-1.成功 0.失败
    msgbox-信息说明
    id-护理类型id
    typeName-护理类型名称
     */
    private String code;
    @SerializedName("msgbox")
    private String msgBox;
    private ArrayList<NurseTypeBean> data;

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

    public ArrayList<NurseTypeBean> getData() {
        return data;
    }

    public void setData(ArrayList<NurseTypeBean> data) {
        this.data = data;
    }
}
