package com.project.myambulancedriver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.project.myambulancedriver.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    ActivityMainBinding activityMainBinding;
    MapboxMap mapboxMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = activityMainBinding.getRoot();
        setContentView(view);

        Mapbox.getInstance(MainActivity.this, getString(R.string.mapbox_access_token));
        activityMainBinding.mapView.onCreate(savedInstanceState);
        activityMainBinding.mapView.getMapAsync(this);

    }

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        Log.e("Ikhwan", "Trace onMapReady");
        this.mapboxMap = mapboxMap;
        this.mapboxMap.setStyle(Style.TRAFFIC_DAY);

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}