package com.threegold.customerviewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by threegold on 2016/9/23.
 */
public class MViewPager extends ViewGroup {

    private GestureDetector gesture;
    private int currentIndex;
    private Scroller myscroll;

    public MViewPager(Context context) {
        this(context, null);
    }

    public MViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);

        myscroll = new Scroller(context);

        gesture = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                scrollBy((int) distanceX, 0);
                return super.onScroll(e1, e2, distanceX, distanceY);
            }
        });
    }


    /**
     * 测量每个孩子
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec widthMeasureSpec :包含父类该当前控件的宽和模式
     *                          三种模式：EXACTLY,AT_MOST,UNSPECIFIED
     *                          系统的onMesaure中所干的事：
     *                           1、根据 widthMeasureSpec 求得宽度width，和父view给的模式
     *                           2、根据自身的宽度width 和自身的padding 值，相减，求得子view可以拥有的宽度newWidth
     *                           3、根据 newWidth 和模式求得一个新的MeasureSpec值:
     *                            MeasureSpec.makeMeasureSpec(newSize, newmode);
     *                           4、用新的MeasureSpec来计算子view
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        Log.e("TAG", "width==" + width + ",height==" + height + ",mode==" + mode);
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);

            child.layout(i * getWidth(), 0, (i + 1) * getWidth(), getHeight());
        }
    }

    private float downX;
    private float downY;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean result = false;
        gesture.onTouchEvent(ev);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                gesture.onTouchEvent(ev);
                startX = ev.getX();    //无论手势识别还是scrollTo()都需要（onInterceptTouchEvent->ACTION_DOWN）
                //重新给startX赋值，因为拦截了之后在移动不会调用ViewPager的ACTION_DOWN
                /**
                 * E/TAG: onInterceptTouchEvent____ACTION_DOWN
                 E/TAG: onInterceptTouchEvent____ACTION_MOVE
                 E/TAG: onInterceptTouchEvent____ACTION_MOVE
                 E/TAG: onTouchEvent____ACTION_MOVE
                 E/TAG: onTouchEvent____ACTION_MOVE
                 E/TAG: onTouchEvent____ACTION_UP
                 */
                downX = ev.getX();
                downY = ev.getY();
                Log.e("TAG", "onInterceptTouchEvent____ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:

                float newdownX = ev.getX();
                float newdownY = ev.getY();
                int distanceX = (int) Math.abs(newdownX - downX);
                int distanceY = (int) Math.abs(newdownY - downY);
                if (distanceX > distanceY && distanceX > 10) {
                    result = true;
                } else
                    movePager(currentIndex);
                downX = ev.getX();
                downY = ev.getY();
                Log.e("TAG", "onInterceptTouchEvent____ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.e("TAG", "onInterceptTouchEvent____ACTION_UP");
                break;
        }
        return result;
    }

    private float startX;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        gesture.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:    //拦截了之后在移动不会调用ViewPager的ACTION_DOWN
                /**
                 * E/TAG: onInterceptTouchEvent____ACTION_DOWN
                 E/TAG: onInterceptTouchEvent____ACTION_MOVE
                 E/TAG: onInterceptTouchEvent____ACTION_MOVE
                 E/TAG: onTouchEvent____ACTION_MOVE
                 E/TAG: onTouchEvent____ACTION_MOVE
                 E/TAG: onTouchEvent____ACTION_UP
                 */
                startX = event.getX();
                Log.e("TAG", "onTouchEvent____ACTION_DOWN");
                Log.e("TAG", "getScorllX=" + getScrollX());
                Log.e("TAG", "getChildAt(currentIndex).getScrollX():" + getChildAt(currentIndex).getScrollX());
                break;
            case MotionEvent.ACTION_MOVE:
           //     float moveEndX = event.getX();
         //       float moveX = moveEndX - startX;
     //           moveX = -moveX;   //向左移动ViewPage正的内容，但是坐标是move了负的
                //向右移动ViewPage负的内容，但是坐标是move了正的
      //          int nowX = (int) (currentIndex * getWidth() + moveX);
//                scrollTo(nowX, 0);
                Log.e("TAG", "onTouchEvent____ACTION_MOVE");
                Log.e("TAG", "getScorllX=" + getScrollX());
                Log.e("TAG", "getChildAt(currentIndex).getScrollX():" + getChildAt(currentIndex).getScrollX());
                break;
            case MotionEvent.ACTION_UP:
                float endX = event.getX();

                int tempIndex = currentIndex;

                if ((endX - startX) > getWidth() / 2) {
                    tempIndex--;
                } else if ((startX - endX) > getWidth() / 2) {
                    tempIndex++;
                }


                movePager(tempIndex);

                startX = event.getX();

                Log.e("TAG", "onTouchEvent____ACTION_UP");
                Log.e("TAG", "getScorllX=" + getScrollX());
                break;
        }
        return true;
    }

    //屏蔽非法下标位置和移动对应下标位置的页面
    public void movePager(int tempIndex) {
        if (tempIndex < 0)
            tempIndex = 0;
        if (tempIndex > getChildCount() - 1) {
            tempIndex = getChildCount() - 1;
        }
        currentIndex = tempIndex;

        if (pagerChangeListener != null) {
            pagerChangeListener.onPagerChange(currentIndex);
        }

        int distanceX = currentIndex * getWidth() - getScrollX();

//        scrollTo(currentIndex * getWidth(), 0);

        myscroll.startScroll(getScrollX(), getScrollY(), distanceX, 0, Math.abs(distanceX));

        invalidate();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (myscroll.computeScrollOffset()) {
            float currentX = myscroll.getCurrX();
            scrollTo((int) currentX, 0);

            invalidate();
        }
    }

    private OnPagerChangeListener pagerChangeListener;

    public interface OnPagerChangeListener {

        void onPagerChange(int index);
    }

    public void setOnPagerChangeListener(OnPagerChangeListener l) {
        pagerChangeListener = l;
    }
}
