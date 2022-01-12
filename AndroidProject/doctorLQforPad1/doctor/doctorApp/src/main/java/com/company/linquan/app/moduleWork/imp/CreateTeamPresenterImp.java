package com.company.linquan.app.moduleWork.imp;

import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

import com.company.linquan.app.config.C;
import com.company.linquan.app.http.HttpApi;
import com.company.linquan.app.http.JSONBean;
import com.company.linquan.app.http.JSONFriends;
import com.company.linquan.app.moduleWork.WorkInterface;
import com.company.linquan.app.util.ToolSharePerference;
import com.company.linquan.app.util.ToolUtil;
import com.company.linquan.app.util.UploadPersonPhotoUtil;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by YC on 2018/6/30.
 */

public class CreateTeamPresenterImp implements WorkInterface.CreateTeamPresenterInterface{

    private WorkInterface.CreateTeamInterface view;

    public CreateTeamPresenterImp(WorkInterface.CreateTeamInterface view) {
        this.view = view;
    }

    @Override
    public void createTeam(final String title,final String logo,final String remark,final String amount,final String memberIDStr) {
        view.showDialog();

        new Thread(new Runnable() {
            @Override
            public void run() {
                StringBuilder buffer = new StringBuilder();
                if (!"".equals(logo)) {
                    Bitmap mBitmap = UploadPersonPhotoUtil.getBitmapFromFile(logo, 720, 1280);
                    final ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    mBitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
                    buffer = buffer.append(Base64.encodeToString(baos.toByteArray(), 0));
                    mBitmap.recycle();
                    mBitmap = null;
                }

                Map<String,String> map = new HashMap<>();
                map.put("personID", ToolSharePerference.getStringData(view.getContext(), C.fileconfig,C.UserID));
                map.put("title", title);
                map.put("logo", buffer.toString());
                map.put("remark", remark);
                map.put("amount", amount);
                map.put("memberIDStr", memberIDStr);
                map.put("sign", ToolUtil.getSign(map));
                HttpApi.createDoctorGroup(map.get("personID"),map.get("title"),map.get("logo")
                        ,map.get("remark"),map.get("amount"),map.get("memberIDStr"),map.get("sign"))
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
                                    view.enterDetail();
                                }else {
                                    view.showToast(result.getMsgBox());
                                }
                            }
                        });

            }
        }).start();

    }

    @Override
    public void getFriends() {
        view.showDialog();
        Map<String,String> map = new HashMap<>();
        map.put("personID", ToolSharePerference.getStringData(view.getContext(), C.fileconfig,C.UserID));
        map.put("sign", ToolUtil.getSign(map));
        HttpApi.getMyFriendList(map.get("personID"),map.get("sign"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JSONFriends>(){
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
                    public void onNext(JSONFriends result) {
                        Log.i("onNext","onNext");
                        //填充UI
                        if ("1".equals(result.getCode())){
                            view.reloadList(result.getTable());
                        }else {
                            view.showToast(result.getMsgBox());
                        }
                    }
                });
    }
}
