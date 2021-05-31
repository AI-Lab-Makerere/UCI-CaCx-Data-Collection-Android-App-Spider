package com.ug.cancerapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ug.cancerapp.R;

public class FirstFragment extends Fragment {

    EditText etstudy, etinitial, etage, etdistrict, etcounty, etzone;
    View view;
    Button back, next;


    public FirstFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_first, container, false);

        etstudy = view.findViewById(R.id.studyId);

        etinitial = view.findViewById(R.id.initials);

        etage = view.findViewById(R.id.age);

        etdistrict = view.findViewById(R.id.district);

        etcounty = view.findViewById(R.id.county);

        etzone = view.findViewById(R.id.zone);



//        back = view.findViewById(R.id.back);
        next = view.findViewById(R.id.next);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String study = etstudy.getText().toString().trim();
                String initial = etinitial.getText().toString().trim();
                String age = etage.getText().toString().trim();
                String district = etdistrict.getText().toString().trim();
                String county = etcounty.getText().toString().trim();
                String zone = etzone.getText().toString().trim();

//                if(study.isEmpty() && initial.isEmpty() && age.isEmpty() && district.isEmpty() && county.isEmpty() && zone.isEmpty()){
////                    startActivity(new Intent(getActivity(), SecondFragment.class));
//                    Toast.makeText(getActivity(), "Please fill in all the fields", Toast.LENGTH_SHORT).show();
//
//                }else {
                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                    fr.replace(R.id.fragment_container, new SecondFragment());
                    fr.addToBackStack(null);
                    fr.commit();
//                }

            }
        });

        return view;
    }




}