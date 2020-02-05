package org.tensorflow.lite.examples.detection;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;

import org.tensorflow.lite.examples.detection.api.Api;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

public class LocationDelegate {

    private static final long UPDATE_INTERVAL = 500;
    private static final long FASTEST_INTERVAL = UPDATE_INTERVAL / 2;

    public int locationRequests = 0;
    public FusedLocationProviderClient locationProvider;
    public LocationRequest mLocationRequest;

    private Context context;

    public LocationDelegate(Activity activity) {
        context = activity;
        locationProvider = getFusedLocationProviderClient(activity);
    }

    public void destroy() {
        locationProvider.removeLocationUpdates(mLocationCallback);
    }

    public void sendLocation() {
        // Create the location request to start receiving updates
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);

        // Create LocationSettingsRequest object using location request
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();

        SettingsClient settingsClient = LocationServices.getSettingsClient(context);
        settingsClient.checkLocationSettings(locationSettingsRequest);

        locationRequests++;

        Log.i("PotholeLocation", "getting location: " + locationRequests);
        locationProvider.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    public final LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            onLocationChanged(locationResult.getLastLocation());
        }
    };

    public void onLocationChanged(Location location) {
        if (location.getAccuracy() > 0.8) {
            Log.i("PotholeLocation", "sending location");
            Api.sendLocation(context, location);

            // Stop sending locations if not queued.
            locationRequests--;
            if (locationRequests <= 0) {
                locationRequests = 0;
                locationProvider.removeLocationUpdates(mLocationCallback);
            }
        } else {
            Log.i("PotholeLocation", "Less accuracy. Waiting for another callback: " + location.getAccuracy());
        }
    }
}
