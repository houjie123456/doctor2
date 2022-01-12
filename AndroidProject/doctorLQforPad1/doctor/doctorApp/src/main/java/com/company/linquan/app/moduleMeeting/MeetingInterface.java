package com.company.linquan.app.moduleMeeting;

import com.company.linquan.app.base.BaseViewInterface;
import com.company.linquan.app.bean.DiscussBean;
import com.company.linquan.app.bean.FriendsBean;
import com.company.linquan.app.bean.MeetingBean;

import java.util.ArrayList;

/**
 * Created by YC on 2018/1/9.
 */

public class MeetingInterface {

    public interface ViewInterface extends BaseViewInterface{
        void refreshList(ArrayList<MeetingBean> list);
        void initUrl(int position,String url);
    }

    public interface CreateMeetingInterface extends BaseViewInterface{
        void initPath(String url);
    }

    public interface MeetingDetailInterface extends BaseViewInterface{
        void reloadView(MeetingBean bean,String url);
        void setListView(ArrayList<DiscussBean> bean);
        void reloadListView();
        void initUrl(String url,String pushUrl);
    }

    public interface MapInterface extends BaseViewInterface{
    }

    public interface SelectPersonInterface extends BaseViewInterface{
        void refreshList(ArrayList<FriendsBean> list);
    }


    public interface MeetingDetailWebInterface extends BaseViewInterface{
        void initUrl(String url,String pushUrl);
    }

    public interface MeetingWebInterface extends BaseViewInterface{
        void initUrl(String url);
    }

    public interface MeetingDetailFragmentInterface extends BaseViewInterface{
        void reloadView(MeetingBean bean);
        void setListView(ArrayList<DiscussBean> bean);
        void reloadListView();
    }


    public interface PresenterInterface{
        void getMeetingList(int page,int type);
        void getVideoUrl(String personId,int position);
    }

    public interface MeetingDetailPresenterInterface{
        void getMeetingDetail(String id);
        void getDiscuss(String id,int page);
        void sendDiscuss(String id,String txt,String score);
        void signIn(String id,String lon,String lat);
        void getVideoUrl(String personId);
    }

    public interface CreatePresenterInterface{
        void createMeeting(String title, String startTime
                , String endTime,String address, String remark,String vedioCover, String inviterIDStr
                ,String longitude, String latitude);
        void upload(String path);
    }

    public interface SelectPersonPresenterInterface{
        void getPersonList(int page,int type);
    }

    public interface MeetingDetailWebPresenterInterface{
        void getVideoUrl(String personId);
    }

    public interface MeetingWebPresenterInterface{
        void getDetailUrl(String personId);
    }

    public interface MeetingFragmentPresenterInterface{
        void getMeetingDetail(String id);
        void getDiscuss(String id,int page);
        void sendDiscuss(String id,String txt,String score);
    }

    public interface MapPresenterInterface{
        void addAddress(String address,String room,String lat,String lon);
    }
}
