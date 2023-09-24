package com.example.facedetectionapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.DialogFragment;

public class ResultDialog extends DialogFragment {
    Button btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_maincontent3, container, false);
        String text = "";

        // Find the button by its ID
        btn = view.findViewById(R.id.button2);

        // Retrieve the text from the arguments bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            text = bundle.getString("RESULT_TEXT", "");
        }



        return view;
    }
}
