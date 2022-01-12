package com.company.wanbei.app.moduleCenter.imp;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.tencent.qcloud.tuikit.tuichat.fromApp.config.C;
import com.company.wanbei.app.http.HttpApi;
import com.company.wanbei.app.http.JSONBean;
import com.company.wanbei.app.moduleCenter.UserCenterInterface;
import com.company.wanbei.app.moduleCenter.ui.AccountActivity;
import com.company.wanbei.app.tim.utils.DemoLog;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.Base64;
import com.company.wanbei.app.util.DownloadApp;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.ToolSharePerference;
import com.company.wanbei.app.util.UploadPersonPhotoUtil;
import com.tencent.imsdk.v2.V2TIMCallback;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.imsdk.v2.V2TIMUserFullInfo;
import com.tencent.qcloud.tuicore.util.ToastUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by YC on 2018/7/2.
 */

public class AccountPresenterImp implements UserCenterInterface.AccountPresenterInterface {

    private UserCenterInterface.AccountInterface view;
    AccountActivity activity;

    public AccountPresenterImp(UserCenterInterface.AccountInterface view) {
        this.view = view;
    }

    @Override
    public void updatePerson(final String name, String mobile,final String photoUrl) {
        view.showDialog();
        V2TIMUserFullInfo v2TIMUserFullInfo = new V2TIMUserFullInfo();
        // 头像
        if (!TextUtils.isEmpty(photoUrl)) {
            v2TIMUserFullInfo.setFaceUrl(photoUrl);
//            UserInfoBean.getInstance().setAvatar(faceUrl);
        }

        V2TIMManager.getInstance().setSelfInfo(v2TIMUserFullInfo, new V2TIMCallback() {
            @Override
            public void onError(int code, String desc) {
                DemoLog.e("updateProfileLog", "modifySelfProfile err code = " + code + ", desc = " + desc);
                ToastUtil.toastShortMessage("Error code = " + code + ", desc = " + desc);
            }

            @Override
            public void onSuccess() {
                DemoLog.i("updateProfileLog", "modifySelfProfile success");
            }
        });
        StringBuilder buffer = new StringBuilder();
        if (!"".equals(photoUrl)) {
            Bitmap mBitmap = UploadPersonPhotoUtil.getBitmapFromFile(photoUrl, 720, 1280);
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
            buffer = buffer.append(Base64.encodeToString(baos.toByteArray()));
            mBitmap.recycle();
            mBitmap = null;
        }
        File file = new File(photoUrl);
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
    public void upload(final String path) {
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
                JSONObject map = new JSONObject();
                map.put("personID", ToolSharePerference.getStringData(view.getContext(), C.fileconfig, C.UserID));
                map.put("picType", "9");
                map.put("picFile", buffer.toString());
                RequestBody requestBody = HttpApi.packageParam(map);
//                HttpApi.uploadPicTemp(requestBody)
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new Subscriber<JSONBean>() {
//                            @Override
//                            public void onCompleted() {
//                                Log.i("onCompleted", "onCompleted");
//                                view.dismissDialog();
//                            }
//                            @Override
//                            public void onError(Throwable e) {
//                                view.dismissDialog();
//                                Log.i("Throwable", e.toString());
//                            }
//                            @Override
//                            public void onNext(JSONBean result) {
//                                Log.i("onNext", "onNext");
//                                //填充UI
//                                if ("0".equals(result.getCode())) {
//                                    view.initPath(result.getData().getPicUrl());
//                                } else {
//                                    view.showToast(result.getMsgBox());
//                                }
//                            }
//                        });
//
            }
        }).start();
    }

    @Override
    public void checkVersion() {
        view.showDialog();
        JSONObject jsonObject =new JSONObject();
        RequestBody requestBody = HttpApi.packageParam(jsonObject);
        HttpApi.getAppVersionInfo(requestBody)
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
                            try {
                                String appCode = result.getData().getVersionAndroid();
                                String appVersion = result.getData().getVersionNumberAndroid();
                                String appurl = result.getData().getDownUrlAndroid();
                                String isMust = result.getData().getIsForceAndroid();
                                //保存到本地
                                ToolSharePerference.putStringData(view.getContext(), C.fileconfig, C.AppUrl,appurl);
                                ToolSharePerference.putStringData(view.getContext(), C.fileconfig, C.AppVersion, appVersion);

                                PackageManager manager = view.getContext().getPackageManager();
                                PackageInfo info = manager.getPackageInfo(view.getContext().getPackageName(), 0);
                                //根据VersionCode判断是否需要更新
                                if(Integer.parseInt(appCode) > info.versionCode){
                                    DownloadApp app = new DownloadApp(view.getContext(),activity);
                                    if("0".equals(isMust)){
                                        app.DownloadDialog(appVersion, "", false);
                                    }else{
                                        app.DownloadDialog(appVersion, "", true);
                                    }
                                }else{
                                    view.showToast("当前版本已经是最新版本");
                                }
                            }catch (Exception e){

                            }
                        }else {
                            view.showToast(result.getMsgBox());
                        }
                    }
                });
    }
}
