package com.company.linquan.app.bean;


/**
 * Created by YC on 2018/7/1.
 */

public class RecipeDrugBean {
    private String id;
    private String drugID;
    private DrugNameBean drugName;
    private String standard;
    private String drugNumber;
    private String dosage;
    private String takingTime;
    private String takingType;
    private String remark;
    private String drugPrice;
    private String drugUnit;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDrugID() {
        return drugID;
    }

    public void setDrugID(String drugID) {
        this.drugID = drugID;
    }

    public DrugNameBean getDrugName() {
        return drugName;
    }

    public void setDrugName(DrugNameBean drugName) {
        this.drugName = drugName;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getDrugNumber() {
        return drugNumber;
    }

    public void setDrugNumber(String drugNumber) {
        this.drugNumber = drugNumber;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getTakingTime() {
        return takingTime;
    }

    public void setTakingTime(String takingTime) {
        this.takingTime = takingTime;
    }

    public String getTakingType() {
        return takingType;
    }

    public void setTakingType(String takingType) {
        this.takingType = takingType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDrugPrice() {
        return drugPrice;
    }

    public void setDrugPrice(String drugPrice) {
        this.drugPrice = drugPrice;
    }

    public String getDrugUnit() {
        return drugUnit;
    }

    public void setDrugUnit(String drugUnit) {
        this.drugUnit = drugUnit;
    }
}
