package com.ug.cancerapp.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ug.cancerapp.R;


public class OtherFragment extends Fragment {


    View view;
    EditText etOther;
    Button back, next;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String OTHER = "other";

    String symptom;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_other, container, false);

        back = view.findViewById(R.id.back);
        next = view.findViewById(R.id.next);
        etOther = view.findViewById(R.id.other);

        loadData();
        updateViews();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                symptom = etOther.getText().toString().trim();
                if (symptom.isEmpty()){
                    Toast.makeText(getActivity(), "Please fill in this field", Toast.LENGTH_SHORT).show();
                }else {
                    saveData();
                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                    fr.replace(R.id.fragment_container, new ThirdFragment());
                    fr.addToBackStack(null);
                    fr.commit();
                }

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment_container, new YesFragment());
                fr.commit();
            }
        });

        return view;
    }

    private void saveData(){
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(OTHER, etOther.getText().toString());

        editor.apply();
//        Toast.makeText(getActivity(), "Data saved", Toast.LENGTH_SHORT).show();
    }

    public void loadData(){
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        symptom = sharedPreferences.getString(OTHER, "");


    }

    public void updateViews(){
        etOther.setText(symptom);
    }
}