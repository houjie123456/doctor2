package com.company.wanbei.app.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;

import com.company.wanbei.app.BuildConfig;

import java.io.File;

//import android.support.annotation.RequiresApi;
//import android.support.v4.BuildConfig;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.content.FileProvider;

public class InstallApk {

    Activity context;
    final int INSTALL_PERMISS_CODE=1;

    public InstallApk(Activity context) {
        this.context = context;
    }

    public void installApk(File apkFile) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            boolean b = context.getPackageManager().canRequestPackageInstalls();
            if (b) {
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                Uri contentUri = FileProvider.getUriForFile(context.getApplicationContext(),
                        BuildConfig.APPLICATION_ID+".fileProvider", apkFile);

                intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
                context.startActivity(intent);
            } else {
                //请求安装未知应用来源的权限
                ConfirmDialog confirmDialog =new ConfirmDialog(context,"Android8.0及以上安装应用需要打开未\n知来源权限，请去设置中开启权限",
                        "去设置","取消");
                confirmDialog.setClicklistener(new ConfirmDialog.ClickListenerInterface() {
                    @Override
                    public void doConfirm() {
//                        String[] mPermissionList = new String[]{Manifest.permission.REQUEST_INSTALL_PACKAGES};
//                        ActivityCompat.requestPermissions(context, mPermissionList, 2);
                            startInstallPermissionSettingActivity();
                    }

                    @Override
                    public void doCancel() {

                    }
                });
                confirmDialog.show();
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Uri contentUri = FileProvider.getUriForFile(context.getApplicationContext(),
                        BuildConfig.APPLICATION_ID+".fileProvider", apkFile);
                intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
                context.startActivity(intent);
            } else {
                intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivityForResult(intent,INSTALL_PERMISS_CODE);
            }
        }

    }
    /**
     * 跳转到设置-允许安装未知来源-页面
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startInstallPermissionSettingActivity() { //注意这个是8.0新API
        Uri packageURI = Uri.parse("package:" + context.getPackageName());
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES,packageURI);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


}
