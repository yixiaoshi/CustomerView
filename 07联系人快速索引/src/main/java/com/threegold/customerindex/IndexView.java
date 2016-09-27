package com.threegold.customerindex;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by threegold on 2016/9/25.
 */

public class IndexView extends View {
    private String[] words = {"A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"};
    private int itemHeight;
    private int itemWidth;
    private Paint paint;
    private int customerTop;
    private OnChangeTextListener changeTextListener;

    public IndexView(Context context) {
        this(context, null);
    }

    public IndexView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndexView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);//设置抗锯齿
        paint.setTypeface(Typeface.DEFAULT_BOLD);//设置粗体字
        paint.setTextSize(DensityUtil.dip2px(context, 20));
        customerTop = DensityUtil.dip2px(context, 16);
    }

    /**
     * 文本变化的监听器
     */
    public interface OnChangeTextListener
    {
        /**
         * 当滑动文字变化的时候回调
         * @param word 被按下的字母
         */
        public void OnChangeText(String word);
    }

    /**
     * 设置监听文本变化
     * @param l
     */
    public void setOnChangeTextListener(OnChangeTextListener l)
    {
        changeTextListener = l;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        itemWidth = getWidth();
        itemHeight = getHeight() / 26;
    }


    private int touchIndex = -1;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                int nowIndex = (int) (event.getY()/itemHeight);
                if(touchIndex != nowIndex) {
                    touchIndex = nowIndex;
                    if(changeTextListener != null && touchIndex < words.length)
                    {
                        changeTextListener.OnChangeText(words[touchIndex]);
                    }
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                touchIndex = -1;
                invalidate();
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < words.length; i++) {

            if(i == touchIndex){
                paint.setColor(Color.GRAY);
            } else {
                paint.setColor(Color.WHITE);
            }

            String word = words[i];

            //用矩形包裹字母得到宽高
            Rect rect = new Rect();
            paint.getTextBounds(word, 0, 1, rect);

            int wordWidth = rect.width();
            int wordHeith = rect.height();

            float wordX = itemWidth / 2 - wordWidth / 2;
            float wordY = itemHeight / 2 - wordHeith / 2 + i * itemHeight;
            //绘制文字是从左下角坐标，图片是左上角
            canvas.drawText(word, wordX, wordY + customerTop, paint);
        }
    }




}
