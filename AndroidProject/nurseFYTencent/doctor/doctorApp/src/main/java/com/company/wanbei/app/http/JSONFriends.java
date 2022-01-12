package com.company.wanbei.app.http;

import com.company.wanbei.app.bean.FriendsBean;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by YC on 2018/6/30.
 */

public class JSONFriends {
    private String code;
    @SerializedName("msgbox")
    private String msgBox;
    private ArrayList<FriendsBean> table;

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

    public ArrayList<FriendsBean> getTable() {
        return table;
    }

    public void setTable(ArrayList<FriendsBean> table) {
        this.table = table;
    }
}
