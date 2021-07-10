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

import static com.ug.cancerapp.Fragments.FirstFragment.AGE;


public class Haart2Fragment extends Fragment {

    View view;
    Button back, next;
    EditText years;

    String value, text;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String YEARS = "years";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_haart2, container, false);

        back = view.findViewById(R.id.back);
        next = view.findViewById(R.id.next);
        years = view.findViewById(R.id.years);

        loadData();
        updateViews();
        getData();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                value = years.getText().toString();


                if (value.isEmpty()){
                    Toast.makeText(getActivity(), "Fill in this field", Toast.LENGTH_SHORT).show();

                }else {
                    int age = Integer.parseInt(text);
                    int year = Integer.parseInt(value);

                    if (year > age){
                        Toast.makeText(getActivity(), "This value should not be greater or equal to the patient's age " + age, Toast.LENGTH_SHORT).show();
                    }else {
                        saveData();
                        FragmentTransaction fr = getFragmentManager().beginTransaction();
                        fr.replace(R.id.fragment_container, new FifthFragment());
                        fr.addToBackStack(null);
                        fr.commit();
                    }

                }





            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment_container, new HaartFragment());
                fr.commit();
            }
        });

        return view;
    }

    private void saveData() {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(YEARS, years.getText().toString());

        editor.apply();
//        Toast.makeText(getActivity(), "Data saved", Toast.LENGTH_SHORT).show();
    }

    public void loadData() {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        value = sharedPreferences.getString(YEARS, "");

    }

    public void updateViews() {
        years.setText(value);
    }

    private void getData() {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        text = sharedPreferences.getString(AGE, "");

    }
}