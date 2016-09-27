package com.atguigu.l06_event;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	private Button btn_main_test1;
	private Button btn_main_test2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btn_main_test1 = (Button)findViewById(R.id.btn_main_test1);
		btn_main_test2 = (Button)findViewById(R.id.btn_main_test2);

		//设置btn_main_test1的点击事件
		btn_main_test1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this,MotionEventTestActivity.class));
			}
		});
		//设置btn_main_test2的长按事件
		btn_main_test2.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				startActivity(new Intent(MainActivity.this,KeyEventTestActivity.class));

				return true;
			}
		});
	}
}
