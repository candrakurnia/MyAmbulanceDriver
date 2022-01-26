package com.project.myambulancedriver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.project.myambulancedriver.databinding.ActivityLoginBinding;
import com.project.myambulancedriver.model.ResponseData;
import com.project.myambulancedriver.model.User;
import com.project.myambulancedriver.pref.SessionManager;
import com.project.myambulancedriver.remote.Network;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding activityLoginBinding;
    private final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = activityLoginBinding.getRoot();
        setContentView(view);
        onInit();
    }

    private void onInit() {
        activityLoginBinding.btnLogin.setOnClickListener(view -> validation());
    }

    private void validation() {
        String username = Objects.requireNonNull(activityLoginBinding.edtUsername.getText()).toString();
        String password = Objects.requireNonNull(activityLoginBinding.edtPassword.getText()).toString();

        if (username.length() < 1) {
            Toast.makeText(LoginActivity.this, "Username tidak boleh kosong", Toast.LENGTH_SHORT).show();
        } else if (password.length() < 1) {
            Toast.makeText(LoginActivity.this, "Password tidak boleh kosong", Toast.LENGTH_SHORT).show();
        } else if (password.length() < 6) {
            Toast.makeText(LoginActivity.this, "Password terlalu pendek", Toast.LENGTH_SHORT).show();
        }else {
            onLogin(username, password);
        }
    }

    private void onLogin(String username, String password) {
        Network.provideApiService().loginUser(username, password).enqueue(new Callback<ResponseData<User>>() {
            @Override
            public void onResponse(Call<ResponseData<User>> call, Response<ResponseData<User>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getStatus()) {
                            User user = response.body().getData();
                            if (user != null) {
                                initHome(user);
                            } else {
                                Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "Login Gagal", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Login Gagal 2", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseData<User>> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Server Down", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure" + t.getLocalizedMessage());
            }
        });
    }

    private void initHome(User user) {
        saveUser(user);
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    private void saveUser(User user) {
        SessionManager.setIsLogin(LoginActivity.this, true);
        SessionManager.setUser(LoginActivity.this, user);
    }
}