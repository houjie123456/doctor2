package com.company.wanbei.app.moduleCenter.imp;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tencent.qcloud.tuikit.tuichat.fromApp.config.C;
import com.company.wanbei.app.http.HttpApi;
import com.company.wanbei.app.http.JSONBean;
import com.company.wanbei.app.moduleCenter.UserCenterInterface;
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
 * Created by YC on 2018/7/2.
 */

public class SignPresenterImp implements UserCenterInterface.SignPresenterInterface {

    private UserCenterInterface.SignInterface view;

    public SignPresenterImp(UserCenterInterface.SignInterface view) {
        this.view = view;
    }

    @Override
    public void stopSignAuto() {
        view.showDialog();
        Map<String,String> map = new HashMap<>();
        map.put("doctorId", ToolSharePerference.getStringData(view.getContext(), C.fileconfig, C.UserID));
        map.put("sign", ToolUtil.getSign2(map));
        String json = JSON.toJSONString(map);//map转String
        JSONObject jsonObject = JSON.parseObject(json);//String转json
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),jsonObject.toString());
        HttpApi.stopSignAuto(requestBody)
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
                        }else {
                            view.showToast(result.getMsgBox());
                        }
                    }
                });
    }
}
