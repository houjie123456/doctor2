package com.company.linquan.app.bean;

/**
 * Created by YC on 2018/7/15.
 */

public class OrderTeamBean {
    private String orderId;
    private String amount;
    private String payManID;
    private String payManName;
    private String payTime;
    private String advisoryText;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPayManID() {
        return payManID;
    }

    public void setPayManID(String payManID) {
        this.payManID = payManID;
    }

    public String getPayManName() {
        return payManName;
    }

    public void setPayManName(String payManName) {
        this.payManName = payManName;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getAdvisoryText() {
        return advisoryText;
    }

    public void setAdvisoryText(String advisoryText) {
        this.advisoryText = advisoryText;
    }
}
