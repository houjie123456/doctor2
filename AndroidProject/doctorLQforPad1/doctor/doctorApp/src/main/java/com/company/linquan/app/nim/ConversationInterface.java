package com.company.linquan.app.nim;


import com.company.linquan.app.base.BaseViewInterface;
import com.company.linquan.app.http.JSONFirstAsk;

/**
 * Created by YC on 2018/8/4.
 */

public class ConversationInterface {

    public interface ConversationViewInterface extends BaseViewInterface{

    }

    public interface ConversationPresenterInterface{

    }

    public interface FirstAskInterface extends BaseViewInterface{
        void reloadInfo(JSONFirstAsk bean);
    }

    public interface FirstAskPresenterInterface{
        void getFirstAskInfo(String id);
    }

}
