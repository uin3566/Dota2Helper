package com.fangxu.dota2helper.ui.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayout;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.fangxu.dota2helper.R;
import com.fangxu.dota2helper.bean.RelatedVideoList;
import com.fangxu.dota2helper.bean.VideoSetList;
import com.fangxu.dota2helper.callback.IVideoPlayerView;
import com.fangxu.dota2helper.presenter.VideoPlayerPresenter;
import com.fangxu.dota2helper.ui.adapter.RelatedVideoAdapter;
import com.fangxu.dota2helper.ui.widget.ScrollListView;
import com.fangxu.dota2helper.ui.widget.SelectButton;
import com.fangxu.dota2helper.util.NumberConversion;
import com.fangxu.dota2helper.util.ToastUtil;
import com.fangxu.dota2helper.util.VideoCacheManager;
import com.youku.service.download.DownloadManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/4/20.
 */
public class VideoPlayerActivity extends BaseVideoActivity implements IVideoPlayerView
        , RelatedVideoAdapter.RelatedVideoClickListener {
    public static final String VIDEO_VID = "video_nid";

    @Bind(R.id.tv_up)
    TextView mUp;
    @Bind(R.id.tv_down)
    TextView mDown;
    @Bind(R.id.tv_watch_count)
    TextView mWatchCount;
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
    @Bind(R.id.fl_empty_view)
    FrameLayout mEmptyBackground;
    @Bind(R.id.tv_empty_list)
    TextView mEmptyRelatedVideo;

    private int mCurrentSelectedIndex = 0;//当前选集序号
    private RelatedVideoAdapter mAdapter;

    private Map<Integer, String> mYoukuVidMap = new HashMap<>();

    private VideoPlayerPresenter mPresenter;

    public static void toVideoPlayerActivity(Context activity, String title, String date, String vid, String background, String ykvid) {
        Intent intent = new Intent(activity, VideoPlayerActivity.class);
        intent.putExtra(VideoPlayerActivity.VIDEO_TITLE, title);
        intent.putExtra(VideoPlayerActivity.VIDEO_DATE, date);
        intent.putExtra(VideoPlayerActivity.VIDEO_VID, vid);
        intent.putExtra(VideoPlayerActivity.VIDEO_BACKGROUND, background);
        intent.putExtra(VideoPlayerActivity.VIDEO_YOUKU_VID, ykvid);
        activity.startActivity(intent);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_video_detail;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        mAdapter = new RelatedVideoAdapter(this, this);
        mListView.setAdapter(mAdapter);
        mListView.setFocusable(false);

        mPresenter = new VideoPlayerPresenter(this, this);
        mVid = getIntent().getStringExtra(VIDEO_YOUKU_VID);
        queryVideoSetInfo();
        super.init(savedInstanceState);
    }

    private void queryVideoSetInfo() {
        String date = getIntent().getStringExtra(VIDEO_DATE);
        String vid = getIntent().getStringExtra(VIDEO_VID);
        if (date != null && vid != null) {
            mPresenter.queryVideoSetInformation(date, vid);
        } else {
            setAnthologyGridGone();
        }
    }

    private void queryRelatedVideo(String youkuVid) {
        mPresenter.queryRelatedYoukuVideo(youkuVid);
    }

    private void queryDetailAndRelatedList(String youkuVid) {
        mPresenter.queryDetailAndRelated(youkuVid);
    }

    private void onSelectButtonClicked(SelectButton selectButton) {
        if (selectButton.getIndex() != mCurrentSelectedIndex) {
            onVidWillChange();
            mVid = mYoukuVidMap.get(selectButton.getIndex());
            if (mVid == null || mVid.isEmpty()) {
                ToastUtil.showToast(VideoPlayerActivity.this, "数据准备中，请稍后再试");
            } else {
                mGridLayout.getChildAt(mCurrentSelectedIndex).setSelected(false);
                mCurrentSelectedIndex = selectButton.getIndex();
                selectButton.setSelected(true);
                queryDetailAndRelatedList(mVid);
                mYoukuPlayer.playVideo(mVid);
                mBlurImageContainer.setVisibility(View.INVISIBLE);
                onVidChanged();
            }
        }
    }

    private void setVideoDetail(String title, String published, int watchedCount, int upCount, int downCount) {
        mTitle = title;
        String watchCount = NumberConversion.bigNumber(watchedCount) + "次播放";
        String up = NumberConversion.bigNumber(upCount);
        String down = NumberConversion.bigNumber(downCount);
        String publishTime = "发布于" + published;
        setVideoDetail(title, publishTime, watchCount, up, down);
    }

    private void onVidWillChange() {
        cacheWatchedVideo();
    }

    private void onVidChanged() {
        mIsVideoStarted = false;
    }

    @OnClick(R.id.iv_download)
    public void onClickDownload(View view) {
        DownloadManager downloadManager = DownloadManager.getInstance();
        downloadManager.createDownload(mVid, mTitle, null);
    }

    @Override
    protected void cacheWatchedVideo() {
        super.cacheWatchedVideo();
        if (mIsVideoStarted || mIsVideoEnded) {
            VideoCacheManager.INSTANCE.cacheWatchedVideo(mVid, mBackgroundUrl, mTitle
                    , mVideoDurationMillis, mCurrentPlayTimeMills, mIsVideoEnded);
        }
    }

    @Override
    public void hideProgressBar() {
        mEmptyBackground.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onRelatedVideoClicked(RelatedVideoList.RelatedVideoEntity entity) {
        onVidWillChange();
        mVid = entity.getId();
        mTitle = entity.getTitle();
        mBackgroundUrl = entity.getThumbnail();
        queryRelatedVideo(mVid);
        setVideoDetail(entity.getTitle(), entity.getPublished(), entity.getView_count(), entity.getUp_count(), entity.getDown_count());
        mYoukuPlayer.playVideo(mVid);
        mBlurImageContainer.setVisibility(View.INVISIBLE);
        mAnthologyContainer.setVisibility(View.GONE);
        mScrollView.smoothScrollTo(0, 0);
        onVidChanged();
    }

    @Override
    public void setAnthologyGridGone() {
        mAnthologyContainer.setVisibility(View.GONE);
        setYoukuVid(true, 0, mVid);
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
        setYoukuVid(true, 0, mVid);
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
            mEmptyBackground.setVisibility(View.VISIBLE);
            queryDetailAndRelatedList(mVid);
        }
        mYoukuVidMap.put(index, youkuVid);
    }

    @Override
    public void onGetInfoFailed(String error) {
        ToastUtil.showToast(this, error);
    }

    @Override
    public void setVideoDetail(String title, String published, String watchedCount, String upCount, String downCount) {
        mWatchCount.setText(watchedCount);
        mUp.setText(upCount);
        mDown.setText(downCount);
        mTitleTextView.setText(title);
        mPublishTime.setText(published);
    }

    @Override
    protected void onDestroy() {
        mPresenter.destroy();
        super.onDestroy();
    }
}
