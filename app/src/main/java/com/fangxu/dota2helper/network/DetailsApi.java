package com.fangxu.dota2helper.network;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by lenov0 on 2016/4/15.
 */
public interface DetailsApi {
    @GET("/api/v1.0/newsdetail/{date}/{nid}")
    Observable<String> getNewsDetail(@Path("date") String date, @Path("nid") String nid);
    @GET("/api/v1.0/video/youkuvid/{date}/{vid}")
    Observable<String> getYoukuVid(@Path("date") String date, @Path("vid") String vid);
}
