package com.fangxu.dota2helper.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fangxu.dota2helper.R;
import com.fangxu.dota2helper.bean.NewsList;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lenov0 on 2016/4/9.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private List<NewsList.NewsEntity> mNewsEntityList;
    private Context mContext;

    public NewsAdapter(Context context) {
        mContext = context;
    }

    public void updateData(List<NewsList.NewsEntity> newsEntityList) {
        mNewsEntityList = newsEntityList;
        notifyDataSetChanged();
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NewsViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_news, parent, false));
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        holder.fillView(mNewsEntityList.get(position));
    }

    @Override
    public int getItemCount() {
        return mNewsEntityList == null ? 0 : mNewsEntityList.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_background)
        ImageView mBackground;
        @Bind(R.id.tv_title)
        TextView mTitle;
        @Bind(R.id.tv_description)
        TextView mDescription;
        @Bind(R.id.tv_time)
        TextView mTime;

        public NewsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void fillView(NewsList.NewsEntity newsEntity) {
            Glide.with(mContext).load(newsEntity.getBackground()).placeholder(R.drawable.news_background_default).into(mBackground);
            mTitle.setText(newsEntity.getTitle());
            mDescription.setText(newsEntity.getDescription());
            mTime.setText(newsEntity.getTime());
        }
    }
}
