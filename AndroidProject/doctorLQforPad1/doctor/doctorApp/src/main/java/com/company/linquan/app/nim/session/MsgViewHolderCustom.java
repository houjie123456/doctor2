package com.company.linquan.app.nim.session;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.company.linquan.app.nim.activity.FirstAskActivity;
import com.netease.nim.uikit.R;
import com.netease.nim.uikit.business.session.viewholder.MsgViewHolderBase;
import com.netease.nim.uikit.common.ui.recyclerview.adapter.BaseMultiItemFetchLoadAdapter;
import com.netease.nim.uikit.common.util.sys.ScreenUtil;
import com.netease.nim.uikit.impl.NimUIKitImpl;
import com.netease.nim.uikit.visitinfo.VisitInfomation;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;

import org.json.JSONException;
import org.json.JSONObject;

import static com.company.linquan.app.config.C.VISIDS;
import static com.company.linquan.app.config.C.VISNAMES;

/**
 * Created by zhoujianghua on 2015/8/6.
 */
public class MsgViewHolderCustom extends MsgViewHolderBase {

    public MsgViewHolderCustom(BaseMultiItemFetchLoadAdapter adapter) {
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
        PatCardAttachment patCardAttachment=(PatCardAttachment)message.getAttachment();
        if(patCardAttachment.getPatid()!=null){
            VISIDS.add(patCardAttachment.getPatid());
            VISNAMES.add(patCardAttachment.getPatname());
        }
//        VISIDS.add(patCardAttachment.getPatid());
//        VISNAMES.add(patCardAttachment.getPatname());
//        VisitInfomation.getInstance().setVisitId(patCardAttachment.getPatid());
//        VisitInfomation.getInstance().setVisitName(patCardAttachment.getPatname());
        patnameTV.setText(patCardAttachment.getPatname());
        patsexTV.setText(patCardAttachment.getPatsex());
        patageTV.setText(patCardAttachment.getPatage());
        patcontentTV.setText(patCardAttachment.getContent());
        inquiryId=patCardAttachment.getInquiryid();

//        MoonUtil.identifyFaceExpression(NimUIKit.getContext(), layout, getDisplayText(), ImageSpan.ALIGN_BOTTOM);
//        layout.setMovementMethod(LinkMovementMethod.getInstance());
        layout.setOnLongClickListener(longClickListener);
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

    @Override
    protected void onItemClick() {
        Intent intent=new Intent(context, FirstAskActivity.class);
        intent.putExtra("inquiryId",inquiryId);
        context.startActivity(intent);

    }
}
