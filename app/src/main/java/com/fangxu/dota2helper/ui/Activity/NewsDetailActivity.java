package com.fangxu.dota2helper.ui.Activity;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fangxu.dota2helper.R;
import com.fangxu.dota2helper.RxCenter;
import com.fangxu.dota2helper.callback.VideoStateCallback;
import com.fangxu.dota2helper.interactor.TaskIds;
import com.fangxu.dota2helper.network.AppNetWork;
import com.fangxu.dota2helper.ui.widget.YoukuPluginPlayer;
import com.fangxu.dota2helper.util.BlurTransformation;
import com.fangxu.dota2helper.util.ToastUtil;
import com.fangxu.dota2helper.util.VideoCacheManager;
import com.youku.player.base.YoukuBasePlayerManager;
import com.youku.player.base.YoukuPlayer;
import com.youku.player.base.YoukuPlayerView;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/8/5.
 */
public class NewsDetailActivity extends BaseActivity {
    public static final String NEWS_DATE = "news_date";
    public static final String NEWS_NID = "news_nid";
    public static final String VIDEO_TITLE = "video_title";
    public static final String VIDEO_DATE = "video_date";
    public static final String VIDEO_DESCRIPTION = "video_description";
    public static final String VIDEO_BACKGROUND = "video_background";

    private FrameLayout mWebViewContainer;
    private WebView mWebView;
    private ProgressBar mProgressBar;

    private YoukuPlayerView mYoukuPlayerView;
    private ImageView mBlurImageView;
    private RelativeLayout mBlurImageContainer;
    private TextView mTitleTextView;
    private TextView mDateTextView;
    private TextView mDescription;

    @Bind(R.id.vs_web)
    ViewStub mWebStub;
    @Bind(R.id.vs_video)
    ViewStub mVideoStub;

    private String mBackgroundUrl;
    private String mTitle;

    private YoukuBasePlayerManager mYoukuBasePlayerManager;
    private YoukuPluginPlayer mPluginPlayer;
    private YoukuPlayer mYoukuPlayer;

    private String mVid;
    private int mCurrentPlayTimeMills;
    private int mVideoDurationMillis;
    private boolean mIsVideoStarted = false;
    private boolean mIsVideoEnded = false;

    private boolean mVideoMode = false;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_news_detail;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mToolbar.setTitle(R.string.news_detail);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        String data = getIntent().getStringExtra(NEWS_DATE);
        String nid = getIntent().getStringExtra(NEWS_NID);
        loadDetail(this, data, nid);
    }

    private void loadDetail(final Context context, String date, String nid) {
        RxCenter.INSTANCE.getCompositeSubscription(TaskIds.newsDetailTaskId).add(AppNetWork.INSTANCE.getDetailsApi().getNewsDetail(date, nid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        if (s.length() < 20) {
                            mVideoMode = true;
                            mVideoStub.inflate();
                            loadVideo(s);
                        } else {
                            mVideoMode = false;
                            mWebStub.inflate();
                            loadWeb(s);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ToastUtil.showToast(context, throwable.getMessage());
                    }
                }));
    }

    private void findVideoViews() {
        mTitleTextView = ButterKnife.findById(this, R.id.tv_title);
        mDateTextView = ButterKnife.findById(this, R.id.tv_date);
        mDescription = ButterKnife.findById(this, R.id.tv_description);
        mBlurImageView = ButterKnife.findById(this, R.id.iv_blur);
        mBlurImageContainer = ButterKnife.findById(this, R.id.rl_blur_container);
        mYoukuPlayerView = ButterKnife.findById(this, R.id.youku_player);
    }

    private void loadVideo(final String ykvid) {
        findVideoViews();
        mVid = ykvid;
        mTitle = getIntent().getStringExtra(VIDEO_TITLE);
        mTitleTextView.setText(mTitle);
        mBackgroundUrl = getIntent().getStringExtra(VIDEO_BACKGROUND);
        String date = getIntent().getStringExtra(VIDEO_DATE);
        String description = getIntent().getStringExtra(VIDEO_DESCRIPTION);
        mDateTextView.setText(date);
        mDescription.setText(description);
        Glide.with(this).load(mBackgroundUrl).asBitmap().placeholder(R.color.black).transform(new BlurTransformation(this)).into(mBlurImageView);
        mYoukuBasePlayerManager = new YoukuBasePlayerManager(this) {
            @Override
            public void setPadHorizontalLayout() {
                // TODO Auto-generated method stub
            }

            @Override
            public void onInitializationSuccess(YoukuPlayer player) {
                // TODO Auto-generated method stub
                mPluginPlayer = new YoukuPluginPlayer(this, mediaPlayerDelegate, mBackgroundUrl);
                mPluginPlayer.setVideoStateCallback(new VideoStateListener());
                addPlugins(mPluginPlayer);
                mYoukuPlayer = player;
                if (ykvid != null) {
                    mYoukuPlayer.playVideo(ykvid);
                    mBlurImageContainer.setVisibility(View.INVISIBLE);
                }
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

    private class VideoStateListener implements VideoStateCallback {
        @Override
        public void onProgressChanged(int currentTimeMillis) {
            mCurrentPlayTimeMills = currentTimeMillis;
        }

        @Override
        public void onVideoStart(int durationMillis) {
            mVideoDurationMillis = durationMillis;
            mIsVideoStarted = true;
            mIsVideoEnded = false;
        }

        @Override
        public void onVideoEnd() {
            mIsVideoStarted = false;
            mIsVideoEnded = true;
        }
    }

    private void findWebViews() {
        mProgressBar = ButterKnife.findById(this, R.id.webview_progressbar);
        mWebViewContainer = ButterKnife.findById(this, R.id.webview_container);
        mWebView = new WebView(getApplicationContext());
        mWebViewContainer.addView(mWebView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    private void loadWeb(String html) {
        findWebViews();
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDefaultTextEncodingName("UTF-8");
        settings.setSupportZoom(false);
        settings.setPluginState(WebSettings.PluginState.ON);
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    mProgressBar.setVisibility(View.VISIBLE);
                    mProgressBar.setProgress(newProgress);
                }
            }
        });

        mWebView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });

        mWebView.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null);
    }

    @Override
    protected boolean applySystemBarDrawable() {
        return false;
    }

    @Override
    protected boolean applyTranslucentStatus() {
        return false;
    }

    @Override
    protected int getTitleResId() {
        return R.string.news_detail;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (mVideoMode) {
            mYoukuBasePlayerManager.onLowMemory();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mVideoMode) {
            mYoukuBasePlayerManager.onPause();
            cacheWatchedVideo();
        }
    }

    protected void cacheWatchedVideo() {
        if (mIsVideoStarted || mIsVideoEnded) {
            VideoCacheManager.INSTANCE.cacheWatchedVideo(mVid, mBackgroundUrl, mTitle
                    , mVideoDurationMillis, mCurrentPlayTimeMills, mIsVideoEnded);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mVideoMode) {
            mYoukuBasePlayerManager.onResume();
        }
    }

    @Override
    public boolean onSearchRequested() {
        if (mVideoMode) {
            return mYoukuBasePlayerManager.onSearchRequested();
        }
        return super.onSearchRequested();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mVideoMode) {
            mYoukuBasePlayerManager.onStart();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mVideoMode) {
            mYoukuBasePlayerManager.onStop();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mVideoMode) {
            mYoukuBasePlayerManager.onBackPressed();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (mVideoMode) {
            mYoukuBasePlayerManager.onConfigurationChanged(newConfig);
            if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                mBlurImageContainer.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                mBlurImageContainer.requestLayout();
                ButterKnife.findById(this, R.id.v_divider).setVisibility(View.GONE);
                mDateTextView.setVisibility(View.GONE);
                mDescription.setVisibility(View.GONE);
                mTitleTextView.setVisibility(View.GONE);
            }
            if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
                mBlurImageContainer.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) getResources().getDimension(R.dimen.video_player_height)));
                mBlurImageContainer.requestLayout();
                ButterKnife.findById(this, R.id.v_divider).setVisibility(View.VISIBLE);
                mDateTextView.setVisibility(View.VISIBLE);
                mDescription.setVisibility(View.VISIBLE);
                mTitleTextView.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void onDestroy() {
        RxCenter.INSTANCE.removeCompositeSubscription(TaskIds.newsDetailTaskId);
        if (mVideoMode) {
            if (mYoukuBasePlayerManager != null) {
                mYoukuBasePlayerManager.onDestroy();
            }
        } else {
            if (mWebViewContainer != null) {
                mWebViewContainer.removeAllViews();
            }
            if (mWebView != null) {
                mWebView.destroy();
                mWebView = null;
            }
        }
        super.onDestroy();
    }
}
