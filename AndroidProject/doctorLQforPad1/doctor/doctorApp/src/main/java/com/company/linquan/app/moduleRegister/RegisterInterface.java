package com.company.linquan.app.moduleRegister;

import com.company.linquan.app.base.BaseViewInterface;

/**
 * Created by YC on 2018/1/9.
 */

public class RegisterInterface {
    public interface RegisterViewInterface extends BaseViewInterface{
        void startTimer();
    }

    public interface RegisterPresenterInterface{
        void getCode(String mobile);
        void login(String mobile, String code, String openID
                , String nickName, String sex, String picSignID,String versionNo);
//        void login(String mobile, String code, String openID
//                , String nickName, String sex, String picSignID);
    }
}
