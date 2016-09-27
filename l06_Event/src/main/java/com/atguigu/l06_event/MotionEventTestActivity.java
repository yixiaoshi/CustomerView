package com.atguigu.l06_event;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MotionEventTestActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_touch);

		//给ImageView绑定touch事件
		findViewById(R.id.iv_touch_test).setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				Log.e("TAG", "MyImageView OnTouchListener onTouch() action = " + event.getAction());
//				return false;
				if(event.getAction() == MotionEvent.ACTION_DOWN){
					return false;
//					return true;
				}
				return true;
			}
		});

	}

	//事件的分发
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		Log.i("TAG", "Activity dispatchTouchEvent() action = " + ev.getAction());
		return super.dispatchTouchEvent(ev);
	}
	//事件的处理
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.i("TAG", "Activity onTouchEvent() action = " + event.getAction());
		return super.onTouchEvent(event);
	}
}
