package com.fangxu.dota2helper.ui.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.fangxu.dota2helper.R;
import com.fangxu.dota2helper.RxCenter;
import com.fangxu.dota2helper.callback.LoadNewsDetailCallback;
import com.fangxu.dota2helper.interactor.TaskIds;
import com.fangxu.dota2helper.network.AppNetWork;
import com.fangxu.dota2helper.util.ToastUtil;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by lenov0 on 2016/4/13.
 */
public class ArticalDetailActivity extends BaseWebActivity implements LoadNewsDetailCallback {
    public static final String NEWS_DATE = "news_date";
    public static final String NEWS_NID = "news_nid";
    public static final String NEWS_HTML = "news_html";

    private static boolean mFromNews;

    public static void toNewsDetailActivity(Context context, String html) {
        Intent intent = new Intent(context, ArticalDetailActivity.class);
        intent.putExtra(NEWS_HTML, html);
        context.startActivity(intent);
    }

    public static void toNewsDetailActivity(Context context, boolean fromNews, String date, String nid, LoadNewsDetailCallback loadCallback) {
        mFromNews = fromNews;
        Intent intent = new Intent(context, ArticalDetailActivity.class);
        intent.putExtra(NEWS_DATE, date);
        intent.putExtra(NEWS_NID, nid);
        if (fromNews) {
            loadDetail(context, date, nid, loadCallback);
        } else {
            context.startActivity(intent);
        }
    }

    @Override
    public int getTitleResId() {
        return R.string.news_detail;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        mToolbar.setTitle(R.string.news_detail);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        if (mFromNews) {
            String html = getIntent().getStringExtra(NEWS_HTML);
            mWebView.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null);
        } else {
            String date = getIntent().getStringExtra(NEWS_DATE);
            String nid = getIntent().getStringExtra(NEWS_NID);
            loadDetail(this, date, nid, this);
        }
    }

    @Override
    public void onLoadedSuccess(boolean toVideoActivity, String content) {
        if (!toVideoActivity) {
            mWebView.loadDataWithBaseURL(null, content, "text/html", "UTF-8", null);
        }
    }

    @Override
    public void onLoadedFailed() {
        ToastUtil.showToast(getApplicationContext(), "error");
    }

    @Override
    protected void onDestroy() {
        RxCenter.INSTANCE.removeCompositeSubscription(TaskIds.newsDetailTaskId);
        super.onDestroy();
    }

    private static void loadDetail(final Context context, String date, String nid, final LoadNewsDetailCallback callback) {
        RxCenter.INSTANCE.getCompositeSubscription(TaskIds.newsDetailTaskId).add(AppNetWork.INSTANCE.getDetailsApi().getNewsDetail(date, nid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        if (callback != null) {
                            if (mFromNews) {
                                if (s.length() < 20) {
                                    callback.onLoadedSuccess(true, s);
                                } else {
                                    callback.onLoadedSuccess(false, s);
                                }
                            } else {
                                callback.onLoadedSuccess(false, s);
                            }
                        } else {
                            ToastUtil.showToast(context, "error");
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (callback != null) {
                            callback.onLoadedFailed();
                        } else {
                            ToastUtil.showToast(context, "error");
                        }
                    }
                }));
    }
}
