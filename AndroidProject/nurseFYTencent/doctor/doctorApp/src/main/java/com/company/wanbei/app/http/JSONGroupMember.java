package com.company.wanbei.app.http;

import com.company.wanbei.app.bean.DocPatGroupBean;
import com.company.wanbei.app.bean.GroupMemberBean;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by YC on 2018/7/15.
 */

public class JSONGroupMember {

    private String code;
    @SerializedName("msgbox")
    private String msgBox;
    private ArrayList<GroupMemberBean> table;

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


    public ArrayList<GroupMemberBean> getTable() {
        return table;
    }

    public void setTable(ArrayList<GroupMemberBean> table) {
        this.table = table;
    }

}
