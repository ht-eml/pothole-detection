package org.tensorflow.lite.examples.detection.api;

import android.text.format.DateFormat;

import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("/pothole_locations")
    Call<String> sendLocation(@Field("pothole_location[latitude]") float lat,
                              @Field("pothole_location[longitude]") float lon);


    @GET("/pothole_locations.json")
    Call<ArrayList<PotholeLocation>> getLocations();
}