package com.company.linquan.app.moduleLogin;

import android.content.pm.PackageManager;
import android.util.Log;

import com.company.linquan.app.bean.UserInfoBean;
import com.company.linquan.app.config.C;
import com.company.linquan.app.http.HttpApi;
import com.company.linquan.app.http.JSONBean;
import com.company.linquan.app.nim.helper.SessionHelper;
import com.company.linquan.app.util.ToolSharePerference;
import com.company.linquan.app.util.ToolUtil;
import com.netease.nim.uikit.visitinfo.VisitInfomation;
import com.netease.nimlib.sdk.uinfo.model.UserInfo;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by YC on 2018/1/9.
 */

public class LoginPresenter implements LoginInterface.LoginPresenterInterface {


    private LoginInterface.LoginViewInterface view;

    public LoginPresenter(LoginInterface.LoginViewInterface view){
        this.view = view;
    }


    @Override
    public void getCode(String mobile) {
        view.showDialog();
        Map<String,String> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("mobileType", "1");
        map.put("sendType", "2");
        map.put("sign", ToolUtil.getSign(map));
        HttpApi.getCode(map.get("mobile"),map.get("mobileType"),map.get("sendType"),map.get("sign"))
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
                        if ("1".equals(result.getCode())){
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
        Map<String,String> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("smsCode", code);
        map.put("openID", openID);
        map.put("nickName", nickName);
        map.put("sex", sex);
        map.put("picSignID", picSignID);
        map.put("versionNo", versionNo);
        map.put("sign", ToolUtil.getSign(map));
        HttpApi.login(map.get("mobile"),map.get("smsCode"),map.get("openID"),map.get("nickName")
                ,map.get("sex"),map.get("picSignID"),map.get("versionNo"),map.get("sign"))
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
                        if ("1".equals(result.getCode())){
                            view.showToast(result.getMsgBox());
                            saveData(result,mobile);
                            Log.i("onNext",ToolSharePerference.getStringData(view.getContext(),C.AppConfig,C.UserID));
                            view.finishActivity();
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
//                        if ("1".equals(result.getCode())){
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


    private void saveData(JSONBean result,String mobile){
        ToolSharePerference.putStringData(view.getContext(),C.fileconfig,C.MOBILE,mobile);
        ToolSharePerference.putStringData(view.getContext(),C.fileconfig,C.USER_SIGN,result.getUserSig());
        ToolSharePerference.putStringData(view.getContext(),C.fileconfig,C.UserID,result.getPersonID());
        ToolSharePerference.putStringData(view.getContext(),C.fileconfig,C.UniqueID,result.getUniqueID());
        ToolSharePerference.putStringData(view.getContext(),C.fileconfig,C.AUTH,result.getIsAuthenticat());

//        UserInfo.getInstance().setId("d"+ToolSharePerference.getStringData(view.getContext(),C.fileconfig,C.MOBILE));
//        UserInfo.getInstance().setUserSig(result.getUserSig());
        /**
         * TODO 保存用户信息
         * accountID-网易云账号
         * token-网易云token
         * **/
        UserInfoBean.getInstance().setAccountID(result.getAccountID());
        UserInfoBean.getInstance().setToken(result.getToken());
        UserInfoBean.getInstance().setIdType(result.getIdType());

        VisitInfomation.getInstance().setAccountID(result.getAccountID());
        VisitInfomation.getInstance().setToken(result.getToken());
        VisitInfomation.getInstance().setIdType(result.getIdType());
        // IM 会话窗口的定制初始化。
        SessionHelper.init();
//        ToolSharePerference.putStringData(view.getContext(),C.fileconfig,C.accountID,result.getAccountID());
//        ToolSharePerference.putStringData(view.getContext(),C.fileconfig,C.token,result.getToken());

    }

    private void clearData(){
        ToolSharePerference.putStringData(view.getContext(),C.fileconfig,C.UserID,"");
        ToolSharePerference.putStringData(view.getContext(),C.fileconfig,C.UniqueID,"");
        ToolSharePerference.putStringData(view.getContext(),C.fileconfig,C.AUTH,"");
        ToolSharePerference.putStringData(view.getContext(),C.fileconfig,C.USER_SIGN,"");
    }
}
