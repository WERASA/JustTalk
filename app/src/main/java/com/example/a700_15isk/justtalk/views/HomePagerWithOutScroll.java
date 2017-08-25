package com.example.a700_15isk.justtalk.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by 700-15isk on 2017/8/25.
 */

public class HomePagerWithOutScroll  extends ViewPager {

    public HomePagerWithOutScroll(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HomePagerWithOutScroll(Context context) {
        super(context);
    }



    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
            return super.onTouchEvent(arg0);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {

        return false;
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item);
    }

}
