package com.ug.cancerapp.fragments;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

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
        String s = "";
        if(pill.isChecked()){
            s += "\n Pills";
        }
        if(inj.isChecked()){
            s += "\n InjectorPlan";
        }
        if(iud.isChecked()){
            s += "\n IUD";
        }
        if(imp.isChecked()){
            s += "\n Implant";
        }
        if(cds.isChecked()){
            s += "\n Condoms";
        }
        if(others.isChecked()){
            s += "\n Others";
        }
        if(none.isChecked()){
            s += "\n None";
        }

        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();

        FragmentTransaction fr = getFragmentManager().beginTransaction();
        fr.replace(R.id.fragment_container, new Camera1Fragment());
        fr.addToBackStack(null);
        fr.commit();
    }
}