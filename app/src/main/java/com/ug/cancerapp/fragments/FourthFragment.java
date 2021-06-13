package com.ug.cancerapp.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ug.cancerapp.R;

import static com.ug.cancerapp.fragments.Haart2Fragment.YEARS;
import static com.ug.cancerapp.fragments.HaartFragment.CHOICE2;
import static com.ug.cancerapp.fragments.OtherFragment.OTHER;
import static com.ug.cancerapp.fragments.ScreenFragment.CHECKSA1;
import static com.ug.cancerapp.fragments.ScreenFragment.CHECKSA2;
import static com.ug.cancerapp.fragments.ScreenFragment.CHECKSA3;
import static com.ug.cancerapp.fragments.ScreenFragment.CHOICE;
import static com.ug.cancerapp.fragments.ScreenFragment.DATEPICKER;
import static com.ug.cancerapp.fragments.ScreenFragment.TREATMENT;
import static com.ug.cancerapp.fragments.SecondFragment.TEXT;

public class FourthFragment extends Fragment {

    View view;
    Button back, next;
    RadioGroup radioGroup;
    RadioButton radioButton1, radioButton2, radioButton3;
    private static final int NEGATIVE = 0;
    private static final int POSITIVE = 1;
    private static final int UNKNOWN = 2;

    String value3, text;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT3 = "text3";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_fourth, container, false);

        back = view.findViewById(R.id.back);
        next = view.findViewById(R.id.next);
        radioButton1 = view.findViewById(R.id.negative);
        radioButton2 = view.findViewById(R.id.positive);
        radioButton3 = view.findViewById(R.id.unknown);
        radioGroup = view.findViewById(R.id.radioGroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radioButton = radioGroup.findViewById(checkedId);
                int index = radioGroup.indexOfChild(radioButton);

                switch (index) {
                    case NEGATIVE:
                        Toast.makeText(getActivity(), "Negative", Toast.LENGTH_SHORT).show();
                        value3 = "Negative";
                        break;
                    case POSITIVE:
                        Toast.makeText(getActivity(), "Positive", Toast.LENGTH_SHORT).show();
                        value3 = "Positive";
                        break;
                    case UNKNOWN:
                        Toast.makeText(getActivity(), "Unknown", Toast.LENGTH_SHORT).show();
                        value3 = "UnKnown";
                        break;
                    default:
                        break;
                }
            }
        });

        loadData();
        updateViews();
        getData();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
                if (value3.equals("Positive")) {
                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                    fr.replace(R.id.fragment_container, new HaartFragment());
                    fr.addToBackStack(null);
                    fr.commit();

                } else if (value3.equals("Negative")) {
                    deleteSharedPreferences();
                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                    fr.replace(R.id.fragment_container, new FifthFragment());
                    fr.addToBackStack(null);
                    fr.commit();

                } else {
                    Toast.makeText(getActivity(), "Select on option", Toast.LENGTH_SHORT).show();
                }

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (text.isEmpty()){
                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                    fr.replace(R.id.fragment_container, new ThirdFragment());
                    fr.commit();
                }else {
                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                    fr.replace(R.id.fragment_container, new ScreenFragment());
                    fr.commit();
                }

            }
        });

        return view;
    }

    private void saveData() {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(TEXT3, value3);

        editor.apply();
        Toast.makeText(getActivity(), "Data saved", Toast.LENGTH_SHORT).show();
    }

    public void loadData() {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        value3 = sharedPreferences.getString(TEXT3, "");

    }

    public void updateViews() {
        if (value3.equals("Negative")) {
            radioButton1.setChecked(true);
        } else if (value3.equals("Positive")) {
            radioButton2.setChecked(true);
        } else if (value3.equals("UnKnown")) {
            radioButton3.setChecked(true);
        } else {
            radioButton1.setChecked(false);
            radioButton2.setChecked(false);
            radioButton3.setChecked(false);
        }

    }

    private void getData() {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        text = sharedPreferences.getString(DATEPICKER, "");

    }

    private void deleteSharedPreferences() {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(CHOICE2);
        editor.remove(YEARS);
        editor.apply();

    }
}