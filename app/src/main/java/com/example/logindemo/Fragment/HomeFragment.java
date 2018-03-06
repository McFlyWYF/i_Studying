package com.example.logindemo.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.logindemo.R;
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

        return view;
    }

    private void getNews() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //获取虎扑新闻5页的数据，网址格式为：https://voice.hupu.com/nba/第几页
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
