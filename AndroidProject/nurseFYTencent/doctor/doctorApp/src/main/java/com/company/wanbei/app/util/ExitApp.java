package com.company.wanbei.app.util;

import android.app.Activity;
import android.app.Application;

import java.util.LinkedList;
import java.util.List;

public class ExitApp extends Application {

	private List<Activity> activityList = new LinkedList<Activity>();

	private static ExitApp instance;

	private ExitApp() {
	}

	// 单例模式中获取唯一的ExitApplication 实例
	public static ExitApp getInstance() {
		if (null == instance) {
			instance = new ExitApp();
		}
		return instance;

	}

	// 添加Activity 到容器中
	public void addActivity(Activity activity) {
		activityList.add(activity);
	}

	// 遍历所有Activity 并finish
	public void exit() {

		for (Activity activity : activityList) {
			activity.finish();
		}
		System.exit(0);
	}

	/**
	 * 清除后面index个Activity
	 * @param index
	 */
	public void exit(int index) {
		int size = activityList.size();
		if(index<=size){
			for (int i = size-index; i < size; i++) {
				activityList.get(i).finish();
			}
		}
	}
}