package com.fangxu.dota2helper.network;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by lenov0 on 2016/4/15.
 */
public interface NewsDetailApi {
    @GET("/api/v1.0/newsdetail/{date}/{nid}")
    Observable<String> getNewsDetail(@Path("date") String date, @Path("nid") String nid);
}
