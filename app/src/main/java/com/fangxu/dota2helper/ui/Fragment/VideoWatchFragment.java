package com.fangxu.dota2helper.ui.Fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fangxu.dota2helper.R;
import com.youku.player.base.YoukuBasePlayerManager;
import com.youku.player.base.YoukuPlayer;
import com.youku.player.base.YoukuPlayerView;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/4/20.
 */
public class VideoWatchFragment extends BaseFragment {
    public static final String DATE = "video_date";
    public static final String VID = "video_vid";
    public static final String YKVID = "video_ykvid";

    @Bind(R.id.youku_player)
    YoukuPlayerView mPlayerView;

    private YoukuBasePlayerManager mBasePlayerManager;
    private YoukuPlayer mPlayer;

    private String mVid;
    private String mDate;
    private String mYkvid;

    public static VideoWatchFragment newInstance(Bundle data) {
        VideoWatchFragment fragment = new VideoWatchFragment();
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public void init() {
        mVid = getArguments().getString(VID);
        mDate = getArguments().getString(DATE);
        mYkvid = getArguments().getString(YKVID);
    }

    @Override
    protected void onFragmentFirstVisible() {
        mBasePlayerManager = new YoukuBasePlayerManager(getActivity()) {
            @Override
            public void setPadHorizontalLayout() {

            }

            @Override
            public void onInitializationSuccess(YoukuPlayer player) {
                addPlugins();
                mPlayer = player;
                goPlay();
            }

            @Override
            public void onFullscreenListener() {

            }

            @Override
            public void onSmallscreenListener() {

            }
        };
        mBasePlayerManager.onCreate();

        mPlayerView.setSmallScreenLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT));
        mPlayerView.setFullScreenLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT));
        mPlayerView.initialize(mBasePlayerManager);
    }

    @Override
    public int getResourceId() {
        return R.layout.fragment_video_watch;
    }

    @Override
    public void initView(View view) {

    }

    private void goPlay() {
        if (!mYkvid.isEmpty()) {
            mPlayer.playVideo(mYkvid);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        if (mBasePlayerManager != null) {
            mBasePlayerManager.onDestroy();
        }
        super.onDestroy();
    }
}
