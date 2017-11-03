package com.wallmarttest.presenters;

import android.util.Log;

import com.wallmarttest.BaseApplication;
import com.wallmarttest.models.RouteModel;
import com.wallmarttest.models.RouteResponseModel;
import com.wallmarttest.repositories.RouteRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class HomePresenter {

    @Inject
    RouteRepository repository;

    private HomeView homeView;

    private List<RouteModel> routes = new ArrayList<>();

    private boolean loading;

    public HomePresenter() {

        BaseApplication.getInstance().getApplicationComponent().inject(this);
    }

    public void startNow(HomeView homeView) {
        this.homeView = homeView;
        if (routes != null && routes.size() > 0) {
            homeView.showRoutes(routes);
            return;
        }
        fetchRoute();
    }

    private void fetchRoute() {

        Observable<RouteResponseModel> observable = repository.discover();

        observable.subscribe(new Observer<RouteResponseModel>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                loading = true;
                homeView.showLoadingProgress();
            }

            @Override
            public void onNext(@NonNull RouteResponseModel routeResponseModel) {
                if (routeResponseModel == null) {
                    return;
                }
                List<RouteModel> newList = routeResponseModel.getRoutes();
                if (newList != null) {
                    if (routes == null || routes.size() == 0) {
                        routes = newList;
                        homeView.showRoutes(routes);
                    } else {
                        routes.addAll(newList);
                        homeView.notifyMoviesListChanged();
                    }
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                homeView.onError(e);
            }

            @Override
            public void onComplete() {
                loading = false;
                homeView.hideLoadingProgress();
            }
        });
    }

    public boolean isLoading() {
        return loading;
    }

    public interface HomeView {

        void showRoutes(List<RouteModel> routes);

        void notifyMoviesListChanged();

        void showLoadingProgress();

        void hideLoadingProgress();

        void onError(Throwable e);
    }
}
