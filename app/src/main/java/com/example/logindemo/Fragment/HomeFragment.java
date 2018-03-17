package com.example.logindemo.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.logindemo.R;
import com.example.logindemo.activity.ContentActivity;
import com.example.logindemo.activity.FindActivity;
import com.example.logindemo.activity.NewsDisplayActivity;
import com.example.logindemo.activity.SchoolNewsActivity;
import com.example.logindemo.adapter.NewsAdapter;
import com.example.logindemo.db.News;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private ListView listView;
    private List<News> newsList;
    private Handler handler;
    private NewsAdapter newsAdapter;
    private TextView news;
    private TextView find;
    private TextView information;
    private TextView activity;
    private SwipeRefreshLayout swipeRefreshLayout;
    String[] searchWeb;
    public String content;
    public String content1;
    public String content2;
    private static final String TAG = "MainActivity";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home_fragment, container, false);
        newsList = new ArrayList<>();
        listView = (ListView) view.findViewById(R.id.news_list_view);
        getNews();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    NewsAdapter adapter = new NewsAdapter(getActivity(), newsList);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            News news = newsList.get(position);
                            Intent intent = new Intent(getActivity(), NewsDisplayActivity.class);
                            intent.putExtra("news_url", news.getNewsUrl());
                            startActivity(intent);
                        }
                    });
                }
            }
        };

        news = (TextView) view.findViewById(R.id.news_text);
        find = (TextView) view.findViewById(R.id.find_text);
        information = (TextView) view.findViewById(R.id.information_text);
        activity = (TextView) view.findViewById(R.id.activity_text);

        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FindActivity.class);
                startActivity(intent);
            }
        });

        information.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), SchoolNewsActivity.class);
                startActivity(intent);
            }
        });

        ImageButton btnGo = (ImageButton) view.findViewById(R.id.btn_go);
        final EditText etSearch = (EditText) view.findViewById(R.id.et_search);
        etSearch.setInputType(InputType.TYPE_NULL);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                content1 = etSearch.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Spinner spinner = (Spinner) view.findViewById(R.id.sp_menu);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                searchWeb = getResources().getStringArray(R.array.search_web);
                if (searchWeb[position].equals("百度")) {
                    content = "http://www.baidu.com/s?wd=";
                }else if (searchWeb[position].equals("知乎")) {
                    content = "https://www.zhihu.com/signup?next=%2F";
                }else if (searchWeb[position].equals("CSDN")) {
                    content = "https://www.csdn.net/";
                }else if (searchWeb[position].equals("掘金")) {
                    content = "https://juejin.im/";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        content1 = etSearch.getText().toString();

        Log.i(TAG, content1);
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                content2 = content + content1;

                Intent intent = new Intent(getActivity().getApplicationContext(), ContentActivity.class);
                intent.putExtra("searchContent", content2);
                Toast.makeText(getActivity(), content2, Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

        return view;
    }
    private void getNews() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //获取腾讯新闻5页的数据，网址格式为：https://voice.hupu.com/nba/第几页
                    for (int i = 1; i <= 1; i++) {

                        Document doc = Jsoup.connect("http://news.qq.com/").get();
                        Elements titleLinks = doc.select("div.text");//解析来获取每条新闻的标题
                        Log.e("title", Integer.toString(titleLinks.size()));
                        for (int j = 0; j < titleLinks.size(); j++) {
                            String title = titleLinks.get(j).select("em").select("a").text();
                            String uri = titleLinks.get(j).select("em").select("a").attr("href");
                            News news = new News(title, uri, null, null);
                            newsList.add(news);
                        }
                    }
                    Message msg = new Message();
                    msg.what = 1;
                    handler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
