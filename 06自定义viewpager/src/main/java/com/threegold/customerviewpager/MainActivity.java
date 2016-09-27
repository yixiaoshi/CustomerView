package com.threegold.customerviewpager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends Activity {

    private int[] ids = {R.drawable.a1, R.drawable.a2, R.drawable.a3,
            R.drawable.a4, R.drawable.a5, R.drawable.a6};
    private MViewPager mViewPager;
    private RadioGroup rg_main_move;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = (MViewPager)findViewById(R.id.mViewPager);
        rg_main_move = (RadioGroup)findViewById(R.id.rg_main_move);

        for(int i = 0; i < ids.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(ids[i]);

            mViewPager.addView(imageView);
        }

        View viewTest = View.inflate(this,R.layout.test,null);
        mViewPager.addView(viewTest,2);

        for(int i = 0; i < mViewPager.getChildCount(); i++) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setId(i);
            if(i != 0)
            {
                radioButton.setChecked(false);
            }
            else
                radioButton.setChecked(true);

            rg_main_move.addView(radioButton);
        }

        rg_main_move.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                mViewPager.movePager(checkedId);
            }
        });

        mViewPager.setOnPagerChangeListener(new MViewPager.OnPagerChangeListener() {
            @Override
            public void onPagerChange(int index) {
                rg_main_move.check(index);
            }
        });
    }
}
