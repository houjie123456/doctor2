package com.company.linquan.app.moduleCenter.imp;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;

import com.company.linquan.app.R;
import com.company.linquan.app.config.C;
import com.company.linquan.app.http.HttpApi;
import com.company.linquan.app.http.JSONBean;
import com.company.linquan.app.moduleCenter.UserCenterInterface;
import com.company.linquan.app.nim.helper.UserUpdateHelper;
import com.company.linquan.app.util.Base64;
import com.company.linquan.app.util.DownloadApp;
import com.company.linquan.app.util.ToolSharePerference;
import com.company.linquan.app.util.ToolUtil;
import com.company.linquan.app.util.UploadPersonPhotoUtil;
import com.netease.nim.uikit.business.session.actions.PickImageAction;
import com.netease.nim.uikit.common.ToastHelper;
import com.netease.nim.uikit.common.util.log.LogUtil;
import com.netease.nimlib.sdk.AbortableFuture;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallbackWrapper;
import com.netease.nimlib.sdk.ResponseCode;
import com.netease.nimlib.sdk.nos.NosService;
import com.netease.nimlib.sdk.uinfo.UserService;
import com.netease.nimlib.sdk.uinfo.constant.UserInfoFieldEnum;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.netease.nim.uikit.common.util.sys.NetworkUtil.TAG;

/**
 * Created by YC on 2018/7/2.
 */

public class AccountPresenterImp implements UserCenterInterface.AccountPresenterInterface {

    private UserCenterInterface.AccountInterface view;
    AbortableFuture<String> uploadAvatarFuture;

    public AccountPresenterImp(UserCenterInterface.AccountInterface view) {
        this.view = view;
    }

    @Override
    public void updatePerson(final String name, String mobile,final String photoUrl) {
        view.showDialog();

        new Thread(new Runnable() {
            @Override
            public void run() {
                StringBuilder buffer = new StringBuilder();
                if (!"".equals(photoUrl)) {
                    Bitmap mBitmap = UploadPersonPhotoUtil.getBitmapFromFile(photoUrl, 720, 1280);
                    final ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    mBitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
                    buffer = buffer.append(Base64.encodeToString(baos.toByteArray()));
                    mBitmap.recycle();
                    mBitmap = null;
                }

                Map<String,String> map = new HashMap<>();
                map.put("personID", ToolSharePerference.getStringData(view.getContext(), C.fileconfig,C.UserID));
                map.put("myName", name);
                map.put("sex", "");
                map.put("personRemark","");
                map.put("idCardNo", "");
                map.put("headUrl", buffer.toString());
                map.put("hospitalID", "");
                map.put("departmentID", "");
                map.put("careerType", "");
                map.put("academicTitle", "");
                map.put("homeAddress", "");
                map.put("bankName", "");
                map.put("bankClearNo", "");
                map.put("branchName", "");
                map.put("branchClearNo", "");
                map.put("bankCardNo", "");
                map.put("accountMobile", "");
                map.put("accountName", "");
                map.put("picTypeList", "");
                map.put("picSignIDList", "");
                map.put("sign", "");
                HttpApi.updatePersonInfoByParam(map.get("personID"),map.get("myName"),"",map.get("headUrl"),map.get("sign"))
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
                                    //网易云更新头像
                                    File file = new File(photoUrl);
                                    if (file == null) {
                                        return;
                                    }
                                    uploadAvatarFuture = NIMClient.getService(NosService.class).upload(file, PickImageAction.MIME_JPEG);
                                    uploadAvatarFuture.setCallback(new RequestCallbackWrapper<String>() {
                                        @Override
                                        public void onResult(int code, String url, Throwable exception) {
                                            if (code == ResponseCode.RES_SUCCESS && !TextUtils.isEmpty(url)) {
                                                LogUtil.i(TAG, "upload avatar success, url =" + url);

                                                UserUpdateHelper.update(UserInfoFieldEnum.AVATAR, url, new RequestCallbackWrapper<Void>() {
                                                    @Override
                                                    public void onResult(int code, Void result, Throwable exception) {
                                                        if (code == ResponseCode.RES_SUCCESS) {
                                                            ToastHelper.showToast(view.getContext(), R.string.head_update_success);
                                                        } else {
                                                            ToastHelper.showToast(view.getContext(), R.string.head_update_failed);
                                                        }
                                                    }
                                                }); // 更新资料
                                            } else {
                                                ToastHelper.showToast(view.getContext(), R.string.user_info_update_failed);
                                            }
                                        }
                                    });
                                    if (!"".equals(name)) {
//                                        //网易云更新昵称
//                                        Map<UserInfoFieldEnum, Object> fields = new HashMap<>(1);
//                                        fields.put(UserInfoFieldEnum.Name, name);
//                                        NIMClient.getService(UserService.class).updateUserInfo(fields)
//                                                .setCallback(new RequestCallbackWrapper<Void>() {
//                                                    @Override
//                                                    public void onResult(int i, Void aVoid, Throwable throwable) {
//
//                                                    }
//                                                });
                                        view.reloadView(name);
                                    }
                                }else {
                                    view.showToast(result.getMsgBox());
                                }
                            }
                        });
            }
        }).start();



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
                Map<String, String> map = new HashMap<>();
                map.put("personID", ToolSharePerference.getStringData(view.getContext(),C.fileconfig,C.UserID));
                map.put("picType", "9");
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
                                    view.initPath(result.getPicUrl());
                                } else {
                                    view.showToast(result.getMsgBox());
                                }
                            }
                        });

            }
        }).start();
    }

    @Override
    public void checkVersion() {
        view.showDialog();
        HttpApi.getAppVersionInfo()
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
                            try {
                                String appCode = result.getVersionAndroid();
                                String appVersion = result.getVersionNumberAndroid();
                                String appurl = result.getDownUrlAndroid();
                                String isMust = result.getIsForceAndroid();
                                //保存到本地
                                ToolSharePerference.putStringData(view.getContext(), C.fileconfig, C.AppUrl,appurl);
                                ToolSharePerference.putStringData(view.getContext(), C.fileconfig, C.AppVersion, appVersion);

                                PackageManager manager = view.getContext().getPackageManager();
                                PackageInfo info = manager.getPackageInfo(view.getContext().getPackageName(), 0);
                                //根据VersionCode判断是否需要更新
                                if(Integer.parseInt(appCode) > info.versionCode){
                                    DownloadApp app = new DownloadApp(view.getContext());
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
