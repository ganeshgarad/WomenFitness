package com.sardar.softsolstudio.femalehomeworkout.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sardar.softsolstudio.femalehomeworkout.R;

public class SplashScreen extends AppCompatActivity {
    private Handler handler;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_activity);
        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent= new Intent(SplashScreen.this,MainActivity.class);
                startActivity(intent);
                finish();

            }
        },3000);

    }

    @Override
    public void onBackPressed() {
        handler.removeCallbacks((Runnable) handler);
        super.onBackPressed();
    }
}
