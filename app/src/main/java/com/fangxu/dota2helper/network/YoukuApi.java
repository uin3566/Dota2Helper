package com.fangxu.dota2helper.network;

import com.fangxu.dota2helper.bean.VideoDetailInfo;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by lenov0 on 2016/4/26.
 */
public interface YoukuApi {
    @GET("v2/videos/show.json/{client_id}/{video_id}")
    Observable<VideoDetailInfo> getVideoDetailInfo(@Path("client_id") String clientId, @Path("video_id") String videoId);
}
