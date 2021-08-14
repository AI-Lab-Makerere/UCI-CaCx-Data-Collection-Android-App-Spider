/*
 * Copyright (c) 2021. The UCI CaCx mobile app is an app developed by MUTEBI CHODRINE
 *  under the Artificial Intelligence Research lab, Makerere University and
 *  it was developed to help the Uganda Cancer Institute in their research.
 */

package com.ug.cancerapp.Fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ug.cancerapp.R;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class ScreenFragment extends Fragment implements AdapterView.OnItemSelectedListener{

   View view;
   Button back, next, datePick, btnNone;
   TextView date, time;
   String tim, text, months, timmy;
   Spinner spinner;
   CheckBox hpv, via, others;
    String s = "";
   EditText treatments;
   RadioGroup radioGroup, radioGroup2;
   RadioButton radioButton1, radioButton2;
    LinearLayout linearLayout;

    private static final int Negative = 0;
    private static final int Positive = 1;

    private static final int Hpv = 0;
    private static final int Via = 1;
    private static final int Other = 2;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String METHOD = "method";
    public static final String CHOICE= "choice";
    public static final String CHECKSA1 = "checksa1";
    public static final String CHECKSA2 = "checksa2";
    public static final String CHECKSA3 = "checksa3";
    public static final String DATEPICKER = "datePicker";
    public static final String DATES = "dates";
    public static final String TREATMENT = "treatment";
    public static final String DURATION = "duration";

    String datey, past, treat, method, today;
    Boolean check1, check2, check3, check4, check5;

    DatePickerDialog.OnDateSetListener dateSetListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_screen, container, false);

        datePick = view.findViewById(R.id.datepicker);
        date = view.findViewById(R.id.date);
        treatments = view.findViewById(R.id.treatment);
        hpv = view.findViewById(R.id.hpv);
        via = view.findViewById(R.id.via);
        others = view.findViewById(R.id.other);
        datePick = view.findViewById(R.id.datepicker);
//        btnNone = view.findViewById(R.id.none);
        radioGroup = view.findViewById(R.id.radioGroup);
//        radioGroup2 = view.findViewById(R.id.radioGroup2);
        radioButton1 = view.findViewById(R.id.negative);
        radioButton2 = view.findViewById(R.id.positive);
        spinner = view.findViewById(R.id.day);
        time = view.findViewById(R.id.time);
        radioButton2 = view.findViewById(R.id.positive);
        linearLayout = view.findViewById(R.id.vnc);

        back = view.findViewById(R.id.back);
        next = view.findViewById(R.id.next);
        radioGroup = view.findViewById(R.id.radioGroup);

        Date dat = Calendar.getInstance().getTime();
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        today = format.format(dat);

        loadData();
        updateViews();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radioButton = radioGroup.findViewById(checkedId);
                int index = radioGroup.indexOfChild(radioButton);

                switch (index){
                    case Negative:
//                        Toast.makeText(getActivity(), "Negative", Toast.LENGTH_SHORT).show();
                        past = "Negative";
                        break;
                    case Positive:
//                        Toast.makeText(getActivity(), "Positive", Toast.LENGTH_SHORT).show();
                        past = "Positive";
                        break;
                    default:
                        break;
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                treat = treatments.getText().toString();
                tim = date.getText().toString();
                timmy = time.getText().toString();

                if(hpv.isChecked()){
                    s += "HPV, ";
                }
                if(via.isChecked()){
                    s += "VIA/VIL, ";
                }
                if(others.isChecked()){
                    s += "Others, ";
                }

                s = s.replaceAll(", $", "");
                if (s.isEmpty() || past.isEmpty() || tim.isEmpty() || treat.isEmpty() || timmy.isEmpty() ){
                    Toast.makeText(getActivity(), "Please fill in all the fields", Toast.LENGTH_SHORT).show();
                }else{
                    saveData(s);
                    compareDates();
                }
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


        datePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);

                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(getActivity(), new MonthPickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(int selectedMonth, int selectedYear) {
                        tim = (selectedMonth + 1) + "/" + selectedYear;
                        date.setText(tim);
//                        linearLayout.setVisibility(View.VISIBLE);
                    }
                }, year, month);

                builder.setActivatedMonth(month)
                        .setMinYear(2010)
                        .setActivatedYear(year)
                        .setMaxYear(year)
                        .setTitle("Select Month")
                        .build().show();
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.same, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        return view;
    }

    private void saveData(String s) {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(DATEPICKER, months);
        editor.putString(DATES, tim);
        editor.putString(DURATION, time.getText().toString());
        editor.putString(METHOD, s);
        editor.putString(CHOICE, past);
        editor.putBoolean(CHECKSA1, hpv.isChecked());
        editor.putBoolean(CHECKSA2, via.isChecked());
        editor.putBoolean(CHECKSA3, others.isChecked());
        editor.putString(TREATMENT, treatments.getText().toString());

        editor.apply();
//        Toast.makeText(getActivity(), "Data saved", Toast.LENGTH_SHORT).show();
    }

    public void loadData(){
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        past = sharedPreferences.getString(CHOICE, "");
        check1 = sharedPreferences.getBoolean(CHECKSA1, false);
        check2 = sharedPreferences.getBoolean(CHECKSA2, false);
        check3 = sharedPreferences.getBoolean(CHECKSA3, false);
//        method = sharedPreferences.getString(METHOD, "");
        months = sharedPreferences.getString(DATEPICKER, "");
        timmy = sharedPreferences.getString(DURATION, "");
        tim = sharedPreferences.getString(DATES, "");
        treat = sharedPreferences.getString(TREATMENT, "");

    }

    public void updateViews(){
        if (past.equals("Negative")){
            radioButton1.setChecked(true);
        }else if (past.equals("Positive")){
            radioButton2.setChecked(true);
        }else {
            radioButton1.setChecked(false);
            radioButton2.setChecked(false);
        }
        date.setText(tim);
        treatments.setText(treat);
        time.setText(timmy);

//        if (method.equals("HPV")){
//            hpv.setChecked(true);
//        }else if (method.equals("VIA/VIL")){
//           via.setChecked(true);
//        }else if (method.equals("Others")){
//            others.setChecked(true);
//        }
        hpv.setChecked(check1);
        via.setChecked(check2);
        others.setChecked(check3);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        text = parent.getItemAtPosition(position).toString();
        if (!(tim == null)){
            if (position == 1){
                time.setText(text);
                months = "01/" + tim;
//                Toast.makeText(getActivity(), months, Toast.LENGTH_SHORT).show();
            }else  if (position == 2){
                time.setText(text);
                months = "15/" + tim;
//                Toast.makeText(getActivity(), months, Toast.LENGTH_SHORT).show();
            }else if (position == 3) {
                time.setText(text);
                months = "28/" + tim;
//                Toast.makeText(getActivity(), months, Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(getActivity(), "Please first select the month and the year", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void compareDates() {
        SimpleDateFormat sdf1=new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date d1 = sdf1.parse(today);
            Date d2 = sdf1.parse(months);
            Calendar cal=Calendar.getInstance();
            cal.setTime(d1);
            long y=cal.getTimeInMillis();
            cal.setTime(d2);
            long y1=cal.getTimeInMillis();
            if(y<y1){
                Toast.makeText(getActivity(), "Please check the Screening Date", Toast.LENGTH_SHORT).show();
            }else {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment_container, new FourthFragment());
                fr.addToBackStack(null);
                fr.commit();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}