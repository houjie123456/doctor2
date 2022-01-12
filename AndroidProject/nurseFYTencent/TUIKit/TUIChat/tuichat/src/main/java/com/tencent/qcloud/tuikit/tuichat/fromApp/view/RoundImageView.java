package com.tencent.qcloud.tuikit.tuichat.fromApp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

import com.tencent.qcloud.tuikit.tuichat.R;


//import androidx.appcompat.widget.ToolbarAppCompatImageView;


/**
 * 圆角图片，支持设置圆形图片
 * @author Work
 *
 */
public class RoundImageView extends AppCompatImageView {
    public RoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RoundImageView(Context context) {
        super(context);
        init();
    }

    private final RectF roundRect = new RectF();
    private float rect_adius = 30;
    private final Paint maskPaint = new Paint();
    private final Paint zonePaint = new Paint();
    public boolean isDrawCircle = false;
    public boolean isDrawCircleNoBround = false;

    private void init() {
        maskPaint.setAntiAlias(true);
        maskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));//取两层绘制交集。显示上层。
        //
        zonePaint.setAntiAlias(true);
        zonePaint.setColor(Color.WHITE);
        //
        float density = getResources().getDisplayMetrics().density;
        rect_adius = rect_adius * density;
    }

    public void setRectAdius(float adius) {
        rect_adius = adius;
        invalidate();
    }

    public void setDrawCircle() {
        isDrawCircle = true;
        invalidate();
    }

    public void setDrawCircleNoBround() {
        isDrawCircleNoBround = true;
        invalidate();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int w = getWidth();
        int h = getHeight();
        roundRect.set(0, 0, w, h);
    }

    @Override
    public void draw(Canvas canvas) {
        if(!isDrawCircle){
            canvas.saveLayer(roundRect, zonePaint, Canvas.ALL_SAVE_FLAG);// 先画矩形背景图片
            canvas.drawRoundRect(roundRect, rect_adius, rect_adius, zonePaint);// 再画圆角矩形
            canvas.saveLayer(roundRect, maskPaint, Canvas.ALL_SAVE_FLAG); //最后根据 --取两层绘制交集。显示上层 显示圆角矩形
        }else{
            if(isDrawCircleNoBround){
                float density = getResources().getDisplayMetrics().density;
                rect_adius = Math.min(getWidth()/2, getHeight()/2);
                canvas.drawCircle(getWidth() / 2, getHeight() / 2, rect_adius-(2*density), zonePaint); // 再画圆形
                canvas.saveLayer(roundRect, maskPaint, Canvas.ALL_SAVE_FLAG);//最后根据 --取两层绘制交集。显示上层 显示圆形
            }else{
                float density = getResources().getDisplayMetrics().density;
                rect_adius = Math.min(getWidth()/2, getHeight()/2);
                drawCircleBorder(canvas, rect_adius, getResources().getColor(R.color.photo_border_color));
                canvas.saveLayer(roundRect, zonePaint, Canvas.ALL_SAVE_FLAG);// 先画矩形背景图片
                canvas.drawCircle(getWidth() / 2, getHeight() / 2, rect_adius-(2*density), zonePaint); // 再画圆形
                canvas.saveLayer(roundRect, maskPaint, Canvas.ALL_SAVE_FLAG);//最后根据 --取两层绘制交集。显示上层 显示圆形
            }
        }
        super.draw(canvas);
        canvas.restore();
    }

    /**
     * 边缘画圆
     */
    private void drawCircleBorder(Canvas canvas, float radius, int color) {
        Paint paint = new Paint();
        /* 去锯齿 */
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        paint.setColor(color);
        /* 设置paint的　style　为STROKE：空心 */
        paint.setStyle(Paint.Style.FILL);
        /* 设置paint的外框宽度 */
        paint.setStrokeWidth(1);
        canvas.drawCircle(getWidth()/ 2, getHeight() / 2, radius, paint);
    }
}
