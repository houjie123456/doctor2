package com.company.wanbei.app.http;

import com.company.wanbei.app.bean.RecipeBean;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by YC on 2018/7/1.
 */

public class JSONRecipe {
    private String code;
    @SerializedName("msgbox")
    private String msgBox;
    private String pageCount;
    private String rowCount;
    private ArrayList<RecipeBean> table;
    private RecipeBean infoJson;

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

    public String getPageCount() {
        return pageCount;
    }

    public void setPageCount(String pageCount) {
        this.pageCount = pageCount;
    }

    public String getRowCount() {
        return rowCount;
    }

    public void setRowCount(String rowCount) {
        this.rowCount = rowCount;
    }

    public ArrayList<RecipeBean> getTable() {
        return table;
    }

    public void setTable(ArrayList<RecipeBean> table) {
        this.table = table;
    }

    public RecipeBean getInfoJson() {
        return infoJson;
    }

    public void setInfoJson(RecipeBean infoJson) {
        this.infoJson = infoJson;
    }
}
