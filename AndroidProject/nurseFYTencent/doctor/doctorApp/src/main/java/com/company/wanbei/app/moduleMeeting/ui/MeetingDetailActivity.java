package com.company.wanbei.app.moduleMeeting.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.company.wanbei.app.R;
import com.company.wanbei.app.base.BaseActivity;
import com.company.wanbei.app.bean.MeetingBean;
import com.company.wanbei.app.moduleMeeting.MeetingInterface;
import com.company.wanbei.app.moduleMeeting.MeetingPresenter;
import com.company.wanbei.app.util.ExitApp;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.MyDialog;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.MyToast;
import com.tencent.qcloud.tuikit.tuichat.fromApp.view.MyTextView;

public class MeetingDetailActivity extends BaseActivity implements MeetingInterface.MeetingDetailInterface, View.OnClickListener {

    private Dialog myDialog;
    private MeetingPresenter presenter;
    private ImageView meetingIV;
    private TextView meetingTitleTV,meetingTimeTV,meetingPlaceTV,meetingSpeakerTV,meetingPeopleTV,meetingContentTV,meetingAskTV;
    private LinearLayout meetingPeopleBTN,meetingContentBTN,meetingAskBtn,meetingPeopleClickBTN,meetingContentClickBTN, meetingAskClickBtn;
    private String meetingID;
    private int sw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExitApp.getInstance().addActivity(this);		// add Activity 方便退出
//        requestWindowFeature(Window.FEATURE_NO_TITLE); // 开启自定义标题栏
        setContentView(R.layout.activity_meeting_detail);
        initHead();
        initView();
        getData();
    }

    private void initHead() {
        RelativeLayout head = (RelativeLayout)findViewById(R.id.head_layout);
        TextView titleTV = (MyTextView)head.findViewById(R.id.head_top_title);
        titleTV.setText("会议详情");

        ImageView rightIV = (ImageView)head.findViewById(R.id.head_top_image);
        rightIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView(){
        sw = getContext().getResources().getDisplayMetrics().widthPixels;
        presenter = new MeetingPresenter(this);
        meetingIV = findViewById(R.id.meeting_image);
        meetingTitleTV = findViewById(R.id.meeting_title);
        meetingTimeTV = findViewById(R.id.meeting_time);
        meetingPlaceTV = findViewById(R.id.meeting_place);
        meetingSpeakerTV = findViewById(R.id.meeting_speaker);

        meetingPeopleBTN = findViewById(R.id.people_btn1);
        meetingPeopleBTN.setVisibility(View.GONE);
        meetingPeopleBTN.setOnClickListener(this);
        meetingContentBTN = findViewById(R.id.content_btn1);
        meetingContentBTN.setVisibility(View.VISIBLE);
        meetingContentBTN.setOnClickListener(this);
        meetingAskBtn = findViewById(R.id.ask_btn1);
        meetingAskBtn.setVisibility(View.VISIBLE);
        meetingAskBtn.setOnClickListener(this);
        meetingPeopleClickBTN = findViewById(R.id.people_btn2);
        meetingPeopleClickBTN.setVisibility(View.VISIBLE);
        meetingPeopleClickBTN.setOnClickListener(this);
        meetingContentClickBTN = findViewById(R.id.content_btn2);
        meetingContentClickBTN.setVisibility(View.GONE);
        meetingContentClickBTN.setOnClickListener(this);
        meetingAskClickBtn = findViewById(R.id.ask_btn2);
        meetingAskClickBtn.setVisibility(View.GONE);
        meetingAskClickBtn.setOnClickListener(this);

        meetingPeopleTV = findViewById(R.id.people_txt);
        meetingPeopleTV.setVisibility(View.VISIBLE);
        meetingContentTV = findViewById(R.id.content_txt);
        meetingContentTV.setVisibility(View.GONE);
        meetingAskTV = findViewById(R.id.ask_txt);
        meetingAskTV.setVisibility(View.GONE);
    }



    public void getData(){
        meetingID = getIntent().getStringExtra("meetingID");
        presenter.getMeetingDetail(meetingID);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.people_btn1:
                meetingPeopleBTN.setVisibility(View.GONE);
                meetingContentBTN.setVisibility(View.VISIBLE);
                meetingAskBtn.setVisibility(View.VISIBLE);
                meetingPeopleClickBTN.setVisibility(View.VISIBLE);
                meetingContentClickBTN.setVisibility(View.GONE);
                meetingAskClickBtn.setVisibility(View.GONE);

                meetingPeopleTV.setVisibility(View.VISIBLE);
                meetingContentTV.setVisibility(View.GONE);
                meetingAskTV.setVisibility(View.GONE);
                break;
            case R.id.content_btn1:
                meetingPeopleBTN.setVisibility(View.VISIBLE);
                meetingContentBTN.setVisibility(View.GONE);
                meetingAskBtn.setVisibility(View.VISIBLE);
                meetingPeopleClickBTN.setVisibility(View.GONE);
                meetingContentClickBTN.setVisibility(View.VISIBLE);
                meetingAskClickBtn.setVisibility(View.GONE);

                meetingPeopleTV.setVisibility(View.GONE);
                meetingContentTV.setVisibility(View.VISIBLE);
                meetingAskTV.setVisibility(View.GONE);
                break;
            case R.id.ask_btn1:
                meetingPeopleBTN.setVisibility(View.VISIBLE);
                meetingContentBTN.setVisibility(View.VISIBLE);
                meetingAskBtn.setVisibility(View.GONE);
                meetingPeopleClickBTN.setVisibility(View.GONE);
                meetingContentClickBTN.setVisibility(View.GONE);
                meetingAskClickBtn.setVisibility(View.VISIBLE);

                meetingPeopleTV.setVisibility(View.GONE);
                meetingContentTV.setVisibility(View.GONE);
                meetingAskTV.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void reloadView(MeetingBean bean) {
//        int w2 = (94*sw)/720;
        Glide.with(this).load(bean.getMeetingCover1()).into(meetingIV);
        meetingTitleTV.setText(bean.getTitle());
        meetingTimeTV.setText(bean.getMeetingTime());
        meetingPlaceTV.setText(bean.getAddress());
        meetingSpeakerTV.setText(bean.getSpeaker());

        meetingPeopleTV.setText(bean.getParticipants());
        meetingContentTV.setText(bean.getContent());
        meetingAskTV.setText(bean.getRequirement());

    }

    @Override
    public void finishActivity() {
        Intent intent = new Intent();
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showDialog() {
        if (myDialog == null) myDialog = MyDialog.showLoadingDialog(this);
    }
    @Override
    public void dismissDialog() {
        if (myDialog != null) myDialog.dismiss();
    }

    @Override
    public void showToast(String txt) {
        MyToast.showToast(this,txt, Toast.LENGTH_SHORT);
    }
}
