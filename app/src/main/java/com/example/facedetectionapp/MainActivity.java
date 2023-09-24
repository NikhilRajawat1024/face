package com.example.facedetectionapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    Button btn;
    Animation animate_btn;
    Animation animate_imageview;
    Animation animate_textview;
    TextView textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imageview = findViewById(R.id.imageview6);

        //creating gif animation
        int gifResourceId = R.drawable.mainactivity;
        Glide.with(this)
                .load(gifResourceId)
                .into(imageview);


        // Java
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast, findViewById(R.id.layout11));

// Set custom text for the Toast message
        TextView toastMessage = layout.findViewById(R.id.toast_message);
        toastMessage.setText("App is Started");

// Create and show the custom Toast
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();





        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button btn = findViewById(R.id.button1);
        textview = findViewById(R.id.textview1);


        // for button
        animate_btn = AnimationUtils.loadAnimation(this, R.anim.anim_btn);
        btn.setAnimation(animate_btn);

        // for imageview
        animate_imageview = AnimationUtils.loadAnimation(this, R.anim.anim_imageview);
        imageview.setAnimation(animate_imageview);

        // for textview
        animate_textview = AnimationUtils.loadAnimation(this, R.anim.anim_textview);
        textview.setAnimation(animate_textview);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, maincontent.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuquit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_item_close) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
