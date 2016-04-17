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

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lenov0 on 2016/4/9.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private List<NewsList.NewsEntity> mNewsEntityList = new ArrayList<>();
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    private ItemClickListener mItemClickListener = null;

    public interface ItemClickListener{
        void onItemClick(String date, String nid);
    }

    public NewsAdapter(Context context, ItemClickListener itemClickListener) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mItemClickListener = itemClickListener;
    }

    public void updateData(List<NewsList.NewsEntity> newsEntityList, boolean append) {
        if (!append) {
            mNewsEntityList.clear();
        }
        mNewsEntityList.addAll(newsEntityList);
        notifyDataSetChanged();
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_news, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick((String)view.getTag(R.id.tag_article_date), (String)view.getTag(R.id.tag_article_nid));
                }
            }
        });
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        NewsList.NewsEntity newsEntity = mNewsEntityList.get(position);
        holder.itemView.setTag(R.id.tag_article_date, newsEntity.getDate());
        holder.itemView.setTag(R.id.tag_article_nid, newsEntity.getNid());
        holder.fillView(newsEntity);
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
