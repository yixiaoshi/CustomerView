package com.threegold.customerattr;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by threegold on 2016/9/23.
 */
public class MyAttributeView extends View {

    private String nameStr;
    private int mage;
    private Bitmap bitmap;

    public MyAttributeView(Context context) {
        this(context,null);
    }

    public MyAttributeView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyAttributeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        //得到属性有三种方式


        //使用命名空间取属性
        String name = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto","name");
        String age = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto","age");
        String show = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto","show");
        System.out.println("name=="+name+",age=="+age+",show=="+show);

        //使用循环
        for(int i = 0; i < attrs.getAttributeCount(); i++) {
          System.out.println(attrs.getAttributeName(i)+"______"+attrs.getAttributeValue(i));
        }

        //使用系统工具
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.MyAttributeView);
        for(int i = 0; i < typedArray.getIndexCount(); i++) {
          int id = typedArray.getIndex(i);
            switch (id) {
                case R.styleable.MyAttributeView_name :
                    nameStr = typedArray.getString(id);
                    break;
                case R.styleable.MyAttributeView_age :
                    mage = typedArray.getInt(id,0);
                    break;
                case R.styleable.MyAttributeView_show :
                    Drawable drawable =  typedArray.getDrawable(id);
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                    bitmap = bitmapDrawable.getBitmap();
                    break;
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(33);
        paint.setColor(Color.YELLOW);
        canvas.drawText(nameStr+":"+mage,0,33,paint);
        canvas.drawBitmap(bitmap,0,66,paint);
    }
}
