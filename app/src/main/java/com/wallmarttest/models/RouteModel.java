
package com.wallmarttest.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.wallmarttest.apis.entity.responses.RouteParser;
import com.wallmarttest.apis.entity.responses.StopParser;

import java.util.ArrayList;
import java.util.List;

public class RouteModel implements Parcelable {

    private String id;
    private String name;
    private List<StopModel> stops = null;
    private String description;
    private boolean accessible;
    private String image;

    public RouteModel() {
    }

    public RouteModel(RouteParser parser) {

        if(parser == null) {
            return;
        }

        this.id = parser.getId();
        this.name = parser.getName();
        this.description = parser.getDescription();
        this.accessible = Boolean.parseBoolean(parser.getAccessible());
        this.image = parser.getImage();

        List<StopParser> stopParsers = parser.getStops();
        if(stopParsers != null) {
            stops = new ArrayList<>();
            for(StopParser stopParser : stopParsers){
                this.stops.add(new StopModel(stopParser));
            }
        }
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<StopModel> getStops() {
        return stops;
    }

    public void setStops(List<StopModel> stops) {
        this.stops = stops;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getAccessible() {
        return accessible;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeTypedList(this.stops);
        dest.writeString(this.description);
        dest.writeByte(this.accessible ? (byte) 1 : (byte) 0);
        dest.writeString(this.image);
    }

    protected RouteModel(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.stops = in.createTypedArrayList(StopModel.CREATOR);
        this.description = in.readString();
        this.accessible = in.readByte() != 0;
        this.image = in.readString();
    }

    public static final Parcelable.Creator<RouteModel> CREATOR = new Parcelable.Creator<RouteModel>() {
        @Override
        public RouteModel createFromParcel(Parcel source) {
            return new RouteModel(source);
        }

        @Override
        public RouteModel[] newArray(int size) {
            return new RouteModel[size];
        }
    };
}
