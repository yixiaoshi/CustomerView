package com.threegold.youku;

import android.animation.ObjectAnimator;
import android.view.ViewGroup;

/**
 * Created by threegold on 2016/9/21.
 */
public class Tools {
    public static void hideView(ViewGroup view, int offStart) {
        /*RotateAnimation rotate = new RotateAnimation(0,180,view.getWidth()/2,view.getHeight());
        Log.e("TAG","viewWidth="+view.getWidth() + "viewHeight=" + view.getHeight());

        rotate.setDuration(500);
        rotate.setFillAfter(true);
        rotate.setStartOffset(offStart);

        view.startAnimation(rotate);

        for(int i = 0; i < view.getChildCount(); i++) {
            view.getChildAt(i).setEnabled(false);
        }*/

//        view.setRotation();
        ObjectAnimator rotate = ObjectAnimator.ofFloat(view,"rotation",0,180);
        rotate.setDuration(500);
        rotate.setStartDelay(offStart);
        rotate.start();

        view.setPivotX(view.getWidth()/2);
        view.setPivotY(view.getHeight());
    }
    public static void showView(ViewGroup view, int offStart) {
        /*RotateAnimation rotate = new RotateAnimation(180,360,view.getWidth()/2,view.getHeight());
        Log.e("TAG","viewWidth="+view.getWidth() + "viewHeight=" + view.getHeight());

        rotate.setDuration(500);
        rotate.setFillAfter(true);
        rotate.setStartOffset(offStart);

        view.startAnimation(rotate);

        for(int i = 0; i < view.getChildCount(); i++) {
          view.getChildAt(i).setEnabled(true);
        }*/

        ObjectAnimator rotate = ObjectAnimator.ofFloat(view,"rotation",180,360);
        rotate.setDuration(500);
        rotate.setStartDelay(offStart);
        rotate.start();

        view.setPivotX(view.getWidth()/2);
        view.setPivotY(view.getHeight());
    }

    public static void hideView(ViewGroup view) {
        hideView(view,0);
    }

    public static void showView(ViewGroup view) {
        showView(view,0);
    }
}
