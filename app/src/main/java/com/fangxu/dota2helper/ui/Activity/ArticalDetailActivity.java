package com.fangxu.dota2helper.ui.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.fangxu.dota2helper.R;
import com.fangxu.dota2helper.RxCenter;
import com.fangxu.dota2helper.interactor.TaskIds;
import com.fangxu.dota2helper.network.AppNetWork;
import com.fangxu.dota2helper.util.ToastUtil;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by lenov0 on 2016/4/13.
 */
public class ArticalDetailActivity extends BaseWebActivity {
    public static final String NEWS_DATE = "news_date";
    public static final String NEWS_NID = "news_nid";
    public static final String NEWS_HTML = "news_html";

    public static void toNewsDetailActivity(Context context, String date, String nid) {
        Intent intent = new Intent(context, ArticalDetailActivity.class);
        intent.putExtra(NEWS_DATE, date);
        intent.putExtra(NEWS_NID, nid);
        context.startActivity(intent);
    }

    public static void toNewsDetailActivity(Context context, String html) {
        Intent intent = new Intent(context, ArticalDetailActivity.class);
        intent.putExtra(NEWS_HTML, html);
        context.startActivity(intent);
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

        loadDetail();
    }

    @Override
    protected void onDestroy() {
        RxCenter.INSTANCE.removeCompositeSubscription(TaskIds.newsDetailTaskId);
        super.onDestroy();
    }

    private void loadDetail() {
        String html = getIntent().getStringExtra(NEWS_HTML);
        if (html != null) {
            mWebView.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null);
        } else {
            String date = getIntent().getStringExtra(NEWS_DATE);
            String nid = getIntent().getStringExtra(NEWS_NID);
            RxCenter.INSTANCE.getCompositeSubscription(TaskIds.newsDetailTaskId).add(AppNetWork.INSTANCE.getDetailsApi().getNewsDetail(date, nid)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<String>() {
                        @Override
                        public void call(String s) {
                            mWebView.loadDataWithBaseURL(null, s, "text/html", "UTF-8", null);
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            ToastUtil.showToast(getApplicationContext(), "error");
                        }
                    }));
        }
    }
}
