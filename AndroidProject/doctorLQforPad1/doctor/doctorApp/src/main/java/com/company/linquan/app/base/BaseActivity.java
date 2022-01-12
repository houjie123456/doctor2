package com.company.linquan.app.base;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.company.linquan.app.R;
import com.company.linquan.app.config.C;
import com.company.linquan.app.util.ToolSharePerference;
import com.company.linquan.app.view.MyTextView;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.lang.reflect.Field;


public class BaseActivity extends AppCompatActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//设置状态栏文字颜色及图标为深色，当状态栏为白色时候，改变其颜色为深色，简单粗暴直接完事
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
	}
//ceshi
	protected void onResume() {
		super.onResume();
//		JPushInterface.onResume(this);
	}

	protected void onPause() {
		super.onPause();
//		JPushInterface.onPause(this);
	}

	public void setTitleStatus(int statusBar, int navBar){
		if(Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT){
			return;
		}
		SystemBarTintManager tintManager = new SystemBarTintManager(this);
		// 激活状态栏
		tintManager.setStatusBarTintEnabled(true);
		// enable navigation bar tint 激活导航栏
		tintManager.setNavigationBarTintEnabled(true);
		//设置系统栏设置颜色
		//tintManager.setTintColor(R.color.red);
		if (statusBar != 0){
			//给状态栏设置颜色
			tintManager.setStatusBarTintResource(statusBar);
		}
		if (navBar != 0){
			//Apply the specified drawable or color resource to the system navigation bar.
			//给导航栏设置资源
			tintManager.setNavigationBarTintResource(navBar);
		}
	}

	/**
	 * 通过反射的方式获取状态栏高度
	 * @return
	 */
	public int getStatusBarHeight() {
		try {
			Class<?> c = Class.forName("com.android.internal.R$dimen");
			Object obj = c.newInstance();
			Field field = c.getField("status_bar_height");
			int x = Integer.parseInt(field.get(obj).toString());
			return getResources().getDimensionPixelSize(x);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public void isLogin(){
		if ("".equals(ToolSharePerference.getStringData(this, C.fileconfig,C.UserID)))
		{
			showTwoBtnDialog("检测到您还没有登录，请先登录！",true);
		}
	}

	/**
	 * 提示登录
	 */
	public void showTwoBtnDialog(String txt,boolean canCancel){
		final Dialog mDialog = new Dialog(this, R.style.custom_dialog);
		final View mDialogContentView= LayoutInflater.from(this).inflate(R.layout.dialog_item_error,null);

		MyTextView cameraTV = (MyTextView)mDialogContentView.findViewById(R.id.dialog_item_cancel);
		MyTextView albumTV = (MyTextView)mDialogContentView.findViewById(R.id.dialog_item_sure);

		MyTextView contentTV = (MyTextView)mDialogContentView.findViewById(R.id.dialog_item_txt);
		contentTV.setText(txt);

		cameraTV.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mDialog.dismiss();
				cancelBtnClick();
			}
		});

		albumTV.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mDialog.dismiss();
				sureBtnClick();
			}
		});

		mDialog.setContentView(mDialogContentView);
		if (canCancel){
			mDialog.setCanceledOnTouchOutside(true);
			mDialog.setCancelable(true);
		}else{
			mDialog.setCanceledOnTouchOutside(false);
			mDialog.setCancelable(false);
		}
		mDialog.getWindow().setGravity(Gravity.CENTER);
		WindowManager.LayoutParams p = mDialog.getWindow().getAttributes(); // 获取对话框当前的参数值
		p.width = (int) (getResources().getDisplayMetrics().widthPixels * 0.85); // 宽度设置为屏幕的0.85
		mDialog.getWindow().setAttributes(p);
		mDialog.show();
		mDialog.getWindow().setWindowAnimations(R.style.anim_dialog);
	}

	/**
	 * 提示登录
	 */
	public void showOneBtnDialog(String txt,boolean canCancel){
		final Dialog mDialog = new Dialog(this, R.style.custom_dialog);
		final View mDialogContentView= LayoutInflater.from(this).inflate(R.layout.dialog_item_error,null);

		MyTextView cameraTV = (MyTextView)mDialogContentView.findViewById(R.id.dialog_item_cancel);
		cameraTV.setVisibility(View.GONE);
		MyTextView albumTV = (MyTextView)mDialogContentView.findViewById(R.id.dialog_item_sure);

		MyTextView contentTV = (MyTextView)mDialogContentView.findViewById(R.id.dialog_item_txt);
		contentTV.setText(txt);

		cameraTV.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mDialog.dismiss();
				cancelBtnClick();
			}
		});

		albumTV.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mDialog.dismiss();
				sureBtnClick();
			}
		});

		mDialog.setContentView(mDialogContentView);
		if (canCancel){
			mDialog.setCanceledOnTouchOutside(true);
			mDialog.setCancelable(true);
		}else{
			mDialog.setCanceledOnTouchOutside(false);
			mDialog.setCancelable(false);
		}
		mDialog.getWindow().setGravity(Gravity.CENTER);
		WindowManager.LayoutParams p = mDialog.getWindow().getAttributes(); // 获取对话框当前的参数值
		p.width = (int) (getResources().getDisplayMetrics().widthPixels * 0.85); // 宽度设置为屏幕的0.85
		mDialog.getWindow().setAttributes(p);
		mDialog.show();
		mDialog.getWindow().setWindowAnimations(R.style.anim_dialog);
	}


	public void sureBtnClick(){

	}

	public void cancelBtnClick(){

	}

	//隐藏虚拟按键：
	protected void hideBottomUIMenu() {
		//隐藏虚拟按键，并且全屏
		if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
			View v = this.getWindow().getDecorView();
			v.setSystemUiVisibility(View.GONE);
		} else if (Build.VERSION.SDK_INT >= 19) {
			//for new api versions.
			View decorView = getWindow().getDecorView();
			int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
					| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_IMMERSIVE;
			decorView.setSystemUiVisibility(uiOptions);
		}
	}
}
