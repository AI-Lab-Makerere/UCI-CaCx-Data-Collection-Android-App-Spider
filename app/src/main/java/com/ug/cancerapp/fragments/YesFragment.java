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

public class YesFragment extends Fragment {

    CheckBox vb, vd, ap, as, other;
    View view;
    Button back, next;

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


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String s = "";
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
                    Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                    fr.replace(R.id.fragment_container, new OtherFragment());
                    fr.addToBackStack(null);
                    fr.commit();

                }else {
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
                fr.replace(R.id.fragment_container, new FirstFragment());
                fr.commit();
            }
        });


        return view;
    }
}