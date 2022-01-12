package com.company.linquan.app.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.company.linquan.app.R;

/**
 */
public class SearchBar extends FrameLayout implements View.OnClickListener,View.OnKeyListener {

    private Context context;
    private LayoutInflater layoutInflater;
    private View rootView;

    private TextView tagTextView;
    private TextView clearTextView;
    private EditText inputEditText;
    private ImageView clearImageView;
    private String requestKey = "";
    private LinearLayout layout;

    private OnEditTextDataChanged onEditTextDataChanged;
    private OnKeyClickListener onKeyClickListener;

    public SearchBar(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public SearchBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    public SearchBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    private void initView() {

        if (this.context != null) {
            layoutInflater = LayoutInflater.from(this.context);
        }

        if (layoutInflater != null) {
            rootView = layoutInflater.inflate(R.layout.search_bar_layout, null, false);
        }

        if (rootView != null) {
            this.addView(rootView);
            layout = (LinearLayout)rootView.findViewById(R.id.search_bar_layout);
            clearImageView = (ImageView)rootView.findViewById(R.id.search_bar_clear_iv );
            clearImageView.setOnClickListener(this);
            tagTextView = (TextView) rootView.findViewById(R.id.search_bar_text);
            clearTextView = (TextView) rootView.findViewById(R.id.search_bar_clear);
            clearTextView.setOnClickListener(this);
            inputEditText = (EditText) rootView.findViewById(R.id.search_bar_edit);
            inputEditText.addTextChangedListener(textWatcher);
            inputEditText.setOnKeyListener(this);
        }
    }

    public TextView getTagTextView() {
        return tagTextView;
    }

    public EditText getInputEditText() {
        return inputEditText;
    }

    public String getRequestKey() {
        return requestKey;
    }

    public TextView getClearTextView() {
        return clearTextView;
    }


    public void setLayoutStyle(int resId){
        if (layout != null){
            layout.setBackgroundResource(resId);
        }
    }

    public void setHint(String hint){
        if (inputEditText != null){
            inputEditText.setHint(hint);
        }
    }

    public void setText(String hint){
        if (inputEditText != null){
            inputEditText.setText(hint);
        }
    }

    public void setOnEditTextDataChanged(OnEditTextDataChanged onEditTextDataChanged) {
        this.onEditTextDataChanged = onEditTextDataChanged;
    }

    public void setOnKeyClickListener(OnKeyClickListener onKeyClickListener) {
        this.onKeyClickListener = onKeyClickListener;
    }

    /**
     * 输入View.GONE, View.INVISIBLE, View.VISIBLE
     *
     * @param visibility
     */
    public void setTagVisibility(int visibility) {

        if (tagTextView != null) {
            tagTextView.setVisibility(visibility);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.search_bar_clear:
                clearInputData();
                break;
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
     *
     * 实现EditText的文本变化监听
     *
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
                clearTextView.setVisibility(View.GONE);
                clearImageView.setVisibility(View.INVISIBLE);
                if(onEditTextDataChanged != null){
                    onEditTextDataChanged.onTextisEmpty();
                }
                requestKey = "";
                return;
            }

            clearTextView.setVisibility(View.GONE);
            clearImageView.setVisibility(View.VISIBLE);
            if(keyword.equals(requestKey)){
                return;
            }

            requestKey = keyword;
            if(onEditTextDataChanged != null){
                onEditTextDataChanged.onTextChanged();
            }
        }
    };

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if(onKeyClickListener != null){
            onKeyClickListener.onKey(v, keyCode, event);
        }
        return false;
    }

    public interface OnEditTextDataChanged {
        void onTextisEmpty();
        void onTextChanged();
    }

    public interface OnKeyClickListener {
        void onKey(View v, int keyCode, KeyEvent event);
    }

}

