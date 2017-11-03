package com.wallmarttest.presenters;

import android.os.Bundle;

import com.wallmarttest.BaseApplication;
import com.wallmarttest.activities.RouteDetailActivity;
import com.wallmarttest.models.RouteModel;


public class DetailRoutePresenter {

    private DetailView detailView;

    private RouteModel routeModel = null;

    public DetailRoutePresenter() {

        BaseApplication.getInstance().getApplicationComponent().inject(this);
    }

    public void startNow(DetailView view, Bundle bundle) {
        this.detailView = view;
        if (routeModel != null) {
            detailView.showNewsDetails(routeModel);
            return;
        }
        fetchRouteDetails(bundle);
    }

    private void fetchRouteDetails(Bundle bundle) {

        if (bundle != null & bundle.containsKey(RouteDetailActivity.ARG_ROUTE_DETAIL)) {
            routeModel = bundle.getParcelable(RouteDetailActivity.ARG_ROUTE_DETAIL);
            detailView.showNewsDetails(routeModel);
        } else {
            detailView.showError();
        }
    }

    public void clean() {
        routeModel = null;
    }

    public interface DetailView {

        void showNewsDetails(RouteModel routeModel);

        void showError();
    }
}
