package com.fangxu.dota2helper.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.fangxu.dota2helper.R;
import com.fangxu.dota2helper.bean.NewsList;
import com.fangxu.dota2helper.ui.Activity.ArticalDetailActivity;
import com.fangxu.dota2helper.ui.widget.FlipperBanner;

import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/6/21.
 */
public class NewsAdapter extends CommonRecyclerAdapter<NewsList.NewsEntity> {
    private List<NewsList.BannerEntity> mBannerEntityList;
    private BannerHolder mBannerHolder;

    private static final long BANNER_SWITCH_TIME = 3000;

    public NewsAdapter(Context context) {
        super(context);
    }

    public void setBanner(List<NewsList.BannerEntity> bannerEntityList) {
        mBannerEntityList = bannerEntityList;
        if (mBannerEntityList == null || mBannerEntityList.isEmpty()) {
            setHasHeader(false);
            if (mBannerHolder != null) {
                mBannerHolder.mConvenientBanner.stopTurning();
            }
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

    public void destroy() {
        if (mBannerHolder != null) {
            mBannerHolder.mConvenientBanner.stopTurning();
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
        ConvenientBanner<NewsList.BannerEntity> mConvenientBanner;

        public BannerHolder(View itemView) {
            super(itemView);
            mConvenientBanner.setPages(new CBViewHolderCreator<BannerItemView>() {
                @Override
                public BannerItemView createHolder() {
                    return new BannerItemView();
                }
            }, mBannerEntityList)
                    .setPageIndicator(new int[]{R.drawable.dot_normal, R.drawable.dot_selected})
                    .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
                    .startTurning(BANNER_SWITCH_TIME);
        }

        @Override
        public void fillView(int position) {

        }
    }

    private class BannerItemView implements Holder<NewsList.BannerEntity> {
        private ImageView mImageView;

        @Override
        public View createView(Context context) {
            FrameLayout frameLayout = new FrameLayout(context);
            mImageView = new ImageView(context);
            mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            mImageView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            frameLayout.addView(mImageView);
            return frameLayout;
        }

        @Override
        public void UpdateUI(Context context, final int i, NewsList.BannerEntity bannerEntity) {
            Glide.with(context).load(bannerEntity.getBackground()).placeholder(R.drawable.img_background_default).into(mImageView);
            mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NewsList.BannerEntity bannerEntity = mBannerEntityList.get(i);
                    ArticalDetailActivity.toNewsDetailActivity(mContext, bannerEntity.getDate(), bannerEntity.getNid());
                }
            });
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
