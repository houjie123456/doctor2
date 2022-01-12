package com.company.linquan.app.util;

import android.util.Log;

import com.company.linquan.app.config.C;


public class LogUtil {

	private static final String TAG  = "esegoo";
	/**
	 * 测试环境打印log日志
	 * @param msg
	 */
	public static void showLog(String msg){
		if(C.isDebug){
			Log.i(TAG, msg);
		}
	}
}
