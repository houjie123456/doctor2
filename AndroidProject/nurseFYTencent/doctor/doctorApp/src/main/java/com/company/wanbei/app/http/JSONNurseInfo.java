package com.company.wanbei.app.http;

import com.company.wanbei.app.bean.NurseInfoBean;
import com.company.wanbei.app.bean.NurseToolBean;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by YC on 2018/8/4.
 */

public class JSONNurseInfo {
    /*
    code-1.成功 0.失败
    msgbox-信息说明
    id-护理类型id
    typeName-护理类型名称
     */
    private String code;
    @SerializedName("msgbox")
    private String msgBox;
    private NurseInfoBean data;
    private ArrayList<NurseToolBean> hosBuffer;


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

    public NurseInfoBean getData() {
        return data;
    }

    public void setData(NurseInfoBean data) {
        this.data = data;
    }

    public ArrayList<NurseToolBean> getHosBuffer() {
        return hosBuffer;
    }

    public void setHosBuffer(ArrayList<NurseToolBean> hosBuffer) {
        this.hosBuffer = hosBuffer;
    }
}
