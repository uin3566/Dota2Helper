package com.fangxu.dota2helper.ui.Activity;

import android.content.res.Configuration;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.widget.GridLayout;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fangxu.dota2helper.R;
import com.fangxu.dota2helper.bean.RelatedVideoList;
import com.fangxu.dota2helper.bean.VideoSetList;
import com.fangxu.dota2helper.presenter.IVideoDetailView;
import com.fangxu.dota2helper.presenter.VideoDetailPresenter;
import com.fangxu.dota2helper.ui.adapter.RelatedVideoAdapter;
import com.fangxu.dota2helper.ui.widget.GoogleCircleProgressView;
import com.fangxu.dota2helper.ui.widget.ScrollListView;
import com.fangxu.dota2helper.ui.widget.SelectButton;
import com.fangxu.dota2helper.ui.widget.YoukuPluginPlayer;
import com.fangxu.dota2helper.util.BlurTransformation;
import com.fangxu.dota2helper.util.NumberConversion;
import com.fangxu.dota2helper.util.ToastUtil;
import com.youku.player.base.YoukuBasePlayerManager;
import com.youku.player.base.YoukuPlayer;
import com.youku.player.base.YoukuPlayerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/4/20.
 */
public class VideoPlayerActivity extends BaseActivity implements IVideoDetailView, RelatedVideoAdapter.RelatedVideoClickListener {
    public static final String VIDEO_TITLE = "video_title";
    public static final String VIDEO_PUBLISH_TIME = "video_publish_time";
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
    @Bind(R.id.tv_title)
    TextView mTitle;
    @Bind(R.id.tv_publish_time)
    TextView mPublishTime;
    @Bind(R.id.rl_anthology_container)
    RelativeLayout mAnthologyContainer;
    @Bind(R.id.grid_layout)
    GridLayout mGridLayout;
    @Bind(R.id.scroll_list_view)
    ScrollListView mListView;
    @Bind(R.id.scroll_view)
    ScrollView mScrollView;
    @Bind(R.id.fl_progress_container)
    FrameLayout mProgressContainer;
    @Bind(R.id.tv_empty_list)
    TextView mEmptyRelatedVideo;

    private YoukuBasePlayerManager mYoukuBasePlayerManager;
    private YoukuPlayer mYoukuPlayer;

    private String mVid = null;
    private boolean mIsPlayerReady = false;
    private int mCurrentSelectedIndex = 0;//当前选集序号
    private RelatedVideoAdapter mAdapter;

    private Map<Integer, String> mYoukuVidMap = new HashMap<>();

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

        mAdapter = new RelatedVideoAdapter(this, this);
        mListView.setAdapter(mAdapter);
        mListView.setFocusable(false);

        mTitle.setText(getIntent().getStringExtra(VIDEO_TITLE));
        mPublishTime.setText(getIntent().getStringExtra(VIDEO_PUBLISH_TIME));

        mPresenter = new VideoDetailPresenter(this, this);
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
                mToolbar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFullscreenListener() {
                // TODO Auto-generated method stub
                mToolbar.setVisibility(View.GONE);
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

    private void queryRelatedVideo(String youkuVid) {
        mPresenter.queryRelatedYoukuVideo(youkuVid);
    }

    private void queryDetailAndRelatedList(String youkuVid) {
        mPresenter.queryDetailAndRelated(youkuVid);
    }

    private void onSelectButtonClicked(SelectButton selectButton) {
        if (selectButton.getIndex() != mCurrentSelectedIndex) {
            mGridLayout.getChildAt(mCurrentSelectedIndex).setSelected(false);
            selectButton.setSelected(true);
            mCurrentSelectedIndex = selectButton.getIndex();
            mVid = mYoukuVidMap.get(mCurrentSelectedIndex);
            if (mVid == null) {
                ToastUtil.showToast(VideoPlayerActivity.this, "数据准备中，请稍后再试");
            } else {
                queryDetailAndRelatedList(mVid);
                mYoukuPlayer.playVideo(mVid);
                mBlurImageContainer.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void setVideoDetail(String title, String published, int watchedCount, int upCount, int downCount) {
        String watchCount = NumberConversion.bigNumber(watchedCount) + "次播放";
        String up = NumberConversion.bigNumber(upCount);
        String down = NumberConversion.bigNumber(downCount);
        String publishTime = "发布于" + published;
        setVideoDetail(title, publishTime, watchCount, up, down);
    }

    @Override
    public void hideProgressBar() {
        mProgressContainer.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onRelatedVideoClicked(RelatedVideoList.RelatedVideoEntity entity) {
        mVid = entity.getId();
        queryRelatedVideo(mVid);
        setVideoDetail(entity.getTitle(), entity.getPublished(), entity.getView_count(), entity.getUp_count(), entity.getDown_count());
        mYoukuPlayer.playVideo(mVid);
        mBlurImageContainer.setVisibility(View.INVISIBLE);
        mAnthologyContainer.setVisibility(View.GONE);
        mScrollView.smoothScrollTo(0, 0);
    }

    @Override
    public void setAnthologyGridGone() {
        mAnthologyContainer.setVisibility(View.GONE);
    }

    @Override
    public void setVideoList(List<VideoSetList.VideoDateVidEntity> videoList) {
        mCurrentSelectedIndex = 0;
        mAnthologyContainer.setVisibility(View.VISIBLE);
        for (int i = 0; i < videoList.size(); i++) {
            final SelectButton selectButton = new SelectButton(this);
            if (i == 0) {
                selectButton.setSelected(true);
            } else {
                selectButton.setSelected(false);
            }
            selectButton.setIndex(i);
            selectButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onSelectButtonClicked(selectButton);
                }
            });
            mGridLayout.addView(selectButton, i);
        }
    }

    @Override
    public void setRelatedVideoList(List<RelatedVideoList.RelatedVideoEntity> relatedVideoList) {
        mAdapter.setData(relatedVideoList);
    }

    @Override
    public void setNoRelatedVideo() {
        mEmptyRelatedVideo.setVisibility(View.VISIBLE);
    }

    @Override
    public void setYoukuVid(boolean queryVideoDetail, int index, String youkuVid) {
        if (queryVideoDetail) {
            mVid = youkuVid;
            mProgressContainer.setVisibility(View.VISIBLE);
            queryDetailAndRelatedList(mVid);
        }
        mYoukuVidMap.put(index, youkuVid);
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
    public void setVideoDetail(String title, String published, String watchedCount, String upCount, String downCount) {
        mWatchCount.setText(watchedCount);
        mUp.setText(upCount);
        mDown.setText(downCount);
        mTitle.setText(title);
        mPublishTime.setText(published);
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
        mYoukuBasePlayerManager.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onDestroy() {
        mYoukuBasePlayerManager.onDestroy();
        super.onDestroy();
    }
}
