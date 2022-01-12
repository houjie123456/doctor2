package com.company.linquan.app.moduleCenter;

import com.company.linquan.app.base.BaseViewInterface;
import com.company.linquan.app.bean.AuthStatusBean;
import com.company.linquan.app.bean.MeetingBean;
import com.company.linquan.app.http.JSONMe;

import java.util.ArrayList;

/**
 * Created by YC on 2018/1/9.
 */

public class UserCenterInterface {
    public interface ViewInterface extends BaseViewInterface{
        void reloadView(AuthStatusBean bean);
    }

    public interface MeInterface extends BaseViewInterface{
        void reloadView(JSONMe bean);
    }

    public interface MyMeetingInterface extends BaseViewInterface{
        void refreshList(ArrayList<MeetingBean> list);
    }

    public interface FeedbackInterface extends BaseViewInterface{

    }

    public interface AccountInterface extends BaseViewInterface{
        void initPath(String url);
        void reloadView(String bean);
    }

    public interface BalanceInterface extends BaseViewInterface{
    }


    public interface PresenterInterface{
        void getPersonInfo();
    }

    public interface FeedbackPresenterInterface{
        void feedback(String title,String content);
    }

    public interface MePresenterInterface{
        void getPerson();
    }

    public interface MyMeetingPresenterInterface{
        void getMeeting(int page, int type);
    }

    public interface AccountPresenterInterface{
        void updatePerson(String name,String mobile, String photoUrl);
        void upload(String path);
        void checkVersion();
    }
}
