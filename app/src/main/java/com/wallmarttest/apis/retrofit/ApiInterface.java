package com.wallmarttest.apis.retrofit;

import com.wallmarttest.apis.entity.responses.RouteResponseParser;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;


public interface ApiInterface {

    @GET("v2/5808f00d10000005074c6340")
    Observable<Response<RouteResponseParser>> discover();

}