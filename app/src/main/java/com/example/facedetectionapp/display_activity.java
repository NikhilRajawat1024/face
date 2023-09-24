package com.example.facedetectionapp;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.face.Face;
import com.google.mlkit.vision.face.FaceDetection;
import com.google.mlkit.vision.face.FaceDetector;
import com.google.mlkit.vision.face.FaceDetectorOptions;

import java.util.List;

public class display_activity extends AppCompatActivity {
    RecyclerView recyclerView;
    Bitmap capturedImage;
    Animation anim_textview2;
    List<Face> detectedFaces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        recyclerView = findViewById(R.id.recyclerView); // Initialize the RecyclerView

        // Retrieve the captured image bitmap that was passed from maincontent
        capturedImage = getIntent().getParcelableExtra("captured_image");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Perform face detection on the captured image
        FaceDetectionProcess(capturedImage);
    }

    public void FaceDetectionProcess(Bitmap bitmap) {
        InputImage image = InputImage.fromBitmap(bitmap, 0);

        FaceDetectorOptions highAccuracyopt = new FaceDetectorOptions.Builder()
                .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
                .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
                .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
                .enableTracking()
                .build();

        FaceDetector detector = FaceDetection.getClient(highAccuracyopt);

        Task<List<Face>> result = detector.process(image);

        result.addOnSuccessListener(new OnSuccessListener<List<Face>>() {
            @Override
            public void onSuccess(List<Face> faces) {
                detectedFaces = faces; // Store the detected faces

                // Initialize and set up the RecyclerView adapter
                RecyclerViewAdapter adapter = new RecyclerViewAdapter(detectedFaces);
                recyclerView.setLayoutManager(new LinearLayoutManager(display_activity.this));
                recyclerView.setAdapter(adapter);
            }
        });

        result.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Handle the failure
            }
        });
    }

    // RecyclerView Adapter to display detected faces
    private class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
        private final List<Face> faces;

        RecyclerViewAdapter(List<Face> faces) {
            this.faces = faces;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.face_cardview_layout, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Face face = faces.get(position);

            int id = face.getTrackingId();
            float rotY = face.getHeadEulerAngleY();
            float rotZ = face.getHeadEulerAngleZ();

            String personDetails = "Face ID: " + id + "\n";
            personDetails += "Head Rotation to Right: " + String.format("%.2f", rotY) + "deg.\n";
            personDetails += "Head Rotation to Left: " + String.format("%.2f", rotZ) + "deg.\n";

            if (face.getSmilingProbability() > 0) {
                float smilingProbability = face.getSmilingProbability();
                personDetails += "Smiling probability: " + String.format("%.2f", smilingProbability) + "\n";
            }

            if (face.getLeftEyeOpenProbability() > 0) {
                float leftEyeOpenProbability = face.getLeftEyeOpenProbability();
                personDetails += "Left eye open probability: " + String.format("%.2f", leftEyeOpenProbability) + "\n";
            }

            if (face.getRightEyeOpenProbability() > 0) {
                float rightEyeOpenProbability = face.getRightEyeOpenProbability();
                personDetails += "Right eye open probability: " + String.format("%.2f", rightEyeOpenProbability) + "\n";
            }

            holder.personDetailsTextView.setText(personDetails);
        }

        @Override
        public int getItemCount() {
            return faces.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView personDetailsTextView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                personDetailsTextView = itemView.findViewById(R.id.result);
            }
        }
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
            finish(); // Close the activity and exit the application
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
