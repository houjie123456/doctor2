package com.company.wanbei.app.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

//import androidx.annotation.Nullable;
//import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by YC on 2018/6/10.
 */

public class CommonRecyclerView extends RecyclerView {
    public CommonRecyclerView(Context context) {
        super(context);
    }

    public CommonRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CommonRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthSpec, expandSpec);
    }
}
