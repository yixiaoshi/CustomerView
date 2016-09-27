package com.threegold.customerindex;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lv_main_contacts;
    private TextView tv_main_word;
    private IndexView main_index_view;
    private Handler handler;
    private List<Person> persons;
    private MyAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv_main_contacts = (ListView)findViewById(R.id.lv_main_contacts);
        tv_main_word = (TextView)findViewById(R.id.tv_main_word);
        main_index_view = (IndexView)findViewById(R.id.main_index_view);
        handler = new Handler();
        persons = new ArrayList<>();
        myAdapter = new MyAdapter();
        initData();
        lv_main_contacts.setAdapter(myAdapter);
        main_index_view.setOnChangeTextListener(new IndexView.OnChangeTextListener() {
            @Override
            public void OnChangeText(String word) {
                if(word != null) {
                    updateListPosition(word);
                    updateWord(word);
                }else
                    Toast.makeText(MainActivity.this, "点击空--------", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateListPosition(String word) {
        for(int i = 0; i < persons.size(); i++) {
          String listWord = persons.get(i).getPinyin().substring(0,1);
            if(word.equals(listWord)) {
                lv_main_contacts.setSelection(i);
                break;
            }
        }
    }

    private void updateWord(String word) {
        tv_main_word.setVisibility(View.VISIBLE);
        tv_main_word.setText(word);
        handler.removeCallbacksAndMessages(null);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.e("TAG", "----"+Thread.currentThread().getName());
                tv_main_word.setVisibility(View.GONE);
            }
        },2000);
    }

    private class MyAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return persons.size();
        }

        @Override
        public Person getItem(int position) {
            return persons.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if(convertView == null){
                viewHolder = new ViewHolder();
                convertView = View.inflate(MainActivity.this,R.layout.item_base,null);
                viewHolder.holder_word = (TextView) convertView.findViewById(R.id.tv_base_word);
                viewHolder.holder_name = (TextView) convertView.findViewById(R.id.tv_base_name);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            String word = persons.get(position).getPinyin().substring(0,1);
            String pinyin = persons.get(position).getName();
            viewHolder.holder_word.setText(word);
            viewHolder.holder_name.setText(pinyin);
            if(position == 0){
                viewHolder.holder_word.setVisibility(View.VISIBLE);
            } else {
                String preWord = persons.get(position-1).getPinyin().substring(0,1);
                if(preWord.equals(word)) {
                    viewHolder.holder_word.setVisibility(View.GONE);
                }
                else{
                    viewHolder.holder_word.setVisibility(View.VISIBLE);
                }
            }
            return convertView;
        }
    }
    static class ViewHolder
    {
        TextView holder_word;
        TextView holder_name;
    }

    /**
     * 初始化数据
     */
    private void initData() {

        persons = new ArrayList<>();
        persons.add(new Person("张晓飞"));
        persons.add(new Person("杨光福"));
        persons.add(new Person("胡继群"));
        persons.add(new Person("波波"));

        persons.add(new Person("钟泽兴"));
        persons.add(new Person("尹革新"));
        persons.add(new Person("安传鑫"));
        persons.add(new Person("张骞壬"));

        persons.add(new Person("温松"));
        persons.add(new Person("单管"));
        persons.add(new Person("刘甫"));
        persons.add(new Person("娄全超"));
        persons.add(new Person("张猛"));

        persons.add(new Person("王英杰"));
        persons.add(new Person("凡振南"));
        persons.add(new Person("孙仁政"));
        persons.add(new Person("唐春雷"));
        persons.add(new Person("牛鹏伟"));
        persons.add(new Person("姜宇航"));

        persons.add(new Person("关门"));
        persons.add(new Person("张洪瑞"));
        persons.add(new Person("张建忠"));
        persons.add(new Person("侯亚帅"));
        persons.add(new Person("管帅"));

        persons.add(new Person("乔竞飞"));
        persons.add(new Person("徐雨健"));
        persons.add(new Person("吴亮"));
        persons.add(new Person("王兆霖"));

        persons.add(new Person("阿三"));
        persons.add(new Person("周娟"));
        persons.add(new Person("的哥"));


        //排序
        Collections.sort(persons, new Comparator<Person>() {
            @Override
            public int compare(Person lhs, Person rhs) {
                return lhs.getPinyin().compareTo(rhs.getPinyin());
            }
        });

    }


}
