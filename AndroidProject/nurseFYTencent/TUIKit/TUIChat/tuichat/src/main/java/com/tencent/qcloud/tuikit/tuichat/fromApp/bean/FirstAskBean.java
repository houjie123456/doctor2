package com.tencent.qcloud.tuikit.tuichat.fromApp.bean;

import java.util.ArrayList;

/**
 * Created by YC on 2018/7/15.
 */

public class FirstAskBean {
    private PatientInfoBean baseInfoJson;
    private ArrayList<MyInqueryInfoBean> healthTable;
    private ArrayList<FileCollectInfoBean> picTable;
    private VoiceCheckStateBean checkJson;

    public PatientInfoBean getBaseInfoJson() {
        return baseInfoJson;
    }

    public void setBaseInfoJson(PatientInfoBean baseInfoJson) {
        this.baseInfoJson = baseInfoJson;
    }

    public ArrayList<MyInqueryInfoBean> getHealthTable() {
        return healthTable;
    }

    public void setHealthTable(ArrayList<MyInqueryInfoBean> healthTable) {
        this.healthTable = healthTable;
    }

    public ArrayList<FileCollectInfoBean> getPicTable() {
        return picTable;
    }

    public void setPicTable(ArrayList<FileCollectInfoBean> picTable) {
        this.picTable = picTable;
    }

    public VoiceCheckStateBean getCheckJson() {
        return checkJson;
    }

    public void setCheckJson(VoiceCheckStateBean checkJson) {
        this.checkJson = checkJson;
    }
}
