package com.company.linquan.app.moduleWork.imp;

import android.util.Log;

import com.company.linquan.app.config.C;
import com.company.linquan.app.http.HttpApi;
import com.company.linquan.app.http.JSONBean;
import com.company.linquan.app.moduleWork.WorkInterface;
import com.company.linquan.app.util.ToolSharePerference;
import com.company.linquan.app.util.ToolUtil;

import java.util.HashMap;
import java.util.Map;

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
        Map<String,String> map = new HashMap<>();
        map.put("id", id);
        map.put("checkState", checkState);
        map.put("checkRemark", checkRemark);
        map.put("sign", ToolUtil.getSign(map));
        HttpApi.updateCheckState(map.get("id"),map.get("checkState"),map.get("checkRemark"),map.get("sign"))
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
                            view.finishActivity();
                        }else {
                            view.showToast(result.getMsgBox());
                        }
                    }
                });
    }
}
