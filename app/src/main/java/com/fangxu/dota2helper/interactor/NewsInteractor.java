package com.fangxu.dota2helper.interactor;

import com.fangxu.dota2helper.bean.NewsList;
import com.fangxu.dota2helper.network.AppNetWork;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by lenov0 on 2016/4/9.
 */
public class NewsInteractor {
    private NewsCallback mCallback;
    private int mNextNewsListId;

    public NewsInteractor(NewsCallback callback) {
        mCallback = callback;
    }

    public void queryNews() {
        AppNetWork.INSTANCE.getNewsApi().refreshNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<NewsList>() {
                    @Override
                    public void call(NewsList newsList) {
                        mNextNewsListId = newsList.getNext_list_id();
                        mCallback.onRefreshSuccessed(newsList.getNews(), false);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mCallback.onRefreshFailed();
                    }
                });
    }

    public void queryMoreNews() {
        AppNetWork.INSTANCE.getNewsApi().loadMoreNews(mNextNewsListId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<NewsList>() {
                    @Override
                    public void call(NewsList newsList) {
                        mNextNewsListId = newsList.getNext_list_id();
                        mCallback.onRefreshSuccessed(newsList.getNews(), true);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mCallback.onRefreshFailed();
                    }
                });
    }

    public void queryUpdates() {
        List<NewsList.NewsEntity> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            NewsList.NewsEntity entity = new NewsList.NewsEntity();
            entity.setTitle("2016年国际邀请赛现场门票");
            entity.setDescription("国际邀请赛门票将于西雅图时间4月7日上午10:00和下午10:00两个时间分别开始出售，今年的大赛分为两种门票。售价75美元的周中票，可以观看8月8日-11日，也就是前四天的比赛，包括开幕式和全明星表演赛。售价100美元的决赛票，可以观看8月12日和13日，主赛事最后两天的比赛。这两种门票可以在单个订单中一同购买。");
            entity.setBackground("http://img.dota2.com.cn/dota2/13/c0/13c02ef1fa724fd8fdd8f6aeb087388f1459492086.jpg");
            entity.setTime("2016-04-01");
            entity.setUrl("http://www.dota2.com.cn/article/details/20160401/183541.html");
            list.add(entity);
        }
        mCallback.onRefreshSuccessed(list, false);
    }

    public void queryMoreUpdates() {

    }
}
