
package com.wallmarttest.apis.entity.responses;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RouteResponseParser {

    @SerializedName("routes")
    @Expose
    private List<RouteParser> routes = null;

    public List<RouteParser> getRoutes() {
        return routes;
    }

    public void setRoutes(List<RouteParser> routes) {
        this.routes = routes;
    }

}
