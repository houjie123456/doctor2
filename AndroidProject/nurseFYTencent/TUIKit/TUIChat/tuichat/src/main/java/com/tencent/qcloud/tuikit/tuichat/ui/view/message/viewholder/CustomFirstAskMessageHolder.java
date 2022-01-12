package com.tencent.qcloud.tuikit.tuichat.ui.view.message.viewholder;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.tencent.qcloud.tuikit.tuichat.R;
import com.tencent.qcloud.tuikit.tuichat.TUIChatService;
import com.tencent.qcloud.tuikit.tuichat.bean.message.CustomFirstAskMessageBean;
import com.tencent.qcloud.tuikit.tuichat.bean.message.CustomLinkMessageBean;
import com.tencent.qcloud.tuikit.tuichat.bean.message.TUIMessageBean;
import com.tencent.qcloud.tuikit.tuichat.ui.page.FirstAskActivity;


public class CustomFirstAskMessageHolder extends MessageContentHolder {

    public CustomFirstAskMessageHolder(View itemView) {
        super(itemView);
    }

    public static final String TAG = CustomFirstAskMessageHolder.class.getSimpleName();
    protected TextView patNameTV;
    protected TextView patSexTV;
    protected TextView patAgeTV;
    protected TextView patContentTV;

    private String visitName;
    private String visitAge;
    private String visitSex;
    private String visitDesc;
    private String inquiryId;


    @Override
    public int getVariableLayout() {
        return R.layout.test_custom_message_layout1;
    }

    @Override
    public void layoutVariableViews(TUIMessageBean msg, int position) {
        // 自定义消息view的实现，这里仅仅展示文本信息，并且实现超链接跳转
        patNameTV=itemView.findViewById(R.id.pat_name);
        patSexTV=itemView.findViewById(R.id.pat_sex);
        patAgeTV=itemView.findViewById(R.id.pat_age);
        patContentTV=itemView.findViewById(R.id.pat_content);
        if (msg instanceof CustomFirstAskMessageBean) {
            visitName = ((CustomFirstAskMessageBean) msg).getVisitName();
            visitAge = ((CustomFirstAskMessageBean) msg).getVisitAge();
            visitSex = ((CustomFirstAskMessageBean) msg).getVisitSex();
            visitDesc = ((CustomFirstAskMessageBean) msg).getVisitDesc();
            inquiryId= ((CustomFirstAskMessageBean) msg).getInquiryId();
        }
        msgContentFrame.setClickable(true);
        patNameTV.setText(visitName);
        patSexTV.setText(visitSex);
        patAgeTV.setText(visitAge+"岁");
        patContentTV.setText(visitDesc);
//        if(msg.isSelf()){
//            setRightMsg();
//        }else {
//            setLeftMsg();
//        }


        msgContentFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TUIChatService.getAppContext(), FirstAskActivity.class);
                intent.putExtra("inquiryId",inquiryId);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                TUIChatService.getAppContext().startActivity(intent);
            }
        });

    }

    public void setLeftMsg(){
        msgContentFrame.setBackgroundResource(R.drawable.nim_message_item_left_selector);
        patNameTV.setTextColor(Color.BLACK);
        patSexTV.setTextColor(Color.BLACK);
        patAgeTV.setTextColor(Color.BLACK);
        patContentTV.setTextColor(Color.BLACK);
    }
    public void setRightMsg(){
        msgContentFrame.setBackgroundResource(R.drawable.nim_message_item_right_selector);
        patNameTV.setTextColor(Color.WHITE);
        patSexTV.setTextColor(Color.WHITE);
        patAgeTV.setTextColor(Color.WHITE);
        patContentTV.setTextColor(Color.WHITE);
    }
}
