package com.ug.cancerapp.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ug.cancerapp.R;

import java.text.DateFormat;
import java.util.Calendar;

import static com.ug.cancerapp.fragments.FourthFragment.TEXT3;
import static com.ug.cancerapp.fragments.Haart2Fragment.YEARS;
import static com.ug.cancerapp.fragments.ScreenFragment.DATEPICKER;


public class FifthFragment extends Fragment {

    View view;
    Button back, next, datepicker;
    TextView date;
    EditText parity, abortions;
    RadioGroup radioGroup;
    RadioButton radioButton1, radioButton2, radioButton3;
    private static final int YES = 0;
    private static final int NO = 1;
    private static final int NOT_SURE = 2;
    DatePickerDialog.OnDateSetListener dateSetListener;

    String value, time, child, abort, fouthText, haart2Text;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String PREGNANT = "pregnant";
    public static final String DATS = "date";
    public static final String PARITY = "parity";
    public static final String ABORTION = "abortion";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_fifth, container, false);

        back = view.findViewById(R.id.back);
        next = view.findViewById(R.id.next);
        datepicker = view.findViewById(R.id.datepicker);
        date = view.findViewById(R.id.date);
        parity = view.findViewById(R.id.parity);
        abortions = view.findViewById(R.id.abortions);
        radioGroup = view.findViewById(R.id.radioGroup);
        radioButton1 = view.findViewById(R.id.yes);
        radioButton2 = view.findViewById(R.id.no);
        radioButton3 = view.findViewById(R.id.not_sure);

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
                        Toast.makeText(getActivity(), "Yes", Toast.LENGTH_SHORT).show();
                        value = "Yes";
                        break;
                    case NO:
                        Toast.makeText(getActivity(), "NO", Toast.LENGTH_SHORT).show();
                        value = "No";
                        break;
                    case NOT_SURE:
                        Toast.makeText(getActivity(), "Not Sure", Toast.LENGTH_SHORT).show();
                        value = "Not Sure";
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

                if (value.isEmpty() || time.equals("No Date Selected") || child.isEmpty() || abort.isEmpty()) {
                    Toast.makeText(getActivity(), "Fill in all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    int part = Integer.parseInt(child);
                    int abo = Integer.parseInt(abort);

                    if (part > 20) {
                        parity.setError("Should be less than 21");
                        Toast.makeText(getActivity(), "The maximum number of children is 20", Toast.LENGTH_SHORT).show();
                    } else if (abo >= part) {
                        abortions.setError("should be less than " + part);
                        Toast.makeText(getActivity(), "The maximum number of abortions should not be greater than maximum number of children", Toast.LENGTH_SHORT).show();
                    } else {
                        saveData();
                        FragmentTransaction fr = getFragmentManager().beginTransaction();
                        fr.replace(R.id.fragment_container, new YesOrNoFragment());
                        fr.addToBackStack(null);
                        fr.commit();
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

        child = parity.getText().toString();
        abort = abortions.getText().toString();


        return view;
    }

    private void saveData() {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(PREGNANT, value);
        editor.putString(DATS, date.getText().toString());
        editor.putString(PARITY, parity.getText().toString());
        editor.putString(ABORTION, abortions.getText().toString());

        editor.apply();
        Toast.makeText(getActivity(), "Data saved", Toast.LENGTH_SHORT).show();
    }

    public void loadData() {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        value = sharedPreferences.getString(PREGNANT, "");
        time = sharedPreferences.getString(DATS, "");
        child = sharedPreferences.getString(PARITY, "");
        abort = sharedPreferences.getString(ABORTION, "");

    }

    public void updateViews() {
        date.setText(time);
        parity.setText(child);
        abortions.setText(abort);
        if (value.equals("Yes")) {
            radioButton1.setChecked(true);
        } else if (value.equals("No")) {
            radioButton2.setChecked(true);
        } else if (value.equals("Not Sure")) {
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

}