package com.example.candynote.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.candynote.R;

import static android.os.Looper.getMainLooper;

public class AppLogoClass extends AppCompatActivity {

    private static final long ANIMATION_TIME = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_logo_class);

        new Handler(getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(AppLogoClass.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, ANIMATION_TIME);
    }

}