package com.company.linquan.app.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.company.linquan.app.R;


/**
 * 带删除按钮的EditText
 * @author YC
 *
 */
public class DelEditText extends FrameLayout implements View.OnClickListener{

	private Context context;
	private EditText inputEditText;
	private ImageView clearImageView;
	private LayoutInflater layoutInflater;
	private View rootView;
	private String requestKey = "";
	private OnEditTextDataChanged onEditTextDataChanged;
	private boolean isHideDel;

	public DelEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		initView();
	}

	public DelEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initView();
	}

	public DelEditText(Context context) {
		super(context);
		this.context = context;
		initView();
	}

	private void initView() {
		if (context != null) {
			layoutInflater = LayoutInflater.from(this.context);
			rootView = layoutInflater.inflate(R.layout.del_edit_layout, null, false);
		}
		if (rootView != null) {
			this.addView(rootView);
			clearImageView = (ImageView)rootView.findViewById(R.id.search_bar_clear_iv );
			clearImageView.setOnClickListener(this);
			inputEditText = (EditText) rootView.findViewById(R.id.search_bar_edit);
			inputEditText.addTextChangedListener(textWatcher);
			inputEditText.setHintTextColor(ContextCompat.getColor(this.context,R.color.hint_color));
		}
	}
	public void setFocusableInTouchMode(boolean str){
		if(inputEditText != null){
			inputEditText.setFocusableInTouchMode(str);
			clearImageView.setVisibility(GONE);
		}
	}


	/**
	 * 输入hint
	 * @param str
	 */
	public void setHint(String str){
		if(inputEditText != null){
			inputEditText.setHint(str);
		}
	}

	/**
	 * 设置输入字体的类别
	 * @param type
	 */
	public void setInputType(int type){
		if(inputEditText != null){
			inputEditText.setInputType(type);
		}
	}

	/**
	 * 文本位置
	 * @param gravity
	 */
	public void setGravity(int gravity){
		if(inputEditText != null){
			inputEditText.setGravity(gravity);
		}
	}

	/**
	 * 设置单行
	 */
	public void setSingleLine(){
		if(inputEditText != null){
			inputEditText.setSingleLine();
		}
	}


	/**
	 * 获取输入的内容
	 * @return
	 */
	public String getText(){
		return requestKey.toString().trim();
	}

	/**
	 * 获取输入的内容
	 * @return
	 */
	public void setText(String txt){
		if(inputEditText != null){
			inputEditText.setText(txt);
		}
	}

	/**
	 * 获取输入的内容
	 * @return
	 */
	public void setFocus(){
		if(inputEditText != null){
			inputEditText.requestFocus();
		}
	}

	/**
	 * 获取输入的内容
	 * @return
	 */
	public void setLastFocus(){
		if(inputEditText != null){
			Editable etable = inputEditText.getText();
			Selection.setSelection(etable, etable.length());
		}
	}

	/**
	 * 密文
	 */
	public void setTransformationMethod_hide(){
		if(inputEditText != null){
			inputEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
			String text = inputEditText.getText().toString().trim();
			inputEditText.setSelection(text.length());
		}
	}

	/**
	 * 明文
	 */
	public void setTransformationMethod_show(){
		if(inputEditText != null){
			inputEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
			String text = inputEditText.getText().toString().trim();
			inputEditText.setSelection(text.length());
		}
	}

	/**
	 * 隐藏删除
	 * @return
	 */
	public void setDelGone(){
		isHideDel = true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.search_bar_clear_iv:
				clearInputData();
				break;
			default:
				break;
		}
	}

	private void clearInputData() {
		if(inputEditText != null){
			inputEditText.setText("");
			requestKey = "";
		}
	}

	/**
	 * 实现EditText的文本变化监听
	 */
	private TextWatcher textWatcher = new TextWatcher() {
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
		}

		@Override
		public void afterTextChanged(Editable s) {
			String keyword = s.toString();
			if(TextUtils.isEmpty(keyword)){
				if(isHideDel){
					clearImageView.setVisibility(View.INVISIBLE);
				}else{
					clearImageView.setVisibility(View.INVISIBLE);
				}

				if(onEditTextDataChanged != null){
					onEditTextDataChanged.onTextIsEmpty();
				}

				requestKey = "";
				return;
			}
			if(isHideDel){
				clearImageView.setVisibility(View.INVISIBLE);
			}else{
				clearImageView.setVisibility(View.VISIBLE);
			}

			if(keyword.equals(requestKey)){
				return;
			}
			requestKey = keyword;
			if(onEditTextDataChanged != null){
				onEditTextDataChanged.onTextChanged();
			}
		}
	};

	public void setOnEditTextDataChanged(OnEditTextDataChanged onEditTextDataChanged) {
		this.onEditTextDataChanged = onEditTextDataChanged;
	}

	public interface OnEditTextDataChanged {
		void onTextIsEmpty();
		void onTextChanged();
	}
}
