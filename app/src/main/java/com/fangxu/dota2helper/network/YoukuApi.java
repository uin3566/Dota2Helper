package com.fangxu.dota2helper.network;

import com.fangxu.dota2helper.bean.RelatedVideoList;
import com.fangxu.dota2helper.bean.VideoDetailInfo;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by lenov0 on 2016/4/26.
 */
public interface YoukuApi {
    @GET("v2/videos/show.json")
    Observable<VideoDetailInfo> getVideoDetailInfo(@Query("client_id") String clientId, @Query("video_id") String videoId);
    @GET("v2/videos/by_related.json")
    Observable<RelatedVideoList> getRelatedVideoList(@Query("client_id") String clientId, @Query("video_id") String videoId, @Query("count") int count);
}
