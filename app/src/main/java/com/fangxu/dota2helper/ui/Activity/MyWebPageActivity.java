package com.fangxu.dota2helper.ui.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.fangxu.dota2helper.R;

/**
 * Created by Administrator on 2016/6/28.
 */
public class MyWebPageActivity extends BaseWebActivity {
    public static final String TYPE = "com.fangxu.dota2helper.TYPE";
    public static final int MY_GITHUB = 0;
    public static final int MY_PROJECT = 1;

    public static void toMyPageActivity(Context activity, int type) {
        if (type != MY_GITHUB && type != MY_PROJECT) {
            return;
        }
        Intent intent = new Intent(activity, MyWebPageActivity.class);
        intent.putExtra(TYPE, type);
        activity.startActivity(intent);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        int type = getIntent().getIntExtra(TYPE, MY_GITHUB);
        String url = null;
        if (type == MY_GITHUB) {
            url = getResources().getString(R.string.my_github_url);
            setToolBarTitle(R.string.my_github);
        } else if (type == MY_PROJECT) {
            url = getResources().getString(R.string.my_project_mainpage_url);
            setToolBarTitle(R.string.my_project_mainpage);
        }
        mWebView.loadUrl(url);
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            finish();
        }
    }

    private void setToolBarTitle(int resId) {
        setTitle(resId);
    }
}
