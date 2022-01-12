package com.company.wanbei.app.moduleAuth.imp;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.company.wanbei.app.bean.SelectDataBean;
import com.company.wanbei.app.http.HttpApi;
import com.company.wanbei.app.http.JSONSelectData;
import com.company.wanbei.app.http.JSONSelectDoc;
import com.company.wanbei.app.moduleAuth.AuthInterface;

import java.util.ArrayList;

import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by YC on 2018/7/8.
 */

public class SelectDataPresenterImp implements AuthInterface.SelectPresenterInterface {

    private AuthInterface.SelectInterface view;

    public SelectDataPresenterImp(AuthInterface.SelectInterface view) {
        this.view = view;
    }

    @Override
    public void getHospital(String name) {
        JSONObject map = new JSONObject();
        map.put("thisName", name);
        RequestBody requestBody = HttpApi.packageParam(map);
        HttpApi.getHospitalInfo(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JSONSelectData>(){
                    @Override
                    public void onCompleted() {
                        Log.i("onCompleted","onCompleted");
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.i("Throwable",e.toString());
                    }
                    @Override
                    public void onNext(JSONSelectData result) {
                        Log.i("onNext","onNext");
                        //填充UI
                        if ("0".equals(result.getCode())){
                            view.setListData(result.getData());
                        }else {
                            view.showToast(result.getMsgBox());
                        }
                    }
                });
    }

    @Override
    public void getJob() {

        ArrayList<SelectDataBean> temp = new ArrayList<>();
        SelectDataBean b1 = new SelectDataBean();
        b1.setId("1");
        b1.setJobName("全职");
        temp.add(b1);

        SelectDataBean b2 = new SelectDataBean();
        b2.setId("2");
        b2.setJobName("兼职");
        temp.add(b2);

        view.setListData(temp);

    }

    @Override
    public void getSex() {
        ArrayList<SelectDataBean> temp = new ArrayList<>();
        SelectDataBean b1 = new SelectDataBean();
        b1.setId("1");
        b1.setSex("男");
        temp.add(b1);

        SelectDataBean b2 = new SelectDataBean();
        b2.setId("2");
        b2.setSex("女");
        temp.add(b2);

        view.setListData(temp);
    }

    @Override
    public void getDepartment() {
        JSONObject map = new JSONObject();
        RequestBody requestBody = HttpApi.packageParam(map);
        HttpApi.getDepartment(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JSONSelectDoc>(){
                    @Override
                    public void onCompleted() {
                        Log.i("onCompleted","onCompleted");
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.i("Throwable",e.toString());
                    }
                    @Override
                    public void onNext(JSONSelectDoc result) {
                        Log.i("onNext","onNext");
                        //填充UI
                        if ("0".equals(result.getCode())){
                            view.setListData(result.getData());
                        }else {
                            view.showToast(result.getMsgBox());
                        }
                    }
                });
    }


    @Override
    public void getPosition() {
        JSONObject map = new JSONObject();
        RequestBody requestBody = HttpApi.packageParam(map);
        HttpApi.getAcademicTitleList(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JSONSelectData>(){
                    @Override
                    public void onCompleted() {
                        Log.i("onCompleted","onCompleted");
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.i("Throwable",e.toString());
                    }
                    @Override
                    public void onNext(JSONSelectData result) {
                        Log.i("onNext","onNext");
                        //填充UI
                        if ("0".equals(result.getCode())){
                            view.setListData(result.getData());
                        }else {
                            view.showToast(result.getMsgBox());
                        }
                    }
                });
//        ArrayList<SelectDataBean> temp = new ArrayList<>();
//        SelectDataBean b1 = new SelectDataBean();
//        b1.setId("1");
//        b1.setPositionName("主任医师");
//        temp.add(b1);
//
//        SelectDataBean b2 = new SelectDataBean();
//        b2.setId("2");
//        b2.setPositionName("副主任医师");
//        temp.add(b2);
//
//        SelectDataBean b3 = new SelectDataBean();
//        b3.setId("3");
//        b3.setPositionName("主治医师");
//        temp.add(b3);
//
//        SelectDataBean b4 = new SelectDataBean();
//        b4.setId("4");
//        b4.setPositionName("医师");
//        temp.add(b4);
//
//        SelectDataBean b5 = new SelectDataBean();
//        b5.setId("5");
//        b5.setPositionName("护士");
//        temp.add(b5);
//
//        SelectDataBean b6 = new SelectDataBean();
//        b6.setId("6");
//        b6.setPositionName("护师");
//        temp.add(b6);
//
//        SelectDataBean b7 = new SelectDataBean();
//        b7.setId("7");
//        b7.setPositionName("副主任护师");
//        temp.add(b7);
//
//        SelectDataBean b8 = new SelectDataBean();
//        b8.setId("8");
//        b8.setPositionName("主任护师");
//        temp.add(b8);
//
//        SelectDataBean b9 = new SelectDataBean();
//        b8.setId("10");
//        b8.setPositionName("医士");
//        temp.add(b9);
//
//        SelectDataBean b10 = new SelectDataBean();
//        b8.setId("11");
//        b8.setPositionName("主管护师");
//        temp.add(b10);
//
//        //4.医师 5.护士 6.护师 7.副主任护师 8.主任护师”
//
//        view.setListData(temp);
    }

    @Override
    public void getTime() {
        ArrayList<SelectDataBean> temp = new ArrayList<>();
        SelectDataBean b1 = new SelectDataBean();
        b1.setId("1");
        b1.setPositionName("每日一次");
        temp.add(b1);

        SelectDataBean b2 = new SelectDataBean();
        b2.setId("2");
        b2.setPositionName("每日两次");
        temp.add(b2);

        SelectDataBean b3 = new SelectDataBean();
        b3.setId("3");
        b3.setPositionName("每日三次");
        temp.add(b3);

        SelectDataBean b4 = new SelectDataBean();
        b4.setId("4");
        b4.setPositionName("每日四次");
        temp.add(b4);

        SelectDataBean b5 = new SelectDataBean();
        b5.setId("");
        b5.setPositionName("自定义");
        temp.add(b5);

        view.setListData(temp);

    }

    @Override
    public void getEat() {
//        ArrayList<SelectDataBean> temp = new ArrayList<>();
//        for(DrugTakingBean bean:TAKINGTYPE){
//            SelectDataBean b1 = new SelectDataBean();
//            b1.setId(bean.getKey());
//            b1.setPositionName(bean.getValue());
//            temp.add(b1);
//        }
//
//        view.setListData(temp);

    }

    @Override
    public void getSettingPictureTime() {
        ArrayList<SelectDataBean> temp = new ArrayList<>();
        SelectDataBean b1 = new SelectDataBean();
        b1.setId("1");
        b1.setPositionName("1天");
        temp.add(b1);

        SelectDataBean b2 = new SelectDataBean();
        b2.setId("5");
        b2.setPositionName("5天");
        temp.add(b2);

        SelectDataBean b3 = new SelectDataBean();
        b3.setId("7");
        b3.setPositionName("7天");
        temp.add(b3);

        SelectDataBean b4 = new SelectDataBean();
        b4.setId("0");
        b4.setPositionName("不限");
        temp.add(b4);

        SelectDataBean b5 = new SelectDataBean();
        b5.setId("");
        b5.setPositionName("自定义");
        temp.add(b5);

        view.setListData(temp);
    }

    @Override
    public void getSettingPictureNum() {
        ArrayList<SelectDataBean> temp = new ArrayList<>();
        SelectDataBean b1 = new SelectDataBean();
        b1.setId("20");
        b1.setPositionName("20条");
        temp.add(b1);

        SelectDataBean b2 = new SelectDataBean();
        b2.setId("50");
        b2.setPositionName("50条");
        temp.add(b2);

        SelectDataBean b3 = new SelectDataBean();
        b3.setId("0");
        b3.setPositionName("不限");
        temp.add(b3);

        SelectDataBean b4 = new SelectDataBean();
        b4.setId("");
        b4.setPositionName("自定义");
        temp.add(b4);

        view.setListData(temp);
    }

    @Override
    public void getVisitType() {
        ArrayList<SelectDataBean> temp = new ArrayList<>();
        SelectDataBean b1 = new SelectDataBean();
        b1.setId("1");
        b1.setPositionName("图文问诊");
        temp.add(b1);

        SelectDataBean b2 = new SelectDataBean();
        b2.setId("2");
        b2.setPositionName("语音问诊");
        temp.add(b2);

        SelectDataBean b3 = new SelectDataBean();
        b3.setId("3");
        b3.setPositionName("视频问诊");
        temp.add(b3);

        SelectDataBean b4 = new SelectDataBean();
        b4.setId("4");
        b4.setPositionName("预约面诊");
        temp.add(b4);

        view.setListData(temp);
    }
}
