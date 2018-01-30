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

import com.example.logindemo.R;
import com.example.logindemo.activity.NewsDisplayActivity;
import com.example.logindemo.adapter.NewsAdapter;
import com.example.logindemo.db.News;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private ListView listView;
    private List<News> newsList;
    private Handler handler;
    private NewsAdapter newsAdapter;

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
        return view;
    }

    private void getNews() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //获取虎扑新闻5页的数据，网址格式为：https://voice.hupu.com/nba/第几页
                    for (int i = 1; i <= 3; i++) {

                        org.jsoup.nodes.Document doc = Jsoup.connect("https://voice.hupu.com/nba/").get();
                        Elements titleLinks = doc.select("div.list-hd" );    //解析来获取每条新闻的标题与链接地址
                        Elements timeLinks = doc.select("div.otherInfo");   //解析来获取每条新闻的时间与来源
                        Log.e("title", Integer.toString(titleLinks.size()));
                        for (int j = 0; j < titleLinks.size(); j++) {
                            String title = titleLinks.get(j).select("a").text();
                            String uri = titleLinks.get(j).select("a").attr("href");
                            //String desc = descLinks.get(j).select("span").text();
                            String time = timeLinks.get(j).select("span.other-left").select("a").text();
                            News news = new News(title, uri, null, time);
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
