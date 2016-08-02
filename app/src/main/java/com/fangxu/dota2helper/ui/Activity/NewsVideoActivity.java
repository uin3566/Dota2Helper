package com.fangxu.dota2helper.ui.Activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.fangxu.dota2helper.R;
import com.fangxu.dota2helper.util.VideoCacheManager;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dear33 on 2016/7/14.
 */
public class NewsVideoActivity extends BaseVideoActivity {
    public static final String VIDEO_DESCRIPTION = "video_description";
    public static final String VIDEO_TITLE = "video_title";
    public static final String VIDEO_DATE = "video_date";

    @Bind(R.id.tv_title)
    TextView mTitleTextView;
    @Bind(R.id.tv_date)
    TextView mDateTextView;
    @Bind(R.id.tv_description)
    TextView mDescription;

    private String mTitle;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_news_video;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        String description = getIntent().getStringExtra(VIDEO_DESCRIPTION);
        setStringText(description, mDescription);
        String date = getIntent().getStringExtra(VIDEO_DATE);
        mTitle = getIntent().getStringExtra(VIDEO_TITLE);
        mTitleTextView.setText(mTitle);
        setStringText(date, mDateTextView);
    }

    @Override
    protected void autoPlay() {
        super.autoPlay();
        if (mVid != null) {
            mYoukuPlayer.playVideo(mVid);
            mBlurImageContainer.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            ButterKnife.findById(this, R.id.v_divider).setVisibility(View.GONE);
            mDateTextView.setVisibility(View.GONE);
            mDescription.setVisibility(View.GONE);
            mTitleTextView.setVisibility(View.GONE);
        }
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            ButterKnife.findById(this, R.id.v_divider).setVisibility(View.VISIBLE);
            mDateTextView.setVisibility(View.VISIBLE);
            mDescription.setVisibility(View.VISIBLE);
            mTitleTextView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void cacheWatchedVideo() {
        super.cacheWatchedVideo();
        if (mIsVideoStarted || mIsVideoEnded) {
            VideoCacheManager.INSTANCE.cacheWatchedVideo(mVid, mBackgroundUrl, mTitle
                    , mVideoDurationMillis, mCurrentPlayTimeMills, mIsVideoEnded);
        }
    }

    private void setStringText(String str, TextView textView) {
        if (str == null || str.isEmpty()) {
            textView.setVisibility(View.GONE);
        } else {
            textView.setVisibility(View.VISIBLE);
            textView.setText(str);
        }
    }
}
