package com.example.facedetectionapp;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.face.FaceDetector;

public class maincontent extends AppCompatActivity {

    private final static int REQUEST_IMAGE_CAPTURE = 124;
    private InputImage firebasevision;
    private FaceDetector visionFaceDetector;

    private Button btn3;
    private ActivityResultLauncher<Intent> imageCaptureLauncher;
    private Bitmap capturedImage; // Declare a Bitmap variable to hold the captured image

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maincontent3);
         btn3 = findViewById(R.id.button3);

        ImageView imageView = findViewById(R.id.imageview2);
        Button btn1 = findViewById(R.id.button2);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FirebaseApp.initializeApp(this);

        // Initialize the ActivityResultLauncher
        imageCaptureLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        // Handle the result here
                        Intent data = result.getData();
                        if (data != null) {
                            // You can process the captured image here
                            // For example, you can display it in an ImageView
                            Bundle extras = data.getExtras();
                            if (extras != null) {
                                Object imageBitmap = extras.get("data");
                                if (imageBitmap instanceof Bitmap) {
                                    // Display the captured image in an ImageView
                                    imageView.setImageBitmap((Bitmap) imageBitmap);
                                    // Assign the captured image to capturedImage
                                    capturedImage = (Bitmap) imageBitmap;
                                }
                            }
                        }
                    } else if (result.getResultCode() == RESULT_CANCELED) {
                        // Handle cancellation here if needed
                        Toast.makeText(this, "Image capture cancelled", Toast.LENGTH_SHORT).show();
                    }
                });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Openfle();

            }

        });





        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (capturedImage != null) {
                    Intent intent = new Intent(maincontent.this, display_activity.class);
                    intent.putExtra("captured_image", capturedImage); // Pass the captured image
                    startActivity(intent);
                } else {
                    Toast.makeText(maincontent.this, "Capture an image first", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void Openfle() {
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (i.resolveActivity(getPackageManager()) != null) {
            imageCaptureLauncher.launch(i);
        } else {
            Toast.makeText(this, "Failed to open camera app", Toast.LENGTH_SHORT).show();
        }
    }
}
