package com.sasuke.demo.assamnews.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sasuke.demo.assamnews.activity.DetailNewsActivity;
import com.sasuke.demo.assamnews.activity.ItemDecorator;
import com.sasuke.demo.assamnews.R;
import com.sasuke.demo.assamnews.adapter.NewsAdapter;
import com.sasuke.demo.assamnews.model.News;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {
    private RecyclerView recyclerView;
    private NewsAdapter newsAdapter;
    private List<News> newsList = new ArrayList<>();

    public NewsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView) view.findViewById(R.id.rvNews);
        newsAdapter = new NewsAdapter(newsList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(newsAdapter);
        recyclerView.addItemDecoration(new ItemDecorator(10));
        initListener();
        prepareNewsData();
    }


    private void initListener(){
        newsAdapter.setOnItemClickListener(new NewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Intent intent = new Intent(getActivity(), DetailNewsActivity.class);
//                News news = newsList.get(position);
//                intent.putExtra("title", news.getTitle());
//                intent.putExtra("logo", news.getLogo());
                startActivity(intent);
            }
        });
    }

    private void prepareNewsData(){
        News news = new News("Aaj Tak",R.drawable.aaj_tak_small );
        newsList.add(news);
        news = new News("PratedinTime", R.drawable.pratedin_small);
        newsList.add(news);
        news = new News("PragNews", R.drawable.prag_news_small);
        newsList.add(news);
        news = new News("DY365",R.drawable.dy365_small);
        newsList.add(news);
        news = new News("News18 Assam", R.drawable.news18assam_small);
        newsList.add(news);
        news = new News("NEWS24", R.drawable.news24_small);
        newsList.add(news);
        news = new News("Assam Talks",R.drawable.assamtalks_small);
        newsList.add(news);
        news = new News("CNBC",R.drawable.cnbc_small);
        newsList.add(news);
        news = new News("Ramdhenu",R.drawable.ramdhenu_small);
        newsList.add(news);
        news = new News("ZEE HINDUSTAN ",R.drawable.zee_hindustan);
        newsList.add(news);
        news = new News("NEWS NATION ",R.drawable.news_nation_small);
        newsList.add(news);
        news = new News("ZEE NEWS ",R.drawable.zee_news_small);
        newsList.add(news);
        news = new News("NDTV ",R.drawable.ndtv_small);
        newsList.add(news);

        newsAdapter.notifyDataSetChanged();
    }
}
