package com.fangxu.dota2helper.ui.Activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.fangxu.dota2helper.R;
import com.fangxu.dota2helper.bean.VideoSetList;
import com.fangxu.dota2helper.presenter.IVideoDetailView;
import com.fangxu.dota2helper.presenter.VideoDetailPresenter;
import com.fangxu.dota2helper.util.ToastUtil;
import com.youku.player.base.YoukuBasePlayerManager;
import com.youku.player.base.YoukuPlayer;
import com.youku.player.base.YoukuPlayerView;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/4/20.
 */
public class VideoDetailActivity extends BaseActivity implements IVideoDetailView {
    public static final String VIDEO_DATE = "video_date";
    public static final String VIDEO_VID = "video_nid";

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.youku_player)
    YoukuPlayerView mYoukuPlayerView;

    private YoukuBasePlayerManager mYoukuBasePlayerManager;
    private YoukuPlayer mYoukuPlayer;
    private boolean mIsPlayerReady = false;

    private VideoDetailPresenter mPresenter;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_video_detail;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        mPresenter = new VideoDetailPresenter(this);

        mToolbar.setTitle(R.string.video_detail);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        queryVideoSetInfo();
        initPlayer();
    }

    private void initPlayer() {
        mYoukuBasePlayerManager = new YoukuBasePlayerManager(this) {
            @Override
            public void setPadHorizontalLayout() {
                // TODO Auto-generated method stub

            }

            @Override
            public void onInitializationSuccess(YoukuPlayer player) {
                // TODO Auto-generated method stub
                addPlugins();
                mYoukuPlayer = player;
                mIsPlayerReady = true;
            }

            @Override
            public void onSmallscreenListener() {
                // TODO Auto-generated method stub

            }

            @Override
            public void onFullscreenListener() {
                // TODO Auto-generated method stub

            }
        };
        mYoukuBasePlayerManager.onCreate();

        mYoukuPlayerView.setSmallScreenLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT));
        mYoukuPlayerView.setFullScreenLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT));
        mYoukuPlayerView.initialize(mYoukuBasePlayerManager);
    }

    private void queryVideoSetInfo() {
        String date = getIntent().getStringExtra(VIDEO_DATE);
        String vid = getIntent().getStringExtra(VIDEO_VID);
        mPresenter.queryVideoSetInformation(date, vid);
    }

    @Override
    public void setVideoSet(VideoSetList videoSetList) {
        ToastUtil.showToast(this, "success");
        if (mIsPlayerReady) {
            String vid = videoSetList.getYoukuvid();
            mYoukuPlayer.playVideo(vid);
        }
    }

    @Override
    public void onGetInfoFailed(String error) {
        ToastUtil.showToast(this, error);
    }

    @Override
    public void onVideoInvalid(String invalid) {
        ToastUtil.showToast(this, invalid);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mYoukuBasePlayerManager.onLowMemory();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mYoukuBasePlayerManager.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mYoukuBasePlayerManager.onResume();
    }

    @Override
    public boolean onSearchRequested() {
        return mYoukuBasePlayerManager.onSearchRequested();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mYoukuBasePlayerManager.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mYoukuBasePlayerManager.onStop();
    }

    @Override
    public void onBackPressed() { // android系统调用
        super.onBackPressed();
        mYoukuBasePlayerManager.onBackPressed();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mYoukuBasePlayerManager.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mYoukuBasePlayerManager.onDestroy();
    }
}
