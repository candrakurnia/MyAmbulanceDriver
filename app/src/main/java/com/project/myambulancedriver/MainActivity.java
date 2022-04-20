package com.project.myambulancedriver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.project.myambulancedriver.adapter.HistoryAdapter;
import com.project.myambulancedriver.databinding.ActivityMainBinding;
import com.project.myambulancedriver.helper.FirebaseHelper;
import com.project.myambulancedriver.helper.GoogleMapHelper;
import com.project.myambulancedriver.helper.UiHelper;
import com.project.myambulancedriver.model.History;
import com.project.myambulancedriver.model.ResponseData;
import com.project.myambulancedriver.model.ResponseList;
import com.project.myambulancedriver.model.User;
import com.project.myambulancedriver.pref.SessionManager;
import com.project.myambulancedriver.remote.Network;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    ActivityMainBinding activityMainBinding;
    private HistoryAdapter historyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = activityMainBinding.getRoot();
        setContentView(view);
        onInit();
        setName();
        onPressBtn();
    }

    private void onPressBtn() {
        activityMainBinding.btnPesan.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, MapsActivity.class));
            finish();

        });
    }

    private void setName() {
        activityMainBinding.tvUsername
                .setText(Objects.requireNonNull(SessionManager.getUser(this)).getUsername());
    }

    private void onInit() {
        historyAdapter = new HistoryAdapter();
        activityMainBinding.vw.setAdapter(historyAdapter);
        getData();
    }

    private void getData() {
        Network.provideApiService().getHistory(Objects.requireNonNull(SessionManager.getUser(this)).getId_driver()).enqueue(new Callback<ResponseList<History>>() {
            @Override
            public void onResponse(Call<ResponseList<History>> call, Response<ResponseList<History>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getStatus()) {
                            if (response.body().getData() != null) {
                                List<History> data = response.body().getData();
                                historyAdapter.setData(data);

                            } else {
                                Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                Log.i("error", "gagal");
                            }
                        } else {
                            Log.i("error", "gagal");
                        }
                    } else {
                        Log.i("error", "gagal");
                    }
                } else {
                    Log.i("error", "gagal");
                }
            }

            @Override
            public void onFailure(Call<ResponseList<History>> call, Throwable t) {

            }
        });

    }

}