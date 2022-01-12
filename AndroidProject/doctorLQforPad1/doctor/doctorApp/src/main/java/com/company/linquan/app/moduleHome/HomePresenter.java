package com.company.linquan.app.moduleHome;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.company.linquan.app.config.C;
import com.company.linquan.app.http.HttpApi;
import com.company.linquan.app.http.JSONBean;
import com.company.linquan.app.util.DownloadApp;
import com.company.linquan.app.util.DownloadFileHelper;
import com.company.linquan.app.util.InstallApk;
import com.company.linquan.app.util.ToolSharePerference;

import java.io.File;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by YC on 2017/8/11.
 */

public class HomePresenter implements HomeInterface.HomeInterfacePresenter {

    private HomeInterface.HomeInterfaceView view;
    HomeActivity activity;

    public HomePresenter(HomeInterface.HomeInterfaceView view){
        this.view = view;
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    new InstallApk(activity)
                            .installApk(new File(Environment.getExternalStorageDirectory(), "your_app_name.apk"));
                    break;
            }

        }
    };
    @Override
    public void getVersion() {
        HttpApi.getAppVersionInfo()
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
//                                        DownloadFileHelper downloadFileHelper =new DownloadFileHelper(view.getContext(),handler);
//                                        downloadFileHelper.downFile(appurl);
                                    }
                                }else{
//                                    view.showToast("当前版本已经是最新版本");
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
