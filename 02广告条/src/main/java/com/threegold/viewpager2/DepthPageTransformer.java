package com.threegold.viewpager2;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * Created by threegold on 2016/9/22.
 */
public class DepthPageTransformer implements ViewPager.PageTransformer {
    private Context mContext;
    public DepthPageTransformer(Context mContext)
    {
        this.mContext = mContext;
    }
    @Override
    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();

        if (position < -1) {
            Animation animation2 = AnimationUtils.loadAnimation(mContext,R.anim.right_in);
            view.startAnimation(animation2);

        } else if (position <= 0)
        {
            Animation animation1 = AnimationUtils.loadAnimation(mContext,R.anim.left_out);
            view.startAnimation(animation1);
        } else if (position <= 1) {
            Animation animation2 = AnimationUtils.loadAnimation(mContext,R.anim.right_in);
            view.startAnimation(animation2);
        } else {
            Animation animation2 = AnimationUtils.loadAnimation(mContext,R.anim.right_in);
            view.startAnimation(animation2);
        }
    }
}
