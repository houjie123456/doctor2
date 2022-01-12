package com.company.wanbei.app.moduleCenter;

import com.tencent.qcloud.tuikit.tuichat.fromApp.base.BaseViewInterface;
import com.company.wanbei.app.bean.MeetingBean;
import com.company.wanbei.app.bean.TransactionBean;
import com.company.wanbei.app.bean.UserInfoBean;
import com.company.wanbei.app.http.JSONBean;
import com.company.wanbei.app.http.JSONMe;

import java.util.ArrayList;

/**
 * Created by YC on 2018/1/9.
 */

public class UserCenterInterface {
    public interface MyMoneyInterface extends BaseViewInterface{
        void reloadList(ArrayList<TransactionBean> list,String amount);
    }

    public interface ViewInterface extends BaseViewInterface {
        void reloadView(UserInfoBean bean);
        void reloadMyMoney(JSONBean bean);
    }

    public interface MeInterface extends BaseViewInterface {
        void reloadView(JSONMe bean);
    }

    public interface MyMeetingInterface extends BaseViewInterface {
        void refreshList(ArrayList<MeetingBean> list);
    }

    public interface FeedbackInterface extends BaseViewInterface {

    }

    public interface AccountInterface extends BaseViewInterface {
        void initPath(String url);
        void reloadView(String bean);
    }

    public interface SignInterface extends BaseViewInterface {
//        void reloadView(String bean);
    }

    public interface BalanceInterface extends BaseViewInterface {
    }


    public interface PresenterInterface{
        void getPersonInfo();
        void getMyMoney();
    }

    public interface FeedbackPresenterInterface{
        void feedback(String title, String content);
    }

    public interface MePresenterInterface{
        void getPerson();
    }

    public interface MyMeetingPresenterInterface{
        void getMeeting(int page, int type);
    }

    public interface AccountPresenterInterface{
        void updatePerson(String name, String mobile, String photoUrl);
        void upload(String path);
        void checkVersion();
    }

    public interface SignPresenterInterface{
        void stopSignAuto();
    }

    public interface MyMoneyPresenterInterface{
        void getList(String profitDate,String state,String profitType,String page);
    }
}
