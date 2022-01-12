package com.company.wanbei.app.util;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;

import com.tencent.qcloud.tuikit.tuichat.fromApp.config.C;
import com.company.wanbei.app.moduleHome.HomeActivity;
import com.company.wanbei.app.R;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.ToolSharePerference;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import kr.co.namee.permissiongen.PermissionGen;

//import android.support.annotation.RequiresApi;
//import android.support.v4.content.FileProvider;

public class DownloadApp {
	/* 下载中 */
	private static final int DOWNLOAD = 1;
	/* 下载结束 */
	private static final int DOWNLOAD_FINISH = 2;
	/* 保存解析的XML信息 */
	HashMap<String, String> mHashMap;
	/* 下载保存路径 */
	private String mSavePath;
	/* 记录进度条数量 */
	private int progress;
	/* 是否取消更新 */
	private boolean cancelUpdate = false;

	/* 无内存卡情况 */
	public static final String RES_LOAD_FOLDER = File.separator + "data" + File.separator + "data"
			+ File.separator + "lab.sodino.downloadbreak" + File.separator + "apps"
			+ File.separator;

	private Context mContext;

	private Activity activity;
	/* 更新进度条 */
	private ProgressBar mProgress;
	private Dialog mDownloadDialog;
	private TextView downloadTV;
	private boolean isMust;
	private boolean isFinish;
	private boolean downloading;

	private Handler mHandler = new Handler()
	{
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
				// 正在下载
				case DOWNLOAD:
					// 设置进度条位置
//					if(isMust){
						downloading = true;
						mProgress.setVisibility(View.VISIBLE);
						mProgress.setProgress(progress);
						downloadTV.setText("已下载"+progress+"%");
//					}
					break;
				case DOWNLOAD_FINISH:
					// 安装文件
//					if(isMust){
						downloading = false;
						isFinish = true;
						mProgress.setVisibility(View.GONE);
						downloadTV.setText("准备安装");
//					}
					installApk();
					break;
				case 3:
					// 安装文件
//				Toast.makeText(mContext, "无SD卡！", Toast.LENGTH_SHORT).show();
					break;
				default:
					break;
			}
		};
	};

	public DownloadApp(Context context,Activity activity){
		this.mContext = context;
		this.activity = activity;
	}


	HomeActivity homeActivity;
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
				case 0:
					new InstallApk((Activity) mContext)
							.installApk(new File(Environment.getExternalStorageDirectory(), "your_app_name.apk"));
					break;
			}

		}
	};
	/**
	 * 显示下载更新窗口
	 * @param title 新版本号
	 * @param content 更新内容
	 * @param config 是否强制更新
	 */
	public void DownloadDialog(String title,String content,Boolean config){
		isMust = config;
		final Dialog mDialog = new Dialog(mContext, R.style.custom_dialog);
		View mDialogContentView = LayoutInflater.from(mContext).inflate(R.layout.base_download_dialog_item,null);
		TextView titleTV = (TextView)mDialogContentView.findViewById(R.id.dialog_item_title);
		EditText contentTV = (EditText)mDialogContentView.findViewById(R.id.dialog_item_content);
		TextView cancelTV = (TextView)mDialogContentView.findViewById(R.id.dialog_item_cancel);
		downloadTV = (TextView)mDialogContentView.findViewById(R.id.dialog_item_download);
		mProgress = (ProgressBar)mDialogContentView.findViewById(R.id.dialog_item_progress);

		titleTV.setText("软件更新");
		contentTV.setText("发现新版本，为了您的更好体验，请下载更新");
		contentTV.append(content);

		if(config){//强制更新
			cancelTV.setVisibility(View.GONE);
		}else{//非强制更新
			cancelTV.setVisibility(View.VISIBLE);
		}

		mDialog.setContentView(mDialogContentView);
		mDialog.setCanceledOnTouchOutside(false);
		mDialog.setCancelable(false);
		mDialog.getWindow().setGravity(Gravity.CENTER);

		Window dialogWindow = mDialog.getWindow();
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		lp.width = (mContext.getResources().getDisplayMetrics().widthPixels *3)/4; // 宽度
		dialogWindow.setAttributes(lp);
		mDialog.show();

		cancelTV.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (downloading){
					cancelUpdate = true;
				}
				mDialog.dismiss();
			}
		});

		downloadTV.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				if(!isMust){//非强制更新
//					mDialog.dismiss();
//				}


				if(downloading){
					return;
				}

				if(isFinish){
					installApk();
					return;
				}
				if (Build.VERSION.SDK_INT>22){
					PermissionGen.with((Activity) mContext)
							.addRequestCode(10)
							.permissions(Manifest.permission.READ_EXTERNAL_STORAGE
									,Manifest.permission.WRITE_EXTERNAL_STORAGE)
							.request();

				}
				Toast.makeText(mContext, "正在下载", Toast.LENGTH_SHORT).show();
				downloadApk();
//				String appurl = ToolSharePerference.getStringData(mContext, C.fileconfig, C.AppUrl);
//				DownloadFileHelper downloadFileHelper =new DownloadFileHelper(mContext,handler);
//                downloadFileHelper.downFile(appurl);

			}
		});

	}

	/**
	 * 显示软件下载对话框
	 */
	private void showDownloadDialog(){
		// 构造软件下载对话框
		Builder builder = new Builder(mContext);
		builder.setCancelable(false);
//		builder.setTitle("正在下载");
		// 给下载对话框增加进度条
		final LayoutInflater inflater = LayoutInflater.from(mContext);
		View v = inflater.inflate(R.layout.base_itools_softupdate_progress, null);
		mProgress = (ProgressBar) v.findViewById(R.id.update_progress);
		downloadTV = (TextView) v.findViewById(R.id.update_txt);
		builder.setView(v);
		builder.setMessage("正在下载线下支付插件！安装完成，软件才能正常使用！");
		mDownloadDialog = builder.create();
		mDownloadDialog.show();
		// 现在文件
		downloadApk();
	}

	/**
	 * 下载apk文件
	 */
	private void downloadApk(){
		// 启动新线程下载软件
		new downloadApkThread().start();
	}

	/**
	 * 下载文件线程
	 *
	 * @author coolszy
	 *@date 2012-4-26
	 *@blog http://blog.92coding.com
	 */
	private class downloadApkThread extends Thread
	{
		@Override
		public void run()
		{
			try
			{
				// 判断SD卡是否存在，并且是否具有读写权限
				if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
				{
					// 获得存储卡的路径
					String sdpath = Environment.getExternalStorageDirectory() + "/";
					mSavePath = sdpath + "download";
					String s = ToolSharePerference.getStringData(mContext, C.fileconfig, C.AppUrl);
					URL url = new URL(ToolSharePerference.getStringData(mContext, C.fileconfig, C.AppUrl));
					// 创建连接
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.connect();
					// 获取文件大小
					int length = conn.getContentLength();
					// 创建输入流
					InputStream is = conn.getInputStream();

					File file = new File(mSavePath);
					// 判断文件目录是否存在
					if (!file.exists())
					{
						file.mkdir();
					}
					File apkFile = new File(mSavePath, "apk"+ ToolSharePerference.getStringData(mContext, C.fileconfig, C.AppVersion)+".apk");
					FileOutputStream fos = new FileOutputStream(apkFile);
					int count = 0;
					// 缓存
					byte buf[] = new byte[1024];
					// 写入到文件中
					do
					{
						int numread = is.read(buf);
						count += numread;
						// 计算进度条位置
						progress = (int) (((float) count / length) * 100);
						// 更新进度
						mHandler.sendEmptyMessage(DOWNLOAD);
						if (numread <= 0)
						{
							// 下载完成
							mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
							break;
						}
						if (cancelUpdate){
							return;
						}
						// 写入文件
						fos.write(buf, 0, numread);
					} while (!cancelUpdate);// 点击取消就停止下载.
					fos.close();
					is.close();
				}else{

					mHandler.sendEmptyMessage(3);
					mSavePath = mContext.getFilesDir().toString();
					String[] args1 = { "chmod", "705", mSavePath };
					exec(args1);
					URL url = new URL(ToolSharePerference.getStringData(mContext, C.fileconfig, C.AppUrl));
					// 创建连接
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.connect();
					// 获取文件大小
					int length = conn.getContentLength();
					// 创建输入流
					InputStream is = conn.getInputStream();

					File file = new File(mSavePath);
					// 判断文件目录是否存在
					if (!file.exists())
					{
						file.mkdir();
					}
					File apkFile = new File(mSavePath, "apk"+ ToolSharePerference.getStringData(mContext, C.fileconfig, C.AppVersion)+".apk");
					FileOutputStream fos = new FileOutputStream(apkFile);
					int count = 0;
					// 缓存
					byte buf[] = new byte[1024];
					// 写入到文件中
					do
					{
						int numread = is.read(buf);
						count += numread;
						// 计算进度条位置
						progress = (int) (((float) count / length) * 100);
						// 更新进度
						mHandler.sendEmptyMessage(DOWNLOAD);
						if (numread <= 0)
						{
							// 下载完成
							String[] args2 = { "chmod", "604", mSavePath +"/"+ "apk"+ ToolSharePerference.getStringData(mContext, C.fileconfig, C.AppVersion)+".apk" };
							exec(args2);
							mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
							break;
						}
						// 写入文件
						fos.write(buf, 0, numread);
					} while (!cancelUpdate);// 点击取消就停止下载.
					fos.close();
					is.close();
				}
			} catch (MalformedURLException e)
			{
				e.printStackTrace();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			// 取消下载对话框显示
//			mDownloadDialog.dismiss();
		}
	};

	/**
	 * 安装APK文件
	 */
	private void installApk(){
		File apkfile = new File(mSavePath, "apk"+ ToolSharePerference.getStringData(mContext, C.fileconfig, C.AppVersion)+".apk");
		if (!apkfile.exists()){
			return;
		}
		// 通过Intent安装APK文件
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//没有最后不会打开新APK
		//判读版本是否在7.0以上
		if (Build.VERSION.SDK_INT >= 24) {

			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
				boolean hasInstallPermission = mContext.getPackageManager().canRequestPackageInstalls();
				if (!hasInstallPermission) {
					startInstallPermissionSettingActivity();
				}
			}

			Uri apkUri = FileProvider.getUriForFile(mContext,"com.company.wanbei.app.fileProvider", apkfile);//通过FileProvider创建一个content类型的Uri
			//Granting Temporary Permissions to a URI
			i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
			i.setDataAndType(apkUri, "application/vnd.android.package-archive");


		} else {
			i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
		}
		mContext.startActivity(i);
		android.os.Process.killProcess(android.os.Process.myPid());//没有最后不会提示完成、打开
	}
	/**
	 * 跳转到设置-允许安装未知来源-页面
	 */
	@RequiresApi(api = Build.VERSION_CODES.O)
	private void startInstallPermissionSettingActivity() { //注意这个是8.0新API
		Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		mContext.startActivity(intent);
	}

	/***
	 * 向通知中心发送通知
	 *
	 * @param id
	 * @param title
	 * @param text
	 */
	public void setNotifyMsg(int id, String title, String text) {
//		NotificationManager nm = (NotificationManager)mContext.getSystemService(Context.NOTIFICATION_SERVICE);
//		Notification notify = new Notification(R.drawable.icon, "新的新闻资讯",
//				System.currentTimeMillis());
//		notify.defaults |= Notification.DEFAULT_SOUND;
//		notify.flags = Notification.FLAG_AUTO_CANCEL;
//		Intent i = new Intent(mContext, MX_Activity_Cover.class);
//		i.putExtra("isInMain", true);
//		i.putExtra("push", true);
//		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
//				| Intent.FLAG_ACTIVITY_NEW_TASK);
//		PendingIntent contentIntent = PendingIntent.getActivity(
//				mContext, R.string.app_name, i,
//				PendingIntent.FLAG_UPDATE_CURRENT);
//		// Use Notification.Builder instead
//		// Notification.Builder.
//		notify.setLatestEventInfo(mContext, title, text,
//				contentIntent);
//		nm.notify(id, notify);
	}

	/** 执行Linux命令，并返回执行结果。 */
	public static String exec(String[] args) {
		String result = "";
		ProcessBuilder processBuilder = new ProcessBuilder(args);
		Process process = null;
		InputStream errIs = null;
		InputStream inIs = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int read = -1;
			process = processBuilder.start();
			errIs = process.getErrorStream();
			while ((read = errIs.read()) != -1) {
				baos.write(read);
			}
//            baos.write('/n');
			inIs = process.getInputStream();
			while ((read = inIs.read()) != -1) {
				baos.write(read);
			}
			byte[] data = baos.toByteArray();
			result = new String(data);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (errIs != null) {
					errIs.close();
				}
				if (inIs != null) {
					inIs.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (process != null) {
				process.destroy();
			}
		}
		return result;
	}
}
