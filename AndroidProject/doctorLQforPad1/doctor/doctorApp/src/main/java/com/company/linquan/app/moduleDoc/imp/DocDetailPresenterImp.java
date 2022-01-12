package com.company.linquan.app.moduleDoc.imp;

import android.util.Log;

import com.company.linquan.app.http.HttpApi;
import com.company.linquan.app.http.JSONDocDetail;
import com.company.linquan.app.moduleDoc.DocInterface;
import com.company.linquan.app.util.ToolUtil;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by YC on 2018/8/1.
 */

public class DocDetailPresenterImp implements DocInterface.DocDetailPresenterInterFace {

    private DocInterface.DocDetailInterFace view;
    public DocDetailPresenterImp(DocInterface.DocDetailInterFace view) {
        this.view = view;
    }

    @Override
    public void getData(String id) {
        Map<String,String> map = new HashMap<>();
        map.put("writingsID", id);
        map.put("sign", ToolUtil.getSign(map));
        HttpApi.getWritingsDetail(map.get("writingsID"),map.get("sign"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JSONDocDetail>(){
                    @Override
                    public void onCompleted() {
                        Log.i("onCompleted","onCompleted");
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.i("Throwable",e.toString());
                    }
                    @Override
                    public void onNext(JSONDocDetail result) {
                        Log.i("onNext","onNext");
                        //填充UI
                        if ("1".equals(result.getCode())){
                            view.reloadView(result.getInfoJson());
                        }else {
                            view.showToast(result.getMsgBox());
                        }
                    }
                });
    }
}
