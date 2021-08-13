/*
 * Copyright (c) 2021. The UCI CaCx mobile app is an app developed by MUTEBI CHODRINE
 *  under the Artificial Intelligence Research lab, Makerere University and
 *  it was developed to help the Uganda Cancer Institute in their research.
 */

package com.ug.cancerapp.Fragments;

import static com.ug.cancerapp.Fragments.Camera1Fragment.IMAGE;
import static com.ug.cancerapp.Fragments.Camera3Fragment.IMAGE3;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ortiz.touchview.TouchImageView;
import com.ug.cancerapp.R;


public class Image3Fragment extends Fragment {

    Button next;
    TouchImageView touchImageView;
    View view;
    SharedPreferences sharedPreferences;
    public static final String SHARED_PREFS = "sharedPrefs";
    SharedPreferences.Editor editor;

    String mmv, sImage, sImage2, sImage3, sImage4;
    Bitmap bitmap1, bitmap2, bitmap3, bitmap4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_image1, container, false);

        touchImageView = view.findViewById(R.id.singleImage);
        next = view.findViewById(R.id.next);

        sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        sImage = sharedPreferences.getString(IMAGE3, "");
        byte[] bytes = Base64.decode(sImage, Base64.DEFAULT);
        bitmap1 = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        touchImageView.setImageBitmap(bitmap1);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment_container, new Image4Fragment());
                fr.addToBackStack(null);
                fr.commit();
            }
        });

        return view;
    }
}