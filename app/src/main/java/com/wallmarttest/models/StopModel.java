
package com.wallmarttest.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.wallmarttest.apis.entity.responses.StopParser;

import java.util.ArrayList;
import java.util.List;

public class StopModel implements Parcelable {

    private String name;

    public StopModel() {
    }

    public StopModel(StopParser parser) {

        if(parser == null) {
            return;
        }
        this.name = parser.getName();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
    }

    protected StopModel(Parcel in) {
        this.name = in.readString();
    }

    public static final Parcelable.Creator<StopModel> CREATOR = new Parcelable.Creator<StopModel>() {
        @Override
        public StopModel createFromParcel(Parcel source) {
            return new StopModel(source);
        }

        @Override
        public StopModel[] newArray(int size) {
            return new StopModel[size];
        }
    };
}
