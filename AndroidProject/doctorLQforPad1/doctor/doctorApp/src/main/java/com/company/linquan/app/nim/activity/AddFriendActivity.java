package com.company.linquan.app.nim.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.company.linquan.app.R;
import com.company.linquan.app.base.DemoCache;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.api.model.SimpleCallback;
import com.netease.nim.uikit.api.wrapper.NimToolBarOptions;
import com.netease.nim.uikit.common.ToastHelper;
import com.netease.nim.uikit.common.activity.ToolBarOptions;
import com.netease.nim.uikit.common.ui.dialog.DialogMaker;
import com.netease.nim.uikit.common.ui.dialog.EasyAlertDialogHelper;
import com.netease.nim.uikit.common.ui.widget.ClearableEditTextWithIcon;
import com.netease.nimlib.sdk.ResponseCode;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;

import com.netease.nim.uikit.common.activity.UI;

public class AddFriendActivity extends UI {
    private ClearableEditTextWithIcon searchEdit;

    public static final void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, AddFriendActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_friend_activity);

        ToolBarOptions options = new NimToolBarOptions();
        options.titleId = R.string.add_buddy;
        setToolBar(R.id.toolbar, options);

        findViews();
        initActionbar();
    }

    private void findViews() {
        searchEdit = findView(R.id.search_friend_edit);
        searchEdit.setDeleteImage(R.drawable.nim_grey_delete_icon);
        RelativeLayout head = (RelativeLayout) findView(R.id.layout_head);
        head.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent));
        TextView title = (TextView) head.findViewById(R.id.head_top_title);
        title.setText("????????????");
        ImageView rightIV = (ImageView)head.findViewById(R.id.head_top_image);
        rightIV.setVisibility(View.GONE);
    }

    private void initActionbar() {
        TextView toolbarView = findView(R.id.action_bar_right_clickable_textview);
        toolbarView.setText(R.string.search);
        toolbarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(searchEdit.getText().toString())) {
                    ToastHelper.showToast(AddFriendActivity.this, R.string.not_allow_empty);
                } else if (searchEdit.getText().toString().equals(DemoCache.getAccount())) {
                    ToastHelper.showToast(AddFriendActivity.this, R.string.add_friend_self_tip);
                } else {
                    query();
                }
            }
        });
    }

    private void query() {
        DialogMaker.showProgressDialog(this, null, false);
        final String account = searchEdit.getText().toString().toLowerCase();
        NimUIKit.getUserInfoProvider().getUserInfoAsync(account, new SimpleCallback<NimUserInfo>() {
            @Override
            public void onResult(boolean success, NimUserInfo result, int code) {
                DialogMaker.dismissProgressDialog();

                if (success) {
                    if (result == null) {
                        EasyAlertDialogHelper.showOneButtonDiolag(AddFriendActivity.this, R.string.user_not_exsit,
                                R.string.user_tips, R.string.ok, false, null);
                    } else {

                        UserProfileActivity.start(AddFriendActivity.this, account);
                    }
                } else if (code == 408) {
                    ToastHelper.showToast(AddFriendActivity.this, R.string.network_is_not_available);
                } else if (code == ResponseCode.RES_EXCEPTION) {
                    ToastHelper.showToast(AddFriendActivity.this, "on exception");
                } else {
                    ToastHelper.showToast(AddFriendActivity.this, "on failed:" + code);
                }
            }
        });
    }
}
