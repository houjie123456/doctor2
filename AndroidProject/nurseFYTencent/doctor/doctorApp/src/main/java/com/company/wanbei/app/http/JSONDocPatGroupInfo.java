package com.company.wanbei.app.http;

import com.company.wanbei.app.bean.DocPatGroupBean;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by YC on 2018/7/15.
 */

public class JSONDocPatGroupInfo {

    private String code;
    @SerializedName("msgbox")
    private String msgBox;
    private DocPatGroupBean table;

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


    public DocPatGroupBean getTable() {
        return table;
    }

    public void setTable(DocPatGroupBean table) {
        this.table = table;
    }

}
