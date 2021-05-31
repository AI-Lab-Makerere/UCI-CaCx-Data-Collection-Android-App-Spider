package com.ug.cancerapp.fragments;

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

public class FourthFragment extends Fragment {

    View view;
    Button back, next;
    RadioGroup radioGroup;
    RadioButton radioButton;
    private static final int NEGATIVE = 0;
    private static final int POSITIVE = 1;
    private static final int UNKNOWN = 2;

    public FourthFragment() {
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
        view =  inflater.inflate(R.layout.fragment_fourth, container, false);

        back = view.findViewById(R.id.back);
        next = view.findViewById(R.id.next);
        radioGroup = view.findViewById(R.id.radioGroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radioButton = radioGroup.findViewById(checkedId);
                int index = radioGroup.indexOfChild(radioButton);

                switch (index){
                    case NEGATIVE:
                        Toast.makeText(getActivity(), "Negative", Toast.LENGTH_SHORT).show();
                        break;
                    case POSITIVE:
                        Toast.makeText(getActivity(), "Positive", Toast.LENGTH_SHORT).show();
                        break;
                    case UNKNOWN:
                        Toast.makeText(getActivity(), "Unknown", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment_container, new FifthFragment());
                fr.addToBackStack(null);
                fr.commit();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment_container, new ThirdFragment());
                fr.commit();
            }
        });

        return view;
    }
}