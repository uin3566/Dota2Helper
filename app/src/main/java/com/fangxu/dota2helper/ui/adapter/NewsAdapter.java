package com.fangxu.dota2helper.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fangxu.dota2helper.R;
import com.fangxu.dota2helper.bean.NewsList;
import com.fangxu.dota2helper.eventbus.BannerItemClickEvent;
import com.fangxu.dota2helper.eventbus.BusProvider;
import com.fangxu.dota2helper.ui.Activity.ArticalDetailActivity;
import com.fangxu.dota2helper.ui.widget.SimpleImageBanner;
import com.flyco.banner.anim.select.ZoomInEnter;
import com.flyco.banner.widget.Banner.base.BaseBanner;

import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/6/21.
 */
public class NewsAdapter extends CommonRecyclerAdapter<NewsList.NewsEntity> {
    private List<NewsList.NewsEntity> mBannerEntityList;
    private BannerHolder mBannerHolder;

    public NewsAdapter(Context context) {
        super(context);
    }

    public void setBanner(List<NewsList.NewsEntity> bannerEntityList) {
        mBannerEntityList = bannerEntityList;
        if (mBannerEntityList == null || mBannerEntityList.isEmpty()) {
            setHasHeader(false);
            mBannerHolder = null;
        } else {
            setHasHeader(true);
        }
        notifyDataSetChanged();
        if (mBannerHolder != null) {
            mBannerHolder.update();
        }
    }

    public void updateData(List<NewsList.NewsEntity> newsEntityList, boolean append) {
        if (!append) {
            mData.clear();
        }
        mData.addAll(newsEntityList);
        notifyDataSetChanged();
    }

    public void resumeBanner() {
        if (mBannerHolder != null) {
            mBannerHolder.mBanner.startScroll();
        }
    }

    public void pauseBanner() {
        if (mBannerHolder != null) {
            mBannerHolder.mBanner.pauseScroll();
        }
    }

    @Override
    protected void onClickHeader() {
        super.onClickHeader();
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
        SimpleImageBanner mBanner;

        public BannerHolder(View itemView) {
            super(itemView);
        }

        public void update() {
            mBanner.setSource(mBannerEntityList).setSelectAnimClass(ZoomInEnter.class).startScroll();
            mBanner.setOnItemClickL(new BaseBanner.OnItemClickL() {
                @Override
                public void onItemClick(int i) {
                    NewsList.NewsEntity bannerEntity = mBannerEntityList.get(i);
                    BusProvider.getInstance().post(new BannerItemClickEvent(bannerEntity));
                }
            });
        }

        @Override
        public void fillView(int position) {

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
            Glide.with(mContext).load(newsEntity.getBackground()).placeholder(R.drawable.img_background_default).into(mBackground);
            mTitle.setText(newsEntity.getTitle());
            mDescription.setText(newsEntity.getDescription());
            mTime.setText(newsEntity.getTime());
        }
    }
}
