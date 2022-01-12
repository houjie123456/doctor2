package com.company.wanbei.app.moduleWork;

import android.util.Log;
import android.util.SparseArray;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tencent.qcloud.tuikit.tuichat.fromApp.config.C;
import com.company.wanbei.app.http.HttpApi;
import com.company.wanbei.app.http.JSONBean;
import com.company.wanbei.app.http.JSONOnlineStudy;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.ToolSharePerference;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.ToolUtil;
import com.company.wanbei.app.R;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by YC on 2018/1/9.
 */

public class WorkPresenter implements WorkInterface.PresenterInterface {

    private WorkInterface.ViewInterface view;

    public WorkPresenter(WorkInterface.ViewInterface view) {
        this.view = view;
    }


    @Override
    public void getGridData() {

        SparseArray<SparseArray<String>> array = new SparseArray<>();

        SparseArray<String> s0 = new SparseArray<>();
        s0.put(0,"使用指南");
        s0.put(1,R.drawable.img_work_use_guide+"");
        array.put(0,s0);

        SparseArray<String> s1 = new SparseArray<>();
        s1.put(0,"院外护理");
        s1.put(1,R.drawable.img_work_nurse+"");
        array.put(1,s1);

        SparseArray<String> s2 = new SparseArray<>();
        s2.put(0,"护理咨询");
        s2.put(1,R.drawable.img_work_nurse_ask+"");
        array.put(2,s2);

        view.reloadGrid(array);
    }

    @Override
    public void onlineEducation() {
        view.showDialog();
        Map<String,String> map = new HashMap<>();
        map.put("personId", ToolSharePerference.getStringData(view.getContext(), C.fileconfig, C.UserID));
        map.put("sign", ToolUtil.getSign2(map));
        String json = JSON.toJSONString(map);//map转String
        JSONObject jsonObject = JSON.parseObject(json);//String转json
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),jsonObject.toString());
        HttpApi.onlineEducation(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JSONOnlineStudy>(){
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
                    public void onNext(JSONOnlineStudy result) {
                        Log.i("onNext","onNext");
                        //填充UI
                        if ("0".equals(result.getCode())){
                            view.enterOnlineStudy(result.getSign());
                        }else {
                            view.showToast(result.getMsgBox());
                        }
                    }
                });
    }

    @Override
    public void getTeamStatus() {
        view.showDialog();
        Map<String,String> map = new HashMap<>();
        map.put("personID", ToolSharePerference.getStringData(view.getContext(), C.fileconfig, C.UserID));
        map.put("sign", ToolUtil.getSign(map));
        HttpApi.getMyQualifications(map.get("personID"),map.get("sign"))
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
                            //0.有资格未创建 1.已创建 2.无资格创建
                            String status = result.getIsCreateState();
                            if ("0".equals(status)){
                                view.enterCreateTeam();
                            }

                            if ("1".equals(status)){
                                view.enterDetailTeam();
                            }

                        }else {
                            view.showToast(result.getMsgBox());
                        }
                    }
                });
    }
}
