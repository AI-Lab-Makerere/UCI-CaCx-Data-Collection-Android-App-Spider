/*
 * Copyright (c) 2021. The UCI CaCx mobile app is an app developed by MUTEBI CHODRINE
 *  under the Artificial Intelligence Research lab, Makerere University and
 *  it was developed to help the Uganda Cancer Institute in their research.
 */

package com.ug.cancerapp.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ug.cancerapp.R;
import com.ug.cancerapp.Activities.SavingActivity;

public class ViaFragment extends Fragment {

    View view;
    RadioGroup radioGroup;
    RadioButton radioButton1, radioButton2;
    Button back, next;
    EditText message, lesion;

    private static final int POSITIVE = 0;
    private static final int NEGATIVE = 1;

    String via, notes, location;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String VIA = "via";
    public static final String NOTES = "notes";
    public static final String LESION = "lesion";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view  =  inflater.inflate(R.layout.fragment_via, container, false);

        back = view.findViewById(R.id.back);
        message = view.findViewById(R.id.notes);
        lesion = view.findViewById(R.id.lesion);
        next = view.findViewById(R.id.next);
        radioGroup = view.findViewById(R.id.radioGroup);
        radioButton1 = view.findViewById(R.id.positive);
        radioButton2 = view.findViewById(R.id.negative);

        loadData();
        updateViews();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radioButton = radioGroup.findViewById(checkedId);
                int index = radioGroup.indexOfChild(radioButton);

                switch (index){
                    case POSITIVE:
//                        Toast.makeText(getActivity(), "Positive", Toast.LENGTH_SHORT).show();
                        via = "Positive";
                        break;
                    case NEGATIVE:
//                        Toast.makeText(getActivity(), "Negative", Toast.LENGTH_SHORT).show();
                        via = "Negative";
                        break;
                    default:
                        break;
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notes = message.getText().toString();
                location = lesion.getText().toString();

                if (via.isEmpty() || notes.isEmpty() || location.isEmpty()){
                    Toast.makeText(getActivity(), "Fill in all fields", Toast.LENGTH_SHORT).show();
                }else {
                    saveData();
                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                    fr.replace(R.id.fragment_container, new Other2Fragment());
                    fr.commit();

                }

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment_container, new Camera4Fragment());
                fr.commit();
            }
        });

        return view;
    }


    private void saveData() {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(VIA, via);
        editor.putString(NOTES, message.getText().toString());
        editor.putString(LESION, lesion.getText().toString());

        editor.apply();
//        Toast.makeText(getActivity(), "Data saved", Toast.LENGTH_SHORT).show();
    }

    public void loadData(){
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        via = sharedPreferences.getString(VIA, "");
        notes = sharedPreferences.getString(NOTES, "");
        location = sharedPreferences.getString(LESION, "");

    }

    public void updateViews(){
        if (via.equals("Positive")){
            radioButton1.setChecked(true);
        }else if (via.equals("Negative")){
            radioButton2.setChecked(true);
        }else {
            radioButton1.setChecked(false);
            radioButton2.setChecked(false);
        }

        message.setText(notes);
        lesion.setText(location);

    }
}