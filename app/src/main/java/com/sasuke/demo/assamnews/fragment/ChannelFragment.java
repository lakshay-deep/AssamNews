package com.sasuke.demo.assamnews.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sasuke.demo.assamnews.activity.DetailNewsActivity;
import com.sasuke.demo.assamnews.activity.ItemDecorator;
import com.sasuke.demo.assamnews.R;
import com.sasuke.demo.assamnews.adapter.ChannelAdapter;
import com.sasuke.demo.assamnews.model.News;

import java.util.ArrayList;
import java.util.List;

public class ChannelFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private RecyclerView recyclerView;
    private ChannelAdapter channelAdapter;
    private List<News> channelList = new ArrayList<>();
    private List<News> devotionalList = new ArrayList<>();
    private List<News> entertainmentList = new ArrayList<>();
    private List<News> newsList = new ArrayList<>();

     @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_channel,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> dataAdapter =  ArrayAdapter.createFromResource(getActivity(),R.array.categories, R.layout.spinner_item);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(this);

        recyclerView = (RecyclerView) view.findViewById(R.id.rvChannel);
        channelAdapter = new ChannelAdapter(channelList);
        RecyclerView.LayoutManager eLayoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(eLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(channelAdapter);
        recyclerView.addItemDecoration(new ItemDecorator(2));
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

        String text = adapterView.getItemAtPosition(position).toString();
        Toast.makeText(adapterView.getContext(),text,Toast.LENGTH_LONG).show();

        if(position == 0){
            prepareChannelData();
            initListener();
        } else if(position == 1) {
            channelList.clear();
            prepareNewsData();
            initListener();
        }
        else if(position == 2){
            channelList.clear();
            prepareEntertainmentData();
            initListener();

        }
        else {
            channelList.clear();
            prepareDevotionalData();
            initListener();
        }

     }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {



    }

    private  void  initListener(){
        channelAdapter.setOnItemClickListener(new ChannelAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), DetailNewsActivity.class);
                startActivity(intent);
            }
        });
    }


    private void prepareChannelData(){
        News channel = new News("AAj Tak",R.drawable.aaj_tak_medium);
        channelList.add(channel);
        channel = new News("Pratedin Time", R.drawable.pratedin_medium);
        channelList.add(channel);
        channel = new News("Prag News", R.drawable.prag_news_medium);
        channelList.add(channel);
        channel = new News("DY365",R.drawable.dy365_medium);
        channelList.add(channel);
        channel = new News("News18 Assam", R.drawable.news18assam_medium);
        channelList.add(channel);
        channel = new News("News 24", R.drawable.news24_medium);
        channelList.add(channel);
        channel = new News("Assam Talks",R.drawable.assamtalks_medium);
        channelList.add(channel);
        channel = new News("CNBC", R.drawable.cnbc_medium);
        channelList.add(channel);
        channel = new News("Ramdhenu", R.drawable.ramdhenu_medium);
        channelList.add(channel);
        channel = new News("Zee Hindustan", R.drawable.zee_hindustan);
        channelList.add(channel);
        channel = new News("News Nation ",R.drawable.news_nation_medium);
        channelList.add(channel);
        channel = new News("Zee News", R.drawable.zee_news_medium);
        channelList.add(channel);
        channel = new News("NDTV", R.drawable.ndtv_medium);
        channelList.add(channel);
        channel = new News("Rang", R.drawable.rang_medium);
        channelList.add(channel);
        channel = new News("Sony", R.drawable.sony_medium);
        channelList.add(channel);
        channel = new News("Set Max ",R.drawable.set_max_medium);
        channelList.add(channel);
        channel = new News("Star Plus", R.drawable.star_medium);
        channelList.add(channel);
        channel = new News("Zee Tv", R.drawable.zee_tv_medium);
        channelList.add(channel);
        channel = new News("Colors", R.drawable.colors_medium);
        channelList.add(channel);
        channel = new News("&Tv",R.drawable.aaj_tak_medium);
        channelList.add(channel);
        channel = new News("Mtv", R.drawable.mtv_medium);
        channelList.add(channel);
        channel = new News("Aastha", R.drawable.aastha_tv_medium);
        channelList.add(channel);
        channel = new News("Bhakti ",R.drawable.bhakti_medium);
        channelList.add(channel);
        channel = new News("Sanskar", R.drawable.sanskar_medium);
        channelList.add(channel);
        channel = new News("Samskruthi", R.drawable.ramdhenu_medium);
        channelList.add(channel);
        channel = new News("Satsang ",R.drawable.satsang_medium);
        channelList.add(channel);
        channel = new News("Sadhna", R.drawable.sadhna_medium);
        channelList.add(channel);
        channel = new News("Shalom", R.drawable.shalom_medium);
        channelList.add(channel);
        channel = new News("Sri Sankara", R.drawable.srisankara_tv_medium);
        channelList.add(channel);

        channelAdapter.notifyDataSetChanged();

    }


    private void prepareNewsData(){
        News news = new News("Aaj Tak",R.drawable.aaj_tak_medium );
        channelList.add(news);
        news = new News("PratedinTime", R.drawable.pratedin_medium);
        channelList.add(news);
        news = new News("PragNews", R.drawable.prag_news_medium);
        channelList.add(news);
        news = new News("DY365",R.drawable.dy365_medium);
        channelList.add(news);
        news = new News("News18 Assam", R.drawable.news18assam_medium);
        channelList.add(news);
        news = new News("NEWS24", R.drawable.news24_medium);
        channelList.add(news);
        news = new News("Assam Talks",R.drawable.assamtalks_medium);
        channelList.add(news);
        news = new News("CNBC",R.drawable.cnbc_medium);
        channelList.add(news);
        news = new News("Ramdhenu",R.drawable.ramdhenu_medium);
        channelList.add(news);
        news = new News("ZEE HINDUSTAN ",R.drawable.zee_hindustan);
        channelList.add(news);
        news = new News("NEWS NATION ",R.drawable.news_nation_medium);
        channelList.add(news);
        news = new News("ZEE NEWS ",R.drawable.zee_news_medium);
        channelList.add(news);
        news = new News("NDTV ",R.drawable.ndtv_medium);
        channelList.add(news);
        channelAdapter.notifyDataSetChanged();
    }

    private void prepareEntertainmentData(){
        News entertainment = new News("Rang", R.drawable.rang_medium);
        channelList.add(entertainment);
        entertainment = new News("Sony", R.drawable.sony_medium);
        channelList.add(entertainment);
        entertainment = new News("SetMax", R.drawable.set_max_medium);
        channelList.add(entertainment);
        entertainment = new News("Star Plus", R.drawable.star_medium);
        channelList.add(entertainment);
        entertainment = new News("Zee Tv", R.drawable.zee_tv_medium);
        channelList.add(entertainment);
        entertainment = new News("Colors", R.drawable.colors_medium);
        channelList.add(entertainment);
        entertainment = new News("Mtv", R.drawable.mtv_medium);
        channelList.add(entertainment);

        channelAdapter.notifyDataSetChanged();
    }

    private void prepareDevotionalData(){
        News devotional = new News("Aastha", R.drawable.aastha_tv_medium);
        channelList.add(devotional);
        devotional = new News("Bhakti", R.drawable.bhakti_medium);
        channelList.add(devotional);
        devotional = new News("Sanskar", R.drawable.sanskar_medium);
        channelList.add(devotional);
        devotional = new News("Satsang", R.drawable.satsang_medium);
        channelList.add(devotional);
        devotional = new News("Sadhna", R.drawable.sadhna_medium);
        channelList.add(devotional);
        devotional = new News("Shalom", R.drawable.shalom_medium);
        channelList.add(devotional);
        devotional = new News("Sri Sankara", R.drawable.srisankara_tv_medium);
        channelList.add(devotional);

        channelAdapter.notifyDataSetChanged();
    }
}
