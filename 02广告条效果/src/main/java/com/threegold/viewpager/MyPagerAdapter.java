package com.threegold.viewpager;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by threegold on 2016/9/21.
 */
public class MyPagerAdapter extends PagerAdapter {
    private Context mContext;
    private List<ImageView> imageViews;
    private Handler handler;
    public MyPagerAdapter(Context mContext, List<ImageView> imageViews, Handler handler) {
        this.mContext = mContext;
        this.imageViews = imageViews;
        this.handler = handler;
    }


    /**
     * 相当于getView方法
     * 第一开始加载2个,自己和右边
     * 最多加载3个，因为只能显示自己和左右
     * @param container ViewPager容器
     * @param position  要创建页面的位置
     * @return
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final ImageView imageView = imageViews.get(position % imageViews.size());
        container.addView(imageView);
        Log.e("TAG", "instantiateItem==positon="+position);
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN :
                        Log.e("TAG", "手指按下图片");
                        handler.removeMessages(0);
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.e("TAG", "手指松开图片");
                        handler.sendEmptyMessageDelayed(0,3000);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        Log.e("TAG", "触摸事件取消");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.e("TAG", "手指移动图片");
                        break;
                }
                return false;
            }
        });

        imageView.setTag(position);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int nowPosition = (int) imageView.getTag();
                Toast.makeText(mContext, "imageView position="+ nowPosition, Toast.LENGTH_SHORT).show();
            }
        });

        return imageView;
    }


    /**
     * 销毁某一条
     *
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);  //注意了，这个方法要注释掉
        container.removeView((View) object);
        Log.e("TAG", "destroyItem==positon="+position);
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    /**
     * 比较视图是否是同一个页面
     *
     * @param view   比较的页面
     * @param object 由instantiateItem返回回来的值
     * @return
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
