package com.company.wanbei.app.bean;

/**
 * Created by YC on 2018/7/1.
 */

public class DrugBean {
    private String id;
    private String drugId;
    private String drugName;
    private String fristPinYin;
    private String standard;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDrugID() {
        return drugId;
    }

    public void setDrugID(String drugID) {
        this.drugId = drugID;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getFristPinYin() {
        return fristPinYin;
    }

    public void setFristPinYin(String fristPinYin) {
        this.fristPinYin = fristPinYin;
    }
}
