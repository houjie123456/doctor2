package com.company.wanbei.app.http;

import com.company.wanbei.app.bean.PictureFaceBean;

/**
 * Created by YC on 2017/7/11.
 */

public class JSONCounseling {
    private String code;
    private String msgbox;

    private PictureFaceBean table;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsgbox() {
        return msgbox;
    }

    public void setMsgbox(String msgbox) {
        this.msgbox = msgbox;
    }

    public PictureFaceBean getTable() {
        return table;
    }

    public void setTable(PictureFaceBean table) {
        this.table = table;
    }
}
