package com.tencent.qcloud.tuikit.tuichat.fromApp.util;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MyToast{
	/**
	 * 显示带图片的Toast
	 * @param context
	 * @param imageID
	 * @param txt
	 * @param duration
	 */
	public static void showImageToast(Context context, int imageID, String txt, int duration){
		Toast toast = Toast.makeText(context, txt, duration);
		toast.setGravity(Gravity.CENTER, 0, 0);
		LinearLayout toastView = (LinearLayout) toast.getView();
		ImageView imageCodeProject = new ImageView(context);
		imageCodeProject.setImageResource(imageID);
		toastView.addView(imageCodeProject, 0);
		toast.show();
	}

	/**
	 * 显示自定义的Toast
	 * @param context
	 * @param txt
	 * @param duration
	 */
	public static void showViewToast(Activity context, String txt, int duration){
//		LayoutInflater inflater = context.getLayoutInflater();
//		View layout = inflater.inflate(R.layout.custom,(ViewGroup)findViewById(R.id.llToast));
//		ImageView image = (ImageView) layout.findViewById(R.id.tvImageToast);
//		image.setImageResource(R.drawable.icon);
//		TextView title = (TextView) layout.findViewById(R.id.tvTitleToast);
//		title.setText("Attention");
//		TextView text = (TextView) layout.findViewById(R.id.tvTextToast);
//		text.setText("完全自定义Toast");
//		Toast toast = new Toast(context);
//		toast.setGravity(Gravity.CENTER,0,0);
//		toast.setDuration(duration);
//		toast.setView(layout);
//		toast.show();
	}

	/**
	 * 显示带图片的Toast
	 * @param context
	 * @param txt
	 * @param duration
	 */
	public static void showToastOffNet(Context context, String txt, int duration){
		if(judgeNet(context)){// 有网再弹出
			Toast toast = Toast.makeText(context, txt, duration);
			toast.show();
		}
	}

	/**
	 * 显示带图片的Toast
	 * @param context
	 * @param txt
	 * @param duration
	 */
	public static void showToast(Context context, String txt, int duration){
		Toast toast = Toast.makeText(context, txt, duration);
		toast.show();
	}

	/**
	 * 判断网络
	 * @return
	 */
	private static boolean judgeNet(Context context){
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeInfo = manager.getActiveNetworkInfo();
		if(activeInfo != null){
			return true;
		}else{
			return false;
		}
	}
}
