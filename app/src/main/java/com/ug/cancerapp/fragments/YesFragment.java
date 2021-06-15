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
import android.widget.CheckBox;
import android.widget.Toast;

import com.ug.cancerapp.R;

public class YesFragment extends Fragment {

    CheckBox vb, vd, ap, as, other;
    View view;
    Button back, next;
    String s = "";

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String CHECKS1 = "checks1";
    public static final String CHECKS2 = "checks2";
    public static final String CHECKS3 = "checks3";
    public static final String CHECKS4 = "checks4";
    public static final String CHECKS5 = "checks5";
    public static final String SS = "ss";

    Boolean check1, check2, check3, check4, check5;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_yes, container, false);

        back = view.findViewById(R.id.back);
        next = view.findViewById(R.id.next);

        vb = view.findViewById(R.id.vb);
        vd= view.findViewById(R.id.vd);
        ap = view.findViewById(R.id.ap);
        as = view.findViewById(R.id.as);
        other = view.findViewById(R.id.other);

        loadData();
        updateViews();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(vb.isChecked()){
                    s += "Vaginal bleeding, ";
                }
                if(vd.isChecked()){
                    s += "Vaginal discharge, ";
                }
                if(ap.isChecked()){
                    s += "Abdominal pain, ";
                }
                if(as.isChecked()){
                    s += "Abdominal swelling, ";
                }
                if(other.isChecked()){
                    s = "Other";
                }

                if (s.contains("Other")){
                    saveData(s);
                    Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                    fr.replace(R.id.fragment_container, new OtherFragment());
                    fr.addToBackStack(null);
                    fr.commit();

                }else if (s.isEmpty()){
                    Toast.makeText(getActivity(), "Select at least one option", Toast.LENGTH_SHORT).show();

                }else {
                    saveData(s);
                    Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
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
                fr.replace(R.id.fragment_container, new SecondFragment());
                fr.commit();
            }
        });


        return view;
    }

    private void saveData(String s) {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(CHECKS1, vb.isChecked());
        editor.putBoolean(CHECKS2, vd.isChecked());
        editor.putBoolean(CHECKS3, ap.isChecked());
        editor.putBoolean(CHECKS4, as.isChecked());
        editor.putBoolean(CHECKS5, other.isChecked());
        editor.putString(SS, s);

        editor.apply();
        Toast.makeText(getActivity(), "Data saved", Toast.LENGTH_SHORT).show();
    }

    private void loadData(){
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        check1 = sharedPreferences.getBoolean(CHECKS1, false);
        check2 = sharedPreferences.getBoolean(CHECKS2, false);
        check3 = sharedPreferences.getBoolean(CHECKS3, false);
        check4 = sharedPreferences.getBoolean(CHECKS4, false);
        check5 = sharedPreferences.getBoolean(CHECKS5, false);
    }

    private void updateViews(){

        vb.setChecked(check1);
        vd.setChecked(check2);
        ap.setChecked(check3);
        as.setChecked(check4);
        other.setChecked(check5);
    }
}