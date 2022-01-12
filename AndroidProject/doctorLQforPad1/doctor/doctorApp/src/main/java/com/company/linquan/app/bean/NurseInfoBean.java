package com.company.linquan.app.bean;

/**
 * Created by YC on 2018/8/4.
 */
/*
id-护理服务ID
serviceName-护理服务名称
servicePrice-服务价格
realPrice-实际价格
introduction-服务简介
useMans-使用人群
tabooTips-禁忌提示
appointmentNotice-预约须知
appointmentNotice-服务时间
refundRules-退款规则
expendPrice-耗材包价格
homeGoodsList-家用自备
maxPlayers-限制预约人数
 */
public class NurseInfoBean {

    private String id;
    private String serviceName;//-护理服务名称
    private String servicePrice;//-服务价格
    private String realPrice;//-实际价格
    private String introduction;//-服务简介
    private String useMans;//-使用人群
    private String tabooTips;//-禁忌提示
    private String appointmentNotice;//-预约须知
    private String serviceTime;//-服务时间
    private String refundRules;//-退款规则
    private String expendPrice;//-耗材包价格
    private String homeGoodsList;//-家用自备
    private String maxPlayers;//-限制预约人数

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(String servicePrice) {
        this.servicePrice = servicePrice;
    }

    public String getRealPrice() {
        return realPrice;
    }

    public void setRealPrice(String realPrice) {
        this.realPrice = realPrice;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getUseMans() {
        return useMans;
    }

    public void setUseMans(String useMans) {
        this.useMans = useMans;
    }

    public String getTabooTips() {
        return tabooTips;
    }

    public void setTabooTips(String tabooTips) {
        this.tabooTips = tabooTips;
    }

    public String getAppointmentNotice() {
        return appointmentNotice;
    }

    public void setAppointmentNotice(String appointmentNotice) {
        this.appointmentNotice = appointmentNotice;
    }

    public String getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(String serviceTime) {
        this.serviceTime = serviceTime;
    }

    public String getRefundRules() {
        return refundRules;
    }

    public void setRefundRules(String refundRules) {
        this.refundRules = refundRules;
    }

    public String getExpendPrice() {
        return expendPrice;
    }

    public void setExpendPrice(String expendPrice) {
        this.expendPrice = expendPrice;
    }

    public String getHomeGoodsList() {
        return homeGoodsList;
    }

    public void setHomeGoodsList(String homeGoodsList) {
        this.homeGoodsList = homeGoodsList;
    }

    public String getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(String maxPlayers) {
        this.maxPlayers = maxPlayers;
    }
}
