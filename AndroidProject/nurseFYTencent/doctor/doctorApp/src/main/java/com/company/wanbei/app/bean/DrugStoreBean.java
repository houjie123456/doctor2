package com.company.wanbei.app.bean;

/**
 * Created by YC on 2018/7/1.
 */

public class DrugStoreBean {

    private String id;
    private String drugName;
    private String component;
    private String characterInfo;
    private String indication;
    private String standard;
    private String usageDosage;
    private String adverse;
    private String taboo;
    private String attention;
    private String pregnantWomenUse;
    private String childrenUse;
    private String elderlyUse;
    private String interactionDrug;
    private String overdose;
    private String pharmacology;
    private String pharmacokinetics;
    private String storage;
    private String packing;
    private String validity;
    private String standardExecut;
    private String approval;
    private String drugFactory;
    private String price;

    private String drugStockNum;// ：药品库存量
    private String unit;

    private String factoryId;// ：药房id（同一张处方不能同时存在两家药房的药）
    private String drugType;// 药品类型1 需要对接类型 2 不需要对接类型 （同一张处方只能存在一种类型）
    private String drugTypeName;// ：药品类型说明 1 西药 2 成药 3 草药 4 保健品 5 食品 6 化妆品 7 生物制品 8 二类医疗器械

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getCharacterInfo() {
        return characterInfo;
    }

    public void setCharacterInfo(String characterInfo) {
        this.characterInfo = characterInfo;
    }

    public String getIndication() {
        return indication;
    }

    public void setIndication(String indication) {
        this.indication = indication;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getUsageDosage() {
        return usageDosage;
    }

    public void setUsageDosage(String usageDosage) {
        this.usageDosage = usageDosage;
    }

    public String getAdverse() {
        return adverse;
    }

    public void setAdverse(String adverse) {
        this.adverse = adverse;
    }

    public String getTaboo() {
        return taboo;
    }

    public void setTaboo(String taboo) {
        this.taboo = taboo;
    }

    public String getAttention() {
        return attention;
    }

    public void setAttention(String attention) {
        this.attention = attention;
    }

    public String getPregnantWomenUse() {
        return pregnantWomenUse;
    }

    public void setPregnantWomenUse(String pregnantWomenUse) {
        this.pregnantWomenUse = pregnantWomenUse;
    }

    public String getChildrenUse() {
        return childrenUse;
    }

    public void setChildrenUse(String childrenUse) {
        this.childrenUse = childrenUse;
    }

    public String getElderlyUse() {
        return elderlyUse;
    }

    public void setElderlyUse(String elderlyUse) {
        this.elderlyUse = elderlyUse;
    }

    public String getInteractionDrug() {
        return interactionDrug;
    }

    public void setInteractionDrug(String interactionDrug) {
        this.interactionDrug = interactionDrug;
    }

    public String getOverdose() {
        return overdose;
    }

    public void setOverdose(String overdose) {
        this.overdose = overdose;
    }

    public String getPharmacology() {
        return pharmacology;
    }

    public void setPharmacology(String pharmacology) {
        this.pharmacology = pharmacology;
    }

    public String getPharmacokinetics() {
        return pharmacokinetics;
    }

    public void setPharmacokinetics(String pharmacokinetics) {
        this.pharmacokinetics = pharmacokinetics;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public String getPacking() {
        return packing;
    }

    public void setPacking(String packing) {
        this.packing = packing;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public String getStandardExecut() {
        return standardExecut;
    }

    public void setStandardExecut(String standardExecut) {
        this.standardExecut = standardExecut;
    }

    public String getApproval() {
        return approval;
    }

    public void setApproval(String approval) {
        this.approval = approval;
    }

    public String getDrugFactory() {
        return drugFactory;
    }

    public void setDrugFactory(String drugFactory) {
        this.drugFactory = drugFactory;
    }

    public String getDrugPrice() {
        return price;
    }

    public void setDrugPrice(String drugPrice) {
        this.price = drugPrice;
    }

    public String getDrugStockNum() {
        return drugStockNum;
    }

    public void setDrugStockNum(String drugStockNum) {
        this.drugStockNum = drugStockNum;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getFactoryId() {
        return factoryId;
    }

    public void setFactoryId(String factoryId) {
        this.factoryId = factoryId;
    }

    public String getDrugType() {
        return drugType;
    }

    public void setDrugType(String drugType) {
        this.drugType = drugType;
    }

    public String getDrugTypeName() {
        return drugTypeName;
    }

    public void setDrugTypeName(String drugTypeName) {
        this.drugTypeName = drugTypeName;
    }
}
