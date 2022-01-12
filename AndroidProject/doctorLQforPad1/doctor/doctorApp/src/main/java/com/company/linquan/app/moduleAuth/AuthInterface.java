
package com.company.linquan.app.moduleAuth;

import com.company.linquan.app.base.BaseViewInterface;
import com.company.linquan.app.bean.SelectDataBean;
import com.company.linquan.app.http.JSONPersonAll;

import java.util.ArrayList;

/**
 * Created by YC on 2018/1/9.
 */

public class AuthInterface {
    public interface ViewInterface extends BaseViewInterface{
        void initPath(String path,String urlId,int type);
        void reloadView(JSONPersonAll bean);
    }

    public interface SelectInterface extends BaseViewInterface{
        void setListData(ArrayList<SelectDataBean> list);
    }

    public interface PresenterInterface{
        void auth(String name,String idCardNo,String sex,String careerType,String academicTitle,String hospitalID
                ,String departmentID,String picTypeList,String picSignIDList,String photo,String personRemark
                ,String beGoodInfo,String personHonor,String serviceIdList,String years,String specialty);
        void upLoad(String path,int type);
        void getInfo();
    }

    public interface SelectPresenterInterface{
        void getHospital(String name);
        void getJob();
        void getSex();
        void getPosition();
        void getDepartment();
        void getTime();
        void getEat();
        void getSettingPictureTime();
        void getSettingPictureNum();
        void getVisitType();
    }
}
