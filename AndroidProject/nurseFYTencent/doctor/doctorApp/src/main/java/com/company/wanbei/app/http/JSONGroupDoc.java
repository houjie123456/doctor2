package com.company.wanbei.app.http;

import com.company.wanbei.app.bean.AddressBean;
import com.company.wanbei.app.bean.GroupMemberBean;
import com.company.wanbei.app.bean.MemberBean;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by YC on 2018/8/4.
 */

public class JSONGroupDoc {
    private String code;
    @SerializedName("msgbox")
    private String msgBox;
    private ArrayList<GroupMemberBean> manageArray;
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

    public ArrayList<GroupMemberBean> getManageArray() {
        return manageArray;
    }

    public void setManageArray(ArrayList<GroupMemberBean> manageArray) {
        this.manageArray = manageArray;
    }

    public ArrayList<GroupMemberBean> getTable() {
        return table;
    }

    public void setTable(ArrayList<GroupMemberBean> table) {
        this.table = table;
    }
}
