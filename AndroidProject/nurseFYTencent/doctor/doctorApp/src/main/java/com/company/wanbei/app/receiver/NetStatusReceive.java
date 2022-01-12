package com.company.wanbei.app.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.tencent.qcloud.tuikit.tuichat.fromApp.config.C;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.ToolSharePerference;


/**
 * 监听网络状态变化
 * @author Work
 *
 */
public class NetStatusReceive extends BroadcastReceiver {

	public static NetStatusReceive instance;
	private HandlerNetListener handlerNetListener;

	public NetStatusReceive(){
	}

	/**
	 * 单例模式
	 * @return
	 */
	public static NetStatusReceive getInstance(){
		if(instance == null){
			instance = new NetStatusReceive();
		}
		return instance;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action)) {
			ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo activeInfo = manager.getActiveNetworkInfo();
			if(activeInfo != null){
				ToolSharePerference.putBooleanData(context, C.fileconfig, C.IsNetOK, true);
			}else{
				ToolSharePerference.putBooleanData(context, C.fileconfig, C.IsNetOK, false);
			}
			if(handlerNetListener != null){
				handlerNetListener.onChangeNetStatus();
			}
		}
	}

	public void setOnHandleNetListener(HandlerNetListener listener){
		this.handlerNetListener = listener;
	}
}
