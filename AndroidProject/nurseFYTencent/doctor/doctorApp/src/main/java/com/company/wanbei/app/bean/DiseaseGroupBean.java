package com.company.wanbei.app.bean;

import com.tencent.qcloud.tuikit.tuichat.fromApp.bean.DiseaseBean;

import java.util.ArrayList;

/**
 * Created by YC on 2018/6/26.
 */

public class DiseaseGroupBean {

    private String docid;
    private String groupname;
    private String groupid;//分组ID
    private ArrayList<DiseaseBean> groupInfoList;


    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public ArrayList<DiseaseBean> getGroupInfoList() {
        return groupInfoList;
    }

    public void setGroupInfoList(ArrayList<DiseaseBean> groupInfoList) {
        this.groupInfoList = groupInfoList;
    }
}
