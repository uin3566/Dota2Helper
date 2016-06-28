package com.fangxu.dota2helper.ui.Activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.fangxu.dota2helper.R;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/6/28.
 */
public class BaseWebActivity extends BaseActivity {
    @Bind(R.id.webview)
    WebView mWebView;
    @Bind(R.id.webview_progressbar)
    ProgressBar mProgressBar;

    protected void configWebView() {
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDefaultTextEncodingName("UTF-8");
        settings.setSupportZoom(false);
        settings.setPluginState(WebSettings.PluginState.ON);
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
    }

    private void setWebViewProgress() {
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
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_base_webview;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        configWebView();
        setWebViewProgress();
    }

    @Override
    protected void onDestroy() {
        mWebView.destroy();
        super.onDestroy();
    }

    @Override
    public boolean applySystemBarDrawable() {
        return true;
    }

    @Override
    public int getTitleResId() {
        return 0;
    }
}
