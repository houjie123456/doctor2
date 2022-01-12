package com.company.wanbei.app.bean;

/**
 * Created by YC on 2018/7/8.
 */

public class SelectDataBean {

    /**
     * 职业信息数据
     */
    private String jobName;
    private String sex;

    /**
     * 职称信息数据
     */
    private String positionName;
    //id-职称(数字)
    //idVal-职称(中文名称)
    private String idVal;

    /**
     * 医院信息数据
     */
    private String id;
    private String thisName;
    private String thisRemark;
    private String provinceID;
    private String provinceName;
    private String cityID;
    private String cityName;
    private String personID;
    private String townID;
    private String townName;
    private String address;
    private String hospital;
    private String thisLevel;

    /**
     * 科室信息数据
     */
    private String myName;
    private String departmentName;
    private String departmentRemark;
    private String contactTel;
    private String masterName;
    private String departmentType;
    private String hospitalID;

    public String getIdVal() {
        return idVal;
    }

    public void setIdVal(String idVal) {
        this.idVal = idVal;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public String getProvinceID() {
        return provinceID;
    }

    public void setProvinceID(String provinceID) {
        this.provinceID = provinceID;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityID() {
        return cityID;
    }

    public void setCityID(String cityID) {
        this.cityID = cityID;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getTownID() {
        return townID;
    }

    public void setTownID(String townID) {
        this.townID = townID;
    }

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }



    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentRemark() {
        return departmentRemark;
    }

    public void setDepartmentRemark(String departmentRemark) {
        this.departmentRemark = departmentRemark;
    }

    public String getContactTel() {
        return contactTel;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
    }

    public String getMasterName() {
        return masterName;
    }

    public void setMasterName(String masterName) {
        this.masterName = masterName;
    }

    public String getDepartmentType() {
        return departmentType;
    }

    public void setDepartmentType(String departmentType) {
        this.departmentType = departmentType;
    }

    public String getHospitalID() {
        return hospitalID;
    }

    public void setHospitalID(String hospitalID) {
        this.hospitalID = hospitalID;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getThisName() {
        return thisName;
    }

    public void setThisName(String thisName) {
        this.thisName = thisName;
    }

    public String getThisRemark() {
        return thisRemark;
    }

    public void setThisRemark(String thisRemark) {
        this.thisRemark = thisRemark;
    }

    public String getThisLevel() {
        return thisLevel;
    }

    public void setThisLevel(String thisLevel) {
        this.thisLevel = thisLevel;
    }

    public String getMyName() {
        return myName;
    }

    public void setMyName(String myName) {
        this.myName = myName;
    }
}
