/*
 * Copyright (c) 2021. The UCI CaCx mobile app is an app developed by MUTEBI CHODRINE
 *  under the Artificial Intelligence Research lab, Makerere University and 
 *  it was developed to help the Uganda Cancer Institute in their research.
 */

package com.ug.cancerapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.ug.cancerapp.Fragments.Camera1Fragment;
import com.ug.cancerapp.Fragments.FifthFragment;
import com.ug.cancerapp.Fragments.Other2Fragment;
import com.ug.cancerapp.Fragments.ScreenFragment;
import com.ug.cancerapp.Fragments.ViaFragment;
import com.ug.cancerapp.R;
import com.ug.cancerapp.Fragments.FirstFragment;

import static com.ug.cancerapp.Activities.DashBoardActivity.SHARED_PREFS;

public class CollectActivity extends AppCompatActivity {

    Dialog dialog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("CaCx Screening");


        if (ContextCompat.checkSelfPermission(CollectActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(CollectActivity.this,
                    new String[]{
                            Manifest.permission.CAMERA
                    },
                    100);
        }

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, new FirstFragment());
//        fragmentTransaction.add(R.id.fragment_container, new Other2Fragment());
        fragmentTransaction.commit();

    }


    @Override
    public void onBackPressed() {

        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        dialog = new Dialog(CollectActivity.this);
        dialog.setContentView(R.layout.exit);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        Button btnYes = dialog.findViewById(R.id.yes);
        Button btnNo = dialog.findViewById(R.id.no);

        dialog.show();
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.clear();
                editor.apply();
                dialog.dismiss();
                startActivity(new Intent(CollectActivity.this, DashBoardActivity.class));
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}