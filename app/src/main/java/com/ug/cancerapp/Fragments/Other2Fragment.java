package com.ug.cancerapp.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.ug.cancerapp.Database.Form;
import com.ug.cancerapp.Database.FormViewModel;
import com.ug.cancerapp.R;
import com.ug.cancerapp.Activities.DashBoardActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.ug.cancerapp.Fragments.Camera1Fragment.IMAGE;
import static com.ug.cancerapp.Fragments.Camera2Fragment.IMAGE2;
import static com.ug.cancerapp.Fragments.Camera3Fragment.IMAGE3;
import static com.ug.cancerapp.Fragments.Camera4Fragment.IMAGE4;
import static com.ug.cancerapp.Fragments.FifthFragment.ABORTION;
import static com.ug.cancerapp.Fragments.FifthFragment.DATS;
import static com.ug.cancerapp.Fragments.FifthFragment.PARITY;
import static com.ug.cancerapp.Fragments.FifthFragment.PREGNANT;
import static com.ug.cancerapp.Fragments.FirstFragment.AGE;
import static com.ug.cancerapp.Fragments.FirstFragment.COUNTY;
import static com.ug.cancerapp.Fragments.FirstFragment.DISTRICT;
import static com.ug.cancerapp.Fragments.FirstFragment.INITIAL;
import static com.ug.cancerapp.Fragments.FirstFragment.STUDY;
import static com.ug.cancerapp.Fragments.FirstFragment.ZONE;
import static com.ug.cancerapp.Fragments.FourthFragment.TEXT3;
import static com.ug.cancerapp.Fragments.Haart2Fragment.YEARS;
import static com.ug.cancerapp.Fragments.HaartFragment.CHOICE2;
import static com.ug.cancerapp.Fragments.OtherFragment.OTHER;
import static com.ug.cancerapp.Fragments.ScreenFragment.CHOICE;
import static com.ug.cancerapp.Fragments.ScreenFragment.DATEPICKER;
import static com.ug.cancerapp.Fragments.ScreenFragment.SSS;
import static com.ug.cancerapp.Fragments.ScreenFragment.TREATMENT;
import static com.ug.cancerapp.Fragments.SecondFragment.TEXT;
import static com.ug.cancerapp.Fragments.SixtyFragment.S4;
import static com.ug.cancerapp.Fragments.ThirdFragment.TEXT2;
import static com.ug.cancerapp.Fragments.ViaFragment.LESION;
import static com.ug.cancerapp.Fragments.ViaFragment.NOTES;
import static com.ug.cancerapp.Fragments.ViaFragment.VIA;
import static com.ug.cancerapp.Fragments.YesFragment.SS;
import static com.ug.cancerapp.Fragments.YesOrNoFragment.CHOICES;


public class Other2Fragment extends Fragment {

    View view;
    Button back, send;
    String s = "";
    int num2 = 0;

    private FormViewModel formViewModel;

    public static final String SHARED_PREFS = "sharedPrefs";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_other2, container, false);

        back = view.findViewById(R.id.back);
        send = view.findViewById(R.id.save);

        formViewModel = ViewModelProviders.of(this).get(FormViewModel.class);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String studyID = sharedPreferences.getString(STUDY, "");
                String initial = sharedPreferences.getString(INITIAL, "");
                String age = sharedPreferences.getString(AGE, "");
                int number = Integer.parseInt(age);
                String district = sharedPreferences.getString(DISTRICT, "");
                String county = sharedPreferences.getString(COUNTY, "");
                String zone = sharedPreferences.getString(ZONE, "");
                String text = sharedPreferences.getString(TEXT, "");
                String ss = sharedPreferences.getString(SS, "");
                String symptom = sharedPreferences.getString(OTHER, "");
                String text2 = sharedPreferences.getString(TEXT2, "");
                String past = sharedPreferences.getString(CHOICE, "");
                String sss = sharedPreferences.getString(SSS, "");
                String datey = sharedPreferences.getString(DATEPICKER, "");
                String treat = sharedPreferences.getString(TREATMENT, "");
                String value3 = sharedPreferences.getString(TEXT3, "");
                String valuex = sharedPreferences.getString(CHOICE2, "");
                String year = sharedPreferences.getString(YEARS, "");

                if (year.isEmpty()){
                    num2 = 0;
                }else {
                    num2 = Integer.parseInt(year);
                }

                String value = sharedPreferences.getString(PREGNANT, "");
                String time = sharedPreferences.getString(DATS, "");
                String child = sharedPreferences.getString(PARITY, "");
                int children = Integer.parseInt(child);
                String abort = sharedPreferences.getString(ABORTION, "");
                int abortion = Integer.parseInt(abort);
                String choice = sharedPreferences.getString(CHOICES, "");
                String s4 = sharedPreferences.getString(S4, "");
                String sImage = sharedPreferences.getString(IMAGE, "");
                String sImage2 = sharedPreferences.getString(IMAGE2, "");
                String sImage3 = sharedPreferences.getString(IMAGE3, "");
                String sImage4 = sharedPreferences.getString(IMAGE4, "");
                String via = sharedPreferences.getString(VIA, "");
                String notes = sharedPreferences.getString(NOTES, "");
                String location = sharedPreferences.getString(LESION, "");
                String diagnosis = "";
                Boolean consult = false;

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
                String format = simpleDateFormat.format(new Date());

                Form form = new Form(format, studyID, initial, district, county, zone, number, text, ss, symptom,
                        text2, datey, sss, treat, past, value3, valuex, num2, value, time, children, abortion, choice,
                        s4, sImage, sImage2, sImage3, sImage4, via, location, notes, diagnosis, consult);

                formViewModel.insert(form);
                editor.clear();
                editor.apply();
                Toast.makeText(getActivity(), "Task Successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), DashBoardActivity.class));

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment_container, new ViaFragment());
                fr.commit();
            }
        });

        return view;
    }
}