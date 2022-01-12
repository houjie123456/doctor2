package com.company.linquan.app.bean;

import java.util.ArrayList;

/**
 * Created by YC on 2018/6/29.
 */
/*
patientName-就诊人姓名
phonenum-联系方式
appointstarttime-预约开始时间
appointendtime-预约结束时间
address-患者地址
amount-金额
paystate-支付状态（0.未支付 1.已支付 2.支付成功 3.退款）
servicestate-服务状态（0.未开始 1.已服务 2.取消 3.结束）
orderid-订单号

illnessdescr-病情描述
sex-就诊人性别（1.男 2.女）
birthdate-出生日期
idcardno-身份证号
idCardFront-身份证正面
idCardAgainst-身份证反面
describe-描述图片（多张，最多9张）
 */
public class NurseServiceBean {
    private String id;
    private String patientName;
    private String phonenum;
    private String age;
    private String appointstarttime;
    private String appointendtime;
    private String address;
    private String amount;
    private String servicestate;
    private String paystate;
    private String orderid;
    private String illnessdescr;
    private String sex;
    private String birthdate;
    private String idcardno;
    private String idCardFront;
    private String idCardAgainst;
    private String checkState;
    private String checkRemark;
    private String describe;
    private ArrayList<String> describes;

    private String accountID;

    private boolean isShow;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public String getAppointstarttime() {
        return appointstarttime;
    }

    public void setAppointstarttime(String appointstarttime) {
        this.appointstarttime = appointstarttime;
    }

    public String getAppointendtime() {
        return appointendtime;
    }

    public void setAppointendtime(String appointendtime) {
        this.appointendtime = appointendtime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getServicestate() {
        return servicestate;
    }

    public void setServicestate(String servicestate) {
        this.servicestate = servicestate;
    }

    public String getPaystate() {
        return paystate;
    }

    public void setPaystate(String paystate) {
        this.paystate = paystate;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }
    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public String getIllnessdescr() {
        return illnessdescr;
    }

    public void setIllnessdescr(String illnessdescr) {
        this.illnessdescr = illnessdescr;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getIdcardno() {
        return idcardno;
    }

    public void setIdcardno(String idcardno) {
        this.idcardno = idcardno;
    }

    public String getIdCardFront() {
        return idCardFront;
    }

    public void setIdCardFront(String idCardFront) {
        this.idCardFront = idCardFront;
    }

    public String getIdCardAgainst() {
        return idCardAgainst;
    }

    public void setIdCardAgainst(String idCardAgainst) {
        this.idCardAgainst = idCardAgainst;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCheckState() {
        return checkState;
    }

    public void setCheckState(String checkState) {
        this.checkState = checkState;
    }

    public String getCheckRemark() {
        return checkRemark;
    }

    public void setCheckRemark(String checkRemark) {
        this.checkRemark = checkRemark;
    }

    public ArrayList<String> getDescribes() {
        return describes;
    }

    public void setDescribes(ArrayList<String> describes) {
        this.describes = describes;
    }

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }
}
