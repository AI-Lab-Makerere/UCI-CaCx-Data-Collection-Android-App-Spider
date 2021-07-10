package com.ug.cancerapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;

import com.ug.cancerapp.R;

import java.util.ArrayList;
import java.util.Random;

public class LoopActivity extends AppCompatActivity {

    ArrayList<String> people;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loop);


        people = new ArrayList<>();

        people.add("mutebi");
        people.add("chodrine");
        people.add("musisi");
        people.add("john");

        for (int i = 0; i<people.size(); i++){

        }

    }
}