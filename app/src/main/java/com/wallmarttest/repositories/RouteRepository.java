package com.wallmarttest.repositories;

import com.wallmarttest.apis.entity.responses.RouteResponseParser;
import com.wallmarttest.apis.retrofit.RestClient;
import com.wallmarttest.models.RouteResponseModel;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;


public class RouteRepository {

    public Observable<RouteResponseModel> discover() {

        Observable<Response<RouteResponseParser>> discoverObservable = RestClient.get().discover();

        discoverObservable = discoverObservable.subscribeOn(Schedulers.newThread());
        discoverObservable = discoverObservable.observeOn(AndroidSchedulers.mainThread());
        return discoverObservable.map(new Function<Response<RouteResponseParser>, RouteResponseModel>() {
            @Override
            public RouteResponseModel apply(@NonNull Response<RouteResponseParser> response) throws Exception {

                if (response.isSuccessful()) {
                    RouteResponseParser parser = response.body();
                  return new RouteResponseModel(parser);
                }
                return null;
            }
        });
    }
}
