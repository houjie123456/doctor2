package com.company.linquan.app.moduleLogin;

import com.company.linquan.app.base.BaseViewInterface;

/**
 * Created by YC on 2018/1/9.
 */

public class LoginInterface {
    public interface LoginViewInterface extends BaseViewInterface{
        void startTimer();
    }

    public interface LoginPresenterInterface{
        void getCode(String mobile);
        void login(String mobile, String code, String openID
                , String nickName, String sex,String versionNo, String picSignID);
//        void login(String mobile, String code, String openID
//                , String nickName, String sex, String picSignID);
    }
}
