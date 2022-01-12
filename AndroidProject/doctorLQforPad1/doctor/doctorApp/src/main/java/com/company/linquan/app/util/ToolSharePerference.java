package com.company.linquan.app.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashSet;
import java.util.Set;

public class ToolSharePerference {
	
	public static boolean putStringData(Context context, String name,String key, String value){
		SharedPreferences sharedPreferences = context.getSharedPreferences(name, 0);
		Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		return editor.commit();
	}
	public static String getStringData(Context context, String name, String key){
		SharedPreferences sharedPreferences = context.getSharedPreferences(name, 0);
		return sharedPreferences.getString(key, "");
	}
	
	public static boolean putBooleanData(Context context,String name, String key, boolean value){
		SharedPreferences sharedPreferences = context.getSharedPreferences(name, 0);
		Editor editor = sharedPreferences.edit();
		editor.putBoolean(key, value);
		return editor.commit();
	}
	
	public static boolean getBooleanData(Context context, String name, String key){
		SharedPreferences sharedPreferences = context.getSharedPreferences(name, 0);
		return sharedPreferences.getBoolean(key,false);
	}
	
	public static boolean putIntData(Context context,String name, String key, int value){
		SharedPreferences sharedPreferences = context.getSharedPreferences(name, 0);
		Editor editor = sharedPreferences.edit();
		editor.putInt(key, value);
		return editor.commit();
	}
	
	public static int getIntData(Context context, String name, String key){
		SharedPreferences sharedPreferences = context.getSharedPreferences(name, 0);
		return sharedPreferences.getInt(key,0);
	}
	
	public static boolean putStringSetData(Context context,String name, String key, Set<String> value){
		SharedPreferences sharedPreferences = context.getSharedPreferences(name, 0);
		Editor editor = sharedPreferences.edit();
		editor.putStringSet(key, value);
		return editor.commit();
	}
	
	public static Set<String> getStringSetData(Context context, String name, String key){
		SharedPreferences sharedPreferences = context.getSharedPreferences(name, 0);
		return sharedPreferences.getStringSet(key, new HashSet<String>());
	}
}
