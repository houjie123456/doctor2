package com.company.wanbei.app.http;

import com.company.wanbei.app.bean.GroupAnnouncementBean;
import com.company.wanbei.app.bean.GroupMemberBean;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by YC on 2018/8/4.
 */

public class JSONGroupAnnouncement {
    private String code;
    @SerializedName("msgbox")
    private String msgBox;
    private GroupAnnouncementBean table;

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

    public GroupAnnouncementBean getTable() {
        return table;
    }

    public void setTable(GroupAnnouncementBean table) {
        this.table = table;
    }
}
