package com.tencent.qcloud.tuikit.tuichat.bean.message;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.tencent.imsdk.v2.V2TIMMessage;
import com.tencent.qcloud.tuikit.tuichat.R;
import com.tencent.qcloud.tuikit.tuichat.TUIChatService;
import com.tencent.qcloud.tuikit.tuichat.bean.message.reply.CustomLinkReplyQuoteBean;
import com.tencent.qcloud.tuikit.tuichat.bean.message.reply.TUIReplyQuoteBean;

import java.util.HashMap;

/**
 * 自定义超链接消息
 */
public class CustomFirstAskMessageBean extends TUIMessageBean {

    private String visitName;
    private String visitAge;
    private String visitSex;
    private String visitDesc;
    private String inquiryId;
    private String showTxt = TUIChatService.getAppContext().getString(R.string.first_ask_extra);

    @Override
    public String onGetDisplayString() {
        return showTxt;
    }

    @Override
    public void onProcessMessage(V2TIMMessage v2TIMMessage) {
        // 自定义消息view的实现，这里仅仅展示文本信息，并且实现超链接跳转
        String data = new String(v2TIMMessage.getCustomElem().getData());
        try {
            HashMap map = new Gson().fromJson(data, HashMap.class);
            if (map != null) {
                visitName = (String) map.get("visitname");
                visitAge = new Double((double)map.get("visitage")).intValue()+"";
                visitSex = (String) map.get("visitsex");
                visitDesc = (String) map.get("value");
                inquiryId = (String) map.get("inquiryid");
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        setExtra("first_ask");
    }

    public String getVisitName() {
        return visitName;
    }

    public void setVisitName(String visitName) {
        this.visitName = visitName;
    }

    public String getVisitAge() {
        return visitAge;
    }

    public void setVisitAge(String visitAge) {
        this.visitAge = visitAge;
    }

    public String getVisitSex() {
        return visitSex;
    }

    public void setVisitSex(String visitSex) {
        this.visitSex = visitSex;
    }

    public String getVisitDesc() {
        return visitDesc;
    }

    public void setVisitDesc(String visitDesc) {
        this.visitDesc = visitDesc;
    }

    public String getInquiryId() {
        return inquiryId;
    }

    public void setInquiryId(String inquiryId) {
        this.inquiryId = inquiryId;
    }

    public String getShowTxt() {
        return showTxt;
    }

    public void setShowTxt(String showTxt) {
        this.showTxt = showTxt;
    }

    @Override
    public Class<? extends TUIReplyQuoteBean> getReplyQuoteBeanClass() {
        return CustomLinkReplyQuoteBean.class;
    }
}
