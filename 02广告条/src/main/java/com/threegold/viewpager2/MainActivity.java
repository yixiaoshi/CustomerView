package com.threegold.viewpager2;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName(); //"MainActivity"
    private static final int MESSAGE_AUTO_DRAG = 0;
    // 图片资源ID
    private final int[] imageIds = {
            R.drawable.a,
            R.drawable.b,
            R.drawable.c,
            R.drawable.d,
            R.drawable.e};


    // 图片标题集合
    private final String[] imageDescriptions = {
            "尚硅谷波河争霸赛！",
            "凝聚你我，放飞梦想！",
            "抱歉没座位了！",
            "7月就业名单全部曝光！",
            "平均起薪11345元"
    };

    private ViewPager vp_main_ad;
    private TextView tv_main_title;
    private LinearLayout ll_main_poing;
    private List<ImageView> imageViews;

    private int preposition;

    private boolean straight;

    private boolean isDragging = false;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            int max = imageIds.length;
            switch (msg.what) {
                case MESSAGE_AUTO_DRAG:
                    int item = vp_main_ad.getCurrentItem();

                    if (item == 0)
                        straight = true;
                    if (item == max - 1)
                        straight = false;
                    if (straight) {
                        vp_main_ad.setCurrentItem(item + 1);
//                        overridePendingTransition(R.anim.right_in, R.anim.left_out);
                    } else {
                        vp_main_ad.setCurrentItem(item - 1);
//                        overridePendingTransition(R.anim.left_in, R.anim.right_out);
                    }

                    handler.sendEmptyMessageDelayed(MESSAGE_AUTO_DRAG, 3000);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();

        initData();

        tv_main_title.setText(imageDescriptions[preposition]);
        ll_main_poing.getChildAt(preposition).setEnabled(true);
        vp_main_ad.addOnPageChangeListener(new MyOnpageChangeListener());
//        Log.e(TAG, "vp_main_ad.getCurrentItem()=" + vp_main_ad.getCurrentItem());

//        vp_main_ad.setPageTransformer(true,new DepthPageTransformer(MainActivity.this));

        handler.sendEmptyMessageDelayed(MESSAGE_AUTO_DRAG, 3000);
    }

    private class MyOnpageChangeListener implements ViewPager.OnPageChangeListener {

        /**
         * 当页面滚动的时候回调这个方法
         *
         * @param position             当前滚动页面的位置
         * @param positionOffset       当前滑动的百分比
         * @param positionOffsetPixels 屏幕滑动的像数
         */
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            tv_main_title.setText(imageDescriptions[position]);
            ll_main_poing.getChildAt(preposition).setEnabled(false);
            ll_main_poing.getChildAt(position).setEnabled(true);
            preposition = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            switch (state) {
                case ViewPager.SCROLL_STATE_DRAGGING:
                    isDragging = true;
                    handler.removeMessages(MESSAGE_AUTO_DRAG);
                    Log.e(TAG, "ViewPager拖拽中----");
                    break;
                case ViewPager.SCROLL_STATE_IDLE:
                    if (isDragging) {
                        handler.removeMessages(MESSAGE_AUTO_DRAG);
                        handler.sendEmptyMessageDelayed(MESSAGE_AUTO_DRAG, 3000);
                        isDragging = false;
                    }
                    Log.e(TAG, "ViewPager静止中----");
                    break;
                case ViewPager.SCROLL_STATE_SETTLING:

                    Log.e(TAG, "ViewPager滑动----");
                    break;
            }
        }
    }

    private void initData() {
        imageViews = new ArrayList<>();
        for (int i = 0; i < imageIds.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(imageIds[i]);
            imageViews.add(imageView);

            ImageView point = new ImageView(this);

            point.setBackgroundResource(R.drawable.point_selector);

            int widthDp = DensityUtil.dip2px(this, 8);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(widthDp, widthDp);
            if (i != 0) {
                params.leftMargin = widthDp;
                point.setEnabled(false);
            }
            point.setLayoutParams(params);
            ll_main_poing.addView(point);
        }
        vp_main_ad.setAdapter(new MyPagerAdapter());
    }


    private class MyPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return imageViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = imageViews.get(position);
            container.addView(imageView);
            Log.e("TAG", "instantiateItem==positon=" + position);

            imageView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            handler.removeMessages(MESSAGE_AUTO_DRAG);
                            Log.e(TAG, "手指按下图片");
                            break;
                        case MotionEvent.ACTION_UP:
                            handler.sendEmptyMessageDelayed(MESSAGE_AUTO_DRAG, 3000);
                            Log.e(TAG, "手指松开图片");
                            break;
                        case MotionEvent.ACTION_MOVE:

                            Log.e(TAG, "手指移动图片");
                            break;
                        case MotionEvent.ACTION_CANCEL:

                            Log.e(TAG, "取消手指图片");
                            break;
                    }
                    return false;
                }
            });

            imageView.setTag(position);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int nowPosition = (int) v.getTag();
                    Toast.makeText(MainActivity.this, imageDescriptions[nowPosition], Toast.LENGTH_SHORT).show();
                }
            });


            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
            container.removeView((View) object);
            Log.e("TAG", "destroyItem==positon=" + position);
        }
    }

    private void initView() {
        setContentView(R.layout.activity_main);
        vp_main_ad = (ViewPager) findViewById(R.id.vp_main_ad);
        tv_main_title = (TextView) findViewById(R.id.tv_main_title);
        ll_main_poing = (LinearLayout) findViewById(R.id.ll_main_poing);
    }
}
