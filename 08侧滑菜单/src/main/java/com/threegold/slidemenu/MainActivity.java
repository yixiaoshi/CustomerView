package com.threegold.slidemenu;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends Activity {

    private ListView lv_main;

    private ArrayList<MyBean> myBeans;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv_main = (ListView) findViewById(R.id.lv_main);

        //初始化数据
        myBeans = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            myBeans.add(new MyBean("Content" + i));
        }

        //设置适配器
        adapter = new MyAdapter();
        lv_main.setAdapter(adapter);
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return myBeans.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(MainActivity.this, R.layout.item_base, null);
                viewHolder = new ViewHolder();
                viewHolder.item_content = (TextView) convertView.findViewById(R.id.item_content);
                viewHolder.item_menu = (TextView) convertView.findViewById(R.id.item_menu);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();

            }

            final MyBean myBean = myBeans.get(position);
            viewHolder.item_content.setText(myBean.getName());
            viewHolder.item_content.setTag(position);
            viewHolder.item_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int posi = (int) v.getTag();
                    MyBean myBean1 = myBeans.get(posi);
                    Toast.makeText(MainActivity.this, "myBean=" + myBean1.getName(), LENGTH_SHORT).show();
                }
            });
            viewHolder.item_menu.setTag(position);
            viewHolder.item_menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SlideLayout slideLayout1 = (SlideLayout) v.getParent();
                    slideLayout1.closeMenu();
                    int posi = (int) v.getTag();
                    myBeans.remove(posi);
                    notifyDataSetChanged();
                }
            });
            SlideLayout slideLayout2 = (SlideLayout) convertView;
            slideLayout2.setOnStateChangeListener(new MyOnStateChangeListener());
            return convertView;
        }
    }
    
    private SlideLayout slideLayout;
    
    class MyOnStateChangeListener implements SlideLayout.OnStateChangeListener {

        @Override
        public void OnDown(SlideLayout layout) {
            //当不相同才关闭
            Log.e("TAG", "OnDown");
            if(slideLayout != null && slideLayout != layout) {
                Log.e("TAG", "OnDown生效");
                slideLayout.closeMenu();
            }
        }

        @Override
        public void OnOpen(SlideLayout layout) {
            //记录谁是打开的
            Log.e("TAG", "OnOpen");
            slideLayout = layout;
        }

        @Override
        public void OnClose(SlideLayout layout) {
            if(slideLayout == layout) {
                Log.e("TAG", "OnClose");
                Log.e("TAG", ""+(slideLayout == layout));
                slideLayout = null;
            }
        }
    }

    static class ViewHolder {
        TextView item_content;
        TextView item_menu;
    }


}
