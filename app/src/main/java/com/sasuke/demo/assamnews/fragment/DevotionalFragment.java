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
import com.sasuke.demo.assamnews.adapter.DevotionalAdapter;
import com.sasuke.demo.assamnews.model.News;

import java.util.ArrayList;
import java.util.List;

public class DevotionalFragment extends Fragment {
    private RecyclerView recyclerView;
    private DevotionalAdapter devotionalAdapter;
    private List<News> devotionalList = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_devotional,container,false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView) view.findViewById(R.id.rvDevotional);
        devotionalAdapter = new DevotionalAdapter(devotionalList);
        RecyclerView.LayoutManager eLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(eLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(devotionalAdapter);
        recyclerView.addItemDecoration(new ItemDecorator(10)) ;
        prepareDevotionalData();
        initListener();
    }

    private  void  initListener(){
        devotionalAdapter.setOnItemClickListener(new DevotionalAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), DetailNewsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void prepareDevotionalData(){
        News devotional = new News("Aastha", R.drawable.aastha_tv_small);
        devotionalList.add(devotional);
        devotional = new News("Bhakti", R.drawable.bhakti_small);
        devotionalList.add(devotional);
        devotional = new News("Sanskar", R.drawable.sanskar_small);
        devotionalList.add(devotional);
        devotional = new News("Satsang", R.drawable.satsang_small);
        devotionalList.add(devotional);
        devotional = new News("Sadhna", R.drawable.sadhna_small);
        devotionalList.add(devotional);
        devotional = new News("Shalom", R.drawable.shalom_small);
        devotionalList.add(devotional);
        devotional = new News("Sri Sankara", R.drawable.srisankara_tv_small);
        devotionalList.add(devotional);
        devotional = new News("Sadhna", R.drawable.sadhna_small);
        devotionalList.add(devotional);
        devotional = new News("Shalom", R.drawable.shalom_small);
        devotionalList.add(devotional);

        devotionalAdapter.notifyDataSetChanged();
    }


}
