package com.company.wanbei.app.util.recycler;

//import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.tencent.qcloud.tuikit.tuichat.fromApp.util.ToolUtil;

/**
 * Created by YC on 2018/6/2.
 */

public class CardAdapterHelper {
    private int mPagePadding = 15;
    private int mShowLeftCardWidth = 15;
    private int with;

    public void onCreateViewHolder(ViewGroup parent, View itemView) {
        RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) itemView.getLayoutParams();
        lp.width = parent.getWidth() - ToolUtil.dp2px(itemView.getContext(), 2 * (mPagePadding + mShowLeftCardWidth));
        itemView.setLayoutParams(lp);
        with = lp.width;
    }

    public void onBindViewHolder(View itemView, final int position, int itemCount) {
        int padding = ToolUtil.dp2px(itemView.getContext(), mPagePadding);
        itemView.setPadding(padding, 0, padding, 0);
        int leftMarin = position == 0 ? padding + ToolUtil.dp2px(itemView.getContext(), mShowLeftCardWidth) : 0;
        int rightMarin = position == itemCount - 1 ? padding + ToolUtil.dp2px(itemView.getContext(), mShowLeftCardWidth) : 0;
        setViewMargin(itemView, leftMarin, 0, rightMarin, 0);

    }

    private void setViewMargin(View view, int left, int top, int right, int bottom) {
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        if (lp.leftMargin != left || lp.topMargin != top || lp.rightMargin != right || lp.bottomMargin != bottom) {
            lp.setMargins(left, top, right, bottom);
            view.setLayoutParams(lp);
        }
    }

    public void setPagePadding(int pagePadding) {
        mPagePadding = pagePadding;
    }

    public void setShowLeftCardWidth(int showLeftCardWidth) {
        mShowLeftCardWidth = showLeftCardWidth;
    }

    public int getWith(){
        return with;
    }
}
