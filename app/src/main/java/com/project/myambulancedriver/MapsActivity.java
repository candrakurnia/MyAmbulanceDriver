package com.project.myambulancedriver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.project.myambulancedriver.databinding.ActivityMapsBinding;
import com.project.myambulancedriver.helper.FirebaseHelper;
import com.project.myambulancedriver.helper.GoogleMapHelper;
import com.project.myambulancedriver.helper.MarkerAnimationHelper;
import com.project.myambulancedriver.helper.UiHelper;
import com.project.myambulancedriver.interfaces.LatLngInterpolator;
import com.project.myambulancedriver.model.LokasiUser;
import com.project.myambulancedriver.pref.SessionManager;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends AppCompatActivity {

    ActivityMapsBinding activityMapsBinding;
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 2161;
    private final GoogleMapHelper googleMapHelper = new GoogleMapHelper();
    private GoogleMap googleMap;
    private FirebaseHelper firebaseHelper;
    private LocationRequest locationRequest;
    private UiHelper uiHelper;
    private Marker currentPositionMarker;
    private FusedLocationProviderClient locationProviderClient;
    private boolean locationFlag = true;
    private final AtomicBoolean driveronlineflag = new AtomicBoolean(false);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMapsBinding = ActivityMapsBinding.inflate(getLayoutInflater());
        View view = activityMapsBinding.getRoot();
        setContentView(view);
        onMaps();
    }

    private final LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            Location location = locationResult.getLastLocation();
            if (location == null) return;
            if (locationFlag) {
                locationFlag = false;
                animateCamera(location);
            }
                firebaseHelper.updateUser(new LokasiUser(Objects.requireNonNull(SessionManager.getUser(MapsActivity.this)).getId_driver(), location.getLatitude(), location.getLongitude()));
                showOrAnimateMarker(location);
            }


    };

    private void animateCamera(Location location) {
        CameraUpdate cameraUpdate = googleMapHelper.buildCameraUpdate(new LatLng(location.getLatitude(), location.getLongitude()));
        googleMap.animateCamera(cameraUpdate);
    }

    private void showOrAnimateMarker(Location location) {
        if (currentPositionMarker == null)
            currentPositionMarker = googleMap.addMarker(googleMapHelper.getDriverMarkerOptions(location));
        else
            MarkerAnimationHelper.animateMarkerToGB(
                    currentPositionMarker,
                    new LatLng(location.getLatitude(),
                            location.getLongitude()),
                    new LatLngInterpolator.Spherical());
    }

    private void onMaps() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.gmaps);
        uiHelper = new UiHelper(this);
        assert mapFragment != null;
        mapFragment.getMapAsync(googleMap -> this.googleMap = googleMap);
        locationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        locationRequest = uiHelper.getLocationRequest();
        if (!uiHelper.isPlayServicesAvailable()) {
            Toast.makeText(this, "Play Services did not installed!", Toast.LENGTH_SHORT).show();
            finish();
        } else requestLocationUpdates();

        firebaseHelper = new FirebaseHelper(Objects.requireNonNull(SessionManager.getUser(this)).getId_driver());
    }

    @SuppressLint("MissingPermission")
    private void requestLocationUpdates() {
        if (!uiHelper.isHaveLocationPermission()) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            return;
        }
        if (uiHelper.isLocationProviderEnabled())
            uiHelper.showPositiveDialogWithListener(this, getResources().getString(R.string.need_location),
                    getResources().getString(R.string.location_content), () ->
                            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)), "Turn on", false);
        locationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            int value = grantResults[0];
            if (value == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(this, "Location Permission denied", Toast.LENGTH_SHORT).show();
                finish();
            } else if (value == PackageManager.PERMISSION_GRANTED) requestLocationUpdates();

        }
    }
}