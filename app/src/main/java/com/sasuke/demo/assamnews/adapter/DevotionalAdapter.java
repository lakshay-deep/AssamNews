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

public class DevotionalAdapter extends RecyclerView.Adapter<DevotionalAdapter.DevotionalViewHolder> {

    private OnItemClickListener onItemClickListener;
    private List<News> devotionalList;

    @NonNull
    @Override
    public DevotionalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_rv,parent,false);
        return new DevotionalViewHolder(itemView, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull DevotionalViewHolder holder, int position) {
        News devotional = devotionalList.get(position);
        holder.title.setText(devotional.getTitle());
        holder.devotionalLogo.setImageResource(devotional.getLogo());

    }

    @Override
    public int getItemCount() {
        return devotionalList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public class DevotionalViewHolder extends RecyclerView.ViewHolder implements ViewGroup.OnClickListener{

        public TextView title;
        public ImageView devotionalLogo;

        private OnItemClickListener onItemClickListener;

        public DevotionalViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);

            itemView.setOnClickListener(this);

            title = (TextView)itemView.findViewById(R.id.tvTitle);
            devotionalLogo = (ImageView) itemView.findViewById(R.id.newsLogo);

            this.onItemClickListener = onItemClickListener;
        }

        @Override
        public void onClick(View view) {
            if(onItemClickListener != null){
                onItemClickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }
    public DevotionalAdapter(List<News> devotionalList){ this.devotionalList = devotionalList;}
}
