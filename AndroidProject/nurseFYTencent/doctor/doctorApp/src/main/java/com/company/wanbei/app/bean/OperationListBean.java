package com.company.wanbei.app.bean;

/**
 * Created by YC on 2018/6/30.
 */

public class OperationListBean {
    private String id;//：手术服务id
    private String serviceTime;//：服务日期
    private String dateType;//：时间段类型 1.上午 2.下午
    private String totalNum;//：总共可预约次数
    private String amount;//：手术费用
    private String faceType;//：出停诊状态 1.出诊 2.停诊
    private String surplus;// ：预约状态 未预约/已预约

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(String serviceTime) {
        this.serviceTime = serviceTime;
    }

    public String getDateType() {
        return dateType;
    }

    public void setDateType(String dateType) {
        this.dateType = dateType;
    }

    public String getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(String totalNum) {
        this.totalNum = totalNum;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getFaceType() {
        return faceType;
    }

    public void setFaceType(String faceType) {
        this.faceType = faceType;
    }

    public String getSurplus() {
        return surplus;
    }

    public void setSurplus(String surplus) {
        this.surplus = surplus;
    }
}
