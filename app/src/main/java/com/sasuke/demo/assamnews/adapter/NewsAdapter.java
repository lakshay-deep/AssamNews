package com.sasuke.demo.assamnews.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sasuke.demo.assamnews.R;
import com.sasuke.demo.assamnews.model.News;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private List<News> newsList;
    private OnItemClickListener onItemClickListener;

    @NonNull
    @Override
    public NewsAdapter.NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_rv,parent,false);
        return new NewsViewHolder(itemView, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.NewsViewHolder holder, int position) {
        News news = newsList.get(position);
        holder.title.setText(news.getTitle());
        holder.newslogo.setImageResource(news.getLogo());

    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(View view , int position);
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder implements ViewGroup.OnClickListener{

        private TextView title;
        private ImageView newslogo;

        private OnItemClickListener onItemClickListener;

        public NewsViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);

            itemView.setOnClickListener(this);

            title = (TextView) itemView.findViewById(R.id.tvTitle);
            newslogo = (ImageView) itemView.findViewById(R.id.newsLogo);

            this.onItemClickListener = onItemClickListener;
        }

        @Override
        public void onClick(View view) {
            if(onItemClickListener != null){
                onItemClickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    public NewsAdapter(List<News> newsList) {this.newsList = newsList;}
}
