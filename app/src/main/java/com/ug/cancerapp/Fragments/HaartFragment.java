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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ug.cancerapp.R;

public class HaartFragment extends Fragment {

    View view;
    Button back, next;
    RadioGroup radioGroup;
    RadioButton radioButton1, radioButton2;
    private static final int YES = 0;
    private static final int NO = 1;

    String value;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String CHOICE2 = "choice2";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_haart, container, false);

        back = view.findViewById(R.id.back);
        next = view.findViewById(R.id.next);
        radioGroup = view.findViewById(R.id.radioGroup);
        radioButton1 = view.findViewById(R.id.yes);
        radioButton2 = view.findViewById(R.id.no);

        loadData();
        updateViews();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radioButton = radioGroup.findViewById(checkedId);
                int index = radioGroup.indexOfChild(radioButton);

                switch (index) {
                    case YES:
//                        Toast.makeText(getActivity(), "Yes", Toast.LENGTH_SHORT).show();
                        value = "Yes";
                        break;
                    case NO:
//                        Toast.makeText(getActivity(), "No", Toast.LENGTH_SHORT).show();
                        value = "No";
                        break;

                    default:
                        break;
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (value.equals("Yes")) {
                    saveData();
                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                    fr.replace(R.id.fragment_container, new Haart2Fragment());
                    fr.addToBackStack(null);
                    fr.commit();

                } else if (value.equals("No")){
                    saveData();
                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                    fr.replace(R.id.fragment_container, new FifthFragment());
                    fr.addToBackStack(null);
                    fr.commit();

                } else {
                    Toast.makeText(getActivity(), "Select one option", Toast.LENGTH_SHORT).show();
                }

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment_container, new FourthFragment());
                fr.commit();
            }
        });


        return view;
    }

    private void saveData() {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(CHOICE2, value);

        editor.apply();
//        Toast.makeText(getActivity(), "Data saved", Toast.LENGTH_SHORT).show();
    }

    public void loadData() {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        value = sharedPreferences.getString(CHOICE2, "");

    }

    public void updateViews() {
        if (value.equals("Yes")) {
            radioButton1.setChecked(true);
        } else if (value.equals("No")) {
            radioButton2.setChecked(true);
        } else {
            radioButton1.setChecked(false);
            radioButton2.setChecked(false);
        }

    }
}