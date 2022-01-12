package com.company.linquan.app.moduleWork;

import android.util.Log;
import android.util.SparseArray;

import com.company.linquan.app.R;
import com.company.linquan.app.bean.UserInfoBean;
import com.company.linquan.app.config.C;
import com.company.linquan.app.http.HttpApi;
import com.company.linquan.app.http.JSONBean;
import com.company.linquan.app.util.ToolSharePerference;
import com.company.linquan.app.util.ToolUtil;

import java.util.HashMap;
import java.util.Map;

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

//        array = new SparseArray<>();
        if(UserInfoBean.getInstance().getIdType().equals("1")){
            SparseArray<String> s1 = new SparseArray<>();
            s1.put(0,"护理管理");
            s1.put(1,R.drawable.img_work_face+"");
            array.put(0,s1);

            SparseArray<String> s2 = new SparseArray<>();
            s2.put(0,"我的患者");
            s2.put(1,R.drawable.img_work_patient+"");
            array.put(1,s2);
        }else{
            SparseArray<String> s1 = new SparseArray<>();
            s1.put(0,"面诊管理");
            s1.put(1, R.drawable.img_work_face+"");
            array.put(0,s1);

            SparseArray<String> s2 = new SparseArray<>();
            s2.put(0,"图文问诊");
            s2.put(1,R.drawable.img_work_inquery+"");
            array.put(1,s2);

            SparseArray<String> s3 = new SparseArray<>();
            s3.put(0,"电话问诊");
            s3.put(1,R.drawable.img_work_voice+"");
            array.put(2,s3);

            SparseArray<String> s4 = new SparseArray<>();
            s4.put(0,"视频问诊");
            s4.put(1,R.drawable.img_work_video+"");
            array.put(3,s4);


            SparseArray<String> s5 = new SparseArray<>();
            s5.put(0,"开处方");
            s5.put(1,R.drawable.img_work_receipe+"");
            array.put(4,s5);

            SparseArray<String> s6 = new SparseArray<>();
            s6.put(0,"转诊管理");
            s6.put(1,R.drawable.img_work_change+"");
            array.put(5,s6);

            SparseArray<String> s7 = new SparseArray<>();
            s7.put(0,"慢病管理");
            s7.put(1,R.drawable.img_work_slow+"");
            array.put(6,s7);

//        SparseArray<String> s8 = new SparseArray<>();
//        s8.put(0,"名医团");
//        s8.put(1,R.drawable.img_work_docters+"");
//        array.put(5,s8);

            SparseArray<String> s9 = new SparseArray<>();
            s9.put(0,"我的患者");
            s9.put(1,R.drawable.img_work_patient+"");
            array.put(7,s9);

            SparseArray<String> s10 = new SparseArray<>();
            s10.put(0,"任务中心");
            s10.put(1,R.drawable.img_work_task+"");
            array.put(8,s10);
        }




        view.reloadGrid(array);
    }

    @Override
    public void getTeamStatus() {
        view.showDialog();
        Map<String,String> map = new HashMap<>();
        map.put("personID", ToolSharePerference.getStringData(view.getContext(), C.fileconfig,C.UserID));
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
                        if ("1".equals(result.getCode())){
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
