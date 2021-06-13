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

import static com.ug.cancerapp.fragments.FirstFragment.STUDY;
import static com.ug.cancerapp.fragments.OtherFragment.OTHER;
import static com.ug.cancerapp.fragments.ScreenFragment.CHECKSA1;
import static com.ug.cancerapp.fragments.ScreenFragment.CHECKSA2;
import static com.ug.cancerapp.fragments.ScreenFragment.CHECKSA3;
import static com.ug.cancerapp.fragments.ScreenFragment.CHOICE;
import static com.ug.cancerapp.fragments.ScreenFragment.DATEPICKER;
import static com.ug.cancerapp.fragments.ScreenFragment.TREATMENT;
import static com.ug.cancerapp.fragments.SecondFragment.TEXT;
import static com.ug.cancerapp.fragments.YesFragment.CHECKS1;
import static com.ug.cancerapp.fragments.YesFragment.CHECKS2;
import static com.ug.cancerapp.fragments.YesFragment.CHECKS3;
import static com.ug.cancerapp.fragments.YesFragment.CHECKS4;
import static com.ug.cancerapp.fragments.YesFragment.CHECKS5;


public class ThirdFragment extends Fragment {

    View view;
    Button back, next;
    RadioGroup radioGroup;
    RadioButton radioButton1, radioButton2;
    private static final int YES = 0;
    private static final int NO = 1;
    String value2;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT2 = "text2";

    String text, other;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_third, container, false);

        back = view.findViewById(R.id.back);
        next = view.findViewById(R.id.next);
        radioButton1 = view.findViewById(R.id.yes);
        radioButton2 = view.findViewById(R.id.no);
        radioGroup = view.findViewById(R.id.radioGroup);

        loadData();
        updateViews();
        getData();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radioButton = radioGroup.findViewById(checkedId);
                int index = radioGroup.indexOfChild(radioButton);

                switch (index){
                    case YES:
                        Toast.makeText(getActivity(), "Yes", Toast.LENGTH_SHORT).show();
                        value2 = "Yes";
                        break;
                    case NO:
                        Toast.makeText(getActivity(), "No", Toast.LENGTH_SHORT).show();
                        value2 = "No";
                        break;
                    default:
                        break;
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (value2.equals("Yes")){
                    saveData();
                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                    fr.replace(R.id.fragment_container, new ScreenFragment());
                    fr.addToBackStack(null);
                    fr.commit();

                }else if (value2.equals("No")){
                    deleteSharedPreferences();
                    saveData();
                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                    fr.replace(R.id.fragment_container, new FourthFragment());
                    fr.addToBackStack(null);
                    fr.commit();

                }else {
                    Toast.makeText(getActivity(), "Choose oe option", Toast.LENGTH_SHORT).show();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (text.equals("No")){
                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                    fr.replace(R.id.fragment_container, new SecondFragment());
                    fr.commit();
                }else if (other.isEmpty()){
                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                    fr.replace(R.id.fragment_container, new YesFragment());
                    fr.commit();
                }else {
                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                    fr.replace(R.id.fragment_container, new OtherFragment());
                    fr.commit();
                }

            }
        });

        return view;
    }

    private void deleteSharedPreferences() {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(CHECKSA1);
        editor.remove(CHECKSA2);
        editor.remove(CHECKSA3);
        editor.remove(DATEPICKER);
        editor.remove(TREATMENT);
        editor.remove(CHOICE);
        editor.apply();

    }

    private void saveData() {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(TEXT2, value2);

        editor.apply();
        Toast.makeText(getActivity(), "Data saved", Toast.LENGTH_SHORT).show();
    }

    public void loadData(){
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        value2 = sharedPreferences.getString(TEXT2, "");

    }

    public void updateViews(){
        if (value2.equals("Yes")){
            radioButton1.setChecked(true);
        }else if (value2.equals("No")){
            radioButton2.setChecked(true);
        }else {
            radioButton1.setChecked(false);
            radioButton2.setChecked(false);
        }

    }

    private void getData() {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        text = sharedPreferences.getString(TEXT, "");
        other = sharedPreferences.getString(OTHER, "");

    }
}