package com.company.wanbei.app.moduleHome;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.company.wanbei.app.bean.UserInfoBean;
import com.tencent.qcloud.tuikit.tuichat.fromApp.config.C;
import com.company.wanbei.app.http.HttpApi;
import com.company.wanbei.app.http.JSONBean;
import com.company.wanbei.app.tim.VisitInfomation;
import com.company.wanbei.app.util.DownloadApp;
import com.company.wanbei.app.util.InstallApk;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.ToolSharePerference;
import com.tencent.imsdk.v2.V2TIMCallback;
import com.tencent.qcloud.tuicore.TUILogin;
import com.tencent.qcloud.tuicore.util.ToastUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by YC on 2017/8/11.
 */

public class HomePresenter implements HomeInterface.HomeInterfacePresenter {

    private HomeInterface.HomeInterfaceView view;
    HomeActivity activity;

    public HomePresenter(HomeInterface.HomeInterfaceView view){
        this.view = view;
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    new InstallApk(activity)
                            .installApk(new File(Environment.getExternalStorageDirectory(), "your_app_name.apk"));
                    break;
            }

        }
    };
    @Override
    public void getVersion() {

        JSONObject jsonObject =new JSONObject();
        RequestBody requestBody = HttpApi.packageParam(jsonObject);
        HttpApi.getAppVersionInfo(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JSONBean>(){
                    @Override
                    public void onCompleted() {
                        Log.i("onCompleted","onCompleted");
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.i("Throwable",e.toString());
                    }
                    @Override
                    public void onNext(JSONBean result) {
                        Log.i("onNext","onNext");
                        //填充UI
                        if ("0".equals(result.getCode())){
                            try {
                                String appCode = result.getData().getVersionAndroid();
                                String appVersion = result.getData().getVersionNumberAndroid();
                                String appUrl = result.getData().getDownUrlAndroid();
                                String isMust = result.getData().getIsForceAndroid();
                                //保存到本地
                                ToolSharePerference.putStringData(view.getContext(), C.fileconfig, C.AppUrl,appUrl);
                                ToolSharePerference.putStringData(view.getContext(), C.fileconfig, C.AppVersion, appVersion);

                                PackageManager manager = view.getContext().getPackageManager();
                                PackageInfo info = manager.getPackageInfo(view.getContext().getPackageName(), 0);
                                //根据VersionCode判断是否需要更新
                                if(Integer.parseInt(appCode) > info.versionCode){
                                    DownloadApp app = new DownloadApp(view.getContext(),activity);
                                    if("0".equals(isMust)){
                                        app.DownloadDialog(appVersion, "", false);
                                    }else{
                                        app.DownloadDialog(appVersion, "", true);
//                                        DownloadFileHelper downloadFileHelper =new DownloadFileHelper(view.getContext(),handler);
//                                        downloadFileHelper.downFile(appurl);
                                    }
                                }else{
//                                    view.showToast("当前版本已经是最新版本");
                                }
                            }catch (Exception e){

                            }
                        }else {
                            view.showToast(result.getMsgBox());
                        }
                    }
                });
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
//                    view.showToast(result.getMsgBox());
                    saveData(result,result.getMobile());
                    login(result,result.getMobile());
                }else {
                    view.showToast(result.getMsgBox());
                    clearData();
                    view.finishActivity();
                }
            }
        });
    }
    /**
     * ***************************************** 登录 **************************************
     */
    private void login(JSONBean result, String mobile) {
        TUILogin.login(result.getData().getAccountId(), result.getData().getToken(), new V2TIMCallback() {
            @Override
            public void onError(final int code, final String desc) {
                ToastUtil.toastLongMessage("登录失败: " + code + "，" + desc);
            }

            @Override
            public void onSuccess() {
                saveData(result, mobile);
            }
        });
    }
    private void saveData(JSONBean result, String mobile){
        ToolSharePerference.putStringData(view.getContext(), C.fileconfig, C.CLIENTID,result.getData().getClientId());
        ToolSharePerference.putStringData(view.getContext(), C.fileconfig, C.MOBILE,mobile);
        ToolSharePerference.putStringData(view.getContext(), C.fileconfig, C.USER_SIGN,result.getData().getUserSig());
        ToolSharePerference.putStringData(view.getContext(), C.fileconfig, C.UserID,result.getData().getPersonId());
        ToolSharePerference.putStringData(view.getContext(), C.fileconfig, C.DeptWorkstationID,result.getData().getDeptWorkstationId());

        VisitInfomation.getInstance().setDoctorId(result.getData().getPersonId());
        VisitInfomation.getInstance().setDeptWorkstationId(result.getData().getDeptWorkstationId());

        VisitInfomation.getInstance().setDocHead(result.getData().getHeadUrl());
        VisitInfomation.getInstance().setDocName(result.getData().getMyName());
        VisitInfomation.getInstance().setDocTitle(result.getData().getAcademicTitleName());
        VisitInfomation.getInstance().setDocDept(result.getData().getDepartmentName());
        VisitInfomation.getInstance().setDocHos(result.getData().getHospitalName());
        VisitInfomation.getInstance().setServiceCount(result.getData().getServiceCount());
        VisitInfomation.getInstance().setConsultCount(result.getData().getConsultCount());

        ToolSharePerference.putStringData(view.getContext(), C.fileconfig, C.UniqueID,result.getData().getUniqueId());
        ToolSharePerference.putStringData(view.getContext(), C.fileconfig, C.AUTH,result.getData().getCheckState());

        ToolSharePerference.putStringData(view.getContext(), C.fileconfig, C.ISSUBJECT,result.getData().getIsSubject());
//        UserInfo.getInstance().setId("d"+ToolSharePerference.getStringData(view.getContext(),C.fileconfig,C.MOBILE));
//        UserInfo.getInstance().setUserSig(result.getUserSig());
        /**
         * TODO 保存用户信息
         * accountID-网易云账号
         * token-网易云token
         * **/
        UserInfoBean.getInstance().setAccountID(result.getData().getAccountId());
        UserInfoBean.getInstance().setToken(result.getData().getToken());
        UserInfoBean.getInstance().setIdType(result.getData().getIdType());

        String s1 = result.getData().getIdType();
        ToolSharePerference.putStringData(view.getContext(), C.fileconfig, C.IDTYPE,result.getData().getIdType());

        String s2 = ToolSharePerference.getStringData(view.getContext(), C.fileconfig, C.IDTYPE);
        VisitInfomation.getInstance().setAccountID(result.getData().getAccountId());
        VisitInfomation.getInstance().setToken(result.getData().getToken());
        VisitInfomation.getInstance().setIdType(result.getData().getIdType());

        VisitInfomation.getInstance().setFromPatID(result.getData().getPersonId());


    }
    private void clearData(){
        ToolSharePerference.putStringData(view.getContext(), C.fileconfig, C.USER_SIGN,"");
        ToolSharePerference.putStringData(view.getContext(), C.fileconfig, C.UserID,"");
        ToolSharePerference.putStringData(view.getContext(), C.fileconfig, C.UniqueID,"");
        ToolSharePerference.putStringData(view.getContext(), C.fileconfig, C.AUTH,"");
    }
}
