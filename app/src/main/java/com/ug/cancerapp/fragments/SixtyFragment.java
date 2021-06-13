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
    CheckBox pill, inj, imp, cds, iud;
    String value;
    String s = "";

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String CHECK1 = "check1";
    public static final String CHECK2 = "check2";
    public static final String CHECK3 = "check3";
    public static final String CHECK4 = "check4";
    public static final String CHECK5 = "check5";
    public static final String CHECKS = "checks";
    public static final String S4 = "s4";


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
                fr.replace(R.id.fragment_container, new YesOrNoFragment());
                fr.commit();
            }
        });




        return view;
    }

    private void checkedlist() {


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

        if (s.isEmpty()){
            Toast.makeText(getActivity(), "Choose at least on option", Toast.LENGTH_SHORT).show();
        }else {
            saveData(s);
            FragmentTransaction fr = getFragmentManager().beginTransaction();
            fr.replace(R.id.fragment_container, new Camera1Fragment());
            fr.addToBackStack(null);
            fr.commit();
        }


    }

    private void saveData(String s) {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(CHECK1, pill.isChecked());
        editor.putBoolean(CHECK2, inj.isChecked());
        editor.putBoolean(CHECK3, iud.isChecked());
        editor.putBoolean(CHECK4, cds.isChecked());
        editor.putBoolean(CHECK5, imp.isChecked());
        editor.putString(CHECKS, "available");
        editor.putString(S4, s);

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
        value = sharedPreferences.getString(CHECKS, "");
    }

    private void updateViews(){

        pill.setChecked(check1);
        inj.setChecked(check2);
        iud.setChecked(check3);
        cds.setChecked(check4);
        imp.setChecked(check5);
    }
}