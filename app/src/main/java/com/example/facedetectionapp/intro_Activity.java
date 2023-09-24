package com.example.facedetectionapp;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.facedetectionapp.MainActivity;
import com.example.facedetectionapp.R;

public class intro_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro2);

        ImageView imageView = findViewById(R.id.intro_image);
        imageView.setVisibility(View.VISIBLE);
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        int gifResourceId = R.drawable.animation;
        Glide.with(this)
                .load(gifResourceId)
                .into(imageView);



        // Set animation listener to start the next activity when the animation ends
        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // Animation started
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Animation ended, start the next activity (your main activity) after a delay
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(intro_Activity.this, MainActivity.class));
                        finish(); // Close the IntroActivity to prevent going back to it

                    }
                }, 2000); // Delay in milliseconds (2000ms = 2 seconds)
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // Animation repeated
            }
        });

        imageView.startAnimation(fadeIn);

    }
}
