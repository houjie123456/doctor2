package com.company.wanbei.app.util.recycler;

//import android.support.annotation.NonNull;
//import androidx.appcompat.widget.ToolbarLinearSnapHelper;
//import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by YC on 2018/6/2.
 */

public class CardLinearSnapHelper extends LinearSnapHelper {
    public boolean mNoNeedToScroll = false;

    @Override
    public int[] calculateDistanceToFinalSnap(@NonNull RecyclerView.LayoutManager layoutManager, @NonNull View targetView) {
        if (mNoNeedToScroll) {
            return new int[]{0, 0};
        } else {
            return super.calculateDistanceToFinalSnap(layoutManager, targetView);
        }
    }
}
