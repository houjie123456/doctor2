package com.company.linquan.app.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.company.linquan.app.util.TypefaceUtil;


public class MyTextView extends TextView {

	private int index;
	private Context context;

	public MyTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		setTypeface(context, index);
	}

	public MyTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		setTypeface(context, index);
	}

	public MyTextView(Context context) {
		super(context);
		this.context = context;
		setTypeface(context, index);
	}

	public void setIndex(int index) {
		setTypeface(this.context, index);
	}

	/**
	 * 设置字体
	 *
	 * @param context
	 */
	private void setTypeface(Context context, int index) {
		setTypeface(TypefaceUtil.getInstance(context).getTypeface(index));
	}
}