package com.fangxu.dota2helper.ui.Activity;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
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
    public static final String VIDEO_BACKGROUND = "video_background";

    @Bind(R.id.webview)
    WebView mWebView;
    @Bind(R.id.webview_progressbar)
    ProgressBar mProgressBar;
    @Bind(R.id.news_container)
    RelativeLayout mNewsContainer;
    @Bind(R.id.video_container)
    LinearLayout mVideoContainer;
    @Bind(R.id.youku_player)
    YoukuPlayerView mYoukuPlayerView;
    @Bind(R.id.iv_blur)
    ImageView mBlurImageView;
    @Bind(R.id.rl_blur_container)
    RelativeLayout mBlurImageContainer;
    @Bind(R.id.tv_title)
    TextView mTitleTextView;

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
                            mVideoContainer.setVisibility(View.VISIBLE);
                            initVideo(s);
                        } else {
                            mVideoMode = false;
                            mNewsContainer.setVisibility(View.VISIBLE);
                            initWebview(s);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ToastUtil.showToast(context, "error");
                    }
                }));
    }

    private void initVideo(final String ykvid) {
        mVid = ykvid;
        mTitle = getIntent().getStringExtra(VIDEO_TITLE);
        mTitleTextView.setText(mTitle);
        mBackgroundUrl = getIntent().getStringExtra(VIDEO_BACKGROUND);
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

    private void initWebview(String html) {
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
        return true;
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
        mYoukuBasePlayerManager.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mBlurImageContainer.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            mBlurImageContainer.requestLayout();
        }
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            mBlurImageContainer.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) getResources().getDimension(R.dimen.video_player_height)));
            mBlurImageContainer.requestLayout();
        }
    }

    @Override
    protected void onDestroy() {
        mYoukuBasePlayerManager.onDestroy();
        super.onDestroy();
    }
}
