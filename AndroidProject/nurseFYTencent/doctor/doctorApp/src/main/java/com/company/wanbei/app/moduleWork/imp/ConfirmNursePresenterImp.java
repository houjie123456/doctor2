package com.company.wanbei.app.moduleWork.imp;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.company.wanbei.app.http.HttpApi;
import com.company.wanbei.app.http.JSONBean;
import com.company.wanbei.app.moduleWork.WorkInterface;

import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by YC on 2018/6/29.
 */

public class ConfirmNursePresenterImp implements WorkInterface.ConfirmNursePresenterInterface {

    private WorkInterface.ConfirmNurseInterface view;

    public ConfirmNursePresenterImp(WorkInterface.ConfirmNurseInterface view) {
        this.view = view;
    }

    /**
     * TODO 审核接口
     */
    @Override
    public void confirmNurse(String id,String checkState,String checkRemark) {
        view.showDialog();
        JSONObject map = new JSONObject();
        map.put("id", id);
        map.put("checkState", checkState);
        map.put("checkRemark", checkRemark);
        RequestBody requestBody = HttpApi.packageParam(map);
        HttpApi.updateCheckState(requestBody)
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
                            view.finishActivity();
                        }else {
                            view.showToast(result.getMsgBox());
                        }
                    }
                });
    }


}
