package com.fangxu.dota2helper.ui.adapter;

import android.content.Context;
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

/**
 * Created by Administrator on 2016/6/21.
 */
public class NewsAdapter extends CommonRecyclerAdapter<NewsList.NewsEntity> {
    private List<NewsList.BannerEntity> mBannerEntityList;
    private BannerHolder mBannerHolder;

    public NewsAdapter(Context context) {
        super(context);
    }

    public void setBanner(List<NewsList.BannerEntity> bannerEntityList) {
        mBannerEntityList = bannerEntityList;
        if (mBannerEntityList == null || mBannerEntityList.isEmpty()) {
            setHasHeader(false);
        } else {
            setHasHeader(true);
        }
        notifyDataSetChanged();
    }

    public void updateData(List<NewsList.NewsEntity> newsEntityList, boolean append) {
        if (!append) {
            mData.clear();
        }
        mData.addAll(newsEntityList);
        notifyDataSetChanged();
    }

    @Override
    protected void clickHeader() {
        super.clickHeader();
        FlipperBanner flipperBanner = (FlipperBanner)mBannerHolder.itemView;
        flipperBanner.clickBanner();
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == ITEM_HEADER) {
            view = mLayoutInflater.inflate(R.layout.layout_banner, parent, false);
            mBannerHolder = new BannerHolder(view);
            return mBannerHolder;
        } else if (viewType == ITEM_NORMAL) {
            view = mLayoutInflater.inflate(R.layout.item_news, parent, false);
            return new NewsViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }

    public class BannerHolder extends CommonViewHolder {
        @Bind(R.id.banner)
        FlipperBanner mFlipperBanner;

        public BannerHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void fillView(int position) {
            mFlipperBanner.setBanner(mBannerEntityList);
        }
    }

    public class NewsViewHolder extends CommonViewHolder {
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
        }

        @Override
        public void fillView(int position) {
            NewsList.NewsEntity newsEntity = getItem(position);
            Glide.with(mContext).load(newsEntity.getBackground()).placeholder(R.drawable.image_background_default).into(mBackground);
            mTitle.setText(newsEntity.getTitle());
            mDescription.setText(newsEntity.getDescription());
            mTime.setText(newsEntity.getTime());
        }
    }
}
