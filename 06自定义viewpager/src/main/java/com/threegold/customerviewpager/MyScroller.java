package com.threegold.customerviewpager;

import android.os.SystemClock;

/**
 * Created by threegold on 2016/9/23.
 */
public class MyScroller {
    private float startX;
    private float startY;
    private int distanceX;
    private int distanceY;
    private long startTime;
    //是否移动完成
    private boolean isFinish;

    private long totalTime = 600;
    private float currX;

    public float getCurrX() {
        return currX;
    }

    public void startScroll(float startX, float startY, int distanceX, int distanceY) {
        this.startX = startX;
        this.startY = startY;
        this.distanceX = distanceX;
        this.distanceY = distanceY;
        this.startTime = SystemClock.uptimeMillis(); //开机到现在的时刻,开始记时
        this.isFinish = false;

    }

    //是否在移动
    public boolean computeScrollOffset() {
        if(isFinish) {
            return false;
        }

        long endTime = SystemClock.uptimeMillis();
        long passTime = endTime - startTime;
        if(passTime < totalTime){
            float distanceSmallX = passTime*distanceX/totalTime;

            currX = startX + distanceSmallX;
        } else {
            currX = startX + distanceX;
            isFinish = true;
        }
        return true;

    }

}
