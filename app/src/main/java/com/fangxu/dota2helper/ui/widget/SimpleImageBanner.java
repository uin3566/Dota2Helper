package com.fangxu.dota2helper.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
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
    private Context mContext;

    public SimpleImageBanner(Context context) {
        this(context, null, 0);
    }

    public SimpleImageBanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleImageBanner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
    }

    @Override
    public void onTitleSlect(TextView tv, int position) {

    }

    @Override
    public View onCreateItemView(int position) {
        ImageView imageView = new ImageView(mContext);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        NewsList.BannerEntity bannerEntity = mDatas.get(position);
        Glide.with(mContext).load(bannerEntity.getBackground()).into(imageView);
        return imageView;
    }
}
