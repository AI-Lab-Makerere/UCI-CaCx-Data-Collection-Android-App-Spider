package com.ug.cancerapp.Fragments;

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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ug.cancerapp.R;

import java.util.Calendar;


public class ScreenFragment extends Fragment {

   View view;
   Button back, next, datePick;
   TextView date;
   CheckBox hpv, via, others;
    String s = "";
   EditText treatments;
   RadioGroup radioGroup;
   RadioButton radioButton1, radioButton2;

    private static final int Negative = 0;
    private static final int Positive = 1;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String CHECKSA1 = "checksa1";
    public static final String CHECKSA2 = "checksa2";
    public static final String CHECKSA3 = "checksa3";
    public static final String CHOICE= "choice";
    public static final String DATEPICKER = "datePicker";
    public static final String TREATMENT = "treatment";
    public static final String SSS = "sss";

    String datey, past, treat;
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
        radioGroup = view.findViewById(R.id.radioGroup);
        radioButton1 = view.findViewById(R.id.negative);
        radioButton2 = view.findViewById(R.id.positive);

        back = view.findViewById(R.id.back);
        next = view.findViewById(R.id.next);
        radioGroup = view.findViewById(R.id.radioGroup);

        loadData();
        updateViews();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radioButton = radioGroup.findViewById(checkedId);
                int index = radioGroup.indexOfChild(radioButton);

                switch (index){
                    case Negative:
                        Toast.makeText(getActivity(), "Negative", Toast.LENGTH_SHORT).show();
                        past = "Negative";
                        break;
                    case Positive:
                        Toast.makeText(getActivity(), "Positive", Toast.LENGTH_SHORT).show();
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
                datey = date.getText().toString();

                if(hpv.isChecked()){
                    s += "HPV, ";
                }
                if(via.isChecked()){
                    s += "VIA/VILI, ";
                }
                if(others.isChecked()){
                    s += "Others, ";
                }

                if (s.isEmpty() || past.isEmpty() || datey.equals("No Date Selected") || treat.isEmpty()){
                    Toast.makeText(getActivity(), "Please fill in all the fields", Toast.LENGTH_SHORT).show();
                }else{
                    saveData(s);
                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                    fr.replace(R.id.fragment_container, new FourthFragment());
                    fr.addToBackStack(null);
                    fr.commit();
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
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(getContext(), android.R.style.Theme_DeviceDefault_Dialog, dateSetListener, year, month, day);
//                disable past date
//                dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
//                disable future date
                dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                dialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String data = day + "/" + month + "/" + year;
                date.setText(data);
            }
        };

        return view;
    }

    private void saveData(String s) {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(DATEPICKER, date.getText().toString());
        editor.putBoolean(CHECKSA1, hpv.isChecked());
        editor.putBoolean(CHECKSA2, via.isChecked());
        editor.putBoolean(CHECKSA3, others.isChecked());
        editor.putString(SSS, s);
        editor.putString(CHOICE, past);
        editor.putString(TREATMENT, treatments.getText().toString());

        editor.apply();
        Toast.makeText(getActivity(), "Data saved", Toast.LENGTH_SHORT).show();
    }

    public void loadData(){
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        past = sharedPreferences.getString(CHOICE, "");
        check1 = sharedPreferences.getBoolean(CHECKSA1, false);
        check2 = sharedPreferences.getBoolean(CHECKSA2, false);
        check3 = sharedPreferences.getBoolean(CHECKSA3, false);
        datey = sharedPreferences.getString(DATEPICKER, "");
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
        date.setText(datey);
        treatments.setText(treat);
        hpv.setChecked(check1);
        via.setChecked(check2);
        others.setChecked(check3);

    }

}