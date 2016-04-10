package com.fangxu.dota2helper.interactor;

import android.util.Log;

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

    public NewsInteractor(NewsCallback callback) {
        mCallback = callback;
    }

    public void queryNews() {
//        AppNetWork.INSTANCE.getNewsApi().updateNews()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<NewsList>() {
//                    @Override
//                    public void call(NewsList newsList) {
//                        mCallback.onRefreshSuccessed(newsList.getNews());
//                    }
//                }, new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable throwable) {
//                        mCallback.onRefreshFailed();
//                    }
//                });
        List<NewsList.NewsEntity> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            NewsList.NewsEntity entity = new NewsList.NewsEntity();
            entity.setTitle("DOTA2特级锦标赛之马尼拉站即将上演");
            entity.setDescription("上海特锦赛产生了冠军之后，DOTA2圈的目光自然就会落到下届特级锦标赛上。大赛将在菲律宾首都马尼拉举办，本次特级锦标赛由PGL负责制作，主赛事将于6月7日至12日在菲律宾马尼拉的亚洲商业中心体育馆（Mall of Asia Arena）举行。");
            entity.setBackground("http://img.dota2.com.cn/dota2/31/8b/318b43ff95c38c7e54b4d1ad476a88a51458026307.jpg");
            entity.setTime("2016-03-15");
            entity.setUrl("http://www.dota2.com.cn/article/details/20160315/183021.html");
            list.add(entity);
        }
        mCallback.onRefreshSuccessed(list);
    }

    public void queryMoreNews() {

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
        mCallback.onRefreshSuccessed(list);
    }

    public void queryMoreUpdates() {

    }
}
