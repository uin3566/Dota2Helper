package com.fangxu.dota2helper.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fangxu.dota2helper.R;
import com.fangxu.dota2helper.bean.NewsList;
import com.flyco.banner.widget.Banner.BaseIndicatorBanner;

import java.util.List;

/**
 * Created by Administrator on 2016/7/12.
 */
public class SimpleImageBanner extends BaseIndicatorBanner<NewsList.BannerEntity, SimpleImageBanner> {
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
    public void onTitleSlect(TextView tv, int position) {

    }

    @Override
    public View onCreateItemView(int position) {
        View view = View.inflate(mContext, R.layout.layout_banner_item, null);
        ImageView imageView = (ImageView)view.findViewById(R.id.iv_banner);
        NewsList.BannerEntity bannerEntity = mDatas.get(position);
        Glide.with(mContext).load(bannerEntity.getBackground()).placeholder(R.drawable.img_background_default).into(imageView);
        return view;
    }
}
