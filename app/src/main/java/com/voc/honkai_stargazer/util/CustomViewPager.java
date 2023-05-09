package com.voc.honkai_stargazer.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

/*
 * Project Genshin Spirit (原神小幫手) was
 * Created & Develop by Voc-夜芷冰 , Programmer of Xectorda
 * Copyright © 2023 Xectorda 版權所有
 */

//https://stackoverflow.com/questions/7814017/is-it-possible-to-disable-scrolling-on-a-viewpager
public class CustomViewPager extends ViewPager {
    boolean isLand = false;
    boolean scrollable = true;
    boolean isLinearLayout = false;

    private boolean isPagingEnabled = true;

    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setLand(boolean land) {
        isLand = land;
        if(isLand){
            setPageTransformer(true, new VerticalPageTransformer());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(isLand && scrollable){
            return super.onTouchEvent(swapXY(event));
        }else if(scrollable == false){
            return false;
        }else {
            return this.isPagingEnabled && super.onTouchEvent(event);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if(isLand && scrollable){
            boolean intercepted = super.onInterceptTouchEvent(swapXY(event));
            swapXY(event); // return touch coordinates to original reference frame for any child views
            return intercepted;
        }else if(scrollable == false){
            return false;
        }else{
            return this.isPagingEnabled && super.onInterceptTouchEvent(event);
        }
    }

    public void setPagingEnabled(boolean b) {
        this.isPagingEnabled = b;
    }

    public void setScrollable(boolean scrollable) {
        this.scrollable = scrollable;
    }
    public void setIsLinearLayout(boolean isLinearLayout) {
        this.isLinearLayout = isLinearLayout;
    }

    public static class VerticalPageTransformer implements PageTransformer {

        @Override
        public void transformPage(View view, float position) {

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 1) { // [-1,1]
                view.setAlpha(1);

                // Counteract the default slide transition
                view.setTranslationX(view.getWidth() * -position);

                //set Y position to swipe in from top
                float yPosition = position * view.getHeight();
                view.setTranslationY(yPosition);

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }

    /**
     * Swaps the X and Y coordinates of your touch event.
     */
    private MotionEvent swapXY(MotionEvent ev) {
        float width = getWidth();
        float height = getHeight();

        float newX = (ev.getY() / height) * width;
        float newY = (ev.getX() / width) * height;

        ev.setLocation(newX, newY);

        return ev;
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        if(isLinearLayout){
            try {
                int numChildren = getChildCount();
                for (int i = 0; i < numChildren; i++) {
                    View child = getChildAt(i);
                    if (child != null) {
                        child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                        int h = child.getMeasuredHeight();
                        heightMeasureSpec = Math.max(heightMeasureSpec, MeasureSpec.makeMeasureSpec(h, MeasureSpec.EXACTLY));
                    }
                }

            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}