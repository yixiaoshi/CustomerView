package com.threegold.customertogglebutton;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by threegold on 2016/9/23.
 */
public class CoustomerSwitch extends View {

    private Bitmap sliderButton;
    private Bitmap switchBack;
    private int sliderLeftMax;
    private Paint paint;
    private int sliderleft;

    //    按钮是否打开
    private boolean isOpen = false;
    private boolean isClick = true;

    //在代码中实例化该类的时候，通常用该方法
    public CoustomerSwitch(Context context) {
        this(context, null);
    }

    //在Android系统规定，在xml文件中使用该控件，实例化用该构造方法，如果缺少，将会崩溃
    public CoustomerSwitch(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    //当想额外指定样式的时候，使用该方法
    public CoustomerSwitch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        intiView();
    }

    private void intiView() {
        switchBack = BitmapFactory.decodeResource(getResources(), R.drawable.switch_background);
        sliderButton = BitmapFactory.decodeResource(getResources(), R.drawable.slide_button);
        sliderLeftMax = switchBack.getWidth() - sliderButton.getWidth();

        //设置抗锯齿
        paint = new Paint();
        paint.setAntiAlias(true);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isClick) {
                    isOpen = !isOpen;
                    flushView();
                }
            }
        });
    }

    private void flushView() {
        if (isOpen) {
            sliderleft = sliderLeftMax;
        } else {
            sliderleft = 0;
        }

        //重新绘制
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(switchBack.getWidth(), switchBack.getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(switchBack, 0,0,paint);
        canvas.drawBitmap(sliderButton, sliderleft, 0, paint);
    }

    private float startX;
    private float clickX;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isClick = true;
                clickX = startX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                float endX = event.getX();
                float distanceX = endX - startX;
                sliderleft += distanceX;

                if (Math.abs(endX - clickX) > 3) {
                    isClick = false;
                }

                if (sliderleft > sliderLeftMax) {
                    sliderleft = sliderLeftMax;
                }
                if (sliderleft < 0) {
                    sliderleft = 0;
                }


                invalidate();
                startX = endX;
                break;
            case MotionEvent.ACTION_UP:
                if (!isClick) {
                    if (sliderleft > sliderLeftMax / 2) {
                        isOpen = true;
                    } else {
                        isOpen = false;
                    }

                    flushView();
                }
                break;
        }


        return true;
    }
}
