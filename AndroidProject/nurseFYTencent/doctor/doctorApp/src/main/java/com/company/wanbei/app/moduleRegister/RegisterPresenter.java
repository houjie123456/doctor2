package com.company.wanbei.app.moduleRegister;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tencent.qcloud.tuikit.tuichat.fromApp.config.C;
import com.company.wanbei.app.http.HttpApi;
import com.company.wanbei.app.http.JSONBean;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.ToolSharePerference;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.ToolUtil;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
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
        String json = JSON.toJSONString(map);//map转String
        JSONObject jsonObject = JSON.parseObject(json);//String转json
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),jsonObject.toString());
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
        String json = JSON.toJSONString(map);//map转String
        JSONObject jsonObject = JSON.parseObject(json);//String转json
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),jsonObject.toString());
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
//                        if ("0".equals(result.getCode())){
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
        ToolSharePerference.putStringData(view.getContext(), C.AppConfig, C.UserID,result.getPersonId());
        ToolSharePerference.putStringData(view.getContext(), C.AppConfig, C.UniqueID,result.getUniqueId());
        ToolSharePerference.putStringData(view.getContext(), C.AppConfig, C.AUTH,result.getIsAuthenticat());
    }

    private void clearData(){
        ToolSharePerference.putStringData(view.getContext(), C.AppConfig, C.UserID,"");
        ToolSharePerference.putStringData(view.getContext(), C.AppConfig, C.UniqueID,"");
        ToolSharePerference.putStringData(view.getContext(), C.AppConfig, C.AUTH,"");
    }
}
