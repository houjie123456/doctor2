package com.company.linquan.app.moduleWork.imp;

import android.graphics.Bitmap;
import android.util.Log;

import com.company.linquan.app.config.C;
import com.company.linquan.app.http.HttpApi;
import com.company.linquan.app.http.JSONBean;
import com.company.linquan.app.http.JSONRecipeDrug;
import com.company.linquan.app.moduleWork.WorkInterface;
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
 * Created by YC on 2018/7/1.
 */

public class CreateRecipePresenterImp implements WorkInterface.CreateRecipePresenterInterface {

    private WorkInterface.CreateRecipeInterface view;

    public CreateRecipePresenterImp(WorkInterface.CreateRecipeInterface view) {
        this.view = view;
    }

    @Override
    public void createRecipe(String patientID,String visitId, String remark, String json) {
        view.showDialog();
        Map<String,String> map = new HashMap<>();
        map.put("personID", ToolSharePerference.getStringData(view.getContext(), C.fileconfig,C.UserID));
        map.put("patientID", patientID);
        map.put("visitID", visitId);
        map.put("diagnosisRemark", remark);
        map.put("drugJsonArray", json);
        map.put("sign", ToolUtil.getSign(map));
        HttpApi.addRecipeInfo(map.get("personID"),map.get("patientID"),map.get("visitID")
                ,map.get("diagnosisRemark"),map.get("drugJsonArray"),map.get("sign"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JSONBean>(){
                    @Override
                    public void onCompleted() {
                        Log.i("onCompleted","onCompleted");
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.i("Throwable",e.toString());
                    }
                    @Override
                    public void onNext(JSONBean result) {
                        Log.i("onNext","onNext");
                        //填充UI
                        if ("1".equals(result.getCode())){
                            view.dismissDialog();
                            view.showToast(result.getMsgBox());
                            view.finishActivity();
                        }else {
                            view.dismissDialog();
                            view.showToast(result.getMsgBox());
                        }
                    }
                });
    }

    @Override
    public void getRecipe(final String recipeID) {
        Map<String,String> map = new HashMap<>();
        map.put("recipeID", recipeID);
        map.put("recipeDrugID", "");
        map.put("sign", ToolUtil.getSign(map));
        HttpApi.getRecipeDrugInfo(map.get("recipeID"),map.get("recipeDrugID"),map.get("sign"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JSONRecipeDrug>(){
                    @Override
                    public void onCompleted() {
                        Log.i("onCompleted","onCompleted");
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.i("Throwable",e.toString());
                    }
                    @Override
                    public void onNext(JSONRecipeDrug result) {
                        Log.i("onNext","onNext");
                        //填充UI
                        if ("1".equals(result.getCode())){
                            view.showToast(result.getMsgBox());
                            view.reloadList(result.getTable());
                        }else {
                            view.showToast(result.getMsgBox());
                        }
                    }
                });
    }

    @Override
    public void upLoad(final String path, final String type) {
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
                map.put("picType", type);
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
                                    view.reloadPicState();

                                } else {
                                    view.showToast(result.getMsgBox());
                                }
                            }
                        });

            }
        }).start();
    }

    @Override
    public void addGroupInfo(String groupid,String icdName) {
        Map<String,String> map = new HashMap<>();
        map.put("groupid", groupid);
        map.put("icdName", icdName);
        HttpApi.addGroupInfo(map.get("groupid"),map.get("icdName"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JSONBean>(){
                    @Override
                    public void onCompleted() {
                        Log.i("onCompleted","onCompleted");
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.i("Throwable",e.toString());
                    }
                    @Override
                    public void onNext(JSONBean result) {
                        Log.i("onNext","onNext");
                        //填充UI
                        if ("1".equals(result.getCode())){
                            view.addGroupInfoResult(result.getCode());
                        }else {
                            view.showToast(result.getMsgBox());
                        }
                    }
                });
    }
}
