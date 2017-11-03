package com.wallmarttest.activities;

import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.wallmarttest.BaseApplication;
import com.wallmarttest.R;
import com.wallmarttest.customview.TimelineLayout;
import com.wallmarttest.models.RouteModel;
import com.wallmarttest.models.StopModel;
import com.wallmarttest.presenters.DetailRoutePresenter;

import javax.inject.Inject;


public class RouteDetailActivity extends BaseActivity implements DetailRoutePresenter.DetailView {

    public static final String ARG_ROUTE_DETAIL = "route_detail";

    @Inject
    DetailRoutePresenter presenter;

    private DraweeView ivThumbnail;
    private TextView tvRouteTitle;
    private TextView tvRouteDescription;
    private ImageView ivAccessible;
    private TimelineLayout mTimelineLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BaseApplication.getInstance().getApplicationComponent().inject(this);

        setContentView(R.layout.activity_route_detail);

        initViews();

        if (getIntent().getExtras() != null) {
            presenter.startNow(this, getIntent().getExtras());
        }

    }

    private void initViews() {
        ivThumbnail = findViewById(R.id.ivThumbnail);
        tvRouteTitle = findViewById(R.id.tvName);
        tvRouteDescription = findViewById(R.id.tvRouteDescription);
        ivAccessible = findViewById(R.id.ivAccessible);
        mTimelineLayout = findViewById(R.id.timeline_layout);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        presenter.clean();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                exit();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void exit() {
        ActivityCompat.finishAfterTransition(this);
        presenter.clean();
    }


    @Override
    public void showNewsDetails(RouteModel model) {
        if (model == null) {
            exit();
            Toast.makeText(this, R.string.no_route_detail, Toast.LENGTH_LONG).show();
            return;
        }


        if (model != null) {

            tvRouteTitle.setText(model.getName());
            tvRouteTitle.setTypeface(tvRouteTitle.getTypeface(), Typeface.BOLD);
            tvRouteDescription.setVisibility(View.VISIBLE);
            tvRouteDescription.setText(model.getDescription());

            if (model.getAccessible()) {
                ivAccessible.setVisibility(View.VISIBLE);
            } else {
                ivAccessible.setVisibility(View.GONE);
            }

            String thumbnail = model.getImage();

            DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                    .setImageRequest(ImageRequest.fromUri(Uri.parse(thumbnail)))
                    .setOldController(ivThumbnail.getController()).build();
            ivThumbnail.setController(draweeController);
            if (model.getStops() != null && model.getStops().size() > 0) {

                for (StopModel stopModel : model.getStops()) {
                    View view = LayoutInflater.from(this).inflate(R.layout.item_timeline, mTimelineLayout, false);
                    ((TextView) view.findViewById(R.id.tvStopName)).setText(stopModel.getName());
                    mTimelineLayout.addView(view);
                }
            }

        }
    }

    @Override
    public void showError() {
        Snackbar.make(findViewById(android.R.id.content), getString(R.string.something_went_wrong), Snackbar.LENGTH_LONG).show();
    }
}
