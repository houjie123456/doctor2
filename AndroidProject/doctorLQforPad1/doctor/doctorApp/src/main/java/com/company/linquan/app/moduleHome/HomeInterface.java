package com.company.linquan.app.moduleHome;


import android.content.Context;

import com.company.linquan.app.base.BaseViewInterface;

/**
 * Created by YC on 2017/8/11.
 */

public class HomeInterface {

    public interface HomeInterfaceView extends BaseViewInterface {
        void download();
    }

    public interface HomeInterfacePresenter{
        void getVersion();
    }
}
