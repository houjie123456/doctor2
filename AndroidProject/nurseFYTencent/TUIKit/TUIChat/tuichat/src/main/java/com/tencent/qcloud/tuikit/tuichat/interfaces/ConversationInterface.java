package com.tencent.qcloud.tuikit.tuichat.interfaces;



import com.tencent.qcloud.tuikit.tuichat.fromApp.base.BaseViewInterface;
import com.tencent.qcloud.tuikit.tuichat.fromApp.bean.DiseaseBean;
import com.tencent.qcloud.tuikit.tuichat.fromApp.http.JSONFirstAsk;

import java.util.ArrayList;

/**
 * Created by YC on 2018/8/4.
 */

public class ConversationInterface {

    public interface ConversationViewInterface extends BaseViewInterface {

    }

    public interface ConversationPresenterInterface{

    }

    public interface FirstAskInterface extends BaseViewInterface {
        void reloadInfo(JSONFirstAsk bean);
        void gotoRecipe(ArrayList<DiseaseBean> list, String isFirstVisit);
    }

    public interface FirstAskPresenterInterface{
        void getFirstAskInfo(String id);
    }

}
