package com.fangxu.dota2helper.ui.Fragment;

import android.util.Log;

import com.fangxu.dota2helper.R;
import com.fangxu.dota2helper.bean.NewsList;
import com.fangxu.dota2helper.network.AppNetWork;

import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Xuf on 2016/4/3.
 */
public class NewsFragment extends BaseFragment {
    public static final String TAG = NewsFragment.class.getName();

    public static NewsFragment newInstance() {
        return new NewsFragment();
    }

    @Override
    public int getResourceId() {
        return R.layout.fragment_news;
    }

    @Override
    public void initView() {

    }

    @OnClick(R.id.tv_news)
    public void requestData() {
        AppNetWork.INSTANCE.getNewsApi().updateNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<NewsList>() {
                    @Override
                    public void call(NewsList newsList) {
                        Log.i(TAG, newsList.getNews().get(0).getTitle());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.i(TAG, "error!!");
                    }
                });
    }
}
