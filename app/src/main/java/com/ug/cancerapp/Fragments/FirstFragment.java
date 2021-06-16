package com.ug.cancerapp.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ug.cancerapp.Database.FormViewModel;
import com.ug.cancerapp.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FirstFragment extends Fragment {

    EditText etstudy, etinitial, etage, etdistrict, etcounty, etzone;
    View view;
    Button back, next;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String STUDY = "study";
    public static final String INITIAL = "initial";
    public static final String AGE = "age";
    public static final String DISTRICT = "district";
    public static final String COUNTY = "county";
    public static final String ZONE = "zone";

    String study, initial, age, district, county, zone;

    private FormViewModel formViewModel;



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
        next = view.findViewById(R.id.next);

        loadData();
        updateViews();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                study = etstudy.getText().toString().trim();
                initial = etinitial.getText().toString().trim();
                age = etage.getText().toString().trim();
                district = etdistrict.getText().toString().trim();
                county = etcounty.getText().toString().trim();
                zone = etzone.getText().toString().trim();

                if(study.isEmpty() || initial.isEmpty() || age.isEmpty() || district.isEmpty() || county.isEmpty() || zone.isEmpty()){

                    Toast.makeText(getActivity(), "Please fill in all the fields", Toast.LENGTH_SHORT).show();

                }else if (!age.isEmpty()){
                    int number = Integer.parseInt(age);
                    if (number <= 15){
                        Toast.makeText(getActivity(), "The patient should be 16 years and above", Toast.LENGTH_SHORT).show();
                        etage.setError("only 16 years and above");
                    }else {
                        saveData();
                    }

                }
                else {

                    saveData();
//                    Form form = new Form();
//
//                    form.setStudyID(study);
//                    form.setInitials(initial);
//                    form.setAge(number);
//                    form.setDistrict(district);
//                    form.setCounty(county);
//                    form.setVillage(zone);
//                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
//                    String format = simpleDateFormat.format(new Date());
//                    form.setDate(format);
//
//                    rowId = formViewModel.insert(form);
//
//
//                    Toast.makeText(getActivity(), "The ID is: " + rowId, Toast.LENGTH_SHORT).show();

                }

            }
        });

        return view;
    }

    private void saveData(){
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(STUDY, etstudy.getText().toString());
        editor.putString(INITIAL, etinitial.getText().toString());
        editor.putString(AGE, etage.getText().toString());
        editor.putString(DISTRICT, etdistrict.getText().toString());
        editor.putString(COUNTY, etcounty.getText().toString());
        editor.putString(ZONE, etzone.getText().toString());

        editor.apply();
        Toast.makeText(getActivity(), "Data saved", Toast.LENGTH_SHORT).show();
        FragmentTransaction fr = getFragmentManager().beginTransaction();
        fr.replace(R.id.fragment_container, new SecondFragment());
        fr.addToBackStack(null);
        fr.commit();
    }

    public void loadData(){
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        study = sharedPreferences.getString(STUDY, "");
        initial = sharedPreferences.getString(INITIAL, "");
        age = sharedPreferences.getString(AGE, "");
        district = sharedPreferences.getString(DISTRICT, "");
        county = sharedPreferences.getString(COUNTY, "");
        zone = sharedPreferences.getString(ZONE, "");


    }

    public void updateViews(){
        etstudy.setText(study);
        etinitial.setText(initial);
        etage.setText(age);
        etdistrict.setText(district);
        etcounty.setText(county);
        etzone.setText(zone);

    }

}