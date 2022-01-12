package com.company.wanbei.app.moduleMeeting.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.company.wanbei.app.R;
import com.company.wanbei.app.base.BaseActivity;
import com.company.wanbei.app.bean.ArticleBean;
import com.company.wanbei.app.moduleMeeting.MeetingInterface;
import com.company.wanbei.app.moduleMeeting.MeetingPresenter;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.Base64;
import com.company.wanbei.app.util.ExitApp;
import com.company.wanbei.app.util.HtmlImageGetter;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.MyDialog;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.MyToast;
import com.tencent.qcloud.tuikit.tuichat.fromApp.view.MyTextView;

public class ArticleDetailActivity extends BaseActivity implements MeetingInterface.ArticleDetailInterface{

    private Dialog myDialog;
    private MeetingPresenter presenter;
    private ImageView articleIV;
    private TextView articleTitleTV,articleTimeTV,articleSubTitleTV,articleContentTV;
    private String writingID;
    private int sw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExitApp.getInstance().addActivity(this);		// add Activity 方便退出
//        requestWindowFeature(Window.FEATURE_NO_TITLE); // 开启自定义标题栏
        setContentView(R.layout.activity_article_detail);
        initHead();
        initView();
        getData();
    }

    private void initHead() {
        RelativeLayout head = (RelativeLayout)findViewById(R.id.head_layout);
        TextView titleTV = (MyTextView)head.findViewById(R.id.head_top_title);
        titleTV.setText("文章详情");

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

        articleTitleTV = findViewById(R.id.article_title);
        articleSubTitleTV = findViewById(R.id.article_subtitle);
        articleTimeTV = findViewById(R.id.article_time);
        articleContentTV = findViewById(R.id.article_content);
    }


    public void getData(){
        writingID = getIntent().getStringExtra("writingID");
        presenter.getWritingsDetail(writingID);
    }




    @Override
    public void reloadView(ArticleBean bean) {
//        int w2 = (94*sw)/720;
//        Glide.with(this).load(bean.getMeetingCover1()).into(meetingIV);

        articleTitleTV.setText(bean.getTitle());
        String subTitle = new String(Base64.decode(bean.getSubtitle()));
        articleSubTitleTV.setText(subTitle);
        articleTimeTV.setText("发布时间："+bean.getReleaseTime());
        String content = new String(Base64.decode(bean.getThisContent()));
        //默认图片，无图片或没加载完显示此图片
        Drawable defaultDrawable = getResources().getDrawable(R.drawable.img_no_img);
        int screenWidth = getWindowManager().getDefaultDisplay().getWidth(); // 屏幕宽（像素，如：480px）
        int screenHeight = getWindowManager().getDefaultDisplay().getHeight(); // 屏幕高（像素，
        Spanned htmlContent = Html.fromHtml(content, new  HtmlImageGetter(articleContentTV, "/esun_msg", defaultDrawable,screenWidth,screenHeight), null);
        articleContentTV.setText(htmlContent);
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
