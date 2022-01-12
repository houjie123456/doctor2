//package com.company.wanbei.app.moduleDoc;
//
//import android.util.Log;
//
//import com.company.wanbei.app.bean.DocBean;
//import com.company.wanbei.app.http.HttpApi;
//import com.company.wanbei.app.http.JSONDoc;
//import com.tencent.qcloud.tuikit.tuichat.fromApp.util.ToolUtil;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//
//import rx.Subscriber;
//import rx.android.schedulers.AndroidSchedulers;
//import rx.schedulers.Schedulers;
//
///**
// * Created by YC on 2018/8/1.
// */
//
//public class DocPresenter implements DocInterface.DocPresenterInterFace{
//
//    private DocInterface.DocInterFace view;
//
//    public DocPresenter(DocInterface.DocInterFace view) {
//        this.view = view;
//    }
//
//    @Override
//    public void getListData(int page) {
//        Map<String,String> map = new HashMap<>();
//        map.put("personID", "");
//        map.put("title", "");
//        map.put("currPage", page+"");
//        map.put("sign", ToolUtil.getSign(map));
//        HttpApi.getWritingsInfo(map.get("personID"),map.get("title"),map.get("currPage"),map.get("sign"))
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<JSONDoc>(){
//                    @Override
//                    public void onCompleted() {
//                        Log.i("onCompleted","onCompleted");
//                    }
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.i("Throwable",e.toString());
//                    }
//                    @Override
//                    public void onNext(JSONDoc result) {
//                        Log.i("onNext","onNext");
//                        //填充UI
//                        if ("0".equals(result.getCode())){
//                            view.reloadList(result.getTable());
//                        }else {
//                            view.showToast(result.getMsgBox());
//                            view.reloadList(new ArrayList<DocBean>());
//                        }
//                    }
//                });
//    }
//}
