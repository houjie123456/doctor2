package com.company.wanbei.app.bean;

public class TransactionBean {
    private String id;//：收益明细id
    private String orderId;//：订单号
    private String visitName;//：患者姓名
    private String visitHeadUrl;//：患者头像
    private String profitType;//：收益类型
    private String profitTime;//：交易时间
    private String tradeAmount;//：交易金额
    private String amount;//：收益金额
    private String state;//：订单状态
    private String week;//：星期


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getVisitName() {
        return visitName;
    }

    public void setVisitName(String visitName) {
        this.visitName = visitName;
    }

    public String getVisitHeadUrl() {
        return visitHeadUrl;
    }

    public void setVisitHeadUrl(String visitHeadUrl) {
        this.visitHeadUrl = visitHeadUrl;
    }

    public String getProfitType() {
        return profitType;
    }

    public void setProfitType(String profitType) {
        this.profitType = profitType;
    }

    public String getProfitTime() {
        return profitTime;
    }

    public void setProfitTime(String profitTime) {
        this.profitTime = profitTime;
    }

    public String getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(String tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }
}
