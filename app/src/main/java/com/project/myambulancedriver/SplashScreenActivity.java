package com.project.myambulancedriver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.project.myambulancedriver.pref.SessionManager;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        onInit();
    }

    private void onInit() {
        new Handler().postDelayed((Runnable) () -> {
            Boolean isLogin = SessionManager.getIsLogin(this);
            if (isLogin) {
                initMain();
            } else {
                initBoarding();
            }
        }, 2000);
    }

    private void initBoarding() {
        startActivity(new Intent(SplashScreenActivity.this, OnboardingActivity.class));
        finish();
    }

    private void initMain() {
        startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
        finish();
    }
}