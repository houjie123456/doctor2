package com.company.wanbei.app.bean;

/**
 * Created by YC on 2018/8/4.
 */

public class AdviceInfoBean {
    private String id;// ：医嘱id
    private String visitName;// ：就诊人名称
    private String itemName;// ：医嘱项目
    private String specName;// ：医嘱标本
    private String regId;// ：就诊号
    private String itemNum;// ：医嘱数量
    private String remarks;// ：医嘱备注
    private String deptName;// ：执行科室名称
    private String executeTime;// ：执行时间

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

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public String getItemNum() {
        return itemNum;
    }

    public void setItemNum(String itemNum) {
        this.itemNum = itemNum;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(String executeTime) {
        this.executeTime = executeTime;
    }
}
