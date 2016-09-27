package com.threegold.slidemenu;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Scroller;

/**
 * Created by threegold on 2016/9/25.
 */
public class SlideLayout extends FrameLayout {
    private View contentView;
    private View menuView;
    private int contentWidth;
    private int menuWidth;
    private int viewHeigth;
    private Scroller scroller;

    public SlideLayout(Context context) {
        this(context,null);
    }

    public SlideLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SlideLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        scroller = new Scroller(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        contentView = getChildAt(0);
        menuView = getChildAt(1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        contentWidth = contentView.getMeasuredWidth();
        menuWidth = menuView.getMeasuredWidth();
        viewHeigth = getMeasuredHeight();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        menuView.layout(contentWidth,0,menuWidth+contentWidth,viewHeigth);
    }

    private float startX;
    private float startY;
    private float downX;
    private float downY;

    private float interceptStartX;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean isIntercept = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN :
                Log.e("TAG", "onInterceptTouchEvent____ACTION_DOWN");
                Log.e("TAG", ""+(stateChangeListener == null));
                if(stateChangeListener != null) {
                    stateChangeListener.OnDown(this);
                }
                interceptStartX = ev.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e("TAG", "onInterceptTouchEvent____ACTION_MOVE");
                float interceptEndX = ev.getX();
                if(Math.abs(interceptEndX-interceptStartX)>5)
                    isIntercept = true;
                break;
            case MotionEvent.ACTION_UP:
                Log.e("TAG", "onInterceptTouchEvent____ACTION_UP");
                break;
        }
        return isIntercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e("TAG", "onTouchEvent____ACTION_DOWN");
                downX = startX = event.getX();
                downY = startY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e("TAG", "onTouchEvent____ACTION_MOVE");
                float endX = event.getX();
                float endY = event.getY();
                float distanceX = endX - startX;
                int toScrollX = (int) (getScrollX() - distanceX);
                if(toScrollX<0) {
                    toScrollX = 0;
                }
                if(toScrollX > menuWidth) {
                    toScrollX = menuWidth;
                }
                scrollTo(toScrollX,getScrollY());
                //5.重新记录坐标
                startX = event.getX();
                startY = event.getY();

                float DX = Math.abs(endX - downX);
                float DY = Math.abs(endY - downY);
                if(DX > DY && DX > 8)
                {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }

                break;
            case MotionEvent.ACTION_UP:
                Log.e("TAG", "onTouchEvent____ACTION_UP");
                int nowScrollX = getScrollX();
                Log.e("TAG","nowScrollX=="+nowScrollX+",menuWidth=="+menuWidth);
                if(nowScrollX > menuWidth/2){
                    openMenu();
                }
                else{
                    closeMenu();
                }
                break;
        }
        return true;
    }

    public void openMenu() {
        scroller.startScroll(getScrollX(),getScrollY(),menuWidth-getScrollX(),getScrollY());
        //会导致重绘制，还会导致computeScroll执行
        invalidate();
        if(stateChangeListener != null) {
            stateChangeListener.OnOpen(this);
        }
    }

    public void closeMenu() {
        scroller.startScroll(getScrollX(),getScrollY(),0-getScrollX(),getScrollY());
        invalidate();
        if(stateChangeListener != null) {
            stateChangeListener.OnClose(this);
        }
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(),getScrollY());
            invalidate();
        }
    }

    private OnStateChangeListener stateChangeListener;
    public interface OnStateChangeListener {
        //当item按下的时候被回调
        void OnDown(SlideLayout slideLayout);
        //当item被打开的时候回调
        void OnOpen(SlideLayout slideLayout);
        //当item关闭的时候回调
        void OnClose(SlideLayout slideLayout);
    }
    public void setOnStateChangeListener(OnStateChangeListener l)
    {
        this.stateChangeListener = l;
    }
}
