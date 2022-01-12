package com.company.wanbei.app.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

//import androidx.viewpager.widget.ViewPager;

/**
 *  设置能否滑动，并且每次只滑动一页
 * @author YC
 *
 */
public class CanScrollViewPager extends ViewPager {
	private boolean isCanScroll = true;
	   
    public CanScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public CanScrollViewPager(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }
   
    public void setCanScroll(boolean isCanScroll){ 
        this.isCanScroll = isCanScroll; 
    }
    
   @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        // TODO Auto-generated method stub
        if(isCanScroll){
            return super.onTouchEvent(arg0);
        }else{
            return false;
        }
    }

   @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        // TODO Auto-generated method stub
        if(isCanScroll){
            return super.onInterceptTouchEvent(arg0);
        }else{
            return false;
        }
    }
   
   @Override
   public void setCurrentItem(int item, boolean smoothScroll) {
       // TODO Auto-generated method stub
       super.setCurrentItem(item, smoothScroll);
   }

   @Override
   public void setCurrentItem(int item) {
       // TODO Auto-generated method stub
       super.setCurrentItem(item, false);
   }
}
