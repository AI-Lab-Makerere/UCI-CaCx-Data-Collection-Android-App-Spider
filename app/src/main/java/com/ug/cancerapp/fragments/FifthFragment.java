package com.ug.cancerapp.fragments;

import android.app.DatePickerDialog;
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


public class FifthFragment extends Fragment {

    View view;
    Button back, next, datepicker;
    TextView date;
    EditText parity, abortions;
    RadioGroup radioGroup;
    RadioButton radioButton;
    private static final int YES = 0;
    private static final int NO = 1;
    private static final int NOT_SURE = 2;
    DatePickerDialog.OnDateSetListener dateSetListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_fifth, container, false);

        back = view.findViewById(R.id.back);
        next = view.findViewById(R.id.next);
        datepicker = view.findViewById(R.id.datepicker);
        date = view.findViewById(R.id.date);
        parity = view.findViewById(R.id.parity);
        abortions = view.findViewById(R.id.abortions);
        radioGroup = view.findViewById(R.id.radioGroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radioButton = radioGroup.findViewById(checkedId);
                int index = radioGroup.indexOfChild(radioButton);

                switch (index){
                    case YES:
                        Toast.makeText(getActivity(), "Yes", Toast.LENGTH_SHORT).show();
                        break;
                    case NO:
                        Toast.makeText(getActivity(), "NO", Toast.LENGTH_SHORT).show();
                        break;
                    case NOT_SURE:
                        Toast.makeText(getActivity(), "Not Sure", Toast.LENGTH_SHORT).show();
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
                fr.replace(R.id.fragment_container, new SixtyFragment());
                fr.addToBackStack(null);
                fr.commit();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment_container, new FourthFragment());
                fr.commit();
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

                dialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String data = day + "/" + month + "/" + year;
                date.setText(data);
            }
        };

        String child = parity.getText().toString();
        String abort = abortions.getText().toString();


        return view;
    }


}