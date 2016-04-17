package com.fangxu.dota2helper.network;

import com.fangxu.dota2helper.bean.NewsList;
import com.fangxu.dota2helper.bean.StrategyList;

import java.util.List;


import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Xuf on 2016/4/6.
 */
public interface NewsApi {
    @GET("/api/v1.0/news/refresh")
    Observable<NewsList> refreshNews();
    @GET("/api/v1.0/news/loadmore/{list_id}")
    Observable<NewsList> loadMoreNews(@Path("list_id") int listId);
    @GET("/api/v1.0/updates/refresh")
    Observable<NewsList> refreshUpdates();
    @GET("/api/v1.0/updates/loadmore/{list_id}")
    Observable<NewsList> loadMoreUpdates(@Path("list_id") int listId);
    @GET("/api/v1.0/strategy/refresh/{strategy_type}")
    Observable<StrategyList> refreshStrategies(@Path("strategy_type") String strategyType);
    @GET("/api/v1.0/strategy/loadmore/{strategy_type}/{list_id}")
    Observable<StrategyList> loadMoreStrategies(@Path("strategy_type") String strategyType, @Path("list_id") int listId);
}
