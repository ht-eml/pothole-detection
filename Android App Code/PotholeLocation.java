package org.tensorflow.lite.examples.detection.api;

import android.location.Location;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class PotholeLocation implements Serializable {
    @SerializedName("latitude")
    public float lat;

    @SerializedName("longitude")
    public float lon;

    @SerializedName("date_time")
    public Date time;

    public PotholeLocation() {
    }

    public PotholeLocation(Location location) {
        lat = (float) location.getLatitude();
        lon = (float) location.getLongitude();
        time = new Date();
    }
}