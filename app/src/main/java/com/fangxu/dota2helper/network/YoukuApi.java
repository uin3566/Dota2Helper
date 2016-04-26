package com.fangxu.dota2helper.network;

import retrofit2.http.GET;

/**
 * Created by lenov0 on 2016/4/26.
 */
public interface YoukuApi {
    @GET("v2/videos/show.json/{client_id}/{video_id}")

}
