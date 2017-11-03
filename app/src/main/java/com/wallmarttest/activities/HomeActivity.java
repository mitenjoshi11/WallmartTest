package com.wallmarttest.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.wallmarttest.BaseApplication;
import com.wallmarttest.R;
import com.wallmarttest.adapters.RouteListAdapter;
import com.wallmarttest.models.RouteModel;
import com.wallmarttest.presenters.HomePresenter;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;

import javax.inject.Inject;

public class HomeActivity extends BaseActivity
        implements HomePresenter.HomeView {

    @Inject
    HomePresenter presenter;

    private RecyclerView rvRoutes;
    private View loadingProgressView;
    private View emptyView;

    private RouteListAdapter routeListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BaseApplication.getInstance().getApplicationComponent().inject(this);

        setContentView(R.layout.activity_main);

        Fresco.initialize(this);

        initViews();

        presenter.startNow(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!presenter.isLoading()) {
            hideLoadingProgress();
        }
    }

    private void initViews() {
        rvRoutes = findViewById(R.id.rvRoutes);

        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvRoutes.setLayoutManager(mLayoutManager);

        routeListAdapter = new RouteListAdapter(this);
        routeListAdapter.setMovieSelectionListener(new RouteListAdapter.OnRouteSelectionListener() {
            @Override
            public void onRouteSelected(RouteModel model, View view) {
                routeSelected(model,view);
            }
        });
        rvRoutes.setAdapter(routeListAdapter);


        loadingProgressView = findViewById(R.id.loadingProgressView);

        emptyView = findViewById(R.id.emptyView);


    }

    private void routeSelected(RouteModel model, View view) {
        if (isFinishing()) {
            return;
        }
        Intent intent = new Intent(this, RouteDetailActivity.class);
        intent.putExtra(RouteDetailActivity.ARG_ROUTE_DETAIL, model);
        startActivity(intent);
    }

    @Override
    public void showRoutes(List<RouteModel> routes) {
        if (isFinishing()) {
            return;
        }
        if (routes == null || routes.size() == 0) {
            showEmptyView();
            return;
        }

        hideEmptyView();
        routeListAdapter.setRoutes(routes);


    }

    private void hideEmptyView() {
        if (emptyView != null) {
            emptyView.setVisibility(View.GONE);
        }
    }

    private void showEmptyView() {
        if (emptyView != null) {
            emptyView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void notifyMoviesListChanged() {
        if (isFinishing()) {
            return;
        }
        routeListAdapter.notifyMoviesListChanged();
    }

    @Override
    public void showLoadingProgress() {
        if (isFinishing()) {
            return;
        }
        loadingProgressView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingProgress() {
        if (isFinishing()) {
            return;
        }
        loadingProgressView.setVisibility(View.GONE);
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
        hideLoadingProgress();
        if (throwable instanceof UnknownHostException || throwable instanceof SocketTimeoutException) {
            Snackbar.make(findViewById(android.R.id.content), getString(R.string.check_network_connection) + " : " + throwable.getMessage(), Snackbar.LENGTH_LONG).show();
            return;
        }
        Snackbar.make(findViewById(android.R.id.content), getString(R.string.something_went_wrong) + " : " + throwable.getMessage(), Snackbar.LENGTH_LONG).show();
    }

}
