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

import com.sasuke.demo.assamnews.activity.ItemDecorator;
import com.sasuke.demo.assamnews.R;
import com.sasuke.demo.assamnews.activity.TemActivity;
import com.sasuke.demo.assamnews.activity.TempActivity;
import com.sasuke.demo.assamnews.adapter.EntertainmentAdapter;
import com.sasuke.demo.assamnews.model.News;

import java.util.ArrayList;
import java.util.List;

public class EntertainmentFragment extends Fragment {

    private RecyclerView recyclerView;
    private EntertainmentAdapter entertainmentAdapter;
    private List<News> entertainmentList = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_entertainment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView) view.findViewById(R.id.rvEntertainment);
        entertainmentAdapter = new EntertainmentAdapter(entertainmentList);
        RecyclerView.LayoutManager eLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(eLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(entertainmentAdapter);
        recyclerView.addItemDecoration(new ItemDecorator(10)) ;
        prepareEntertainmentData();
        initListener();
    }

    private  void  initListener(){
        entertainmentAdapter.setOnItemClickListener(new EntertainmentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), TempActivity.class);
                startActivity(intent);
            }
        });
    }

    private void prepareEntertainmentData(){
        News entertainment = new News("Rang", R.drawable.rang_small);
        entertainmentList.add(entertainment);
        entertainment = new News("Sony", R.drawable.sony_small);
        entertainmentList.add(entertainment);
        entertainment = new News("SetMax", R.drawable.set_max_small);
        entertainmentList.add(entertainment);
        entertainment = new News("Star Plus", R.drawable.star_small);
        entertainmentList.add(entertainment);
        entertainment = new News("Zee Tv", R.drawable.zee_tv_small);
        entertainmentList.add(entertainment);
        entertainment = new News("Colors", R.drawable.colors_small);
        entertainmentList.add(entertainment);
        entertainment = new News("Mtv", R.drawable.mtv_small);
        entertainmentList.add(entertainment);
        entertainment = new News("Colors", R.drawable.colors_small);
        entertainmentList.add(entertainment);
        entertainment = new News("Mtv", R.drawable.mtv_small);
        entertainmentList.add(entertainment);

        entertainmentAdapter.notifyDataSetChanged();
    }
}
