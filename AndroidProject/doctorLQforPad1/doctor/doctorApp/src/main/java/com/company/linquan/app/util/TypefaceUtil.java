package com.company.linquan.app.util;

import android.content.Context;
import android.graphics.Typeface;

public class TypefaceUtil {

	private static TypefaceUtil instance;
	private Typeface typeface_w3, typeface_w6;
	
	private TypefaceUtil(Context context){
		initTypeface(context);
	}
	
	public static void initTypefaceUtil(Context context){
		TypefaceUtil.getInstance(context);
	}
	
	public static TypefaceUtil getInstance(Context context){
		if(instance == null){
			instance = new TypefaceUtil(context);
		}
		return instance;
	}
	
	private void initTypeface(Context context){
		typeface_w3 = Typeface.createFromAsset(context.getAssets(),"w3.otf");
		typeface_w6 = Typeface.createFromAsset(context.getAssets(),"w3.otf");
	}
	
	public Typeface getTypeface(int index){
		if(index == 0){
			return typeface_w3;
		}
		if(index == 1){
			return typeface_w6;
		}
		return typeface_w3;
	}
}
