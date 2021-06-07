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


public class SixtyFragment extends Fragment {

    View view;
    Button back, next;
    CheckBox pill, inj, imp, others, none, cds, iud;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String CHECK1 = "check1";
    public static final String CHECK2 = "check2";
    public static final String CHECK3 = "check3";
    public static final String CHECK4 = "check4";
    public static final String CHECK5 = "check5";
    public static final String CHECK6 = "check6";
    public static final String CHECK7 = "check7";


    Boolean check1, check2, check3, check4, check5, check6, check7;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sixty, container, false);

        back = view.findViewById(R.id.back);
        next = view.findViewById(R.id.next);
        pill = view.findViewById(R.id.pills);
        inj = view.findViewById(R.id.inj);
        imp = view.findViewById(R.id.imp);
        others = view.findViewById(R.id.oth);
        none = view.findViewById(R.id.none);
        cds = view.findViewById(R.id.cds);
        iud = view.findViewById(R.id.iud);

        loadData();
        updateViews();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkedlist();

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment_container, new FifthFragment());
                fr.commit();
            }
        });




        return view;
    }

    private void checkedlist() {
        saveData();
        String s = "";
        if(pill.isChecked()){
            s += "Pills, ";
        }
        if(inj.isChecked()){
            s += "InjectorPlan, ";
        }
        if(iud.isChecked()){
            s += "IUD, ";
        }
        if(imp.isChecked()){
            s += "Implant, ";
        }
        if(cds.isChecked()){
            s += "Condoms, ";
        }
        if(others.isChecked()){
            s = "Others";
        }
        if(none.isChecked()){
            s = "None";
        }

        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();

        if (s.contains("Others")){
            FragmentTransaction fr = getFragmentManager().beginTransaction();
            fr.replace(R.id.fragment_container, new Other2Fragment());
            fr.addToBackStack(null);
            fr.commit();

        }else {
            FragmentTransaction fr = getFragmentManager().beginTransaction();
            fr.replace(R.id.fragment_container, new Camera1Fragment());
            fr.addToBackStack(null);
            fr.commit();
        }

    }

    private void saveData() {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(CHECK1, pill.isChecked());
        editor.putBoolean(CHECK2, inj.isChecked());
        editor.putBoolean(CHECK3, iud.isChecked());
        editor.putBoolean(CHECK4, cds.isChecked());
        editor.putBoolean(CHECK5, imp.isChecked());
        editor.putBoolean(CHECK6, others.isChecked());
        editor.putBoolean(CHECK7, none.isChecked());

        editor.apply();
        Toast.makeText(getActivity(), "Data saved", Toast.LENGTH_SHORT).show();
    }


    private void loadData(){
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        check1 = sharedPreferences.getBoolean(CHECK1, false);
        check2 = sharedPreferences.getBoolean(CHECK2, false);
        check3 = sharedPreferences.getBoolean(CHECK3, false);
        check4 = sharedPreferences.getBoolean(CHECK4, false);
        check5 = sharedPreferences.getBoolean(CHECK5, false);
        check6 = sharedPreferences.getBoolean(CHECK6, false);
        check7 = sharedPreferences.getBoolean(CHECK7, false);
    }

    private void updateViews(){

        pill.setChecked(check1);
        inj.setChecked(check2);
        iud.setChecked(check3);
        cds.setChecked(check4);
        imp.setChecked(check5);
        others.setChecked(check6);
        none.setChecked(check7);
    }
}