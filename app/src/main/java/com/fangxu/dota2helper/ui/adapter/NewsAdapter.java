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
import com.fangxu.dota2helper.ui.widget.FlipperBanner;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lenov0 on 2016/4/9.
 */
public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_BANNER = 1;

    private boolean mHasHeader;
    private List<NewsList.NewsEntity> mNewsEntityList = new ArrayList<>();
    private List<NewsList.BannerEntity> mBannerEntityList = new ArrayList<>();
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

    public void setBanner(List<NewsList.BannerEntity> bannerEntityList) {
        mBannerEntityList.clear();
        if (bannerEntityList != null && !bannerEntityList.isEmpty()) {
            mHasHeader = true;
            mBannerEntityList = bannerEntityList;
        } else {
            mHasHeader = false;
        }
        notifyDataSetChanged();
    }

    public void updateData(List<NewsList.NewsEntity> newsEntityList, boolean append) {
        if (!append) {
            mNewsEntityList.clear();
        }
        mNewsEntityList.addAll(newsEntityList);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        View view;
        if (viewType == TYPE_NORMAL) {
            view = mLayoutInflater.inflate(R.layout.item_news, parent, false);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemClick((String) view.getTag(R.id.tag_date), (String) view.getTag(R.id.tag_id));
                    }
                }
            });
            holder = new NewsViewHolder(view);
        } else {
            view = mLayoutInflater.inflate(R.layout.layout_banner, parent, false);
            holder = new BannerViewHolder(view);
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NewsViewHolder) {
            NewsList.NewsEntity newsEntity = mNewsEntityList.get(position);
            holder.itemView.setTag(R.id.tag_date, newsEntity.getDate());
            holder.itemView.setTag(R.id.tag_id, newsEntity.getNid());
            ((NewsViewHolder)holder).fillView(newsEntity);
        }
        if (holder instanceof BannerViewHolder) {
            ((BannerViewHolder) holder).fillView();
        }
    }

    @Override
    public int getItemCount() {
        return mNewsEntityList == null ? 0 : mNewsEntityList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mHasHeader && position == 0) {
            return TYPE_BANNER;
        } else {
            return TYPE_NORMAL;
        }
    }

    public class BannerViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.banner)
        FlipperBanner mFlipperBanner;

        public BannerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void fillView() {
            mFlipperBanner.setBanner(mBannerEntityList);
        }
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
            Glide.with(mContext).load(newsEntity.getBackground()).placeholder(R.drawable.image_background_default).into(mBackground);
            mTitle.setText(newsEntity.getTitle());
            mDescription.setText(newsEntity.getDescription());
            mTime.setText(newsEntity.getTime());
        }
    }
}
