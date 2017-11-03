package com.wallmarttest.adapters;

import android.app.Activity;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.wallmarttest.R;
import com.wallmarttest.models.RouteModel;


import java.util.ArrayList;
import java.util.List;

public class RouteListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<RouteModel> routes = new ArrayList<>();

    private final LayoutInflater inflater;

    private Activity activity;

    private OnRouteSelectionListener movieSelectionListener;


    public RouteListAdapter(Activity activity) {

        this.activity = activity;
        inflater = LayoutInflater.from(activity);

        setHasStableIds(true);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = inflater.inflate(R.layout.row_route, parent, false);

        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

        final MovieViewHolder holder = (MovieViewHolder) viewHolder;
        final RouteModel model = routes.get(position);

        String name = model.getName();
        holder.tvName.setText(name);

        String thumbnail = model.getImage();

        DraweeController draweeController = Fresco.newDraweeControllerBuilder().setImageRequest(ImageRequest.fromUri
                (Uri.parse(thumbnail))).setOldController(holder.ivThumbnail.getController()).build();
        holder.ivThumbnail.setController(draweeController);


        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(movieSelectionListener != null) {
                    movieSelectionListener.onRouteSelected(model, holder.ivThumbnail);
                }
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return routes.size();
    }

    public OnRouteSelectionListener getMovieSelectionListener() {
        return movieSelectionListener;
    }

    public void setMovieSelectionListener(OnRouteSelectionListener movieSelectionListener) {
        this.movieSelectionListener = movieSelectionListener;
    }

    public List<RouteModel> getRoutes() {
        return routes;
    }

    public void setRoutes(List<RouteModel> routes) {
        if (routes == null) {
            return;
        }
        this.routes = routes;
        notifyDataSetChanged();
    }

    public void notifyMoviesListChanged() {
        notifyDataSetChanged();
    }



    private static class MovieViewHolder extends RecyclerView.ViewHolder {

        public View view;
        public TextView tvName;
        public SimpleDraweeView ivThumbnail;

        public MovieViewHolder(View view) {
            super(view);
            this.view = view;
            this.ivThumbnail = view.findViewById(R.id.ivThumbnail);
            this.tvName = view.findViewById(R.id.tvName);
        }
    }

    public interface OnRouteSelectionListener {

        void onRouteSelected(RouteModel model, View view);
    }
}
