package com.company.wanbei.app.moduleHome;


import com.tencent.qcloud.tuikit.tuichat.fromApp.base.BaseViewInterface;

/**
 * Created by YC on 2017/8/11.
 */

public class HomeInterface {

    public interface HomeInterfaceView extends BaseViewInterface {
        void download();
    }

    public interface HomeInterfacePresenter{
        void getVersion();
        void login(String version);
    }
}
