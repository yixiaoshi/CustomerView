package com.threegold.youku;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    private ImageView icon_home;
    private ImageView icon_menu;
    private RelativeLayout level1;
    private RelativeLayout level2;
    private RelativeLayout level3;

    private boolean showLevel1 = true;
    private boolean showLevel2 = true;
    private boolean showLevel3 = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        icon_home = (ImageView) findViewById(R.id.icon_home);
        icon_menu = (ImageView) findViewById(R.id.icon_menu);
        level1 = (RelativeLayout) findViewById(R.id.level1);
        level2 = (RelativeLayout) findViewById(R.id.level2);
        level3 = (RelativeLayout) findViewById(R.id.level3);

        MyListener myListener = new MyListener();

        icon_home.setOnClickListener(myListener);
        icon_menu.setOnClickListener(myListener);
    }

    class MyListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.icon_menu:
                    magLevel3();
                    break;
                case R.id.icon_home:
                    magLevel2();
                    break;
            }
        }
    }

    private void magLevel3() {
        if (showLevel3) {
            Tools.hideView(level3);
            showLevel3 = false;
        } else {
            Tools.showView(level3);
            showLevel3 = true;
        }
    }

    private void magLevel2() {
        if (showLevel2) {
            Tools.hideView(level2);
            showLevel2 = false;
            if (showLevel3) {
                Tools.hideView(level3, 200);
                showLevel3 = false;
            }
        } else {
            Tools.showView(level2);
            showLevel2 = true;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            magLevel1();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void magLevel1() {
        if (showLevel1) {
            Tools.hideView(level1);
            showLevel1 = false;
            if (showLevel2) {
                Tools.hideView(level2,200);
                showLevel2 = false;
                if (showLevel3) {
                    Tools.hideView(level3, 400);
                    showLevel3 = false;
                }
            }
        } else {

            Tools.showView(level1);
            showLevel1 = true;
            Tools.showView(level2,200);
            showLevel2 = true;
        }
    }
}
