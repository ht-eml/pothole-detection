package org.tensorflow.lite.examples.detection;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.tensorflow.lite.examples.detection.api.Api;
import org.tensorflow.lite.examples.detection.api.PotholeLocation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Maps extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    LocationManager locationManager;
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    Marker marker;
    LocationListener locationListener;

    private ArrayList<PotholeLocation> locations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Setting up map
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void showCurrentLocation() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        } else {

            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    try {
                        List<Address> addresses =
                                geocoder.getFromLocation(latitude, longitude, 1);
                        String result = addresses.get(0).getLocality() + ":";
                        result += addresses.get(0).getCountryName();
                        LatLng latLng = new LatLng(latitude, longitude);
                        if (marker != null) {
                            marker.remove();
                            //marker = mMap.addMarker(new MarkerOptions().position(latLng).title("You are Here"));
                            mMap.setMaxZoomPreference(20);
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12.0f));
                        } else {
                            //marker = mMap.addMarker(new MarkerOptions().position(latLng).title("You are Here"));
                            //mMap.setMaxZoomPreference(20);
                            //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 21.0f));
                        }


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            };
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
    }

    private void getPotHoles() {
        Api.getLocations(new Callback<ArrayList<PotholeLocation>>() {
            @Override
            public void onResponse(Call<ArrayList<PotholeLocation>> call, Response<ArrayList<PotholeLocation>> response) {
                if (response.isSuccessful()) {
                    Log.d("Maps", "Fetched locations");
                    locations = response.body();

                    showPotholes();
                } else {
                    Log.d("Maps", "Server error");
                    Toast.makeText(Maps.this, "Server error", Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<PotholeLocation>> call, Throwable t) {
                Toast.makeText(Maps.this, "Failed to fetch pothole locations", Toast.LENGTH_LONG);
            }
        });
    }

    private void showPotholes() {
        if (locations == null) return;

        for (PotholeLocation location : locations) {
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(new LatLng(location.lat, location.lon));

            mMap.addMarker(markerOptions);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    //get the location name from latitude and longitude
                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    try {
                        List<Address> addresses =
                                geocoder.getFromLocation(latitude, longitude, 1);
                        String result = addresses.get(0).getLocality() + ":";
                        result += addresses.get(0).getCountryName();
                        LatLng latLng = new LatLng(latitude, longitude);
                        if (marker != null) {
                            marker.remove();
                            //marker = mMap.addMarker(new MarkerOptions().position(latLng).title("You are Here"));
                            mMap.setMaxZoomPreference(20);
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12.0f));
                        } else {
                           // marker = mMap.addMarker(new MarkerOptions().position(latLng).title("You are Here"));
                            mMap.setMaxZoomPreference(20);
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 21.0f));
                        }


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            };
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        mMap.clear();
        getPotHoles();
        showCurrentLocation();
    }

    @Override
    protected void onStop() {
        super.onStop();
        locationManager.removeUpdates(locationListener);
    }
}
