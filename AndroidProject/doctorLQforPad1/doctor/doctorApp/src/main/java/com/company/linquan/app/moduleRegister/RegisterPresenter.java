package com.company.linquan.app.moduleRegister;

import android.util.Log;

import com.company.linquan.app.config.C;
import com.company.linquan.app.http.HttpApi;
import com.company.linquan.app.http.JSONBean;
import com.company.linquan.app.util.ToolSharePerference;
import com.company.linquan.app.util.ToolUtil;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by YC on 2018/1/9.
 */

public class RegisterPresenter implements RegisterInterface.RegisterPresenterInterface {

    private RegisterInterface.RegisterViewInterface view;

    public RegisterPresenter(RegisterInterface.RegisterViewInterface view){
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
    public void login(String mobile, String code, String openID, String nickName, String sex, String picSignID,String versionNo) {
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
                            saveData(result);
                            view.finishActivity();
                        }else {
                            view.showToast(result.getMsgBox());
                            clearData();
                        }
                    }
                });

    }
//    @Override
//    public void login(String mobile, String code, String openID, String nickName, String sex, String picSignID) {
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
//                            saveData(result);
//                            view.finishActivity();
//                        }else {
//                            view.showToast(result.getMsgBox());
//                            clearData();
//                        }
//                    }
//                });
//
//    }


    private void saveData(JSONBean result){
        ToolSharePerference.putStringData(view.getContext(), C.AppConfig,C.UserID,result.getPersonID());
        ToolSharePerference.putStringData(view.getContext(),C.AppConfig,C.UniqueID,result.getUniqueID());
        ToolSharePerference.putStringData(view.getContext(),C.AppConfig,C.AUTH,result.getIsAuthenticat());
    }

    private void clearData(){
        ToolSharePerference.putStringData(view.getContext(),C.AppConfig,C.UserID,"");
        ToolSharePerference.putStringData(view.getContext(),C.AppConfig,C.UniqueID,"");
        ToolSharePerference.putStringData(view.getContext(),C.AppConfig,C.AUTH,"");
    }
}
