package com.company.linquan.app.nim.session;



import com.alibaba.fastjson.JSONObject;


public class PatCardAttachment extends CustomAttachment {
    private final String PAT_ID = "visitid";
    private final String PAT_NAME = "visitname";
    private final String PAT_SEX = "visitsex";
    private final String PAT_AGE = "visitage";
    private final String PAT_CONTENT = "value";
    private final String PAT_INQUIRYID = "inquiryid";

    private String patid;
    private String patname;
    private String patsex;
    private String patage;
    private String content;
    private String inquiryid;

    public PatCardAttachment() {
        super(CustomAttachmentType.PatCard);
    }
    @Override
    protected void parseData(JSONObject data) {
        this.patid = data.getString(PAT_ID);
        this.patname = data.getString(PAT_NAME);
        this.patsex = data.getString(PAT_SEX);
        this.patage = data.getString(PAT_AGE);
        this.content = data.getString(PAT_CONTENT);
        this.inquiryid = data.getString(PAT_INQUIRYID);
    }
    @Override
    protected JSONObject packData() {
        JSONObject data = new JSONObject();
        data.put(PAT_ID, patid);
        data.put(PAT_NAME, patname);
        data.put(PAT_SEX, patsex);
        data.put(PAT_AGE, patage);
        data.put(PAT_CONTENT, content);
        data.put(PAT_INQUIRYID, inquiryid);
        return data;
    }

    public String getPatid() {
        return patid;
    }

    public String getPatname() {
        return patname;
    }

    public String getPatsex() {
        return patsex;
    }

    public String getPatage() {
        return patage;
    }

    public String getContent() {
        return content;
    }

    public String getInquiryid() {
        return inquiryid;
    }
}
