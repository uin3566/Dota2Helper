package com.fangxu.dota2helper.ui.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.baseproject.utils.UIUtils;
import com.fangxu.dota2helper.R;
import com.youku.player.base.GoplayException;
import com.youku.player.base.YoukuBasePlayerManager;
import com.youku.player.plugin.PluginOverlay;
import com.youku.player.ui.interf.IMediaPlayerDelegate;
import com.youku.player.util.DetailMessage;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lenov0 on 2016/4/24.
 */
public class YoukuPluginPlayer extends PluginOverlay implements DetailMessage {

    @Bind(R.id.seek_loading_bg)
    LinearLayout mLoadingContainer;
    @Bind(R.id.ll_player_container)
    LinearLayout mRootContainer;

    private YoukuBasePlayerManager mBasePlayerManager;
    private Activity mActivity;
    private View mRoot;

    public YoukuPluginPlayer(YoukuBasePlayerManager basePlayerManager, IMediaPlayerDelegate mediaPlayerDelegate) {
        super(basePlayerManager.getBaseActivity(), mediaPlayerDelegate);
        mBasePlayerManager = basePlayerManager;
        mActivity = basePlayerManager.getBaseActivity();
        mRoot = LayoutInflater.from(mActivity).inflate(R.layout.layout_youku_plugin_player, null);
        addView(mRoot);
        initButterKnife();
        initPlayLayout();
    }

    private void initButterKnife() {
        ButterKnife.bind(this);
    }

    private void unInitButterKnife() {
        ButterKnife.unbind(this);
    }

    private void initPlayLayout() {
        if (null == mRoot)
            return;
        mLoadingContainer.setVisibility(View.GONE);
        mRootContainer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onContainerClick();
            }
        });
        if (UIUtils.hasHoneycomb()) {
            mContainerLayout
                    .setOnSystemUiVisibilityChangeListener(new OnSystemUiVisibilityChangeListener() {

                        @Override
                        public void onSystemUiVisibilityChange(int visibility) {
                            hideShowControl();
                        }
                    });
        }
        interactFrameLayout = (FrameLayout) containerView
                .findViewById(com.youku.player.ui.R.id.fl_interact);
        mContainerLayout.setClickable(false);

        controlLayout = (RelativeLayout) containerView
                .findViewById(com.youku.player.ui.R.id.layout_play_control);
        if (null != controlLayout)
            controlLayout.setVisibility(View.GONE);
        videoBar = (SeekBar) containerView
                .findViewById(com.youku.player.ui.R.id.sb_detail_play_progress);
        totalTime = (TextView) containerView.findViewById(com.youku.player.ui.R.id.total_time);
        currentTime = (TextView) containerView.findViewById(com.youku.player.ui.R.id.current_time);
        if (null != videoBar)
            videoBar.setOnSeekBarChangeListener(mBarChangeListener);
        play_pauseButton = (ImageButton) containerView
                .findViewById(com.youku.player.ui.R.id.ib_detail_play_control);
        play_pauseButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (null == mMediaPlayerDelegate)
                    return;
                if (isLoading) {
                    play_pauseButton
                            .setImageResource(com.youku.player.ui.R.drawable.play_btn_pause_big_detail_down);
                    return;
                }
                if (mMediaPlayerDelegate.isPlaying()) {
                    mMediaPlayerDelegate.pause();
                    if (!isLoading) {
                        play_pauseButton
                                .setImageResource(com.youku.player.ui.R.drawable.play_btn_play_big_detail);
                    } else {
                        play_pauseButton
                                .setImageResource(com.youku.player.ui.R.drawable.play_btn_play_big_detail_down);
                    }
                } else {
                    mMediaPlayerDelegate.start();
                    if (null != play_pauseButton)
                        if (!isLoading) {
                            play_pauseButton
                                    .setImageResource(com.youku.player.ui.R.drawable.play_btn_pause_big_detail);
                        } else {
                            play_pauseButton
                                    .setImageResource(com.youku.player.ui.R.drawable.play_btn_pause_big_detail_down);
                        }
                }
                if (isBack) {
                    isBack = false;
                    isLoading = true;
                    play_pauseButton
                            .setImageResource(com.youku.player.ui.R.drawable.play_btn_pause_big_detail_down);
                }
                userAction();
            }
        });
        full_screenButton = (ImageButton) containerView
                .findViewById(com.youku.player.ui.R.id.ib_detail_play_full);

        full_screenButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mMediaPlayerDelegate.isFullScreen) {
                    mMediaPlayerDelegate.goSmall();
                } else {
                    mMediaPlayerDelegate.goFullScreen();
                }

            }
        });
        playTitleTextView = (TextView) containerView
                .findViewById(com.youku.player.ui.R.id.tv_detail_play_title);
        titleLayoutPort = (LinearLayout) containerView
                .findViewById(com.youku.player.ui.R.id.layout_title);
        titleLayoutPort.setOnClickListener(null);

        initSeekLoading();
        if (null != mMediaPlayerDelegate
                && null != mMediaPlayerDelegate.videoInfo) {
            int duration = mMediaPlayerDelegate.videoInfo.getDurationMills();
            videoBar.setMax(duration);
        }
        userPlayButton = (ImageView) containerView
                .findViewById(com.youku.player.ui.R.id.ib_user_play);
        if (null != userPlayButton) {
            userPlayButton.setOnClickListener(userPlayClickListener);
            userPlayButton.setVisibility(View.GONE);
        }
        // videoBar.setMax(240000);
        if (null != mMediaPlayerDelegate)
            if (mMediaPlayerDelegate.isPlaying())
                play_pauseButton
                        .setImageResource(com.youku.player.ui.R.drawable.play_btn_pause_big_detail);
            else {
                play_pauseButton
                        .setImageResource(com.youku.player.ui.R.drawable.play_btn_play_big_detail);
            }
        initRetry();
        initEndPage();
        initLoadInfoPage();
    }

    @Override
    public void onBufferingUpdateListener(int percent) {

    }

    @Override
    public void onCompletionListener() {

    }

    @Override
    public boolean onErrorListener(int what, int extra) {
        return false;
    }

    @Override
    public void OnPreparedListener() {

    }

    @Override
    public void OnSeekCompleteListener() {

    }

    @Override
    public void OnVideoSizeChangedListener(int width, int height) {

    }

    @Override
    public void OnTimeoutListener() {

    }

    @Override
    public void OnCurrentPositionChangeListener(int currentPosition) {

    }

    @Override
    public void onLoadedListener() {

    }

    @Override
    public void onLoadingListener() {

    }

    @Override
    public void onNotifyChangeVideoQuality() {

    }

    @Override
    public void onRealVideoStarted() {

    }

    @Override
    public void onUp() {

    }

    @Override
    public void onDown() {

    }

    @Override
    public void onClearUpDownFav() {

    }

    @Override
    public void onFavor() {

    }

    @Override
    public void onUnFavor() {

    }

    @Override
    public void newVideo() {

    }

    @Override
    public void onVolumnUp() {

    }

    @Override
    public void onVolumnDown() {

    }

    @Override
    public void onMute(boolean mute) {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onVideoChange() {

    }

    @Override
    public void onVideoInfoGetting() {

    }

    @Override
    public void onVideoInfoGetted() {

    }

    @Override
    public void onRealVideoStart() {

    }

    @Override
    public void onPlayNoRightVideo(GoplayException e) {

    }

    @Override
    public void onPlayReleateNoRightVideo() {

    }

    @Override
    public void onADplaying() {

    }

    @Override
    public void onVideoInfoGetFail(boolean needRetry) {

    }

    @Override
    public void setVisible(boolean visible) {

    }

    @Override
    public void back() {

    }
}
