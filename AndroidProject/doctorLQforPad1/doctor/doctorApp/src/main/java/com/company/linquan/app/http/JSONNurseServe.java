package com.company.linquan.app.http;

import com.company.linquan.app.bean.NurseServeBean;
import com.company.linquan.app.bean.NurseTypeBean;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by YC on 2018/8/4.
 */

public class JSONNurseServe {
    /*
    code-1.成功 0.失败
    msgbox-信息说明
    id-护理类型id
    typeName-护理类型名称
     */
    private String code;
    @SerializedName("msgbox")
    private String msgBox;
    private ArrayList<NurseServeBean> table;

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

    public ArrayList<NurseServeBean> getTable() {
        return table;
    }

    public void setTable(ArrayList<NurseServeBean> table) {
        this.table = table;
    }
}
