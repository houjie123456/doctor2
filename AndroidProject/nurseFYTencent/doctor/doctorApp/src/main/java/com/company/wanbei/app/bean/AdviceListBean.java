package com.company.wanbei.app.bean;

/**
 * Created by YC on 2018/8/4.
 */

public class AdviceListBean {

    private String id;// ：医嘱id
    private String visitName;// ：就诊人名称
    private String itemName;// ：医嘱项目
    private String executeTime;// ：执行时间
    private String regId;// ：就诊号

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVisitName() {
        return visitName;
    }

    public void setVisitName(String visitName) {
        this.visitName = visitName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(String executeTime) {
        this.executeTime = executeTime;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }
}
