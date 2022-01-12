 package com.company.wanbei.app.moduleSplash;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.company.wanbei.app.bean.UserInfoBean;
import com.tencent.qcloud.tuikit.tuichat.fromApp.config.C;
import com.company.wanbei.app.http.HttpApi;
import com.company.wanbei.app.http.JSONBean;
import com.company.wanbei.app.tim.VisitInfomation;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.ToolSharePerference;

import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by YC on 2018/1/9.
 */

public class SplashPresenter implements SplashInterface.SplashPresenterInterface{

    private SplashInterface.SplashViewInterface view;

    public SplashPresenter(SplashInterface.SplashViewInterface view){
        this.view = view;
    }

    @Override
    public void login(String versionNo) {
        view.showDialog();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("uniqueId", ToolSharePerference.getStringData(view.getContext(), C.fileconfig, C.UniqueID));
        jsonObject.put("versionNo", versionNo);
        RequestBody requestBody = HttpApi.packageParam(jsonObject);
        HttpApi.loginById(requestBody).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<JSONBean>(){
                    @Override
                    public void onCompleted() {
                        view.dismissDialog();
                        Log.i("onCompleted","onCompleted");
                    }
                    @Override
                    public void onError(Throwable e) {
                        view.dismissDialog();
                        Log.i("Throwable",e.toString());
                    }
                    @Override
                    public void onNext(JSONBean result) {
                        Log.i("onNext","onNext");
                        //填充UI
                        if ("0".equals(result.getCode())){
                            view.showToast(result.getMsgBox());
                            saveData(result);
                            view.enterHomeActivity();
                        }else {
                            view.showToast(result.getMsgBox());
                            clearData();
                            view.finishActivity();
                        }
                    }
        });
    }

    private void saveData(JSONBean result){
        ToolSharePerference.putStringData(view.getContext(), C.fileconfig, C.CLIENTID,result.getData().getClientId());
        ToolSharePerference.putStringData(view.getContext(), C.fileconfig, C.MOBILE,result.getData().getMobile());

        ToolSharePerference.putStringData(view.getContext(), C.fileconfig, C.USER_SIGN,result.getData().getUserSig());
        ToolSharePerference.putStringData(view.getContext(), C.fileconfig, C.UserID,result.getData().getPersonId());

        ToolSharePerference.putStringData(view.getContext(), C.fileconfig, C.DeptWorkstationID,result.getData().getDeptWorkstationId());

        String s = ToolSharePerference.getStringData(view.getContext(), C.fileconfig, C.DeptWorkstationID);
        VisitInfomation.getInstance().setDoctorId(result.getData().getPersonId());
        VisitInfomation.getInstance().setDeptWorkstationId(result.getData().getDeptWorkstationId());

        ToolSharePerference.putStringData(view.getContext(), C.fileconfig, C.UniqueID,result.getData().getUniqueId());
        ToolSharePerference.putStringData(view.getContext(), C.fileconfig, C.AUTH,result.getData().getCheckState());

        ToolSharePerference.putStringData(view.getContext(), C.fileconfig, C.ISSUBJECT,result.getData().getIsSubject());

        /**
         * TODO 保存用户信息
         * accountID-网易云账号
         * token-网易云token
         ***/
        UserInfoBean.getInstance().setAccountID(result.getData().getAccountId());
        UserInfoBean.getInstance().setToken(result.getData().getToken());
        UserInfoBean.getInstance().setIdType(result.getData().getIdType());

        String s1 = result.getData().getIdType();
        ToolSharePerference.putStringData(view.getContext(), C.fileconfig, C.IDTYPE,result.getData().getIdType());

        String s2 = ToolSharePerference.getStringData(view.getContext(), C.fileconfig, C.IDTYPE);
        VisitInfomation.getInstance().setIdType(result.getData().getIdType());
        VisitInfomation.getInstance().setAccountID(result.getData().getAccountId());
        VisitInfomation.getInstance().setToken(result.getData().getToken());

        VisitInfomation.getInstance().setDocHead(result.getData().getHeadUrl());
        VisitInfomation.getInstance().setDocName(result.getData().getMyName());
        VisitInfomation.getInstance().setDocTitle(result.getData().getAcademicTitleName());
        VisitInfomation.getInstance().setDocDept(result.getData().getDepartmentName());
        VisitInfomation.getInstance().setDocHos(result.getData().getHospitalName());
        VisitInfomation.getInstance().setServiceCount(result.getData().getServiceCount());
        VisitInfomation.getInstance().setConsultCount(result.getData().getConsultCount());

        VisitInfomation.getInstance().setFromPatID(result.getData().getPersonId());
    }

    private void clearData(){
        ToolSharePerference.putStringData(view.getContext(), C.fileconfig, C.USER_SIGN,"");
        ToolSharePerference.putStringData(view.getContext(), C.fileconfig, C.UserID,"");
        ToolSharePerference.putStringData(view.getContext(), C.fileconfig, C.UniqueID,"");
        ToolSharePerference.putStringData(view.getContext(), C.fileconfig, C.AUTH,"");
    }
}
