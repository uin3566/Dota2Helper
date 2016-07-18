package com.fangxu.dota2helper.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fangxu.dota2helper.R;
import com.fangxu.dota2helper.bean.NewsList;
import com.flyco.banner.widget.Banner.BaseIndicatorBanner;
import com.flyco.banner.widget.Banner.base.BaseBanner;

/**
 * Created by Administrator on 2016/7/12.
 */
public class SimpleImageBanner extends BaseIndicatorBanner<NewsList.NewsEntity, SimpleImageBanner> {
    private long lastClickTime = 0;

    public SimpleImageBanner(Context context) {
        this(context, null, 0);
    }

    public SimpleImageBanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleImageBanner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setOnItemClickL(OnItemClickL onItemClickL) {
        long interval = System.currentTimeMillis() - lastClickTime;
        if (interval > 500) {
            lastClickTime = System.currentTimeMillis();
            super.setOnItemClickL(onItemClickL);
        }
    }

    @Override
    public void onTitleSlect(TextView tv, int position) {
        NewsList.NewsEntity bannerEntity = mDatas.get(position);
        tv.setText(bannerEntity.getTitle());
    }

    @Override
    public View onCreateItemView(int position) {
        View view = View.inflate(mContext, R.layout.layout_banner_item, null);
        ImageView imageView = (ImageView)view.findViewById(R.id.iv_banner);
        NewsList.NewsEntity bannerEntity = mDatas.get(position);
        Glide.with(mContext).load(bannerEntity.getBackground()).placeholder(R.drawable.img_background_default).into(imageView);
        return view;
    }
}
