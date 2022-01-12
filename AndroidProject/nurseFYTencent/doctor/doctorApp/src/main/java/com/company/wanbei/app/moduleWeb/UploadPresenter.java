package com.company.wanbei.app.moduleWeb;

import android.graphics.Bitmap;

import com.alibaba.fastjson.JSONObject;
import com.tencent.qcloud.tuikit.tuichat.fromApp.http.HttpApi;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.Base64;
import com.company.wanbei.app.util.UploadPersonPhotoUtil;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import okhttp3.RequestBody;

/**
 * Created by YC on 2018/1/9.
 */

public class UploadPresenter implements UploadInterface.PresenterInterface {

    private UploadInterface.ViewInterface view;
    private String[] ids = new String[]{"3", "4", "10", "1", "2", "5"};

    public UploadPresenter(UploadInterface.ViewInterface view) {
        this.view = view;
    }



    @Override
    public void upLoad(String personID,final String path, final int type) {
        view.showDialog();
        ArrayList<String> arrayList=new ArrayList<>();
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
                try {

                } catch (Exception e) {

                }

                JSONObject map = new JSONObject();
                map.put("relatId", personID);
                map.put("relatType", "6");
                map.put("fileType", String.valueOf(type));
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
//
//                            @Override
//                            public void onError(Throwable e) {
//                                view.dismissDialog();
//                                Log.i("Throwable", e.toString());
//                            }
//
//                            @Override
//                            public void onNext(JSONBean result) {
//                                Log.i("onNext", "onNext");
//                                //填充UI
//                                if ("0".equals(result.getCode())) {
//                                    view.initPath(result.getData().getPicUrl(),result.getData().getPicSignId());
//                                } else {
//                                    view.showToast(result.getMsgBox());
//                                }
//                            }
//                        });

            }
        }).start();
    }

}

