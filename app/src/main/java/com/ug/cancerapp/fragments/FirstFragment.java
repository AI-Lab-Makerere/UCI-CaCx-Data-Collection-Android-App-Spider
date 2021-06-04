package com.ug.cancerapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ug.cancerapp.Database.Form;
import com.ug.cancerapp.Database.FormDAO;
import com.ug.cancerapp.Database.FormDatabase;
import com.ug.cancerapp.Database.FormViewModel;
import com.ug.cancerapp.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FirstFragment extends Fragment {

    EditText etstudy, etinitial, etage, etdistrict, etcounty, etzone;
    View view;
    Button back, next;
    long rowId;

    private FormViewModel formViewModel;


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

        formViewModel = ViewModelProviders.of(getActivity()).get(FormViewModel.class);

        etstudy = view.findViewById(R.id.studyId);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
        String format = simpleDateFormat.format(new Date());

//        etstudy.setText(format);

        etinitial = view.findViewById(R.id.initials);

        etage = view.findViewById(R.id.age);

        etdistrict = view.findViewById(R.id.district);

        etcounty = view.findViewById(R.id.county);

        etzone = view.findViewById(R.id.zone);

        etage.addTextChangedListener(textWatcher);

//        back = view.findViewById(R.id.back);
        next = view.findViewById(R.id.next);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String study = etstudy.getText().toString().trim();
                String initial = etinitial.getText().toString().trim();
                String age = etage.getText().toString().trim();
                int number = Integer.parseInt(age);
                String district = etdistrict.getText().toString().trim();
                String county = etcounty.getText().toString().trim();
                String zone = etzone.getText().toString().trim();

                if(study.isEmpty() && initial.isEmpty() && age.isEmpty() && district.isEmpty() && county.isEmpty() && zone.isEmpty()){

                    Toast.makeText(getActivity(), "Please fill in all the fields", Toast.LENGTH_SHORT).show();

                }else {

                    Form form = new Form();

                    form.setStudyID(study);
                    form.setInitials(initial);
                    form.setAge(number);
                    form.setDistrict(district);
                    form.setCounty(county);
                    form.setVillage(zone);
//                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
//                    String format = simpleDateFormat.format(new Date());
//                    form.setDate(format);

                    rowId = formViewModel.insert(form);


                    Toast.makeText(getActivity(), "The ID is: " + rowId, Toast.LENGTH_SHORT).show();

//                    FragmentTransaction fr = getFragmentManager().beginTransaction();
//                    fr.replace(R.id.fragment_container, new SecondFragment());
//                    fr.addToBackStack(null);
//                    fr.commit();
                }

            }
        });

        return view;
    }

    TextWatcher textWatcher =new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String age = etage.getText().toString().trim();
            int number = Integer.parseInt(age);
            if (number <= 15){
                etage.setError("The Age only should be greater than 15");
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
    
}