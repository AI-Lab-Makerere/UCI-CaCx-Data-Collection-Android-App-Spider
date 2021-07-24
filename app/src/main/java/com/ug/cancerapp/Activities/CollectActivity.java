package com.ug.cancerapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.ug.cancerapp.Fragments.Camera1Fragment;
import com.ug.cancerapp.Fragments.FifthFragment;
import com.ug.cancerapp.Fragments.ScreenFragment;
import com.ug.cancerapp.Fragments.ViaFragment;
import com.ug.cancerapp.R;
import com.ug.cancerapp.Fragments.FirstFragment;

public class CollectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("CaCx Screening");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        if (ContextCompat.checkSelfPermission(CollectActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(CollectActivity.this,
                    new String[]{
                            Manifest.permission.CAMERA
                    },
                    100);
        }

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, new FirstFragment());
        fragmentTransaction.commit();

    }




}