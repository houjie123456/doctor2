package com.company.wanbei.app.http;

import com.company.wanbei.app.bean.MemberBean;
import com.company.wanbei.app.bean.OrderTeamBean;
import com.company.wanbei.app.bean.TeamDetailBean;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by YC on 2018/6/30.
 */

public class JSONTeam {
    private String code;
    @SerializedName("msgbox")
    private String msgBox;
    private TeamDetailBean infoJson;
    private ArrayList<MemberBean> table;
    private ArrayList<OrderTeamBean> orderTable;

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

    public TeamDetailBean getInfoJson() {
        return infoJson;
    }

    public void setInfoJson(TeamDetailBean infoJson) {
        this.infoJson = infoJson;
    }

    public ArrayList<MemberBean> getTable() {
        return table;
    }

    public void setTable(ArrayList<MemberBean> table) {
        this.table = table;
    }

    public ArrayList<OrderTeamBean> getOrderTable() {
        return orderTable;
    }

    public void setOrderTable(ArrayList<OrderTeamBean> orderTable) {
        this.orderTable = orderTable;
    }
}
