package com.company.wanbei.app.tim;


import com.tencent.imsdk.v2.V2TIMMessage;

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

    private String docHead;
    private String docName;
    private String docTitle;
    private String docDept;
    private String docHos;

    private String doctorId;
    private String deptWorkstationId;

    private String content;

    private String workType;

    private String counselingHead;
    private String counselingName;
    private String counselingSex;
    private String counselingAge;

    private String toDocID;
    private String fromPatID;

    private String sessionID;

    private V2TIMMessage imMessage;

    private String serviceCount;// 服务次数
    private String consultCount;// 咨询次数


    private static VisitInfomation ourInstance = new VisitInfomation();

    public static VisitInfomation getInstance() {
        return ourInstance;
    }

    public String getBespeakId() {
        return bespeakId;
    }

    public void setBespeakId(String bespeakId) {
        this.bespeakId = bespeakId;
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

    public String getDocHead() {
        return docHead;
    }

    public void setDocHead(String docHead) {
        this.docHead = docHead;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getDocTitle() {
        return docTitle;
    }

    public void setDocTitle(String docTitle) {
        this.docTitle = docTitle;
    }

    public String getDocDept() {
        return docDept;
    }

    public void setDocDept(String docDept) {
        this.docDept = docDept;
    }

    public String getDocHos() {
        return docHos;
    }

    public void setDocHos(String docHos) {
        this.docHos = docHos;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public String getCounselingHead() {
        return counselingHead;
    }

    public void setCounselingHead(String counselingHead) {
        this.counselingHead = counselingHead;
    }

    public String getCounselingName() {
        return counselingName;
    }

    public void setCounselingName(String counselingName) {
        this.counselingName = counselingName;
    }

    public String getCounselingSex() {
        return counselingSex;
    }

    public void setCounselingSex(String counselingSex) {
        this.counselingSex = counselingSex;
    }

    public String getCounselingAge() {
        return counselingAge;
    }

    public void setCounselingAge(String counselingAge) {
        this.counselingAge = counselingAge;
    }

    public String getToDocID() {
        return toDocID;
    }

    public void setToDocID(String toDocID) {
        this.toDocID = toDocID;
    }

    public String getFromPatID() {
        return fromPatID;
    }

    public void setFromPatID(String fromPatID) {
        this.fromPatID = fromPatID;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDeptWorkstationId() {
        return deptWorkstationId;
    }

    public void setDeptWorkstationId(String deptWorkstationId) {
        this.deptWorkstationId = deptWorkstationId;
    }

    public V2TIMMessage getImMessage() {
        return imMessage;
    }

    public void setImMessage(V2TIMMessage imMessage) {
        this.imMessage = imMessage;
    }

    public String getServiceCount() {
        return serviceCount;
    }

    public void setServiceCount(String serviceCount) {
        this.serviceCount = serviceCount;
    }

    public String getConsultCount() {
        return consultCount;
    }

    public void setConsultCount(String consultCount) {
        this.consultCount = consultCount;
    }
}
