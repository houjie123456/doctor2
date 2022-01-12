package com.company.linquan.app.moduleAuth;

import android.graphics.Bitmap;
import android.util.Log;

import com.company.linquan.app.config.C;
import com.company.linquan.app.http.HttpApi;
import com.company.linquan.app.http.JSONBean;
import com.company.linquan.app.http.JSONPersonAll;
import com.company.linquan.app.util.Base64;
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
 * Created by YC on 2018/1/9.
 */

public class AuthPresenter implements AuthInterface.PresenterInterface {

    private AuthInterface.ViewInterface view;
    private String[] ids = new String[]{"3","4","10","1","2","5"};

    public AuthPresenter(AuthInterface.ViewInterface view) {
        this.view = view;
    }

    @Override
    public void auth(final String name,final String idCardNo,final String sex,final String careerType,final String academicTitle
            ,final String hospitalID,final String departmentID,final String picTypeList
            ,final String picSignIDList,final String photo,final String personRemark,final String beGoodInfo,final String personHonor,final String serviceIdList,final String years,final String specialty) {

        view.showDialog();
        new Thread(new Runnable() {
            @Override
            public void run() {
                StringBuilder buffer = new StringBuilder();
                if (!"".equals(photo) && !photo.startsWith("http")) {
                    Bitmap mBitmap = UploadPersonPhotoUtil.getBitmapFromFile(photo, 720, 1280);
                    final ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    mBitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
                    buffer = buffer.append(Base64.encodeToString(baos.toByteArray()));
                    mBitmap.recycle();
                    mBitmap = null;
                }
                    Map<String,String> map = new HashMap<>();
                    map.put("mobile", ToolSharePerference.getStringData(view.getContext(),C.fileconfig,C.MOBILE));
                    map.put("personID", ToolSharePerference.getStringData(view.getContext(),C.fileconfig,C.UserID));
                    map.put("headUrl", buffer.toString());
//                    map.put("headUrl", "");
                    map.put("name", name);
                    map.put("idCardNo", idCardNo);
                    map.put("sex", sex);
                    map.put("careerType", careerType);
                    map.put("academicTitle", academicTitle);
                    map.put("hospitalID", hospitalID);
                    map.put("departmentID", departmentID);
                    map.put("personRemark", personRemark);
                    map.put("beGoodInfo", beGoodInfo);
                    map.put("personHonor", personHonor);
                    map.put("serviceIdList", serviceIdList);
                    map.put("years", years);
                    map.put("specialty", specialty);
                    map.put("sign", ToolUtil.getSign(map));
                    if(!"".equals(picSignIDList)&&!",,,,".equals(picSignIDList)){
                        map.put("picTypeList", picTypeList);
                        map.put("picSignIDList", picSignIDList);
                    }
                    HttpApi.authByRealName(map.get("headUrl"),map.get("mobile"),map.get("personID"),map.get("name"),map.get("idCardNo")
                            ,map.get("sex"),map.get("careerType"),map.get("academicTitle"),map.get("hospitalID"),map.get("departmentID")
                            ,map.get("picTypeList"),map.get("picSignIDList"),map.get("sign"),map.get("personRemark")
                            ,map.get("beGoodInfo"),map.get("personHonor"),map.get("serviceIdList"),map.get("years"),map.get("specialty"))
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
        }).start();


    }

    @Override
    public void upLoad(final String path,final int type) {
        view.showDialog();
        new Thread(new Runnable() {
            @Override
            public void run() {
                StringBuilder buffer = new StringBuilder();
                if (!"".equals(path)) {
                    Bitmap mBitmap = UploadPersonPhotoUtil.getBitmapFromFile(path, 720, 1280);
                    final ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    mBitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
                    buffer = buffer.append(Base64.encodeToString(baos.toByteArray()));
                    mBitmap.recycle();
                    mBitmap = null;
                }
                try{

                }
                catch (Exception e){

                }

                Map<String, String> map = new HashMap<>();
                map.put("personID", ToolSharePerference.getStringData(view.getContext(),C.fileconfig,C.UserID));
                map.put("picType", ids[type]);
                map.put("picFile", buffer.toString());
                map.put("sign", ToolUtil.getSign(map));
                HttpApi.uploadPicTemp(map.get("personID"), map.get("picType"), map.get("picFile"), map.get("sign"))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<JSONBean>() {
                            @Override
                            public void onCompleted() {
                                Log.i("onCompleted", "onCompleted");
                                view.dismissDialog();
                            }
                            @Override
                            public void onError(Throwable e) {
                                view.dismissDialog();
                                Log.i("Throwable", e.toString());
                            }
                            @Override
                            public void onNext(JSONBean result) {
                                Log.i("onNext", "onNext");
                                //填充UI
                                if ("1".equals(result.getCode())) {
                                        view.initPath(result.getPicUrl(),result.getPicSignID(),type);

                                } else {
                                    view.showToast(result.getMsgBox());
                                }
                            }
                        });

            }
        }).start();
    }


    @Override
    public void getInfo() {
        view.showDialog();
        Map<String, String> map = new HashMap<>();
        map.put("personID", ToolSharePerference.getStringData(view.getContext(),C.fileconfig,C.UserID));
        map.put("mobile", "");
        map.put("personType", "1");
        map.put("sign", ToolUtil.getSign(map));
        HttpApi.getPersonInfoByParam(map.get("personID"), map.get("mobile"), map.get("personType"), map.get("sign"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JSONPersonAll>() {
                    @Override
                    public void onCompleted() {
                        Log.i("onCompleted", "onCompleted");
                        view.dismissDialog();
                    }
                    @Override
                    public void onError(Throwable e) {
                        view.dismissDialog();
                        Log.i("Throwable", e.toString());
                    }
                    @Override
                    public void onNext(JSONPersonAll result) {
                        Log.i("onNext", "onNext");
                        //填充UI
                        if ("1".equals(result.getCode())) {
                            view.reloadView(result);
                        } else {
                            view.showToast(result.getMsgBox());
                        }
                    }
                });
    }
}

