package com.example.logindemo.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.logindemo.R;
import com.example.logindemo.adapter.NewsAdapter;
import com.example.logindemo.db.News;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class SchoolNewsActivity extends AppCompatActivity {

    private List<News> newList;
    private Handler handler;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_news);
        newList = new ArrayList<>();
        listView = (ListView) findViewById(R.id.news_list_view);

        getNews();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    NewsAdapter adapter = new NewsAdapter(SchoolNewsActivity.this, newList);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            News news = newList.get(position);
                            Intent intent = new Intent(SchoolNewsActivity.this, NewsDisplayActivity.class);
                            intent.putExtra("news_url", news.getNewsUrl());
                            startActivity(intent);
                        }
                    });
                }
            }
        };
    }

    private void getNews() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 1; i <= 1; i++) {
                        Document doc = Jsoup.connect("http://ty.58.com/jianzhi/1/?combination_id=1&PGTID=0d203675-002e-4305-9fb3-5e37fc6e2403&ClickID=1").get();
                        Elements titleLinks = doc.select("div.item1").select("h2").select("a");//标题
                        Elements urlLinks = doc.select("div.item1").select("h2").select("a");//链接
                        Elements desc = doc.select("div.item1").select("p");//地址
                        Elements timeLinks = doc.select("div.item2");//时间
                        Log.e("title", Integer.toString(titleLinks.size()));
                        for (int j = 0; j < titleLinks.size(); j++) {
                            String title = titleLinks.get(j).text();
                            String url = urlLinks.get(j).attr("href");
                            String des = desc.get(j).text();
                            String time = timeLinks.get(j).text();
                            News news = new News(title, url, des, time);
                            newList.add(news);
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

