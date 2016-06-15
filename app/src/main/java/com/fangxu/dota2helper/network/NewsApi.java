package com.fangxu.dota2helper.network;

import com.fangxu.dota2helper.bean.NewsList;
import com.fangxu.dota2helper.bean.StrategyList;
import com.fangxu.dota2helper.bean.VideoList;
import com.fangxu.dota2helper.bean.VideoSetList;

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
    @GET("/api/v1.0/strategy/loadmore/{strategy_type}/{nid}")
    Observable<StrategyList> loadMoreStrategies(@Path("strategy_type") String strategyType, @Path("nid") String nid);
    @GET("/api/v1.0/video/refresh/{video_type}")
    Observable<VideoList> refreshVideos(@Path("video_type") String videoType);
    @GET("/api/v1.0/video/loadmore/{video_type}/{vid}")
    Observable<VideoList> loadMoreVideos(@Path("video_type") String videoType, @Path("vid") String vid);
    @GET("/api/v1.0/video/videoset/{date}/{vid}")
    Observable<VideoSetList> getVideoSetInfo(@Path("date") String date, @Path("vid") String vid);
}
