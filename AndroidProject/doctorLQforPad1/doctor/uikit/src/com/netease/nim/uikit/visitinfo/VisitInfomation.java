package com.netease.nim.uikit.visitinfo;

import java.util.ArrayList;

public class VisitInfomation {
    private String bespeakId;
    private String visitName;
    private String idType;
    /**
     * 网易云账号
     * **/
    private String accountID;
    private String token;
    private String wyyID;
    private ArrayList<String> visIDs;
    private ArrayList<String> visNames;

    private static VisitInfomation ourInstance = new VisitInfomation();

    public static VisitInfomation getInstance() {
        return ourInstance;
    }

    public String getVisitName() {
        return visitName;
    }

    public void setVisitName(String visitName) {
        this.visitName = visitName;
    }

    public ArrayList<String> getVisIDs() {
        return visIDs;
    }

    public void setVisIDs(ArrayList<String> visIDs) {
        this.visIDs = visIDs;
    }

    public ArrayList<String> getVisNames() {
        return visNames;
    }

    public void setVisNames(ArrayList<String> visNames) {
        this.visNames = visNames;
    }

    public String getBespeakId() {
        return bespeakId;
    }

    public void setBespeakId(String bespeakId) {
        this.bespeakId = bespeakId;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getWyyID() {
        return wyyID;
    }

    public void setWyyID(String wyyID) {
        this.wyyID = wyyID;
    }
}
