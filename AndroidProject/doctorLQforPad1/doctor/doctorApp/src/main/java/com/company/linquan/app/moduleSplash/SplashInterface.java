package com.company.linquan.app.moduleSplash;

import com.company.linquan.app.base.BaseViewInterface;

/**
 * Created by YC on 2018/1/9.
 */

public class SplashInterface {

    public interface SplashViewInterface extends BaseViewInterface {
        void enterHomeActivity();
    }

    public interface SplashPresenterInterface{
//        void login();
        void login(String versionNo);
    }
}
