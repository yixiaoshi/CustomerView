package com.threegold.viewpager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = MainActivity.class.getSimpleName(); //"MainActivity"
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
    /**
     * ListView的使用
     * 1.在布局文件定义
     * 2.准备数据-集合
     * 3.设置适配器
     * 4.实现getView 和getCount();
     * 5.实现getView()-item.xml
     * <p/>
     * ViewPager的使用和ListView类似
     * 1.在布局文件定义
     * 2.准备数据-集合
     * 3.设置适配器-PagerAdapter
     * 4.有四个方法一定要实现的
     */

    private List<ImageView> imageViews;
    private ViewPager vp_main_ad;
    private TextView tv_main_title;
    private LinearLayout ll_main_pointg;
    /**
     * 上一次被高亮显示的位置
     */
    private int prepostion;

    private boolean isDragging = false;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                int item = vp_main_ad.getCurrentItem() + 1;
                vp_main_ad.setCurrentItem(item);

                handler.removeMessages(0);

                handler.sendEmptyMessageDelayed(0, 3000);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();

        initData();

        setParams();

    }

    private void setParams() {
        //设置文本
        tv_main_title.setText(imageDescriptions[prepostion]);
        //设置监听页面的改变
        vp_main_ad.addOnPageChangeListener(new MyOnPageChangeListener());
        //设置第0个点高亮
        ll_main_pointg.getChildAt(prepostion).setEnabled(true);

        //保证是imageViews.size()整数倍
        int item = Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % imageViews.size();
        vp_main_ad.setCurrentItem(item);

        handler.sendEmptyMessageDelayed(0, 3000);
    }

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
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

        /**
         * 当某个页面被选中的时候回调
         *
         * @param position 被选中页面的位置
         */
        @Override
        public void onPageSelected(int position) {
            int realposition = position % imageViews.size();
            tv_main_title.setText(imageDescriptions[realposition]);
            //把之前高亮显示的设置灰色
            ll_main_pointg.getChildAt(prepostion).setEnabled(false);
            //把当前位置对应的点设置红色
            ll_main_pointg.getChildAt(realposition).setEnabled(true);
            prepostion = realposition;
        }

        /**
         * 当页面滑动状态改变的时候回调
         * 拖拽-->滑动
         * 滑动-->静止
         * 静止-->拖拽
         *
         * @param state
         */
        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                Log.e(TAG, "ViewPager拖拽中----");
                isDragging = true;
                handler.removeMessages(0);
            } else if (state == ViewPager.SCROLL_STATE_IDLE && isDragging) {
                Log.e(TAG, "ViewPager静止中----");
                isDragging = false;
                handler.removeMessages(0);
                handler.sendEmptyMessageDelayed(0, 3000);
            } else if (state == ViewPager.SCROLL_STATE_SETTLING) {
                Log.e(TAG, "ViewPager滑动中----");
            }
        }
    }


    private void initData() {
        imageViews = new ArrayList<>();
        for (int i = 0; i < imageIds.length; i++) {
            ImageView imageView = new ImageView(this);
//            imageView.setImageResource(imageIds[i]);  //错的，不能不会充满
            imageView.setBackgroundResource(imageIds[i]);

            imageViews.add(imageView);

            //创建点,添加到线性布局
            ImageView point = new ImageView(this);
//            point.setBackgroundResource(R.drawable.point_selector);
            point.setImageResource(R.drawable.point_selector);

            int widthDp =  DensityUtil.dip2px(this, 8);
//            Log.e(TAG, "widthDp=" + widthDp);
//            Log.e(TAG, "i=" + i);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(widthDp, widthDp);
            if (i != 0) {
                params.leftMargin = widthDp;
                point.setEnabled(false);
            }
            point.setLayoutParams(params);
            ll_main_pointg.addView(point);
        }
        vp_main_ad.setAdapter(new MyPagerAdapter(this, imageViews, handler));
    }

    private void initView() {
        setContentView(R.layout.activity_main);
        vp_main_ad = (ViewPager) findViewById(R.id.vp_main_ad);
        tv_main_title = (TextView) findViewById(R.id.tv_main_title);
        ll_main_pointg = (LinearLayout) findViewById(R.id.ll_main_pointg);
    }

    @Override
    protected void onDestroy() {
        handler.removeMessages(0);
        super.onDestroy();
    }
}
