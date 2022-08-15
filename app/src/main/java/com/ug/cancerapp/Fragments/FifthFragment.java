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

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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

import static com.ug.cancerapp.Fragments.FourthFragment.TEXT3;
import static com.ug.cancerapp.Fragments.Haart2Fragment.YEARS;


public class FifthFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    View view;
    Button back, next, datepicker, btnNone;
    TextView date, times;
    String tim, text, months, timmy;
    Spinner spinner;
    EditText parity, abortions;
    RadioGroup radioGroup;
    RadioButton radioButton1, radioButton2, radioButton3;
    private static final int YES = 0;
    private static final int NO = 1;
    private static final int NOT_SURE = 2;
    DatePickerDialog.OnDateSetListener dateSetListener;

    String value, time, child, abort, fouthText, haart2Text, today;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String PREGNANT = "pregnant";
    public static final String DATS = "date";
    public static final String PARITY = "parity";
    public static final String ABORTION = "abortion";
    public static final String DATES1 = "dates1";
    public static final String DURATION1 = "duration1";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_fifth, container, false);

        back = view.findViewById(R.id.back);
        next = view.findViewById(R.id.next);
        datepicker = view.findViewById(R.id.datepicker);
//        btnNone = view.findViewById(R.id.none);
        date = view.findViewById(R.id.date);
        parity = view.findViewById(R.id.parity);
        abortions = view.findViewById(R.id.abortions);
        radioGroup = view.findViewById(R.id.radioGroup);
        radioButton1 = view.findViewById(R.id.yes);
        radioButton2 = view.findViewById(R.id.no);
        radioButton3 = view.findViewById(R.id.not_sure);
        spinner = view.findViewById(R.id.day);
        times = view.findViewById(R.id.time);

        Date dat = Calendar.getInstance().getTime();
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        today = format.format(dat);
//        Toast.makeText(getActivity(), today, Toast.LENGTH_SHORT).show();

        loadData();
        updateViews();
        getData();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radioButton = radioGroup.findViewById(checkedId);
                int index = radioGroup.indexOfChild(radioButton);

                switch (index) {
                    case YES:
//                        Toast.makeText(getActivity(), "Yes", Toast.LENGTH_SHORT).show();
                        value = "Yes";
                        break;
                    case NO:
//                        Toast.makeText(getActivity(), "NO", Toast.LENGTH_SHORT).show();
                        value = "No";
                        break;
                    case NOT_SURE:
//                        Toast.makeText(getActivity(), "Not Sure", Toast.LENGTH_SHORT).show();
                        value = "Unknown";
                        break;
                    default:
                        break;
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                child = parity.getText().toString();
                abort = abortions.getText().toString();
                tim = date.getText().toString();
                timmy = times.getText().toString();

                if (value.isEmpty() || child.isEmpty() || abort.isEmpty()) {
                    Toast.makeText(getActivity(), "Fill in all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    int part = Integer.parseInt(child);
                    int abo = Integer.parseInt(abort);

                    if (part > 20) {
                        parity.setError("Should be less than 21");
                        Toast.makeText(getActivity(), "The maximum number of children is 20", Toast.LENGTH_SHORT).show();
                    } else if (abo > part) {
                        abortions.setError("should be less than " + part);
                        Toast.makeText(getActivity(), "The maximum number of abortions should not be greater than maximum number of children", Toast.LENGTH_SHORT).show();
                    } else {
                        saveData();
                    }
                }

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!fouthText.equals("Positive")) {
                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                    fr.replace(R.id.fragment_container, new FourthFragment());
                    fr.commit();
                } else if (haart2Text.isEmpty()) {
                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                    fr.replace(R.id.fragment_container, new HaartFragment());
                    fr.commit();
                } else {
                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                    fr.replace(R.id.fragment_container, new Haart2Fragment());
                    fr.commit();
                }

            }
        });

        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
//                int day = calendar.get(Calendar.DAY_OF_MONTH);
//
//                int style = AlertDialog.THEME_HOLO_LIGHT;
//
//                DatePickerDialog dialog = new DatePickerDialog(getContext(), style, dateSetListener, year, month, day);
////                disable past date
////                dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
////                disable future date
//                dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
//                dialog.show();

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

        child = parity.getText().toString();
        abort = abortions.getText().toString();


        return view;
    }

    private void saveData() {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(PREGNANT, value);
        editor.putString(DATS, months);
        editor.putString(DATES1, tim);
        editor.putString(DURATION1, times.getText().toString());
        editor.putString(PARITY, parity.getText().toString());
        editor.putString(ABORTION, abortions.getText().toString());

        editor.apply();

        compareDates();
//        Toast.makeText(getActivity(), "Data saved", Toast.LENGTH_SHORT).show();
    }

    public void loadData() {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        value = sharedPreferences.getString(PREGNANT, "");
        months = sharedPreferences.getString(DATS, "");
        timmy = sharedPreferences.getString(DURATION1, "");
        tim = sharedPreferences.getString(DATES1, "");
        child = sharedPreferences.getString(PARITY, "");
        abort = sharedPreferences.getString(ABORTION, "");

    }

    public void updateViews() {
        date.setText(tim);
        times.setText(timmy);
        parity.setText(child);
        abortions.setText(abort);
        if (value.equals("Yes")) {
            radioButton1.setChecked(true);
        } else if (value.equals("No")) {
            radioButton2.setChecked(true);
        } else if (value.equals("Unknown")) {
            radioButton3.setChecked(true);
        } else {
            radioButton1.setChecked(false);
            radioButton2.setChecked(false);
            radioButton3.setChecked(false);
        }

    }

    private void getData() {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        fouthText = sharedPreferences.getString(TEXT3, "");
        haart2Text = sharedPreferences.getString(YEARS, "");

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        text = parent.getItemAtPosition(position).toString();
        if (!tim.isEmpty()){
            if (position == 1){
                times.setText(text);
                months = "01/" + tim;
//                Toast.makeText(getActivity(), months, Toast.LENGTH_SHORT).show();
            }else  if (position == 2){
                times.setText(text);
                months = "15/" + tim;
//                Toast.makeText(getActivity(), months, Toast.LENGTH_SHORT).show();
            }else if (position == 3) {
                times.setText(text);
                months = "28/" + tim;
//                Toast.makeText(getActivity(), months, Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(getActivity(), "Please first select the date", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void compareDates() {
        if (tim.isEmpty() && months.isEmpty()){
            FragmentTransaction fr = getFragmentManager().beginTransaction();
            fr.replace(R.id.fragment_container, new YesOrNoFragment());
            fr.addToBackStack(null);
            fr.commit();
        }else {
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
                    Toast.makeText(getActivity(), "Please change the both Date and also the Period of the month", Toast.LENGTH_SHORT).show();
                }else {
                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                    fr.replace(R.id.fragment_container, new YesOrNoFragment());
                    fr.addToBackStack(null);
                    fr.commit();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }
}