package com.company.wanbei.app.moduleMeeting;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.company.wanbei.app.http.HttpApi;
import com.company.wanbei.app.http.JSONArticle;
import com.company.wanbei.app.http.JSONArticleDetail;
import com.company.wanbei.app.http.JSONBanner;
import com.company.wanbei.app.http.JSONMeeting;
import com.company.wanbei.app.http.JSONMeetingDetail;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.ToolUtil;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MeetingPresenter implements MeetingInterface.PresenterInterface {

    private MeetingInterface.ViewInterface view;
    private MeetingInterface.MeetingDetailInterface view2;
    private MeetingInterface.ArticleDetailInterface view3;
    public MeetingPresenter(MeetingInterface.ViewInterface view) {
        this.view = view;
    }
    public MeetingPresenter(MeetingInterface.MeetingDetailInterface view) {
        this.view2 = view;
    }
    public MeetingPresenter(MeetingInterface.ArticleDetailInterface view) {
        this.view3 = view;
    }

    @Override
    public void getMeetingList(int status) {
        Map<String,String> map = new HashMap<>();
        map.put("status", String.valueOf(status));
        map.put("sign", ToolUtil.getSign2(map));
        String json = JSON.toJSONString(map);//map转String
        JSONObject jsonObject = JSON.parseObject(json);//String转json
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),jsonObject.toString());
        HttpApi.getMeetingHomeList(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JSONMeeting>(){
                    @Override
                    public void onCompleted() {
                        Log.i("onCompleted","onCompleted");
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.i("Throwable",e.toString());
                    }
                    @Override
                    public void onNext(JSONMeeting result) {
                        Log.i("onNext","onNext");
                        //填充UI
                        if ("0".equals(result.getCode())){
                            view.refreshList(status,result.getTable());
                        }else {
                            view.showToast(result.getMsgBox());
                        }
                    }
                });
    }

    @Override
    public void getMeetingListByPage(int status, int page, int pageSize) {
        Map<String,String> map = new HashMap<>();
        map.put("status", String.valueOf(status));
        map.put("page", String.valueOf(page));
        map.put("pageSize", String.valueOf(pageSize));
        map.put("sign", ToolUtil.getSign2(map));
        String json = JSON.toJSONString(map);//map转String
        JSONObject jsonObject = JSON.parseObject(json);//String转json
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),jsonObject.toString());
        HttpApi.getMeetingInfoList(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JSONMeeting>(){
                    @Override
                    public void onCompleted() {
                        Log.i("onCompleted","onCompleted");
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.i("Throwable",e.toString());
                    }
                    @Override
                    public void onNext(JSONMeeting result) {
                        Log.i("onNext","onNext");
                        //填充UI
                        if ("0".equals(result.getCode())){
                            view.refreshList(status,result.getTable());
                        }else {
                            view.showToast(result.getMsgBox());
                        }
                    }
                });

    }

    @Override
    public void getBanner() {
        HttpApi.getBanner()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JSONBanner>(){
                    @Override
                    public void onCompleted() {
                        Log.i("onCompleted","onCompleted");
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.i("Throwable",e.toString());
                    }
                    @Override
                    public void onNext(JSONBanner result) {
                        Log.i("onNext","onNext");
                        //填充UI
                        if ("0".equals(result.getCode())){
                            view.reloadBanner(result.getTable());
                        }else {
                            view.showToast(result.getMsgBox());
                        }
                    }
                });
    }

    @Override
    public void getMeetingDetail(String id) {
        Map<String,String> map = new HashMap<>();
        map.put("id", id);
        map.put("sign", ToolUtil.getSign2(map));
        String json = JSON.toJSONString(map);//map转String
        JSONObject jsonObject = JSON.parseObject(json);//String转json
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),jsonObject.toString());
        HttpApi.getMeetingDetail(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JSONMeetingDetail>(){
                    @Override
                    public void onCompleted() {
                        Log.i("onCompleted","onCompleted");
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.i("Throwable",e.toString());
                    }
                    @Override
                    public void onNext(JSONMeetingDetail result) {
                        Log.i("onNext","onNext");
                        //填充UI
                        if ("0".equals(result.getCode())){
                            view2.reloadView(result.getTable());
                        }else {
                            view2.showToast(result.getMsgBox());
                        }
                    }
                });
    }

    @Override
    public void getWritingsByPage(String writingsType, String page, String pageSize) {
        Map<String,String> map = new HashMap<>();
        map.put("writingsType", writingsType);
        map.put("page", page);
        map.put("pageSize", pageSize);
        map.put("sign", ToolUtil.getSign2(map));
        String json = JSON.toJSONString(map);//map转String
        JSONObject jsonObject = JSON.parseObject(json);//String转json
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),jsonObject.toString());
        HttpApi.getWritingsByPage(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JSONArticle>(){
                    @Override
                    public void onCompleted() {
                        Log.i("onCompleted","onCompleted");
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.i("Throwable",e.toString());
                    }
                    @Override
                    public void onNext(JSONArticle result) {
                        Log.i("onNext","onNext");
                        //填充UI
                        if ("0".equals(result.getCode())){
                            view.refreshArticle(result.getTable());
                        }else {
                            view.showToast(result.getMsgBox());
                        }
                    }
                });
    }

    @Override
    public void getWritingsDetail(String id) {
        Map<String,String> map = new HashMap<>();
        map.put("id", id);
        map.put("sign", ToolUtil.getSign2(map));
        String json = JSON.toJSONString(map);//map转String
        JSONObject jsonObject = JSON.parseObject(json);//String转json
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),jsonObject.toString());
        HttpApi.findWritingsDetail(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JSONArticleDetail>(){
                    @Override
                    public void onCompleted() {
                        Log.i("onCompleted","onCompleted");
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.i("Throwable",e.toString());
                    }
                    @Override
                    public void onNext(JSONArticleDetail result) {
                        Log.i("onNext","onNext");
                        //填充UI
                        if ("0".equals(result.getCode())){
                            view3.reloadView(result.getInfoJson());
                        }else {
                            view3.showToast(result.getMsgBox());
                        }
                    }
                });
    }

}
