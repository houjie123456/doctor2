package com.company.linquan.app.moduleSplash;

import android.util.Log;

import com.company.linquan.app.bean.UserInfoBean;
import com.company.linquan.app.config.C;
import com.company.linquan.app.http.HttpApi;
import com.company.linquan.app.http.JSONBean;
import com.company.linquan.app.nim.helper.SessionHelper;
import com.company.linquan.app.util.ToolSharePerference;
import com.company.linquan.app.util.ToolUtil;
import com.netease.nim.uikit.visitinfo.VisitInfomation;

import java.util.HashMap;
import java.util.Map;

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
        Map<String,String> map = new HashMap<>();
        map.put("uniqueID", ToolSharePerference.getStringData(view.getContext(), C.fileconfig, C.UniqueID));
        map.put("versionNo", versionNo);
        map.put("sign", ToolUtil.getSign(map));
        HttpApi.loginById(map.get("uniqueID"),map.get("versionNo"),map.get("sign"))
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
        ToolSharePerference.putStringData(view.getContext(),C.fileconfig,C.USER_SIGN,"");
        ToolSharePerference.putStringData(view.getContext(),C.fileconfig,C.UserID,"");
        ToolSharePerference.putStringData(view.getContext(),C.fileconfig,C.UniqueID,"");
        ToolSharePerference.putStringData(view.getContext(),C.fileconfig,C.AUTH,"");
    }
}
