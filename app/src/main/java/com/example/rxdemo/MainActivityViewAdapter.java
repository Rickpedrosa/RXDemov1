package com.example.rxdemo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;

import com.example.rxdemo.base.recycler.BaseListAdapter;
import com.example.rxdemo.base.recycler.BaseViewHolder;
import com.example.rxdemo.data.remote.pojos.anime_episode.AnimeEpisode;
import com.example.rxdemo.data.remote.pojos.anime_episode.Datum;
import com.example.rxdemo.databinding.ItemEpisodeBinding;

public class MainActivityViewAdapter extends BaseListAdapter<Datum, BaseViewHolder<Datum>> {

    MainActivityViewAdapter(DiffUtil.ItemCallback<Datum> diffUtilItemCallback) {
        super(diffUtilItemCallback);
    }

    @NonNull
    @Override
    public BaseViewHolder<Datum> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AnimeEpisodeViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_episode, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<Datum> holder, int position) {
        holder.bind(getItem(position));
    }

    class AnimeEpisodeViewHolder extends BaseViewHolder<Datum> {

        private ItemEpisodeBinding b;

        AnimeEpisodeViewHolder(ItemEpisodeBinding itemView) {
            super(itemView.getRoot(), getOnItemClickListener(), getOnItemLongClickListener());
            b = itemView;
        }

        @Override
        public void bind(Datum type) {
            b.textView.setText(String.valueOf(type.getAttributes().getNumber()));
            b.textView2.setText(type.getAttributes().getCanonicalTitle());
            b.textView3.setText(String.valueOf(type.getAttributes().getLength()).concat("mins"));
        }
    }
}
