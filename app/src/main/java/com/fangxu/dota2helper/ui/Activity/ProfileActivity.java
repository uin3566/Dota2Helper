package com.fangxu.dota2helper.ui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.ecloud.pulltozoomview.PullToZoomScrollViewEx;
import com.fangxu.dota2helper.R;
import com.fangxu.dota2helper.ui.widget.ProfileItemLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/6/27.
 */
public class ProfileActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.pull_to_zoom_scroll_view)
    PullToZoomScrollViewEx mZoomScrollViewEx;

    private long mLastClickTime = 0;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_profile;
    }

    @Override
    public int getTitleResId() {
        return R.string.profile;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                destroy();
            }
        });

        View headView = new FrameLayout(this);
        View zoomView = LayoutInflater.from(this).inflate(R.layout.profile_zoom_view, null, false);
        View contentView = LayoutInflater.from(this).inflate(R.layout.profile_content_view, null, false);
        mZoomScrollViewEx.setHeaderView(headView);
        mZoomScrollViewEx.setZoomView(zoomView);
        mZoomScrollViewEx.setScrollContentView(contentView);

        ProfileItemLayout toMyGitHub = ButterKnife.findById(contentView, R.id.pil_my_github);
        ProfileItemLayout toMyProject = ButterKnife.findById(contentView, R.id.pil_my_project);
        ProfileItemLayout toWatchedVideo = ButterKnife.findById(contentView, R.id.pil_watched_video);
        toMyGitHub.setOnClickListener(this);
        toMyProject.setOnClickListener(this);
        toWatchedVideo.setOnClickListener(this);

        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        int mScreenWidth = localDisplayMetrics.widthPixels;
        LinearLayout.LayoutParams headerLayoutParams = new LinearLayout.LayoutParams(mScreenWidth, (int) (9.0f * (mScreenWidth / 16.0f)));
        mZoomScrollViewEx.setHeaderLayoutParams(headerLayoutParams);
    }

    @Override
    public boolean applySystemBarDrawable() {
        return true;
    }

    @Override
    public void onClick(View view) {
        if (System.currentTimeMillis() - mLastClickTime <= 500) {
            return;
        }
        mLastClickTime = System.currentTimeMillis();
        switch (view.getId()) {
            case R.id.pil_my_github:
                MyWebPageActivity.toMyPageActivity(this, MyWebPageActivity.MY_GITHUB);
                break;
            case R.id.pil_my_project:
                MyWebPageActivity.toMyPageActivity(this, MyWebPageActivity.MY_PROJECT);
                break;
            case R.id.pil_watched_video:
                Intent intent = new Intent(this, WatchedVideoListActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        destroy();
    }

    private void destroy() {
        setResult(RESULT_OK);
        finish();
    }
}
