package com.company.wanbei.app.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;

import com.company.wanbei.app.R;

public class SlideBar extends AppCompatButton {
    public interface OnTouchAssortListener{
        public void onTouchAssortListener(String s);
    }

    // 分类
    private static final String[] ASSORT_TEXT = {"A", "B", "C", "D", "E", "F", "G",
            "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z" ,"#"};

    private Paint mPaint = new Paint();
    private Paint mPaint2 = new Paint();
    private int mSelectIndex = -1;
    private OnTouchAssortListener mListener = null;
    private Activity mAttachActivity;
    PopupWindow mPopupWindow = null;
    View layoutView;
    TextView text;

    public SlideBar(Context context){
        this(context,null);
    }

    public SlideBar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SlideBar(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
        mAttachActivity = (Activity)context;
        init(context);
    }
    private void init(Context context) {
        layoutView = LayoutInflater.from(context).inflate(R.layout.alert_dialog_menu_layout, null);
        text = (TextView) layoutView.findViewById(R.id.content);
    }

    public void setOnTouchAssortListener(OnTouchAssortListener listener) {
        this.mListener = listener;
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        // 获取焦点改变背景颜色.
        int height = getHeight();// 获取对应高度
        int width = getWidth(); // 获取对应宽度
        int singleHeight = height / ASSORT_TEXT.length;// 获取每一个字母的高度

        for (int i = 0; i < ASSORT_TEXT.length; i++) {
            mPaint.setColor(Color.parseColor("#999999"));
            mPaint.setTypeface(Typeface.DEFAULT_BOLD);
            mPaint.setAntiAlias(true);
            mPaint.setTextSize(40);
            // x坐标等于中间-字符串宽度的一半.
            float xPos = width / 2 - mPaint.measureText(ASSORT_TEXT[i]) / 2;
            float yPos = singleHeight * i + singleHeight;
            // 选中的状态
            if (i == mSelectIndex) {
                mPaint.setColor(Color.parseColor("#ffffff"));
                mPaint.setFakeBoldText(true);
                mPaint2.setColor(Color.parseColor("#38AFF7"));
                canvas.drawCircle(width / 2, yPos - mPaint.measureText(ASSORT_TEXT[i]) / 2, width / 3, mPaint2);
            }
            canvas.drawText(ASSORT_TEXT[i], xPos, yPos, mPaint);
            mPaint.reset();// 重置画笔
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        //判断是哪一个字母被点击了
        int nIndex = (int) (event.getY() / getHeight() * ASSORT_TEXT.length);
        if (nIndex >= 0 && nIndex < ASSORT_TEXT.length){
            switch (event.getAction()){
                case MotionEvent.ACTION_MOVE:
                    // 如果滑动改变
                    if (mSelectIndex != nIndex){
                        mSelectIndex = nIndex;
                        showCharacter(ASSORT_TEXT[mSelectIndex]);
                        if (mListener != null){
                            mListener.onTouchAssortListener(ASSORT_TEXT[mSelectIndex]);
                        }
                    }
                    break;
                case MotionEvent.ACTION_DOWN:
                    mSelectIndex = nIndex;
                    showCharacter(ASSORT_TEXT[mSelectIndex]);
                    if (mListener != null){
                        mListener.onTouchAssortListener(ASSORT_TEXT[mSelectIndex]);
                    }

                    break;
                case MotionEvent.ACTION_UP:
                    disShowCharacter();
                    mSelectIndex = -1;
                    break;
            }
        } else {
            mSelectIndex = -1;
            disShowCharacter();
        }
        invalidate();
        return true;
    }

    private void disShowCharacter() {
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
            mPopupWindow=null;
        }
    }

    /**
     * 显示弹出的字符
     * @param string
     */
    private void showCharacter(String string){

        if (mPopupWindow != null){
            text.setText(string);
        } else{
            mPopupWindow = new PopupWindow(layoutView, 160, 160, false);
            mPopupWindow.showAtLocation(mAttachActivity.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        }
        text.setText(string);
    }
}
