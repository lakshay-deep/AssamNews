package com.sasuke.demo.assamnews.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sasuke.demo.assamnews.R;
import com.sasuke.demo.assamnews.model.News;

import java.util.List;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.DetailViewHolder> {
    private List<News> detailList;
    private OnItemClickListener onItemClickListener;



    @NonNull
    @Override
    public DetailAdapter.DetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_similar_view, parent,false);
        return new DetailViewHolder(itemView, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailAdapter.DetailViewHolder holder, int position) {
        News news  = detailList.get(position);
        holder.title.setText(news.getTitle());
        holder.newsLogo.setImageResource(news.getLogo());
//        holder.logo.setImageResource(detail.getLogo());

    }

    @Override
    public int getItemCount() {
        return detailList.size();

    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface  OnItemClickListener{
        void onItemClick(View view , int position);
    }

    public class DetailViewHolder extends RecyclerView.ViewHolder implements ViewGroup.OnClickListener{

        public TextView title;
        public ImageView newsLogo;
        //        public ImageView logo;
        private OnItemClickListener onItemClickListener;

        public DetailViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);

            itemView.setOnClickListener(this);

            title = (TextView) itemView.findViewById(R.id.tvTitle);
            newsLogo = (ImageView) itemView.findViewById(R.id.newsLogo);
//            logo = (ImageView) itemView.findViewById(R.id.ivLogo);

            this.onItemClickListener = onItemClickListener;
        }

        @Override
        public void onClick(View view) {
            if(onItemClickListener!= null){
                onItemClickListener.onItemClick(view, getAdapterPosition());
            }

        }
    }
    public DetailAdapter(List<News> detailList){
        this.detailList = detailList;
    }
}

