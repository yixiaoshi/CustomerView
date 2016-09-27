package com.atguigu.l06_event;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * Created by shkstart on 2016/9/9 0009.
 */
public class MyImageView extends ImageView {

    /**
     *
     * @param context
     * @param attrs ：对应着布局文件中的属性节点。
     */
    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.e("TAG", "MyImageView()");
    }
    //事件的分发
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.e("TAG", "MyImageView dispatchTouchEvent() action = " + event.getAction());
        return super.dispatchTouchEvent(event);
//        return true;
    }

    //事件的处理
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("TAG", "MyImageView onTouchEvent() action = " + event.getAction());
        return super.onTouchEvent(event);
//        return true;
    }
}
