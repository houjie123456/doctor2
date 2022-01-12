package com.company.linquan.app.base;

import android.content.Context;

/**
 * Created by YC on 2017/8/23.
 */

public interface BaseViewInterface {
    void showDialog();
    void dismissDialog();
    void showToast(String txt);
    Context getContext();
    void finishActivity();
}
