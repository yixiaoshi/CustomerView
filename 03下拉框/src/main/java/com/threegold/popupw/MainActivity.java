package com.threegold.popupw;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText et_main;
    private ImageView iv_main_arrow;
    private PopupWindow popup;

    private List<String> msg;
    private ListView listView;
    private MyAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();

        initData();

        myAdapter = new MyAdapter();
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String mess = msg.get(position);
                et_main.setText(mess);
                if(popup != null && popup.isShowing())
                {
                    popup.dismiss();
                }

            }
        });
    }

    private void initData() {
        msg = new ArrayList<>();
        for(int i = 0; i < 200; i++) {
            msg.add(i+"aaaaaaaa"+i);
        }
    }

    private void initView() {
        setContentView(R.layout.activity_main);
        et_main = (EditText)findViewById(R.id.et_main);
        iv_main_arrow = (ImageView)findViewById(R.id.iv_main_arrow);

        listView = new ListView(this);

        listView.setBackgroundResource(R.drawable.listview_background);

        listView.setVerticalScrollBarEnabled(false);

        iv_main_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(popup == null)
                {
                    popup = new PopupWindow(MainActivity.this);
                    popup.setWidth(et_main.getWidth());
                    popup.setHeight(DensityUtil.dip2px(MainActivity.this,200));
                    //设置PopupWindow里面装的视图
                    popup.setContentView(listView);
                    popup.setFocusable(true); //设置焦点
                }

                popup.showAsDropDown(et_main,0,0);
            }
        });
    }

    private class MyAdapter extends BaseAdapter{
        private LayoutInflater mInflater;
        public MyAdapter()
        {
            mInflater = LayoutInflater.from(MainActivity.this);
        }
        @Override
        public int getCount() {
            return msg.size();
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
        public View getView(int position, View convertView, ViewGroup parent)
        {
            ViewHolder viewHolder = null;
            if(convertView == null)
            {
                convertView = mInflater.inflate(R.layout.base_item,null);
                viewHolder = new ViewHolder();
                viewHolder.tv_holder_num = (TextView) convertView.findViewById(R.id.tv_item_num);
                viewHolder.iv_holder_delete = (ImageView) convertView.findViewById(R.id.iv_item_delete);
                convertView.setTag(viewHolder);
            }
            else
                viewHolder = (ViewHolder) convertView.getTag();
            final String itemData = msg.get(position);
            viewHolder.tv_holder_num.setText(itemData);
            viewHolder.iv_holder_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    msg.remove(itemData);
                    notifyDataSetChanged();
                }
            });
            return convertView;
        }

    }
    static class ViewHolder
    {
        TextView tv_holder_num;
        ImageView iv_holder_delete;
    }
}
