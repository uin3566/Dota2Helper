package com.fangxu.dota2helper.ui.Activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fangxu.dota2helper.R;
import com.fangxu.dota2helper.bean.VideoSetList;
import com.fangxu.dota2helper.presenter.IVideoDetailView;
import com.fangxu.dota2helper.presenter.VideoDetailPresenter;
import com.fangxu.dota2helper.ui.view.YoukuPluginPlayer;
import com.fangxu.dota2helper.util.BlurTransformation;
import com.fangxu.dota2helper.util.ToastUtil;
import com.youku.player.base.YoukuBasePlayerManager;
import com.youku.player.base.YoukuPlayer;
import com.youku.player.base.YoukuPlayerView;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/4/20.
 */
public class VideoPlayerActivity extends BaseActivity implements IVideoDetailView {
    public static final String VIDEO_DATE = "video_date";
    public static final String VIDEO_VID = "video_nid";
    public static final String VIDEO_BACKGROUND = "video_background";

    @Bind(R.id.youku_player)
    YoukuPlayerView mYoukuPlayerView;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.rl_blur_container)
    RelativeLayout mBlurImageContainer;
    @Bind(R.id.iv_blur)
    ImageView mBlurImageView;
    @Bind(R.id.tv_up)
    TextView mUp;
    @Bind(R.id.tv_down)
    TextView mDown;
    @Bind(R.id.tv_watch_count)
    TextView mWatchCount;

    private YoukuBasePlayerManager mYoukuBasePlayerManager;
    private YoukuPlayer mYoukuPlayer;

    private String mVid = null;
    private boolean mIsPlayerReady = false;

    private VideoDetailPresenter mPresenter;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_video_detail;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        mToolbar.setTitle(R.string.video_detail);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        mPresenter = new VideoDetailPresenter(this);
        queryVideoSetInfo();
        initPlayer();
    }

    private void initPlayer() {
        final String backgroundUrl = getIntent().getStringExtra(VIDEO_BACKGROUND);
        Glide.with(this).load(backgroundUrl).asBitmap().placeholder(R.color.black).transform(new BlurTransformation(this, 20)).into(mBlurImageView);
        mYoukuBasePlayerManager = new YoukuBasePlayerManager(this) {
            @Override
            public void setPadHorizontalLayout() {
                // TODO Auto-generated method stub

            }

            @Override
            public void onInitializationSuccess(YoukuPlayer player) {
                // TODO Auto-generated method stub
                YoukuPluginPlayer pluginPlayer = new YoukuPluginPlayer(this, mediaPlayerDelegate, backgroundUrl);
                addPlugins(pluginPlayer);
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

        mYoukuPlayerView.setSmallScreenLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        mYoukuPlayerView.setFullScreenLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        mYoukuPlayerView.initialize(mYoukuBasePlayerManager);
    }

    private void queryVideoSetInfo() {
        String date = getIntent().getStringExtra(VIDEO_DATE);
        String vid = getIntent().getStringExtra(VIDEO_VID);
        mPresenter.queryVideoSetInformation(date, vid);
    }

    private void queryVideoDetail(String youkuVid) {
        mPresenter.queryYoukuVideoDetail(youkuVid);
    }

    @Override
    public void setVideoSet(VideoSetList videoSetList) {
        mVid = videoSetList.getYoukuvid();
        queryVideoDetail(mVid);
    }

    @OnClick(R.id.iv_play)
    public void onClickPlay(ImageView imageView) {
        if (mIsPlayerReady && mVid != null) {
            mYoukuPlayer.playVideo(mVid);
            mBlurImageContainer.setVisibility(View.INVISIBLE);
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
    public void setVideoDetail(String watchedCount, String upCount, String downCount) {
        mWatchCount.setText(watchedCount);
        mUp.setText(upCount);
        mDown.setText(downCount);
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
    public void onBackPressed() {
        super.onBackPressed();
        mYoukuBasePlayerManager.onBackPressed();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mToolbar.setVisibility(View.GONE);
        } else {
            mToolbar.setVisibility(View.VISIBLE);
        }
        mYoukuBasePlayerManager.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mYoukuBasePlayerManager.onDestroy();
    }
}
