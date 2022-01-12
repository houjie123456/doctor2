package com.netease.nim.uikit.business.session.viewholder;

import android.graphics.Color;
import android.provider.Settings;
import android.text.method.LinkMovementMethod;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.netease.nim.uikit.R;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.business.robot.parser.elements.group.LinearLayout;
import com.netease.nim.uikit.business.session.emoji.MoonUtil;
import com.netease.nim.uikit.common.ui.recyclerview.adapter.BaseMultiItemFetchLoadAdapter;
import com.netease.nim.uikit.common.util.sys.ScreenUtil;
import com.netease.nim.uikit.impl.NimUIKitImpl;
import com.netease.nim.uikit.visitinfo.VisitInfomation;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.uinfo.model.UserInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import static com.netease.nim.uikit.api.NimUIKit.getContext;
import static com.netease.nim.uikit.api.NimUIKit.getOnlineStateContentProvider;
import static me.everything.android.ui.overscroll.OverScrollBounceEffectDecoratorBase.TAG;

/**
 * Created by zhoujianghua on 2015/8/6.
 */
public class MsgViewHolderUnknown2 extends MsgViewHolderBase {

    public MsgViewHolderUnknown2(BaseMultiItemFetchLoadAdapter adapter) {
        super(adapter);
    }

    protected RelativeLayout layout;
    protected TextView patnameTV;
    protected TextView patsexTV;
    protected TextView patageTV;
    protected TextView patcontentTV;

    private String visitname;
    private String visitage;
    private String visitsex;
    private String visitdesc;
    private String inquiryId;
    @Override
    protected int getContentResId() {
        return R.layout.nim_message_item_unknown;
    }

    @Override
    protected boolean isShowHeadImage() {
        if (message.getSessionType() == SessionTypeEnum.ChatRoom) {
            return false;
        }
        return true;
    }

    @Override
    protected void inflateContentView() {
        layout=findViewById(R.id.message_item_unsupport_container);
        patnameTV=findViewById(R.id.pat_name);
        patsexTV=findViewById(R.id.pat_sex);
        patageTV=findViewById(R.id.pat_age);
        patcontentTV=findViewById(R.id.pat_content);
    }

    @Override
    protected void bindContentView() {
        layoutDirection();
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick();
            }
        });

        patnameTV.setText(message.getAttachment().toJson(true));
        patsexTV.setText(new String(message.getContent().getBytes()));
        try {
            String str = null;
//            str = new String(elem.getData(), "UTF-8");
            JSONObject jsonObj = null;
            jsonObj = new JSONObject(message.getContent());

//            VisitInfomation.getInstance().setVisitId(jsonObj.getString("visitid"));
//            VisitInfomation.getInstance().setVisitId(jsonObj.getString("visitname"));

            visitname=jsonObj.getString("visitname");
            visitage=jsonObj.getString("visitage");
            visitsex=jsonObj.getString("visitsex");
            if(jsonObj.getString("visittxt").length()>8){
                visitdesc=jsonObj.getString("visittxt").substring(0,6)+"...";
            }else {
                visitdesc=jsonObj.getString("visittxt");
            }

            inquiryId=jsonObj.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        patnameTV.setText(visitname);
        patsexTV.setText(visitage);
        patageTV.setText(visitsex);
        patcontentTV.setText(visitdesc);
//        MoonUtil.identifyFaceExpression(NimUIKit.getContext(), layout, getDisplayText(), ImageSpan.ALIGN_BOTTOM);
//        layout.setMovementMethod(LinkMovementMethod.getInstance());
        layout.setOnLongClickListener(longClickListener);
    }
    protected String getDisplayText() {
        return message.getContent();
    }
    private void layoutDirection() {
        if (isReceivedMessage()) {
            layout.setBackgroundResource(NimUIKitImpl.getOptions().messageLeftBackground);
            patnameTV.setTextColor(Color.BLACK);
            patsexTV.setTextColor(Color.BLACK);
            patageTV.setTextColor(Color.BLACK);
            patcontentTV.setTextColor(Color.BLACK);
            layout.setPadding(ScreenUtil.dip2px(15), ScreenUtil.dip2px(8), ScreenUtil.dip2px(10), ScreenUtil.dip2px(8));
        } else {
            layout.setBackgroundResource(NimUIKitImpl.getOptions().messageRightBackground);
            patnameTV.setTextColor(Color.WHITE);
            patsexTV.setTextColor(Color.WHITE);
            patageTV.setTextColor(Color.WHITE);
            patcontentTV.setTextColor(Color.WHITE);
            layout.setPadding(ScreenUtil.dip2px(10), ScreenUtil.dip2px(8), ScreenUtil.dip2px(15), ScreenUtil.dip2px(8));
        }
    }
}
