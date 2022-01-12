package com.company.wanbei.app.bean;

/**
 * Created by YC on 2018/8/4.
 */

public class HospitalBean {

    private String id;
    private String thisName;//：医院名称
    private String doctorMobile;//：转诊医生名称
    private String doctorName;//：转诊医生手机号
    private String address;//：详细地址
    private String doctorId;//：转诊医生id
    private boolean isSelected;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getThisName() {
        return thisName;
    }

    public void setThisName(String thisName) {
        this.thisName = thisName;
    }

    public String getDoctorMobile() {
        return doctorMobile;
    }

    public void setDoctorMobile(String doctorMobile) {
        this.doctorMobile = doctorMobile;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
