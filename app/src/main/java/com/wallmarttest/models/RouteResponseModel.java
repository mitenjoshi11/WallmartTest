
package com.wallmarttest.models;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.wallmarttest.apis.entity.responses.RouteParser;
import com.wallmarttest.apis.entity.responses.RouteResponseParser;

public class RouteResponseModel {


    public RouteResponseModel() {
    }

    public RouteResponseModel(RouteResponseParser parser) {

        if(parser == null) {
            return;
        }

        List<RouteParser> routeParsers = parser.getRoutes();
        if(routeParsers != null) {
            routes = new ArrayList<>();
            for(RouteParser routeParser : routeParsers){
                this.routes.add(new RouteModel(routeParser));
            }
        }
    }


    private List<RouteModel> routes = null;

    public List<RouteModel> getRoutes() {
        return routes;
    }

    public void setRoutes(List<RouteModel> routes) {
        this.routes = routes;
    }

}
