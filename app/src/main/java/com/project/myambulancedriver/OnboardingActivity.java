package com.project.myambulancedriver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.project.myambulancedriver.databinding.ActivityOnboardingBinding;

public class OnboardingActivity extends AppCompatActivity {

    ActivityOnboardingBinding activityOnboardingBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityOnboardingBinding = ActivityOnboardingBinding.inflate(getLayoutInflater());
        View view = activityOnboardingBinding.getRoot();
        setContentView(view);
        onInit();
    }

    private void onInit() {
        activityOnboardingBinding.btnStart.setOnClickListener(view -> {
            startActivity(new Intent(OnboardingActivity.this, LoginActivity.class));
            finish();
        });
    }
}