package org.tensorflow.lite.examples.detection.api;

import android.content.Context;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Api {

    private static Retrofit retrofit;

    static ApiInterface getClient() {
        if (retrofit == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

            retrofit = new Retrofit.Builder()
                    .baseUrl("https://pothhole.herokuapp.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .client(client)
                    .build();
        }

        return retrofit.create(ApiInterface.class);
    }

    public static void getLocations(Callback<ArrayList<PotholeLocation>> callback) {
        getClient().getLocations().enqueue(callback);
    }

    public static void sendLocation(Context context, Location location) {
        final PotholeLocation pLoc = new PotholeLocation(location);

        Log.i("PotholeLocation", "send location -- " + pLoc.lat + " ," + pLoc.lon);
        getClient().sendLocation(pLoc.lat, pLoc.lon).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && context != null) {
                    Log.i("PotholeLocation", "sent successfully");
                    Toast.makeText(context, "Sent location to server", Toast.LENGTH_SHORT);
                } else {
                    Log.i("PotholeLocation", "Server error");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (context != null) {
                    Toast.makeText(context, "Failed to send location to server", Toast.LENGTH_SHORT);
                }
                Log.i("PotholeLocation", "Server error");
            }
        });
    }
}
