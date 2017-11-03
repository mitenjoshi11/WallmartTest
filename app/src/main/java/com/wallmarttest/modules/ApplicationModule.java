package com.wallmarttest.modules;

import com.wallmarttest.presenters.DetailRoutePresenter;
import com.wallmarttest.presenters.HomePresenter;
import com.wallmarttest.repositories.RouteRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class ApplicationModule {

    @Provides
    @Singleton
    public HomePresenter providesHomePresenter() {
        return new HomePresenter();
    }

    @Provides
    @Singleton
    public DetailRoutePresenter providesRouteDetailPresenter() {
        return new DetailRoutePresenter();
    }

    @Provides
    @Singleton
    public RouteRepository providesRouteRepository() {
        return new RouteRepository();
    }

}
