package com.fangxu.dota2helper.ui.Activity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ecloud.pulltozoomview.PullToZoomScrollViewEx;
import com.fangxu.dota2helper.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/6/27.
 */
public class ProfileActivity extends BaseActivity implements View.OnClickListener{
    @Bind(R.id.pull_to_zoom_scroll_view)
    PullToZoomScrollViewEx mZoomScrollViewEx;

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

        TextView toMyGithub = ButterKnife.findById(contentView, R.id.tv_my_github);
        TextView toMyProject = ButterKnife.findById(contentView, R.id.tv_my_project);
        toMyGithub.setOnClickListener(this);
        toMyProject.setOnClickListener(this);

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
        switch (view.getId()){
            case R.id.tv_my_github:
                MyWebPageActivity.toMyPageActivity(this, MyWebPageActivity.MY_GITHUB);
                break;
            case R.id.tv_my_project:
                MyWebPageActivity.toMyPageActivity(this, MyWebPageActivity.MY_PROJECT);
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
