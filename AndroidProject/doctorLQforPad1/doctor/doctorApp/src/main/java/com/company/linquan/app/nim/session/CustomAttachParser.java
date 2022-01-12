package com.company.linquan.app.nim.session;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.netease.nimlib.sdk.msg.attachment.MsgAttachment;
import com.netease.nimlib.sdk.msg.attachment.MsgAttachmentParser;

//import org.json.JSONObject;


public class CustomAttachParser implements MsgAttachmentParser {
    private static final String KEY_TYPE = "type";
    private static final String KEY_DATA = "data";
    @Override
    public MsgAttachment parse(String json) {
        CustomAttachment attachment = null;

        try {
            JSONObject object = JSON.parseObject(json);
//            if(object.getString(KEY_TYPE).equals("患者健康档案")){
//                JSONObject data = object.getJSONObject(KEY_DATA);
//                attachment = new PatCardAttachment();
//                if (attachment != null) {
//                    attachment.fromJson(data);
//                }
//            }else {
                int type = object.getInteger(KEY_TYPE);
                JSONObject data = object.getJSONObject(KEY_DATA);
                switch (type) {
                    case CustomAttachmentType.PatCard:
                        attachment = new PatCardAttachment();
                    default:
//                    attachment = new DefaultCustomAttachment();
                        break;
                }
                if (attachment != null) {
                    attachment.fromJson(data);
                }
//            }


        } catch (Exception e) {

        }
        return attachment;
    }
    public static String packData(int type, JSONObject data) {
        JSONObject object = new JSONObject();
        object.put(KEY_TYPE, type);
        if (data != null) {
            object.put(KEY_DATA, data);
        }
        return object.toJSONString();
    }
}
