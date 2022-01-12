package com.company.linquan.app.util;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import com.company.linquan.app.R;
import com.company.linquan.app.view.MyTextView;


/**
 * Dialog 各种显示样式
 * @author YC
 *
 */
public class MyDialog {

	/**
	 * 显示加载数据对话框
	 * @return
	 */
	public static Dialog showLoadingDialog(Context context){
		final Dialog mDialog = new Dialog(context, R.style.custom_dialog);
		View mDialogContentView= LayoutInflater.from(context).inflate(R.layout.base_dialog_loading_layout,null);
		mDialog.setContentView(mDialogContentView);
		mDialog.setCanceledOnTouchOutside(false);
		mDialog.setCancelable(false);
		mDialog.getWindow().setGravity(Gravity.CENTER);
		mDialog.show();
		return mDialog;
	}


	/**
	 * 显示加载数据对话框
	 * @return
	 */
	public static Dialog showErrorDialog(Context context, String msg){
		final Dialog mDialog = new Dialog(context, R.style.custom_dialog);
		View mDialogContentView= LayoutInflater.from(context).inflate(R.layout.dialog_item_error,null);
		MyTextView txtTV = (MyTextView)mDialogContentView.findViewById(R.id.dialog_item_txt);
		txtTV.setText(msg);
		MyTextView sureTV = (MyTextView)mDialogContentView.findViewById(R.id.dialog_item_sure);
		sureTV.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mDialog.dismiss();
			}
		});

		mDialog.setContentView(mDialogContentView);
		mDialog.setCanceledOnTouchOutside(false);
		mDialog.setCancelable(false);
		mDialog.getWindow().setGravity(Gravity.CENTER);
		mDialog.show();
		mDialog.getWindow().setWindowAnimations(R.style.anim_dialog);
		return mDialog;
	}

	/**
	 * 系统弹框（兼容2.1以上 Material 风格的 Dialog）
	 * @param context
	 * @param msg
     */
	public static void showSystemDialog(Context context, String msg){
		AlertDialog builder = new AlertDialog.Builder(context,R.style.custom_dialog).create();
		builder.setMessage(msg);
		builder.setTitle("提示");
		builder.setCancelable(false);
		builder.setButton(AlertDialog.BUTTON_POSITIVE,"确定",new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				dialogInterface.dismiss();
			}
		});
		builder.getWindow().setGravity(Gravity.CENTER);
		builder.show();
	}
}
