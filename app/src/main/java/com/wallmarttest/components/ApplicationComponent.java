package com.wallmarttest.components;

import com.wallmarttest.activities.HomeActivity;
import com.wallmarttest.activities.RouteDetailActivity;
import com.wallmarttest.modules.ApplicationModule;
import com.wallmarttest.presenters.DetailRoutePresenter;
import com.wallmarttest.presenters.HomePresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {
    void inject(HomePresenter homePresenter);

    void inject(DetailRoutePresenter detailRoutePresenter);

    void inject(HomeActivity homeActivity);

    void inject(RouteDetailActivity routeDetailActivity);
}
