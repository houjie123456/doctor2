package com.company.wanbei.app.moduleAuth;

import android.graphics.Bitmap;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.tencent.qcloud.tuikit.tuichat.fromApp.config.C;
import com.company.wanbei.app.http.HttpApi;
import com.company.wanbei.app.http.JSONAuth;
import com.company.wanbei.app.http.JSONBean;
import com.company.wanbei.app.http.JSONPersonAll;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.Base64;
import com.company.wanbei.app.util.PhotoBitmapUtils;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.ToolSharePerference;
import com.company.wanbei.app.util.UploadPersonPhotoUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by YC on 2018/1/9.
 */

public class AuthPresenter implements AuthInterface.PresenterInterface {

    private AuthInterface.ViewInterface view;
    private String[] ids = new String[]{"3", "4", "10", "1", "2", "5"};

    public AuthPresenter(AuthInterface.ViewInterface view) {
        this.view = view;
    }

    @Override
    public void auth(final String name, final String idCardNo, final String sex, final String careerType, final String academicTitle
            , final String hospitalID, final String departmentID, final String picTypeList
            , final String picSignIDList, final String photo, final String personRemark, final String beGoodInfo
            , final String personHonor, final String serviceIdList, final String years, final String specialty) {
        view.showDialog();
        new Thread(new Runnable() {
            @Override
            public void run() {
                StringBuilder buffer = new StringBuilder();
                if (!"".equals(photo) && !photo.startsWith("http")) {
                    Bitmap mBitmap = UploadPersonPhotoUtil.getBitmapFromFile(photo, 720, 1280);
                    mBitmap= PhotoBitmapUtils.ImageCompressL(mBitmap);

                    final ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    mBitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
                    buffer = buffer.append(Base64.encodeToString(baos.toByteArray()));
                    mBitmap.recycle();
                    mBitmap = null;
                }
                JSONObject jsonObject  = new JSONObject();
                jsonObject.put("mobile", ToolSharePerference.getStringData(view.getContext(), C.fileconfig, C.MOBILE));
                jsonObject.put("id", ToolSharePerference.getStringData(view.getContext(), C.fileconfig, C.UserID));
//                jsonObject.put("headUrl", buffer.toString());
                jsonObject.put("headUrl", "");
                jsonObject.put("myName", name);
                jsonObject.put("idCardNo", idCardNo);
                jsonObject.put("sex", sex);
                jsonObject.put("careerType", careerType);
                jsonObject.put("academicTitle", academicTitle);
                jsonObject.put("hospitalId", hospitalID);
                jsonObject.put("departmentId", departmentID);
                jsonObject.put("personRemark", personRemark);
                jsonObject.put("beGoodAt", beGoodInfo);
                jsonObject.put("personHonor", personHonor);
                jsonObject.put("serviceIdList", serviceIdList);
                jsonObject.put("years", years);
                jsonObject.put("specialty", specialty);
                if (!"".equals(picSignIDList) && !",,,,".equals(picSignIDList)) {
                    jsonObject.put("picTypeList", picTypeList);
                    jsonObject.put("picSignIdList", picSignIDList);
                }
                RequestBody requestBody = HttpApi.packageParam(jsonObject);
                HttpApi.authByRealName(requestBody)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<JSONAuth>() {
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
                            public void onNext(JSONAuth result) {
                                Log.i("onNext", "onNext");
                                //填充UI
                                if ("0".equals(result.getCode())) {
                                    view.showToast(result.getMsgBox());
                                    view.finishActivity();
                                } else {
                                    view.showToast(result.getMsgBox());
                                }
                            }
                        });
            }
        }).start();
    }

    @Override
    public void upLoad(final String path, final int type) {
        view.showDialog();
        StringBuilder buffer = new StringBuilder();
        if (!"".equals(path)) {
            Bitmap mBitmap = UploadPersonPhotoUtil.getBitmapFromFile(path, 720, 1280);
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
            buffer = buffer.append(Base64.encodeToString(baos.toByteArray()));
            mBitmap.recycle();
            mBitmap = null;
        }
        File file = new File(path);
        RequestBody photoRequestBody = RequestBody.create(MediaType.parse("image/png"), file);
        MultipartBody.Part photo = MultipartBody.Part.createFormData("file", file.getName(), photoRequestBody);
        RequestBody relateType = RequestBody.create(MediaType.parse("text/plain"), "1");
        RequestBody fileType = RequestBody.create(MediaType.parse("text/plain"), ids[type]);
        HttpApi.uploadPicTemp(relateType,fileType,photo)
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
                        if ("0".equals(result.getCode())) {
                            view.initPath(result.getData().getPicUrl(), result.getData().getPicSignId(), type);

                        } else {
                            view.showToast(result.getMsgBox());
                        }
                    }
                });


    }
    @Override
    public void upLoadHead(final String path) {
        view.showDialog();
        StringBuilder buffer = new StringBuilder();
        if (!"".equals(path)) {
            Bitmap mBitmap = UploadPersonPhotoUtil.getBitmapFromFile(path, 720, 1280);
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
            buffer = buffer.append(Base64.encodeToString(baos.toByteArray()));
            mBitmap.recycle();
            mBitmap = null;
        }
        File file = new File(path);
        RequestBody photoRequestBody = RequestBody.create(MediaType.parse("image/png"), file);
        MultipartBody.Part photo = MultipartBody.Part.createFormData("file", file.getName(), photoRequestBody);
        RequestBody doctorId = RequestBody.create(MediaType.parse("text/plain"), ToolSharePerference.getStringData(view.getContext(), C.fileconfig, C.UserID));
        HttpApi.uploadPicHead(doctorId,photo)
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
                        view.showToast(result.getMsgBox());
                    }
                });


    }

    @Override
    public void getInfo() {
        view.showDialog();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", ToolSharePerference.getStringData(view.getContext(), C.fileconfig, C.UserID));
        RequestBody requestBody = HttpApi.packageParam(jsonObject);
        HttpApi.getPersonInfoByParam(requestBody)
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
                        if ("0".equals(result.getCode())) {
                            view.reloadView(result);
                        } else {
                            view.showToast(result.getMsgBox());
                        }
                    }
                });
    }
}

