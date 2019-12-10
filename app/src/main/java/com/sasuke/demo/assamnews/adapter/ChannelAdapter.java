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

public class ChannelAdapter extends RecyclerView.Adapter<ChannelAdapter.ChannelViewHolder> {
    private List<News> channelList;
    private OnItemClickListener onItemClickListener;

    @NonNull
    @Override
    public ChannelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_channel,parent,false);
        return new ChannelViewHolder(itemView, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ChannelViewHolder holder, int position) {
        News channel = channelList.get(position);
//        holder.title.setText(channel.getTitle());
        holder.channelLogo.setImageResource(channel.getLogo());

    }

    @Override
    public int getItemCount() {
        return channelList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public class ChannelViewHolder extends RecyclerView.ViewHolder implements ViewGroup.OnClickListener{

        public TextView title;
        public ImageView channelLogo;
        private OnItemClickListener onItemClickListener;

        public ChannelViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);


            itemView.setOnClickListener(this);

//            title= itemView.findViewById(R.id.tvTitle);
            channelLogo = itemView.findViewById(R.id.tvLogo);

            this.onItemClickListener = onItemClickListener;
        }

        @Override
        public void onClick(View view) {
            if(onItemClickListener != null){
                onItemClickListener.onItemClick(view, getAdapterPosition());
            }

        }
    }
    public ChannelAdapter(List<News> channelList) { this.channelList = channelList;}
}
