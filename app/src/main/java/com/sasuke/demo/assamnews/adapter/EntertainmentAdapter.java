package com.sasuke.demo.assamnews.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sasuke.demo.assamnews.R;
import com.sasuke.demo.assamnews.model.News;

import java.util.List;

public class EntertainmentAdapter extends RecyclerView.Adapter<EntertainmentAdapter.EntertainmentViewHolder> {

    private OnItemClickListener onItemClickListener;
    private List<News> entertainmentList;

    @NonNull
    @Override
    public EntertainmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_rv,parent,false);
        return new EntertainmentViewHolder(itemView,onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull EntertainmentViewHolder holder, int position) {
        News entertainment = entertainmentList.get(position);
        holder.title.setText(entertainment.getTitle());
        holder.entertainmentLogo.setImageResource(entertainment.getLogo());

    }

    @Override
    public int getItemCount() {
        return entertainmentList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public class EntertainmentViewHolder extends RecyclerView.ViewHolder implements ViewGroup.OnClickListener{

        public TextView title;
        public ImageView entertainmentLogo;

        private OnItemClickListener onItemClickListener;

        public EntertainmentViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);

            itemView.setOnClickListener(this);

            title = (TextView) itemView.findViewById(R.id.tvTitle);
            entertainmentLogo = (ImageView) itemView.findViewById(R.id.newsLogo);

            this.onItemClickListener = onItemClickListener;
        }

        @Override
        public void onClick(View view) {
            if(onItemClickListener != null){
                onItemClickListener.onItemClick(view,getAdapterPosition());
            }

        }
    }

    public EntertainmentAdapter(List<News> entertainmentList) { this.entertainmentList = entertainmentList;}
}
