package com.company.wanbei.app.moduleLogin;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.company.wanbei.app.bean.UserInfoBean;
import com.company.wanbei.app.http.HttpApi;
import com.tencent.qcloud.tuikit.tuichat.fromApp.config.C;
import com.company.wanbei.app.http.JSONBean;
import com.company.wanbei.app.moduleSplash.SplashActivity;
import com.company.wanbei.app.tim.VisitInfomation;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.ToolSharePerference;
import com.tencent.imsdk.v2.V2TIMCallback;
import com.tencent.qcloud.tuicore.TUILogin;
import com.tencent.qcloud.tuicore.util.ToastUtil;

import cn.org.bjca.sdk.core.kit.BJCASDK;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by YC on 2018/1/9.
 */

public class LoginPresenter implements LoginInterface.LoginPresenterInterface {

    private final String TAG = SplashActivity.class.getSimpleName();

    private LoginInterface.LoginViewInterface view;

    public LoginPresenter(LoginInterface.LoginViewInterface view){
        this.view = view;
    }


    @Override
    public void getCode(String mobile) {
        view.showDialog();
//        Map<String,String> map = new HashMap<>();
//        map.put("mobile", mobile);
////        map.put("mobileType", "1");
//        map.put("sendType", "2");
//        map.put("sign", ToolUtil.getSign2(map));
//        String json = JSON.toJSONString(map);//map转String
//        JSONObject jsonObject = JSON.parseObject(json);//String转json

        JSONObject jsonObject =new JSONObject();
        jsonObject.put("mobile", mobile);
        jsonObject.put("sendType", "2");

        RequestBody requestBody = HttpApi.packageParam(jsonObject);
        HttpApi.getCode(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JSONBean>(){
                    @Override
                    public void onCompleted() {
                        Log.i("onCompleted","onCompleted");
                        view.dismissDialog();
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
                            view.startTimer();
                        }else {
                            view.showToast(result.getMsgBox());
                        }
                    }
                });

    }

    @Override
    public void login(final String mobile, String code, String openID, String nickName, String sex, String versionNo,String picSignID) {
        view.showDialog();


        JSONObject jsonObject =new JSONObject();
        jsonObject.put("mobile", mobile);
        jsonObject.put("smsCode", code);
        jsonObject.put("openId", openID);
        jsonObject.put("nickName", nickName);
        jsonObject.put("sex", sex);
        jsonObject.put("picSignId", picSignID);
        jsonObject.put("versionNo", versionNo);

        RequestBody requestBody = HttpApi.packageParam(jsonObject);

        HttpApi.login(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JSONBean>(){
                    @Override
                    public void onCompleted() {
                        Log.i("onCompleted","onCompleted");
                        view.dismissDialog();
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
                            String openId= BJCASDK.getInstance().getOpenId(view.getContext());
                            if(openId!=null&&!openId.equals(result.getOpenId())){
                                BJCASDK.getInstance().clearCert(view.getContext());
                            }
                            login(result,mobile);

                        }else {
                            view.showToast(result.getMsgBox());
//                            clearData();
                        }
                    }
                });

    }
//    @Override
//    public void login(final String mobile, String code, String openID, String nickName, String sex,String picSignID) {
//        view.showDialog();
//        Map<String,String> map = new HashMap<>();
//        map.put("mobile", mobile);
//        map.put("smsCode", code);
//        map.put("openID", openID);
//        map.put("nickName", nickName);
//        map.put("sex", sex);
//        map.put("picSignID", picSignID);
//        map.put("sign", ToolUtil.getSign(map));
//        HttpApi.login(map.get("mobile"),map.get("smsCode"),map.get("openID"),map.get("nickName")
//                ,map.get("sex"),map.get("picSignID"),map.get("sign"))
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<JSONBean>(){
//                    @Override
//                    public void onCompleted() {
//                        Log.i("onCompleted","onCompleted");
//                        view.dismissDialog();
//                    }
//                    @Override
//                    public void onError(Throwable e) {
//                        view.dismissDialog();
//                        Log.i("Throwable",e.toString());
//                    }
//                    @Override
//                    public void onNext(JSONBean result) {
//                        Log.i("onNext","onNext");
//                        //填充UI
//                        if ("0".equals(result.getCode())){
//                            view.showToast(result.getMsgBox());
//                            saveData(result,mobile);
//                            Log.i("onNext",ToolSharePerference.getStringData(view.getContext(),C.AppConfig,C.UserID));
//                            view.finishActivity();
//                        }else {
//                            view.showToast(result.getMsgBox());
////                            clearData();
//                        }
//                    }
//                });
//
//    }


    private void saveData(JSONBean result, String mobile){
        ToolSharePerference.putStringData(view.getContext(), C.fileconfig, C.CLIENTID,result.getData().getClientId());
        ToolSharePerference.putStringData(view.getContext(), C.fileconfig, C.MOBILE,mobile);
        ToolSharePerference.putStringData(view.getContext(), C.fileconfig, C.USER_SIGN,result.getData().getUserSig());
        ToolSharePerference.putStringData(view.getContext(), C.fileconfig, C.UserID,result.getData().getPersonId());

        ToolSharePerference.putStringData(view.getContext(), C.fileconfig, C.DeptWorkstationID,result.getData().getDeptWorkstationId());

        VisitInfomation.getInstance().setDoctorId(result.getData().getPersonId());
        VisitInfomation.getInstance().setDeptWorkstationId(result.getData().getDeptWorkstationId());

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
        ToolSharePerference.putStringData(view.getContext(), C.fileconfig, C.UserID,"");
        ToolSharePerference.putStringData(view.getContext(), C.fileconfig, C.UniqueID,"");
        ToolSharePerference.putStringData(view.getContext(), C.fileconfig, C.AUTH,"");
        ToolSharePerference.putStringData(view.getContext(), C.fileconfig, C.USER_SIGN,"");
    }

    /**
     * ***************************************** 登录 **************************************
     */
    private void login(JSONBean result, String mobile) {
        String s1 = result.getData().getAccountId();
        String s2 = result.getData().getToken();
        TUILogin.login(result.getData().getAccountId(), result.getData().getToken(), new V2TIMCallback() {
            @Override
            public void onError(final int code, final String desc) {
                ToastUtil.toastLongMessage("登录失败: " + code + "，" + desc);
            }

            @Override
            public void onSuccess() {
                view.showToast(result.getMsgBox());

                saveData(result, mobile);
                Log.i("onNext", ToolSharePerference.getStringData(view.getContext(), C.AppConfig, C.UserID));

                view.finishActivity();
            }
        });


    }
}
